package core

import core.ServerStore.Companion.getInt
import org.json.simple.JSONObject

/**
 * Global stats
 */
object GlobalStatistics {

    @JvmStatic
    fun incrementDeathCount() {
        getDailyDeathArchive()["players"] = getDailyDeathArchive().getInt("players") + 1
    }

    @JvmStatic
    fun incrementDailyCowDeaths() {
        getDailyDeathArchive()["lumbridge-cows"] = getDailyDeathArchive().getInt("lumbridge-cows") + 1
    }

    @JvmStatic
    fun incrementGuardPickpockets() {
        getGuardPickpocketArchive()["count"] = getGuardPickpocketArchive().getInt("count") + 1
    }

    @JvmStatic
    fun getDailyGuardPickpockets(): Int {
        return getGuardPickpocketArchive().getInt("count")
    }

    fun getDailyDeaths(): Int {
        return getDailyDeathArchive().getInt("players")
    }

    @JvmStatic
    fun getDailyCowDeaths(): Int {
        return getDailyDeathArchive().getInt("lumbridge-cows")
    }

    private fun getDailyDeathArchive(): JSONObject {
        return ServerStore.getArchive("daily-deaths-global")
    }

    private fun getGuardPickpocketArchive(): JSONObject {
        return ServerStore.getArchive("daily-guard-pickpockets")
    }

}