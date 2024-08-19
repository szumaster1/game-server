package content.minigame.mta

import core.api.*
import core.api.consts.Components
import core.api.consts.Items
import core.game.component.CloseEvent
import core.game.component.Component
import core.game.container.Container
import core.game.container.ContainerType
import core.game.container.access.InterfaceContainer
import core.game.interaction.InterfaceListener
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser

/**
 * Represents the MTA shop interface listener.
 */
class MTAShopInterfaceListener : InterfaceListener {
    private val container = Container(ITEMS.size, ContainerType.SHOP)
    private val viewers: MutableList<Player> = ArrayList(100)
    private val component = Component(Components.MAGICTRAINING_SHOP_197).setCloseEvent(CloseEvent { player, c ->
        if (player == null) {
            return@CloseEvent true
        }
        viewers.remove(player)
        true
    })

    init {
        container.add(*ITEMS)
        Pulser.submit(object : Pulse(100) {
            override fun pulse(): Boolean {
                for (i in container.toArray().indices) {
                    val main = true
                    val item = container.toArray()[i] ?: continue
                    if (main) {
                        if (item.amount < 100) {
                            item.amount += 1
                        }
                        this@MTAShopInterfaceListener.update()
                    }
                }
                return false
            }
        })
    }

    /**
     * Open
     *
     * @param player The player who is opening the interface.
     */
    fun open(player: Player) {
        viewers.add(player)
        player.interfaceManager.open(component)
        update()
        updatePoints(player)
        Pulser.submit(object : Pulse(1, player) {
            override fun pulse(): Boolean {
                updatePoints(player)
                return true
            }
        })
    }

    private fun update() {
        for (p in viewers) {
            if (p == null || !p.isActive) {
                continue
            }
            InterfaceContainer.generateItems(p, container.toArray(), arrayOf("Buy", "Value"), 197, 16, 4, 7)
        }
    }

    /**
     * Buy
     *
     * @param player The player making the purchase.
     * @param item The item to be purchased.
     * @param slot The slot in which the item is located.
     */
    fun buy(player: Player, item: Item, slot: Int) {
        var item = item // Create a mutable reference to the item.
        val prices = PRICES[slot] // Retrieve the prices for the item based on the slot.
        if (item.amount < 1) { // Check if the item is out of stock.
            sendMessage(player, "The shop has ran out of stock.") // Notify the player.
            return // Exit the function.
        }
        item = Item(item.id, 1) // Create a new item instance with a quantity of 1.
        if (!player.inventory.hasSpaceFor(item)) { // Check if the player has space in their inventory.
            sendMessage(player, "You don't have enough inventory space.") // Notify the player.
            return // Exit the function.
        }
        for (i in prices.indices) { // Iterate through the prices.
            if (getPoints(player, i) < prices[i]) { // Check if the player can afford the item.
                sendMessage(player, "You cannot afford that item.") // Notify the player.
                return // Exit the function.
            }
        }
        if (item.id == Items.BONES_TO_PEACHES_6926 && player.getSavedData().activityData.isBonesToPeaches) { // Check if the player already unlocked the spell.
            sendMessage(player, "You already unlocked that spell.") // Notify the player.
            return // Exit the function.
        }
        var itemToRemove: ContainerisedItem? = null // Initialize a variable to hold an item to remove.
        if (slot in 1..3) { // Check if the slot is within the valid range.
            val required = ITEMS[slot - 1] // Get the required item for the upgrade.
            itemToRemove = hasAnItem(player, required.id) // Check if the player has the required item.
            if (!itemToRemove.exists() && !player.hasItem(Item(6914, 1))) { // Check if the player has the required wand.
                sendMessage(player, "You don't have the required wand in order to buy this upgrade.") // Notify the player.
                return // Exit the function.
            }
        }
        if (container.getAmount(item) - 1 <= 0) { // Check if the item amount in the container will drop to zero.
            container[slot].amount = 0 // Set the item amount in the container to zero.
        } else {
            container.remove(item) // Remove the item from the container.
        }
        if (item.id == Items.BONES_TO_PEACHES_6926) { // Check if the item is the Bones to Peaches spell.
            player.getSavedData().activityData.isBonesToPeaches = true // Mark the spell as unlocked for the player.
            sendDialogue(player, "The guardian teaches you how to use the Bones to Peaches spell!") // Notify the player.
        } else {
            if (itemToRemove == null || itemToRemove.remove()) player.inventory.add(item) // Add the item to the player's inventory if conditions are met.
        }
        for (i in prices.indices) { // Iterate through the prices again.
            decrementPoints(player, i, prices[i]) // Deduct the points from the player for each price.
        }
        updatePoints(player) // Update the player's points display after the purchase.
        update() // Refresh the interface to reflect changes.
    }

    /**
     * Value
     *
     * @param player The player requesting the item's value.
     * @param item The item whose value is being checked.
     * @param slot The slot in which the item is located.
     */
    fun value(player: Player, item: Item, slot: Int) {
        val prices = PRICES[slot]
        sendMessage(player, "The " + item.name + " costs " + prices[0] + " Telekinetic, " + prices[1] + " Alchemist,")
        sendMessage(player, prices[2].toString() + " Enchantment and " + prices[3] + " Graveyard Pizazz Points.")
    }

    /**
     * Update points
     *
     * @param player The player whose points are being updated.
     */
    fun updatePoints(player: Player) {
        setInterfaceText(player, getPoints(player, 0).toString(), Components.MAGICTRAINING_SHOP_197, 8)
        setInterfaceText(player, getPoints(player, 2).toString(), Components.MAGICTRAINING_SHOP_197, 9)
        setInterfaceText(player, getPoints(player, 1).toString(), Components.MAGICTRAINING_SHOP_197, 10)
        setInterfaceText(player, getPoints(player, 3).toString(), Components.MAGICTRAINING_SHOP_197, 11)
    }

    /**
     * Increment points
     *
     * @param player The player whose points are being incremented.
     * @param index The index of the points to increment.
     * @param increment The amount to increment the points by.
     */
    fun incrementPoints(player: Player, index: Int, increment: Int) {
        player.getSavedData().activityData.incrementPizazz(index, increment)
    }

    /**
     * Decrement points
     *
     * @param player The player whose points are being decremented.
     * @param index The index of the points to decrement.
     * @param decrement The amount to decrement the points by.
     */
    fun decrementPoints(player: Player, index: Int, decrement: Int) {
        player.getSavedData().activityData.decrementPizazz(index, decrement)
    }

    /**
     * Get points
     *
     * @param player The player whose points are being retrieved.
     * @param index The index of the points to retrieve.
     * @return The number of points the player has at the specified index.
     */
    fun getPoints(player: Player, index: Int): Int {
        return player.getSavedData().activityData.getPizazzPoints(index)
    }

    companion object {
        private val ITEMS = arrayOf(
            Item(Items.BEGINNER_WAND_6908, 100),
            Item(Items.APPRENTICE_WAND_6910, 100),
            Item(Items.TEACHER_WAND_6912, 100),
            Item(Items.MASTER_WAND_6914, 100),
            Item(Items.INFINITY_TOP_6916, 100),
            Item(Items.INFINITY_HAT_6918, 100),
            Item(Items.INFINITY_BOOTS_6920, 100),
            Item(Items.INFINITY_GLOVES_6922, 100),
            Item(Items.INFINITY_BOTTOMS_6924, 100),
            Item(Items.MAGES_BOOK_6889, 100),
            Item(Items.BONES_TO_PEACHES_6926, 100),
            Item(Items.MIST_RUNE_4695, 100),
            Item(Items.DUST_RUNE_4696, 100),
            Item(Items.SMOKE_RUNE_4697, 100),
            Item(Items.MUD_RUNE_4698, 100),
            Item(Items.STEAM_RUNE_4694, 100),
            Item(Items.LAVA_RUNE_4699, 100),
            Item(Items.COSMIC_RUNE_564, 100),
            Item(Items.CHAOS_RUNE_562, 100),
            Item(Items.NATURE_RUNE_561, 100),
            Item(Items.DEATH_RUNE_560, 100),
            Item(Items.LAW_RUNE_563, 100),
            Item(Items.SOUL_RUNE_566, 100),
            Item(Items.BLOOD_RUNE_565, 100)
        )
        private val PRICES = arrayOf(
            intArrayOf(30, 30, 300, 30),
            intArrayOf(60, 60, 600, 60),
            intArrayOf(150, 200, 1500, 150),
            intArrayOf(240, 240, 2400, 240),
            intArrayOf(400, 450, 4000, 400),
            intArrayOf(350, 400, 3000, 350),
            intArrayOf(120, 120, 1200, 120),
            intArrayOf(175, 225, 1500, 175),
            intArrayOf(450, 500, 5000, 450),
            intArrayOf(500, 550, 6000, 500),
            intArrayOf(200, 300, 2000, 200),
            intArrayOf(1, 1, 15, 1),
            intArrayOf(1, 1, 15, 1),
            intArrayOf(1, 1, 15, 1),
            intArrayOf(1, 1, 15, 1),
            intArrayOf(1, 1, 15, 1),
            intArrayOf(1, 1, 15, 1),
            intArrayOf(0, 0, 5, 0),
            intArrayOf(0, 1, 5, 1),
            intArrayOf(0, 1, 0, 1),
            intArrayOf(2, 1, 20, 1),
            intArrayOf(2, 0, 0, 0),
            intArrayOf(2, 2, 25, 2),
            intArrayOf(2, 2, 25, 2)
        )
    }

    override fun defineInterfaceListeners() {
        on(Components.MAGICTRAINING_SHOP_197) { player, _, opcode, _, slot, _ ->
            val item = container[slot] ?: return@on true
            if (opcode == 155) {
                value(player, item, slot)
            } else if (opcode == 196) {
                buy(player, item, slot)
            }
            return@on true
        }
    }
}