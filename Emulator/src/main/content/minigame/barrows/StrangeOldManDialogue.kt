package content.minigame.barrows

import core.api.addItemOrDrop
import org.rs.consts.Items
import org.rs.consts.NPCs
import core.api.inInventory
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.RandomFunction

/**
 * Represents the Strange old man dialogue.
 */
@Initializable
class StrangeOldManDialogue(player: Player? = null) : Dialogue(player) {

    private var conversationNum = RandomFunction.getRandom(4)

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        if (inInventory(player, Items.SPADE_952)) {
            when (conversationNum) {
                1 -> when (stage) {
                    0 -> npcl(FacialExpression.ASKING, "Pst, wanna hear a secret?").also { stage++ }
                    1 -> options("Sure!", "No, thanks.").also { stage++ }
                    2 -> when (buttonId) {
                        1 -> player("Sure!").also { stage++ }
                        2 -> player("No, thanks.").also { stage = END_DIALOGUE }
                    }

                    3 -> npcl(FacialExpression.LAUGH, "They're not normal!").also { stage = END_DIALOGUE }
                }

                2 -> when (stage) {
                    0 -> npcl(FacialExpression.NEUTRAL, "Knock, knock.").also { stage++ }
                    1 -> playerl(FacialExpression.ASKING, "Who's there?").also { stage++ }
                    2 -> npcl(FacialExpression.LAUGH, "A big scary monster, HAHAHAHAHAHAHAHAHAHA!").also { stage++ }
                    3 -> playerl(FacialExpression.HALF_ROLLING_EYES, "Okay...").also { stage = END_DIALOGUE }
                }

                3 -> when (stage) {
                    0 -> npcl(FacialExpression.HALF_ASKING, "What? I didn't ask for a book!").also { stage++ }
                    1 -> {
                        end()
                        addItemOrDrop(player, Items.CRUMBLING_TOME_4707, 1)
                        stage = END_DIALOGUE
                    }
                }

                4 -> when (stage) {
                    0 -> npcl(FacialExpression.FURIOUS, "AAAAAAAAARRRRRRGGGGGHHHHHHHH!").also { stage++ }
                    1 -> options("What's wrong?", "I'll leave you to it, then...").also { stage++ }
                    2 -> when (buttonId) {
                        1 -> npcl(FacialExpression.FURIOUS, "AAAAAAAAARRRRRRGGGGGHHHHHHHH!").also { stage = END_DIALOGUE }
                        2 -> playerl(FacialExpression.STRUGGLE, "I'll leave you to it, then...").also { stage = END_DIALOGUE }
                    }
                }
            }
        } else {
            when (stage) {
                0 -> npcl(FacialExpression.HALF_ASKING, "Dig, dig, DIG! You want to dig? You need a spade!").also { stage++ }
                1 -> playerl(FacialExpression.ASKING, "Yes you're right, I probably do. Where can I get one?").also { stage++ }
                2 -> npcl(FacialExpression.FRIENDLY, "Ohh... Spades have cost me so much hassle - always being pestered for them! I ended up giving in and just putting one at each mound for you forgetful adventurers to use.").also { stage = END_DIALOGUE }
            }
        }
        return true
    }


    /*
    if (player.questRepository.hasStarted("Temple at Senntisten")) {
        when (stage) {
            1 -> npcl(FacialExpression.NEUTRAL, "At last, the time comes. I have been expecting your arrival since before you were born.").also { stage++ }
            2 -> playerl(FacialExpression.HALF_ASKING, "You're not speaking gibberish any more, what is going on?").also { stage++ }
            3 -> npcl(FacialExpression.NEUTRAL, "After all this time, I'm not of the 'It will wait another day' school of thought. You can ask our employer what is going on.").also { stage++ }
            4 -> npcl(FacialExpression.NEUTRAL, "For now, get into the Barrows, show the brothers who is boss, and recover the icon.").also { stage++ }
            5 -> npcl(FacialExpression.NEUTRAL, "They will not hand it over without a good beating, though. So batter the lot of them, to be sure.").also { stage++ }
            6 -> playerl(FacialExpression.FRIENDLY, "Right, but I always thought you were just some mad old bloke with a spade obsession.").also { stage++ }
            7 -> npcl(FacialExpression.NEUTRAL, "I do my best to look useless; if only they knew the truth. it would chill their hearts.").also { stage++ }
            8 -> npcl(FacialExpression.NEUTRAL, "Anyway, who knows who is listening? I'd best get back to what I do for now.").also { stage = END_DIALOGUE }
        }
    }

    if (inInventory(player, Items.BARROWS_ICON_15378)) {
        when (stage) {
            1 -> playerl(FacialExpression.FRIENDLY, "Hello.").also { stage++ }
            2 -> npcl(FacialExpression.HAPPY, "You have it! Oh, the master will be delighted.").also { stage++ }
            3 -> playerl(FacialExpression.HALF_ASKING, "Are you talking about the icon I got from the Barrows?").also { stage++ }
            4 -> npcl(FacialExpression.HAPPY, "Indeed, indeed! Run to him, run as fast as you can!").also { stage++ }
            5 -> playerl(FacialExpression.FRIENDLY, "Run back to Azzanadra? Why?").also { stage++ }
            6 -> npcl(FacialExpression.HAPPY, "No reason, I just like to make people run places.").also { stage++ }
            7 -> npcl(FacialExpression.HAPPY, "And how they run! You have to get the voice right, though.").also { stage++ }
            8 -> playerl(FacialExpression.FRIENDLY, "I see.").also { stage++ }
            9 -> npcl(FacialExpression.SAD, "It can get a bit lonely here, I suppose. Still, there's always digging!").also { stage = END_DIALOGUE }
        }
    } else {
        when (stage) {
            1 -> playerl(FacialExpression.OLD_DISTRESSED, "I've lost the icon I got from the Barrows.").also { stage++ }
            2 -> npcl(FacialExpression.HALF_ASKING, "Could be...could be I found a little something. A trinket, see?").also { stage++ }
            3 -> sendItemDialogue(player, Items.BARROWS_ICON_15378, "The Strange Old man shakingly hands you the Barrows icon.").also { stage++ }
            4 -> npcl(FacialExpression.FRIENDLY, "See? Digging is not so crazy now!").also { stage = END_DIALOGUE }
        }
    }
    */

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.STRANGE_OLD_MAN_2024)
    }

}
