package content.region.wilderness.handlers

import core.api.*
import org.rs.consts.NPCs
import org.rs.consts.Sounds
import core.game.dialogue.DialogueFile
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.TeleportManager
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Location

/**
 * Represents the Wilderness lever listeners.
 */
class WildernessLeverListeners : InteractionListener {

    private val leverIds = intArrayOf(1814, 1815, 5959, 5960, 9706, 9707)

    private fun teleport(player: Player, location: Location?) {
        player.teleporter.send(location, TeleportManager.TeleportType.NORMAL, TeleportManager.WILDY_TELEPORT)
    }

    override fun defineListeners() {
        on(leverIds, IntType.SCENERY, "pull") { player, node ->
            when (node.id) {
                1814 -> {
                    openDialogue(player, object : DialogueFile() {
                        override fun handle(componentID: Int, buttonID: Int) {
                            when (stage) {
                                0 -> sendDialogue(player, "Warning! Pulling the lever will teleport you deep into the wilderness.").also { stage++ }
                                1 -> options("Yes I'm brave.", "Eep! The wilderness... No thank you.").also { stage++ }
                                2 -> when (buttonID) {
                                    1 -> {
                                        closeDialogue(player)
                                        lock(player, 2)
                                        animate(player, 2140)
                                        playAudio(player, Sounds.LEVER_2400)
                                        sendMessage(player, "You pull the lever...")
                                        Pulser.submit(object : Pulse(2, player) {
                                            override fun pulse(): Boolean {
                                                if (player.timers.getTimer("teleblock") == null) {
                                                    teleport(player, Location(3154, 3923, 0))
                                                    sendMessage(player, "...And teleport into the wilderness.")
                                                } else {
                                                    sendMessage(
                                                        player, "A magical force has stopped you from teleporting."
                                                    )
                                                }
                                                return true
                                            }
                                        })
                                    }

                                    2 -> end()
                                }
                            }
                        }
                    })
                }

                1815, 5959, 5960, 9706, 9707 -> {
                    if (!player.savedData.activityData.hasKilledKolodion() && node.id == 9706) {
                        sendNPCDialogue(player, NPCs.KOLODION_905, "You're not allowed in there. Come downstairs if you want to enter my arena.")
                        return@on false
                    }
                    lock(player, 2)
                    animate(player, 2140)
                    playAudio(player, Sounds.LEVER_2400)
                    sendMessage(player, "You pull the lever...")
                    Pulser.submit(object : Pulse(2, player) {
                        override fun pulse(): Boolean {
                            if (player.timers.getTimer("teleblock") == null) {
                                teleport(
                                    player, when (node.id) {
                                        1815 -> Location(2561, 3311, 0)
                                        5959 -> Location(2539, 4712, 0)
                                        5960 -> Location(3090, 3956, 0)
                                        9706 -> Location(3105, 3951, 0)
                                        else -> Location(3105, 3956, 0)
                                    }
                                )
                                sendMessage(
                                    player, "...And teleport ${
                                        when (node.id) {
                                            1815 -> "out of the wilderness."
                                            5959 -> "into the mage's cave."
                                            5960 -> "out of the mage's cave."
                                            9706 -> "out of the arena."
                                            else -> "into the arena."
                                        }
                                    }"
                                )
                            } else {
                                sendMessage(player, "A magical force has stopped you from teleporting.")
                            }
                            return true
                        }
                    })
                }

                else -> {
                    sendMessage(player, "nothing interesting happens.")
                }
            }

            return@on true
        }
    }
}