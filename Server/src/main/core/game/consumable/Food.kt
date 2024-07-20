package core.game.consumable

import core.api.consts.Animations
import core.api.consts.Sounds
import core.api.playAudio
import core.api.sendMessage
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.update.flag.context.Animation

/**
 * The type Food.
 */
open class Food : Consumable {
    /**
     * Instantiates a new Food.
     *
     * @param ids      the ids
     * @param effect   the effect
     * @param messages the messages
     */
    constructor(ids: IntArray?, effect: ConsumableEffect?, vararg messages: String?) : super(ids, effect, *messages) {
        animation = Animation(Animations.EAT_OLD_829)
    }

    /**
     * Instantiates a new Food.
     *
     * @param ids       the ids
     * @param effect    the effect
     * @param animation the animation
     * @param messages  the messages
     */
    constructor(ids: IntArray?, effect: ConsumableEffect?, animation: Animation?, vararg messages: String?) : super(
        ids,
        effect,
        animation,
        *messages
    )

    override fun sendDefaultMessages(player: Player, item: Item) {
        sendMessage(player, "You eat the " + getFormattedName(item) + ".")
    }

    override fun executeConsumptionActions(player: Player) {
        player.animate(animation)
        playEatingSound(player)
    }

    private fun playEatingSound(player: Player) {
        playAudio(player, Sounds.EAT_2393)
    }
}
