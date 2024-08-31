package core.game.node.entity.combat.equipment

import cfg.consts.Items
import cfg.consts.NPCs
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
            // Increase the estimated hit by a random value between 3 and 20.
            state.estimatedHit += RandomFunction.random(3, 20)
            // Cap the estimated hit at 21 if it exceeds 29.
            if (state.estimatedHit > 29) {
                state.estimatedHit = 21
            }
            super.impact(state) // Call the superclass impact method.
        }
    },

    /**
     * The Jade bolt effect.
     */
    JADE(Items.JADE_BOLTS_E_9237, Graphic(755), Audio(2916)) {
        override fun impact(state: BattleState) {
            // Lock the victim player for 2 ticks if the victim is a player.
            if (state.victim is Player) {
                val p = state.victim.asPlayer()
                p.lock(2)
            }
            super.impact(state) // Call the superclass impact method.
        }

        override fun canFire(state: BattleState): Boolean {
            var success = false
            // Calculate success chance based on the victim's agility level.
            if (state.victim is Player) {
                val p = state.victim.asPlayer()
                val level = p.getSkills().getLevel(Skills.AGILITY).toDouble()
                val req = 80.0
                val successChance = ceil((level * 50 - req * 15) / req / 3 * 4)
                val roll = RandomFunction.random(99)
                success = successChance >= roll // Determine if the attack can fire.
            }
            return super.canFire(state) && !success // Ensure the attack can fire and was not successful.
        }
    },

    /**
     * The Pearl bolt effect.
     */
    PEARL(Items.PEARL_BOLTS_E_9238, Graphic.create(750), Audio(2920)) {
        override fun impact(state: BattleState) {
            // Increase the estimated hit by a random value between 3 and 20.
            state.estimatedHit += RandomFunction.random(3, 20)
            // Cap the estimated hit at 21 if it exceeds 29.
            if (state.estimatedHit > 29) {
                state.estimatedHit = 21
            }
            super.impact(state) // Call the superclass impact method.
        }

        override fun canFire(state: BattleState): Boolean {
            // Prevent firing if the victim has specific equipment.
            if (state.victim is Player) {
                if (state.victim.asPlayer().equipment.contains(1383, 1) || state.victim.asPlayer().equipment.contains(
                        1395,
                        1
                    )
                ) {
                    return false
                }
            }
            return super.canFire(state) // Call the superclass canFire method.
        }
    },

    /**
     * The Topaz bolt effect.
     */
    TOPAZ(Items.TOPAZ_BOLTS_E_9239, Graphic.create(757), Audio(2914)) {
        override fun impact(state: BattleState) {
            // Decrease the victim's magic level based on their current level.
            if (state.victim is Player) {
                val p = state.victim.asPlayer()
                val level = (p.getSkills().getLevel(Skills.MAGIC) * 0.03).toInt()
                p.getSkills().updateLevel(Skills.MAGIC, -level, 0)
            }
            super.impact(state) // Call the superclass impact method.
        }
    },

    /**
     * The Sapphire bolt effect.
     */
    SAPPHIRE(Items.SAPPHIRE_BOLTS_E_9240, Graphic(759, 100), Audio(2912)) {
        override fun impact(state: BattleState) {
            // Transfer prayer points from the victim to the attacker.
            if (state.victim is Player && state.attacker is Player) {
                val p = state.victim.asPlayer()
                val player = state.attacker.asPlayer()
                val give = (p.getSkills().prayerPoints * 0.05).toInt()
                if (give > 0) {
                    p.getSkills().decrementPrayerPoints(give.toDouble()) // Decrease victim's prayer points.
                    player.getSkills().incrementPrayerPoints(give.toDouble()) // Increase attacker's prayer points.
                }
            }
            super.impact(state) // Call the superclass impact method.
        }
    },

    /**
     * The Emerald bolt effect.
     */
    EMERALD(Items.EMERALD_BOLTS_E_9241, Graphic(752), Audio(2919)) {
        override fun impact(state: BattleState) {
            // Apply poison effect to the victim.
            applyPoison(state.victim, state.attacker, 40)
            super.impact(state) // Call the superclass impact method.
        }
    },

    /**
     * The Ruby bolt effect.
     */
    RUBY(Items.RUBY_BOLTS_E_9242, Graphic(754), Audio(2911, 1)) {
        // In this case, volume is the number of times to play the sound.
        override fun impact(state: BattleState) {
            // Hit target for 20% of their HP, hit self for 10% of HP.
            var victimPoints = (state.victim.getSkills().lifepoints * 0.20).toInt()
            val playerPoints = (state.attacker.getSkills().lifepoints * 0.10).toInt()
            // Cap the victim's points if they are the Corporeal Beast.
            if (victimPoints >= 100 && state.victim.id == NPCs.CORPOREAL_BEAST_8133) {
                victimPoints = 100
            }
            state.estimatedHit = victimPoints // Set the estimated hit for the attack.
            state.attacker.impactHandler.manualHit(state.victim, playerPoints, HitsplatType.NORMAL) // Apply damage to the victim.
            super.impact(state) // Call the superclass impact method.
        }

        override fun canFire(state: BattleState): Boolean {
            // Ensure the attacker has enough life points to fire the attack.
            val playerPoints = (state.attacker.getSkills().lifepoints * 0.10).toInt()
            if (playerPoints < 1) {
                return false // Cannot fire if not enough life points.
            }
            return super.canFire(state) && state.attacker.getSkills().lifepoints - playerPoints >= 1 // Ensure the attack can fire.
        }
    },

    /**
     * The Diamond bolt effect.
     */
    DIAMOND(Items.DIAMOND_BOLTS_E_9243, Graphic(758), Audio(2913)) {
        override fun impact(state: BattleState) {
            // Increase the estimated hit by a random value between 5 and 14.
            state.estimatedHit += RandomFunction.random(5, 14) // Note: This needs fixing for authenticity.
            super.impact(state) // Call the superclass impact method.
        }
    },

    /**
     * The Dragon bolt effect.
     */
    DRAGON(Items.DRAGON_BOLTS_E_9244, Graphic(756), Audio(2915)) {
        override fun impact(state: BattleState) {
            // Increase the estimated hit by a random value between 17 and 29.
            state.estimatedHit += RandomFunction.random(17, 29)
            super.impact(state) // Call the superclass impact method.
        }

        override fun canFire(state: BattleState): Boolean {
            // Prevent firing if the victim is a fire-related NPC or has fire resistance.
            if (state.victim is NPC) {
                val n = state.victim as NPC
                if (n.name.lowercase().contains("fire") || n.name.lowercase().contains("dragon")) {
                    return false
                }
            }
            if (state.victim is Player) {
                if (state.victim.asPlayer().equipment.contains(1540, 1) || state.victim.asPlayer().equipment.contains(
                        11283,
                        1
                    ) || state.victim.hasFireResistance()
                ) {
                    return false
                }
            }
            return super.canFire(state) // Call the superclass canFire method.
        }
    },

    /**
     * The Onyx bolt effect.
     */
    ONYX(Items.ONYX_BOLTS_E_9245, Graphic(753), Audio(2917)) {
        override fun impact(state: BattleState) {
            // Calculate new damage based on the estimated hit and heal the attacker.
            val newDamage = (state.estimatedHit * 0.25).toInt()
            state.estimatedHit += newDamage // Increase the estimated hit.
            state.attacker.getSkills().heal((state.estimatedHit * 0.25).toInt()) // Heal the attacker.
            state.attacker.setAttribute("onyx-effect", ticks + 12) // Set the onyx effect duration.
            super.impact(state) // Call the superclass impact method.
        }

        override fun canFire(state: BattleState): Boolean {
            // Prevent firing if the onyx effect is still active.
            if (state.attacker.getAttribute("onyx-effect", 0) > ticks) {
                return false
            }
            // Prevent firing if the victim is an undead NPC.
            if (state.victim is NPC) {
                val n = state.victim as NPC
                if (n.task != null && n.task.undead) {
                    return false
                }
            }
            return super.canFire(state) // Call the superclass canFire method.
        }
    };

    /**
     * Impact
     *
     * @param state The current battle state.
     */
    open fun impact(state: BattleState) {
        val victim = state.victim // Get the victim from the battle state.
        val attacker = state.attacker // Get the attacker from the battle state.
        // Play the sound effect at the attacker's location if they are a player.
        if (attacker is Player) {
            playGlobalAudio(attacker.getLocation(), sound.id)
        }
        // Play the sound effect at the victim's location if they are a player.
        if (victim is Player) {
            playGlobalAudio(victim.getLocation(), sound.id)
        }
        victim.graphics(graphic) // Apply the graphic effect to the victim.
    }

    /**
     * Can fire
     *
     * @param state The current battle state.
     * @return True if the attack can fire, false otherwise.
     */
    open fun canFire(state: BattleState): Boolean {
        // Randomly determine if the attack can fire.
        return RandomFunction.random(13) == 5
    }

    companion object {
        // Retrieve the BoltEffect based on the item ID.
        fun forId(id: Int): BoltEffect? {
            for (effect in values()) {
                if (effect.itemId == id) {
                    return effect // Return the matching BoltEffect.
                }
            }
            return null // Return null if no match is found.
        }
    }
}