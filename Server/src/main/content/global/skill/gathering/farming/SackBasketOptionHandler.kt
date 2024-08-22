package content.global.skill.gathering.farming

import cfg.consts.Items
import core.cache.def.impl.ItemDefinition
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Sack basket option handler.
 */
@Initializable
class SackBasketOptionHandler : OptionHandler() {

    private companion object {
        val fruit = arrayOf(Items.ORANGE_2108,Items.COOKING_APPLE_1955,Items.BANANA_1963,Items.STRAWBERRY_5504,Items.TOMATO_1982)
        val produce = arrayOf(Items.POTATO_1942,Items.ONION_1957,Items.CABBAGE_1965)
    }

    /**
     * New instance
     *
     * @param arg
     * @return
     */
    override fun newInstance(arg: Any?): Plugin<Any> {
        BasketsAndSacks.values().forEach { it.containers.forEach { id ->
            val def = ItemDefinition.forId(id)
            def.handlers["option:fill"] = this
            def.handlers["option:empty"] = this
            def.handlers["option:remove-one"] = this
        } }
        var def = ItemDefinition.forId(Items.EMPTY_SACK_5418)
        def.handlers["option:fill"] = this
        def = ItemDefinition.forId(Items.BASKET_5376)
        def.handlers["option:fill"] = this
        return this
    }

    /**
     * Handle
     *
     * @param player
     * @param node
     * @param option
     * @return
     */
    override fun handle(player: Player?, node: Node?, option: String?): Boolean {
        player ?: return false
        node ?: return false

        when(option){
            "fill" -> tryFill(player,node.asItem())
            "empty" -> tryEmpty(player,node.asItem())
            "remove-one" -> tryTakeOne(player,node.asItem())
        }
        return true
    }

    /**
     * Try fill
     *
     * @param player
     * @param item
     */
    private fun tryFill(player: Player?, item: Item) {
        player ?: return
        val containerID = item.id
        val appropriateProduce = getAppropriateProduce(player,containerID) ?: return
        val container = BasketsAndSacks.forId(containerID) ?: BasketsAndSacks.forId(appropriateProduce.id) ?: return
        val isLast = container.checkIsLast(containerID)
        val specific = container.checkWhich(containerID)
        val max = container.containers.size - 1

        if(isLast){
            player.sendMessage("This is already full.")
            return
        }

        if(specific + appropriateProduce.amount > max){
            appropriateProduce.amount = (max - specific)
        }

        if(player.inventory.remove(item) && player.inventory.remove(appropriateProduce))
            player.inventory.add(Item(container.containers[specific + appropriateProduce.amount]))
    }

    /**
     * Try empty
     *
     * @param player
     * @param item
     */
    private fun tryEmpty(player: Player?, item: Item) {
        val container = BasketsAndSacks.forId(item.id)
        if(container == null) return
        player ?: return

        val emptyItem = if(produce.contains(container.produceID)) Items.EMPTY_SACK_5418 else Items.BASKET_5376
        val returnItem = Item(container.produceID,container.checkWhich(item.id) + 1)

        if(!player.inventory.hasSpaceFor(returnItem)){
            player.sendMessage("You don't have enough inventory space to do this.")
            return
        }

        if(player.inventory.remove(item)){
            player.inventory.add(Item(emptyItem))
            player.inventory.add(returnItem)
        }
    }

    /**
     * Try take one
     *
     * @param player
     * @param item
     */
    private fun tryTakeOne(player: Player?,item: Item) {
        val container = BasketsAndSacks.forId(item.id)
        if(container == null) return
        player ?: return

        val emptyItem = if(produce.contains(container.produceID)) Items.EMPTY_SACK_5418 else Items.BASKET_5376
        val isLast = container.checkIsFirst(item.id)
        val withdrawnItem = Item(container.produceID)

        if(!player.inventory.hasSpaceFor(withdrawnItem)){
            player.sendMessage("You don't have enough inventory space to do this.")
            return
        }

        if(player.inventory.remove(item)){
            if(isLast){
                player.inventory.add(Item(emptyItem))
            } else {
                val it = Item(container.containers[container.checkWhich(item.id) - 1])
                player.inventory.add(it)
            }
            player.inventory.add(withdrawnItem)
        }
    }

    /**
     * Get appropriate produce
     *
     * @param player
     * @param containerID
     * @return
     */
    private fun getAppropriateProduce(player: Player?, containerID: Int): Item? {
        player ?: return null
        val container = BasketsAndSacks.forId(containerID)
        val produce = if(container == null){
            var selected = 0
            if(containerID == Items.EMPTY_SACK_5418) {
                for (i in (produce)) {
                    if (player.inventory.contains(i, 1)) {
                        selected = i
                        break
                    }
                }
                selected
            } else {
                for (i in (fruit)) {
                    if (player.inventory.contains(i, 1)) {
                        selected = i
                        break
                    }
                }
                selected
            }
        } else {
            container.produceID
        }

        return if(produce == 0) null else Item(produce,player.inventory.getAmount(produce))
    }
}