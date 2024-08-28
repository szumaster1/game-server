package content.activity.oldman

import content.global.handlers.iface.ScrollInterface
import cfg.consts.Components
import cfg.consts.Items
import cfg.consts.NPCs
import core.api.getAttribute
import core.api.openInterface
import core.api.sendNPCDialogue
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Represents the Wise old man listeners.
 */
class WomListener : InteractionListener {

    override fun defineListeners() {
        // Listener for using items with the Wise Old Man NPC
        onUseWith(IntType.NPC, 0, WISE_OLD_MAN) { player, used, _ ->
            if (used.id != 0) {
                sendNPCDialogue(player, WISE_OLD_MAN, "Humph! You could at least say hello before waving your items in my face.", FacialExpression.HALF_GUILTY)
            }
            return@onUseWith true
        }

        // Listener for reading the old man's message
        on(OLD_MAN_MESSAGE, IntType.ITEM, "read") { player, node ->
            val letterContent = getAttribute(player, WomDeliveryLetter.LETTER_DELIVERY_ATTRIBUTE, -1)
            openInterface(player, Components.MESSAGESCROLL_220).also {
                ScrollInterface.scrollSetup(player, Components.MESSAGESCROLL_220, WomDeliveryLetter.THE_ORACLE_LETTER_CONTENT)
            }
            /* Commented out code for future reference
             * LetterDeliveryContent.getLetterContent(player, x)
             * val letterContent = getAttribute(player, LetterDeliveryContent.LETTER_DELIVERY_ATTRIBUTE, -1)
             *
             * if(getAttribute(player, OldManUtils.LETTER_DELIVERY,false)) {
             *       LetterDeliveryContent.getLetterContent(player, letterContent)
             *       setAttribute(player, OldManUtils.TASK_START, true)
             *   }
             */
            return@on true
        }
    }

    companion object {
        const val WISE_OLD_MAN = NPCs.WISE_OLD_MAN_2253
        const val OLD_MAN_MESSAGE = Items.OLD_MANS_MESSAGE_5506
    }
}
