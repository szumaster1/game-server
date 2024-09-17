package content.minigame.castlewars

import org.rs.consts.Items
import org.rs.consts.NPCs
import core.api.openNpcShop
import core.api.sendMessage
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Lanthus dialogue.
 * @author Dginovoker
 */
@Initializable
class LanthusDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npcl(FacialExpression.FRIENDLY, "Good day, how may I help you?")
        stage = 1
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            1 -> options("What is this place?", "What do you have for trade?", "Do you have a manual? I'd like to learn how to play!").also { stage++ }

            2 -> when (buttonId) {
                1 -> player("What is this place?").also { stage = 4 }
                2 -> openNpcShop(player!!, NPCs.LANTHUS_1526).also { stage = END_DIALOGUE }
                3 -> player("Do you have a manual? I'd like to learn how to play!").also { stage = 50 }
            }
            4 -> npcl(FacialExpression.FRIENDLY, "This is the great Castle Wars arena! Here you can fight for the glory of Saradomin or Zamorak.").also { stage++ }
            5 -> options("Really, how do I do that?", "Are there any rules?", "What can I win?").also { stage++ }
            6 -> when (buttonId) {
                1 -> player("Really, how do I do that?").also { stage = 7 }
                2 -> player("Are there any rules?").also { stage = 80 }
                3 -> player("What can I win?").also { stage = 90 }
            }
            7 -> npcl(FacialExpression.FRIENDLY, "Easy, you just step through one of the three portals. To join Zamorak, pass through the red portal. To join Saradomin, pass through the blue portal. If you don't mind then pass through the green portal.",).also { stage++ }
            8 -> options("Are there any rules?", "What can I win?", "What do you have for trade?", "Do you have a manual? I'd like to learn how to play!").also { stage++ }
            9 -> when (buttonId) {
                1 -> player("Are there any rules?").also { stage = 80 }
                2 -> player("What can I win?").also { stage = 90 }
                3 -> openNpcShop(player!!, NPCs.LANTHUS_1526).also { stage = END_DIALOGUE }
                4 -> player("Do you have a manual? I'd like to learn how to play!").also { stage = 50 }
            }
            80 -> npcl(FacialExpression.FRIENDLY, "Of course, there are always rules. Firstly you can't wear a cape as you enter the portal, you'll be given your team colours to wear while in the arena. You're also prohibited from taking non-combat related items in").also { stage++ }
            81 -> npcl(FacialExpression.FRIENDLY, " with you. So you should only have equipment, potions, and runes on you. Secondly, attacking your own team or your team's defences isn't allowed. You don't want to be angering").also { stage++ }
            82 -> npcl(FacialExpression.FRIENDLY, "your patron god, do you? Other than that, just have fun and enjoy it!").also { stage++ }
            50 -> npcl(FacialExpression.FRIENDLY, "Sure, here you go.").also { stage = END_DIALOGUE }.also { player!!.inventory.add(Item(Items.CASTLEWARS_MANUAL_4055)) }
            83 -> player("Great! Oh, how do I win the game?").also { stage++ }
            84 -> npcl(FacialExpression.FRIENDLY, "The aim is to get into your opponents' castle and take their team standard. Then bring that back and capture it on your team's standard.").also { stage++ }
            85 -> options("What can I win?", "What do you have to trade?", "Do you have a manual? I'd like to learn how to play!").also { stage++ }
            86 -> when (buttonId) {
                1 -> player("What can I win?").also { stage = 90 }
                2 -> openNpcShop(player!!, NPCs.LANTHUS_1526).also { stage = END_DIALOGUE }
                3 -> player("Do you have a manual? I'd like to learn how to play!").also { stage = 50 }
            }
            90 -> npcl(FacialExpression.FRIENDLY, "Players on the winning team will receive 2 Castle Wars Tickets which you can trade back to me for other items. In the event of a draw every player will get 1 ticket.").also { stage++ }
            91 -> options("Are there any rules?", "What do you have to trade?", "Do you have a manual? I'd like to learn how to play!").also { stage++ }
            92 -> when (buttonId) {
                1 -> player("Are there any rules?").also { stage = 80 }
                2 -> openNpcShop(player!!, NPCs.LANTHUS_1526).also { stage = END_DIALOGUE }
                3 -> player("Do you have a manual? I'd like to learn how to play!").also { stage = 50 }
            }
            // Default
            else -> sendMessage(player!!, "Error - unknown stage $stage").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.LANTHUS_1526)
    }

}
