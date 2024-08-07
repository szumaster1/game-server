package content.region.asgarnia.quest.bkfortress

import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.plugin.Plugin

/**
 * B k cabbage plugin
 *
 * @constructor B k cabbage plugin
 */
class BKCabbagePlugin: UseWithHandler(1965, 1967) {

    override fun newInstance(arg: Any?): Plugin<Any> {
        addHandler(2336, OBJECT_TYPE, this)
        return this
    }

    override fun handle(event: NodeUsageEvent): Boolean {
        val player = event.player
        val quest = player.getQuestRepository().getQuest("Black Knights' Fortress")
        if (quest.getStage(player) == 20) {
            if (event.usedItem.id == 1967) {
                player.dialogueInterpreter.sendDialogue("This is the wrong sort of cabbage!")
                return true
            }
            player.dialogueInterpreter.open(992752973, true, true)
            return true
        } else {
            player.dialogueInterpreter.sendDialogues(player, null, "Why exactly would I want to do that?")
        }
        return false
    }
}
