package core.game.global.presets

import core.game.node.item.Item

/**
 * Preset
 *
 * @constructor
 *
 * @param equipment
 * @param inventory
 */
class Preset(equipment: ArrayList<Item>, inventory: ArrayList<Item>) {

    var equipment: ArrayList<Item>? = null
    var inventory: ArrayList<Item>? = null

    init {
        this.equipment = equipment
        this.inventory = inventory
    }
}
