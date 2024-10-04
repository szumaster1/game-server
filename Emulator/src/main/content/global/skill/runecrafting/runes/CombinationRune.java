package content.global.skill.runecrafting.runes;

import content.global.skill.runecrafting.object.Altar;
import content.global.skill.runecrafting.items.Talisman;
import core.game.node.item.Item;

public enum CombinationRune {
    MIST(new Item(4695), 6, 8.0, new Altar[]{Altar.WATER, Altar.AIR}, Rune.AIR, Rune.WATER),
    DUST(new Item(4696), 10, 8.3, new Altar[]{Altar.EARTH, Altar.AIR}, Rune.AIR, Rune.EARTH),
    MUD(new Item(4698), 13, 9.3, new Altar[]{Altar.EARTH, Altar.WATER}, Rune.WATER, Rune.EARTH),
    SMOKE(new Item(4697), 15, 8.5, new Altar[]{Altar.FIRE, Altar.AIR}, Rune.AIR, Rune.FIRE),
    STEAM(new Item(4694), 19, 9.3, new Altar[]{Altar.WATER, Altar.FIRE}, Rune.WATER, Rune.FIRE),
    LAVA(new Item(4699), 23, 10.0, new Altar[]{Altar.FIRE, Altar.EARTH}, Rune.EARTH, Rune.FIRE);

    private final Item rune;

    private final int level;

    private final double experience;

    private final Altar[] altars;

    private final Rune[] runes;

    CombinationRune(final Item rune, final int level, final double experience, final Altar[] altars, final Rune... runes) {
        this.rune = rune;
        this.level = level;
        this.experience = experience;
        this.altars = altars;
        this.runes = runes;
    }

    public final Item getRune() {
        return rune;
    }

    public final int getLevel() {
        return level;
    }

    public final double getExperience() {
        return experience;
    }

    public final Rune[] getRunes() {
        return runes;
    }

    public final double getHighExperience() {
        return (experience % 1 == 0) ? experience + 5 : experience + 8;
    }

    public Altar[] getAltars() {
        return altars;
    }

    public static CombinationRune forAltar(final Altar altar, final Item item) {
        for (CombinationRune rune : values()) {
            for (Altar alt : rune.getAltars()) {
                if (alt == altar) {
                    String altarElement = alt.name();
                    String talismanElement = item.getName().contains("talisman") ? Talisman.forItem(item).name() : Rune.forItem(item).name();
                    if (altarElement.equals(talismanElement)) {
                        continue;
                    }
                    for (Rune r : rune.getRunes()) {
                        if (r.name().equals(talismanElement)) {
                            return rune;
                        }
                    }
                    continue;
                }
            }
        }
        return null;
    }
}
