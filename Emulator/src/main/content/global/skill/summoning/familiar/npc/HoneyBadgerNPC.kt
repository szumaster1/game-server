package content.global.skill.summoning.familiar.npc

import content.global.skill.summoning.familiar.Familiar
import content.global.skill.summoning.familiar.FamiliarSpecial
import core.api.sendMessage
import core.api.visualize
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.impl.Animator.Priority
import core.game.node.entity.player.Player
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import org.rs.consts.NPCs

/**
 * Honey badger familiar.
 */
@Initializable
class HoneyBadgerNPC @JvmOverloads constructor(owner: Player? = null, id: Int = NPCs.HONEY_BADGER_6845) : Familiar(owner, id, 2500, 12065, 4, WeaponInterface.STYLE_AGGRESSIVE) {

    override fun construct(owner: Player, id: Int): Familiar {
        return HoneyBadgerNPC(owner, id)
    }

    override fun visualizeSpecialMove() {
        visualize(owner, Animation.create(7660), Graphic.create(1399))
    }

    public override fun getText(): String {
        return "Raaaar!"
    }

    override fun specialMove(special: FamiliarSpecial): Boolean {
        if (isCharged) {
            return false
        }
        charge()
        visualize(Animation(7928, Priority.HIGH), Graphic.create(1397))
        return true
    }

    override fun isCharged(): Boolean {
        if (charged) {
            sendMessage(owner, "Your honey badger is already enraged!")
            return true
        }
        return false
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.HONEY_BADGER_6845, NPCs.HONEY_BADGER_6846)
    }
}
