package core.game.system.config

import core.api.Commands
import core.game.node.item.GroundItemManager
import core.game.system.command.Privilege
import core.game.world.map.RegionManager
import core.game.world.repository.Repository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Config parser.
 */
class ConfigParser : Commands {

    fun prePlugin() {
        // Load NPC configurations
        NPCConfigParser().load()

        // Load Item configurations
        ItemConfigParser().load()

        // Load Object configurations
        ObjectConfigParser().load()

        // Load Xtea configurations
        XteaParser().load()

        // Load Interface configurations
        InterfaceConfigParser().load()
    }

    fun postPlugin() {
        // Load Shop configurations
        ShopParser().load()

        // Load DropTable configurations
        DropTableParser().load()

        // Load NPC Spawner configurations
        NPCSpawner().load()

        // Load Door configurations
        DoorConfigLoader().load()

        // Load Ground Spawn configurations
        GroundSpawnLoader().load()

        // Load Music configurations
        MusicConfigLoader().load()

        // Load Ranged configurations
        RangedConfigLoader().load()

        // Load Custom Varbit configurations
        CustomVarbitParser().load()

        // Load Clue Reward configurations
        ClueRewardParser().load()
    }

    /**
     * Function to reload configurations.
     *
     * @param callback a lambda function to be executed after reloading configurations.
     */
    fun reloadConfigs(callback: () -> Unit) {
        GlobalScope.launch {
            // Reset NPCs
            Repository.npcs.toTypedArray().forEach { npc ->
                npc.isRespawn = false
                npc.clear()
                Repository.npcs.remove(npc)
                Repository.removeRenderableNPC(npc)
            }

            // Remove ground items
            GroundItemManager.getItems().toTypedArray().forEach { gi ->
                GroundItemManager.getItems().remove(gi)
                RegionManager.getRegionPlane(gi.location).remove(gi)
            }

            // Reload configurations
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
