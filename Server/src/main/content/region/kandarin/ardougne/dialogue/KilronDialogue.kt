package content.region.kandarin.ardougne.dialogue

import content.region.kandarin.quest.biohazard.dialogue.KilronDialogueFile
import cfg.consts.NPCs
import core.api.isQuestComplete
import core.api.isQuestInProgress
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Kilron dialogue.
 */
@Initializable
class KilronDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Man found in the south-eastern corner of the wall
     * isolating East Ardougne from West Ardougne.
     * He plays a minor role in the Biohazard quest,
     * helping to smuggle the player over the wall without
     * alerting the mourners with the aid of his partner Omart.
     * Location: 2556,3266
     */

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (isQuestComplete(player, "Plague City") && isQuestComplete(player, "Biohazard")) {
            npcl(FacialExpression.FRIENDLY, "Looks like you won't be needing the rope ladder any more, adventurer. I heard it was you who started the revolution and freed West Ardougne!").also { stage = END_DIALOGUE }
        } else if (isQuestInProgress(player, "Biohazard", 1, 99)) {
            end().also { openDialogue(player, KilronDialogueFile()) }
        } else {
            npc("Hello.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.KILRON_349)
    }
}
