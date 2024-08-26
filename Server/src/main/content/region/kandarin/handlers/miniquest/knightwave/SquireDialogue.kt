package content.region.kandarin.handlers.miniquest.knightwave

import cfg.consts.NPCs
import core.api.getAttribute
import core.api.hasRequirement
import core.api.setAttribute
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Squire dialogue.
 */
@Initializable
class SquireDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (!hasRequirement(player, "King's Ransom")) return true
        when {
            getAttribute(player, KnightWave.KW_BEGIN, false) -> npc("Good day, my lord. Is there anything I can do for you?").also { stage = 14 }
            getAttribute(player, KnightWave.KW_COMPLETE, false) -> npc("Congratulations on succeeding in the Knight Waves, ${if (!player.isMale) "my lady" else "my lord"}.").also { stage = 100 }
            else -> npc("Greetings, brave knight!")
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> player(FacialExpression.THINKING, "Hello. What's through that door?").also { stage++ }
            1 -> npc(FacialExpression.HAPPY, "That door leads to the training ground for the Knights", "of the Round Table.").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "Can I go in there?").also { stage++ }
            3 -> npc("Of course, my lord. But I must tell you the rules first.", "Would you like to hear them now?").also { stage++ }
            4 -> options("Yes please.", "No, thank you.").also { stage++ }
            5 -> when (buttonId) {
                1 -> player("Yes please.").also { stage++ }
                2 -> player("No, thank you.").also { stage = END_DIALOGUE }
            }
            6 -> npc("The training ground is a melee combat area only.", "Merlin has cast a spell over the grounds to prevent", "anyone using Prayer, Ranged or Magic.").also { stage++ }
            7 -> npc("Upon entering the grounds, you will fight each of the", "Round Table Knights in turn. Each knight is more", "skilled than the last, out all have different attack styles", "that can be exploited. There are eight knights in all, and").also { stage++ }
            8 -> npc("each will do damage not only to your hitpoints, but also", "your combat stats. If you should die in the grounds", "Merlin will teleport you back here. You will not lose", "any of your items.").also { stage++ }
            9 -> npc("If you feel you cannot complete the battle, you may", "exit the training grounds. However, if you exit or die,", "you will have to start over with the first knight the next", "time you enter.").also { stage++ }

            10 -> npc("If you are a great warrior and defeat Sir Lancelot,", "mightiest of all knights, you will receive great rewards.", "Two new prayers will become available to you and your", "respawn point will be set to Camelot, as well as 20,000").also { stage++ }
            11 -> npc("XP in to each of your combat stats.").also { stage++ }
            12 -> npc("If you do not want your respawn point set to Camelot,", "Merlin can change it for you.").also { stage++ }
            13 -> npc("Would you like me to repeat any of that for you?").also {
                setAttribute(player, KnightWave.KW_BEGIN, true)
                stage++
            }
            14 -> options("Tell me about the training ground.", "Tell me about dying or leaving the grounds.", "Tell me about the rewards.", "No, thank you.").also { stage++ }
            15 -> when (buttonId) {
                1 -> player("Tell me about the training ground.").also { stage++ }
                2 -> player("Tell me about dying or leaving the grounds.").also { stage = 20 }
                3 -> player("Tell me about the rewards.").also { stage = 21 }
                4 -> player("No, thank you.").also { stage = END_DIALOGUE } // [MISSING]
            }
            16 -> npc("Only melee combat is allowed within the training", "grounds. Merlin will prevent the use of Prayer,", "Ranged and Magic.").also { stage++ }
            17 -> player("Why?").also { stage++ }
            18 -> npc("These grounds are for only the best knights of the", "land. It is not just testing your combat ability, but your", "honour and your chivalry. You must face your enemy", "head on, honourably and bravely.").also { stage++ }
            19 -> npc("Be watchful of your stats, as the Knights can do", "damage to your combat stats as well as your hitpoints.").also { stage = END_DIALOGUE }

            20 -> end() // [MISSING]
            21 -> end() // [MISSING]

            100 -> player(FacialExpression.HALF_ASKING, "Thank you. Can I go back inside?").also { stage++ }
            101 -> npcl(FacialExpression.NEUTRAL, "I'm afraid not. Once you have won, you cannot re-enter.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SQUIRE_6169)
    }

}