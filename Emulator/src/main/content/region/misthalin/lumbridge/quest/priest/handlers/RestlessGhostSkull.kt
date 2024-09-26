package content.region.misthalin.lumbridge.quest.priest.handlers

import org.rs.consts.Items
import org.rs.consts.Sounds
import core.api.*
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.plugin.Initializable
import core.plugin.Plugin
import org.rs.consts.QuestName

/**
 * Represents the Restless ghost skull.
 */
@Initializable
class RestlessGhostSkull : UseWithHandler(Items.SKULL_964) {

    override fun newInstance(arg: Any?): Plugin<Any> {
        addHandler(org.rs.consts.Scenery.COFFIN_2145, OBJECT_TYPE, this)
        addHandler(org.rs.consts.Scenery.COFFIN_2145, OBJECT_TYPE, this)
        addHandler(15061, OBJECT_TYPE, this)
        return this
    }

    override fun handle(event: NodeUsageEvent): Boolean {
        val scenery = event.usedWith as Scenery
        if (scenery.id == org.rs.consts.Scenery.COFFIN_2145) {
            sendDialogue(event.player, "Maybe I should open it first.")
            return true
        }
        if (removeItem(event.player, Item(Items.SKULL_964, 1))) {
            playAudio(event.player, Sounds.RG_PLACE_SKULL_1744)
            sendMessage(event.player, "You put the skull in the coffin.")
            finishQuest(event.player, QuestName.THE_RESTLESS_GHOST)
        }
        return true
    }
}
