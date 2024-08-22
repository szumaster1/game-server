package content.region.kandarin.quest.merlinsquest.dialogue

import cfg.consts.NPCs
import core.api.sendDialogue
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.dialogue.Topic
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE

/**
 * Represents the Door Renegade Knight dialogue file.
 *
 * Related to [Merlin Crystal][content.region.kandarin.quest.merlinsquest.MerlinCrystal] quest.
 * @author lostmyphat
 */
class DoorRenegadeKnightDialogueFile : DialogueFile() {

    var STAGE_NO = 10

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.RENEGADE_KNIGHT_237)

        when (stage) {
            0 -> {
                if (player!!.location.x >= 2764) {
                    // we are inside already
                    playerl(FacialExpression.THINKING, "Uh... I don't think anyone outside will answer me if I knock on the door...")
                    stage = END_DIALOGUE
                } else {
                    sendDialogue(player!!, "You knock at the door. You hear a voice from inside...")
                    stage++
                }
            }
            1 -> {
                npcl(FacialExpression.ANGRY, "Yes? What do you want?")
                stage++
            }
            2 -> {
                playerl(FacialExpression.NEUTRAL, "Um...")
                stage++
            }
            3 -> {
                showTopics(
                        Topic(FacialExpression.ANGRY,"Pizza Delivery!", 5),
                        Topic("Have you ever thought about letting Saradomin into your life?", STAGE_NO),
                        Topic("Can I interest you in some double glazing?", 15),
                        Topic("Would you like to buy some lucky heather?", STAGE_NO)
                )
            }

            // option 1
            5 -> {
                npcl(FacialExpression.NEUTRAL, "We didn't order any Pizza. Get lost!")
                stage = END_DIALOGUE
            }

            // option 2 & 4
            10 -> {
                npcl(FacialExpression.ANGRY,"No. Go away.")
                stage = END_DIALOGUE
            }

            //option 3
            15 -> {
                npc(FacialExpression.ANGRY,"No. Get out of here before I run you through.")
                stage++
            }
            16 -> {
                sendDialogue(player!!,"It looks like you'll have to find another way in...")
                stage = END_DIALOGUE
            }
        }
    }

}
