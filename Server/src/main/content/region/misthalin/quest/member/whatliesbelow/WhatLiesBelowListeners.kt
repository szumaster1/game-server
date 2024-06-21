package content.region.misthalin.quest.member.whatliesbelow

import core.api.consts.Components
import core.api.consts.Items
import core.api.openInterface
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

class WhatLiesBelowListeners : InteractionListener {

    override fun defineListeners() {
        on(Items.ZAFFS_INSTRUCTIONS_11011, IntType.ITEM, "read") { player, _ ->
            openInterface(player, Components.ZAFFS_INSTRUCTIONS_251)
            return@on true
        }
    }
}