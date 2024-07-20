package core.game.global.presets

import core.game.node.entity.player.Player
import java.util.*

/**
 * The type Preset manager.
 */
class PresetManager {
    private val player: Player? = null

    private val currentPresets: MutableMap<String, Preset> = HashMap()

    /**
     * Store set preset manager.
     *
     * @param nameKey the name key
     * @return the preset manager
     */
    fun storeSet(nameKey: String): PresetManager {
        var name = nameKey
        val set = currentPresets[name]
        if (set != null) {
            player!!.sendMessage("You were unable to store the set $name as it already exists.")
        }
        name = name.lowercase(Locale.getDefault())
        val equipment = ArrayList(Arrays.asList(*player!!.equipment.event.items))
        val inventory = ArrayList(Arrays.asList(*player.inventory.event.items))
        currentPresets[name] = Preset(equipment, inventory)
        return this
    }

    /**
     * Print available setups.
     */
    fun printAvailableSetups() {
        val size = currentPresets.size
        player!!.sendMessage("You have used " + size + "/" + maxSize() + " available setups.")
        if (size > 0) {
            player.sendMessage("<col=ff0000>Your available setups are:")
            for (key in currentPresets.keys) {
                player.sendMessage(key)
            }
        }
    }

    fun maxSize(): Int {
        return 6
    }
}
