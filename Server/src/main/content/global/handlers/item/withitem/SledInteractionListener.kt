package content.global.handlers.item.withitem

import core.api.*
import core.api.consts.Components
import core.api.consts.Items
import core.api.consts.Sounds
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.link.emote.Emotes
import core.game.node.item.Item
import core.game.world.update.flag.context.Animation

/**
 * Sled interaction listener.
 */
class SledInteractionListener : InteractionListener {

    private val SLED = Items.SLED_4084
    private val SLED_EMOTE_BUTTONS = intArrayOf(22, 10, 9, 7)
    private val AVAILABLE_EMOTES = Emotes.values().map { it.buttonId }.toIntArray()

    override fun defineListeners() {

        /*
         * Creating The wax.
         */

        onUseWith(IntType.ITEM, Items.SWAMP_TAR_1939, Items.BUCKET_OF_WAX_30) { player, used, with ->
            if (!inInventory(player, Items.CAKE_TIN_1887)) {
                sendMessage(player, "Nothing interesting happens.")
            }
            if (removeItem(player, used.asItem()) && removeItem(player, Items.CAKE_TIN_1887) && removeItem(player, with.asItem())) {
                sendMessage(player, "You made some sled wax.")
                addItemOrDrop(player, Items.WAX_4085)
            }
            return@onUseWith true
        }

        /*
         * Used wax with sled to create waxed sled.
         */

        onUseWith(IntType.ITEM, Items.WAX_4085, Items.SLED_4083) { player, used, with ->
            lock(player, 6)
            lockInteractions(player, 6)
            if (removeItem(player, used.asItem()) && removeItem(player, with.asItem())) {
                sendMessage(player, "You wax the sled. You're now ready to go.")
                animate(player, 1470)
                playAudio(player, Sounds.WAX_SLED_1871)
                addItem(player, Items.CAKE_TIN_1887)
                addItemOrDrop(player, SLED)
            }
            return@onUseWith true
        }

        /*
         * "Ride sled" interaction.
         */

        on(SLED, IntType.ITEM, "ride") { player, _ ->
            removeItem(player, SLED)
            player.animate(Animation.create(1461))
            player.equipment.replace(Item(4084), 3)
            if (player.equipment.contains(Items.SLED_4084, 1) && player.interfaceManager.opened.id == Components.EMOTES_464) {
                for (i in AVAILABLE_EMOTES.indices) {
                    if (!SLED_EMOTE_BUTTONS.contentEquals(AVAILABLE_EMOTES)) {
                        sendMessage(player, "You can't do that on a sled.")
                        return@on true
                    }
                }
            }
            return@on true
        }

        /*
         * Equip sled interaction.
         */

        onEquip(SLED) { player, _ ->
            lock(player, 2)
            lockInteractions(player, 2)
            playAudio(player, Sounds.EQUIP_SLED_1864)
            animate(player, 1461)
            return@onEquip true
        }

        /*
         * Un-equip sled interaction.
         */

        onUnequip(SLED) { _, _ ->
            return@onUnequip true
        }
    }

}
