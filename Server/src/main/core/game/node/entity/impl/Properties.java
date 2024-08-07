package core.game.node.entity.impl;

import core.game.container.Container;
import core.game.container.impl.EquipmentContainer;
import core.game.node.entity.Entity;
import core.game.node.entity.combat.CombatPulse;
import core.game.node.entity.combat.CombatStyle;
import core.game.node.entity.combat.equipment.ArmourSet;
import core.game.node.entity.combat.equipment.WeaponInterface;
import core.game.node.entity.combat.spell.CombatSpell;
import core.game.node.entity.npc.NPC;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.game.system.config.ItemConfigParser;
import core.game.system.config.NPCConfigParser;
import core.game.world.GameWorld;
import core.game.world.map.Location;
import core.game.world.update.flag.context.Animation;
import core.game.world.update.flag.context.Graphic;

/**
 * Properties.
 */
public final class Properties {

    private final Entity entity;

    private CombatPulse combatPulse;

    private boolean retaliating = true;

    private boolean teleporting;

    private int combatLevel;

    private WeaponInterface.AttackStyle attackStyle;

    private Location teleportLocation;

    private Location spawnLocation;

    private int[] bonuses = new int[15];

    private int attackSpeed = 4;

    private long lastAnimationEnd;

    private Animation attackAnimation = new Animation(422, Animator.Priority.HIGH);

    private Animation defenceAnimation = new Animation(404);

    private Animation deathAnimation = new Animation(9055, Animator.Priority.HIGH);

    /**
     * The Death gfx.
     */
    public Graphic deathGfx = new Graphic(-1);

    private Animation rangeAnimation;

    private Animation magicAnimation;

    private CombatSpell spell;

    private CombatSpell autocastSpell;

    private ArmourSet armourSet;

    private boolean multiZone;

    private boolean safeZone;

    /**
     * The Safe respawn.
     */
    public Location safeRespawn;

    private int combatTimeOut = 10;

    private boolean npcWalkable;

    private CombatStyle protectStyle;

    /**
     * Instantiates a new Properties.
     *
     * @param entity the entity
     */
    public Properties(Entity entity) {
        this.entity = entity;
        this.combatPulse = new CombatPulse(entity);
    }

    /**
     * Update defence animation.
     */
    public void updateDefenceAnimation() {
        if (entity instanceof NPC) {
            Animation animation = ((NPC) entity).getDefinition().getConfiguration(NPCConfigParser.DEFENCE_ANIMATION);
            if (animation != null) {
                defenceAnimation = animation;
            }
            return;
        }
        Container c = ((Player) entity).getEquipment();
        Item item = c.get(EquipmentContainer.SLOT_SHIELD);
        if (item != null) {
            defenceAnimation = item.getDefinition().getConfiguration(ItemConfigParser.DEFENCE_ANIMATION, Animation.create(1156));
        } else if ((item = c.get(EquipmentContainer.SLOT_WEAPON)) != null) {
            defenceAnimation = item.getDefinition().getConfiguration(ItemConfigParser.DEFENCE_ANIMATION, Animation.create(424));
        } else {
            defenceAnimation = Animation.create(397);
        }
    }

    /**
     * Gets combat animation.
     *
     * @param index the index
     * @return the combat animation
     */
    public Animation getCombatAnimation(int index) {
        return index == 0 ? attackAnimation : index == 1 ? magicAnimation : index == 2 ? rangeAnimation : index == 3 ? defenceAnimation : deathAnimation;
    }

    /**
     * Gets entity.
     *
     * @return the entity
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     * Is teleporting boolean.
     *
     * @return the boolean
     */
    public boolean isTeleporting() {
        return teleporting;
    }

    /**
     * Sets teleporting.
     *
     * @param teleporting the teleporting
     */
    public void setTeleporting(boolean teleporting) {
        this.teleporting = teleporting;
    }

    /**
     * Gets combat level.
     *
     * @return the combat level
     */
    @Deprecated
    public int getCombatLevel() {
        return combatLevel;
    }

    /**
     * Gets current combat level.
     *
     * @return the current combat level
     */
    public int getCurrentCombatLevel() {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            if ((GameWorld.getSettings().isPvp() || (GameWorld.getSettings().getWild_pvp_enabled() && player.getSkullManager().isWilderness()))
                && !player.getFamiliarManager().isUsingSummoning()) {
                //TODO: Split combat levels should also be used for Bounty Hunter
                return combatLevel;
            } else {
                return combatLevel + player.getFamiliarManager().getSummoningCombatLevel();
            }
        }
        return combatLevel;
    }

    /**
     * Sets combat level.
     *
     * @param combatLevel the combat level
     */
    public void setCombatLevel(int combatLevel) {
        this.combatLevel = combatLevel;
    }

    /**
     * Gets attack style.
     *
     * @return the attack style
     */
    public WeaponInterface.AttackStyle getAttackStyle() {
        return attackStyle;
    }

    /**
     * Sets attack style.
     *
     * @param attackStyle the attack style
     */
    public void setAttackStyle(WeaponInterface.AttackStyle attackStyle) {
        this.attackStyle = attackStyle;
    }

    /**
     * Gets teleport location.
     *
     * @return the teleport location
     */
    public Location getTeleportLocation() {
        return teleportLocation;
    }

    /**
     * Sets teleport location.
     *
     * @param teleportLocation the teleport location
     */
    public void setTeleportLocation(Location teleportLocation) {
        this.teleportLocation = teleportLocation;
    }

    /**
     * Gets spawn location.
     *
     * @return the spawn location
     */
    public Location getSpawnLocation() {
        return spawnLocation;
    }

    /**
     * Sets spawn location.
     *
     * @param spawnLocation the spawn location
     */
    public void setSpawnLocation(Location spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

    /**
     * Get bonuses int [ ].
     *
     * @return the int [ ]
     */
    public int[] getBonuses() {
        return bonuses;
    }

    /**
     * Sets bonuses.
     *
     * @param bonuses the bonuses
     */
    public void setBonuses(int[] bonuses) {
        this.bonuses = bonuses;
    }

    /**
     * Gets last animation end.
     *
     * @return the last animation end
     */
    public long getLastAnimationEnd() {
        return lastAnimationEnd;
    }

    /**
     * Gets combat pulse.
     *
     * @return the combat pulse
     */
    public CombatPulse getCombatPulse() {
        return combatPulse;
    }

    /**
     * Sets combat pulse.
     *
     * @param combatPulse the combat pulse
     */
    public void setCombatPulse(CombatPulse combatPulse) {
        this.combatPulse = (CombatPulse) combatPulse;
    }

    /**
     * Is retaliating boolean.
     *
     * @return the boolean
     */
    public boolean isRetaliating() {
        return retaliating;
    }

    /**
     * Sets retaliating.
     *
     * @param retaliating the retaliating
     */
    public void setRetaliating(boolean retaliating) {
        this.retaliating = retaliating;
    }

    /**
     * Gets attack speed.
     *
     * @return the attack speed
     */
    public int getAttackSpeed() {
        return attackSpeed;
    }

    /**
     * Sets attack speed.
     *
     * @param attackSpeed the attack speed
     */
    public void setAttackSpeed(int attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    /**
     * Gets attack animation.
     *
     * @return the attack animation
     */
    public Animation getAttackAnimation() {
        return attackAnimation;
    }

    /**
     * Sets attack animation.
     *
     * @param attackAnimation the attack animation
     */
    public void setAttackAnimation(Animation attackAnimation) {
        this.attackAnimation = attackAnimation;
    }

    /**
     * Gets defence animation.
     *
     * @return the defence animation
     */
    public Animation getDefenceAnimation() {
        return defenceAnimation;
    }

    /**
     * Sets defence animation.
     *
     * @param defenceAnimation the defence animation
     */
    public void setDefenceAnimation(Animation defenceAnimation) {
        this.defenceAnimation = defenceAnimation;
    }

    /**
     * Sets spell.
     *
     * @param spell the spell
     */
    public void setSpell(CombatSpell spell) {
        this.spell = spell;
        if (spell != null) {
            combatPulse.updateStyle();
        }
    }

    /**
     * Gets spell.
     *
     * @return the spell
     */
    public CombatSpell getSpell() {
        return spell;
    }

    /**
     * Gets autocast spell.
     *
     * @return the autocast spell
     */
    public CombatSpell getAutocastSpell() {
        return autocastSpell;
    }

    /**
     * Sets autocast spell.
     *
     * @param autocastSpell the autocast spell
     */
    public void setAutocastSpell(CombatSpell autocastSpell) {
        this.autocastSpell = autocastSpell;
    }

    /**
     * Gets armour set.
     *
     * @return the armour set
     */
    public ArmourSet getArmourSet() {
        return armourSet;
    }

    /**
     * Sets armour set.
     *
     * @param armourSet the armour set
     */
    public void setArmourSet(ArmourSet armourSet) {
        this.armourSet = armourSet;
    }

    /**
     * Gets death animation.
     *
     * @return the death animation
     */
    public Animation getDeathAnimation() {
        return deathAnimation;
    }

    /**
     * Sets death animation.
     *
     * @param deathAnimation the death animation
     */
    public void setDeathAnimation(Animation deathAnimation) {
        this.deathAnimation = deathAnimation;
    }

    /**
     * Is multi zone boolean.
     *
     * @return the boolean
     */
    public boolean isMultiZone() {
        return multiZone;
    }

    /**
     * Sets multi zone.
     *
     * @param multiZone the multi zone
     */
    public void setMultiZone(boolean multiZone) {
        this.multiZone = multiZone;
    }

    /**
     * Is safe zone boolean.
     *
     * @return the boolean
     */
    public boolean isSafeZone() {
        return safeZone;
    }

    /**
     * Sets safe zone.
     *
     * @param safeZone the safe zone
     */
    public void setSafeZone(boolean safeZone) {
        this.safeZone = safeZone;
    }

    /**
     * Gets combat time out.
     *
     * @return the combat time out
     */
    public int getCombatTimeOut() {
        return combatTimeOut;
    }

    /**
     * Sets combat time out.
     *
     * @param combatTimeOut the combat time out
     */
    public void setCombatTimeOut(int combatTimeOut) {
        this.combatTimeOut = combatTimeOut;
    }

    /**
     * Is npc walkable boolean.
     *
     * @return the boolean
     */
    public boolean isNPCWalkable() {
        return npcWalkable;
    }

    /**
     * Sets npc walkable.
     *
     * @param npcWalkable the npc walkable
     */
    public void setNPCWalkable(boolean npcWalkable) {
        this.npcWalkable = npcWalkable;
    }

    /**
     * Gets range animation.
     *
     * @return the range animation
     */
    public Animation getRangeAnimation() {
        return rangeAnimation;
    }

    /**
     * Sets range animation.
     *
     * @param rangeAnimation the range animation
     */
    public void setRangeAnimation(Animation rangeAnimation) {
        this.rangeAnimation = rangeAnimation;
    }

    /**
     * Gets magic animation.
     *
     * @return the magic animation
     */
    public Animation getMagicAnimation() {
        return magicAnimation;
    }

    /**
     * Sets magic animation.
     *
     * @param magicAnimation the magic animation
     */
    public void setMagicAnimation(Animation magicAnimation) {
        this.magicAnimation = magicAnimation;
    }

    /**
     * Gets protect style.
     *
     * @return the protect style
     */
    public CombatStyle getProtectStyle() {
        return protectStyle;
    }

    /**
     * Sets protect style.
     *
     * @param protectStyle the protect style
     */
    public void setProtectStyle(CombatStyle protectStyle) {
        this.protectStyle = protectStyle;
    }

}
