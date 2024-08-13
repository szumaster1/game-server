package content.minigame.gnomecook.cocktails

import core.api.consts.Items
import core.cache.def.impl.ItemDefinition
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import core.plugin.Plugin

private const val UNF_CHOC_SAT = 9573
private const val UNF_DRUN_DRA = 9575

/**
 * Cocktail finisher.
 */
@Initializable
class CocktailFinisher : OptionHandler() {
    override fun newInstance(arg: Any?): Plugin<Any> {
        ItemDefinition.forId(UNF_CHOC_SAT).handlers["option:add-ingreds"] = this
        ItemDefinition.forId(UNF_DRUN_DRA).handlers["option:add-ingreds"] = this
        return this
    }

    override fun handle(player: Player?, node: Node?, option: String?): Boolean {
        player ?: return false
        node ?: return false
        when (node.id) {
            UNF_CHOC_SAT -> attemptMake(FinishedDrinks.FIN_CHOC_SAT, player, node)
            UNF_DRUN_DRA -> attemptMake(FinishedDrinks.FIN_DRUN_DRA, player, node)
        }
        return true
    }

    private fun attemptMake(drink: FinishedDrinks, player: Player, node: Node) {
        var hasAll = true
        for (item in drink.requiredItems) {
            if (!player.inventory.containsItem(item)) {
                hasAll = false
                break
            }
        }

        if (!hasAll) {
            player.dialogueInterpreter.sendDialogue("You don't have the ingredients for this.")
            return
        }

        player.inventory.remove(*drink.requiredItems)
        player.inventory.remove(node.asItem())
        player.inventory.add(Item(drink.product))
    }

    /**
     * Enum representing finished drinks.
     *
     * @param product The product code of the finished drink.
     * @param requiredItems The array of items required to make the finished drink.
     * @constructor Creates a new FinishedDrinks enum instance.
     */
    internal enum class FinishedDrinks(val product: Int, val requiredItems: Array<Item>) {
        FIN_CHOC_SAT(2074, arrayOf(Item(Items.CHOCOLATE_DUST_1975), Item(Items.POT_OF_CREAM_2130))),
        FIN_DRUN_DRA(9576, arrayOf(Item(Items.PINEAPPLE_CHUNKS_2116), Item(Items.POT_OF_CREAM_2130)))
    }

}