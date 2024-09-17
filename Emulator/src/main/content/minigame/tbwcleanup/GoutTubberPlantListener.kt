package content.minigame.tbwcleanup

import core.api.*
import org.rs.consts.Items
import org.rs.consts.Scenery
import org.rs.consts.Sounds
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.Node
import core.game.node.entity.player.Player
import core.tools.RandomFunction.random
import core.game.node.scenery.SceneryBuilder
import core.game.world.update.flag.context.Animation
import core.game.interaction.QueueStrength

/**
 * Represents the Spade on gout tubber plant.
 */
class GoutTubberPlantListener : InteractionListener {
    override fun defineListeners() {
        onUseWith(IntType.SCENERY, Items.SPADE_952, Scenery.GOUT_TUBER_PLANT_9033, handler = ::digForGoutTubber)
    }

    fun digForGoutTubber(player: Player, used: Node, with: Node): Boolean {
        tryDiggingForGoutTubber(player, with)
        return true
    }

    fun tryDiggingForGoutTubber(player: Player, with: Node): Boolean {
        val locationStart = player.location
        val digTime = random(2, 12)
        var dugUp = false
        clearScripts(player)
        player.sendMessage("You start digging up the plant 1.${digTime}")

        player.animate(Animation(831))
        player.sendMessage("You start digging up the plant 2.${digTime}")
        playAudio(player, Sounds.DIGSPADE_1470, 0, digTime / 2 + digTime % 2)
        queueScript(player, digTime, QueueStrength.SOFT) {
            if (player.animator.animation.id == 831 && locationStart == player.location) {
                player.animate(Animation(2697), 0)
                dugUp = true
            }
            return@queueScript stopExecuting(player)
        }
        queueScript(player, digTime + 2, QueueStrength.SOFT) {
            if (dugUp) {
                player.sendMessage("You dig up the plant and find a gout tuber.")
                addItem(player, Items.GOUT_TUBER_6311, 1)
                val node = when (with.asScenery().asConstructed().replaced.id) {
                    9010, 9011, 9012, 9013, 9014 -> 9010
                    9015, 9016, 9017, 9018, 9019 -> 9015
                    9020, 9021, 9022, 9023, 9024 -> 9020
                    else -> 0
                }
                player.sendMessage("replace node id: ${node}")
                player.sendMessage("replace node id + 4: ${node + 4}")
                SceneryBuilder.replaceWithTempBeforeNew(
                    with.asScenery(),
                    with.asScenery().transform(node + 4),
                    with.asScenery().transform(node),
                    100,
                    true
                )
            }
            return@queueScript stopExecuting(player)
        }
        return true
    }
}
