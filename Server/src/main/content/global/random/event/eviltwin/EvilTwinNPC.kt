package content.global.random.event.eviltwin

import content.global.random.RandomEventNPC
import core.api.openDialogue
import core.api.runTask
import core.api.utils.WeightBasedTable
import core.game.node.entity.npc.NPC
import core.game.system.timer.impl.AntiMacro

val MOLLY: Int = 3892 + (0 and 0xFF) + (((0 shr 16) and 0xFF) * EvilTwinColors.values().size)

/**
 * Evil twin NPC.
 */
class EvilTwinNPC(override var loot: WeightBasedTable? = null) : RandomEventNPC(MOLLY) {

    override fun init() {
        super.init()
        EvilTwinUtils.start(player)
        AntiMacro.terminateEventNpc(player)
    }

    override fun talkTo(npc: NPC) {
        /*
         * Empty
         */
    }

}
