package content.global.skill.summoning.familiar.npc

import content.global.skill.summoning.familiar.BurdenBeast
import content.global.skill.summoning.familiar.Familiar
import content.global.skill.summoning.familiar.FamiliarSpecial
import core.api.sendMessage
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import org.rs.consts.NPCs

/**
 * Abyssal lurker familiar.
 */
@Initializable
class AbyssalLurkerNPC @JvmOverloads constructor(owner: Player? = null, id: Int = 6820) :
    BurdenBeast(owner, id, 4100, 12037, 3, 7, WeaponInterface.STYLE_CAST) {

    override fun construct(owner: Player, id: Int): Familiar {
        return AbyssalLurkerNPC(owner, id)
    }

    override fun isAllowed(owner: Player, item: Item): Boolean {
        if (item.id != 1436 && item.id != 7936) {
            sendMessage(owner, "Your familiar can only hold unnoted essence.")
            return false
        }
        return super.isAllowed(owner, item)
    }

    override fun specialMove(special: FamiliarSpecial): Boolean {
        visualize(Animation.create(7682), Graphic.create(0))
        owner.getSkills().updateLevel(Skills.AGILITY, 4)
        owner.getSkills().updateLevel(Skills.THIEVING, 4)
        return true
    }

    override fun visualizeSpecialMove() {
        owner.visualize(Animation(7660), Graphic(1296))
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ABYSSAL_LURKER_6820, NPCs.ABYSSAL_LURKER_6821)
    }
}
