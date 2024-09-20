package content.global.skill.summoning.familiar.npc

import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable

/**
 * Bull ant familiar.
 */
@Initializable
class BullAntNPC @JvmOverloads constructor(owner: Player? = null, id: Int = 6867) :
    content.global.skill.summoning.familiar.BurdenBeast(owner, id, 3000, 12087, 12, 9, WeaponInterface.STYLE_CONTROLLED) {
    override fun construct(owner: Player, id: Int): content.global.skill.summoning.familiar.Familiar {
        return BullAntNPC(owner, id)
    }

    override fun specialMove(special: content.global.skill.summoning.familiar.FamiliarSpecial): Boolean {
        if (owner.settings.runEnergy >= 100) {
            owner.packetDispatch.sendMessage("You already have full run energy.")
            return false
        }
        val amount = owner.getSkills().getStaticLevel(Skills.AGILITY) / 2
        visualize(Animation.create(7896), Graphic.create(1382))
        owner.settings.updateRunEnergy(-amount.toDouble())
        return true
    }

    override fun visualizeSpecialMove() {
        owner.visualize(Animation(7660), Graphic(1296))
    }

    override fun getIds(): IntArray {
        return intArrayOf(6867, 6868)
    }
}
