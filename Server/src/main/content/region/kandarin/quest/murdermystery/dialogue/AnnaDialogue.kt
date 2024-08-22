package content.region.kandarin.quest.murdermystery.dialogue

import core.api.*
import cfg.consts.Items
import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Anna dialogue.
 */
@Initializable
class AnnaDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (!isQuestComplete(player, "Murder Mystery")) {
            playerl(FacialExpression.FRIENDLY, "I'm here to help the guards with their investigation.").also { stage++ }
        } else {
            sendMessage(player!!, "She is ignoring you.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun handle(componentID: Int, buttonID: Int): Boolean {
        val questStage = getQuestStage(player!!, "Murder Mystery")
        when (questStage) {
            in 1..3 -> when (stage) {
                0 -> npcl(FacialExpression.ANNOYED, "Oh really? What do you want to know then?").also { stage = 2 }
                2 -> if (getQuestStage(player, "Murder Mystery") == 3) {
                    options("Who do you think is responsible?", "Where were you when the murder happened?", "Do you recognise this thread?", "Why'd you buy poison the other day?").also { stage++ }
                } else {
                    options("Who do you think is responsible?", "Where were you when the murder happened?", "Do you recognise this thread?").also { stage++ }
                }

                3 -> if (getQuestStage(player, "Murder Mystery") == 3) {
                    when (buttonID) {
                        1 -> playerl(FacialExpression.SUSPICIOUS, "Who do you think is responsible?").also { stage++ }
                        2 -> playerl(FacialExpression.SUSPICIOUS, "Where were you when the murder happened?").also { stage = 7 }
                        3 -> playerl(FacialExpression.SUSPICIOUS, "Do you recognise this thread?").also { stage = 8 }
                    }
                } else {
                    when (buttonID) {
                        1 -> playerl(FacialExpression.SUSPICIOUS, "Who do you think is responsible?").also { stage++ }
                        2 -> playerl(FacialExpression.SUSPICIOUS, "Where were you when the murder happened?").also { stage = 7 }
                        3 -> playerl(FacialExpression.SUSPICIOUS, "Do you recognise this thread?").also { stage = 8 }
                        4 -> playerl(FacialExpression.SUSPICIOUS, "Why'd you buy poison the other day?").also { stage = 10 }
                    }
                }

                4 -> npcl(FacialExpression.ANNOYED, "It was clearly an intruder.").also { stage++ }
                5 -> playerl(FacialExpression.SUSPICIOUS, "Well, I don't think it was.").also { stage++ }
                6 -> npcl(FacialExpression.ANNOYED, "It was one of our lazy servants then.").also { stage = END_DIALOGUE }
                7 -> npcl(FacialExpression.ANNOYED, "In the library. No one else was there so you'll just have to take my word for it.").also { stage = END_DIALOGUE }
                8 -> {
                    if (inInventory(player!!, Items.CRIMINALS_THREAD_1809)) {
                        sendItemDialogue(player!!, Items.CRIMINALS_THREAD_1809, "You show Anna the thread from the study.").also { stage++ }
                    } else {
                        npcl(FacialExpression.ANNOYED, "Not really, no. Thread is fairly common.").also { stage = END_DIALOGUE }
                    }
                }
                9 -> npcl(FacialExpression.ANNOYED, "It's some Green thread. It's not exactly uncommon is it? My trousers are made of the same material.").also { stage = END_DIALOGUE }
                10 -> npcl(FacialExpression.NEUTRAL, "That useless Gardener Stanford has let his compost heap fester. It's an eyesore to the garden! So I bought some poison from a travelling salesman so that I could kill off some of the wildlife living in it.").also { stage++ }
                11 -> {
                    end()
                    setQuestStage(player!!, "Murder Mystery", 4)
                }
            }

            100 -> when (stage) {
                0 -> npcl(FacialExpression.FRIENDLY, "Apparently you aren't as stupid as you look.").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ANNA_814)
    }
}
