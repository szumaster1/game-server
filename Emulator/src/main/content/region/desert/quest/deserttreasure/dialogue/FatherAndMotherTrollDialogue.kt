package content.region.desert.quest.deserttreasure.dialogue

import content.region.desert.quest.deserttreasure.DesertTreasure
import core.api.*
import org.rs.consts.Components
import org.rs.consts.Items
import org.rs.consts.NPCs
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.interaction.QueueStrength
import core.game.world.map.Location
import core.tools.END_DIALOGUE

/**
 * Represents the Father and mother troll dialogue.
 * @author Ovenbreado
 */
class FatherAndMotherTrollDialogue {
}

/**
 * Represents the Chat father and mother troll dialogue file.
 * @author Ovenbreado
 */
class ChatFatherAndMotherTrollDialogueFile : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        when (stage) {
            0 -> npcl(NPCs.TROLL_FATHER_1949, FacialExpression.OLD_CALM_TALK2, "Phew! Am I glad to be out of that big ice cube! Are you okay too darling?").also { stage++ }
            1 -> npcl(NPCs.TROLL_MOTHER_1950, FacialExpression.OLD_CALM_TALK2, "Yes, I thought we were done for! Why ever did that nasty Kamil freeze us up there anyway?").also { stage++ }
            2 -> playerl("He must have been trying to protect his Diamond...").also { stage++ }
            3 -> npcl(NPCs.TROLL_FATHER_1949, FacialExpression.OLD_CALM_TALK2, "You mean that diamond I found the other day belonged to him? But why didn't he just ask for it back? It's not like I really want it or anything!").also { stage++ }
            4 -> npcl(NPCs.TROLL_FATHER_1949, FacialExpression.OLD_CALM_TALK2, "And how did you know we had that diamond anyway, fleshy?").also { stage++ }
            5 -> playerl("Your son told me. That's why I rescued you, it is very important that I have that diamond...").also { stage++ }
            6 -> npcl(NPCs.TROLL_MOTHER_1950, FacialExpression.OLD_CALM_TALK2, "Ooohhhhh, my poor baby! He must have been so worried about us...").also { stage++ }
            7 -> npcl(NPCs.TROLL_FATHER_1949, FacialExpression.OLD_CALM_TALK2, "Yes, but he certainly inherited his Dad's smarts!").also { stage++ }
            8 -> npcl(NPCs.TROLL_FATHER_1949, FacialExpression.OLD_CALM_TALK2, "If he'd told this fleshy that he had the Diamond and not us, we might never have been rescued!").also { stage++ }
            9 -> playerl("Wait... what? That stupid little troll kid had the diamond all along?").also { stage++ }
            10 -> npcl(NPCs.TROLL_MOTHER_1950, FacialExpression.OLD_CALM_TALK2, "Don't you talk about my baby like that!").also { stage++ }
            11 -> npcl(NPCs.TROLL_FATHER_1949, FacialExpression.OLD_CALM_TALK2, "Now, now dear, all's well that ends well. We've been freed and this fleshy has certainly earned himself that diamond.").also { stage++ }
            12 -> npcl(NPCs.TROLL_FATHER_1949, FacialExpression.OLD_CALM_TALK2, "Let's get out of this terrible place and see our son!").also { stage++ }
            13 -> {
                queueScript(player!!, 0, QueueStrength.SOFT) { stage: Int ->
                    when (stage) {
                        0 -> {
                            closeOverlay(player!!)
                            openOverlay(player!!, Components.FADE_TO_BLACK_120)
                            return@queueScript delayScript(player!!, 6)
                        }

                        1 -> {
                            teleport(player!!, Location(2836, 3739, 0))
                            return@queueScript delayScript(player!!, 1)
                        }

                        2 -> {
                            openOverlay(player!!, Components.FADE_FROM_BLACK_170)
                            return@queueScript delayScript(player!!, 6)
                        }

                        3 -> {
                            closeOverlay(player!!)
                            openDialogue(player!!, ChatFatherAndMotherTrollAfterDialogueFile())
                            return@queueScript stopExecuting(player!!)
                        }

                        else -> return@queueScript stopExecuting(player!!)
                    }
                }
                end()
            }
        }
    }
}

/**
 * Represents the Chat father and mother troll after dialogue file.
 * @author Ovenbreado
 */
class ChatFatherAndMotherTrollAfterDialogueFile : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        when (stage) {
            0 -> npcl(NPCs.TROLL_CHILD_1933, FacialExpression.OLD_CALM_TALK2, "Mommy! Daddy! You're free!").also { stage++ }
            1 -> npc(NPCs.TROLL_FATHER_1949, FacialExpression.OLD_CALM_TALK1, "That's right son, and it's all thanks to this brave", "adventurer here.", "Now, make sure you hand over that diamond he was", "looking for.").also { stage++ }
            2 -> npcl(NPCs.TROLL_FATHER_1949, FacialExpression.OLD_CALM_TALK1, "It has been nothing but trouble for us, let's just get back to our cave and have dinner.").also { stage++ }
            3 -> npcl(NPCs.TROLL_MOTHER_1950, FacialExpression.OLD_CALM_TALK2, "That's right son, it's your favorite tonight too! A big plate of raw mackerel!").also { stage++ }
            4 -> npcl(NPCs.TROLL_CHILD_1933, FacialExpression.OLD_CALM_TALK2, "RAW MACKEREL! YUMMY!").also {
                stage++
                if (DesertTreasure.getSubStage(player!!, DesertTreasure.attributeIceStage) in 3..4) {
                    addItemOrDrop(player!!, Items.ICE_DIAMOND_4671)
                    DesertTreasure.setSubStage(player!!, DesertTreasure.attributeIceStage, 100)
                }
            }
            5 -> npcl(NPCs.TROLL_CHILD_1933, FacialExpression.OLD_CALM_TALK1, "Here ya go mister! Thanks for getting my mom and dad away from the bad man!").also { stage++ }
            6 -> playerl("Don't worry about it, just as long as I don't have to go back into that blizzard.").also { stage = END_DIALOGUE }
        }
    }
}