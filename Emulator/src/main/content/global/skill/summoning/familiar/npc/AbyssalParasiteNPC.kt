package content.global.skill.summoning.familiar.npc

import content.global.skill.summoning.familiar.BurdenBeast
import content.global.skill.summoning.familiar.Familiar
import content.global.skill.summoning.familiar.FamiliarSpecial
import core.game.node.entity.impl.Projectile
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import core.tools.RandomFunction

/**
 * Abyssal parasite familiar.
 */
@Initializable
class AbyssalParasiteNPC @JvmOverloads constructor(owner: Player? = null, id: Int = 6818) : content.global.skill.summoning.familiar.BurdenBeast(owner, id, 3000, 12035, 1, 7) {

    @Suppress("unused")
    private val specialMove = false

    override fun construct(owner: Player, id: Int): content.global.skill.summoning.familiar.Familiar {
        return AbyssalParasiteNPC(owner, id)
    }

    override fun isAllowed(owner: Player, item: Item): Boolean {
        if (item.id != 1436 && item.id != 7936) {
            owner.packetDispatch.sendMessage("Your familiar can only hold unnoted essence.")
            return false
        }
        return super.isAllowed(owner, item)
    }

    override fun specialMove(special: content.global.skill.summoning.familiar.FamiliarSpecial): Boolean {
        val target = special.target
        if (!canCombatSpecial(target)) {
            return false
        }
        faceTemporary(target, 2)
        sendFamiliarHit(target, 7)
        visualize(Animation.create(7672), Graphic.create(1422))
        Projectile.magic(this, target, 1423, 40, 36, 51, 10).send()
        target.getSkills().decrementPrayerPoints(RandomFunction.random(1, 3).toDouble())
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(6818, 6819)
    }
}
