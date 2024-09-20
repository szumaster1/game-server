package content.global.skill.support.construction.decoration.skillhall

import org.rs.consts.NPCs
import org.rs.consts.Scenery
import core.api.openDialogue
import core.api.sendDialogue
import core.api.sendNPCDialogue
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.world.GameWorld
import core.tools.DARK_RED
import core.tools.END_DIALOGUE
import core.tools.RandomFunction
import core.tools.START_DIALOGUE

/**
 * Head trophy listener.
 */
class HeadTrophyListener : InteractionListener {
    private val crawlingTrophy = Scenery.CRAWLING_HAND_13481
    private val cockatriceTrophy = Scenery.COCKATRICE_HEAD_13482
    private val basiliskTrophy = Scenery.BASILISK_HEAD_13483
    private val kuraskTrophy = Scenery.KURASK_HEAD_13484
    private val abyssalTrophy = Scenery.ABYSSAL_DEMON_HEAD_13485
    private val kdbTrophy = Scenery.KBD_HEADS_13486
    private val kqTrophy = Scenery.KQ_HEAD_13487

    override fun defineListeners() {
        on(crawlingTrophy, IntType.SCENERY, "talk-to") { player, node ->
            openDialogue(player, CrawlingHandTrophyDialogue(), node)
            return@on true
        }

        on(cockatriceTrophy, IntType.SCENERY, "talk-to") { player, node ->
            openDialogue(player, CockatriceHeadTrophyDialogue(), node)
            return@on true
        }

        on(basiliskTrophy, IntType.SCENERY, "talk-to") { player, node ->
            openDialogue(player, BasiliskHeadTrophyDialogue(), node)
            return@on true
        }

        on(kuraskTrophy, IntType.SCENERY, "talk-to") { player, node ->
            openDialogue(player, KuraskHeadTrophyDialogue(), node)
            return@on true
        }

        on(abyssalTrophy, IntType.SCENERY, "talk-to") { player, node ->
            openDialogue(player, AbyssalDemonHeadTrophyDialogue(), node)
            return@on true
        }

        on(kdbTrophy, IntType.SCENERY, "talk-to") { player, node ->
            openDialogue(player, KingBlackDragonHeadTrophyDialogue(), node)
            return@on true
        }

        on(kqTrophy, IntType.SCENERY, "talk-to") { player, node ->
            openDialogue(player, KalphiteQueenHeadTrophyDialogue(), node)
            return@on true
        }
    }
}

/**
 * Abyssal demon head trophy dialogue.
 */
class AbyssalDemonHeadTrophyDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.ABYSSAL_DEMON_4230)
        when (stage) {
            0 -> {
                if (!player!!.houseManager.isInHouse(player!!)) {
                    player("Player killed an abyssal demon! Cool!").also { stage++ }
                } else {
                    npc(FacialExpression.CHILD_FRIENDLY, "Have you considered visiting$DARK_RED THE ABYSS</col>?").also { stage = 2 }
                }
            }

            1 -> npcl(FacialExpression.CHILD_THINKING, "Cool for ${if (player!!.isMale) "him" else "her"} maybe. How would you like to be stuck on a wall?").also { stage = END_DIALOGUE }

            2 -> options("I visit the abyss all the time.", "It's too scary for me.", "Could I get an abyssal whip?").also { stage++ }

            3 -> when (buttonID) {
                1 -> player(FacialExpression.HAPPY, "I visit the abyss all the time.").also { stage = 8 }
                2 -> player("It's too scary for me.").also { stage = 13 }
                3 -> player(FacialExpression.HALF_ASKING, "Could I get an abyssal whip?").also { stage++ }
            }
            4 -> npc(FacialExpression.CHILD_FRIENDLY, "You must take all your gold and all your most valued", "items, and take them into$DARK_RED THE ABYSS</col> without", "weapons or armour.").also { stage++ }
            5 -> playerl(FacialExpression.HALF_ASKING, "And then will I get an abyssal whip?").also { stage++ }
            6 -> npcl(FacialExpression.CHILD_EVIL_LAUGH, "You'll get an$DARK_RED ABYSSAL WHIPPING</col>!").also { stage++ }
            7 -> playerl(FacialExpression.SAD, "That pun was abyssmal.").also { stage = END_DIALOGUE }
            8 -> npc(FacialExpression.CHILD_NORMAL, "I bet you just rush through it though. Everyone there", "is in such a rush. No one stops to appreciate the", "beauty of$DARK_RED THE ABYSS</col>.").also { stage++ }
            9 -> options("I have to run through quickly or I'll die.", "The abyss looks pretty ugly to me.").also { stage++ }
            10 -> when (buttonID) {
                1 -> player(FacialExpression.SCARED, "I have to run through quickly or I'll die.").also { stage++ }
                2 -> player("The abyss looks pretty ugly to me.").also { stage = 12 }
            }
            11 -> npc(FacialExpression.CHILD_FRIENDLY, "Death is a small thing compared to the beauty of$DARK_RED THE", "$DARK_RED ABYSS</col>.").also { stage = END_DIALOGUE }
            12 -> npc(FacialExpression.CHILD_FRIENDLY, "Poor deluded fool. There is no hope for you at all.").also { stage = END_DIALOGUE }
            13 -> npc(FacialExpression.CHILD_FRIENDLY, "But does not the fear contribute to your appreciation", "of$DARK_RED THE ABYSS</col>?").also { stage = END_DIALOGUE }
            14 -> options("No, it's just scary.", "I suppose fear does heighten the senses.").also { stage++ }
            15 -> when (buttonID) {
                1 -> player(FacialExpression.HALF_GUILTY, "No, it's just scary.").also { stage++ }
                2 -> player(FacialExpression.HALF_GUILTY, "I suppose fear does heighten the senses.").also {
                    stage = 17
                }
            }

            16 -> npcl(FacialExpression.CHILD_EVIL_LAUGH, "Poor human. You must not judge$DARK_RED THE ABYSS</col> by the standards of this world. You must learn to embrace your fear as part of the experience of$DARK_RED THE ABYSS</col>.").also { stage = END_DIALOGUE }

            17 -> npcl(FacialExpression.CHILD_FRIENDLY, "Then you should enhance them further by raising the stakes. Next time you go to$DARK_RED THE ABYSS</col> you should take all your most valuable items with you.").also { stage = END_DIALOGUE }
        }
    }
}

/**
 * Basilisk head trophy dialogue.
 */
class BasiliskHeadTrophyDialogue : DialogueFile() {

    private val randomWord_0 = RandomFunction.getRandomElement(arrayOf("boring", "fat", "hideous", "puny", "smelly", "stupid"))
    private val randomWord_1 = RandomFunction.getRandomElement(arrayOf("beetle", "chicken", "egg", "mud", "slime", "worm"))
    private val randomWord_2 = RandomFunction.getRandomElement(arrayOf("brained", "eating", "like", "loving", "smelling", "witted"))
    private val randomWord_3 = RandomFunction.getRandomElement(arrayOf("basilisk", "idiot", "lizard", "mudworm", "slimeball", "weakling"))

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.BASILISK_4228)
        when (stage) {
            0 -> npcl(FacialExpression.CHILD_FRIENDLY, "What do you want?").also { stage++ }
            1 -> if (!player!!.houseManager.isInHouse(player!!)) {
                playerl(FacialExpression.AFRAID, "Oh, er, nothing!").also { stage = END_DIALOGUE }
            } else {
                options("I want to mock you.", "I want to apologise for killing you.", "I just wanted to check that you're okay.", "Nothing.").also { stage++ }
            }

            2 -> when (buttonID) {
                1 -> playerl(FacialExpression.FRIENDLY, "I want to mock you.").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "I want to apologise for killing you.").also { stage = 6 }
                3 -> playerl(FacialExpression.FRIENDLY, "I just wanted to check that you're okay.").also { stage = 30 }
                4 -> playerl(FacialExpression.FRIENDLY, "Nothing.").also { stage = END_DIALOGUE }
            }
            3 -> npcl(FacialExpression.CHILD_FRIENDLY, "All right. Go on then.").also { stage++ }
            4 -> playerl(FacialExpression.FRIENDLY, "You're a $randomWord_0 $randomWord_1 $randomWord_2 $randomWord_3.").also { stage++ }
            5 -> npcl(FacialExpression.CHILD_SAD, "I'm going back to sleep.").also { stage = END_DIALOGUE }
            6 -> npcl(FacialExpression.CHILD_FRIENDLY, "Go on then.").also { stage++ }
            7 -> playerl(FacialExpression.FRIENDLY, "I'm, um, very sorry I killed you.").also { stage++ }
            8 -> npcl(FacialExpression.CHILD_FRIENDLY, "Really sorry?").also { stage++ }
            9 -> options("No, not really.", "Yes, really.").also { stage++ }
            10 -> when (buttonID) {
                1 -> playerl(FacialExpression.FRIENDLY, "No, not really!").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "Yes really!").also { stage = 12 }
            }
            11 -> npcl(FacialExpression.CHILD_NORMAL, "I don't care.").also { stage = END_DIALOGUE }
            12 -> npcl(FacialExpression.CHILD_FRIENDLY, "Really really?").also { stage++ }
            13 -> options("Yes, really really.", "Don't push it.").also { stage++ }
            14 -> when (buttonID) {
                1 -> playerl(FacialExpression.FRIENDLY, "Yes, really really!").also { stage = 16 }
                2 -> playerl(FacialExpression.FRIENDLY, "Don't push it!").also { stage++ }
            }
            15 -> npcl(FacialExpression.CHILD_FRIENDLY, "I don't care anyway.").also { stage = END_DIALOGUE }
            16 -> npcl(FacialExpression.CHILD_FRIENDLY, "Fat lot of good that does, I'm still dead.").also { stage++ }
            17 -> options("I'm not THAT sorry.", "But will you forgive me?", "I promise not to do it again.").also { stage++ }
            18 -> when (buttonID) {
                1 -> playerl(FacialExpression.FRIENDLY, "I'm not THAT sorry!").also { stage = 11 }
                2 -> playerl(FacialExpression.FRIENDLY, "But will you forgive me?").also { stage = 19 }
                3 -> playerl(FacialExpression.FRIENDLY, "I promise not to do it again.").also { stage = 22 }
            }
            19 -> npcl(FacialExpression.CHILD_FRIENDLY, "Of course I'll forgive you!").also { stage++ }
            20 -> playerl(FacialExpression.FRIENDLY, "Really?").also { stage++ }
            21 -> npcl(FacialExpression.CHILD_FRIENDLY, "No!").also { stage = END_DIALOGUE }
            22 -> npcl(FacialExpression.CHILD_FRIENDLY, "Of course you won't do it again, you can only kill me once.").also { stage++ }
            23 -> options("That's why I won't do it again.", "But I won't do it to any other basilisks.").also { stage++ }
            24 -> when (buttonID) {
                1 -> playerl(FacialExpression.FRIENDLY, "That's why I won't do it again! There's be no point!").also { stage = 15 }

                2 -> playerl(FacialExpression.FRIENDLY, "But I won't do it to any other basilisks!").also { stage++ }
            }
            25 -> npcl(FacialExpression.CHILD_FRIENDLY, "Really?").also { stage++ }
            26 -> options("Yes, really!", "No, not really!", "Don't start that again!").also { stage++ }
            27 -> when (buttonID) {
                1 -> playerl(FacialExpression.FRIENDLY, "Yes, really!").also { stage = 29 }
                2 -> playerl(FacialExpression.FRIENDLY, "No, not really!").also { stage = 11 }
                3 -> player(FacialExpression.FRIENDLY, "Don't start that again!").also { stage++ }
            }
            28 -> npcl(FacialExpression.CHILD_FRIENDLY, "Leave me alone then.").also { stage = END_DIALOGUE }
            29 -> npcl(FacialExpression.CHILD_FRIENDLY, "All right then. Apology accepted. Now leave me alone.").also { stage = END_DIALOGUE }
            30 -> npcl(FacialExpression.CHILD_FRIENDLY, "Apart from being dead and stuffed and hanging on a wall, you mean?").also { stage++ }
            31 -> playerl(FacialExpression.FRIENDLY, "Uh... yeah, apart from that are you okay?").also { stage++ }
            32 -> npcl(FacialExpression.CHILD_FRIENDLY, "Actually there's something blocking my view of the far wall.").also { stage++ }
            33 -> playerl(FacialExpression.FRIENDLY, "I don't see anything.").also { stage++ }
            34 -> npcl(FacialExpression.CHILD_FRIENDLY, "Perhaps if you were to move to one side of me.").also { stage++ }
            35 -> sendDialogue(player!!, "You walk to the side of the basilisk head...").also { stage++ }
            36 -> playerl(FacialExpression.FRIENDLY, "I still don't see anything.").also { stage++ }
            37 -> npcl(FacialExpression.CHILD_FRIENDLY, "Oh, it's moved away. I can see now.").also { stage = END_DIALOGUE }
        }
    }
}


/**
 * Cockatrice head trophy dialogue.
 */
class CockatriceHeadTrophyDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.COCKATRICE_4227)
        when (stage) {
            0 -> {
                if (!player!!.houseManager.isInHouse(player!!)) {
                    player("Hey, a cockatrice!").also { stage = 100 }
                } else {
                    npcl(FacialExpression.CHILD_FRIENDLY, "You deaded me!").also { stage++ }
                }
            }

            1 -> playerl(FacialExpression.FRIENDLY, "Well, yes.").also { stage++ }
            2 -> npcl(FacialExpression.CHILD_FRIENDLY, "What did you do that for?").also { stage++ }
            3 -> options("A Slayer Master told me to.", "So I could mount your head on my wall.", "I just wanted to.").also { stage++ }

            4 -> when (buttonID) {
                1 -> playerl(FacialExpression.FRIENDLY, "A Slayer Master told me to.").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "So I could mount your head on my wall.").also { stage = 8 }
                3 -> playerl(FacialExpression.FRIENDLY, "I just wanted to.").also { stage = 11 }
            }
            5 -> npcl(FacialExpression.CHILD_FRIENDLY, "Why do the Slayer Masters all pick on poor cockatrice?").also { stage++ }
            6 -> playerl(FacialExpression.FRIENDLY, "They pick on lots of other creatures too.").also { stage++ }
            7 -> npcl(FacialExpression.CHILD_FRIENDLY, "Then mount one of them on your wall and let poor cockatrice rest in peace!").also { stage = END_DIALOGUE }
            8 -> npcl(FacialExpression.CHILD_FRIENDLY, "Another cockatrice falls victim to the dreaded mirror shield!").also { stage++ }
            9 -> playerl(FacialExpression.FRIENDLY, "Don't take it personally. You look good on my wall.").also { stage++ }
            10 -> npcl(FacialExpression.CHILD_FRIENDLY, "I don't care! I think I looked better with a body!").also { stage = END_DIALOGUE }
            11 -> npcl(FacialExpression.CHILD_FRIENDLY, "You dirty rotten swine, you!").also { stage++ }
            12 -> playerl(FacialExpression.FRIENDLY, "Steady on.").also { stage++ }
            13 -> npcl(FacialExpression.CHILD_FRIENDLY, "I will kill you with my paralyzing-type magic eyes look!").also { stage++ }
            14 -> npcl(FacialExpression.CHILD_FRIENDLY, "Dots appear in air between eyes and victim. Dot! Dot! Dotty!").also { stage++ }
            15 -> playerl(FacialExpression.FRIENDLY, "Er, nothing's happening...").also { stage++ }
            16 -> npcl(FacialExpression.CHILD_FRIENDLY, "Concentrates mental power. Eyes narrow beak clenches veins on head stand out.").also { stage++ }
            17 -> npcl(FacialExpression.CHILD_FRIENDLY, "Strain!").also { stage++ }
            18 -> playerl(FacialExpression.FRIENDLY, "You're dead, cockatrice. Your eyes are glass beads. It won't work.").also { stage++ }
            19 -> npcl(FacialExpression.CHILD_FRIENDLY, "STRA-A-AIN!").also { stage++ }
            20 -> playerl(FacialExpression.FRIENDLY, "think I'll leave it to you.").also { stage = END_DIALOGUE }
            100 -> npc(FacialExpression.CHILD_FRIENDLY, "House owner deaded me! That wasn't very nice!").also { stage = END_DIALOGUE }
        }
    }
}

/**
 * Crawling hand trophy dialogue.
 */
class CrawlingHandTrophyDialogue : DialogueFile() {

    private val randomConversation = RandomFunction.getRandomElement(arrayOf(0, 1, 2))
    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.CRAWLING_HAND_4226)
        if (!player!!.houseManager.isInHouse(player!!)) {
            when (stage) {
                0 -> playerl(FacialExpression.LAUGH, "Hey, a crawling hand!").also { stage = 10 }
                10 -> npc(FacialExpression.CHILD_FRIENDLY, "yes, what?").also { stage = 20 }
                20 -> player("House owner must be pretty handy to have slayed that!").also { stage = END_DIALOGUE }
            }
        }

        if (randomConversation == 0) {
            when (stage) {
                START_DIALOGUE -> playerl(FacialExpression.LAUGH, "Hey, I was going to make some furniture, do you think you could lend a HAND?").also { stage = 100 }
                100 -> npc(FacialExpression.CHILD_NEUTRAL, "Very funny.").also { stage = END_DIALOGUE }
            }
        }

        if (randomConversation == 1) {
            when (stage) {
                START_DIALOGUE -> playerl(FacialExpression.ASKING, "Hey, hand, do you want to know how I slayed you?").also { stage = 200 }
                200 -> npc(FacialExpression.CHILD_NEUTRAL, "I don't know, how?").also { stage = 300 }
                300 -> player(FacialExpression.LOUDLY_LAUGHING, "Because you're just a hand! You're ARMLESS!").also { stage = END_DIALOGUE }
            }
        }

        if (randomConversation == 2) {
            when (stage) {
                START_DIALOGUE -> playerl(FacialExpression.HALF_ASKING, "Hey, you're just a hand, right? So what do you eat?").also { stage = 400 }
                400 -> npc(FacialExpression.CHILD_NORMAL, "Finger food, of course!").also { stage = END_DIALOGUE }
            }
        }
    }
}

/**
 * Kalphite queen head trophy dialogue.
 */
class KalphiteQueenHeadTrophyDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.KALPHITE_QUEEN_4234)
        when (stage) {
            0 -> npcl(FacialExpression.CHILD_THINKING, "Soft-thing! How dare you approach the queen of the kalphite?").also { stage++ }

            1 -> {
                if (!player!!.houseManager.isInHouse(player!!)) {
                    player("Player killed you! I can do what I like.").also { stage++ }
                } else {
                    player("I killed you, remember?").also { stage = 3 }
                }
            }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "Player killed me but you could not. My successor will be as strong as me. Come down to meet her, she is unafraid.").also { stage = END_DIALOGUE }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "Yes you killed the queen, but by now another will have risen up! One kalphite may die but the hive goes on!").also { stage++ }
            4 -> npcl(FacialExpression.CHILD_NORMAL, "The kalphite race grows stronger everyday, and our young feed on the blood of the soft-things that invade from above!").also { stage++ }
            5 -> npcl(FacialExpression.CHILD_NORMAL, "Someday we will overrun the world again and all soft creatures will die.").also { stage++ }
            6 -> npcl(FacialExpression.CHILD_FRIENDLY, "But we will reserve the worst fate for those who have killed a queen and hung her head in their house.").also { stage++ }
            7 -> npcl(FacialExpression.CHILD_THINKING, "We will lay eggs in your brain and you will not die until it explodes and a million kalphites emerge to make a grand hive of all the world!").also { stage++ }
            8 -> options("You don't scare me!", "Please don't kill me!").also { stage++ }
            9 -> when (buttonID) {
                1 -> player(FacialExpression.CALM, "You don't scare me!").also { stage++ }
                2 -> player(FacialExpression.WORRIED, "Please don't kill me!").also { stage = 11 }

            }
            10 -> npcl(FacialExpression.CHILD_NORMAL, "Your pitiful misplaced confidence is irrelevant. You will all die!").also { stage = END_DIALOGUE }
            11 -> npcl(FacialExpression.CHILD_EVIL_LAUGH, "Ha ha ha! It is too late for pleading now, pathetic mammal! Your fate is sealed!").also { stage = END_DIALOGUE }
        }
    }
}

/**
 * King black dragon head trophy dialogue.
 */
class KingBlackDragonHeadTrophyDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        when (stage) {
            0 -> if (!player!!.houseManager.isInHouse(player!!)) {
                playerl(FacialExpression.AFRAID, "Hey, House owner killed the King Black Dragon!").also { stage = 23 }
            } else {
                playerl(FacialExpression.FRIENDLY, "How do you feel about all the more powerful monsters?").also { stage++ }
            }
            1 -> sendNPCDialogue(player!!, NPCs.LEFT_HEAD_4231, "There no monsters more powerful than us!", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            2 -> sendNPCDialogue(player!!, NPCs.MIDDLE_HEAD_4232, "We top monster of all ${GameWorld.settings!!.name}!", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            3 -> playerl(FacialExpression.FRIENDLY, "No you're not. The Kalphite Queen is more powerful than you!").also { stage++ }
            4 -> sendNPCDialogue(player!!, NPCs.MIDDLE_HEAD_4232, "Kalphite Queen? What that?", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            5 -> playerl(FacialExpression.FRIENDLY, "She's a giant insect who lives in the desert.").also { stage++ }
            6 -> sendNPCDialogue(player!!, NPCs.MIDDLE_HEAD_4232, "Insect?", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            7 -> sendNPCDialogue(player!!, NPCs.RIGHT_HEAD_4233, "Ha ha ha ha!", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            8 -> sendNPCDialogue(player!!, NPCs.LEFT_HEAD_4231, "No insect tougher than us! We best!", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            9 -> playerl(FacialExpression.FRIENDLY, "No, she's way tougher than you!").also { stage++ }
            10 -> sendNPCDialogue(player!!, NPCs.LEFT_HEAD_4231, "We no believe it!", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            11 -> sendNPCDialogue(player!!, NPCs.MIDDLE_HEAD_4232, "Even if Kalphite Queen real, which we doubt, second best not bad, is it?", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            12 -> playerl(FacialExpression.FRIENDLY, "But it's not just the Kalphite Queen. What about TzTok-Jad?").also { stage++ }
            13 -> sendNPCDialogue(player!!, NPCs.LEFT_HEAD_4231, "Never heard of it!", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            14 -> playerl(FacialExpression.FRIENDLY, "Or Dagannoth Rex?").also { stage++ }
            15 -> sendNPCDialogue(player!!, NPCs.RIGHT_HEAD_4233, "You making it up!", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            16 -> playerl(FacialExpression.FRIENDLY, "Or the Chaos Elemental?").also { stage++ }
            17 -> sendNPCDialogue(player!!, NPCs.MIDDLE_HEAD_4232, "Now then, how we know you not just making these monsters up to demoralise us?", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            18 -> playerl(FacialExpression.FRIENDLY, "Alright, then, what about me?").also { stage++ }
            19 -> sendNPCDialogue(player!!, NPCs.LEFT_HEAD_4231, "Puny human! You not fearsome monster!", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            20 -> playerl(FacialExpression.FRIENDLY, "I defeated you, didn't I? So, I must be stronger than you!").also { stage++ }
            21 -> sendNPCDialogue(player!!, NPCs.LEFT_HEAD_4231, "You got lucky! We get you next time!", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            22 -> playerl(FacialExpression.FRIENDLY, "Now that you're just a stuffed head? I don't think so!").also { stage = END_DIALOGUE }
            23 -> sendNPCDialogue(player!!, NPCs.MIDDLE_HEAD_4232, "No, he didn't?", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            24 -> sendNPCDialogue(player!!, NPCs.LEFT_HEAD_4231, "What? Oh, ah, no. Course he didn't. We actually artificial likeness of King Black Dragon. No one could really kill King Black Dragon!", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            25 -> sendNPCDialogue(player!!, NPCs.MIDDLE_HEAD_4232, "No! We...no, it far too powerful!", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            26 -> sendNPCDialogue(player!!, NPCs.RIGHT_HEAD_4233, "What are you talking about? Of course we King Black Dragon!", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            27 -> sendNPCDialogue(player!!, NPCs.MIDDLE_HEAD_4232, "Shut up, idiot!", FacialExpression.CHILD_FRIENDLY).also { stage = END_DIALOGUE }
        }
    }
}


/**
 * Kurask head trophy dialogue.
 */
class KuraskHeadTrophyDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.KURASK_4229)
        when (stage) {
            0 -> npcl(FacialExpression.CHILD_FRIENDLY, "I KILL YOU!!!").also { stage++ }
            1 -> if (!player!!.houseManager.isInHouse(player!!)) {
                player(FacialExpression.FRIENDLY, "No, House owner kill you!").also { stage = END_DIALOGUE }
            } else {
                player(FacialExpression.FRIENDLY, "No, I kill you!").also { stage++ }
            }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "UUUHRG! Now I kill you!").also { stage++ }
            3 -> playerl(FacialExpression.FRIENDLY, "How are you going to do that? You're just a head on a wall!").also { stage++ }
            4 -> npcl(FacialExpression.CHILD_FRIENDLY, "Uhhhhrrr...").also { stage++ }
            5 -> options("Why are you so violent?", "What do you think about up there?", "I killed you really easily!").also { stage++ }
            6 -> when (buttonID) {
                1 -> playerl(FacialExpression.FRIENDLY, "Why are you so violent?").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "What do you think about up there?").also { stage = 23 }
                3 -> playerl(FacialExpression.HAPPY, "I killed you really easily!").also { stage = 18 }
            }
            7 -> npcl(FacialExpression.CHILD_FRIENDLY, "You kill me! Uuurgh! That make me angry!").also { stage++ }
            8 -> options("You seemed pretty angry before I killed you.", "I'm sorry I killed you.", "I killed you really easily!").also { stage++ }
            9 -> when (buttonID) {
                1 -> playerl(FacialExpression.FRIENDLY, "You seemed pretty angry before I killed you.").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "I'm sorry I killed you.").also { stage = 11 }
                3 -> playerl(FacialExpression.HAPPY, "I killed you really easily!").also { stage = 18 }
            }
            10 -> npcl(FacialExpression.CHILD_FRIENDLY, "I like angry!").also { stage = END_DIALOGUE }
            11 -> npcl(FacialExpression.CHILD_FRIENDLY, "I hate sorry! Makes me more angry! WANT TO KILL YOU!").also { stage++ }
            12 -> options("Please try to calm down.", "I'm not really sorry.").also { stage++ }
            13 -> when (buttonID) {
                1 -> playerl(FacialExpression.FRIENDLY, "Please try to calm down!").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "I'm not really sorry!").also { stage = 15 }
            }
            14 -> npcl(FacialExpression.CHILD_FRIENDLY, "Hate calm! Smash it! Hur hur hur!").also { stage = END_DIALOGUE }
            15 -> npcl(FacialExpression.CHILD_FRIENDLY, "That make me more angry! Uuuurgh!").also { stage++ }
            16 -> playerl(FacialExpression.FRIENDLY, "Is there anything that doesn't make you angry?").also { stage++ }
            17 -> npcl(FacialExpression.CHILD_FRIENDLY, "No! I like angry! Hur hur hur!").also { stage = END_DIALOGUE }
            18 -> npcl(FacialExpression.CHILD_FRIENDLY, "Uhhhhrrr...").also { stage++ }
            19 -> playerl(FacialExpression.FRIENDLY, "Yeah! I could kill you again in my sleep! I think I might go off and kill some other kurask!").also { stage++ }
            20 -> npcl(FacialExpression.CHILD_FRIENDLY, "Uuuurrrrh! Hate you!").also { stage++ }
            21 -> playerl(FacialExpression.FRIENDLY, "What are you going to do about it? Eh? I totally owned you!").also { stage++ }
            22 -> npcl(FacialExpression.CHILD_FRIENDLY, "Hate you hate you hate you!!!").also { stage = END_DIALOGUE }
            23 -> playerl(FacialExpression.FRIENDLY, "What do you think about up there?").also { stage++ }
            24 -> npcl(FacialExpression.CHILD_FRIENDLY, "Think?").also { stage++ }
            25 -> playerl(FacialExpression.FRIENDLY, "You know, what goes through your tiny stuffed head?").also { stage++ }
            26 -> npcl(FacialExpression.CHILD_FRIENDLY, "Little bugs...").also { stage++ }
            27 -> playerl(FacialExpression.FRIENDLY, "You have bugs living in you? Eww!").also { stage++ }
            28 -> npcl(FacialExpression.CHILD_FRIENDLY, "Little bugs! Stomp and crush and stomp!").also { stage++ }
            29 -> options("Yeah! Stomp the bugs!", "What have the bugs done to you?", "You can't, you've got no feet!").also { stage++ }
            30 -> when (buttonID) {
                1 -> playerl(FacialExpression.FRIENDLY, "Yeah! Stomp the bugs!").also { stage++ }
                2 -> player(FacialExpression.FRIENDLY, "What have the bugs done to you?").also { stage = 38 }
                3 -> player(FacialExpression.FRIENDLY, "You can't, you've got no feet!").also { stage = 40 }
            }
            31 -> npcl(FacialExpression.CHILD_FRIENDLY, "Stomp crush splat!").also { stage++ }
            32 -> npcl(FacialExpression.CHILD_FRIENDLY, "Smash! Destroy! Crunch break tear destroy splunch! Hurt wound kill hit punch stab slash kill!").also { stage++ }
            33 -> options("'Splunch'? That's not a word!", "You said 'kill' twice.", "Yeah! Kill smash destroy!").also { stage++ }
            34 -> when (buttonID) {
                1 -> playerl(FacialExpression.FRIENDLY, "'Splunch'? That's not a word!").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "You said 'kill' twice.").also { stage = 36 }
                3 -> player(FacialExpression.FRIENDLY, "Yeah! Kill smash destroy!").also { stage = 37 }
            }
            35 -> npcl(FacialExpression.CHILD_FRIENDLY, "I HATE WORDS! Kill all words!").also { stage = END_DIALOGUE }
            36 -> npcl(FacialExpression.CHILD_FRIENDLY, "I like kill! Hur hur hur hur!").also { stage = END_DIALOGUE }
            37 -> npcl(FacialExpression.CHILD_FRIENDLY, "Kill smash destroy! Hur hur hur!").also { stage = END_DIALOGUE }
            38 -> npcl(FacialExpression.CHILD_FRIENDLY, "Skitter skitter through head noise in ears behind eyes.").also { stage++ }
            39 -> npcl(FacialExpression.CHILD_FRIENDLY, "HATE THEM! Kill kill kill!").also { stage = END_DIALOGUE }
            40 -> playerl(FacialExpression.FRIENDLY, "You can't, you've got no feet!").also { stage++ }
            41 -> npcl(FacialExpression.CHILD_FRIENDLY, "No feet...").also { stage++ }
            42 -> npcl(FacialExpression.CHILD_FRIENDLY, "Hate lack of feet! Stomp lack of feet! Kill crush destroy smash!").also { stage++ }
            43 -> playerl(FacialExpression.FRIENDLY, "That makes no sense! You can't destroy the absence of something!").also { stage++ }
            44 -> npcl(FacialExpression.CHILD_FRIENDLY, "Hate requirement to make sense! Smash it kill it destroy kill kill!").also { stage++ }
            45 -> playerl(FacialExpression.FRIENDLY, "You can't physically destroy an abstract concept! It's impossible!").also { stage++ }
            46 -> npcl(FacialExpression.CHILD_FRIENDLY, "Hate abstract concepts! Hate impossible! Kill kill kill destroy smash!").also { stage++ }
            47 -> playerl(FacialExpression.FRIENDLY, "This is getting both surreal and repetitive.").also { stage = END_DIALOGUE }
        }
    }
}
