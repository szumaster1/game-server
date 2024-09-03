package content.dd.challenge.handlers

import content.dd.challenge.handlers.npc.EarthWarriorChampionNPC.Companion.spawnEarthWarriorChampion
import content.dd.challenge.handlers.npc.GhoulChampionNPC.Companion.spawnGhoulChampion
import content.dd.challenge.handlers.npc.GiantChampionNPC.Companion.spawnGiantChampion
import content.dd.challenge.handlers.npc.GoblinChampionNPC.Companion.spawnGoblinChampion
import content.dd.challenge.handlers.npc.HobgoblinChampionNPC.Companion.spawnHobgoblinChampion
import content.dd.challenge.handlers.npc.ImpChampionNPC.Companion.spawnImpChampion
import content.dd.challenge.handlers.npc.JogreChampionNPC.Companion.spawnJogreChampion
import content.dd.challenge.handlers.npc.LesserDemonChampionNPC.Companion.spawnLesserDemonChampion
import content.dd.challenge.handlers.npc.SkeletonChampionNPC.Companion.spawnSkeletonChampion
import content.dd.challenge.handlers.npc.ZombieChampionNPC.Companion.spawnZombieChampion
import core.api.*
import cfg.consts.Components
import cfg.consts.Items
import cfg.consts.NPCs
import cfg.consts.Scenery
import content.dd.challenge.dialogue.LarxusDialogueFile
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.map.Location
import core.game.world.map.zone.ZoneBorders
import core.game.world.map.zone.ZoneRestriction

/**
 * Represents the Champion challenge.
 * @author Szumaster
 */
class ChampionChallengeListener : InteractionListener, MapArea {

    val earthWarriorScroll = Items.CHAMPION_SCROLL_6798
    val ghoulScroll = Items.CHAMPION_SCROLL_6799
    val giantScroll = Items.CHAMPION_SCROLL_6800
    val goblinScroll = Items.CHAMPION_SCROLL_6801
    val hobgoblinScroll = Items.CHAMPION_SCROLL_6802
    val impScroll = Items.CHAMPION_SCROLL_6803
    val jogreScroll = Items.CHAMPION_SCROLL_6804
    val lesserDemonScroll = Items.CHAMPION_SCROLL_6805
    val skeletonScroll = Items.CHAMPION_SCROLL_6806
    val zombieScroll = Items.CHAMPION_SCROLL_6807

    val blankScroll = Components.BLANK_SCROLL_222

    private val impScrollContent = arrayOf("How about picking on someone your own size? I'll", "see you at the Champion's Guild.", "", "Champion of Imps")
    private val goblinScrollContent = arrayOf("Fight me if you think you can human, I'll wait", "for you in the Champion's Guild.", "", "Champion of Goblins")
    private val skeletonScrollContent = arrayOf("I'll be waiting at the Champions' Guild to", "collect your bones.", "", "Champion of Skeletons")
    private val zombieScrollContent = arrayOf("You come to Champions' Guild, you fight me,", "I squish you, I get brains!", "", "Champion of Zombies")
    private val giantScrollContent = arrayOf("Get yourself to the Champions' Guild, if you", "dare to face me puny human.", "", "Champion of Giants")
    private val hobgoblinScrollContent = arrayOf("You won't defeat me, though you're welcome to", "try at the Champions' Guild.", "", "Champion of Hobgoblins")
    private val ghoulScrollContent = arrayOf("Come and duel me at the Champions' Guild, I'll", "make sure nothing goes to waste.", "", "Champion of Ghouls")
    private val earthWarriorScrollContent = arrayOf("I challenge you to a duel, come to the arena beneath", "the Champion's Guild and fight me if you dare.", "", "Champion of Earth Warriors")
    private val jogreScrollContent = arrayOf("You think you can defeat me? Come to the", "Champion's Guild and prove it!", "", "Champion of Jogres")
    private val lesserDemonScrollContent = arrayOf("Come to the Champion's Guild so I can banish", "you mortal!", "", "Champion of Lesser Demons")

    private val portcullisScenery = Scenery.PORTCULLIS_10553
    private val ladderID = Scenery.LADDER_10554
    private val championStatue = Scenery.CHAMPION_STATUE_10556
    private val championStatueOpen = Scenery.CHAMPION_STATUE_10557
    private val trapdoorID = Scenery.TRAPDOOR_10558
    private val trapdoorOpen = Scenery.TRAPDOOR_10559
    private val regionID = 12696

    // Register listeners for various interactions related to the Champion Challenge.
    override fun defineListeners() {

        // Handle reading champion scrolls.
        on(ChampionScrollsDropHandler.SCROLLS, IntType.ITEM, "read") { player, node ->
            updateAndReadScroll(player, node.asItem())
            return@on true
        }

        // Handle opening the trapdoor.
        on(trapdoorID, IntType.SCENERY, "open") { _, node ->
            replaceScenery(node.asScenery(), trapdoorOpen, 100, node.location)
            return@on true
        }

        // Handle using champion scrolls on Larxus.
        onUseWith(IntType.NPC,
            ChampionScrollsDropHandler.SCROLLS, NPCs.LARXUS_3050) { player, _, _ ->
            openDialogue(player, LarxusDialogueFile(true))
            return@onUseWith true
        }

        // Handle closing the trapdoor.
        on(trapdoorOpen, IntType.SCENERY, "close") { _, node ->
            replaceScenery(node.asScenery(), trapdoorID, -1, node.location)
            return@on true
        }

        // Handle opening the champion statue.
        on(championStatue, IntType.SCENERY, "open") { _, node ->
            replaceScenery(node.asScenery(), championStatueOpen, 100, node.location)
            return@on true
        }

        // Handle climbing up the ladder.
        on(ladderID, IntType.SCENERY, "climb-up") { player, _ ->
            teleport(player, Location.create(3185, 9758, 0))
            return@on true
        }

        // Handle climbing down the champion statue.
        on(championStatueOpen, IntType.SCENERY, "climb-down") { player, _ ->
            teleport(player, Location.create(3182, 9758, 0))
            return@on true
        }

        // Handle opening the portcullis and starting the champion challenge.
        on(portcullisScenery, IntType.SCENERY, "open") { player, node ->
            if (player.getAttribute("championsarena:start", false) == false) {
                sendNPCDialogue(
                    player, NPCs.LARXUS_3050, "You need to arrange a challenge with me before you enter the arena."
                )
            } else {
                lock(player, 3)
                submitWorldPulse(object : Pulse() {
                    var counter = 0
                    override fun pulse(): Boolean {
                        when (counter++) {
                            1 -> player.familiarManager.dismiss()
                            2 -> DoorActionHandler.handleDoor(player, node.asScenery())
                            3 -> when {
                                removeItem(player, impScroll) -> spawnImpChampion(player)
                                removeItem(player, goblinScroll) -> spawnGoblinChampion(player)
                                removeItem(player, skeletonScroll) -> spawnSkeletonChampion(player)
                                removeItem(player, zombieScroll) -> spawnZombieChampion(player)
                                removeItem(player, giantScroll) -> spawnGiantChampion(player)
                                removeItem(player, hobgoblinScroll) -> spawnHobgoblinChampion(player)
                                removeItem(player, ghoulScroll) -> spawnGhoulChampion(player)
                                removeItem(player, earthWarriorScroll) -> {
                                    spawnEarthWarriorChampion(player)
                                    player.prayer.reset()
                                }

                                removeItem(player, jogreScroll) -> spawnJogreChampion(player)
                                removeItem(player, lesserDemonScroll) -> spawnLesserDemonChampion(player)
                            }
                        }
                        return false
                    }
                })
            }
            return@on true
        }
    }

    // Handle updating and reading champion scrolls.
    private fun updateAndReadScroll(player: Player, item: Item) {
        val id = item.id
        openInterface(player, blankScroll).also {
            when (id) {
                impScroll -> setInterfaceText(player, impScrollContent.joinToString("<br>"), blankScroll, 4)
                goblinScroll -> setInterfaceText(player, goblinScrollContent.joinToString("<br>"), blankScroll, 4)
                skeletonScroll -> setInterfaceText(player, skeletonScrollContent.joinToString("<br>"), blankScroll, 4)

                zombieScroll -> setInterfaceText(player, zombieScrollContent.joinToString("<br>"), blankScroll, 4)
                giantScroll -> setInterfaceText(player, giantScrollContent.joinToString("<br>"), blankScroll, 4)
                hobgoblinScroll -> setInterfaceText(player, hobgoblinScrollContent.joinToString("<br>"), blankScroll, 4)

                ghoulScroll -> setInterfaceText(player, ghoulScrollContent.joinToString("<br>"), blankScroll, 4)
                earthWarriorScroll -> setInterfaceText(player, earthWarriorScrollContent.joinToString("<br>"), blankScroll, 4)

                jogreScroll -> setInterfaceText(player, jogreScrollContent.joinToString("<br>"), blankScroll, 4)
                lesserDemonScroll -> setInterfaceText(player, lesserDemonScrollContent.joinToString("<br>"), blankScroll, 4)
            }
        }
    }

    // Override the destination for climbing down the trapdoor.
    override fun defineDestinationOverrides() {
        setDest(IntType.SCENERY, intArrayOf(trapdoorOpen), "climb-down") { _, _ ->
            return@setDest Location.create(3191, 3355, 0)
        }
    }

    // Define the area borders for the champion challenge.
    override fun defineAreaBorders(): Array<ZoneBorders> {
        return arrayOf(getRegionBorders(regionID))
    }

    // Define the restrictions for the champion challenge area.
    override fun getRestrictions(): Array<ZoneRestriction> {
        return arrayOf(ZoneRestriction.CANNON, ZoneRestriction.FIRES, ZoneRestriction.RANDOM_EVENTS)
    }

}
