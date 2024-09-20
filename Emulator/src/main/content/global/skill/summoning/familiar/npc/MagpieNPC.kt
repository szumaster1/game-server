package content.global.skill.summoning.familiar.npc

import content.global.skill.production.crafting.data.Gem
import core.api.visualize
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillBonus
import core.game.node.entity.skill.Skills
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import org.rs.consts.NPCs

/**
 * Magpie NPC.
 */
@Initializable
class MagpieNPC @JvmOverloads constructor(owner: Player? = null, id: Int = NPCs.MAGPIE_6824) : content.global.skill.summoning.familiar.Forager(owner, id, 3400, 12041, 3, *ITEMS) {

    init {
        boosts.add(SkillBonus(Skills.THIEVING, 3.0))
    }

    override fun construct(owner: Player, id: Int): content.global.skill.summoning.familiar.Familiar {
        return MagpieNPC(owner, id)
    }

    override fun specialMove(special: content.global.skill.summoning.familiar.FamiliarSpecial): Boolean {
        visualize(Animation.create(8020), Graphic.create(1336))
        return true
    }

    override fun visualizeSpecialMove() {
        owner.getSkills().updateLevel(Skills.THIEVING, 2)
        visualize(owner, Animation(7660), Graphic(1296))
    }

    override fun getRandom(): Int {
        return 14
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MAGPIE_6824)
    }

    companion object {
        private val ITEMS = arrayOf(Gem.SAPPHIRE.uncut, Gem.EMERALD.uncut, Gem.RUBY.uncut, Gem.DIAMOND.uncut)
    }

}
