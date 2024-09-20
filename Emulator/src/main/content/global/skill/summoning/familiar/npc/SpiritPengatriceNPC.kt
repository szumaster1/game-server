package content.global.skill.summoning.familiar.npc

import content.global.skill.summoning.familiar.Familiar
import content.global.skill.summoning.familiar.FamiliarSpecial
import org.rs.consts.NPCs
import core.game.node.entity.player.Player

/**
 * Spirit pengatrice familiar.
 */
class SpiritPengatriceNPC@JvmOverloads constructor(owner: Player? = null, id: Int = NPCs.SPIRIT_PENGATRICE_6883) : content.global.skill.summoning.familiar.Familiar(owner, id, 3600, 12103, 3) {

    override fun construct(owner: Player, id: Int): content.global.skill.summoning.familiar.Familiar {
        return SpiritPengatriceNPC(owner, id)
    }

    override fun specialMove(special: content.global.skill.summoning.familiar.FamiliarSpecial): Boolean {
        return false
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SPIRIT_PENGATRICE_6883, NPCs.SPIRIT_PENGATRICE_6884)
    }

}