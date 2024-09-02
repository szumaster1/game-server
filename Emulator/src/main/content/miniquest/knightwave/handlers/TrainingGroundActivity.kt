package content.miniquest.knightwave.handlers

import core.api.*
import core.game.activity.ActivityManager
import core.game.activity.ActivityPlugin
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.game.world.map.zone.ZoneBorders

/**
 * Represents the training ground activity for the knight wave mini-quest.
 * Source [Link](https://web.archive.org/web/20090327073551/http://www.tip.it/runescape/?rs2quest_id=178#trainingground)
 */
class TrainingGroundActivity : ActivityPlugin("knight wave", true, false, true), MapArea {
    private var activity: TrainingGroundActivity? = this

    init {
        activity = this
        this.safeRespawn = Location.create(2750, 3507, 2)
    }

    override fun areaLeave(entity: Entity, logout: Boolean) {
        super.areaLeave(entity, logout)
        if (entity is Player) {
            removeAttributes(entity, KWUtils.PRAYER_LOCK, KWUtils.KW_SPAWN, KWUtils.KW_TIER, KWUtils.KW_KC, KWUtils.KW_BEGIN)
            poofClear(KnightNPC())
        }
    }

    override fun areaEnter(entity: Entity) {
        super.areaEnter(entity)
        if (entity is Player) {
            setAttribute(entity, KWUtils.PRAYER_LOCK, true)
        }
    }

    override fun defineAreaBorders(): Array<ZoneBorders> =
        arrayOf(ZoneBorders(2752, 3502, 2764, 3513, 2, false))

    override fun newInstance(p: Player?): ActivityPlugin {
        ActivityManager.register(this)
        return this
    }

    override fun getSpawnLocation(): Location? = Location.create(2750, 3507, 2)
}