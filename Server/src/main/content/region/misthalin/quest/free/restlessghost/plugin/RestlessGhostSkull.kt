package content.region.misthalin.quest.free.restlessghost.plugin

import content.region.misthalin.quest.free.restlessghost.RestlessGhost
import core.api.consts.Items
import core.api.consts.Sounds
import core.api.playAudio
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Restless ghost skull.
 */
@Initializable
class RestlessGhostSkull : UseWithHandler(Items.SKULL_964) {

    override fun newInstance(arg: Any?): Plugin<Any> {
        addHandler(2145, OBJECT_TYPE, this)
        addHandler(15052, OBJECT_TYPE, this)
        addHandler(15061, OBJECT_TYPE, this)
        return this
    }

    override fun handle(event: NodeUsageEvent): Boolean {
        val scenery = event.usedWith as Scenery
        if (scenery.id == 2145) {
            event.player.dialogueInterpreter.sendDialogue("Maybe I should open it first.")
            return true
        }
        if (event.player.inventory.remove(Item(Items.SKULL_964, 1))) {
            playAudio(event.player, Sounds.RG_PLACE_SKULL_1744)
            event.player.packetDispatch.sendMessage("You put the skull in the coffin.")
            event.player.getQuestRepository().getQuest(RestlessGhost.NAME).finish(event.player)
        }
        return true
    }
}
