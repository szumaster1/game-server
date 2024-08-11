package core.game.global.presets

import core.game.node.entity.player.Player
import java.util.*

/**
 * Initializes the PresetManager class with a nullable Player instance
 * and an empty map of presets.
 */
class PresetManager {
    private val player: Player? = null // Player instance associated with the PresetManager

    private val currentPresets: MutableMap<String, Preset> = HashMap() // Map to store current presets

    /**
     * Store set.
     *
     * @param nameKey The key to identify the preset.
     * @return The PresetManager instance.
     */
    fun storeSet(nameKey: String): PresetManager {
        var name = nameKey // Assign the nameKey to the local variable name
        val set = currentPresets[name] // Retrieve the preset associated with the name
        if (set != null) {
            player!!.sendMessage("You were unable to store the set $name as it already exists.") // Notify the player if the preset already exists
        }
        name = name.lowercase(Locale.getDefault()) // Convert the name to lowercase
        val equipment = ArrayList(Arrays.asList(*player!!.equipment.event.items)) // Retrieve equipment items
        val inventory = ArrayList(Arrays.asList(*player.inventory.event.items)) // Retrieve inventory items
        currentPresets[name] = Preset(equipment, inventory) // Store the preset in the map
        return this
    }

    /**
     * Print available setups.
     */
    fun printAvailableSetups() {
        val size = currentPresets.size // Get the number of available presets
        player!!.sendMessage("You have used " + size + "/" + maxSize() + " available setups.") // Notify the player about the number of setups used
        if (size > 0) {
            player.sendMessage("<col=ff0000>Your available setups are:") // Inform the player about available setups
            for (key in currentPresets.keys) {
                player.sendMessage(key) // Display each available setup key
            }
        }
    }

    /**
     * Max size.
     *
     * @return The maximum size of presets allowed.
     */
    fun maxSize(): Int {
        return 6 // Return the maximum size of presets
    }
}
