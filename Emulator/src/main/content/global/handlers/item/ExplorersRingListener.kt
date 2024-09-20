package content.global.handlers.item

import org.rs.consts.Graphics
import org.rs.consts.Items
import content.global.skill.magic.modern.ModernListeners
import core.ServerStore
import core.ServerStore.Companion.getInt
import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.TeleportManager.TeleportType
import core.game.node.entity.skill.Skills
import core.game.world.map.Location
import org.json.simple.JSONObject

/**
 * Handles the explorer's diary ring option interaction.
 */
class ExplorersRingListener : InteractionListener {

    override fun defineListeners() {

        /*
         * Explorer ring interaction.
         */

        on(RINGS, IntType.ITEM, "run-replenish") { player, node ->
            val charges = getStoreFile().getInt(player.username.lowercase() + ":run")
            if (charges >= getRingLevel(node.id)) {
                sendDialogue(player, "Your ring appears to have no more run energy recharges left for today.")
                return@on true
            }
            if (player.settings.runEnergy == 100.0) {
                sendMessage(player, "You are fully rested. You do not need to use the ring's power for the moment.")
                return@on true
            }
            player.settings.updateRunEnergy(-50.0)
            playAudio(player, 5035)

            getStoreFile()[player.username.lowercase() + ":run"] = charges + 1

            sendMessage(player, "You feel refreshed as the ring revitalises you and a charge is used up.")
            visualize(player, 9988, Graphics.RECHARGE_RUN_1733)
            return@on true
        }

        /*
         * Low alchemy spell ring interaction.
         */

        on(RINGS, IntType.ITEM, "low-alchemy") { player, _ ->
            if (!hasLevelStat(player, Skills.MAGIC, 21)) {
                sendMessage(player, "You need a Magic level of 21 in order to do that.")
                return@on true
            }
            val remaining = getStoreFile().getInt(player.username.lowercase() + ":alchs", 30)
            if (remaining <= 0) {
                sendMessage(player, "You have used up all of your charges for the day.")
                return@on true
            }
            sendDialogue(player, "Choose the item that you wish to convert to coins.")
            addDialogueAction(player) { _, _ ->
                sendItemSelect(player, "Choose") { slot, _ ->
                    val item = player.inventory[slot]
                    if (item == null) return@sendItemSelect
                    if (!ModernListeners().alchemize(player, item, false, explorersRing = true)) return@sendItemSelect
                    getStoreFile()[player.username.lowercase() + ":alchs"] = remaining - 1
                }
            }
            return@on true
        }

        /*
         * Teleport to Port Sarim farm interaction using Explorer ring.
         */

        on(RINGS, IntType.ITEM, "cabbage-port") { player, node ->
            teleport(player)
            return@on true
        }

        /*
         * Interaction needed to open the ring options.
         */

        on(RINGS, IntType.ITEM, "operate", "rub") { player, node ->
            if (getRingLevel(node.id) < 3) {
                sendMessage(player, "This item can not be operated.")
                return@on true
            }

            teleport(player)
            return@on true
        }
    }

    fun teleport(player: Player) {
        teleport(player, CABBAGE_PORT, TeleportType.CABBAGE)
    }

    /**
     * Get the level of **explorer's ring**.
     *
     * @param id The item id of the ring to determine its level.
     * @return The level of the ring based on its ID, or -1 if the ID is not recognized.
     */
    fun getRingLevel(id: Int): Int {
        return when (id) {
            Items.EXPLORERS_RING_1_13560 -> 1
            Items.EXPLORERS_RING_2_13561 -> 2
            Items.EXPLORERS_RING_3_13562 -> 3
            else -> -1
        }
    }

    /**
     * Get the daily archive from the server store.
     *
     * @return The JSON object representing the store file for the daily explorer ring.
     */
    fun getStoreFile(): JSONObject {
        return ServerStore.getArchive("daily-explorer-ring")
    }

    companion object {
        val RINGS = intArrayOf(Items.EXPLORERS_RING_1_13560, Items.EXPLORERS_RING_2_13561, Items.EXPLORERS_RING_3_13562)
        val CABBAGE_PORT: Location = Location.create(3051, 3291, 0)
    }
}
