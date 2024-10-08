package content.global.ame.quizmaster

import content.global.ame.RandomEventNPC
import core.api.lockInteractions
import core.api.runTask
import core.api.utils.WeightBasedTable
import core.game.node.entity.npc.NPC
import core.game.system.timer.impl.AntiMacro
import org.rs.consts.NPCs

/**
 * Quiz master NPC.
 */
class QuizMasterNPC(override var loot: WeightBasedTable? = null) : RandomEventNPC(NPCs.QUIZ_MASTER_2477) {

    override fun init() {
        super.init()
        sendChat("It's your lucky day!")
    }

    override fun tick() {
        super.tick()
        runTask(player, 1) {
            lock(1000)
            lockInteractions(player, 1000)
            core.api.setAttribute(player, QuizMasterUtils.PLAYER_LOCATION, player.location)
            player.properties.teleportLocation = QuizMasterUtils.EVENT_LOCATION
            AntiMacro.terminateEventNpc(player)
        }
    }

    override fun talkTo(npc: NPC) {
        player.dialogueInterpreter.open(NPCs.QUIZ_MASTER_2477, this.asNpc())
    }

}