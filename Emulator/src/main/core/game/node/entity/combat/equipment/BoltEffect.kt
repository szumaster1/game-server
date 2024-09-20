package core.game.node.entity.combat.equipment

import org.rs.consts.Items
import org.rs.consts.NPCs
import core.api.applyPoison
import core.api.playGlobalAudio
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.ImpactHandler.HitsplatType
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.audio.Audio
import core.game.node.entity.skill.Skills
import core.game.world.GameWorld.ticks
import core.game.world.update.flag.context.Graphic
import core.tools.RandomFunction
import kotlin.math.ceil

/**
 * Represents a bolt effect.
 * @author Vexia, Aero
 *
 * @param itemId The ID of the bolt item.
 * @param graphic The graphic effect associated with the bolt.
 * @param sound The sound effect associated with the bolt.
 * @constructor Bolt effect
 */
enum class BoltEffect(val itemId: Int, private val graphic: Graphic, private val sound: Audio) {

    /**
     * The Opal bolt effect.
     */
    OPAL(Items.OPAL_BOLTS_E_9236, Graphic.create(749), Audio(2918)) {
        override fun impact(state: BattleState) {
            state.estimatedHit += RandomFunction.random(3, 20)
            if (state.estimatedHit > 29) {
                state.estimatedHit = 21
            }
            super.impact(state)
        }
    },

    /**
     * The Jade bolt effect.
     */
    JADE(Items.JADE_BOLTS_E_9237, Graphic(755), Audio(2916)) {
        override fun impact(state: BattleState) {
            if (state.victim is Player) {
                val p = state.victim.asPlayer()
                p.lock(2)
            }
            super.impact(state)
        }

        override fun canFire(state: BattleState): Boolean {
            var success = false
            if (state.victim is Player) {
                val p = state.victim.asPlayer()
                val level = p.getSkills().getLevel(Skills.AGILITY).toDouble()
                val req = 80.0
                val successChance = ceil((level * 50 - req * 15) / req / 3 * 4)
                val roll = RandomFunction.random(99)
                success = successChance >= roll
            }
            return super.canFire(state) && !success
        }
    },

    /**
     * The Pearl bolt effect.
     */
    PEARL(Items.PEARL_BOLTS_E_9238, Graphic.create(750), Audio(2920)) {
        override fun impact(state: BattleState) {
            state.estimatedHit += RandomFunction.random(3, 20)
            if (state.estimatedHit > 29) {
                state.estimatedHit = 21
            }
            super.impact(state)
        }

        override fun canFire(state: BattleState): Boolean {
            if (state.victim is Player) {
                if (state.victim.asPlayer().equipment.contains(1383, 1) || state.victim.asPlayer().equipment.contains(1395, 1)) {
                    return false
                }
            }
            return super.canFire(state)
        }
    },

    /**
     * The Topaz bolt effect.
     */
    TOPAZ(Items.TOPAZ_BOLTS_E_9239, Graphic.create(757), Audio(2914)) {
        override fun impact(state: BattleState) {
            if (state.victim is Player) {
                val p = state.victim.asPlayer()
                val level = (p.getSkills().getLevel(Skills.MAGIC) * 0.03).toInt()
                p.getSkills().updateLevel(Skills.MAGIC, -level, 0)
            }
            super.impact(state)
        }
    },

    /**
     * The Sapphire bolt effect.
     */
    SAPPHIRE(Items.SAPPHIRE_BOLTS_E_9240, Graphic(759, 100), Audio(2912)) {
        override fun impact(state: BattleState) {
            if (state.victim is Player && state.attacker is Player) {
                val p = state.victim.asPlayer()
                val player = state.attacker.asPlayer()
                val give = (p.getSkills().prayerPoints * 0.05).toInt()
                if (give > 0) {
                    p.getSkills().decrementPrayerPoints(give.toDouble())
                    player.getSkills().incrementPrayerPoints(give.toDouble())
                }
            }
            super.impact(state)
        }
    },

    /**
     * The Emerald bolt effect.
     */
    EMERALD(Items.EMERALD_BOLTS_E_9241, Graphic(752), Audio(2919)) {
        override fun impact(state: BattleState) {
            applyPoison(state.victim, state.attacker, 40)
            super.impact(state)
        }
    },

    /**
     * The Ruby bolt effect.
     */
    RUBY(Items.RUBY_BOLTS_E_9242, Graphic(754), Audio(2911, 1)) {
        override fun impact(state: BattleState) {
            var victimPoints = (state.victim.getSkills().lifepoints * 0.20).toInt()
            val playerPoints = (state.attacker.getSkills().lifepoints * 0.10).toInt()
            if (victimPoints >= 100 && state.victim.id == NPCs.CORPOREAL_BEAST_8133) {
                victimPoints = 100
            }
            state.estimatedHit = victimPoints
            state.attacker.impactHandler.manualHit(state.victim, playerPoints, HitsplatType.NORMAL) // Apply damage to the victim.
            super.impact(state)
        }

        override fun canFire(state: BattleState): Boolean {
            val playerPoints = (state.attacker.getSkills().lifepoints * 0.10).toInt()
            if (playerPoints < 1) {
                return false
            }
            return super.canFire(state) && state.attacker.getSkills().lifepoints - playerPoints >= 1
        }
    },

    /**
     * The Diamond bolt effect.
     */
    DIAMOND(Items.DIAMOND_BOLTS_E_9243, Graphic(758), Audio(2913)) {
        override fun impact(state: BattleState) {
            state.estimatedHit += RandomFunction.random(5, 14)
            super.impact(state)
        }
    },

    /**
     * The Dragon bolt effect.
     */
    DRAGON(Items.DRAGON_BOLTS_E_9244, Graphic(756), Audio(2915)) {
        override fun impact(state: BattleState) {
            state.estimatedHit += RandomFunction.random(17, 29)
            super.impact(state)
        }

        override fun canFire(state: BattleState): Boolean {
            if (state.victim is NPC) {
                val n = state.victim as NPC
                if (n.name.lowercase().contains("fire") || n.name.lowercase().contains("dragon")) {
                    return false
                }
            }
            if (state.victim is Player) {
                if (state.victim.asPlayer().equipment.contains(1540, 1) || state.victim.asPlayer().equipment.contains(11283, 1) || state.victim.hasFireResistance()) {
                    return false
                }
            }
            return super.canFire(state)
        }
    },

    /**
     * The Onyx bolt effect.
     */
    ONYX(Items.ONYX_BOLTS_E_9245, Graphic(753), Audio(2917)) {
        override fun impact(state: BattleState) {
            val newDamage = (state.estimatedHit * 0.25).toInt()
            state.estimatedHit += newDamage
            state.attacker.getSkills().heal((state.estimatedHit * 0.25).toInt())
            state.attacker.setAttribute("onyx-effect", ticks + 12)
            super.impact(state)
        }

        override fun canFire(state: BattleState): Boolean {
            if (state.attacker.getAttribute("onyx-effect", 0) > ticks) {
                return false
            }
            if (state.victim is NPC) {
                val n = state.victim as NPC
                if (n.task != null && n.task.undead) {
                    return false
                }
            }
            return super.canFire(state)
        }
    };

    /**
     * Impact
     *
     * @param state The current battle state.
     */
    open fun impact(state: BattleState) {
        val victim = state.victim
        val attacker = state.attacker
        if (attacker is Player) {
            playGlobalAudio(attacker.getLocation(), sound.id)
        }
        if (victim is Player) {
            playGlobalAudio(victim.getLocation(), sound.id)
        }
        victim.graphics(graphic)
    }

    /**
     * Can fire
     *
     * @param state The current battle state.
     * @return True if the attack can fire, false otherwise.
     */
    open fun canFire(state: BattleState): Boolean {
        return RandomFunction.random(13) == 5
    }

    companion object {
        fun forId(id: Int): BoltEffect? {
            for (effect in values()) {
                if (effect.itemId == id) {
                    return effect
                }
            }
            return null
        }
    }
}