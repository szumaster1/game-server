package content.global.skill.summoning.familiar.npc

import content.global.skill.summoning.familiar.Familiar
import content.global.skill.summoning.familiar.FamiliarSpecial
import core.api.applyPoison
import org.rs.consts.NPCs
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.impl.Projectile
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable

/**
 * Spirit scorpion familiar.
 */
@Initializable
class SpiritScorpionNPC @JvmOverloads constructor(owner: Player? = null, id: Int = NPCs.SPIRIT_SCORPION_6837) :
    content.global.skill.summoning.familiar.Familiar(owner, id, 1700, 12055, 6, WeaponInterface.STYLE_CONTROLLED) {

    override fun construct(owner: Player, id: Int): content.global.skill.summoning.familiar.Familiar {
        return SpiritScorpionNPC(owner, id)
    }

    override fun adjustPlayerBattle(state: BattleState) {
        if (state.style == CombatStyle.RANGE) {
            val weapon = state.weapon
            if (isCharged && Item(weapon.id + 6).name.startsWith(weapon.name)) {
                val victim = state.victim
                isCharged = false
                applyPoison(victim, owner, 1)
            }
        }
    }

    override fun specialMove(special: content.global.skill.summoning.familiar.FamiliarSpecial): Boolean {
        if (isCharged) {
            return false
        }
        charge()
        owner.graphics(Graphic(1355, 180), 2)
        visualize(Animation(6261), Graphic(1354, 95))
        Projectile.create(this, owner, 1355, 95, 50, 50, 10).send()
        return false
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SPIRIT_SCORPION_6837, NPCs.SPIRIT_SCORPION_6838)
    }

}
