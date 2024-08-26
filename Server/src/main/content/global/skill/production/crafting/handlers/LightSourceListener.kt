package content.global.skill.production.crafting.handlers

import content.global.skill.production.crafting.data.LightSource
import core.api.log
import core.game.container.Container
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item
import core.tools.Log

/**
 * Light source listener.
 */
class LightSourceListener : InteractionListener {

    override fun defineListeners() {

        fun Container.replace(item: Item, with: Item) {
            if (remove(item)) {
                add(with)
            }
        }

        on(IntType.ITEM, "extinguish") { player, node ->
            val lightSource = LightSource.forId(node.id)

            lightSource ?: return@on false.also {
                log(this::class.java, Log.WARN, "UNHANDLED EXTINGUISH OPTION: ID = ${node.id}")
            }

            player.inventory.replace(node.asItem(), Item(lightSource.fullId))
            return@on true
        }

    }

}
