package content.global.skill.production.smithing.data

import cfg.consts.Items
import core.game.node.entity.player.Player

/**
 * Bars enum class.
 * @author Emperor, Vexia
 */
enum class Bars(
    val barType: BarType,
    val smithingType: SmithingType,
    val product: Int,
    val level: Int
) {
    /**
     * The bronze dagger.
     */
    BRONZE_DAGGER(BarType.BRONZE, SmithingType.TYPE_DAGGER, Items.BRONZE_DAGGER_1205, 1),

    /**
     * The bronze axe.
     */
    BRONZE_AXE(BarType.BRONZE, SmithingType.TYPE_AXE, Items.BRONZE_AXE_1351, 1),

    /**
     * The bronze mace.
     */
    BRONZE_MACE(BarType.BRONZE, SmithingType.TYPE_MACE, Items.BRONZE_MACE_1422, 2),

    /**
     * The bronze Med Helm.
     */
    BRONZE_MED_HELM(BarType.BRONZE, SmithingType.TYPE_MEDIUM_HELM, Items.BRONZE_MED_HELM_1139, 3),

    /**
     * The bronze crossbow bolt.
     */
    BRONZE_CROSSBOW_BOLT(BarType.BRONZE, SmithingType.TYPE_CROSSBOW_BOLT, Items.BRONZE_BOLTS_UNF_9375, 3),

    /**
     * The bronze sword.
     */
    BRONZE_SWORD(BarType.BRONZE, SmithingType.TYPE_SWORD, Items.BRONZE_SWORD_1277, 4),

    /**
     * The bronze dart dips.
     */
    BRONZE_DART_TIPS(BarType.BRONZE, SmithingType.TYPE_DART_TIP, Items.BRONZE_DART_TIP_819, 4),

    /**
     * The bronze nails.
     */
    BRONZE_NAILS(BarType.BRONZE, SmithingType.TYPE_NAIL, Items.BRONZE_NAILS_4819, 4),

    /**
     * The bronze wire.
     */
    BRONZE_WIRE(BarType.BRONZE, SmithingType.TYPE_WIRE, Items.BRONZE_WIRE_1794, 4),

    /**
     * The bronze arrow tips.
     */
    BRONZE_ARROW_TIPS(BarType.BRONZE, SmithingType.TYPE_ARROW_TIP, Items.BRONZE_ARROWTIPS_39, 5),

    /**
     * The bronze scimitar.
     */
    BRONZE_SCIMITAR(BarType.BRONZE, SmithingType.TYPE_SCIMITAR, Items.BRONZE_SCIMITAR_1321, 5),

    /**
     * The bronze crossbow limbs.
     */
    BRONZE_CROSSBOW_LIMBS(BarType.BRONZE, SmithingType.TYPE_CROSSBOW_LIMB, Items.BRONZE_LIMBS_9420, 6),

    /**
     * The bronze longsword.
     */
    BRONZE_LONGSWORD(BarType.BRONZE, SmithingType.TYPE_LONGSWORD, Items.BRONZE_LONGSWORD_1291, 6),

    /**
     * The bronze throwing knfie.
     */
    BRONZE_THROWINGKNFIE(BarType.BRONZE, SmithingType.TYPE_THROWING_KNIFE, Items.BRONZE_KNIFE_864, 7),

    /**
     * The bronze full helm.
     */
    BRONZE_FULL_HELM(BarType.BRONZE, SmithingType.TYPE_FULL_HELM, Items.BRONZE_FULL_HELM_1155, 7),

    /**
     * The bronze sq shield.
     */
    BRONZE_SQUARE_SHIELD(BarType.BRONZE, SmithingType.TYPE_SQUARE_SHIELD, Items.BRONZE_SQ_SHIELD_1173, 8),

    /**
     * The bronze war hammer.
     */
    BRONZE_WAR_HAMMER(BarType.BRONZE, SmithingType.TYPE_WARHAMMER, Items.BRONZE_WARHAMMER_1337, 9),

    /**
     * The bronze battleaxe.
     */
    BRONZE_BATTLEAXE(BarType.BRONZE, SmithingType.TYPE_BATTLE_AXE, Items.BRONZE_BATTLEAXE_1375, 10),

    /**
     * The bronze chainbody.
     */
    BRONZE_CHAINBODY(BarType.BRONZE, SmithingType.TYPE_CHAINBODY, Items.BRONZE_CHAINBODY_1103, 11),

    /**
     * The bronze kiteshield.
     */
    BRONZE_KITESHIELD(BarType.BRONZE, SmithingType.TYPE_KITE_SHIELD, Items.BRONZE_KITESHIELD_1189, 12),

    /**
     * The bronze claws.
     */
    BRONZE_CLAWS(BarType.BRONZE, SmithingType.TYPE_CLAWS, Items.BRONZE_CLAWS_3095, 13),

    /**
     * The bronze two-handed sword.
     */
    BRONZE_TWO_HANDED(BarType.BRONZE, SmithingType.TYPE_TWO_HAND_SWORD, Items.BRONZE_2H_SWORD_1307, 14),

    /**
     * The bronze plateskirt.
     */
    BRONZE_PLATE_SKIRT(BarType.BRONZE, SmithingType.TYPE_PLATE_SKIRT, Items.BRONZE_PLATESKIRT_1087, 16),

    /**
     * The bronze platelegs.
     */
    BRONZE_PLATELEGS(BarType.BRONZE, SmithingType.TYPE_Platelegs, Items.BRONZE_PLATELEGS_1075, 16),

    /**
     * The bronze platebody.
     */
    BRONZE_PLATEBODY(BarType.BRONZE, SmithingType.TYPE_PLATEBODY, Items.BRONZE_PLATEBODY_1117, 18),

    /**
     * The bronze pickaxe.
     */
    BRONZE_PICKAXE(BarType.BRONZE, SmithingType.TYPE_PICKAXE, Items.BRONZE_PICKAXE_1265, 5),

    /**
     * The iron dagger.
     */
    IRON_DAGGER(BarType.IRON, SmithingType.TYPE_DAGGER, Items.IRON_DAGGER_1203, 15),

    /**
     * The iron axe.
     */
    IRON_AXE(BarType.IRON, SmithingType.TYPE_AXE, Items.IRON_AXE_1349, 16),

    /**
     * The iron mace.
     */
    IRON_MACE(BarType.IRON, SmithingType.TYPE_MACE, Items.IRON_MACE_1420, 17),

    /**
     * The iron Med Helm.
     */
    IRON_MED_HELM(BarType.IRON, SmithingType.TYPE_MEDIUM_HELM, Items.IRON_MED_HELM_1137, 18),

    /**
     * The iron bolt.
     */
    IRON_BOLT(BarType.IRON, SmithingType.TYPE_CROSSBOW_BOLT, Items.IRON_BOLTS_UNF_9377, 18),

    /**
     * The iron sword.
     */
    IRON_SWORD(BarType.IRON, SmithingType.TYPE_SWORD, Items.IRON_SWORD_1279, 19),

    /**
     * The iron dart dips.
     */
    IRON_DART_TIPS(BarType.IRON, SmithingType.TYPE_DART_TIP, Items.IRON_DART_TIP_820, 19),

    /**
     * The iron nails.
     */
    IRON_NAILS(BarType.IRON, SmithingType.TYPE_NAIL, Items.IRON_NAILS_4820, 19),

    /**
     * The iron Spit.
     */
    IRON_SPIT(BarType.IRON, SmithingType.TYPE_SPIT_IRON, Items.IRON_SPIT_7225, 16),

    /**
     * The iron arrow tips.
     */
    IRON_ARROW_TIPS(BarType.IRON, SmithingType.TYPE_ARROW_TIP, Items.IRON_ARROWTIPS_40, 20),

    /**
     * The iron scimitar.
     */
    IRON_SCIMITAR(BarType.IRON, SmithingType.TYPE_SCIMITAR, Items.IRON_SCIMITAR_1323, 20),

    /**
     * The iron crossbow limbs.
     */
    IRON_CROSSBOW_LIMBS(BarType.IRON, SmithingType.TYPE_CROSSBOW_LIMB, Items.IRON_LIMBS_9423, 23),

    /**
     * The iron longsword.
     */
    IRON_LONGSWORD(BarType.IRON, SmithingType.TYPE_LONGSWORD, Items.IRON_LONGSWORD_1293, 21),

    /**
     * The iron knife.
     */
    IRON_KNIFE(BarType.IRON, SmithingType.TYPE_THROWING_KNIFE, Items.IRON_KNIFE_863, 22),

    /**
     * The iron full helm.
     */
    IRON_FULL_HELM(BarType.IRON, SmithingType.TYPE_FULL_HELM, Items.IRON_FULL_HELM_1153, 22),

    /**
     * The iron sq shield.
     */
    IRON_SQUARE_SHIELD(BarType.IRON, SmithingType.TYPE_SQUARE_SHIELD, Items.IRON_SQ_SHIELD_1175, 23),

    /**
     * The oil lantern frame.
     */
    OIL_LANTERN_FRAME(BarType.IRON, SmithingType.TYPE_OIL_LANTERN, Items.OIL_LANTERN_FRAME_4540, 26),

    /**
     * The iron war hammer.
     */
    IRON_WARHAMMER(BarType.IRON, SmithingType.TYPE_WARHAMMER, Items.IRON_WARHAMMER_1335, 24),

    /**
     * The iron battleaxe.
     */
    IRON_BATTLEAXE(BarType.IRON, SmithingType.TYPE_BATTLE_AXE, Items.IRON_BATTLEAXE_1363, 25),

    /**
     * The iron chainbody.
     */
    IRON_CHAINBODY(BarType.IRON, SmithingType.TYPE_CHAINBODY, Items.IRON_CHAINBODY_1101, 26),

    /**
     * The iron kiteshield.
     */
    IRON_KITE_SHIELD(BarType.IRON, SmithingType.TYPE_KITE_SHIELD, Items.IRON_KITESHIELD_1191, 27),

    /**
     * The iron claws.
     */
    IRON_CLAWS(BarType.IRON, SmithingType.TYPE_CLAWS, Items.IRON_CLAWS_3096, 28),

    /**
     * The iron two-handed sword.
     */
    IRON_TWO_HANDED_SWORD(BarType.IRON, SmithingType.TYPE_TWO_HAND_SWORD, Items.IRON_2H_SWORD_1309, 29),

    /**
     * The iron plateskirt.
     */
    IRON_PLATESKIRT(BarType.IRON, SmithingType.TYPE_PLATE_SKIRT, Items.IRON_PLATESKIRT_1081, 31),

    /**
     * The iron platelegs.
     */
    IRON_PLATELEGS(BarType.IRON, SmithingType.TYPE_Platelegs, Items.IRON_PLATELEGS_1067, 31),

    /**
     * The iron platebody.
     */
    IRON_PLATEBODY(BarType.IRON, SmithingType.TYPE_PLATEBODY, Items.IRON_PLATEBODY_1115, 33),

    /**
     * The iron pickaxe.
     */
    IRON_PICKAXE(BarType.IRON, SmithingType.TYPE_PICKAXE, Items.IRON_PICKAXE_1267, 20),

    /**
     * The steel dagger.
     */
    STEEL_DAGGER(BarType.STEEL, SmithingType.TYPE_DAGGER, Items.STEEL_DAGGER_1207, 30),

    /**
     * The steel axe.
     */
    STEEL_AXE(BarType.STEEL, SmithingType.TYPE_AXE, Items.STEEL_AXE_1353, 31),

    /**
     * The steel mace.
     */
    STEEL_MACE(BarType.STEEL, SmithingType.TYPE_MACE, Items.STEEL_MACE_1424, 32),

    /**
     * The steel med helm.
     */
    STEEL_MED_HELM(BarType.STEEL, SmithingType.TYPE_MEDIUM_HELM, Items.STEEL_MED_HELM_1141, 33),

    /**
     * The steel crossbow bolt.
     */
    STEEL_CROSSBOW_BOLT(BarType.STEEL, SmithingType.TYPE_CROSSBOW_BOLT, Items.STEEL_BOLTS_UNF_9378, 33),

    /**
     * The steel sword.
     */
    STEEL_SWORD(BarType.STEEL, SmithingType.TYPE_SWORD, Items.STEEL_SWORD_1281, 34),

    /**
     * The steel dart dips.
     */
    STEEL_DART_TIPS(BarType.STEEL, SmithingType.TYPE_DART_TIP, Items.STEEL_DART_TIP_821, 34),

    /**
     * The steel nails.
     */
    STEEL_NAILS(BarType.STEEL, SmithingType.TYPE_NAIL, Items.STEEL_NAILS_1539, 34),

    /**
     * The steel arrow tips.
     */
    STEEL_ARROW_TIPS(BarType.STEEL, SmithingType.TYPE_ARROW_TIP, Items.STEEL_ARROWTIPS_41, 35),

    /**
     * The steel scimitar.
     */
    STEEL_SCIMITAR(BarType.STEEL, SmithingType.TYPE_SCIMITAR, Items.STEEL_SCIMITAR_1325, 35),

    /**
     * The steel crossbow limbs.
     */
    STEEL_CROSSBOW_LIMBS(BarType.STEEL, SmithingType.TYPE_CROSSBOW_LIMB, Items.STEEL_LIMBS_9425, 36),

    /**
     * The steel longsword.
     */
    STEEL_LONGSWORD(BarType.STEEL, SmithingType.TYPE_LONGSWORD, Items.STEEL_LONGSWORD_1295, 36),

    /**
     * The steel throwing knife.
     */
    STEEL_THROWING_KNIFE(BarType.STEEL, SmithingType.TYPE_THROWING_KNIFE, Items.STEEL_KNIFE_865, 37),

    /**
     * The steel full helm.
     */
    STEEL_FULL_HELM(BarType.STEEL, SmithingType.TYPE_FULL_HELM, Items.STEEL_FULL_HELM_1157, 37),

    /**
     * The steel studs.
     */
    STEEL_STUDS(BarType.STEEL, SmithingType.TYPE_STUDS, Items.STEEL_STUDS_2370, 36),

    /**
     * The steel sq shield.
     */
    STEEL_SQUARE_SHIELD(BarType.STEEL, SmithingType.TYPE_SQUARE_SHIELD, Items.STEEL_SQ_SHIELD_1177, 38),

    /**
     * The steel lantern.
     */
    STEEL_LANTERN(BarType.STEEL, SmithingType.TYPE_LANTERN, Items.CANDLE_LANTERN_4527, 49),

    /**
     * The steel bullseye.
     */
    STEEL_BULLSEYE(BarType.STEEL, SmithingType.TYPE_BULLSEYE, Items.BULLSEYE_LANTERN_4544, 49),

    /**
     * The steel war hammer.
     */
    STEEL_WARHAMMER(BarType.STEEL, SmithingType.TYPE_WARHAMMER, Items.STEEL_WARHAMMER_1339, 39),

    /**
     * The steel battleaxe.
     */
    STEEL_BATTLE_AXE(BarType.STEEL, SmithingType.TYPE_BATTLE_AXE, Items.STEEL_BATTLEAXE_1365, 40),

    /**
     * The steel chainbody.
     */
    STEEL_CHAINBODY(BarType.STEEL, SmithingType.TYPE_CHAINBODY, Items.STEEL_CHAINBODY_1105, 41),

    /**
     * The steel kiteshield.
     */
    STEEL_KITE_SHIELD(BarType.STEEL, SmithingType.TYPE_KITE_SHIELD, Items.STEEL_KITESHIELD_1193, 42),

    /**
     * The steel claws.
     */
    STEEL_CLAWS(BarType.STEEL, SmithingType.TYPE_CLAWS, Items.STEEL_CLAWS_3097, 43),

    /**
     * The steel two-handed sword.
     */
    STEEL_TWO_HANDED_SWORD(BarType.STEEL, SmithingType.TYPE_TWO_HAND_SWORD, Items.STEEL_2H_SWORD_1311, 44),

    /**
     * The steel plateskirt.
     */
    STEEL_PLATE_SKIRT(BarType.STEEL, SmithingType.TYPE_PLATE_SKIRT, Items.STEEL_PLATESKIRT_1083, 46),

    /**
     * The steel platelegs.
     */
    STEEL_PLATELEGS(BarType.STEEL, SmithingType.TYPE_Platelegs, Items.STEEL_PLATELEGS_1069, 46),

    /**
     * The steel platebody.
     */
    STEEL_PLATEBODY(BarType.STEEL, SmithingType.TYPE_PLATEBODY, Items.STEEL_PLATEBODY_1119, 48),

    /**
     * The steel pickaxe.
     */
    STEEL_PICKAXE(BarType.STEEL, SmithingType.TYPE_PICKAXE, Items.STEEL_PICKAXE_1269, 35),

    /**
     * The blurite crossbow bolt.
     */
    BLURITE_CROSSBOW_BOLT(BarType.BLURITE, SmithingType.TYPE_Crossbow_Bolt, Items.BLURITE_BOLTS_UNF_9376, 8),

    /**
     * The blurite crossbow limbs.
     */
    BLURITE_CROSSBOW_LIMBS(BarType.BLURITE, SmithingType.TYPE_Crossbow_Limb, Items.BLURITE_LIMBS_9422, 13),

    /**
     * The mithril dagger.
     */
    MITHRIL_DAGGER(BarType.MITHRIL, SmithingType.TYPE_DAGGER, Items.MITHRIL_DAGGER_1209, 50),

    /**
     * The mithril axe.
     */
    MITHRIL_AXE(BarType.MITHRIL, SmithingType.TYPE_AXE, Items.MITHRIL_AXE_1355, 51),

    /**
     * The mithril mace.
     */
    MITHRIL_MACE(BarType.MITHRIL, SmithingType.TYPE_MACE, Items.MITHRIL_MACE_1428, 52),

    /**
     * The mithril Med Helm.
     */
    MITHRIL_MED_HELM(BarType.MITHRIL, SmithingType.TYPE_MEDIUM_HELM, Items.MITHRIL_MED_HELM_1143, 53),

    /**
     * The mithril crossbow bolt.
     */
    MITHRIL_CROSSBOW_BOLT(BarType.MITHRIL, SmithingType.TYPE_CROSSBOW_BOLT, Items.MITHRIL_BOLTS_UNF_9379, 53),

    /**
     * The mithril sword.
     */
    MITHRIL_SWORD(BarType.MITHRIL, SmithingType.TYPE_SWORD, Items.MITHRIL_SWORD_1285, 54),

    /**
     * The mithril dart dips.
     */
    MITHRIL_DART_TIPS(BarType.MITHRIL, SmithingType.TYPE_DART_TIP, Items.MITHRIL_DART_TIP_822, 54),

    /**
     * The mithril nails.
     */
    MITHRIL_NAILS(BarType.MITHRIL, SmithingType.TYPE_NAIL, Items.MITHRIL_NAILS_4822, 54),

    /**
     * The mithril arrow tips.
     */
    MITHRIL_ARROW_TIPS(BarType.MITHRIL, SmithingType.TYPE_ARROW_TIP, Items.MITHRIL_ARROWTIPS_42, 55),

    /**
     * The mithril scimitar.
     */
    MITHRIL_SCIMITAR(BarType.MITHRIL, SmithingType.TYPE_SCIMITAR, Items.MITHRIL_SCIMITAR_1329, 55),

    /**
     * The mithril crossbow limbs.
     */
    MITHRIL_CROSSBOW_LIMBS(BarType.MITHRIL, SmithingType.TYPE_CROSSBOW_LIMB, Items.MITHRIL_LIMBS_9427, 56),

    /**
     * The mithril longsword.
     */
    MITHRIL_LONGSWORD(BarType.MITHRIL, SmithingType.TYPE_LONGSWORD, Items.MITHRIL_LONGSWORD_1299, 56),

    /**
     * The mithril knife.
     */
    MITHRIL_KNIFE(BarType.MITHRIL, SmithingType.TYPE_THROWING_KNIFE, Items.MITHRIL_KNIFE_866, 57),

    /**
     * The mithril full helm.
     */
    MITHRIL_FULL_HELM(BarType.MITHRIL, SmithingType.TYPE_FULL_HELM, Items.MITHRIL_FULL_HELM_1159, 57),

    /**
     * The mithril sq shield.
     */
    MITHRIL_SQUARE_SHIELD(BarType.MITHRIL, SmithingType.TYPE_SQUARE_SHIELD, Items.MITHRIL_SQ_SHIELD_1181, 58),

    /**
     * The mithril Grapple Tips.
     */
    MITHRIL_GRAPPLE_TIPS(BarType.MITHRIL, SmithingType.TYPE_GRAPPLE_TIP, Items.MITH_GRAPPLE_TIP_9416, 59),

    /**
     * The mithril war hammer.
     */
    MITHRIL_WARHAMMER(BarType.MITHRIL, SmithingType.TYPE_WARHAMMER, Items.MITHRIL_WARHAMMER_1343, 59),

    /**
     * The mithril battleaxe.
     */
    MITHRIL_BATTLEAXE(BarType.MITHRIL, SmithingType.TYPE_BATTLE_AXE, Items.MITHRIL_BATTLEAXE_1369, 60),

    /**
     * The mithril chainbody.
     */
    MITHRIL_CHAINBODY(BarType.MITHRIL, SmithingType.TYPE_CHAINBODY, Items.MITHRIL_CHAINBODY_1109, 61),

    /**
     * The mithril kiteshield.
     */
    MITHRIL_KITE_SHIELD(BarType.MITHRIL, SmithingType.TYPE_KITE_SHIELD, Items.MITHRIL_KITESHIELD_1197, 62),

    /**
     * The mithril claws.
     */
    MITHRIL_CLAWS(BarType.MITHRIL, SmithingType.TYPE_CLAWS, Items.MITHRIL_CLAWS_3099, 63),

    /**
     * The mithril two-handed sword.
     */
    MITHRIL_TWO_HANDED_SWORD(BarType.MITHRIL, SmithingType.TYPE_TWO_HAND_SWORD, Items.MITHRIL_2H_SWORD_1315, 64),

    /**
     * The mithril plateskirt.
     */
    MITHRIL_PLATESKIRT(BarType.MITHRIL, SmithingType.TYPE_PLATE_SKIRT, Items.MITHRIL_PLATESKIRT_1085, 66),

    /**
     * The mithril platelegs.
     */
    MITHRIL_PLATELEGS(BarType.MITHRIL, SmithingType.TYPE_Platelegs, Items.MITHRIL_PLATELEGS_1071, 66),

    /**
     * The mithril platebody.
     */
    MITHRIL_PLATEBODY(BarType.MITHRIL, SmithingType.TYPE_PLATEBODY, Items.MITHRIL_PLATEBODY_1121, 68),

    /**
     * The mithril pickaxe.
     */
    MITHRIL_PICKAXE(BarType.MITHRIL, SmithingType.TYPE_PICKAXE, Items.MITHRIL_PICKAXE_1273, 55),

    /**
     * The adamant dagger.
     */
    ADAMANT_DAGGER(BarType.ADAMANT, SmithingType.TYPE_DAGGER, Items.ADAMANT_DAGGER_1211, 70),

    /**
     * The adamant axe.
     */
    ADAMANT_AXE(BarType.ADAMANT, SmithingType.TYPE_AXE, Items.ADAMANT_AXE_1357, 71),

    /**
     * The adamant mace.
     */
    ADAMANT_MACE(BarType.ADAMANT, SmithingType.TYPE_MACE, Items.ADAMANT_MACE_1430, 72),

    /**
     * The adamant med helm.
     */
    ADAMANT_MEDIUM_HELM(BarType.ADAMANT, SmithingType.TYPE_MEDIUM_HELM, Items.ADAMANT_MED_HELM_1145, 73),

    /**
     * The adamant bolt.
     */
    ADAMANT_BOLT(BarType.ADAMANT, SmithingType.TYPE_CROSSBOW_BOLT, Items.ADAMANT_BOLTS_UNF_9380, 73),

    /**
     * The adamant sword.
     */
    ADAMANT_SWORD(BarType.ADAMANT, SmithingType.TYPE_SWORD, Items.ADAMANT_SWORD_1287, 74),

    /**
     * The adamant dart dips.
     */
    ADAMANT_DART_TIPS(BarType.ADAMANT, SmithingType.TYPE_DART_TIP, Items.ADAMANT_DART_TIP_823, 74),

    /**
     * The adamant nails.
     */
    ADAMANT_NAILS(BarType.ADAMANT, SmithingType.TYPE_NAIL, Items.ADAMANTITE_NAILS_4823, 74),

    /**
     * The adamant arrow tips.
     */
    ADAMANT_ARROW_TIPS(BarType.ADAMANT, SmithingType.TYPE_ARROW_TIP, Items.ADAMANT_ARROWTIPS_43, 75),

    /**
     * The adamant scimitar.
     */
    ADAMANT_SCIMITAR(BarType.ADAMANT, SmithingType.TYPE_SCIMITAR, Items.ADAMANT_SCIMITAR_1331, 75),

    /**
     * The adamant limbs.
     */
    ADAMANT_LIMBS(BarType.ADAMANT, SmithingType.TYPE_CROSSBOW_LIMB, Items.ADAMANTITE_LIMBS_9429, 76),

    /**
     * The adamant longsword.
     */
    ADAMANT_LONGSWORD(BarType.ADAMANT, SmithingType.TYPE_LONGSWORD, Items.ADAMANT_LONGSWORD_1301, 76),

    /**
     * The adamant knife.
     */
    ADAMANT_KNIFE(BarType.ADAMANT, SmithingType.TYPE_THROWING_KNIFE, Items.ADAMANT_KNIFE_867, 77),

    /**
     * The adamant full helm.
     */
    ADAMANT_FULL_HELM(BarType.ADAMANT, SmithingType.TYPE_FULL_HELM, Items.ADAMANT_FULL_HELM_1161, 77),

    /**
     * The adamant sq shield.
     */
    ADAMANT_SQUARE_SHIELD(BarType.ADAMANT, SmithingType.TYPE_SQUARE_SHIELD, Items.ADAMANT_SQ_SHIELD_1183, 78),

    /**
     * The adamant war hammer.
     */
    ADAMANT_WARHAMMER(BarType.ADAMANT, SmithingType.TYPE_WARHAMMER, Items.ADAMANT_WARHAMMER_1345, 79),

    /**
     * The adamant battleaxe.
     */
    ADAMANT_BATTLEAXE(BarType.ADAMANT, SmithingType.TYPE_BATTLE_AXE, Items.ADAMANT_BATTLEAXE_1371, 80),

    /**
     * The adamant chainbody.
     */
    ADAMANT_CHAINBODY(BarType.ADAMANT, SmithingType.TYPE_CHAINBODY, Items.ADAMANT_CHAINBODY_1111, 81),

    /**
     * The adamant kiteshield.
     */
    ADAMANT_KITESHIELD(BarType.ADAMANT, SmithingType.TYPE_KITE_SHIELD, Items.ADAMANT_KITESHIELD_1199, 82),

    /**
     * The adamant claws.
     */
    ADAMANT_CLAWS(BarType.ADAMANT, SmithingType.TYPE_CLAWS, Items.ADAMANT_CLAWS_3100, 83),

    /**
     * The adamant two-handed sword.
     */
    ADAMANT_TWO_HANDED_SWORD(BarType.ADAMANT, SmithingType.TYPE_TWO_HAND_SWORD, Items.ADAMANT_2H_SWORD_1317, 84),

    /**
     * The adamant plateskirt.
     */
    ADAMANT_PLATE_SKIRT(BarType.ADAMANT, SmithingType.TYPE_PLATE_SKIRT, Items.ADAMANT_PLATESKIRT_1091, 86),

    /**
     * The adamant platelegs.
     */
    ADAMANT_PLATE_LEGS(BarType.ADAMANT, SmithingType.TYPE_Platelegs, Items.ADAMANT_PLATELEGS_1073, 86),

    /**
     * The adamant platebody.
     */
    ADAMANT_PLATE_BODY(BarType.ADAMANT, SmithingType.TYPE_PLATEBODY, Items.ADAMANT_PLATEBODY_1123, 88),

    /**
     * The adamant pickaxe.
     */
    ADAMANT_PICKAXE(BarType.ADAMANT, SmithingType.TYPE_PICKAXE, Items.ADAMANT_PICKAXE_1271, 75),

    /**
     * Rune dagger.
     */
    RUNE_DAGGER(BarType.RUNITE, SmithingType.TYPE_DAGGER, Items.RUNE_DAGGER_1213, 85),

    /**
     * The runite axe.
     */
    RUNITE_AXE(BarType.RUNITE, SmithingType.TYPE_AXE, Items.RUNE_AXE_1359, 86),

    /**
     * The runite mace.
     */
    RUNITE_MACE(BarType.RUNITE, SmithingType.TYPE_MACE, Items.RUNE_MACE_1432, 87),

    /**
     * The runite med helm.
     */
    RUNITE_MEDIUM_HELM(BarType.RUNITE, SmithingType.TYPE_MEDIUM_HELM, Items.RUNE_MED_HELM_1147, 88),

    /**
     * The runite bolt.
     */
    RUNITE_BOLT(BarType.RUNITE, SmithingType.TYPE_CROSSBOW_BOLT, Items.RUNITE_BOLTS_UNF_9381, 88),

    /**
     * The runite sword.
     */
    RUNITE_SWORD(BarType.RUNITE, SmithingType.TYPE_SWORD, Items.RUNE_SWORD_1289, 89),

    /**
     * The runite dart dips.
     */
    RUNITE_DART_TIPS(BarType.RUNITE, SmithingType.TYPE_DART_TIP, Items.RUNE_DART_TIP_824, 89),

    /**
     * The runite nails.
     */
    RUNITE_NAILS(BarType.RUNITE, SmithingType.TYPE_NAIL, Items.RUNE_NAILS_4824, 89),

    /**
     * The runite arrow tips.
     */
    RUNITE_ARROW_TIPS(BarType.RUNITE, SmithingType.TYPE_ARROW_TIP, Items.RUNE_ARROWTIPS_44, 90),

    /**
     * The runite scimitar.
     */
    RUNITE_SCIMITAR(BarType.RUNITE, SmithingType.TYPE_SCIMITAR, Items.RUNE_SCIMITAR_1333, 90),

    /**
     * The runite limbs.
     */
    RUNITE_LIMBS(BarType.RUNITE, SmithingType.TYPE_CROSSBOW_LIMB, Items.RUNITE_LIMBS_9431, 91),

    /**
     * The runite longsword.
     */
    RUNITE_LONGSWORD(BarType.RUNITE, SmithingType.TYPE_LONGSWORD, Items.RUNE_LONGSWORD_1303, 91),

    /**
     * The runite knife.
     */
    RUNITE_KNIFE(BarType.RUNITE, SmithingType.TYPE_THROWING_KNIFE, Items.RUNE_KNIFE_868, 92),

    /**
     * The runite full helm.
     */
    RUNITE_FULL_HELM(BarType.RUNITE, SmithingType.TYPE_FULL_HELM, Items.RUNE_FULL_HELM_1163, 92),

    /**
     * The runite sq shield.
     */
    RUNITE_SQUARE_SHIELD(BarType.RUNITE, SmithingType.TYPE_SQUARE_SHIELD, Items.RUNE_SQ_SHIELD_1185, 93),

    /**
     * The runite war hammer.
     */
    RUNITE_WARHAMMER(BarType.RUNITE, SmithingType.TYPE_WARHAMMER, Items.RUNE_WARHAMMER_1347, 94),

    /**
     * The runite battleaxe.
     */
    RUNITE_BATTLEAXE(BarType.RUNITE, SmithingType.TYPE_BATTLE_AXE, Items.RUNE_BATTLEAXE_1373, 95),

    /**
     * The runite chainbody.
     */
    RUNITE_CHAINBODY(BarType.RUNITE, SmithingType.TYPE_CHAINBODY, Items.RUNE_CHAINBODY_1113, 96),

    /**
     * The runite kiteshield.
     */
    RUNITE_KITESHIELD(BarType.RUNITE, SmithingType.TYPE_KITE_SHIELD, Items.RUNE_KITESHIELD_1201, 97),

    /**
     * The runite claws.
     */
    RUNITE_CLAWS(BarType.RUNITE, SmithingType.TYPE_CLAWS, Items.RUNE_CLAWS_3101, 98),

    /**
     * The runite two-handed sword.
     */
    RUNITE_TWO_HANDED_SWORD(BarType.RUNITE, SmithingType.TYPE_TWO_HAND_SWORD, Items.RUNE_2H_SWORD_1319, 99),

    /**
     * The runite plateskirt.
     */
    RUNITE_PLATE_SKIRT(BarType.RUNITE, SmithingType.TYPE_PLATE_SKIRT, Items.RUNE_PLATESKIRT_1093, 99),

    /**
     * The runite platelegs.
     */
    RUNITE_PLATE_LEGS(BarType.RUNITE, SmithingType.TYPE_Platelegs, Items.RUNE_PLATELEGS_1079, 99),

    /**
     * The runite platebody.
     */
    RUNITE_PLATE_BODY(BarType.RUNITE, SmithingType.TYPE_PLATEBODY, Items.RUNE_PLATEBODY_1127, 99),

    /**
     * The runite pickaxe.
     */
    RUNITE_PICKAXE(BarType.RUNITE, SmithingType.TYPE_PICKAXE, Items.RUNE_PICKAXE_1275, 90);

    companion object {
        private val bars: MutableMap<Short, Bars> = HashMap()

        /**
         * Gets a bar by an item id.
         *
         * @param item The item id.
         * @return The bar or `null` if the object is not a bar.
         */
        fun forId(item: Int): Bars? {
            for (bar in values()) {
                if (bar.product == item) {
                    return bar
                }
            }
            return null
        }

        /**
         * Populates the bar mapping.
         */
        init {
            for (bar in values()) {
                bars[bar.product.toShort()] = bar
            }
        }

        fun getBars(type: BarType): Array<Bars?> {
            val bars: MutableList<Bars> = ArrayList()
            for (bar in values()) {
                if (bar.barType == type) {
                    bars.add(bar)
                }
            }
            val barss = arrayOfNulls<Bars>(bars.size)
            for (i in bars.indices) {
                barss[i] = bars[i]
            }
            return barss
        }

        fun getItemId(buttonId: Int, type: BarType): Int {
            for (bar in values()) {
                if (bar.barType != type) {
                    continue
                }
                for (i in bar.smithingType.button) {
                    if (buttonId == i) {
                        return bar.product
                    }
                }
            }
            return -1
        }

        fun getIndex(player: Player?, buttonId: Int, type: BarType): Int {
            var index = 0
            for (bar in values()) {
                if (bar.barType != type) {
                    continue
                }
                val bar = forId(bar.product)!!
                for (i in bar.smithingType.button.indices) {
                    if (buttonId != bar.smithingType.button[i]) {
                        index++
                        return index
                    }
                }
            }
            return -1
        }
    }
}