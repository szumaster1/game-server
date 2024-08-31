package content.dd.hns

import core.ServerStore.Companion.toJSONArray
import core.api.log
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.tools.Log
import org.json.simple.JSONArray
import org.json.simple.JSONObject

class PenguinManager {

    companion object {
        // List to store the IDs of the penguins
        var penguins: MutableList<Int> = ArrayList()
        // List to store the NPCs
        var npcs = ArrayList<NPC>()
        // Instance of PenguinSpawner class
        val spawner = PenguinSpawner()
        // Map to store the tag mapping of penguins
        var tagMapping: MutableMap<Int, JSONArray> = HashMap()

        // Function to register a tag for a player at a specific location
        fun registerTag(player: Player, location: Location) {
            // Get the ordinal value of the penguin at the location
            val ordinal = Penguin.forLocation(location)?.ordinal ?: -1
            // Get the list of taggers for the penguin
            val list = tagMapping[ordinal] ?: JSONArray()
            // Add the player's username to the list of taggers
            list.add(player.username.lowercase())
            // Update the tag mapping with the new list
            tagMapping[ordinal] = list
            // Update the store file
            updateStoreFile()
        }

        // Function to check if a player has tagged a penguin at a specific location
        fun hasTagged(player: Player, location: Location): Boolean {
            // Get the ordinal value of the penguin at the location
            val ordinal = Penguin.forLocation(location)?.ordinal
            // Check if the tag mapping contains the player's username for the penguin
            return tagMapping[ordinal]?.contains(player.username.lowercase()) ?: false
        }

        // Function to update the store file with the tag mapping
        private fun updateStoreFile() {
            // Create a JSON array to store the tag mapping
            val jsonTags = JSONArray()
            // Filter the tag mapping to remove empty lists and iterate over the remaining entries
            tagMapping.filter { it.value.isNotEmpty() }.forEach { (ordinal, taggers) ->
                // Log the ordinal and the first tagger for debugging purposes
                log(this::class.java, Log.FINE, "$ordinal - ${taggers.first()}")
                // Create a JSON object to store the ordinal and taggers
                val tag = JSONObject()
                tag["ordinal"] = ordinal
                tag["taggers"] = taggers
                // Add the tag object to the JSON array
                jsonTags.add(tag)
            }

            // Update the "tag-mapping" key in the store file with the JSON array
            PenguinHNSEvent.getStoreFile()["tag-mapping"] = jsonTags
        }
    }

    // Function to rebuild the variables when the server starts
    fun rebuildVars() {
        // Check if the store file does not contain the key "spawned-penguins"
        if (!PenguinHNSEvent.getStoreFile().containsKey("spawned-penguins")) {
            // Spawn 10 penguins and store their IDs in the "spawned-penguins" key of the store file
            penguins = spawner.spawnPenguins(10)
            PenguinHNSEvent.getStoreFile()["spawned-penguins"] = penguins.toJSONArray()
            // Clear the tag mapping
            tagMapping.clear()
            // Iterate over the penguins and add an empty list to the tag mapping for each penguin
            for (p in penguins) {
                tagMapping.put(p, JSONArray())
            }
            // Update the store file with the new tag mapping
            updateStoreFile()
        } else {
            // Get the list of spawned penguins from the store file
            val spawnedOrdinals =
                (PenguinHNSEvent.getStoreFile()["spawned-penguins"] as JSONArray).map { it.toString().toInt() }
            // Spawn the penguins with the stored IDs
            spawner.spawnPenguins(spawnedOrdinals)

            // Get the tag mapping from the store file
            val storedTags = (PenguinHNSEvent.getStoreFile()["tag-mapping"] as? JSONArray)?.associate { jRaw ->
                val jObj = jRaw as JSONObject
                jObj["ordinal"].toString().toInt() to (jObj["taggers"] as JSONArray)
            }?.toMutableMap() ?: HashMap()

            // Update the tag mapping and the list of penguins with the stored values
            tagMapping = storedTags
            penguins = spawnedOrdinals.toMutableList()
        }
    }
}
