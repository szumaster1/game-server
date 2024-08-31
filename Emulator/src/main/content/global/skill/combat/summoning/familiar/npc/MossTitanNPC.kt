package content.global.skill.combat.summoning.familiar.npc

import content.global.skill.combat.summoning.familiar.Familiar
import cfg.consts.NPCs
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Moss titan familiar.
 */
@Initializable
class MossTitanNPC(owner: Player? = null, id: Int = NPCs.MOSS_TITAN_7357) : ElementalTitanNPC(owner, id, 5800, 12804, 20, WeaponInterface.STYLE_AGGRESSIVE) {

    override fun construct(owner: Player, id: Int): Familiar {
        return MossTitanNPC(owner, id)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MOSS_TITAN_7357, NPCs.MOSS_TITAN_7358)
    }
}
