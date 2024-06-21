package content.region.kandarin.quest.hazeelcult.dialogue

import content.region.kandarin.quest.hazeelcult.HazeelCultListeners
import core.api.consts.NPCs
import core.api.getAttribute
import core.api.getQuestStage
import core.api.setQuestStage
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class AlomoneDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (getQuestStage(player, "Hazeel Cult") >= 2) {
            npcl(FacialExpression.FRIENDLY, "How did YOU get in here?").also { stage++ }
        } else if (getAttribute(player, HazeelCultListeners.MAHJARRAT, true) && !getAttribute(player, HazeelCultListeners.CARNILLEAN, true)
        ) {
            playerl(FacialExpression.FRIENDLY, "Hello, Alomone.").also { stage = 1 }
        } else {
            playerl(FacialExpression.FRIENDLY, "Hello again.").also { stage = 2 }
        }
        return true
    }

    override fun handle(componentID: Int, buttonID: Int): Boolean {
        when (getQuestStage(player!!, "Hazeel Cult")) {
            2 -> when (stage) {
                1 -> playerl(FacialExpression.FRIENDLY, "I've come for the Carnillean family armour. Hand it over, or face the consequences.").also { stage++ }
                2 -> npcl(FacialExpression.FRIENDLY, "I thought I made it clear to the butler you could not be allowed to interfere with our mission. The incompetent fool must be going soft.").also { stage++ }
                3 -> playerl(FacialExpression.FRIENDLY, "So, the butler's part of your sordid little cult, huh? Why is it ALWAYS butler? I should have known...").also { stage++ }
                4 -> npcl(FacialExpression.ANNOYED, "Well, you won't live long enough to tell anyone! Prepare to DIE!!!").also { stage++ }
                5 -> {
                    end()
                    setQuestStage(player, "Hazeel Cult", 3)
                    npc!!.attack(player!!)
                }
            }

            100 -> when (stage) {
                // Sided with Hazeel
                1 -> npcl(FacialExpression.NEUTRAL, "Welcome, adventurer. Know that as a friend to Hazeel, you are always welcome here.").also { stage = END_DIALOGUE }
                // Sided with Carnillean
                2 -> npcl(FacialExpression.ANNOYED, "You have crossed my path too many times intruder. Leave or face my wrath.").also { stage++ }
                3 -> playerl(FacialExpression.FRIENDLY, "Yeah, whatever.").also { stage = END_DIALOGUE }

            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ALOMONE_891)
    }
}
