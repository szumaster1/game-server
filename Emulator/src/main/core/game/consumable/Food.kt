package core.game.consumable

import cfg.consts.Animations
import cfg.consts.Sounds
import core.api.animate
import core.api.playAudio
import core.api.sendMessage
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.update.flag.context.Animation

/**
 * Represents a consumable food item.
 */
open class Food : Consumable {

    private val defaultAnimation = Animation(Animations.EAT_OLD_829)

    /**
     * Constructs a Food instance with specified parameters.
     *
     * @param ids      the ids of the food items
     * @param effect   the effect of consuming the food
     * @param messages the messages to display upon consumption
     */
    constructor(ids: IntArray?, effect: ConsumableEffect?, vararg messages: String?) : super(ids, effect, *messages) {
        animation = defaultAnimation
    }

    /**
     * Constructs a Food instance with specified parameters including a custom animation.
     *
     * @param ids       the ids of the food items
     * @param effect    the effect of consuming the food
     * @param animation the animation to play when consuming the food
     * @param messages  the messages to display upon consumption
     */
    constructor(ids: IntArray?, effect: ConsumableEffect?, animation: Animation?, vararg messages: String?) : super(
        ids,
        effect,
        animation,
        *messages
    )

    override fun sendDefaultMessages(player: Player, item: Item) {
        sendMessage(player, "You eat the ${getFormattedName(item)}.")
    }

    override fun executeConsumptionActions(player: Player) {
        animate(player, animation)
        playEatingSound(player)
    }

    private fun playEatingSound(player: Player) {
        playAudio(player, Sounds.EAT_2393)
    }
}
