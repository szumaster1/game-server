package content.region.misthalin.handlers.stronghold.playersafety

import cfg.consts.Music
import core.api.inBorders
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.game.world.map.zone.MapZone
import core.game.world.map.zone.ZoneBorders
import core.plugin.Initializable

/**
 * Player safety zone.
 */
@Initializable
class PlayerSafetyZone : MapZone("player-safety", true) {

    override fun configure() {
        register(FIRST_FLOOR)
        register(GOBLIN_JAIL)
    }

    companion object {
        val instance = PlayerSafetyZone()
        var GOBLIN_JAIL = ZoneBorders(3076, 4228, 3089, 4253, 0, true)
        var FIRST_FLOOR = ZoneBorders(3132, 4221, 3175, 4281, 3, true)
        var CLASS_ROOM = ZoneBorders(3076, 3452, 3085, 3458,0, true)
    }

    override fun locationUpdate(e: Entity?, last: Location?) {
        if (e is Player) {
            val player = e.asPlayer()

            if (inBorders(player, GOBLIN_JAIL)) {
                if (!player.musicPlayer.hasUnlocked(Music.INCARCERATION_494)) {
                    player.musicPlayer.unlock(Music.INCARCERATION_494)
                }
                return
            }

            if (inBorders(player, FIRST_FLOOR)) {
                if (!player.musicPlayer.hasUnlocked(Music.SAFETY_IN_NUMBERS_493)) {
                    player.musicPlayer.unlock(Music.SAFETY_IN_NUMBERS_493)
                }
                return
            }

            if (inBorders(player, CLASS_ROOM)) {
                if (!player.musicPlayer.hasUnlocked(Music.EXAM_CONDITIONS_492)) {
                    player.musicPlayer.unlock(Music.EXAM_CONDITIONS_492)
                }
            }
        }
    }
}
