package core.game.world.update.flag.context;

import core.game.node.entity.Entity;

/**
 * Represents a hit mark.
 * @author Emperor
 */
public class HitMark {
    /**
     * The amount of damage dealt.
     */
    private final int damage;

    /**
     * The hit type.
     */
    private final int type;

    /**
     * The entity's lifepoints.
     */
    private int lifepoints;

    /**
     * The entity.
     */
    private final Entity entity;

    /**
     * The Show health bar.
     */
    public boolean showHealthBar = true;

    /**
     * Constructs a new {@code HitMark} {@code Object}.
     *
     * @param damage The amount of damage.
     * @param type   The hit type;
     * @param entity The entity.
     */
    public HitMark(int damage, int type, Entity entity) {
        this.damage = damage;
        this.type = type;
        this.entity = entity;
    }

    /**
     * Constructs a new Hit mark.
     *
     * @param damage        the damage
     * @param type          the type
     * @param entity        the entity
     * @param showHealthBar the show health bar
     */
    public HitMark(int damage, int type, Entity entity, boolean showHealthBar) {
        this.damage = damage;
        this.type = type;
        this.entity = entity;
        this.showHealthBar = showHealthBar;
    }

    /**
     * Gets the damage.
     *
     * @return The damage.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Gets the type.
     *
     * @return The type.
     */
    public int getType() {
        return type;
    }

    /**
     * Gets entity.
     *
     * @return the entity.
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     * Gets the lifepoints.
     *
     * @return The lifepoints.
     */
    public int getLifepoints() {
        return lifepoints;
    }

    /**
     * Sets the lifepoints.
     *
     * @param lifepoints The lifepoints to set.
     */
    public void setLifepoints(int lifepoints) {
        this.lifepoints = lifepoints;
    }
}
