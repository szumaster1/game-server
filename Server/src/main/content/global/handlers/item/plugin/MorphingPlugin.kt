package content.global.handlers.item.plugin

import core.api.consts.Components
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.consts.Sounds
import core.api.playAudio
import core.api.toIntArray
import core.cache.def.impl.ItemDefinition
import core.game.component.Component
import core.game.component.ComponentDefinition
import core.game.component.ComponentPlugin
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.GameWorld
import core.plugin.ClassScanner
import core.plugin.Initializable
import core.plugin.Plugin
import core.tools.RandomFunction

@Initializable
class MorphingPlugin : Plugin<Any> {

    private val EASTER_EGG_IDS = (NPCs.EGG_3689..NPCs.EGG_3694).toIntArray()

    private val COMPONENT = Component(Components.UNMORPH_375).setCloseEvent { player, c ->
        unmorph(player)
        return@setCloseEvent true
    }

    override fun newInstance(arg: Any?): Plugin<Any> {
        ItemDefinition.forId(Items.EASTER_RING_7927).handlers["equipment"] = this
        ItemDefinition.forId(Items.RING_OF_STONE_6583).handlers["equipment"] = this
        ClassScanner.definePlugin(MorphInterfacePlugin())
        return this
    }

    override fun fireEvent(identifier: String, vararg args: Any): Any {
        val player = args[0] as Player
        val item = args[1] as Item
        when (identifier) {
            "equip" -> {
                morph(player, item)
                return false
            }
        }
        return true
    }

    private fun morph(player: Player, item: Item) {
        val morphId = if (item.id == Items.RING_OF_STONE_6583) NPCs.ROCKS_2626 else EASTER_EGG_IDS[RandomFunction.random(EASTER_EGG_IDS.size)]
        playAudio(player, Sounds.EASTER06_HUMAN_INTO_EGG_1520)
        player.interfaceManager.close()
        player.appearance.transformNPC(morphId)
        player.interfaceManager.removeTabs(0, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14)
        player.locks.lockMovement(GameWorld.ticks + 900000000)
        player.locks.lockInteractions(GameWorld.ticks + 90000000)
        player.locks.lockTeleport(GameWorld.ticks + 900000000)
        player.interfaceManager.openSingleTab(COMPONENT)
        player.appearance.sync()
        player.walkingQueue.reset()
    }

    private fun unmorph(player: Player) {
        player.appearance.transformNPC(-1)
        player.unlock()
        player.interfaceManager.restoreTabs()
    }

    inner class MorphInterfacePlugin : ComponentPlugin() {

        override fun newInstance(arg: Any?): Plugin<Any> {
            ComponentDefinition.forId(Components.UNMORPH_375).plugin = this
            return this
        }

        override fun handle(player: Player, component: Component, opcode: Int, button: Int, slot: Int, itemId: Int): Boolean {
            player.interfaceManager.closeSingleTab()
            return true
        }
    }
}
