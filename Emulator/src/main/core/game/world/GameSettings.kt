package core.game.world

import core.Configuration
import org.json.simple.JSONObject
import java.io.FileInputStream
import java.io.IOException
import java.util.*

/**
 * Game settings class to store various game settings.
 */
class GameSettings internal constructor(
    var name: String,
    var isBeta: Boolean,
    var isDevMode: Boolean,
    var isGui: Boolean,
    var worldId: Int,
    var countryIndex: Int,
    var activity: String,
    var isMembers: Boolean,
    var isPvp: Boolean,
    var isQuickChat: Boolean,
    var isLootshare: Boolean,
    var msAddress: String,
    var default_xp_rate: Double,
    var enable_default_clan: Boolean,
    var enable_bots: Boolean,
    var autostock_ge: Boolean,
    var allow_token_purchase: Boolean,
    var skillcape_perks: Boolean,
    var increased_door_time: Boolean,
    var enabled_botting: Boolean,
    var max_adv_bots: Int,
    var enable_doubling_money_scammers: Boolean,
    var wild_pvp_enabled: Boolean,
    var jad_practice_enabled: Boolean,
    var ge_announcement_limit: Int,
    var smartpathfinder_bfs: Boolean,
    var enable_castle_wars: Boolean,

    /**
     * "Lobby" interface
     * The message of the week models to display
     * 15 & 22 = keys & lock || 16 = fly swat || 17 = person with question marks || 18 & 447 = wise old man
     * 19 = man & woman with mouth closed || 20 = man & lock & key || 21 = closed chests
     * 23 = snowmen || 405 = Construction houses || 622 = Two sets of 3 people range, mage, melee
     * 623 = Woodcutting || 679 = Summoning || 715 = Easter || 800 = Halloween
     * Any value that isn't one listed above = random selection
     */
    var message_model: Int,

    /**
     * "Lobby" interface
     * The message of the week text
     * The "child" for writing text to these interfaces is located inside of LoginConfiguration.java method: getMessageChild
     */
    var message_string: String
) {
    val isHosted: Boolean
        get() = !isDevMode

    override fun toString(): String {
        return "GameSettings [name=$name, debug=$isBeta, devMode=$isDevMode, gui=$isGui, worldId=$worldId]"
    }

    companion object {
        /**
         * Parses a JSONObject and creates a new GameSettings object from it.
         */
        fun parse(data: JSONObject): GameSettings {
            val name = Configuration.SERVER_NAME
            val debug = data["debug"] as Boolean
            val dev = data["dev"] as Boolean
            val startGui = data["startGui"] as Boolean
            val worldId = data["worldID"].toString().toInt()
            val countryId = data["countryID"].toString().toInt()
            val activity = data["activity"].toString()
            val pvpWorld = data["pvpWorld"] as Boolean
            val msip = data["msip"].toString()
            val default_xp_rate = data["default_xp_rate"].toString().toDouble()
            val enable_default_clan = data["enable_default_clan"] as Boolean
            val enable_bots = data["enable_bots"] as Boolean
            val autostock_ge = data["autostock_ge"] as Boolean
            val skillcape_perks = if (data.containsKey("skillcape_perks")) data["skillcape_perks"] as Boolean else false
            val increased_door_time = if (data.containsKey("increased_door_time")) data["increased_door_time"] as Boolean else false
            val enable_botting = if (data.containsKey("botting_enabled")) data["botting_enabled"] as Boolean else false
            val max_adv_bots = if (data.containsKey("max_adv_bots")) data["max_adv_bots"].toString().toInt() else 100
            val enable_doubling_money_scammers = if (data.containsKey("enable_doubling_money_scammers")) data["enable_doubling_money_scammers"] as Boolean else false
            val wild_pvp_enabled = if (data.containsKey("wild_pvp_enabled")) data["wild_pvp_enabled"] as Boolean else true
            val jad_practice_enabled = if (data.containsKey("jad_practice_enabled")) data["jad_practice_enabled"] as Boolean else true
            val ge_announcement_limit = data["ge_announcement_limit"].toString().toInt()
            val smartpathfinder_bfs = if (data.containsKey("smartpathfinder_bfs")) data["smartpathfinder_bfs"] as Boolean else false
            val enable_castle_wars = if (data.containsKey("enable_castle_wars")) data["enable_castle_wars"] as Boolean else false
            val allow_token_purchase = data["allow_token_purchase"] as Boolean
            val message_of_the_week_identifier = data["message_of_the_week_identifier"].toString().toInt()
            val message_of_the_week_text = data["message_of_the_week_text"].toString()
            return GameSettings(name, debug, dev, startGui, worldId, countryId, activity, true, pvpWorld, false, false, msip, default_xp_rate, enable_default_clan, enable_bots, autostock_ge, allow_token_purchase, skillcape_perks, increased_door_time, enable_botting, max_adv_bots, enable_doubling_money_scammers, wild_pvp_enabled, jad_practice_enabled, ge_announcement_limit, smartpathfinder_bfs, enable_castle_wars, message_of_the_week_identifier, message_of_the_week_text)
        }

        /**
         * Gets the properties.
         */
        private fun getProperties(path: String): Properties {
            val file: FileInputStream
            val properties = Properties()
            try {
                file = FileInputStream(path)
                properties.load(file)
            } catch (e: IOException) {
                println("Warning: Could not find file in " + System.getProperty("user.dir") + path)
                e.printStackTrace()
            }
            return properties
        }
    }
}
