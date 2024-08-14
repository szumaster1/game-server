package core.game.global.presets

import core.game.node.item.Item

/**
 * Preset class represents a preset configuration of equipment and inventory items.
 *
 * @param equipment The list of equipment items in the preset.
 * @param inventory The list of inventory items in the preset.
 * @constructor Represents a new Preset with the provided equipment and inventory items.
 */
class Preset(equipment: ArrayList<Item>, inventory: ArrayList<Item>) {

    var equipment: ArrayList<Item>? = null // Declaring a nullable ArrayList to store equipment items.
    var inventory: ArrayList<Item>? = null // Declaring a nullable ArrayList to store inventory items.

    init {
        this.equipment = equipment // Assigning the provided equipment items to the Preset.
        this.inventory = inventory // Assigning the provided inventory items to the Preset.
    }
}
