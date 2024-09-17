package content.region.kandarin.quest.tree.handlers

import content.global.skill.support.agility.AgilityHandler
import content.region.kandarin.quest.tree.TreeGnomeVillage.Companion.mazeEntrance
import content.region.kandarin.quest.tree.TreeGnomeVillage.Companion.mazeVillage
import content.region.kandarin.quest.tree.dialogue.BallistaDialogue
import content.region.kandarin.quest.tree.dialogue.ElkoyDialogue
import content.region.kandarin.quest.tree.dialogue.KhazardWarlordDialogue
import core.api.*
import org.rs.consts.Items
import org.rs.consts.NPCs
import core.game.dialogue.DialogueFile
import core.game.global.action.ClimbActionHandler
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.map.RegionManager
import core.game.world.update.flag.context.Animation

/**
 * Tree gnome village listeners.
 */
class TreeGnomeVillageListeners : InteractionListener {

    private val ballista = 2181
    private val crumbledWall = 12762
    private val closedChest = 2183
    private val openedChest = 2182
    private val strongholdDoor = 2184
    private val treeGnomeVillageLadders = intArrayOf(5250,5251)

    override fun defineDestinationOverrides() {
        setDest(IntType.NPC, intArrayOf(NPCs.TRACKER_GNOME_2_482), "talk-to") { _, _ ->
            return@setDest Location.create(2524, 3256, 0)
        }
        setDest(IntType.SCENERY, intArrayOf(5250)) { _, _ ->
            return@setDest Location.create(2533, 3156, 0)
        }
    }

    override fun defineListeners() {
        on(treeGnomeVillageLadders, IntType.SCENERY, "climb-down", "climb-up") { player, node ->
            when(getUsedOption(player)) {
                "climb-down" -> ClimbActionHandler.climb(player, Animation(827), Location(2533, 9556, 0))
                else -> ClimbActionHandler.climb(player, Animation(828), Location(2533, 3156, 0))
            }
            return@on true
        }

        on(NPCs.ELKOY_5179, IntType.NPC, "talk-to") { player, npc ->
            openDialogue(player, ElkoyDialogue(), npc)
            return@on true
        }
        on(NPCs.ELKOY_5182, IntType.NPC, "talk-to") { player, npc ->
            openDialogue(player, ElkoyDialogue(), npc)
            return@on true
        }
        on(NPCs.ELKOY_5182, IntType.NPC, "follow") { player, _ ->
            ElkoyDialogue().travelCutscene(player, if (player.location.y > 3161) mazeVillage else mazeEntrance)
            return@on true
        }

        on(NPCs.KHAZARD_WARLORD_477, IntType.NPC, "talk-to") { player, npc ->
            openDialogue(player, KhazardWarlordDialogue(), npc)
            return@on true
        }
        on(ballista, IntType.SCENERY, "fire") { player, _ ->
            openDialogue(player, BallistaDialogue())
            return@on true
        }
        on(crumbledWall, IntType.SCENERY, "climb-over") { player, _ ->
            openDialogue(player, ClimbWall())
            return@on true
        }
        on(closedChest, IntType.SCENERY, "open") { player, node ->
            SceneryBuilder.replace(node.asScenery(), Scenery(openedChest, node.location, node.asScenery().rotation), 10)
            val upperGuard: NPC = RegionManager.getNpc(player.location, NPCs.KHAZARD_COMMANDER_478, 10) ?: return@on true
            sendChat(upperGuard, "Oi! You! Get out of there.")
            sendChat(upperGuard, "Oi! You! Hands off the property of General Khazard.", 3)
            upperGuard.attack(player)
            return@on true
        }
        on(openedChest, IntType.SCENERY, "search") { player, _ ->
            if (!inInventory(player, Items.ORB_OF_PROTECTION_587)) {
                sendDialogue(player, "You search the chest. Inside you find the gnomes' stolen orb of protection.")
                addItemOrDrop(player, Items.ORB_OF_PROTECTION_587)
            }
            return@on true
        }
        on(strongholdDoor, IntType.SCENERY, "open") { player, node ->
            if (player.location.y >= 3251) {
                DoorActionHandler.handleAutowalkDoor(player, node as Scenery)
            }
            return@on true
        }
    }

    private
    class ClimbWall : DialogueFile() {
        val climbAnimation = Animation(839)
        override fun handle(componentID: Int, buttonID: Int) {
            if (inBorders(player!!, 2507, 3254, 2512, 3259)) {
                return sendMessage(player!!, "I can't get over the wall from this side.")
            }
            if (getQuestStage(player!!, "Tree Gnome Village") > 30) {
                when (stage) {
                    0 -> sendDialogue(
                        player!!,
                        "The wall has been reduced to rubble. It should be possible to climb over the remains"
                    ).also { stage++ }

                    1 -> AgilityHandler.forceWalk(
                        player!!,
                        -1,
                        player!!.location,
                        player!!.location.transform(Direction.NORTH, 2),
                        climbAnimation,
                        15,
                        0.0,
                        null
                    ).also {
                        end()
                        val lowerGuard: NPC = RegionManager.getNpc(player!!.location, NPCs.KHAZARD_COMMANDER_478, 10) ?: return
                        lock(player!!, 4)
                        GameWorld.Pulser.submit(object : Pulse(1) {
                            var count = 0
                            override fun pulse(): Boolean {
                                when (count) {
                                    0 -> sendChat(lowerGuard, "What? How did you manage to get in here.")
                                    2 -> sendChat(player!!, "I've come for the orb.")
                                    3 -> {
                                        sendChat(lowerGuard, "I'll never let you take it.")
                                        lowerGuard.attack(player)
                                        unlock(player!!)
                                        return true
                                    }
                                }
                                count++
                                return false
                            }
                        })
                    }
                }
            }
        }
    }
}
