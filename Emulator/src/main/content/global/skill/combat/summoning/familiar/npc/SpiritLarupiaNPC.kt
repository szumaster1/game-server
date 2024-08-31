package content.global.skill.combat.summoning.familiar.npc

import content.global.skill.combat.summoning.familiar.Familiar
import content.global.skill.combat.summoning.familiar.FamiliarSpecial
import cfg.consts.NPCs
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillBonus
import core.game.node.entity.skill.Skills
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable

/**
 * Spirit larupia familiar.
 */
@Initializable
class SpiritLarupiaNPC(owner: Player? = null, id: Int = NPCs.SPIRIT_LARUPIA_7337) : Familiar(owner, id, 4900, 12784, 6, WeaponInterface.STYLE_CONTROLLED) {

    init {
        boosts.add(SkillBonus(Skills.HUNTER, 5.0))
    }

    override fun construct(owner: Player, id: Int): Familiar {
        return SpiritLarupiaNPC(owner, id)
    }

    override fun specialMove(special: FamiliarSpecial): Boolean {
        val target = special.target
        if (!canCombatSpecial(target)) {
            return false
        }

        target.getSkills().updateLevel(Skills.STRENGTH, -1, target.getSkills().getStaticLevel(Skills.STRENGTH) - 1)
        faceTemporary(target, 2)
        projectile(target, 1371)
        sendFamiliarHit(target, 10)
        visualize(Animation.create(5229), Graphic.create(1370))
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SPIRIT_LARUPIA_7337, NPCs.SPIRIT_LARUPIA_7338)
    }

}
