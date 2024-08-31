package content.activity.jobs

import content.activity.jobs.dialogue.CheckJobDialogue
import content.activity.jobs.dialogue.StartJobDialogue
import content.activity.jobs.impl.Employers
import core.api.openDialogue
import core.api.sendNPCDialogue
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC

/**
 * Handles the work-for actions for the NPCs.
 * @author Ceikry, vddCore
 */
class WorkListener : InteractionListener {
    override fun defineListeners() {
        on(Employers.values().map { employer -> employer.npcId }.toIntArray(), IntType.NPC, "work-for") { player, node ->
            val playerJobManager = JobManager.getInstance(player)
            val job = playerJobManager.job

            // Check if the player is talking to the NPC that gave them their job
            if (job != null && job.employer.npcId == node.id) {
                if (playerJobManager.jobAmount == 0) {
                    playerJobManager.rewardPlayer()
                    sendNPCDialogue(
                        player, job.employer.npcId, "There you go, thanks for your help. You're quite a skilled worker!"
                    )
                } else {
                    openDialogue(player, CheckJobDialogue(), node as NPC)
                }

                return@on true
            }

            openDialogue(player, StartJobDialogue(), node as NPC)
            return@on true
        }
    }
}