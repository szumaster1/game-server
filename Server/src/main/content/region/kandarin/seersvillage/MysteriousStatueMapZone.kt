package content.region.kandarin.seersvillage

import core.api.removeAttribute
import core.api.setAttribute
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.world.map.Location
import core.game.world.map.zone.MapZone
import core.game.world.map.zone.ZoneBorders
import core.game.world.map.zone.ZoneBuilder
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Mysterious statue map zone.
 */
@Initializable
class MysteriousStatueMapZone : MapZone("mysterious-statue", true), Plugin<Any?> {

    private val northWest = Location.create(2739, 3492, 0)
    private val northEast = Location.create(2742, 3492, 0)
    private val southEast = Location.create(2742, 3489, 0)
    private val southWest = Location.create(2739, 3489, 0)


    override fun newInstance(arg: Any?): MysteriousStatueMapZone {
        ZoneBuilder.configure(this)
        return this
    }

    override fun configure() {
        super.register(ZoneBorders(2739, 3492, 2742, 3489))
    }

    override fun enter(e: Entity?): Boolean {
        return super.enter(e)
    }

    override fun fireEvent(identifier: String?, vararg args: Any?): Any {
        return Unit
    }

    override fun locationUpdate(e: Entity?, last: Location?) {
        if (e is Player && !e.isArtificial) {
            val player = e.asPlayer()
            if (!player.achievementDiaryManager.hasCompletedTask(DiaryType.SEERS_VILLAGE, 0, 1) && player.getAttribute("seersStatueProgress", -1) !in 0..3) {
                setAttribute(player, "seersStatueProgress", 0)
            } else {
                when (player.location) {
                    northWest -> {
                        if (player.getAttribute("seersStatueProgress", -1) == 0) {
                            setAttribute(player, "seersStatueProgress", 1)
                        } else if (player.getAttribute("seersStatueProgress", -1) in 2..3) {
                            setAttribute(player, "seersStatueProgress", 0)
                        }
                    }

                    northEast -> {
                        if (player.getAttribute("seersStatueProgress", 0) == 1) {
                            setAttribute(player, "seersStatueProgress", 2)
                        } else if (player.getAttribute("seersStatueProgress", 0) != 1) {
                            setAttribute(player, "seersStatueProgress", 0)
                        }
                    }

                    southEast -> {
                        if (player.getAttribute("seersStatueProgress", 0) == 2) {
                            setAttribute(player, "seersStatueProgress", 3)
                        } else if (player.getAttribute("seersStatueProgress", 0) != 2) {
                            setAttribute(player, "seersStatueProgress", 0)
                        }
                    }

                    southWest -> {
                        if (player.getAttribute("seersStatueProgress", 0) == 3) {
                            setAttribute(player, "seersStatueProgress", 4)
                        } else if (player.getAttribute("seersStatueProgress", 0) != 3) {
                            setAttribute(player, "seersStatueProgress", 0)
                        }
                    }
                }
            }
            if (player.getAttribute("seersStatueProgress", 0) == 4) {
                player.achievementDiaryManager.finishTask(player, DiaryType.SEERS_VILLAGE, 0, 1)
                setAttribute(player, "seersStatueComplete", true)
                removeAttribute(player, "seersStatueProgress")
            }
        }
        super.locationUpdate(e, last)
    }

    override fun leave(e: Entity?, logout: Boolean): Boolean {
        if (e is Player && !e.isArtificial) {
            val player = e.asPlayer()
            if (!player.getAttribute("seersStatueComplete", false) && player.getAttribute("seersStatueProgress", -0) in 0..3) {
                removeAttribute(player, "seersStatueProgress")
                super.leave(e, logout)
            } else return super.leave(e, logout)
        }
        return super.leave(e, logout)
    }

}