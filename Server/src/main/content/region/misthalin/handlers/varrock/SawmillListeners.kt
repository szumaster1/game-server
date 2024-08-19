package content.region.misthalin.handlers.varrock

import content.region.misthalin.dialogue.varrock.SawmillOperatorDialogue
import core.api.*
import core.api.consts.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.world.map.Location

/**
 * Represents the Sawmill listeners.
 */
class SawmillListeners : InteractionListener {

    companion object {
        // Constant representing the Sawmill Operator NPC identifier
        private const val SAWMILL_OPERATOR = NPCs.SAWMILL_OPERATOR_4250
    }

    override fun defineListeners() {

        /**
         * Sawmill operator NPC options.
         * Handles the "talk-to" interaction with the Sawmill Operator.
         */
        on(SAWMILL_OPERATOR, IntType.NPC, "talk-to") { player, _ ->
            // Opens the dialogue with the Sawmill Operator for the player
            openDialogue(player, SawmillOperatorDialogue())
            return@on true
        }

        /**
         * Sawmill operator NPC options.
         * Handles the "buy-plank" interaction with the Sawmill Operator.
         */
        on(SAWMILL_OPERATOR, IntType.NPC, "buy-plank") { player, _ ->
            // Opens the interface for purchasing planks at the Sawmill
            openInterface(player, Components.POH_SAWMILL_403)
            return@on true
        }

        /**
         * Sawmill operator NPC options.
         * Handles the "trade" interaction with the Sawmill Operator.
         */
        on(SAWMILL_OPERATOR, IntType.NPC, "trade") { player, _ ->
            // Opens the shop interface for trading with the Sawmill Operator
            openNpcShop(player, SAWMILL_OPERATOR)
            return@on true
        }

        /**
         * Sets the destination for the Sawmill Operator interactions.
         * This defines where the player will be directed when interacting with the Sawmill Operator.
         */
        setDest(IntType.NPC, intArrayOf(SAWMILL_OPERATOR), "talk-to", "buy-plank", "trade") { _, _ ->
            // Returns the location coordinates for the Sawmill Operator
            return@setDest Location.create(3302, 3491, 0)
        }
    }

}