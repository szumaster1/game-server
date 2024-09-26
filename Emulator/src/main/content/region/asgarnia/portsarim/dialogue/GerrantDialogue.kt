package content.region.asgarnia.portsarim.dialogue

import core.api.*
import org.rs.consts.Items
import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.DialogueBuilder
import core.game.dialogue.DialogueBuilderFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import org.rs.consts.QuestName

/**
 * Represents the Gerrant dialogue.
 */
@Initializable
class GerrantDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Gerrant is the owner of Fishy Business in Port Sarim.
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        openDialogue(player, GerrantDialogueFile(), npc)
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Let's see what you've got then.", "Sorry, I'm not interested.").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.HAPPY, "Let's see what you've got then.").also { stage++ }
                2 -> player(FacialExpression.HALF_GUILTY, "Sorry, I'm not interested.").also { stage = END_DIALOGUE }
            }
            2 -> {
                end()
                openNpcShop(player, NPCs.GERRANT_558)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GERRANT_558)
    }
}

/**
 * Represents the Gerrant dialogue file.
 */
class GerrantDialogueFile : DialogueBuilderFile() {
    override fun create(b: DialogueBuilder) {

        b.onPredicate { _ -> true }
            .npc(FacialExpression.HAPPY, "Welcome! You can buy fishing equipment at my store.", "We'll also buy anything you catch off you.")
            .options()
            .let { optionBuilder ->
                optionBuilder.option_playerl("Let's see what you've got then.")
                    .endWith { _, player ->
                        openNpcShop(player, npc!!.id)
                    }

                optionBuilder.option_playerl("Sorry, I'm not interested.")
                    .end()

                optionBuilder.optionIf("I want to find out how to catch a lava eel.") { player -> return@optionIf getQuestStage(player, QuestName.HEROES_QUEST) >= 1 }
                    .playerl("I want to find out how to catch a lava eel.")
                    .npcl("Lava eels, eh? That's a tricky one, that is. You'll need a lava-proof fishing rod. The method for making this would be to take an ordinary fishing rod, and then cover it with fire-proof blamish oil.")
                    .branch { player ->
                        return@branch if (inInventory(player, Items.BLAMISH_SNAIL_SLIME_1581)) { 1 } else { 0 }
                    }.let { branch ->
                        branch.onValue(1)
                            .npcl("Of course, you knew that already.")
                            .playerl("So where can I fish lava eels?")
                            .npcl("Taverley dungeon or the lava maze in the Wilderness.")
                            .end()

                        branch.onValue(0)
                            .npcl("You know... thinking about it... I may have a jar of blamish slime around here somewhere. Now where did I put it?")
                            .linel("Gerrant searches around a bit.")
                            .betweenStage { df, player, _, _ ->
                                addItemOrDrop(player, Items.BLAMISH_SNAIL_SLIME_1581)
                            }
                            .npcl("Aha! Here it is! Take this slime, mix it with some Harralander and water and you'll have the blamish oil you need to treat your fishing rod.")
                            .end()
                    }

            }
    }
}