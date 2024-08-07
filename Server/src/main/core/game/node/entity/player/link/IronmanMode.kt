package core.game.node.entity.player.link

/**
 * Ironman mode
 *
 * @property icon
 * @constructor Ironman mode
 */
enum class IronmanMode(@JvmField val icon: Int) {
    /**
     * None
     *
     * @constructor None
     */
    NONE(-1),

    /**
     * Standard
     *
     * @constructor Standard
     */
    STANDARD(5),

    /**
     * Hardcore
     *
     * @constructor Hardcore
     */
    HARDCORE(6),

    /**
     * Ultimate
     *
     * @constructor Ultimate
     */
    ULTIMATE(7)
}
