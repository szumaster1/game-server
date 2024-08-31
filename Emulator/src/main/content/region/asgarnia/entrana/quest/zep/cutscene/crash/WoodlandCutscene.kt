package content.region.asgarnia.entrana.quest.zep.cutscene.crash

import core.api.location
import core.game.activity.Cutscene
import core.game.node.entity.player.Player

/**
 * Woodland cutscene.
 */
class WoodlandCutscene(player: Player) : Cutscene(player) {
    override fun setup() {
        setExit(location(0, 0, 0))
        if (player.settings.isRunToggled) {
            player.settings.toggleRun()
        }
        loadRegion(7244)
        // Location.create(1841, 4916, 0)
    }

    override fun runStage(stage: Int) {
        when (stage) {
            0 -> {
                end()
                timedUpdate(1)
            }
        }
    }
}