package content.region.kandarin.feldip.gutanoth.dialogue

import cfg.consts.NPCs
import content.region.kandarin.feldip.gutanoth.handlers.BogrogPlugin
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player

/**
 * Represents the Bogrog dialogue.
 */
class BogrogDialogue(player: Player? = null) : Dialogue(player) {

    override fun newInstance(player: Player): Dialogue {
        return BogrogDialogue(player)
    }

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.CHILD_NORMAL, "Hey, yooman, what you wanting?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Can I buy some summoning supplies?", "Are you interested in buying pouch pouches or scrolls?").also { stage++ }
            1 -> when (buttonId) {
                1 -> player("Can I buy some summoning supplies?").also { stage++ }
                2 -> player("Are you interested in buying pouch pouches or scrolls?").also { stage = 4 }
            }
            2 -> npc(FacialExpression.CHILD_NORMAL, "Hur, hur, hur! Yooman's gotta buy lotsa stuff if yooman", "wants ta train good!").also { stage++ }
            3 -> end().also { openNpcShop(player, npc.id )}
            4 -> npc(FacialExpression.CHILD_NORMAL, "Des other ogre's stealin' Bogrog's stock. Gimmie pouches", "and scrolls and yooman gets da shardies.").also { stage++ }
            5 -> player("Ok.").also { stage++ }
            6 -> end().also { BogrogPlugin.openSwap(player) }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BOGROG_4472)
    }
}