package content.global.skill.smithing

import core.game.node.entity.player.Player
import org.rs.consts.Items

/**
 * Enum class representing different types of bars used in smithing.
 *
 * @property barType        The type of bar (e.g., BRONZE).
 * @property smithingType   The type of item that can be crafted (e.g., DAGGER, AXE).
 * @property product        The specific item ID that corresponds to the crafted product.
 * @property level          The required level to craft the item.
 */
enum class Bars(val barType: BarType, val smithingType: SmithingType, val product: Int, val level: Int) {
    BRONZE_DAGGER(
        barType = BarType.BRONZE,
        smithingType = SmithingType.TYPE_DAGGER,
        product = Items.BRONZE_DAGGER_1205,
        level = 1
    ),
    BRONZE_AXE(
        barType = BarType.BRONZE,
        smithingType = SmithingType.TYPE_AXE,
        product = Items.BRONZE_AXE_1351,
        level = 1
    ),
    BRONZE_MACE(
        barType = BarType.BRONZE,
        smithingType = SmithingType.TYPE_MACE,
        product = Items.BRONZE_MACE_1422,
        level = 2
    ),
    BRONZE_MED_HELM(
        barType = BarType.BRONZE,
        smithingType = SmithingType.TYPE_MEDIUM_HELM,
        product = Items.BRONZE_MED_HELM_1139,
        level = 3
    ),
    BRONZE_CROSSBOW_BOLT(
        barType = BarType.BRONZE,
        smithingType = SmithingType.TYPE_CROSSBOW_BOLT,
        product = Items.BRONZE_BOLTS_UNF_9375,
        level = 3
    ),
    BRONZE_SWORD(
        barType = BarType.BRONZE,
        smithingType = SmithingType.TYPE_SWORD,
        product = Items.BRONZE_SWORD_1277,
        level = 4
    ),
    BRONZE_DART_TIPS(
        barType = BarType.BRONZE,
        smithingType = SmithingType.TYPE_DART_TIP,
        product = Items.BRONZE_DART_TIP_819,
        level = 4
    ),
    BRONZE_NAILS(
        barType = BarType.BRONZE,
        smithingType = SmithingType.TYPE_NAIL,
        product = Items.BRONZE_NAILS_4819,
        level = 4
    ),
    BRONZE_WIRE(
        barType = BarType.BRONZE,
        smithingType = SmithingType.TYPE_WIRE,
        product = Items.BRONZE_WIRE_1794,
        level = 4
    ),
    BRONZE_ARROW_TIPS(
        barType = BarType.BRONZE,
        smithingType = SmithingType.TYPE_ARROW_TIP,
        product = Items.BRONZE_ARROWTIPS_39,
        level = 5
    ),
    BRONZE_SCIMITAR(
        barType = BarType.BRONZE,
        smithingType = SmithingType.TYPE_SCIMITAR,
        product = Items.BRONZE_SCIMITAR_1321,
        level = 5
    ),
    BRONZE_CROSSBOW_LIMBS(
        barType = BarType.BRONZE,
        smithingType = SmithingType.TYPE_CROSSBOW_LIMB,
        product = Items.BRONZE_LIMBS_9420,
        level = 6
    ),
    BRONZE_LONGSWORD(
        barType = BarType.BRONZE,
        smithingType = SmithingType.TYPE_LONGSWORD,
        product = Items.BRONZE_LONGSWORD_1291,
        level = 6
    ),
    BRONZE_THROWINGKNFIE(
        barType = BarType.BRONZE,
        smithingType = SmithingType.TYPE_THROWING_KNIFE,
        product = Items.BRONZE_KNIFE_864,
        level = 7
    ),
    BRONZE_FULL_HELM(
        barType = BarType.BRONZE,
        smithingType = SmithingType.TYPE_FULL_HELM,
        product = Items.BRONZE_FULL_HELM_1155,
        level = 7
    ),
    BRONZE_SQUARE_SHIELD(
        barType = BarType.BRONZE,
        smithingType = SmithingType.TYPE_SQUARE_SHIELD,
        product = Items.BRONZE_SQ_SHIELD_1173,
        level = 8
    ),
    BRONZE_WAR_HAMMER(
        barType = BarType.BRONZE,
        smithingType = SmithingType.TYPE_WARHAMMER,
        product = Items.BRONZE_WARHAMMER_1337,
        level = 9
    ),
    BRONZE_BATTLEAXE(
        barType = BarType.BRONZE,
        smithingType = SmithingType.TYPE_BATTLE_AXE,
        product = Items.BRONZE_BATTLEAXE_1375,
        level = 10
    ),
    BRONZE_CHAINBODY(
        barType = BarType.BRONZE,
        smithingType = SmithingType.TYPE_CHAINBODY,
        product = Items.BRONZE_CHAINBODY_1103,
        level = 11
    ),
    BRONZE_KITESHIELD(
        barType = BarType.BRONZE,
        smithingType = SmithingType.TYPE_KITE_SHIELD,
        product = Items.BRONZE_KITESHIELD_1189,
        level = 12
    ),
    BRONZE_CLAWS(
        barType = BarType.BRONZE,
        smithingType = SmithingType.TYPE_CLAWS,
        product = Items.BRONZE_CLAWS_3095,
        level = 13
    ),
    BRONZE_TWO_HANDED(
        barType = BarType.BRONZE,
        smithingType = SmithingType.TYPE_TWO_HAND_SWORD,
        product = Items.BRONZE_2H_SWORD_1307,
        level = 14
    ),
    BRONZE_PLATE_SKIRT(
        barType = BarType.BRONZE,
        smithingType = SmithingType.TYPE_PLATE_SKIRT,
        product = Items.BRONZE_PLATESKIRT_1087,
        level = 16
    ),
    BRONZE_PLATELEGS(
        barType = BarType.BRONZE,
        smithingType = SmithingType.TYPE_Platelegs,
        product = Items.BRONZE_PLATELEGS_1075,
        level = 16
    ),
    BRONZE_PLATEBODY(
        barType = BarType.BRONZE,
        smithingType = SmithingType.TYPE_PLATEBODY,
        product = Items.BRONZE_PLATEBODY_1117,
        level = 18
    ),
    BRONZE_PICKAXE(
        barType = BarType.BRONZE,
        smithingType = SmithingType.TYPE_PICKAXE,
        product = Items.BRONZE_PICKAXE_1265,
        level = 5
    ),
    IRON_DAGGER(
        barType = BarType.IRON,
        smithingType = SmithingType.TYPE_DAGGER,
        product = Items.IRON_DAGGER_1203,
        level = 15
    ),
    IRON_AXE(
        barType = BarType.IRON,
        smithingType = SmithingType.TYPE_AXE,
        product = Items.IRON_AXE_1349,
        level = 16
    ),
    IRON_MACE(
        barType = BarType.IRON,
        smithingType = SmithingType.TYPE_MACE,
        product = Items.IRON_MACE_1420,
        level = 17
    ),
    IRON_MED_HELM(
        barType = BarType.IRON,
        smithingType = SmithingType.TYPE_MEDIUM_HELM,
        product = Items.IRON_MED_HELM_1137,
        level = 18
    ),
    IRON_BOLT(
        barType = BarType.IRON,
        smithingType = SmithingType.TYPE_CROSSBOW_BOLT,
        product = Items.IRON_BOLTS_UNF_9377,
        level = 18
    ),
    IRON_SWORD(
        barType = BarType.IRON,
        smithingType = SmithingType.TYPE_SWORD,
        product = Items.IRON_SWORD_1279,
        level = 19
    ),
    IRON_DART_TIPS(
        barType = BarType.IRON,
        smithingType = SmithingType.TYPE_DART_TIP,
        product = Items.IRON_DART_TIP_820,
        level = 19
    ),
    IRON_NAILS(
        barType = BarType.IRON,
        smithingType = SmithingType.TYPE_NAIL,
        product = Items.IRON_NAILS_4820,
        level = 19
    ),
    IRON_SPIT(
        barType = BarType.IRON,
        smithingType = SmithingType.TYPE_SPIT_IRON,
        product = Items.IRON_SPIT_7225,
        level = 16
    ),
    IRON_ARROW_TIPS(
        barType = BarType.IRON,
        smithingType = SmithingType.TYPE_ARROW_TIP,
        product = Items.IRON_ARROWTIPS_40,
        level = 20
    ),
    IRON_SCIMITAR(
        barType = BarType.IRON,
        smithingType = SmithingType.TYPE_SCIMITAR,
        product = Items.IRON_SCIMITAR_1323,
        level = 20
    ),
    IRON_CROSSBOW_LIMBS(
        barType = BarType.IRON,
        smithingType = SmithingType.TYPE_CROSSBOW_LIMB,
        product = Items.IRON_LIMBS_9423,
        level = 23
    ),
    IRON_LONGSWORD(
        barType = BarType.IRON,
        smithingType = SmithingType.TYPE_LONGSWORD,
        product = Items.IRON_LONGSWORD_1293,
        level = 21
    ),
    IRON_KNIFE(
        barType = BarType.IRON,
        smithingType = SmithingType.TYPE_THROWING_KNIFE,
        product = Items.IRON_KNIFE_863,
        level = 22
    ),
    IRON_FULL_HELM(
        barType = BarType.IRON,
        smithingType = SmithingType.TYPE_FULL_HELM,
        product = Items.IRON_FULL_HELM_1153,
        level = 22
    ),
    IRON_SQUARE_SHIELD(
        barType = BarType.IRON,
        smithingType = SmithingType.TYPE_SQUARE_SHIELD,
        product = Items.IRON_SQ_SHIELD_1175,
        level = 23
    ),
    OIL_LANTERN_FRAME(
        barType = BarType.IRON,
        smithingType = SmithingType.TYPE_OIL_LANTERN,
        product = Items.OIL_LANTERN_FRAME_4540,
        level = 26
    ),
    IRON_WARHAMMER(
        barType = BarType.IRON,
        smithingType = SmithingType.TYPE_WARHAMMER,
        product = Items.IRON_WARHAMMER_1335,
        level = 24
    ),
    IRON_BATTLEAXE(
        barType = BarType.IRON,
        smithingType = SmithingType.TYPE_BATTLE_AXE,
        product = Items.IRON_BATTLEAXE_1363,
        level = 25
    ),
    IRON_CHAINBODY(
        barType = BarType.IRON,
        smithingType = SmithingType.TYPE_CHAINBODY,
        product = Items.IRON_CHAINBODY_1101,
        level = 26
    ),
    IRON_KITE_SHIELD(
        barType = BarType.IRON,
        smithingType = SmithingType.TYPE_KITE_SHIELD,
        product = Items.IRON_KITESHIELD_1191,
        level = 27
    ),
    IRON_CLAWS(
        barType = BarType.IRON,
        smithingType = SmithingType.TYPE_CLAWS,
        product = Items.IRON_CLAWS_3096,
        level = 28
    ),
    IRON_TWO_HANDED_SWORD(
        barType = BarType.IRON,
        smithingType = SmithingType.TYPE_TWO_HAND_SWORD,
        product = Items.IRON_2H_SWORD_1309,
        level = 29
    ),
    IRON_PLATESKIRT(
        barType = BarType.IRON,
        smithingType = SmithingType.TYPE_PLATE_SKIRT,
        product = Items.IRON_PLATESKIRT_1081,
        level = 31
    ),
    IRON_PLATELEGS(
        barType = BarType.IRON,
        smithingType = SmithingType.TYPE_Platelegs,
        product = Items.IRON_PLATELEGS_1067,
        level = 31
    ),
    IRON_PLATEBODY(
        barType = BarType.IRON,
        smithingType = SmithingType.TYPE_PLATEBODY,
        product = Items.IRON_PLATEBODY_1115,
        level = 33
    ),
    IRON_PICKAXE(
        barType = BarType.IRON,
        smithingType = SmithingType.TYPE_PICKAXE,
        product = Items.IRON_PICKAXE_1267,
        level = 20
    ),
    STEEL_DAGGER(
        barType = BarType.STEEL,
        smithingType = SmithingType.TYPE_DAGGER,
        product = Items.STEEL_DAGGER_1207,
        level = 30
    ),
    STEEL_AXE(
        barType = BarType.STEEL,
        smithingType = SmithingType.TYPE_AXE,
        product = Items.STEEL_AXE_1353,
        level = 31
    ),
    STEEL_MACE(
        barType = BarType.STEEL,
        smithingType = SmithingType.TYPE_MACE,
        product = Items.STEEL_MACE_1424,
        level = 32
    ),
    STEEL_MED_HELM(
        barType = BarType.STEEL,
        smithingType = SmithingType.TYPE_MEDIUM_HELM,
        product = Items.STEEL_MED_HELM_1141,
        level = 33
    ),
    STEEL_CROSSBOW_BOLT(
        barType = BarType.STEEL,
        smithingType = SmithingType.TYPE_CROSSBOW_BOLT,
        product = Items.STEEL_BOLTS_UNF_9378,
        level = 33
    ),
    STEEL_SWORD(
        barType = BarType.STEEL,
        smithingType = SmithingType.TYPE_SWORD,
        product = Items.STEEL_SWORD_1281,
        level = 34
    ),
    STEEL_DART_TIPS(
        barType = BarType.STEEL,
        smithingType = SmithingType.TYPE_DART_TIP,
        product = Items.STEEL_DART_TIP_821,
        level = 34
    ),
    STEEL_NAILS(
        barType = BarType.STEEL,
        smithingType = SmithingType.TYPE_NAIL,
        product = Items.STEEL_NAILS_1539,
        level = 34
    ),
    STEEL_ARROW_TIPS(
        barType = BarType.STEEL,
        smithingType = SmithingType.TYPE_ARROW_TIP,
        product = Items.STEEL_ARROWTIPS_41,
        level = 35
    ),
    STEEL_SCIMITAR(
        barType = BarType.STEEL,
        smithingType = SmithingType.TYPE_SCIMITAR,
        product = Items.STEEL_SCIMITAR_1325,
        level = 35
    ),
    STEEL_CROSSBOW_LIMBS(
        barType = BarType.STEEL,
        smithingType = SmithingType.TYPE_CROSSBOW_LIMB,
        product = Items.STEEL_LIMBS_9425,
        level = 36
    ),
    STEEL_LONGSWORD(
        barType = BarType.STEEL,
        smithingType = SmithingType.TYPE_LONGSWORD,
        product = Items.STEEL_LONGSWORD_1295,
        level = 36
    ),
    STEEL_THROWING_KNIFE(
        barType = BarType.STEEL,
        smithingType = SmithingType.TYPE_THROWING_KNIFE,
        product = Items.STEEL_KNIFE_865,
        level = 37
    ),
    STEEL_FULL_HELM(
        barType = BarType.STEEL,
        smithingType = SmithingType.TYPE_FULL_HELM,
        product = Items.STEEL_FULL_HELM_1157,
        level = 37
    ),
    STEEL_STUDS(
        barType = BarType.STEEL,
        smithingType = SmithingType.TYPE_STUDS,
        product = Items.STEEL_STUDS_2370,
        level = 36
    ),
    STEEL_SQUARE_SHIELD(
        barType = BarType.STEEL,
        smithingType = SmithingType.TYPE_SQUARE_SHIELD,
        product = Items.STEEL_SQ_SHIELD_1177,
        level = 38
    ),
    STEEL_LANTERN(
        barType = BarType.STEEL,
        smithingType = SmithingType.TYPE_LANTERN,
        product = Items.CANDLE_LANTERN_4527,
        level = 49
    ),
    STEEL_BULLSEYE(
        barType = BarType.STEEL,
        smithingType = SmithingType.TYPE_BULLSEYE,
        product = Items.BULLSEYE_LANTERN_4544,
        level = 49
    ),
    STEEL_WARHAMMER(
        barType = BarType.STEEL,
        smithingType = SmithingType.TYPE_WARHAMMER,
        product = Items.STEEL_WARHAMMER_1339,
        level = 39
    ),
    STEEL_BATTLE_AXE(
        barType = BarType.STEEL,
        smithingType = SmithingType.TYPE_BATTLE_AXE,
        product = Items.STEEL_BATTLEAXE_1365,
        level = 40
    ),
    STEEL_CHAINBODY(
        barType = BarType.STEEL,
        smithingType = SmithingType.TYPE_CHAINBODY,
        product = Items.STEEL_CHAINBODY_1105,
        level = 41
    ),
    STEEL_KITE_SHIELD(
        barType = BarType.STEEL,
        smithingType = SmithingType.TYPE_KITE_SHIELD,
        product = Items.STEEL_KITESHIELD_1193,
        level = 42
    ),
    STEEL_CLAWS(
        barType = BarType.STEEL,
        smithingType = SmithingType.TYPE_CLAWS,
        product = Items.STEEL_CLAWS_3097,
        level = 43
    ),
    STEEL_TWO_HANDED_SWORD(
        barType = BarType.STEEL,
        smithingType = SmithingType.TYPE_TWO_HAND_SWORD,
        product = Items.STEEL_2H_SWORD_1311,
        level = 44
    ),
    STEEL_PLATE_SKIRT(
        barType = BarType.STEEL,
        smithingType = SmithingType.TYPE_PLATE_SKIRT,
        product = Items.STEEL_PLATESKIRT_1083,
        level = 46
    ),
    STEEL_PLATELEGS(
        barType = BarType.STEEL,
        smithingType = SmithingType.TYPE_Platelegs,
        product = Items.STEEL_PLATELEGS_1069,
        level = 46
    ),
    STEEL_PLATEBODY(
        barType = BarType.STEEL,
        smithingType = SmithingType.TYPE_PLATEBODY,
        product = Items.STEEL_PLATEBODY_1119,
        level = 48
    ),
    STEEL_PICKAXE(
        barType = BarType.STEEL,
        smithingType = SmithingType.TYPE_PICKAXE,
        product = Items.STEEL_PICKAXE_1269,
        level = 35
    ),
    BLURITE_CROSSBOW_BOLT(
        barType = BarType.BLURITE,
        smithingType = SmithingType.TYPE_Crossbow_Bolt,
        product = Items.BLURITE_BOLTS_UNF_9376,
        level = 8
    ),
    BLURITE_CROSSBOW_LIMBS(
        barType = BarType.BLURITE,
        smithingType = SmithingType.TYPE_Crossbow_Limb,
        product = Items.BLURITE_LIMBS_9422,
        level = 13
    ),
    MITHRIL_DAGGER(
        barType = BarType.MITHRIL,
        smithingType = SmithingType.TYPE_DAGGER,
        product = Items.MITHRIL_DAGGER_1209,
        level = 50
    ),
    MITHRIL_AXE(
        barType = BarType.MITHRIL,
        smithingType = SmithingType.TYPE_AXE,
        product = Items.MITHRIL_AXE_1355,
        level = 51
    ),
    MITHRIL_MACE(
        barType = BarType.MITHRIL,
        smithingType = SmithingType.TYPE_MACE,
        product = Items.MITHRIL_MACE_1428,
        level = 52
    ),
    MITHRIL_MED_HELM(
        barType = BarType.MITHRIL,
        smithingType = SmithingType.TYPE_MEDIUM_HELM,
        product = Items.MITHRIL_MED_HELM_1143,
        level = 53
    ),
    MITHRIL_CROSSBOW_BOLT(
        barType = BarType.MITHRIL,
        smithingType = SmithingType.TYPE_CROSSBOW_BOLT,
        product = Items.MITHRIL_BOLTS_UNF_9379,
        level = 53
    ),
    MITHRIL_SWORD(
        barType = BarType.MITHRIL,
        smithingType = SmithingType.TYPE_SWORD,
        product = Items.MITHRIL_SWORD_1285,
        level = 54
    ),
    MITHRIL_DART_TIPS(
        barType = BarType.MITHRIL,
        smithingType = SmithingType.TYPE_DART_TIP,
        product = Items.MITHRIL_DART_TIP_822,
        level = 54
    ),
    MITHRIL_NAILS(
        barType = BarType.MITHRIL,
        smithingType = SmithingType.TYPE_NAIL,
        product = Items.MITHRIL_NAILS_4822,
        level = 54
    ),
    MITHRIL_ARROW_TIPS(
        barType = BarType.MITHRIL,
        smithingType = SmithingType.TYPE_ARROW_TIP,
        product = Items.MITHRIL_ARROWTIPS_42,
        level = 55
    ),
    MITHRIL_SCIMITAR(
        barType = BarType.MITHRIL,
        smithingType = SmithingType.TYPE_SCIMITAR,
        product = Items.MITHRIL_SCIMITAR_1329,
        level = 55
    ),
    MITHRIL_CROSSBOW_LIMBS(
        barType = BarType.MITHRIL,
        smithingType = SmithingType.TYPE_CROSSBOW_LIMB,
        product = Items.MITHRIL_LIMBS_9427,
        level = 56
    ),
    MITHRIL_LONGSWORD(
        barType = BarType.MITHRIL,
        smithingType = SmithingType.TYPE_LONGSWORD,
        product = Items.MITHRIL_LONGSWORD_1299,
        level = 56
    ),
    MITHRIL_KNIFE(
        barType = BarType.MITHRIL,
        smithingType = SmithingType.TYPE_THROWING_KNIFE,
        product = Items.MITHRIL_KNIFE_866,
        level = 57
    ),
    MITHRIL_FULL_HELM(
        barType = BarType.MITHRIL,
        smithingType = SmithingType.TYPE_FULL_HELM,
        product = Items.MITHRIL_FULL_HELM_1159,
        level = 57
    ),
    MITHRIL_SQUARE_SHIELD(
        barType = BarType.MITHRIL,
        smithingType = SmithingType.TYPE_SQUARE_SHIELD,
        product = Items.MITHRIL_SQ_SHIELD_1181,
        level = 58
    ),
    MITHRIL_GRAPPLE_TIPS(
        barType = BarType.MITHRIL,
        smithingType = SmithingType.TYPE_GRAPPLE_TIP,
        product = Items.MITH_GRAPPLE_TIP_9416,
        level = 59
    ),
    MITHRIL_WARHAMMER(
        barType = BarType.MITHRIL,
        smithingType = SmithingType.TYPE_WARHAMMER,
        product = Items.MITHRIL_WARHAMMER_1343,
        level = 59
    ),
    MITHRIL_BATTLEAXE(
        barType = BarType.MITHRIL,
        smithingType = SmithingType.TYPE_BATTLE_AXE,
        product = Items.MITHRIL_BATTLEAXE_1369,
        level = 60
    ),
    MITHRIL_CHAINBODY(
        barType = BarType.MITHRIL,
        smithingType = SmithingType.TYPE_CHAINBODY,
        product = Items.MITHRIL_CHAINBODY_1109,
        level = 61
    ),
    MITHRIL_KITE_SHIELD(
        barType = BarType.MITHRIL,
        smithingType = SmithingType.TYPE_KITE_SHIELD,
        product = Items.MITHRIL_KITESHIELD_1197,
        level = 62
    ),
    MITHRIL_CLAWS(
        barType = BarType.MITHRIL,
        smithingType = SmithingType.TYPE_CLAWS,
        product = Items.MITHRIL_CLAWS_3099,
        level = 63
    ),
    MITHRIL_TWO_HANDED_SWORD(
        barType = BarType.MITHRIL,
        smithingType = SmithingType.TYPE_TWO_HAND_SWORD,
        product = Items.MITHRIL_2H_SWORD_1315,
        level = 64
    ),
    MITHRIL_PLATESKIRT(
        barType = BarType.MITHRIL,
        smithingType = SmithingType.TYPE_PLATE_SKIRT,
        product = Items.MITHRIL_PLATESKIRT_1085,
        level = 66
    ),
    MITHRIL_PLATELEGS(
        barType = BarType.MITHRIL,
        smithingType = SmithingType.TYPE_Platelegs,
        product = Items.MITHRIL_PLATELEGS_1071,
        level = 66
    ),
    MITHRIL_PLATEBODY(
        barType = BarType.MITHRIL,
        smithingType = SmithingType.TYPE_PLATEBODY,
        product = Items.MITHRIL_PLATEBODY_1121,
        level = 68
    ),
    MITHRIL_PICKAXE(
        barType = BarType.MITHRIL,
        smithingType = SmithingType.TYPE_PICKAXE,
        product = Items.MITHRIL_PICKAXE_1273,
        level = 55
    ),
    ADAMANT_DAGGER(
        barType = BarType.ADAMANT,
        smithingType = SmithingType.TYPE_DAGGER,
        product = Items.ADAMANT_DAGGER_1211,
        level = 70
    ),
    ADAMANT_AXE(
        barType = BarType.ADAMANT,
        smithingType = SmithingType.TYPE_AXE,
        product = Items.ADAMANT_AXE_1357,
        level = 71
    ),
    ADAMANT_MACE(
        barType = BarType.ADAMANT,
        smithingType = SmithingType.TYPE_MACE,
        product = Items.ADAMANT_MACE_1430,
        level = 72
    ),
    ADAMANT_MEDIUM_HELM(
        barType = BarType.ADAMANT,
        smithingType = SmithingType.TYPE_MEDIUM_HELM,
        product = Items.ADAMANT_MED_HELM_1145,
        level = 73
    ),
    ADAMANT_BOLT(
        barType = BarType.ADAMANT,
        smithingType = SmithingType.TYPE_CROSSBOW_BOLT,
        product = Items.ADAMANT_BOLTS_UNF_9380,
        level = 73
    ),
    ADAMANT_SWORD(
        barType = BarType.ADAMANT,
        smithingType = SmithingType.TYPE_SWORD,
        product = Items.ADAMANT_SWORD_1287,
        level = 74
    ),
    ADAMANT_DART_TIPS(
        barType = BarType.ADAMANT,
        smithingType = SmithingType.TYPE_DART_TIP,
        product = Items.ADAMANT_DART_TIP_823,
        level = 74
    ),
    ADAMANT_NAILS(
        barType = BarType.ADAMANT,
        smithingType = SmithingType.TYPE_NAIL,
        product = Items.ADAMANTITE_NAILS_4823,
        level = 74
    ),
    ADAMANT_ARROW_TIPS(
        barType = BarType.ADAMANT,
        smithingType = SmithingType.TYPE_ARROW_TIP,
        product = Items.ADAMANT_ARROWTIPS_43,
        level = 75
    ),
    ADAMANT_SCIMITAR(
        barType = BarType.ADAMANT,
        smithingType = SmithingType.TYPE_SCIMITAR,
        product = Items.ADAMANT_SCIMITAR_1331,
        level = 75
    ),
    ADAMANT_LIMBS(
        barType = BarType.ADAMANT,
        smithingType = SmithingType.TYPE_CROSSBOW_LIMB,
        product = Items.ADAMANTITE_LIMBS_9429,
        level = 76
    ),
    ADAMANT_LONGSWORD(
        barType = BarType.ADAMANT,
        smithingType = SmithingType.TYPE_LONGSWORD,
        product = Items.ADAMANT_LONGSWORD_1301,
        level = 76
    ),
    ADAMANT_KNIFE(
        barType = BarType.ADAMANT,
        smithingType = SmithingType.TYPE_THROWING_KNIFE,
        product = Items.ADAMANT_KNIFE_867,
        level = 77
    ),
    ADAMANT_FULL_HELM(
        barType = BarType.ADAMANT,
        smithingType = SmithingType.TYPE_FULL_HELM,
        product = Items.ADAMANT_FULL_HELM_1161,
        level = 77
    ),
    ADAMANT_SQUARE_SHIELD(
        barType = BarType.ADAMANT,
        smithingType = SmithingType.TYPE_SQUARE_SHIELD,
        product = Items.ADAMANT_SQ_SHIELD_1183,
        level = 78
    ),
    ADAMANT_WARHAMMER(
        barType = BarType.ADAMANT,
        smithingType = SmithingType.TYPE_WARHAMMER,
        product = Items.ADAMANT_WARHAMMER_1345,
        level = 79
    ),
    ADAMANT_BATTLEAXE(
        barType = BarType.ADAMANT,
        smithingType = SmithingType.TYPE_BATTLE_AXE,
        product = Items.ADAMANT_BATTLEAXE_1371,
        level = 80
    ),
    ADAMANT_CHAINBODY(
        barType = BarType.ADAMANT,
        smithingType = SmithingType.TYPE_CHAINBODY,
        product = Items.ADAMANT_CHAINBODY_1111,
        level = 81
    ),
    ADAMANT_KITESHIELD(
        barType = BarType.ADAMANT,
        smithingType = SmithingType.TYPE_KITE_SHIELD,
        product = Items.ADAMANT_KITESHIELD_1199,
        level = 82
    ),
    ADAMANT_CLAWS(
        barType = BarType.ADAMANT,
        smithingType = SmithingType.TYPE_CLAWS,
        product = Items.ADAMANT_CLAWS_3100,
        level = 83
    ),
    ADAMANT_TWO_HANDED_SWORD(
        barType = BarType.ADAMANT,
        smithingType = SmithingType.TYPE_TWO_HAND_SWORD,
        product = Items.ADAMANT_2H_SWORD_1317,
        level = 84
    ),
    ADAMANT_PLATE_SKIRT(
        barType = BarType.ADAMANT,
        smithingType = SmithingType.TYPE_PLATE_SKIRT,
        product = Items.ADAMANT_PLATESKIRT_1091,
        level = 86
    ),
    ADAMANT_PLATE_LEGS(
        barType = BarType.ADAMANT,
        smithingType = SmithingType.TYPE_Platelegs,
        product = Items.ADAMANT_PLATELEGS_1073,
        level = 86
    ),
    ADAMANT_PLATE_BODY(
        barType = BarType.ADAMANT,
        smithingType = SmithingType.TYPE_PLATEBODY,
        product = Items.ADAMANT_PLATEBODY_1123,
        level = 88
    ),
    ADAMANT_PICKAXE(
        barType = BarType.ADAMANT,
        smithingType = SmithingType.TYPE_PICKAXE,
        product = Items.ADAMANT_PICKAXE_1271,
        level = 75
    ),
    RUNE_DAGGER(
        barType = BarType.RUNITE,
        smithingType = SmithingType.TYPE_DAGGER,
        product = Items.RUNE_DAGGER_1213,
        level = 85
    ),
    RUNITE_AXE(
        barType = BarType.RUNITE,
        smithingType = SmithingType.TYPE_AXE,
        product = Items.RUNE_AXE_1359,
        level = 86
    ),
    RUNITE_MACE(
        barType = BarType.RUNITE,
        smithingType = SmithingType.TYPE_MACE,
        product = Items.RUNE_MACE_1432,
        level = 87
    ),
    RUNITE_MEDIUM_HELM(
        barType = BarType.RUNITE,
        smithingType = SmithingType.TYPE_MEDIUM_HELM,
        product = Items.RUNE_MED_HELM_1147,
        level = 88
    ),
    RUNITE_BOLT(
        barType = BarType.RUNITE,
        smithingType = SmithingType.TYPE_CROSSBOW_BOLT,
        product = Items.RUNITE_BOLTS_UNF_9381,
        level = 88
    ),
    RUNITE_SWORD(
        barType = BarType.RUNITE,
        smithingType = SmithingType.TYPE_SWORD,
        product = Items.RUNE_SWORD_1289,
        level = 89
    ),
    RUNITE_DART_TIPS(
        barType = BarType.RUNITE,
        smithingType = SmithingType.TYPE_DART_TIP,
        product = Items.RUNE_DART_TIP_824,
        level = 89
    ),
    RUNITE_NAILS(
        barType = BarType.RUNITE,
        smithingType = SmithingType.TYPE_NAIL,
        product = Items.RUNE_NAILS_4824,
        level = 89
    ),
    RUNITE_ARROW_TIPS(
        barType = BarType.RUNITE,
        smithingType = SmithingType.TYPE_ARROW_TIP,
        product = Items.RUNE_ARROWTIPS_44,
        level = 90
    ),
    RUNITE_SCIMITAR(
        barType = BarType.RUNITE,
        smithingType = SmithingType.TYPE_SCIMITAR,
        product = Items.RUNE_SCIMITAR_1333,
        level = 90
    ),
    RUNITE_LIMBS(
        barType = BarType.RUNITE,
        smithingType = SmithingType.TYPE_CROSSBOW_LIMB,
        product = Items.RUNITE_LIMBS_9431,
        level = 91
    ),
    RUNITE_LONGSWORD(
        barType = BarType.RUNITE,
        smithingType = SmithingType.TYPE_LONGSWORD,
        product = Items.RUNE_LONGSWORD_1303,
        level = 91
    ),
    RUNITE_KNIFE(
        barType = BarType.RUNITE,
        smithingType = SmithingType.TYPE_THROWING_KNIFE,
        product = Items.RUNE_KNIFE_868,
        level = 92
    ),
    RUNITE_FULL_HELM(
        barType = BarType.RUNITE,
        smithingType = SmithingType.TYPE_FULL_HELM,
        product = Items.RUNE_FULL_HELM_1163,
        level = 92
    ),
    RUNITE_SQUARE_SHIELD(
        barType = BarType.RUNITE,
        smithingType = SmithingType.TYPE_SQUARE_SHIELD,
        product = Items.RUNE_SQ_SHIELD_1185,
        level = 93
    ),
    RUNITE_WARHAMMER(
        barType = BarType.RUNITE,
        smithingType = SmithingType.TYPE_WARHAMMER,
        product = Items.RUNE_WARHAMMER_1347,
        level = 94
    ),
    RUNITE_BATTLEAXE(
        barType = BarType.RUNITE,
        smithingType = SmithingType.TYPE_BATTLE_AXE,
        product = Items.RUNE_BATTLEAXE_1373,
        level = 95
    ),
    RUNITE_CHAINBODY(
        barType = BarType.RUNITE,
        smithingType = SmithingType.TYPE_CHAINBODY,
        product = Items.RUNE_CHAINBODY_1113,
        level = 96
    ),
    RUNITE_KITESHIELD(
        barType = BarType.RUNITE,
        smithingType = SmithingType.TYPE_KITE_SHIELD,
        product = Items.RUNE_KITESHIELD_1201,
        level = 97
    ),
    RUNITE_CLAWS(
        barType = BarType.RUNITE,
        smithingType = SmithingType.TYPE_CLAWS,
        product = Items.RUNE_CLAWS_3101,
        level = 98
    ),
    RUNITE_TWO_HANDED_SWORD(
        barType = BarType.RUNITE,
        smithingType = SmithingType.TYPE_TWO_HAND_SWORD,
        product = Items.RUNE_2H_SWORD_1319,
        level = 99
    ),
    RUNITE_PLATE_SKIRT(
        barType = BarType.RUNITE,
        smithingType = SmithingType.TYPE_PLATE_SKIRT,
        product = Items.RUNE_PLATESKIRT_1093,
        level = 99
    ),
    RUNITE_PLATE_LEGS(
        barType = BarType.RUNITE,
        smithingType = SmithingType.TYPE_Platelegs,
        product = Items.RUNE_PLATELEGS_1079,
        level = 99
    ),
    RUNITE_PLATE_BODY(
        barType = BarType.RUNITE,
        smithingType = SmithingType.TYPE_PLATEBODY,
        product = Items.RUNE_PLATEBODY_1127,
        level = 99
    ),
    RUNITE_PICKAXE(
        barType = BarType.RUNITE,
        smithingType = SmithingType.TYPE_PICKAXE,
        product = Items.RUNE_PICKAXE_1275,
        level = 90
    );

    companion object {
        private val bars: MutableMap<Short, Bars> = mutableMapOf()

        /**
         * Gets a bar by an item id.
         *
         * @param item the item id.
         * @return The bar or `null` if the object is not a bar.
         */
        fun forId(item: Int): Bars? {
            return bars[item.toShort()]
        }

        /**
         * Populates the bar mapping.
         */
        init {
            values().forEach { bar ->
                bars[bar.product.toShort()] = bar
            }
        }

        /**
         * Gets the bars.
         *
         * @param type the type of bar.
         * @return An array of Bars objects of the specified type.
         */
        fun getBars(type: BarType): Array<Bars?> {
            return values().filter { it.barType == type }.toTypedArray()
        }

        /**
         * Get the item id based on [buttonId] and [BarType].
         */
        fun getItemId(buttonId: Int, type: BarType): Int {
            return values().firstOrNull { it.barType == type && buttonId in it.smithingType.button }?.product ?: -1
        }

        /**
         * Get the index of a bar based on [buttonId], and [BarType].
         */
        fun getIndex(player: Player?, buttonId: Int, type: BarType): Int {
            return values().indexOfFirst {
                it.barType == type && buttonId in it.smithingType.button
            }.takeIf { it >= 0 } ?: -1
        }
    }
}