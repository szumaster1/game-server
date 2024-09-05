package content.region.kandarin.quest.murder.handlers

import cfg.consts.Items
import core.cache.def.impl.SceneryDefinition
import core.game.interaction.NodeUsageEvent
import core.game.interaction.OptionHandler
import core.game.interaction.UseWithHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.plugin.Initializable
import core.plugin.Plugin
import core.plugin.PluginManager

/**
 * Plugin that enables getting flour from barrel in Sinclair mansion kitchen.
 * @author Afaroutdude
 */
@Initializable
class SinclairFlourBarrelPlugin : OptionHandler() {
    companion object {
        private val EMPTY_POT = Item(Items.EMPTY_POT_1931)
        private val FLOUR = Item(Items.POT_OF_FLOUR_1933)
        private const val DIARY_TASK_INDEX = 0
        private const val DIARY_TASK_ID = 5
        private const val MAX_FLOUR_COUNT = 4
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        return retrieveFlour(player, node as Scenery)
    }

    private fun retrieveFlour(player: Player, barrel: Scenery): Boolean {
        if (!player.inventory.containsItem(EMPTY_POT)) {
            player.packetDispatch.sendMessage("I need an empty pot to hold the flour in.")
            return true
        }

        if (player.inventory.remove(EMPTY_POT)) {
            player.lock(3)
            player.inventory.add(FLOUR)
            player.packetDispatch.sendMessage("You take some flour from the barrel.")
            updateDiaryProgress(player)
            player.packetDispatch.sendMessage("There's still plenty of flour left.")
            return true
        }
        return false
    }

    private fun updateDiaryProgress(player: Player) {
        if (!player.achievementDiaryManager.getDiary(DiaryType.SEERS_VILLAGE).isComplete(DIARY_TASK_INDEX, DIARY_TASK_ID)) {
            val currentFlourCount = player.getAttribute("diary:seers:sinclair-flour", 0)
            if (currentFlourCount >= MAX_FLOUR_COUNT) {
                player.setAttribute("/save:diary:seers:sinclair-flour", DIARY_TASK_ID)
                player.achievementDiaryManager.getDiary(DiaryType.SEERS_VILLAGE).updateTask(player, DIARY_TASK_INDEX, DIARY_TASK_ID, true)
            } else {
                player.setAttribute("/save:diary:seers:sinclair-flour", currentFlourCount + 1)
            }
        }
    }

    @Throws(Throwable::class)
    override fun newInstance(arg: Any?): Plugin<Any> {
        SceneryDefinition.forId(26122).handlers["option:take from"] = this
        PluginManager.definePlugin(FlourHandler())
        return this
    }

    private inner class FlourHandler : UseWithHandler(EMPTY_POT.id) {
        @Throws(Throwable::class)
        override fun newInstance(arg: Any?): Plugin<Any> {
            addHandler(26122, OBJECT_TYPE, this)
            return this
        }

        override fun handle(event: NodeUsageEvent): Boolean {
            return retrieveFlour(event.player, event.usedWith.asScenery())
        }
    }
}