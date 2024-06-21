package content.global.random.event.eviltwin

import content.global.random.RandomEventNPC
import core.api.runTask
import core.api.utils.WeightBasedTable
import core.game.node.entity.npc.NPC
import core.game.system.timer.impl.AntiMacro

class EvilTwinNPC(override var loot: WeightBasedTable? = null) : RandomEventNPC(-1) {

    override fun init() {
        super.init()
        runTask(player, 1) {
            EvilTwinUtils.start(player)
            AntiMacro.terminateEventNpc(player)
        }
    }

    override fun talkTo(npc: NPC) {

    }

}
