package content.global.skill.summoning.familiar.npc

import content.global.skill.gather.fishing.Fish
import core.api.rewardXP
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillBonus
import core.game.node.entity.skill.Skills
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import core.tools.RandomFunction
import org.rs.consts.NPCs

/**
 * Granite crab familiar.
 */
@Initializable
class GraniteCrabNPC(owner: Player? = null, id: Int = NPCs.GRANITE_CRAB_6796) : content.global.skill.summoning.familiar.Forager(owner, id, 1800, 12009, 12, WeaponInterface.STYLE_DEFENSIVE) {

    init {
        boosts.add(SkillBonus(Skills.FISHING, 1.0))
    }

    override fun construct(owner: Player, id: Int): content.global.skill.summoning.familiar.Familiar {
        return GraniteCrabNPC(owner, id)
    }

    override fun handlePassiveAction() {
        if (RandomFunction.random(4) == 1) {
            val item = FISH[RandomFunction.random(FISH.size)]
            animate(Animation.create(8107))
            if (item.id == Fish.COD.getItem().id || item.id == Fish.PIKE.getItem().id) {
                rewardXP(owner, Skills.FISHING, 5.5)
            }
            produceItem(item)
        }
    }

    override fun specialMove(special: content.global.skill.summoning.familiar.FamiliarSpecial): Boolean {
        owner.getSkills().updateLevel(Skills.DEFENCE, 4)
        visualize(Animation.create(8109), Graphic.create(1326))
        return true
    }

    override fun visualizeSpecialMove() {
        owner.visualize(Animation(7660), Graphic(1296))
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GRANITE_CRAB_6796, NPCs.GRANITE_CRAB_6797)
    }

    companion object {
        private val FISH =
            arrayOf(
                Fish.COD.getItem(),
                Fish.PIKE.getItem(),
                Fish.SEAWEED.getItem(),
                Fish.OYSTER.getItem()
            )
    }
}
