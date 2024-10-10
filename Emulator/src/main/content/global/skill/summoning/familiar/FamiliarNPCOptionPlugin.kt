package content.global.skill.summoning.familiar

import core.api.openDialogue
import core.api.sendMessage
import core.cache.def.impl.NPCDefinition
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Familiar NPC option plugin.
 */
@Initializable
class FamiliarNPCOptionPlugin : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        NPCDefinition.setOptionHandler("pick-up", this)
        NPCDefinition.setOptionHandler("interact-with", this)
        NPCDefinition.setOptionHandler("interact", this)
        NPCDefinition.setOptionHandler("store", this)
        NPCDefinition.setOptionHandler("withdraw", this)
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        if (node !is Familiar) {
            return false
        }
        if (node.getOwner() !== player) {
            sendMessage(player, "This is not your familiar.")
            return true
        }
        when (option) {
            "pick-up" -> {
                player.faceLocation(node.getFaceLocation(player.location))
                player.familiarManager.pickup()
            }

            "interact-with" -> openDialogue(player, 343823)
            "interact" -> openDialogue(player, node.getId(), node)
            "store", "withdraw" -> {
                if (!node.isBurdenBeast) {
                    sendMessage(player, "This is not a beast of burden.")
                    return false
                }
                (node as BurdenBeast).openInterface()
            }
        }
        return true
    }
}
