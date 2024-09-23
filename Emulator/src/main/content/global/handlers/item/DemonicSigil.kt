package content.global.handlers.item

import core.api.animate
import core.api.lock
import core.api.sendChat
import core.api.submitIndividualPulse
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.system.task.Pulse
import core.game.world.update.flag.context.Animation
import org.rs.consts.Animations
import org.rs.consts.Items

/**
 * Handles basic option of demonic sigil.
 */
class DemonicSigil : InteractionListener {

    override fun defineListeners() {
        on(Items.DEMONIC_SIGIL_6748, IntType.ITEM, "chant"){ player, _ ->
            lock(player, 10)
            animate(player, Animations.DEMONIC_SIGIL_I_E_SHADOW_OF_THE_STORM_2880)
            submitIndividualPulse(player, object : Pulse(1) {
                var counter = 0
                override fun pulse(): Boolean {
                    when (counter++) {
                        0 -> sendChat(player, "Caldar...")
                        3 -> sendChat(player, "Nahudu...")
                        6 -> sendChat(player, "Agrith Naar...")
                        9 -> sendChat(player, "Camerinthum...")
                        12 -> {
                            animate(player, Animations.DEMONIC_SIGIL_I_E_SHADOW_OF_THE_STORM_2880)
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
