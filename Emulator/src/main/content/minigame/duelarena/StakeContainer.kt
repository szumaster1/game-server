package content.minigame.duelarena

import core.game.component.Component
import core.game.container.*
import core.game.container.access.InterfaceContainer
import core.game.node.entity.player.Player
import core.game.node.entity.player.info.login.PlayerParser
import core.game.node.item.Item
import core.network.packet.PacketRepository
import core.network.packet.context.ContainerContext
import core.network.packet.outgoing.ContainerPacket

/**
 * Stake container for holding items during a duel.
 *
 * @param player the player involved in the duel
 * @param session the duel session associated with the container
 * @constructor Creates a StakeContainer with the specified player and duel session.
 */
class StakeContainer(private val player: Player, val session: DuelSession) :
    Container(28, ContainerType.DEFAULT, SortType.ID) {

    var listener: StakeListener? = null
    private var released = false

    init {
        listeners.add(StakeListener().also { listener = it })
    }

    /**
     * Open the overlay interface for the player.
     */
    fun open() {
        player.interfaceManager.openSingleTab(OVERLAY)
        player.packetDispatch.sendRunScript(150, "IviiiIssssssss", *INVY_PARAMS)
        player.packetDispatch.sendIfaceSettings(1278, 0, 336, 0, 27)
        PacketRepository.send(ContainerPacket::class.java, ContainerContext(player, -1, 2, 93, player.inventory, false))
    }

    /**
     * Offer an item from the player's inventory for trade.
     *
     * @param slot The inventory slot of the item to offer.
     * @param amount The amount of the item to offer.
     */
    fun offer(slot: Int, amount: Int) {
        val item = player.inventory[slot] ?: return
        if (slot < 0 || slot > player.inventory.capacity() || amount < 1) {
            return
        }
        if (!item.definition.isTradeable) {
            player.sendMessage("You can't offer that item.")
            return
        }
        val remove = Item(item.id, amount)
        if (item.amount > getMaximumAdd(item)) {
            item.amount = getMaximumAdd(item)
            if (item.amount < 1) {
                return
            }
        }
        remove.amount = if (amount > player.inventory.getAmount(item)) player.inventory.getAmount(item) else amount
        if (player.inventory.remove(remove, slot, true)) {
            session.resetAccept()
            add(remove)
            val c = session.getOppositeContainer(player)
            c!!.listener!!.update(c, c.event)
        }
    }

    /**
     * Withdraw an item from the player's inventory.
     *
     * @param slot The inventory slot of the item to withdraw.
     * @param amount The amount of the item to withdraw.
     */
    fun withdraw(slot: Int, amount: Int) {
        val item = get(slot) ?: return
        if (slot < 0 || slot > player.inventory.capacity() || amount < 1) {
            return
        }
        if (!item.definition.isTradeable) {
            player.sendMessage("You can't offer that item.")
            return
        }
        val remove = Item(item.id, amount)
        if (item.amount > getMaximumAdd(item)) {
            item.amount = getMaximumAdd(item)
            if (item.amount < 1) {
                return
            }
        }
        remove.amount = if (amount > getAmount(item)) getAmount(item) else amount
        if (remove(remove, slot, true) && player.inventory.add(remove)) {
            session.resetAccept()
            shift()
            val c = session.getOppositeContainer(player)
            c!!.listener!!.update(c, c.event)
        }
    }

    /**
     * Release
     */
    fun release() {
        if (released) {
            return
        }
        released = true
        if (!player.inventory.hasSpaceFor(this)) {
            player.bank.addAll(this)
            player.sendMessage("Your stake was sent to your bake due to invalid inventory space.")
            return
        }
        player.inventory.addAll(this)
        PlayerParser.save(player)
    }

    /**
     * Stake listener
     */
    inner class StakeListener : ContainerListener {
        override fun update(c: Container?, event: ContainerEvent?) {
            InterfaceContainer.generateItems(
                player,
                c!!.toArray(),
                WITHDRAW_OPTIONS,
                631,
                103,
                12,
                3
            )
            InterfaceContainer.generateItems(
                player,
                session.getOppositeContainer(player)!!.toArray(),
                WITHDRAW_OPTIONS,
                631,
                104,
                12,
                3
            )
        }

        override fun refresh(c: Container?) {
            PacketRepository.send(
                ContainerPacket::class.java,
                ContainerContext(player, -1, 2, 93, player.inventory, false)
            )
        }
    }

    companion object {
        private val INVY_PARAMS = arrayOf<Any>(
            "",
            "",
            "",
            "Stake-X",
            "Stake-All",
            "Stake-10",
            "Stake-5",
            "Stake",
            -1,
            0,
            7,
            4,
            93,
            336 shl 16
        )
        private val WITHDRAW_OPTIONS = arrayOf("Remove-X", "Remove-All", "Remove-10", "Remove-5", "Remove")
        val OVERLAY = Component(336)

        //private const val serialVersionUID = 3454088488966242754L
        //val serialversionuid: Long
        //get() = StakeContainer.serialVersionUID
    }
}
