package content.global.ame.freakyforest

import content.global.ame.RandomEventNPC
import core.api.*
import core.api.utils.WeightBasedTable
import core.game.interaction.QueueStrength
import core.game.node.entity.npc.NPC
import core.game.system.timer.impl.AntiMacro
import core.game.world.map.Location
import core.game.world.update.flag.context.Graphic
import org.rs.consts.NPCs
import org.rs.consts.Sounds

/**
 * Freaky forester NPC.
 */
class FreakyForesterNPC(override var loot: WeightBasedTable? = null) : RandomEventNPC(NPCs.FREAKY_FORESTER_2458) {

    override fun init() {
        super.init()
        sendChat("Ah, ${player.username}, just the person I need!")
        queueScript(player, 4, QueueStrength.SOFT) { stage: Int ->
            when (stage) {
                0 -> {
                    lock(player, 6)
                    visualize(player, 714, Graphic(308, 85, 50))
                    playAudio(player, Sounds.TELEPORT_ALL_200)
                    return@queueScript delayScript(player, 3)
                }

                1 -> {
                    setAttribute(player, FreakyForesterUtils.freakPreviousLoc, player.location)
                    teleport(player, Location.create(2599, 4777, 0))

                    FreakyForesterUtils.giveFreakTask(player)
                    AntiMacro.terminateEventNpc(player)
                    openDialogue(player, FreakyForesterDialogue(), FreakyForesterUtils.freakNpc)
                    resetAnimator(player)
                    return@queueScript stopExecuting(player)
                }

                else -> return@queueScript stopExecuting(player)
            }
        }
    }

    override fun talkTo(npc: NPC) {
        player.dialogueInterpreter.open(FreakyForesterDialogue(), npc)
    }

}