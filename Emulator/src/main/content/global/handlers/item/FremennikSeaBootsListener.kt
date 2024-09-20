package content.global.handlers.item

import content.global.travel.LyreTeleport
import core.ServerStore.Companion.getBoolean
import core.api.*
import org.rs.consts.Items
import org.rs.consts.NPCs
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player

val seaBoots =
    intArrayOf(Items.FREMENNIK_SEA_BOOTS_1_14571, Items.FREMENNIK_SEA_BOOTS_2_14572, Items.FREMENNIK_SEA_BOOTS_3_14573)
val enchantedLyre = intArrayOf(
    Items.ENCHANTED_LYRE1_3691,
    Items.ENCHANTED_LYRE2_6125,
    Items.ENCHANTED_LYRE3_6126,
    Items.ENCHANTED_LYRE4_6127,
    Items.ENCHANTED_LYRE5_14590,
    Items.ENCHANTED_LYRE6_14591
)

/**
 * Handles the fremennik sea boots option interactions.
 */
class FremennikSeaBootsListener : InteractionListener {


    override fun defineListeners() {
        on(seaBoots, IntType.ITEM, "operate") { player, _ ->
            openDialogue(player, FremennikSeaBootsDialogue())
            return@on true
        }
    }
}

class FremennikSeaBootsDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.FOSSEGRIMEN_1273)
        when (stage) {
            0 -> handleBootsOptions()
            1 -> handleBootsChoices(buttonID)
            4 -> proceedToOffering()
            5 -> askForOffering()
            6 -> handleOffering(buttonID)
            7, 10, 11, 12, 15, 16, 17, 18 -> showBootsBenefits(buttonID)
            8 -> confirmLyreTeleport()
            9 -> executeLyreTeleport(buttonID)
            13 -> chooseLyreDestination(buttonID)
            14 -> confirmDestinationChange(buttonID)
        }
    }

    private fun handleBootsOptions() {
        val options = when {
            inEquipmentOrInventory(player!!, Items.FREMENNIK_SEA_BOOTS_1_14571) -> arrayOf(
                "Contact the Fossegrimen.",
                "Explain benefits.",
                "Cancel."
            )

            inEquipmentOrInventory(player!!, Items.FREMENNIK_SEA_BOOTS_2_14572) -> arrayOf(
                "Contact the Fossegrimen.",
                "Free lyre teleport.",
                "Explain benefits.",
                "Cancel."
            )

            inEquipmentOrInventory(player!!, Items.FREMENNIK_SEA_BOOTS_3_14573) -> arrayOf(
                "Contact the Fossegrimen.",
                "Free lyre teleport.",
                "Lyre teleport destination.",
                "Explain benefits.",
                "Cancel."
            )

            else -> return
        }
        sendDialogueOptions(player!!, "What would you like to do?", *options)
        stage++
    }

    private fun handleBootsChoices(buttonID: Int) {
        when (buttonID) {
            1 -> {
                npc(FacialExpression.FRIENDLY, "Good day, ${player!!.username}. Do you want to make an offering?")
                stage = 4
            }

            2 -> handleBenefitsExplanation()
            3 -> end()
        }
    }

    private fun handleBenefitsExplanation() {
        val benefits = when {
            inEquipmentOrInventory(player!!, Items.FREMENNIK_SEA_BOOTS_1_14571) -> Items.FREMENNIK_SEA_BOOTS_1_14571
            inEquipmentOrInventory(player!!, Items.FREMENNIK_SEA_BOOTS_2_14572) -> Items.ENCHANTED_LYRE1_3691
            inEquipmentOrInventory(player!!, Items.FREMENNIK_SEA_BOOTS_3_14573) -> Items.FREMENNIK_SEA_BOOTS_3_14573
            else -> return
        }
        sendItemDialogue(player!!, benefits, "Your Fremennik sea boots will bring you certain benefits within the Fremennik area.")
        stage++
    }

    private fun proceedToOffering() {
        npc(FacialExpression.NEUTRAL, "Remember that you will gain a greater enchantment from offerings if you bring them to my altar.")
        stage++
    }

    private fun askForOffering() {
        setTitle(player!!, 2)
        val option = if (inInventory(player!!, Items.RAW_BASS_363)) "A raw bass." else "A raw shark."
        sendDialogueOptions(player!!, "What do you offer?", option, "Nothing at the moment.")
        stage++
    }

    private fun handleOffering(buttonID: Int) {
        when (buttonID) {
            1 -> processOffering()
            2 -> end()
        }
    }

    private fun processOffering() {
        if (inInventory(player!!, Items.RAW_BASS_363) && isWearingCharosRing()) {
            npc(FacialExpression.HALF_GUILTY, "A raw bass? You should know that is not a worthy offering, outlander.")
        } else if (hasEnchantedLyre()) {
            sendDialogue(player!!, "All lyre charges must be used up before it will allow a charge to the lyre.")
        } else if (canEnchantLyre()) {
            npc(FacialExpression.FRIENDLY, "I offer you this enchantment for your worthy offering.")
            addItemOrDrop(player!!, Items.ENCHANTED_LYRE1_3691, 1)
        } else {
            sendDialogue(player!!, "You don't have the required items in your inventory.")
        }
        stage = 0
    }

    private fun isWearingCharosRing() =
        inEquipmentOrInventory(player!!, Items.RING_OF_CHAROSA_6465) || inEquipmentOrInventory(player!!, Items.RING_OF_CHAROS_4202)

    private fun hasEnchantedLyre() = hasAnItem(player!!, *enchantedLyre).container == player!!.inventory

    private fun canEnchantLyre() = inInventory(player!!, Items.RAW_SHARK_383) && hasAnItem(
        player!!,
        Items.ENCHANTED_LYRE_3690
    ).container == player!!.inventory

    private fun showBootsBenefits(buttonID: Int) {
        val messages = when (stage) {
            7 -> arrayOf("If you speak to Peer the Seer, he will deposit items into your bank.", "The Fossegrimen's enchantment will give your lyre extra charges, if you make her an offering in person.")
            10 -> arrayOf("As Regent of Miscellania, the people will appreciate your efforts more and your approval rating will increase faster.", "There is also a broken section of pier on Miscellania that you can use to quickly travel between there and Etceteria.")
            11 -> arrayOf("Advisor Ghrim will accept flat-packed furniture as a contribution to the coffers of Miscellania.")
            else -> emptyArray()
        }
        sendItemDialogue(player!!, Items.FREMENNIK_SEA_BOOTS_2_14572, messages.joinToString("<br>"))
        stage = 0
    }

    private fun confirmLyreTeleport() {
        setTitle(player!!, 2)
        sendDialogueOptions(player!!, "Do this now?", "Yes.", "No.")
        stage = 9
    }

    private fun executeLyreTeleport(buttonID: Int) {
        when (buttonID) {
            1 -> if (LyreTeleport.getLyreTeleportFile().getBoolean(player!!.username.lowercase())) {
                sendDialogue(player!!, "This can only be done once per day.")
            } else {
                LyreTeleport.teleport(player!!)
                end()
            }

            2 -> end()
        }
        stage = 0
    }

    private fun chooseLyreDestination(buttonID: Int) {
        val options = arrayOf("Rellekka", "Waterbirth Island", "Don't change.")
        sendDialogueOptions(player!!, "Choose a destination:", *options)
        stage = 14
    }

    private fun confirmDestinationChange(buttonID: Int) {
        val message =
            "Remember that you must be wearing your Fremennik sea boots if you want to teleport to an alternative location."
        when (buttonID) {
            1 -> {
                interpreter!!.sendItemMessage(Items.FREMENNIK_SEA_BOOTS_3_14573, message)
                removeAttribute(player!!, LyreTeleport.LYRE_TELEPORT_ALT)
            }

            2 -> {
                interpreter!!.sendItemMessage(Items.FREMENNIK_SEA_BOOTS_3_14573, message)
                setAttribute(player!!, LyreTeleport.LYRE_TELEPORT_ALT, true)
            }

            3 -> end()
        }
        stage = 0
    }
}
