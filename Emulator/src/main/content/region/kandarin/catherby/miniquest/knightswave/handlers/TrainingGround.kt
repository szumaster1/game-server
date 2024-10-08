package content.region.kandarin.catherby.miniquest.knightswave.handlers

import content.data.GameAttributes
import core.api.*
import core.game.activity.ActivityManager
import core.game.activity.ActivityPlugin
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.game.world.map.zone.ZoneBorders
import core.game.world.map.zone.ZoneRestriction
import core.plugin.Initializable

/**
 * Represents the training ground.
 */
@Initializable
class TrainingGround : ActivityPlugin("knight wave", true, false, true), MapArea {
    private var activity: TrainingGround? = this

    init {
        activity = this
        this.safeRespawn = Location.create(2750, 3507, 2)
    }

    override fun death(entity: Entity, killer: Entity): Boolean {
        if (entity is Player) {
            entity.getProperties().teleportLocation = Location.create(2751, 3507, 2)
            return true
        }
        return false
    }

    override fun areaLeave(entity: Entity, logout: Boolean) {
        super.areaLeave(entity, logout)
        if (entity is Player) {
            removeAttributes(entity, GameAttributes.PRAYER_LOCK, KnightWavesUtils.KW_SPAWN, KnightWavesUtils.KW_TIER, KnightWavesUtils.KW_BEGIN)
            findLocalNPC(entity, KnightWavesNPC().id)?.let { poofClear(it) }
            clearLogoutListener(entity, "Knight's training")
        }
    }

    override fun start(player: Player?, login: Boolean, vararg args: Any?): Boolean {
        return super.start(player, login, *args)
    }

    override fun areaEnter(entity: Entity) {
        super.areaEnter(entity)
        if (entity is Player) {
            setAttribute(entity, GameAttributes.PRAYER_LOCK, true)
        }
    }

    override fun defineAreaBorders(): Array<ZoneBorders> =
        arrayOf(ZoneBorders(2752, 3502, 2764, 3513, 2, false))

    override fun newInstance(p: Player?): ActivityPlugin {
        ActivityManager.register(this)
        return this
    }

    override fun getRestrictions(): Array<ZoneRestriction> {
        return arrayOf(
            ZoneRestriction.CANNON,
            ZoneRestriction.RANDOM_EVENTS,
            ZoneRestriction.FOLLOWERS,
            ZoneRestriction.TELEPORT
        )
    }

    override fun getSpawnLocation(): Location? = null
}