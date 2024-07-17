package content.region.misc.dialogue.entrana

import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

@Initializable
class FrincosDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HALF_GUILTY, "Hello, how can I help you?")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                options("What are you selling?",
                    "You can't; I'm beyond help.",
                    "I'm okay, thank you."
                )
                stage = 1
            }

            1 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY, "What are you selling?")
                    stage = 10
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY, "You can't; I'm beyond help.")
                    stage = 20
                }

                3 -> {
                    player(FacialExpression.HALF_GUILTY, "I'm okay, thank you.")
                    stage = 30
                }
            }

            10 -> {
                end()
                npc.openShop(player)
            }

            20 -> end()
            30 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(578)
    }
}
