package content.global.handlers.areas.falador

import content.data.BossKillCounter.Companion.addtoKillcount
import content.data.LightSource.Companion.getActiveLightSource
import content.data.LightSource.Companion.hasActiveLightSource
import content.global.skill.skillcape.SkillcapePerks
import content.global.skill.skillcape.SkillcapePerks.Companion.isActive
import core.api.*
import cfg.consts.Components
import cfg.consts.Graphics
import cfg.consts.NPCs
import cfg.consts.Sounds
import core.cache.def.impl.SceneryDefinition
import core.game.component.Component
import core.game.component.ComponentDefinition
import core.game.component.ComponentPlugin
import core.game.global.action.DigAction
import core.game.global.action.DigSpadeHandler.register
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.impl.Animator.Priority
import core.game.node.entity.impl.Projectile
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.map.RegionManager.getLocalPlayers
import core.game.world.map.zone.impl.DarkZone
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.network.packet.PacketRepository
import core.network.packet.context.InterfaceContext
import core.network.packet.outgoing.Interface
import core.plugin.Initializable
import core.plugin.Plugin
import core.tools.RandomFunction

/**
 * Represents the Giant mole NPC.
 */
@Initializable
class GiantMoleNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    private var digging = false

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return GiantMoleNPC(id, location)
    }

    override fun init() {
        super.init()
        super.setWalks(true)
        super.walkRadius = 128
    }

    private fun dig() {
        digging = true
        lock(5)
        properties.combatPulse.stop()
        walkingQueue.reset()
        locks.lockMovement(5)
        val dir = Direction.get(RandomFunction.randomize(4))
        faceLocation(centerLocation.transform(dir.stepX shl 2, dir.stepY shl 2, 0))
        setDirection(dir)
        val index = RandomFunction.randomize(DIG_LOCATIONS.size)
        var dest = DIG_LOCATIONS[index]
        if (dest.withinDistance(getLocation())) {
            dest = DIG_LOCATIONS[(index + 1) % DIG_LOCATIONS.size]
        }
        val destination = dest
        Pulser.submit(object : Pulse(1, this) {
            var count: Int = 0
            var hole: Location? = null

            override fun pulse(): Boolean {
                if (count == 0) {
                    hole = visualizeDig(destination, true)
                } else if (count == 1) {
                    if (RandomFunction.RANDOM.nextBoolean()) {
                        splatterMud(hole)
                    }
                } else if (count == 3) {
                    properties.teleportLocation = destination
                } else if (count == 4) {
                    visualizeDig(destination, false)
                    digging = false
                    return true
                }
                count++
                return false
            }
        })
    }

    private fun splatterMud(hole: Location?) {
        for (p in getLocalPlayers(centerLocation, (size() shr 1) + 2)) {
            PacketRepository.send(Interface::class.java, InterfaceContext(p, 548, 77, 226, true))
            val s = getActiveLightSource(p)
            if (s == null || s.open) {
                if (s != null) {
                    sendMessage(p, "Your " + s.name + " seems to have been extinguished by the mud.")
                    val slot = p.inventory.getSlot(s.product)
                    if (slot > -1) {
                        p.inventory.replace(Item(s.raw.id), slot)
                    }
                }
                DarkZone.checkDarkArea(p)
            }
        }
        for (i in 0 until 1 + RandomFunction.randomize(3)) {
            Projectile.create(hole, hole!!.transform(-4 + RandomFunction.randomize(9), -4 + RandomFunction.randomize(9), 0), 570, 0, 5, 45, 70, 5, 11).send()
        }
    }

    private fun visualizeDig(destination: Location, underground: Boolean): Location {
        var offset = centerLocation
        if (underground) {
            offset = when (getDirection()) {
                Direction.NORTH -> getLocation().transform(1, size() - 1, 0)
                Direction.EAST -> getLocation().transform(size() - 1, 1, 0)
                Direction.WEST -> getLocation().transform(0, 1, 0)
                else -> getLocation().transform(1, 0, 0)
            }
        }
        for (x in -1..1) {
            for (y in -1..1) {
                Graphic.send(DUST_GRAPHIC, offset.transform(x, y, 0))
            }
        }
        if (underground) {
            animate(DIG_ANIMATION)
            Graphic.send(DIG_GRAPHIC, offset)
        } else {
            animate(DIG_UP_ANIMATION)
            Graphic.send(DIG_UP_GRAPHIC, offset)
        }
        return offset
    }

    override fun onImpact(entity: Entity, state: BattleState) {
        if (!locks.isInteractionLocked) {
            if (RandomFunction.randomize(100) < 24 && inCombat() && getSkills().lifepoints < 100 && getSkills().lifepoints > 5) {
                dig()
                return
            }
        }
        super.onImpact(entity, state)
    }

    override fun isAttackable(entity: Entity, style: CombatStyle, message: Boolean): Boolean {
        if (digging) {
            return false
        }
        return super.isAttackable(entity, style, message)
    }


    override fun newInstance(arg: Any?): Plugin<Any> {
        ComponentDefinition.put(Components.CWS_WARNING_3_568, object : ComponentPlugin() {
            override fun newInstance(arg: Any?): Plugin<Any> {
                return this
            }

            override fun handle(
                player: Player,
                component: Component,
                opcode: Int,
                button: Int,
                slot: Int,
                itemId: Int
            ): Boolean {
                if (button == 17) {
                    player.properties.teleportLocation = Location.create(1752, 5237, 0)
                    playAudio(player, Sounds.ROOF_COLLAPSE_1384)
                    sendMessage(player, "You seem to have dropped down into a network of mole tunnels.")

                    if (!hasDiaryTaskComplete(player, DiaryType.FALADOR, 0, 5)) {
                        finishDiaryTask(player, DiaryType.FALADOR, 0, 5)
                    }
                }
                closeInterface(player)
                return false
            }
        })

        val action: DigAction = object : DigAction {
            override fun run(player: Player?) {
                if (!isActive(SkillcapePerks.CONSTANT_GLOW, player!!) && !hasActiveLightSource(player)) {
                    sendPlayerDialogue(player, "It's going to be dark down there, I should bring a light source.")
                    return
                }
                openInterface(player, Components.CWS_WARNING_3_568)
            }
        }
        register(Location.create(3005, 3376, 0), action)
        register(Location.create(2999, 3375, 0), action)
        register(Location.create(2996, 3377, 0), action)
        register(Location.create(2989, 3378, 0), action)
        register(Location.create(2984, 3387, 0), action)
        register(Location.create(2987, 3387, 0), action)
        SceneryDefinition.forId(12230).handlers["option:climb"] = object : OptionHandler() {
            @Throws(Throwable::class)
            override fun newInstance(arg: Any?): Plugin<Any> {
                return this
            }

            override fun handle(player: Player, node: Node, option: String): Boolean {
                player.animate(Animation.create(828))
                player.lock(2)
                Pulser.submit(object : Pulse(1, player) {
                    override fun pulse(): Boolean {
                        player.properties.teleportLocation = Location.create(2985, 3316, 0)
                        return true
                    }
                })
                return true
            }
        }
        return super.newInstance(arg)
    }

    override fun finalizeDeath(killer: Entity) {
        super.finalizeDeath(killer)
        if (killer is Player) {
            val player = killer.asPlayer()
            addtoKillcount(player, this.id)
        }
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GIANT_MOLE_3340)
    }

    companion object {
        private val DIG_LOCATIONS = arrayOf(
            Location.create(1760, 5183, 0),
            // center.
            Location.create(1736, 5223, 0),
            // top left.
            Location.create(1777, 5235, 0),
            // top right.
            Location.create(1739, 5150, 0),
            Location.create(1769, 5148, 0),
            Location.create(1750, 5195, 0),
            Location.create(1778, 5207, 0),
            Location.create(1772, 5199, 0),
            Location.create(1774, 5173, 0),
            Location.create(1760, 5162, 0),
            Location.create(1753, 5151, 0),
            Location.create(1739, 5152, 0)
        )

        private val DIG_ANIMATION = Animation(3314, Priority.VERY_HIGH)
        private val DIG_GRAPHIC = Graphic(Graphics.HOLE_OPENS_IN_GROUND_572)
        private val DIG_UP_ANIMATION = Animation(3315, Priority.VERY_HIGH)
        private val DIG_UP_GRAPHIC = Graphic(Graphics.HOLE_OPENS_IN_GROUND_SHORTER_573)
        private val DUST_GRAPHIC = Graphic(Graphics.BUNCHA_SMOKE_BROWN_COLORED_571)
    }
}
