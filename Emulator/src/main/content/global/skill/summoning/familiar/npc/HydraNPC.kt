package content.global.skill.summoning.familiar.npc

import content.global.skill.farming.FarmingPatch
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.plugin.Initializable
import org.rs.consts.NPCs

/**
 * Hydra familiar.
 */
@Initializable
class HydraNPC @JvmOverloads constructor(owner: Player? = null, id: Int = NPCs.HYDRA_6811) :
    content.global.skill.summoning.familiar.Familiar(owner, id, 4900, 12025, 6, WeaponInterface.STYLE_RANGE_ACCURATE) {

    override fun construct(owner: Player, id: Int): content.global.skill.summoning.familiar.Familiar {
        return HydraNPC(owner, id)
    }

    override fun specialMove(special: content.global.skill.summoning.familiar.FamiliarSpecial): Boolean {
        val node = special.node
        if (node is Scenery) {
            val farmingPatch = FarmingPatch.forObject(node)
            if (farmingPatch != null) {
                val patch = farmingPatch.getPatchFor(owner)
                patch.regrowIfTreeStump()
                return true
            }
        }

        return false
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.HYDRA_6811, NPCs.HYDRA_6812)
    }

}
