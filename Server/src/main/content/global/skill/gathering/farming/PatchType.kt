package content.global.skill.gathering.farming

/**
 * Patch type
 *
 * @property stageGrowthTime
 * @constructor Patch type
 */
enum class PatchType(val stageGrowthTime: Int) {
    /**
     * Allotment
     *
     * @constructor Allotment
     */
    ALLOTMENT(10),

    /**
     * Hops Patch
     *
     * @constructor Hops Patch
     */
    HOPS_PATCH(10),

    /**
     * Tree Patch
     *
     * @constructor Tree Patch
     */
    TREE_PATCH(40),

    /**
     * Fruit Tree Patch
     *
     * @constructor Fruit Tree Patch
     */
    FRUIT_TREE_PATCH(160),

    /**
     * Bush Patch
     *
     * @constructor Bush Patch
     */
    BUSH_PATCH(20),

    /**
     * Flower Patch
     *
     * @constructor Flower Patch
     */
    FLOWER_PATCH(5),

    /**
     * Herb Patch
     *
     * @constructor Herb Patch
     */
    HERB_PATCH(20),

    /**
     * Spirit Tree Patch
     *
     * @constructor Spirit Tree Patch
     */
    SPIRIT_TREE_PATCH(295),

    /**
     * Mushroom Patch
     *
     * @constructor Mushroom Patch
     */
    MUSHROOM_PATCH(30),

    /**
     * Belladonna Patch
     *
     * @constructor Belladonna Patch
     */
    BELLADONNA_PATCH(80),

    /**
     * Cactus Patch
     *
     * @constructor Cactus Patch
     */
    CACTUS_PATCH(60),

    /**
     * Evil Turnip Patch
     *
     * @constructor Evil Turnip Patch
     */
    EVIL_TURNIP_PATCH(5),

    /**
     * Special Patch
     *
     * @constructor Special Patch
     */
    SPECIAL_PATCH(20);

    /**
     * Display name
     *
     * @return
     */
    fun displayName(): String = name.lowercase().replace("_", " ")
}
