package content.miniquest.knightwave.handlers

import core.api.MapArea
import core.api.isPrayerActive
import core.api.toIntArray
import core.game.activity.ActivityManager
import core.game.activity.ActivityPlugin
import core.game.event.PrayerDeactivatedEvent
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.prayer.PrayerType
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

    override fun areaEnter(entity: Entity) {
        super.areaEnter(entity)
        if(entity is Player) {
            val button = (5..57)
            player.prayer.toggle(PrayerType.get(button.first))
            player.prayer.toggle(PrayerType.get(-1))
            // Need tests
        }
    }

    override fun defineAreaBorders(): Array<ZoneBorders> =
        arrayOf(ZoneBorders(2764, 3513, 2752, 3502, 2, true))

    override fun newInstance(p: Player?): ActivityPlugin {
        ActivityManager.register(this)
        return this
    }

    override fun getSpawnLocation(): Location? = null
}