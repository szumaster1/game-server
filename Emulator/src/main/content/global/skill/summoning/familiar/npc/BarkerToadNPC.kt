package content.global.skill.summoning.familiar.npc

import content.global.skill.summoning.familiar.Familiar
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import org.rs.consts.NPCs

/**
 * Barker toad familiar.
 */
@Initializable
class BarkerToadNPC(owner: Player? = null, id: Int = NPCs.BARKER_TOAD_6889) :
    Familiar(owner, id, 800, 12123, 6, WeaponInterface.STYLE_AGGRESSIVE) {

    override fun construct(owner: Player, id: Int): Familiar {
        return BarkerToadNPC(owner, id)
    }

    override fun specialMove(special: content.global.skill.summoning.familiar.FamiliarSpecial): Boolean {
        val target = special.target
        if (!canCombatSpecial(target)) {
            return false
        }
        visualize(properties.attackAnimation, Graphic.create(1403))
        sendFamiliarHit(target, 8, Graphic.create(1404))
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BARKER_TOAD_6889, NPCs.BARKER_TOAD_6890)
    }

}
