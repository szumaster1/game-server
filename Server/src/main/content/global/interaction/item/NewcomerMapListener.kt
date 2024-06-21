package content.global.interaction.item

import core.api.consts.Items
import core.api.openInterface
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

class NewcomerMapListener : InteractionListener {

    private val newcomerMap = Items.NEWCOMER_MAP_550
    private val newcomerMapContent = 270

    override fun defineListeners() {
        on(newcomerMap, IntType.ITEM, "read") { player, node ->
            openInterface(player, newcomerMapContent)
            return@on true
        }
    }

}