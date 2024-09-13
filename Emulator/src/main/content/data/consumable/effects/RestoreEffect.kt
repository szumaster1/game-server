package content.data.consumable.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills

/**
 * Restore effect class for consumable items.
 *
 * This class defines the behavior of a consumable effect that restores player skills.
 */
class RestoreEffect : ConsumableEffect {
    var base: Double // Base value for skill restoration
    var bonus: Double // Bonus value for skill restoration
    var skills: Boolean // Flag to determine if all skills should be restored

    /**
     * Constructor for RestoreEffect with base and bonus values.
     *
     * @param base The base value for skill restoration.
     * @param bonus The bonus value for skill restoration.
     */
    constructor(base: Double, bonus: Double) {
        this.base = base
        this.bonus = bonus
        this.skills = false
    }

    /**
     * Constructor for RestoreEffect with base, bonus, and skills flag.
     *
     * @param base The base value for skill restoration.
     * @param bonus The bonus value for skill restoration.
     * @param skills Flag to determine if all skills should be restored.
     */
    constructor(base: Double, bonus: Double, skills: Boolean) {
        this.base = base
        this.bonus = bonus
        this.skills = skills
    }

    val SKILLS: IntArray = intArrayOf(Skills.DEFENCE, Skills.ATTACK, Skills.STRENGTH, Skills.MAGIC, Skills.RANGE)
    val ALL_SKILLS: IntArray = intArrayOf(
        Skills.ATTACK, Skills.DEFENCE, Skills.STRENGTH, Skills.RANGE, Skills.PRAYER, Skills.MAGIC, Skills.COOKING,
        Skills.WOODCUTTING, Skills.FLETCHING, Skills.FISHING, Skills.FIREMAKING, Skills.CRAFTING, Skills.SMITHING,
        Skills.MINING, Skills.HERBLORE, Skills.AGILITY, Skills.THIEVING, Skills.SLAYER, Skills.FARMING,
        Skills.RUNECRAFTING, Skills.HUNTER, Skills.CONSTRUCTION, Skills.SUMMONING
    )

    /**
     * Activate method to restore player skills based on the effect.
     *
     * @param p The player whose skills need to be restored.
     */
    override fun activate(p: Player) {
        val sk = p.getSkills()
        val skills = if (this.skills) ALL_SKILLS else SKILLS
        for (skill in skills) {
            val statL = sk.getStaticLevel(skill)
            val curL = sk.getLevel(skill)
            if (curL < statL) {
                val boost = (base + (statL * bonus)).toInt()
                p.getSkills().updateLevel(skill, boost, statL)
            }
        }
    }
}
