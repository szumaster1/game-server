package content.global.skill.support.construction.decoration.questhall

import core.api.*
import core.api.consts.Items
import core.api.consts.Sounds
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MountedGlory : InteractionListener {

    private val mountedGloryId = 13523
    val teleportLocations = arrayOf(
        Location.create(3087, 3495, 0),
        Location.create(2919, 3175, 0),
        Location.create(3104, 3249, 0),
        Location.create(3304, 3124, 0)
    )

    override fun defineListeners() {
        on(mountedGloryId, IntType.SCENERY, "Edgeville") { player, _ ->
            mountedGloryTeleport(player, 0)
            return@on true
        }

        on(mountedGloryId, IntType.SCENERY, "Karamja") { player, _ ->
            mountedGloryTeleport(player, 1)
            return@on true
        }

        on(mountedGloryId, IntType.SCENERY, "Draynor Village") { player, _ ->
            mountedGloryTeleport(player, 2)
            return@on true
        }

        on(mountedGloryId, IntType.SCENERY, "Al Kharid") { player, _ ->
            mountedGloryTeleport(player, 3)
            return@on true
        }
    }

    private fun mountedGloryTeleport(player: Player, int: Int) {
        if (!player.zoneMonitor.teleport(1, Item(Items.AMULET_OF_GLORY_1704))) return
        Executors.newSingleThreadScheduledExecutor().schedule({
            lock(player, 5)
            player.pulseManager.run(object : Pulse() {
                var counter = 0
                override fun pulse(): Boolean {
                    when (counter++) {
                        1 -> {
                            visualize(player, Animation(714),
                                Graphic(308, 100, 50)
                            )
                            playGlobalAudio(player.location, Sounds.TELEPORT_ALL_200)
                        }

                        4 -> {
                            resetAnimator(player)
                            teleport(player, teleportLocations[int])
                        }
                    }
                    return false
                }
            })
        }, 0, TimeUnit.SECONDS)
    }
}