package content.region.kandarin.dialogue.stronghold

import content.region.fremennik.quest.horrorfromthedeep.dialogue.GunnjornDialogueFile
import cfg.consts.Items
import cfg.consts.NPCs
import core.api.getQuestStage
import core.api.inInventory
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Gunnjorn dialogue.
 */
@Initializable
class GunnjornDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (getQuestStage(player, "Horror from the Deep") > 0 && !inInventory(player, Items.LIGHTHOUSE_KEY_3848)) {
            options("Talk about Horror from the Deep.", "Talk about the Agility course.", "Talk about the wall after the log balance.", "Nothing.").also { stage = 1 }
        } else {
            options("Talk about the Agility course.", "Talk about the wall after the log balance.", "Can I talk about rewards?", "Nothing.").also { stage = 2 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            1 -> when (buttonId) {
                1 -> {
                    end()
                    openDialogue(player, GunnjornDialogueFile(), npc).also { stage = END_DIALOGUE }
                }
                2 -> playerl(FacialExpression.FRIENDLY, "Hey there. What is this place?").also { stage = 3 }
                3 -> playerl(FacialExpression.FRIENDLY, "What's wrong with the wall after the log balance?").also { stage = 6 }
                4 -> playerl(FacialExpression.FRIENDLY, "Nothing.").also { stage = 15 }
            }

            2 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "Hey there. What is this place?").also { stage = 3 }
                2 -> playerl(FacialExpression.FRIENDLY, "What's wrong with the wall after the log balance?").also { stage = 6 }
                3 -> playerl(FacialExpression.FRIENDLY, "Can I talk about rewards?").also { stage = 16 } // Release 22 June 2009 (Update)
                4 -> playerl(FacialExpression.FRIENDLY, "Nothing.").also { stage = 15 }
            }
            3 -> npc(FacialExpression.FRIENDLY, "Haha welcome to my obstacle course. Have fun, but", "remember this isn't a child's playground. People have", "died here.").also { stage++ }
            4 -> npcl(FacialExpression.FRIENDLY, "This course starts at the ropeswings to the east. When you've done the swing, head across the slippery log to the building. When you've traversed the obstacles inside, you'll come out on the other side.").also { stage++ }
            5 -> npcl(FacialExpression.FRIENDLY, "From there, head across the low walls to finish. If you've done all the obstacles as I've described, and in order, you'll get a lap bonus.").also { stage = END_DIALOGUE }
            6 -> npcl(FacialExpression.FRIENDLY, "The wall after the log balance? Nothing, really. I just put some tough material on it, giving some people something to grip hold of.").also { stage++ }
            7 -> playerl(FacialExpression.FRIENDLY, "Why would you do that?").also { stage++ }
            8 -> npcl(FacialExpression.FRIENDLY, "So people like you can have a tougher route round this course. Me and a mate get together and set up a new challenge that only the truly agile will conquer.").also { stage++ }
            9 -> npcl(FacialExpression.FRIENDLY, "The extra stuff starts at that wall; so, if you think you're up to it, I suggest you scramble up there after the log balance.").also { stage++ }
            10 -> playerl(FacialExpression.FRIENDLY, "Sounds interesting. Anything else I should know?").also { stage++ }
            11 -> npcl(FacialExpression.FRIENDLY, "Nothing, really. Just make sure you complete the other obstacles before ya do. If you finish a full lap, you'll get an increased bonus for doing the tougher route.").also { stage++ }
            12 -> npcl(FacialExpression.FRIENDLY, "If you manage to do 250 laps of this advanced route without a single mistake, I'll let you have a special item.").also { stage++ }
            13 -> npcl(FacialExpression.FRIENDLY, "I'll keep track of your lap tallies, so you can check how you're getting on with me at any time.").also { stage++ }
            14 -> playerl(FacialExpression.FRIENDLY, "That's all I need for now. Bye.").also { stage++ }
            15 -> npcl(FacialExpression.FRIENDLY, "Bye for now. Come back if you need any help.").also { stage = END_DIALOGUE }
            16 -> npcl(FacialExpression.FRIENDLY, "There's no reward for you just yet. Your lap count is only 0. It's 250 successful laps or no reward.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GUNNJORN_607)
    }

}
