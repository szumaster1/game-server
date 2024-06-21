package core.game.global.presets;

import core.game.node.item.Item;

import java.util.ArrayList;

/**
 * The type Preset.
 */
public class Preset {
	
	private ArrayList<Item> equipment;
	
	private ArrayList<Item> inventory;

    /**
     * Instantiates a new Preset.
     *
     * @param equipment the equipment
     * @param inventory the inventory
     */
    public Preset(ArrayList<Item> equipment, ArrayList<Item> inventory) {
		setEquipment(equipment);
		setInventory(inventory);
	}

    /**
     * Gets equipment.
     *
     * @return the equipment
     */
    public ArrayList<Item> getEquipment() {
		return equipment;
	}

    /**
     * Sets equipment.
     *
     * @param equipment the equipment
     */
    public void setEquipment(ArrayList<Item> equipment) {
		this.equipment = equipment;
	}

    /**
     * Gets inventory.
     *
     * @return the inventory
     */
    public ArrayList<Item> getInventory() {
		return inventory;
	}

    /**
     * Sets inventory.
     *
     * @param inventory the inventory
     */
    public void setInventory(ArrayList<Item> inventory) {
		this.inventory = inventory;
	}
}
