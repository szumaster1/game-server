package content.region.morytania.hollows.handlers

import core.api.*
import org.rs.consts.Items
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.world.map.zone.ZoneBorders

/**
 * Represents the Werewolf course map area.
 */
class WerewolfCourse : MapArea {

    override fun defineAreaBorders(): Array<ZoneBorders> {
        return arrayOf(ZoneBorders(3510, 9851, 3592, 9920))
    }

    override fun areaEnter(entity: Entity) {
        if (entity is Player) {
            setAttribute(entity, "/save:werewolf-agility-course", false)
        }
    }

    override fun areaLeave(entity: Entity, logout: Boolean) {
        if (entity is Player) {
            removeAttribute(entity, "werewolf-agility-course")
            if (removeAll(entity, Items.STICK_4179, Container.INVENTORY)) {
                sendMessage(entity, "The werewolf trainer removes your stick as you leave.")
            }
        }
    }


}