package content.global.skill.combat.summoning.familiar.npc

import content.global.skill.combat.summoning.familiar.BurdenBeast
import content.global.skill.combat.summoning.familiar.Familiar
import content.global.skill.combat.summoning.familiar.FamiliarSpecial
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable

@Initializable
class BullAntNPC @JvmOverloads constructor(owner: Player? = null, id: Int = 6867) :
    BurdenBeast(owner, id, 3000, 12087, 12, 9, WeaponInterface.STYLE_CONTROLLED) {
    override fun construct(owner: Player, id: Int): Familiar {
        return BullAntNPC(owner, id)
    }

    override fun specialMove(special: FamiliarSpecial): Boolean {
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
