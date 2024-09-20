package content.global.skill.summoning.familiar.npc

import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillBonus
import core.game.node.entity.skill.Skills
import core.plugin.Initializable

/**
 * Spirit graahk familiar.
 */
@Initializable
class SpiritGraahkNPC @JvmOverloads constructor(owner: Player? = null, id: Int = 7363) :
    content.global.skill.summoning.familiar.Familiar(owner, id, 4900, 12810, 3, WeaponInterface.STYLE_AGGRESSIVE) {
    init {
        boosts.add(SkillBonus(Skills.HUNTER, 5.0))
    }

    override fun construct(owner: Player, id: Int): content.global.skill.summoning.familiar.Familiar {
        return SpiritGraahkNPC(owner, id)
    }

    override fun specialMove(special: content.global.skill.summoning.familiar.FamiliarSpecial): Boolean {
        if (!super.isOwnerAttackable()) {
            return false
        }
        call()
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(7363, 7364)
    }
}
