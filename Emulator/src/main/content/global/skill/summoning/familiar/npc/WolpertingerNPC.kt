package content.global.skill.summoning.familiar.npc

import core.api.visualize
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillBonus
import core.game.node.entity.skill.Skills
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import org.rs.consts.NPCs

/**
 * Wolpertinger familiar.
 */
@Initializable
class WolpertingerNPC @JvmOverloads constructor(owner: Player? = null, id: Int = NPCs.WOLPERTINGER_6869) : content.global.skill.summoning.familiar.Familiar(owner, id, 6200, 12089, 1, WeaponInterface.STYLE_CAST) {

    init {
        boosts.add(SkillBonus(Skills.HUNTER, 5.0))
    }

    override fun construct(owner: Player, id: Int): content.global.skill.summoning.familiar.Familiar {
        return WolpertingerNPC(owner, id)
    }

    override fun specialMove(special: content.global.skill.summoning.familiar.FamiliarSpecial): Boolean {
        owner.getSkills().updateLevel(Skills.MAGIC, 7, (owner.getSkills().getStaticLevel(Skills.MAGIC) + 7))
        visualize(Animation.create(8267), Graphic.create(1464))
        return true
    }

    override fun visualizeSpecialMove() {
        visualize(owner, Animation.create(7660), Graphic.create(1306))
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.WOLPERTINGER_6869, NPCs.WOLPERTINGER_6870)
    }
}
