package content.global.skill.combat.summoning.familiar

import core.api.sendInputDialogue
import core.game.component.Component
import core.game.component.ComponentDefinition
import core.game.component.ComponentPlugin
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Burden interface plugin.
 */
@Initializable
class BurdenInterfacePlugin : ComponentPlugin() {
    @Throws(Throwable::class)
    override fun newInstance(arg: Any?): Plugin<Any> {
        ComponentDefinition.put(665, this)
        ComponentDefinition.put(671, this)
        return this
    }

    override fun handle(
        player: Player,
        component: Component,
        opcode: Int,
        button: Int,
        slot: Int,
        itemId: Int
    ): Boolean {
        if (!player.familiarManager.hasFamiliar() || !player.familiarManager.familiar.isBurdenBeast) {
            return false
        }
        val beast = player.familiarManager.familiar as BurdenBeast
        val withdraw = component.id == 671
        val container = if (withdraw) beast.container else player.inventory
        val item = if (slot >= 0 && slot < container.capacity()) container[slot] else null
        if (item == null && button != 29) {
            return true
        }
        when (opcode) {
            155 -> {
                if (button == 29) {
                    beast.withdrawAll()
                    return true
                }
                beast.transfer(item, 1, withdraw)
                return true
            }

            196 -> {
                beast.transfer(item, 5, withdraw)
                return true
            }

            124 -> {
                beast.transfer(item, 10, withdraw)
                return true
            }

            199 -> {
                beast.transfer(item, container.getAmount(item), withdraw)
                return true
            }

            234 -> sendInputDialogue(player, true, "Enter the amount:") { value: Any ->
                beast.transfer(item, value as Int, withdraw)
                Unit
            }

            168 -> {
                player.packetDispatch.sendMessage(item!!.definition.examine)
                return true
            }
        }
        return false
    }
}
