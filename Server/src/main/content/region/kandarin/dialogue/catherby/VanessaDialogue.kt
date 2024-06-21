package content.region.kandarin.dialogue.catherby

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

@Initializable
class VanessaDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HAPPY, "Hello. How can I help you?")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                interpreter.sendOptions(
                    "Select an Option",
                    "What are you selling?",
                    "Can you give me any Farming advice?",
                    "I'm okay, thank you."
                )
                stage = 10
            }

            10 -> when (buttonId) {
                1 -> {
                    end()
                    npc.openShop(player)
                }

                2 -> {
                    player(FacialExpression.HALF_ASKING,
                        "Can you give me any Farming advice?"
                    )
                    stage = 20
                }

                3 -> {
                    player(FacialExpression.FRIENDLY, "I'm okay, thank you.")
                    stage = 30
                }
            }

            20 -> {
                npc(FacialExpression.HALF_GUILTY, "Yes - ask a gardener.")
                stage = 30
            }

            30 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.VANESSA_2305)
    }
}