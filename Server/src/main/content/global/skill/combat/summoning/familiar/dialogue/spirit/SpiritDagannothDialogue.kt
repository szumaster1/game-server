package content.global.skill.combat.summoning.familiar.dialogue.spirit

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Spirit dagannoth dialogue.
 */
@Initializable
class SpiritDagannothDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..3).random()) {
            0 -> npc(FacialExpression.CHILD_NORMAL, "Grooooooowl graaaaawl raaaawl?","(Are you ready to surrender to the power of","the Deep Waters?)").also { stage = 0 }
            1 -> npc(FacialExpression.CHILD_NORMAL, "Groooooowl. Hsssssssssssssss!","(The Deeps will swallow the lands. None will","stand before us!)").also { stage = 5 }
            2 -> npc(FacialExpression.CHILD_NORMAL, "Hssssss graaaawl grooooowl, growwwwwwwwwl!","(Oh how the bleak gulfs hunger for the","Day of Rising.)").also { stage = 8 }
            3 -> npc(FacialExpression.CHILD_NORMAL, "Raaaawl!","(Submit!)").also { stage = 11 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "Err, not really.").also { stage++ }
            1 -> npc(FacialExpression.CHILD_NORMAL, "Rooooowl?","(How about now?)").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "No, sorry.").also { stage++ }
            3 -> npc(FacialExpression.CHILD_NORMAL, "Rooooowl?","(How about now?)").also { stage++ }
            4 -> playerl(FacialExpression.FRIENDLY, "No, sorry. You might want to try again a little later.").also { stage = END_DIALOGUE }

            5 -> playerl(FacialExpression.FRIENDLY, "What if we build boats?").also { stage++ }
            6 -> npc(FacialExpression.CHILD_NORMAL, "Hsssssssss groooooowl?","Hssssshsss grrooooooowl?","(What are boats? The tasty wooden containers full of meat?)").also { stage++ }
            7 -> playerl(FacialExpression.FRIENDLY, "I suppose they could be described as such, yes.").also { stage = END_DIALOGUE }

            8 -> playerl(FacialExpression.FRIENDLY, "My brain hurts when I listen to you talk...").also { stage++ }
            9 -> npc(FacialExpression.CHILD_NORMAL, "Raaaaawl groooowl grrrrawl!","(That's the truth biting into your clouded mind!)").also { stage++ }
            10 -> playerl(FacialExpression.FRIENDLY, "Could you try using a little less truth please?").also { stage = END_DIALOGUE }

            11 -> playerl(FacialExpression.FRIENDLY, "Submit to what?").also { stage++ }
            12 -> npc(FacialExpression.CHILD_NORMAL, "Hssssssssss rawwwwwl graaaawl!","(To the inevitable defeat of all life on the Surface!)").also { stage++ }
            13 -> playerl(FacialExpression.FRIENDLY, "I think I'll wait a little longer before I just keep over and submit, thanks.").also { stage++ }
            14 -> npc(FacialExpression.CHILD_NORMAL, "Hsssss, grooooowl, raaaaawl.","(Well, it's your choice, but those that submit first will be eaten first.)").also { stage++ }
            15 -> playerl(FacialExpression.FRIENDLY, "I'll pass on that one, thanks.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SPIRIT_DAGANNOTH_6804, NPCs.SPIRIT_DAGANNOTH_6805)
    }
}
