package content.global.handlers.item.plugin

import org.rs.consts.Components
import org.rs.consts.Items
import org.rs.consts.NPCs
import org.rs.consts.Sounds
import core.api.playAudio
import core.api.toIntArray
import core.cache.def.impl.ItemDefinition
import core.game.component.Component
import core.game.component.ComponentDefinition
import core.game.component.ComponentPlugin
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.GameWorld
import core.plugin.PluginManager
import core.plugin.Initializable
import core.plugin.Plugin
import core.tools.RandomFunction

/**
 * Morphing plugin.
 */
@Initializable
class MorphingPlugin : Plugin<Any> {

    // Define Easter egg NPC IDs as a range and convert to an array
    private val EASTER_EGG_IDS = (NPCs.EGG_3689..NPCs.EGG_3694).toIntArray()

    // Create a component for unmorphing with a close event
    private val COMPONENT = Component(Components.UNMORPH_375).setCloseEvent { player, c ->
        unmorph(player)
        return@setCloseEvent true
    }

    // Create a new instance of the plugin and set up item handlers
    override fun newInstance(arg: Any?): Plugin<Any> {
        // Assign this plugin as the handler for specific items
        ItemDefinition.forId(Items.EASTER_RING_7927).handlers["equipment"] = this
        ItemDefinition.forId(Items.RING_OF_STONE_6583).handlers["equipment"] = this
        PluginManager.definePlugin(MorphInterfacePlugin())
        return this
    }

    // Handle events triggered by the plugin
    override fun fireEvent(identifier: String, vararg args: Any): Any {
        val player = args[0] as Player
        val item = args[1] as Item
        when (identifier) {
            "equip" -> {
                morph(player, item)
                return false
            }
        }
        return true // Return true if the event was not handled
    }

    // Function to morph the player into a different NPC
    private fun morph(player: Player, item: Item) {
        val morphId = if (item.id == Items.RING_OF_STONE_6583) NPCs.ROCKS_2626 else EASTER_EGG_IDS[RandomFunction.random(EASTER_EGG_IDS.size)]
        playAudio(player, Sounds.EASTER06_HUMAN_INTO_EGG_1520)
        player.interfaceManager.close()
        player.appearance.transformNPC(morphId)
        player.interfaceManager.removeTabs(0, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14)
        // Lock player movement, interactions, and teleportation for a duration
        player.locks.lockMovement(GameWorld.ticks + 900000000)
        player.locks.lockInteractions(GameWorld.ticks + 90000000)
        player.locks.lockTeleport(GameWorld.ticks + 900000000)
        player.interfaceManager.openSingleTab(COMPONENT)
        player.appearance.sync()
        player.walkingQueue.reset()
    }

    // Function to unmorph the player back to their original form
    private fun unmorph(player: Player) {
        player.appearance.transformNPC(-1)
        player.unlock()
        player.interfaceManager.restoreTabs()
    }

    /**
     * Morph interface plugin.
     */
    inner class MorphInterfacePlugin : ComponentPlugin() {

        // Create a new instance of the morph interface plugin
        override fun newInstance(arg: Any?): Plugin<Any> {
            ComponentDefinition.forId(Components.UNMORPH_375).plugin = this
            return this
        }

        // Handle interactions with the morph interface
        override fun handle(player: Player, component: Component, opcode: Int, button: Int, slot: Int, itemId: Int): Boolean {
            player.interfaceManager.closeSingleTab()
            return true
        }
    }
}