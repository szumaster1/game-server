package content.global.skill.support.construction.decoration.workshop

import org.rs.consts.Items
import org.rs.consts.Scenery
import core.api.*
import core.game.dialogue.Dialogue
import core.game.dialogue.DialogueInterpreter
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable

/**
 * Handles interaction for tool store in the workshop.
 */
@Initializable
class ToolStoreListener : InteractionListener {

    private val storeIDs = ToolStore.values().map(ToolStore::objects).toIntArray()

    override fun defineListeners() {
        /*
         * Handling the search for tool interaction.
         */

        on(storeIDs, IntType.SCENERY, "search") { player, node ->
            node.asScenery().let { scenery ->
                ToolStore.forId(scenery.id)?.let { toolStore ->
                    openDialogue(player, DialogueInterpreter.getDialogueKey("con:tools"), toolStore)
                }
            }
            return@on true
        }
    }

    private enum class ToolStore(val objects: Int, vararg val tools: Int) {
        TOOLSTORE_1(Scenery.TOOLS_13699, Items.SAW_8794, Items.CHISEL_1755, Items.HAMMER_2347, Items.SHEARS_1735),
        TOOLSTORE_2(Scenery.TOOLS_13700, Items.BUCKET_1925, Items.SPADE_952, Items.TINDERBOX_590),
        TOOLSTORE_3(Scenery.TOOLS_13701, Items.BROWN_APRON_1757, Items.GLASSBLOWING_PIPE_1785, Items.NEEDLE_1733),
        TOOLSTORE_4(Scenery.TOOLS_13702, Items.AMULET_MOULD_1595, Items.NECKLACE_MOULD_1597, Items.RING_MOULD_1592, Items.HOLY_MOULD_1599, Items.TIARA_MOULD_5523),
        TOOLSTORE_5(Scenery.TOOLS_13703, Items.RAKE_5341, Items.SPADE_952, Items.TROWEL_676, Items.SEED_DIBBER_5343, Items.WATERING_CAN_5331);

        companion object {
            fun forId(objectId: Int): ToolStore? = values().find { it.objects == objectId }
        }
    }

    /**
     * Represents the Tool dialogue.
     */
    private inner class ToolDialogue(player: Player) : Dialogue(player) {
        private lateinit var toolStore: ToolStore

        override fun newInstance(player: Player): Dialogue = ToolDialogue(player)

        override fun open(vararg args: Any?): Boolean {
            toolStore = args[0] as ToolStore
            val itemNames = toolStore.tools.map(::getItemName)
            sendDialogueOptions(player, "Select a Tool", *itemNames.toTypedArray())
            stage = 1
            return true
        }

        override fun handle(interfaceId: Int, buttonId: Int): Boolean {
            return when (stage) {
                1 -> handleToolSelection(buttonId)
                2 -> {
                    end()
                    true
                }
                else -> false
            }
        }

        private fun handleToolSelection(buttonId: Int): Boolean {
            if (buttonId !in 1..toolStore.tools.size) {
                player.debug("Invalid selection.")
                return false
            }

            val item = Item(toolStore.tools[buttonId - 1], 1)
            return if (freeSlots(player) <= 0) {
                sendDialogue(player, "You have no space in your inventory.")
                stage = 2
                true
            } else {
                addItem(player, item.id)
                end()
                true
            }
        }

        override fun getIds(): IntArray = intArrayOf(DialogueInterpreter.getDialogueKey("con:tools"))
    }
}