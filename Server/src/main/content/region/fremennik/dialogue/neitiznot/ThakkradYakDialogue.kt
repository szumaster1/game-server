package content.region.fremennik.dialogue.neitiznot

import core.api.sendNPCDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.DialogueInterpreter
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * The Thakkrad yak dialogue.
 */
class ThakkradYakDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        options("Cure my yak-hide, please.", "Nothing, thanks.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> when (buttonId) {
                1 -> {
                    player("Cure my yak-hide, please.")
                    stage = 4
                }

                2 -> {
                    player("Nothing, thanks.")
                    stage++
                }
            }

            1 -> {
                interpreter.sendDialogues(5506, -1,false, "See you later.")
                stage++
            }

            2 -> {
                interpreter.sendDialogues(5506, -1,false, "You won't find anyone else who can cure yak-hide.")
                stage++
            }

            3 -> end()
            4 -> {
                interpreter.sendDialogues(5506, -1,false,"I will cure yak-hide for a fee of 5 gp per hide.")
                stage++
            }

            5 -> {
                if (!player.inventory.contains(10818, 1)) {
                    interpreter.sendDialogues(5506, -1,false,"You have no yak-hide to cure.")
                    stage = 7

                }
                if (!player.inventory.contains(995, 5)) {
                    interpreter.sendDialogues(5506, -1,false, "You don't have enough gold to pay me!")
                    stage = 7

                }
                options(
                    "Cure all my hides.",
                    "Cure one hide.",
                    "Cure no hide.",
                    "Can you cure any other type of leather?"
                )
                stage++
            }

            6 -> when (buttonId) {
                1, 2 -> {
                    cure(player, if (buttonId == 2) 1 else player.inventory.getAmount(10818))
                    stage = 8
                }

                3 -> {
                    interpreter.sendDialogues(5506, -1,false, "Bye!")
                    stage = 7
                }

                4 -> {
                   sendNPCDialogue(player, 5506, "Other types of leather? Why would you need any other type of leather?")
                    stage = 40
                }
            }

            7 -> end()
            40 -> {
                player("I'll take that as a no then.")
                stage = 7
            }

            8 -> end()
        }
        return true
    }

    private fun cure(player: Player, amount: Int): Boolean {
        if (!player.inventory.contains(995, 5 * amount)) {
            interpreter.sendDialogues(5506, -1,false, "You don't have enough gold to pay me!")
            return false
        }
        if (player.inventory.remove(Item(995, 5 * amount))) {
            for (i in 0 until amount) {
                if (player.inventory.remove(Item(10818))) {
                    player.inventory.add(Item(10820))
                }
            }
        }
        player("There you go!")
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(DialogueInterpreter.getDialogueKey("thakkrad-yak"))
    }
}