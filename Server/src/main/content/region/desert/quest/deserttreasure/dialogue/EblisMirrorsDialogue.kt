package content.region.desert.quest.deserttreasure.dialogue

import content.region.desert.quest.deserttreasure.DesertTreasure
import cfg.consts.NPCs
import core.api.getQuestStage
import core.api.openDialogue
import core.api.setQuestStage
import core.game.dialogue.Dialogue
import core.game.dialogue.DialogueBuilder
import core.game.dialogue.DialogueBuilderFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Eblis mirrors dialogue.
 * @author Ovenbreado
 */
@Initializable
class EblisMirrorsDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        openDialogue(player!!, EblisMirrorsDialogueFile(), npc)
        return false
    }

    override fun newInstance(player: Player?): Dialogue {
        return EblisMirrorsDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.EBLIS_1924, NPCs.EBLIS_1925)
    }
}

/**
 * Represents the Eblis mirrors dialogue file.
 * @author Ovenbreado
 */
class EblisMirrorsDialogueFile : DialogueBuilderFile() {
    override fun create(b: DialogueBuilder) {

        b.onQuestStages(DesertTreasure.questName, 8)
            .npcl("Ah, so you got here at last.")
            .npc("As you may noticed, I have made the mirrors for", "the spell, and cast the enchantment upon them.")
            .npc(
                "By simply looking into each mirror, you will be able to",
                "see the area where the trace magics from the Diamonds",
                "of Azzanadra are emanating from."
            )
            .npc(
                "Unfortunately, I cannot narrow the search closer with",
                "this kind of spell, but if you search the areas shown to",
                "you, you may be able to find some clues leading you to",
                "the evil warriors of Zamorak who stole the diamonds in"
            )
            .npc("the first place.")
            .player(
                FacialExpression.THINKING,
                "So you can't be anymore specific about where to look",
                "for these warriors and their diamonds?"
            )
            .npc(
                "I'm afraid not, other than the direction that the mirror",
                "is facing will be approximately the direction you will",
                "need to head in."
            )
            .npc("Make sure to come and speak to me when you have", "retrieved all four diamonds.")
            .endWith { _, player ->
                if (getQuestStage(player, DesertTreasure.questName) == 8) {
                    setQuestStage(player, DesertTreasure.questName, 9)
                }
            }

        b.onQuestStages(DesertTreasure.questName, 9)
            .playerl("So can you give me any help on where to find these warriors and their diamonds?")
            .npcl("No, the magic used in this spell is powerful, but inaccurate. The direction the scrying glass faces is roughly the direction you will find the warrior, but I'm afraid I")
            .npcl("can't be any more help than that.")
            .playerl("I don't understand why there are six mirrors when there are only four diamonds...")
            .npcl("As I say, the enchantment is very inaccurate.")
            .npcl("I can only focus upon the aura the diamonds have left behind them, so any place where the Diamonds were present for a significant period of time will still be shown - such as the Bandit Camp where I make my home.")
            .npcl("My apologies, but magic is an inaccurate art in many respects.")
            .npcl("Don't forget to come back here when you have collected all four diamonds.")
            .end()


    }
}