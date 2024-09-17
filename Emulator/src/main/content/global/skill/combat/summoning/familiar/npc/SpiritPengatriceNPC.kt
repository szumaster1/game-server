package content.global.skill.combat.summoning.familiar.npc

import content.global.skill.combat.summoning.familiar.Familiar
import content.global.skill.combat.summoning.familiar.FamiliarSpecial
import org.rs.consts.NPCs
import core.game.node.entity.player.Player

/**
 * Spirit pengatrice familiar.
 */
class SpiritPengatriceNPC@JvmOverloads constructor(owner: Player? = null, id: Int = NPCs.SPIRIT_PENGATRICE_6883) : Familiar(owner, id, 3600, 12103, 3) {

    override fun construct(owner: Player, id: Int): Familiar {
        return SpiritPengatriceNPC(owner, id)
    }

    override fun specialMove(special: FamiliarSpecial): Boolean {
        return false
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SPIRIT_PENGATRICE_6883, NPCs.SPIRIT_PENGATRICE_6884)
    }

}