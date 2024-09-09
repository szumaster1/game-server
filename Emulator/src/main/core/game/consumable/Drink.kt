package core.game.consumable

import cfg.consts.Animations
import core.api.animate
import core.api.playAudio
import core.api.sendMessage
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.update.flag.context.Animation

/**
 * Drink.
 */
open class Drink : Consumable {
    /**
     * Instantiates a new Drink.
     *
     * @param ids      the ids
     * @param effect   the effect
     * @param messages the messages
     */
    constructor(ids: IntArray?, effect: ConsumableEffect?, vararg messages: String?) : super(ids, effect, *messages) {
        animation = Animation(Animations.DRINK_BEER_1327)
    }

    /**
     * Instantiates a new Drink.
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
        sendMessage(player,"You drink the " + getFormattedName(item) + ".")
    }

    override fun executeConsumptionActions(player: Player) {
        animate(player, animation)
        playAudio(player, 4580)
    }

    override fun getFormattedName(item: Item): String {
        val doses = listOf("(4)", "(3)", "(2)", "(1)", "(m4)", "(m3)", "(m2)", "(m1)", "(m)")
        return item.name
            .replace(Regex(doses.joinToString("|") { Regex.escape(it) }), "")
            .trim()
            .lowercase()
    }
}
