package content.minigame.puropuro

import content.global.skill.hunter.bnet.BNetTypes
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Elnock exchange
 *
 * @param button Represents the button associated with the exchange.
 * @param configValue Holds the configuration value for the exchange.
 * @param sendItem Indicates the item that will be sent during the exchange.
 * @param reward Represents the reward item received from the exchange.
 * @param required A variable number of items that are required for the exchange.
 */
enum class ElnockExchange(val button: Int, val configValue: Int, val sendItem: Int, val reward: Item, vararg required: Item) {
    /**
     * Imp Repellant.
     */
    IMP_REPELLANT(23, 444928, 11271, Item(11262), Item(11238, 3), Item(11240, 2), Item(11242)),

    /**
     * Magic Butterfly.
     */
    MAGIC_BUTTERFLY(26, 707072, 11268, Item(11259), Item(11242, 3), Item(11244, 2), Item(11246)),

    /**
     * Jar Generator.
     */
    JAR_GENERATOR(29, 969216, 11267, Item(11258), Item(11246, 3), Item(11248, 2), Item(11250)),

    /**
     * Impling Jar.
     */
    IMPLING_JAR(32, 1231360, 11269, Item(11260, 3)) {
        override fun hasItems(player: Player): Boolean {
            for (node in BNetTypes.getImplings()) {
                if (player.inventory.containsItem(node.reward)) {
                    return true
                }
            }
            return false
        }
    };

    val required: Array<Item>

    init {
        this.required = required as Array<Item>
    }

    /**
     * Has items.
     */
    open fun hasItems(player: Player): Boolean {
        return player.inventory.containsItems(*required)
    }

    companion object {
        @JvmStatic
        fun getItem(player: Player): Item? {
            for (node in BNetTypes.getImplings()) {
                if (player.inventory.containsItem(node.reward)) {
                    return node.reward
                }
            }
            return null
        }
        @JvmStatic
        fun forButton(button: Int): ElnockExchange? {
            for (e in ElnockExchange.values()) {
                if (e.button == button) {
                    return e
                }
            }
            return null
        }
    }
}