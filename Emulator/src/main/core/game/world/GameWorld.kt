package core.game.world

import core.Configuration
import core.ServerStore
import core.api.*
import core.auth.Auth
import core.auth.AuthProvider
import core.cache.Cache
import core.cache.def.impl.SceneryDefinition
import core.game.node.entity.player.Player
import core.game.system.SystemManager
import core.game.system.SystemState
import core.game.system.config.ConfigParser
import core.game.system.task.Pulse
import core.game.system.task.TaskExecutor
import core.game.world.map.Location
import core.game.world.map.Region
import core.game.world.map.RegionManager
import core.game.world.repository.Repository
import core.plugin.PluginManager
import core.plugin.type.StartupPlugin
import core.storage.AccountStorageProvider
import core.tools.Log
import core.tools.RandomFunction
import core.worker.MajorUpdateWorker
import java.text.SimpleDateFormat
import java.util.*
import java.util.function.Consumer

object GameWorld {

    @JvmStatic val worldPersists = ArrayList<PersistWorld>()
    @JvmStatic val majorUpdateWorker = MajorUpdateWorker()
    @JvmStatic val loginListeners = ArrayList<LoginListener>()
    @JvmStatic val logoutListeners = ArrayList<LogoutListener>()
    @JvmStatic val tickListeners = ArrayList<TickListener>()
    @JvmStatic val startupListeners = ArrayList<StartupListener>()
    @JvmStatic val shutdownListeners = ArrayList<ShutdownListener>()
    @JvmStatic val STARTUP_PLUGINS: List<StartupPlugin> = ArrayList()

    private val configParser = ConfigParser()

    @JvmStatic var PCBotsSpawned = false
    @JvmStatic var PCnBotsSpawned = false
    @JvmStatic var PCiBotsSpawned = false

    @JvmStatic var settings: GameSettings? = null

    @JvmStatic
    val authenticator: AuthProvider<*>
        get() = Auth.authenticator

    @JvmStatic
    val accountStorage: AccountStorageProvider
        get() = Auth.storageProvider

    @JvmStatic var ticks = 0
    @JvmStatic var Pulser = PulseRunner()

    /*
     * Submits a pulse.
     */
    @Deprecated("", ReplaceWith("Pulser.submit(pulse!!)", "core.game.world.GameWorld.Pulser"))
    fun submit(pulse: Pulse?) {
        Pulser.submit(pulse!!)
    }

    fun pulse() {
        ticks++
        if (ticks % 50 == 0) {
            TaskExecutor.execute {
                val player = Repository.players
                try {
                    player.stream().filter { obj: Player? -> Objects.nonNull(obj) }
                        .filter { p: Player -> !p.isArtificial && p.isPlaying }
                        .forEach { p: Player? -> Repository.disconnectionQueue.save(p!!, false) }
                } catch (t: Throwable) {
                    t.printStackTrace()
                }
            }
        }
    }

    fun checkDay(): Int {
        val weeklySdf = SimpleDateFormat("u")
        return weeklySdf.format(Date()).toInt()
    }

    /*
     * Prompts the [GameWorld] to begin its initialization.
     */
    @Throws(Throwable::class)
    fun prompt(directory: String?) {
        prompt(true, directory)
    }

    /*
     * Prompts the game world.
     */
    @Throws(Throwable::class)
    @JvmStatic
    fun prompt(running: Boolean) {
        prompt(running, "server.properties")
    }

    /*
     * Prompts the [GameWorld] to begin its initialization.
     */
    @Throws(Throwable::class)
    fun prompt(run: Boolean, directory: String?) {
        log(GameWorld::class.java, Log.FINE, "Prompting ${settings?.name} Game World...")
        Cache.init(Configuration.CACHE_PATH)
        /*
         * Go overboard with checks to make sure
         * dev mode authenticator never triggers on live.
         */
        Auth.configure()
        ConfigParser().prePlugin()
        PluginManager.scanClasspath()
        PluginManager.loadPureInterfaces()
        PluginManager.loadTimers()
        val s = worldPersists.filterIsInstance<ServerStore>().first()
        s.parse()
        worldPersists.filter { it !is ServerStore }.forEach { it.parse() }
        PluginManager.loadSideEffectfulPlugins()
        configParser.postPlugin()
        startupListeners.forEach { it.startup() }
        if (run) {
            SystemManager.flag(if (settings?.isDevMode == true) SystemState.PRIVATE else SystemState.ACTIVE)
        }
        SceneryDefinition.getDefinitions().values.forEach(Consumer { obj: SceneryDefinition -> obj.examine })

        if (Configuration.PRELOAD_MAP) {
            /*
             * Force early loading of all commonly accessed regions
             * to improve performance at the cost of memory usage.
             */
            (7483..15420).forEach { id -> RegionManager.forId(id).also { Region.load(it) } }
        }

        System.gc()
    }

    /*
     * Called when the server shuts down.
     */
    @Throws(Throwable::class)
    fun shutdown() {
        SystemManager.flag(SystemState.TERMINATED)
    }

    @JvmStatic
    val isEconomyWorld: Boolean
        get() = false

    private fun generateLocation(): Location {
        val randomLocation = Location(3075 + RandomFunction.random(-15, 15), 3954 + RandomFunction.random(-15, 15), 0)
        if (!RegionManager.isTeleportPermitted(randomLocation)) {
            return generateLocation()
        }
        return if (RegionManager.getObject(randomLocation) != null) {
            generateLocation()
        } else randomLocation
    }
}
