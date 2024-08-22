package content.region.morytania.dialogue.phasmatys

import cfg.consts.Items
import cfg.consts.NPCs
import content.region.morytania.handlers.phasmatys.EnergyBarrierUtils
import core.api.*
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Ghost guard dialogue.
 */
@Initializable
class GhostGuardDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Info: Two guards that watch over the entrance-portals
     * allowing access into Port Phasmatys.
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (isQuestComplete(player, "Ghosts Ahoy") && inEquipment(player, Items.GHOSTSPEAK_AMULET_552) && inEquipment(player, Items.BEDSHEET_4285)) {
            sendDialogue(player, "You can't pass the barriers of Port Phasmatys while wearing the bedsheet.")
        } else if (isQuestComplete(player, "Ghosts Ahoy") && inEquipment(player, Items.GHOSTSPEAK_AMULET_552) && !inEquipment(player, Items.BEDSHEET_4285)) {
            findLocalNPC(player, 1706)?.let { face(player, it, 1) }
            npcl(FacialExpression.FRIENDLY, "All visitors to Port Phasmatys must pay a toll charge of 2 Ectotokens. However, you have done the ghosts of our town a service that surpasses all value, so you may pass without charge.")
            stage = 3
        } else if (!inEquipment(player, Items.GHOSTSPEAK_AMULET_552)) {
            sendDialogue(player, "The ghostly guards wail at you incomprehensibly, and though you cannot understand their exact words you understand their meaning - you may not pass the barriers of Port Phasmatys.")
            stage = END_DIALOGUE
        } else {
            npcl(FacialExpression.FRIENDLY, "All visitors to Port Phasmatys must pay a toll charge of 2 Ectotokens.")
            stage = 1
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            1 -> if (amountInInventory(player, Items.ECTO_TOKEN_4278) >= 2) {
                options("I would like to enter Port Phasmatys - here's 2 Ectotokens.", "I'm not paying you Ectotokens just to go through a gate.", "Where can I get Ectotokens?").also { stage++ }
            } else {
                options("I don't have that many Ectotokens.", "Where can I get Ectotokens?").also { stage = 6 }
            }
            2 -> when (buttonId) {
                1 -> player("I would like to enter Port Phasmatys - here's", "2 Ectotokens.").also { stage = 3 }
                2 -> player("I'm not paying you Ectotockens just to go through a gate.").also { stage = 4 }
                3 -> player("Where can I get Ectotokens?").also { stage = 5 }
            }
            3 -> {
                end()
                EnergyBarrierUtils.passGateByTalk(player, npc)
            }
            4 -> npc("Sorry - it's Town Policy.").also { stage = END_DIALOGUE }
            5 -> npc("You need to go to the Temple and earn some.", "Talk to the disciples - they will tell you how.").also { stage = END_DIALOGUE }
            6 -> when (buttonId) {
                1 -> player("I don't have that many Ectotokens.").also { stage = 4 }
                2 -> player("Where can I get Ectotokens?").also { stage = 5 }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GHOST_GUARD_1706)
    }

}
