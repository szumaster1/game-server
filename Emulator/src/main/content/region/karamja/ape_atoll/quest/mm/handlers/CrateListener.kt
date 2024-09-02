package content.region.karamja.ape_atoll.quest.mm.handlers

import content.region.karamja.ape_atoll.quest.mm.dialogue.CrateMonkeyMadnessDialogue
import core.api.openDialogue
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.system.task.Pulse
import core.game.world.update.flag.context.Animation

/**
 * Represents the Crate listener.
 */
class CrateListener : InteractionListener {

    private val monkeyAmuletMouldCrate = 4724
    private val threadCrate = 4718
    private val monkeyTalkingDentures = 4715
    private val bananaCrate = 4723
    private val monkeyMadnessEntranceDown = 4714
    private val tinderboxCrate = 4719
    private val slimyGnomeEyesCrate = 4716
    private val hammersCrate = 4726

    override fun defineListeners() {
        // Searching crates with monkey amulet moulds.
        on(monkeyAmuletMouldCrate, IntType.SCENERY, "search") { player, _ ->
            player.animator.animate(Animation(536))
            sendMessage(player, "You search the crate.")
            openDialogue(player, CrateMonkeyMadnessDialogue(0))
            return@on true
        }

        on(threadCrate, IntType.SCENERY, "search") { player, _ ->
            sendMessage(player, "You search the crate.")
            player.pulseManager.run(object : Pulse() {
                var counter = 0
                override fun pulse(): Boolean {
                    when (counter++) {
                        3 -> openDialogue(player, CrateMonkeyMadnessDialogue(1))
                    }
                    return false
                }
            })
            return@on true
        }

        on(monkeyTalkingDentures, IntType.SCENERY, "search") { player, _ ->
            sendMessage(player, "You search the crate.")
            player.pulseManager.run(object : Pulse() {
                var counter = 0
                override fun pulse(): Boolean {
                    when (counter++) {
                        3 -> openDialogue(player, CrateMonkeyMadnessDialogue(2))
                    }
                    return false
                }
            })
            return@on true
        }

        on(bananaCrate, IntType.SCENERY, "search") { player, _ ->
            sendMessage(player, "You search the crate.")
            player.pulseManager.run(object : Pulse() {
                var counter = 0
                override fun pulse(): Boolean {
                    when (counter++) {
                        3 -> openDialogue(player, CrateMonkeyMadnessDialogue(3))
                    }
                    return false
                }
            })
            return@on true
        }

        on(monkeyMadnessEntranceDown, IntType.SCENERY, "search") { player, _ ->
            sendMessage(player, "You search the crate.")
            openDialogue(player, CrateMonkeyMadnessDialogue(4))
            return@on true
        }

        on(tinderboxCrate, IntType.SCENERY, "search") { player, _ ->
            sendMessage(player, "You search the crate.")
            player.pulseManager.run(object : Pulse() {
                var counter = 0
                override fun pulse(): Boolean {
                    when (counter++) {
                        3 -> openDialogue(player, CrateMonkeyMadnessDialogue(5))
                    }
                    return false
                }
            })
            return@on true
        }

        on(slimyGnomeEyesCrate, IntType.SCENERY, "search") { player, _ ->
            sendMessage(player, "You search the crate.")
            player.pulseManager.run(object : Pulse() {
                var counter = 0
                override fun pulse(): Boolean {
                    when (counter++) {
                        3 -> openDialogue(player, CrateMonkeyMadnessDialogue(6))
                    }
                    return false
                }
            })
            return@on true
        }

        on(hammersCrate, IntType.SCENERY, "search") { player, _ ->
            sendMessage(player, "You search the crate.")
            player.pulseManager.run(object : Pulse() {
                var counter = 0
                override fun pulse(): Boolean {
                    when (counter++) {
                        3 -> openDialogue(player, CrateMonkeyMadnessDialogue(7))
                    }
                    return false
                }
            })
            return@on true
        }

    }
}
