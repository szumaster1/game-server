package core.tools

import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.terminal.Terminal
import core.Configuration
import core.api.log
import core.game.world.GameWorld
import java.text.SimpleDateFormat
import java.util.*

object SystemLogger {
    val t = Terminal()
    val errT = t.forStdErr()
    val formatter = SimpleDateFormat("HH:mm:ss")

    // Function returns the current time in the format [HH:mm:ss]
    private fun getTime(): String {
        return "[" + formatter.format(Date(System.currentTimeMillis())) + "]"
    }

    @JvmStatic
    fun processLogEntry(clazz: Class<*>, log: Log, message: String) {
        when (log) {
            Log.DEBUG -> {
                if (GameWorld.settings?.isDevMode != true)
                    return
                val msg = TextColors.cyan("${getTime()}: [${clazz.simpleName}] $message")
                t.println(msg)
            }

            Log.FINE -> {
                if (Configuration.LOG_LEVEL < LogLevel.VERBOSE)
                    return
                val msg = TextColors.gray("${getTime()}: [${clazz.simpleName}] $message")
                t.println(msg)
            }

            Log.INFO -> {
                if (Configuration.LOG_LEVEL < LogLevel.DETAILED)
                    return

                val msg = "${getTime()}: [${clazz.simpleName}] $message"
                t.println(msg)
            }

            Log.WARN -> {
                if (Configuration.LOG_LEVEL < LogLevel.CAUTIOUS)
                    return

                val msg = TextColors.yellow("${getTime()}: [${clazz.simpleName}] $message")
                t.println(msg)
            }

            Log.ERR -> {
                val msg = "${getTime()}: [${clazz.simpleName}] $message"
                errT.println(msg)
            }
        }
    }

    @JvmStatic
    fun logGE(message: String) {
        log(this::class.java, Log.FINE, "[  GE] $message")
    }

    @JvmStatic
    fun logStartup(message: String) {
        log(this::class.java, Log.FINE, "[STARTUP] $message")
    }

    @JvmStatic
    fun logShutdown(message: String) {
        log(this::class.java, Log.FINE, "[SHUTDOWN] $message")
    }

    fun logMS(s: String) {
        log(this::class.java, Log.FINE, "[  MS] $s")
    }
}

enum class LogLevel {
    SILENT,
    CAUTIOUS,
    DETAILED,
    VERBOSE
}

enum class Log {
    FINE,
    INFO,
    WARN,
    ERR,
    DEBUG
}