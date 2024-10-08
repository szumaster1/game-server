package content.global.skill.summoning.familiar.npc

import content.global.skill.summoning.familiar.Familiar
import content.global.skill.summoning.familiar.FamiliarSpecial
import core.game.node.entity.Entity
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import org.rs.consts.NPCs

/**
 * Spirit mosquito familiar.
 */
@Initializable
class SpiritMosquitoNPC(owner: Player? = null, id: Int = NPCs.SPIRIT_MOSQUITO_7331) :
    Familiar(owner, id, 1200, 12778, 3, WeaponInterface.STYLE_ACCURATE) {

    override fun construct(owner: Player, id: Int): Familiar {
        return SpiritMosquitoNPC(owner, id)
    }

    override fun specialMove(special: FamiliarSpecial): Boolean {
        val target = special.node as Entity
        if (!canAttack(target)) {
            return false
        }
        visualize(Animation.create(8032), Graphic.create(1442))
        properties.combatPulse.attack(target)
        return true
    }

    override fun isPoisonImmune(): Boolean {
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SPIRIT_MOSQUITO_7331, NPCs.SPIRIT_MOSQUITO_7332)
    }

}
