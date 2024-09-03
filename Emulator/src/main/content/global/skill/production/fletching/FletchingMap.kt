package content.global.skill.production.fletching

import cfg.consts.Items
import core.game.node.item.Item

/**
 * Represents the fletching map.
 */
object FletchingMap {

    private val logMap = HashMap<Int, Array<FletchingItems>>()

    init {
        Item.values().forEach { item ->
            logMap[item.id] = item.items
        }
    }

    @JvmStatic
    fun getEntries(id: Int): Array<FletchingItems>? {
        return logMap[id]
    }

    @JvmStatic
    fun isLog(id: Int): Boolean {
        return logMap[id] != null
    }

    @JvmStatic
    fun getItems(id: Int): Array<core.game.node.item.Item?> {
        val entry = getEntries(id)
        var items = arrayOf<core.game.node.item.Item?>()
        when (entry!!.size) {
            1 -> items = arrayOf(Item(entry[0].id))
            2 -> items = arrayOf(Item(entry[0].id), Item(entry[1].id))

            3 -> items = arrayOf(Item(entry[0].id), Item(entry[1].id), Item(entry[2].id))

            4 -> items = arrayOf(Item(entry[0].id), Item(entry[1].id), Item(entry[2].id), Item(entry[3].id))
        }
        return items
    }

    private enum class Item {
        STANDARD(Items.LOGS_1511, FletchingItems.ARROW_SHAFT, FletchingItems.SHORT_BOW, FletchingItems.LONG_BOW, FletchingItems.WOODEN_STOCK),
        ACHEY(Items.ACHEY_TREE_LOGS_2862, FletchingItems.OGRE_ARROW_SHAFT, FletchingItems.OGRE_COMPOSITE_BOW),
        OAK(Items.OAK_LOGS_1521, FletchingItems.OAK_SHORTBOW, FletchingItems.OAK_LONGBOW, FletchingItems.OAK_STOCK),
        WILLOW(Items.WILLOW_LOGS_1519, FletchingItems.WILLOW_SHORTBOW, FletchingItems.WILLOW_LONGBOW, FletchingItems.WILLOW_STOCK),
        MAPLE(Items.MAPLE_LOGS_1517, FletchingItems.MAPLE_SHORTOW, FletchingItems.MAPLE_LONGBOW, FletchingItems.MAPLE_STOCK),
        YEW(Items.YEW_LOGS_1515, FletchingItems.YEW_SHORTBOW, FletchingItems.YEW_LONGBOW, FletchingItems.YEW_STOCK),
        MAGIC(Items.MAGIC_LOGS_1513, FletchingItems.MAGIC_SHORTBOW, FletchingItems.MAGIC_LONGBOW),
        TEAK(Items.TEAK_LOGS_6333, FletchingItems.TEAK_STOCK),
        MAHOGANY(Items.MAHOGANY_LOGS_6332, FletchingItems.MAHOGANY_STOCK);

        val items: Array<FletchingItems>

        var id: Int

        constructor(id: Int, firstSet: FletchingItems, secondSet: FletchingItems, thirdSet: FletchingItems, fourthSet: FletchingItems) {
            items = arrayOf(firstSet, secondSet, thirdSet, fourthSet)
            this.id = id
        }

        constructor(id: Int, firstSet: FletchingItems, secondSet: FletchingItems, thirdSet: FletchingItems) {
            items = arrayOf(firstSet, secondSet, thirdSet)
            this.id = id
        }

        constructor(id: Int, firstSet: FletchingItems, secondSet: FletchingItems) {
            items = arrayOf(firstSet, secondSet)
            this.id = id
        }

        constructor(id: Int, firstSet: FletchingItems) {
            items = arrayOf(firstSet)
            this.id = id
        }
    }

    enum class FletchingItems(var id: Int, var experience: Double, var level: Int, var amount: Int) {
        ARROW_SHAFT(Items.ARROW_SHAFT_52, 5.0, 1, 15),
        SHORT_BOW(Items.SHORTBOW_U_50, 5.0, 5, 1),
        LONG_BOW(Items.LONGBOW_U_48, 10.0, 10, 1),
        WOODEN_STOCK(Items.WOODEN_STOCK_9440, 6.0, 9, 1),

        OGRE_ARROW_SHAFT(Items.OGRE_ARROW_SHAFT_2864, 6.4, 5, 4),
        OGRE_COMPOSITE_BOW(Items.COMP_OGRE_BOW_4827, 40.0, 30, 1),

        OAK_SHORTBOW(Items.OAK_SHORTBOW_U_54, 16.5, 20, 1),
        OAK_LONGBOW(Items.OAK_LONGBOW_U_56, 25.0, 25, 1),
        OAK_STOCK(Items.OAK_STOCK_9442, 16.0, 24, 1),

        WILLOW_SHORTBOW(Items.WILLOW_SHORTBOW_U_60, 33.3, 35, 1),
        WILLOW_LONGBOW(Items.WILLOW_LONGBOW_U_58, 41.5, 40, 1),
        WILLOW_STOCK(Items.WILLOW_STOCK_9444, 22.0, 39, 1),

        MAPLE_SHORTOW(Items.MAPLE_SHORTBOW_U_64, 50.0, 50, 1),
        MAPLE_LONGBOW(Items.MAPLE_LONGBOW_U_62, 58.3, 55, 1),
        MAPLE_STOCK(Items.MAPLE_STOCK_9448, 32.0, 54, 1),

        YEW_SHORTBOW(Items.YEW_SHORTBOW_U_68, 67.5, 65, 1),
        YEW_LONGBOW(Items.YEW_LONGBOW_U_66, 75.0, 70, 1),
        YEW_STOCK(Items.YEW_STOCK_9452, 50.0, 69, 1),

        MAGIC_SHORTBOW(Items.MAGIC_SHORTBOW_U_72, 83.3, 80, 1),
        MAGIC_LONGBOW(Items.MAGIC_LONGBOW_U_70, 91.5, 85, 1),

        TEAK_STOCK(Items.TEAK_STOCK_9446, 27.0, 46, 1),

        MAHOGANY_STOCK(Items.MAHOGANY_STOCK_9450, 41.0, 61, 1);

        var logId: Int = 0
        val item: Int = id
    }
}
