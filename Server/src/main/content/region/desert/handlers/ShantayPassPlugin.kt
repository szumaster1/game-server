package content.region.desert.handlers

import content.global.skill.support.agility.AgilityHandler
import core.api.*
import core.api.consts.Components
import core.api.consts.Items
import core.api.consts.NPCs
import core.cache.def.impl.NPCDefinition
import core.cache.def.impl.SceneryDefinition
import core.game.dialogue.FacialExpression
import core.game.global.action.DoorActionHandler
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.game.world.map.Location
import core.plugin.Initializable
import core.plugin.Plugin
import core.tools.DARK_RED

@Initializable
class ShantayPassPlugin : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        for (i in 35542 until 35545) {
            SceneryDefinition.forId(i).handlers["option:look-at"] = this
            SceneryDefinition.forId(i).handlers["option:go-through"] = this
            SceneryDefinition.forId(i).handlers["option:quick-pass"] = this
        }
        SceneryDefinition.forId(35400).handlers["option:look-at"] = this
        SceneryDefinition.forId(35400).handlers["option:go-through"] = this
        SceneryDefinition.forId(35400).handlers["option:quick-pass"] = this
        NPCDefinition.forId(838).handlers["option:bribe"] = this
        SceneryDefinition.forId(35401).handlers["option:open"] = this
        SceneryDefinition.forId(2693).handlers["option:open"] = this
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        val id = if (node is Scenery) node.id else node.id
        when (option) {
            "open" -> {
                if (id == 2693) {
                    openBankAccount(player)
                    return true
                }
                if (player.getAttribute("shantay-jail", false) && player.location.x > 3299) {
                    removeAttribute(player, "shantay-jail")
                }
                if (!player.getAttribute("shantay-jail", false)) {
                    DoorActionHandler.handleDoor(player, node as Scenery)
                    return true
                } else {
                    player.dialogueInterpreter.open(836, null, true)
                }
            }

            "bribe" -> player.dialogueInterpreter.open(838, node)
            "look-at" -> sendDialogueLines(
                player,
                DARK_RED + "The Desert is a VERY Dangerous place. Do not enter if you are",
                DARK_RED + "afraid of dying. Beware of high temperatures, and storms, robbers,",
                DARK_RED + "and slavers. No responsibility is taken by Shantay if anything bad",
                DARK_RED + "should happen to you in any circumstances whatsoever."
            )

            "go-through" -> {
                if (player.location.y < 3117) {
                    sendMessage(player, "You go through the gate.")
                    AgilityHandler.walk(player, 0, player.location, player.location.transform(0, if (player.location.y > 3116) -2 else 2, 0), null, 0.0, null)
                    return true
                }
                openInterface(player, Components.CWS_WARNING_10_565)
            }

            "quick-pass" -> {
                if (player.location.y > 3116) {
                    if (!inInventory(player, Items.SHANTAY_PASS_1854, 1)) {
                        sendNPCDialogue(player, NPCs.SHANTAY_GUARD_838, "You need a Shantay pass to get through this gate. See Shantay, he will sell you one for a very reasonable price.", FacialExpression.NEUTRAL)
                        return true
                    }
                    if (!removeItem(player, Items.SHANTAY_PASS_1854, Container.INVENTORY)) return true
                    sendMessage(player, "You hand your Shantay pass to the guard and pass through the gate.")
                }
                AgilityHandler.walk(player, 0, player.location, player.location.transform(0, if (player.location.y > 3116) -2 else 2, 0), null, 0.0, null)
            }
        }
        return true
    }

    override fun getDestination(node: Node, n: Node): Location? {
        if (n is Scenery) {
            val node = n as Scenery
            when (node.id) {
                35543 -> {
                    return node.location.transform(-1, if (node.location.y > n.location.y) 1 else -1, 0)
                }

                35544 -> {
                    return node.location.transform(-1, if (node.location.y > n.location.y) 1 else -1, 0)
                }

                35542 -> {
                    return node.location.transform(1, if (node.location.y > n.location.y) 1 else -1, 0)
                }
            }
        }
        return null
    }
}