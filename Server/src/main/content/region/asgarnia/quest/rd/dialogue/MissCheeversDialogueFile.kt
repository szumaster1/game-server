package content.region.asgarnia.quest.rd.dialogue

import content.region.asgarnia.quest.rd.MissCheeversRoomListeners
import core.api.removeAttribute
import core.game.dialogue.DialogueBuilder
import core.game.dialogue.DialogueBuilderFile
import core.game.dialogue.FacialExpression

/**
 * Represents the Miss Cheevers dialogue file.
 */
class MissCheeversDialogueFile(private val dialogueNum: Int = 0) : DialogueBuilderFile() {

    override fun create(b: DialogueBuilder) {
        b.onPredicate { _ -> dialogueNum == 0 }

            .playerl(FacialExpression.FRIENDLY, "Can you give me any help?")
            .npcl(FacialExpression.FRIENDLY, "No, I am sorry, but that is forbidden by our rules.")
            .npcl(FacialExpression.FRIENDLY, "If you are having a particularly tough time of it, I suggest you leave and come back later when you are in a more receptive frame of mind.")
            .npcl(FacialExpression.FRIENDLY, "Sometimes a break from concentration will yield fresh insight. Our aim is to test you, but not to the point of frustration!")
            .playerl(FacialExpression.FRIENDLY, "Okay, thanks!")
            .end()


        b.onPredicate { _ -> dialogueNum == 1 }.betweenStage { _, player, _, _ ->

            removeAttribute(player, MissCheeversRoomListeners.ATTRIBUTE_BOOK)
            removeAttribute(player, MissCheeversRoomListeners.ATTRIBUTE_MAGNET)
            removeAttribute(player, MissCheeversRoomListeners.ATTRIBUTE_KNIFE)
            removeAttribute(player, MissCheeversRoomListeners.ATTRIBUTE_SHEARS)
            removeAttribute(player, MissCheeversRoomListeners.ATTRIBUTE_TIN)
            removeAttribute(player, MissCheeversRoomListeners.ATTRIBUTE_CHISEL)
            removeAttribute(player, MissCheeversRoomListeners.ATTRIBUTE_WIRE)
            removeAttribute(player, MissCheeversRoomListeners.ATTRIBUTE_VIALS)
            MissCheeversRoomListeners.Companion.Vials.vialMap.map {
                removeAttribute(player, it.value.attribute)
            }

            MissCheeversRoomListeners.Companion.DoorVials.doorVialsRequiredMap.map {
                removeAttribute(player, it.value.attribute)
            }
        }
            .npcl(FacialExpression.FRIENDLY, "Greetings,  ${player!!.username}. Welcome to my challenge.")
            .npcl(FacialExpression.FRIENDLY, "All you need to do is leave from the opposite door to where you came in by.")
            .npcl(FacialExpression.FRIENDLY, "I will warn you that this is more complicated than it may at first appear.")
            .npcl(FacialExpression.FRIENDLY, "I should also warn you that there are limited supplies of the items in this room, so think carefully before using them, you may find yourself stuck and have to leave to start again!")
            .npcl(FacialExpression.FRIENDLY, "Best of luck!").end()

        /*
            0 -> playerl(FacialExpression.FRIENDLY, "Please... I am REALLY stuck... Isn't there ANYTHING you can do to help me...?").also { stage = 1 }
            1 -> npcl(FacialExpression.FRIENDLY, "Well... Look, I really shouldn't say anything about this room, but...").also { stage = 2 }
            2 -> npcl(FacialExpression.FRIENDLY, "When I was attempting to join the Temple Knights I myself had to do this puzzle myself.").also { stage = 3 }
            3 -> npcl(FacialExpression.FRIENDLY, "It was slightly different, but the idea behind it was the same, and I left the notes I had made while doing it hidden in one of the bookcases.").also { stage = 4 }
            4 -> npcl(FacialExpression.FRIENDLY, "If you look carefully you may find them, and they may be of some use to you.").also { stage = 5 }
            5 -> npcl(FacialExpression.FRIENDLY, "I really can't be any more help than that I'm afraid, it is more than my job's worth to have given you the help I already have.").also { stage = 6 }
            6 -> playerl(FacialExpression.FRIENDLY, "Okay, thanks a lot, you've been very helpful!").also { stage = 7 }
            7 -> npcl(FacialExpression.FRIENDLY, "Best of luck with the test  ${player!!.username}. I hope your application is successful.").also { stage = 8 }
        */
    }
}
