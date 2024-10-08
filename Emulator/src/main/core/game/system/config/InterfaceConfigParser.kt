package core.game.system.config

import core.Configuration
import core.api.log
import core.game.component.ComponentDefinition
import core.tools.Log
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.FileReader

/**
 * Interface config parser.
 */
class InterfaceConfigParser {
    val parser = JSONParser()
    var reader: FileReader? = null

    /**
     * Load door configurations from file.
     */
    fun load() {
        var count = 0
        reader = FileReader(Configuration.CONFIG_PATH + "interface_configs.json")
        val configlist = parser.parse(reader) as JSONArray
        for (config in configlist) {
            val e = config as JSONObject
            val id = e["id"].toString().toInt()
            if (ComponentDefinition.getDefinitions().containsKey(id)) {
                ComponentDefinition.getDefinitions()[id]!!.parse(
                    e["interfaceType"].toString(),
                    e["walkable"].toString(),
                    e["tabIndex"].toString()
                )
            }
            ComponentDefinition.getDefinitions()[id] = ComponentDefinition().parse(
                e["interfaceType"].toString(),
                e["walkable"].toString(),
                e["tabIndex"].toString()
            )
            count++
        }

        log(this::class.java, Log.FINE, "Parsed $count interface configs.")
    }
}
