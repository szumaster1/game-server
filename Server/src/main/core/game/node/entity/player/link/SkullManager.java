package core.game.node.entity.player.link;

import core.game.container.Container;
import core.game.container.ContainerEvent;
import core.game.container.ContainerListener;
import core.game.node.entity.Entity;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;

import static core.api.ContentAPIKt.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents a managing class of the active player skulls.
 *
 * @author Vexia
 * @author Emperor
 */
public final class SkullManager {
    /**
     * The enum Skull icon.
     */
    public enum SkullIcon {
        /**
         * None skull icon.
         */
        NONE(-1),
        /**
         * White skull icon.
         */
        WHITE(0),
        /**
         * Red skull icon.
         */
        RED(1),
        /**
         * Bh red 5 skull icon.
         */
        BH_RED5(2),
        /**
         * Bh blue 4 skull icon.
         */
        BH_BLUE4(3),
        /**
         * Bh green 3 skull icon.
         */
        BH_GREEN3(4),
        /**
         * Bh grey 2 skull icon.
         */
        BH_GREY2(5),
        /**
         * Bh brown 1 skull icon.
         */
        BH_BROWN1(6),
        /**
         * Scream skull icon.
         */
        SCREAM(7);
        /**
         * The Id.
         */
        public final int id;
        SkullIcon(int id) {
            this.id = id;
        }

        /**
         * For id skull icon.
         *
         * @param id the id
         * @return the skull icon
         */
        public static SkullIcon forId(int id) {
            switch(id) {
                case 0: return SkullIcon.WHITE;
                case 1: return SkullIcon.RED;
                case 2: return SkullIcon.BH_RED5;
                case 3: return SkullIcon.BH_BLUE4;
                case 4: return SkullIcon.BH_GREEN3;
                case 5: return SkullIcon.BH_GREY2;
                case 6: return SkullIcon.BH_BROWN1;
                case 7: return SkullIcon.SCREAM;
                default: return SkullIcon.NONE;
            }
        }
    }


	/**
	 * The player instance.
	 */
	private final Player player;

	/**
	 * Represents if the player is in the wilderness.
	 */
	private boolean wilderness = false;

	/**
	 * If the wilderness zone is currently disabled.
	 */
	private boolean wildernessDisabled = false;

	/**
	 * Represents the current wilderness level.
	 */
	private int level;

	/**
	 * The players this player has skulled on.
	 */
	private final List<Player> skullCauses = new ArrayList<Player>();

	/**
	 * If the player is skulled.
	 */
	private boolean skulled;

	/**
	 * If the skull check is disabled.
	 */
	private boolean skullCheckDisabled;

        private boolean deepWilderness;

    /**
     * Constructs a new {@code SkullManager} {@code Object}.
     *
     * @param player the player.
     */
    public SkullManager(Player player) {
		this.player = player;
	}

    /**
     * Checks if we should skull on this entity.
     *
     * @param other The entity to check.
     */
    public void checkSkull(Entity other) {
		if (!(other instanceof Player) || !wilderness || skullCheckDisabled) {
			return;
		}
		Player o = (Player) other;
		for (Player p : o.getSkullManager().skullCauses) {
			if (p == player) {
				return;
			}
		}
		if (skullCauses.contains(o)) {
			return;
		}
		skullCauses.add(o);
                removeTimer (player, "skulled");
                registerTimer (player, spawnTimer("skulled", 2000));
	}

    /**
     * Sets the skull icon.
     *
     * @param skullIcon The skull icon.
     */
    public void setSkullIcon(int skullIcon) {
		player.getAppearance().setSkullIcon(skullIcon);
		player.updateAppearance();
	}

    /**
     * Resets the skull causes cache.
     */
    public void reset() {
		skullCauses.clear();
		setSkullIcon(-1);
		setSkulled(false);
                player.getAppearance().sync();
	}

    /**
     * Gets the player.
     *
     * @return the player.
     */
    public Player getPlayer() {
		return player;
	}

    /**
     * Gets the level.
     *
     * @return the level.
     */
    public int getLevel() {
		return level;
	}

    /**
     * Sets the level.
     *
     * @param level the level to set.
     */
    public void setLevel(int level) {
                if (!deepWilderness && level >= 49)
                    setDeepWilderness(true);
                else if (deepWilderness && level < 48)
                    setDeepWilderness(false);

                if (level > 20)
                    player.getLocks().lockTeleport (1_000_000);
                else
                    player.getLocks().unlockTeleport();

		this.level = level;
	}

    /**
     * Gets the value.
     *
     * @return the wilderness.
     */
    public boolean isWilderness() {
		return wilderness;
	}

    /**
     * Sets the value of this boolean.
     *
     * @param wilderness the wilderness to set.
     */
    public void setWilderness(boolean wilderness) {
		this.wilderness = wilderness;
	}

    /**
     * Gets the skullCheckDisabled.
     *
     * @return The skullCheckDisabled.
     */
    public boolean isSkullCheckDisabled() {
		return skullCheckDisabled;
	}

    /**
     * Sets the skullCheckDisabled.
     *
     * @param skullCheckDisabled The skullCheckDisabled to set.
     */
    public void setSkullCheckDisabled(boolean skullCheckDisabled) {
		this.skullCheckDisabled = skullCheckDisabled;
	}

    /**
     * Gets the wildernessDisabled.
     *
     * @return The wildernessDisabled.
     */
    public boolean isWildernessDisabled() {
		return wildernessDisabled;
	}

    /**
     * Has wilderness protection boolean.
     *
     * @return the boolean
     */
    public boolean hasWildernessProtection() {
                return level < 49;
        }

    /**
     * Sets the wildernessDisabled.
     *
     * @param wildernessDisabled The wildernessDisabled to set.
     */
    public void setWildernessDisabled(boolean wildernessDisabled) {
		this.wildernessDisabled = wildernessDisabled;
	}

    /**
     * Gets the skulled.
     *
     * @return The skulled.
     */
    public boolean isSkulled() {
		return skulled || deepWilderness;
	}

    /**
     * Is deep wilderness boolean.
     *
     * @return the boolean
     */
    public boolean isDeepWilderness() {
            return deepWilderness;
    }

    /**
     * Sets deep wilderness.
     *
     * @param deepWildy the deep wildy
     */
    public void setDeepWilderness (boolean deepWildy) {
            if(deepWildy) {
                updateDWSkullIcon();
            } else {
                removeDWSkullIcon();
            }
            setSkullCheckDisabled(deepWildy);
            deepWilderness = deepWildy;
    }

    /**
     * The constant DEEP_WILD_DROP_RISK_THRESHOLD.
     */
    public static final long DEEP_WILD_DROP_RISK_THRESHOLD = 100000;

    /**
     * Update dw skull icon.
     */
    public void updateDWSkullIcon() {
        if (player.getAttribute("deepwild-value-listener") == null) {
            ContainerListener listener = new ContainerListener() {
                @Override
                public void update(Container c, ContainerEvent event) {
                    refresh(c);
                }

                @Override
                public void refresh(Container c) {
                    updateDWSkullIcon();
                }
            };
            player.setAttribute("deepwild-value-listener", listener);
            player.getInventory().getListeners().add(listener);
            player.getEquipment().getListeners().add(listener);
        }
        long value = 0;
        long maxValue = 0;
        for (Item item : player.getInventory().toArray()) {
            if (item != null) {
                long alchValue = item.getAlchemyValue();
                value += alchValue;
                maxValue = Math.max(maxValue, alchValue);
            }
        }
        for (Item item : player.getEquipment().toArray()) {
            if (item != null) {
                long alchValue = item.getAlchemyValue();
                value += alchValue;
                maxValue = Math.max(maxValue, alchValue);
            }
        }
        // Act as if protect item is always active when calculating risk
        value -= maxValue;
        player.setAttribute("deepwild-value-risk", value);
        SkullIcon skull = SkullIcon.BH_BROWN1;
        if (value >= DEEP_WILD_DROP_RISK_THRESHOLD) {
            skull = SkullIcon.RED;
        }
        setSkullIcon(skull.id);
    }

    /**
     * Remove dw skull icon.
     */
    public void removeDWSkullIcon() {
        setSkullIcon(skulled ? 0 : -1);
        ContainerListener listener = player.getAttribute("deepwild-value-listener");
        if (listener != null) {
            player.getInventory().getListeners().remove(listener);
            player.getEquipment().getListeners().remove(listener);
        }
        player.removeAttribute("deepwild-value-listener");
        player.removeAttribute("deepwild-value-risk");
    }

    /**
     * Sets the skulled.
     *
     * @param skulled The skulled to set.
     */
    public void setSkulled(boolean skulled) {
		this.skulled = skulled;
	}
}
