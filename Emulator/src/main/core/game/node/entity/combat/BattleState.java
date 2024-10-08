package core.game.node.entity.combat;

import core.game.node.entity.Entity;
import core.game.node.entity.combat.equipment.Ammunition;
import core.game.node.entity.combat.equipment.ArmourSet;
import core.game.node.entity.combat.equipment.RangeWeapon;
import core.game.node.entity.combat.equipment.Weapon;
import core.game.node.entity.combat.spell.CombatSpell;

/**
 * Represents an entity's current battle state.
 * @author Emperor
 */
public final class BattleState {

    /**
     * The entity.
     */
    private Entity entity;

    /**
     * The victim entity.
     */
    private Entity victim;

    /**
     * The estimated hit.
     */
    private int estimatedHit;

    /**
     * The maximum hit.
     */
    private int maximumHit;

    /**
     * The second estimated hit.
     */
    private int secondaryHit = -1;

    /**
     * The amount of recoil damage.
     */
    private int recoilDamage = -1;

    /**
     * The amount of poison damage.
     */
    private int poisonDamage = -1;

    /**
     * If the victim is frozen.
     */
    private boolean frozen;

    /**
     * The other targets.
     */
    private BattleState[] targets;

    /**
     * The weapon used.
     */
    private Weapon weapon;

    /**
     * The range weapon used.
     */
    private RangeWeapon rangeWeapon;

    /**
     * The range ammunition used.
     */
    private Ammunition ammunition;

    /**
     * The magic spell used.
     */
    private CombatSpell spell;

    /**
     * The combat style used (used for style-switching NPCs).
     */
    private CombatStyle style;

    /**
     * The active armour set effect.
     */
    private ArmourSet armourEffect;

    /**
     * Constructs a new {@code BattleState} {@code Object}.
     */
    public BattleState() {
        /*
         * empty.
         */
    }

    /**
     * Constructs a new {@code BattleState} {@Code Object}
     *
     * @param entity the entity
     * @param victim The victim entity.
     */
    public BattleState(Entity entity, Entity victim) {
        this.victim = victim;
        this.entity = entity;
    }

    /**
     * Neutralizes the hits.
     */
    public void neutralizeHits() {
        if (getEstimatedHit() > 0) {
            setEstimatedHit(0);
        }
        if (getSecondaryHit() > 0) {
            setSecondaryHit(0);
        }
    }

    /**
     * Gets victim.
     *
     * @return the victim.
     */
    public Entity getVictim() {
        return victim;
    }

    /**
     * Sets victim.
     *
     * @param victim the victim to set.
     */
    public void setVictim(Entity victim) {
        this.victim = victim;
    }

    /**
     * Gets the estimatedHit.
     *
     * @return The estimatedHit.
     */
    public int getEstimatedHit() {
        return estimatedHit;
    }

    /**
     * Sets the estimated hit.
     *
     * @param estimatedHit The estimated hit to set.
     */
    public void setEstimatedHit(int estimatedHit) {
        this.estimatedHit = estimatedHit;
    }

    /**
     * Gets secondary hit.
     *
     * @return the secondaryHit.
     */
    public int getSecondaryHit() {
        return secondaryHit;
    }

    /**
     * Sets secondary hit.
     *
     * @param secondaryHit the secondaryHit to set.
     */
    public void setSecondaryHit(int secondaryHit) {
        this.secondaryHit = secondaryHit;
    }

    /**
     * Gets the recoilDamage.
     *
     * @return The recoilDamage.
     */
    public int getRecoilDamage() {
        return recoilDamage;
    }

    /**
     * Sets the recoilDamage.
     *
     * @param recoilDamage The recoilDamage to set.
     */
    public void setRecoilDamage(int recoilDamage) {
        this.recoilDamage = recoilDamage;
    }

    /**
     * Get targets battle state.
     *
     * @return the targets.
     */
    public BattleState[] getTargets() {
        return targets;
    }

    /**
     * Sets targets.
     *
     * @param targets the targets to set.
     */
    public void setTargets(BattleState[] targets) {
        this.targets = targets;
    }

    /**
     * Gets poison damage.
     *
     * @return the poisonDamage.
     */
    public int getPoisonDamage() {
        return poisonDamage;
    }

    /**
     * Sets poison damage.
     *
     * @param poisonDamage the poisonDamage to set.
     */
    public void setPoisonDamage(int poisonDamage) {
        this.poisonDamage = poisonDamage;
    }

    /**
     * Gets weapon.
     *
     * @return the weapon.
     */
    public Weapon getWeapon() {
        return weapon;
    }

    /**
     * Sets weapon.
     *
     * @param weapon the weapon to set.
     */
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    /**
     * Gets spell.
     *
     * @return the spell.
     */
    public CombatSpell getSpell() {
        return spell;
    }

    /**
     * Sets spell.
     *
     * @param spell the spell to set.
     */
    public void setSpell(CombatSpell spell) {
        this.spell = spell;
    }

    /**
     * Gets the maximumHit.
     *
     * @return The maximumHit.
     */
    public int getMaximumHit() {
        return maximumHit;
    }

    /**
     * Sets the maximumHit.
     *
     * @param maximumHit The maximumHit to set.
     */
    public void setMaximumHit(int maximumHit) {
        this.maximumHit = maximumHit;
    }

    /**
     * Gets the rangeWeapon.
     *
     * @return The rangeWeapon.
     */
    public RangeWeapon getRangeWeapon() {
        return rangeWeapon;
    }

    /**
     * Sets the rangeWeapon.
     *
     * @param rangeWeapon The rangeWeapon to set.
     */
    public void setRangeWeapon(RangeWeapon rangeWeapon) {
        this.rangeWeapon = rangeWeapon;
    }

    /**
     * Gets the ammunition.
     *
     * @return The ammunition.
     */
    public Ammunition getAmmunition() {
        return ammunition;
    }

    /**
     * Sets the ammunition.
     *
     * @param ammunition The ammunition to set.
     */
    public void setAmmunition(Ammunition ammunition) {
        this.ammunition = ammunition;
    }

    /**
     * Gets the frozen.
     *
     * @return The frozen.
     */
    public boolean isFrozen() {
        return frozen;
    }

    /**
     * Sets the frozen.
     *
     * @param frozen The frozen to set.
     */
    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    /**
     * Gets the style.
     *
     * @return The style.
     */
    public CombatStyle getStyle() {
        return style;
    }

    /**
     * Sets the style.
     *
     * @param style The style to set.
     */
    public void setStyle(CombatStyle style) {
        this.style = style;
    }

    /**
     * Gets the armourEffect.
     *
     * @return The armourEffect.
     */
    public ArmourSet getArmourEffect() {
        return armourEffect;
    }

    /**
     * Sets the armourEffect.
     *
     * @param armourEffect The armourEffect to set.
     */
    public void setArmourEffect(ArmourSet armourEffect) {
        this.armourEffect = armourEffect;
    }

    /**
     * Gets the attacking entity.
     *
     * @return The entity.
     */
    public Entity getAttacker() {
        return entity;
    }

    /**
     * Gets total damage.
     *
     * @return the total damage
     */
    public int getTotalDamage() {
        int hit = Math.max(estimatedHit, 0) + Math.max(secondaryHit, 0);

        if (targets != null) {
            for (BattleState s : targets) {
                if (s != null) {
                    hit += Math.max(s.getEstimatedHit(), 0) + Math.max(s.getSecondaryHit(), 0);
                }
            }
        }
        return hit;
    }

}