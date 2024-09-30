package core.game.system.config

import com.moandjiezana.toml.Toml
import core.Configuration
import core.api.log
import core.api.parseEnumEntry
import core.game.world.GameSettings
import core.game.world.GameWorld
import core.game.world.map.Location
import core.integration.mysql.Database
import core.tools.Log
import core.tools.LogLevel
import java.io.File
import java.net.URL
import kotlin.system.exitProcess

/**
 * Class for parsing the server config, I.E default.toml
 * @param path the path to the TOML file to parse.
 */
object ServerConfigParser {
    var confFile: File? = null
    var tomlData: Toml? = null

    fun parse(path: String) {
        confFile = File(parsePath(path))
        parseFromFile(confFile)
    }

    fun parse(path: URL?) {
        confFile = File(path!!.toURI())
        parseFromFile(confFile)
    }

    private fun parseFromFile(confFile: File?) {
        if (!confFile!!.canonicalFile.exists()) {
            log(this::class.java, Log.ERR, "${confFile.canonicalFile} does not exist.")
            exitProcess(0)
        } else {
            try {
                tomlData = Toml().read(confFile)
                parseServerSettings()
                parseGameSettings()
                val jvmString = System.getProperty("java.version")
                if (jvmString.startsWith("1.")) {
                    Configuration.JAVA_VERSION = jvmString.substring(2, 3).toInt()
                } else if (!jvmString.startsWith("1.")) {
                    Configuration.JAVA_VERSION = jvmString.substring(0, 2).toInt()
                } else if (!jvmString.contains(".")) {
                    Configuration.JAVA_VERSION = jvmString.toInt()
                }
                log(
                    this::class.java,
                    Log.FINE,
                    "It seems we are in a Java ${Configuration.JAVA_VERSION} environment."
                )
            } catch (e: java.lang.IllegalStateException) {
                log(this::class.java, Log.ERR, "Passed config file is not a TOML file. Path: ${confFile.canonicalPath}")
                log(this::class.java, Log.ERR, "Exception received: $e")
                exitProcess(0)
            }
        }
    }

    private fun parseGameSettings() {
        tomlData ?: return
        val data = tomlData!!

        GameWorld.settings = GameSettings(
            name = Configuration.SERVER_NAME,
            isBeta = data.getBoolean("world.debug"),
            isDevMode = data.getBoolean("world.dev"),
            isGui = data.getBoolean("world.start_gui"),
            worldId = data.getString("world.world_id").toInt(),
            countryIndex = data.getString("world.country_id").toInt(),
            activity = data.getString("world.activity"),
            isMembers = data.getBoolean("world.members"),
            isPvp = data.getBoolean("world.pvp"),
            isQuickChat = false,
            isLootshare = false,
            msAddress = data.getString("server.msip"),
            default_xp_rate = data.getDouble("world.default_xp_rate"),
            enable_default_clan = data.getBoolean("world.enable_default_clan"),
            enable_bots = data.getBoolean("world.enable_bots"),
            autostock_ge = data.getBoolean("world.autostock_ge"),
            allow_token_purchase = data.getBoolean("world.allow_token_purchase"),
            skillcape_perks = data.getBoolean("world.skillcape_perks"),
            increased_door_time = data.getBoolean("world.increased_door_time"),
            enabled_botting = data.getBoolean("world.enable_botting"),
            max_adv_bots = data.getLong("world.max_adv_bots").toInt(),
            enable_doubling_money_scammers = data.getBoolean("world.enable_doubling_money_scammers", false),
            wild_pvp_enabled = data.getBoolean("world.wild_pvp_enabled"),
            jad_practice_enabled = data.getBoolean("world.jad_practice_enabled"),
            ge_announcement_limit = data.getLong("world.ge_announcement_limit", 500L).toInt(),
            smartpathfinder_bfs = data.getBoolean("world.smartpathfinder_bfs", false),
            enable_castle_wars = data.getBoolean("world.enable_castle_wars", false),
            message_model = data.getString("world.motw_identifier").toInt(),
            message_string = data.getString("world.motw_text").replace("@name", Configuration.SERVER_NAME)
        )
    }

    private fun parseServerSettings() {
        tomlData ?: return
        val data = tomlData!!

        Configuration.DATA_PATH = data.getString("paths.data_path")
        Configuration.WRITE_LOGS = data.getBoolean("server.write_logs")
        Configuration.DATABASE_NAME = data.getString("database.database_name")
        Configuration.DATABASE_USER = data.getString("database.database_username")
        Configuration.DATABASE_PASS = data.getString("database.database_password")
        Configuration.DATABASE_ADDRESS = data.getString("database.database_address")
        Configuration.DATABASE_PORT = data.getString("database.database_port")
        Configuration.DATABASE = Database(Configuration.DATABASE_ADDRESS + ":" + Configuration.DATABASE_PORT, Configuration.DATABASE_NAME, Configuration.DATABASE_USER, Configuration.DATABASE_PASS)
        Configuration.CACHE_PATH = data.getPath("paths.cache_path")
        Configuration.CONFIG_PATH = data.getPath("paths.configs_path")
        Configuration.PLAYER_SAVE_PATH = data.getPath("paths.save_path")
        Configuration.STORE_PATH = data.getPath("paths.store_path")
        Configuration.RDT_DATA_PATH = data.getPath("paths.rare_drop_table_path")
        Configuration.OBJECT_PARSER_PATH = data.getPath("paths.object_parser_path")
        Configuration.LOGS_PATH = data.getPath("paths.logs_path")
        Configuration.SERVER_NAME = data.getPath("world.name")
        Configuration.BOT_DATA_PATH = data.getPath("paths.bot_data")
        Configuration.MS_SECRET_KEY = data.getString("server.secret_key")
        Configuration.HOME_LOCATION = parseLocation(data.getString("world.home_location"))
        Configuration.START_LOCATION = parseLocation(data.getString("world.new_player_location"))
        Configuration.DAILY_RESTART = data.getBoolean("world.daily_restart")
        Configuration.LOG_CUTSCENE = data.getBoolean("world.verbose_cutscene", false)
        Configuration.GRAND_EXCHANGE_DATA_PATH = data.getPath("paths.eco_data")
        Configuration.CELEDT_DATA_PATH = data.getPath("paths.cele_drop_table_path")
        Configuration.USDT_DATA_PATH = data.getPath("paths.uncommon_seed_drop_table_path")
        Configuration.HDT_DATA_PATH = data.getPath("paths.herb_drop_table_path")
        Configuration.GDT_DATA_PATH = data.getPath("paths.gem_drop_table_path")
        Configuration.RSDT_DATA_PATH = data.getPath("paths.rare_seed_drop_table_path")
        Configuration.ASDT_DATA_PATH = data.getPath("paths.allotment_seed_drop_table_path")
        Configuration.SERVER_GE_NAME = data.getString("world.name_ge") ?: Configuration.SERVER_NAME
        Configuration.BOTS_INFLUENCE_PRICE_INDEX = data.getBoolean("world.bots_influence_ge_price", true)
        Configuration.PRELOAD_MAP = data.getBoolean("server.preload_map", false)
        Configuration.REVENANT_POPULATION = data.getLong("world.revenant_population", 30L).toInt()
        Configuration.BANK_BOOTH_QUICK_OPEN = data.getBoolean("world.bank_booth_quick_open", false)
        Configuration.BANK_BOOTH_NOTE_ENABLED = data.getBoolean("world.bank_booth_note_enabled", true)
        Configuration.BANK_BOOTH_NOTE_UIM = data.getBoolean("world.bank_booth_note_uim", true)
        Configuration.DISCORD_GE_WEBHOOK = data.getString("integrations.discord_ge_webhook", "")
        Configuration.WATCHDOG_ENABLED = data.getBoolean("server.watchdog_enabled", true)
        Configuration.I_AM_A_CHEATER = data.getBoolean("world.i_want_to_cheat", false)
        Configuration.USE_AUTH = data.getBoolean("server.use_auth", true)
        Configuration.PERSIST_ACCOUNTS = data.getBoolean("server.persist_accounts", true)
        Configuration.DAILY_ACCOUNT_LIMIT = data.getLong("server.daily_accounts_per_ip", 3L).toInt()
        Configuration.DISCORD_MOD_WEBHOOK = data.getString("integrations.discord_moderation_webhook", "")
        Configuration.NOAUTH_DEFAULT_ADMIN = data.getBoolean("server.noauth_default_admin", false)
        Configuration.DRAGON_AXE_USE_OSRS_SPEC = data.getBoolean("world.dragon_axe_use_osrs_spec", false)
        Configuration.DISCORD_OPENRSC_HOOK = data.getString("integrations.openrsc_integration_webhook", "")
        Configuration.ENABLE_GLOBALCHAT = data.getBoolean("world.enable_globalchat", true)
        Configuration.MAX_PATHFIND_DISTANCE = data.getLong("server.max_pathfind_dist", 25L).toInt()
        Configuration.IRONMAN_ICONS = data.getBoolean("world.ironman_icons", false)
        Configuration.PLAYER_STOCK_CLEAR_INTERVAL = data.getLong("world.playerstock_clear_mins", 180L).toInt()
        Configuration.PLAYER_STOCK_RECIRCULATE = data.getBoolean("world.playerstock_bot_offers", true)
        Configuration.BOTSTOCK_LIMIT = data.getLong("world.botstock_limit", 5000L).toInt()
        Configuration.BETTER_AGILITY_PYRAMID_GP = data.getBoolean("world.better_agility_pyramid_gp", true)
        Configuration.GRAFANA_PATH = data.getPath("integrations.grafana_log_path")
        Configuration.GRAFANA_LOGGING = data.getBoolean("integrations.grafana_logging", false)
        Configuration.GRAFANA_TTL_DAYS = data.getLong("integrations.grafana_log_ttl_days", 7L).toInt()
        Configuration.BETTER_DFS = data.getBoolean("world.better_dfs", true)
        Configuration.NEW_PLAYER_ANNOUNCEMENT = data.getBoolean("world.new_player_announcement", true)
        Configuration.HOLIDAY_EVENT_RANDOMS = data.getBoolean("world.holiday_event_randoms", true)
        Configuration.FORCE_HALLOWEEN_EVENTS = data.getBoolean("world.force_halloween_randoms", false)
        Configuration.FORCE_CHRISTMAS_EVENTS = data.getBoolean("world.force_christmas_randoms", false)
        Configuration.FORCE_EASTER_EVENTS = data.getBoolean("world.force_easter_randoms", false)
        Configuration.RUNECRAFTING_FORMULA_REVISION = data.getLong("world.runecrafting_formula_revision", 581).toInt()

        val logLevel = data.getString("server.log_level", "VERBOSE").uppercase()
        Configuration.LOG_LEVEL = parseEnumEntry<LogLevel>(logLevel) ?: LogLevel.VERBOSE
    }


    private fun Toml.getPath(key: String): String {
        try {
            return parsePath(getString(key, "@data/").replace("@data", Configuration.DATA_PATH!!))
        } catch (e: Exception) {
            log(this::class.java, Log.ERR, "Error parsing key: $key")
            exitProcess(0)
        }
    }

    /**
     * Parses a location from the format "x,y,z".
     * @param locString The string to parse
     * @return Location
     */
    fun parseLocation(locString: String): Location {
        val locTokens = locString.split(",").map { it.toInt() }
        return Location(locTokens[0], locTokens[1], locTokens[2])
    }

    /**
     * Parses a path string
     * @param pathString The string to parse
     * @return a String with the proper file separators for the current OS.
     */
    fun parsePath(pathString: String): String {
        var pathTokens: List<String>? = null
        if (pathString.contains("/")) pathTokens = pathString.split("/")
        else if (pathString.contains("\\")) pathTokens = pathString.split("\\")

        pathTokens ?: return pathString //return the initial pathString if path does not contain file separators.
        var pathProduct = ""
        for (token in pathTokens) {
            if (token != "")
                pathProduct += "$token${File.separator}"
        }

        return pathProduct
    }
}
