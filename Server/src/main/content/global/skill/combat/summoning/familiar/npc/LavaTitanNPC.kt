package content.global.skill.combat.summoning.familiar.npc

import content.global.skill.combat.summoning.familiar.Familiar
import content.global.skill.combat.summoning.familiar.FamiliarSpecial
import core.api.consts.NPCs
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillBonus
import core.game.node.entity.skill.Skills
import core.plugin.Initializable

/**
 * Lava titan familiar.
 */
@Initializable
class LavaTitanNPC @JvmOverloads constructor(owner: Player? = null, id: Int = NPCs.LAVA_TITAN_7341) : Familiar(owner, id, 6100, 12788, 4, WeaponInterface.STYLE_AGGRESSIVE) {

    init {
        boosts.add(SkillBonus(Skills.MINING, 10.0))
        boosts.add(SkillBonus(Skills.FIREMAKING, 10.0))
    }

    override fun construct(owner: Player, id: Int): Familiar {
        return LavaTitanNPC(owner, id)
    }

    override fun specialMove(special: FamiliarSpecial): Boolean {
        return false
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.LAVA_TITAN_7341, NPCs.LAVA_TITAN_7342)
    }

}
