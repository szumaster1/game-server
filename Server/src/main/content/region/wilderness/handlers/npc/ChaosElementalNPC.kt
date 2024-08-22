package content.region.wilderness.handlers.npc

import content.data.BossKillCounter.Companion.addtoKillcount
import cfg.consts.Animations
import cfg.consts.Graphics
import cfg.consts.NPCs
import cfg.consts.Sounds
import core.api.getPathableRandomLocalCoordinate
import core.api.playAudio
import core.api.playGlobalAudio
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.combat.CombatSwingHandler
import core.game.node.entity.combat.MultiSwingHandler
import core.game.node.entity.combat.equipment.SwitchAttack
import core.game.node.entity.impl.Projectile
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import core.tools.RandomFunction

/**
 * Represents the Chaos elemental NPC.
 */
@Initializable
class ChaosElementalNPC @JvmOverloads constructor(id: Int = -1, location: Location? = null) : AbstractNPC(id, location) {

    private val COMBAT_HANDLER: MultiSwingHandler = ChaosCombatHandler()

    override fun tick() {
        super.tick()
        if (!isActive) {
            properties.combatPulse.stop()
        }
    }

    override fun getSwingHandler(swing: Boolean): CombatSwingHandler {
        return COMBAT_HANDLER
    }

    override fun sendImpact(state: BattleState) {
        if (state.estimatedHit > 28) {
            /*
             * possibly absolutely mental "haha random" damage adjustment.
             */
            state.estimatedHit = RandomFunction.random(20, 28)
        }
        super.sendImpact(state)
    }

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return ChaosElementalNPC(id, location)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.CHAOS_ELEMENTAL_3200)
    }

    override fun finalizeDeath(killer: Entity) {
        super.finalizeDeath(killer)
        addtoKillcount(killer as Player, this.id)
    }

    /**
     * Chaos combat handler.
     */
    class ChaosCombatHandler : MultiSwingHandler(*ATTACKS) {

        override fun impact(entity: Entity?, victim: Entity?, state: BattleState?) {
            super.impact(entity, victim, state)
            val attack = super.current
            if (victim is Player) {
                val player = victim.asPlayer() ?: return
                if (attack.projectile!!.projectileId == Graphics.MULTICOLORS_2_557) {
                    playGlobalAudio(location = player.location, id = Sounds.CHAOS_ELEMENTAL_DISCORD_IMPACT_350) // C. Elemental Discord Impact SFX
                } else if (attack.projectile.projectileId == 554) {
                    playAudio(player, Sounds.CHAOS_ELEMENTAL_CONFUSION_IMPACT_346) // C. Elemental Confusion Impact SFX
                    val loc = getPathableRandomLocalCoordinate(player, 10, entity!!.location, 3)
                    player.teleport(loc)
                } else if (attack.projectile.projectileId == Graphics.GREEN_BALLS_SPIN_551) {
                    playGlobalAudio(location = player.location, id = Sounds.CHAOS_ELEMENTAL_MADNESS_IMPACT_353) // C. Elemental Madness Impact SFX
                    if (player.inventory.freeSlots() < 1 || player.equipment.itemCount() < 1) {
                        return
                    }
                    var e: Item? = null
                    var tries = 0
                    while (e == null && tries < 30) {
                        e = player.equipment.toArray()[RandomFunction.random(player.equipment.itemCount())]
                        tries++
                        if (e != null && player.inventory.hasSpaceFor(e)) {
                            break
                        }
                        e = null
                    }
                    if (e == null) {
                        return
                    }
                    player.lock(1)
                    if (!player.equipment.containsItem(e)) {
                        return
                    }
                    if (player.equipment.remove(e)) {
                        player.inventory.add(e)
                    }
                }
            }
        }

        /**
         * Get random loc
         *
         * @param entity
         * @return
         */
        fun getRandomLoc(entity: Entity): Location {
            val l = entity.location
            val negative = RandomFunction.random(2) == 1
            return l.location.transform(
                (if (negative) RandomFunction.random(-10, 10) else RandomFunction.random(
                    0,
                    10
                )), (if (negative) RandomFunction.random(-10, 10) else RandomFunction.random(0, 10)), 0
            )
        }

        companion object {
            private val PROJECTILE_ANIM = Animation(Animations.CHAOS_ELEMENTAL_PROJECTILE_3148)
            private val PRIMARY_PROJECTILE: Projectile = Projectile.create(null as Entity?, null, Graphics.MULTICOLORS_2_557, 60, 55, 41, 46, 20, 255)
            private val ATTACKS = arrayOf(SwitchAttack(CombatStyle.MELEE.swingHandler, PROJECTILE_ANIM, Graphic(Graphics.MULTICOLORS_556), null, PRIMARY_PROJECTILE),  // primary
                SwitchAttack(CombatStyle.RANGE.swingHandler, PROJECTILE_ANIM, Graphic(Graphics.MULTICOLORS_556), null, PRIMARY_PROJECTILE),  // primary
                SwitchAttack(CombatStyle.MAGIC.swingHandler, PROJECTILE_ANIM, Graphic(Graphics.MULTICOLORS_556), null, PRIMARY_PROJECTILE),  // primary
                object : SwitchAttack(CombatStyle.MAGIC.swingHandler, PROJECTILE_ANIM, Graphic(Graphics.RED_550_553), null, Projectile.create(null as Entity?, null, Graphics.RED_FLYING_554, 60, 55, 41, 46, 20, 255)) {
                    override fun canSelect(entity: Entity?, victim: Entity?, state: BattleState?): Boolean {
                        return true
                    }
                },
                /*
                 * Teleport.
                 */
                object : SwitchAttack(CombatStyle.MAGIC.swingHandler, PROJECTILE_ANIM, Graphic(Graphics.GREEN_BALLS_550), null, Projectile.create(null as Entity?, null, Graphics.GREEN_BALLS_SPIN_551, 60, 55, 41, 46, 20, 255)) {
                    override fun canSelect(entity: Entity?, victim: Entity?, state: BattleState?): Boolean {
                        return true
                    }
                })
        }
    }
}
