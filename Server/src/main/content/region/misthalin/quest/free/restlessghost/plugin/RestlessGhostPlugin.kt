package content.region.misthalin.quest.free.restlessghost.plugin

import content.region.misthalin.quest.free.restlessghost.RestlessGhost
import core.api.consts.Sounds
import core.api.playAudio
import core.api.setVarp
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
                15052 -> player.packetDispatch.sendMessage("You search the coffin and find some human remains.")
                15053 -> player.dialogueInterpreter.sendDialogue("There's a nice and complete skeleton in here!")
                15050 -> searchAltar(player, scenery)
                15051 -> {
                    if (!player.getQuestRepository().isComplete(RestlessGhost.NAME) && !player.bank.containsItem(SKULL) && !player.inventory.containsItem(SKULL)) {
                        player.inventory.add(SKULL)
                        player.packetDispatch.sendMessage("You find another skull.")
                    }
                    player.getQuestRepository().getQuest(RestlessGhost.NAME).setStage(player, 40)
                }

                2145 -> toggleCoffin(player, scenery)
            }
        }
        return true
    }

    private fun toggleCoffin(player: Player, scenery: Scenery) {
        val open = scenery.id == 2145
        player.lock(2)
        player.animate(if (open) OPEN_ANIM else CLOSE_ANIM)
        SceneryBuilder.replace(scenery, scenery.transform(if (open) 15061 else 2145))
        player.packetDispatch.sendMessage("You " + (if (open) "open" else "close") + " the coffin.")
        if (open && !player.getQuestRepository().isComplete(RestlessGhost.NAME)) {
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
        if (player.getQuestRepository().getQuest(RestlessGhost.NAME).getStage(player) != 30) {
            player.packetDispatch.sendMessage("You search the altar and find nothing.")
            return
        }
        if (!hasSkull) {
            if (!player.inventory.add(SKULL)) {
                GroundItemManager.create(SKULL, player)
            }
            setVarp(player, 728, 5, true)
            player.getQuestRepository().getQuest(RestlessGhost.NAME).setStage(player, 40)
            player.packetDispatch.sendMessage("The skeleton in the corner suddenly comes to life!")
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

    /**
     * Restless ghost NPC.
     */
    class RestlessGhostNPC : AbstractNPC {
        constructor() : super(0, null)

        internal constructor(id: Int, location: Location) : super(id, location, false)

        override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
            return RestlessGhostNPC(id, location)
        }

        override fun init() {
            super.init()
            properties.combatPulse.style = CombatStyle.MELEE
        }

        override fun tick() {
            super.tick()
            val pl = getAttribute<Player>("player", null)
            if (pl != null) {
                if (getAttribute("dead", false) || !getLocation().withinDistance(pl.location, 16)) {
                    clear()
                }
            }
        }

        override fun isAttackable(entity: Entity, style: CombatStyle, message: Boolean): Boolean {
            val player = (entity as Player)
            val pl = getAttribute<Player>("player", null)
            return pl != null && pl === player
        }

        override fun finalizeDeath(killer: Entity) {
            super.finalizeDeath(killer)
            removeAttribute("player")
        }

        override fun isHidden(player: Player): Boolean {
            val pl = getAttribute<Player>("player", null)
            if (this.respawnTick > ticks) {
                return true
            }
            return player.getQuestRepository().isComplete(RestlessGhost.NAME) || (pl != null && player !== pl)
        }

        override fun getIds(): IntArray {
            return ID
        }

        companion object {
            private val ID = intArrayOf(459, 457)
        }
    }

    companion object {
        private val OPEN_ANIM = Animation(536)
        private val CLOSE_ANIM = Animation(535)
        private val SKULL = Item(964)
        private var GHOST: RestlessGhostNPC? = null
        private val COFFIN_IDS = intArrayOf(2145, 15061, 15052, 15053, 15050, 15051)
        private val OPTIONS = arrayOf("open", "close", "search")
    }
}
