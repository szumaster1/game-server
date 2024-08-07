package core.game.node.entity.skill;

import content.global.handlers.item.equipment.gloves.BrawlingGloves;
import content.global.handlers.item.equipment.gloves.BrawlingGlovesManager;
import content.global.skill.skillcape.SkillcapePerks;
import core.api.consts.Items;
import core.api.consts.Sounds;
import core.game.event.DynamicSkillLevelChangeEvent;
import core.game.event.XPGainEvent;
import core.game.node.entity.Entity;
import core.game.node.entity.npc.NPC;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.info.PlayerMonitor;
import core.game.node.entity.player.link.request.assist.AssistSession;
import core.game.node.item.Item;
import core.game.world.GameWorld;
import core.network.packet.PacketRepository;
import core.network.packet.context.SkillContext;
import core.network.packet.outgoing.SkillLevel;
import core.plugin.type.XPGainPlugins;
import kotlin.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import static core.api.ContentAPIKt.*;
import static java.lang.Math.floor;
import static java.lang.Math.max;

/**
 * Skills.
 */
public final class Skills {

    /**
     * The Experience multiplier.
     */
    public double experienceMultiplier = 50.0;
    /**
     * The constant SKILL_NAME.
     */
    public static final String[] SKILL_NAME = {"Attack", "Defence", "Strength", "Hitpoints", "Ranged", "Prayer", "Magic", "Cooking", "Woodcutting", "Fletching", "Fishing", "Firemaking", "Crafting", "Smithing", "Mining", "Herblore", "Agility", "Thieving", "Slayer", "Farming", "Runecrafting", "Hunter", "Construction", "Summoning"};
    /**
     * The constant ATTACK.
     */
    public static final int ATTACK = 0,

    /**
     * The Defence.
     */
    DEFENCE = 1,
    /**
     * The Strength.
     */
    STRENGTH = 2,
    /**
     * The Hitpoints.
     */
    HITPOINTS = 3,
    /**
     * The Range.
     */
    RANGE = 4,
    /**
     * The Prayer.
     */
    PRAYER = 5,
    /**
     * The Magic.
     */
    MAGIC = 6,
    /**
     * The Cooking.
     */
    COOKING = 7,
    /**
     * The Woodcutting.
     */
    WOODCUTTING = 8,
    /**
     * The Fletching.
     */
    FLETCHING = 9,
    /**
     * The Fishing.
     */
    FISHING = 10,
    /**
     * The Firemaking.
     */
    FIREMAKING = 11,
    /**
     * The Crafting.
     */
    CRAFTING = 12,
    /**
     * The Smithing.
     */
    SMITHING = 13,
    /**
     * The Mining.
     */
    MINING = 14,
    /**
     * The Herblore.
     */
    HERBLORE = 15,
    /**
     * The Agility.
     */
    AGILITY = 16,
    /**
     * The Thieving.
     */
    THIEVING = 17,
    /**
     * The Slayer.
     */
    SLAYER = 18,
    /**
     * The Farming.
     */
    FARMING = 19,
    /**
     * The Runecrafting.
     */
    RUNECRAFTING = 20,
    /**
     * The Hunter.
     */
    HUNTER = 21,
    /**
     * The Construction.
     */
    CONSTRUCTION = 22,
    /**
     * The Summoning.
     */
    SUMMONING = 23;

    /**
     * The constant NUM_SKILLS.
     */
    public static final int NUM_SKILLS = 24;
    private final Entity entity;
    private final double[] experience;
    private double[] lastUpdateXp = null;
    private int lastUpdate = GameWorld.getTicks();
    private final int[] staticLevels;
    private final int[] dynamicLevels;
    private double prayerPoints = 1.;
    private int lifepoints = 10;
    private int lifepointsIncrease = 0;
    private double experienceGained = 0;
    private boolean lifepointsUpdate;
    private int combatMilestone;
    private int skillMilestone;
    /**
     * The Last trained skill.
     */
    public int lastTrainedSkill = -1;
    /**
     * The Last xp gain.
     */
    public int lastXpGain = 0;

    /**
     * Instantiates a new Skills.
     *
     * @param entity the entity
     */
    public Skills(Entity entity) {
        this.entity = entity;
        this.experience = new double[24];
        this.staticLevels = new int[24];
        this.dynamicLevels = new int[24];
        for (int i = 0; i < 24; i++) {
            this.staticLevels[i] = 1;
            this.dynamicLevels[i] = 1;
        }
        this.experience[HITPOINTS] = 1154;
        this.dynamicLevels[HITPOINTS] = 10;
        this.staticLevels[HITPOINTS] = 10;
        entity.getProperties().setCombatLevel(3);
    }


    /**
     * Is combat boolean.
     *
     * @param skill the skill
     * @return the boolean
     */
    public boolean isCombat(int skill) {
        if ((skill >= ATTACK && skill <= MAGIC) || (skill == SUMMONING)) {
            return true;
        }
        return false;
    }

    /**
     * Configure.
     */
    public void configure() {
        updateCombatLevel();
    }

    /**
     * Pulse.
     */
    public void pulse() {
        if (lifepoints < 1) {
            return;
        }
    }

    /**
     * Copy.
     *
     * @param skills the skills
     */
    public void copy(Skills skills) {
        for (int i = 0; i < 24; i++) {
            this.staticLevels[i] = skills.staticLevels[i];
            this.dynamicLevels[i] = skills.dynamicLevels[i];
            this.experience[i] = skills.experience[i];
        }
        prayerPoints = skills.prayerPoints;
        lifepoints = skills.lifepoints;
        lifepointsIncrease = skills.lifepointsIncrease;
        experienceGained = skills.experienceGained;
    }

    /**
     * Add experience.
     *
     * @param slot       the slot
     * @param experience the experience
     * @param playerMod  the player mod
     */
    public void addExperience(int slot, double experience, boolean playerMod) {
        if (lastUpdateXp == null)
            lastUpdateXp = this.experience.clone();
        double mod = getExperienceMod(slot, experience, playerMod, true);
        final Player player = entity instanceof Player ? ((Player) entity) : null;
        final AssistSession assist = entity.getExtension(AssistSession.class);
        if (assist != null && assist.translateExperience(player, slot, experience, mod)) {
            return;
        }
        boolean already200m = this.experience[slot] == 200000000;
        double experienceAdd = (experience * mod);
        /*
         * Check if a player has brawling gloves and, if equipped, modify xp.
         */
        BrawlingGlovesManager bgManager = BrawlingGlovesManager.getInstance(player);
        if (!bgManager.GloveCharges.isEmpty()) {
            Item gloves = BrawlingGloves.forSkill(slot) == null ? null : new Item(BrawlingGloves.forSkill(slot).getId());
            if (gloves == null && (slot == Skills.STRENGTH || slot == Skills.DEFENCE)) {
                gloves = new Item(BrawlingGloves.forSkill(Skills.ATTACK).getId());
            }
            if (gloves != null && player.getEquipment().containsItem(gloves)) {
                experienceAdd += experienceAdd * bgManager.getExperienceBonus();
                bgManager.updateCharges(gloves.getId(), 1);
            }
        }
        /*
         * Check for Flame Gloves and Ring of Fire.
         */
        if (player.getEquipment().containsItem(new Item(Items.FLAME_GLOVES_13660)) || player.getEquipment().containsItem(new Item(Items.RING_OF_FIRE_13659))) {
            if (slot == Skills.FIREMAKING) {
                int count = 0;
                if (player.getEquipment().containsItem(new Item(Items.FLAME_GLOVES_13660))) count += 1;
                if (player.getEquipment().containsItem(new Item(Items.RING_OF_FIRE_13659))) count += 1;
                if (count == 2) experienceAdd += (0.05 * experienceAdd);
                else experienceAdd += (0.02 * experienceAdd);
            }
        }
        this.experience[slot] += experienceAdd;
        if (this.experience[slot] >= 200000000) {
            if (!already200m && !player.isArtificial()) {
                sendNews(entity.asPlayer().getUsername() + " has just reached 200m experience in " + SKILL_NAME[slot] + "!");
            }
            this.experience[slot] = 200000000;
        }
        if (entity instanceof Player && this.experience[slot] > 175) {
            if (!player.getAttribute("tutorial:complete", false) && slot != HITPOINTS) {
                this.experience[slot] = 175;
            }
        }
        experienceGained += experienceAdd;
        XPGainPlugins.run(player, slot, experienceAdd);
        int newLevel = getStaticLevelByExperience(slot);
        if (newLevel > staticLevels[slot]) {
            int amount = newLevel - staticLevels[slot];
            if (dynamicLevels[slot] < newLevel) {
                dynamicLevels[slot] += amount;
            }
            if (slot == HITPOINTS) {
                lifepoints += amount;
            }
            staticLevels[slot] = newLevel;

            if (entity instanceof Player) {
                player.updateAppearance();
                LevelUp.levelUp(player, slot, amount);
                updateCombatLevel();
            }
        }
        if (entity instanceof Player) {
            PacketRepository.send(SkillLevel.class, new SkillContext((Player) entity, slot));
            entity.dispatch(new XPGainEvent(slot, experienceAdd));
        }
        if (GameWorld.getTicks() - lastUpdate >= 200) {
            ArrayList<Pair<Integer, Double>> diffs = new ArrayList<>();
            for (int i = 0; i < this.experience.length; i++) {
                double diff = this.experience[i] - lastUpdateXp[i];
                if (diff != 0.0) {
                    diffs.add(new Pair<>(i, diff));
                }
            }
            PlayerMonitor.logXpGains(player, diffs);
            lastUpdateXp = this.experience.clone();
            lastUpdate = GameWorld.getTicks();
        }
        lastTrainedSkill = slot;
        lastXpGain = getWorldTicks();
    }

    private double getExperienceMod(int slot, double experience, boolean playerMod, boolean multiplyer) {
        return experienceMultiplier;
    }

    /**
     * Add experience.
     *
     * @param slot       the slot
     * @param experience the experience
     */
    public void addExperience(final int slot, double experience) {
        addExperience(slot, experience, false);
    }

    /**
     * Gets highest combat skill id.
     *
     * @return the highest combat skill id
     */
    public int getHighestCombatSkillId() {
        int id = 0;
        int last = 0;
        for (int i = 0; i < 5; i++) {
            if (staticLevels[i] > last) {
                last = staticLevels[i];
                id = i;
            }
        }
        return id;
    }

    /**
     * Restore.
     */
    public void restore() {
        for (int i = 0; i < 24; i++) {
            int staticLevel = getStaticLevel(i);
            setLevel(i, staticLevel);
        }
        if (entity instanceof Player) {
            playAudio(entity.asPlayer(), Sounds.PRAYER_RECHARGE_2674);
        }
        rechargePrayerPoints();
    }

    /**
     * Parse.
     *
     * @param buffer the buffer
     */
    public void parse(ByteBuffer buffer) {
        for (int i = 0; i < 24; i++) {
            experience[i] = ((double) buffer.getInt() / 10D);
            dynamicLevels[i] = buffer.get() & 0xFF;
            if (i == HITPOINTS) {
                lifepoints = dynamicLevels[i];
            } else if (i == PRAYER) {
                prayerPoints = dynamicLevels[i];
            }
            staticLevels[i] = buffer.get() & 0xFF;
        }
        experienceGained = buffer.getInt();
    }

    /**
     * Parse.
     *
     * @param skillData the skill data
     */
    public void parse(JSONArray skillData) {
        for (int i = 0; i < skillData.size(); i++) {
            JSONObject skill = (JSONObject) skillData.get(i);
            int id = Integer.parseInt(skill.get("id").toString());
            dynamicLevels[id] = Integer.parseInt(skill.get("dynamic").toString());
            if (id == HITPOINTS) {
                lifepoints = dynamicLevels[i];
            } else if (id == PRAYER) {
                prayerPoints = dynamicLevels[i];
            }
            staticLevels[id] = Integer.parseInt(skill.get("static").toString());
            experience[id] = Double.parseDouble(skill.get("experience").toString());
        }
    }

    /**
     * Correct.
     *
     * @param divisor the divisor
     */
    public void correct(double divisor) {
        for (int i = 0; i < staticLevels.length; i++) {
            experience[i] /= divisor;
            staticLevels[i] = getStaticLevelByExperience(i);
            dynamicLevels[i] = staticLevels[i];
            if (i == PRAYER) {
                setPrayerPoints(staticLevels[i]);
            }
            if (i == HITPOINTS) {
                setLifepoints(staticLevels[i]);
            }
        }
        experienceMultiplier = 50.0;
        updateCombatLevel();
    }

    /**
     * Parse exp rate.
     *
     * @param buffer the buffer
     */
    public void parseExpRate(ByteBuffer buffer) {
        experienceMultiplier = buffer.getDouble();
        if (GameWorld.getSettings().getDefault_xp_rate() != experienceMultiplier) {
            experienceMultiplier = GameWorld.getSettings().getDefault_xp_rate();
        }
    }

    /**
     * Save.
     *
     * @param buffer the buffer
     */
    public void save(ByteBuffer buffer) {
        for (int i = 0; i < 24; i++) {
            buffer.putInt((int) (experience[i] * 10));
            if (i == HITPOINTS) {
                buffer.put((byte) lifepoints);
            } else if (i == PRAYER) {
                buffer.put((byte) Math.ceil(prayerPoints));
            } else {
                buffer.put((byte) dynamicLevels[i]);
            }
            buffer.put((byte) staticLevels[i]);
        }
        buffer.putInt((int) experienceGained);
    }

    /**
     * Save exp rate.
     *
     * @param buffer the buffer
     */
    public void saveExpRate(ByteBuffer buffer) {
        buffer.putDouble(experienceMultiplier);
    }

    /**
     * Refresh.
     */
    public void refresh() {
        if (!(entity instanceof Player)) {
            return;
        }
        Player player = (Player) entity;
        for (int i = 0; i < 24; i++) {
            PacketRepository.send(SkillLevel.class, new SkillContext(player, i));
        }
        LevelUp.sendFlashingIcons(player, -1);
    }

    /**
     * Gets static level by experience.
     *
     * @param slot the slot
     * @return the static level by experience
     */
    public int getStaticLevelByExperience(int slot) {
        double exp = experience[slot];

        int points = 0;
        int output = 0;
        for (byte lvl = 1; lvl < 100; lvl++) {
            points += (int) floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
            output = (int) floor((double) points / 4);
            if ((output - 1) >= exp) {
                return lvl;
            }
        }
        return 99;
    }

    /**
     * Level from xp int.
     *
     * @param exp the exp
     * @return the int
     */
    public int levelFromXP(double exp) {

        int points = 0;
        int output = 0;
        for (byte lvl = 1; lvl < 100; lvl++) {
            points += (int) floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
            output = (int) floor((double) points / 4);
            if ((output - 1) >= exp) {
                return lvl;
            }
        }
        return 99;
    }

    /**
     * Gets experience by level.
     *
     * @param level the level
     * @return the experience by level
     */
    public int getExperienceByLevel(int level) {
        int points = 0;
        int output = 0;
        for (int lvl = 1; lvl <= level; lvl++) {
            points += (int) floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
            if (lvl >= level) {
                return output;
            }
            output = (int) floor((double) points / 4);
        }
        return 0;
    }

    /**
     * Update combat level boolean.
     *
     * @return the boolean
     */
    @SuppressWarnings("deprecation")
    public boolean updateCombatLevel() {
        boolean update = false;
        int level = calculateCombatLevel();
        update = level != entity.getProperties().getCombatLevel();
        if (entity instanceof Player) {
            Player player = (Player) entity;
            int summon = staticLevels[SUMMONING] / 8;
            if (summon != player.getFamiliarManager().getSummoningCombatLevel()) {
                player.getFamiliarManager().setSummoningCombatLevel(summon);
                update = true;
            }
        }
        entity.getProperties().setCombatLevel(level);
        return update;
    }

    private int calculateCombatLevel() {
        if (entity instanceof NPC) {
            return ((NPC) entity).getDefinition().getCombatLevel();
        }

        double base = 0.25 * (staticLevels[DEFENCE] + staticLevels[HITPOINTS] + floor(0.5 * staticLevels[PRAYER]));
        double meleeBase = 0.325 * (staticLevels[ATTACK] + staticLevels[STRENGTH]);
        double rangeBase = 0.325 * (floor(staticLevels[RANGE] / 2.0) * 3.0);
        double magicBase = 0.325 * (floor(staticLevels[MAGIC] / 2.0) * 3.0);

        return (int) (base + max(meleeBase, max(rangeBase, magicBase)));
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
     * Gets experience.
     *
     * @param slot the slot
     * @return the experience
     */
    public double getExperience(int slot) {
        return experience[slot];
    }

    /**
     * Gets static level.
     *
     * @param slot the slot
     * @return the static level
     */
    public int getStaticLevel(int slot) {
        return staticLevels[slot];
    }

    /**
     * Sets experience gained.
     *
     * @param experienceGained the experience gained
     */
    public void setExperienceGained(double experienceGained) {
        this.experienceGained = experienceGained;
    }

    /**
     * Gets experience gained.
     *
     * @return the experience gained
     */
    public double getExperienceGained() {
        return experienceGained;
    }

    /**
     * Sets level.
     *
     * @param slot  the slot
     * @param level the level
     */
    public void setLevel(int slot, int level) {
        if (slot == HITPOINTS) {
            lifepoints = level;
        } else if (slot == PRAYER) {
            prayerPoints = level;
        }

        int previousLevel = dynamicLevels[slot];
        dynamicLevels[slot] = level;

        if (entity instanceof Player) {
            PacketRepository.send(SkillLevel.class, new SkillContext((Player) entity, slot));
            entity.dispatch(new DynamicSkillLevelChangeEvent(slot, previousLevel, level));
        }
    }

    /**
     * Gets level.
     *
     * @param slot          the slot
     * @param discardAssist the discard assist
     * @return the level
     */
    public int getLevel(int slot, boolean discardAssist) {
        if (!discardAssist) {
            if (entity instanceof Player) {
                final Player p = (Player) entity;
                final AssistSession assist = p.getExtension(AssistSession.class);
                if (assist != null && assist.getPlayer() != p) {
                    Player assister = assist.getPlayer();
                    int index = assist.getSkillIndex(slot);
                    if (index != -1 && !assist.isRestricted()) {

                        // assist.getSkills()[index] + ", " + SKILL_NAME[slot]);
                        if (assist.getSkills()[index]) {
                            int assistLevel = assister.getSkills().getLevel(slot);
                            int playerLevel = dynamicLevels[slot];
                            if (assistLevel > playerLevel) {
                                return assistLevel;
                            }
                        }
                    }
                }
            }
        }
        return dynamicLevels[slot];
    }

    /**
     * Gets level.
     *
     * @param slot the slot
     * @return the level
     */
    public int getLevel(int slot) {
        return getLevel(slot, false);
    }

    /**
     * Sets lifepoints.
     *
     * @param lifepoints the lifepoints
     */
    public void setLifepoints(int lifepoints) {
        this.lifepoints = lifepoints;
        if (this.lifepoints < 0) {
            this.lifepoints = 0;
        }
        lifepointsUpdate = true;
    }

    /**
     * Gets lifepoints.
     *
     * @return the lifepoints
     */
    public int getLifepoints() {
        return lifepoints;
    }

    /**
     * Gets maximum lifepoints.
     *
     * @return the maximum lifepoints
     */
    public int getMaximumLifepoints() {
        if (this.entity instanceof Player && SkillcapePerks.isActive(SkillcapePerks.DAMAGE_SPONG, this.getEntity().asPlayer())) {
            lifepointsIncrease = 11;
        } else {
            lifepointsIncrease = 0;
        }
        return staticLevels[HITPOINTS] + lifepointsIncrease;
    }

    /**
     * Sets lifepoints increase.
     *
     * @param amount the amount
     */
    public void setLifepointsIncrease(int amount) {
        this.lifepointsIncrease = amount;
    }

    /**
     * Heal int.
     *
     * @param health the health
     * @return the int
     */
    public int heal(int health) {
        lifepoints += health;
        int left = 0;
        if (lifepoints > getMaximumLifepoints()) {
            left = lifepoints - getMaximumLifepoints();
            lifepoints = getMaximumLifepoints();
        }
        lifepointsUpdate = true;
        return left;
    }

    /**
     * Heal no restrictions.
     *
     * @param amount the amount
     */
    public void healNoRestrictions(int amount) {
        lifepoints += amount;
        lifepointsUpdate = true;
    }

    /**
     * Hit int.
     *
     * @param damage the damage
     * @return the int
     */
    public int hit(int damage) {
        lifepoints -= damage;
        int left = 0;
        if (lifepoints < 0) {
            left = -lifepoints;
            lifepoints = 0;
        }
        lifepointsUpdate = true;
        return left;
    }

    /**
     * Gets prayer points.
     *
     * @return the prayer points
     */
    public double getPrayerPoints() {
        return prayerPoints;
    }

    /**
     * Recharge prayer points.
     */
    public void rechargePrayerPoints() {
        prayerPoints = staticLevels[PRAYER];
        if (entity instanceof Player) {
            PacketRepository.send(SkillLevel.class, new SkillContext((Player) entity, PRAYER));
        }
    }

    /**
     * Decrement prayer points.
     *
     * @param amount the amount
     */
    public void decrementPrayerPoints(double amount) {
        prayerPoints -= amount;
        if (prayerPoints < 0) {
            prayerPoints = 0;
        }
        // if (prayerPoints > staticLevels[PRAYER]) {
        // prayerPoints = staticLevels[PRAYER];
        // }
        if (entity instanceof Player) {
            PacketRepository.send(SkillLevel.class, new SkillContext((Player) entity, PRAYER));
        }
    }

    /**
     * Increment prayer points.
     *
     * @param amount the amount
     */
    public void incrementPrayerPoints(double amount) {
        prayerPoints += amount;
        if (prayerPoints < 0) {
            prayerPoints = 0;
        }
        if (prayerPoints > staticLevels[PRAYER]) {
            prayerPoints = staticLevels[PRAYER];
        }
        if (entity instanceof Player) {
            PacketRepository.send(SkillLevel.class, new SkillContext((Player) entity, PRAYER));
        }
    }

    /**
     * Sets prayer points.
     *
     * @param amount the amount
     */
    public void setPrayerPoints(double amount) {
        prayerPoints = amount;
        if (entity instanceof Player) {
            PacketRepository.send(SkillLevel.class, new SkillContext((Player) entity, PRAYER));
        }
    }

    /**
     * Update level int.
     *
     * @param skill   the skill
     * @param amount  the amount
     * @param maximum the maximum
     * @return the int
     */
    public int updateLevel(int skill, int amount, int maximum) {
        if (amount > 0 && dynamicLevels[skill] > maximum) {
            return -amount;
        }
        int left = (dynamicLevels[skill] + amount) - maximum;
        int level = dynamicLevels[skill] += amount;
        if (level < 0) {
            dynamicLevels[skill] = 0;
        } else if (amount < 0 && level < maximum) {
            dynamicLevels[skill] = maximum;
        } else if (amount > 0 && level > maximum) {
            dynamicLevels[skill] = maximum;
        }
        if (entity instanceof Player) {
            PacketRepository.send(SkillLevel.class, new SkillContext((Player) entity, skill));
        }
        return left;
    }

    /**
     * Update level int.
     *
     * @param skill  the skill
     * @param amount the amount
     * @return the int
     */
    public int updateLevel(int skill, int amount) {
        return updateLevel(skill, amount, amount >= 0 ? getStaticLevel(skill) + amount : getStaticLevel(skill) - amount);
    }

    /**
     * Drain level.
     *
     * @param skill                  the skill
     * @param drainPercentage        the drain percentage
     * @param maximumDrainPercentage the maximum drain percentage
     */
    public void drainLevel(int skill, double drainPercentage, double maximumDrainPercentage) {
        int drain = (int) (dynamicLevels[skill] * drainPercentage);
        int minimum = (int) (staticLevels[skill] * (1.0 - maximumDrainPercentage));
        updateLevel(skill, -drain, minimum);
    }

    /**
     * Sets static level.
     *
     * @param skill the skill
     * @param level the level
     */
    public void setStaticLevel(int skill, int level) {
        experience[skill] = getExperienceByLevel(staticLevels[skill] = dynamicLevels[skill] = level);
        if (entity instanceof Player) {
            PacketRepository.send(SkillLevel.class, new SkillContext((Player) entity, skill));
        }
    }

    /**
     * Gets mastered skills.
     *
     * @return the mastered skills
     */
    public int getMasteredSkills() {
        int count = 0;
        for (int i = 0; i < 23; i++) {
            if (getStaticLevel(i) >= 99) {
                count++;
            }
        }
        return count;
    }

    /**
     * Gets skill by name.
     *
     * @param name the name
     * @return the skill by name
     */
    public static int getSkillByName(final String name) {
        for (int i = 0; i < SKILL_NAME.length; i++) {
            if (SKILL_NAME[i].equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Gets total level.
     *
     * @return the total level
     */
    public int getTotalLevel() {
        int level = 0;
        for (int i = 0; i < 24; i++) {
            level += getStaticLevel(i);
        }
        return level;
    }

    /**
     * Gets total xp.
     *
     * @return the total xp
     */
    public int getTotalXp() {
        int total = 0;
        for (int skill = 0; skill < Skills.SKILL_NAME.length; skill++) {
            total += (int) this.getExperience(skill);
        }
        return total;
    }

    /**
     * Is lifepoints update boolean.
     *
     * @return the boolean
     */
    public boolean isLifepointsUpdate() {
        return lifepointsUpdate;
    }

    /**
     * Sets lifepoints update.
     *
     * @param lifepointsUpdate the lifepoints update
     */
    public void setLifepointsUpdate(boolean lifepointsUpdate) {
        this.lifepointsUpdate = lifepointsUpdate;
    }

    /**
     * Get static levels int [ ].
     *
     * @return the int [ ]
     */
    public int[] getStaticLevels() {
        return staticLevels;
    }

    /**
     * Has level boolean.
     *
     * @param skillId the skill id
     * @param i       the
     * @return the boolean
     */
    public boolean hasLevel(int skillId, int i) {
        return getStaticLevel(skillId) >= i;
    }

    /**
     * Gets combat milestone.
     *
     * @return the combat milestone
     */
    public int getCombatMilestone() {
        return combatMilestone;
    }

    /**
     * Sets combat milestone.
     *
     * @param combatMilestone the combat milestone
     */
    public void setCombatMilestone(int combatMilestone) {
        this.combatMilestone = combatMilestone;
    }

    /**
     * Gets skill milestone.
     *
     * @return the skill milestone
     */
    public int getSkillMilestone() {
        return skillMilestone;
    }

    /**
     * Sets skill milestone.
     *
     * @param skillMilestone the skill milestone
     */
    public void setSkillMilestone(int skillMilestone) {
        this.skillMilestone = skillMilestone;
    }

    /**
     * Get dynamic levels int [ ].
     *
     * @return the int [ ]
     */
    public int[] getDynamicLevels() {
        return dynamicLevels;
    }
}
