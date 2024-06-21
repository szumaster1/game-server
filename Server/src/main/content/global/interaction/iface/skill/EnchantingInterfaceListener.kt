package content.global.interaction.iface.skill

import core.api.*
import core.api.consts.Components
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.interaction.InterfaceListener
import core.game.node.item.Item
import core.utilities.StringUtils

class EnchantingInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        onOpen(Components.STAFF_ENCHANT_332) { player, _ ->
            for (staff in EnchantedStaff.values()) sendItemZoomOnInterface(player, Components.STAFF_ENCHANT_332, staff.child, staff.basic)
            return@onOpen true
        }

        on(Components.STAFF_ENCHANT_332) { player, _, opcode, buttonID, slot, itemID ->
            val price = 40000
            val discountPrice = 27000
            val headbandInEquipment = inEquipment(player, Items.SEERS_HEADBAND_14631)
            val completeDiary = if (!headbandInEquipment) price else discountPrice

            sendMessage(player, "op: $opcode button: $buttonID slot: $slot item: $itemID")

            if (EnchantedStaff.childToBasic.containsKey(buttonID)) {
                val basicStaff = Item(EnchantedStaff.childToBasic[buttonID]!!)
                val enchantedStaff = Item(EnchantedStaff.basicToEnchanted[basicStaff.id]!!)
                if (!inInventory(player, basicStaff.id)) {
                    sendMessage(player, "You don't have a" + (if (StringUtils.isPlusN(basicStaff.name)) "n " else " ") + basicStaff.name + " to enchant.")
                    return@on true
                }

                if (!inInventory(player, Items.COINS_995, completeDiary)) {
                    closeInterface(player)
                    sendNPCDialogue(player, NPCs.THORMAC_389,"I need $completeDiary coins for materials. Come back when you have the money!")
                    return@on true
                }

                if (player.inventory.remove(basicStaff, Item(Items.COINS_995, completeDiary))) {
                    closeInterface(player)
                    sendNPCDialogue(player, NPCs.THORMAC_389,"Just a moment... hang on... hocus pocus abra- cadabra... there you go! Enjoy your enchanted staff!")
                    replaceSlot(player, basicStaff.slot, enchantedStaff)
                }
            }
            return@on true
        }

    }

    enum class EnchantedStaff(val enchanted: Int, val basic: Int, val child: Int) {
        AIR(Items.MYSTIC_AIR_STAFF_1405, Items.AIR_BATTLESTAFF_1397, 21),
        WATER(Items.MYSTIC_WATER_STAFF_1403, Items.WATER_BATTLESTAFF_1395, 22),
        EARTH(Items.MYSTIC_EARTH_STAFF_1407, Items.EARTH_BATTLESTAFF_1399, 23),
        FIRE(Items.MYSTIC_FIRE_STAFF_1401, Items.FIRE_BATTLESTAFF_1393, 24),
        LAVA(Items.MYSTIC_LAVA_STAFF_3054, Items.LAVA_BATTLESTAFF_3053, 25),
        MUD(Items.MYSTIC_MUD_STAFF_6563, Items.MUD_BATTLESTAFF_6562, 26),
        STEAM(Items.MYSTIC_STEAM_STAFF_11738, Items.STEAM_BATTLESTAFF_11736, 27);

        companion object {
            val basicToEnchanted = HashMap<Int, Int>()
            val childToBasic = HashMap<Int, Int>()

            init {
                for (staff in values()) {
                    basicToEnchanted[staff.basic] = staff.enchanted
                    childToBasic[staff.child] = staff.basic
                }
            }
        }

    }
}
