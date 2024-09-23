package content.global.skill.summoning.familiar.npc

import core.game.node.entity.Entity
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.impl.Projectile
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import org.rs.consts.Items
import org.rs.consts.NPCs
import kotlin.math.floor

/**
 * Evil turnip familiar.
 */
@Initializable
class EvilTurnipNPC(owner: Player? = null, id: Int = NPCs.EVIL_TURNIP_6833) :
    content.global.skill.summoning.familiar.Forager(
        owner,
        id,
        3000,
        12051,
        6,
        WeaponInterface.STYLE_RANGE_ACCURATE,
        EVIL_TURNIP
    ) {

    override fun construct(owner: Player, id: Int): content.global.skill.summoning.familiar.Familiar {
        return EvilTurnipNPC(owner, id)
    }

    override fun specialMove(special: content.global.skill.summoning.familiar.FamiliarSpecial): Boolean {
        val target = special.node as Entity
        if (!canCombatSpecial(target)) {
            return false
        }
        val ticks = 2 + floor(getLocation().getDistance(target.location) * 0.5).toInt()
        getSkills().heal(2)
        faceTemporary(target, 2)
        sendFamiliarHit(target, 10)
        animate(Animation.create(8251))
        target.graphics(Graphic.create(1329), ticks)
        target.getSkills().updateLevel(Skills.MAGIC, -1, 0)
        Projectile.magic(this, target, 1330, 40, 36, 51, 10).send()
        return true
    }

    override fun getRandom(): Int {
        return 20
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.EVIL_TURNIP_6833, NPCs.EVIL_TURNIP_6834)
    }

    companion object {
        private val EVIL_TURNIP = Item(Items.TWO_THIRDS_EVIL_TURNIP_12136)
    }
}
