package content.region.wilderness.handlers.npc

import content.data.BossKillCounter.Companion.addtoKillcount
import cfg.consts.NPCs
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.combat.CombatSwingHandler
import core.game.node.entity.combat.ImpactHandler.HitsplatType
import core.game.node.entity.combat.MultiSwingHandler
import core.game.node.entity.combat.equipment.SwitchAttack
import core.game.node.entity.impl.Projectile
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Location
import core.game.world.map.RegionManager.getLocalPlayers
import core.game.world.map.RegionManager.getTeleportLocation
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import core.tools.RandomFunction
import java.util.*
import kotlin.math.ceil

/**
 * Represents the Corporeal beast NPC.
 */
@Initializable
class CorporealBeastNPC: NPCBehavior(NPCs.CORPOREAL_BEAST_8133) {

    private val combatHandler: MultiSwingHandler = CombatHandler()
    var darkEnergyCore: NPC? = null

    override fun onCreation(self: NPC) {
        self.configureBossData()
    }

    override fun getSwingHandlerOverride(self: NPC, original: CombatSwingHandler): CombatSwingHandler {
        return combatHandler
    }

    override fun beforeDamageReceived(self: NPC, attacker: Entity, state: BattleState) {
        if (state.style == CombatStyle.MELEE || state.style == CombatStyle.RANGE) {
            val w = state.weapon
            val name = w?.name ?: ""
            if (w == null || name.lowercase(Locale.getDefault()).indexOf("spear") == -1) {
                if (state.estimatedHit > 0) {
                    state.estimatedHit = state.estimatedHit / 2
                }
                if (state.secondaryHit > 0) {
                    state.secondaryHit = state.secondaryHit / 2
                }
            }
        }
        if (state.estimatedHit > 100) {
            state.estimatedHit = 100
        }
        if (state.secondaryHit > 100) {
            state.secondaryHit = 100
        }
    }

    override fun onDeathFinished(self: NPC, killer: Entity) {
        addtoKillcount(killer as Player, NPCs.CORPOREAL_BEAST_8133)
        if (darkEnergyCore != null) {
            darkEnergyCore!!.clear()
            darkEnergyCore = null
        }
    }

    /**
     * Combat handler.
     */
    internal class CombatHandler : MultiSwingHandler(
        /*
         * Melee (crush).
         */
        SwitchAttack(handler = CombatStyle.MELEE.swingHandler, animation = Animation.create(10057)).setMaximumHit(52),
        /*
         * Melee (slash).
         */
        SwitchAttack(handler = CombatStyle.MELEE.swingHandler, animation = Animation.create(10058)).setMaximumHit(51),
        /*
         * Magic (drain skill).
         */
        SwitchAttack(handler = CombatStyle.MAGIC.swingHandler, animation = Animation.create(10410), startGraphic = null, endGraphic = null, projectile = Projectile.create(null, null, 1823, 60, 36, 41, 46)).setMaximumHit(55),
        /*
         * Magic (location based)
         */
        SwitchAttack(handler = CombatStyle.MAGIC.swingHandler, animation = Animation.create(10410), startGraphic = null, endGraphic = null, projectile = Projectile.create(null, null, 1824, 60, 36, 41, 46)).setMaximumHit(42),
        /*
         * Magic (hit through prayer).
         */
        SwitchAttack(handler = CombatStyle.MAGIC.swingHandler, animation = Animation.create(10410), startGraphic = null, endGraphic = null, projectile = Projectile.create(null, null, 1825, 60, 36, 41, 46)).setMaximumHit(66)
    ) {
        override fun swing(entity: Entity?, victim: Entity?, state: BattleState?): Int {
            spawnDarkCore(entity, (entity as NPC?)!!.behavior as CorporealBeastNPC, victim)
            if (doStompAttack(entity)) {
                entity!!.properties.combatPulse.setNextAttack(entity.properties.attackSpeed)
                return -1
            }
            /*
             * Location based attack.
             */
            if (super.next.projectile != null && super.next.projectile!!.projectileId == 1824) {
                current = next
                val style = current.style
                type = style
                val index = RandomFunction.randomize(super.attacks.size)
                val pick = getNext(entity, victim, state, index)
                next = pick
                fireLocationBasedAttack(entity, victim!!.location)
                entity!!.properties.combatPulse.setNextAttack(entity.properties.attackSpeed)
                return -1
            }
            return super.swing(entity, victim, state)
        }


        private fun spawnDarkCore(corp: Entity?, npc: CorporealBeastNPC, victim: Entity?) {
            if (npc.darkEnergyCore != null && npc.darkEnergyCore!!.isActive) {
                return
            }
            val max = corp!!.getSkills().maximumLifepoints * (0.3 + (corp.viewport.currentPlane.players.size * 0.05))
            if (corp.getSkills().lifepoints > max) {
                return
            }
            val l = getTeleportLocation(victim!!.location, 3)
            npc.darkEnergyCore = NPC.create(NPCs.DARK_ENERGY_CORE_8127, l, corp)
            npc.darkEnergyCore!!.isActive = true
            Projectile.create(corp.location.transform(2, 2, 0), l, 1828, 60, 0, 0, 60, 20, 0).send()
            Pulser.submit(object : Pulse(2, corp) {
                override fun pulse(): Boolean {
                    if (npc.darkEnergyCore == null) return true
                    npc.darkEnergyCore!!.init()
                    return true
                }
            })
        }


        private fun fireLocationBasedAttack(entity: Entity?, location: Location) {
            entity!!.animate(current.animation)
            Projectile.create(entity, null, 1824, 60, 0, 41, 0).transform(entity, location, true, 46, 10).send()
            val ticks = 1 + ceil(entity.location.getDistance(location) * 0.5).toInt()
            Pulser.submit(object : Pulse(ticks) {
                var secondStage: Boolean = false
                var players: List<Player>? = getLocalPlayers(entity)
                var locations: Array<Location?>? = null

                override fun pulse(): Boolean {
                    if (!secondStage) {
                        for (p in players!!) {
                            if (p.location == location) {
                                hit(p)
                            }
                        }
                        locations = arrayOfNulls(4 + RandomFunction.random(3))
                        for (i in locations!!.indices) {
                            locations!![i] =
                                location.transform(-2 + RandomFunction.random(5), -2 + RandomFunction.random(5), 0)
                            Projectile.create(location, locations!![i], 1824, 60, 0, 25, 56, 0, 0).send()
                        }
                        delay = 2
                        secondStage = true
                        return false
                    }
                    for (i in locations!!.indices) {
                        val l = locations!![i]
                        Graphic.send(Graphic.create(1806), l!!.transform(-1, -1, 0))
                        for (p in players!!) {
                            if (p.location == l) {
                                hit(p)
                            }
                        }
                    }
                    (players as Player).clear()
                    players = null
                    locations = null
                    return true
                }

                private fun hit(p: Player) {
                    val max = if (p.hasProtectionPrayer(CombatStyle.MAGIC)) 13 else 42
                    var hit = 0
                    if (isAccurateImpact(entity, p)) {
                        hit = RandomFunction.random(max)
                    }
                    p.impactHandler.handleImpact(entity, hit, CombatStyle.MAGIC)
                }
            })
        }

        /**
         * Do stomp attack
         *
         * @param entity
         * @return
         */
        fun doStompAttack(entity: Entity?): Boolean {
            val l = entity!!.location
            var stompTargets: MutableList<Player>? = null
            for (player in getLocalPlayers(entity, 5)) {
                val p = player.location
                if (p.x >= l.x && p.y >= l.y && p.x < l.x + entity.size() && p.y < l.y + entity.size()) {
                    if (stompTargets == null) {
                        stompTargets = ArrayList(20)
                    }
                    stompTargets.add(player)
                }
            }
            if (stompTargets != null) {
                entity.visualize(Animation.create(10496), Graphic.create(1834))
                for (p in stompTargets) {
                    p.impactHandler.manualHit(entity, RandomFunction.random(52), HitsplatType.NORMAL, 1)
                }
                return true
            }
            return false
        }

        override fun adjustBattleState(entity: Entity, victim: Entity, state: BattleState) {
            super.adjustBattleState(entity, victim, state)
            if (current.projectile != null && current.projectile!!.projectileId == 1823) {
                if (state.estimatedHit > 0) {
                    val random = RandomFunction.random(3)
                    val skill = if (random == 0) Skills.PRAYER else if (random == 1) Skills.MAGIC else Skills.SUMMONING
                    val drain = 1 + RandomFunction.random(6)
                    if ((if (skill == Skills.PRAYER) victim.getSkills().prayerPoints else victim.getSkills()
                            .getLevel(skill).toDouble()) < 1
                    ) {
                        victim.impactHandler.manualHit(entity, drain, HitsplatType.NORMAL, 2)
                        (victim as Player).packetDispatch.sendMessage("Your Hitpoints have been slightly drained!")
                    } else {
                        if (skill == Skills.PRAYER) {
                            victim.getSkills().decrementPrayerPoints(drain.toDouble())
                        } else {
                            victim.getSkills().updateLevel(skill, -drain, 0)
                        }
                        if (victim is Player) {
                            victim.packetDispatch.sendMessage("Your " + Skills.SKILL_NAME[skill] + " has been slightly drained!")
                        }
                    }
                }
            }
        }

        override fun getFormattedHit(attacker: Entity, victim: Entity, state: BattleState, rawHit: Int): Int {
            var hit = rawHit
            if (current.projectile == null || current.projectile!!.projectileId != 1825) {
                hit = attacker.getFormattedHit(state, hit).toInt()
            }
            return formatHit(victim, hit)
        }
    }
}
