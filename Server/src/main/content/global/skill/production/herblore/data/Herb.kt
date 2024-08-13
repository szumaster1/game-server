package content.global.skill.production.herblore.data

import core.api.consts.Items
import core.game.node.item.Item
/**
 * Herb
 *
 * @param herb Represents the item associated with the herb.
 * @param experience The experience points gained from using the herb.
 * @param level The required level to use or obtain the herb.
 * @param product The product that can be created from the herb.
 * @constructor Herb Represents a new instance of the Herb enum class.
 */
enum class Herb(
    val herb: Item,          // The item that represents the herb.
    val experience: Double,  // The experience points awarded for using the herb.
    val level: Int,          // The level required to utilize the herb.
    val product: Item        // The resulting product derived from the herb.
) {
    /**
     * Guam
     *
     * @constructor Guam
     */
    GUAM(
        herb = Item(Items.GRIMY_GUAM_199),
        experience = 2.5,
        level = 3,
        product = Item(Items.CLEAN_GUAM_249)
    ),

    /**
     * Marrentill
     *
     * @constructor Marrentill
     */
    MARRENTILL(
        herb = Item(Items.GRIMY_MARRENTILL_201),
        experience = 3.8,
        level = 5,
        product = Item(Items.CLEAN_MARRENTILL_251)
    ),

    /**
     * Tarromin
     *
     * @constructor Tarromin
     */
    TARROMIN(
        herb = Item(Items.GRIMY_TARROMIN_203),
        experience = 5.0,
        level = 11,
        product = Item(Items.CLEAN_TARROMIN_253)
    ),

    /**
     * Harralander
     *
     * @constructor Harralander
     */
    HARRALANDER(
        herb = Item(Items.GRIMY_HARRALANDER_205),
        experience = 6.3,
        level = 20,
        product = Item(Items.CLEAN_HARRALANDER_255)
    ),

    /**
     * Ranarr
     *
     * @constructor Ranarr
     */
    RANARR(
        herb = Item(Items.GRIMY_RANARR_207),
        experience = 7.5,
        level = 25,
        product = Item(Items.CLEAN_RANARR_257)
    ),

    /**
     * Toadflax
     *
     * @constructor Toadflax
     */
    TOADFLAX(
        herb = Item(Items.GRIMY_TOADFLAX_3049),
        experience = 8.0,
        level = 30,
        product = Item(Items.CLEAN_TOADFLAX_2998)
    ),

    /**
     * Spirit Weed
     *
     * @constructor Spirit Weed
     */
    SPIRIT_WEED(
        herb = Item(Items.GRIMY_SPIRIT_WEED_12174),
        experience = 7.8,
        level = 35,
        product = Item(Items.CLEAN_SPIRIT_WEED_12172)
    ),

    /**
     * Irit
     *
     * @constructor Irit
     */
    IRIT(
        herb = Item(Items.GRIMY_IRIT_209),
        experience = 8.8,
        level = 40,
        product = Item(Items.CLEAN_IRIT_259)
    ),

    /**
     * Avantoe
     *
     * @constructor Avantoe
     */
    AVANTOE(
        herb = Item(Items.GRIMY_AVANTOE_211),
        experience = 10.0,
        level = 48,
        product = Item(Items.CLEAN_AVANTOE_261)
    ),

    /**
     * Kwuarm
     *
     * @constructor Kwuarm
     */
    KWUARM(
        herb = Item(Items.GRIMY_KWUARM_213),
        experience = 11.3,
        level = 54,
        product = Item(Items.CLEAN_KWUARM_263)
    ),

    /**
     * Snapdragon
     *
     * @constructor Snapdragon
     */
    SNAPDRAGON(
        herb = Item(Items.GRIMY_SNAPDRAGON_3051),
        experience = 11.8,
        level = 59,
        product = Item(Items.CLEAN_SNAPDRAGON_3000)
    ),

    /**
     * Cadantine
     *
     * @constructor Cadantine
     */
    CADANTINE(
        herb = Item(Items.GRIMY_CADANTINE_215),
        experience = 12.5,
        level = 65,
        product = Item(Items.CLEAN_CADANTINE_265)
    ),

    /**
     * Lantadyme
     *
     * @constructor Lantadyme
     */
    LANTADYME(
        herb = Item(Items.GRIMY_LANTADYME_2485),
        experience = 13.1,
        level = 67,
        product = Item(Items.CLEAN_LANTADYME_2481)
    ),

    /**
     * Dwarf Weed
     *
     * @constructor Dwarf Weed
     */
    DWARF_WEED(
        herb = Item(Items.GRIMY_DWARF_WEED_217),
        experience = 13.8,
        level = 70,
        product = Item(Items.CLEAN_DWARF_WEED_267)
    ),

    /**
     * Torstol
     *
     * @constructor Torstol
     */
    TORSTOL(
        herb = Item(Items.GRIMY_TORSTOL_219),
        experience = 15.0,
        level = 75,
        product = Item(Items.CLEAN_TORSTOL_269)
    ),

    /**
     * Snake Weed
     *
     * @constructor Snake Weed
     */
    SNAKE_WEED(
        herb = Item(Items.GRIMY_SNAKE_WEED_1525),
        experience = 2.5,
        level = 3,
        product = Item(Items.CLEAN_SNAKE_WEED_1526)
    ),

    /**
     * Ardrigal
     *
     * @constructor Ardrigal
     */
    ARDRIGAL(
        herb = Item(Items.GRIMY_ARDRIGAL_1527),
        experience = 2.5,
        level = 3,
        product = Item(Items.CLEAN_ARDRIGAL_1528)
    ),

    /**
     * Sito Foil
     *
     * @constructor Sito Foil
     */
    SITO_FOIL(
        herb = Item(Items.GRIMY_SITO_FOIL_1529),
        experience = 2.5,
        level = 3,
        product = Item(Items.CLEAN_SITO_FOIL_1530)
    ),

    /**
     * Volencia Moss
     *
     * @constructor Volencia Moss
     */
    VOLENCIA_MOSS(
        herb = Item(Items.GRIMY_VOLENCIA_MOSS_1531),
        experience = 2.5,
        level = 3,
        product = Item(Items.CLEAN_VOLENCIA_MOSS_1532)
    ),

    /**
     * Rogues Puse
     *
     * @constructor Rogues Puse
     */
    ROGUES_PUSE(
        herb = Item(Items.GRIMY_ROGUES_PURSE_1533),
        experience = 2.5,
        level = 3,
        product = Item(Items.CLEAN_ROGUES_PURSE_1534)
    );

    companion object {
        fun forItem(item: Item): Herb? {
            for (herb in values()) {
                if (herb.herb.id == item.id) {
                    return herb
                }
            }
            return null
        }
    }
}
