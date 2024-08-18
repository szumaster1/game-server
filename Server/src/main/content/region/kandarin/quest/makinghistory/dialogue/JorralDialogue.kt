package content.region.kandarin.quest.makinghistory.dialogue

import content.region.kandarin.quest.makinghistory.MHUtils
import content.region.kandarin.quest.makinghistory.MHUtils.checkRequirements
import content.region.kandarin.quest.makinghistory.OutpostHistoryCutscene
import core.api.*
import core.api.consts.Components
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Jorral dialogue.
 */
@Initializable
class JorralDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Jorral is a historian who starts the Making History and
     * Meeting History quests. He can be found in his Outpost
     * south of the Gnome Stronghold.
     * Location: 2437,3347
     */

    override fun open(vararg args: Any): Boolean {
        if (!checkRequirements(player) && getQuestStage(player, "Making History") == 0) {
            sendDialogue(player, "Jorral seems too busy to talk.")
            sendMessage(player, "you do not meet the requirements to start this quest.")
            return false
        } else if (checkRequirements(player) && getQuestStage(player, "Making History") == 0) {
            player("Hi there.")
            stage = 1
            return true
        }
        if (getVarbit(player, MHUtils.PROGRESS) == 2) {
            player("Hi there.")
            stage = 40
            return true
        }
        if (getVarbit(player, MHUtils.PROGRESS) == 3) {
            player("Hi there.")
            stage = 73
            return true
        } else if (getVarbit(player, MHUtils.DRON_PROGRESS) == 4) {
            player("Hi there.")
            stage = 63
            return true
        }
        if (inInventory(player, Items.CHEST_6759)) {
            player("Hi there.")
            stage = 45
            return true
        } else if (inInventory(player, Items.JOURNAL_6755)) {
            player("Hi there.")
            stage = 49
            return true
        } else if (inInventory(player, Items.SCROLL_6758)) {
            player("Hi there.")
            stage = 58
            return true
        }
        if(inInventory(player, Items.LETTER_6756) && getQuestStage(player, "Making History") >= 1){
            npcl(FacialExpression.HALF_ASKING,"Have you taken that letter to King Lathas in Ardougne yet?")
            stage = 400
            return true
        } else if (!inInventory(player, Items.LETTER_6756)) {
            playerl(FacialExpression.FRIENDLY, "Don't suppose you could give me the letter again?")
            stage = 86
            return true
        } else if (inInventory(player, Items.LETTER_6757)) {
            playerl(FacialExpression.FRIENDLY, "I've been to see the king and he gave me this letter.")
            stage = 88
            return true
        } else if (isQuestComplete(player, "Making History")) {
            options("How's the outpost?", "Anything else I can help you with?")
            stage = 94
            return true
        }
        return true
    }

    override fun handle(componentID: Int, buttonID: Int) : Boolean {
        when (stage) {
            0 -> player("Hi there.").also { stage++ }
            1 -> npcl(FacialExpression.HALF_GUILTY, "All is lost!").also { stage++ }
            2 -> playerl(FacialExpression.HALF_GUILTY, "Sorry?").also { stage++ }
            3 -> npc(FacialExpression.SAD, "Just look around you! This great building will soon be", "in ruin.").also { stage++ }
            4 -> playerl(FacialExpression.HALF_GUILTY, "Great building?").also { stage++ }
            5 -> npc(FacialExpression.ANNOYED, "Of course! This building has a history spanning", "generations!").also { stage++ }
            6 -> player(FacialExpression.HALF_GUILTY, "Ah, I see, a great history. But why will it be in ruin?").also { stage++ }
            7 -> npc(FacialExpression.SAD, "It's to be ripped apart to make way for King Lathas'", "new alchemists' lab.").also { stage++ }
            8 -> player(FacialExpression.HALF_GUILTY, "Does the king not respect the outpost's GREAT", "history?").also { stage++ }
            9 -> npc(FacialExpression.HALF_GUILTY, "Well, actually nobody knows what the history is. But", "there must be something.").also { stage++ }
            10 -> npc("Wait a minute. You could help me!").also { stage++ }
            11 -> playerl(FacialExpression.HALF_GUILTY, "Me?").also { stage++ }
            12 -> npc(FacialExpression.HALF_GUILTY, "Yes! Uncover the history of this building to convince", "King Lathas to leave it alone. Do you want to know", "more?").also { stage++ }
            13 -> options("Tell me more.", "I don't care about some dusty building.").also { stage++ }
            14 -> when (buttonID) {
                1 -> player(FacialExpression.HALF_GUILTY, "Tell me more.").also { stage = 16 }
                2 -> playerl(FacialExpression.HALF_GUILTY, "I don't care about some dusty building.").also { stage++ }
            }
            15 -> npc("It's doomed. DOOMED!").also { stage = END_DIALOGUE }
            16 -> npcl(FacialExpression.HALF_GUILTY, "That's what I like to hear.").also { stage++ }
            17 -> {
                end()
                OutpostHistoryCutscene(player!!).start()
            }
            22 -> playerl(FacialExpression.HALF_GUILTY, "But where should I start?").also { stage++ }
            23 -> player("What do I need to do now?").also { stage++ }
            24 -> npcl(FacialExpression.HALF_GUILTY, "There are three people that may be able to help:").also { stage++ }
            25 -> options("A trader in Ardougne", "A ghost in Port Phasmatys", "A warrior in Rellekka").also { stage++ }
            26 -> when (buttonID) {
                1 -> npc("There is a silver trader in East Ardougne called Erin,", "who I believe can help.").also { stage++ }
                2 -> npc("I've been told that there's a ghost far off in Port", "Phasmatys that moans of losing his life to this place.").also { stage = 30 }
                3 -> npc("Up near the mountains, in Rellekka there is a warrior called", "Dron whom I have spoken to in the past. He is always", "on the lookout for information that can improve his", "fighting and commanding skills.").also { stage = 35 }
            }
            27 -> playerl(FacialExpression.HALF_GUILTY, "In what way can he help?").also { stage++ }
            28 -> npc("His Great Grandfather lived in this outpost according to", "the records. He must know something!").also { stage++ }
            29 -> playerl(FacialExpression.HALF_GUILTY, "OK, I'll see what he has to say.").also { stage = 41 }
            30 -> playerl(FacialExpression.HALF_GUILTY, "Sounds ominous. Does he have a name?").also { stage++ }
            31 -> npcl(FacialExpression.HALF_GUILTY, "He does indeed. It's Droalak.").also { stage++ }
            32 -> playerl(FacialExpression.HALF_GUILTY, "I'll track him down.").also { stage++ }
            33 -> npc("It might not be so simple! You'll need an amulet of", "ghostspeak to talk to him!").also { stage++ }
            34 -> player(FacialExpression.HALF_GUILTY, "I've conversed with the undead before, that shouldn't be", "too much of a problem.").also { stage = 41 }
            35 -> player("And how's he related to this outpost?").also { stage++ }
            36 -> npc("He isn't directly, but he's studied many wars, and as", "this used to be an outpost it should have been involved", "in some war.").also { stage++ }
            37 -> player("That sounds simple enough.").also { stage++ }
            38 -> npc("He isn't the easiest person to talk tom so you may need", "to speak to his brother, Blanin, first.").also { stage = 41 }
            39 -> player("Hi there.").also { stage++ }
            40 -> npc("How's it going?").also { stage++ }
            41 -> options("What's the story so far?", "What do I need to do now?", "Got to go, bye!").also { stage++ }
            42 -> when (buttonID) {
                1 -> player("What's the story so far?").also { stage++ }
                2 -> player("What do I need to do now?").also { stage = 24 }
                3 -> player("Got to go, bye!").also { stage = END_DIALOGUE }
            }
            43 -> if(getAttribute(player, MHUtils.ATTRIBUTE_ERIN_PROGRESS, false) || getVarbit(player,
                    MHUtils.ERIN_PROGRESS
                ) == 4){
                npcl(FacialExpression.FRIENDLY, "From the two pieces of evidence, we know that the outpost was lived in by followers of Zamorak,").also { stage = 100 }
            } else if(getAttribute(player, MHUtils.ATTRIBUTE_DROALAK_PROGRESS, false) || getVarbit(player,
                    MHUtils.DROALAK_PROGRESS
                ) >= 4){
                npcl(FacialExpression.FRIENDLY, "From the journal you received detailing the experiences of a follower of Zamorak,").also { stage = 200 }
            } else if(getAttribute(player, MHUtils.ATTRIBUTE_DRON_PROGRESS, false) || getVarbit(player,
                    MHUtils.DRON_PROGRESS
                ) == 4){
                npcl(FacialExpression.FRIENDLY, "One of the survivors became King, which must be the current King Lathas' Great Grandfather and the other started the market place.").also { stage = 300 }
            } else {
                npcl(FacialExpression.ANNOYED, "What do you mean? You've discovered nothing!").also { stage = END_DIALOGUE }
            }
            44 -> player("Hi there.").also { stage++ }
            45 -> npcl(FacialExpression.FRIENDLY, "How's it going?").also { stage++ }
            46 -> playerl(FacialExpression.FRIENDLY, "Hi. I found a chest.").also { stage++ }
            47 -> npcl(FacialExpression.FRIENDLY, "Best you look inside then.").also { stage = END_DIALOGUE }

            48 -> player("Hi there.").also { stage++ }
            49 -> npcl(FacialExpression.FRIENDLY, "How's it going?").also { stage++ }
            50 -> playerl(FacialExpression.FRIENDLY, "I found a journal!").also { stage++ }
            51 -> npcl(FacialExpression.FRIENDLY, "Good work. Let's see what it says.").also { stage++ }
            52 -> sendDoubleItemDialogue(player!!, -1, Items.JOURNAL_6755, "Jorral reads through the journal.").also { stage++ }
            53 -> npcl(FacialExpression.FRIENDLY, "The person who wrote the journal spent time living in the outpost, following the order of Zamorak.").also { stage++ }
            54 -> playerl(FacialExpression.FRIENDLY, "I see.").also { stage++ }
            55 -> npcl(FacialExpression.FRIENDLY, "He talks of all the nasty things they did to the people of Ardougne, which I don't care to mention! It looks like they were stopped by someone. But it doesn't say who.").also { stage++ }
            56 -> playerl(FacialExpression.FRIENDLY, "Interesting.").also {
                removeItem(player, Items.JOURNAL_6755)
                MHUtils.checkProgress(player!!)
                stage = if(getVarbit(player, MHUtils.PROGRESS) == 3) 72 else END_DIALOGUE
                stage = END_DIALOGUE
            }

            57 -> player("Hi there.").also { stage++ }
            58 -> npcl(FacialExpression.FRIENDLY, "How's it going?").also { stage++ }
            59 -> playerl(FacialExpression.FRIENDLY, "I have been in contact with the ghost you suggested, and I've recovered this:").also { stage++ }
            60 -> sendDoubleItemDialogue(player!!, -1, Items.SCROLL_6758, "Jorral skims over the contents of the scroll.").also { stage++ }
            61 -> npcl(FacialExpression.FRIENDLY, "Very interesting. So there was a great battle at the outpost. Then one of the survivors became king, and the other started the market place. Good work.").also {
                removeItem(player, Items.SCROLL_6758)
                MHUtils.checkProgress(player!!)
                stage = if(getVarbit(player, MHUtils.PROGRESS) == 3) 72 else END_DIALOGUE
            }

            62 -> player("Hi there.").also { stage++ }
            63 -> npcl(FacialExpression.FRIENDLY, "How's it going?").also { stage++ }
            64 -> playerl(FacialExpression.FRIENDLY, "I've talked to the warrior.").also { stage++ }
            65 -> npcl(FacialExpression.FRIENDLY, "Excellent! What did he say?").also {
                openInterface(player!!, Components.FADE_TO_BLACK_115)
                stage++
            }
            66 -> playerl(FacialExpression.FRIENDLY, "Well...").also {
                openInterface(player!!, Components.FADE_FROM_BLACK_170)
                stage++
            }
            67 -> playerl(FacialExpression.FRIENDLY, "And then I asked him about getting the blood stains out of his clothes, but he didn't like that!").also { stage++ }
            68 -> npcl(FacialExpression.FRIENDLY, "It seems you're lucky he didn't beat you up! Well done, that's another piece of the puzzle.").also { stage++ }
            69 -> npcl(FacialExpression.FRIENDLY, "From the two pieces of evidence, we know that the outpost was occupied by followers of Zamorak who caused havoc to the nearby city of Ardougne.").also { stage++ }
            70 -> npcl(FacialExpression.FRIENDLY, "Ardougne called in some Saradomin followers to deal with the problem. The two sides were led by ex-friends who settled their differences and decided to worship Guthix.").also { stage++ }
            71 -> npcl(FacialExpression.FRIENDLY, "But who survived and what happened to them?").also {
                MHUtils.checkProgress(player!!)
                stage = if(getVarbit(player, MHUtils.PROGRESS) == 3) 72 else END_DIALOGUE
            }

            72 -> npcl(FacialExpression.FRIENDLY, "It all makes sense now, I never realised there was quite so much history to this place, it was more than I could have hoped.").also { stage++ }
            73 -> playerl(FacialExpression.FRIENDLY, "I'm glad to hear. What's the whole story?").also { stage++ }
            74 -> npcl(FacialExpression.FRIENDLY, "Many years ago, there were two friends who fell out over their difference of opinion in religion. One decided to dedicate his life to Saradomin, the other to Zamorak.").also { stage++ }
            75 -> npcl(FacialExpression.FRIENDLY, "Years passed and the follower of Zamorak moved into this outpost with a group of others to cause havoc for the city of Ardougne.").also { stage++ }
            76 -> npcl(FacialExpression.FRIENDLY, "The people of the city called upon some Saradomin followers, who happened to be led by the other friend.").also { stage++ }
            77 -> npcl(FacialExpression.FRIENDLY, "A battle ensued that ended with the two ex-friends at the top of the outpost as the sole survivors. Realising their mistakes they made friends again.").also { stage++ }
            78 -> npcl(FacialExpression.FRIENDLY, "One decided to become King and spread the word of equality, and the other chose to start up a market where all could trade their wares equally and fairly.").also { stage++ }
            79 -> npcl(FacialExpression.FRIENDLY, "The one that became king is the great grandfather of the King Lathas that we know!").also { stage++ }
            80 -> playerl(FacialExpression.FRIENDLY, "So what should I do now?").also { stage++ }
            81 -> npcl(FacialExpression.FRIENDLY, "Well, what I'll do is write down the details you have provided in a more easy to digest manner, along with my plans for using this building as a museum.").also { stage++ }
            82 -> playerl(FacialExpression.FRIENDLY, "Then I suppose you want ME to deliver it to the king?").also { stage++ }
            83 -> npcl(FacialExpression.FRIENDLY, "You've got that right!").also { stage++ }
            84 -> {
                end()
                sendItemDialogue(player!!, Items.LETTER_6756, "Player receives Letter.")
                addItemOrDrop(player!!, Items.LETTER_6756)
                setVarbit(player!!, MHUtils.PROGRESS, 3, true)
            }

            85 -> playerl(FacialExpression.FRIENDLY, "Don't suppose you could give me the letter again?").also { stage++ }
            86 -> npcl(FacialExpression.FRIENDLY, "Ok. Just don't lose it again!").also { stage++ }
            87 -> {
                end()
                sendItemDialogue(player!!, Items.LETTER_6756, "Player receives Letter.")
                addItemOrDrop(player!!, Items.LETTER_6756)
            }

            88 -> npcl(FacialExpression.FRIENDLY, "Quick, let me see.").also { stage++ }
            89 -> sendDoubleItemDialogue(player!!, -1, Items.LETTER_6757, "Jorral reads the letter.").also { stage++ }
            90 -> npcl(FacialExpression.FRIENDLY, "Hurrah! Good work, you've done it. You've saved the outpost!").also { stage++ }
            91 -> playerl(FacialExpression.FRIENDLY, "My pleasure.").also { stage++ }
            92 -> npcl(FacialExpression.FRIENDLY, "I can now continue with my plans for this place. Thank you.").also { stage++ }
            93 -> {
                end()
                finishQuest(player!!, "Making History")
            }

            94 -> when(buttonID){
                1 -> playerl(FacialExpression.FRIENDLY, "How's the outpost?").also { stage++ }
                2 -> end()
            }
            95 -> npcl(FacialExpression.HAPPY, "It's perfectly good. Thanks for the help again. Feel free to browse the room next door!").also { stage = END_DIALOGUE }


            100 -> npcl(FacialExpression.FRIENDLY, "whom caused havoc to the nearby city of Ardougne.").also { stage++ }
            101 -> npcl(FacialExpression.FRIENDLY, "This must be the time of 'The Dreadful Years of Tragedy', which was stopped in 'The Great battle'.").also { stage = END_DIALOGUE }

            200 -> npcl(FacialExpression.FRIENDLY, "we know that Zamorak followers were living in the outpost at some point, and that they were being a nuisance to Ardougne").also { stage++ }
            201 -> npcl(FacialExpression.FRIENDLY, "but the Journal ends abruptly, I think they were halted by someone, some force.").also { stage = END_DIALOGUE }

            300 -> npcl(FacialExpression.FRIENDLY,"But who stopped the Zamorakian followers?").also { stage = END_DIALOGUE }

            400 -> playerl(FacialExpression.FRIENDLY, "No, sorry.").also { stage++ }
            401 -> npcl(FacialExpression.NEUTRAL, "Any time soon would be great.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.JORRAL_2932)
    }

}
