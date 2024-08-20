package content.region.misthalin.handlers.stronghold.playersafety

import core.ServerConstants
import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.dialogue.Topic
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.emote.Emotes
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Represents the Professor henry dialogue.
 */
@Initializable
class ProfessorHenryDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> {
                if (player.savedData.globalData.getTestStage() == 2 && inInventory(player, Items.TEST_PAPER_12626)) {
                    player("Hello, Professor.").also { stage = HAND_IN_TEST }
                } else if (player.savedData.globalData.getTestStage() >= 3) {
                    npcl(FacialExpression.HAPPY, "Good job ${player.name}, you completed the test!").also { stage = END_DIALOGUE }
                    return true
                } else {
                    player(FacialExpression.NEUTRAL, "Hello.").also { stage = MEETING }
                }
            }
            MEETING -> npcl(FacialExpression.ANNOYED, "Hello what?").also { stage++ }
            MEETING + 1 -> playerl(FacialExpression.HALF_GUILTY, "Uh...hello there?").also { stage++ }
            MEETING + 2 -> npcl(FacialExpression.HALF_GUILTY, "Hello, 'Professor'. Manners cost nothing, you know. " + "When you're in my classroom, I ask that you use the proper address for my station.").also { stage++ }
            MEETING + 3 -> playerl(FacialExpression.HALF_GUILTY, "Your station?").also { stage++ }
            MEETING + 4 -> npcl(FacialExpression.HALF_GUILTY, "Yes. It means 'position', so to speak.").also { stage++ }
            MEETING + 5 -> playerl(FacialExpression.HALF_GUILTY, "Oh, okay.").also { stage++ }
            MEETING + 6 -> npcl(FacialExpression.HALF_GUILTY, "Now, what can I do for you, exactly?").also { stage++ }
            MEETING + 7 -> playerl(FacialExpression.HALF_GUILTY, "What is this place?").also { stage++ }
            MEETING + 8 -> npcl(FacialExpression.HALF_GUILTY, "This is the Misthalin Training Centre of Excellence. " + "It is where bold adventurers, such as yourself, can come to learn of the dangers of " + "the wide world and gain some valuable experience at the same time.").also { stage++ }
            MEETING + 9 -> playerl(FacialExpression.HALF_GUILTY, "What can I do here?").also { stage++ }
            MEETING + 10 -> npcl(FacialExpression.HALF_GUILTY, "Here you can take part in the Player Safety test: " + "a set of valuable lessons to learn about staying safe " + "in ${ServerConstants.SERVER_NAME}.").also { stage++ }
            MEETING + 11 -> npcl(FacialExpression.HALF_GUILTY, "I can give you a test paper to take and, once completed, " + "you can bring it back to me for marking. Would you like to take the test? " + "It will not cost you anything.").also { stage++ }
            MEETING + 12 -> showTopics(Topic("Yes, please.", GET_TEST), Topic("Not right now, thanks.", END_DIALOGUE))
            GET_TEST -> {
                if (freeSlots(player) == 0) {
                    npcl(FacialExpression.HALF_GUILTY, "It seems your inventory is full.").also { stage = END_DIALOGUE }
                } else if (amountInInventory(player, Items.TEST_PAPER_12626) > 0) {
                    npcl(FacialExpression.HALF_GUILTY, "You already have a test, please fill it out and return it to me.").also { stage = END_DIALOGUE }
                } else {
                    player.savedData.globalData.setTestStage(1)
                    addItem(player, Items.TEST_PAPER_12626)
                    npcl(FacialExpression.HALF_GUILTY, "Right then. Here is the test paper. " + "When you have completed all the questions, bring it back to me for marking.").also { stage++ }
                }
            }

            GET_TEST + 1 -> playerl(FacialExpression.HALF_GUILTY, "Okay, thanks.").also { stage = END_DIALOGUE }
            HAND_IN_TEST -> npcl(FacialExpression.HAPPY, "Ah, ${player.name}. How's the test going?").also { stage++ }
            HAND_IN_TEST + 1 -> playerl(FacialExpression.NEUTRAL, "I think I've finished.").also { stage++ }
            HAND_IN_TEST + 2 -> npcl(FacialExpression.HAPPY, "Excellent! Let me just mark the paper for you then.").also { stage++ }
            HAND_IN_TEST + 3 -> npcl(FacialExpression.HAPPY, "Hmm. Uh-huh, yes I see. Good! Yes, that's right.").also { stage++ }
            HAND_IN_TEST + 4 -> npcl(FacialExpression.HAPPY, "Excellent! Allow me to reward you for your work. " + "I have these two old lamps that you may find useful.").also { stage++ }
            HAND_IN_TEST + 5 -> npc("Also, there is an old jail block connected to the cells", "below the training centre, which have been overrun with", "vicious creatures. If you search around the jail cells", "downstairs, you should find it easily enough.").also { stage++ }
            HAND_IN_TEST + 6 -> npcl(FacialExpression.HAPPY, "Now, your rewards.").also { stage++ }
            HAND_IN_TEST + 7 -> {
                if (freeSlots(player) >= 1) {
                    showReward().also { stage = END_DIALOGUE }
                } else {
                    npcl(FacialExpression.SAD, "You do not have space in your inventory for the rewards").also { stage = END_DIALOGUE }
                }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.PROFESSOR_HENRY_7143)
    }

    private fun showReward() {
        /*
         * Show the poster tunnel.
         */

        setVarp(player, 1203, 1 shl 29, true)
        player.savedData.globalData.setTestStage(3)

        removeItem(player, Items.TEST_PAPER_12626)

        addItem(player, Items.ANTIQUE_LAMP_4447, 2)
        player.emoteManager.unlock(Emotes.SAFETY_FIRST)

        openInterface(player, INTERFACE)

        /*
         * Clear the other lines.
         */
        for (i in 9..18) {
            setInterfaceText(player, "", INTERFACE, i)
        }
        setInterfaceText(player, "You have completed the Player Safety test!", INTERFACE, 4)
        setInterfaceText(player, getQuestPoints(player).toString(), INTERFACE, 7)
        setInterfaceText(player, "2 Experience lamps", INTERFACE, 9)
        setInterfaceText(player, "Access to the Stronghold of", INTERFACE, 10)
        setInterfaceText(player, "Player Safety Dungeon", INTERFACE, 11)
        setInterfaceText(player, "The Safety First' emote", INTERFACE, 12)
        sendItemZoomOnInterface(player, INTERFACE, 5, Items.TEST_PAPER_12626)
    }

    companion object {
        const val HAND_IN_TEST = 10
        const val MEETING = 100
        const val GET_TEST = 200
        const val INTERFACE = 277
    }

}
