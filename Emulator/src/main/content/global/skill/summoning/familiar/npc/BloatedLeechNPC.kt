package content.global.skill.summoning.familiar.npc

import core.api.curePoison
import core.api.removeTimer
import core.game.node.entity.combat.ImpactHandler.HitsplatType
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.plugin.Initializable
import core.tools.RandomFunction
import kotlin.math.ceil

/**
 * Bloated leech familiar.
 */
@Initializable
class BloatedLeechNPC @JvmOverloads constructor(owner: Player? = null, id: Int = 6843) :
    content.global.skill.summoning.familiar.Familiar(owner, id, 3400, 12061, 6, WeaponInterface.STYLE_ACCURATE) {
    override fun construct(owner: Player, id: Int): content.global.skill.summoning.familiar.Familiar {
        return BloatedLeechNPC(owner, id)
    }

    override fun specialMove(special: content.global.skill.summoning.familiar.FamiliarSpecial): Boolean {
        curePoison(owner)
        removeTimer(owner, "disease")
        for (i in Skills.SKILL_NAME.indices) {
            if (owner.getSkills().getLevel(i) < owner.getSkills().getStaticLevel(i)) {
                owner.getSkills().updateLevel(
                    i,
                    ceil(owner.getSkills().getStaticLevel(i) * 0.2).toInt(),
                    owner.getSkills().getStaticLevel(i)
                )
            }
        }
        owner.impactHandler.manualHit(owner, RandomFunction.random(1, 5), HitsplatType.NORMAL)
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(6843, 6844)
    }
}
