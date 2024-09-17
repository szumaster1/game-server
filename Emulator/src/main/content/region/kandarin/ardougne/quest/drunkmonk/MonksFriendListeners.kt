package content.region.kandarin.ardougne.quest.drunkmonk

import content.region.kandarin.ardougne.quest.drunkmonk.dialogue.BrotherCedricDialogueFile
import content.region.kandarin.ardougne.quest.drunkmonk.dialogue.BrotherOmadDialogueFile
import content.region.kandarin.ardougne.quest.drunkmonk.dialogue.MonasteryMonkDialogueFile
import org.rs.consts.NPCs
import core.api.openDialogue
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC

class MonksFriendListeners : InteractionListener {
    companion object {
        const val CEDRIC = NPCs.BROTHER_CEDRIC_280
        const val OMAD = NPCs.BROTHER_OMAD_279
        const val MONK = NPCs.MONK_281
    }

    override fun defineListeners() {
        on(CEDRIC, IntType.NPC, "talk-to") { player, _ ->
            openDialogue(player, BrotherCedricDialogueFile(), NPC(NPCs.BROTHER_CEDRIC_280))
            return@on true
        }

        on(OMAD, IntType.NPC, "talk-to") { player, _ ->
            openDialogue(player, BrotherOmadDialogueFile(), NPC(NPCs.BROTHER_OMAD_279))
            return@on true
        }

        on(MONK, IntType.NPC, "talk-to") { player, _ ->
            openDialogue(player, MonasteryMonkDialogueFile(), NPC(NPCs.MONK_281))
            return@on true
        }
    }

}