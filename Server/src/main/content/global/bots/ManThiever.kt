package content.global.bots

import core.game.bots.Script
import core.game.interaction.IntType
import core.game.interaction.InteractionListeners
import core.game.node.item.Item

class ManThiever : Script() {

    override fun tick() {
        val man = scriptAPI.getNearestNode("Man", false)
        man ?: return
        bot.interfaceManager.close()
        InteractionListeners.run(man.id, IntType.NPC, "Pickpocket", bot, man)
    }

    override fun newInstance(): Script {
        return this
    }

    init {
        equipment.addAll(listOf(Item(1103), Item(1139), Item(1265)))
    }
}