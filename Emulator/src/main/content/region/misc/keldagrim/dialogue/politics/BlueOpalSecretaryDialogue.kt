package content.region.misc.keldagrim.dialogue.politics

import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import org.rs.consts.NPCs

/**
 * Represents the Blue opal secretary dialogue.
 */
@Initializable
class BlueOpalSecretaryDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        when((0..7).random()){
            0 -> npcl(FacialExpression.OLD_DEFAULT, "I don't have much time for you, what do you want?").also { stage++ }
            1 -> npcl(FacialExpression.OLD_DEFAULT, "You're blocking my view, human, so hurry up.").also { stage++ }
            2 -> npcl(FacialExpression.OLD_DEFAULT, "I'm busy, please leave.").also { stage++ }
            3 -> npcl(FacialExpression.OLD_DEFAULT, "Can we hurry this along?").also { stage++ }
            4 -> npcl(FacialExpression.OLD_DEFAULT, "Say what you want or get out of our office, human!").also { stage++ }
            5 -> npcl(FacialExpression.OLD_DEFAULT, "Do not test the patience of the Blue Opal, human. Say what you need!").also { stage++ }
            6 -> npcl(FacialExpression.OLD_DEFAULT, "I think I've misplaced my quill somewhere...").also { stage++ }
            7 -> npcl(FacialExpression.OLD_DEFAULT, "Oh, and what did you want?").also { stage = END_DIALOGUE }
        }
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> player("But...").also { stage++ }
            1 -> when ((0..1).random()) {
                0 -> npcl(FacialExpression.OLD_ANGRY1, "I said go!").also { stage = END_DIALOGUE }
                1 -> npcl(FacialExpression.OLD_ANGRY2, "I told you to leave, human!").also { stage = END_DIALOGUE }
            }
            100 -> options("Who are you?", "Where am I?", "Is there anything I can help you with?").also { stage++ }
            101 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_ASKING, "Who are you?").also { stage++ }
                2 -> player(FacialExpression.HALF_ASKING, "Where am I?").also { stage = 105 }
            }
            102 -> npcl(FacialExpression.OLD_NORMAL, "I am the head secretary of the Blue Opal.").also { stage++ }
            103 -> player(FacialExpression.HALF_ASKING, "The what?").also { stage++ }
            104 -> npcl(FacialExpression.OLD_DEFAULT, "We are the Blue Opal. We are part of the Consortium, rulers of Keldagrim and beyond.").also { stage = END_DIALOGUE }
            105 -> when ((0..3).random()) {
                0 -> npcl(FacialExpression.OLD_DEFAULT, "I'm not here to give directions!").also { stage = END_DIALOGUE }
                1 -> npcl(FacialExpression.OLD_DEFAULT, "Do I look like a tour guide to you?").also { stage = END_DIALOGUE }
                2 -> npcl(FacialExpression.OLD_NORMAL, "I'd tell you, human, but you wouldn't understand anyway.").also { stage = END_DIALOGUE }
                3 -> npcl(FacialExpression.OLD_NORMAL, "These are the offices of the Blue Opal.").also { stage = 200 }
            }
            200 -> npcl(FacialExpression.OLD_DEFAULT, "We share this floor of the palace with the other companies of the Consortium. And trade with them on the Trade Octagon.").also { stage++ }
            201 -> player(FacialExpression.HALF_ASKING, "The Trade Octagon?").also { stage++ }
            202 -> npcl(FacialExpression.OLD_NORMAL, "See those traders standing out there, just out the door? They're all standing in the Trade Octagon.").also { stage++ }
            203 -> npcl(FacialExpression.OLD_DEFAULT, "There's an awful lot of trade going on in the palace, you know.").also { stage++ }
            204 -> player(FacialExpression.HALF_ASKING, "Anything else of interest in the palace?").also { stage++ }
            205 -> npcl(FacialExpression.OLD_DEFAULT, "You will have to find that out on your own, I'm afraid. I do have more work to do.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BLUE_OPAL_SECRETARY_2094)
    }
}
