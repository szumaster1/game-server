package content.region.asgarnia.handlers.portsarim.mogre

import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.consts.Sounds
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE

class SobberSkippyDialogue : DialogueFile() {

    /*
        Skippy is found behind Hetty's house in Rimmington.
        He is drunk, and you need to sober him up, before he
        will tell you about the Mogres.
        Location = 2983, 3196
    */

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.SKIPPY_2796)
        when(stage) {
            0 -> playerl("Well, I could dump this bucket of water over him. That would sober him up a little.").also { stage++ }
            1 -> options("Throw the water!", "I think I'll leave it for now").also { stage++ }
            2 -> when (buttonID) {
                1 -> playerl("Hey, Skippy!").also { stage++ }
                2 -> end()
            }
            3 -> npcl("What?").also { removeItem(player!!, Items.BUCKET_OF_WATER_1929)
                playAudio(player!!, Sounds.SKIPPY_BUCKET_1399)
                animate(player!!, SkippyUtils.ANIMATION_THROW_BUCKET)
                addItem(player!!, Items.BUCKET_1925)
                stage++
            }
            4 -> npcl("Ahhhhhhhhhhgh! That's cold! Are you trying to kill me?").also { stage++ }
            5 -> playerl("Nope. Just sober you up. Memory coming back yet?").also { stage++ }
            6 -> npcl("No. But I could do with a bowl of tea to warm myself up a bit. Go grab me one and we'll talk.").also { stage++ }
            7 -> playerl("Any particular type of tea?").also { stage++ }
            8 -> npcl("Nettle for preference. Just make sure it's hot.").also { stage++ }
            9 -> npcl("And don't throw it at me!").also { stage++ }
            10 -> playerl("What's your problem? You're all clean now.").also { stage++ }
            11 -> {
                end()
                setVarbit(player!!, SkippyUtils.VARBIT_SKIPPY_AND_THE_MOGRES_PROGRESS, 1, true)
            }
        }
    }
}

class SkippyDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.SKIPPY_2796)
        when(stage) {
            0 -> playerl("Hey, Skippy.").also { stage++ }
            1 -> npcl("Gaah! Don't drench me again!").also { stage++ }
            2 -> player("Hey! I only did that once! Try not to be such a big","baby!").also { stage++ }
            3 -> npcl("So what are you here for then?").also { stage++ }
            4 -> {
                if(!removeItem(player!!, Items.CUP_OF_TEA_4242)){
                    playerl("No real reason. I just thought I would check up on you is all.").also { stage = 100 }
                } else {
                    playerl("I've come to give you your tea.").also {
                        addItem(player!!, Items.EMPTY_CUP_1980)
                        stage++
                    }
                }
            }
            5 -> npcl("Excellent! I was thinking I was going to freeze out here!").also { stage++ }
            6 -> sendDialogue(player!!, "Skippy drinks the tea and clutches his forehead in pain.").also { stage++ }
            7 -> npc("Ohhhhh...").also { stage++ }
            8 -> player("What? What's wrong?").also { stage++ }
            9 -> npcl("Not so loud...I think I have a hangover...").also { stage++ }
            10 -> player("Great...Well, I doubt you can remember anything","through a hangover. What a waste of nettle tea...").also { stage++ }
            11 -> npc("Hey! A little sympathy here?").also { stage++ }
            12 -> npc("Owwowwoww... must remember not to shout...").also { stage++ }
            13 -> npc("Look, I do know a hangover cure. If you can get me a","bucket of the stuff I think I'll be ok.").also { stage++ }
            14 -> player("Wait... is this cure a bucket of chocolate milk and snape","grass?").also { stage++ }
            15 -> npc("Yes!, That's the stuff!").also { stage++ }
            16 -> player("Ahhh. Yes, I've made some of that stuff before. I should","be able to get you some, no problem.").also { stage++ }
            17 -> {
                end()
                setVarbit(player!!, SkippyUtils.VARBIT_SKIPPY_AND_THE_MOGRES_PROGRESS, 2, true)
            }
            100 -> npc("Well, I'm still wet, still cold and still","waiting on that nettle tea.").also { stage = END_DIALOGUE }

        }
    }
}

class SkippyHangoverCureDialogue : DialogueFile() {
    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.SKIPPY_2796)
        when (stage) {
            0 -> playerl("Hey, Skippy!").also { stage++ }
            1 -> npcl("Egad! Don't you know not to shout around a guy with a hangover?").also { stage++ }
            2 -> npcl("Ahhhhhg...No more shouting for me...").also { stage++ }
            3 -> npcl("What is it anyway?").also { stage++ }
            4 -> {
                if (!removeItem(player!!, Items.HANGOVER_CURE_1504)) {
                    playerl("I just came back to ask you something.").also { stage++ }
                } else {
                    playerl(FacialExpression.HAPPY,"Well Skippy, you will no doubt be glad to hear that I got you your hangover cure!").also {
                        addItem(player!!, Items.BUCKET_1925)
                        stage = 17
                    }
                }
            }
            5 -> npc("What?").also { stage++ }
            6 -> options("How do I make that hangover cure again?", "Why do they call you 'Skippy'?").also { stage++ }
            7 -> when (buttonID) {
                1 -> playerl("How do I make that hangover cure again?").also { stage++ }
                2 -> player("Why do they call you 'Skippy'?").also { stage = 15 }
            }
            8 -> npcl("Give me strength...Here's what you do. Pay attention!").also { stage++ }
            9 -> npcl("You take a bucket of milk, a bar of chocolate and some snape grass.").also { stage++ }
            10 -> npcl("Grind the chocolate with a pestle and mortar.").also { stage++ }
            11 -> npcl("Add the chocolate powder to the milk, then add the snape grass.").also { stage++ }
            12 -> npcl("Then bring it here and I will drink it.").also { stage++ }
            13 -> npcl("Are you likely to remember that or should I go get some crayons and draw you a picture?").also { stage++ }
            14 -> playerl("Hey! I remember it now, ok! See you in a bit.").also { stage = END_DIALOGUE }

            15 -> npcl("I think it may have something to do with my near-constant raving about mudskippers.").also { stage++ }
            16 -> npcl("That or it's something to do with that time with the dress and the field full of daisies...").also { stage = END_DIALOGUE }

            17 -> npc("Gimme!").also { stage++ }
            18 -> playerl(FacialExpression.HAPPY, "Well Skippy, you will no doubt be glad to hear that I got you your hangover cure!").also { stage++ }
            19 -> sendDialogue(player!!, "Skippy chugs the hangover cure... very impressive.").also { stage++ }
            20 -> npc("Ahhhhhhhhhhhhhhh...").also { stage++ }
            21 -> npc("Much better...").also { stage++ }
            22 -> player("Feeling better?").also { stage++ }
            23 -> npc("Considerably.").also { stage++ }
            24 -> playerl("Then tell me where the mudskippers are!").also { stage++ }
            25 -> npc("Much better...").also { stage++ }
            26 -> npcl("I wish you wouldn't go looking for them. Those vicious killers will tear you apart.").also { stage++ }
            27 -> npcl("It's all becoming clear to me now...").also { stage++ }
            28 -> npcl("I was fishing using a Fishing Explosive...").also { stage++ }
            29 -> playerl("A Fishing Explosive?").also { stage++ }
            30 -> npcl("Well, Slayer Masters sell these highly volatile potions for killing underwater creatures.").also { stage++ }
            31 -> npcl("If you don't feel like lobbing a net about all day you can use them to fish with...").also { stage++ }
            32 -> npcl("But this time I was startled by what I thought was a giant mudskipper.").also { stage++ }
            33 -> npcl("What it was, infact, was a...").also { stage++ }
            34 -> npcl("Dramatic Pause...").also { stage++ }
            35 -> npcl("A Mogre!").also { stage++ }
            36 -> playerl("What exactly is a Mogre?").also { stage++ }
            37 -> npcl("A Mogre is a type of Ogre that spends most of its time underwater.").also { stage++ }
            38 -> npcl("They hunt giant mudskippers by wearing their skins and swimming close until they can attack them.").also { stage++ }
            39 -> npcl("When I used the Fishing Explosive I scared off all the fish, and so the Mogre got out of the water to express its extreme displeasure.").also { stage++ }
            40 -> npcl("My head still hurts.").also { stage++ }
            41 -> playerl("I take it the head injury is responsible for the staggering and yelling?").also { stage++ }
            42 -> npcl("No, no.").also { stage++ }
            43 -> npcl("My addiction to almost-lethal alcohol did that.").also { stage++ }
            44 -> npcl("But if you are set on finding those Mogres just head south from here until you find Mudskipper Point.").also { stage++ }
            45 -> playerl("The mudskipper-eating monsters are to be found at Mudskipper point?").also { stage++ }
            46 -> playerl("Shock!").also { stage++ }
            47 -> playerl("Thanks. I'll be careful.").also { stage++ }
            48 -> {
                end()
                setVarbit(player!!, SkippyUtils.VARBIT_SKIPPY_AND_THE_MOGRES_PROGRESS, 3, true)
            }

        }
    }
}

class SkippyAfterDialogue : DialogueFile() {
    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.SKIPPY_2796)
        when (stage) {
            0 -> playerl("Hey, Skippy.").also { stage++ }
            1 -> npcl("Hey you!").also { stage++ }
            2 -> playerl("How do I annoy the Mogres again?").also { stage++ }
            3 -> npcl("Go south to Mudskipper Point and lob a Fishing Explosive into the sea. You can grab them from the Slayer masters.").also { stage++ }
            4 -> playerl("Thanks! So, what are you going to do now?").also { stage++ }
            5 -> npcl("Well, I was planning on continuing my hobby of wandering up and down this coastline, bellowing random threats and throwing bottles. And you?").also { stage++ }
            6 -> playerl("I was planning on wandering up and down the landscape, bugging people to see if they had mindblowingly dangerous quests for me to undertake!").also { stage++ }
            7 -> npcl("Well, good luck with that!").also { stage++ }
            8 -> playerl("You too!").also { stage++ }
            9 -> npcl("Weirdo...").also { stage++ }
            10 -> playerl("Loony...").also { stage = END_DIALOGUE }
        }
    }
}
