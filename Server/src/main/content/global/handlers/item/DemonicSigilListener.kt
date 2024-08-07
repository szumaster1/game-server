package content.global.handlers.item

import core.api.animate
import core.api.consts.Animations
import core.api.consts.Items
import core.api.lock
import core.api.sendChat
import core.api.submitIndividualPulse
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.system.task.Pulse
import core.game.world.update.flag.context.Animation

/**
 * Demonic sigil listener
 *
 * @constructor Demonic sigil listener
 */
class DemonicSigilListener : InteractionListener {

    private val demonicSigilId = Items.DEMONIC_SIGIL_6748
    private val chantingAnimStart = Animation(Animations.DEMONIC_SIGIL_I_E_SHADOW_OF_THE_STORM_2879)
    private val chantingAnimEnd = Animation(Animations.DEMONIC_SIGIL_I_E_SHADOW_OF_THE_STORM_2880)

    override fun defineListeners() {

        /*
         * Demonic sigil basic interaction.
         */

        on(demonicSigilId, IntType.ITEM, "chant"){ player, _ ->
            lock(player, 10)
            animate(player, chantingAnimStart)
            submitIndividualPulse(player, object : Pulse(1) {
                var counter = 0
                override fun pulse(): Boolean {
                    when (counter++) {
                        0 -> sendChat(player, "Caldar...")
                        3 -> sendChat(player, "Nahudu...")
                        6 -> sendChat(player, "Agrith Naar...")
                        9 -> sendChat(player, "Camerinthum...")
                        12 -> {
                            animate(player, chantingAnimEnd)
                            sendChat(player, "Tarren!")
                            return true
                        }
                    }
                    return false
                }
            })
            return@on true
        }
    }
}
