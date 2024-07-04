package content.global.skill.combat.summoning.familiar.npc

import content.global.skill.combat.summoning.familiar.Familiar
import content.global.skill.combat.summoning.familiar.FamiliarSpecial
import content.global.skill.gathering.farming.FarmingPatch.Companion.forObject
import core.api.consts.NPCs
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.plugin.Initializable

@Initializable
class HydraNPC(owner: Player? = null, id: Int = NPCs.HYDRA_6811) :
    Familiar(owner, id, 4900, 12025, 6, WeaponInterface.STYLE_RANGE_ACCURATE) {

    override fun construct(owner: Player, id: Int): Familiar {
        return HydraNPC(owner, id)
    }

    override fun specialMove(special: FamiliarSpecial): Boolean {
        val node = special.node
        if (node is Scenery) {
            val farmingPatch = forObject(node)
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
