package content.global.skill.agility;

import core.cache.def.impl.SceneryDefinition;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.node.scenery.Scenery;
import core.game.world.map.Direction;
import core.game.world.map.Location;
import core.plugin.Plugin;

import static core.api.ContentAPIKt.sendMessage;

/**
 * Abstract class representing an agility shortcut in the game.
 */
public abstract class AgilityShortcut extends OptionHandler {
    private final int[] ids;
    private final int level;
    private final double experience;
    private final boolean canFail;
    private double failChance;
    private final String[] options;

    /**
     * Constructor for creating an AgilityShortcut with failure chance.
     *
     * @param ids        Array of scenery IDs
     * @param level      Required agility level
     * @param experience Experience gained
     * @param canFail    Indicates if the shortcut can fail
     * @param failChance Chance of failure
     * @param options    Options available for the shortcut
     */
    public AgilityShortcut(int[] ids, int level, double experience, boolean canFail, double failChance, String... options) {
        this.ids = ids;
        this.level = level;
        this.experience = experience;
        this.canFail = canFail;
        this.failChance = failChance;
        this.options = options;
    }

    /**
     * Constructor for creating an AgilityShortcut without failure chance.
     *
     * @param ids        Array of scenery IDs
     * @param level      Required agility level
     * @param experience Experience gained
     * @param options    Options available for the shortcut
     */
    public AgilityShortcut(int[] ids, int level, double experience, String... options) {
        this(ids, level, experience, false, 0.0, options);
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        configure(this);
        return this;
    }

    @Override
    public boolean handle(Player player, Node node, String option) {
        if (!checkRequirements(player)) {
            return true;
        }
        run(player, node.asScenery(), option, checkFail(player, node.asScenery(), option));
        return true;
    }

    /**
     * Abstract method to run the agility shortcut.
     *
     * @param player The player using the shortcut
     * @param object The scenery object associated with the shortcut
     * @param option The option selected by the player
     * @param failed Indicates if the attempt failed
     */
    public abstract void run(Player player, Scenery object, String option, boolean failed);

    /**
     * Checks if the player meets the requirements to use the agility shortcut.
     *
     * @param player The player attempting to use the shortcut
     * @return true if the player meets the requirements, false otherwise
     */
    public boolean checkRequirements(Player player) {
        if (player.getSkills().getLevel(Skills.AGILITY) < level) {
            sendMessage(player, "You need an agility level of at least " + level + " to negotiate this obstacle.");
            return false;
        }
        return true;
    }

    private boolean checkFail(Player player, Scenery object, String option2) {
        if (!canFail) {
            return false;
        }
        return AgilityHandler.hasFailed(player, level, failChance);
    }

    /**
     * Configures the agility shortcut by associating it with scenery definitions.
     *
     * @param shortcut The agility shortcut to configure
     */
    public void configure(AgilityShortcut shortcut) {
        for (int objectId : shortcut.ids) {
            SceneryDefinition def = SceneryDefinition.forId(objectId);
            for (String option : shortcut.options) {
                def.getHandlers().put("option:" + option, shortcut);
            }
        }
    }

    /**
     * Gets the direction of the object based on the provided direction.
     *
     * @param direction The original direction
     * @return The new direction
     */
    protected Direction getObjectDirection(Direction direction) {
        return direction == Direction.NORTH ? Direction.EAST : direction == Direction.SOUTH ? Direction.WEST : direction == Direction.EAST ? Direction.NORTH : Direction.SOUTH;
    }

    /**
     * Calculates the destination location for the player when using a pipe.
     *
     * @param player The player using the pipe
     * @param object The scenery object associated with the pipe
     * @param steps  The number of steps to move
     * @return The destination location
     */
    public Location pipeDestination(Player player, Scenery object, int steps) {
        player.faceLocation(object.getLocation());
        int diffX = object.getLocation().getX() - player.getLocation().getX();
        if (diffX < -1) {
            diffX = -1;
        }
        if (diffX > 1) {
            diffX = 1;
        }
        int diffY = object.getLocation().getY() - player.getLocation().getY();
        if (diffY < -1) {
            diffY = -1;
        }
        if (diffY > 1) {
            diffX = 1;
        }
        Location dest = player.getLocation().transform((diffX) * steps, (diffY) * steps, 0);
        return dest;
    }

    /**
     * Calculates the destination location for the player when using agility.
     *
     * @param player The player using the agility shortcut
     * @param object The scenery object associated with the agility shortcut
     * @param steps  The number of steps to move
     * @return The destination location
     */
    public Location agilityDestination(Player player, Scenery object, int steps) {
        player.faceLocation(object.getLocation());
        int diffX = object.getLocation().getX() - player.getLocation().getX();
        int diffY = object.getLocation().getY() - player.getLocation().getY();
        Location dest = player.getLocation().transform((diffX) * steps, (diffY) * steps, 0);
        return dest;
    }

    /**
     * Gets the required agility level for this shortcut.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the experience gained from using this shortcut.
     *
     * @return the experience
     */
    public double getExperience() {
        return experience;
    }

    /**
     * Checks if the shortcut can fail.
     *
     * @return the boolean indicating if it can fail
     */
    public boolean isCanFail() {
        return canFail;
    }

    /**
     * Gets the chance of failure for this shortcut.
     *
     * @return the fail chance
     */
    public double getFailChance() {
        return failChance;
    }

    /**
     * Sets the chance of failure for this shortcut.
     *
     * @param failChance the fail chance to set
     */
    public void setFailChance(double failChance) {
        this.failChance = failChance;
    }

    /**
     * Gets the array of scenery IDs associated with this shortcut.
     *
     * @return the array of IDs
     */
    public int[] getIds() {
        return ids;
    }

    /**
     * Gets the array of options available for this shortcut.
     *
     * @return the array of options
     */
    public String[] getOption() {
        return options;
    }

}
