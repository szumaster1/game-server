package content.global.skill.support.construction.decoration.kitchen

import cfg.consts.NPCs
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable

/**
 * Represents the Cat on blanket NPC.
 */
@Initializable
class CatblanketNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return CatblanketNPC(id, location)
    }

    companion object {
        fun spawnCat(player: Player, location: Location) {
            val cat = NPC.create(NPCs.CAT_768, location)
            cat.isWalks = false
            cat.isNeverWalks = true
            cat.isActive = false

            if (cat.isActive) {
                cat.properties.teleportLocation = cat.properties.spawnLocation
            }

            cat.isActive = true
            GameWorld.Pulser.submit(object : Pulse(2, cat) {
                private var counter = 0

                override fun pulse(): Boolean {
                    return when (counter) {
                        0 -> {
                            cat.init()
                            cat.lock()
                            cat.sendChat("Meeeew!")
                            cat.animator.animate(Animation(2160))
                            counter++
                            false
                        }
                        1 -> {
                            cat.animator.animate(Animation(2159))
                            counter++
                            false
                        }
                        2 -> {
                            cat.animator.animate(Animation(2159))
                            true
                        }
                        else -> true
                    }
                }
            })
        }
    }

    override fun getIds(): IntArray {
        return intArrayOf(
            NPCs.CAT_768, NPCs.CAT_769, NPCs.CAT_770,
            NPCs.CAT_771, NPCs.CAT_772, NPCs.CAT_773
        )
    }
}