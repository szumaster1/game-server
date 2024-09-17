package content.global.skill.combat.summoning.familiar.npc

import content.data.consumables.Consumables.Companion.getConsumableById
import content.global.skill.combat.summoning.familiar.Familiar
import content.global.skill.combat.summoning.familiar.FamiliarSpecial
import org.rs.consts.NPCs
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable

/**
 * Ravenous locust familiar.
 */
@Initializable
class RavenousLocustNPC @JvmOverloads constructor(owner: Player? = null, id: Int = NPCs.RAVENOUS_LOCUST_7372) : Familiar(owner, id, 2400, 12820, 12, WeaponInterface.STYLE_ACCURATE) {

    override fun construct(owner: Player, id: Int): Familiar {
        return RavenousLocustNPC(owner, id)
    }

    override fun specialMove(special: FamiliarSpecial): Boolean {
        val target = special.target
        if (!canCombatSpecial(target)) {
            return false
        }
        animate(properties.attackAnimation)
        graphics(Graphic.create(1346))
        target.graphics(Graphic.create(1347))
        if (target is Player) {
            val p = target.asPlayer()
            for (item in p.inventory.toArray()) {
                if (item == null) {
                    continue
                }
                val consumable = getConsumableById(item.id)!!.consumable
                if (consumable != null) {
                    p.inventory.remove(item)
                    break
                }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.RAVENOUS_LOCUST_7372, NPCs.RAVENOUS_LOCUST_7373)
    }

}
