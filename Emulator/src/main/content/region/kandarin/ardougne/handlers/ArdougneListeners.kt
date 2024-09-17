package content.region.kandarin.ardougne.handlers

import content.global.travel.charter.Ship
import content.region.kandarin.quest.grail.dialogue.GalahadHolyGrailDialogueFile
import core.api.*
import org.rs.consts.Items
import org.rs.consts.NPCs
import content.region.kandarin.ardougne.dialogue.CaptainBarnabyDialogue
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.node.item.Item
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Ardougne listeners.
 */
class ArdougneListeners : InteractionListener {

    override fun defineListeners() {

        on(NPCs.GALAHAD_218, IntType.NPC, "talk-to") { player, _ ->
            /* TODO
             * If a player had completed the Holy Grail quest before talking to Galahad,
             * the player will automatically exclaim: I've returned the Holy grail to Camelot
             * and Galahad will congratulate the player. The same exchange could happen before
             * the achievements, as a player-chosen interaction option.
             */
            openDialogue(player, GalahadHolyGrailDialogueFile())
            return@on true
        }

        /*
         * Handling talk-to interaction with Captain Barnaby.
         */

        on(NPCs.CAPTAIN_BARNABY_4974, IntType.NPC, "talk-to") { player, npc ->
            openDialogue(player, CaptainBarnabyDialogue(), npc)
            return@on true
        }

        /*
         * Handling fast-travel.
         */

        on(NPCs.CAPTAIN_BARNABY_4974, IntType.NPC, "pay-fare") { player, _ ->
            if (!removeItem(player, Item(Items.COINS_995, 30))) {
                sendMessage(player, "You don't have enough coins.")
            } else {
                sendMessage(player, "You pay 30 coins and board the ship.")
                playJingle(player, 171)
                Ship.ARDOUGNE_TO_BRIMHAVEN.sail(player)
            }
            return@on true
        }

        /*
         * Handling using Ferret on Charlie in Ardougne zoo.
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
