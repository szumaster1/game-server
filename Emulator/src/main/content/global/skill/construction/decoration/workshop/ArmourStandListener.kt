package content.global.skill.construction.decoration.workshop

import content.data.items.BrokenItem
import content.data.items.BrokenItem.getRepair
import content.data.items.RepairItem
import content.region.misthalin.lumbridge.dialogue.BobDialogue.BarrowsEquipment
import content.region.misthalin.lumbridge.dialogue.BobDialogue.BarrowsEquipment.BarrowsFullEquipment
import core.api.*
import core.game.dialogue.Dialogue
import core.game.dialogue.DialogueInterpreter
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.plugin.Initializable
import org.rs.consts.Items
import kotlin.math.ceil

/**
 * Handles the interactions of the armour stand in the workshop.
 */
class ArmourStandListener : InteractionListener {

    private val itemIDs = intArrayOf(
        494, 468, 474, 476, 478, 470, 472, 496, 498, 500, 502, 504, 506, 686, 687, 697, 698, 689,
        4856, 4857, 4858, 4859, 4860, 4862, 4863, 4864, 4865, 4866, 4868, 4869, 4870, 4871,
        4872, 4874, 4875, 4876, 4877, 4878, 4880, 4881, 4882, 4883, 4884, 4886, 4887, 4888,
        4889, 4890, 4892, 4893, 4894, 4895, 4896, 4898, 4899, 4900, 4901, 4902, 4904, 4905,
        4906, 4907, 4908, 4910, 4911, 4912, 4913, 4914, 4916, 4917, 4918, 4919, 4920, 4922,
        4923, 4924, 4925, 4926, 4928, 4929, 4930, 4931, 4932, 4934, 4935, 4936, 4937, 4938,
        4940, 4941, 4942, 4943, 4944, 4946, 4947, 4948, 4949, 4950, 4952, 4953, 4954, 4955,
        4956, 4958, 4959, 4960, 4961, 4962, 4964, 4965, 4966, 4967, 4968, 4970, 4971, 4972,
        4973, 4974, 4976, 4977, 4978, 4979, 4980, 4982, 4983, 4984, 4985, 4986, 4988, 4989,
        4990, 4991, 4992, 4994, 4995, 4996, 4997, 4998, 6741
    )

    private val idMap = itemIDs.associateWith { 0 }

    override fun defineListeners() {
        idMap[0]?.let { id ->
            onUseWith(IntType.SCENERY, id, 13715) { player, used, _ ->
                handle(player, used.asItem())
                return@onUseWith true
            }
        }

    }

    private fun handle(player: Player, used: Item) {
        val repairItem = RepairItem.forId(used.id)
        val brokenItems = intArrayOf(686, 687, 697, 698, 689)

        if (player.inventory.containsAtLeastOneItem(brokenItems)) {
            animate(player, 3654)
            repairBrokenItem(player, used)
            return
        }

        val (repairCost, product) = calculateRepairCost(used, repairItem)

        if (repairCost == 0.0) {
            player.sendMessage("That item can't be repaired.")
            return
        }

        val cost = calculateCost(player, repairCost)
        player.dialogueInterpreter.open("con:armour-stand", used, cost, product)
    }

    private fun calculateRepairCost(used: Item, repairItem: RepairItem?): Pair<Double, Item?> {
        var baseCost = repairItem?.cost?.toDouble() ?: 0.0
        var product: Item? = repairItem?.product

        if (product == null && BarrowsEquipment.isBarrowsItem(used.id)) {
            val type = BarrowsEquipment.formattedName(used.id)
            val equipment = BarrowsEquipment.getEquipmentType(type)
            val formatName = type.lowercase().replace(BarrowsEquipment.getSingleName(type), "").trim().replace("'s", "")
            val fullEquipment = BarrowsFullEquipment.forName("$formatName $equipment")
            baseCost = BarrowsEquipment.getFormattedCost(equipment, used).toDouble()
            product = fullEquipment.full
        }

        return Pair(baseCost, product)
    }

    private fun calculateCost(player: Player, baseCost: Double): Int {
        return ceil(((100.0 - (player.skills.getLevel(Skills.SMITHING) / 2.0)) / 100.0) * baseCost).toInt()
    }

    private fun repairBrokenItem(player: Player, item: Item) {
        val repairMap = mapOf(
            Items.BROKEN_ARROW_687 to BrokenItem.EquipmentType.ARROWS,
            Items.BROKEN_STAFF_689 to BrokenItem.EquipmentType.STAVES,
            Items.RUSTY_SWORD_686 to BrokenItem.EquipmentType.SWORDS,
            Items.DAMAGED_ARMOUR_697 to BrokenItem.EquipmentType.ARMOUR,
            Items.BROKEN_ARMOUR_698 to BrokenItem.EquipmentType.LEGS
        )

        repairMap[item.id]?.let { type ->
            val product = getRepair(type)
            removeItem(player, item)
            addItem(player, product.id)
            sendItemDialogue(player, product.id, "You repair the item and find it is a ${getItemName(product.id)}.")
        }
    }

    /**
     * Represents the Repair dialogue.
     */
    @Initializable
    class RepairDialogue(player: Player? = null) : Dialogue(player) {
        /**
         * the base item.
         */
        private lateinit var item: Item

        /**
         * the cost of repair.
         */
        private var cost: Int = 0

        /**
         * the repaired item.
         */
        private lateinit var product: Item

        override fun open(vararg args: Any?): Boolean {
            item = args[0] as Item
            cost = args[1] as Int
            product = args[2] as Item
            sendDialogue("Would you like to repair your ${getItemName(item.id)} for $cost gp?")
            stage = 0
            return true
        }

        override fun handle(interfaceId: Int, buttonId: Int): Boolean {
            if (!::item.isInitialized || !::product.isInitialized) return false
            when (stage) {
                0 -> {
                    options("Yes, please", "No, thanks.")
                    stage++
                }

                1 -> {
                    if (buttonId == 1) {
                        repairItem(item, cost, product)
                        end()
                    } else if (buttonId == 2) {
                        end()
                    }
                }
            }
            return true
        }

        /**
         * Handles the repair item.
         *
         * @param item    the item to be repaired.
         * @param cost    the cost of the repair.
         * @param product the item that will be produced after repair.
         */
        private fun repairItem(item: Item, cost: Int, product: Item) {
            val coins = Item(Items.COINS_995, cost)
            if (player.inventory.containsItem(coins) && player.inventory.containsItem(item)) {
                player.inventory.remove(item, coins)
                player.inventory.add(product)
                sendDialogue(player, "You repair your ${getItemName(product.id)} for $cost coins.")
            } else {
                sendDialogue(player, "You can't afford that.")
            }
        }

        override fun newInstance(player: Player?): Dialogue {
            return RepairDialogue(player)
        }

        override fun getIds(): IntArray {
            return intArrayOf(DialogueInterpreter.getDialogueKey("con:armour-stand"))
        }
    }
}
