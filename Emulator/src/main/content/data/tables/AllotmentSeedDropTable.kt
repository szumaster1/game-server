package content.data.tables

import core.Configuration
import core.api.StartupListener
import core.api.log
import core.api.shouldRemoveNothings
import core.api.utils.WeightBasedTable
import core.api.utils.WeightedItem
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.tools.Log
import core.tools.RandomFunction
import org.rs.consts.Items
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.io.File
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

/**
 * Allotment seed drop table.
 * @author Von Hresvelg
 */
class AllotmentSeedDropTable : StartupListener {

    override fun startup() {
        // Check if the ASDT data path is provided and the file exists
        if (Configuration.ASDT_DATA_PATH != null && !File(Configuration.ASDT_DATA_PATH).exists()) {
            log(this.javaClass, Log.ERR, "Can't locate ASDT file at " + Configuration.ASDT_DATA_PATH)
            return
        }
        // Parse the ASDT data file
        parse(Configuration.ASDT_DATA_PATH)
        // Log the initialization of the drop table
        log(this.javaClass, Log.FINE, "Initialized Allotment Seed Drop Table from " + Configuration.ASDT_DATA_PATH)
    }

    companion object {
        // Create a weight-based table for the drop items
        private val TABLE: WeightBasedTable = object : WeightBasedTable() {
            override fun roll(receiver: Entity?): ArrayList<Item> {
                val items = ArrayList(guaranteedItems)
                var effectiveWeight = totalWeight
                val p = if (receiver is Player) receiver else null
                // Check if the player should remove "nothing" items from the drop table
                if (p != null && shouldRemoveNothings(p))
                    effectiveWeight -= nothingWeight

                if (this.size == 1) {
                    items.add(get(0))
                } else if (!this.isEmpty()) {
                    var rngWeight = RandomFunction.randomDouble(effectiveWeight)
                    for (item in this.shuffled()) {
                        // Skip the "DWARF_REMAINS_0" item
                        if (item.id == Items.DWARF_REMAINS_0) continue
                        rngWeight -= item.weight
                        if (rngWeight <= 0) {
                            items.add(item)
                            break
                        }
                    }
                }
                return convertWeightedItems(items, receiver)
            }

            // Calculate the total weight of "nothing" items in the drop table
            private val nothingWeight: Double
                get() {
                    var sum = 0.0
                    for (i in this) {
                        if (i.id == Items.DWARF_REMAINS_0)
                            sum += i.weight
                    }
                    return sum
                }
        }

        // Create a DocumentBuilderFactory and DocumentBuilder for XML parsing
        var factory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
        var builder: DocumentBuilder? = null

        init {
            try {
                builder = factory.newDocumentBuilder()
            } catch (e: ParserConfigurationException) {
                e.printStackTrace()
            }
        }

        // Parse the ASDT data file and add items to the drop table
        fun parse(file: String?) {
            try {
                val doc = builder!!.parse(file)
                val itemNodes = doc.getElementsByTagName("item")
                for (i in 0 until itemNodes.length) {
                    val itemNode = itemNodes.item(i)
                    if (itemNode.nodeType == Node.ELEMENT_NODE) {
                        val item = itemNode as Element
                        val itemId = item.getAttribute("id").toInt()
                        val minAmt = item.getAttribute("minAmt").toInt()
                        val maxAmt = item.getAttribute("maxAmt").toInt()
                        val weight = item.getAttribute("weight").toInt()
                        TABLE.add(WeightedItem(itemId, minAmt, maxAmt, weight.toDouble(), false))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Retrieve a random item from the drop table for the given receiver
        fun retrieve(receiver: Entity?): Item? {
            return TABLE.roll(receiver).getOrNull(0)
        }
    }
}
