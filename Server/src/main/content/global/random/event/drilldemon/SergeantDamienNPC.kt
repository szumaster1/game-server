package content.global.random.event.drilldemon

import content.global.random.RandomEventNPC
import core.api.*
import core.api.consts.NPCs
import core.api.utils.WeightBasedTable
import core.game.interaction.QueueStrength
import core.game.node.entity.npc.NPC
import core.game.system.timer.impl.AntiMacro
import core.utilities.secondsToTicks

class SergeantDamienNPC(override var loot: WeightBasedTable? = null) : RandomEventNPC(NPCs.SERGEANT_DAMIEN_2790) {

    override fun init() {
        super.init()
        sendChat(player.username.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() } + "! Drop and give me 20!")
        queueScript(player, 4, QueueStrength.SOFT) { stage: Int ->
            when (stage) {
                0 -> {
                    lock(player, secondsToTicks(30))
                    DrillDemonUtils.teleport(player)
                    AntiMacro.terminateEventNpc(player)
                    return@queueScript delayScript(player, 2)
                }

                1 -> {
                    openDialogue(player, SergeantDamienDialogue(isCorrect = true, eventStart = true), NPCs.SERGEANT_DAMIEN_2790)
                    return@queueScript stopExecuting(player)
                }

                else -> return@queueScript stopExecuting(player)
            }
        }
    }

    override fun talkTo(npc: NPC) {
        openDialogue(player, SergeantDamienDialogue(), npc)
    }
}
