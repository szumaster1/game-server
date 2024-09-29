package content.global.skill.crafting.casting.gold

import content.global.skill.slayer.SlayerManager
import core.api.getStatLevel
import core.api.inInventory
import core.api.sendInputDialogue
import core.api.sendMessage
import core.cache.def.impl.ItemDefinition
import core.game.interaction.InterfaceListener
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.tools.StringUtils
import org.rs.consts.Components

/**
 * Jewellery interface listener.
 */
class JewelleryCraftingInterface : InterfaceListener {

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
            var data: Jewellery.JewelleryItem? = null

            when (buttonID) {
                20 -> data = Jewellery.JewelleryItem.GOLD_RING
                22 -> data = Jewellery.JewelleryItem.SAPPIRE_RING
                24 -> data = Jewellery.JewelleryItem.EMERALD_RING
                26 -> data = if (inInventory(player, PERFECT_GOLD_BAR)) Jewellery.JewelleryItem.PERFECT_RING else Jewellery.JewelleryItem.RUBY_RING
                28 -> data = Jewellery.JewelleryItem.DIAMOND_RING
                30 -> data = Jewellery.JewelleryItem.DRAGONSTONE_RING
                32 -> data = Jewellery.JewelleryItem.ONYX_RING
                35 -> data = Jewellery.JewelleryItem.SLAYER_RING
            }

            when (buttonID - 3) {
                39 -> data = Jewellery.JewelleryItem.GOLD_NECKLACE
                41 -> data = Jewellery.JewelleryItem.SAPPHIRE_NECKLACE
                43 -> data = Jewellery.JewelleryItem.EMERALD_NECKLACE
                45 -> data = if (inInventory(player, PERFECT_GOLD_BAR)) Jewellery.JewelleryItem.PERFECT_NECKLACE else Jewellery.JewelleryItem.RUBY_NECKLACE
                47 -> data = Jewellery.JewelleryItem.DIAMOND_NECKLACE
                49 -> data = Jewellery.JewelleryItem.DRAGONSTONE_NECKLACE
                51 -> data = Jewellery.JewelleryItem.ONYX_NECKLACE
                58 -> data = Jewellery.JewelleryItem.GOLD_AMULET
                60 -> data = Jewellery.JewelleryItem.SAPPHIRE_AMULET
                62 -> data = Jewellery.JewelleryItem.EMERALD_AMULET
                64 -> data = Jewellery.JewelleryItem.RUBY_AMULET
                66 -> data = Jewellery.JewelleryItem.DIAMOND_AMULET
                68 -> data = Jewellery.JewelleryItem.DRAGONSTONE_AMULET
                70 -> data = Jewellery.JewelleryItem.ONYX_AMULET
                77 -> data = Jewellery.JewelleryItem.GOLD_BRACELET
                79 -> data = Jewellery.JewelleryItem.SAPPHIRE_BRACELET
                81 -> data = Jewellery.JewelleryItem.EMERALD_BRACELET
                83 -> data = Jewellery.JewelleryItem.RUBY_BRACELET
                85 -> data = Jewellery.JewelleryItem.DIAMOND_BRACELET
                87 -> data = Jewellery.JewelleryItem.DRAGONSTONE_BRACELET
                89 -> data = Jewellery.JewelleryItem.ONYX_BRACELET
            }

            if (data == null) {
                return@on true
            }

            if (getStatLevel(player, Skills.CRAFTING) < data.level) {
                val an = if (StringUtils.isPlusN(ItemDefinition.forId(data.sendItem).name.lowercase())) "an" else "a"
                sendMessage(
                    player,
                    "You need a crafting level of " + data.level + " to craft " + an + " " + ItemDefinition.forId(data.sendItem).name.lowercase() + "."
                )
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
                124 -> amount =
                    if (itemID == GOLD_BAR) player.inventory.getAmount(Item(GOLD_BAR))
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
                    val d: Jewellery.JewelleryItem = data
                    sendInputDialogue(player, true, "Enter the amount:") { value: Any ->
                        Jewellery.make(player, d, value as Int)
                    }
                    return@on true
                }
            }

            if (!SlayerManager.getInstance(player).flags.isRingUnlocked() && data == Jewellery.JewelleryItem.SLAYER_RING) {
                player.sendMessages("You don't know how to make this. Talk to any Slayer master in order to learn the", "ability that creates Slayer rings.")
                return@on true
            }

            Jewellery.make(player, data, amount)
            return@on true
        }

    }
}
