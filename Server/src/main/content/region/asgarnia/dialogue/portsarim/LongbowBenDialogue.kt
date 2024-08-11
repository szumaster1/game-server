package content.region.asgarnia.dialogue.portsarim

import core.api.consts.NPCs
import core.api.sendDialogueOptions
import core.api.setTitle
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Longbow Ben dialogue.
 */
@Initializable
class LongbowBenDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Longbow Ben is a pirate inside The Rusty Anchor in Port Sarim.
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HALF_GUILTY, "Arrr, matey!")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                setTitle(player, 2)
                sendDialogueOptions(player, "What would you like to say?", "Why are you called Longbow Ben?", "Have you got any quests I could do?").also { stage++ }
            }

            1 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY, "Why are you called Longbow Ben?")
                    stage = 100
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY, "Have you got any quests I could do?")
                    stage = 200
                }
            }

            10 -> {
                npc(FacialExpression.HALF_GUILTY, "I was to be marooned, ye see. A srurvy troublemaker had", "taken my ship, and he put  me ashore on a little island.")
                stage = 11
            }

            11 -> {
                player(FacialExpression.HALF_GUILTY, "Gosh, how did you escape?")
                stage = 12
            }

            12 -> {
                npc(FacialExpression.HALF_GUILTY, "Arrr, ye see, he made on mistake! Before he sailed", "he gave me a bow and one arrow so that I wouldn't have", "to die slowly.")
                stage = 13
            }

            13 -> {
                npc(FacialExpression.HALF_GUILTY, "So I shot him and took my ship back.")
                stage = 14
            }

            14 -> {
                player(FacialExpression.HALF_GUILTY, "Right...")
                stage = 15
            }

            15 -> end()
            100 -> {
                npc(FacialExpression.HALF_GUILTY, "Arrr, that's a strange yarn.")
                stage = 10
            }

            200 -> {
                npc(FacialExpression.HALF_GUILTY, "Nay, I've nothing for ye to do.")
                stage = 201
            }

            201 -> {
                player(FacialExpression.HALF_GUILTY, "Thanks.")
                stage = 202
            }

            202 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.LONGBOW_BEN_2691)
    }
}
