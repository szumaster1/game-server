package core.game.node.entity.combat.graves

import core.api.clearHintIcon
import core.api.consts.NPCs
import core.api.registerHintIcon
import core.api.sendMessage
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.node.item.GroundItem
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.game.world.GameWorld
import core.game.world.map.Location
import core.game.world.repository.Repository
import core.plugin.Initializable
import core.tools.colorize
import core.tools.secondsToTicks
import core.tools.ticksToSeconds

/**
 * Grave class representing a player's grave in the game.
 */
@Initializable
class Grave : AbstractNPC {
    lateinit var type: GraveType // Type of the grave
    private val items = ArrayList<GroundItem>() // List to hold ground items associated with the grave
    var ownerUsername: String = "" // Username of the grave owner
    var ownerUid: Int = -1 // Unique identifier for the grave owner

    var ticksRemaining = -1 // Remaining ticks before the grave expires

    constructor() : super(NPCs.GRAVESTONE_6571, Location.create(0, 0, 0), false) // Default constructor
    private constructor(id: Int, location: Location) : super(id, location) // Private constructor for custom ID and location

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return Grave(id, location) // Constructs a new Grave instance
    }

    override fun getIds(): IntArray {
        return GraveType.ids // Returns the IDs associated with the grave type
    }

    /**
     * Configure the type of the grave.
     */
    fun configureType(type: GraveType) {
        this.type = type // Set the grave type
        this.transform(type.npcId) // Transform the NPC to the corresponding grave type
        this.ticksRemaining = secondsToTicks(type.durationMinutes * 60) // Set the remaining ticks based on duration
    }

    /**
     * Initialize the grave for a player.
     *
     * @param player The player object that will be initialized.
     * @param location The location where the player will be placed.
     * @param inventory The array of items that the player will have.
     */
    fun initialize(player: Player, location: Location, inventory: Array<Item>) {
        if (!GraveController.allowGenerate(player)) // Check if grave generation is allowed
            return // Exit if not allowed

        this.ownerUid = player.details.uid // Set the owner's unique ID
        this.ownerUsername = player.username // Set the owner's username
        this.location = player.getAttribute("/save:original-loc", location) // Get the original location of the player
        this.isRespawn = false // Set respawn flag to false
        this.isWalks = false // Set walking flag to false
        this.isNeverWalks = true // Set never walks flag to true

        for (item in inventory) { // Iterate through the player's inventory
            if (GraveController.shouldRelease(item.id)) { // Check if the item should be released
                sendMessage(player, "Your ${item.name.lowercase().replace("jar", "")} has escaped.") // Notify player
                continue // Skip to the next item
            }

            if (GraveController.shouldCrumble(item.id)) { // Check if the item should crumble
                sendMessage(player, "Your ${item.name.lowercase()} has crumbled to dust.") // Notify player
                continue // Skip to the next item
            }

            val finalItem = GraveController.checkTransform(item) // Check if the item needs transformation

            val gi = GroundItemManager.create(finalItem, this.location, player) // Create a ground item
            gi.isRemainPrivate = true // Set the ground item to remain private
            gi.decayTime = secondsToTicks(type.durationMinutes * 60) // Set decay time for the item
            this.items.add(gi) // Add the ground item to the list
        }

        if (items.isEmpty()) { // Check if there are no items
            clear() // Clear the grave
            return // Exit the function
        }

        this.init() // Initialize the grave

        if (GraveController.activeGraves[ownerUid] != null) { // Check if there is an existing grave for the owner
            val oldGrave = GraveController.activeGraves[ownerUid] // Get the old grave
            oldGrave?.collapse() // Collapse the old grave if it exists
        }

        GraveController.activeGraves[ownerUid] = this // Register the new grave
        sendMessage(
            player,
            colorize("%RBecause of your current gravestone, you have ${type.durationMinutes} minutes to get your items back.") // Notify player of time limit
        )
    }

    /**
     * Setup the grave from JSON parameters.
     *
     * @param playerUid Unique identifier for the player
     * @param ticks Number of ticks to process
     * @param location The player's current location in the game
     * @param items Array of items associated with the player
     * @param username The name of the player
     */
    fun setupFromJsonParams(playerUid: Int, ticks: Int, location: Location, items: Array<Item>, username: String) {
        this.ownerUid = playerUid // Set the owner's unique ID
        this.ticksRemaining = ticks // Set the remaining ticks
        this.location = location // Set the grave location
        this.isRespawn = false // Set respawn flag to false
        this.isWalks = false // Set walking flag to false
        this.isNeverWalks = true // Set never walks flag to true
        this.ownerUsername = username // Set the owner's username

        for (item in items) { // Iterate through the items
            val gi = GroundItemManager.create(item, location, playerUid, GameWorld.ticks + ticksRemaining) // Create a ground item
            gi.isRemainPrivate = true // Set the ground item to remain private
            this.items.add(gi) // Add the ground item to the list
        }

        this.transform(type.npcId) // Transform the NPC to the corresponding grave type
        this.init() // Initialize the grave
    }

    override fun tick() {
        // Grave should not do anything else on tick, that is all handled by GraveController.
        if (Repository.uid_map[ownerUid] != null) { // Check if the owner exists in the repository
            val p = Repository.uid_map[ownerUid] ?: return // Get the player from the repository
            registerHintIcon(p, this) // Register the hint icon for the player
        }
    }

    /**
     * Add time to the grave.
     *
     * @param ticks The number of ticks to add to the remaining time.
     */
    fun addTime(ticks: Int) {
        ticksRemaining += ticks // Increase the remaining ticks
        for (gi in items) { // Iterate through the ground items
            gi.decayTime = ticksRemaining // Update the decay time for each item
        }
        if (ticksRemaining < 30) // Check if remaining time is less than 30 ticks
            transform(type.npcId + 2) // Transform to a different NPC type
        else if (ticksRemaining < 90) // Check if remaining time is less than 90 ticks
            transform(type.npcId + 1) // Transform to another NPC type
        else
            transform(type.npcId) // Transform back to the original NPC type
    }

    /**
     * Collapse the grave.
     *
     */
    fun collapse() {
        for (item in items) { // Iterate through the ground items
            GroundItemManager.destroy(item) // Destroy each ground item
        }
        clear() // Clear the grave
        GraveController.activeGraves.remove(ownerUid) // Remove the grave from active graves
        if (Repository.uid_map[ownerUid] != null) { // Check if the owner exists in the repository
            val p = Repository.uid_map[ownerUid] ?: return // Get the player from the repository
            clearHintIcon(p) // Clear the hint icon for the player
        }
    }

    /**
     * Demolish the grave.
     *
     */
    fun demolish() {
        val owner = Repository.uid_map[ownerUid] ?: return // Get the owner from the repository
        for (item in items) { // Iterate through the ground items
            if (!item.isRemoved) // Check if the item is not removed
                item.decayTime = secondsToTicks(45) // Set decay time for the item
        }
        clear() // Clear the grave
        sendMessage(owner, "It looks like it'll last another ${getFormattedTimeRemaining()}.") // Notify owner of remaining time
        sendMessage(owner, "You demolish it anyway.") // Notify owner of demolition
        GraveController.activeGraves.remove(ownerUid) // Remove the grave from active graves
        clearHintIcon(owner) // Clear the hint icon for the owner
    }

    /**
     * Get items associated with the grave.
     *
     * @return An array of GroundItem objects.
     */
    fun getItems(): Array<GroundItem> {
        return this.items.toTypedArray() // Return the list of ground items as an array
    }

    /**
     * Retrieve formatted text for the grave.
     *
     * @return A formatted string with the owner's username and remaining time.
     */
    fun retrieveFormattedText(): String {
        return type.text
            .replace("@name", ownerUsername) // Replace placeholder with owner's username
            .replace("@mins", getFormattedTimeRemaining()) // Replace placeholder with formatted remaining time
    }

    /**
     * Get formatted time remaining.
     *
     * @return A string representing the remaining time in a human-readable format.
     */
    fun getFormattedTimeRemaining(): String {
        val seconds = ticksToSeconds(ticksRemaining) // Convert ticks to seconds
        val timeQty = if (seconds / 60 > 0) seconds / 60 else seconds // Determine time quantity
        val timeUnit = (if (seconds / 60 > 0) "minute" else "second") + if (timeQty > 1) "s" else "" // Determine time unit
        return "$timeQty $timeUnit" // Return formatted time string
    }
}