package content.global.skill.production.crafting.data

import cfg.consts.Graphics
import cfg.consts.Items

/**
 * Origami data
 *
 * @param requiredDye The amount of dye required to create the origami.
 * @param ballonId The identifier for the balloon associated with the origami.
 * @param gfxId The graphical identifier for rendering the origami.
 * @constructor Origami data
 */
enum class Origami(val requiredDye: Int, val ballonId: Int, val gfxId: Int) {
    /**
     * Yellow.
     */
    YELLOW(
        requiredDye = Items.YELLOW_DYE_1765,
        ballonId = Items.YELLOW_BALLOON_9935,
        gfxId = Graphics.YELLOW_HOT_AIR_BALLOON_FLY_UPWARDS_883
    ),

    /**
     * Blue.
     */
    BLUE(
        requiredDye = Items.BLUE_DYE_1767,
        ballonId = Items.BLUE_BALLOON_9936,
        gfxId = Graphics.BLUE_HOT_AIR_BALLOON_FLY_UPWARDS_886
    ),

    /**
     * Red.
     */
    RED(
        requiredDye = Items.RED_DYE_1763,
        ballonId = Items.RED_BALLOON_9937,
        gfxId = Graphics.RED_HOT_AIR_BALLOON_FLY_UPWARDS_889
    ),

    /**
     * Orange.
     */
    ORANGE(
        requiredDye = Items.ORANGE_DYE_1769,
        ballonId = Items.ORANGE_BALLOON_9938,
        gfxId = Graphics.ORANGE_HOT_AIR_BALLOON_FLY_UPWARDS_892
    ),

    /**
     * Green.
     */
    GREEN(
        requiredDye = Items.GREEN_DYE_1771,
        ballonId = Items.GREEN_BALLOON_9939,
        gfxId = Graphics.GREEN_HOT_AIR_BALLOON_FLY_UPWARDS_895
    ),

    /**
     * Purple.
     */
    PURPLE(
        requiredDye = Items.PURPLE_DYE_1773,
        ballonId = Items.PURPLE_BALLOON_9940,
        gfxId = Graphics.PURPLE_HOT_AIR_BALLOON_FLY_UPWARDS_898
    ),

    /**
     * Pink.
     */
    PINK(
        requiredDye = Items.PINK_DYE_6955,
        ballonId = Items.PINK_BALLOON_9941,
        gfxId = Graphics.PINK_HOT_AIR_BALLOON_FLY_UPWARDS_901
    ),

    /**
     * Black.
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
