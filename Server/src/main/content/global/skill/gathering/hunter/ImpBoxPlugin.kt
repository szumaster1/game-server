package content.global.skill.gathering.hunter

import core.cache.def.impl.ItemDefinition
import core.game.component.Component
import core.game.component.ComponentDefinition.forId
import core.game.component.ComponentPlugin
import core.game.dialogue.Dialogue
import core.game.dialogue.DialogueInterpreter
import core.game.dialogue.FacialExpression
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.network.packet.PacketRepository
import core.network.packet.context.ContainerContext
import core.network.packet.outgoing.ContainerPacket
import core.plugin.ClassScanner.definePlugin
import core.plugin.Initializable
import core.plugin.Plugin
import core.tools.RandomFunction

/**
 * Handles the imp box.
 * @author Taylor
 */
@Initializable
class ImpBoxPlugin : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        definePlugin(ImpInterfaceHandler(null))
        for (id in IDS) {
            ItemDefinition.forId(id).handlers["option:bank"] = this
            ItemDefinition.forId(id).handlers["option:talk-to"] = this
        }
        definePlugin(ImpBoxDialogue())
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        when (option) {
            "bank" -> {
                val component = Component(478)
                component.plugin = ImpInterfaceHandler(node as Item)
                player.interfaceManager.open(component)
                PacketRepository.send(ContainerPacket::class.java, ContainerContext(player, 478, 61, 91, player.inventory, true))
            }

            "talk-to" -> player.dialogueInterpreter.open("imp-box")
        }
        return true
    }

    override fun isWalk(): Boolean {
        return false
    }

    /**
     * Handles talk-to dialogue on the imp box.
     */
    class ImpBoxDialogue : Dialogue {

        constructor()

        constructor(player: Player?) : super(player)

        override fun newInstance(player: Player): Dialogue {
            return ImpBoxDialogue(player)
        }

        override fun open(vararg args: Any): Boolean {
            interpreter.sendDialogues(708, FacialExpression.FURIOUS, MESSAGES[RandomFunction.getRandom(MESSAGES.size - 1)])
            return true
        }

        override fun handle(interfaceId: Int, buttonId: Int): Boolean {
            end()
            return true
        }

        override fun getIds(): IntArray {
            return intArrayOf(DialogueInterpreter.getDialogueKey("imp-box"))
        }

        companion object {
            private val MESSAGES =
                arrayOf("Let me outa here!", "Errgghh..", "Well look who it is.", "What are you looking at?")
        }
    }

    /**
     * Handles the imp interface.
     */
    inner class ImpInterfaceHandler(private var box: Item?) : ComponentPlugin() {

        override fun newInstance(arg: Any?): Plugin<Any> {
            forId(478)!!.plugin = this
            return this
        }

        override fun handle(player: Player, component: Component, opcode: Int, button: Int, slot: Int, itemId: Int): Boolean {
            val item = player.inventory[slot]
            if (item != null) {
                if (player.bank.canAdd(item) && item.id != box!!.id) {
                    player.dialogueInterpreter.close()
                    player.inventory.remove(item)
                    player.bank.add(item)
                    PacketRepository.send(
                        ContainerPacket::class.java,
                        ContainerContext(player, 478, 61, 91, player.inventory, true)
                    )
                    if (box!!.id == IDS[1]) {
                        val boxSlot = player.inventory.getSlot(box)
                        player.inventory.replace((Item(IDS[0]).also { box = it }), boxSlot)
                    } else if (box!!.id == IDS[0]) {
                        val boxSlot = player.inventory.getSlot(box)
                        player.inventory.replace(Item(10025), boxSlot)
                        player.interfaceManager.close(component)
                        player.sendMessage(Companion.FINISHING_MESSAGE)
                    }
                }
            } else {
                player.sendMessage("You cannot add this item to your bank.")
                return false
            }
            return true
        }
    }

    companion object {
        private val IDS = intArrayOf(10028, 10027)
        /**
         * The message to show when the imp is gone.
         */
        private const val FINISHING_MESSAGE = "The imp teleports away, taking the item to your bank account."
    }
}
