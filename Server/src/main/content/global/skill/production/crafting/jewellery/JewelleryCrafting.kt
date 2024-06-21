package content.global.skill.production.crafting.jewellery

import core.cache.def.impl.ItemDefinition
import core.game.component.Component
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

object JewelleryCrafting {

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

    @JvmStatic
    fun open(player: Player) {
        player.interfaceManager.open(Component(446))
        if (player.inventory.contains(RING_MOULD, 1)) {
            player.packetDispatch.sendInterfaceConfig(446, 14, true)
        }
        if (player.inventory.contains(NECKLACE_MOULD, 1)) {
            player.packetDispatch.sendInterfaceConfig(446, 36, true)
        }
        if (player.inventory.contains(AMULET_MOULD, 1)) {
            player.packetDispatch.sendInterfaceConfig(446, 55, true)
        }
        if (player.inventory.contains(BRACELET_MOULD, 1)) {
            player.packetDispatch.sendInterfaceConfig(446, 74, true)
        }
        for (data in JewelleryItem.values()) {
            var length = 0
            for (i in data.items.indices) {
                if (player.inventory.contains(data.items[i], 1)) {
                    length++
                }
            }
            if (!player.inventory.contains(mouldFor(data.name), 1)) {
                length--
            }
            if (length == data.items.size && player.getSkills().getLevel(Skills.CRAFTING) >= data.level) {
                player.packetDispatch.sendItemZoomOnInterface(data.sendItem, 170, 446, data.componentId)
            } else {
                val name = ItemDefinition.forId(data.sendItem).name.lowercase()
                if (name.contains("amulet") && player.inventory.contains(mouldFor(data.name), 1)) {
                    for (i in data.items.indices) {
                        if (!player.inventory.contains(data.items[i], 1)) {
                            player.packetDispatch.sendItemZoomOnInterface(1685, 220, 446, data.componentId)
                            break
                        }
                    }
                }
                if (name.contains("ring") && !player.inventory.contains(RING_MOULD, 1)) {
                    continue
                }
                if (name.contains("necklace") && !player.inventory.contains(NECKLACE_MOULD, 1)) {
                    continue
                }
                if (data == JewelleryItem.DRAGONSTONE_AMULET && !player.inventory.contains(AMULET_MOULD, 1)) {
                    continue
                }
                if (name.contains("amulet") && !player.inventory.contains(AMULET_MOULD, 1)) {
                    continue
                }
                if (name.contains("bracelet") && !player.inventory.contains(BRACELET_MOULD, 1)) {
                    continue
                }
                player.packetDispatch.sendItemZoomOnInterface(
                    if (name.contains("ring")) 1647 else
                        if (name.contains("necklace")) 1666 else
                            if (name.contains("amulet") || name.contains("ammy")) 1685 else
                                if (name.contains("bracelet")) 11067 else -1, 1, 446, data.componentId
                )
            }
        }
    }

    @JvmStatic
    fun make(player: Player, data: JewelleryItem, amount: Int) {
        var amount = amount
        var length = 0
        var amt = 0
        amt = if (data.items.contains(GOLD_BAR))
            player.inventory.getAmount(Item(GOLD_BAR))
        else if (data.items.contains(PERFECT_GOLD_BAR)) {
            player.inventory.getAmount(Item(PERFECT_GOLD_BAR))
        } else {
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
        if (amount > amt) {
            amount = amt
        }
        for (i in data.items.indices) {
            if (player.inventory.contains(data.items[i], amount)) {
                length++
            }
        }
        if (length != data.items.size) {
            player.packetDispatch.sendMessage("You don't have the required items to make this item.")
            return
        }
        if (player.getSkills().getLevel(Skills.CRAFTING) < data.level) {
            player.packetDispatch.sendMessage("You need a crafting level of " + data.level + " to craft this.")
            return
        }
        val items = arrayOfNulls<Item>(data.items.size)
        for ((index, i) in data.items.indices.withIndex()) {
            items[index] = Item(data.items[i], 1 * amount)
        }
        player.interfaceManager.close()
        player.pulseManager.run(JewelleryPulse(player, null, data, amount))
    }

    private fun mouldFor(name: String): Int {
        var name = name
        name = name.lowercase()
        if (name.contains("ring")) {
            return RING_MOULD
        }
        if (name.contains("necklace")) {
            return NECKLACE_MOULD
        }
        if (name.contains("amulet")) {
            return AMULET_MOULD
        }
        return if (name.contains("bracelet")) {
            BRACELET_MOULD
        } else -1
    }

    enum class JewelleryItem(
        val level: Int,
        experience: Int,
        val componentId: Int,
        val sendItem: Int,
        vararg val items: Int
    ) {
        GOLD_RING(5, 15, 19, 1635, GOLD_BAR),
        SAPPIRE_RING(20, 40, 21, 1637, SAPPHIRE, GOLD_BAR),
        EMERALD_RING(27, 55, 23, 1639, EMERALD, GOLD_BAR),
        RUBY_RING(34, 70, 25, 1641, 1603, GOLD_BAR),
        PERFECT_RING(40, 75, 26, 773, 2365, RUBY),
        DIAMOND_RING(43, 85, 27, 1643, DIAMOND, GOLD_BAR),
        DRAGONSTONE_RING(55, 100, 29, 1645, DRAGONSTONE, GOLD_BAR),
        ONYX_RING(67, 115, 31, 6575, 6573, GOLD_BAR),

        GOLD_NECKLACE(6, 20, 41, 1654, 2357),
        SAPPHIRE_NECKLACE(22, 55, 43, 1656, 1607, 2357),
        EMERALD_NECKLACE(29, 60, 45, 1658, 1605, 2357),
        RUBY_NECKLACE(40, 75, 47, 1660, 1603, 2357),
        PERFECT_NECKLACE(40, 75, 48, 774, 2365, RUBY),
        DIAMOND_NECKLACE(56, 90, 49, 1662, 1601, 2357),
        DRAGONSTONE_NECKLACE(72, 105, 51, 1664, 1615, 2357),
        ONYX_NECKLACE(82, 120, 53, 6577, 6573, 2357),
        SLAYER_RING(75, 15, 34, 13281, 4155, GOLD_BAR),

        GOLD_AMULET(8, 30, 60, 1673, 2357),
        SAPPHIRE_AMULET(24, 63, 62, 1675, 1607, 2357),
        EMERALD_AMULET(31, 70, 64, 1677, 1605, 2357),
        RUBY_AMULET(50, 85, 66, 1679, 1603, 2357),
        DIAMOND_AMULET(70, 100, 68, 1681, 1601, 2357),
        DRAGONSTONE_AMULET(80, 150, 70, 1683, 1615, 2357),
        ONYX_AMULET(90, 165, 72, 6579, 6573, 2357),

        GOLD_BRACELET(7, 25, 79, 11069, 2357),
        SAPPHIRE_BRACELET(23, 60, 81, 11072, 1607, 2357),
        EMERALD_BRACELET(30, 65, 83, 11076, 1605, 2357),
        RUBY_BRACELET(42, 80, 85, 11085, 1603, 2357),
        DIAMOND_BRACELET(58, 95, 87, 11092, 1601, 2357),
        DRAGONSTONE_BRACELET(74, 110, 89, 11115, 1615, 2357),
        ONYX_BRACELET(84, 125, 91, 11130, 6573, 2357);

        val experience: Double

        init {
            this.experience = experience.toDouble()
        }

        companion object {
            var productMap = HashMap<Int, JewelleryItem>()

            init {
                val jewelleryArray = values()
                for (jewelleryItem in jewelleryArray) {
                    productMap.putIfAbsent(jewelleryItem.sendItem, jewelleryItem)
                }
            }

            @JvmStatic
            fun forProduct(id: Int): JewelleryItem? {
                return productMap[id]
            }
        }
    }

}