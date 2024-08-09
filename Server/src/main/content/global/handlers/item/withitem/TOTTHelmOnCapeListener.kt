package content.global.handlers.item.withitem

import core.api.*
import core.api.consts.Items
import core.game.dialogue.DialogueFile
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.tools.END_DIALOGUE

/**
 * TOTTHelm on cape listener.
 */
class TOTTHelmOnCapeListener : InteractionListener {

    private val slayerHelmet = Items.SLAYER_HELMET_13263
    private val slayerCape = Items.SLAYER_CAPE_9786
    private val slayerCapeT = Items.SLAYER_CAPET_9787

    override fun defineListeners() {

        /*
         * Combining interaction  Slayer helmet + Slayer skillcape.
         */

        onUseWith(IntType.ITEM, slayerHelmet, slayerCape, slayerCapeT) { player, _, _ ->
            val alreadyHasHelm = getAttribute(player, "cape_perks:tott:helmet-stored", false)
            if (alreadyHasHelm) {
                sendDialogue(player, "You've already stored the components of a helmet in this cape.")
            } else {
                openDialogue(player, object : DialogueFile() {
                    override fun handle(componentID: Int, buttonID: Int) {
                        when (stage) {
                            0 -> sendDialogue(player, "This will destroy your helmet permanently.").also { stage++ }
                            1 -> sendDialogueOptions(player, "Continue?", "Yes", "No").also { stage++ }
                            2 -> when (buttonID) {
                                1 -> {
                                    if (removeItem(player, slayerHelmet, Container.INVENTORY)) {
                                        dialogue(*splitLines("You disassemble the helmet and place the components into their respective pockets on your cape."))
                                        setAttribute(player, "/save:cape_perks:tott:helmet-stored", true)
                                        stage = END_DIALOGUE
                                    }
                                }

                                2 -> end()
                            }
                        }
                    }
                })
            }

            return@onUseWith true
        }
    }
}
