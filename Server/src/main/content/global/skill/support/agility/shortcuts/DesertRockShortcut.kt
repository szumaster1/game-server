package content.global.skill.support.agility.shortcuts

import core.api.*
import core.api.consts.Animations
import core.api.consts.Items
import core.api.consts.Scenery
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.system.task.Pulse
import core.game.world.GameWorld

/**
 * Desert rock shortcut.
 */
class DesertRockShortcut : InteractionListener {

    override fun defineListeners() {

        /**
         * Setup rock shortcut using rope on it.
         */
        onUseWith(IntType.SCENERY, Items.ROPE_954, ROCK) { player, _, _ ->
            animate(player, TIE_ROPE)
            setVarbit(player, 4231, 1)
            return@onUseWith true
        }

        /**
         * Interaction with rock.
         */
        on(ROCK, IntType.SCENERY, "climb down") { player, _ ->
            lock(player, 1000)
            lockInteractions(player, 1000)
            GameWorld.Pulser.submit(
                object : Pulse() {
                    var counter = 0

                    override fun pulse(): Boolean {
                        when (counter++) {
                            1 -> teleport(player, location(3382, 2825, 1))
                            2 -> {
                                player.faceLocation(location(3382, 2823, 1))
                                animate(player, Animations.LOOKING_TO_THE_SIDE_1772)
                            }

                            5 -> {
                                teleport(player, location(3382, 2825, 0))
                                return true
                            }
                        }
                        return false
                    }
                })
            return@on true
        }

        on(ROPE, IntType.SCENERY, "climb up") { player, _ ->
            teleport(player, location(3382, 2825, 0))
            return@on true
        }
    }

    /**
     * Location: https://i.imgur.com/pzs942s.png
     */
    companion object {
        private const val ROCK = Scenery.ROCK_28487
        private const val ROPE = Scenery.ROPE_28490
        private const val TIE_ROPE = Animations.HUMAN_TIES_ROPE_9086
    }
}
