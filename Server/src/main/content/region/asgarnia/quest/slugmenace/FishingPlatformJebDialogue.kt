package content.region.asgarnia.quest.slugmenace

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class FishingPlatformJebDialogue(player: Player? = null) : Dialogue(player) {
    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.FRIENDLY, "Hey, Jeb.").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc("Business here is complete. Do you wish to return to","the dry land?").also { stage++ }
            1 -> options("No, I'm going to stay a while.", "Okay, let's go back.").also { stage++ }
            2 -> when(buttonId){
                1 -> player("No, I'm going to stay a while.").also { stage++ }
                2 -> player("Okay, let's go back.").also { stage = 4 }
            }
            // Add after Slug Menace.
            3 -> npc("Then stay if that is your wish.").also { stage = END_DIALOGUE }
            4 -> npc("Then board the rowing boat.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.JEB_4896)
    }
}
