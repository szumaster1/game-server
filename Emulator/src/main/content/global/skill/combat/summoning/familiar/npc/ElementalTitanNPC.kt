package content.global.skill.combat.summoning.familiar.npc

import content.global.skill.combat.summoning.familiar.Familiar
import content.global.skill.combat.summoning.familiar.FamiliarSpecial
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import kotlin.math.min

/**
 * Elemental titan npc.
 */
abstract class ElementalTitanNPC
/**
 * Instantiates a new Elemental titan npc.
 *
 * @param owner       the owner
 * @param id          the id
 * @param ticks       the ticks
 * @param pouchId     the pouch id
 * @param specialCost the special cost
 * @param attackStyle the attack style
 */
    (owner: Player?, id: Int, ticks: Int, pouchId: Int, specialCost: Int, attackStyle: Int) :
    Familiar(owner, id, ticks, pouchId, specialCost, attackStyle) {
    override fun specialMove(special: FamiliarSpecial): Boolean {
        val currentDefenceLevel = owner.getSkills().getLevel(Skills.DEFENCE)
        val maximumDefenceLevel = owner.getSkills().getStaticLevel(Skills.DEFENCE)
        owner.getSkills().updateLevel(
            Skills.DEFENCE,
            ((1.0 + scrollDefenceBoostPercent) * currentDefenceLevel).toInt(),
            ((1.0 + scrollDefenceBoostPercent) * maximumDefenceLevel).toInt()
        )
        val currentHp = owner.getSkills().lifepoints
        val maxHp = owner.getSkills().maximumLifepoints + scrollHealAmount
        val healAmount =
            min((maxHp - currentHp).toDouble(), scrollHealAmount.toDouble()).toInt()
        if (healAmount > 0) {
            owner.getSkills().healNoRestrictions(healAmount)
            return true
        } else {
            owner.sendMessage("You are already at maximum hitpoints!")
            return false
        }
    }

    companion object {
        private const val scrollHealAmount = 8
        private const val scrollDefenceBoostPercent = 0.125
    }
}
