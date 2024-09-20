package content.global.skill.summoning.familiar.npc

import content.global.skill.summoning.familiar.Familiar
import content.global.skill.summoning.familiar.FamiliarSpecial
import org.rs.consts.NPCs
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Praying mantis familiar.
 */
@Initializable
class PrayingMantisNPC @JvmOverloads constructor(owner: Player? = null, id: Int = NPCs.PRAYING_MANTIS_6798) : content.global.skill.summoning.familiar.Familiar(owner, id, 6900, 12011, 6, WeaponInterface.STYLE_ACCURATE) {

    override fun construct(owner: Player, id: Int): content.global.skill.summoning.familiar.Familiar {
        return PrayingMantisNPC(owner, id)
    }

    override fun specialMove(special: content.global.skill.summoning.familiar.FamiliarSpecial): Boolean {
        return false
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.PRAYING_MANTIS_6798, NPCs.PRAYING_MANTIS_6799)
    }

}
