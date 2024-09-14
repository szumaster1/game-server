package content.global.skill.combat.summoning.familiar

import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.GameWorld
import core.game.world.GameWorld.ticks
import core.tools.RandomFunction

/**
 * Forager.
 */
abstract class Forager(
    owner: Player?,
    id: Int,
    ticks: Int,
    pouchId: Int,
    specialCost: Int,
    attackStyle: Int,
    vararg items: Item
) :
    BurdenBeast(owner, id, ticks, pouchId, specialCost, 30, attackStyle) {
    private val items: Array<Item>?
    private var passiveDelay = 0

    /**
     * Instantiates a new Forager.
     *
     * @param owner       the owner
     * @param id          the id
     * @param ticks       the ticks
     * @param pouchId     the pouch id
     * @param specialCost the special cost
     * @param attackStyle the attack style
     * @param items       the items
     */
    init {
        this.items = items as Array<Item>
        setRandomPassive()
    }

    /**
     * Instantiates a new Forager.
     *
     * @param owner       the owner
     * @param id          the id
     * @param ticks       the ticks
     * @param pouchId     the pouch id
     * @param specialCost the special cost
     * @param items       the items
     */
    constructor(owner: Player?, id: Int, ticks: Int, pouchId: Int, specialCost: Int, vararg items: Item) : this(
        owner,
        id,
        ticks,
        pouchId,
        specialCost,
        WeaponInterface.STYLE_DEFENSIVE,
        *items
    )

    public override fun handleFamiliarTick() {
        super.handleFamiliarTick()
        if (items != null && items.size > 0 && passiveDelay < GameWorld.ticks) {
            if (RandomFunction.random(random) < 4) {
                produceItem(items[RandomFunction.random(items.size)])
            }
            setRandomPassive()
        }
    }

    /**
     * Produce item boolean.
     *
     * @param item the item
     * @return the boolean
     */
    open fun produceItem(item: Item): Boolean {
        if (!container.hasSpaceFor(item)) {
            owner.packetDispatch.sendMessage("Your familar is too full to collect items.")
            return false
        }
        owner.packetDispatch.sendMessage(if (item.amount == 1) "Your familar has produced an item." else "Your familiar has produced items.")
        return container.add(item)
    }

    /**
     * Produce item boolean.
     *
     * @return the boolean
     */
    fun produceItem(): Boolean {
        if (items == null || items.size == 0) {
            return false
        }
        return produceItem(items[RandomFunction.random(items.size)])
    }

    /**
     * Handle passive action.
     */
    open fun handlePassiveAction() {
    }

    open val random: Int
        /**
         * Gets random.
         *
         * @return the random
         */
        get() = 11

    /**
     * Sets random passive.
     */
    fun setRandomPassive() {
        passiveDelay = GameWorld.ticks + RandomFunction.random(100, 440)
    }
}
