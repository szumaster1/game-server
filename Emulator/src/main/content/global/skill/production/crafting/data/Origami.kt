package content.global.skill.production.crafting.data

import cfg.consts.Graphics
import cfg.consts.Items

/**
 * Reprsents the origami (balloons) items.
 */
enum class Origami(val requiredDye: Int, val ballonId: Int, val gfxId: Int) {
    /**
     * The yellow.
     */
    YELLOW(
        requiredDye = Items.YELLOW_DYE_1765,
        ballonId = Items.YELLOW_BALLOON_9935,
        gfxId = Graphics.YELLOW_HOT_AIR_BALLOON_FLY_UPWARDS_883
    ),

    /**
     * The blue.
     */
    BLUE(
        requiredDye = Items.BLUE_DYE_1767,
        ballonId = Items.BLUE_BALLOON_9936,
        gfxId = Graphics.BLUE_HOT_AIR_BALLOON_FLY_UPWARDS_886
    ),

    /**
     * The red.
     */
    RED(
        requiredDye = Items.RED_DYE_1763,
        ballonId = Items.RED_BALLOON_9937,
        gfxId = Graphics.RED_HOT_AIR_BALLOON_FLY_UPWARDS_889
    ),

    /**
     * The orange.
     */
    ORANGE(
        requiredDye = Items.ORANGE_DYE_1769,
        ballonId = Items.ORANGE_BALLOON_9938,
        gfxId = Graphics.ORANGE_HOT_AIR_BALLOON_FLY_UPWARDS_892
    ),

    /**
     * The green.
     */
    GREEN(
        requiredDye = Items.GREEN_DYE_1771,
        ballonId = Items.GREEN_BALLOON_9939,
        gfxId = Graphics.GREEN_HOT_AIR_BALLOON_FLY_UPWARDS_895
    ),

    /**
     * The purple.
     */
    PURPLE(
        requiredDye = Items.PURPLE_DYE_1773,
        ballonId = Items.PURPLE_BALLOON_9940,
        gfxId = Graphics.PURPLE_HOT_AIR_BALLOON_FLY_UPWARDS_898
    ),

    /**
     * The pink.
     */
    PINK(
        requiredDye = Items.PINK_DYE_6955,
        ballonId = Items.PINK_BALLOON_9941,
        gfxId = Graphics.PINK_HOT_AIR_BALLOON_FLY_UPWARDS_901
    ),

    /**
     * The black.
     */
    BLACK(
        requiredDye = Items.BLACK_MUSHROOM_INK_4622,
        ballonId = Items.BLACK_BALLOON_9942,
        gfxId = Graphics.BLACK_HOT_AIR_BALLOON_FLY_UPWARDS_904
    );

    companion object {
        @JvmStatic
        fun forId(itemId: Int): Origami? {
            for (product in Origami.values()) {
                if (product.requiredDye == itemId) {
                    return product
                }
            }
            return null
        }
    }
}
