package content.global.skill.combat.summoning.familiar.npc

import content.global.skill.combat.summoning.familiar.Familiar
import content.global.skill.combat.summoning.familiar.FamiliarSpecial
import content.global.skill.combat.summoning.familiar.Forager
import content.global.skill.gathering.fishing.data.Fish
import core.api.consts.NPCs
import core.api.rewardXP
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillBonus
import core.game.node.entity.skill.Skills
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import core.tools.RandomFunction

/**
 * Granite crab familiar.
 */
@Initializable
class GraniteCrabNPC(owner: Player? = null, id: Int = NPCs.GRANITE_CRAB_6796) : Forager(owner, id, 1800, 12009, 12, WeaponInterface.STYLE_DEFENSIVE) {

    init {
        boosts.add(SkillBonus(Skills.FISHING, 1.0))
    }

    override fun construct(owner: Player, id: Int): Familiar {
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

    override fun specialMove(special: FamiliarSpecial): Boolean {
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
