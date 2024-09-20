package content.global.skill.summoning.familiar.npc

import content.global.skill.summoning.familiar.Familiar
import content.global.skill.summoning.familiar.FamiliarSpecial
import org.rs.consts.NPCs
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Karamthulhu overlord familiar.
 */
@Initializable
class KaramthulhuOverlordNPC @JvmOverloads constructor(owner: Player? = null, id: Int = NPCs.KARAMTHULHU_OVERLORD_6809) : content.global.skill.summoning.familiar.Familiar(owner, id, 4400, 12023, 3, WeaponInterface.STYLE_RANGE_ACCURATE) {

    override fun construct(owner: Player, id: Int): content.global.skill.summoning.familiar.Familiar {
        return KaramthulhuOverlordNPC(owner, id)
    }

    override fun specialMove(special: content.global.skill.summoning.familiar.FamiliarSpecial): Boolean {
        return false
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.KARAMTHULHU_OVERLORD_6809, NPCs.KARAMTHULHU_OVERLORD_6810)
    }
}
