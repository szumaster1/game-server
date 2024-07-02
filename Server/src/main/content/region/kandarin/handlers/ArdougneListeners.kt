package content.region.kandarin.handlers

import content.global.travel.ship.Ships
import content.region.kandarin.dialogue.ardougne.CaptainBarnabyDialogue
import content.region.kandarin.quest.holygrail.dialogue.GalahadHolyGrailDialogueFile
import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.node.item.Item
import core.utilities.END_DIALOGUE
import core.utilities.START_DIALOGUE

class ArdougneListeners : InteractionListener {

    override fun defineListeners() {

        on(NPCs.GALAHAD_218, IntType.NPC, "talk-to") { player, _ ->
            openDialogue(player, GalahadHolyGrailDialogueFile())
            return@on true
        }

        on(NPCs.CAPTAIN_BARNABY_4974, IntType.NPC, "talk-to") { player, npc ->
            openDialogue(player, CaptainBarnabyDialogue(), npc)
            return@on true
        }

        on(NPCs.CAPTAIN_BARNABY_4974, IntType.NPC, "pay-fare") { player, _ ->
            if (!removeItem(player, Item(Items.COINS_995, 30))) {
                sendMessage(player, "You don't have enough coins.")
            } else {
                sendMessage(player, "You pay 30 coins and board the ship.")
                playJingle(player, 171)
                Ships.ARDOUGNE_TO_BRIMHAVEN.sail(player)
            }
            return@on true
        }

        /*
         * Use ferret on Charlie interaction in Ardougne zoo.
         */

        onUseWith(IntType.NPC, Items.FERRET_10092, NPCs.CHARLIE_5138) { player, _, _ ->
            if (!hasRequirement(player, "Eagles' Peak")) return@onUseWith true
            openDialogue(player, object : DialogueFile() {
                override fun handle(componentID: Int, buttonID: Int) {
                    npc = NPC(NPCs.CHARLIE_5138)
                    when (stage) {
                        START_DIALOGUE -> playerl(FacialExpression.FRIENDLY, "Hey, I've got another ferret if you're interested?").also { stage++ }
                        1 -> npcl(FacialExpression.NEUTRAL, "Er, oh! Well that's very kind of you, but we don't really need another ferret at the moment, ").also { stage++ }
                        2 -> npcl(FacialExpression.HALF_GUILTY, "I'm afraid. We're having enough trouble taming the one we've got.").also { stage = END_DIALOGUE }
                    }
                }
            })
            return@onUseWith true
        }
    }
}
