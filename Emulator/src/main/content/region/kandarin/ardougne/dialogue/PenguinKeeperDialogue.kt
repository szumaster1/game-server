package content.region.kandarin.ardougne.dialogue

import core.api.addItemOrDrop
import org.rs.consts.Items
import org.rs.consts.NPCs
import core.api.getStatLevel
import core.api.hasAnItem
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Penguin Keeper dialogue.
 */
@Initializable
class PenguinKeeperDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        playerl(FacialExpression.HALF_ASKING, "Hello there. How are the penguins doing today?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        val hasPenguinEgg = hasAnItem(player, Items.PENGUIN_EGG_12483).container != null
        when (stage) {
            0 -> if (hasPenguinEgg && getStatLevel(player, Skills.SUMMONING) >= 30) {
                npcl(FacialExpression.FRIENDLY, "They are doing fine, thanks.").also { stage = END_DIALOGUE }
            } else {
                npcl(FacialExpression.FRIENDLY, "They are doing fine, thanks.").also { stage++ }
            }
            1 -> npcl(FacialExpression.FRIENDLY, "Actually, you might be able to help me with something - if you are interested.").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "What do you mean?").also { stage++ }
            3 -> npcl(FacialExpression.NEUTRAL, "Well, you see, the penguins have been laying so many eggs recently that we can't afford to raise them all ourselves.").also { stage++ }
            4 -> npcl(FacialExpression.HALF_ASKING, "You seem to know a bit about raising animals - would you like to raise a penguin for us?").also { stage++ }
            5 -> options("Yes, of course.", "No thanks.").also { stage++ }
            6 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "Yes, of course.").also { stage = 8 }
                2 -> playerl(FacialExpression.FRIENDLY, "No thanks.").also { stage = 7 }
            }
            7 -> npcl(FacialExpression.FRIENDLY, "Fair enough. The offer still stands if you change your mind.").also { stage = END_DIALOGUE }
            8 -> npcl(FacialExpression.FRIENDLY, "Wonderful!").also { stage++ }
            9 -> npcl(FacialExpression.FRIENDLY, "Here you go - this egg will hatch into a baby penguin.").also { stage++ }
            10 -> npcl(FacialExpression.FRIENDLY, "They eat raw fish and aren't particularly fussy about anything, so it won't be any trouble to raise.").also { stage++ }
            11 -> playerl(FacialExpression.FRIENDLY, "Okay, thank you very much.").also { stage++ }
            12 -> {
                end()
                addItemOrDrop(player!!, Items.PENGUIN_EGG_12483)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.PENGUIN_KEEPER_6891)
    }
}
