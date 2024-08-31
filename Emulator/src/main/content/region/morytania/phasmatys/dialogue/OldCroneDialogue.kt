package content.region.morytania.phasmatys.dialogue

import cfg.consts.NPCs
import content.region.misthalin.quest.anma.dialogue.OldCroneDialogue
import content.region.morytania.phasmatys.quest.ahoy.dialogue.OldCroneDialogueFile
import core.api.getQuestStage
import core.api.isQuestComplete
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Old crone dialogue.
 */
@Initializable
class OldCroneDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Info: The Old crone can be found in the house east of the slayer tower.
     * She has a central role in Ghosts Ahoy, and a minor role in Animal Magnetism.
     * Location: 3461,3558
     */

    override fun open(vararg args: Any): Boolean {
        val animalMagnetism = getQuestStage(player, "Animal Magnetism")
        npc = args[0] as NPC
        when {
            getQuestStage(player, "Ghosts Ahoy") >= 3 && (animalMagnetism < 16 || isQuestComplete(player, "Animal Magnetism")) -> openDialogue(player, OldCroneDialogueFile())
            animalMagnetism in 16..18 && getQuestStage(player, "Ghosts Ahoy") >= 3 -> options("Talk about quest. (Animal Magnetism)", "Talk about quest. (Ghosts Ahoy)", "Nevermind.").also { stage = 1 }
            animalMagnetism in 16..18 -> openDialogue(player,
                OldCroneDialogue()
            )
            else -> player("Hello, old woman.").also { stage = 0 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.HALF_GUILTY, "I lived here when this was all just fields, you know.").also { stage = END_DIALOGUE }
            1 -> when (buttonId) {
                1 -> openDialogue(player,
                    OldCroneDialogue()
                )
                2 -> openDialogue(player, OldCroneDialogueFile())
                3 -> end()
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.OLD_CRONE_1695)
    }

}
