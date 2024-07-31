package content.region.karamja.dialogue.brimhaven

import core.api.consts.NPCs
import core.api.sendDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class GripDialogue(player: Player? = null): Dialogue(player) {

    /*
     * Grip is the head guard for Scarface Pete and resides in his mansion in Brimhaven.
     * During Heroes' Quest, he holds the key to the chest
     * containing the candlestick needed to trade for the master thief's armband.
     * Location: 2774,3197
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        sendDialogue(player, "He doesn't seem interested in talking to you.").also { stage = END_DIALOGUE }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GRIP_792)
    }
}
