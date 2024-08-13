package core.game.node.entity.player.link

/**
 * Ironman mode
 *
 * @property icon Represents the icon associated with the Ironman mode.
 * @constructor Represents the Ironman mode with a specific icon.
 */
enum class IronmanMode(@JvmField val icon: Int) {
    /**
     * None
     *
     * @constructor Represents the absence of any Ironman mode.
     */
    NONE(-1), // Indicates no Ironman mode is selected.

    /**
     * Standard
     *
     * @constructor Represents the standard Ironman mode.
     */
    STANDARD(5), // Represents the standard Ironman mode with a specific icon value.

    /**
     * Hardcore
     *
     * @constructor Represents the hardcore Ironman mode.
     */
    HARDCORE(6), // Represents the hardcore Ironman mode with a specific icon value.

    /**
     * Ultimate
     *
     * @constructor Represents the ultimate Ironman mode.
     */
    ULTIMATE(7) // Represents the ultimate Ironman mode with a specific icon value.
}
