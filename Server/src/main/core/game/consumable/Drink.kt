package core.game.consumable

import core.api.consts.Animations
import core.api.playAudio
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
        player.packetDispatch.sendMessage("You drink the " + getFormattedName(item) + ".")
    }

    override fun executeConsumptionActions(player: Player) {
        player.animate(animation)
        playAudio(player, 4580)
    }

    override fun getFormattedName(item: Item): String {
        return item.name.replace("(4)", "").replace("(3)", "").replace("(2)", "").replace("(1)", "").replace("(m4)", "")
            .replace("(m3)", "").replace("(m2)", "").replace("(m1)", "").replace("(m)", "").trim { it <= ' ' }
            .lowercase()
    }
}
