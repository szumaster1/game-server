package content.global.skill.gathering.mining

import core.Configuration
import core.game.node.item.WeightedChanceItem
import core.game.world.repository.Repository.players

/**
 * Represents the mining node.
 */
enum class MiningNode(var id: Int, var emptyId: Int, var identifier: Byte) {
    COPPER_ORE_0(
        id = 2090,
        emptyId = 450,
        identifier = 1.toByte()
    ),
    COPPER_ORE_1(
        id = 2091,
        emptyId = 452,
        identifier = 1.toByte()
    ),
    COPPER_ORE_2(
        id = 4976,
        emptyId = 4994,
        identifier = 1.toByte()
    ),
    COPPER_ORE_3(
        id = 4977,
        emptyId = 4995,
        identifier = 1.toByte()
    ),
    COPPER_ORE_4(
        id = 4978,
        emptyId = 4996,
        identifier = 1.toByte()
    ),
    COPPER_ORE_5(
        id = 9710,
        emptyId = 18954,
        identifier = 1.toByte()
    ),
    COPPER_ORE_6(
        id = 9709,
        emptyId = 32448,
        identifier = 1.toByte()
    ),
    COPPER_ORE_7(
        id = 9708,
        emptyId = 32447,
        identifier = 1.toByte()
    ),
    COPPER_ORE_8(
        id = 11960,
        emptyId = 11555,
        identifier = 1.toByte()
    ),
    COPPER_ORE_9(
        id = 11961,
        emptyId = 11556,
        identifier = 1.toByte()
    ),
    COPPER_ORE_10(
        id = 11962,
        emptyId = 11557,
        identifier = 1.toByte()
    ),
    COPPER_ORE_11(
        id = 11937,
        emptyId = 11553,
        identifier = 1.toByte()
    ),
    COPPER_ORE_12(
        id = 11936,
        emptyId = 11552,
        identifier = 1.toByte()
    ),
    COPPER_ORE_13(
        id = 11938,
        emptyId = 11554,
        identifier = 1.toByte()
    ),
    COPPER_ORE_14(
        id = 12746,
        emptyId = 450,
        identifier = 1.toByte()
    ),
    COPPER_ORE_15(
        id = 14906,
        emptyId = 14894,
        identifier = 1.toByte()
    ),
    COPPER_ORE_16(
        id = 14907,
        emptyId = 14895,
        identifier = 1.toByte()
    ),
    COPPER_ORE_17(
        id = 20448,
        emptyId = 20445,
        identifier = 1.toByte()
    ),
    COPPER_ORE_18(
        id = 20451,
        emptyId = 20445,
        identifier = 1.toByte()
    ),
    COPPER_ORE_19(
        id = 20446,
        emptyId = 20443,
        identifier = 1.toByte()
    ),
    COPPER_ORE_20(
        id = 20447,
        emptyId = 20444,
        identifier = 1.toByte()
    ),
    COPPER_ORE_21(
        id = 20408,
        emptyId = 20407,
        identifier = 1.toByte()
    ),
    COPPER_ORE_22(
        id = 18993,
        emptyId = 19005,
        identifier = 1.toByte()
    ),
    COPPER_ORE_23(
        id = 18992,
        emptyId = 19004,
        identifier = 1.toByte()
    ),
    COPPER_ORE_24(
        id = 19007,
        emptyId = 19016,
        identifier = 1.toByte()
    ),
    COPPER_ORE_25(
        id = 19006,
        emptyId = 19021,
        identifier = 1.toByte()
    ),
    COPPER_ORE_26(
        id = 18991,
        emptyId = 19003,
        identifier = 1.toByte()
    ),
    COPPER_ORE_27(
        id = 19008,
        emptyId = 19017,
        identifier = 1.toByte()
    ),
    COPPER_ORE_28(
        id = 21285,
        emptyId = 21297,
        identifier = 1.toByte()
    ),
    COPPER_ORE_29(
        id = 21284,
        emptyId = 21296,
        identifier = 1.toByte()
    ),
    COPPER_ORE_30(
        id = 21286,
        emptyId = 21298,
        identifier = 1.toByte()
    ),
    COPPER_ORE_31(
        id = 29231,
        emptyId = 29219,
        identifier = 1.toByte()
    ),
    COPPER_ORE_32(
        id = 29230,
        emptyId = 29218,
        identifier = 1.toByte()
    ),
    COPPER_ORE_33(
        id = 29232,
        emptyId = 29220,
        identifier = 1.toByte()
    ),
    COPPER_ORE_34(
        id = 31082,
        emptyId = 37650,
        identifier = 1.toByte()
    ),
    COPPER_ORE_35(
        id = 31081,
        emptyId = 37649,
        identifier = 1.toByte()
    ),
    COPPER_ORE_36(
        id = 31080,
        emptyId = 37639,
        identifier = 1.toByte()
    ),
    COPPER_ORE_37(
        id = 37647,
        emptyId = 37650,
        identifier = 1.toByte()
    ),
    COPPER_ORE_38(
        id = 37646,
        emptyId = 37649,
        identifier = 1.toByte()
    ),
    COPPER_ORE_39(
        id = 37645,
        emptyId = 37639,
        identifier = 1.toByte()
    ),
    COPPER_ORE_40(
        id = 37637,
        emptyId = 37639,
        identifier = 1.toByte()
    ),
    COPPER_ORE_41(
        id = 37688,
        emptyId = 21298,
        identifier = 1.toByte()
    ),
    COPPER_ORE_42(
        id = 37686,
        emptyId = 21296,
        identifier = 1.toByte()
    ),
    COPPER_ORE_43(
        id = 37687,
        emptyId = 21297,
        identifier = 1.toByte()
    ),
    COPPER_ORE_44(
        id = 3042,
        emptyId = 11552,
        identifier = 1.toByte()
    ),
    TIN_ORE_0(
        id = 2094,
        emptyId = 450,
        identifier = 2.toByte()
    ),
    TIN_ORE_1(
        id = 2095,
        emptyId = 452,
        identifier = 2.toByte()
    ),
    TIN_ORE_2(
        id = 3043,
        emptyId = 11552,
        identifier = 2.toByte()
    ),
    TIN_ORE_3(
        id = 4979,
        emptyId = 4994,
        identifier = 2.toByte()
    ),
    TIN_ORE_4(
        id = 4980,
        emptyId = 4995,
        identifier = 2.toByte()
    ),
    TIN_ORE_5(
        id = 4981,
        emptyId = 4996,
        identifier = 2.toByte()
    ),
    TIN_ORE_6(
        id = 11957,
        emptyId = 11555,
        identifier = 2.toByte()
    ),
    TIN_ORE_7(
        id = 11958,
        emptyId = 11556,
        identifier = 2.toByte()
    ),
    TIN_ORE_8(
        id = 11959,
        emptyId = 11557,
        identifier = 2.toByte()
    ),
    TIN_ORE_9(
        id = 11934,
        emptyId = 11553,
        identifier = 2.toByte()
    ),
    TIN_ORE_10(
        id = 11935,
        emptyId = 11554,
        identifier = 2.toByte()
    ),
    TIN_ORE_11(
        id = 11933,
        emptyId = 11552,
        identifier = 2.toByte()
    ),
    TIN_ORE_12(
        id = 14902,
        emptyId = 14894,
        identifier = 2.toByte()
    ),
    TIN_ORE_13(
        id = 14903,
        emptyId = 14895,
        identifier = 2.toByte()
    ),
    TIN_ORE_14(
        id = 18995,
        emptyId = 19004,
        identifier = 2.toByte()
    ),
    TIN_ORE_15(
        id = 18994,
        emptyId = 19003,
        identifier = 2.toByte()
    ),
    TIN_ORE_16(
        id = 18996,
        emptyId = 19005,
        identifier = 2.toByte()
    ),
    TIN_ORE_17(
        id = 19025,
        emptyId = 19016,
        identifier = 2.toByte()
    ),
    TIN_ORE_18(
        id = 19024,
        emptyId = 19021,
        identifier = 2.toByte()
    ),
    TIN_ORE_19(
        id = 19026,
        emptyId = 19017,
        identifier = 2.toByte()
    ),
    TIN_ORE_20(
        id = 21293,
        emptyId = 21296,
        identifier = 2.toByte()
    ),
    TIN_ORE_21(
        id = 21295,
        emptyId = 21298,
        identifier = 2.toByte()
    ),
    TIN_ORE_22(
        id = 21294,
        emptyId = 21297,
        identifier = 2.toByte()
    ),
    TIN_ORE_23(
        id = 29227,
        emptyId = 29218,
        identifier = 2.toByte()
    ),
    TIN_ORE_24(
        id = 29229,
        emptyId = 29220,
        identifier = 2.toByte()
    ),
    TIN_ORE_25(
        id = 29228,
        emptyId = 29219,
        identifier = 2.toByte()
    ),
    TIN_ORE_26(
        id = 31079,
        emptyId = 37650,
        identifier = 2.toByte()
    ),
    TIN_ORE_27(
        id = 31078,
        emptyId = 37649,
        identifier = 2.toByte()
    ),
    TIN_ORE_28(
        id = 31077,
        emptyId = 37639,
        identifier = 2.toByte()
    ),
    TIN_ORE_29(
        id = 37644,
        emptyId = 37650,
        identifier = 2.toByte()
    ),
    TIN_ORE_30(
        id = 37643,
        emptyId = 37649,
        identifier = 2.toByte()
    ),
    TIN_ORE_31(
        id = 37642,
        emptyId = 37639,
        identifier = 2.toByte()
    ),
    TIN_ORE_32(
        id = 37638,
        emptyId = 37639,
        identifier = 2.toByte()
    ),
    TIN_ORE_33(
        id = 37685,
        emptyId = 21298,
        identifier = 2.toByte()
    ),
    CLAY_0(
        id = 2109,
        emptyId = 452,
        identifier = 3.toByte()
    ),
    CLAY_1(
        id = 2108,
        emptyId = 450,
        identifier = 3.toByte()
    ),
    CLAY_2(
        id = 9712,
        emptyId = 32448,
        identifier = 3.toByte()
    ),
    CLAY_3(
        id = 9713,
        emptyId = 18954,
        identifier = 3.toByte()
    ),
    CLAY_4(
        id = 9711,
        emptyId = 32447,
        identifier = 3.toByte()
    ),
    CLAY_5(
        id = 10949,
        emptyId = 10945,
        identifier = 3.toByte()
    ),
    CLAY_6(
        id = 11190,
        emptyId = 21297,
        identifier = 3.toByte()
    ),
    CLAY_7(
        id = 11191,
        emptyId = 21298,
        identifier = 3.toByte()
    ),
    CLAY_8(
        id = 11189,
        emptyId = 21296,
        identifier = 3.toByte()
    ),
    CLAY_9(
        id = 12942,
        emptyId = 4995,
        identifier = 3.toByte()
    ),
    CLAY_10(
        id = 12943,
        emptyId = 4996,
        identifier = 3.toByte()
    ),
    CLAY_11(
        id = 12941,
        emptyId = 4994,
        identifier = 3.toByte()
    ),
    CLAY_12(
        id = 14904,
        emptyId = 14894,
        identifier = 3.toByte()
    ),
    CLAY_13(
        id = 14905,
        emptyId = 14895,
        identifier = 3.toByte()
    ),
    CLAY_14(
        id = 15505,
        emptyId = 11557,
        identifier = 3.toByte()
    ),
    CLAY_15(
        id = 15504,
        emptyId = 11556,
        identifier = 3.toByte()
    ),
    CLAY_16(
        id = 15503,
        emptyId = 11555,
        identifier = 3.toByte()
    ),
    CLAY_17(
        id = 20449,
        emptyId = 20443,
        identifier = 3.toByte()
    ),
    CLAY_18(
        id = 20450,
        emptyId = 20444,
        identifier = 3.toByte()
    ),
    CLAY_19(
        id = 20409,
        emptyId = 20407,
        identifier = 3.toByte()
    ),
    CLAY_20(
        id = 32429,
        emptyId = 33400,
        identifier = 3.toByte()
    ),
    CLAY_21(
        id = 32430,
        emptyId = 33401,
        identifier = 3.toByte()
    ),
    CLAY_22(
        id = 32431,
        emptyId = 33402,
        identifier = 3.toByte()
    ),
    CLAY_23(
        id = 31062,
        emptyId = 37639,
        identifier = 3.toByte()
    ),
    CLAY_24(
        id = 31063,
        emptyId = 37649,
        identifier = 3.toByte()
    ),
    CLAY_25(
        id = 31064,
        emptyId = 37650,
        identifier = 3.toByte()
    ),
    LIMESTONE_0(
        id = 4027,
        emptyId = 4028,
        identifier = 4.toByte()
    ),
    LIMESTONE_1(
        id = 4028,
        emptyId = 4029,
        identifier = 4.toByte()
    ),
    LIMESTONE_2(
        id = 4029,
        emptyId = 4030,
        identifier = 4.toByte()
    ),
    LIMESTONE_3(
        id = 4030,
        emptyId = 4027,
        identifier = 4.toByte()
    ),
    BLURITE_ORE_0(
        id = 33220,
        emptyId = 33222,
        identifier = 5.toByte()
    ),
    BLURITE_ORE_1(
        id = 33221,
        emptyId = 33223,
        identifier = 5.toByte()
    ),
    IRON_ORE_0(
        id = 2092,
        emptyId = 450,
        identifier = 6.toByte()
    ),
    IRON_ORE_1(
        id = 2093,
        emptyId = 452,
        identifier = 6.toByte()
    ),
    IRON_ORE_2(
        id = 4982,
        emptyId = 4994,
        identifier = 6.toByte()
    ),
    IRON_ORE_3(
        id = 4983,
        emptyId = 4995,
        identifier = 6.toByte()
    ),
    IRON_ORE_4(
        id = 4984,
        emptyId = 4996,
        identifier = 6.toByte()
    ),
    IRON_ORE_5(
        id = 6943,
        emptyId = 21296,
        identifier = 6.toByte()
    ),
    IRON_ORE_6(
        id = 6944,
        emptyId = 21297,
        identifier = 6.toByte()
    ),
    IRON_ORE_7(
        id = 9718,
        emptyId = 32448,
        identifier = 6.toByte()
    ),
    IRON_ORE_8(
        id = 9719,
        emptyId = 18954,
        identifier = 6.toByte()
    ),
    IRON_ORE_9(
        id = 9717,
        emptyId = 32447,
        identifier = 6.toByte()
    ),
    IRON_ORE_10(
        id = 11956,
        emptyId = 11557,
        identifier = 6.toByte()
    ),
    IRON_ORE_11(
        id = 11954,
        emptyId = 11555,
        identifier = 6.toByte()
    ),
    IRON_ORE_12(
        id = 11955,
        emptyId = 11556,
        identifier = 6.toByte()
    ),
    IRON_ORE_13(
        id = 14914,
        emptyId = 14895,
        identifier = 6.toByte()
    ),
    IRON_ORE_14(
        id = 14913,
        emptyId = 14894,
        identifier = 6.toByte()
    ),
    IRON_ORE_15(
        id = 14858,
        emptyId = 25373,
        identifier = 6.toByte()
    ),
    IRON_ORE_16(
        id = 14857,
        emptyId = 25372,
        identifier = 6.toByte()
    ),
    IRON_ORE_17(
        id = 14856,
        emptyId = 25371,
        identifier = 6.toByte()
    ),
    IRON_ORE_18(
        id = 14900,
        emptyId = 14894,
        identifier = 6.toByte()
    ),
    IRON_ORE_19(
        id = 14901,
        emptyId = 14895,
        identifier = 6.toByte()
    ),
    IRON_ORE_20(
        id = 20423,
        emptyId = 20444,
        identifier = 6.toByte()
    ),
    IRON_ORE_21(
        id = 20422,
        emptyId = 20443,
        identifier = 6.toByte()
    ),
    IRON_ORE_22(
        id = 20425,
        emptyId = 20407,
        identifier = 6.toByte()
    ),
    IRON_ORE_23(
        id = 20424,
        emptyId = 20445,
        identifier = 6.toByte()
    ),
    IRON_ORE_24(
        id = 19002,
        emptyId = 19005,
        identifier = 6.toByte()
    ),
    IRON_ORE_25(
        id = 19001,
        emptyId = 19004,
        identifier = 6.toByte()
    ),
    IRON_ORE_26(
        id = 19000,
        emptyId = 19003,
        identifier = 6.toByte()
    ),
    IRON_ORE_27(
        id = 21281,
        emptyId = 21296,
        identifier = 6.toByte()
    ),
    IRON_ORE_28(
        id = 21283,
        emptyId = 21298,
        identifier = 6.toByte()
    ),
    IRON_ORE_29(
        id = 21282,
        emptyId = 21297,
        identifier = 6.toByte()
    ),
    IRON_ORE_30(
        id = 29221,
        emptyId = 29218,
        identifier = 6.toByte()
    ),
    IRON_ORE_31(
        id = 29223,
        emptyId = 29220,
        identifier = 6.toByte()
    ),
    IRON_ORE_32(
        id = 29222,
        emptyId = 29219,
        identifier = 6.toByte()
    ),
    IRON_ORE_33(
        id = 32441,
        emptyId = 33400,
        identifier = 6.toByte()
    ),
    IRON_ORE_34(
        id = 32443,
        emptyId = 33402,
        identifier = 6.toByte()
    ),
    IRON_ORE_35(
        id = 32442,
        emptyId = 33401,
        identifier = 6.toByte()
    ),
    IRON_ORE_36(
        id = 32452,
        emptyId = 32448,
        identifier = 6.toByte()
    ),
    IRON_ORE_37(
        id = 32451,
        emptyId = 32447,
        identifier = 6.toByte()
    ),
    IRON_ORE_38(
        id = 31073,
        emptyId = 37650,
        identifier = 6.toByte()
    ),
    IRON_ORE_39(
        id = 31072,
        emptyId = 37649,
        identifier = 6.toByte()
    ),
    IRON_ORE_40(
        id = 31071,
        emptyId = 37639,
        identifier = 6.toByte()
    ),
    IRON_ORE_41(
        id = 37307,
        emptyId = 11552,
        identifier = 6.toByte()
    ),
    IRON_ORE_42(
        id = 37309,
        emptyId = 11554,
        identifier = 6.toByte()
    ),
    IRON_ORE_43(
        id = 37308,
        emptyId = 11553,
        identifier = 6.toByte()
    ),
    IRON_ORE_49(
        id = 42034,
        emptyId = 450,
        identifier = 6.toByte()
    ),
    SILVER_ORE_0(
        id = 2101,
        emptyId = 452,
        identifier = 7.toByte()
    ),
    SILVER_ORE_1(
        id = 2100,
        emptyId = 450,
        identifier = 7.toByte()
    ),
    SILVER_ORE_2(
        id = 6945,
        emptyId = 21296,
        identifier = 7.toByte()
    ),
    SILVER_ORE_3(
        id = 6946,
        emptyId = 21297,
        identifier = 7.toByte()
    ),
    SILVER_ORE_4(
        id = 9716,
        emptyId = 18954,
        identifier = 7.toByte()
    ),
    SILVER_ORE_5(
        id = 9714,
        emptyId = 32447,
        identifier = 7.toByte()
    ),
    SILVER_ORE_6(
        id = 9715,
        emptyId = 32448,
        identifier = 7.toByte()
    ),
    SILVER_ORE_7(
        id = 11188,
        emptyId = 21298,
        identifier = 7.toByte()
    ),
    SILVER_ORE_8(
        id = 11186,
        emptyId = 21296,
        identifier = 7.toByte()
    ),
    SILVER_ORE_9(
        id = 11187,
        emptyId = 21297,
        identifier = 7.toByte()
    ),
    SILVER_ORE_10(
        id = 15581,
        emptyId = 14834,
        identifier = 7.toByte()
    ),
    SILVER_ORE_11(
        id = 15580,
        emptyId = 14833,
        identifier = 7.toByte()
    ),
    SILVER_ORE_12(
        id = 15579,
        emptyId = 14832,
        identifier = 7.toByte()
    ),
    SILVER_ORE_13(
        id = 16998,
        emptyId = 14915,
        identifier = 7.toByte()
    ),
    SILVER_ORE_14(
        id = 16999,
        emptyId = 14916,
        identifier = 7.toByte()
    ),
    SILVER_ORE_15(
        id = 17007,
        emptyId = 14915,
        identifier = 7.toByte()
    ),
    SILVER_ORE_16(
        id = 17000,
        emptyId = 31061,
        identifier = 7.toByte()
    ),
    SILVER_ORE_17(
        id = 17009,
        emptyId = 31061,
        identifier = 7.toByte()
    ),
    SILVER_ORE_18(
        id = 17008,
        emptyId = 14916,
        identifier = 7.toByte()
    ),
    SILVER_ORE_19(
        id = 17385,
        emptyId = 32447,
        identifier = 7.toByte()
    ),
    SILVER_ORE_20(
        id = 17387,
        emptyId = 18954,
        identifier = 7.toByte()
    ),
    SILVER_ORE_21(
        id = 17386,
        emptyId = 32448,
        identifier = 7.toByte()
    ),
    SILVER_ORE_22(
        id = 29225,
        emptyId = 29219,
        identifier = 7.toByte()
    ),
    SILVER_ORE_23(
        id = 29224,
        emptyId = 29218,
        identifier = 7.toByte()
    ),
    SILVER_ORE_24(
        id = 29226,
        emptyId = 29220,
        identifier = 7.toByte()
    ),
    SILVER_ORE_25(
        id = 32445,
        emptyId = 33401,
        identifier = 7.toByte()
    ),
    SILVER_ORE_26(
        id = 32444,
        emptyId = 33400,
        identifier = 7.toByte()
    ),
    SILVER_ORE_27(
        id = 32446,
        emptyId = 33402,
        identifier = 7.toByte()
    ),
    SILVER_ORE_28(
        id = 31075,
        emptyId = 37649,
        identifier = 7.toByte()
    ),
    SILVER_ORE_29(
        id = 31074,
        emptyId = 37639,
        identifier = 7.toByte()
    ),
    SILVER_ORE_30(
        id = 31076,
        emptyId = 37650,
        identifier = 7.toByte()
    ),
    SILVER_ORE_31(
        id = 37305,
        emptyId = 11553,
        identifier = 7.toByte()
    ),
    SILVER_ORE_32(
        id = 37304,
        emptyId = 11552,
        identifier = 7.toByte()
    ),
    SILVER_ORE_33(
        id = 37306,
        emptyId = 11554,
        identifier = 7.toByte()
    ),
    SILVER_ORE_34(
        id = 37670,
        emptyId = 11552,
        identifier = 7.toByte()
    ),
    SILVER_ORE_35(
        id = 11948,
        emptyId = 11555,
        identifier = 7.toByte()
    ),
    SILVER_ORE_36(
        id = 11949,
        emptyId = 11556,
        identifier = 7.toByte()
    ),
    SILVER_ORE_37(
        id = 11950,
        emptyId = 11557,
        identifier = 7.toByte()
    ),
    SILVER_ORE_38(
        id = 2311,
        emptyId = 11552,
        identifier = 7.toByte()
    ),
    COAL_0(
        id = 2097,
        emptyId = 452,
        identifier = 8.toByte()
    ),
    COAL_1(
        id = 2096,
        emptyId = 450,
        identifier = 8.toByte()
    ),
    COAL_2(
        id = 4985,
        emptyId = 4994,
        identifier = 8.toByte()
    ),
    COAL_3(
        id = 4986,
        emptyId = 4995,
        identifier = 8.toByte()
    ),
    COAL_4(
        id = 4987,
        emptyId = 4996,
        identifier = 8.toByte()
    ),
    COAL_5(
        id = 4676,
        emptyId = 450,
        identifier = 8.toByte()
    ),
    COAL_6(
        id = 10948,
        emptyId = 10944,
        identifier = 8.toByte()
    ),
    COAL_7(
        id = 11964,
        emptyId = 11556,
        identifier = 8.toByte()
    ),
    COAL_8(
        id = 11965,
        emptyId = 11557,
        identifier = 8.toByte()
    ),
    COAL_9(
        id = 11963,
        emptyId = 11555,
        identifier = 8.toByte()
    ),
    COAL_10(
        id = 11932,
        emptyId = 11554,
        identifier = 8.toByte()
    ),
    COAL_11(
        id = 11930,
        emptyId = 11552,
        identifier = 8.toByte()
    ),
    COAL_12(
        id = 11931,
        emptyId = 11553,
        identifier = 8.toByte()
    ),
    COAL_13(
        id = 15246,
        emptyId = 15249,
        identifier = 8.toByte()
    ),
    COAL_14(
        id = 15247,
        emptyId = 15250,
        identifier = 8.toByte()
    ),
    COAL_15(
        id = 15248,
        emptyId = 15251,
        identifier = 8.toByte()
    ),
    COAL_16(
        id = 14852,
        emptyId = 25373,
        identifier = 8.toByte()
    ),
    COAL_17(
        id = 14851,
        emptyId = 25372,
        identifier = 8.toByte()
    ),
    COAL_18(
        id = 14850,
        emptyId = 25371,
        identifier = 8.toByte()
    ),
    COAL_19(
        id = 20410,
        emptyId = 20443,
        identifier = 8.toByte()
    ),
    COAL_20(
        id = 20411,
        emptyId = 20444,
        identifier = 8.toByte()
    ),
    COAL_21(
        id = 20412,
        emptyId = 20445,
        identifier = 8.toByte()
    ),
    COAL_22(
        id = 20413,
        emptyId = 20407,
        identifier = 8.toByte()
    ),
    COAL_23(
        id = 18999,
        emptyId = 19005,
        identifier = 8.toByte()
    ),
    COAL_24(
        id = 18998,
        emptyId = 19004,
        identifier = 8.toByte()
    ),
    COAL_25(
        id = 18997,
        emptyId = 19003,
        identifier = 8.toByte()
    ),
    COAL_26(
        id = 21287,
        emptyId = 21296,
        identifier = 8.toByte()
    ),
    COAL_27(
        id = 21289,
        emptyId = 21298,
        identifier = 8.toByte()
    ),
    COAL_28(
        id = 21288,
        emptyId = 21297,
        identifier = 8.toByte()
    ),
    COAL_29(
        id = 23565,
        emptyId = 21298,
        identifier = 8.toByte()
    ),
    COAL_30(
        id = 23564,
        emptyId = 21297,
        identifier = 8.toByte()
    ),
    COAL_31(
        id = 23563,
        emptyId = 21296,
        identifier = 8.toByte()
    ),
    COAL_32(
        id = 29215,
        emptyId = 29218,
        identifier = 8.toByte()
    ),
    COAL_33(
        id = 29217,
        emptyId = 29220,
        identifier = 8.toByte()
    ),
    COAL_34(
        id = 29216,
        emptyId = 29219,
        identifier = 8.toByte()
    ),
    COAL_35(
        id = 32426,
        emptyId = 33400,
        identifier = 8.toByte()
    ),
    COAL_36(
        id = 32427,
        emptyId = 33401,
        identifier = 8.toByte()
    ),
    COAL_37(
        id = 32428,
        emptyId = 33402,
        identifier = 8.toByte()
    ),
    COAL_38(
        id = 32450,
        emptyId = 32448,
        identifier = 8.toByte()
    ),
    COAL_39(
        id = 32449,
        emptyId = 32447,
        identifier = 8.toByte()
    ),
    COAL_40(
        id = 31068,
        emptyId = 37639,
        identifier = 8.toByte()
    ),
    COAL_41(
        id = 31069,
        emptyId = 37649,
        identifier = 8.toByte()
    ),
    COAL_42(
        id = 31070,
        emptyId = 37650,
        identifier = 8.toByte()
    ),
    COAL_43(
        id = 31168,
        emptyId = 14833,
        identifier = 8.toByte()
    ),
    COAL_44(
        id = 31169,
        emptyId = 14834,
        identifier = 8.toByte()
    ),
    COAL_45(
        id = 31167,
        emptyId = 14832,
        identifier = 8.toByte()
    ),
    COAL_46(
        id = 37699,
        emptyId = 21298,
        identifier = 8.toByte()
    ),
    COAL_47(
        id = 37698,
        emptyId = 21297,
        identifier = 8.toByte()
    ),
    COAL_48(
        id = 37697,
        emptyId = 21296,
        identifier = 8.toByte()
    ),
    COAL_49(
        id = 42035,
        emptyId = 452,
        identifier = 8.toByte()
    ),
    GOLD_ORE_0(
        id = 2099,
        emptyId = 452,
        identifier = 9.toByte()
    ),
    GOLD_ORE_1(
        id = 2098,
        emptyId = 450,
        identifier = 9.toByte()
    ),
    GOLD_ORE_2(
        id = 2611,
        emptyId = 21298,
        identifier = 9.toByte()
    ),
    GOLD_ORE_3(
        id = 2610,
        emptyId = 21297,
        identifier = 9.toByte()
    ),
    GOLD_ORE_4(
        id = 2609,
        emptyId = 21296,
        identifier = 9.toByte()
    ),
    GOLD_ORE_5(
        id = 9722,
        emptyId = 18954,
        identifier = 9.toByte()
    ),
    GOLD_ORE_6(
        id = 9720,
        emptyId = 32447,
        identifier = 9.toByte()
    ),
    GOLD_ORE_7(
        id = 9721,
        emptyId = 32448,
        identifier = 9.toByte()
    ),
    GOLD_ORE_8(
        id = 11183,
        emptyId = 21296,
        identifier = 9.toByte()
    ),
    GOLD_ORE_9(
        id = 11184,
        emptyId = 21297,
        identifier = 9.toByte()
    ),
    GOLD_ORE_10(
        id = 11185,
        emptyId = 21298,
        identifier = 9.toByte()
    ),
    GOLD_ORE_11(
        id = 11952,
        emptyId = 11556,
        identifier = 9.toByte()
    ),
    GOLD_ORE_12(
        id = 11953,
        emptyId = 11557,
        identifier = 9.toByte()
    ),
    GOLD_ORE_13(
        id = 11951,
        emptyId = 11555,
        identifier = 9.toByte()
    ),
    GOLD_ORE_14(
        id = 15578,
        emptyId = 14834,
        identifier = 9.toByte()
    ),
    GOLD_ORE_15(
        id = 15577,
        emptyId = 14833,
        identifier = 9.toByte()
    ),
    GOLD_ORE_16(
        id = 15576,
        emptyId = 14832,
        identifier = 9.toByte()
    ),
    GOLD_ORE_17(
        id = 17002,
        emptyId = 14916,
        identifier = 9.toByte()
    ),
    GOLD_ORE_18(
        id = 17003,
        emptyId = 31061,
        identifier = 9.toByte()
    ),
    GOLD_ORE_19(
        id = 17001,
        emptyId = 14915,
        identifier = 9.toByte()
    ),
    GOLD_ORE_20(
        id = 21291,
        emptyId = 21297,
        identifier = 9.toByte()
    ),
    GOLD_ORE_21(
        id = 21290,
        emptyId = 21296,
        identifier = 9.toByte()
    ),
    GOLD_ORE_22(
        id = 21292,
        emptyId = 21298,
        identifier = 9.toByte()
    ),
    GOLD_ORE_23(
        id = 32433,
        emptyId = 33401,
        identifier = 9.toByte()
    ),
    GOLD_ORE_24(
        id = 32432,
        emptyId = 33400,
        identifier = 9.toByte()
    ),
    GOLD_ORE_25(
        id = 32434,
        emptyId = 33402,
        identifier = 9.toByte()
    ),
    GOLD_ORE_26(
        id = 31065,
        emptyId = 37639,
        identifier = 9.toByte()
    ),
    GOLD_ORE_27(
        id = 31066,
        emptyId = 37649,
        identifier = 9.toByte()
    ),
    GOLD_ORE_28(
        id = 31067,
        emptyId = 37650,
        identifier = 9.toByte()
    ),
    GOLD_ORE_29(
        id = 37311,
        emptyId = 11553,
        identifier = 9.toByte()
    ),
    GOLD_ORE_30(
        id = 37310,
        emptyId = 11552,
        identifier = 9.toByte()
    ),
    GOLD_ORE_31(
        id = 37312,
        emptyId = 11554,
        identifier = 9.toByte()
    ),
    GOLD_ORE_32(
        id = 37471,
        emptyId = 15249,
        identifier = 9.toByte()
    ),
    GOLD_ORE_33(
        id = 37473,
        emptyId = 15251,
        identifier = 9.toByte()
    ),
    GOLD_ORE_34(
        id = 37472,
        emptyId = 15250,
        identifier = 9.toByte()
    ),
    GOLD_ORE_49(
        id = 42033,
        emptyId = 452,
        identifier = 9.toByte()
    ),
    MITHRIL_ORE_0(
        id = 2103,
        emptyId = 452,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_1(
        id = 2102,
        emptyId = 450,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_2(
        id = 4988,
        emptyId = 4994,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_3(
        id = 4989,
        emptyId = 4995,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_4(
        id = 4990,
        emptyId = 4996,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_5(
        id = 11943,
        emptyId = 11553,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_6(
        id = 11942,
        emptyId = 11552,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_7(
        id = 11945,
        emptyId = 11555,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_8(
        id = 11944,
        emptyId = 11554,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_9(
        id = 11947,
        emptyId = 11557,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_10(
        id = 11946,
        emptyId = 11556,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_11(
        id = 14855,
        emptyId = 25373,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_12(
        id = 14854,
        emptyId = 25372,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_13(
        id = 14853,
        emptyId = 25371,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_14(
        id = 16687,
        emptyId = 450,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_15(
        id = 20421,
        emptyId = 20407,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_16(
        id = 20420,
        emptyId = 20445,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_17(
        id = 20419,
        emptyId = 20444,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_18(
        id = 20418,
        emptyId = 20443,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_19(
        id = 19012,
        emptyId = 19021,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_20(
        id = 19013,
        emptyId = 19016,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_21(
        id = 19014,
        emptyId = 19017,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_22(
        id = 21278,
        emptyId = 21296,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_23(
        id = 21279,
        emptyId = 21297,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_24(
        id = 21280,
        emptyId = 21298,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_25(
        id = 25369,
        emptyId = 10586,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_26(
        id = 25368,
        emptyId = 10585,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_27(
        id = 25370,
        emptyId = 10587,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_28(
        id = 29236,
        emptyId = 29218,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_29(
        id = 29237,
        emptyId = 29219,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_30(
        id = 29238,
        emptyId = 29220,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_31(
        id = 32439,
        emptyId = 33401,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_32(
        id = 32438,
        emptyId = 33400,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_33(
        id = 32440,
        emptyId = 33402,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_34(
        id = 31087,
        emptyId = 37649,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_35(
        id = 31086,
        emptyId = 37639,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_36(
        id = 31088,
        emptyId = 37650,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_37(
        id = 31170,
        emptyId = 14832,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_38(
        id = 31171,
        emptyId = 14833,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_39(
        id = 31172,
        emptyId = 14834,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_40(
        id = 37692,
        emptyId = 21296,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_41(
        id = 37693,
        emptyId = 21297,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_42(
        id = 37694,
        emptyId = 21298,
        identifier = 10.toByte()
    ),
    MITHRIL_ORE_49(
        id = 42036,
        emptyId = 452,
        identifier = 10.toByte()
    ),
    ADAMANTITE_ORE_0(
        id = 2105,
        emptyId = 452,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_1(
        id = 2104,
        emptyId = 450,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_2(
        id = 4991,
        emptyId = 4994,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_3(
        id = 4992,
        emptyId = 4995,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_4(
        id = 4993,
        emptyId = 4996,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_5(
        id = 11941,
        emptyId = 11554,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_6(
        id = 11940,
        emptyId = 11553,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_7(
        id = 11939,
        emptyId = 11552,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_8(
        id = 14864,
        emptyId = 25373,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_9(
        id = 14863,
        emptyId = 25372,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_10(
        id = 14862,
        emptyId = 25371,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_11(
        id = 20417,
        emptyId = 20407,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_12(
        id = 20416,
        emptyId = 20445,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_13(
        id = 20414,
        emptyId = 20443,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_14(
        id = 20415,
        emptyId = 20444,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_15(
        id = 19020,
        emptyId = 19017,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_16(
        id = 19018,
        emptyId = 19021,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_17(
        id = 19019,
        emptyId = 19016,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_18(
        id = 21275,
        emptyId = 21296,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_19(
        id = 21276,
        emptyId = 21297,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_20(
        id = 21277,
        emptyId = 21298,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_21(
        id = 29233,
        emptyId = 29218,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_22(
        id = 29234,
        emptyId = 29219,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_23(
        id = 29235,
        emptyId = 29220,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_24(
        id = 32435,
        emptyId = 33400,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_25(
        id = 32437,
        emptyId = 33402,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_26(
        id = 32436,
        emptyId = 33401,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_27(
        id = 31083,
        emptyId = 37639,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_28(
        id = 31085,
        emptyId = 37650,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_29(
        id = 31084,
        emptyId = 37649,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_30(
        id = 31173,
        emptyId = 14832,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_31(
        id = 31174,
        emptyId = 14833,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_32(
        id = 31175,
        emptyId = 14834,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_33(
        id = 37468,
        emptyId = 15249,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_34(
        id = 37469,
        emptyId = 15250,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_35(
        id = 37470,
        emptyId = 15251,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_36(
        id = 37689,
        emptyId = 21296,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_37(
        id = 37690,
        emptyId = 21297,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_38(
        id = 37691,
        emptyId = 21298,
        identifier = 11.toByte()
    ),
    ADAMANTITE_ORE_39(
        id = 42037,
        emptyId = 452,
        identifier = 11.toByte()
    ),
    RUNITE_ORE_0(
        id = 2107,
        emptyId = 452,
        identifier = 12.toByte()
    ),
    RUNITE_ORE_1(
        id = 2106,
        emptyId = 450,
        identifier = 12.toByte()
    ),
    RUNITE_ORE_2(
        id = 6669,
        emptyId = 21296,
        identifier = 12.toByte()
    ),
    RUNITE_ORE_3(
        id = 6671,
        emptyId = 21298,
        identifier = 12.toByte()
    ),
    RUNITE_ORE_4(
        id = 6670,
        emptyId = 21297,
        identifier = 12.toByte()
    ),
    RUNITE_ORE_5(
        id = 14861,
        emptyId = 25373,
        identifier = 12.toByte()
    ),
    RUNITE_ORE_6(
        id = 14860,
        emptyId = 25372,
        identifier = 12.toByte()
    ),
    RUNITE_ORE_7(
        id = 14859,
        emptyId = 25371,
        identifier = 12.toByte()
    ),
    RUNITE_ORE_8(
        id = 33079,
        emptyId = 33401,
        identifier = 12.toByte()
    ),
    RUNITE_ORE_9(
        id = 33078,
        emptyId = 33400,
        identifier = 12.toByte()
    ),
    RUNITE_ORE_10(
        id = 37208,
        emptyId = 21296,
        identifier = 12.toByte()
    ),
    RUNITE_ORE_11(
        id = 37465,
        emptyId = 15249,
        identifier = 12.toByte()
    ),
    RUNITE_ORE_12(
        id = 37466,
        emptyId = 15250,
        identifier = 12.toByte()
    ),
    RUNITE_ORE_13(
        id = 37467,
        emptyId = 15251,
        identifier = 12.toByte()
    ),
    RUNITE_ORE_14(
        id = 37695,
        emptyId = 21297,
        identifier = 12.toByte()
    ),
    RUNITE_ORE_15(
        id = 37696,
        emptyId = 21298,
        identifier = 12.toByte()
    ),
    GEM_ROCK_0(
        id = 23567,
        emptyId = 21297,
        identifier = 13.toByte()
    ),
    GEM_ROCK_1(
        id = 23566,
        emptyId = 21296,
        identifier = 13.toByte()
    ),
    GEM_ROCK_2(
        id = 23568,
        emptyId = 21298,
        identifier = 13.toByte()
    ),
    GEM_ROCK_3(
        id = 23560,
        emptyId = 25371,
        identifier = 13.toByte()
    ),
    GEM_ROCK_4(
        id = 23561,
        emptyId = 25372,
        identifier = 13.toByte()
    ),
    GEM_ROCK_5(
        id = 23562,
        emptyId = 21298,
        identifier = 13.toByte()
    ),
    GEM_ROCK_6(
        id = 9030,
        emptyId = 9010,
        identifier = 13.toByte()
    ),
    GEM_ROCK_7(
        id = 9031,
        emptyId = 9015,
        identifier = 13.toByte()
    ),
    GEM_ROCK_8(
        id = 9032,
        emptyId = 9020,
        identifier = 13.toByte()
    ),
    RUNE_ESSENCE_0(
        id = 2491,
        emptyId = -1,
        identifier = 14.toByte()
    ),
    RUNE_ESSENCE_1(
        id = 16684,
        emptyId = -1,
        identifier = 14.toByte()
    ),
    SANDSTONE(
        id = 10946,
        emptyId = 10944,
        identifier = 15.toByte()
    ),
    GRANITE(
        id = 10947,
        emptyId = 10945,
        identifier = 16.toByte()
    ),
    RUBIUM(
        id = 29746,
        emptyId = 29747,
        identifier = 17.toByte()
    );

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

        @JvmStatic
        fun forId(id: Int): MiningNode? {
            return NODE_MAP[id]
        }

        @JvmStatic
        fun isEmpty(id: Int): Boolean {
            return EMPTY_MAP[id] != null
        }
    }
}
