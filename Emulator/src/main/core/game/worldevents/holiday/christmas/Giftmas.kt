package core.game.worldevents.holiday.christmas

import core.ServerStore
import core.ServerStore.Companion.getBoolean
import core.ServerStore.Companion.getInt
import core.api.*
import cfg.consts.Items
import core.api.utils.WeightBasedTable
import core.api.utils.WeightedItem
import core.game.event.EventHook
import core.game.event.XPGainEvent
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.system.command.Privilege
import core.game.world.repository.Repository
import core.game.worldevents.WorldEvents
import core.tools.RandomFunction
import core.tools.colorize
import org.json.simple.JSONObject

/**
 * Handle various events and giftmas commands.
 * @author Zerken
 */
class Giftmas : Commands, StartupListener, LoginListener, InteractionListener {
    // Method called during startup to initialize the event if active
    override fun startup() {
        if (checkActive()) // Check if the event is active
            init() // Initialize the event
    }

    /**
     * Check active
     *
     * @return Boolean indicating if the event is active
     */
    fun checkActive(): Boolean {
        val archive = getArchive() // Retrieve the event archive
        return archive.getBoolean("active") // Return the active status
    }

    // Method called when a player logs in
    override fun login(player: Player) {
        if (!checkActive()) return // Exit if the event is not active
        player.hook(Event.XpGained, XpGainHook) // Hook the XP gain event for the player
    }

    fun init() {
        try {
            // Set up interaction for opening the mystery box
            on(Items.MYSTERY_BOX_6199, IntType.ITEM, "open") { player, node ->
                val loot = MBOX_LOOT.roll().first() // Roll for loot from the mystery box

                if (!removeItem(player, node.asItem())) return@on true // Remove the item if possible

                // Send dialogue to the player about the loot received
                sendDialogue(player, "You open the mystery box and find ${loot.amount}x ${loot.name.lowercase()}!")
                addItem(player, loot.id, loot.amount) // Add the loot to the player's inventory
                return@on true // Return true to indicate successful handling
            }
        } catch (ignored: Exception) {
            // Catch and ignore any exceptions that occur during initialization
        }

        // Hook all players to the XP gain event
        for (player in Repository.players)
            player.hook(Event.XpGained, XpGainHook) // Hook the XP gain event for each player
    }

    fun cleanup() {
        // Unhook all players from the XP gain event
        for (player in Repository.players)
            player.unhook(XpGainHook) // Unhook the XP gain event for each player
    }

    // Define commands related to the event
    override fun defineCommands() {
        define("toggle-giftmas", Privilege.ADMIN, "", "Toggles the giftmas christmas event.") { player, _ ->
            val enabled = checkActive() // Check if the event is currently active
            getArchive()["active"] = !enabled // Toggle the active status
            notify(player, "Giftmas is now ${if (enabled) "DISABLED" else "ENABLED"}.") // Notify the player of the change
            if (!enabled) {
                init() // Initialize if the event is now enabled
            } else {
                cleanup() // Clean up if the event is now disabled
            }
        }
    }

    object XpGainHook : EventHook<XPGainEvent> {
        // Process XP gain events
        override fun process(entity: Entity, event: XPGainEvent) {
            val wasCombat = event.skillId in 0..6 // Determine if the XP gain was from combat
            val daily = getDailyGifts(entity.asPlayer(), wasCombat) // Get the daily gifts for the player
            val player = entity.asPlayer() // Cast entity to player
            val cooldown = entity.getAttribute("christmas-cooldown", 0L) // Get the cooldown attribute

            if (event.amount < 25.0) return // Exit if the XP gained is less than 25
            if (!RandomFunction.roll(15)) return // Randomly determine if the event should proceed
            if (daily >= if (wasCombat) DAILY_LIMIT_COMBAT else DAILY_LIMIT_SKILLING) return // Check daily limit
            if (System.currentTimeMillis() < cooldown) return // Check if the cooldown has expired
            if (!addItem(player, Items.MYSTERY_BOX_6199)) return // Attempt to add a mystery box to the player's inventory

            player.setAttribute("/save:christmas-cooldown", System.currentTimeMillis() + 5000L) // Set cooldown for the player
            incrementDailyGifts(player, wasCombat) // Increment the daily gifts for the player
            sendMessage(player, MESSAGE_PRESENT_GRANTED) // Notify the player that a present has been granted

            // Notify the player if they have reached their daily limit for combat or skilling
            if (wasCombat && daily == DAILY_LIMIT_COMBAT - 1)
                sendMessage(player, MESSAGE_DAILYXP_REACHED_COMBAT)
            if (!wasCombat && daily == DAILY_LIMIT_SKILLING - 1)
                sendMessage(player, MESSAGE_DAILYXP_REACHED_SKILLING)
        }

        // Get the number of daily gifts for the player
        private fun getDailyGifts(player: Player, wasCombat: Boolean): Int {
            val archive = if (wasCombat) "daily-xmas-gifts-combat" else "daily-xmas-gifts-skilling" // Determine the archive based on combat or skilling
            return ServerStore.getArchive(archive).getInt(player.name) // Return the number of daily gifts
        }

        // Increment the daily gifts for the player
        fun incrementDailyGifts(player: Player, wasCombat: Boolean) {
            val start = getDailyGifts(player, wasCombat) // Get the current number of daily gifts
            val archive = if (wasCombat) "daily-xmas-gifts-combat" else "daily-xmas-gifts-skilling" // Determine the archive
            ServerStore.getArchive(archive)[player.name] = start + 1 // Increment the daily gifts count
        }
    }

    override fun defineListeners() {
        // Define any additional listeners if necessary
    }

    companion object {
        private val DAILY_LIMIT_SKILLING = 15 // Daily limit for skilling gifts
        private val DAILY_LIMIT_COMBAT = 5 // Daily limit for combat gifts

        // Retrieve the event archive
        private fun getArchive(): JSONObject {
            val mainArchive = WorldEvents.getArchive() // Get the main event archive
            if (!mainArchive.containsKey("giftmas")) // Check if the giftmas key exists
                mainArchive["giftmas"] = JSONObject() // Create a new JSONObject for giftmas
            return mainArchive["giftmas"] as JSONObject // Return the giftmas archive
        }

        private val MESSAGE_DAILYXP_REACHED_SKILLING =
            colorize("%RYou have reached your daily limit of presents from skilling!") // Message for skilling limit reached
        private val MESSAGE_DAILYXP_REACHED_COMBAT =
            colorize("%RYou have reached your daily limit of presents from combat!") // Message for combat limit reached
        private val MESSAGE_PRESENT_GRANTED = colorize("%GYou find a present while training!") // Message for present granted
        val MBOX_LOOT = WeightBasedTable.create( // Create a weighted table for mystery box loot
            WeightedItem(Items.TOY_HORSEY_2520, 1, 1, 0.025),
            WeightedItem(Items.TOY_HORSEY_2522, 1, 1, 0.025),
            WeightedItem(Items.TOY_HORSEY_2524, 1, 1, 0.025),
            WeightedItem(Items.TOY_HORSEY_2526, 1, 1, 0.025),
            WeightedItem(Items.TOY_KITE_12844, 1, 1, 0.025),
            WeightedItem(Items.COAL_453, 1, 1, 0.025),
            WeightedItem(Items.MOLTEN_GLASS_1776, 25, 50, 0.25),
            WeightedItem(Items.FLAX_1780, 15, 70, 0.25),
            WeightedItem(Items.BOW_STRING_1778, 10, 50, 0.15),
            WeightedItem(Items.UNCUT_SAPPHIRE_1624, 1, 5, 0.15),
            WeightedItem(Items.UNCUT_EMERALD_1622, 1, 5, 0.15),
            WeightedItem(Items.UNCUT_RUBY_1620, 1, 5, 0.15),
            WeightedItem(Items.UNCUT_DIAMOND_1618, 1, 5, 0.15),
            WeightedItem(Items.UNCUT_SAPPHIRE_1624, 100, 100, 0.0015),
            WeightedItem(Items.UNCUT_EMERALD_1622, 100, 100, 0.0015),
            WeightedItem(Items.UNCUT_RUBY_1620, 100, 100, 0.0015),
            WeightedItem(Items.UNCUT_DIAMOND_1618, 100, 100, 0.0015),
            WeightedItem(Items.PURE_ESSENCE_7937, 1, 50, 0.15),
            WeightedItem(Items.PURE_ESSENCE_7937, 1000, 1000, 0.0015),
            WeightedItem(Items.RANARR_SEED_5295, 1, 3, 0.065),
            WeightedItem(Items.SNAPDRAGON_SEED_5300, 1, 3, 0.065),
            WeightedItem(Items.GOLD_CHARM_12158, 1, 15, 0.15),
            WeightedItem(Items.CRIMSON_CHARM_12160, 1, 15, 0.15),
            WeightedItem(Items.BLUE_CHARM_12163, 1, 15, 0.15),
            WeightedItem(Items.GREEN_CHARM_12159, 1, 15, 0.15),
            WeightedItem(Items.PURPLE_SWEETS_10476, 1, 15, 0.25),
            WeightedItem(Items.COINS_995, 100, 1000, 0.15),
            WeightedItem(Items.COINS_995, 50000, 100000, 0.0015),
            WeightedItem(Items.COINS_995, 1000000, 1000000, 0.0005),
            WeightedItem(Items.NATURE_RUNE_561, 1, 10, 0.15),
            WeightedItem(Items.ABYSSAL_WHIP_4151, 1, 1, 0.00005),
            WeightedItem(Items.SANTA_HAT_1050, 1, 1, 0.00005),
        ).insertEasyClue(0.015).insertMediumClue(0.010).insertHardClue(0.005).insertRDTRoll(0.015) // Insert clues into the loot table
    }
}