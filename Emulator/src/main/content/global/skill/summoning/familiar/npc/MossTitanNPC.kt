package content.global.skill.summoning.familiar.npc

import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.plugin.Initializable
import org.rs.consts.NPCs

/**
 * Moss titan familiar.
 */
@Initializable
class MossTitanNPC(owner: Player? = null, id: Int = NPCs.MOSS_TITAN_7357) :
    ElementalTitanNPC(
        owner,
        id,
        5800,
        12804,
        20,
        WeaponInterface.STYLE_AGGRESSIVE
    ) {

    override fun construct(owner: Player, id: Int): content.global.skill.summoning.familiar.Familiar {
        return MossTitanNPC(owner, id)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MOSS_TITAN_7357, NPCs.MOSS_TITAN_7358)
    }
}
