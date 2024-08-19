package content.region.fremennik.handlers.miniquest.generalsshadow

import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Shadow Realm Ghost dialogue.
 */
@Initializable
class ShadowRealmGhostDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        if (!inEquipment(player, Items.GHOSTSPEAK_AMULET_552)) {
            npc("Whoooo wooo Whooooooooo.").also { stage = END_DIALOGUE }
        } else if (getAttribute(player, GSUtils.GS_COMPLETE, false)) {
            playerl(FacialExpression.HALF_GUILTY, "I fought a really big ghost dog in here - is he gone now?").also { stage = 0 }
        } else {
            sendDialogue(player, "Ghost seems too busy to talk.")
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc("Yes, he is gone from this place.", "You did a great thing removing him.").also { stage++ }
            1 -> {
                if (hasAnItem(player, Items.SHADOW_SWORD_10858).container != null) {
                    npc("You were well rewarded for you effort.", "But you should leave this place, for it is not wise to tarry.").also { stage = END_DIALOGUE }
                } else {
                    playerl(FacialExpression.HALF_GUILTY, "But I lost my shadow sword and I don't know how to get it back.").also { stage++ }
                }
            }
            2 -> npc(FacialExpression.NEUTRAL, "Ah, so that is why you return. I can assist you in that", "and give you another.").also { stage++ }
            3 -> {
                end()
                addItemOrDrop(player, Items.SHADOW_SWORD_10858)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GHOST_5572)
    }

}
