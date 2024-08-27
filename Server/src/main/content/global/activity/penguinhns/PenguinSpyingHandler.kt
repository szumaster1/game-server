package content.global.activity.penguinhns

import core.api.*
import cfg.consts.NPCs
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.update.flag.context.Animation

/**
 * Penguin spying handler.
 */
class PenguinSpyingHandler : InteractionListener {

    override fun defineListeners() {
        // This method defines the listeners for the "spy-on" interaction with penguins.
        on(sceneryPenguinIDs, IntType.NPC, "spy-on") { player, node ->
            val npc = node.asNpc()
            if (PenguinManager.hasTagged(player, npc.location)) {
                sendMessage(player, "You've already tagged this penguin.")
            } else {
                Pulser.submit(SpyPulse(player, npc))
                playJingle(player, 345)
            }
            return@on true
        }
    }

    private val sceneryPenguinIDs = intArrayOf(NPCs.BARREL_8104, NPCs.BUSH_8105, NPCs.CACTUS_8107, NPCs.CRATE_8108, NPCs.ROCK_8109, NPCs.TOADSTOOL_8110)

    class SpyPulse(val player: Player, val npc: NPC) : Pulse() {
        var stage = 0
        val totalPoints = getAttribute(player, "phns:points", 0)
        val animationID = Animation(10355)

        override fun pulse(): Boolean {
            when (stage++) {
                // This stage locks the player for 1000 milliseconds and plays an animation.
                0 -> {
                    lock(player,1000)
                    animate(player, animationID)
                }
                // This stage sends a message to the player, increments the player's points, and registers the tag on the penguin.
                2 -> {
                    sendMessage(player, "You manage to spy on the penguin.").also {
                        setAttribute(player, "/save:phns:points", totalPoints + 1)
                        PenguinManager.registerTag(player, npc.location)
                    }
                }
                // This stage unlocks the player and returns true to stop the pulse.
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
