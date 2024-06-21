package content.region.fremennik.dialogue.waterbrith

import core.api.addItemOrDrop
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.getAttribute
import core.api.removeItem
import core.api.sendDialogueOptions
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC

class BardurExchangeDialogue : DialogueFile() {
    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.BARDUR_2879)
        when (stage) {
            0 -> npcl(
                FacialExpression.FRIENDLY,
                "Ah, just what I was looking for! You wish to trade me that for a cooked shark?"
            ).also { stage++ }

            1 -> sendDialogueOptions(player!!, "Trade with Bardur?", "YES", "NO").also { stage++ }
            2 -> when (buttonID) {
                1 -> if (removeItem(player!!, Items.FREMENNIK_HELM_3748) || removeItem(player!!, Items.FREMENNIK_BLADE_3757) || removeItem(player!!, Items.FREMENNIK_SHIELD_3758)) {
                    end()
                    addItemOrDrop(player!!, Items.SHARK_385)
                    npcl(FacialExpression.FRIENDLY, "Ah, this will serve me well as a backup! My thanks to you ${getAttribute(player!!, "fremennikname", "name")}, I trust we will one day sing songs together of glorious battles in the Rellekka longhall!")
                }

                2 -> {
                    end()
                    npcl(FacialExpression.FRIENDLY, "As you wish, ${getAttribute(player!!, "fremennikname", "name")}. My weapons have lasted me this long, I will keep my trust in them yet.")
                }
            }
        }
    }
}