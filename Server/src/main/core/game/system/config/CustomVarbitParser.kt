package core.game.system.config

import core.ServerConstants
import core.api.log
import core.cache.def.impl.VarbitDefinition
import core.tools.Log
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.FileReader

/**
 * Custom varbit parser.
 */
class CustomVarbitParser {
    val parser = JSONParser()
    var reader: FileReader? = null

    /**
     * Load.
     */
    fun load() {
        var count = 0

        reader = FileReader(ServerConstants.CONFIG_PATH + "varbit_definitions.json")
        val array = parser.parse(reader) as JSONArray

        for (jObject in array) {
            val varbitDef = jObject as JSONObject

            val varpId = varbitDef["varpId"].toString().toInt()
            val varbitId = varbitDef["varbitId"].toString().toInt()
            val startBit = varbitDef["startBit"].toString().toInt()
            val endBit = varbitDef["endBit"].toString().toInt()

            VarbitDefinition.create(varpId, varbitId, startBit, endBit)
            count++
        }

        log(this::class.java, Log.FINE, "Parsed $count custom varbit definitions.")
    }
}
