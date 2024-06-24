package content.global.random.event.surpriseexam

import core.ServerConstants
import core.api.getAttribute
import core.api.registerLogoutListener
import core.api.sendMessageWithDelay
import core.game.dialogue.DialogueFile
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.TeleportManager
import core.game.system.timer.impl.AntiMacro
import core.game.world.map.Location

class SurpriseExamInitDialogue(val type: String) : DialogueFile() {

    val CHOICE_STAGE = 50000

    override fun handle(componentID: Int, buttonID: Int) {

        if (type == "sexam" && stage < CHOICE_STAGE) {
            npc("Would you like to come do a surprise exam?")
            stage = CHOICE_STAGE
        } else if (stage >= CHOICE_STAGE) {
            when (stage) {
                CHOICE_STAGE -> options("Yeah, sure!", "No, thanks.").also { stage++ }
                CHOICE_STAGE.substage(1) -> when (buttonID) {
                    1 -> {
                        end()
                        teleport(player!!, type)
                        sendMessageWithDelay(player!!, "Answer three out of six questions correctly to be teleported back where you came from.", 1)
                        AntiMacro.terminateEventNpc(player!!)
                    }

                    2 -> {
                        end()
                        AntiMacro.terminateEventNpc(player!!)
                    }
                }
            }
        }
    }

    fun teleport(player: Player, type: String) {
        when (type) {
            "sexam" -> {
                player.setAttribute(SurpriseExamUtils.SE_KEY_LOC, player.location)

                registerLogoutListener(player, SurpriseExamUtils.SE_LOGOUT_KEY) { p ->
                    p.location = getAttribute(p, SurpriseExamUtils.SE_KEY_LOC, ServerConstants.HOME_LOCATION)
                }

                core.api.teleport(player, Location(1886, 5025, 0), TeleportManager.TeleportType.NORMAL)
            }
        }
    }
}
