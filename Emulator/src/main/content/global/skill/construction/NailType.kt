package content.global.skill.construction

import core.game.node.entity.player.Player
import core.tools.RandomFunction
import org.rs.consts.Items

/**
 * Represents different types of nails.
 */
enum class NailType(val itemId: Int, val bendRate: Int) {
    BRONZE(
        itemId = Items.BRONZE_NAILS_4819,
        bendRate = 5
    ),
    IRON(
        itemId = Items.IRON_NAILS_4820,
        bendRate = 7
    ),
    BLACK(
        itemId = Items.BLACK_NAILS_4821,
        bendRate = 8
    ),
    STEEL(
        itemId = Items.STEEL_NAILS_1539,
        bendRate = 10
    ),
    MITHRIL(
        itemId = Items.MITHRIL_NAILS_4822,
        bendRate = 13
    ),
    ADAMANT(
        itemId = Items.ADAMANTITE_NAILS_4823,
        bendRate = 15
    ),
    RUNE(
        itemId = Items.RUNE_NAILS_4824,
        bendRate = 20
    );

    /**
     * Checks if the nail bends based on its bend rate.
     */
    fun isBend(): Boolean = RandomFunction.getRandom(bendRate) == 0

    companion object {
        /**
         * Gets the nail type based on the player's inventory.
         *
         * @param player the player whose inventory is checked.
         * @param amount the required amount of nails.
         * @return the NailType if found, null otherwise.
         */
        @JvmStatic
        fun get(player: Player, amount: Int): NailType? {
            return values().reversed().find { player.inventory.contains(it.itemId, amount) }
        }
    }
}
