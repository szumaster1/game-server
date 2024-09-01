package content.miniquest.knightwave.handlers

import core.api.MapArea
import core.game.activity.ActivityManager
import core.game.activity.ActivityPlugin
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.game.world.map.zone.ZoneBorders

/**
 * Represents the training ground activity for the knight wave mini-quest.
 */
class TrainingGroundActivity : ActivityPlugin("knight wave", false, false, true), MapArea {
    private var activity: TrainingGroundActivity? = this

    init {
        activity = this
        this.safeRespawn = Location.create(2750, 3507, 2)
    }

    override fun defineAreaBorders(): Array<ZoneBorders> =
        arrayOf(ZoneBorders(2764, 3513, 2752, 3502, 2, true))

    override fun newInstance(p: Player?): ActivityPlugin {
        ActivityManager.register(this)
        return this
    }

    override fun getSpawnLocation(): Location? = null
}