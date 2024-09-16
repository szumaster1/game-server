package content.global.skill.production.herblore.data

import cfg.consts.Items
import core.game.node.item.Item

/**
 * Represents the herb data.
 *
 * @param herb          the item that represents the herb.
 * @param experience    the experience points awarded for using the herb.
 * @param level         the level required to utilize the herb.
 * @param product       the resulting product derived from the herb.
 */
enum class Herb(val herb: Item, val experience: Double, val level: Int, val product: Item) {
    GUAM(
        herb = Item(Items.GRIMY_GUAM_199),
        experience = 2.5,
        level = 3,
        product = Item(Items.CLEAN_GUAM_249)
    ),
    MARRENTILL(
        herb = Item(Items.GRIMY_MARRENTILL_201),
        experience = 3.8,
        level = 5,
        product = Item(Items.CLEAN_MARRENTILL_251)
    ),
    TARROMIN(
        herb = Item(Items.GRIMY_TARROMIN_203),
        experience = 5.0,
        level = 11,
        product = Item(Items.CLEAN_TARROMIN_253)
    ),
    HARRALANDER(
        herb = Item(Items.GRIMY_HARRALANDER_205),
        experience = 6.3,
        level = 20,
        product = Item(Items.CLEAN_HARRALANDER_255)
    ),
    RANARR(
        herb = Item(Items.GRIMY_RANARR_207),
        experience = 7.5,
        level = 25,
        product = Item(Items.CLEAN_RANARR_257)
    ),
    TOADFLAX(
        herb = Item(Items.GRIMY_TOADFLAX_3049),
        experience = 8.0,
        level = 30,
        product = Item(Items.CLEAN_TOADFLAX_2998)
    ),
    SPIRIT_WEED(
        herb = Item(Items.GRIMY_SPIRIT_WEED_12174),
        experience = 7.8,
        level = 35,
        product = Item(Items.CLEAN_SPIRIT_WEED_12172)
    ),
    IRIT(
        herb = Item(Items.GRIMY_IRIT_209),
        experience = 8.8,
        level = 40,
        product = Item(Items.CLEAN_IRIT_259)
    ),
    AVANTOE(
        herb = Item(Items.GRIMY_AVANTOE_211),
        experience = 10.0,
        level = 48,
        product = Item(Items.CLEAN_AVANTOE_261)
    ),
    KWUARM(
        herb = Item(Items.GRIMY_KWUARM_213),
        experience = 11.3,
        level = 54,
        product = Item(Items.CLEAN_KWUARM_263)
    ),
    SNAPDRAGON(
        herb = Item(Items.GRIMY_SNAPDRAGON_3051),
        experience = 11.8,
        level = 59,
        product = Item(Items.CLEAN_SNAPDRAGON_3000)
    ),
    CADANTINE(
        herb = Item(Items.GRIMY_CADANTINE_215),
        experience = 12.5,
        level = 65,
        product = Item(Items.CLEAN_CADANTINE_265)
    ),
    LANTADYME(
        herb = Item(Items.GRIMY_LANTADYME_2485),
        experience = 13.1,
        level = 67,
        product = Item(Items.CLEAN_LANTADYME_2481)
    ),
    DWARF_WEED(
        herb = Item(Items.GRIMY_DWARF_WEED_217),
        experience = 13.8,
        level = 70,
        product = Item(Items.CLEAN_DWARF_WEED_267)
    ),
    TORSTOL(
        herb = Item(Items.GRIMY_TORSTOL_219),
        experience = 15.0,
        level = 75,
        product = Item(Items.CLEAN_TORSTOL_269)
    ),
    SNAKE_WEED(
        herb = Item(Items.GRIMY_SNAKE_WEED_1525),
        experience = 2.5,
        level = 3,
        product = Item(Items.CLEAN_SNAKE_WEED_1526)
    ),
    ARDRIGAL(
        herb = Item(Items.GRIMY_ARDRIGAL_1527),
        experience = 2.5,
        level = 3,
        product = Item(Items.CLEAN_ARDRIGAL_1528)
    ),
    SITO_FOIL(
        herb = Item(Items.GRIMY_SITO_FOIL_1529),
        experience = 2.5,
        level = 3,
        product = Item(Items.CLEAN_SITO_FOIL_1530)
    ),
    VOLENCIA_MOSS(
        herb = Item(Items.GRIMY_VOLENCIA_MOSS_1531),
        experience = 2.5,
        level = 3,
        product = Item(Items.CLEAN_VOLENCIA_MOSS_1532)
    ),
    ROGUES_PUSE(
        herb = Item(Items.GRIMY_ROGUES_PURSE_1533),
        experience = 2.5,
        level = 3,
        product = Item(Items.CLEAN_ROGUES_PURSE_1534)
    );

    companion object {
        private val herbMap: Map<Int, Herb> = values().associateBy { it.herb.id }

        @JvmStatic
        fun forItem(item: Item): Herb? {
            return herbMap[item.id]
        }
    }
}
