package content.global.skill.combat.summoning.familiar.npc

import content.global.skill.combat.summoning.familiar.Familiar
import content.global.skill.combat.summoning.familiar.FamiliarSpecial
import core.api.consts.NPCs
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.plugin.Initializable

@Initializable
class PrayingMantisNPC @JvmOverloads constructor(owner: Player? = null, id: Int = NPCs.PRAYING_MANTIS_6798) : Familiar(owner, id, 6900, 12011, 6, WeaponInterface.STYLE_ACCURATE) {

    override fun construct(owner: Player, id: Int): Familiar {
        return PrayingMantisNPC(owner, id)
    }

    override fun specialMove(special: FamiliarSpecial): Boolean {
        return false
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.PRAYING_MANTIS_6798, NPCs.PRAYING_MANTIS_6799)
    }

}
