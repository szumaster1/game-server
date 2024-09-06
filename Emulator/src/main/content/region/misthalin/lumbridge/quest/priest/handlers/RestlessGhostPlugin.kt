package content.region.misthalin.lumbridge.quest.priest.handlers

import cfg.consts.Animations
import cfg.consts.Items
import cfg.consts.NPCs
import content.region.misthalin.lumbridge.quest.priest.RestlessGhost
import cfg.consts.Sounds
import core.api.*
import core.cache.def.impl.SceneryDefinition
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.GameWorld.ticks
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Represents the Restless ghost plugin.
 */
@Initializable
class RestlessGhostPlugin : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        for (coffin in COFFIN_IDS) {
            for (option in OPTIONS) {
                SceneryDefinition.forId(coffin).handlers["option:$option"] = this
            }
        }
        RestlessGhostNPC().newInstance(arg)
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        if (GHOST == null) {
            GHOST = RestlessGhostNPC(457, Location.create(3250, 3195, 0))
            GHOST!!.init()
            GHOST!!.isInvisible = true
        }
        val id = node.id
        val scenery = node as Scenery
        when (option) {
            "open" -> {
                when (id) {
                    else -> toggleCoffin(player, scenery)
                }
                when (id) {
                    15052, 15053 -> toggleCoffin(player, scenery)
                }
            }

            "close" -> when (id) {
                15052, 15053 -> toggleCoffin(player, scenery)
            }

            "search" -> when (id) {
                15052 -> sendMessage(player, "You search the coffin and find some human remains.")
                15053 -> sendDialogue(player, "There's a nice and complete skeleton in here!")
                15050 -> searchAltar(player, scenery)
                15051 -> {
                    if (!isQuestComplete(player, "The Restless Ghost") && !inBank(player, Items.SKULL_964) && !inInventory(player, Items.SKULL_964)) {
                        addItem(player, Items.SKULL_964)
                        sendMessage(player, "You find another skull.")
                    }
                    setQuestStage(player,"The Restless Ghost", 40)
                }

                2145 -> toggleCoffin(player, scenery)
            }
        }
        return true
    }

    private fun toggleCoffin(player: Player, scenery: Scenery) {
        val open = scenery.id == 2145
        lock(player, 2)
        animate(player, if (open) Animation(Animations.OPEN_CHEST_536) else Animation(Animations.OPEN_POH_WARDROBE_535))
        SceneryBuilder.replace(scenery, scenery.transform(if (open) 15061 else 2145))
        sendMessage(player, "You " + (if (open) "open" else "close") + " the coffin.")
        if (open && !isQuestComplete(player, "The Restless Ghost")) {
            playAudio(player, Sounds.RG_GHOST_APPROACH_1743, 1)
            sendGhost()
        }
    }

    private fun sendGhost() {
        if (!GHOST!!.isInvisible) {
            return
        }
        GHOST!!.isInvisible = false
        Pulser.submit(object : Pulse(100, GHOST) {
            override fun pulse(): Boolean {
                GHOST!!.isInvisible = true
                return true
            }
        })
    }

    private fun searchAltar(player: Player, scenery: Scenery) {
        val hasSkull = scenery.id == 15051
        if (getQuestStage(player, "The Restless Ghost") != 30) {
            sendMessage(player, "You search the altar and find nothing.")
            return
        }
        if (!hasSkull) {
            if (!addItem(player, Items.SKULL_964)) {
                GroundItemManager.create(Item(Items.SKULL_964), player)
            }
            setVarp(player, 728, 5, true)
            setQuestStage(player, "The Restless Ghost", 40)
            sendMessage(player, "The skeleton in the corner suddenly comes to life!")
            sendSkeleton(player)
        }
    }

    private fun sendSkeleton(player: Player) {
        val skeleton = NPC.create(459, Location.create(3120, 9568, 0))
        skeleton.isWalks = false
        skeleton.isRespawn = false
        skeleton.setAttribute("player", player)
        skeleton.init()
        playAudio(player, Sounds.RG_SKELETON_AWAKE_1746)
        skeleton.properties.combatPulse.style = CombatStyle.MELEE
        skeleton.properties.combatPulse.attack(player)
    }

    companion object {
        private var GHOST: RestlessGhostNPC? = null
        private val COFFIN_IDS = intArrayOf(2145, 15061, 15052, 15053, 15050, 15051)
        private val OPTIONS = arrayOf("open", "close", "search")
    }
}
