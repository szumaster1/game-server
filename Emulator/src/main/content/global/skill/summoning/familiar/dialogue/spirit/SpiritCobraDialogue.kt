package content.global.skill.summoning.familiar.dialogue.spirit

import org.rs.consts.Items
import org.rs.consts.NPCs
import core.api.emote
import core.api.inEquipment
import core.api.inInventory
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.emote.Emotes
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Spirit cobra dialogue.
 */
@Initializable
class SpiritCobraDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if(inInventory(player, Items.RING_OF_CHAROSA_6465) || inEquipment(player, Items.RING_OF_CHAROSA_6465)){
            npcl(FacialExpression.OLD_NORMAL, "You are under my power!").also { stage = 20 }
            return true
        }
        when ((0..4).random()) {
            0 -> npcl(FacialExpression.OLD_NORMAL, "Do we have to do thissss right now?").also { stage = 0 }
            1 -> npcl(FacialExpression.OLD_NORMAL, "You are feeling ssssleepy...").also { stage = 5 }
            2 -> npcl(FacialExpression.OLD_NORMAL, "I'm bored, do ssssomething to entertain me...").also { stage = 11 }
            3 -> playerl(FacialExpression.FRIENDLY, "Your will is my command...").also { stage = 13 }
            4 -> npcl(FacialExpression.OLD_NORMAL, "I am king of the world!").also { stage = 15 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "Yes, I'm afraid so.").also { stage++ }
            1 -> npcl(FacialExpression.OLD_NORMAL, "You are under my sssspell...").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "I will do as you ask...").also { stage++ }
            3 -> npcl(FacialExpression.OLD_NORMAL, "Do we have to do thissss right now?").also { stage++ }
            4 -> playerl(FacialExpression.FRIENDLY, "Not at all, I had just finished!").also { stage = END_DIALOGUE }

            5 -> playerl(FacialExpression.FRIENDLY, "I am feeling sssso ssssleepy...").also { stage++ }
            6 -> npcl(FacialExpression.OLD_NORMAL, "You will bring me lotssss of sssstuff!").also { stage++ }
            7 -> playerl(FacialExpression.FRIENDLY, "What ssssort of sssstuff?").also { stage++ }
            8 -> npcl(FacialExpression.OLD_NORMAL, "What ssssort of sssstuff have you got?").also { stage++ }
            9 -> playerl(FacialExpression.FRIENDLY, "All kindsss of sssstuff.").also { stage++ }
            10 -> npcl(FacialExpression.OLD_NORMAL, "Then just keep bringing sssstuff until I'm ssssatissssfied!").also { stage = END_DIALOGUE }

            11 -> playerl(FacialExpression.FRIENDLY, "Errr, I'm not here to entertain you, you know.").also { stage++ }
            12 -> npcl(FacialExpression.OLD_NORMAL, "You will do as I assssk...").also { stage = END_DIALOGUE }

            13 -> npcl(FacialExpression.OLD_NORMAL, "I'm bored, do ssssomething to entertain me...").also { stage++ }
            14 -> {
                playerl(FacialExpression.FRIENDLY, "I'll dance for you!").also {
                    end()
                    emote(player!!, Emotes.DANCE)
                    stage = END_DIALOGUE
                }
            }
            15 -> playerl(FacialExpression.FRIENDLY, "You know, I think there is a law against snakes being the king.").also { stage++ }
            16 -> npcl(FacialExpression.OLD_NORMAL, "My will is your command...").also { stage++ }
            17 -> playerl(FacialExpression.FRIENDLY, "I am yours to command...").also { stage++ }
            18 -> npcl(FacialExpression.OLD_NORMAL, "I am king of the world!").also { stage++ }
            19 -> playerl(FacialExpression.FRIENDLY, "All hail King Serpentor!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SPIRIT_COBRA_6802, NPCs.SPIRIT_COBRA_6803)
    }

}
