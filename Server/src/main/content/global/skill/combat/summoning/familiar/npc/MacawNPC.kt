package content.global.skill.combat.summoning.familiar.npc

import content.global.skill.combat.summoning.familiar.Familiar
import content.global.skill.combat.summoning.familiar.FamiliarSpecial
import content.global.skill.combat.summoning.familiar.Forager
import core.game.node.entity.player.Player
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.GameWorld.Pulser
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import core.plugin.Plugin
import core.tools.RandomFunction

@Initializable
class MacawNPC @JvmOverloads constructor(owner: Player? = null, id: Int = 6851) : Forager(owner, id, 3100, 12071, 12, *HERBS) {

    private var specialDelay = 0

    override fun construct(owner: Player, id: Int): Familiar {
        return MacawNPC(owner, id)
    }

    @Throws(Throwable::class)
    override fun newInstance(`object`: Any): Plugin<Any> {
        return super.newInstance(`object`)
    }

    override fun getIds(): IntArray {
        return intArrayOf(6851, 6852)
    }

    override fun specialMove(special: FamiliarSpecial): Boolean {
        if (specialDelay > GameWorld.ticks) {
            owner.packetDispatch.sendMessage("You must wait one minute until using the macaws special again.")
            return false
        }
        val herb = HERBS[RandomFunction.random(HERBS.size)]
        animate(Animation.create(8013))
        graphics(Graphic.create(1321), 2)
        Pulser.submit(object : Pulse(5, owner) {
            override fun pulse(): Boolean {
                GroundItemManager.create(herb, getLocation(), owner)
                return true
            }
        })
        specialDelay = GameWorld.ticks + 100
        return true
    }

    companion object {
        private val HERBS = arrayOf(Item(249), Item(251), Item(253), Item(255), Item(257), Item(2998), Item(12172), Item(259), Item(261), Item(263), Item(3000), Item(265), Item(2481), Item(267), Item(269))
    }
}
