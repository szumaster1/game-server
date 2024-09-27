package core.game.bots

import core.game.node.item.Item
import core.game.world.map.Location

/**
 * SkillingBotAssembler is responsible for producing and assembling AI Players with specific equipment sets based on their wealth.
 */
class SkillingBotAssembler {
    /**
     * Produces an AIPlayer with resources reflective of their specified wealth.
     *
     * @param type The wealth category of the AIPlayer (POOR, AVERAGE, RICH).
     * @param loc The location where the AIPlayer will be assembled.
     * @return A newly assembled AIPlayer configured based on the specified wealth type.
     */
    fun produce(type: Wealth, loc: Location): AIPlayer {
        return assembleBot(AIPlayer(loc), type)
    }

    /**
     * Assembles an AI player with a set of equipment based on the given wealth type.
     *
     * @param bot The AI player to equip.
     * @param type The wealth type to determine the equipment set.
     * @return The equipped AI player.
     */
    fun assembleBot(bot: AIPlayer, type: Wealth): AIPlayer {
        return when (type) {
            Wealth.POOR -> equipSet(bot, POORSETS.random())
            Wealth.AVERAGE -> equipSet(bot, AVGSETS.random())
            Wealth.RICH -> equipSet(bot, RICHSETS.random())
        }
    }

    /**
     * Equips a set of items on the given AIPlayer bot.
     *
     * @param bot The AIPlayer that will be equipped with the items.
     * @param set A list of item IDs to equip on the AIPlayer.
     * @return The AIPlayer after equipping the provided set of items.
     */
    fun equipSet(bot: AIPlayer, set: List<Int>): AIPlayer {
        for (i in set) {
            val item = Item(i)
            val configs = item.definition.handlers
            val slot = configs["equipment_slot"] ?: continue
            if (bot.inventory.get(slot as Int) == null) {
                bot.equipment.add(item, slot, false, false)
            }
            val reqs = configs["requirements"]
            if (reqs != null)
                for (req in configs["requirements"] as HashMap<Int, Int>)
                    bot.skills.setStaticLevel(req.key, req.value)
        }
        bot.skills.updateCombatLevel()
        return bot
    }

    /**
     * Enum class representing different levels of wealth.
     */
    enum class Wealth {
        /**
         * The POOR class represents an entity with limited resources or capabilities.
         *
         * This class serves as a basic representation of an object that is constrained by
         * minimal attributes. It might be used for illustrating simple functionalities
         * or representing basic objects within a certain context where one wishes to
         * highlight simplicity or lack of richness in properties.
         *
         * It is not intended for use in complex scenarios or for objects requiring
         * extensive methods and attributes.
         *
         * Please note:
         * - The POOR class may be extended for more specific use-cases.
         * - This class might be used in examples where minimalistic object representation is desired.
         */
        POOR,

        /**
         * The AVERAGE class provides methods to calculate the average
         * (arithmetic mean) of a list of numerical values.
         *
         * Primary functionalities include:
         * - Calculation of mean for a given list of integers or floating-point numbers.
         * - Handling of edge cases such as empty lists and lists with a single element.
         *
         * It is designed to work with both positive and negative numbers and can
         * process large datasets efficiently.
         *
         * Ideal for use in statistical and numerical analysis applications.
         */
        AVERAGE,

        /**
         * RICH class represents a rich text structure with multiple styling options.
         * It allows for handling, storing, and manipulating diverse formats of text data.
         *
         * Key operations include:
         * - Adding, removing, and updating text with various styles.
         * - Supporting multiple text segments with different formats.
         * - Combining and splitting text segments.
         *
         * Use this class to manage complex text data efficiently.
         *
         * Available properties:
         * @property textSegments list of text segments with applied styles
         * @property defaultStyle default style applied to the text
         * @property length the total length of the text
         *
         * Available methods:
         * @method addTextSegment Adds a new text segment with a specific style.
         * @method removeTextSegment Removes a text segment from the list.
         * @method updateStyle Updates the style of a specific text segment.
         * @method getTextSegment Retrieves a text segment by its index.
         * @method combineSegments Combines multiple text segments into one.
         * @method splitSegment Splits a text segment into two at a specified index.
         * @method clear Removes all text segments and styles.
         */
        RICH
    }

    /**
     * A predefined array of lists representing different equipment sets for bots categorized as "POOR".
     * Each inner list contains item IDs signifying specific equipment pieces that a bot can be equipped with.
     * These sets are utilized in the bot assembly process to randomly assign an equipment set to bots
     * with a "POOR" wealth type.
     */
    val POORSETS = arrayOf(
        listOf(542, 544),
        listOf(581),
        listOf(6654, 6655, 6656),
        listOf(6654, 6656),
        listOf(636, 646),
        listOf(638, 648),
        listOf(),
        listOf(),
        listOf()
    )
    /**
     * AVGSETS is a constant array of lists containing integer values.
     * Each list represents a unique set of numbers that are grouped together logically.
     *
     * The array is structured such that:
     * - Each inner list contains a set of integer values.
     * - These sets are used for logical groupings within the application.
     *
     * This constant is typically used where predefined sets of integers are required for computations or configurations.
     */
    val AVGSETS = arrayOf(
        listOf(2649, 342, 344),
        listOf(2651, 542, 544),
        listOf(6654, 6655, 6656),
        listOf(6139, 6141),
        listOf(9923, 9924, 9925),
        listOf(10400, 10402, 2649),
        listOf(10404, 10406),
        listOf(12971, 12978)
    )
    /**
     * Array of lists containing integer values representing various item sets.
     *
     * Each inner list represents a grouping of related integers.
     * The significance or meaning of these integers is context-dependent
     * and should be understood in conjunction with the application logic.
     */
    val RICHSETS = arrayOf(
        listOf(10330, 10332, 2649),
        listOf(12873, 12880, 1046),
        listOf(13858, 13861, 13864),
        listOf(13887, 13893),
        listOf(3481, 3483),
        listOf(2653, 2655),
        listOf(2661, 2663),
        listOf(2591, 2593),
        listOf(14490, 14492)
    )
}
