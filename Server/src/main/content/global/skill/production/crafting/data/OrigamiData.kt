package content.global.skill.production.crafting.data

import core.api.consts.Items

enum class OrigamiData(val requiredDye: Int, val ballonId: Int, val gfxId: Int) {
    YELLOW(Items.YELLOW_DYE_1765, Items.YELLOW_BALLOON_9935, 883),
    BLUE(Items.BLUE_DYE_1767, Items.BLUE_BALLOON_9936, 886),
    RED(Items.RED_DYE_1763, Items.RED_BALLOON_9937, 889),
    ORANGE(Items.ORANGE_DYE_1769, Items.ORANGE_BALLOON_9938, 892),
    GREEN(Items.GREEN_DYE_1771, Items.GREEN_BALLOON_9939, 895),
    PURPLE(Items.PURPLE_DYE_1773, Items.PURPLE_BALLOON_9940, 898),
    PINK(Items.PINK_DYE_6955, Items.PINK_BALLOON_9941, 901),
    BLACK(Items.BLACK_MUSHROOM_INK_4622, Items.BLACK_BALLOON_9942, 904);

    companion object {
        val productMap = HashMap<Int, OrigamiData>()
        val projectileMap = HashMap<Int, Int>()

        init {
            for (base in values()) {
                productMap[base.requiredDye] = base
            }

            for (projectile in values()) {
                projectileMap[projectile.ballonId] = projectile.gfxId
            }
        }
    }
}
