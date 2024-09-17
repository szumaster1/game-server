package content.global.skill.gathering.hunter.falconry

import core.api.Container
import core.api.MapArea
import cfg.consts.Items
import core.api.removeAttribute
import core.api.removeItem
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.map.zone.ZoneBorders
import core.game.world.map.zone.ZoneRestriction

/**
 * Represents the falconry area.
 */
class FalconryArea : MapArea {

    companion object {
        private val BONES = Item(Items.BONES_526)
        private val FALCON = Item(Items.FALCONERS_GLOVE_10024)
        private val GLOVE = Item(Items.FALCONERS_GLOVE_10023)

        val NPCS = FalconCatch.values().map { it.npc }.toIntArray()
    }

    override fun defineAreaBorders(): Array<ZoneBorders> {
        return arrayOf(ZoneBorders(2363, 3574, 2395, 3620))
    }

    override fun areaLeave(entity: Entity, logout: Boolean) {
        super.areaLeave(entity, logout)
        if (entity is Player && !entity.isArtificial) {
            val player = entity.asPlayer()
            removeAttribute(player, "falconry")
            removeItem(player, Items.FALCONERS_GLOVE_10024, Container.EQUIPMENT)
            removeItem(player, Items.FALCONERS_GLOVE_10024, Container.INVENTORY)
            removeItem(player, Items.FALCONERS_GLOVE_10023, Container.INVENTORY)
            removeItem(player, Items.FALCONERS_GLOVE_10023, Container.EQUIPMENT)
        }
    }

    override fun getRestrictions(): Array<ZoneRestriction> {
        return arrayOf(ZoneRestriction.CANNON, ZoneRestriction.FIRES, ZoneRestriction.RANDOM_EVENTS)
    }
}