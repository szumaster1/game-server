package content.region.asgarnia.burthope.quest.death.handlers

import core.api.*
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.world.map.zone.ZoneBorders
import org.rs.consts.QuestName

/**
 * Secret way location.
 */
class SecretWayLocationArea : MapArea {
    override fun defineAreaBorders(): Array<ZoneBorders> {
        return arrayOf(ZoneBorders(2866, 3609, 2866, 3609))
    }

    override fun areaEnter(entity: Entity) {
        if (entity is Player && getQuestStage(entity, QuestName.DEATH_PLATEAU) == 25) {
            sendPlayerDialogue(entity, "I think this is far enough, I can see Death Plateau and it looks like the trolls haven't found the path. I'd better go and tell Denulth.")
            sendMessage(entity, "You can see that there are no trolls on the secret path")
            sendMessage(entity, "You should go and speak to Denulth")
            setQuestStage(entity, QuestName.DEATH_PLATEAU, 26)
        }
    }
}