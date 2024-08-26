package content.region.kandarin.handlers.miniquest.knightwave

import core.api.MapArea
import core.game.activity.ActivityManager
import core.game.activity.ActivityPlugin
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.game.world.map.zone.ZoneBorders
import core.game.world.map.zone.ZoneRestriction

/**
 * Represents the training ground activity for the knight wave mini-quest.
 */
class TrainingGroundActivity : ActivityPlugin(
    "knight wave",
    false,
    false,
    true,
    ZoneRestriction.CANNON,
    ZoneRestriction.FOLLOWERS,
    ZoneRestriction.FIRES,
    ZoneRestriction.RANDOM_EVENTS
), MapArea {

    private var activity: TrainingGroundActivity? = this
    var ticks = 0

    init {
        this.safeRespawn = Location.create(2750, 3507, 2)
    }

    override fun defineAreaBorders(): Array<ZoneBorders> =
        arrayOf(ZoneBorders(2764, 3513, 2752, 3502, 2, false))

    override fun getRestrictions(): Array<ZoneRestriction> =
        ZoneRestriction.values().filter {
            it in listOf(
                ZoneRestriction.CANNON,
                ZoneRestriction.FOLLOWERS,
                ZoneRestriction.FIRES,
                ZoneRestriction.RANDOM_EVENTS
            )
        }.toTypedArray()

    override fun areaEnter(entity: Entity) {
        (entity as? Player)?.addExtension(TrainingGroundActivity::class.java, this)
    }

    override fun newInstance(p: Player?): ActivityPlugin {
        ActivityManager.register(this)
        return this
    }

    override fun getSpawnLocation(): Location? = null
}