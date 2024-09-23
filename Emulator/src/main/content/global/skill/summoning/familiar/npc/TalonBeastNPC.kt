package content.global.skill.summoning.familiar.npc

import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.plugin.Initializable
import org.rs.consts.NPCs

/**
 * Talon beast familiar.
 */
@Initializable
class TalonBeastNPC(owner: Player? = null, id: Int = 7347) :
    content.global.skill.summoning.familiar.Familiar(owner, id, 4900, 12794, 6, WeaponInterface.STYLE_AGGRESSIVE) {

    override fun construct(owner: Player, id: Int): content.global.skill.summoning.familiar.Familiar {
        return TalonBeastNPC(owner, id)
    }

    override fun specialMove(special: content.global.skill.summoning.familiar.FamiliarSpecial): Boolean {
        return false
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.TALON_BEAST_7347, NPCs.TALON_BEAST_7348)
    }

}
