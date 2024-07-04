package content.global.skill.production.crafting

import content.global.skill.production.crafting.data.JewelleryData.JewelleryItem
import content.global.skill.production.crafting.data.JewelleryData.make
import content.global.skill.support.slayer.SlayerManager.Companion.getInstance
import core.api.consts.Components
import core.api.inInventory
import core.api.sendInputDialogue
import core.cache.def.impl.ItemDefinition
import core.game.interaction.InterfaceListener
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.tools.StringUtils

class JewelleryInterfaceListener : InterfaceListener {

    companion object {
        const val RING_MOULD = 1592
        const val AMULET_MOULD = 1595
        const val NECKLACE_MOULD = 1597
        const val BRACELET_MOULD = 11065
        const val GOLD_BAR = 2357
        const val PERFECT_GOLD_BAR = 2365
        const val SAPPHIRE = 1607
        const val EMERALD = 1605
        const val RUBY = 1603
        const val DIAMOND = 1601
        const val DRAGONSTONE = 1615
        const val ONYX = 6573
    }

    override fun defineInterfaceListeners() {
        on(Components.CRAFTING_GOLD_446) { player, _, opcode, buttonID, _, itemID ->
            var amount = 0
            var data: JewelleryItem? = null

            when (buttonID) {
                20 -> data = JewelleryItem.GOLD_RING
                22 -> data = JewelleryItem.SAPPIRE_RING
                24 -> data = JewelleryItem.EMERALD_RING
                26 -> data = if (inInventory(player, PERFECT_GOLD_BAR)) JewelleryItem.PERFECT_RING else JewelleryItem.RUBY_RING
                28 -> data = JewelleryItem.DIAMOND_RING
                30 -> data = JewelleryItem.DRAGONSTONE_RING
                32 -> data = JewelleryItem.ONYX_RING
                35 -> data = JewelleryItem.SLAYER_RING
            }

            when (buttonID - 3) {
                39 -> data = JewelleryItem.GOLD_NECKLACE
                41 -> data = JewelleryItem.SAPPHIRE_NECKLACE
                43 -> data = JewelleryItem.EMERALD_NECKLACE
                45 -> data = if (inInventory(player, PERFECT_GOLD_BAR)) JewelleryItem.PERFECT_NECKLACE else JewelleryItem.RUBY_NECKLACE
                47 -> data = JewelleryItem.DIAMOND_NECKLACE
                49 -> data = JewelleryItem.DRAGONSTONE_NECKLACE
                51 -> data = JewelleryItem.ONYX_NECKLACE
                58 -> data = JewelleryItem.GOLD_AMULET
                60 -> data = JewelleryItem.SAPPHIRE_AMULET
                62 -> data = JewelleryItem.EMERALD_AMULET
                64 -> data = JewelleryItem.RUBY_AMULET
                66 -> data = JewelleryItem.DIAMOND_AMULET
                68 -> data = JewelleryItem.DRAGONSTONE_AMULET
                70 -> data = JewelleryItem.ONYX_AMULET
                77 -> data = JewelleryItem.GOLD_BRACELET
                79 -> data = JewelleryItem.SAPPHIRE_BRACELET
                81 -> data = JewelleryItem.EMERALD_BRACELET
                83 -> data = JewelleryItem.RUBY_BRACELET
                85 -> data = JewelleryItem.DIAMOND_BRACELET
                87 -> data = JewelleryItem.DRAGONSTONE_BRACELET
                89 -> data = JewelleryItem.ONYX_BRACELET
            }

            if (data == null) {
                return@on true
            }

            if (player.getSkills().getLevel(Skills.CRAFTING) < data.level) {
                val an = if (StringUtils.isPlusN(ItemDefinition.forId(data.sendItem).name.lowercase())) "an" else "a"
                player.packetDispatch.sendMessage("You need a crafting level of " + data.level + " to craft " + an + " " + ItemDefinition.forId(data.sendItem).name.lowercase() + ".")
                return@on true
            }

            val name = ItemDefinition.forId(data.sendItem).name.lowercase()
            var flag = false
            if (name.contains("ring") && !player.inventory.contains(RING_MOULD, 1)) {
                flag = true
            }

            if (name.contains("necklace") && !player.inventory.contains(NECKLACE_MOULD, 1)) {
                flag = true
            }

            if (name.contains("amulet") && !player.inventory.contains(AMULET_MOULD, 1)) {
                flag = true
            }

            if (name.contains("bracelet") && !player.inventory.contains(BRACELET_MOULD, 1)) {
                flag = true
            }

            if (flag) {
                player.packetDispatch.sendMessage("You don't have the required mould to make this.")
                return@on flag
            }

            when (opcode) {
                155 -> amount = 1
                196 -> amount = 5
                124 -> amount = if (itemID == GOLD_BAR) player.inventory.getAmount(Item(GOLD_BAR))
                else if (itemID == PERFECT_GOLD_BAR) player.inventory.getAmount(Item(PERFECT_GOLD_BAR))
                else {
                    val first = player.inventory.getAmount(Item(data.items[0]))
                    val second = player.inventory.getAmount(Item(data.items[1]))

                    if (first == second) {
                        first
                    } else if (first > second) {
                        second
                    } else {
                        first
                    }
                }

                199 -> {
                    val d: JewelleryItem = data
                    sendInputDialogue(player, true, "Enter the amount:") { value: Any ->
                        make(player, d, value as Int)
                    }
                    return@on true
                }
            }

            if (!getInstance(player).flags.isRingUnlocked() && data == JewelleryItem.SLAYER_RING) {
                player.sendMessages("You don't know how to make this. Talk to any Slayer master in order to learn the", "ability that creates Slayer rings.")
                return@on true
            }

            make(player, data, amount)
            return@on true
        }

    }
}
