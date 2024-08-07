package core.game.node.entity.impl;

import core.game.interaction.Clocks;
import core.game.node.entity.Entity;
import core.game.world.GameWorld;
import core.game.world.update.flag.EntityFlag;
import core.game.world.update.flag.context.Animation;
import core.game.world.update.flag.context.Graphic;

/**
 * Animator.
 */
public final class Animator {

    /**
     * The constant RESET_A.
     */
    public static final Animation RESET_A = new Animation(-1);

    /**
     * The constant RESET_G.
     */
    public static final Graphic RESET_G = new Graphic(-1);

    private Entity entity;

    private Animation animation;

    private Graphic graphic;

    private Priority priority = Priority.LOW;

    private int ticks;

    /**
     * Instantiates a new Animator.
     *
     * @param entity the entity
     */
    public Animator(Entity entity) {
        this.entity = entity;
    }

    /**
     * The enum Priority.
     */
    public static enum Priority {

        /**
         * Low priority.
         */
        LOW,

        /**
         * Mid priority.
         */
        MID,

        /**
         * High priority.
         */
        HIGH,

        /**
         * Very high priority.
         */
        VERY_HIGH;
    }

    /**
     * Animate boolean.
     *
     * @param animation the animation
     * @return the boolean
     */
    public boolean animate(Animation animation) {
        return animate(animation, null);
    }

    /**
     * Graphics boolean.
     *
     * @param graphic the graphic
     * @return the boolean
     */
    public boolean graphics(Graphic graphic) {
        return animate(null, graphic);
    }

    /**
     * Animate boolean.
     *
     * @param animation the animation
     * @param graphic   the graphic
     * @return the boolean
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
            this.graphic = graphic;
            entity.getUpdateMasks().register(EntityFlag.SpotAnim, graphic);
        }
        return true;
    }

    /**
     * Stop.
     */
    public void stop() {
        animate(RESET_A);
    }

    /**
     * Force animation.
     *
     * @param animation the animation
     */
    public void forceAnimation(Animation animation) {
        ticks = -1;
        animate(animation);
        priority = Priority.HIGH;
    }

    /**
     * Reset.
     */
    public void reset() {
        animate(RESET_A);
        entity.clocks[Clocks.getANIMATION_END()] = 0;
        ticks = 0;
    }

    /**
     * Is animating boolean.
     *
     * @return the boolean
     */
    public boolean isAnimating() {
        return animation != null && animation.getId() != -1 && ticks > GameWorld.getTicks();
    }

    /**
     * Gets animation.
     *
     * @return the animation
     */
    public Animation getAnimation() {
        return animation;
    }

    /**
     * Sets animation.
     *
     * @param animation the animation
     */
    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    /**
     * Gets graphics.
     *
     * @return the graphics
     */
    public Graphic getGraphics() {
        return graphic;
    }

    /**
     * Sets graphics.
     *
     * @param graphic the graphic
     */
    public void setGraphics(Graphic graphic) {
        this.graphic = graphic;
    }

    /**
     * Gets priority.
     *
     * @return the priority
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * Sets priority.
     *
     * @param priority the priority
     */
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
