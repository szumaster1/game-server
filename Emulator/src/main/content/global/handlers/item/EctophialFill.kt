package content.global.handlers.item

import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.TeleportManager.TeleportType
import core.game.node.item.Item
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import org.rs.consts.Items
import org.rs.consts.Scenery
import org.rs.consts.Sounds

/**
 * Fills an ectophial.
 */
class EctophialFill : InteractionListener {
    private val fillAnimation = Animation(832)
    private val emptyAnimation = Animation(9609)
    private val emptyGraphic = Graphic(1688)

    private fun refillEctophial(player: Player) {
        delayEntity(player, fillAnimation.duration)
        animate(player, fillAnimation)
        playAudio(player, Sounds.FILL_ECTOPLASM_1132)
        if (removeItem(player, Items.ECTOPHIAL_4252)) {
            addItem(player, Items.ECTOPHIAL_4251)
            sendMessage(player, "You refill the ectophial from the Ectofuntus.")
        }
    }

    override fun defineListeners() {

        /*
         * Use ectophial on ectofuntus.
         */

        onUseWith(IntType.SCENERY, Items.ECTOPHIAL_4252, Scenery.ECTOFUNTUS_5282) { player, _, _ ->
            refillEctophial(player)
            return@onUseWith true
        }

        /*
         * Emptying the ectophial.
         */

        on(Items.ECTOPHIAL_4251, IntType.ITEM, "empty") { player, node ->
            if (!hasRequirement(player, "Ghosts Ahoy")) return@on true

            if (player.isTeleBlocked) {
                sendMessage(player, "A magical force has stopped you from teleporting.")
                return@on true
            }
            delayEntity(player, 10)
            queueScript(player, 0, QueueStrength.SOFT) { stage: Int ->
                when (stage) {
                    0 -> {
                        sendMessage(player, "You empty the ectoplasm onto the ground around your feet...")
                        playAudio(player, 4580)
                        visualize(player, emptyAnimation, emptyGraphic)
                        replaceSlot(player, node.asItem().slot, Item(Items.ECTOPHIAL_4252), Item(Items.ECTOPHIAL_4251))
                        return@queueScript delayScript(player, emptyAnimation.duration)
                    }
                    1 -> {
                        teleport(player, Location.create(3658, 3517, 0), TeleportType.ECTOPHIAL)
                        sendMessage(player, "...and the world changes around you.")
                        return@queueScript delayScript(player, 9)
                    }
                    2 -> {
                        face(player, Location(3659, 3519, 0))
                        refillEctophial(player)
                        return@queueScript stopExecuting(player)
                    }

                    else -> return@queueScript stopExecuting(player)
                }
            }
            return@on true
        }
    }
}

