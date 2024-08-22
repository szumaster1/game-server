package core.game.node.entity.combat.graves

import core.ServerStore
import core.api.*
import cfg.consts.Items
import cfg.consts.Sounds
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.Node
import core.game.node.entity.combat.ImpactHandler
import core.game.node.entity.player.Player
import core.game.node.entity.player.info.Rights
import core.game.node.entity.player.link.IronmanMode
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.system.command.Privilege
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Location
import core.game.world.map.zone.ZoneRestriction
import core.game.world.map.zone.impl.WildernessZone
import core.game.world.repository.Repository
import core.tools.colorize
import core.tools.secondsToTicks
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import java.util.Map
import kotlin.math.min

/**
 * Grave controller.
 * @author Ceikry
 */
class GraveController : PersistWorld, TickListener, InteractionListener, Commands {

    // Define the listeners for various grave-related actions
    override fun defineListeners() {
        // Register the read option for graves
        on(GraveType.ids, IntType.NPC, "read", handler = this::onGraveReadOption)
        // Register the bless option for graves
        on(GraveType.ids, IntType.NPC, "bless", handler = this::onGraveBlessed)
        // Register the repair option for graves
        on(GraveType.ids, IntType.NPC, "repair", handler = this::onGraveRepaired)
        // Register the demolish option for graves
        on(GraveType.ids, IntType.NPC, "demolish", handler = this::onGraveDemolished)
    }

    // Define commands related to grave actions
    override fun defineCommands() {
        // Command to force a grave death
        define("forcegravedeath", Privilege.ADMIN, "", "Forces a death that should produce a grave.") { player, _ ->
            // Set player's rights to regular player
            player.details.rights = Rights.REGULAR_PLAYER
            // Mark the tutorial as complete for the player
            setAttribute(player, "tutorial:complete", true)
            // Manually hit the player to simulate death
            player.impactHandler.manualHit(player, player.skills.lifepoints, ImpactHandler.HitsplatType.NORMAL)
            // Notify the player about the grave creation location
            notify(player, "Grave created at ${player.getAttribute("/save:original-loc", player.location)}")
            // Schedule a pulse to restore player's rights after a delay
            GameWorld.Pulser.submit(object : Pulse(15) {
                override fun pulse(): Boolean {
                    // Restore player's rights to administrator
                    player.details.rights = Rights.ADMINISTRATOR
                    // Send a message to the player confirming rights restoration
                    sendMessage(player, "Rights restored")
                    return true
                }
            })
        }
    }

    /**
     * Handles the ticking of graves.
     */
    override fun tick() {
        // Iterate through all active graves
        for (grave in activeGraves.values.toTypedArray()) {
            // If ticks remaining is -1, exit the function
            if (grave.ticksRemaining == -1) return

            // Transform the grave if it reaches specific tick thresholds
            if (grave.ticksRemaining == secondsToTicks(30) || grave.ticksRemaining == secondsToTicks(90)) {
                grave.transform(grave.id + 1)
            }

            // Collapse the grave if ticks remaining reaches zero
            if (grave.ticksRemaining == 0) {
                grave.collapse()
            }

            // Decrement the ticks remaining for the grave
            grave.ticksRemaining--
        }
    }

    /**
     * Handles the read option for a grave.
     * @param player The player interacting with the grave.
     * @param node The grave node being interacted with.
     */
    private fun onGraveReadOption(player: Player, node: Node): Boolean {
        // Cast the node to a Grave object
        val grave = node as? Grave ?: return false

        // Variable to check if the grave has a granite background
        var isGraniteBackground = false

        // Determine if the grave type is within the granite background range
        when (grave.type) {
            in GraveType.SMALL_GS..GraveType.ANGEL_DEATH -> isGraniteBackground = true
            else -> {}
        }

        // Set the varbit based on the granite background status
        if (isGraniteBackground)
            setVarbit(player, 4191, 1)
        else
            setVarbit(player, 4191, 0)

        // Open the interface for the player
        openInterface(player, 266)
        // Set the interface text with the grave's formatted text
        setInterfaceText(player, grave.retrieveFormattedText(), 266, 23)

        // Notify the player about the time remaining for the grave
        sendMessage(player, "It looks like it'll survive another ${grave.getFormattedTimeRemaining()}.")
        // If the player is the owner of the grave, send a special message
        if (player.details.uid == grave.ownerUid) {
            sendMessage(player, "Isn't there something a bit odd about reading your own gravestone?")
        }
        return true
    }

    /**
     * Handles the blessing of a grave.
     * @param player The player attempting to bless the grave.
     * @param node The grave node being blessed.
     */
    private fun onGraveBlessed(player: Player, node: Node): Boolean {
        // Cast the node to a Grave object
        val g = node as? Grave ?: return false

        // Check if the grave has already been blessed
        if (getAttribute(g, "blessed", false)) {
            sendMessage(player, "This grave has already been blessed.")
            return true
        }

        // Check if the player is the owner of the grave
        if (player.details.uid == g.ownerUid) {
            sendMessage(player, "The gods don't seem to approve of people attempting to bless their own gravestones.")
            return true
        }

        // Check if the player has sufficient Prayer level to bless the grave
        if (getStatLevel(player, Skills.PRAYER) < 70) {
            sendMessage(player, "You need a Prayer level of 70 to bless a grave.")
            return true
        }

        // Calculate the amount of prayer points to use for blessing
        val blessAmount = min(60, player.skills.prayerPoints.toInt() - 10)

        // Check if the player has enough prayer points
        if (blessAmount <= 0) {
            sendMessage(player, "You do not have enough prayer points to do that.")
            return true
        }

        // Add time to the grave based on the blessing
        g.addTime(secondsToTicks(blessAmount * 60))
        // Deduct the used prayer points from the player
        player.skills.prayerPoints -= blessAmount
        // Mark the grave as blessed
        setAttribute(g, "blessed", true)

        // Play audio and animate the player during the blessing
        playAudio(player, Sounds.PRAYER_RECHARGE_2674)
        animate(player, 645)

        // Notify the grave owner about the blessing
        val gOwner = Repository.uid_map[g.ownerUid]
        if (gOwner != null) {
            sendMessage(gOwner, colorize("%RYour grave has been blessed."))
        }
        return true
    }

    /**
     * Handles the event when a grave is repaired by a player.
     * @param player The player who is repairing the grave.
     * @param node The node representing the grave.
     */
    private fun onGraveRepaired(player: Player, node: Node): Boolean {
        // Cast the node to a Grave type, return false if the cast fails
        val g = node as? Grave ?: return false

        // Check if the grave has already been repaired
        if (getAttribute(g, "repaired", false)) {
            // Notify the player that the grave is already repaired
            sendMessage(player, "This grave has already been repaired.")
            return true
        }

        // Ensure the player has a Prayer level of at least 2
        if (getStatLevel(player, Skills.PRAYER) < 2) {
            // Inform the player they need a higher Prayer level
            sendMessage(player, "You need a Prayer level of 2 to bless a grave.")
            return true
        }

        // Check if the player has enough prayer points
        if (player.skills.prayerPoints < 1.0) {
            // Notify the player about insufficient prayer points
            sendMessage(player, "You do not have enough prayer points to do that.")
            return true
        }

        // Calculate the amount of prayer points to restore, capped at 5
        val restoreAmount = min(5, player.skills.prayerPoints.toInt())
        // Add time to the grave based on the restored prayer points
        g.addTime(secondsToTicks(restoreAmount * 60))
        // Deduct the used prayer points from the player
        player.skills.prayerPoints -= restoreAmount
        // Mark the grave as repaired
        setAttribute(g, "repaired", true)

        // Play a sound effect for the prayer recharge
        playAudio(player, Sounds.PRAYER_RECHARGE_2674)
        // Trigger an animation for the player
        animate(player, 645)
        return true
    }

    /**
     * Handles the event when a grave is demolished by a player.
     * @param player The player who is demolishing the grave.
     * @param node The node representing the grave.
     */
    private fun onGraveDemolished(player: Player, node: Node): Boolean {
        // Cast the node to a Grave type, return false if the cast fails
        val g = node as? Grave ?: return false

        // Check if the player is the owner of the grave
        if (player.details.uid != g.ownerUid) {
            // Notify the player they cannot demolish someone else's grave
            sendMessage(player, "You cannot demolish someone else's gravestone!")
            return true
        }

        // Proceed to demolish the grave
        g.demolish()
        return true
    }

    // Method to save the state of the graves
    override fun save() {
        // Serialize active graves to the server store
        serializeToServerStore()
    }

    // Method to parse the state of the graves
    override fun parse() {
        // Deserialize graves from the server store
        deserializeFromServerStore()
    }

    companion object {
        // Map to hold active graves with their unique IDs
        val activeGraves = HashMap<Int, Grave>()

        // Counter for child graves
        var childCounter = 0

        // Attribute key for grave type
        val ATTR_GTYPE = "/save:gravetype"

        /**
         * Produces a new grave of the specified type.
         * @param type The type of grave to produce.
         * @return A new Grave object.
         */
        @JvmStatic
        fun produceGrave(type: GraveType): Grave {
            // Create a new Grave instance
            val g = Grave()
            // Configure the grave type
            g.configureType(type)
            return g
        }

        /**
         * Determines if an item should crumble.
         * @param item The item ID to check.
         * @return True if the item should crumble, false otherwise.
         */
        @JvmStatic
        fun shouldCrumble(item: Int): Boolean {
            // Check specific items that should crumble
            when (item) {
                Items.ECTOPHIAL_4251 -> return true
                in Items.SMALL_POUCH_5509..Items.GIANT_POUCH_5515 -> return true
            }

            // Check if the item has a "destroy" action
            return itemDefinition(item).hasAction("destroy")
        }

        /**
         * Determines if an item should be released.
         * @param item The item ID to check.
         * @return True if the item should be released, false otherwise.
         */
        @JvmStatic
        fun shouldRelease(item: Int): Boolean {
            // Check specific items that should be released
            when (item) {
                Items.CHINCHOMPA_9976 -> return true
                Items.CHINCHOMPA_10033 -> return true
                in Items.BABY_IMPLING_JAR_11238..Items.DRAGON_IMPLING_JAR_11257 -> return itemDefinition(item).isUnnoted
            }

            return false
        }

        /**
         * Checks if the item has a plugin and retrieves the death item if it does.
         * @param item The item to check.
         * @return The transformed item or the original item if no plugin is found.
         */
        @JvmStatic
        fun checkTransform(item: Item): Item {
            // Check if the item has an associated plugin
            if (item.hasItemPlugin())
            // Return the death item from the plugin
                return item.plugin.getDeathItem(item)
            // Return the original item if no plugin is found
            return item
        }

        /**
         * Determines if a player is allowed to generate a grave.
         * @param player The player to check.
         * @return True if the player can generate a grave, false otherwise.
         */
        @JvmStatic
        fun allowGenerate(player: Player): Boolean {
            // Check if the player is currently skulled
            if (player.skullManager.isSkulled)
                return false
            // Check if the player is in the wilderness
            if (player.skullManager.isWilderness)
                return false
            // Check if the player is in a wilderness zone
            if (WildernessZone.isInZone(player))
                return false
            // Check if the player is in hardcore ironman mode
            if (player.ironmanManager.mode == IronmanMode.HARDCORE)
                return false
            // Check if the player's zone has grave restrictions
            if (player.zoneMonitor.isRestricted(ZoneRestriction.GRAVES))
                return false
            // If all checks pass, allow grave generation
            return true
        }

        /**
         * Retrieves the grave type for a player based on their attributes.
         * @param player The player to check.
         * @return The GraveType associated with the player.
         */
        @JvmStatic
        fun getGraveType(player: Player): GraveType {
            // Get the grave type attribute for the player, defaulting to 0 if not set
            return GraveType.values()[getAttribute(player, ATTR_GTYPE, 0)]
        }

        /**
         * Updates the grave type attribute for a player.
         * @param player The player to update.
         * @param type The new grave type to set.
         */
        @JvmStatic
        fun updateGraveType(player: Player, type: GraveType) {
            // Set the player's grave type attribute to the ordinal of the given grave type
            setAttribute(player, ATTR_GTYPE, type.ordinal)
        }

        /**
         * Checks if there is a grave at the specified location.
         * @param loc The location to check.
         * @return True if a grave exists at the location, false otherwise.
         */
        @JvmStatic
        fun hasGraveAt(loc: Location): Boolean {
            // Check if any active graves match the specified location
            return activeGraves.values.toTypedArray().any { it.location == loc }
        }

        /**
         * Serializes active graves to the server store for persistence.
         */
        fun serializeToServerStore() {
            // Get the archive for active graves from the server store
            val archive = ServerStore.getArchive("active-graves")
            // Clear any existing entries in the archive
            archive.clear()
            // Iterate through each active grave
            for ((uid, grave) in activeGraves) {
                // Create a JSON object to hold grave data
                val g = JSONObject()
                // Store the remaining ticks for the grave
                g["ticksRemaining"] = grave.ticksRemaining
                // Store the location of the grave
                g["location"] = grave.location.toString()
                // Store the type of the grave
                g["type"] = grave.type.ordinal
                // Store the username of the grave owner
                g["username"] = grave.ownerUsername
                // Create a JSON array to hold items in the grave
                val items = JSONArray()
                // Iterate through each item in the grave
                for (item in grave.getItems()) {
                    // Create a JSON object for each item
                    val i = JSONObject()
                    // Store the item ID
                    i["id"] = item.id
                    // Store the item amount
                    i["amount"] = item.amount
                    // Store the item charge
                    i["charge"] = item.charge
                    // Add the item JSON object to the items array
                    items.add(i)
                }
                // Add the items array to the grave JSON object
                g["items"] = items
                // Store the grave JSON object in the archive using its UID as the key
                archive["$uid"] = g
            }
        }

        /**
         * Deserializes graves from the server store to restore their state.
         */
        fun deserializeFromServerStore() {
            // Get the archive for active graves from the server store
            val archive = ServerStore.getArchive("active-graves")
            // Iterate through each entry in the archive
            for (entry in archive.entries as Set<Map.Entry<String, JSONObject>>) {
                // Get the grave JSON object from the entry
                val g = entry.value as JSONObject
                // Extract the UID from the entry key
                val uid = (entry.key as String).toInt()
                // Extract the grave type from the JSON object
                val type = g["type"].toString().toInt()
                // Extract the remaining ticks from the JSON object
                val ticks = g["ticksRemaining"].toString().toInt()
                // Extract the location from the JSON object
                val location = Location.fromString(g["location"].toString())
                // Extract the username from the JSON object
                val username = g["username"].toString()

                // Create a list to hold items for the grave
                val items = ArrayList<Item>()
                // Get the raw items array from the JSON object
                val itemsRaw = g["items"] as JSONArray
                // Iterate through each raw item in the items array
                for (itemRaw in itemsRaw) {
                    // Cast the raw item to a JSON object
                    val item = itemRaw as JSONObject
                    // Extract the item ID
                    val id = item["id"].toString().toInt()
                    // Extract the item amount
                    val amount = item["amount"].toString().toInt()
                    // Extract the item charge
                    val charge = item["charge"].toString().toInt()
                    // Create a new Item object and add it to the items list
                    items.add(Item(id, amount, charge))
                }

                // Create a new grave based on the extracted grave type
                val grave = produceGrave(GraveType.values()[type])
                // Set up the grave using the extracted parameters
                grave.setupFromJsonParams(uid, ticks, location, items.toTypedArray(), username)
                // Store the grave in the active graves map using its UID
                activeGraves[uid] = grave
            }
        }
    }
}