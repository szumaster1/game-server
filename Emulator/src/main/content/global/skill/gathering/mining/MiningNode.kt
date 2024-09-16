package content.global.skill.gathering.mining

import core.Configuration
import core.game.node.item.WeightedChanceItem
import core.game.world.repository.Repository.players

/**
 * Represents the mining node.
 */
enum class MiningNode(var id: Int, var emptyId: Int, var identifier: Byte) {
    COPPER_ORE_0(2090, 450, 1.toByte()),
    COPPER_ORE_1(2091, 452, 1.toByte()),
    COPPER_ORE_2(4976, 4994, 1.toByte()),
    COPPER_ORE_3(4977, 4995, 1.toByte()),
    COPPER_ORE_4(4978, 4996, 1.toByte()),
    COPPER_ORE_5(9710, 18954, 1.toByte()),
    COPPER_ORE_6(9709, 32448, 1.toByte()),
    COPPER_ORE_7(9708, 32447, 1.toByte()),
    COPPER_ORE_8(11960, 11555, 1.toByte()),
    COPPER_ORE_9(11961, 11556, 1.toByte()),
    COPPER_ORE_10(11962, 11557, 1.toByte()),
    COPPER_ORE_11(11937, 11553, 1.toByte()),
    COPPER_ORE_12(11936, 11552, 1.toByte()),
    COPPER_ORE_13(11938, 11554, 1.toByte()),
    COPPER_ORE_14(12746, 450, 1.toByte()),
    COPPER_ORE_15(14906, 14894, 1.toByte()),
    COPPER_ORE_16(14907, 14895, 1.toByte()),
    COPPER_ORE_17(20448, 20445, 1.toByte()),
    COPPER_ORE_18(20451, 20445, 1.toByte()),
    COPPER_ORE_19(20446, 20443, 1.toByte()),
    COPPER_ORE_20(20447, 20444, 1.toByte()),
    COPPER_ORE_21(20408, 20407, 1.toByte()),
    COPPER_ORE_22(18993, 19005, 1.toByte()),
    COPPER_ORE_23(18992, 19004, 1.toByte()),
    COPPER_ORE_24(19007, 19016, 1.toByte()),
    COPPER_ORE_25(19006, 19021, 1.toByte()),
    COPPER_ORE_26(18991, 19003, 1.toByte()),
    COPPER_ORE_27(19008, 19017, 1.toByte()),
    COPPER_ORE_28(21285, 21297, 1.toByte()),
    COPPER_ORE_29(21284, 21296, 1.toByte()),
    COPPER_ORE_30(21286, 21298, 1.toByte()),
    COPPER_ORE_31(29231, 29219, 1.toByte()),
    COPPER_ORE_32(29230, 29218, 1.toByte()),
    COPPER_ORE_33(29232, 29220, 1.toByte()),
    COPPER_ORE_34(31082, 37650, 1.toByte()),
    COPPER_ORE_35(31081, 37649, 1.toByte()),
    COPPER_ORE_36(31080, 37639, 1.toByte()),
    COPPER_ORE_37(37647, 37650, 1.toByte()),
    COPPER_ORE_38(37646, 37649, 1.toByte()),
    COPPER_ORE_39(37645, 37639, 1.toByte()),
    COPPER_ORE_40(37637, 37639, 1.toByte()),
    COPPER_ORE_41(37688, 21298, 1.toByte()),
    COPPER_ORE_42(37686, 21296, 1.toByte()),
    COPPER_ORE_43(37687, 21297, 1.toByte()),
    COPPER_ORE_44(3042, 11552, 1.toByte()),
    TIN_ORE_0(2094, 450, 2.toByte()),
    TIN_ORE_1(2095, 452, 2.toByte()),
    TIN_ORE_2(3043, 11552, 2.toByte()),
    TIN_ORE_3(4979, 4994, 2.toByte()),
    TIN_ORE_4(4980, 4995, 2.toByte()),
    TIN_ORE_5(4981, 4996, 2.toByte()),
    TIN_ORE_6(11957, 11555, 2.toByte()),
    TIN_ORE_7(11958, 11556, 2.toByte()),
    TIN_ORE_8(11959, 11557, 2.toByte()),
    TIN_ORE_9(11934, 11553, 2.toByte()),
    TIN_ORE_10(11935, 11554, 2.toByte()),
    TIN_ORE_11(11933, 11552, 2.toByte()),
    TIN_ORE_12(14902, 14894, 2.toByte()),
    TIN_ORE_13(14903, 14895, 2.toByte()),
    TIN_ORE_14(18995, 19004, 2.toByte()),
    TIN_ORE_15(18994, 19003, 2.toByte()),
    TIN_ORE_16(18996, 19005, 2.toByte()),
    TIN_ORE_17(19025, 19016, 2.toByte()),
    TIN_ORE_18(19024, 19021, 2.toByte()),
    TIN_ORE_19(19026, 19017, 2.toByte()),
    TIN_ORE_20(21293, 21296, 2.toByte()),
    TIN_ORE_21(21295, 21298, 2.toByte()),
    TIN_ORE_22(21294, 21297, 2.toByte()),
    TIN_ORE_23(29227, 29218, 2.toByte()),
    TIN_ORE_24(29229, 29220, 2.toByte()),
    TIN_ORE_25(29228, 29219, 2.toByte()),
    TIN_ORE_26(31079, 37650, 2.toByte()),
    TIN_ORE_27(31078, 37649, 2.toByte()),
    TIN_ORE_28(31077, 37639, 2.toByte()),
    TIN_ORE_29(37644, 37650, 2.toByte()),
    TIN_ORE_30(37643, 37649, 2.toByte()),
    TIN_ORE_31(37642, 37639, 2.toByte()),
    TIN_ORE_32(37638, 37639, 2.toByte()),
    TIN_ORE_33(37685, 21298, 2.toByte()),
    CLAY_0(2109, 452, 3.toByte()),
    CLAY_1(2108, 450, 3.toByte()),
    CLAY_2(9712, 32448, 3.toByte()),
    CLAY_3(9713, 18954, 3.toByte()),
    CLAY_4(9711, 32447, 3.toByte()),
    CLAY_5(10949, 10945, 3.toByte()),
    CLAY_6(11190, 21297, 3.toByte()),
    CLAY_7(11191, 21298, 3.toByte()),
    CLAY_8(11189, 21296, 3.toByte()),
    CLAY_9(12942, 4995, 3.toByte()),
    CLAY_10(12943, 4996, 3.toByte()),
    CLAY_11(12941, 4994, 3.toByte()),
    CLAY_12(14904, 14894, 3.toByte()),
    CLAY_13(14905, 14895, 3.toByte()),
    CLAY_14(15505, 11557, 3.toByte()),
    CLAY_15(15504, 11556, 3.toByte()),
    CLAY_16(15503, 11555, 3.toByte()),
    CLAY_17(20449, 20443, 3.toByte()),
    CLAY_18(20450, 20444, 3.toByte()),
    CLAY_19(20409, 20407, 3.toByte()),
    CLAY_20(32429, 33400, 3.toByte()),
    CLAY_21(32430, 33401, 3.toByte()),
    CLAY_22(32431, 33402, 3.toByte()),
    CLAY_23(31062, 37639, 3.toByte()),
    CLAY_24(31063, 37649, 3.toByte()),
    CLAY_25(31064, 37650, 3.toByte()),
    LIMESTONE_0(4027, 4028, 4.toByte()),
    LIMESTONE_1(4028, 4029, 4.toByte()),
    LIMESTONE_2(4029, 4030, 4.toByte()),
    LIMESTONE_3(4030, 4027, 4.toByte()),
    BLURITE_ORE_0(33220, 33222, 5.toByte()),
    BLURITE_ORE_1(33221, 33223, 5.toByte()),
    IRON_ORE_0(2092, 450, 6.toByte()),
    IRON_ORE_1(2093, 452, 6.toByte()),
    IRON_ORE_2(4982, 4994, 6.toByte()),
    IRON_ORE_3(4983, 4995, 6.toByte()),
    IRON_ORE_4(4984, 4996, 6.toByte()),
    IRON_ORE_5(6943, 21296, 6.toByte()),
    IRON_ORE_6(6944, 21297, 6.toByte()),
    IRON_ORE_7(9718, 32448, 6.toByte()),
    IRON_ORE_8(9719, 18954, 6.toByte()),
    IRON_ORE_9(9717, 32447, 6.toByte()),
    IRON_ORE_10(11956, 11557, 6.toByte()),
    IRON_ORE_11(11954, 11555, 6.toByte()),
    IRON_ORE_12(11955, 11556, 6.toByte()),
    IRON_ORE_13(14914, 14895, 6.toByte()),
    IRON_ORE_14(14913, 14894, 6.toByte()),
    IRON_ORE_15(14858, 25373, 6.toByte()),
    IRON_ORE_16(14857, 25372, 6.toByte()),
    IRON_ORE_17(14856, 25371, 6.toByte()),
    IRON_ORE_18(14900, 14894, 6.toByte()),
    IRON_ORE_19(14901, 14895, 6.toByte()),
    IRON_ORE_20(20423, 20444, 6.toByte()),
    IRON_ORE_21(20422, 20443, 6.toByte()),
    IRON_ORE_22(20425, 20407, 6.toByte()),
    IRON_ORE_23(20424, 20445, 6.toByte()),
    IRON_ORE_24(19002, 19005, 6.toByte()),
    IRON_ORE_25(19001, 19004, 6.toByte()),
    IRON_ORE_26(19000, 19003, 6.toByte()),
    IRON_ORE_27(21281, 21296, 6.toByte()),
    IRON_ORE_28(21283, 21298, 6.toByte()),
    IRON_ORE_29(21282, 21297, 6.toByte()),
    IRON_ORE_30(29221, 29218, 6.toByte()),
    IRON_ORE_31(29223, 29220, 6.toByte()),
    IRON_ORE_32(29222, 29219, 6.toByte()),
    IRON_ORE_33(32441, 33400, 6.toByte()),
    IRON_ORE_34(32443, 33402, 6.toByte()),
    IRON_ORE_35(32442, 33401, 6.toByte()),
    IRON_ORE_36(32452, 32448, 6.toByte()),
    IRON_ORE_37(32451, 32447, 6.toByte()),
    IRON_ORE_38(31073, 37650, 6.toByte()),
    IRON_ORE_39(31072, 37649, 6.toByte()),
    IRON_ORE_40(31071, 37639, 6.toByte()),
    IRON_ORE_41(37307, 11552, 6.toByte()),
    IRON_ORE_42(37309, 11554, 6.toByte()),
    IRON_ORE_43(37308, 11553, 6.toByte()),
    IRON_ORE_49(42034, 450, 6.toByte()),
    SILVER_ORE_0(2101, 452, 7.toByte()),
    SILVER_ORE_1(2100, 450, 7.toByte()),
    SILVER_ORE_2(6945, 21296, 7.toByte()),
    SILVER_ORE_3(6946, 21297, 7.toByte()),
    SILVER_ORE_4(9716, 18954, 7.toByte()),
    SILVER_ORE_5(9714, 32447, 7.toByte()),
    SILVER_ORE_6(9715, 32448, 7.toByte()),
    SILVER_ORE_7(11188, 21298, 7.toByte()),
    SILVER_ORE_8(11186, 21296, 7.toByte()),
    SILVER_ORE_9(11187, 21297, 7.toByte()),
    SILVER_ORE_10(15581, 14834, 7.toByte()),
    SILVER_ORE_11(15580, 14833, 7.toByte()),
    SILVER_ORE_12(15579, 14832, 7.toByte()),
    SILVER_ORE_13(16998, 14915, 7.toByte()),
    SILVER_ORE_14(16999, 14916, 7.toByte()),
    SILVER_ORE_15(17007, 14915, 7.toByte()),
    SILVER_ORE_16(17000, 31061, 7.toByte()),
    SILVER_ORE_17(17009, 31061, 7.toByte()),
    SILVER_ORE_18(17008, 14916, 7.toByte()),
    SILVER_ORE_19(17385, 32447, 7.toByte()),
    SILVER_ORE_20(17387, 18954, 7.toByte()),
    SILVER_ORE_21(17386, 32448, 7.toByte()),
    SILVER_ORE_22(29225, 29219, 7.toByte()),
    SILVER_ORE_23(29224, 29218, 7.toByte()),
    SILVER_ORE_24(29226, 29220, 7.toByte()),
    SILVER_ORE_25(32445, 33401, 7.toByte()),
    SILVER_ORE_26(32444, 33400, 7.toByte()),
    SILVER_ORE_27(32446, 33402, 7.toByte()),
    SILVER_ORE_28(31075, 37649, 7.toByte()),
    SILVER_ORE_29(31074, 37639, 7.toByte()),
    SILVER_ORE_30(31076, 37650, 7.toByte()),
    SILVER_ORE_31(37305, 11553, 7.toByte()),
    SILVER_ORE_32(37304, 11552, 7.toByte()),
    SILVER_ORE_33(37306, 11554, 7.toByte()),
    SILVER_ORE_34(37670, 11552, 7.toByte()),
    SILVER_ORE_35(11948, 11555, 7.toByte()),
    SILVER_ORE_36(11949, 11556, 7.toByte()),
    SILVER_ORE_37(11950, 11557, 7.toByte()),
    SILVER_ORE_38(2311, 11552, 7.toByte()),
    COAL_0(2097, 452, 8.toByte()),
    COAL_1(2096, 450, 8.toByte()),
    COAL_2(4985, 4994, 8.toByte()),
    COAL_3(4986, 4995, 8.toByte()),
    COAL_4(4987, 4996, 8.toByte()),
    COAL_5(4676, 450, 8.toByte()),
    COAL_6(10948, 10944, 8.toByte()),
    COAL_7(11964, 11556, 8.toByte()),
    COAL_8(11965, 11557, 8.toByte()),
    COAL_9(11963, 11555, 8.toByte()),
    COAL_10(11932, 11554, 8.toByte()),
    COAL_11(11930, 11552, 8.toByte()),
    COAL_12(11931, 11553, 8.toByte()),
    COAL_13(15246, 15249, 8.toByte()),
    COAL_14(15247, 15250, 8.toByte()),
    COAL_15(15248, 15251, 8.toByte()),
    COAL_16(14852, 25373, 8.toByte()),
    COAL_17(14851, 25372, 8.toByte()),
    COAL_18(14850, 25371, 8.toByte()),
    COAL_19(20410, 20443, 8.toByte()),
    COAL_20(20411, 20444, 8.toByte()),
    COAL_21(20412, 20445, 8.toByte()),
    COAL_22(20413, 20407, 8.toByte()),
    COAL_23(18999, 19005, 8.toByte()),
    COAL_24(18998, 19004, 8.toByte()),
    COAL_25(18997, 19003, 8.toByte()),
    COAL_26(21287, 21296, 8.toByte()),
    COAL_27(21289, 21298, 8.toByte()),
    COAL_28(21288, 21297, 8.toByte()),
    COAL_29(23565, 21298, 8.toByte()),
    COAL_30(23564, 21297, 8.toByte()),
    COAL_31(23563, 21296, 8.toByte()),
    COAL_32(29215, 29218, 8.toByte()),
    COAL_33(29217, 29220, 8.toByte()),
    COAL_34(29216, 29219, 8.toByte()),
    COAL_35(32426, 33400, 8.toByte()),
    COAL_36(32427, 33401, 8.toByte()),
    COAL_37(32428, 33402, 8.toByte()),
    COAL_38(32450, 32448, 8.toByte()),
    COAL_39(32449, 32447, 8.toByte()),
    COAL_40(31068, 37639, 8.toByte()),
    COAL_41(31069, 37649, 8.toByte()),
    COAL_42(31070, 37650, 8.toByte()),
    COAL_43(31168, 14833, 8.toByte()),
    COAL_44(31169, 14834, 8.toByte()),
    COAL_45(31167, 14832, 8.toByte()),
    COAL_46(37699, 21298, 8.toByte()),
    COAL_47(37698, 21297, 8.toByte()),
    COAL_48(37697, 21296, 8.toByte()),
    COAL_49(42035, 452, 8.toByte()),
    GOLD_ORE_0(2099, 452, 9.toByte()),
    GOLD_ORE_1(2098, 450, 9.toByte()),
    GOLD_ORE_2(2611, 21298, 9.toByte()),
    GOLD_ORE_3(2610, 21297, 9.toByte()),
    GOLD_ORE_4(2609, 21296, 9.toByte()),
    GOLD_ORE_5(9722, 18954, 9.toByte()),
    GOLD_ORE_6(9720, 32447, 9.toByte()),
    GOLD_ORE_7(9721, 32448, 9.toByte()),
    GOLD_ORE_8(11183, 21296, 9.toByte()),
    GOLD_ORE_9(11184, 21297, 9.toByte()),
    GOLD_ORE_10(11185, 21298, 9.toByte()),
    GOLD_ORE_11(11952, 11556, 9.toByte()),
    GOLD_ORE_12(11953, 11557, 9.toByte()),
    GOLD_ORE_13(11951, 11555, 9.toByte()),
    GOLD_ORE_14(15578, 14834, 9.toByte()),
    GOLD_ORE_15(15577, 14833, 9.toByte()),
    GOLD_ORE_16(15576, 14832, 9.toByte()),
    GOLD_ORE_17(17002, 14916, 9.toByte()),
    GOLD_ORE_18(17003, 31061, 9.toByte()),
    GOLD_ORE_19(17001, 14915, 9.toByte()),
    GOLD_ORE_20(21291, 21297, 9.toByte()),
    GOLD_ORE_21(21290, 21296, 9.toByte()),
    GOLD_ORE_22(21292, 21298, 9.toByte()),
    GOLD_ORE_23(32433, 33401, 9.toByte()),
    GOLD_ORE_24(32432, 33400, 9.toByte()),
    GOLD_ORE_25(32434, 33402, 9.toByte()),
    GOLD_ORE_26(31065, 37639, 9.toByte()),
    GOLD_ORE_27(31066, 37649, 9.toByte()),
    GOLD_ORE_28(31067, 37650, 9.toByte()),
    GOLD_ORE_29(37311, 11553, 9.toByte()),
    GOLD_ORE_30(37310, 11552, 9.toByte()),
    GOLD_ORE_31(37312, 11554, 9.toByte()),
    GOLD_ORE_32(37471, 15249, 9.toByte()),
    GOLD_ORE_33(37473, 15251, 9.toByte()),
    GOLD_ORE_34(37472, 15250, 9.toByte()),
    GOLD_ORE_49(42033, 452, 9.toByte()),
    MITHRIL_ORE_0(2103, 452, 10.toByte()),
    MITHRIL_ORE_1(2102, 450, 10.toByte()),
    MITHRIL_ORE_2(4988, 4994, 10.toByte()),
    MITHRIL_ORE_3(4989, 4995, 10.toByte()),
    MITHRIL_ORE_4(4990, 4996, 10.toByte()),
    MITHRIL_ORE_5(11943, 11553, 10.toByte()),
    MITHRIL_ORE_6(11942, 11552, 10.toByte()),
    MITHRIL_ORE_7(11945, 11555, 10.toByte()),
    MITHRIL_ORE_8(11944, 11554, 10.toByte()),
    MITHRIL_ORE_9(11947, 11557, 10.toByte()),
    MITHRIL_ORE_10(11946, 11556, 10.toByte()),
    MITHRIL_ORE_11(14855, 25373, 10.toByte()),
    MITHRIL_ORE_12(14854, 25372, 10.toByte()),
    MITHRIL_ORE_13(14853, 25371, 10.toByte()),
    MITHRIL_ORE_14(16687, 450, 10.toByte()),
    MITHRIL_ORE_15(20421, 20407, 10.toByte()),
    MITHRIL_ORE_16(20420, 20445, 10.toByte()),
    MITHRIL_ORE_17(20419, 20444, 10.toByte()),
    MITHRIL_ORE_18(20418, 20443, 10.toByte()),
    MITHRIL_ORE_19(19012, 19021, 10.toByte()),
    MITHRIL_ORE_20(19013, 19016, 10.toByte()),
    MITHRIL_ORE_21(19014, 19017, 10.toByte()),
    MITHRIL_ORE_22(21278, 21296, 10.toByte()),
    MITHRIL_ORE_23(21279, 21297, 10.toByte()),
    MITHRIL_ORE_24(21280, 21298, 10.toByte()),
    MITHRIL_ORE_25(25369, 10586, 10.toByte()),
    MITHRIL_ORE_26(25368, 10585, 10.toByte()),
    MITHRIL_ORE_27(25370, 10587, 10.toByte()),
    MITHRIL_ORE_28(29236, 29218, 10.toByte()),
    MITHRIL_ORE_29(29237, 29219, 10.toByte()),
    MITHRIL_ORE_30(29238, 29220, 10.toByte()),
    MITHRIL_ORE_31(32439, 33401, 10.toByte()),
    MITHRIL_ORE_32(32438, 33400, 10.toByte()),
    MITHRIL_ORE_33(32440, 33402, 10.toByte()),
    MITHRIL_ORE_34(31087, 37649, 10.toByte()),
    MITHRIL_ORE_35(31086, 37639, 10.toByte()),
    MITHRIL_ORE_36(31088, 37650, 10.toByte()),
    MITHRIL_ORE_37(31170, 14832, 10.toByte()),
    MITHRIL_ORE_38(31171, 14833, 10.toByte()),
    MITHRIL_ORE_39(31172, 14834, 10.toByte()),
    MITHRIL_ORE_40(37692, 21296, 10.toByte()),
    MITHRIL_ORE_41(37693, 21297, 10.toByte()),
    MITHRIL_ORE_42(37694, 21298, 10.toByte()),
    MITHRIL_ORE_49(42036, 452, 10.toByte()),
    ADAMANTITE_ORE_0(2105, 452, 11.toByte()),
    ADAMANTITE_ORE_1(2104, 450, 11.toByte()),
    ADAMANTITE_ORE_2(4991, 4994, 11.toByte()),
    ADAMANTITE_ORE_3(4992, 4995, 11.toByte()),
    ADAMANTITE_ORE_4(4993, 4996, 11.toByte()),
    ADAMANTITE_ORE_5(11941, 11554, 11.toByte()),
    ADAMANTITE_ORE_6(11940, 11553, 11.toByte()),
    ADAMANTITE_ORE_7(11939, 11552, 11.toByte()),
    ADAMANTITE_ORE_8(14864, 25373, 11.toByte()),
    ADAMANTITE_ORE_9(14863, 25372, 11.toByte()),
    ADAMANTITE_ORE_10(14862, 25371, 11.toByte()),
    ADAMANTITE_ORE_11(20417, 20407, 11.toByte()),
    ADAMANTITE_ORE_12(20416, 20445, 11.toByte()),
    ADAMANTITE_ORE_13(20414, 20443, 11.toByte()),
    ADAMANTITE_ORE_14(20415, 20444, 11.toByte()),
    ADAMANTITE_ORE_15(19020, 19017, 11.toByte()),
    ADAMANTITE_ORE_16(19018, 19021, 11.toByte()),
    ADAMANTITE_ORE_17(19019, 19016, 11.toByte()),
    ADAMANTITE_ORE_18(21275, 21296, 11.toByte()),
    ADAMANTITE_ORE_19(21276, 21297, 11.toByte()),
    ADAMANTITE_ORE_20(21277, 21298, 11.toByte()),
    ADAMANTITE_ORE_21(29233, 29218, 11.toByte()),
    ADAMANTITE_ORE_22(29234, 29219, 11.toByte()),
    ADAMANTITE_ORE_23(29235, 29220, 11.toByte()),
    ADAMANTITE_ORE_24(32435, 33400, 11.toByte()),
    ADAMANTITE_ORE_25(32437, 33402, 11.toByte()),
    ADAMANTITE_ORE_26(32436, 33401, 11.toByte()),
    ADAMANTITE_ORE_27(31083, 37639, 11.toByte()),
    ADAMANTITE_ORE_28(31085, 37650, 11.toByte()),
    ADAMANTITE_ORE_29(31084, 37649, 11.toByte()),
    ADAMANTITE_ORE_30(31173, 14832, 11.toByte()),
    ADAMANTITE_ORE_31(31174, 14833, 11.toByte()),
    ADAMANTITE_ORE_32(31175, 14834, 11.toByte()),
    ADAMANTITE_ORE_33(37468, 15249, 11.toByte()),
    ADAMANTITE_ORE_34(37469, 15250, 11.toByte()),
    ADAMANTITE_ORE_35(37470, 15251, 11.toByte()),
    ADAMANTITE_ORE_36(37689, 21296, 11.toByte()),
    ADAMANTITE_ORE_37(37690, 21297, 11.toByte()),
    ADAMANTITE_ORE_38(37691, 21298, 11.toByte()),
    ADAMANTITE_ORE_39(42037, 452, 11.toByte()),
    RUNITE_ORE_0(2107, 452, 12.toByte()),
    RUNITE_ORE_1(2106, 450, 12.toByte()),
    RUNITE_ORE_2(6669, 21296, 12.toByte()),
    RUNITE_ORE_3(6671, 21298, 12.toByte()),
    RUNITE_ORE_4(6670, 21297, 12.toByte()),
    RUNITE_ORE_5(14861, 25373, 12.toByte()),
    RUNITE_ORE_6(14860, 25372, 12.toByte()),
    RUNITE_ORE_7(14859, 25371, 12.toByte()),
    RUNITE_ORE_8(33079, 33401, 12.toByte()),
    RUNITE_ORE_9(33078, 33400, 12.toByte()),
    RUNITE_ORE_10(37208, 21296, 12.toByte()),
    RUNITE_ORE_11(37465, 15249, 12.toByte()),
    RUNITE_ORE_12(37466, 15250, 12.toByte()),
    RUNITE_ORE_13(37467, 15251, 12.toByte()),
    RUNITE_ORE_14(37695, 21297, 12.toByte()),
    RUNITE_ORE_15(37696, 21298, 12.toByte()),
    GEM_ROCK_0(23567, 21297, 13.toByte()),
    GEM_ROCK_1(23566, 21296, 13.toByte()),
    GEM_ROCK_2(23568, 21298, 13.toByte()),
    GEM_ROCK_3(23560, 25371, 13.toByte()),
    GEM_ROCK_4(23561, 25372, 13.toByte()),
    GEM_ROCK_5(23562, 21298, 13.toByte()),
    GEM_ROCK_6(9030, 9010, 13.toByte()),
    GEM_ROCK_7(9031, 9015, 13.toByte()),
    GEM_ROCK_8(9032, 9020, 13.toByte()),
    RUNE_ESSENCE_0(2491, -1, 14.toByte()),
    RUNE_ESSENCE_1(16684, -1, 14.toByte()),
    SANDSTONE(10946, 10944, 15.toByte()),
    GRANITE(10947, 10945, 16.toByte()),
    RUBIUM(29746, 29747, 17.toByte());

    var respawnRate: Int = 0
    var reward: Int = 0
    var level: Int = 0
    var rate: Double = 0.0
    var experience: Double = 0.0

    init {
        when (identifier.toInt() and 0xFF) {
            1, 2 -> {
                respawnRate = 4 or (8 shl 16)
                experience = 17.5
                rate = 0.05
                reward = if (identifier.toInt() == 1) 436 else 438
                level = 1
            }

            3 -> {
                respawnRate = 1 or (1 shl 16)
                experience = 5.0
                rate = 0.1
                reward = 434
                level = 1
            }

            4 -> {
                respawnRate = 10 or (20 shl 16)
                experience = 26.5
                rate = 0.2
                reward = 3211
                level = 10
            }

            5 -> {
                respawnRate = 10 or (20 shl 16)
                experience = 17.5
                rate = 0.2
                reward = 668
                level = 10
            }

            6 -> {
                respawnRate = 15 or (25 shl 16)
                experience = 35.0
                rate = 0.2
                reward = 440
                level = 15
            }

            7 -> {
                respawnRate = 100 or (200 shl 16)
                experience = 40.0
                rate = 0.3
                reward = 442
                level = 20
            }

            8 -> {
                respawnRate = 50 or (100 shl 16)
                experience = 50.0
                rate = 0.4
                reward = 453
                level = 30
            }

            9 -> {
                respawnRate = 100 or (200 shl 16)
                experience = 65.0
                rate = 0.6
                reward = 444
                level = 40
            }

            10 -> {
                respawnRate = 200 or (400 shl 16)
                experience = 80.0
                rate = 0.70
                reward = 447
                level = 55
            }

            11 -> {
                respawnRate = 400 or (800 shl 16)
                experience = 95.0
                rate = 0.85
                reward = 449
                level = 70
            }

            12 -> {
                respawnRate = 1250 or (2500 shl 16)
                experience = 125.0
                rate = 0.95
                reward = 451
                level = 85
            }

            13 -> {
                respawnRate = 166 or (175 shl 16)
                experience = 65.0
                rate = 0.95
                reward = 1625
                level = 40
            }

            14 -> {
                respawnRate = 1 or (1 shl 16)
                experience = 5.0
                rate = 0.1
                reward = 1436
                level = 1
            }

            15 -> {
                respawnRate = 30 or (60 shl 16)
                experience = 30.0
                rate = 0.2
                reward = 6971
                level = 35
            }

            16 -> {
                respawnRate = 10 or (20 shl 16)
                experience = 50.0
                rate = 0.2
                reward = 6979
                level = 45
            }

            17 -> {
                respawnRate = 50 or (100 shl 16)
                experience = 17.5
                rate = 0.6
                reward = 12630
                level = 46
            }
        }
    }

    val rewardAmount: Int
        get() = 1
    val minimumRespawn: Int
        get() = respawnRate and 0xFFFF
    val maximumRespawn: Int
        get() = (respawnRate shr 16) and 0xFFFF
    val respawnDuration: Int
        get() {
            val minimum = respawnRate and 0xFFFF
            val maximum = (respawnRate shr 16) and 0xFFFF
            val playerRatio = Configuration.MAX_PLAYERS.toDouble() / players.size
            return (minimum + ((maximum - minimum) / playerRatio)).toInt()
        }


    companion object {
        var gemRockGems: MutableList<WeightedChanceItem> = ArrayList(20)

        init {
            gemRockGems.add(WeightedChanceItem(1625, 1, 60))
            gemRockGems.add(WeightedChanceItem(1627, 1, 30))
            gemRockGems.add(WeightedChanceItem(1629, 1, 15))
            gemRockGems.add(WeightedChanceItem(1623, 1, 9))
            gemRockGems.add(WeightedChanceItem(1621, 1, 5))
            gemRockGems.add(WeightedChanceItem(1619, 1, 5))
            gemRockGems.add(WeightedChanceItem(1617, 1, 4))
        }

        private val NODE_MAP = HashMap<Int, MiningNode>()
        private val EMPTY_MAP = HashMap<Int, Int?>()

        init {
            for (node in values()) {
                NODE_MAP.putIfAbsent(node.id, node)
            }
            for (node in values()) {
                EMPTY_MAP.putIfAbsent(node.emptyId, node.id)
            }
        }

        fun forId(id: Int): MiningNode? {
            return NODE_MAP[id]
        }

        fun isEmpty(id: Int): Boolean {
            return EMPTY_MAP[id] != null
        }
    }
}
