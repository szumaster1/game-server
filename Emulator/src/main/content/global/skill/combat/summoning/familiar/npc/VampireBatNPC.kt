package content.global.skill.combat.summoning.familiar.npc

import content.global.skill.combat.summoning.familiar.Familiar
import content.global.skill.combat.summoning.familiar.FamiliarSpecial
import cfg.consts.NPCs
import core.game.node.entity.Entity
import core.game.node.entity.combat.ImpactHandler.HitsplatType
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import core.tools.RandomFunction

/**
 * Vampire bat familiar.
 */
@Initializable
class VampireBatNPC @JvmOverloads constructor(owner: Player? = null, id: Int = NPCs.VAMPIRE_BAT_6835) : Familiar(owner, id, 3300, 12053, 4, WeaponInterface.STYLE_CONTROLLED) {

    override fun construct(owner: Player, id: Int): Familiar {
        return VampireBatNPC(owner, id)
    }

    override fun specialMove(special: FamiliarSpecial): Boolean {
        val target = special.node as Entity
        if (!canCombatSpecial(target)) {
            return false
        } else {
            if (RandomFunction.random(10) < 4) {
                owner.getSkills().heal(2)
            }
            visualize(Animation.create(8275), Graphic.create(1323))
            target.impactHandler.manualHit(this, RandomFunction.random(12), HitsplatType.NORMAL)
            return true
        }
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.VAMPIRE_BAT_6835, NPCs.VAMPIRE_BAT_6836)
    }
}
