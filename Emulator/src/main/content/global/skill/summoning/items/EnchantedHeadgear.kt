package content.global.skill.summoning.items

import org.rs.consts.Items

/**
 * Represents various types of enchanted headgear.
 *
 * @param emptyItem         The item id of the empty version of the headgear.
 * @param chargedItem       The item id of the charged version of the headgear.
 * @param scrollCapacity    The maximum amount of scrolls the headgear can hold.
 * @param requiredLevel     The required summoning level to charge the headgear.
 */
enum class EnchantedHeadgear(
    val emptyItem: Int,
    val chargedItem: Int,
    val scrollCapacity: Int,
    val requiredLevel: Int
) {
    ANTLERS(
        emptyItem = Items.ANTLERS_12204,
        chargedItem = Items.ANTLERS_CHARGED_12206,
        scrollCapacity = 40,
        requiredLevel = 10
    ),
    ADAMANT_FULL_HELM(
        emptyItem = Items.ADAMANT_FULL_HELM_E_12658,
        chargedItem = Items.ADAMANT_FULL_HELM_CHARGED_12659,
        scrollCapacity = 50,
        requiredLevel = 20
    ),
    SLAYER_HELMET(
        emptyItem = Items.SLAYER_HELMET_E_14636,
        chargedItem = Items.SLAYER_HELMET_CHARGED_14637,
        scrollCapacity = 50,
        requiredLevel = 20
    ),
    SNAKESKIN_BANDANA(
        emptyItem = Items.SNAKESKIN_BANDANA_E_12660,
        chargedItem = Items.SNAKESKIN_BANDANA_CHARGED_12661,
        scrollCapacity = 50,
        requiredLevel = 20
    ),
    LIZARD_SKULL(
        emptyItem = Items.LIZARD_SKULL_12207,
        chargedItem = Items.LIZARD_SKULL_CHARGED_12209,
        scrollCapacity = 65,
        requiredLevel = 30
    ),
    SPLITBARK_HELM(
        emptyItem = Items.SPLITBARK_HELM_E_12662,
        chargedItem = Items.SPLITBARK_HELM_CHARGED_12663,
        scrollCapacity = 50,
        requiredLevel = 30
    ),
    RUNE_FULL_HELM(
        emptyItem = Items.RUNE_FULL_HELM_E_12664,
        chargedItem = Items.RUNE_FULL_HELM_CHARGED_12665,
        scrollCapacity = 60,
        requiredLevel = 30
    ),
    WARRIOR_HELM(
        emptyItem = Items.WARRIOR_HELM_E_12676,
        chargedItem = Items.WARRIOR_HELM_CHARGED_12677,
        scrollCapacity = 70,
        requiredLevel = 35
    ),
    BERSERKER_HELM(
        emptyItem = Items.BERSERKER_HELM_E_12674,
        chargedItem = Items.BERSERKER_HELM_CHARGED_12675,
        scrollCapacity = 70,
        requiredLevel = 35
    ),
    ARCHER_HELM(
        emptyItem = Items.ARCHER_HELM_E_12672,
        chargedItem = Items.ARCHER_HELM_CHARGED_12673,
        scrollCapacity = 70,
        requiredLevel = 35
    ),
    FARSEER_HELM(
        emptyItem = Items.FARSEER_HELM_E_12678,
        chargedItem = Items.FARSEER_HELM_CHARGED_12679,
        scrollCapacity = 70,
        requiredLevel = 35
    ),
    HELM_OF_NEITIZNOT(
        emptyItem = Items.HELM_OF_NEITIZNOT_E_12680,
        chargedItem = Items.HELM_OF_NEITIZNOT_CHARGED_12681,
        scrollCapacity = 90,
        requiredLevel = 45
    ),

    FEATHER_HEADDRESS_0(
        emptyItem = Items.FEATHER_HEADDRESS_12210,
        chargedItem = Items.FEATHER_HEADDRESS_CHARGED_12212,
        scrollCapacity = 150,
        requiredLevel = 50
    ),
    FEATHER_HEADDRESS_1(
        emptyItem = Items.FEATHER_HEADDRESS_12222,
        chargedItem = Items.FEATHER_HEADDRESS_CHARGED_12224,
        scrollCapacity = 150,
        requiredLevel = 50
    ),
    FEATHER_HEADDRESS_2(
        emptyItem = Items.FEATHER_HEADDRESS_12216,
        chargedItem = Items.FEATHER_HEADDRESS_CHARGED_12218,
        scrollCapacity = 150,
        requiredLevel = 50
    ),
    FEATHER_HEADDRESS_3(
        emptyItem = Items.FEATHER_HEADDRESS_12219,
        chargedItem = Items.FEATHER_HEADDRESS_CHARGED_12221,
        scrollCapacity = 150,
        requiredLevel = 50
    ),
    FEATHER_HEADDRESS_4(
        emptyItem = Items.FEATHER_HEADDRESS_12213,
        chargedItem = Items.FEATHER_HEADDRESS_CHARGED_12215,
        scrollCapacity = 150,
        requiredLevel = 50
    ),
    DRAGON_HELM(
        emptyItem = Items.DRAGON_MED_HELM_E_12666,
        chargedItem = Items.DRAGON_MED_HELM_CHARGED_12667,
        scrollCapacity = 110,
        requiredLevel = 50
    ),
    LUNAR_HELM(
        emptyItem = Items.LUNAR_HELM_E_12668,
        chargedItem = Items.LUNAR_HELM_CHARGED_12669,
        scrollCapacity = 110,
        requiredLevel = 55
    ),
    ARMADYL_HELM(
        emptyItem = Items.ARMADYL_HELMET_E_12670,
        chargedItem = Items.ARMADYL_HELMET_CHARGED_12671,
        scrollCapacity = 120,
        requiredLevel = 60
    )
}
