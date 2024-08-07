package content.global.skill.gathering.farming

import core.api.consts.Items
import core.game.node.item.Item

/**
 * Plantable
 *
 * @property itemID
 * @property displayName
 * @property value
 * @property stages
 * @property plantingXP
 * @property harvestXP
 * @property checkHealthXP
 * @property requiredLevel
 * @property applicablePatch
 * @property harvestItem
 * @property protectionItem
 * @property protectionFlower
 * @constructor Plantable
 */
enum class Plantable(val itemID: Int, val displayName: String, val value: Int, val stages: Int, val plantingXP: Double, val harvestXP: Double, val checkHealthXP: Double, val requiredLevel: Int, val applicablePatch: PatchType, val harvestItem: Int, val protectionItem: Item? = null, val protectionFlower: Plantable? = null) {

    /**
     * Marigold Seed.
     */
    MARIGOLD_SEED(Items.MARIGOLD_SEED_5096,"marigold seed",8,4,8.5,47.0,0.0,2,PatchType.FLOWER_PATCH,Items.MARIGOLDS_6010),

    /**
     * Rosemary Seed.
     */
    ROSEMARY_SEED(Items.ROSEMARY_SEED_5097,"rosemary seed",13,4,12.0,66.5,0.0,11,PatchType.FLOWER_PATCH, Items.ROSEMARY_6014),

    /**
     * Nasturtium Seed.
     */
    NASTURTIUM_SEED(Items.NASTURTIUM_SEED_5098,"nasturtium seed",18,4,19.5,111.0,0.0,24,PatchType.FLOWER_PATCH,Items.NASTURTIUMS_6012),

    /**
     * Woad Seed.
     */
    WOAD_SEED(Items.WOAD_SEED_5099,"woad seed",23,4,20.5,115.5,0.0,25,PatchType.FLOWER_PATCH,Items.WOAD_LEAF_1793),

    /**
     * Limpwurt Seed.
     */
    LIMPWURT_SEED(Items.LIMPWURT_SEED_5100,"limpwurt seed",28,4,21.5,120.0,0.0,26,PatchType.FLOWER_PATCH,Items.LIMPWURT_ROOT_225),

    /**
     * White Lily Seed.
     */
    WHITE_LILY_SEED(Items.WHITE_LILY_SEED_14589,"white lily seed",37,4,42.0,250.0,0.0,52,PatchType.FLOWER_PATCH,Items.WHITE_LILY_14583),

    /**
     * Scarecrow.
     */
    SCARECROW(Items.SCARECROW_6059,"scarecrow",33,3,0.0,0.0,0.0,23,PatchType.FLOWER_PATCH,Items.SCARECROW_6059),

    /**
     * Potato Seed.
     */
    POTATO_SEED(Items.POTATO_SEED_5318, "potato seed", 6, 4, 8.0, 9.0, 0.0, 1, PatchType.ALLOTMENT, Items.POTATO_1942,Item(Items.COMPOST_6032,2),MARIGOLD_SEED),

    /**
     * Onion Seed.
     */
    ONION_SEED(Items.ONION_SEED_5319, "onion seed", 13, 4, 9.5, 10.5,0.0, 5, PatchType.ALLOTMENT,Items.ONION_1957,Item(Items.POTATOES10_5438),MARIGOLD_SEED),

    /**
     * Cabbage Seed.
     */
    CABBAGE_SEED(Items.CABBAGE_SEED_5324, "cabbage seed", 20, 4, 10.0, 11.5, 0.0,7, PatchType.ALLOTMENT,Items.CABBAGE_1965,Item(Items.ONIONS10_5458),ROSEMARY_SEED),

    /**
     * Tomato Seed.
     */
    TOMATO_SEED(Items.TOMATO_SEED_5322,"tomato seed",27,4,12.5,14.0,0.0,12,PatchType.ALLOTMENT,Items.TOMATO_1982,Item(Items.CABBAGES10_5478,2),MARIGOLD_SEED),

    /**
     * Sweetcorn Seed.
     */
    SWEETCORN_SEED(Items.SWEETCORN_SEED_5320,"sweetcorn seed",34,6,17.0,19.0,0.0,20,PatchType.ALLOTMENT,Items.SWEETCORN_5986,Item(Items.JUTE_FIBRE_5931,10),SCARECROW),

    /**
     * Strawberry Seed.
     */
    STRAWBERRY_SEED(Items.STRAWBERRY_SEED_5323,"strawberry seed",43,6,26.0,29.0,0.0,31,PatchType.ALLOTMENT,Items.STRAWBERRY_5504,Item(Items.APPLES5_5386)),

    /**
     * Watermelon Seed.
     */
    WATERMELON_SEED(Items.WATERMELON_SEED_5321,"watermelon seed",52,8,48.5,54.5,0.0,47,PatchType.ALLOTMENT,Items.WATERMELON_5982,Item(Items.CURRY_LEAF_5970,10),NASTURTIUM_SEED),

    /**
     * Barley Seed.
     */
    BARLEY_SEED(Items.BARLEY_SEED_5305,"barley seed",49,4,8.5,9.5,0.0,3,PatchType.HOPS_PATCH,Items.BARLEY_6006,Item(Items.COMPOST_6032,3)),

    /**
     * Hammerstone Seed.
     */
    HAMMERSTONE_SEED(Items.HAMMERSTONE_SEED_5307,"Hammerstone hop seed",4,4,9.0,10.0,0.0,4,PatchType.HOPS_PATCH,Items.HAMMERSTONE_HOPS_5994,Item(Items.MARIGOLDS_6010)),

    /**
     * Asgarnian Seed.
     */
    ASGARNIAN_SEED(Items.ASGARNIAN_SEED_5308,"Asgarnian hop seed",11,5,10.9,12.0,0.0,8,PatchType.HOPS_PATCH,Items.ASGARNIAN_HOPS_5996,Item(Items.ONIONS10_5458)),

    /**
     * Jute Seed.
     */
    JUTE_SEED(Items.JUTE_SEED_5306,"jute plant seed",56,5,13.0,14.5,0.0,13,PatchType.HOPS_PATCH,Items.JUTE_FIBRE_5931,Item(Items.BARLEY_MALT_6008,6)),

    /**
     * Yanillian Seed.
     */
    YANILLIAN_SEED(Items.YANILLIAN_SEED_5309,"Yanillian hop seed",19,6,14.5,16.0,0.0,16,PatchType.HOPS_PATCH,Items.YANILLIAN_HOPS_5998,Item(Items.TOMATOES5_5968)),

    /**
     * Krandorian Seed.
     */
    KRANDORIAN_SEED(Items.KRANDORIAN_SEED_5310,"Krandorian hop seed",28,7,17.5,19.5,0.0,21,PatchType.HOPS_PATCH,Items.KRANDORIAN_HOPS_6000,Item(Items.CABBAGES10_5478,3)),

    /**
     * Wildblood Seed.
     */
    WILDBLOOD_SEED(Items.WILDBLOOD_SEED_5311,"Wildblood hop seed",38,8,23.0,26.0,0.0,28,PatchType.HOPS_PATCH,Items.WILDBLOOD_HOPS_6002,Item(Items.NASTURTIUMS_6012)),

    /**
     * Oak Sapling.
     */
    OAK_SAPLING(Items.OAK_SAPLING_5370,"oak sapling",8,4,14.0,0.0,467.3,15,PatchType.TREE_PATCH,Items.OAK_ROOTS_6043,Item(Items.TOMATOES5_5968)),

    /**
     * Willow Sapling.
     */
    WILLOW_SAPLING(Items.WILLOW_SAPLING_5371,"willow sapling",15,6,25.0,0.0,1456.5,30,PatchType.TREE_PATCH,Items.WILLOW_ROOTS_6045,Item(Items.APPLES5_5386)),

    /**
     * Maple Sapling.
     */
    MAPLE_SAPLING(Items.MAPLE_SAPLING_5372,"maple sapling",24,8,45.0,0.0,3403.4,45,PatchType.TREE_PATCH,Items.MAPLE_ROOTS_6047,Item(Items.ORANGES5_5396)),

    /**
     * Yew Sapling.
     */
    YEW_SAPLING(Items.YEW_SAPLING_5373,"yew sapling",35,10,81.0,0.0,7069.9,60,PatchType.TREE_PATCH,Items.YEW_ROOTS_6049,Item(Items.CACTUS_SPINE_6016,10)),

    /**
     * Magic Sapling.
     */
    MAGIC_SAPLING(Items.MAGIC_SAPLING_5374,"magic Tree sapling",48,12,145.5,0.0,13768.3,75,PatchType.TREE_PATCH,Items.MAGIC_ROOTS_6051,Item(Items.COCONUT_5974,25)),

    /**
     * Apple Sapling.
     */
    APPLE_SAPLING(Items.APPLE_SAPLING_5496,"apple tree sapling",8,6,22.0,8.5,1199.5,27,PatchType.FRUIT_TREE_PATCH,Items.COOKING_APPLE_1955,Item(Items.SWEETCORN_5986,9)),

    /**
     * Banana Sapling.
     */
    BANANA_SAPLING(Items.BANANA_SAPLING_5497,"banana tree sapling",35,6,28.0,10.5,1750.5,33,PatchType.FRUIT_TREE_PATCH,Items.BANANA_1963,Item(Items.APPLES5_5386,4)),

    /**
     * Orange Sapling.
     */
    ORANGE_SAPLING(Items.ORANGE_SAPLING_5498,"orange tree sapling",72,6,35.5,13.5,2470.2,39,PatchType.FRUIT_TREE_PATCH,Items.ORANGE_2108,Item(Items.STRAWBERRIES5_5406,3)),

    /**
     * Curry Sapling.
     */
    CURRY_SAPLING(Items.CURRY_SAPLING_5499,"curry tree sapling",99,6,40.0,15.0,2906.9,42,PatchType.FRUIT_TREE_PATCH,Items.CURRY_LEAF_5970,Item(Items.BANANAS5_5416,5)),

    /**
     * Pineapple Sapling.
     */
    PINEAPPLE_SAPLING(Items.PINEAPPLE_SAPLING_5500,"pineapple plant",136,6,57.0,21.5,4605.7,51,PatchType.FRUIT_TREE_PATCH,Items.PINEAPPLE_2114,Item(Items.WATERMELON_5982,10)),

    /**
     * Papaya Sapling.
     */
    PAPAYA_SAPLING(Items.PAPAYA_SAPLING_5501,"papaya tree sapling",163,6,72.0,27.0,6146.4,57,PatchType.FRUIT_TREE_PATCH,Items.PAPAYA_FRUIT_5972,Item(Items.PINEAPPLE_2114,10)),

    /**
     * Palm Sapling.
     */
    PALM_SAPLING(Items.PALM_SAPLING_5502,"palm tree sapling",200,6,110.5,41.5,10150.1,68,PatchType.FRUIT_TREE_PATCH,Items.COCONUT_5974,Item(Items.PAPAYA_FRUIT_5972,15)),

    /**
     * Calquat Tree Sapling.
     */
    CALQUAT_TREE_SAPLING(Items.CALQUAT_SAPLING_5503, "calquat tree sapling",4,8,129.5,0.0,12096.0,72,PatchType.FRUIT_TREE_PATCH,Items.CALQUAT_FRUIT_5980,Item(Items.POISON_IVY_BERRIES_6018, 8)),

    /**
     * Redberry Seed.
     */
    REDBERRY_SEED(Items.REDBERRY_SEED_5101,"redberry bush seed",5,5,11.5,4.5,64.0,10,PatchType.BUSH_PATCH,Items.REDBERRIES_1951,Item(Items.CABBAGES10_5478,4)),

    /**
     * Cadavaberry Seed.
     */
    CADAVABERRY_SEED(Items.CADAVABERRY_SEED_5102,"cadavaberry bush seed",15,6,18.0,7.0,102.5,22,PatchType.BUSH_PATCH,Items.CADAVA_BERRIES_753,Item(Items.TOMATOES5_5968,3)),

    /**
     * Dwellberry Seed.
     */
    DWELLBERRY_SEED(Items.DWELLBERRY_SEED_5103,"dwellberry bush seed",26,27,31.5,12.0,177.5,36,PatchType.BUSH_PATCH,Items.DWELLBERRIES_2126,Item(Items.STRAWBERRIES5_5406,3)),

    /**
     * Jangerberry Seed.
     */
    JANGERBERRY_SEED(Items.JANGERBERRY_SEED_5104,"jangerberry bush seed",38,8,50.5,19.0,284.5,48,PatchType.BUSH_PATCH,Items.JANGERBERRIES_247,Item(Items.WATERMELON_5982,6)),

    /**
     * Whiteberry Seed.
     */
    WHITEBERRY_SEED(Items.WHITEBERRY_SEED_5105,"whiteberry bush seed",51,8,78.0,29.0,437.5,59,PatchType.BUSH_PATCH,Items.WHITE_BERRIES_239,null),

    /**
     * Poison Ivy Seed.
     */
    POISON_IVY_SEED(Items.POISON_IVY_SEED_5106,"poison ivy bush seed",197,8,120.0,45.0,675.0,70,PatchType.BUSH_PATCH,Items.POISON_IVY_BERRIES_6018,null),

    /**
     * Guam Seed.
     */
    GUAM_SEED(Items.GUAM_SEED_5291,"guam seed",4,4,11.0,12.5,0.0,9,PatchType.HERB_PATCH,Items.GRIMY_GUAM_199),

    /**
     * Marrentill Seed.
     */
    MARRENTILL_SEED(Items.MARRENTILL_SEED_5292,"marrentill seed",11,4,13.5,15.0,0.0,14,PatchType.HERB_PATCH,Items.GRIMY_MARRENTILL_201),

    /**
     * Tarromin Seed.
     */
    TARROMIN_SEED(Items.TARROMIN_SEED_5293,"tarromin seed",18,4,16.0,18.0,0.0,19,PatchType.HERB_PATCH,Items.GRIMY_TARROMIN_203),

    /**
     * Harralander Seed.
     */
    HARRALANDER_SEED(Items.HARRALANDER_SEED_5294,"harralander seed",25,4,21.5,24.0,0.0,26,PatchType.HERB_PATCH,Items.GRIMY_HARRALANDER_205),

    /**
     * Ranarr Seed.
     */
    RANARR_SEED(Items.RANARR_SEED_5295,"ranarr seed",32,4,27.0,30.5,0.0,32,PatchType.HERB_PATCH,Items.GRIMY_RANARR_207),

    /**
     * Avantoe Seed.
     */
    AVANTOE_SEED(Items.AVANTOE_SEED_5298,"avantoe seed",39,4,54.5,61.5,0.0,50,PatchType.HERB_PATCH,Items.GRIMY_AVANTOE_211),

    /**
     * Toadflax Seed.
     */
    TOADFLAX_SEED(Items.TOADFLAX_SEED_5296,"toadflax seed",46,4,34.0,38.5,0.0,38,PatchType.HERB_PATCH,Items.GRIMY_TOADFLAX_3049),

    /**
     * Irit Seed.
     */
    IRIT_SEED(Items.IRIT_SEED_5297,"irit seed",53,4,43.0,48.5,0.0,44,PatchType.HERB_PATCH,Items.GRIMY_IRIT_209),

    /**
     * Kwuarm Seed.
     */
    KWUARM_SEED(Items.KWUARM_SEED_5299,"kwuarm seed",68,4,69.0,78.0,0.0,56,PatchType.HERB_PATCH,Items.GRIMY_KWUARM_213),

    /**
     * Snapdragon Seed.
     */
    SNAPDRAGON_SEED(Items.SNAPDRAGON_SEED_5300,"snapdragon seed",75,4,87.5,98.5,0.0,62,PatchType.HERB_PATCH,Items.GRIMY_SNAPDRAGON_3051),

    /**
     * Cadantine Seed.
     */
    CADANTINE_SEED(Items.CADANTINE_SEED_5301,"cadantine seed",82,4,106.5,120.0,0.0,67,PatchType.HERB_PATCH,Items.GRIMY_CADANTINE_215),

    /**
     * Lantadyme Seed.
     */
    LANTADYME_SEED(Items.LANTADYME_SEED_5302,"lantadyme seed",89,4,134.5,151.5,0.0,73,PatchType.HERB_PATCH,Items.GRIMY_LANTADYME_2485),

    /**
     * Dwarf Weed Seed.
     */
    DWARF_WEED_SEED(Items.DWARF_WEED_SEED_5303,"dwarf weed seed",96,4,170.5,192.0,0.0,79,PatchType.HERB_PATCH,Items.GRIMY_DWARF_WEED_217),

    /**
     * Torstol Seed.
     */
    TORSTOL_SEED(Items.TORSTOL_SEED_5304,"torstol seed",103,4,199.5,224.5,0.0,85,PatchType.HERB_PATCH,Items.GRIMY_TORSTOL_219),

    /**
     * Gout Tuber.
     */
    GOUT_TUBER(Items.GOUT_TUBER_6311,"gout tuber",192,4,105.0,45.0,0.0,29,PatchType.HERB_PATCH,Items.GOUTWEED_3261),

    /**
     * Spirit Weed Seed.
     */
    SPIRIT_WEED_SEED(Items.SPIRIT_WEED_SEED_12176,"spirit weed seed", 204, 4, 32.0, 36.0, 0.0, 36, PatchType.HERB_PATCH, Items.GRIMY_SPIRIT_WEED_12174),

    /**
     * Belladonna Seed.
     */
    BELLADONNA_SEED(Items.BELLADONNA_SEED_5281, "belladonna seed", 4, 4, 91.0, 128.0, 0.0, 63, PatchType.BELLADONNA_PATCH, Items.CAVE_NIGHTSHADE_2398),

    /**
     * Mushroom Spore.
     */
    MUSHROOM_SPORE(Items.MUSHROOM_SPORE_5282, "mushroom spore", 6, 7, 61.5, 57.7, 0.0, 53, PatchType.MUSHROOM_PATCH, Items.MUSHROOM_6004),

    /**
     * Cactus Seed.
     */
    CACTUS_SEED(Items.CACTUS_SEED_5280, "cactus seed", 8, 7, 66.5, 25.0, 374.0, 55, PatchType.CACTUS_PATCH, Items.CACTUS_SPINE_6016),

    /**
     * Evil Turnip Seed.
     */
    EVIL_TURNIP_SEED(Items.EVIL_TURNIP_SEED_12148, "evil turnip seed", 4, 1, 41.0, 46.0, 0.0, 42, PatchType.EVIL_TURNIP_PATCH, Items.EVIL_TURNIP_12134),

    /**
     * Enriched Seed.
     */
    ENRICHED_SEED(Items.ENRICHED_SNAPDRAGON_SEED_14506, "enriched snapdragon seed", 12, 4, 0.0,0.0,0.0,65,PatchType.SPECIAL_PATCH, Items.ENRICHED_SNAPDRAGON_14487)
    ;

    constructor(itemID: Int, displayName: String, value: Int, stages: Int, plantingXP: Double, harvestXP: Double, checkHealthXP: Double, requiredLevel: Int, applicablePatch: PatchType, harvestItem: Int, protectionFlower: Plantable) : this(itemID,displayName,value,stages,plantingXP,harvestXP,checkHealthXP,requiredLevel,applicablePatch,harvestItem,null,protectionFlower)
    companion object {
        @JvmField
        val plantables = values().map { it.itemID to it }.toMap()

        @JvmStatic
        fun forItemID(id: Int): Plantable?{
            return plantables[id]
        }

        @JvmStatic
        fun forItem(item: Item): Plantable?{
            return forItemID(item.id)
        }
    }
}
