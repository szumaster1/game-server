package core.game.node.entity.player.link

/**
 * A type of iron man mode.
 * @author Vexia
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
