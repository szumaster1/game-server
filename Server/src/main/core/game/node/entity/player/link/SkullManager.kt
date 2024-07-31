package core.game.node.entity.player.link

import core.api.registerTimer
import core.api.removeTimer
import core.api.spawnTimer
import core.game.container.Container
import core.game.container.ContainerEvent
import core.game.container.ContainerListener
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import kotlin.math.max

/**
 * Represents a managing class of the active player skulls.
 */
class SkullManager(val player: Player) {
    enum class SkullIcon(val id: Int) {
        NONE(-1),
        WHITE(0),
        RED(1),
        BH_RED5(2),
        BH_BLUE4(3),
        BH_GREEN3(4),
        BH_GREY2(5),
        BH_BROWN1(6),
        SCREAM(7);

        companion object {
            fun forId(id: Int): SkullIcon {
                return when (id) {
                    0 -> WHITE
                    1 -> RED
                    2 -> BH_RED5
                    3 -> BH_BLUE4
                    4 -> BH_GREEN3
                    5 -> BH_GREY2
                    6 -> BH_BROWN1
                    7 -> SCREAM
                    else -> NONE
                }
            }
        }
    }

    var isWilderness: Boolean = false

    var isWildernessDisabled: Boolean = false

    private var level = 0

    private val skullCauses: MutableList<Player> = ArrayList()

    private var skulled = false

    var isSkullCheckDisabled: Boolean = false

    private var deepWilderness = false

    fun checkSkull(other: Entity?) {
        if (other !is Player || !isWilderness || isSkullCheckDisabled) {
            return
        }
        val o = other
        for (p in o.skullManager.skullCauses) {
            if (p === player) {
                return
            }
        }
        if (skullCauses.contains(o)) {
            return
        }
        skullCauses.add(o)
        removeTimer(player, "skulled")
        registerTimer(player, spawnTimer("skulled", 2000))
    }

    fun setSkullIcon(skullIcon: Int) {
        player.appearance.skullIcon = skullIcon
        player.updateAppearance()
    }

    fun reset() {
        skullCauses.clear()
        setSkullIcon(-1)
        setSkulled(false)
        player.appearance.sync()
    }

    fun getLevel(): Int {
        return level
    }

    fun setLevel(level: Int) {
        if (!deepWilderness && level >= 49) setDeepWilderness(true)
        else if (deepWilderness && level < 48) setDeepWilderness(false)

        if (level > 20) player.locks.lockTeleport(1000000)
        else player.locks.unlockTeleport()

        this.level = level
    }

    fun hasWildernessProtection(): Boolean {
        return level < 49
    }

    fun isSkulled(): Boolean {
        return skulled || deepWilderness
    }

    fun isDeepWilderness(): Boolean {
        return deepWilderness
    }

    fun setDeepWilderness(deepWildy: Boolean) {
        if (deepWildy) {
            updateDWSkullIcon()
        } else {
            removeDWSkullIcon()
        }
        isSkullCheckDisabled = deepWildy
        deepWilderness = deepWildy
    }

    /**
     * Update dw skull icon.
     */
    fun updateDWSkullIcon() {
        if (player.getAttribute<Any?>("deepwild-value-listener") == null) {
            val listener: ContainerListener = object : ContainerListener {
                override fun update(c: Container?, event: ContainerEvent?) {
                    refresh(c)
                }

                override fun refresh(c: Container?) {
                    updateDWSkullIcon()
                }
            }
            player.setAttribute("deepwild-value-listener", listener)
            player.inventory.listeners.add(listener)
            player.equipment.listeners.add(listener)
        }
        var value: Long = 0
        var maxValue: Long = 0
        for (item in player.inventory.toArray()) {
            if (item != null) {
                val alchValue = item.alchemyValue
                value += alchValue
                maxValue = max(maxValue.toDouble(), alchValue.toDouble()).toLong()
            }
        }
        for (item in player.equipment.toArray()) {
            if (item != null) {
                val alchValue = item.alchemyValue
                value += alchValue
                maxValue = max(maxValue.toDouble(), alchValue.toDouble()).toLong()
            }
        }
        // Act as if protect item is always active when calculating risk.
        value -= maxValue
        player.setAttribute("deepwild-value-risk", value)
        var skull = SkullIcon.BH_BROWN1
        if (value >= DEEP_WILD_DROP_RISK_THRESHOLD) {
            skull = SkullIcon.RED
        }
        setSkullIcon(skull.id)
    }

    fun removeDWSkullIcon() {
        setSkullIcon(if (skulled) 0 else -1)
        val listener = player.getAttribute<ContainerListener>("deepwild-value-listener")
        if (listener != null) {
            player.inventory.listeners.remove(listener)
            player.equipment.listeners.remove(listener)
        }
        player.removeAttribute("deepwild-value-listener")
        player.removeAttribute("deepwild-value-risk")
    }

    fun setSkulled(skulled: Boolean) {
        this.skulled = skulled
    }

    companion object {
        const val DEEP_WILD_DROP_RISK_THRESHOLD: Long = 100000
    }
}
