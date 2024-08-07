package content.global.skill.combat.summoning.familiar.npc

import content.global.skill.combat.summoning.familiar.Familiar
import content.global.skill.combat.summoning.familiar.FamiliarSpecial
import core.api.consts.NPCs
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Swamp titan familiar.
 */
@Initializable
class SwampTitanNPC(owner: Player? = null, id: Int = NPCs.SWAMP_TITAN_7329) : Familiar(owner, id, 5600, 12776, 6, WeaponInterface.STYLE_ACCURATE) {

    override fun construct(owner: Player, id: Int): Familiar {
        return SwampTitanNPC(owner, id)
    }

    override fun specialMove(special: FamiliarSpecial): Boolean {
        return false
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SWAMP_TITAN_7329, NPCs.SWAMP_TITAN_7330)
    }
}
