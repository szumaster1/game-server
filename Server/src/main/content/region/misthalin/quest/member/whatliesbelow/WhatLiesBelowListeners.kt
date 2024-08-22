package content.region.misthalin.quest.member.whatliesbelow

import cfg.consts.Components
import cfg.consts.Items
import core.api.openInterface
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Represents the What lies below listeners.
 */
class WhatLiesBelowListeners : InteractionListener {

    override fun defineListeners() {

        /*
         * Handle read the Zaff instruction.
         */

        on(Items.ZAFFS_INSTRUCTIONS_11011, IntType.ITEM, "read") { player, _ ->
            openInterface(player, Components.ZAFFS_INSTRUCTIONS_251)
            return@on true
        }
    }
}