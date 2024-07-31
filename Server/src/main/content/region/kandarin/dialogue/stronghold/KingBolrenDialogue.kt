package content.region.kandarin.dialogue.stronghold

import content.region.kandarin.quest.treegnomevillage.dialogue.KingBolrenDialogueFile
import core.api.addItemOrDrop
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.hasAnItem
import core.api.isQuestComplete
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class KingBolrenDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (isQuestComplete(player, "Tree Gnome Village")) {
            playerl(FacialExpression.FRIENDLY, "Hello again Bolren.")
        } else {
            end()
            openDialogue(player, KingBolrenDialogueFile())
        }
        return true
    }

    override fun handle(componentID: Int, buttonID: Int): Boolean {
        var hasAnGnomeAmulet = hasAnItem(player, Items.GNOME_AMULET_589).container != null
        when (stage) {
            0 -> if (!hasAnGnomeAmulet) {
                npc(FacialExpression.OLD_NORMAL, "Well hello, it's good to see you again.").also { stage++ }
            } else {
                npcl(FacialExpression.OLD_NORMAL, "Thank you for your help traveler.").also { stage = END_DIALOGUE }
            }
            1 -> player(FacialExpression.SAD, "I've lost my amulet.").also { stage++ }
            2 -> npc(FacialExpression.OLD_NORMAL, "Oh dear. Here, take another. We are truly indebted", "to you.").also { stage++ }
            3 -> {
                end()
                addItemOrDrop(player, Items.GNOME_AMULET_589)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.KING_BOLREN_469)
    }
}
