package core.game.system.config

import core.api.Commands
import core.game.node.item.GroundItemManager
import core.game.system.command.Privilege
import core.game.world.map.RegionManager
import core.game.world.repository.Repository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Config parser
 *
 * @constructor Config parser
 */
class ConfigParser : Commands {
    /**
     * Pre plugin
     *
     */
    fun prePlugin() {
        NPCConfigParser().load()
        ItemConfigParser().load()
        ObjectConfigParser().load()
        XteaParser().load()
        InterfaceConfigParser().load()
    }

    /**
     * Post plugin
     *
     */
    fun postPlugin() {
        ShopParser().load()
        DropTableParser().load()
        NPCSpawner().load()
        DoorConfigLoader().load()
        GroundSpawnLoader().load()
        MusicConfigLoader().load()
        RangedConfigLoader().load()
        CustomVarbitParser().load()
        ClueRewardParser().load()
    }

    /**
     * Reload configs
     *
     * @param callback
     * @receiver
     */
    fun reloadConfigs(callback: () -> Unit) {
        GlobalScope.launch {
            Repository.npcs.toTypedArray().forEach { npc ->
                npc.isRespawn = false
                npc.clear()
                Repository.npcs.remove(npc)
                Repository.removeRenderableNPC(npc)
            }

            GroundItemManager.getItems().toTypedArray().forEach { gi ->
                GroundItemManager.getItems().remove(gi)
                RegionManager.getRegionPlane(gi.location).remove(gi)
            }

            prePlugin()
            postPlugin()

            callback.invoke()
        }
    }

    override fun defineCommands() {
        define("reloadjson", Privilege.ADMIN, "", "Reloads all the JSON configs.") { player, _ ->
            notify(player, "Reloading JSON...")
            reloadConfigs { notify(player, "JSON reloaded.") }
        }
    }
}
