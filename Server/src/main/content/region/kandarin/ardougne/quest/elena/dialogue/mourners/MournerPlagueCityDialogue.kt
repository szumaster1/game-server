package content.region.kandarin.ardougne.quest.elena.dialogue.mourners

import core.api.*
import cfg.consts.NPCs
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.global.action.DoorActionHandler
import core.game.node.entity.npc.NPC
import core.game.world.map.RegionManager.getObject
import core.tools.END_DIALOGUE

/**
 * Represents the Mourner plague city dialogue.
 */
class MournerPlagueCityDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.MOURNER_3216)
        when (getQuestStage(player!!, "Plague City")) {

            in 0..5 -> when (stage) {
                1 -> npcl(FacialExpression.NEUTRAL, "What are you up to with old man Edmond?").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "Nothing, we've just been chatting.").also { stage++ }
                3 -> npcl(FacialExpression.NEUTRAL, "What about his daughter?").also { stage++ }
                4 -> playerl(FacialExpression.FRIENDLY, "you know about that then?").also { stage++ }
                5 -> npcl(FacialExpression.NEUTRAL, "We know about everything that goes on in Ardougne. We have to if we are to contain the plague.").also { stage++ }
                6 -> playerl(FacialExpression.FRIENDLY, "Have you see his daughter recently?").also { stage++ }
                7 -> npcl(FacialExpression.NEUTRAL, "I imagine she's caught the plague. Either way she won't be allowed out of West Ardougne, the risk is too great.").also { stage = END_DIALOGUE }
            }

            6 -> when (stage) {
                0 -> playerl(FacialExpression.FRIENDLY, "Hello there.").also { stage++ }
                1 -> npcl(FacialExpression.NEUTRAL, "Been digging have we?").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "What do you mean?").also { stage++ }
                3 -> npcl(FacialExpression.NEUTRAL, "Your hands are covered in mud.").also { stage++ }
                4 -> playerl(FacialExpression.FRIENDLY, "Oh that...").also { stage++ }
                5 -> npcl(FacialExpression.NEUTRAL, "Funny, you don't look like the gardening type.").also { stage++ }
                6 -> playerl(FacialExpression.FRIENDLY, "Oh no, I love gardening! It's my favorite pastime.").also { stage = END_DIALOGUE }
            }

            7 -> when (stage) {
                0 -> playerl(FacialExpression.FRIENDLY, "Hello there.").also { stage++ }
                1 -> npcl(FacialExpression.NEUTRAL, "What are you up to?").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "What do you mean?").also { stage++ }
                3 -> npcl(FacialExpression.NEUTRAL, "You and that Edmond fella, you're looking very suspicious.").also { stage++ }
                4 -> playerl(FacialExpression.FRIENDLY, "We're just gardening. Have you heard any news about West Ardougne?").also { stage++ }
                5 -> npcl(FacialExpression.NEUTRAL, "Just the usual, everyone's sick or dying. I'm furious at King Tyras for bringing this plague to our lands.").also { stage = END_DIALOGUE }
            }

            8 -> when (stage) {
                0 -> playerl(FacialExpression.FRIENDLY, "Hello there.").also { stage++ }
                1 -> npcl(FacialExpression.NEUTRAL, "Do you have a problem traveller?").also { stage++ }
                2 -> playerl(FacialExpression.NEUTRAL, "No, I just wondered why you're wearing that outfit... Is it fancy dress?").also { stage++ }
                3 -> npcl(FacialExpression.NEUTRAL, "No! It's for protection.").also { stage++ }
                4 -> playerl(FacialExpression.NEUTRAL, "Protection from what?").also { stage++ }
                5 -> npcl(FacialExpression.FRIENDLY, "The plague of course...").also { stage = END_DIALOGUE }
            }

            in 9..15 -> when (stage) {
                0 -> playerl(FacialExpression.FRIENDLY, "Hello there.").also { stage++ }
                1 -> npcl(FacialExpression.NEUTRAL, "Can I help you?").also { stage++ }
                2 -> playerl(FacialExpression.NEUTRAL, "What are you doing?").also { stage++ }
                3 -> npcl(FacialExpression.NEUTRAL, "I'm guarding the border to West Ardougne. No-one except we mourners can pass through.").also { stage++ }
                4 -> playerl(FacialExpression.NEUTRAL, "Why?").also { stage++ }
                5 -> npcl(FacialExpression.FRIENDLY, "The plague of course. We can't risk cross contamination.").also { stage++ }
                6 -> playerl(FacialExpression.FRIENDLY, "Ok then, see you around.").also { stage++ }
                7 -> npcl(FacialExpression.FRIENDLY, "Maybe...").also { stage = END_DIALOGUE }
            }

            16 -> when (stage) {
                0 -> if (inBorders(player!!, 2532, 3272, 2534, 3273)) {
                    sendDialogueLines(player!!, "The door won't open.", "You notice a black cross on the door.").also { stage = END_DIALOGUE }
                } else {
                    playerl(FacialExpression.FRIENDLY, "I have a warrant from Bravek to enter here.").also { stage++ }
                }

                1 -> npcl(FacialExpression.ANNOYED, "This is highly irregular. Please wait...").also { stage++ }
                2 -> {
                    end()
                    stage = END_DIALOGUE
                    lock(player!!, 6)
                    lockInteractions(player!!, 6)
                    runTask(player!!, 2) {
                        findLocalNPC(player!!, NPCs.MOURNER_717)!!.sendChat("Hay... I got someone here with a warrant from Bravek, what should we do?")
                        findLocalNPC(player!!, NPCs.MOURNER_717)!!.faceLocation(location(2536, 3273, 0))
                        runTask(player!!, 1) {
                            findLocalNPC(player!!, NPCs.MOURNER_3216)!!.sendChat("Well you can't let them in...", 1)
                            findLocalNPC(player!!, NPCs.MOURNER_3216)!!.faceLocation(location(2537, 3273, 0))
                            runTask(player!!, 1) {
                                DoorActionHandler.handleAutowalkDoor(player!!, getObject(location(2540, 3273, 0))!!.asScenery())
                                runTask(player!!, 1) {
                                    setQuestStage(player!!, "Plague City", 17)
                                    sendDialogueLines(player!!, "You wait until the mourner's back is turned and sneak into the", "building.")
                                }
                            }
                        }
                    }
                }
            }

            in 17..100 -> when (stage) {
                0 -> playerl(FacialExpression.FRIENDLY, "Hello there.").also { stage++ }
                1 -> npcl(FacialExpression.FRIENDLY, "I'd stand away from there. That black cross means that house has been touched by the plague.").also { stage = END_DIALOGUE }
            }
        }
    }
}
