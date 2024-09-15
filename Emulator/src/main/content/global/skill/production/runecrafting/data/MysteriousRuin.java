package content.global.skill.production.runecrafting.data;

import core.game.node.scenery.Scenery;
import core.game.world.map.Location;

/**
 * Represents a mysterious ruin.
 *
 * @author Vexia
 */
public enum MysteriousRuin {
    /**
     * The air altar.
     */
    AIR(
        new int[]{2452, 7103, 7104},
        Location.create(2983, 3292, 0),
        Location.create(2841, 4829, 0),
        Talisman.AIR,
        Tiara.AIR
    ),

    /**
     * The mind altar.
     */
    MIND(
        new int[]{2453, 7105, 7106},
        Location.create(2980, 3514, 0),
        Location.create(2793, 4828, 0),
        Talisman.MIND,
        Tiara.MIND
    ),

    /**
     * The water altar.
     */
    WATER(
        new int[]{2454, 7107, 7108},
        Location.create(3183, 3163, 0),
        Location.create(3494, 4832, 0),
        Talisman.WATER,
        Tiara.WATER
    ),

    /**
     * The earth altar.
     */
    EARTH(
        new int[]{2455, 7109, 7110},
        Location.create(3304, 3475, 0),
        Location.create(2655, 4830, 0),
        Talisman.EARTH,
        Tiara.EARTH
    ),


    /**
     * The air altar.
     */
    FIRE(
        new int[]{2456, 7111, 7112},
        Location.create(3312, 3253, 0),
        Location.create(2576, 4845, 0),
        Talisman.FIRE,
        Tiara.FIRE
    ),

    /**
     * The body altar.
     */
    BODY(
        new int[]{2457, 7113, 7114},
        Location.create(3051, 3443, 0),
        Location.create(2521, 4834, 0),
        Talisman.BODY,
        Tiara.BODY
    ),

    /**
     * The cosmic altar.
     */
    COSMIC(
        new int[]{2458, 7115, 7116},
        Location.create(2407, 4375, 0),
        Location.create(2162, 4833, 0),
        Talisman.COSMIC,
        Tiara.COSMIC
    ),

    /**
     * The chaos altar.
     */
    CHAOS(
        new int[]{2461, 7121, 7122},
        Location.create(3059, 3589, 0),
        Location.create(2281, 4837, 0),
        Talisman.CHAOS,
        Tiara.CHAOS
    ),

    /**
     * The nature altar.
     */
    NATURE(
        new int[]{2460, 7119, 7120},
        Location.create(2869, 3021, 0),
        Location.create(2400, 4835, 0),
        Talisman.NATURE,
        Tiara.NATURE
    ),

    /**
     * The law altar.
     */
    LAW(
        new int[]{2459, 7117, 7118},
        Location.create(2857, 3379, 0),
        Location.create(2464, 4819, 0),
        Talisman.LAW,
        Tiara.LAW
    ),

    /**
     * The death altar.
     */
    DEATH(
        new int[]{2462, 7123, 7124},
        Location.create(1862, 4639, 0),
        Location.create(2208, 4830, 0),
        Talisman.DEATH,
        Tiara.DEATH
    ),

    /**
     * The blood altar.
     */
    BLOOD(
        new int[]{2464, 30529, 30530},
        Location.create(3561, 9779, 0),
        Location.create(2468, 4889, 1),
        Talisman.BLOOD,
        Tiara.BLOOD
    );

    /**
     * Represents the object id.
     */
    private final int[] object;

    /**
     * Represents the base location.
     */
    private final Location base;

    /**
     * Represents the end location.
     */
    private final Location end;

    /**
     * Represents the talisman.
     */
    private final Talisman talisman;

    /**
     * Represents the tiara.
     */
    private final Tiara tiara;

    /**
     * Constructs a new {@code MysteriousRuin} {@code Object}.
     *
     * @param object   the object.
     * @param base     the base.
     * @param end      the end.
     * @param talisman the talisman.
     * @param tiara    the tiara.
     */
    MysteriousRuin(int[] object, Location base, Location end, final Talisman talisman, final Tiara tiara) {
        this.object = object;
        this.base = base;
        this.end = end;
        this.talisman = talisman;
        this.tiara = tiara;
    }

    /**
     * Gets the object.
     *
     * @return The object.
     */
    public int[] getObject() {
        return object;
    }

    /**
     * Gets the base.
     *
     * @return The base.
     */
    public Location getBase() {
        return base;
    }

    /**
     * Gets the end.
     *
     * @return The end.
     */
    public Location getEnd() {
        return end;
    }

    /**
     * Gets the talisman.
     *
     * @return The talisman.
     */
    public Talisman getTalisman() {
        for (Talisman talisman : Talisman.values()) {
            if (talisman.name().equals(name())) {
                return talisman;
            }
        }
        return talisman;
    }

    /**
     * Gets the tiara.
     *
     * @return The tiara.
     */
    public Tiara getTiara() {
        for (Tiara tiara : Tiara.values()) {
            if (tiara.name().equals(name())) {
                return tiara;
            }
        }
        return tiara;
    }

    /**
     * Method used to get the <code>MysteriousRuin</code> by the object.
     *
     * @param object the object.
     * @return the <code>MysteriousRuin</code> or <code>Null</code>.
     */
    public static MysteriousRuin forObject(final Scenery object) {
        for (MysteriousRuin ruin : values()) {
            for (int i : ruin.getObject()) {
                if (i == object.getId()) {
                    return ruin;
                }
            }
        }
        return null;
    }

    /**
     * Method used to get the <code>MysteriousRuin</code>
     *
     * @param talisman the talisman.
     * @return the <code>MysteriousRuin</code>.
     */
    public static MysteriousRuin forTalisman(final Talisman talisman) {
        for (MysteriousRuin ruin : values()) {
            if (ruin.getTalisman() == talisman) {
                return ruin;
            }
        }
        return null;
    }

}
