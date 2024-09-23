package content.global.skill.summoning.familiar.npc

import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillBonus
import core.game.node.entity.skill.Skills
import core.plugin.Initializable
import org.rs.consts.NPCs

/**
 * Lava titan familiar.
 */
@Initializable
class LavaTitanNPC @JvmOverloads constructor(owner: Player? = null, id: Int = NPCs.LAVA_TITAN_7341) :
    content.global.skill.summoning.familiar.Familiar(owner, id, 6100, 12788, 4, WeaponInterface.STYLE_AGGRESSIVE) {

    init {
        boosts.add(SkillBonus(Skills.MINING, 10.0))
        boosts.add(SkillBonus(Skills.FIREMAKING, 10.0))
    }

    override fun construct(owner: Player, id: Int): content.global.skill.summoning.familiar.Familiar {
        return LavaTitanNPC(owner, id)
    }

    override fun specialMove(special: content.global.skill.summoning.familiar.FamiliarSpecial): Boolean {
        return false
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.LAVA_TITAN_7341, NPCs.LAVA_TITAN_7342)
    }

}
