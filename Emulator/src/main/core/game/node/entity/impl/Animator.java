package core.game.node.entity.impl;

import core.game.interaction.Clocks;
import core.game.node.entity.Entity;
import core.game.world.GameWorld;
import core.game.world.update.flag.EntityFlag;
import core.game.world.update.flag.context.Animation;
import core.game.world.update.flag.context.Graphic;

/**
 * Handles the animating of an Entity.
 * @author Emperor
 */
public final class Animator {

    /**
     * The reset animation.
     */
    public static final Animation RESET_A = new Animation(-1);

    /**
     * The reset graphics.
     */
    public static final Graphic RESET_G = new Graphic(-1);

    /**
     * The entity.
     */
    private Entity entity;

    /**
     * The current animation.
     */
    private Animation animation;

    /**
     * The current graphics.
     */
    private Graphic graphics;

    /**
     * Current priority.
     */
    private Priority priority = Priority.LOW;

    /**
     * The current animation delay.
     */
    private int ticks;

    /**
     * Constructs a new {@code Animator} {@Code Object}.
     *
     * @param entity The entity.
     */
    public Animator(Entity entity) {
        this.entity = entity;
    }

    /**
     * Represents the priorities.
     *
     * @author Emperor
     */
    public static enum Priority {

        /**
         * Lowest priority.
         */
        LOW,

        /**
         * Medium priority (override low priority)
         */
        MID,

        /**
         * High priority (override all)
         */
        HIGH,

        /**
         * Extra priority only to be used when really needed. (overrides death
         * animation etc).
         */
        VERY_HIGH;
    }

    /**
     * Starts an animation.
     *
     * @param animation The animation.
     * @return {@code True} if successful.
     */
    public boolean animate(Animation animation) {
        return animate(animation, null);
    }

    /**
     * Starts a graphic.
     *
     * @param graphic The graphic.
     * @return {@code True} if successful.
     */
    public boolean graphics(Graphic graphic) {
        return animate(null, graphic);
    }

    /**
     * Starts an animation (and if animation is null or has successfully started
     * > start graphic)
     *
     * @param animation The animation.
     * @param graphic   The graphic.
     * @return {@code True} if successfully started.
     */
    public boolean animate(Animation animation, Graphic graphic) {
        if (animation != null) {
            if (ticks > GameWorld.getTicks() && priority.ordinal() > animation.getPriority().ordinal()) {
                return false;
            }
            if (animation.getId() == 0) {
                animation.setId(-1);
            }
            this.animation = animation;
            if (animation.getId() != -1) {
                ticks = GameWorld.getTicks() + animation.getDuration();
            } else {
                ticks = 0;
            }
            entity.clocks[Clocks.getANIMATION_END()] = ticks;
            entity.getUpdateMasks().register(EntityFlag.Animate, animation);
            priority = animation.getPriority();
        }
        if (graphic != null) {
            this.graphics = graphic;
            entity.getUpdateMasks().register(EntityFlag.SpotAnim, graphic);
        }
        return true;
    }

    public void stop() {
        animate(RESET_A);
    }

    /**
     * Forces an animation.
     *
     * @param animation The animation to display.
     */
    public void forceAnimation(Animation animation) {
        ticks = -1;
        animate(animation);
        priority = Priority.HIGH;
    }

    /**
     * Method used to reset the animator.
     */
    public void reset() {
        animate(RESET_A);
        entity.clocks[Clocks.getANIMATION_END()] = 0;
        ticks = 0;
    }

    /**
     * Checks if the entity is animating.
     *
     * @return {@code True} if so.
     */
    public boolean isAnimating() {
        return animation != null && animation.getId() != -1 && ticks > GameWorld.getTicks();
    }

    /**
     * Gets animation.
     *
     * @return the animation.
     */
    public Animation getAnimation() {
        return animation;
    }

    /**
     * Sets animation.
     *
     * @param animation the animation to set.
     */
    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    /**
     * Gets graphics.
     *
     * @return the graphics.
     */
    public Graphic getGraphics() {
        return graphics;
    }

    /**
     * Sets graphics.
     *
     * @param graphics the graphics to set.
     */
    public void setGraphics(Graphic graphics) {
        this.graphics = graphics;
    }

    /**
     * Gets the priority.
     *
     * @return The priority.
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * Sets the priority.
     *
     * @param priority The priority to set.
     */
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
