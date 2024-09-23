package content.global.skill.summoning.familiar.npc

import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import org.rs.consts.Graphics
import org.rs.consts.NPCs

/**
 * Albino rat familiar.
 */
@Initializable
class AlbinoRatNPC(owner: Player? = null, id: Int = 6847) :
    content.global.skill.summoning.familiar.Forager(owner, id, 2200, 12067, 6, WeaponInterface.STYLE_ACCURATE, CHEESE) {

    override fun construct(owner: Player, id: Int): content.global.skill.summoning.familiar.Familiar {
        return AlbinoRatNPC(owner, id)
    }

    override fun specialMove(special: content.global.skill.summoning.familiar.FamiliarSpecial): Boolean {
        if (produceItem(CHEESE)) {
            owner.lock(7)
            visualize(Animation.create(4934), Graphic.create(Graphics.MAKING_CHEESE_WHEEL_1384))
            return true
        }
        return false
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ALBINO_RAT_6847, NPCs.ALBINO_RAT_6848)
    }

    companion object {
        private val CHEESE = Item(1985, 4)
    }
}
