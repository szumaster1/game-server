package content.global.skill.combat.summoning.familiar.npc

import content.global.skill.combat.summoning.familiar.Familiar
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Fire titan npc.
 */
@Initializable
class FireTitanNPC
/**
 * Instantiates a new Fire titan npc.
 *
 * @param owner the owner
 * @param id    the id
 */
/**
 * Instantiates a new Fire titan npc.
 */
@JvmOverloads constructor(owner: Player? = null, id: Int = 7355) :
    ElementalTitanNPC(owner, id, 6200, 12802, 20, WeaponInterface.STYLE_CAST) {
    override fun construct(owner: Player, id: Int): Familiar {
        return FireTitanNPC(owner, id)
    }

    override fun getIds(): IntArray {
        return intArrayOf(7355, 7356)
    }
}
