package content.global.skill.combat.summoning.familiar.npc

import content.global.skill.combat.summoning.familiar.Familiar
import content.global.skill.combat.summoning.familiar.FamiliarSpecial
import core.api.consts.NPCs
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.plugin.Initializable


@Initializable
class KaramthulhuOverlordNPC(owner: Player? = null, id: Int = NPCs.KARAMTHULHU_OVERLORD_6809) : Familiar(owner, id, 4400, 12023, 3, WeaponInterface.STYLE_RANGE_ACCURATE) {

    override fun construct(owner: Player, id: Int): Familiar {
        return KaramthulhuOverlordNPC(owner, id)
    }

    override fun specialMove(special: FamiliarSpecial): Boolean {
        return false
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.KARAMTHULHU_OVERLORD_6809, NPCs.KARAMTHULHU_OVERLORD_6810)
    }
}
