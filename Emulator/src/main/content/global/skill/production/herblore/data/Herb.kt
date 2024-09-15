package content.global.skill.production.herblore.data

import cfg.consts.Items
import core.game.node.item.Item

/**
 * Represents the herb data.
 *
 * @param herb          The item that represents the herb.
 * @param experience    The experience points awarded for using the herb.
 * @param level         The level required to utilize the herb.
 * @param product       The resulting product derived from the herb.
 * @return the [Herb].
 */
enum class Herb(val herb: Item, val experience: Double, val level: Int, val product: Item) {
    /**
     * The guam.
     */
    GUAM(
        herb = Item(Items.GRIMY_GUAM_199),
        experience = 2.5,
        level = 3,
        product = Item(Items.CLEAN_GUAM_249)
    ),

    /**
     * The marrentill.
     */
    MARRENTILL(
        herb = Item(Items.GRIMY_MARRENTILL_201),
        experience = 3.8,
        level = 5,
        product = Item(Items.CLEAN_MARRENTILL_251)
    ),

    /**
     * The tarromin.
     */
    TARROMIN(
        herb = Item(Items.GRIMY_TARROMIN_203),
        experience = 5.0,
        level = 11,
        product = Item(Items.CLEAN_TARROMIN_253)
    ),

    /**
     * The harralander.
     */
    HARRALANDER(
        herb = Item(Items.GRIMY_HARRALANDER_205),
        experience = 6.3,
        level = 20,
        product = Item(Items.CLEAN_HARRALANDER_255)
    ),

    /**
     * The ranarr.
     */
    RANARR(
        herb = Item(Items.GRIMY_RANARR_207),
        experience = 7.5,
        level = 25,
        product = Item(Items.CLEAN_RANARR_257)
    ),

    /**
     * The toadflax.
     */
    TOADFLAX(
        herb = Item(Items.GRIMY_TOADFLAX_3049),
        experience = 8.0,
        level = 30,
        product = Item(Items.CLEAN_TOADFLAX_2998)
    ),

    /**
     * The spirit weed.
     */
    SPIRIT_WEED(
        herb = Item(Items.GRIMY_SPIRIT_WEED_12174),
        experience = 7.8,
        level = 35,
        product = Item(Items.CLEAN_SPIRIT_WEED_12172)
    ),

    /**
     * The irit.
     */
    IRIT(
        herb = Item(Items.GRIMY_IRIT_209),
        experience = 8.8,
        level = 40,
        product = Item(Items.CLEAN_IRIT_259)
    ),

    /**
     * The avantoe.
     */
    AVANTOE(
        herb = Item(Items.GRIMY_AVANTOE_211),
        experience = 10.0,
        level = 48,
        product = Item(Items.CLEAN_AVANTOE_261)
    ),

    /**
     * The kwuarm.
     */
    KWUARM(
        herb = Item(Items.GRIMY_KWUARM_213),
        experience = 11.3,
        level = 54,
        product = Item(Items.CLEAN_KWUARM_263)
    ),

    /**
     * The snapdragon.
     */
    SNAPDRAGON(
        herb = Item(Items.GRIMY_SNAPDRAGON_3051),
        experience = 11.8,
        level = 59,
        product = Item(Items.CLEAN_SNAPDRAGON_3000)
    ),

    /**
     * The cadantine.
     */
    CADANTINE(
        herb = Item(Items.GRIMY_CADANTINE_215),
        experience = 12.5,
        level = 65,
        product = Item(Items.CLEAN_CADANTINE_265)
    ),

    /**
     * The lantadyme.
     */
    LANTADYME(
        herb = Item(Items.GRIMY_LANTADYME_2485),
        experience = 13.1,
        level = 67,
        product = Item(Items.CLEAN_LANTADYME_2481)
    ),

    /**
     * The dwarf weed.
     */
    DWARF_WEED(
        herb = Item(Items.GRIMY_DWARF_WEED_217),
        experience = 13.8,
        level = 70,
        product = Item(Items.CLEAN_DWARF_WEED_267)
    ),

    /**
     * The torstol.
     */
    TORSTOL(
        herb = Item(Items.GRIMY_TORSTOL_219),
        experience = 15.0,
        level = 75,
        product = Item(Items.CLEAN_TORSTOL_269)
    ),

    /**
     * The snake weed.
     */
    SNAKE_WEED(
        herb = Item(Items.GRIMY_SNAKE_WEED_1525),
        experience = 2.5,
        level = 3,
        product = Item(Items.CLEAN_SNAKE_WEED_1526)
    ),

    /**
     * The ardrigal.
     */
    ARDRIGAL(
        herb = Item(Items.GRIMY_ARDRIGAL_1527),
        experience = 2.5,
        level = 3,
        product = Item(Items.CLEAN_ARDRIGAL_1528)
    ),

    /**
     * The sito foil.
     */
    SITO_FOIL(
        herb = Item(Items.GRIMY_SITO_FOIL_1529),
        experience = 2.5,
        level = 3,
        product = Item(Items.CLEAN_SITO_FOIL_1530)
    ),

    /**
     * The volencia moss.
     */
    VOLENCIA_MOSS(
        herb = Item(Items.GRIMY_VOLENCIA_MOSS_1531),
        experience = 2.5,
        level = 3,
        product = Item(Items.CLEAN_VOLENCIA_MOSS_1532)
    ),

    /**
     * The rogues puse.
     */
    ROGUES_PUSE(
        herb = Item(Items.GRIMY_ROGUES_PURSE_1533),
        experience = 2.5,
        level = 3,
        product = Item(Items.CLEAN_ROGUES_PURSE_1534)
    );

    companion object {
        /**
         * Gets the herb from the item id.
         *
         * @param item  the item.
         * @return the herb.
         */
        @JvmStatic
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
