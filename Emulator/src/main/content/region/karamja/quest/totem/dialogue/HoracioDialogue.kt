package content.region.karamja.quest.totem.dialogue

import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import org.rs.consts.NPCs
import org.rs.consts.QuestName

/**
 * Represents the Horacio dialogue.
 */
@Initializable
class HoracioDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        if (player.questRepository.hasStarted(QuestName.TRIBAL_TOTEM)) {
            npcl(FacialExpression.HAPPY, "It's a fine day to be out in a garden, isn't it?").also { stage = 5 }
        } else {
            npcl(FacialExpression.HAPPY, "It's a fine day to be out in a garden, isn't it?")
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.HAPPY, "Yes it's very nice.").also { stage++ }
            1 -> npcl(FacialExpression.HAPPY, "Days like these make me glad to be alive!").also { stage = END_DIALOGUE }
            5 -> playerl(FacialExpression.ASKING, "So... who are you?").also { stage++ }
            6 -> npcl(FacialExpression.HAPPY, "My name is Horacio Dobson. I'm a gardener to Lord Handlemort.").also { stage++ }
            7 -> npcl(FacialExpression.HAPPY, "Take a look around this beautiful garden, all of this is my handiwork.").also { stage++ }
            8 -> options("So... do you garden round the back too?", "Do you need any help?").also { stage++ }
            9 -> when (buttonId) {
                1 -> playerl(FacialExpression.THINKING, "So... do you garden round the back, too?").also { stage = 10 }
                2 -> playerl(FacialExpression.ASKING, "Do you need any help?").also { stage = 20 }
            }
            10 -> npcl(FacialExpression.HAPPY, "That I do!").also { stage++ }
            11 -> playerl(FacialExpression.ASKING, "Doesn't all of the security around the house get in your way then?").also { stage++ }
            12 -> npcl(FacialExpression.HAPPY, "Ah. I'm used to all that. I have my keys, the guard dogs know me, and I know the combination to the door lock.").also { stage++ }
            13 -> npcl(FacialExpression.HAPPY, "It's rather easy, it's his middle name.").also { stage++ }
            14 -> playerl(FacialExpression.ASKING, "Whose middle name?").also { stage++ }
            15 -> npcl(FacialExpression.ANNOYED, "Hum. I probably shouldn't have said that. Forget I mentioned it.").also { stage = END_DIALOGUE }
            20 -> npcl(FacialExpression.ANNOYED, "Trying to muscle in on my job, eh? I'm more than happy to do this all by myself!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.HORACIO_845)
    }
}
