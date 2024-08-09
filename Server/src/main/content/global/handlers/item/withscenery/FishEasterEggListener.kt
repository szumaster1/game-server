package content.global.handlers.item.withscenery

import content.global.skill.gathering.woodcutting.WoodcuttingNode
import core.api.consts.Items
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Fish easter egg listener.
 */
class FishEasterEggListener : InteractionListener {

    private val tree = WoodcuttingNode.values().map { it.id }.toIntArray()
    private val fish = intArrayOf(Items.RAW_HERRING_345, Items.HERRING_347)
    private val doors = intArrayOf(1967, 1968)

    override fun defineListeners() {
        onUseWith(IntType.SCENERY, fish, *tree) { player, _, _ ->
            sendMessage(player, "This is not the mightiest tree in the forest.")
            return@onUseWith true
        }

        onUseWith(IntType.SCENERY, fish, *doors) { player, _, _ ->
            sendMessage(player, "It can't be done!")
            return@onUseWith true
        }
    }
}
