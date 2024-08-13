package content.global.skill.gathering.farming

/**
 * Patch type.
 *
 * @param stageGrowthTime The time it takes for a patch of this type to go through each growth stage.
 * @constructor Creates a new PatchType with the specified stage growth time.
 */
enum class PatchType(val stageGrowthTime: Int) {
    /**
     * Allotment patch type.
     *
     * @constructor Creates a new Allotment patch type with a stage growth time of 10.
     */
    ALLOTMENT(10),

    /**
     * Hops patch type.
     *
     * @constructor Creates a new Hops patch type with a stage growth time of 10.
     */
    HOPS_PATCH(10),

    /**
     * Tree patch type.
     *
     * @constructor Creates a new Tree patch type with a stage growth time of 40.
     */
    TREE_PATCH(40),

    /**
     * Fruit tree patch type.
     *
     * @constructor Creates a new Fruit Tree patch type with a stage growth time of 160.
     */
    FRUIT_TREE_PATCH(160),

    /**
     * Bush patch type.
     *
     * @constructor Creates a new Bush patch type with a stage growth time of 20.
     */
    BUSH_PATCH(20),

    /**
     * Flower patch type.
     *
     * @constructor Creates a new Flower patch type with a stage growth time of 5.
     */
    FLOWER_PATCH(5),

    /**
     * Herb patch type.
     *
     * @constructor Creates a new Herb patch type with a stage growth time of 20.
     */
    HERB_PATCH(20),

    /**
     * Spirit tree patch type.
     *
     * @constructor Creates a new Spirit Tree patch type with a stage growth time of 295.
     */
    SPIRIT_TREE_PATCH(295),

    /**
     * Mushroom patch type.
     *
     * @constructor Creates a new Mushroom patch type with a stage growth time of 30.
     */
    MUSHROOM_PATCH(30),

    /**
     * Belladonna patch type.
     *
     * @constructor Creates a new Belladonna patch type with a stage growth time of 80.
     */
    BELLADONNA_PATCH(80),

    /**
     * Cactus patch type.
     *
     * @constructor Creates a new Cactus patch type with a stage growth time of 60.
     */
    CACTUS_PATCH(60),

    /**
     * Evil Turnip patch type.
     *
     * @constructor Creates a new Evil Turnip patch type with a stage growth time of 5.
     */
    EVIL_TURNIP_PATCH(5),

    /**
     * Special patch type.
     *
     * @constructor Creates a new Special patch type with a stage growth time of 20.
     */
    SPECIAL_PATCH(20);

    /**
     * Returns the display name of the patch type.
     *
     * @return The display name of the patch type.
     */
    fun displayName(): String = name.lowercase().replace("_", " ")
}
