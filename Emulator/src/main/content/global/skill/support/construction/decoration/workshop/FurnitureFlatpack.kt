package content.global.skill.support.construction.decoration.workshop

import cfg.consts.Items
import core.game.node.item.Item

@Suppress("unused")
enum class FurnitureFlatpack(val interfaceItem: Int, val producedItemId: Int, val requiredLevel: Int, val experience: Double, val items: Array<Item>) {
    /**
     * Crude Wooden Chair Flatpack
     *
     * @constructor Crude Wooden Chair Flatpack
     */
    CRUDE_WOODEN_CHAIR_FLATPACK(Items.CRUDE_WOODEN_CHAIR_8309, Items.CRUDE_WOODEN_CHAIR_8496, 1, 58.00, arrayOf(Item(Items.PLANK_960, 2))),

    /**
     * Wooden Chair Flatpack
     *
     * @constructor Wooden Chair Flatpack
     */
    WOODEN_CHAIR_FLATPACK(Items.WOODEN_CHAIR_8310, Items.WOODEN_CHAIR_8498, 8, 87.00, arrayOf(Item(Items.PLANK_960, 3))),

    /**
     * Rocking Chair Flatpack
     *
     * @constructor Rocking Chair Flatpack
     */
    ROCKING_CHAIR_FLATPACK(Items.ROCKING_CHAIR_8311, Items.ROCKING_CHAIR_8500, 14, 87.00, arrayOf(Item(Items.PLANK_960, 3))),

    /**
     * Oak Chair Flatpack
     *
     * @constructor Oak Chair Flatpack
     */
    OAK_CHAIR_FLATPACK(Items.OAK_CHAIR_8312, Items.OAK_CHAIR_8502, 19, 120.00, arrayOf(Item(Items.OAK_PLANK_8778, 2))),

    /**
     * Oak Armchair Flatpack
     *
     * @constructor Oak Armchair Flatpack
     */
    OAK_ARMCHAIR_FLATPACK(Items.OAK_ARMCHAIR_8313, Items.OAK_ARMCHAIR_8504, 26, 180.00, arrayOf(Item(Items.OAK_PLANK_8778, 2))),

    /**
     * Teak Armchair Flatpack
     *
     * @constructor Teak Armchair Flatpack
     */
    TEAK_ARMCHAIR_FLATPACK(Items.TEAK_ARMCHAIR_8314, Items.TEAK_ARMCHAIR_8506, 35, 180.00, arrayOf(Item(Items.TEAK_PLANK_8780, 2))),

    /**
     * Mahogany Armchair Flatpack
     *
     * @constructor Mahogany Armchair Flatpack
     */
    MAHOGANY_ARMCHAIR_FLATPACK(Items.MAHOGANY_ARMCHAIR_8315, Items.MAHOGANY_ARMCHAIR_8508, 50, 280.00, arrayOf(Item(Items.MAHOGANY_PLANK_8782, 2))),

    /**
     * Wooden Bookcase Flatpack
     *
     * @constructor Wooden Bookcase Flatpack
     */
    WOODEN_BOOKCASE_FLATPACK(Items.WOODEN_BOOKCASE_8319, Items.WOODEN_BOOKCASE_8510, 4, 115.00, arrayOf(Item(Items.PLANK_960, 4))),

    /**
     * Oak Bookcase Flatpack
     *
     * @constructor Oak Bookcase Flatpack
     */
    OAK_BOOKCASE_FLATPACK(Items.OAK_BOOKCASE_8320, Items.OAK_BOOKCASE_8512, 29, 180.00, arrayOf(Item(Items.OAK_PLANK_8778, 3))),

    /**
     * Mahogany Bookcase Flatpack
     *
     * @constructor Mahogany Bookcase Flatpack
     */
    MAHOGANY_BOOKCASE_FLATPACK(Items.MAHOGANY_BKCASE_8321, Items.MAHOGANY_BKCASE_8514, 40, 420.00, arrayOf(Item(Items.MAHOGANY_PLANK_8782, 4))),

    /**
     * Wooden Kitchen Table Flatpack
     *
     * @constructor Wooden Kitchen Table Flatpack
     */
    WOODEN_KITCHEN_TABLE_FLATPACK(Items.WOOD_KITCHEN_TABLE_8246, Items.WOOD_KITCHEN_TABLE_8528, 12, 87.00, arrayOf(Item(Items.PLANK_960, 3))),

    /**
     * Oak Kitchen Table Flatpack
     *
     * @constructor Oak Kitchen Table Flatpack
     */
    OAK_KITCHEN_TABLE_FLATPACK(Items.OAK_KITCHEN_TABLE_8247, Items.OAK_KITCHEN_TABLE_8530, 32, 180.00, arrayOf(Item(Items.OAK_PLANK_8778, 3))),

    /**
     * Teak Kitchen Table Flatpack
     *
     * @constructor Teak Kitchen Table Flatpack
     */
    TEAK_KITCHEN_TABLE_FLATPACK(Items.TEAK_KITCHEN_TABLE_8248, Items.TEAK_KITCHEN_TABLE_8532, 52, 270.00, arrayOf(Item(Items.TEAK_PLANK_8780, 3))),

    /**
     * Wood Dining Table Flatpack
     *
     * @constructor Wood Dining Table Flatpack
     */
    WOOD_DINING_TABLE_FLATPACK(Items.WOOD_DINING_TABLE_8115, Items.WOOD_DINING_TABLE_8548, 10, 115.00, arrayOf(Item(Items.PLANK_960, 4))),

    /**
     * Oak Dining Table Flatpack
     *
     * @constructor Oak Dining Table Flatpack
     */
    OAK_DINING_TABLE_FLATPACK(Items.OAK_DINING_TABLE_8116, Items.OAK_DINING_TABLE_8550, 22, 240.00, arrayOf(Item(Items.OAK_PLANK_8778, 4))),

    /**
     * Carved Oak Table Flatpack
     *
     * @constructor Carved Oak Table Flatpack
     */
    CARVED_OAK_TABLE_FLATPACK(Items.CARVED_OAK_TABLE_8117, Items.CARVED_OAK_TABLE_8552, 31, 360.00, arrayOf(Item(Items.OAK_PLANK_8778, 6))),

    /**
     * Teak Table Flatpack
     *
     * @constructor Teak Table Flatpack
     */
    TEAK_TABLE_FLATPACK(Items.TEAK_TABLE_8118, Items.TEAK_TABLE_8554, 38, 360.00, arrayOf(Item(Items.TEAK_PLANK_8780, 4))),

    /**
     * Carved Teak Table Flatpack
     *
     * @constructor Carved Teak Table Flatpack
     */
    CARVED_TEAK_TABLE_FLATPACK(Items.CARVED_TEAK_TABLE_8119, Items.CARVED_TEAK_TABLE_8556, 45, 600.00, arrayOf(Item(Items.TEAK_PLANK_8780, 6), Item(Items.BOLT_OF_CLOTH_8790, 4))),

    /**
     * Mahogany Table Flatpack
     *
     * @constructor Mahogany Table Flatpack
     */
    MAHOGANY_TABLE_FLATPACK(Items.MAHOGANY_TABLE_8120, Items.MAHOGANY_TABLE_8558, 52, 840.00, arrayOf(Item(Items.MAHOGANY_PLANK_8782, 6))),

    /**
     * Opulent Table Flatpack
     *
     * @constructor Opulent Table Flatpack
     */
    OPULENT_TABLE_FLATPACK(Items.OPULENT_TABLE_8121, Items.OPULENT_TABLE_8560, 72, 3100.00, arrayOf(Item(Items.MAHOGANY_PLANK_8782, 6), Item(Items.BOLT_OF_CLOTH_8790, 4), Item(Items.GOLD_LEAF_8784, 4), Item(Items.MARBLE_BLOCK_8786, 2))),

    /**
     * Wooden Bench Flatpack
     *
     * @constructor Wooden Bench Flatpack
     */
    WOODEN_BENCH_FLATPACK(Items.WOODEN_BENCH_8108, Items.WOODEN_BENCH_8562, 10, 115.00, arrayOf(Item(Items.PLANK_960, 4))),

    /**
     * Oak Bench Flatpack
     *
     * @constructor Oak Bench Flatpack
     */
    OAK_BENCH_FLATPACK(Items.OAK_BENCH_8109, Items.OAK_BENCH_8564, 22, 240.00, arrayOf(Item(Items.OAK_PLANK_8778, 4))),

    /**
     * Carved Oak Bench Flatpack
     *
     * @constructor Carved Oak Bench Flatpack
     */
    CARVED_OAK_BENCH_FLATPACK(Items.CARVED_OAK_BENCH_8110, Items.CARVED_OAK_BENCH_8566, 31, 240.00, arrayOf(Item(Items.OAK_PLANK_8778, 4))),

    /**
     * Teak Dining Bench Flatpack
     *
     * @constructor Teak Dining Bench Flatpack
     */
    TEAK_DINING_BENCH_FLATPACK(Items.TEAK_DINING_BENCH_8111, Items.TEAK_DINING_BENCH_8568, 38, 360.00, arrayOf(Item(Items.TEAK_PLANK_8780, 4))),

    /**
     * Carved Teak Bench Flatpack
     *
     * @constructor Carved Teak Bench Flatpack
     */
    CARVED_TEAK_BENCH_FLATPACK(Items.CARVED_TEAK_BENCH_8112, Items.CARVED_TEAK_BENCH_8570, 44, 360.00, arrayOf(Item(Items.TEAK_PLANK_8780, 4))),

    /**
     * Mahogany Bench Flatpack
     *
     * @constructor Mahogany Bench Flatpack
     */
    MAHOGANY_BENCH_FLATPACK(Items.MAHOGANY_BENCH_8113, Items.MAHOGANY_BENCH_8572, 52, 560.00, arrayOf(Item(Items.MAHOGANY_PLANK_8782, 4))),

    /**
     * Gilded Bench Flatpack
     *
     * @constructor Gilded Bench Flatpack
     */
    GILDED_BENCH_FLATPACK(Items.GILDED_BENCH_8114, Items.GILDED_BENCH_8574, 61, 1760.00, arrayOf(Item(Items.MAHOGANY_PLANK_8782, 4), Item(Items.GOLD_LEAF_8784, 4))),

    /**
     * Wooden Bed Flatpack
     *
     * @constructor Wooden Bed Flatpack
     */
    WOODEN_BED_FLATPACK(Items.WOODEN_BED_8031, Items.WOODEN_BED_8576, 20, 126.00, arrayOf(Item(Items.PLANK_960, 3), Item(Items.BOLT_OF_CLOTH_8790, 2))),

    /**
     * Oak Bed Flatpack
     *
     * @constructor Oak Bed Flatpack
     */
    OAK_BED_FLATPACK(Items.OAK_BED_8032, Items.OAK_BED_8578, 30, 210.00, arrayOf(Item(Items.OAK_PLANK_8778, 3), Item(Items.BOLT_OF_CLOTH_8790, 2))),

    /**
     * Large Oak Bed Flatpack
     *
     * @constructor Large Oak Bed Flatpack
     */
    LARGE_OAK_BED_FLATPACK(Items.LARGE_OAK_BED_8033, Items.LARGE_OAK_BED_8580, 34, 330.00, arrayOf(Item(Items.OAK_PLANK_8778, 5), Item(Items.BOLT_OF_CLOTH_8790, 2))),

    /**
     * Teak Bed Flatpack
     *
     * @constructor Teak Bed Flatpack
     */
    TEAK_BED_FLATPACK(Items.TEAK_BED_8034, Items.TEAK_BED_8582, 40, 300.00, arrayOf(Item(Items.TEAK_PLANK_8780, 3), Item(Items.BOLT_OF_CLOTH_8790, 2))),

    /**
     * Large Teak Bed Flatpack
     *
     * @constructor Large Teak Bed Flatpack
     */
    LARGE_TEAK_BED_FLATPACK(Items.LARGE_TEAK_BED_8035, Items.LARGE_TEAK_BED_8584, 45, 480.00, arrayOf(Item(Items.TEAK_PLANK_8780, 5), Item(Items.BOLT_OF_CLOTH_8790, 2))),

    /**
     * Four Poster Flatpack
     *
     * @constructor Four Poster Flatpack
     */
    FOUR_POSTER_FLATPACK(Items.FOUR_POSTER_8036, Items.FOUR_POSTER_8586, 53, 450.00, arrayOf(Item(Items.MAHOGANY_PLANK_8782, 3), Item(Items.BOLT_OF_CLOTH_8790, 2))),

    /**
     * Gilded Four Poster Flatpack
     *
     * @constructor Gilded Four Poster Flatpack
     */
    GILDED_FOUR_POSTER_FLATPACK(Items.GILDED_4_POSTER_8037, Items.GILDED_4_POSTER_8588, 60, 1330.00, arrayOf(Item(Items.MAHOGANY_PLANK_8782, 5), Item(Items.BOLT_OF_CLOTH_8790, 2), Item(Items.GOLD_LEAF_8784, 2))),

    /**
     * Shaving Stand Flatpack
     *
     * @constructor Shaving Stand Flatpack
     */
    SHAVING_STAND_FLATPACK(Items.SHAVING_STAND_8045, Items.SHAVING_STAND_8596, 21, 30.00, arrayOf(Item(Items.PLANK_960, 1), Item(Items.MOLTEN_GLASS_1775, 1))),

    /**
     * Oak Shaving Stand Flatpack
     *
     * @constructor Oak Shaving Stand Flatpack
     */
    OAK_SHAVING_STAND_FLATPACK(Items.OAK_SHAVING_STAND_8046, Items.OAK_SHAVING_STAND_8598, 29, 61.00, arrayOf(Item(Items.OAK_PLANK_8778, 1), Item(Items.MOLTEN_GLASS_1775, 1))),

    /**
     * Oak Dresser Flatpack
     *
     * @constructor Oak Dresser Flatpack
     */
    OAK_DRESSER_FLATPACK(Items.OAK_DRESSER_8047, Items.OAK_DRESSER_8600, 37, 121.00, arrayOf(Item(Items.OAK_PLANK_8778, 2), Item(Items.MOLTEN_GLASS_1775, 1))),

    /**
     * Teak Dresser Flatpack
     *
     * @constructor Teak Dresser Flatpack
     */
    TEAK_DRESSER_FLATPACK(Items.TEAK_DRESSER_8048, Items.TEAK_DRESSER_8602, 46, 181.00, arrayOf(Item(Items.TEAK_PLANK_8780, 2), Item(Items.MOLTEN_GLASS_1775, 1))),

    /**
     * Fancy Teak Dresser Flatpack
     *
     * @constructor Fancy Teak Dresser Flatpack
     */
    FANCY_TEAK_DRESSER_FLATPACK(Items.FANCY_TEAK_DRESSER_8049, Items.FANCY_TEAK_DRESSER_8604, 56, 182.00, arrayOf(Item(Items.TEAK_PLANK_8780, 2), Item(Items.MOLTEN_GLASS_1775, 1))),

    /**
     * Mahogany Dresser Flatpack
     *
     * @constructor Mahogany Dresser Flatpack
     */
    MAHOGANY_DRESSER_FLATPACK(Items.MAHOGANY_DRESSER_8050, Items.MAHOGANY_DRESSER_8606, 64, 281.00, arrayOf(Item(Items.MAHOGANY_PLANK_8782, 2), Item(Items.MOLTEN_GLASS_1775, 1))),

    /**
     * Gilded Dresser Flatpack
     *
     * @constructor Gilded Dresser Flatpack
     */
    GILDED_DRESSER_FLATPACK(Items.GILDED_DRESSER_8051, Items.GILDED_DRESSER_8608, 74, 582.00, arrayOf(Item(Items.MAHOGANY_PLANK_8782, 2), Item(Items.MOLTEN_GLASS_1775, 2), Item(Items.GOLD_LEAF_8784, 1))),

    /**
     * Shoe Box Flatpack
     *
     * @constructor Shoe Box Flatpack
     */
    SHOE_BOX_FLATPACK(Items.SHOE_BOX_8038, Items.SHOE_BOX_8610, 20, 58.00, arrayOf(Item(Items.PLANK_960, 2))),

    /**
     * Oak Drawers Flatpack
     *
     * @constructor Oak Drawers Flatpack
     */
    OAK_DRAWERS_FLATPACK(Items.OAK_DRAWERS_8039, Items.OAK_DRAWERS_8612, 27, 120.00, arrayOf(Item(Items.OAK_PLANK_8778, 2))),

    /**
     * Oak Wardrobe Flatpack
     *
     * @constructor Oak Wardrobe Flatpack
     */
    OAK_WARDROBE_FLATPACK(Items.OAK_WARDROBE_8040, Items.OAK_WARDROBE_8614, 39, 180.00, arrayOf(Item(Items.OAK_PLANK_8778, 3))),

    /**
     * Teak Drawers Flatpack
     *
     * @constructor Teak Drawers Flatpack
     */
    TEAK_DRAWERS_FLATPACK(Items.TEAK_DRAWERS_8041, Items.TEAK_DRAWERS_8616, 51, 180.00, arrayOf(Item(Items.TEAK_PLANK_8780, 2))),

    /**
     * Teak Wardrobe Flatpack
     *
     * @constructor Teak Wardrobe Flatpack
     */
    TEAK_WARDROBE_FLATPACK(Items.TEAK_WARDROBE_8042, Items.TEAK_WARDROBE_8618, 63, 270.00, arrayOf(Item(Items.TEAK_PLANK_8780, 3))),

    /**
     * Mahogany Wardrobe Flatpack
     *
     * @constructor Mahogany Wardrobe Flatpack
     */
    MAHOGANY_WARDROBE_FLATPACK(Items.MAHOGANY_DROBE_8043, Items.MAHOGANY_DROBE_8620, 75, 420.00, arrayOf(Item(Items.MAHOGANY_PLANK_8782, 3))),

    /**
     * Gilded Wardrobe Flatpack
     *
     * @constructor Gilded Wardrobe Flatpack
     */
    GILDED_WARDROBE_FLATPACK(Items.GILDED_WARDROBE_8044, Items.GILDED_WARDROBE_8622, 87, 720.00, arrayOf(Item(Items.MAHOGANY_PLANK_8782, 3), Item(Items.GOLD_LEAF_8784, 1))),

    /**
     * Oak Clock Flatpack
     *
     * @constructor Oak Clock Flatpack
     */
    OAK_CLOCK_FLATPACK(Items.OAK_CLOCK_8052, Items.OAK_CLOCK_8590, 25, 142.00, arrayOf(Item(Items.OAK_PLANK_8778, 2), Item(Items.CLOCKWORK_8792, 1))),

    /**
     * Teak Clock Flatpack
     *
     * @constructor Teak Clock Flatpack
     */
    TEAK_CLOCK_FLATPACK(Items.TEAK_CLOCK_8053, Items.TEAK_CLOCK_8592, 55, 202.00, arrayOf(Item(Items.TEAK_PLANK_8780, 2), Item(Items.CLOCKWORK_8792, 1))),

    /**
     * Gilded Clock Flatpack
     *
     * @constructor Gilded Clock Flatpack
     */
    GILDED_CLOCK_FLATPACK(Items.GILDED_CLOCK_8054, Items.GILDED_CLOCK_8594, 85, 602.00, arrayOf(Item(Items.MAHOGANY_PLANK_8782, 2), Item(Items.CLOCKWORK_8792, 1), Item(Items.GOLD_LEAF_8784))),

    /**
     * Oak Magic Wardrobe Flatpack
     *
     * @constructor Oak Magic Wardrobe Flatpack
     */
    OAK_MAGIC_WARDROBE_FLATPACK(Items.OAK_MAGIC_WARDROBE_9829, Items.OAK_MAGIC_WARDROBE_9852, 42, 240.00, arrayOf(Item(Items.OAK_PLANK_8778, 4))),

    /**
     * Carved Oak Magic Wardrobe Flatpack
     *
     * @constructor Carved Oak Magic Wardrobe Flatpack
     */
    CARVED_OAK_MAGIC_WARDROBE_FLATPACK(Items.CARVED_OAK_MAGIC_WARDROBE_9830, Items.CARVED_OAK_MAGIC_WARDROBE_9853, 51, 360.00, arrayOf(Item(Items.OAK_PLANK_8778, 6))),

    /**
     * Teak Magic Wardrobe Flatpack
     *
     * @constructor Teak Magic Wardrobe Flatpack
     */
    TEAK_MAGIC_WARDROBE_FLATPACK(Items.TEAK_MAGIC_WARDROBE_9831, Items.TEAK_MAGIC_WARDROBE_9854, 60, 360.00, arrayOf(Item(Items.TEAK_PLANK_8780, 4))),

    /**
     * Carved Teak Magic Wardrobe Flatpack
     *
     * @constructor Carved Teak Magic Wardrobe Flatpack
     */
    CARVED_TEAK_MAGIC_WARDROBE_FLATPACK(Items.CARVED_TEAK_MAGIC_WARDROBE_9832, Items.CARVED_TEAK_MAGIC_WARDROBE_9855, 69, 540.00, arrayOf(Item(Items.TEAK_PLANK_8780, 6))),

    /**
     * Mahogany Magic Wardrobe Flatpack
     *
     * @constructor Mahogany Magic Wardrobe Flatpack
     */
    MAHOGANY_MAGIC_WARDROBE_FLATPACK(Items.MAHOGANY_MAGIC_WARDROBE_9833, Items.MAHOGANY_MAGIC_WARDROBE_9856, 78, 560.00, arrayOf(Item(Items.MAHOGANY_PLANK_8782, 4))),

    /**
     * Gilded Magic Wardrobe Flatpack
     *
     * @constructor Gilded Magic Wardrobe Flatpack
     */
    GILDED_MAGIC_WARDROBE_FLATPACK(Items.GILDED_MAGIC_WARDROBE_9834, Items.GILDED_MAGIC_WARDROBE_9857, 87, 860.00, arrayOf(Item(Items.MAHOGANY_PLANK_8782, 4), Item(Items.GOLD_LEAF_8784, 1))),

    /**
     * Marble Magic Wardrobe Flatpack
     *
     * @constructor Marble Magic Wardrobe Flatpack
     */
    MARBLE_MAGIC_WARDROBE_FLATPACK(Items.MARBLE_MAGIC_WARDROBE_9835, Items.MARBLE_MAGIC_WARDROBE_9858, 96, 500.00, arrayOf(Item(Items.MARBLE_BLOCK_8786, 1))),

    /**
     * Oak Armour Case Flatpack
     *
     * @constructor Oak Armour Case Flatpack
     */
    OAK_ARMOUR_CASE_FLATPACK(Items.OAK_ARMOUR_CASE_9826, Items.OAK_ARMOUR_CASE_9859, 46, 180.00, arrayOf(Item(Items.OAK_PLANK_8778, 3))),

    /**
     * Teak Armour Case Flatpack
     *
     * @constructor Teak Armour Case Flatpack
     */
    TEAK_ARMOUR_CASE_FLATPACK(Items.TEAK_ARMOUR_CASE_9827, Items.TEAK_ARMOUR_CASE_9860, 64, 270.00, arrayOf(Item(Items.TEAK_PLANK_8780, 3))),

    /**
     * Mahogany Armr Case Flatpack
     *
     * @constructor Mahogany Armr Case Flatpack
     */
    MAHOGANY_ARMR_CASE_FLATPACK(Items.MGANY_ARMR_CASE_9828, Items.MGANY_ARMR_CASE_9861, 82, 420.00, arrayOf(Item(Items.MAHOGANY_PLANK_8782, 3))),

    /**
     * Oak Treasure Chest Flatpack
     *
     * @constructor Oak Treasure Chest Flatpack
     */
    OAK_TREASURE_CHEST_FLATPACK(Items.OAK_TREASURE_CHEST_9839, Items.OAK_TREASURE_CHEST_9862, 48, 120.00, arrayOf(Item(Items.OAK_PLANK_8778, 2))),

    /**
     * Teak Treasure Chest Flatpack
     *
     * @constructor Teak Treasure Chest Flatpack
     */
    TEAK_TREASURE_CHEST_FLATPACK(Items.TEAK_TREAS_CHEST_9840, Items.TEAK_TREAS_CHEST_9863, 66, 180.00, arrayOf(Item(Items.TEAK_PLANK_8780, 2))),

    /**
     * Mahogany Treas Chest Flatpack
     *
     * @constructor Mahogany Treas Chest Flatpack
     */
    MAHOGANY_TREAS_CHEST_FLATPACK(Items.MGANY_TREAS_CHEST_9841, Items.MGANY_TREAS_CHEST_9864, 84, 280.00, arrayOf(Item(Items.MAHOGANY_PLANK_8782, 2))),

    /**
     * Oak Costume Box Flatpack
     *
     * @constructor Oak Costume Box Flatpack
     */
    OAK_COSTUME_BOX_FLATPACK(Items.OAK_COSTUME_BOX_9823, Items.OAK_COSTUME_BOX_9865, 44, 120.00, arrayOf(Item(Items.OAK_PLANK_8778, 2))),

    /**
     * Teak Costume Box Flatpack
     *
     * @constructor Teak Costume Box Flatpack
     */
    TEAK_COSTUME_BOX_FLATPACK(Items.TEAK_COSTUME_BOX_9824, Items.TEAK_COSTUME_BOX_9866, 62, 180.00, arrayOf(Item(Items.TEAK_PLANK_8780, 2))),

    /**
     * Mahogany Cos Box Flatpack
     *
     * @constructor Mahogany Cos Box Flatpack
     */
    MAHOGANY_COS_BOX_FLATPACK(Items.MAHOGANY_COS_BOX_9825, Items.MAHOGANY_COS_BOX_9867, 80, 280.00, arrayOf(Item(Items.MAHOGANY_PLANK_8782, 2))),

    /**
     * Oak Toy Box Flatpack
     *
     * @constructor Oak Toy Box Flatpack
     */
    OAK_TOY_BOX_FLATPACK(Items.OAK_TOY_BOX_9836, Items.OAK_TOY_BOX_9849, 50, 120.00, arrayOf(Item(Items.OAK_PLANK_8778, 2))),

    /**
     * Teak Toy Box Flatpack
     *
     * @constructor Teak Toy Box Flatpack
     */
    TEAK_TOY_BOX_FLATPACK(Items.TEAK_TOY_BOX_9837, Items.TEAK_TOY_BOX_9850, 68, 180.00, arrayOf(Item(Items.TEAK_PLANK_8780, 2))),

    /**
     * Mahogany Toy Box Flatpack
     *
     * @constructor Mahogany Toy Box Flatpack
     */
    MAHOGANY_TOY_BOX_FLATPACK(Items.MAHOGANY_TOY_BOX_9838, Items.MAHOGANY_TOY_BOX_9851, 86, 280.00, arrayOf(Item(Items.MAHOGANY_PLANK_8782, 2))),

    /**
     * Beer Barrel Flatpack
     *
     * @constructor Beer Barrel Flatpack
     */
    BEER_BARREL_FLATPACK(Items.BEER_BARREL_8239, Items.BEER_BARREL_8516, 7, 87.00, arrayOf(Item(Items.PLANK_960, 3))),

    /**
     * Cider Barrel Flatpack
     *
     * @constructor Cider Barrel Flatpack
     */
    CIDER_BARREL_FLATPACK(Items.CIDER_BARREL_8240, Items.CIDER_BARREL_8518, 12, 91.00, arrayOf(Item(Items.PLANK_960, 3), Item(Items.CIDER_5763, 8))),

    /**
     * Asgarnian Barrel Flatpack
     *
     * @constructor Asgarnian Barrel Flatpack
     */
    ASGARNIAN_BARREL_FLATPACK(Items.ASGARNIAN_ALE_8241, Items.ASGARNIAN_ALE_8520, 18, 184.00, arrayOf(Item(Items.OAK_PLANK_8778, 3), Item(Items.ASGARNIAN_ALE_1905, 8))),

    /**
     * Greenman Ale Barrel Flatpack
     *
     * @constructor Greenman Ale Barrel Flatpack
     */
    GREENMAN_ALE_BARREL_FLATPACK(Items.GREENMANS_ALE_8242, Items.GREENMANS_ALE_8522, 26, 184.00, arrayOf(Item(Items.OAK_PLANK_8778, 3), Item(Items.GREENMANS_ALE_1909, 8))),

    /**
     * Dragon Bitter Barrel Flatpack
     *
     * @constructor Dragon Bitter Barrel Flatpack
     */
    DRAGON_BITTER_BARREL_FLATPACK(Items.DRAGON_BITTER_8243, Items.DRAGON_BITTER_8524, 36, 224.00, arrayOf(Item(Items.OAK_PLANK_8778, 3), Item(Items.STEEL_BAR_2353, 2), Item(Items.DRAGON_BITTER_1911, 8))),

    /**
     * Chef Delight Barrel Flatpack
     *
     * @constructor Chef Delight Barrel Flatpack
     */
    CHEF_DELIGHT_BARREL_FLATPACK(Items.CHEFS_DELIGHT_8244, Items.CHEFS_DELIGHT_8526, 48, 224.00, arrayOf(Item(Items.OAK_PLANK_8778, 3), Item(Items.STEEL_BAR_2354, 2), Item(Items.CHEFS_DELIGHT_5755, 8)));

    companion object {
        val productMap = HashMap<Int, FurnitureFlatpack>()

        init {
            for (product in FurnitureFlatpack.values()) {
                productMap[product.interfaceItem] = product
            }
        }
    }
}
