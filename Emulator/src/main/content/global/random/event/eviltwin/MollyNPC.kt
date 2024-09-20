package content.global.random.event.eviltwin

import content.global.random.RandomEventNPC
import core.api.*
import core.api.utils.WeightBasedTable
import core.game.node.entity.npc.NPC
import core.game.system.timer.impl.AntiMacro

private val MOLLY: Int = 3892 + (0 and 0xFF) + (((0 shr 16) and 0xFF) * TwinColors.values().size)

/**
 * Represents the Molly NPC.
 */
class MollyNPC(override var loot: WeightBasedTable? = null) : RandomEventNPC(MOLLY) {

    override fun init() {
        super.init()
        EvilTwinUtils.start(player)
        AntiMacro.terminateEventNpc(player)
    }

    override fun talkTo(npc: NPC) {
        openDialogue(player, "")
    }

}
