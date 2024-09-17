package content.region.desert.quest.deserttreasure.dialogue

import org.rs.consts.NPCs
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.DialogueBuilder
import core.game.dialogue.DialogueBuilderFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Ice troll dialogue.
 * @author Ovenbreado
 */
@Initializable
class IceTrollDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        openDialogue(player!!, IceTrollDialogueFile(), npc)
        return false
    }

    override fun newInstance(player: Player?): Dialogue {
        return IceTrollDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ICE_TROLL_1935)
    }

}

/**
 * Represents the Ice troll dialogue file.
 * @author Ovenbreado
 */
class IceTrollDialogueFile : DialogueBuilderFile() {
    override fun create(b: DialogueBuilder) {

        b.onPredicate { _ -> true }
            .npc(FacialExpression.OLD_LAUGH1, "Hur hur hur!", "Well look here, a puny fleshy human!")
            .npc(
                FacialExpression.OLD_LAUGH1,
                "You should beware of the icy wind that runs through this valley,",
                "it will bring a fleshy like you to a cold end indeed!"
            )
            .end()
    }

}
