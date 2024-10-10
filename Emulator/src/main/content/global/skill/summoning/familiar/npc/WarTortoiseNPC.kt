package content.global.skill.summoning.familiar.npc

import content.global.skill.summoning.familiar.BurdenBeast
import content.global.skill.summoning.familiar.Familiar
import content.global.skill.summoning.familiar.FamiliarSpecial
import core.api.getStatLevel
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import org.rs.consts.NPCs

/**
 * War tortoise familiar.
 */
@Initializable
class WarTortoiseNPC(owner: Player? = null, id: Int = NPCs.WAR_TORTOISE_6815) :
    BurdenBeast(owner, id, 4300, 12031, 20, 18, WeaponInterface.STYLE_DEFENSIVE) {

    override fun construct(owner: Player, id: Int): Familiar {
        return WarTortoiseNPC(owner, id)
    }

    override fun specialMove(special: FamiliarSpecial): Boolean {
        owner.getSkills().updateLevel(Skills.DEFENCE, 9, getStatLevel(owner, Skills.DEFENCE) + 9)
        visualize(Animation.create(8288), Graphic.create(1414))
        return true
    }

    override fun visualizeSpecialMove() {
        owner.visualize(Animation.create(7660), Graphic.create(1310))
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.WAR_TORTOISE_6815, NPCs.WAR_TORTOISE_6816)
    }

}
