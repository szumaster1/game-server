package content.minigame.barbassault.dialogue

import core.api.consts.Components
import core.api.consts.NPCs
import core.api.openInterface
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class CaptainCainDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        playerl(FacialExpression.FRIENDLY, "Hello. What's this place?").also { stage = 1 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            1 -> npcl(FacialExpression.FRIENDLY, "Why you wretched Son-of-a-Big-Nosed-Warty-Smelly-Goblin! How dare you speak to a commanding officer like that?").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "I...err... What?").also { stage++ }
            3 -> npcl(FacialExpression.FRIENDLY, "Stand straight! Chest out, shoulders back!").also { stage++ }
            4 -> playerl(FacialExpression.FRIENDLY, "Okay, okay.").also { stage++ }
            5 -> npcl(FacialExpression.FRIENDLY, "You'll address me as captain!").also { stage++ }
            6 -> playerl(FacialExpression.FRIENDLY, "Yea...yes, captain!").also { stage++ }
            7 -> npcl(FacialExpression.FRIENDLY, "Better. Now what is a low-life, pathetic excuse for a human being doing sniffing around my arena?").also { stage++ }
            8 -> playerl(FacialExpression.FRIENDLY, "Exploring, captain. Looked like a chance for combat, sir!").also { stage++ }
            9 -> npcl(FacialExpression.FRIENDLY, "And you think you have what is necessary to take on the Penance? No chance.").also { stage++ }
            10 -> playerl(FacialExpression.FRIENDLY, "With respect, captain, I don't understand what this place is.").also { stage++ }
            11 -> npcl(FacialExpression.FRIENDLY, "You really are useless, aren't you? Very well, make the most of this because I don't suffer idiots easily! This is the Barbarian Assault arena. You want to sign up with us?").also { stage++ }
            12 -> options("Sir, yes sir!", "Nah, sounds boring.").also { stage++ }
            13 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "Sir, yes sir!").also { stage++ }
                2 -> {
                    end(); stage = END_DIALOGUE
                }
            }

            14 -> npcl(FacialExpression.FRIENDLY, "Then listen closely.").also { stage++ }
            15 -> openInterface(player!!, Components.BARBASSAULT_TUTORIAL_496).also { end(); stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.CAPTAIN_CAIN_5030)
    }

}
