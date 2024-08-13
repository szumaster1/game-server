package content.global.handlers.item

import content.data.consumables.effects.PrayerEffect
import core.api.*
import core.api.consts.Animations
import core.api.consts.Items
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.Entity
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.update.flag.context.Graphic
import java.util.concurrent.TimeUnit

/**
 * Falador shield listener.
 */
class FaladorShieldListener : InteractionListener {

    override fun defineListeners() {

        /**
         * Interaction with Falador diary shields.
         */

        on(FALADOR_SHIELD, IntType.ITEM, "prayer-restore", "operate") { player, node ->
            val item = node as Item
            val level = getLevel(item.id)
            when (getUsedOption(player)) {
                "prayer-restore" -> {
                    val attrTime = player.getAttribute<Long>("diary:falador:shield-restore-time")
                    setTitle(player, 2)
                    sendDialogueOptions(player, "Are you sure you wish to recharge?", "Yes, recharge my Prayer points.", "No, I've changed my mind.")
                    addDialogueAction(player) { player, button ->
                        if (button == 2) {
                            if (attrTime != null && attrTime > System.currentTimeMillis()) {
                                sendMessage(player, "You have no charges left today.")
                            } else {
                                val effect = PrayerEffect(0.0, if (level == 0) 0.25 else if (level == 1) 0.5 else 1.0)
                                visualize(player, -1, Graphic(GFX_PRAYER_RESTORE[level]))
                                setAttribute(player, "/save:diary:falador:shield-restore-time", System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1))
                                sendMessage(player, "You restore " + (if (level < 2) "some" else "your") + " prayer points.")
                                effect.activate(player)
                            }
                        }
                    }
                }

                "operate" -> Pulser.submit(getPulse(player, level))

            }
            return@on true
        }

    }

    private fun getLevel(itemId: Int): Int {
        when (itemId) {
            14577 -> return 0
            14578 -> return 1
            14579 -> return 2
        }
        return -1
    }

    private fun getPulse(entity: Entity, level: Int): Pulse {
        return object : Pulse(1) {
            override fun pulse(): Boolean {
                if (delay == 0) {
                    lock(entity, 3)
                    visualize(entity, ANIM_EMOTE, Graphic(GFX_EMOTE[level]))
                } else if (delay == 3) {
                    resetAnimator(entity.asPlayer())
                    return true
                }
                delay++
                return false
            }
        }
    }

    companion object {
        const val ANIM_EMOTE: Int = Animations.HUMAN_FALADOR_SHIELD_PRAYER_RESTORE_11012
        val GFX_EMOTE: IntArray = intArrayOf(1966, 1965, 1965)
        val GFX_PRAYER_RESTORE: IntArray = intArrayOf(1962, 1963, 1964)
        val FALADOR_SHIELD: IntArray = intArrayOf(Items.FALADOR_SHIELD_1_14577, Items.FALADOR_SHIELD_2_14578, Items.FALADOR_SHIELD_3_14579)
    }

}