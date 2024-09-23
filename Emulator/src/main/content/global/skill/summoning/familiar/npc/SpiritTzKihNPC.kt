package content.global.skill.summoning.familiar.npc

import core.game.node.entity.Entity
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.world.map.RegionManager.getLocalEntitys
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import org.rs.consts.NPCs

/**
 * Spirit tzkih familiar.
 */
@Initializable
class SpiritTzKihNPC @JvmOverloads constructor(owner: Player? = null, id: Int = NPCs.SPIRIT_TZ_KIH_7361) :
    content.global.skill.summoning.familiar.Familiar(owner, id, 1800, 12808, 6, WeaponInterface.STYLE_CAST) {

    override fun construct(owner: Player, id: Int): content.global.skill.summoning.familiar.Familiar {
        return SpiritTzKihNPC(owner, id)
    }

    override fun specialMove(special: content.global.skill.summoning.familiar.FamiliarSpecial): Boolean {
        val entity = getLocalEntitys(owner, 8)
        if (entity.size == 0) {
            return false
        }
        var success = false
        var target: Entity? = null
        for (i in 0..1) {
            if (entity.size >= i) {
                target = entity[i]
                if (target == null || target === this || target === owner) {
                    continue
                }
                if (!canCombatSpecial(target)) {
                    continue
                }
                success = true
                sendFamiliarHit(target, 7, Graphic.create(1329))
            }
        }
        if (success) {
            animate(Animation.create(8257))
            return true
        }
        return false
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SPIRIT_TZ_KIH_7361, NPCs.SPIRIT_TZ_KIH_7362)
    }

}
