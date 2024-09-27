package content.region.desert.alkharid.handlers

import org.rs.consts.Components
import org.rs.consts.Items
import org.rs.consts.NPCs
import core.api.*
import core.cache.def.impl.NPCDefinition
import core.cache.def.impl.SceneryDefinition
import core.game.component.Component
import core.game.dialogue.FacialExpression
import core.game.global.action.DoorActionHandler
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.game.world.map.Location
import core.plugin.Initializable
import core.plugin.Plugin
import core.tools.RED

/**
 * Represents the Shantay pass plugin.
 */
@Initializable
class ShantayPassPlugin : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        val sceneryIds = listOf(
            org.rs.consts.Scenery.SHANTAY_PASS_35542,
            org.rs.consts.Scenery.SHANTAY_PASS_35543,
            org.rs.consts.Scenery.SHANTAY_PASS_35544,
            org.rs.consts.Scenery.SHANTAY_PASS_35400
        )
        sceneryIds.forEach { id ->
            SceneryDefinition.forId(id).handlers.apply {
                put("option:look-at", this@ShantayPassPlugin)
                put("option:go-through", this@ShantayPassPlugin)
                put("option:quick-pass", this@ShantayPassPlugin)
            }
        }
        NPCDefinition.forId(NPCs.SHANTAY_GUARD_838).handlers["option:bribe"] = this
        SceneryDefinition.forId(org.rs.consts.Scenery.JAIL_DOOR_35401).handlers["option:open"] = this
        SceneryDefinition.forId(org.rs.consts.Scenery.SHANTAY_CHEST_2693).handlers["option:open"] = this
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        val id = (node as? Scenery)?.id ?: (node as NPC).id
        return when (option) {
            "open" -> handleOpenOption(player, node, id)
            "bribe" -> {
                player.dialogueInterpreter.open(NPCs.SHANTAY_GUARD_838, node)
                true
            }

            "look-at" -> {
                sendDialogueLines(
                    player,
                    RED + "The Desert is a VERY Dangerous place. Do not enter if you are",
                    RED + "afraid of dying. Beware of high temperatures, and storms, robbers,",
                    RED + "and slavers. No responsibility is taken by Shantay if anything bad",
                    RED + "should happen to you in any circumstances whatsoever."
                )
                true
            }

            "go-through" -> handleGoThroughOption(player, node)
            "quick-pass" -> handleQuickPassOption(player, node)
            else -> false
        }
    }

    private fun handleOpenOption(player: Player, node: Node, id: Int): Boolean {
        if (id == org.rs.consts.Scenery.SHANTAY_CHEST_2693) {
            player.bank.open()
            return true
        }
        if (player.getAttribute("shantay-jail", false) && player.location.x > 3299) {
            player.removeAttribute("shantay-jail")
        }
        return if (!player.getAttribute("shantay-jail", false)) {
            DoorActionHandler.handleDoor(player, node as Scenery)
            true
        } else {
            player.dialogueInterpreter.open(NPCs.SHANTAY_836, null, true)
            true
        }
    }

    private fun handleGoThroughOption(player: Player, node: Node): Boolean {
        if (player.location.y < 3117) {
            player.packetDispatch.sendMessage("You go through the gate.")
            content.global.skill.agility.AgilityHandler.walk(player, 0, player.location, player.location.transform(0, if (player.location.y > 3116) -2 else 2, 0), null, 0.0, null)
            return true
        }
        player.interfaceManager.open(Component(Components.CWS_WARNING_10_565))
        return true
    }

    private fun handleQuickPassOption(player: Player, node: Node): Boolean {
        if (player.location.y > 3116) {
            if (!inInventory(player, Items.SHANTAY_PASS_1854, 1)) {
                sendNPCDialogue(player, NPCs.SHANTAY_GUARD_838, "You need a Shantay pass to get through this gate. See Shantay, he will sell you one for a very reasonable price.", FacialExpression.NEUTRAL)
                return true
            }
            if (!removeItem(player, Items.SHANTAY_PASS_1854, Container.INVENTORY)) {
                sendMessage(player, "An error occurred while trying to remove your Shantay pass. Please try again.")
                return false
            }
            sendMessage(player, "You hand your Shantay pass to the guard and pass through the gate.")
        }
        content.global.skill.agility.AgilityHandler.walk(player, 0, player.location, player.location.transform(0, if (player.location.y > 3116) -2 else 2, 0), null, 0.0, null)
        return true
    }

    override fun getDestination(node: Node, n: Node): Location? {
        if (n is Scenery) {
            return when (n.id) {
                35543, 35544 -> n.location.transform(-1, if (node.location.y > n.location.y) 1 else -1, 0)
                35542 -> n.location.transform(1, if (node.location.y > n.location.y) 1 else -1, 0)
                else -> null
            }
        }
        return null
    }
}
