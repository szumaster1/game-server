package content.region.misthalin.dialogue.varrock

import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable

@Initializable
class VarrockBartenderDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.HALF_GUILTY, "Hello.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                npc(FacialExpression.HALF_ASKING,
                    "Good day to you, brave adventurer. Can I get you a",
                    "refreshing beer?"
                )
                stage = 1
            }

            1 -> {
                options( "Yes please!", "No thanks.", "How much?")
                stage = 2
            }

            2 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HAPPY, "Yes please!")
                    stage = 10
                }

                2 -> {
                    player(FacialExpression.NEUTRAL, "No thanks.")
                    stage = 20
                }

                3 -> {
                    player(FacialExpression.HALF_ASKING, "How much?")
                    stage = 30
                }
            }

            10 -> {
                npc(FacialExpression.FRIENDLY, "Ok then, that's two gold coins please.")
                stage = 11
            }

            11 -> if (player.inventory.contains(995, 2)) {
                player.inventory.remove(Item(995, 2))
                player.inventory.add(Item(1917, 1))
                npc(FacialExpression.HAPPY, "Cheers!")
                stage = 12
            } else {
                end()
                player.packetDispatch.sendMessage("You need two gold coins to buy a beer.")
            }

            12 -> {
                player(FacialExpression.HAPPY, "Cheers!")
                stage = 13
            }

            13 -> end()
            20 -> {
                npc(FacialExpression.FRIENDLY, "Let me know if you change your mind.")
                stage = 21
            }

            21 -> end()
            30 -> {
                npc(FacialExpression.FRIENDLY,
                    "Two gold pieces a pint. So, what do you say?"
                )
                stage = 31
            }

            31 -> {
                options( "Yes please!", "No thanks.")
                stage = 32
            }

            32 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HAPPY, "Yes please!")
                    stage = 10
                }

                2 -> {
                    player(FacialExpression.FRIENDLY, "No thanks.")
                    stage = 20
                }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(732, 731)
    }

}