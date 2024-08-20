package content.global.random.event.eviltwin

import content.global.random.RandomEventNPC
import core.api.*
import core.api.utils.WeightBasedTable
import core.game.interaction.QueueStrength
import core.game.node.entity.npc.NPC
import core.game.node.scenery.Scenery
import core.game.system.timer.impl.AntiMacro
import core.game.world.map.Location
import core.tools.RandomFunction

private val MOLLY: Int = 3892 + (0 and 0xFF) + (((0 shr 16) and 0xFF) * EvilTwinColors.values().size)

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
