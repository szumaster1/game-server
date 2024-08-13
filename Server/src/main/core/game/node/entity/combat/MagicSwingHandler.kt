package core.game.node.entity.combat

import core.game.node.entity.Entity
import core.game.node.entity.combat.equipment.ArmourSet
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.combat.spell.SpellType
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.tools.RandomFunction
import kotlin.math.floor

/**
 * Magic swing handler.
 *
 * @constructor
 * This constructor initializes the MagicSwingHandler with the provided flags.
 *
 * @param flags
 * Vararg parameter that allows passing multiple SwingHandlerFlag instances.
 */
open class MagicSwingHandler(vararg flags: SwingHandlerFlag) : CombatSwingHandler(CombatStyle.MAGIC, *flags) {

    /**
     * Determines if the entity can swing at the victim.
     *
     * @param entity The attacking entity.
     * @param victim The target entity.
     * @return The type of interaction possible.
     */
    override fun canSwing(entity: Entity, victim: Entity): InteractionType? {
        // Check if the projectile is clipped between the entity and the victim
        if (!isProjectileClipped(entity, victim, false)) {
            return InteractionType.NO_INTERACT // No interaction if clipped
        }
        var distance = 10 // Initial distance for combat
        var type = InteractionType.STILL_INTERACT // Default interaction type
        // Check if the victim is within combat distance
        var goodRange = victim.centerLocation.withinDistance(entity.centerLocation, getCombatDistance(entity, victim, distance))
        // If the victim is moving and not in good range, check again with increased distance
        if (victim.walkingQueue.isMoving && !goodRange) {
            goodRange = victim.centerLocation.withinDistance(
                entity.centerLocation,
                getCombatDistance(entity, victim, ++distance)
            )
            type = InteractionType.MOVE_INTERACT // Update interaction type to moving
        }
        // If in good range and attackable, return the interaction type
        if (goodRange && isAttackable(entity, victim) != InteractionType.NO_INTERACT) {
            if (type == InteractionType.STILL_INTERACT) {
                entity.walkingQueue.reset() // Reset walking queue if still interacting
            }
            return type // Return the interaction type
        }
        return InteractionType.NO_INTERACT // No interaction if not in range or attackable
    }

    /**
     * Executes the swing action for the entity against the victim.
     *
     * @param entity The attacking entity.
     * @param victim The target entity.
     * @param state The current battle state.
     * @return The number of ticks until the next action.
     */
    override fun swing(entity: Entity?, victim: Entity?, state: BattleState?): Int {
        var spell = entity!!.properties.spell // Get the spell from entity properties
        // If no spell is set, check for autocast spell
        if (spell == null) {
            if (entity.properties.autocastSpell.also { spell = it } == null) {
                return -1 // Return -1 if no spell is available
            }
        }
        state!!.style = CombatStyle.MAGIC // Set the combat style to magic
        // Check if the spell meets the requirements
        if (!spell!!.meetsRequirements(entity, true, true)) {
            entity.properties.spell = null // Clear the spell if requirements are not met
            val inter = entity.getExtension<WeaponInterface>(WeaponInterface::class.java) // Get weapon interface
            if (inter != null) {
                inter.selectAutoSpell(-1, true) // Deselect autocast spell
                entity.properties.combatPulse.updateStyle() // Update combat style
            }
            return -1 // Return -1 if requirements are not met
        }
        // Calculate the maximum hit based on the spell's impact
        val max = calculateHit(entity, victim, spell!!.getMaximumImpact(entity, victim, state).toDouble())
        state.targets = spell!!.getTargets(entity, victim) // Get targets for the spell
        state.spell = spell // Set the spell in the state
        for (s in state.targets) {
            var hit = -1 // Initialize hit variable
            s.spell = spell // Assign spell to the target
            // Check if the attack is accurate
            if (isAccurateImpact(entity, s.victim, CombatStyle.MAGIC, 1.3, 1.0)) {
                s.maximumHit = max // Set maximum hit for the target
                hit = RandomFunction.random(max) // Calculate random hit value
            }
            s.style = CombatStyle.MAGIC // Set the combat style for the target
            s.estimatedHit = hit // Set the estimated hit for the target
        }
        // Clear the spell if it matches the entity's current spell
        if (spell === entity.properties.spell) {
            entity.properties.spell = null // Clear the spell
            if (entity.properties.autocastSpell == null) {
                entity.properties.combatPulse.stop() // Stop combat pulse if no autocast
            }
        }
        // Calculate the number of ticks based on distance to the victim
        var ticks = 2 + floor(entity.location.getDistance(victim!!.location) * 0.5).toInt()
        if (spell!!.type === SpellType.BLITZ) {
            ticks++ // Increase ticks for blitz spell
        }
        // Ensure estimated hit does not exceed victim's lifepoints
        if (state.estimatedHit > victim.skills.lifepoints) state.estimatedHit = victim.skills.lifepoints
        if (state.estimatedHit + state.secondaryHit > victim.skills.lifepoints) state.secondaryHit -= ((state.estimatedHit + state.secondaryHit) - victim.skills.lifepoints)
        return ticks // Return the calculated ticks
    }

    /**
     * Adjusts the battle state based on the current interaction.
     *
     * @param entity The attacking entity.
     * @param victim The target entity.
     * @param state The current battle state.
     */
    override fun adjustBattleState(entity: Entity, victim: Entity, state: BattleState) {
        // If no targets are set, adjust the battle state normally
        if (state.targets == null) {
            super.adjustBattleState(entity, victim, state)
            if (state.spell != null) {
                state.spell.fireEffect(entity, victim, state) // Fire spell effect if applicable
            }
            return
        }
        // Iterate through targets and adjust their battle states
        for (s in state.targets) {
            if (s != null) {
                super.adjustBattleState(entity, s.victim, s) // Adjust battle state for the target
                if (s.spell != null) {
                    s.spell.fireEffect(entity, s.victim, s) // Fire spell effect for the target
                }
            }
        }
    }

    /**
     * Visualizes the spell effect for the interaction.
     *
     * @param entity The attacking entity.
     * @param victim The target entity.
     * @param state The current battle state.
     */
    override fun visualize(entity: Entity, victim: Entity?, state: BattleState?) {
        state!!.spell.visualize(entity, victim!!) // Visualize the spell effect
    }

    /**
     * Handles the impact of the spell on the target.
     *
     * @param entity The attacking entity.
     * @param victim The target entity.
     * @param state The current battle state.
     */
    override fun impact(entity: Entity?, victim: Entity?, state: BattleState?) {
        // If no targets are set, handle impact normally
        if (state!!.targets == null) {
            if (state.estimatedHit > -1) {
                victim!!.impactHandler.handleImpact(entity, state.estimatedHit, CombatStyle.MAGIC, state) // Handle impact for the victim
            }
            return
        }
        // Iterate through targets and handle their impacts
        for (s in state.targets) {
            if (s == null || s.estimatedHit < 0) {
                continue // Skip if target is null or has no estimated hit
            }
            val hit = s.estimatedHit // Get the estimated hit for the target
            s.victim.impactHandler.handleImpact(entity, hit, CombatStyle.MAGIC, s) // Handle impact for the target
        }
    }

    /**
     * Visualizes the impact of the spell on the target.
     *
     * @param entity The attacking entity.
     * @param victim The target entity.
     * @param state The current battle state.
     */
    override fun visualizeImpact(entity: Entity?, victim: Entity?, state: BattleState?) {
        // If no targets are set, visualize impact normally
        if (state!!.targets == null) {
            if (state.spell != null) {
                state.spell.visualizeImpact(entity, victim, state) // Visualize impact for the spell
            }
            return
        }
        // Iterate through targets and visualize their impacts
        for (s in state.targets) {
            if (s != null) {
                state.spell.visualizeImpact(entity, s.victim, s) // Visualize impact for the target
            }
        }
    }

    /**
     * Calculates the accuracy of the entity's attack.
     *
     * @param entity The attacking entity.
     * @return The calculated accuracy.
     */
    override fun calculateAccuracy(entity: Entity?): Int {
        val baseLevel = entity!!.skills.getStaticLevel(Skills.MAGIC) // Get the base magic level
        var spellRequirement = baseLevel // Initialize spell requirement
        // If the entity is a player, check for spell requirements
        if (entity is Player) {
            if (entity.getProperties().spell != null) {
                spellRequirement = entity.getProperties().spell.level // Set requirement to current spell level
            } else if (entity.getProperties().autocastSpell != null) {
                spellRequirement = entity.getProperties().autocastSpell.level // Set requirement to autocast spell level
            }
        }
        var spellBonus = 0.0 // Initialize spell bonus
        if (baseLevel > spellRequirement) {
            spellBonus = (baseLevel - spellRequirement) * .3 // Calculate bonus based on level difference
        }
        val level = entity.skills.getLevel(Skills.MAGIC, true) // Get the effective magic level
        var prayer = 1.0 // Initialize prayer bonus
        // If the entity is a player and not ignoring prayer boosts
        if (entity is Player && !flags.contains(SwingHandlerFlag.IGNORE_PRAYER_BOOSTS_ACCURACY)) {
            prayer += entity.prayer.getSkillBonus(Skills.MAGIC) // Add prayer bonus
        }
        val additional = getSetMultiplier(entity, Skills.MAGIC) // Get additional multipliers
        val effective = floor(level * prayer * additional + spellBonus) // Calculate effective accuracy
        val bonus =
            if (!flags.contains(SwingHandlerFlag.IGNORE_STAT_BOOSTS_ACCURACY))
                entity.properties.bonuses[WeaponInterface.BONUS_MAGIC] // Get magic bonus
            else 0
        return floor((effective + 8) * (bonus + 64) / 10).toInt() // Return final accuracy
    }

    /**
     * Calculates the hit damage for the attack.
     *
     * @param entity The attacking entity.
     * @param victim The target entity.
     * @param modifier The damage modifier.
     * @return The calculated hit damage.
     */
    override fun calculateHit(entity: Entity?, victim: Entity?, modifier: Double): Int {
        var baseDamage = modifier // Initialize base damage
        // If base damage is less than 2 or the entity is an NPC
        if (baseDamage < 2.0 || entity is NPC) {
            if (baseDamage > 2.0) {
                baseDamage = 1.0 // Set base damage to 1 if greater than 2
            }
            val level = entity!!.skills.getLevel(Skills.MAGIC, true) // Get the magic level
            val bonus = entity.properties.bonuses[if (entity is Player) 14 else 13] // Get the appropriate bonus
            val cumulativeStr = level.toDouble() // Convert level to double
            return 1 + ((14 + cumulativeStr + bonus.toDouble() / 8 + cumulativeStr * bonus * 0.016865) * baseDamage).toInt() / 10 // Calculate hit damage
        }
        var levelMod = 1.0 // Initialize level modifier
        val entityMod = entity!!.getLevelMod(entity, victim) // Get level modifier based on entity and victim
        if (entityMod != 0.0) {
            levelMod += entityMod // Add entity modifier if applicable
        }
        return (baseDamage * levelMod).toInt() + 1 // Return calculated hit damage
    }

    /**
     * Calculates the defense value for the victim.
     *
     * @param victim The target entity.
     * @param attacker The attacking entity.
     * @return The calculated defense value.
     */
    override fun calculateDefence(victim: Entity?, attacker: Entity?): Int {
        val level = victim!!.skills.getLevel(Skills.DEFENCE, true) // Get the defense level
        var prayer = 1.0 // Initialize prayer bonus
        // If the victim is a player, add prayer bonus
        if (victim is Player) {
            prayer += victim.prayer.getSkillBonus(Skills.MAGIC)
        }
        val effective = floor(level * prayer * 0.3) + victim.skills.getLevel(Skills.MAGIC, true) * 0.7 // Calculate effective defense
        val equipment = victim.properties.bonuses[WeaponInterface.BONUS_MAGIC + 5] // Get equipment bonus
        return floor((effective + 8) * (equipment + 64) / 10).toInt() // Return final defense value
    }

    /**
     * Gets the set multiplier for the entity based on the skill.
     *
     * @param e The entity.
     * @param skillId The skill identifier.
     * @return The calculated set multiplier.
     */
    override fun getSetMultiplier(e: Entity?, skillId: Int): Double {
        if (skillId == Skills.MAGIC) {
            if (e is Player && e.isWearingVoid(CombatStyle.MAGIC)) {
                return 1.3 // Return multiplier for wearing void
            }
        }
        return 1.0 // Default multiplier
    }

    /**
     * Adds experience to the entity based on the interaction.
     *
     * @param entity The attacking entity.
     * @param victim The target entity.
     * @param state The current battle state.
     */
    override fun addExperience(entity: Entity?, victim: Entity?, state: BattleState?) {
        // If the state has a spell, add experience based on total damage
        if (state?.spell != null && entity is Player) {
            state.spell.addExperience(entity, state.totalDamage)
            return
        }

        super.addExperience(entity, victim, state) // Call super method for experience addition
    }

    /**
     * Gets the armor set for the entity.
     *
     * @param e The entity.
     * @return The armor set if applicable.
     */
    override fun getArmourSet(e: Entity?): ArmourSet? {
        return if (ArmourSet.AHRIM.isUsing(e)) {
            ArmourSet.AHRIM // Return Ahrim's armor set if in use
        } else super.getArmourSet(e) // Call super method for other armor sets
    }
}