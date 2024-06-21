package core.game.global.presets;

import core.game.node.entity.player.Player;
import core.game.node.item.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Preset manager.
 */
public class PresetManager {

	private Player player;

	private final Map<String, Preset> current_presets;

    /**
     * Instantiates a new Preset manager.
     */
    public PresetManager() {
		current_presets = new HashMap<>();
	}

    /**
     * Store set preset manager.
     *
     * @param name_key the name key
     * @return the preset manager
     */
    public PresetManager storeSet(String name_key) {
		final Preset set = current_presets.get(name_key);
		if (set != null) {
			player.sendMessage("You were unable to store the set " + name_key + " as it already exists.");
		}
		name_key = name_key.toLowerCase();
		ArrayList<Item> equipment = new ArrayList<Item>(Arrays.asList(player.getEquipment().getEvent().getItems())), inventory = new ArrayList<Item>(Arrays.asList(player.getInventory().getEvent().getItems()));
		current_presets.put(name_key, new Preset(equipment, inventory));
		return this;
	}

    /**
     * Print available setups.
     */
    public void printAvailableSetups() {
		final int size = current_presets.size();
		player.sendMessage("You have used "+size+"/"+maxSize()+" available setups.");
		if (size > 0) {
			player.sendMessage("<col=ff0000>Your available setups are:");
			for (final String key : current_presets.keySet()) {
				player.sendMessage(key);
			}
		}
	}

    /**
     * Max size int.
     *
     * @return the int
     */
    public int maxSize() {
		return 6;
	}
}
