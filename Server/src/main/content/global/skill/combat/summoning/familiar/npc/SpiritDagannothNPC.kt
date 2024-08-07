package content.global.skill.combat.summoning.familiar.npc

import content.global.skill.combat.summoning.familiar.Familiar
import content.global.skill.combat.summoning.familiar.FamiliarSpecial
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Spirit dagannoth familiar.
 */
@Initializable
class SpiritDagannothNPC @JvmOverloads constructor(owner: Player? = null, id: Int = 6804) : Familiar(owner, id, 5700, 12017, 6, WeaponInterface.STYLE_CONTROLLED) {

    override fun construct(owner: Player, id: Int): Familiar {
        return SpiritDagannothNPC(owner, id)
    }

    override fun specialMove(special: FamiliarSpecial): Boolean {
        return false
    }

    override fun getIds(): IntArray {
        return intArrayOf(6804, 6805)
    }
}
