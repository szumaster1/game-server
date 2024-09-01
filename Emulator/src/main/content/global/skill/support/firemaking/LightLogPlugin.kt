package content.global.skill.support.firemaking

import content.data.skill.SkillingTool
import content.region.kandarin.baxtorian.barbarian_training.BarbarianTraining
import content.region.kandarin.baxtorian.barbarian_training.firemaking.BarbFiremakingPulse
import core.api.inInventory
import core.api.sendMessage
import core.cache.def.impl.ItemDefinition
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.item.GroundItem
import core.game.node.item.Item
import core.plugin.Initializable
import core.plugin.Plugin

@Initializable
class LightLogPlugin : OptionHandler() {

    override fun handle(player: Player, node: Node, option: String): Boolean {
        var barbarianFiremaking = player.getAttribute(BarbarianTraining.FM_BASE, false) == true
        var completeBarbarianFiremaking = player.getAttribute(BarbarianTraining.FM_FULL, false) == true

        if (completeBarbarianFiremaking || barbarianFiremaking) {
            if (!inInventory(player, SkillingTool.getFiremakingTool(player)!!.id)) {
                player.pulseManager.run(FireMakingPulse(player, (node as Item), (node as GroundItem)))
            } else {
                player.pulseManager.run(
                    BarbFiremakingPulse(
                        player,
                        (node as Item),
                        (node as GroundItem)
                    )
                )
            }
        } else {
            sendMessage(player, "You do not have the required items to light this.")
        }
        return true
    }

    @Throws(Throwable::class)
    override fun newInstance(arg: Any?): Plugin<Any> {
        ItemDefinition.setOptionHandler("light", this)
        return this
    }

    override fun handleSelectionCallback(skill: Int, player: Player) {
            super.handleSelectionCallback(skill, player)
    }
}
