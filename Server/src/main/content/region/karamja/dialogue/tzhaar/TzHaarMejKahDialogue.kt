package content.region.karamja.dialogue.tzhaar

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

@Initializable
class TzHaarMejKahDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.CHILD_GUILTY, "You want help JalYt-Ket-" + player.username + "?")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                options(
                    "What is this place?",
                    "Who's the current champion?",
                    "What did you call me?",
                    "No I'm fine thanks."
                )
                stage = 1
            }

            1 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY, "What is this place?")
                    stage = 10
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY, "Who's the current champion?")
                    stage = 20
                }

                3 -> {
                    player(FacialExpression.HALF_GUILTY, "What did you call me?")
                    stage = 30
                }

                4 -> {
                    player(FacialExpression.HALF_GUILTY, "No I'm fine thanks.")
                    stage = 40
                }
            }

            40 -> end()
            30 -> {
                npc(FacialExpression.CHILD_GUILTY, "Are you not JalYt-Ket?")
                stage = 31
            }

            31 -> {
                player(FacialExpression.HALF_GUILTY, "I guess so...")
                stage = 32
            }

            32 -> {
                npc(FacialExpression.CHILD_GUILTY, "Well then, no problems.")
                stage = 13
            }

            10 -> {
                npc(
                    FacialExpression.CHILD_GUILTY,
                    "This is the fight pit, TzHaar-Xil made it for their sport",
                    "but many JalYt come here to fight too.",
                    "If you are wanting to fight then enter the cage, you",
                    "will be summoned when the next round is ready to begin."
                )
                stage = 11
            }

            11 -> {
                interpreter.sendOptions("Select an Option", "Are there any rules?", "Ok thanks.")
                stage = 12
            }

            12 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY, "Are there any rules?")
                    stage = 14
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY, "Ok thanks.")
                    stage = 13
                }
            }

            13 -> end()
            14 -> {
                npc(
                    FacialExpression.CHILD_GUILTY,
                    "No rules, you use whatever you want. Last person",
                    "standing wins and is declared champion, they stay in",
                    "the pit for the next fight."
                )
                stage = 15
            }

            15 -> {
                interpreter.sendOptions("Select an Option", "Do I win anything?", "Sounds good.")
                stage = 16
            }

            16 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY, "Do I win anything?")
                    stage = 17
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY, "Sounds good.")
                    stage = 13
                }
            }

            17 -> {
                npc(
                    FacialExpression.CHILD_GUILTY,
                    "You ask a lot of questions.",
                    "Champion gets TokKul as reward, but more fights the more",
                    "TokKul they get."
                )
                stage = 18
            }

            18 -> {
                player(FacialExpression.HALF_GUILTY, "...")
                stage = 19
            }

            19 -> {
                npc(FacialExpression.CHILD_GUILTY, "Before you ask, TokKul is like your coins.")
                stage = 400
            }

            400 -> {
                npc(
                    FacialExpression.CHILD_GUILTY,
                    "Gold is like you JalYt, soft and easily broken, we use",
                    "hard rock forged in fire like TzHaar!"
                )
                stage = 401
            }

            401 -> end()
            20 -> {
                npc(
                    FacialExpression.CHILD_GUILTY,
                    "Ah that would be " + npc.getAttribute("fp_champion", "JalYt-Ket-Emperor")
                )
                stage = 21
            }

            21 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.TZHAAR_MEJ_KAH_2618)
    }
}
