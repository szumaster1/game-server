package content.global.skill.support.construction;


import core.api.consts.Items;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.game.node.scenery.Scenery;
import core.game.world.map.Location;

/**
 * The enum Decoration.
 */
public enum Decoration {

    /**
     * Garden centrepiece decorations.
     */
    PORTAL(13405, 8168, 1, 100.0, new Item(Items.IRON_BAR_2351, 10)),
    /**
     * The Rock.
     */
    ROCK(13406, 8169, 5, 100.0, new Item(Items.LIMESTONE_BRICK_3420, 5)),
    /**
     * The Pond.
     */
    POND(13407, 8170, 10, 100.0, new Item(Items.SOFT_CLAY_1761, 10)),
    /**
     * The Imp statue.
     */
    IMP_STATUE(13408, 8171, 15, 150.0, new Item(Items.LIMESTONE_BRICK_3420, 5), new Item(Items.SOFT_CLAY_1761, 5)),
    /**
     * The Small obelisk.
     */
    SMALL_OBELISK(42004, 14657, 41, 676, new Item(Items.MARBLE_BLOCK_8786, 1), new Item(Items.SPIRIT_SHARDS_12183, 1000), new Item(Items.CRIMSON_CHARM_12160, 10), new Item(Items.BLUE_CHARM_12163, 10)),
    /**
     * The Dungeon entrance.
     */
    DUNGEON_ENTRANCE(13409, 8172, 70, 500.0, new Item(Items.MARBLE_BLOCK_8786)),
    /**
     * Garden big tree decorations.
     */
    BIG_DEAD_TREE(13411, 8173, 5, 31.0, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_DEAD_TREE_8417)),
    /**
     * The Big tree.
     */
    BIG_TREE(13412, 8174, 10, 44.0, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_NICE_TREE_8419)),
    /**
     * The Big oak tree.
     */
    BIG_OAK_TREE(13413, 8175, 15, 70.0, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_OAK_TREE_8421)),
    /**
     * The Big willow tree.
     */
    BIG_WILLOW_TREE(13414, 8176, 30, 100.0, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_WILLOW_TREE_8423)),
    /**
     * The Big maple tree.
     */
    BIG_MAPLE_TREE(13415, 8177, 45, 122.0, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_MAPLE_TREE_8425)),
    /**
     * The Big yew tree.
     */
    BIG_YEW_TREE(13416, 8178, 60, 141.0, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_YEW_TREE_8427)),
    /**
     * The Big magic tree.
     */
    BIG_MAGIC_TREE(13417, 8179, 75, 223.0, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_MAGIC_TREE_8429)),

    /**
     * Garden tree decorations.
     */
    DEAD_TREE(13418, 8173, 5, 31.0, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_DEAD_TREE_8417)),
    /**
     * The Tree.
     */
    TREE(13419, 8174, 10, 44.0, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_NICE_TREE_8419)),
    /**
     * The Oak tree.
     */
    OAK_TREE(13420, 8175, 15, 70.0, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_OAK_TREE_8421)),
    /**
     * The Willow tree.
     */
    WILLOW_TREE(13421, 8176, 30, 100.0, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_WILLOW_TREE_8423)),
    /**
     * The Maple tree.
     */
    MAPLE_TREE(13423, 8177, 45, 122.0, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_MAPLE_TREE_8425)),
    /**
     * The Yew tree.
     */
    YEW_TREE(13422, 8178, 60, 141.0, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_YEW_TREE_8427)),
    /**
     * The Magic tree.
     */
    MAGIC_TREE(13424, 8179, 75, 223.0, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_MAGIC_TREE_8429)),

    /**
     * Garden big plant 1 decorations.
     */
    FERN(13425, 8186, 1, 31.0, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_PLANT_1_8431)),
    /**
     * The Bush.
     */
    BUSH(13426, 8187, 6, 70.0, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_PLANT_2_8433)),
    /**
     * The Tall plant.
     */
    TALL_PLANT(13427, 8188, 12, 100.0, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_PLANT_3_8435)),

    /**
     * Garden big plant 2 decorations.
     */
    SHORT_PLANT(13428, 8189, 1, 31.0, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_PLANT_1_8431)),
    /**
     * The Large leaf plant.
     */
    LARGE_LEAF_PLANT(13429, 8190, 6, 70.0, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_PLANT_2_8433)),
    /**
     * The Huge plant.
     */
    HUGE_PLANT(13430, 8191, 12, 100.0, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_PLANT_3_8435)),

    /**
     * Garden small plant 1 decorations.
     */
    PLANT(13431, 8180, 1, 31.0, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_PLANT_1_8431)),
    /**
     * The Small fern.
     */
    SMALL_FERN(13432, 8181, 6, 70.0, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_PLANT_2_8433)),
    /**
     * The Fern sp.
     */
    FERN_SP(13433, 8182, 12, 100.0, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_PLANT_3_8435)),

    /**
     * Garden small plant 2 decorations.
     */
    DOCK_LEAF(13434, 8183, 1, 31.0, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_PLANT_1_8431)),
    /**
     * The Thistle.
     */
    THISTLE(13435, 8184, 6, 70.0, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_PLANT_2_8433)),
    /**
     * The Reeds.
     */
    REEDS(13436, 8185, 12, 100.0, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_PLANT_3_8435)),

    /**
     * Parlour chair spot
     */
    CRUDE_CHAIR(13581, 8309, 1, 66.0, new Item(Items.PLANK_960, 2)),
    /**
     * The Wooden chair.
     */
    WOODEN_CHAIR(13582, 8310, 8, 96.0, new Item(Items.PLANK_960, 3)),
    /**
     * The Rocking chair.
     */
    ROCKING_CHAIR(13583, 8311, 14, 96.0, new Item(Items.PLANK_960, 3)),
    /**
     * The Oak chair.
     */
    OAK_CHAIR(13584, 8312, 19, 120.0, new Item(Items.OAK_PLANK_8778, 2)),
    /**
     * The Oak armchair.
     */
    OAK_ARMCHAIR(13585, 8313, 26, 180.0, new Item(Items.OAK_PLANK_8778, 3)),
    /**
     * The Teak armchair.
     */
    TEAK_ARMCHAIR(13586, 8314, 35, 180.0, new Item(Items.TEAK_PLANK_8780, 2)),
    /**
     * The Mahogany armchair.
     */
    MAHOGANY_ARMCHAIR(13587, 8315, 50, 280.0, new Item(Items.MAHOGANY_PLANK_8782, 2)),

    /**
     * Rugs rugs rugs
     */
    BROWN_RUG_CORNER(13588, 8316, 2, 30.0, new Item(Items.BOLT_OF_CLOTH_8790, 2)),
    /**
     * The Red rug corner.
     */
    RED_RUG_CORNER(13591, 8317, 13, 60.0, new Item(Items.BOLT_OF_CLOTH_8790, 4)),
    /**
     * The Opulent rug corner.
     */
    OPULENT_RUG_CORNER(13594, 8318, 65, 360.0, new Item(Items.BOLT_OF_CLOTH_8790, 4), new Item(Items.GOLD_LEAF_8784, 1)),

    /**
     * The Brown rug end.
     */
    BROWN_RUG_END(13589, 8316, 2, 30.0, new Item(Items.BOLT_OF_CLOTH_8790, 2)),
    /**
     * The Red rug end.
     */
    RED_RUG_END(13592, 8317, 13, 60.0, new Item(Items.BOLT_OF_CLOTH_8790, 4)),
    /**
     * The Opulent rug end.
     */
    OPULENT_RUG_END(13595, 8318, 65, 360.0, new Item(Items.BOLT_OF_CLOTH_8790, 4), new Item(Items.GOLD_LEAF_8784, 1)),

    /**
     * The Brown rug center.
     */
    BROWN_RUG_CENTER(13590, 8316, 2, 30.0, new Item(Items.BOLT_OF_CLOTH_8790, 2)),
    /**
     * The Red rug center.
     */
    RED_RUG_CENTER(13593, 8317, 13, 60.0, new Item(Items.BOLT_OF_CLOTH_8790, 4)),
    /**
     * The Opulent rug center.
     */
    OPULENT_RUG_CENTER(13596, 8318, 65, 360.0, new Item(Items.BOLT_OF_CLOTH_8790, 4), new Item(Items.GOLD_LEAF_8784, 1)),

    /**
     * Parlour fireplaces
     */
    CLAY_FIREPLACE(13609, 8325, 3, 30.0, new Item(Items.SOFT_CLAY_1761, 3)),
    /**
     * The Stone fireplace.
     */
    STONE_FIREPLACE(13611, 8326, 33, 40.0, new Item(Items.LIMESTONE_BRICK_3420, 2)),
    /**
     * The Marble fireplace.
     */
    MARBLE_FIREPLACE(13613, 8327, 63, 500.0, new Item(Items.MARBLE_BLOCK_8786, 1)),

    /**
     * Parlour curtain spot
     */
    TORN_CURTAINS(13603, 8322, 2, 132.0, new Item(Items.PLANK_960, 3), new Item(Items.BOLT_OF_CLOTH_8790, 3)),
    /**
     * The Curtains.
     */
    CURTAINS(13604, 8323, 18, 225.0, new Item(Items.OAK_PLANK_8778, 3), new Item(Items.BOLT_OF_CLOTH_8790, 3)),
    /**
     * The Opulent curtains.
     */
    OPULENT_CURTAINS(13605, 8324, 40, 315.0, new Item(Items.TEAK_PLANK_8780, 3), new Item(Items.BOLT_OF_CLOTH_8790, 3)),

    /**
     * Parlour bookcases
     */
    WOODEN_BOOKCASE(13597, 8319, 4, 132.0, new Item(Items.PLANK_960, 4)),
    /**
     * The Oak bookcase.
     */
    OAK_BOOKCASE(13598, 8320, 29, 225.0, new Item(Items.OAK_PLANK_8778, 3)),
    /**
     * The Mahogany bookcase.
     */
    MAHOGANY_BOOKCASE(13599, 8321, 40, 315.0, new Item(Items.MAHOGANY_PLANK_8782, 3)),


    /**
     * Kitchen Beer Barrels
     * TODO: These also require cooking levels!
     * Basic: 1, Cider: 14, Asgarnian: 24, Greenman's: 29, D.Bitter: 39, Chef's: 54
     */
    BASIC_BEER_BARREL(13568, 8239, 7, 87.0, new Item(Items.PLANK_960, 3)),
    /**
     * The Cider barrel.
     */
    CIDER_BARREL(13569, 8240, 12, 91.0, new Item(Items.PLANK_960, 3), new Item(Items.CIDER_5763, 8)),
    /**
     * The Asgarnian ale barrel.
     */
    ASGARNIAN_ALE_BARREL(13570, 8241, 18, 184.0, new Item(Items.OAK_PLANK_8778, 3), new Item(Items.ASGARNIAN_ALE_1905, 8)),
    /**
     * The Greenmans ale barrel.
     */
    GREENMANS_ALE_BARREL(13571, 8242, 26, 184.0, new Item(Items.OAK_PLANK_8778, 3), new Item(Items.GREENMANS_ALE_1909, 8)),
    /**
     * The Dragon bitter barrel.
     */
    DRAGON_BITTER_BARREL(13572, 8243, 36, 224.0, new Item(Items.OAK_PLANK_8778, 3), new Item(Items.DRAGON_BITTER_1911, 8), new Item(Items.STEEL_BAR_2353, 2)),
    /**
     * The Chefs delight barrel.
     */
    CHEFS_DELIGHT_BARREL(13573, 8244, 48, 224.0, new Item(Items.OAK_PLANK_8778, 3), new Item(Items.CHEFS_DELIGHT_5755, 8), new Item(Items.STEEL_BAR_2353, 2)),


    /**
     * Kitchen Tables!
     */
    KITCHEN_WOODEN_TABLE(13577, 8246, 12, 87.0, new Item(Items.PLANK_960, 3)),
    /**
     * The Kitchen oak table.
     */
    KITCHEN_OAK_TABLE(13578, 8247, 32, 180.0, new Item(Items.OAK_PLANK_8778, 3)),
    /**
     * The Kitchen teak table.
     */
    KITCHEN_TEAK_TABLE(13579, 8248, 52, 270.0, new Item(Items.TEAK_PLANK_8780, 3)),


    /**
     * Kitchen Stoves
     */
    BASIC_FIREPIT(13528, 8216, 5, 40.0, new Item(Items.SOFT_CLAY_1761, 2), new Item(Items.STEEL_BAR_2353, 1)),
    /**
     * The Firepit with hook.
     */
    FIREPIT_WITH_HOOK(13529, 8217, 11, 60.0, new Item(Items.SOFT_CLAY_1761, 2), new Item(Items.STEEL_BAR_2353, 2)),
    /**
     * The Firepit with pot.
     */
    FIREPIT_WITH_POT(13531, 8218, 17, 80.0, new Item(Items.SOFT_CLAY_1761, 2), new Item(Items.STEEL_BAR_2353, 3)),
    /**
     * The Small oven.
     */
    SMALL_OVEN(13533, 8219, 24, 80.0, new Item(Items.STEEL_BAR_2353, 4)),
    /**
     * The Large oven.
     */
    LARGE_OVEN(13536, 8220, 29, 100.0, new Item(Items.STEEL_BAR_2353, 5)),
    /**
     * The Basic range.
     */
    BASIC_RANGE(13539, 8221, 34, 120.0, new Item(Items.STEEL_BAR_2353, 6)),
    /**
     * The Fancy range.
     */
    FANCY_RANGE(13542, 8222, 42, 160.0, new Item(Items.STEEL_BAR_2353, 8)),

    /**
     * Kitchen larders
     */
    WOODEN_LARDER(13565, 8233, 9, 228.0, new Item(Items.PLANK_960, 8)),
    /**
     * The Oak larder.
     */
    OAK_LARDER(13566, 8234, 33, 480.0, new Item(Items.OAK_PLANK_8778, 8)),
    /**
     * The Teak larder.
     */
    TEAK_LARDER(13567, 8235, 43, 750.0, new Item(Items.TEAK_PLANK_8780, 8), new Item(Items.BOLT_OF_CLOTH_8790, 2)),


    /**
     * Kitchen shelves
     */
    WOODEN_SHELVES_1(13545, 8223, 6, 87.0, new Item(Items.PLANK_960, 3)),
    /**
     * The Wooden shelves 2.
     */
    WOODEN_SHELVES_2(13546, 8224, 12, 147.0, new Item(Items.PLANK_960, 3), new Item(Items.SOFT_CLAY_1761, 6)),
    /**
     * The Wooden shelves 3.
     */
    WOODEN_SHELVES_3(13547, 8225, 23, 147.0, new Item(Items.PLANK_960, 3), new Item(Items.SOFT_CLAY_1761, 6)),
    /**
     * The Oak shelves 1.
     */
    OAK_SHELVES_1(13548, 8226, 34, 240.0, new Item(Items.OAK_PLANK_8778, 3), new Item(Items.SOFT_CLAY_1761, 6)),
    /**
     * The Oak shelves 2.
     */
    OAK_SHELVES_2(13549, 8227, 45, 240.0, new Item(Items.OAK_PLANK_8778, 3), new Item(Items.SOFT_CLAY_1761, 6)),
    /**
     * The Teak shelves 1.
     */
    TEAK_SHELVES_1(13550, 8228, 56, 330.0, new Item(Items.TEAK_PLANK_8780, 3), new Item(Items.SOFT_CLAY_1761, 6)),
    /**
     * The Teak shelves 2.
     */
    TEAK_SHELVES_2(13551, 8229, 67, 930.0, new Item(Items.TEAK_PLANK_8780, 3), new Item(Items.SOFT_CLAY_1761, 6), new Item(Items.GOLD_LEAF_8784, 2)),

    /**
     * Kitchen sinks
     */
    PUMP_AND_DRAIN(13559, 8230, 7, 100.0, new Item(Items.STEEL_BAR_2353, 5)),
    /**
     * The Pump and tub.
     */
    PUMP_AND_TUB(13561, 8231, 27, 200.0, new Item(Items.STEEL_BAR_2353, 10)),
    /**
     * The Sink.
     */
    SINK(13563, 8232, 47, 300.0, new Item(Items.STEEL_BAR_2353, 15)),


    /**
     * Kitchen cat baskets/blankets
     */
    CAT_BLANKET(13574, 8236, 5, 15.0, new Item(Items.BOLT_OF_CLOTH_8790, 1)),
    /**
     * The Cat basket.
     */
    CAT_BASKET(13575, 8237, 19, 58.0, new Item(Items.PLANK_960, 2)),
    /**
     * The Cast basket cushioned.
     */
    CAST_BASKET_CUSHIONED(13576, 8238, 33, 58.0, new Item(Items.PLANK_960, 2), new Item(Items.WOOL_1737, 2)),


    /**
     * Dining room tables
     */
    DINING_TABLE_WOOD(13293, 8246, 10, 115.0, new Item(Items.PLANK_960, 4)),
    /**
     * The Dining table oak.
     */
    DINING_TABLE_OAK(13294, 8247, 22, 240.0, new Item(Items.OAK_PLANK_8778, 4)),
    /**
     * The Dining table carved oak.
     */
    DINING_TABLE_CARVED_OAK(13295, 8247, 31, 360.0, new Item(Items.OAK_PLANK_8778, 6)),
    /**
     * The Dining table teak.
     */
    DINING_TABLE_TEAK(13296, 8248, 38, 360.0, new Item(Items.TEAK_PLANK_8780, 4)),
    /**
     * The Dining table carved teak.
     */
    DINING_TABLE_CARVED_TEAK(13297, 8248, 45, 600.0, new Item(Items.TEAK_PLANK_8780, 6), new Item(Items.BOLT_OF_CLOTH_8790, 4)),
    /**
     * The Dining table mahogany.
     */
    DINING_TABLE_MAHOGANY(13298, 8120, 52, 840.0, new Item(Items.MAHOGANY_PLANK_8782, 6)),
    /**
     * The Dining table opulent.
     */
    DINING_TABLE_OPULENT(13299, 8121, 72, 3100.0, new Item(Items.MAHOGANY_PLANK_8782, 6), new Item(Items.BOLT_OF_CLOTH_8790, 4),
			new Item(Items.GOLD_LEAF_8784, 4), new Item(Items.MARBLE_BLOCK_8786, 2)),


    /**
     * Dining room benches
     */
    BENCH_WOODEN(13300, 8108, 10, 115.0, new Item(Items.PLANK_960, 4)),
    /**
     * The Bench oak.
     */
    BENCH_OAK(13301, 8109, 22, 240.0, new Item(Items.OAK_PLANK_8778, 4)),
    /**
     * The Bench carved oak.
     */
    BENCH_CARVED_OAK(13302, 8110, 31, 240.0, new Item(Items.OAK_PLANK_8778, 4)),
    /**
     * The Bench teak.
     */
    BENCH_TEAK(13303, 8111, 38, 360.0, new Item(Items.TEAK_PLANK_8780, 4)),
    /**
     * The Bench carved teak.
     */
    BENCH_CARVED_TEAK(13304, 8112, 44, 360.0, new Item(Items.TEAK_PLANK_8780, 4)),
    /**
     * The Bench mahogany.
     */
    BENCH_MAHOGANY(13305, 8113, 52, 560.0, new Item(Items.MAHOGANY_PLANK_8782, 6)),
    /**
     * The Bench gilded.
     */
    BENCH_GILDED(13306, 8114, 61, 1760.0, new Item(Items.MAHOGANY_PLANK_8782, 4), new Item(Items.GOLD_LEAF_8784, 4)),

    /**
     * Dining room bell-pulls
     */
    ROPE_PULL(13307, 8099, 5, 15.0, new Item(Items.ROPE_954, 1), new Item(Items.OAK_PLANK_8778, 1)),
    /**
     * The Bell pull.
     */
    BELL_PULL(13308, 8100, 19, 58.0, new Item(Items.TEAK_PLANK_8780, 1), new Item(Items.BOLT_OF_CLOTH_8790, 2)),
    /**
     * The Fancy bell pull.
     */
    FANCY_BELL_PULL(13309, 8101, 33, 58.0, new Item(Items.TEAK_PLANK_8780, 1), new Item(Items.BOLT_OF_CLOTH_8790, 2), new Item(Items.GOLD_LEAF_8784, 1)),

    /**
     * Workshop workbench
     */
    WORKBENCH_WOODEN(13704, 8375, 17, 145.0, new Item(Items.PLANK_960, 1)),
    /**
     * The Workbench oak.
     */
    WORKBENCH_OAK(13705, 8376, 32, 300.0, new Item(Items.OAK_PLANK_8778, 5)),
    /**
     * The Workbench steel frame.
     */
    WORKBENCH_STEEL_FRAME(13706, 8377, 46, 440.0, new Item(Items.OAK_PLANK_8778, 6), new Item(Items.STEEL_BAR_2353, 4)),
    /**
     * The Workbench with vice.
     */
    WORKBENCH_WITH_VICE(13707, 8378, 62, 750.0, new Item(Items.STEEL_FRAMED_BENCH_8377, 1), new Item(Items.OAK_PLANK_8778, 2), new Item(Items.STEEL_BAR_2353, 1)),
    /**
     * The Workbench with lathe.
     */
    WORKBENCH_WITH_LATHE(13708, 8379, 77, 1000.0, new Item(Items.OAK_WORKBENCH_8376, 1), new Item(Items.OAK_PLANK_8778, 2), new Item(Items.STEEL_BAR_2353, 1)),

    /**
     * Workshop repair benches/stands
     */
    REPAIR_BENCH(13713, 8389, 15, 120.0, new Item(Items.OAK_PLANK_8778, 2)),
    /**
     * The Whetstone.
     */
    WHETSTONE(13714, 8390, 35, 260.0, new Item(Items.OAK_PLANK_8778, 4), new Item(Items.LIMESTONE_BRICK_3420, 1)),
    /**
     * The Armour stand.
     */
    ARMOUR_STAND(13715, 8391, 55, 500.0, new Item(Items.OAK_PLANK_8778, 8), new Item(Items.LIMESTONE_BRICK_3420, 1)),

    /**
     * Workshop easels
     */
    PLUMING_STAND(13716, 8392, 16, 120.0, new Item(Items.OAK_PLANK_8778, 2)),
    /**
     * The Shield easel.
     */
    SHIELD_EASEL(13717, 8393, 41, 240.0, new Item(Items.OAK_PLANK_8778, 4)),
    /**
     * The Banner easel.
     */
    BANNER_EASEL(13718, 8394, 66, 510.0, new Item(Items.OAK_PLANK_8778, 8), new Item(Items.BOLT_OF_CLOTH_8790, 2)),

    /**
     * Workshop crafting tables
     * 	TODO: These are upgradable hotspots, therefore crafting table 3 would require
     * 	crafting table 2 to be already built in that spot.
     */
    CRAFTING_TABLE_1(13709, 8380, 16, 50.0, new Item(Items.OAK_PLANK_8778, 4)),
    /**
     * The Crafting table 2.
     */
    CRAFTING_TABLE_2(13710, 8381, 25, 100.0, new Item(Items.MOLTEN_GLASS_1775, 1)),
    /**
     * The Crafting table 3.
     */
    CRAFTING_TABLE_3(13711, 8382, 34, 175.0, new Item(Items.MOLTEN_GLASS_1775, 2)),
    /**
     * The Crafting table 4.
     */
    CRAFTING_TABLE_4(13712, 8383, 42, 240.0, new Item(Items.OAK_PLANK_8778, 2)),

    /**
     * Workshop tool stores
     * These are also upgradable just like the tables above.
     */
    TOOL_STORE_1(13699, 8384, 15, 120.0, new Item(Items.OAK_PLANK_8778, 2)),
    /**
     * The Tool store 2.
     */
    TOOL_STORE_2(13700, 8385, 25, 120.0, new Item(Items.OAK_PLANK_8778, 2)),
    /**
     * The Tool store 3.
     */
    TOOL_STORE_3(13701, 8386, 35, 120.0, new Item(Items.OAK_PLANK_8778, 2)),
    /**
     * The Tool store 4.
     */
    TOOL_STORE_4(13702, 8387, 44, 120.0, new Item(Items.OAK_PLANK_8778, 2)),
    /**
     * The Tool store 5.
     */
    TOOL_STORE_5(13703, 8388, 55, 120.0, new Item(Items.OAK_PLANK_8778, 2)),


    /**
     * Wall-mounted decorations
     */
    OAK_DECORATION(13606, 8102, 16, 120.0, new Item(Items.OAK_PLANK_8778, 2)),
    /**
     * The Teak decoration.
     */
    TEAK_DECORATION(13606, 8103, 36, 180.0, new Item(Items.TEAK_PLANK_8780, 2)),
    /**
     * The Gilded decoration.
     */
    GILDED_DECORATION(13607, 8104, 56, 1020.0, new Item(Items.MAHOGANY_PLANK_8782, 3), new Item(Items.GOLD_LEAF_8784, 2)),

    /**
     * Staircases.
     */
    OAK_STAIRCASE(13497, 8249, 27, 680.0, new Item(Items.OAK_PLANK_8778, 10), new Item(Items.STEEL_BAR_2353, 4)),
    /**
     * The Teak staircase.
     */
    TEAK_STAIRCASE(13499, 8252, 48, 980.0, new Item(Items.TEAK_PLANK_8780, 10), new Item(Items.STEEL_BAR_2353, 4)),
    /**
     * The Spiral staircase.
     */
    SPIRAL_STAIRCASE(13503, 8258, 67, 1040.0, new Item(Items.TEAK_PLANK_8780, 10), new Item(Items.LIMESTONE_BRICK_3420, 7)),
    /**
     * The Marble staircase.
     */
    MARBLE_STAIRCASE(13501, 8257, 82, 3200.0, new Item(Items.MAHOGANY_PLANK_8782, 5), new Item(Items.MARBLE_BLOCK_8786, 5)),
    /**
     * The Marble spiral.
     */
    MARBLE_SPIRAL(13505, 8259, 97, 4400.0, new Item(Items.TEAK_PLANK_8780, 10), new Item(Items.MARBLE_BLOCK_8786, 7)),

    /**
     * Staircases going down.
     */
    OAK_STAIRS_DOWN(13498, 8249, 27, 680.0, new Item(Items.OAK_PLANK_8778, 10), new Item(Items.STEEL_BAR_2353, 4)),
    /**
     * The Teak stairs down.
     */
    TEAK_STAIRS_DOWN(13500, 8252, 48, 980.0, new Item(Items.TEAK_PLANK_8780, 10), new Item(Items.STEEL_BAR_2353, 4)),
    /**
     * The Spiral stairs down.
     */
    SPIRAL_STAIRS_DOWN(13504, 8258, 67, 1040.0, new Item(Items.TEAK_PLANK_8780, 10), new Item(Items.LIMESTONE_BRICK_3420, 7)),
    /**
     * The Marble stairs down.
     */
    MARBLE_STAIRS_DOWN(13502, 8257, 82, 3200.0, new Item(Items.MAHOGANY_PLANK_8782, 5), new Item(Items.MARBLE_BLOCK_8786, 5)),
    /**
     * The Marble spiral down.
     */
    MARBLE_SPIRAL_DOWN(13506, 8259, 97, 4400.0, new Item(Items.TEAK_PLANK_8780, 10), new Item(Items.MARBLE_BLOCK_8786, 7)),

    /**
     * Portal room decorations.
     */
    TEAK_PORTAL(13636, 8328, 50, 270.0, new Item(Items.TEAK_PLANK_8780, 3)),
    /**
     * The Mahogany portal.
     */
    MAHOGANY_PORTAL(13637, 8329, 65, 420.0, new Item(Items.MAHOGANY_PLANK_8782, 3)),
    /**
     * The Marble portal.
     */
    MARBLE_PORTAL(13638, 8330, 80, 1500.0, new Item(Items.MARBLE_BLOCK_8786, 3)),
    /**
     * The Teleport focus.
     */
    TELEPORT_FOCUS(13640, 8331, 50, 40, new Item(Items.LIMESTONE_BRICK_3420, 2)),
    /**
     * The Greater teleport focus.
     */
    GREATER_TELEPORT_FOCUS(13641, 8332, 65, 500.0, new Item(Items.MARBLE_BLOCK_8786, 1)),
    /**
     * The Scrying pool.
     */
    SCRYING_POOL(13639, 8333, 80, 2000.0, new Item(Items.MARBLE_BLOCK_8786, 4)),
    /**
     * Teak varrock portal decoration.
     */
    TEAK_VARROCK_PORTAL(13615, true),
    /**
     * Mahogany varrock portal decoration.
     */
    MAHOGANY_VARROCK_PORTAL(13622, true),
    /**
     * Marble varrock portal decoration.
     */
    MARBLE_VARROCK_PORTAL(13629, true),
    /**
     * Teak lumbridge portal decoration.
     */
    TEAK_LUMBRIDGE_PORTAL(13616, true),
    /**
     * Mahogany lumbridge portal decoration.
     */
    MAHOGANY_LUMBRIDGE_PORTAL(13623, true),
    /**
     * Marble lumbridge portal decoration.
     */
    MARBLE_LUMBRIDGE_PORTAL(13630, true),
    /**
     * Teak falador portal decoration.
     */
    TEAK_FALADOR_PORTAL(13617, true),
    /**
     * Mahogany falador portal decoration.
     */
    MAHOGANY_FALADOR_PORTAL(13624, true),
    /**
     * Marble falador portal decoration.
     */
    MARBLE_FALADOR_PORTAL(13631, true),
    /**
     * Teak camelot portal decoration.
     */
    TEAK_CAMELOT_PORTAL(13618, true),
    /**
     * Mahogany camelot portal decoration.
     */
    MAHOGANY_CAMELOT_PORTAL(13625, true),
    /**
     * Marble camelot portal decoration.
     */
    MARBLE_CAMELOT_PORTAL(13632, true),
    /**
     * Teak ardougne portal decoration.
     */
    TEAK_ARDOUGNE_PORTAL(13619, true),
    /**
     * Mahogany ardougne portal decoration.
     */
    MAHOGANY_ARDOUGNE_PORTAL(13626, true),
    /**
     * Marble ardougne portal decoration.
     */
    MARBLE_ARDOUGNE_PORTAL(13633, true),
    /**
     * Teak yanille portal decoration.
     */
    TEAK_YANILLE_PORTAL(13620, true),
    /**
     * Mahogany yanille portal decoration.
     */
    MAHOGANY_YANILLE_PORTAL(13627, true),
    /**
     * Marble yanille portal decoration.
     */
    MARBLE_YANILLE_PORTAL(13634, true),
    /**
     * Teak kharyrll portal decoration.
     */
    TEAK_KHARYRLL_PORTAL(13621, true),
    /**
     * Mahogany kharyrll portal decoration.
     */
    MAHOGANY_KHARYRLL_PORTAL(13628, true),
    /**
     * Marble kharyrll portal decoration.
     */
    MARBLE_KHARYRLL_PORTAL(13635, true),

    /**
     * Skill hall decorations.
     */
    MITHRIL_ARMOUR(13491, 8270, 28, 135.0, new Item(Items.OAK_PLANK_8778, 2), new Item(Items.MITHRIL_FULL_HELM_1159, 1), new Item(Items.MITHRIL_PLATEBODY_1121, 1), new Item(Items.MITHRIL_PLATESKIRT_1085, 1)),
    /**
     * The Adamant armour.
     */
    ADAMANT_ARMOUR(13492, 8271, 28, 150.0, new Item(Items.OAK_PLANK_8778, 2), new Item(Items.ADAMANT_FULL_HELM_1161, 1), new Item(Items.ADAMANT_PLATEBODY_1123, 1), new Item(Items.ADAMANT_PLATESKIRT_1091, 1)),
    /**
     * The Rune armour.
     */
    RUNE_ARMOUR(13493, 8272, 28, 165.0, new Item(Items.OAK_PLANK_8778, 2),new Item(Items.RUNE_FULL_HELM_1163, 1), new Item(Items.RUNE_PLATEBODY_1127, 1), new Item(Items.RUNE_PLATESKIRT_1093, 1)),
    /**
     * The Crawling hand.
     */
    CRAWLING_HAND(13481, 8260, 38, 211.0, new Item(Items.TEAK_PLANK_8780, 2), new Item(Items.CRAWLING_HAND_7982, 1)),
    /**
     * The Cockatrice head.
     */
    COCKATRICE_HEAD(13482, 8261, 38, 224.0, new Item(Items.TEAK_PLANK_8780, 2), new Item(Items.COCKATRICE_HEAD_7983, 1)),
    /**
     * The Basilisk head.
     */
    BASILISK_HEAD(13483, 8262, 38, 243.0, new Item(Items.TEAK_PLANK_8780, 2), new Item(Items.BASILISK_HEAD_7984, 1)),
    /**
     * The Kurask head.
     */
    KURASK_HEAD(13484, 8263, 58, 357.0, new Item(Items.MAHOGANY_PLANK_8782, 2), new Item(Items.KURASK_HEAD_7985, 1)),
    /**
     * The Abyssal demon head.
     */
    ABYSSAL_DEMON_HEAD(13485, 8264, 58, 389.0, new Item(Items.MAHOGANY_PLANK_8782, 2), new Item(Items.ABYSSAL_HEAD_7986, 1)),
    /**
     * The Kbd head.
     */
    KBD_HEAD(13486, 8265, 78, 1103.0, new Item(Items.MAHOGANY_PLANK_8782, 2), new Item(Items.KBD_HEADS_7987, 1)),
    /**
     * The Kq head.
     */
    KQ_HEAD(13487, 8266, 78, 1103.0, new Item(Items.MAHOGANY_PLANK_8782, 2), new Item(Items.KQ_HEAD_7988, 1)),
    /**
     * The Mounted bass.
     */
    MOUNTED_BASS(13488, 8267, 36, 151.0, new Item(Items.OAK_PLANK_8778, 2), new Item(Items.BIG_BASS_7990, 1)),
    /**
     * The Mounted swordfish.
     */
    MOUNTED_SWORDFISH(13489, 8268, 56, 230.0, new Item(Items.TEAK_PLANK_8780, 2), new Item(Items.BIG_SWORDFISH_7992, 1)),
    /**
     * The Mounted shark.
     */
    MOUNTED_SHARK(13490, 8269, 76, 350.0, new Item(Items.MAHOGANY_PLANK_8782, 2), new Item(Items.BIG_SHARK_7994, 1)),
    /**
     * The Rune case 1.
     */
    RUNE_CASE1(13507, 8095, 41, 190.0, new Item(Items.TEAK_PLANK_8780, 2), new Item(Items.MOLTEN_GLASS_1775, 2), new Item(Items.FIRE_RUNE_554, 1), new Item(Items.AIR_RUNE_556, 1), new Item(Items.EARTH_RUNE_557, 1), new Item(Items.WATER_RUNE_555, 1)),
    /**
     * The Rune case 2.
     */
    RUNE_CASE2(13508, 8095, 41, 212.0, new Item(Items.TEAK_PLANK_8780, 2), new Item(Items.MOLTEN_GLASS_1775, 2), new Item(Items.BODY_RUNE_559, 1), new Item(Items.COSMIC_RUNE_564, 1), new Item(Items.CHAOS_RUNE_562, 1), new Item(Items.NATURE_RUNE_561, 1)),


    /**
     * Games room decorations.
     */
    CLAY_STONE(13392, 8153, 39, 100.0, new Item(Items.SOFT_CLAY_1761, 10)),
    /**
     * The Limestone stone.
     */
    LIMESTONE_STONE(13393, 8154, 59, 200.0, new Item(Items.LIMESTONE_BRICK_3420, 10)),
    /**
     * The Marble stone.
     */
    MARBLE_STONE(13394, 8155, 79, 2000.0, new Item(Items.MARBLE_BLOCK_8786, 4)),
    /**
     * The Hoop and stick.
     */
    HOOP_AND_STICK(13398, 8162, 30, 120.0, new Item(Items.OAK_PLANK_8778, 2)),
    /**
     * The Dartboard.
     */
    DARTBOARD(13400, 8163, 54, 290.0, new Item(Items.TEAK_PLANK_8780, 3), new Item(Items.STEEL_BAR_2353, 1)),
    /**
     * The Archery target.
     */
    ARCHERY_TARGET(13402, 8164, 81, 600.0, new Item(Items.TEAK_PLANK_8780, 6), new Item(Items.STEEL_BAR_2353, 3)),
    /**
     * The Balance 1.
     */
    BALANCE_1(13395, 8156, 37, 176.0, new Item(Items.FIRE_RUNE_554, 500), new Item(Items.AIR_RUNE_556, 500), new Item(Items.EARTH_RUNE_557, 500), new Item(Items.WATER_RUNE_555, 500)),
    /**
     * The Balance 2.
     */
    BALANCE_2(13396, 8157, 57, 252.0, new Item(Items.FIRE_RUNE_554, 1000), new Item(Items.AIR_RUNE_556, 1000), new Item(Items.EARTH_RUNE_557, 1000), new Item(Items.WATER_RUNE_555, 1000)),
    /**
     * The Balance 3.
     */
    BALANCE_3(13397, 8158, 77, 356.0, new Item(Items.FIRE_RUNE_554, 2000), new Item(Items.AIR_RUNE_556, 2000), new Item(Items.EARTH_RUNE_557, 2000), new Item(Items.WATER_RUNE_555, 2000)),
    /**
     * The Oak chest.
     */
    OAK_CHEST(13385, 8165, 34, 240.0, new Item(Items.OAK_PLANK_8778, 4)),
    /**
     * The Teak chest.
     */
    TEAK_CHEST(13387, 8166, 44, 660.0, new Item(Items.TEAK_PLANK_8780, 4), new Item(Items.GOLD_LEAF_8784, 1)),
    /**
     * The Mahogany chest.
     */
    MAHOGANY_CHEST(13389, 8167, 54, 860.0, new Item(Items.MAHOGANY_PLANK_8782, 4), new Item(Items.GOLD_LEAF_8784, 1)),
    /**
     * The Jester.
     */
    JESTER(13390, 8159, 39, 360.0, new Item(Items.TEAK_PLANK_8780, 4)),
    /**
     * The Treasure hunt.
     */
    TREASURE_HUNT(13379, 8160, 49, 800.0, new Item(Items.TEAK_PLANK_8780, 8), new Item(Items.STEEL_BAR_2353, 4)),
    /**
     * The Hangman.
     */
    HANGMAN(13404, 8161, 59, 1200.0, new Item(Items.TEAK_PLANK_8780, 12), new Item(Items.STEEL_BAR_2353, 6)),


    /**
     * Combat room decorations.
     */
    BOXING_RING(13129, 8023, 32, 570.0, new Item(Items.OAK_PLANK_8778, 6), new Item(Items.BOLT_OF_CLOTH_8790, 4)),
    /**
     * The Fencing ring.
     */
    FENCING_RING(13133, 8024, 41, 570.0, new Item(Items.OAK_PLANK_8778, 8), new Item(Items.BOLT_OF_CLOTH_8790, 6)),
    /**
     * The Combat ring.
     */
    COMBAT_RING(13137, 8025, 51, 630.0, new Item(Items.TEAK_PLANK_8780, 6), new Item(Items.BOLT_OF_CLOTH_8790, 6)),
    /**
     * The Balance beam left.
     */
    BALANCE_BEAM_LEFT(13143, 8027, 81, 1000.0, new Item(Items.TEAK_PLANK_8780, 10), new Item(Items.STEEL_BAR_2353, 5)),
    /**
     * The Balance beam center.
     */
    BALANCE_BEAM_CENTER(13142, 8027, 81, 1000.0, new Item(Items.TEAK_PLANK_8780, 10), new Item(Items.STEEL_BAR_2353, 5)),
    /**
     * The Balance beam right.
     */
    BALANCE_BEAM_RIGHT(13144, 8027, 81, 1000.0, new Item(Items.TEAK_PLANK_8780, 10), new Item(Items.STEEL_BAR_2353, 5)),
    /**
     * The Ranging pedestals.
     */
    RANGING_PEDESTALS(13147, 8026, 71, 720.0, new Item(Items.TEAK_PLANK_8780, 8)),
    /**
     * The Magic barrier.
     */
    MAGIC_BARRIER(13145, 8026, 71, 720.0, new Item(Items.TEAK_PLANK_8780, 8)),
    /**
     * The Nothing.
     */
    NOTHING(13721, 8027, 81, 1000.0, new Item(Items.TEAK_PLANK_8780, 10), new Item(Items.STEEL_BAR_2353, 5)),
    /**
     * The Nothing 2.
     */
    NOTHING2(13721, 8026, 71, 720.0, new Item(Items.TEAK_PLANK_8780, 8)),
    /**
     * The Invisible wall.
     */
    INVISIBLE_WALL(15283, 8023, 32, 570.0, new Item(Items.OAK_PLANK_8778, 6), new Item(Items.BOLT_OF_CLOTH_8790, 4)),
    /**
     * The Invisible wall 2.
     */
    INVISIBLE_WALL2(15284, 8023, 32, 570.0, new Item(Items.OAK_PLANK_8778, 6), new Item(Items.BOLT_OF_CLOTH_8790, 4)),
    /**
     * The Invisible wall 3.
     */
    INVISIBLE_WALL3(15285, 8023, 32, 570.0, new Item(Items.OAK_PLANK_8778, 6), new Item(Items.BOLT_OF_CLOTH_8790, 4)),
    /**
     * The Glove rack.
     */
    GLOVE_RACK(13381, 8028, 34, 120.0, new Item(Items.OAK_PLANK_8778, 2)),
    /**
     * The Weapons rack.
     */
    WEAPONS_RACK(13382, 8029, 44, 180.0, new Item(Items.TEAK_PLANK_8780, 2)),
    /**
     * The Extra weapons rack.
     */
    EXTRA_WEAPONS_RACK(13383, 8030, 54, 440.0, new Item(Items.TEAK_PLANK_8780, 4), new Item(Items.STEEL_BAR_2353, 4)),
    /**
     * The Boxing mat corner.
     */
    BOXING_MAT_CORNER(13126, 8023, 32, 570.0, new Item(Items.OAK_PLANK_8778, 6), new Item(Items.BOLT_OF_CLOTH_8790, 4)),
    /**
     * The Fencing mat corner.
     */
    FENCING_MAT_CORNER(13135, 8024, 41, 570.0, new Item(Items.OAK_PLANK_8778, 8), new Item(Items.BOLT_OF_CLOTH_8790, 6)),
    /**
     * The Combat mat corner.
     */
    COMBAT_MAT_CORNER(13138, 8025, 51, 630.0, new Item(Items.TEAK_PLANK_8780, 6), new Item(Items.BOLT_OF_CLOTH_8790, 6)),
    /**
     * The Boxing mat side.
     */
    BOXING_MAT_SIDE(13128, 8023, 32, 570.0, new Item(Items.OAK_PLANK_8778, 6), new Item(Items.BOLT_OF_CLOTH_8790, 4)),
    /**
     * The Fencing mat side.
     */
    FENCING_MAT_SIDE(13134, 8024, 41, 570.0, new Item(Items.OAK_PLANK_8778, 8), new Item(Items.BOLT_OF_CLOTH_8790, 6)),
    /**
     * The Combat mat side.
     */
    COMBAT_MAT_SIDE(13139, 8025, 51, 630.0, new Item(Items.TEAK_PLANK_8780, 6), new Item(Items.BOLT_OF_CLOTH_8790, 6)),
    /**
     * The Boxing mat.
     */
    BOXING_MAT(13127, 8023, 32, 570.0, new Item(Items.OAK_PLANK_8778, 6), new Item(Items.BOLT_OF_CLOTH_8790, 4)),
    /**
     * The Fencing mat.
     */
    FENCING_MAT(13136, 8024, 41, 570.0, new Item(Items.OAK_PLANK_8778, 8), new Item(Items.BOLT_OF_CLOTH_8790, 6)),
    /**
     * The Combat mat.
     */
    COMBAT_MAT(13140, 8025, 51, 630.0, new Item(Items.TEAK_PLANK_8780, 6), new Item(Items.BOLT_OF_CLOTH_8790, 6)),


    /**
     * Formal garden decorations
     */
    GAZEBO(13477, 8192, 65, 1200, new Item(Items.MAHOGANY_PLANK_8782, 8), new Item(Items.STEEL_BAR_2353, 4)),
    /**
     * The Small fountain.
     */
    SMALL_FOUNTAIN(13478, 8193, 71, 500, new Item(Items.MARBLE_BLOCK_8786, 1)),
    /**
     * The Large fountain.
     */
    LARGE_FOUNTAIN(13479, 8194, 75, 1000, new Item(Items.MARBLE_BLOCK_8786, 2)),
    /**
     * The Posh fountain.
     */
    POSH_FOUNTAIN(13480, 8195, 81, 1500, new Item(Items.MARBLE_BLOCK_8786, 3)),
    /**
     * The Sunflower.
     */
    SUNFLOWER(13446, 8213, 66, 70, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_SUNFLOWER_8457, 1)),
    /**
     * The Marigolds.
     */
    MARIGOLDS(13447, 8214, 71, 100, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_MARIGOLDS_8459, 1)),
    /**
     * The Roses.
     */
    ROSES(13448, 8215, 76, 122, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_ROSES_8461, 1)),
    /**
     * The Sunflower big.
     */
    SUNFLOWER_BIG(13443, 8213, 66, 70, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_SUNFLOWER_8457, 1)),
    /**
     * The Marigolds big.
     */
    MARIGOLDS_BIG(13444, 8214, 71, 100, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_MARIGOLDS_8459, 1)),
    /**
     * The Roses big.
     */
    ROSES_BIG(13445, 8215, 76, 122, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_ROSES_8461, 1)),
    /**
     * The Rosemary.
     */
    ROSEMARY(13440, 8210, 66, 70, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_ROSEMARY_8451, 1)),
    /**
     * The Daffodils.
     */
    DAFFODILS(13441, 8211, 71, 100, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_DAFFODILS_8453, 1)),
    /**
     * The Bluebells.
     */
    BLUEBELLS(13442, 8212, 76, 122, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_BLUEBELLS_8455, 1)),
    /**
     * The Rosemary big.
     */
    ROSEMARY_BIG(13437, 8210, 66, 70, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_ROSEMARY_8451, 1)),
    /**
     * The Daffodils big.
     */
    DAFFODILS_BIG(13438, 8211, 71, 100, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_DAFFODILS_8453, 1)),
    /**
     * The Bluebells big.
     */
    BLUEBELLS_BIG(13439, 8212, 76, 122, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.BAGGED_BLUEBELLS_8455, 1)),
    /**
     * The Thorny hedge 1.
     */
    THORNY_HEDGE1(13456, 8203, 56, 70, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.THORNY_HEDGE_8437, 1)),
    /**
     * The Thorny hedge 2.
     */
    THORNY_HEDGE2(13457, 8203, 56, 70, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.THORNY_HEDGE_8437, 1)),
    /**
     * The Thorny hedge 3.
     */
    THORNY_HEDGE3(13458, 8203, 56, 70, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.THORNY_HEDGE_8437, 1)),
    /**
     * The Nice hedge 1.
     */
    NICE_HEDGE1(13459, 8204, 60, 100, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.NICE_HEDGE_8439, 1)),
    /**
     * The Nice hedge 2.
     */
    NICE_HEDGE2(13461, 8204, 60, 100, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.NICE_HEDGE_8439, 1)),
    /**
     * The Nice hedge 3.
     */
    NICE_HEDGE3(13460, 8204, 60, 100, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.NICE_HEDGE_8439, 1)),
    /**
     * The Small box hedge 1.
     */
    SMALL_BOX_HEDGE1(13462, 8205, 64, 122, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.SMALL_BOX_HEDGE_8441, 1)),
    /**
     * The Small box hedge 2.
     */
    SMALL_BOX_HEDGE2(13464, 8205, 64, 122, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.SMALL_BOX_HEDGE_8441, 1)),
    /**
     * The Small box hedge 3.
     */
    SMALL_BOX_HEDGE3(13463, 8205, 64, 122, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.SMALL_BOX_HEDGE_8441, 1)),
    /**
     * The Topiary hedge 1.
     */
    TOPIARY_HEDGE1(13465, 8206, 68, 141, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.TOPIARY_HEDGE_8443, 1)),
    /**
     * The Topiary hedge 2.
     */
    TOPIARY_HEDGE2(13467, 8206, 68, 141, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.TOPIARY_HEDGE_8443, 1)),
    /**
     * The Topiary hedge 3.
     */
    TOPIARY_HEDGE3(13466, 8206, 68, 141, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.TOPIARY_HEDGE_8443, 1)),
    /**
     * The Fancy hedge 1.
     */
    FANCY_HEDGE1(13468, 8207, 72, 158, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.FANCY_HEDGE_8445, 1)),
    /**
     * The Fancy hedge 2.
     */
    FANCY_HEDGE2(13470, 8207, 72, 158, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.FANCY_HEDGE_8445, 1)),
    /**
     * The Fancy hedge 3.
     */
    FANCY_HEDGE3(13469, 8207, 72, 158, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.FANCY_HEDGE_8445, 1)),
    /**
     * The Tall fancy hedge 1.
     */
    TALL_FANCY_HEDGE1(13471, 8208, 76, 223, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.TALL_FANCY_HEDGE_8447, 1)),
    /**
     * The Tall fancy hedge 2.
     */
    TALL_FANCY_HEDGE2(13473, 8208, 76, 223, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.TALL_FANCY_HEDGE_8447, 1)),
    /**
     * The Tall fancy hedge 3.
     */
    TALL_FANCY_HEDGE3(13472, 8208, 76, 223, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.TALL_FANCY_HEDGE_8447, 1)),
    /**
     * The Tall box hedge 1.
     */
    TALL_BOX_HEDGE1(13474, 8209, 80, 316, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.TALL_BOX_HEDGE_8449, 1)),
    /**
     * The Tall box hedge 2.
     */
    TALL_BOX_HEDGE2(13476, 8209, 80, 316, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.TALL_BOX_HEDGE_8449, 1)),
    /**
     * The Tall box hedge 3.
     */
    TALL_BOX_HEDGE3(13475, 8209, 80, 316, new int[] {BuildingUtils.WATERING_CAN}, new Item(Items.TALL_BOX_HEDGE_8449, 1)),
    /**
     * The Boundary stones.
     */
    BOUNDARY_STONES(13449, 8196, 55, 100, new Item(Items.SOFT_CLAY_1761, 10)),
    /**
     * The Wooden fence.
     */
    WOODEN_FENCE(13450, 8197, 59, 280, new Item(Items.PLANK_960, 10)),
    /**
     * The Stone wall.
     */
    STONE_WALL(13451, 8198, 63, 200, new Item(Items.LIMESTONE_BRICK_3420, 10)),
    /**
     * The Iron railings.
     */
    IRON_RAILINGS(13452, 8199, 67, 220, new Item(Items.IRON_BAR_2351, 10), new Item(Items.LIMESTONE_BRICK_3420, 6)),
    /**
     * The Picket fence.
     */
    PICKET_FENCE(13453, 8200, 71, 640, new Item(Items.OAK_PLANK_8778, 10), new Item(Items.STEEL_BAR_2353, 2)),
    /**
     * The Garden fence.
     */
    GARDEN_FENCE(13454, 8201, 75, 940, new Item(Items.TEAK_PLANK_8780, 10), new Item(Items.STEEL_BAR_2353, 2)),
    /**
     * The Marble wall.
     */
    MARBLE_WALL(13455, 8202, 79, 4000, new Item(Items.MARBLE_BLOCK_8786, 10)),


    /**
     * Bedroom decorations.
     */
    WOODEN_BED(13148, 8031, 20, 117, new Item(Items.PLANK_960, 3), new Item(Items.BOLT_OF_CLOTH_8790, 2)),
    /**
     * The Oak bed.
     */
    OAK_BED(13149, 8032, 30, 210, new Item(Items.OAK_PLANK_8778, 3), new Item(Items.BOLT_OF_CLOTH_8790, 2)),
    /**
     * The Large oak bed.
     */
    LARGE_OAK_BED(13150, 8033, 34, 330, new Item(Items.OAK_PLANK_8778, 5), new Item(Items.BOLT_OF_CLOTH_8790, 2)),
    /**
     * The Teak bed.
     */
    TEAK_BED(13151, 8034, 40, 300, new Item(Items.TEAK_PLANK_8780, 3), new Item(Items.BOLT_OF_CLOTH_8790, 2)),
    /**
     * The Large teak bed.
     */
    LARGE_TEAK_BED(13152, 8035, 45, 480, new Item(Items.TEAK_PLANK_8780, 5), new Item(Items.BOLT_OF_CLOTH_8790, 2)),
    /**
     * The Four poster.
     */
    FOUR_POSTER(13153, 8036, 53, 450, new Item(Items.MAHOGANY_PLANK_8782, 3), new Item(Items.BOLT_OF_CLOTH_8790, 2)),
    /**
     * The Gilded four poster.
     */
    GILDED_FOUR_POSTER(13154, 8037, 60, 1330, new Item(Items.MAHOGANY_PLANK_8782, 5), new Item(Items.BOLT_OF_CLOTH_8790, 2), new Item(Items.GOLD_LEAF_8784, 2)),
    /**
     * The Oak clock.
     */
    OAK_CLOCK(13169, 8052, 25, 142, new Item(Items.OAK_PLANK_8778, 2), new Item(Items.CLOCKWORK_8792, 1)),
    /**
     * The Teak clock.
     */
    TEAK_CLOCK(13170, 8053, 55, 202, new Item(Items.TEAK_PLANK_8780, 2), new Item(Items.CLOCKWORK_8792, 1)),
    /**
     * The Gilded clock.
     */
    GILDED_CLOCK(13171, 8054, 85, 602, new Item(Items.MAHOGANY_PLANK_8782, 2), new Item(Items.CLOCKWORK_8792, 1), new Item(Items.GOLD_LEAF_8784, 1)),
    /**
     * The Shaving stand.
     */
    SHAVING_STAND(13162, 8045, 21, 30, new Item(Items.PLANK_960, 1), new Item(Items.MOLTEN_GLASS_1775, 1)),
    /**
     * The Oak shaving stand.
     */
    OAK_SHAVING_STAND(13163, 8046, 29, 61, new Item(Items.OAK_PLANK_8778, 1), new Item(Items.MOLTEN_GLASS_1775, 1)),
    /**
     * The Oak dresser.
     */
    OAK_DRESSER(13164, 8047, 37, 121, new Item(Items.OAK_PLANK_8778, 2), new Item(Items.MOLTEN_GLASS_1775, 1)),
    /**
     * The Teak dresser.
     */
    TEAK_DRESSER(13165, 8048, 46, 181, new Item(Items.TEAK_PLANK_8780, 2), new Item(Items.MOLTEN_GLASS_1775, 1)),
    /**
     * The Fancy teak dresser.
     */
    FANCY_TEAK_DRESSER(13166, 8049, 56, 182, new Item(Items.TEAK_PLANK_8780, 2), new Item(Items.MOLTEN_GLASS_1775, 2)),
    /**
     * The Mahogany dresser.
     */
    MAHOGANY_DRESSER(13167, 8050, 64, 281, new Item(Items.MAHOGANY_PLANK_8782, 2), new Item(Items.MOLTEN_GLASS_1775, 1)),
    /**
     * The Gilded dresser.
     */
    GILDED_DRESSER(13168, 8051, 74, 582, new Item(Items.MAHOGANY_PLANK_8782, 2), new Item(Items.MOLTEN_GLASS_1775, 2), new Item(Items.GOLD_LEAF_8784, 1)),
    /**
     * The Shoe box.
     */
    SHOE_BOX(13155, 8038, 20, 58, new Item(Items.PLANK_960, 2)),
    /**
     * The Oak drawers.
     */
    OAK_DRAWERS(13156, 8039, 27, 120, new Item(Items.OAK_PLANK_8778, 2)),
    /**
     * The Oak wardrobe.
     */
    OAK_WARDROBE(13157, 8040, 39, 180, new Item(Items.OAK_PLANK_8778, 3)),
    /**
     * The Teak drawers.
     */
    TEAK_DRAWERS(13158, 8041, 51, 180, new Item(Items.TEAK_PLANK_8780, 2)),
    /**
     * The Teak wardrobe.
     */
    TEAK_WARDROBE(13159, 8042, 63, 270, new Item(Items.TEAK_PLANK_8780, 3)),
    /**
     * The Mahogany wardrobe.
     */
    MAHOGANY_WARDROBE(13160, 8043, 75, 420, new Item(Items.MAHOGANY_PLANK_8782, 2)),
    /**
     * The Gilded wardrobe.
     */
    GILDED_WARDROBE(13161, 8044, 87, 720, new Item(Items.MAHOGANY_PLANK_8782, 2), new Item(Items.GOLD_LEAF_8784, 1)),


    /**
     * Quest hall decorations.
     */
    ANTIDRAGON_SHIELD(13522, 8282, 47, 280, new Item(Items.TEAK_PLANK_8780, 3), new Item(Items.ANTI_DRAGON_SHIELD_1540, 1)),
    /**
     * The Amulet of glory.
     */
    AMULET_OF_GLORY(13523, 8283, 47, 290, new Item(Items.TEAK_PLANK_8780, 3), new Item(Items.AMULET_OF_GLORY_1704, 1)),
    /**
     * The Cape of legends.
     */
    CAPE_OF_LEGENDS(13524, 8284, 47, 300, new Item(Items.TEAK_PLANK_8780, 3), new Item(Items.CAPE_OF_LEGENDS_1052, 1)),
    /**
     * The King arthur.
     */
    KING_ARTHUR(13510, 8285, 35, 211, new Item(Items.TEAK_PLANK_8780, 3), new Item(Items.ARTHUR_PORTRAIT_7995, 1)),
    /**
     * The Elena.
     */
    ELENA(13511, 8286, 35, 211, new Item(Items.TEAK_PLANK_8780, 3), new Item(Items.ELENA_PORTRAIT_7996, 1)),
    /**
     * The Giant dwarf.
     */
    GIANT_DWARF(13512, 8287, 35, 211, new Item(Items.TEAK_PLANK_8780, 3), new Item(Items.KELDAGRIM_PORTRAIT_7997, 1)),
    /**
     * The Miscellanians.
     */
    MISCELLANIANS(13513, 8288, 35, 311, new Item(Items.TEAK_PLANK_8780, 3), new Item(Items.MISC_PORTRAIT_7998, 1)),
    /**
     * The Lumbridge.
     */
    LUMBRIDGE(13517, 8289, 44, 314, new Item(Items.TEAK_PLANK_8780, 3), new Item(Items.LUMBRIDGE_PAINTING_8002, 1)),
    /**
     * The The desert.
     */
    THE_DESERT(13514, 8290, 44, 314, new Item(Items.TEAK_PLANK_8780, 3), new Item(Items.DESERT_PAINTING_7999, 1)),
    /**
     * The Morytania.
     */
    MORYTANIA(13518, 8291, 44, 314, new Item(Items.TEAK_PLANK_8780, 3), new Item(Items.MORYTANIA_PAINTING_8003, 1)),
    /**
     * The Karamja.
     */
    KARAMJA(13516, 8292, 65, 464, new Item(Items.MAHOGANY_PLANK_8782, 3), new Item(Items.KARAMJA_PAINTING_8001, 1)),
    /**
     * The Isafdar.
     */
    ISAFDAR(13515, 8293, 65, 464, new Item(Items.MAHOGANY_PLANK_8782, 3), new Item(Items.ISAFDAR_PAINTING_8000, 1)),
    /**
     * The Silverlight.
     */
    SILVERLIGHT(13519, 8279, 42, 187, new Item(Items.TEAK_PLANK_8780, 3), new Item(Items.SILVERLIGHT_2402, 1)),
    /**
     * The Excalibur.
     */
    EXCALIBUR(13521, 8280, 42, 194, new Item(Items.TEAK_PLANK_8780, 3), new Item(Items.EXCALIBUR_35, 1)),
    /**
     * The Darklight.
     */
    DARKLIGHT(13520, 8281, 42, 202, new Item(Items.TEAK_PLANK_8780, 3), new Item(Items.DARKLIGHT_6746, 1)),
    /**
     * The Small map.
     */
    SMALL_MAP(13525, 8294, 38, 211, new Item(Items.TEAK_PLANK_8780, 3), new Item(Items.SMALL_MAP_8004, 1)),
    /**
     * The Medium map.
     */
    MEDIUM_MAP(13526, 8295, 58, 451, new Item(Items.MAHOGANY_PLANK_8782, 3), new Item(Items.MEDIUM_MAP_8005, 1)),
    /**
     * The Large map.
     */
    LARGE_MAP(13527, 8296, 78, 591, new Item(Items.MAHOGANY_PLANK_8782, 4), new Item(Items.LARGE_MAP_8006, 1)),

    /**
     * Menagerie Decorations
     */
//OBELISK
	MINI_OBELISK(44837, 15236, 41, 676, new Item(Items.MARBLE_BLOCK_8786, 1), new Item(Items.SPIRIT_SHARDS_12183, 1000), new Item(Items.GOLD_CHARM_12158, 10), new Item(Items.GREEN_CHARM_12159, 10), new Item(Items.CRIMSON_CHARM_12160, 10), new Item(Items.BLUE_CHARM_12163, 10))
	//PET_FEEDER
	,
    /**
     * The Oak pet feeder.
     */
    OAK_PET_FEEDER(44834, 15233, 37, 240, new Item(Items.OAK_PLANK_8778, 4)),
    /**
     * The Teak pet feeder.
     */
    TEAK_PET_FEEDER(44835, 15234, 52, 380, new Item(Items.TEAK_PLANK_8780, 4)),
    /**
     * The Mahogany pet feeder.
     */
    MAHOGANY_PET_FEEDER(44836, 15235, 67, 880, new Item(Items.MAHOGANY_PLANK_8782, 4), new Item(Items.GOLD_LEAF_8784, 1))
	//PET_HOUSE
	,
    /**
     * The Oak pet house.
     */
    OAK_PET_HOUSE(44828, 15227, 37, 240, new Item(Items.OAK_PLANK_8778, 4)),
    /**
     * The Teak pet house.
     */
    TEAK_PET_HOUSE(44829, 15228, 52, 380, new Item(Items.TEAK_PLANK_8780, 4)),
    /**
     * The Mahogany pet house.
     */
    MAHOGANY_PET_HOUSE(44830, 15229, 67, 580, new Item(Items.MAHOGANY_PLANK_8782, 4)),
    /**
     * The Consecrated pet house.
     */
    CONSECRATED_PET_HOUSE(44831, 15230, 92, 1580, new Item(Items.MAHOGANY_PLANK_8782, 4), new Item(Items.MAGIC_STONE_8788, 1)),
    /**
     * The Desecrated pet house.
     */
    DESECRATED_PET_HOUSE(44832, 15231, 92, 1580, new Item(Items.MAHOGANY_PLANK_8782, 4), new Item(Items.MAGIC_STONE_8788, 1)),
    /**
     * The Natural pet house.
     */
    NATURAL_PET_HOUSE(44833, 15232, 92, 1580, new Item(Items.MAHOGANY_PLANK_8782, 4), new Item(Items.MAGIC_STONE_8788, 1))
	//HABITAT_SPACE
	,
    /**
     * The Garden habitat.
     */
    GARDEN_HABITAT(new int[]{
			4497,
			4498,
			44500,
			44501,
			44502,
			44503,
			44504,
			44505,
			44506,
			44507,
			44508,
			44509,
			44510,
			44511,
			44512,
			44513,
			44514,
			44515,
			44516,
			44517,
			44518,
			44519,
			44520,
			44521,
			44522,
			44523,
			44524,
			44525,
			44526,
			44527,
			44528,
			44529,
			44530,
			44531,
			44532,
			44533,
			44534,
			44535,
			44536,
			44537,
			44538,
			44539,
			44540,
			44541,
			44542,
			44543,
			44544,
			44545,
			44546,
			44547,
			44548,
			44549,
			44550,
			44551,
			44552,
			44553,
			44554,
			44555,
			44556,
			44557,
			44558,
			44559,
			44560,
			44561,
			44562,
			44563 }, 15222, 37, 201, new Item(Items.BAGGED_PLANT_1_8431, 1), new Item(Items.BAGGED_PLANT_2_8433, 1), new Item(Items.BAGGED_PLANT_3_8435, 1)),
    /**
     * The Jungle habitat.
     */
    JUNGLE_HABITAT(new int[]
			{
					44564,
					44565,
					44566,
					44567,
					44568,
					44569,
					44570,
					44571,
					44572,
					44573,
					44574,
					44575,
					44576,
					44577,
					44578,
					44579,
					44580,
					44581,
					44582,
					44583,
					44584,
					44585,
					44586,
					44587,
					44588,
					44589,
					44590,
					44591,
					44592,
					44593,
					44594,
					44595,
					44596,
					44597,
					44598,
					44599,
					44600,
					44601,
					44602,
					44603,
					44604,
					44605,
					44606,
					44607,
					44608,
					44609,
					44610,
					44611,
					44612,
					44613,
					44614,
					44615,
					44616,
					44617,
					44618,
					44619,
					44620,
					44621,
					44622,
					44623,
					44624,
					44625,
					44626,
					44627,
					44628,
					44629 }, 15223, 47, 278, new Item(Items.BAGGED_PLANT_3_8435, 3), new Item(Items.BAGGED_WILLOW_TREE_8423, 1), new Item(Items.BUCKET_OF_WATER_1929, 5)),
    /**
     * The Desert habitat.
     */
    DESERT_HABITAT(new int[]
			{
					44630,
					44631,
					44632,
					44633,
					44634,
					44635,
					44636,
					44637,
					44638,
					44639,
					44640,
					44641,
					44642,
					44643,
					44644,
					44645,
					44646,
					44647,
					44648,
					44649,
					44650,
					44651,
					44652,
					44653,
					44654,
					44655,
					44656,
					44657,
					44658,
					44659,
					44660,
					44661,
					44662,
					44663,
					44664,
					44665,
					44666,
					44667,
					44668,
					44669,
					44670,
					44671,
					44672,
					44673,
					44674,
					44675,
					44676,
					44677,
					44678,
					44679,
					44680,
					44681,
					44682,
					44683,
					44684,
					44685,
					44686,
					44687,
					44688,
					44689,
					44690,
					44691,
					44692,
					44693,
					44694,
					44695 }, 15224, 57, 238, new Item(Items.BUCKET_OF_SAND_1783, 10), new Item(Items.LIMESTONE_BRICK_3420, 5), new Item(15237, 1)),
    /**
     * The Polar habitat.
     */
    POLAR_HABITAT(new int[]
			{
					44696,
					44697,
					44698,
					44699,
					44700,
					44701,
					44702,
					44703,
					44704,
					44705,
					44706,
					44707,
					44708,
					44709,
					44710,
					44711,
					44712,
					44713,
					44714,
					44715,
					44716,
					44717,
					44718,
					44719,
					44720,
					44721,
					44722,
					44723,
					44724,
					44725,
					44726,
					44727,
					44728,
					44729,
					44730,
					44731,
					44732,
					44733,
					44734,
					44735,
					44736,
					44737,
					44738,
					44739,
					44740,
					44741,
					44742,
					44743,
					44744,
					44745,
					44746,
					44747,
					44748,
					44749,
					44750,
					44751,
					44752,
					44753,
					44754,
					44755,
					44756,
					44757,
					44758,
					44759,
					44760,
					44761 }, 15225, 67, 373, new Item(Items.AIR_RUNE_556, 1000), new Item(Items.WATER_RUNE_555, 1000), new Item(15239, 1)),
    /**
     * The Volcanic habitat.
     */
    VOLCANIC_HABITAT(new int[]
			{
					44762,
					44763,
					44764,
					44765,
					44766,
					44767,
					44768,
					44769,
					44770,
					44771,
					44772,
					44773,
					44774,
					44775,
					44776,
					44777,
					44778,
					44779,
					44780,
					44781,
					44782,
					44783,
					44784,
					44785,
					44786,
					44787,
					44788,
					44789,
					44790,
					44791,
					44792,
					44793,
					44794,
					44795,
					44796,
					44797,
					44798,
					44799,
					44800,
					44801,
					44802,
					44803,
					44804,
					44805,
					44806,
					44807,
					44808,
					44809,
					44810,
					44811,
					44812,
					44813,
					44814,
					44815,
					44816,
					44817,
					44818,
					44819,
					44820,
					44821,
					44822,
					44823,
					44824,
					44825,
					44826,
					44827 }, 15226, 77, 77, new Item(Items.FIRE_RUNE_554, 1000), new Item(Items.EARTH_RUNE_557, 1000), new Item(Items.BAGGED_DEAD_TREE_8417, 1), new Item(Items.STONE_SLAB_13245, 5)),


    /**
     * Study decorations.
     */
    GLOBE(13649, 8341, 41, 180, new Item(Items.OAK_PLANK_8778, 3)),
    /**
     * The Ornamental globe.
     */
    ORNAMENTAL_GLOBE(13650, 8342, 50, 270, new Item(Items.TEAK_PLANK_8780, 3)),
    /**
     * The Lunar globe.
     */
    LUNAR_GLOBE(13651, 8343, 59, 570, new Item(Items.TEAK_PLANK_8780, 3), new Item(Items.GOLD_LEAF_8784, 1)),
    /**
     * The Celestial globe.
     */
    CELESTIAL_GLOBE(13652, 8344, 68, 570, new Item(Items.TEAK_PLANK_8780, 3), new Item(Items.GOLD_LEAF_8784, 1)),
    /**
     * The Armillary sphere.
     */
    ARMILLARY_SPHERE(13653, 8345, 77, 960, new Item(Items.MAHOGANY_PLANK_8782, 2), new Item(Items.GOLD_LEAF_8784, 2), new Item(Items.STEEL_BAR_2353, 4)),
    /**
     * The Small orrey.
     */
    SMALL_ORREY(13654, 8346, 86, 1320, new Item(Items.MAHOGANY_PLANK_8782, 3), new Item(Items.GOLD_LEAF_8784, 3)),
    /**
     * The Large orrey.
     */
    LARGE_ORREY(13655, 8347, 95, 1420, new Item(Items.MAHOGANY_PLANK_8782, 3), new Item(Items.GOLD_LEAF_8784, 5)),
    /**
     * The Oak lectern.
     */
    OAK_LECTERN(13642, 8334, 40, 60, new Item(Items.OAK_PLANK_8778, 1)),
    /**
     * The Eagle lectern.
     */
    EAGLE_LECTERN(13643, 8335, 47, 120, new Item(Items.OAK_PLANK_8778, 2)),
    /**
     * The Demon lectern.
     */
    DEMON_LECTERN(13644, 8336, 47, 120, new Item(Items.OAK_PLANK_8778, 2)),
    /**
     * The Teak eagle lectern.
     */
    TEAK_EAGLE_LECTERN(13645, 8337, 57, 180, new Item(Items.TEAK_PLANK_8780, 2)),
    /**
     * The Teak demon lectern.
     */
    TEAK_DEMON_LECTERN(13646, 8338, 57, 180, new Item(Items.TEAK_PLANK_8780, 2)),
    /**
     * The Mahogany eagle lectern.
     */
    MAHOGANY_EAGLE_LECTERN(13647, 8339, 67, 580, new Item(Items.MAHOGANY_PLANK_8782, 2), new Item(Items.GOLD_LEAF_8784, 1)),
    /**
     * The Mahogany demon lectern.
     */
    MAHOGANY_DEMON_LECTERN(13648, 8340, 67, 580, new Item(Items.MAHOGANY_PLANK_8782, 2), new Item(Items.GOLD_LEAF_8784, 1)),
    /**
     * The Crystal ball.
     */
    CRYSTAL_BALL(13659, 8351, 42, 280, new Item(Items.TEAK_PLANK_8780, 3), new Item(Items.UNPOWERED_ORB_567, 1)),
    /**
     * The Elemental sphere.
     */
    ELEMENTAL_SPHERE(13660, 8352, 54, 580, new Item(Items.TEAK_PLANK_8780, 3), new Item(Items.UNPOWERED_ORB_567, 1), new Item(Items.GOLD_LEAF_8784, 1)),
    /**
     * The Crystal of power.
     */
    CRYSTAL_OF_POWER(13661, 8353, 66, 890, new Item(Items.MAHOGANY_PLANK_8782, 2), new Item(Items.UNPOWERED_ORB_567, 1), new Item(Items.GOLD_LEAF_8784, 2)),
    /**
     * The Alchemical chart.
     */
    ALCHEMICAL_CHART(13662, 8354, 43, 30, new Item(Items.BOLT_OF_CLOTH_8790, 2)),
    /**
     * The Astronomical chart.
     */
    ASTRONOMICAL_CHART(13663, 8355, 63, 45, new Item(Items.BOLT_OF_CLOTH_8790, 3)),
    /**
     * The Infernal chart.
     */
    INFERNAL_CHART(13664, 8356, 83, 60, new Item(Items.BOLT_OF_CLOTH_8790, 4)),
    /**
     * The Telescope 1.
     */
    TELESCOPE1(13656, 8348, 44, 121, new Item(Items.OAK_PLANK_8778, 2), new Item(Items.MOLTEN_GLASS_1775, 1)),
    /**
     * The Telescope 2.
     */
    TELESCOPE2(13657, 8349, 64, 181, new Item(Items.TEAK_PLANK_8780, 2), new Item(Items.MOLTEN_GLASS_1775, 1)),
    /**
     * The Telescope 3.
     */
    TELESCOPE3(13658, 8350, 84, 580, new Item(Items.MAHOGANY_PLANK_8782, 2), new Item(Items.MOLTEN_GLASS_1775, 1)),

    /**
     * Costume room decorations.
     */
    OAK_TREASURE_CHEST(18804, 9839, 48, 120, new Item(Items.OAK_PLANK_8778, 2)),
    /**
     * The Teak treasure chest.
     */
    TEAK_TREASURE_CHEST(18806, 9840, 66, 180, new Item(Items.TEAK_PLANK_8780, 2)),
    /**
     * The Mahogany treasure chest.
     */
    MAHOGANY_TREASURE_CHEST(18808, 9841, 84, 280, new Item(Items.MAHOGANY_PLANK_8782, 2)),
    /**
     * The Oak armour case.
     */
    OAK_ARMOUR_CASE(18778, 9826, 46, 180, new Item(Items.OAK_PLANK_8778, 3)),
    /**
     * The Teak armour case.
     */
    TEAK_ARMOUR_CASE(18780, 9827, 64, 270, new Item(Items.TEAK_PLANK_8780, 3)),
    /**
     * The Mgany armour case.
     */
    MGANY_ARMOUR_CASE(18782, 9828, 82, 420, new Item(Items.MAHOGANY_PLANK_8782, 3)),
    /**
     * The Oak magic wardrobe.
     */
    OAK_MAGIC_WARDROBE(18784, 9829, 42, 240, new Item(Items.OAK_PLANK_8778, 4)),
    /**
     * The C oak magic wardrobe.
     */
    C_OAK_MAGIC_WARDROBE(18786, 9830, 51, 360, new Item(Items.OAK_PLANK_8778, 6)),
    /**
     * The Teak magic wardrobe.
     */
    TEAK_MAGIC_WARDROBE(18788, 9831, 60, 360, new Item(Items.TEAK_PLANK_8780, 4)),
    /**
     * The C teak magic wardrobe.
     */
    C_TEAK_MAGIC_WARDROBE(18790, 9832, 69, 540, new Item(Items.TEAK_PLANK_8780, 6)),
    /**
     * The Mgany magic wardrobe.
     */
    MGANY_MAGIC_WARDROBE(18792, 9833, 78, 560, new Item(Items.MAHOGANY_PLANK_8782, 4)),
    /**
     * The Gilded magic wardrobe.
     */
    GILDED_MAGIC_WARDROBE(18794, 9834, 87, 860, new Item(Items.MAHOGANY_PLANK_8782, 4), new Item(Items.GOLD_LEAF_8784, 1)),
    /**
     * The Marble magic wardrobe.
     */
    MARBLE_MAGIC_WARDROBE(18796, 9835, 96, 500, new Item(Items.MARBLE_BLOCK_8786, 1)),
    /**
     * The Oak cape rack.
     */
    OAK_CAPE_RACK(18766, 9817, 54, 240, new Item(Items.OAK_PLANK_8778, 4)),
    /**
     * The Teak cape rack.
     */
    TEAK_CAPE_RACK(18767, 9818, 63, 360, new Item(Items.TEAK_PLANK_8780, 4)),
    /**
     * The Mgany cape rack.
     */
    MGANY_CAPE_RACK(18768, 9819, 72, 560, new Item(Items.MAHOGANY_PLANK_8782, 4)),
    /**
     * The Gilded cape rack.
     */
    GILDED_CAPE_RACK(18769, 9820, 81, 860, new Item(Items.MAHOGANY_PLANK_8782, 4), new Item(Items.GOLD_LEAF_8784, 1)),
    /**
     * The Marble cape rack.
     */
    MARBLE_CAPE_RACK(18770, 9821, 90, 500, new Item(Items.MARBLE_BLOCK_8786, 1)),
    /**
     * The Magic cape rack.
     */
    MAGIC_CAPE_RACK(18771, 9822, 99, 1000, new Item(Items.MAGIC_STONE_8788, 1)),
    /**
     * The Oak toy box.
     */
    OAK_TOY_BOX(18798, 9836, 50, 120, new Item(Items.OAK_PLANK_8778, 2)),
    /**
     * The Teak toy box.
     */
    TEAK_TOY_BOX(18800, 9837, 68, 180, new Item(Items.TEAK_PLANK_8780, 2)),
    /**
     * The Mahogany toy box.
     */
    MAHOGANY_TOY_BOX(18802, 9838, 86, 280, new Item(Items.MAHOGANY_PLANK_8782, 2)),
    /**
     * The Oak costume box.
     */
    OAK_COSTUME_BOX(18772, 9823, 44, 120, new Item(Items.OAK_PLANK_8778, 2)),
    /**
     * The Teak costume box.
     */
    TEAK_COSTUME_BOX(18774, 9824, 62, 180, new Item(Items.TEAK_PLANK_8780, 2)),
    /**
     * The Mahogany costume box.
     */
    MAHOGANY_COSTUME_BOX(18776, 9825, 80, 280, new Item(Items.MAHOGANY_PLANK_8782, 2)),

    /**
     * Chapel decorations.
     */
    OAK_ALTAR(13179, 8062, 45, 240, new Item(Items.OAK_PLANK_8778, 4)),
    /**
     * The Teak altar.
     */
    TEAK_ALTAR(13182, 8063, 50, 360, new Item(Items.TEAK_PLANK_8780, 4)),
    /**
     * The Cloth altar.
     */
    CLOTH_ALTAR(13185, 8064, 56, 390, new Item(Items.TEAK_PLANK_8780, 4), new Item(Items.BOLT_OF_CLOTH_8790, 2)),
    /**
     * The Mahogany altar.
     */
    MAHOGANY_ALTAR(13188, 8065, 60, 590, new Item(Items.MAHOGANY_PLANK_8782, 4), new Item(Items.BOLT_OF_CLOTH_8790, 2)),
    /**
     * The Limestone altar.
     */
    LIMESTONE_ALTAR(13191, 8066, 64, 910, new Item(Items.MAHOGANY_PLANK_8782, 6), new Item(Items.BOLT_OF_CLOTH_8790, 2), new Item(Items.LIMESTONE_BRICK_3420, 2)),
    /**
     * The Marble altar.
     */
    MARBLE_ALTAR(13194, 8067, 70, 1030, new Item(Items.MARBLE_BLOCK_8786, 2), new Item(Items.BOLT_OF_CLOTH_8790, 2)),
    /**
     * The Gilded altar.
     */
    GILDED_ALTAR(13197, 8068, 75, 2230, new Item(Items.MARBLE_BLOCK_8786, 2), new Item(Items.BOLT_OF_CLOTH_8790, 2), new Item(Items.GOLD_LEAF_8784, 4)),
    /**
     * The Small statue.
     */
    SMALL_STATUE(13271, 8082, 49, 40, new Item(Items.LIMESTONE_BRICK_3420, 2)),
    /**
     * The Medium statue.
     */
    MEDIUM_STATUE(13272, 8083, 69, 500, new Item(Items.MARBLE_BLOCK_8786, 1)),
    /**
     * The Large statue.
     */
    LARGE_STATUE(13282, 8084, 89, 1500, new Item(Items.MARBLE_BLOCK_8786, 3)),
    /**
     * The Windchimes.
     */
    WINDCHIMES(13214, 8079, 49, 323, new Item(Items.OAK_PLANK_8778, 4), new Item(Items.STEEL_BAR_2353, 4)),
    /**
     * The Bells.
     */
    BELLS(13215, 8080, 58, 480, new Item(Items.TEAK_PLANK_8780, 4), new Item(Items.STEEL_BAR_2353, 6)),
    /**
     * The Organ.
     */
    ORGAN(13216, 8081, 69, 680, new Item(Items.MAHOGANY_PLANK_8782, 4), new Item(Items.STEEL_BAR_2353, 6)),
    /**
     * The Saradomin symbol.
     */
    SARADOMIN_SYMBOL(13172, 8055, 48, 120, new Item(Items.OAK_PLANK_8778, 2)),
    /**
     * The Zamorak symbol.
     */
    ZAMORAK_SYMBOL(13173, 8056, 48, 120, new Item(Items.OAK_PLANK_8778, 2)),
    /**
     * The Guthix symbol.
     */
    GUTHIX_SYMBOL(13174, 8057, 48, 120, new Item(Items.OAK_PLANK_8778, 2)),
    /**
     * The Saradomin icon.
     */
    SARADOMIN_ICON(13175, 8058, 59, 960, new Item(Items.TEAK_PLANK_8780, 4), new Item(Items.GOLD_LEAF_8784, 2)),
    /**
     * The Zamorak icon.
     */
    ZAMORAK_ICON(13176, 8059, 59, 960, new Item(Items.TEAK_PLANK_8780, 4), new Item(Items.GOLD_LEAF_8784, 2)),
    /**
     * The Guthix icon.
     */
    GUTHIX_ICON(13177, 8060, 59, 960, new Item(Items.TEAK_PLANK_8780, 4), new Item(Items.GOLD_LEAF_8784, 2)),
    /**
     * The Icon of bob.
     */
    ICON_OF_BOB(13178, 8061, 71, 1160, new Item(Items.MAHOGANY_PLANK_8782, 4), new Item(Items.GOLD_LEAF_8784, 2)),
    /**
     * The Steel torches.
     */
    STEEL_TORCHES(13202, 8070, 45, 80, new Item(Items.STEEL_BAR_2353, 2)),
    /**
     * The Wooden torches.
     */
    WOODEN_TORCHES(13200, 8069, 49, 58, new Item(Items.PLANK_960, 2)),
    /**
     * The Steel candlesticks.
     */
    STEEL_CANDLESTICKS(13204, 8071, 53, 124, new Item(Items.STEEL_BAR_2353, 6), new Item(Items.CANDLE_36, 6)),
    /**
     * The Gold candlesticks.
     */
    GOLD_CANDLESTICKS(13206, 8072, 57, 46, new Item(Items.GOLD_BAR_2357, 6), new Item(Items.CANDLE_36, 6)),
    /**
     * The Incense burners.
     */
    INCENSE_BURNERS(13208, 8073, 61, 280, new Item(Items.OAK_PLANK_8778, 4), new Item(Items.STEEL_BAR_2353, 2)),
    /**
     * The Mahogany burners.
     */
    MAHOGANY_BURNERS(13210, 8074, 65, 600, new Item(Items.MAHOGANY_PLANK_8782, 4), new Item(Items.STEEL_BAR_2353, 2)),
    /**
     * The Marble burners.
     */
    MARBLE_BURNERS(13212, 8075, 69, 1600, new Item(Items.MARBLE_BLOCK_8786, 2), new Item(Items.STEEL_BAR_2353, 2)),
    /**
     * The Shuttered window.
     */
    SHUTTERED_WINDOW(new int[] { 13253, 13226, 13235, 13244, 13217, 13262 }, 8076, 49, 228, new Item(Items.PLANK_960, 8)),
    /**
     * The Decorative window.
     */
    DECORATIVE_WINDOW(new int[] { 13254, 13227, 13236, 13245, 13218, 13263 }, 8077, 69, 200, new Item(Items.MOLTEN_GLASS_1775, 8)),
    /**
     * The Stained glass.
     */
    STAINED_GLASS(new int[] { 13255, 13228, 13237, 13246, 13219, 13264 }, 8078, 89, 400, new Item(Items.MOLTEN_GLASS_1775, 16)),

    /**
     * Throne room
     */
    OAK_THRONE(13665, 8357, 60, 800, new Item(Items.OAK_PLANK_8778, 5), new Item(Items.MARBLE_BLOCK_8786, 1)),
    /**
     * The Teak throne.
     */
    TEAK_THRONE(13666, 8358, 67, 1450, new Item(Items.TEAK_PLANK_8780, 5), new Item(Items.MARBLE_BLOCK_8786, 2)),
    /**
     * The Mahogany throne.
     */
    MAHOGANY_THRONE(13667, 8359, 74, 2200, new Item(Items.MAHOGANY_PLANK_8782, 5), new Item(Items.MARBLE_BLOCK_8786, 3)),
    /**
     * The Gilded throne.
     */
    GILDED_THRONE(13668, 8360, 81, 1700, new Item(Items.MAHOGANY_PLANK_8782, 5), new Item(Items.MARBLE_BLOCK_8786, 2), new Item(Items.GOLD_LEAF_8784, 3)),
    /**
     * The Skeleton throne.
     */
    SKELETON_THRONE(13669, 8361, 88, 7003, new Item(Items.MAGIC_STONE_8788, 5), new Item(Items.MARBLE_BLOCK_8786, 4), new Item(Items.BONES_526, 5), new Item(Items.SKULL_964, 2)),
    /**
     * The Crystal throne.
     */
    CRYSTAL_THRONE(13670, 8362, 95, 15000, new Item(Items.MAGIC_STONE_8788, 15)),
    /**
     * The Demonic throne.
     */
    DEMONIC_THRONE(13671, 8363, 99, 25000, new Item(Items.MAGIC_STONE_8788, 25)),
    /**
     * The Oak lever.
     */
    OAK_LEVER(13672, 8364, 68, 300, new Item(Items.OAK_PLANK_8778, 5)),
    /**
     * The Teak lever.
     */
    TEAK_LEVER(13673, 8365, 78, 450, new Item(Items.TEAK_PLANK_8780, 5)),
    /**
     * The Mahogany lever.
     */
    MAHOGANY_LEVER(13674, 8366, 88, 700, new Item(Items.MAHOGANY_PLANK_8782, 5)),
    /**
     * The Floor decoration.
     */
    FLOOR_DECORATION(new int[] { 13689, 13686, 13687, 13688, 13684, 13685 }, 8370, 61, 700, new Item(Items.MAHOGANY_PLANK_8782, 5)),
    /**
     * The Steel cage.
     */
    STEEL_CAGE(new int[] { 13689, 13686, 13687, 13688, 13684, 13685 }, 8371, 68, 1100, new Item(Items.MAHOGANY_PLANK_8782, 5), new Item(Items.STEEL_BAR_2353, 20)),
    /**
     * The Floor trap.
     */
    FLOOR_TRAP(new int[] { 13689, 13686, 13687, 13688, 13684, 13685 }, 8372, 74, 770, new Item(Items.MAHOGANY_PLANK_8782, 5), new Item(Items.CLOCKWORK_8792, 10)),
    /**
     * The Magic circle.
     */
    MAGIC_CIRCLE(new int[] { 13689, 13686, 13687, 13688, 13684, 13685 }, 8373, 82, 2700, new Item(Items.MAHOGANY_PLANK_8782, 5), new Item(Items.MAGIC_STONE_8788, 2)),
    /**
     * The Magic cage.
     */
    MAGIC_CAGE(new int[] { 13689, 13686, 13687, 13688, 13684, 13685 }, 8374, 89, 4700, new Item(Items.MAHOGANY_PLANK_8782, 5), new Item(Items.MAGIC_STONE_8788, 4)),
    /**
     * The Oak trapdoor.
     */
    OAK_TRAPDOOR(13675, 8367, 68, 300, new Item(Items.OAK_PLANK_8778, 5)),
    /**
     * The Teak trapdoor.
     */
    TEAK_TRAPDOOR(13676, 8368, 78, 450, new Item(Items.TEAK_PLANK_8780, 5)),
    /**
     * The Mahogany trapdoor.
     */
    MAHOGANY_TRAPDOOR(13677, 8369, 88, 700, new Item(Items.MAHOGANY_PLANK_8782, 5)),
    /**
     * The Carved teak bench.
     */
    CARVED_TEAK_BENCH(13694, 8112, 44, 360, new Item(Items.TEAK_PLANK_8780, 4)),
    /**
     * The Mahogany bench.
     */
    MAHOGANY_BENCH(13695, 8113, 52, 560, new Item(Items.MAHOGANY_PLANK_8782, 4)),
    /**
     * The Gilded bench.
     */
    GILDED_BENCH(13696, 8114, 61, 1760, new Item(Items.MAHOGANY_PLANK_8782, 4), new Item(Items.GOLD_LEAF_8784, 4)),
    /**
     * The Oak deco.
     */
    OAK_DECO(13798, 8102, 16, 120.0, new Item(Items.OAK_PLANK_8778, 2)),
    /**
     * The Teak deco.
     */
    TEAK_DECO(13814, 8103, 36, 180.0, new Item(Items.TEAK_PLANK_8780, 2)),
    /**
     * The Gilded deco.
     */
    GILDED_DECO(13782, 8104, 56, 1020.0, new Item(Items.MAHOGANY_PLANK_8782, 3), new Item(Items.GOLD_LEAF_8784, 2)),
    /**
     * The Round shield.
     */
    ROUND_SHIELD(13734, 8105, 66, 120, new Item(Items.OAK_PLANK_8778, 2)),
    /**
     * The Square shield.
     */
    SQUARE_SHIELD(13766, 8106, 76, 360, new Item(Items.TEAK_PLANK_8780, 4)),
    /**
     * The Kite shield.
     */
    KITE_SHIELD(13750, 8107, 86, 420, new Item(Items.MAHOGANY_PLANK_8782, 3)),

    /**
     * Oubliette
     */
    SPIKES_MID(13334, 8302, 65, 623, new Item(Items.STEEL_BAR_2353, 20), new Item(Items.COINS_995, 50000)),
    /**
     * The Spikes side.
     */
    SPIKES_SIDE(13335, 8302, 65, 623, new Item(Items.STEEL_BAR_2353, 20), new Item(Items.COINS_995, 50000)),
    /**
     * The Spikes corner.
     */
    SPIKES_CORNER(13336, 8302, 65, 623, new Item(Items.STEEL_BAR_2353, 20), new Item(Items.COINS_995, 50000)),
    /**
     * The Spikes fl.
     */
    SPIKES_FL(13338, 8302, 65, 623, new Item(Items.STEEL_BAR_2353, 20), new Item(Items.COINS_995, 50000)),
    /**
     * The Tentacle mid.
     */
    TENTACLE_MID(13331, 8303, 71, 326, new Item(Items.BUCKET_OF_WATER_1929, 20), new Item(Items.COINS_995, 100000)),
    /**
     * The Tentacle side.
     */
    TENTACLE_SIDE(13332, 8303, 71, 326, new Item(Items.BUCKET_OF_WATER_1929, 20), new Item(Items.COINS_995, 100000)),
    /**
     * The Tentacle corner.
     */
    TENTACLE_CORNER(13333, 8303, 71, 326, new Item(Items.BUCKET_OF_WATER_1929, 20), new Item(Items.COINS_995, 100000)),
    /**
     * The Tentacle fl.
     */
    TENTACLE_FL(13338, 8303, 71, 326, new Item(Items.BUCKET_OF_WATER_1929, 20), new Item(Items.COINS_995, 100000)),
    /**
     * The Fp floor mid.
     */
    FP_FLOOR_MID(13371, 8304, 77, 357, new Item(Items.TINDERBOX_590, 20), new Item(Items.COINS_995, 125000)),
    /**
     * The Fp floor side.
     */
    FP_FLOOR_SIDE(13371, 8304, 77, 357, new Item(Items.TINDERBOX_590, 20), new Item(Items.COINS_995, 125000)),
    /**
     * The Fp floor corner.
     */
    FP_FLOOR_CORNER(13371, 8304, 77, 357, new Item(Items.TINDERBOX_590, 20), new Item(Items.COINS_995, 125000)),
    /**
     * The Flame pit.
     */
    FLAME_PIT(13337, 8304, 77, 357, new Item(Items.TINDERBOX_590, 20), new Item(Items.COINS_995, 125000)),
    /**
     * The Rocnar floor mid.
     */
    ROCNAR_FLOOR_MID(13371, 8305, 83, 387, new Item(Items.COINS_995, 150000)),
    /**
     * The Rocnar floor side.
     */
    ROCNAR_FLOOR_SIDE(13371, 8305, 83, 387, new Item(Items.COINS_995, 150000)),
    /**
     * The Rocnar floor corner.
     */
    ROCNAR_FLOOR_CORNER(13371, 8305, 83, 387, new Item(Items.COINS_995, 150000)),
    /**
     * The Rocnar.
     */
    ROCNAR(13373, 8305, 83, 387, new Item(Items.COINS_995, 150000)),
    /**
     * The Rocnar fl.
     */
    ROCNAR_FL(13338, 8305, 83, 387, new Item(Items.COINS_995, 150000)),
    /**
     * The Oak cage.
     */
    OAK_CAGE(13313, 8297, 65, 640, new Item(Items.OAK_PLANK_8778, 10), new Item(Items.STEEL_BAR_2353, 2)),
    /**
     * The Oak cage door.
     */
    OAK_CAGE_DOOR(13314, 8297, 65, 640, new Item(Items.OAK_PLANK_8778, 10), new Item(Items.STEEL_BAR_2353, 2)),
    /**
     * The Oak steel cage.
     */
    OAK_STEEL_CAGE(13316, 8298, 70, 800, new Item(Items.OAK_PLANK_8778, 10), new Item(Items.STEEL_BAR_2353, 10)),
    /**
     * The Oak steel cage door.
     */
    OAK_STEEL_CAGE_DOOR(13317, 8298, 70, 800, new Item(Items.OAK_PLANK_8778, 10), new Item(Items.STEEL_BAR_2353, 10)),
    /**
     * The Steel cage ou.
     */
    STEEL_CAGE_OU(13319, 8299, 75, 400, new Item(Items.STEEL_BAR_2353, 20)),
    /**
     * The Steel cage door.
     */
    STEEL_CAGE_DOOR(13320, 8299, 75, 400, new Item(Items.STEEL_BAR_2353, 20)),
    /**
     * The Spiked cage.
     */
    SPIKED_CAGE(13322, 8300, 80, 500, new Item(Items.STEEL_BAR_2353, 25)),
    /**
     * The Spiked cage door.
     */
    SPIKED_CAGE_DOOR(13323, 8300, 80, 500, new Item(Items.STEEL_BAR_2353, 25)),
    /**
     * The Bone cage.
     */
    BONE_CAGE(13325, 8301, 85, 603, new Item(Items.OAK_PLANK_8778, 10), new Item(Items.BONES_526, 10)),
    /**
     * The Bone cage door.
     */
    BONE_CAGE_DOOR(13326, 8301, 85, 603, new Item(Items.OAK_PLANK_8778, 10), new Item(Items.BONES_526, 10)),
    /**
     * The Skeleton guard.
     */
    SKELETON_GUARD(13366, 8131, 70, 223, new Item(Items.COINS_995, 50000)),
    /**
     * The Guard dog.
     */
    GUARD_DOG(13367, 8132, 74, 273, new Item(Items.COINS_995, 75000)),
    /**
     * The Hobgoblin.
     */
    HOBGOBLIN(13368, 8133, 78, 316, new Item(Items.COINS_995, 100000)),
    /**
     * The Baby red dragon.
     */
    BABY_RED_DRAGON(13372, 8134, 82, 387, new Item(Items.COINS_995, 150000)),
    /**
     * The Huge spider.
     */
    HUGE_SPIDER(13370, 8135, 86, 447, new Item(Items.COINS_995, 200000)),
    /**
     * The Troll.
     */
    TROLL(13369, 8136, 90, 1000, new Item(Items.COINS_995, 1000000)),
    /**
     * The Hellhound.
     */
    HELLHOUND(2715, 8137, 94, 2236, new Item(Items.COINS_995, 5000000)),
    /**
     * The Oak ladder.
     */
    OAK_LADDER(13328, 8306, 68, 300, new Item(Items.OAK_PLANK_8778, 5)),
    /**
     * The Teak ladder.
     */
    TEAK_LADDER(13329, 8307, 78, 450, new Item(Items.TEAK_PLANK_8780, 5)),
    /**
     * The Mahogany ladder.
     */
    MAHOGANY_LADDER(13330, 8308, 88, 700, new Item(Items.MAHOGANY_PLANK_8782, 5)),
    /**
     * The Decorative blood.
     */
    DECORATIVE_BLOOD(13312, 8125, 72, 4, new Item(Items.RED_DYE_1763, 4)),
    /**
     * The Decorative pipe.
     */
    DECORATIVE_PIPE(13311, 8126, 83, 120, new Item(Items.STEEL_BAR_2353, 6)),
    /**
     * The Hanging skeleton.
     */
    HANGING_SKELETON(13310, 8127, 94, 3, new Item(Items.SKULL_964, 2), new Item(Items.BONES_526, 6)),
    /**
     * The Candle.
     */
    CANDLE(13342, 8128, 72, 243, new Item(Items.OAK_PLANK_8778, 4), new Item(Items.LIT_CANDLE_33, 4)),
    /**
     * The Torch.
     */
    TORCH(13341, 8129, 84, 244, new Item(Items.OAK_PLANK_8778, 4), new Item(Items.LIT_TORCH_594, 4)),
    /**
     * The Skull torch.
     */
    SKULL_TORCH(13343, 8130, 94, 246, new Item(Items.OAK_PLANK_8778, 4), new Item(Items.LIT_TORCH_594, 4), new Item(Items.SKULL_964, 4)),

    /**
     * Dungeon corridor, junction, stairs & pit
     */
    OAK_DOOR_LEFT(13344, 8122, 74, 600, new Item(Items.OAK_PLANK_8778, 10)),
    /**
     * The Oak door right.
     */
    OAK_DOOR_RIGHT(13345, 8122, 74, 600, new Item(Items.OAK_PLANK_8778, 10)),
    /**
     * The Steel door left.
     */
    STEEL_DOOR_LEFT(13346, 8123, 84, 800, new Item(Items.OAK_PLANK_8778, 10), new Item(Items.STEEL_BAR_2353, 10)),
    /**
     * The Steel door right.
     */
    STEEL_DOOR_RIGHT(13347, 8123, 84, 800, new Item(Items.OAK_PLANK_8778, 10), new Item(Items.STEEL_BAR_2353, 10)),
    /**
     * The Marble door left.
     */
    MARBLE_DOOR_LEFT(13348, 8124, 94, 2000, new Item(Items.MARBLE_BLOCK_8786, 4)),
    /**
     * The Marble door right.
     */
    MARBLE_DOOR_RIGHT(13349, 8124, 94, 2000, new Item(Items.MARBLE_BLOCK_8786, 4)),
    /**
     * The Spike trap.
     */
    SPIKE_TRAP(13356, 8143, 72, 223, new Item(Items.COINS_995, 50000)),
    /**
     * The Man trap.
     */
    MAN_TRAP(13357, 8144, 76, 273, new Item(Items.COINS_995, 75000)),
    /**
     * The Tangle trap.
     */
    TANGLE_TRAP(13358, 8145, 80, 316, new Item(Items.COINS_995, 100000)),
    /**
     * The Marble trap.
     */
    MARBLE_TRAP(13359, 8146, 84, 387, new Item(Items.COINS_995, 150000)),
    /**
     * The Teleport trap.
     */
    TELEPORT_TRAP(13360, 8147, 88, 447, new Item(Items.COINS_995, 200000)),

    /**
     * The Pit dog.
     */
    /*     objID, int, lvl, exp, materials */
	PIT_DOG(39260, 18791, 70, 200, new Item(Items.COINS_995, 40000)),
    /**
     * The Pit ogre.
     */
    PIT_OGRE(39261, 18792, 73, 234, new Item(Items.COINS_995, 55000)),
    /**
     * The Pit rock protector.
     */
    PIT_ROCK_PROTECTOR(39262, 18793, 79, 300, new Item(Items.COINS_995, 90000)),
    /**
     * The Pit scabarite.
     */
    PIT_SCABARITE(39263, 18794, 84, 387, new Item(Items.COINS_995, 150000)),
    /**
     * The Pit black demon.
     */
    PIT_BLACK_DEMON(39264, 18795, 89, 547, new Item(Items.COINS_995, 300000)),
    /**
     * The Pit iron dragon.
     */
    PIT_IRON_DRAGON(39265, 18796, 97, 2738, new Item(Items.COINS_995, 7500000)),

    /**
     * Treasure room
     */
    DEMON(13378, 8138, 75, 707, new Item(Items.COINS_995, 500000)),
    /**
     * The Kalphite soldier.
     */
    KALPHITE_SOLDIER(13374, 8139, 80, 866, new Item(Items.COINS_995, 750000)),
    /**
     * The Tok xil.
     */
    TOK_XIL(13377, 8140, 85, 2236, new Item(Items.COINS_995, 5000000)),
    /**
     * The Dagannoth.
     */
    DAGANNOTH(13376, 8141, 90, 2738, new Item(Items.COINS_995, 7500000)),
    /**
     * The Steel dragon.
     */
    STEEL_DRAGON(13375, 8142, 95, 3162, new Item(Items.COINS_995, 1000000)),
    /**
     * The Wooden crate.
     */
    WOODEN_CRATE(13283, 8148, 75, 143, new Item(Items.PLANK_960, 5)),
    /**
     * The Oak t chest.
     */
    OAK_T_CHEST(13285, 8149, 79, 340, new Item(Items.OAK_PLANK_8778, 5), new Item(Items.STEEL_BAR_2353, 2)),
    /**
     * The Teak t chest.
     */
    TEAK_T_CHEST(13287, 8150, 83, 530, new Item(Items.TEAK_PLANK_8780, 5), new Item(Items.STEEL_BAR_2353, 4)),
    /**
     * The Mgany t chest.
     */
    MGANY_T_CHEST(13289, 8151, 87, 1000, new Item(Items.MAHOGANY_PLANK_8782, 5), new Item(Items.GOLD_LEAF_8784, 1)),
    /**
     * The Magic chest.
     */
    MAGIC_CHEST(13291, 8152, 91, 1000, new Item(Items.MAGIC_STONE_8788, 1)),

    /**
     * Style related decoration.
     */
    BASIC_WOOD_WINDOW(13099, -1, 1, 0.0),
    /**
     * Basic stone window decoration.
     */
    BASIC_STONE_WINDOW(13091, -1, 1, 0.0),
    /**
     * Whitewashed stone window decoration.
     */
    WHITEWASHED_STONE_WINDOW(13005, -1, 1, 0.0),
    /**
     * Fremennik window decoration.
     */
    FREMENNIK_WINDOW(13112, -1, 1, 0.0),
    /**
     * Tropical wood window decoration.
     */
    TROPICAL_WOOD_WINDOW(10816, -1, 1, 0.0),
    /**
     * Fancy stone window decoration.
     */
    FANCY_STONE_WINDOW(13117, -1, 1, 0.0),
	
	;
	/**
	 * The object id.
	 */
	private final int objectId;
	
	/**
	 * The item id for the interface.
	 */
	private final int interfaceItem;

	/**
	 * The level requirement.
	 */
	private final int level;
	
	/**
	 * The experience gained for building this decoration.
	 */
	private final double experience;
	
	/**
	 * The item required.
	 */
	private final Item[] items;
	
	/**
	 * The tools required.
	 */
	private final int[] tools;

	/**
	 * The object ids depending on styling.
	 */
	private final int[] objectIds;
	
	/**
	 * If this node should be invisible to user build options
	 */
	private boolean invisibleNode;
	
	/**
	 * Constructs a new {@code Portal} {@code Object}.
	 * @param objectId The object id.
	 * @param interfaceItem The item id for the building interface.
	 * @param level The level required.
	 * @param experience The experience gained.
	 * @param items The items required.
	 */
	private Decoration(int objectId, int interfaceItem, int level, double experience, Item... items) {
		this(objectId, interfaceItem, level, experience, new int[] { 2347, 8794 }, items);
	}
	
	/**
	 * Constructs a new {@code Portal} {@code Object}.
	 * @param objectId The object id.
	 * @param interfaceItem The item id for the building interface.
	 * @param level The level required.
	 * @param experience The experience gained.
	 * @param items The items required.
	 */
	private Decoration(int objectId, int interfaceItem, int level, double experience, int[] tools, Item... items) {
		this.objectId = objectId;
		this.objectIds = null;
		this.interfaceItem = interfaceItem;
		this.level = level;
		this.experience = experience;
		this.tools = tools;
		this.items = items;
	}
	
	/**
	 * Decoration
	 * @param objectId
	 * @param invisibleNode
	 */
	private Decoration(int objectId, boolean invisibleNode) {
		this(objectId, -1, -1, -1);
		this.invisibleNode = true;
	}
	
	/**
	 * Constructs a new {@code Portal} {@code Object}.
	 * @param objectIds The object id.
	 * @param interfaceItem The item id for the building interface.
	 * @param level The level required.
	 * @param experience The experience gained.
	 * @param items The items required.
	 */
	private Decoration(int[] objectIds, int interfaceItem, int level, double experience, Item... items) {
		this(objectIds, interfaceItem, level, experience, new int[] { 2347, 8794 }, items);
	}
	
	/**
	 * Constructs a new {@code Portal} {@code Object}.
	 * @param objectIds The object id.
	 * @param interfaceItem The item id for the building interface.
	 * @param level The level required.
	 * @param experience The experience gained.
	 * @param items The items required.
	 */
	private Decoration(int[] objectIds, int interfaceItem, int level, double experience, int[] tools, Item... items) {
		this.objectId = objectIds[0];
		this.objectIds = objectIds;
		this.interfaceItem = interfaceItem;
		this.level = level;
		this.experience = experience;
		this.tools = tools;
		this.items = items;
	}

    /**
     * Gets the decoration on the given location.
     *
     * @param player The player.
     * @param object the object
     * @return The decoration.
     */
    public static Decoration getDecoration(Player player, Scenery object) {
		Location l = object.getLocation();
		int z = l.getZ();
		if (HouseManager.isInDungeon(player)) {
			z = 3;
		}
		Room room = player.getHouseManager().getRooms()[z][l.getChunkX()][l.getChunkY()];
		for (Hotspot h : room.getHotspots()) {
			if (h.getCurrentX() == l.getChunkOffsetX() && h.getCurrentY() == l.getChunkOffsetY()) {
				if (h.getDecorationIndex() != -1) {
					Decoration deco = h.getHotspot().getDecorations()[h.getDecorationIndex()];
					if (deco.getObjectId(player.getHouseManager().getStyle()) == object.getId()) {
						return deco;
					}
				}
			}
		}
		return null;
	}

    /**
     * Gets a decoration for the given object id
     *
     * @param objectId - the object id of the built object
     * @return the decoration or null
     */
    public static Decoration forObjectId(int objectId) {
		for (Decoration d : Decoration.values()) {
			if (d.getObjectId() == objectId) {
				return d;
			}
		}
		return null;
	}

    /**
     * For name decoration.
     *
     * @param name the name
     * @return the decoration
     */
    public static Decoration forName(String name) {
		for (Decoration d : Decoration.values()) {
			if (d.name().equals(name)) {
				return d;
			}
		}
		return null;
	}

    /**
     * Gets the amount of nails required for this hotspot.
     *
     * @return The amount of nails.
     */
    public int getNailAmount() {
		for (Item item : items) {
			if (item.getId() == 960) { //1 nail per normal plank required.
				return item.getAmount();
			}
		}
		return 0;
	}

    /**
     * Gets the objectId.
     *
     * @param style The current housing style.
     * @return The objectId.
     */
    public int getObjectId(HousingStyle style) {
		if (objectIds != null) {
			return objectIds[style.ordinal()];
		}
		return objectId;
	}

    /**
     * Gets the objectId.
     *
     * @return The objectId.
     */
    public int getObjectId() {
		return objectId;
	}

    /**
     * Gets the level.
     *
     * @return The level.
     */
    public int getLevel() {
		return level;
	}

    /**
     * Gets the experience.
     *
     * @return The experience.
     */
    public double getExperience() {
		return experience;
	}

    /**
     * Gets the items.
     *
     * @return The items.
     */
    public Item[] getItems() {
		return items;
	}

    /**
     * Gets the tools.
     *
     * @return The tools.
     */
    public int[] getTools() {
		return tools;
	}

    /**
     * Gets the interfaceItem.
     *
     * @return the interfaceItem
     */
    public int getInterfaceItem() {
		return interfaceItem;
	}

    /**
     * Gets the objectIds value.
     *
     * @return The objectIds.
     */
    public int[] getObjectIds() {
		return objectIds;
	}

    /**
     * Is invisible node boolean.
     *
     * @return the boolean
     */
    public boolean isInvisibleNode() {
		return invisibleNode;
	}
	
	

}
