package content.data.item

import cfg.consts.Items
import core.game.node.item.Item
import core.game.node.item.WeightedChanceItem
import core.tools.RandomFunction

/**
 * Represents the broken items data that can be repaired.
 */
object BrokenItem {

    /*
     * List of broken arrows.
     */

    private val brokenArrows: ArrayList<WeightedChanceItem?> = object : ArrayList<WeightedChanceItem?>() {
        init {
            add(WeightedChanceItem(Items.BRONZE_ARROW_882, 1, 40))
            add(WeightedChanceItem(Items.IRON_ARROW_884, 1, 30))
            add(WeightedChanceItem(Items.STEEL_ARROW_886, 1, 20))
            add(WeightedChanceItem(Items.MITHRIL_ARROW_888, 1, 10))
        }
    }

    /*
     * List of broken staves.
     */

    private val brokenStaves: ArrayList<WeightedChanceItem?> = object : ArrayList<WeightedChanceItem?>() {
        init {
            add(WeightedChanceItem(Items.STAFF_OF_AIR_1381, 1, 25))
            add(WeightedChanceItem(Items.STAFF_OF_WATER_1383, 1, 25))
            add(WeightedChanceItem(Items.STAFF_OF_EARTH_1385, 1, 25))
            add(WeightedChanceItem(Items.STAFF_OF_FIRE_1387, 1, 25))
        }
    }

    /*
     * List of rusty swords.
     */

    private val rustySwords: ArrayList<WeightedChanceItem?> = object : ArrayList<WeightedChanceItem?>() {
        init {
            add(WeightedChanceItem(Items.BRONZE_SWORD_1277, 1, 12))
            add(WeightedChanceItem(Items.BRONZE_LONGSWORD_1291, 1, 12))
            add(WeightedChanceItem(Items.IRON_SWORD_1279, 1, 11))
            add(WeightedChanceItem(Items.IRON_LONGSWORD_1293, 1, 11))
            add(WeightedChanceItem(Items.STEEL_SWORD_1281, 1, 10))
            add(WeightedChanceItem(Items.STEEL_LONGSWORD_1295, 1, 10))
            add(WeightedChanceItem(Items.BLACK_SWORD_1283, 1, 9))
            add(WeightedChanceItem(Items.BLACK_LONGSWORD_1297, 1, 9))
            add(WeightedChanceItem(Items.MITHRIL_SWORD_1285, 1, 8))
            add(WeightedChanceItem(Items.MITHRIL_LONGSWORD_1299, 1, 8))
        }
    }

    /*
     * List of rusty scimitars.
     */

    private val rustyScimitars: ArrayList<WeightedChanceItem?> = object : ArrayList<WeightedChanceItem?>() {
        init {
            add(WeightedChanceItem(Items.BRONZE_SCIMITAR_1321, 1, 12))
            add(WeightedChanceItem(Items.IRON_SCIMITAR_1323, 1, 11))
            add(WeightedChanceItem(Items.STEEL_SCIMITAR_1325, 1, 10))
            add(WeightedChanceItem(Items.BLACK_SCIMITAR_1327, 1, 9))
            add(WeightedChanceItem(Items.MITHRIL_SCIMITAR_1329, 1, 8))
        }
    }

    /*
     * List of damaged armour.
     */

    private val damagedArmour: ArrayList<WeightedChanceItem?> = object : ArrayList<WeightedChanceItem?>() {
        init {
            add(WeightedChanceItem(Items.BRONZE_PLATEBODY_1117, 1, 40))
            add(WeightedChanceItem(Items.IRON_PLATEBODY_1115, 1, 30))
            add(WeightedChanceItem(Items.STEEL_PLATEBODY_1119, 1, 15))
            add(WeightedChanceItem(Items.BLACK_PLATEBODY_1125, 1, 10))
            add(WeightedChanceItem(Items.MITHRIL_PLATEBODY_1121, 1, 5))
        }
    }

    /*
     * List of broken armour.
     */

    private val brokenArmour: ArrayList<WeightedChanceItem?> = object : ArrayList<WeightedChanceItem?>() {
        init {
            add(WeightedChanceItem(Items.BRONZE_PLATELEGS_1075, 1, 40))
            add(WeightedChanceItem(Items.IRON_PLATELEGS_1067, 1, 30))
            add(WeightedChanceItem(Items.STEEL_PLATELEGS_1069, 1, 15))
            add(WeightedChanceItem(Items.BLACK_PLATELEGS_1077, 1, 10))
            add(WeightedChanceItem(Items.MITHRIL_PLATELEGS_1071, 1, 5))
        }
    }

    /**
     * Returns a repaired item based on the specified equipment type.
     *
     * @param type The type of equipment to repair.
     * @return The repaired item.
     */
    fun getRepair(type: EquipmentType): Item {
        return when (type) {
            EquipmentType.ARROWS -> RandomFunction.rollWeightedChanceTable(brokenArrows)
            EquipmentType.STAVES -> RandomFunction.rollWeightedChanceTable(brokenStaves)
            EquipmentType.SWORDS -> RandomFunction.rollWeightedChanceTable(rustySwords)
            EquipmentType.SCIMITARS -> RandomFunction.rollWeightedChanceTable(rustyScimitars)
            EquipmentType.ARMOUR -> RandomFunction.rollWeightedChanceTable(damagedArmour)
            EquipmentType.LEGS -> RandomFunction.rollWeightedChanceTable(brokenArmour)
            else -> throw IllegalArgumentException("Invalid equipment type: $type.")
        }
    }

    /**
     * Representing the type of equipment that can be repaired.
     */
    enum class EquipmentType {
        ARROWS, STAVES, SWORDS, SCIMITARS, ARMOUR, LEGS
    }
}