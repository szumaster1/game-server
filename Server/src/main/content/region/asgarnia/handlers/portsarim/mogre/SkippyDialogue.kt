package content.region.asgarnia.handlers.portsarim.mogre

import core.api.*
import core.api.consts.Items
import core.api.consts.Sounds
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.interaction.QueueStrength
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class SkippyDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        when(getVarbit(player, SkippyUtils.VARBIT_SKIPPY_AND_THE_MOGRES_PROGRESS)){
            0 -> if(!inInventory(player, Items.BUCKET_OF_WATER_1929)) {
                playerl(FacialExpression.HALF_GUILTY, "You know, I could shock him out of it if I could find some cold water...").also { stage = END_DIALOGUE }
            } else {
                playerl(FacialExpression.HALF_GUILTY,"Well, I could dump this bucket of water over him. That would sober him up a little.").also { stage = 0 }
            }
            1 -> player("Hey, Skippy.").also { stage = 12 }
            2 -> player("Hey, Skippy.").also { stage = 31 }
        }

        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Throw the water!", "I think I'll leave it for now").also { stage++ }
            1 -> when (buttonId) {
                1 -> player("Hey, Skippy!").also { stage++ }
                2 -> end()
            }

            2 -> npc("What?").also { stage++ }
            3 -> queueScript(player, 1, QueueStrength.WEAK) { tick: Int ->
                when (tick) {
                    0 -> {
                        removeItem(player!!, Items.BUCKET_OF_WATER_1929)
                        animate(player!!, SkippyUtils.ANIMATION_THROW_BUCKET)
                        playAudio(player!!, Sounds.SKIPPY_BUCKET_1399, 2)
                        addItem(player!!, Items.BUCKET_1925)
                        return@queueScript delayScript(player, SkippyUtils.ANIMATION_THROW_BUCKET.delay)
                    }

                    1 -> {
                        npc("Ahhhhhhhhhhgh! That's cold! Are you trying to kill me?")
                        return@queueScript stopExecuting(player).also { stage = 5 }
                    }

                    else -> return@queueScript stopExecuting(player)
                }
            }

            5 -> playerl(FacialExpression.HALF_ASKING, "Nope. Just sober you up. Memory coming back yet?").also { stage++ }
            6 -> npcl(FacialExpression.NEUTRAL, "No. But I could do with a bowl of tea to warm myself up a bit. Go grab me one and we'll talk.").also { stage++ }
            7 -> playerl(FacialExpression.HAPPY, "Any particular type of tea?").also { stage++ }
            8 -> npcl(FacialExpression.NEUTRAL, "Nettle for preference. Just make sure it's hot.").also { stage++ }
            9 -> npcl(FacialExpression.NEUTRAL, "And don't throw it at me!").also { stage++ }
            10 -> player(FacialExpression.FRIENDLY, "What's your problem? You're all clean now.").also { stage++ }
            11 -> {
                end()
                setVarbit(player!!, SkippyUtils.VARBIT_SKIPPY_AND_THE_MOGRES_PROGRESS, 1, true)
            }

            12 -> npc("Gaah! Don't drench me again!").also { stage++ }
            13 -> player("Hey! I only did that once! Try not to be such a big", "baby!").also { stage++ }
            14 -> npc("So what are you here for then?").also { stage++ }
            15 -> {
                if (!anyInInventory(player, NETTLE_TEA_BOWL, NETTLE_TEA_CUP, NETTLE_TEA_PORCELAIN_CUP)) {
                    playerl(FacialExpression.NEUTRAL, "No real reason. I just thought I would check up on you is all.").also { stage = 29 }
                } else {
                    if((inInventory(player, NETTLE_TEA_BOWL) && removeItem(player, NETTLE_TEA_BOWL) && addItem(player, EMPTY_BOWL)) || (inInventory(player, NETTLE_TEA_CUP) && removeItem(player, NETTLE_TEA_CUP) && addItem(player, EMPTY_CUP)) || (inInventory(player, NETTLE_TEA_PORCELAIN_CUP) && removeItem(player, NETTLE_TEA_PORCELAIN_CUP) && addItem(player, EMPTY_PORCELAIN_CUP))) {
                        playerl(FacialExpression.HAPPY, "I've come to give you your tea.").also { stage++ }
                    } else {
                        playerl(FacialExpression.NEUTRAL, "No real reason. I just thought I would check up on you is all.").also { stage = 29 }
                    }
                }
            }
            16 -> npcl(FacialExpression.HALF_GUILTY,"Excellent! I was thinking I was going to freeze out here!").also { stage++ }
            17 -> sendDialogue(player!!, "Skippy drinks the tea and clutches his forehead in pain.").also { stage++ }
            18 -> npc("Ohhhhh...").also { stage++ }
            19 -> player("What? What's wrong?").also { stage++ }
            20 -> npcl(FacialExpression.NEUTRAL, "Not so loud...I think I have a hangover...").also { stage++ }
            21 -> player("Great...Well, I doubt you can remember anything", "through a hangover. What a waste of nettle tea...").also { stage++ }

            22 -> npc("Hey! A little sympathy here?").also { stage++ }
            23 -> npc("Owwowwoww... must remember not to shout...").also { stage++ }
            24 -> npc("Look, I do know a hangover cure. If you can get me a", "bucket of the stuff I think I'll be ok.").also { stage++ }

            25 -> player("Wait... is this cure a bucket of chocolate milk and snape", "grass?").also { stage++ }
            26 -> npc("Yes!, That's the stuff!").also { stage++ }
            27 -> player("Ahhh. Yes, I've made some of that stuff before. I should", "be able to get you some, no problem.").also { stage++ }

            28 -> {
                end()
                setVarbit(player!!, SkippyUtils.VARBIT_SKIPPY_AND_THE_MOGRES_PROGRESS, 2, true)
            }

            29 -> npc("Well, I'm still wet, still cold and still", "waiting on that nettle tea.").also { stage = END_DIALOGUE }

            31 -> npcl(FacialExpression.HALF_GUILTY,"Egad! Don't you know not to shout around a guy with a hangover?").also { stage++ }
            32 -> npcl(FacialExpression.HALF_GUILTY,"Ahhhhhg...No more shouting for me...").also { stage++ }
            33 -> npcl(FacialExpression.HALF_GUILTY,"What is it anyway?").also { stage++ }
            34 -> {
                if (!removeItem(player!!, Items.HANGOVER_CURE_1504)) {
                    playerl(FacialExpression.HALF_GUILTY,"I just came back to ask you something.").also { stage++ }
                } else {
                    playerl(FacialExpression.HAPPY,"Well Skippy, you will no doubt be glad to hear that I got you your hangover cure!").also {
                        addItem(player!!, Items.BUCKET_1925)
                        stage = 47
                    }
                }
            }
            35 -> npc("What?").also { stage++ }
            36 -> options("How do I make that hangover cure again?", "Why do they call you 'Skippy'?").also { stage++ }
            37 -> when (buttonId) {
                1 -> player("How do I make that hangover cure again?").also { stage++ }
                2 -> player("Why do they call you 'Skippy'?").also { stage = 15 }
            }
            38 -> npcl(FacialExpression.HALF_GUILTY,"Give me strength...Here's what you do. Pay attention!").also { stage++ }
            39 -> npcl(FacialExpression.HALF_GUILTY,"You take a bucket of milk, a bar of chocolate and some snape grass.").also { stage++ }
            40 -> npcl(FacialExpression.HALF_GUILTY,"Grind the chocolate with a pestle and mortar.").also { stage++ }
            41 -> npcl(FacialExpression.HALF_GUILTY,"Add the chocolate powder to the milk, then add the snape grass.").also { stage++ }
            42 -> npcl(FacialExpression.HALF_GUILTY,"Then bring it here and I will drink it.").also { stage++ }
            43 -> npcl(FacialExpression.HALF_GUILTY,"Are you likely to remember that or should I go get some crayons and draw you a picture?").also { stage++ }
            44 -> playerl(FacialExpression.HALF_GUILTY,"Hey! I remember it now, ok! See you in a bit.").also { stage = END_DIALOGUE }

            45 -> npcl(FacialExpression.HALF_GUILTY,"I think it may have something to do with my near-constant raving about mudskippers.").also { stage++ }
            46 -> npcl(FacialExpression.HALF_GUILTY,"That or it's something to do with that time with the dress and the field full of daisies...").also { stage = END_DIALOGUE }

            47 -> npc("Gimme!").also { stage++ }
            48 -> playerl(FacialExpression.HAPPY, "Well Skippy, you will no doubt be glad to hear that I got you your hangover cure!").also { stage++ }
            49 -> sendDialogue(player!!, "Skippy chugs the hangover cure... very impressive.").also { stage++ }
            50 -> npc("Ahhhhhhhhhhhhhhh...").also { stage++ }
            51 -> npc("Much better...").also { stage++ }
            52 -> player("Feeling better?").also { stage++ }
            53 -> npc("Considerably.").also { stage++ }
            54 -> player("Then tell me where the mudskippers are!").also { stage++ }
            55 -> npc("Much better...").also { stage++ }
            56 -> npcl(FacialExpression.HALF_GUILTY, "I wish you wouldn't go looking for them. Those vicious killers will tear you apart.").also { stage++ }
            57 -> npc("It's all becoming clear to me now...").also { stage++ }
            58 -> npc("I was fishing using a Fishing Explosive...").also { stage++ }
            59 -> player("A Fishing Explosive?").also { stage++ }
            60 -> npcl(FacialExpression.HALF_GUILTY,"Well, Slayer Masters sell these highly volatile potions for killing underwater creatures.").also { stage++ }
            61 -> npcl(FacialExpression.HALF_GUILTY,"If you don't feel like lobbing a net about all day you can use them to fish with...").also { stage++ }
            62 -> npcl(FacialExpression.HALF_GUILTY,"But this time I was startled by what I thought was a giant mudskipper.").also { stage++ }
            63 -> npcl(FacialExpression.HALF_GUILTY,"What it was, infact, was a...").also { stage++ }
            64 -> npc("Dramatic Pause...").also { stage++ }
            65 -> npc("A Mogre!").also { stage++ }
            66 -> player("What exactly is a Mogre?").also { stage++ }
            67 -> npcl(FacialExpression.HALF_GUILTY,"A Mogre is a type of Ogre that spends most of its time underwater.").also { stage++ }
            68 -> npcl(FacialExpression.HALF_GUILTY,"They hunt giant mudskippers by wearing their skins and swimming close until they can attack them.").also { stage++ }
            69 -> npcl(FacialExpression.HALF_GUILTY,"When I used the Fishing Explosive I scared off all the fish, and so the Mogre got out of the water to express its extreme displeasure.").also { stage++ }
            70 -> npc("My head still hurts.").also { stage++ }
            71 -> playerl(FacialExpression.HALF_ASKING,"I take it the head injury is responsible for the staggering and yelling?").also { stage++ }
            72 -> npc("No, no.").also { stage++ }
            73 -> npcl(FacialExpression.HALF_GUILTY,"My addiction to almost-lethal alcohol did that.").also { stage++ }
            74 -> npcl(FacialExpression.HALF_GUILTY,"But if you are set on finding those Mogres just head south from here until you find Mudskipper Point.").also { stage++ }
            75 -> playerl(FacialExpression.HALF_ASKING,"The mudskipper-eating monsters are to be found at Mudskipper point?").also { stage++ }
            76 -> player("Shock!").also { stage++ }
            77 -> player("Thanks. I'll be careful.").also { stage++ }
            78 -> {
                end()
                setVarbit(player!!, SkippyUtils.VARBIT_SKIPPY_AND_THE_MOGRES_PROGRESS, 3, true)
            }

        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(2795,2796,2797,2798,2799)
    }

    companion object {
        val NETTLE_TEA_BOWL = Items.NETTLE_TEA_4239
        val NETTLE_TEA_CUP = Items.CUP_OF_TEA_4242
        val NETTLE_TEA_PORCELAIN_CUP = Items.CUP_OF_TEA_4245

        val EMPTY_BOWL = Items.BOWL_1923
        val EMPTY_CUP = Items.EMPTY_CUP_1980
        val EMPTY_PORCELAIN_CUP = Items.PORCELAIN_CUP_4244

    }

}
