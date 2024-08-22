package content.region.asgarnia.handlers.taverley

import core.api.*
import cfg.consts.Items
import cfg.consts.Scenery
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.world.map.Location

/**
 * Represents the Taverley Dungeon listeners.
 */
class TaverleyDungeonListeners : InteractionListener {

    private val SUITS = intArrayOf(2143, 2144)
    private val ARMOUR_SUITS: Array<NPC?> = arrayOfNulls(2)

    override fun defineListeners() {

        // This listener handles the interaction with the gate scenery in Taverley dungeon.
        on(Scenery.GATE_2623, IntType.SCENERY, "open") { player, node ->
            if (!inInventory(player, Items.DUSTY_KEY_1590)) {
                sendMessage(player, "This gate seems to be locked.")
            } else {
                DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
            }
            return@on true
        }

        // This listener handles the interaction with the armour suits scenery in Taverley dungeon.
        on(SUITS, IntType.SCENERY, "open") { player, node ->
            if (player.location.x < node.location.x && !player.getAttribute<Boolean>("spawned_suits", false)) {
                var alive = true
                for (i in ARMOUR_SUITS.indices) {
                    var npc = ARMOUR_SUITS[i]
                    if (npc == null || !npc.isActive) {
                        val location = Location.create(2887, 9829 + (i * 3), 0)
                        npc = NPC(453, location)
                        ARMOUR_SUITS[i] = npc
                        npc.init()
                        npc.properties.combatPulse.attack(player)
                        val scenery = getScenery(location)
                        if (scenery != null) {
                            removeScenery(scenery)
                        }
                        alive = false
                    }
                }
                if (!alive) {
                    setAttribute(player, "spawned_suits", true)
                    sendMessage(player, "Suddenly the suit of armour comes to life!")
                    return@on true
                }
            }
            removeAttribute(player, "spawned_suits")
            DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
            return@on true
        }
    }
}
