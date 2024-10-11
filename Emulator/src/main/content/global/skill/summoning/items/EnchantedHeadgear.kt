package content.global.skill.summoning.items

import core.game.node.item.Item
import org.rs.consts.Items

/**
 * Represents various types of enchanted headgear.
 *
 * @param defaultItem The item id to create an empty item id.
 * @param enchantedItem The item id of the empty version of the headgear.
 * @param chargedItem The item id of the charged version of the headgear.
 * @param scrollCapacity The maximum amount of scrolls the headgear can hold.
 * @param requiredLevel The required summoning level to charge the headgear.
 */
enum class EnchantedHeadgear(
    val defaultItem: Item,
    val enchantedItem: Item,
    val chargedItem: Item,
    val scrollCapacity: Int,
    val requiredLevel: Int
) {
    ANTLERS(
        defaultItem = Item(Items.ANTLERS_12204),
        enchantedItem = Item(Items.ANTLERS_12204),
        chargedItem = Item(Items.ANTLERS_CHARGED_12206),
        scrollCapacity = 40,
        requiredLevel = 10
    ),
    ADAMANT_FULL_HELM(
        defaultItem = Item(Items.ADAMANT_FULL_HELM_1161),
        enchantedItem = Item(Items.ADAMANT_FULL_HELM_E_12658),
        chargedItem = Item(Items.ADAMANT_FULL_HELM_CHARGED_12659),
        scrollCapacity = 50,
        requiredLevel = 20
    ),
    SLAYER_HELMET(
        defaultItem = Item(Items.SLAYER_HELMET_13263),
        enchantedItem = Item(Items.SLAYER_HELMET_E_14636),
        chargedItem = Item(Items.SLAYER_HELMET_CHARGED_14637),
        scrollCapacity = 50,
        requiredLevel = 20
    ),
    SNAKESKIN_BANDANA(
        defaultItem = Item(Items.SNAKESKIN_BANDANA_6326),
        enchantedItem = Item(Items.SNAKESKIN_BANDANA_E_12660),
        chargedItem = Item(Items.SNAKESKIN_BANDANA_CHARGED_12661),
        scrollCapacity = 50,
        requiredLevel = 20
    ),
    LIZARD_SKULL(
        defaultItem = Item(Items.LIZARD_SKULL_12207),

        enchantedItem = Item(Items.LIZARD_SKULL_12207),
        chargedItem = Item(Items.LIZARD_SKULL_CHARGED_12209),
        scrollCapacity = 65,
        requiredLevel = 30
    ),
    SPLITBARK_HELM(
        defaultItem = Item(Items.SPLITBARK_HELM_3385),
        enchantedItem = Item(Items.SPLITBARK_HELM_E_12662),
        chargedItem = Item(Items.SPLITBARK_HELM_CHARGED_12663),
        scrollCapacity = 50,
        requiredLevel = 30
    ),
    RUNE_FULL_HELM(
        defaultItem = Item(Items.RUNE_FULL_HELM_1163),
        enchantedItem = Item(Items.RUNE_FULL_HELM_E_12664),
        chargedItem = Item(Items.RUNE_FULL_HELM_CHARGED_12665),
        scrollCapacity = 60,
        requiredLevel = 30
    ),
    WARRIOR_HELM(
        defaultItem = Item(Items.WARRIOR_HELM_3753),
        enchantedItem = Item(Items.WARRIOR_HELM_E_12676),
        chargedItem = Item(Items.WARRIOR_HELM_CHARGED_12677),
        scrollCapacity = 70,
        requiredLevel = 35
    ),
    BERSERKER_HELM(
        defaultItem = Item(Items.BERSERKER_HELM_3751),
        enchantedItem = Item(Items.BERSERKER_HELM_E_12674),
        chargedItem = Item(Items.BERSERKER_HELM_CHARGED_12675),
        scrollCapacity = 70,
        requiredLevel = 35
    ),
    ARCHER_HELM(
        defaultItem = Item(Items.ARCHER_HELM_3749),
        enchantedItem = Item(Items.ARCHER_HELM_E_12672),
        chargedItem = Item(Items.ARCHER_HELM_CHARGED_12673),
        scrollCapacity = 70,
        requiredLevel = 35
    ),
    FARSEER_HELM(
        defaultItem = Item(Items.FARSEER_HELM_3755),
        enchantedItem = Item(Items.FARSEER_HELM_E_12678),
        chargedItem = Item(Items.FARSEER_HELM_CHARGED_12679),
        scrollCapacity = 70,
        requiredLevel = 35
    ),
    HELM_OF_NEITIZNOT(
        defaultItem = Item(Items.HELM_OF_NEITIZNOT_10828),
        enchantedItem = Item(Items.HELM_OF_NEITIZNOT_E_12680),
        chargedItem = Item(Items.HELM_OF_NEITIZNOT_CHARGED_12681),
        scrollCapacity = 90,
        requiredLevel = 45
    ),

    FEATHER_HEADDRESS_0(
        defaultItem = Item(Items.FEATHER_HEADDRESS_12210),
        enchantedItem = Item(Items.FEATHER_HEADDRESS_12210),
        chargedItem = Item(Items.FEATHER_HEADDRESS_CHARGED_12212),
        scrollCapacity = 150,
        requiredLevel = 50
    ),
    FEATHER_HEADDRESS_1(
        defaultItem = Item(Items.FEATHER_HEADDRESS_12222),
        enchantedItem = Item(Items.FEATHER_HEADDRESS_12222),
        chargedItem = Item(Items.FEATHER_HEADDRESS_CHARGED_12224),
        scrollCapacity = 150,
        requiredLevel = 50
    ),
    FEATHER_HEADDRESS_2(
        defaultItem = Item(Items.FEATHER_HEADDRESS_12216),
        enchantedItem = Item(Items.FEATHER_HEADDRESS_12216),
        chargedItem = Item(Items.FEATHER_HEADDRESS_CHARGED_12218),
        scrollCapacity = 150,
        requiredLevel = 50
    ),
    FEATHER_HEADDRESS_3(
        defaultItem = Item(Items.FEATHER_HEADDRESS_12219),
        enchantedItem = Item(Items.FEATHER_HEADDRESS_12219),
        chargedItem = Item(Items.FEATHER_HEADDRESS_CHARGED_12221),
        scrollCapacity = 150,
        requiredLevel = 50
    ),
    FEATHER_HEADDRESS_4(
        defaultItem = Item(Items.FEATHER_HEADDRESS_12213),
        enchantedItem = Item(Items.FEATHER_HEADDRESS_12213),
        chargedItem = Item(Items.FEATHER_HEADDRESS_CHARGED_12215),
        scrollCapacity = 150,
        requiredLevel = 50
    ),
    DRAGON_HELM(
        defaultItem = Item(Items.DRAGON_MED_HELM_1149),
        enchantedItem = Item(Items.DRAGON_MED_HELM_E_12666),
        chargedItem = Item(Items.DRAGON_MED_HELM_CHARGED_12667),
        scrollCapacity = 110,
        requiredLevel = 50
    ),
    LUNAR_HELM(
        defaultItem = Item(Items.LUNAR_HELM_9096),
        enchantedItem = Item(Items.LUNAR_HELM_E_12668),
        chargedItem = Item(Items.LUNAR_HELM_CHARGED_12669),
        scrollCapacity = 110,
        requiredLevel = 55
    ),
    ARMADYL_HELM(
        defaultItem = Item(Items.ARMADYL_HELMET_11718),
        enchantedItem = Item(Items.ARMADYL_HELMET_E_12670),
        chargedItem = Item(Items.ARMADYL_HELMET_CHARGED_12671),
        scrollCapacity = 120,
        requiredLevel = 60
    );

    companion object {
        val values = enumValues<EnchantedHeadgear>()
        val product = values.associateBy { it.defaultItem }

        /**
         * Get the enchanted gear.
         * @param item The item id.
         * @return The gear.
         */
        @JvmStatic
        fun forItem(item: Item): EnchantedHeadgear? {
            return EnchantedHeadgear.values().find { it.defaultItem.id == item.id }
        }

    }

}
