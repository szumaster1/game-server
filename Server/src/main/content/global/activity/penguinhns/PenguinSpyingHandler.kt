package content.global.activity.penguinhns

import core.api.*
import core.api.consts.NPCs
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.update.flag.context.Animation

class PenguinSpyingHandler : InteractionListener {

    override fun defineListeners() {
        on(PENGUINS, IntType.NPC, "spy-on") { player, node ->
            val npc = node.asNpc()
            if (PenguinManager.hasTagged(player, npc.location)) {
                sendMessage(player, "You've already tagged this penguin.")
            } else {
                Pulser.submit(SpyPulse(player, npc)!!)
                playJingle(player, 345)
            }
            return@on true
        }
    }

    val PENGUINS = intArrayOf(NPCs.BARREL_8104, NPCs.BUSH_8105, NPCs.CACTUS_8107, NPCs.CRATE_8108, NPCs.ROCK_8109, NPCs.TOADSTOOL_8110)


    class SpyPulse(val player: Player, val npc: NPC) : Pulse() {
        var stage = 0
        val curPoints = getAttribute(player, "phns:points", 0)
        val ANIMATION = Animation(10355)

        override fun pulse(): Boolean {
            when (stage++) {

                0 -> {
                    lock(player,1000)
                    animate(player, 10355)
                }
                2 -> {
                    sendMessage(player, "You manage to spy on the penguin.").also {
                        setAttribute(player, "/save:phns:points", curPoints + 1)
                        PenguinManager.registerTag(player, npc.location)
                    }
                }

                3 -> {
                    unlock(player)
                    return true
                }
            }
            return false
        }

        override fun stop() {
            super.stop()
            unlock(player)
        }
    }
}