package content.global.skill.combat.summoning.familiar.npc

import content.global.skill.combat.summoning.familiar.Familiar
import content.global.skill.combat.summoning.familiar.FamiliarSpecial
import core.api.consts.NPCs
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.impl.Projectile
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillBonus
import core.game.node.entity.skill.Skills
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable

/**
 * Arctic bear familiar.
 */
@Initializable
class ArcticBearNPC @JvmOverloads constructor(owner: Player? = null, id: Int = 6839) : Familiar(owner, id, 2800, 12057, 6, WeaponInterface.STYLE_CONTROLLED) {

    init {
        boosts.add(SkillBonus(Skills.HUNTER, 7.0))
    }

    override fun construct(owner: Player, id: Int): Familiar {
        return ArcticBearNPC(owner, id)
    }

    override fun specialMove(special: FamiliarSpecial): Boolean {
        val target = special.target
        if (!canCombatSpecial(target)) {
            return false
        }
        animate(Animation.create(4926))
        graphics(Graphic.create(1405))
        val p = Projectile.magic(this, target, 1406, 40, 40, 1, 10)
        p.speed = 25
        p.send()
        sendFamiliarHit(target, 15, Graphic.create(1407))
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ARCTIC_BEAR_6839, NPCs.ARCTIC_BEAR_6840)
    }
}
