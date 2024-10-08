package core.game.worldevents.holiday.halloween

import core.ServerStore
import core.ServerStore.Companion.getInt
import core.ServerStore.Companion.getString
import core.api.addItemOrDrop
import org.rs.consts.Components
import core.api.sendNPCDialogue
import core.game.component.Component
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.tools.END_DIALOGUE
import core.tools.RandomFunction

/**
 * Trick or treat handler.
 */
class TrickOrTreatHandler : InteractionListener {

    override fun defineListeners() {
        on(IntType.NPC, "trick-or-treat") { player, node ->
            val hasDone5 = getDailyTrickOrTreats(player) == 5
            val hasDoneMe = getTrickOrTreatedNPCs(player).contains(node.name.lowercase())

            if (hasDone5) {
                sendNPCDialogue(player, node.id, "My informants tell me you've already collected candy from 5 people today.", FacialExpression.FRIENDLY)
                return@on true
            }

            if (hasDoneMe) {
                sendNPCDialogue(player, node.id, "You've already asked me today! Don't get greedy, now.", FacialExpression.ANNOYED)
                return@on true
            }

            player.dialogueInterpreter.open(object : DialogueFile() {
                override fun handle(componentID: Int, buttonID: Int) {
                    when (stage) {
                        0 -> playerl(FacialExpression.FRIENDLY, "Trick or treat!").also { if (RandomFunction.roll(20)) stage = 10 else stage++ }

                        1 -> npcl(FacialExpression.FRIENDLY, "Very well, then, here you are my friend.").also { stage++ }

                        2 -> {
                            player.dialogueInterpreter.sendItemMessage(14084, "They hand you a nicely-wrapped candy.")
                            addItemOrDrop(player, 14084, 1)
                            registerNpc(player, npc!!)
                            incrementDailyToT(player)
                            stage = END_DIALOGUE
                        }

                        10 -> npcl(FacialExpression.EVIL_LAUGH, "I CHOOSE TRICK!").also {
                            player.lock(); Pulser.submit(
                            object : Pulse() {
                                var counter = 0
                                override fun pulse(): Boolean {
                                    //gfx 1898
                                    when (counter++) {
                                        0 -> npc!!.visualize(
                                            Animation(1979),
                                            Graphic(1898)
                                        ).also { npc!!.faceLocation(player.location) }

                                        2 -> player.dialogueInterpreter.close()
                                        5 -> player.interfaceManager.open(Component(Components.FADE_TO_BLACK_120))
                                        8 -> player.properties.teleportLocation = Location.create(3106, 3382, 0)
                                        12 -> {
                                            player.interfaceManager.close()
                                            player.interfaceManager.open(Component(Components.FADE_FROM_BLACK_170))
                                            registerNpc(player, npc!!)
                                        }

                                        15 -> player.interfaceManager.close().also { player.unlock() }
                                        16 -> return true
                                    }
                                    return false
                                }
                            })
                        }
                    }
                }
            }, node.asNpc())
            return@on true
        }
    }

    fun incrementDailyToT(player: Player) {
        ServerStore.getArchive("daily-tot-total")[player.username.lowercase()] = getDailyTrickOrTreats(player) + 1
    }

    fun getDailyTrickOrTreats(player: Player): Int {
        return ServerStore.getArchive("daily-tot-total").getInt(player.username.lowercase())
    }

    fun getTrickOrTreatedNPCs(player: Player): String {
        return ServerStore.getArchive("daily-tot-npcs").getString(player.username.lowercase())
    }

    fun registerNpc(player: Player, npc: NPC) {
        var soFar = getTrickOrTreatedNPCs(player)
        soFar += ":" + npc.name.lowercase() + ":"
        ServerStore.getArchive("daily-tot-npcs")[player.username.lowercase()] = soFar
    }
}
