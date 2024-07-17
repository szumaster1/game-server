package content.region.kandarin.dialogue.seersvillage

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

@Initializable
class PhantuwtiFanstuwiFarsightDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        options("Hello, what is this place?", "What do you do here?", "Do you have any quests?", "Ok, thanks.")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.ASKING, "Hello, what is this place?")
                    stage = 10
                }

                2 -> {
                    player(FacialExpression.HALF_ASKING, "What do you do here?")
                    stage = 20
                }

                3 -> {
                    player(FacialExpression.HALF_ASKING, "Do you have any quests?")
                    stage = 30
                }

                4 -> {
                    player(FacialExpression.NEUTRAL, "Ok, thanks.")
                    stage = 40
                }
            }

            10 -> {
                npc(FacialExpression.NEUTRAL, "This is Seers Village! We're an organisation of mystically", "gifted people with the power of foresight...we see things", "that have yet to come to pass.")
                stage = 11
            }

            11 -> {
                options("Hello, what is this place?", "What do you do here?", "Do you have any quests?", "Ok, thanks.")
                stage = 0
            }

            20 -> {
                npc(FacialExpression.NEUTRAL, "A lot of our time is spent addressing everyday sorts of", "things, plus we meditate a lot and enhance our", "powers of mystical foresight.")
                stage = 31
            }

            30 -> {
                npc(FacialExpression.HALF_GUILTY, "Unfortunately no, sorry, but if adventure is what you", "seek, try checking through your quest list!")
                stage = 31
            }

            31 -> {
                options("Hello, what is this place?", "What do you do here?", "Do you have any quests?", "Ok, thanks.")
                stage = 0
            }

            40 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.PHANTUWTI_FANSTUWI_FARSIGHT_1798)
    }

}