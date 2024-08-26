package content.region.kandarin.handlers.miniquest.knightwave

import cfg.consts.NPCs
import core.api.*
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.skill.Skills
import core.tools.RED

/**
 * Represents the Merlin dialogue.
 */
class MerlinDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.MERLIN_213)
        when (stage) {
            0 -> npc(
                FacialExpression.HAPPY,
                "Well done, young adventurer. You truly are a worthy",
                "knight."
            ).also { stage++ }
            1 -> {
                player!!.unlock()
                sendDialogueLines(
                    player!!,
                    "You receive access to the prayers Chivalry and Piety and 20,0000",
                    "XP in your Hitpoints, Strength, Attack, and Defence skills. You may",
                    "also speak to Merlin to set your respawn point to Camelot."
                ).also {
                    if (!getAttribute(player!!, KnightWave.KW_COMPLETE, false)) {
                        sendMessage(player!!, RED + "This miniquest can only be done once.")
                    } else {
                        rewardXP(player!!, Skills.HITPOINTS, 20.000)
                        rewardXP(player!!, Skills.ATTACK, 20.000)
                        rewardXP(player!!, Skills.STRENGTH, 20.000)
                        rewardXP(player!!, Skills.DEFENCE, 20.000)
                    }
                }
            }
        }
    }
}