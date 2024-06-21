package core.game.node.entity.player.link.prayer;

/**
 * Players can also use some Prayers at the same time with others, though doing
 * so will compound the drain rate of these Prayers. Some Prayers cannot be used
 * simultaneously and are marked in the table with the same background colour.
 * For example, activating the "Smite" Prayer while the Protect From
 * Melee" Prayer is active will automatically deactivate "Protect from Melee" as
 * they are incompatible Prayers.
 *
 * @author 'Vexia
 * @date 06 /11/2013
 */
public enum PrayerCategory {
    /**
     * Baby blue prayer category.
     */
    BABY_BLUE,
    /**
     * Green prayer category.
     */
    GREEN,
    /**
     * Pink prayer category.
     */
    PINK,
    /**
     * Lime green prayer category.
     */
    LIME_GREEN,
    /**
     * Orange prayer category.
     */
    ORANGE,
    /**
     * Purple prayer category.
     */
    PURPLE,
    /**
     * Dark green prayer category.
     */
    DARK_GREEN,
    /**
     * Dark brown prayer category.
     */
    DARK_BROWN,
    /**
     * Light brown prayer category.
     */
    LIGHT_BROWN,
    /**
     * Red prayer category.
     */
    RED,
    /**
     * Magenta prayer category.
     */
    MAGENTA;
}
