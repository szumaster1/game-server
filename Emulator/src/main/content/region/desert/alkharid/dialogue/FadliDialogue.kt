package content.region.desert.alkharid.dialogue

import org.rs.consts.NPCs
import core.api.openBankAccount
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.dialogue.Topic
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.tools.END_DIALOGUE

/**
 * Represents the Fadli dialogue.
 */
class FadliDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.FRIENDLY, "Hi!")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.HALF_ASKING, "What?").also { stage++ }
            1 -> showTopics(
                Topic("What do you do?", 101),
                Topic("What is this place?", 201),
                Topic(FacialExpression.FRIENDLY, "I'd like to store some items, please.", 301),
                Topic("Do you watch any matches?", 401)
            )

            101 -> npcl(FacialExpression.FRIENDLY, "You can store your stuff here if you want. You can dump anything you don't want to carry whilst your fighting duels and then pick it up again on the way out.").also { stage++ }
            102 -> npcl(FacialExpression.SAD, "To be honest I'm wasted here.").also { stage++ }
            103 -> npcl(FacialExpression.EVIL_LAUGH, "I should be winning duels in an arena! I'm the best warrior in Al-Kharid!").also { stage++ }
            104 -> player(FacialExpression.HALF_WORRIED, "Easy, tiger!").also { stage = END_DIALOGUE }
            201 -> npcl(FacialExpression.LAUGH, "Isn't it obvious?").also { stage++ }
            202 -> npcl(FacialExpression.ANNOYED, "This is the Duel Arena...duh!").also { stage = END_DIALOGUE }
            301 -> npcl(FacialExpression.FRIENDLY, "Sure.").also { stage++ }
            302 -> {
                end()
                openBankAccount(player)
            }
            401 -> npcl(FacialExpression.LAUGH, "Most aren't any good so I throw rotten fruit at them!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FADLI_958)
    }
}
