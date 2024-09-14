package content.global.skill.combat.summoning.familiar

import content.global.skill.combat.summoning.SummoningPouch.Companion.get
import core.game.component.Component
import core.game.container.Container
import core.game.container.access.InterfaceContainer
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.node.item.GroundItem
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.game.system.config.ItemConfigParser
import java.util.*

/**
 * Burden beast.
 */
abstract class BurdenBeast @JvmOverloads constructor(
    owner: Player?,
    id: Int,
    ticks: Int,
    pouchId: Int,
    specialCost: Int,
    containerSize: Int,
    attackStyle: Int = WeaponInterface.STYLE_DEFENSIVE
) :
    Familiar(owner, id, ticks, pouchId, specialCost, attackStyle) {
    /**
     * Gets container.
     *
     * @return the container
     */
    /*
         * Gets container.
         */
    /**
     * The Container.
     */
    @JvmField
    var container: Container =
        Container(containerSize).register(BurdenContainerListener(owner))

    /**
     * Instantiates a new Burden beast.
     *
     * @param owner         the owner
     * @param id            the id
     * @param ticks         the ticks
     * @param pouchId       the pouch id
     * @param specialCost   the special cost
     * @param containerSize the container size
     * @param attackStyle   the attack style
     */

    override fun dismiss() {
        if (owner.interfaceManager.hasMainComponent(671)) {
            owner.interfaceManager.close()
        }

        for (item in container.toArray()) {
            if (item != null) {
                GroundItemManager.create(GroundItem(item, location, 500, owner))
            }
        }

        container.clear()
        super.dismiss()
    }

    override fun isBurdenBeast(): Boolean {
        return true
    }

    override fun isPoisonImmune(): Boolean {
        return true
    }

    /**
     * Is allowed boolean.
     *
     * @param owner the owner
     * @param item  the item
     * @return the boolean
     */
    open fun isAllowed(owner: Player, item: Item): Boolean {
        if (item.value > 50000) {
            owner.packetDispatch.sendMessage("This item is too valuable to trust to this familiar.")
            return false
        }
        if (!item.definition.isTradeable) {
            owner.packetDispatch.sendMessage("You can't trade this item, not even to your familiar.")
            return false
        }
        if ((!get(pouchId)!!.abyssal && (item.id == 1436 || item.id == 7936)) || !item.definition.getConfiguration(
                ItemConfigParser.BANKABLE,
                true
            )
        ) {
            owner.packetDispatch.sendMessage("You can't store " + item.name.lowercase(Locale.getDefault()) + " in this familiar.")
            return false
        }
        if (get(this.pouchId)!!.abyssal) {
            if (!item.name.lowercase(Locale.getDefault()).contains("essence")) {
                owner.packetDispatch.sendMessage("You can only give unnoted essence to this familiar.")
                return false
            }
            if (item.id == 1437 || item.id == 7937) {
                owner.packetDispatch.sendMessage("You can't give noted essence to this familiar.")
                return false
            }
        }
        return true
    }

    /**
     * Transfer.
     *
     * @param item     the item
     * @param amount   the amount
     * @param withdraw the withdraw
     */
    fun transfer(item: Item?, amount: Int, withdraw: Boolean) {
        var item = item
        var amount = amount
        if (this is Forager && !withdraw) {
            owner.packetDispatch.sendMessage("You can't store your items in this familiar.")
            return
        }
        if (item == null || owner == null) {
            return
        }
        if (!withdraw && !isAllowed(owner, Item(item.id, if (item.definition.isStackable) amount else 1))) {
            return
        }
        val to = if (withdraw) owner.inventory else container
        val from = if (withdraw) container else owner.inventory
        val fromAmount = from.getAmount(item)
        if (amount > fromAmount) {
            amount = fromAmount
        }
        val maximum = to.getMaximumAdd(item)
        if (amount > maximum) {
            amount = maximum
        }
        if (amount < 1) {
            if (withdraw) {
                owner.packetDispatch.sendMessage("Not enough space in your inventory.")
            } else {
                owner.packetDispatch.sendMessage("Your familiar can't carry any more items.")
            }
            return
        }
        if (!item.definition.isStackable && item.slot > -1) {
            from.replace(null, item.slot)
            to.add(Item(item.id, 1))
            amount--
        }
        if (amount > 0) {
            item = Item(item.id, amount)
            if (from.remove(item)) {
                to.add(item)
            }
        }
    }

    /**
     * Withdraw all.
     */
    /*
     * Withdraw all.
     */
    fun withdrawAll() {
        for (i in 0 until container.capacity()) {
            var item = container[i] ?: continue
            val amount = owner.inventory.getMaximumAdd(item)
            if (item.amount > amount) {
                item = Item(item.id, amount)
                container.remove(item, false)
                owner.inventory.add(item, false)
            } else {
                container.replace(null, i, false)
                owner.inventory.add(item, false)
            }
        }
        container.update()
        owner.inventory.update()
    }

    /**
     * Open interface.
     */
    /*
     * Open interface.
     */
    fun openInterface() {
        if (container.itemCount() == 0 && this is Forager) {
            owner.packetDispatch.sendMessage("Your familiar is not carrying any items that you can withdraw.")
            return
        }
        owner.interfaceManager.open(Component(671)).setCloseEvent { player, c ->
            player.interfaceManager.closeSingleTab()
            true
        }
        container.shift()
        owner.interfaceManager.openSingleTab(Component(665))
        InterfaceContainer.generateItems(
            owner,
            owner.inventory.toArray(),
            arrayOf("Examine", "Store-X", "Store-All", "Store-10", "Store-5", "Store-1"),
            665,
            0,
            7,
            4,
            93
        )
        InterfaceContainer.generateItems(
            owner,
            container.toArray(),
            arrayOf("Examine", "Withdraw-X", "Withdraw-All", "Withdraw-10", "Withdraw-5", "Withdraw-1"),
            671,
            27,
            5,
            6,
            30
        )
        container.refresh()
    }
}
