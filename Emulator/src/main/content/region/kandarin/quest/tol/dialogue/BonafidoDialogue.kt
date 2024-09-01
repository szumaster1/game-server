package content.region.kandarin.quest.tol.dialogue

import content.region.kandarin.quest.tol.handlers.TolUtils
import core.api.*
import cfg.consts.Items
import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.game.world.GameWorld
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Represents the Bonafido dialogue.
 */
@Initializable
class BonafidoDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(componentID: Int, buttonID: Int): Boolean {
        when (getQuestStage(player!!, "Tower of Life")) {
            0 -> when (stage) {
                START_DIALOGUE -> playerl(FacialExpression.FRIENDLY, "Hi there.").also { stage++ }
                1 -> npcl(FacialExpression.FRIENDLY, "A'up, babe, how's it goin'?").also { stage++ }
                2 -> playerl(
                    FacialExpression.FRIENDLY,
                    "Not bad, thank you. What are you building here?"
                ).also { stage++ }

                3 -> npcl(FacialExpression.FRIENDLY, "Nowt at moment. We're on strike!").also { stage++ }
                4 -> playerl(FacialExpression.FRIENDLY, "You're on strike? Whatever for?").also { stage++ }
                5 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Those strange alchemists are up ta somefin', best go ta one ov 'em."
                ).also { stage++ }

                6 -> playerl(FacialExpression.FRIENDLY, "Oh, okay.").also { stage = END_DIALOGUE }
            }

            1 -> when (stage) {
                START_DIALOGUE -> playerl(FacialExpression.FRIENDLY, "Are you Bonafido by any chance?").also { stage++ }
                1 -> npcl(FacialExpression.FRIENDLY, "That I am, babe.").also { stage++ }
                2 -> npcl(FacialExpression.FRIENDLY, "How can I be of assistance?").also { stage++ }
                3 -> playerl(
                    FacialExpression.FRIENDLY,
                    "Well, I've just spoken with Effigy about this tower. He tells me he hired you to work but you're on strike. Something about an extended tea break?"
                ).also { stage++ }

                4 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Extended cup o' char? E's got it all wrong, babe. We know those alchemi-whatsits are up ta somefin' weird in building this tower, wot wiv all the machinery, and until we get some answers we're not moving a muscle."
                ).also { stage++ }

                5 -> playerl(FacialExpression.FRIENDLY, "Well, he wanted me to do something about it.").also { stage++ }
                6 -> playerl(
                    FacialExpression.FRIENDLY,
                    "Is there no way I can convince you to continue?"
                ).also { stage++ }

                7 -> npcl(FacialExpression.FRIENDLY, "Sorry, babe.").also { stage++ }
                8 -> playerl(
                    FacialExpression.FRIENDLY,
                    "Well, can I at least have a look inside the tower?"
                ).also { stage++ }

                9 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Only builders are allowed in there, like. You don't look much ov a builder to me."
                ).also { stage++ }

                10 -> playerl(
                    FacialExpression.FRIENDLY,
                    "I take offence at that! Give me a chance; you may be surprised"
                ).also { stage++ }

                11 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Okay. Come on me kitted out likea  builder - hard hat, some boots, scruffy trousers and shirt - and I'll let ya try out ta be one ov us."
                ).also { stage++ }

                12 -> playerl(FacialExpression.FRIENDLY, "Count me in!").also { stage++ }
                13 -> {
                    end()
                    setQuestStage(player, "Tower of Life", 2)
                    setAttribute(player, TolUtils.TOL_CONSTRUCTION_ATTRIBUTE, 0)
                }
            }

            3 -> if (hasAnItem(
                    player,
                    Items.BUILDERS_SHIRT_10863,
                    Items.BUILDERS_TROUSERS_10864,
                    Items.BUILDERS_BOOTS_10865
                ).container != null
            ) when (stage) {
                START_DIALOGUE -> playerl(FacialExpression.FRIENDLY, "Hey, Bonafido.").also {
                    sendMessage(player, "You whistle for attention.")
                }.also { stage++ }

                1 -> npcl(FacialExpression.FRIENDLY, "Well, if it ain't ol' skinny bones.").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "So...").also { stage++ }
                3 -> playerl(FacialExpression.FRIENDLY, "How do I look?").also { stage++ }
                4 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Like a builder frew and frew... Good job, babe! Nah, it's time to test yur skills."
                ).also { stage++ }

                5 -> playerl(FacialExpression.FRIENDLY, "Okay, what do you want me to build?").also { stage++ }
                6 -> npcl(FacialExpression.FRIENDLY, "Build?").also { stage++ }
                7 -> npcl(FacialExpression.FRIENDLY, "Hahaha, don't be a plonka!").also { stage++ }
                8 -> npcl(
                    FacialExpression.FRIENDLY,
                    "It's not building skills that makes a true builder, you have to have the right mental attitude, yeah?"
                ).also { stage++ }

                9 -> npcl(FacialExpression.FRIENDLY, "Let me see...").also { stage++ }
                10 -> npcl(
                    FacialExpression.FRIENDLY,
                    "You've plenty of work to do, but you need a drink fast - what do you go for?"
                ).also { stage++ }

                11 -> options("Orange juice", "Tea", "Bottle of wine").also { stage++ }
                12 -> when (buttonID) {
                    1 -> playerl(FacialExpression.FRIENDLY, "Orange juice").also { stage = 13 }
                    2 -> playerl(FacialExpression.FRIENDLY, "Tea").also { stage = 15 }
                    3 -> playerl(FacialExpression.FRIENDLY, "Bottle of wine").also { stage = 14 }
                }

                13 -> npcl(
                    FacialExpression.FRIENDLY,
                    "What are you? A healthy builder? Of course, not juice!"
                ).also { stage = END_DIALOGUE }

                14 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Ooh, got '" + (if (player.isMale) "MISTER" else "MISS") + " Fancy Pants' here and " + (if (player.isMale) "HIS" else "HER") + " posh wine. You're no builder."
                ).also { stage = END_DIALOGUE }

                15 -> npcl(FacialExpression.FRIENDLY, "Bingo! Ain't nufin' betta.").also { stage++ }
                16 -> npcl(FacialExpression.FRIENDLY, "Now, let's hear you whistle!").also { stage++ }
                17 -> options(
                    "Do a little dance and whistle as loud as you can",
                    "Whistle a pretty tune",
                    "Whiste for attention"
                ).also { stage++ }

                18 -> when (buttonID) {
                    1 -> playerl(
                        FacialExpression.FRIENDLY,
                        "Do a little dance and whistle as loud as you can"
                    ).also { stage = 19 }

                    2 -> playerl(FacialExpression.FRIENDLY, "Whistle a pretty tune").also { stage = 20 }
                    3 -> playerl(FacialExpression.FRIENDLY, "Whiste for attention").also { stage = 24 }
                }

                19 -> npcl(
                    FacialExpression.FRIENDLY,
                    "What on " + GameWorld.settings!!.name + " are you doing? Get out of my sight."
                ).also { stage = END_DIALOGUE }

                20 -> npcl(FacialExpression.FRIENDLY, "You know, you have quite a talent there.").also { stage++ }
                21 -> playerl(
                    FacialExpression.FRIENDLY,
                    "Why thank you. Do you think I have what it takes to become a famous whistler?"
                ).also { stage++ }

                22 -> npcl(FacialExpression.FRIENDLY, "No.").also { stage++ }
                23 -> playerl(FacialExpression.FRIENDLY, "Oh.").also { stage = END_DIALOGUE }
                24 -> npcl(FacialExpression.FRIENDLY, "Wahey! Nice on. Next question.").also { stage++ }
                25 -> npcl(
                    FacialExpression.FRIENDLY,
                    "What's a good sign that you need to replace your trousers?"
                ).also { stage++ }

                26 -> options(
                    "Your legs are getting a bit cold",
                    "They're ripped and full of holes",
                    "The colour is starting to fade"
                ).also { stage++ }

                27 -> when (buttonID) {
                    1 -> playerl(FacialExpression.FRIENDLY, "Your legs are getting a bit cold").also { stage = 30 }
                    2 -> playerl(FacialExpression.FRIENDLY, "They're ripped and full of holes").also { stage = 28 }
                    3 -> playerl(FacialExpression.FRIENDLY, "The colour is starting to fade").also { stage = 29 }
                }

                28 -> npcl(FacialExpression.FRIENDLY, "No, no, no. That's the only way they should be!").also {
                    stage = END_DIALOGUE
                }

                29 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Oh no, mate, you're really not getting the hang of this are you?"
                ).also { stage = END_DIALOGUE }

                30 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Exactamondo! Glad to see you are thinking like a builder."
                ).also { stage++ }

                31 -> playerl(FacialExpression.FRIENDLY, "I am?").also { stage++ }
                32 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Yes, obviously if your legs are cold, you lost your trousers altogether!"
                ).also { stage++ }

                33 -> playerl(FacialExpression.FRIENDLY, "That's just what I was thinking.").also { stage++ }
                34 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Now the last question - what do you do if you cut your finger?"
                ).also { stage++ }

                35 -> options("Cry", "Carry on, it'll fix itself", "Fetch a plaster and ointment").also { stage++ }
                36 -> when (buttonID) {
                    1 -> playerl(FacialExpression.FRIENDLY, "Cry").also { stage = 37 }
                    2 -> playerl(FacialExpression.FRIENDLY, "Carry on, it'll fix itself").also { stage = 39 }
                    3 -> playerl(FacialExpression.FRIENDLY, "Fetch a plaster and ointment").also { stage = 38 }
                }

                37 -> npcl(FacialExpression.FRIENDLY, "When was the last time you saw a builder cry?").also {
                    stage = END_DIALOGUE
                }

                38 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Look, let me give you a bit of advice. If you want to look weak, that's the best way to go."
                ).also { stage = END_DIALOGUE }

                39 -> npcl(FacialExpression.FRIENDLY, "Yep, that's the one!").also { stage++ }
                40 -> playerl(
                    FacialExpression.FRIENDLY,
                    "Suppose it could get infected then, but that'll just impress the lads all the more."
                ).also { stage++ }

                41 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Exactly, you're gettin the hang of this quickly!"
                ).also { stage++ }

                42 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Cosmic! I canna see no reason why ya can't go in t'tower now. But be careful, there be some precarious situations in there."
                ).also { stage++ }

                43 -> playerl(FacialExpression.FRIENDLY, "So, what's left to build?").also { stage++ }
                44 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Well, there's a pipe system, a pressure machine, and a strange cage up at the top that needs finishing off. The alchemists couldn't explain what they were for."
                ).also { stage++ }

                45 -> playerl(FacialExpression.FRIENDLY, "Thanks!").also { stage = END_DIALOGUE }
            } else {
                if (getAttribute(player, TolUtils.TOL_PLAYER_ENTER_TOWER_ATTRIBUTE, 0) == 1) when (stage) {
                    0 -> playerl(FacialExpression.FRIENDLY, "Hi again Bonafido.").also { stage++ }
                    1 -> npcl(
                        FacialExpression.FRIENDLY,
                        "Hi, ${player.username} the Builder'! Been into the tower yet?"
                    ).also { stage++ }

                    2 -> playerl(
                        FacialExpression.FRIENDLY,
                        "Not yet. I'll go when I'm ready. I'm taking my time, may have a quick cup of tea first."
                    ).also { stage++ }

                    3 -> npcl(FacialExpression.FRIENDLY, "Hahaha.").also { stage = END_DIALOGUE }
                } else {
                    when (stage) {
                        0 -> playerl(
                            FacialExpression.FRIENDLY,
                            "Heya, Bona. I had a look at the building. There's some complex machinery in there! Going to cost someone."
                        ).also { stage++ }

                        1 -> npcl(
                            FacialExpression.FRIENDLY,
                            "Yep, told ya so. Any idea what those alchemists are up ta?"
                        ).also { stage++ }

                        2 -> playerl(
                            FacialExpression.FRIENDLY,
                            "Well, I agree now they're up to something. The question is what...? I don't think we're going to find out much from the alchemists, though."
                        ).also { stage++ }

                        3 -> npcl(FacialExpression.FRIENDLY, "So what are you going to do?").also { stage++ }
                        4 -> playerl(
                            FacialExpression.FRIENDLY,
                            "I shall see if I can't fix it up and see for myself what is going on. Should be able to get a good reward from them. But don't worry, I'll make sure your efforts don't go unpaid."
                        ).also { stage++ }

                        5 -> npcl(FacialExpression.FRIENDLY, "You're a star among builders, darling!").also {
                            stage = END_DIALOGUE
                        }
                    }
                }
            }

            // Speaking to Bonafido after the alchemists run to the top of the tower
            5 -> when (stage) {
                START_DIALOGUE -> playerl(
                    FacialExpression.FRIENDLY,
                    "I'm really getting annoyed with those alchemists."
                ).also { stage++ }

                1 -> npcl(FacialExpression.FRIENDLY, "Why?").also { stage++ }
                2 -> playerl(
                    FacialExpression.FRIENDLY,
                    "Well, I completed the tower and I still have no idea what it does, and then as soon as I tell Effigy of my hard work, he doesn't say one word of thanks and legs it off into the tower!"
                ).also { stage++ }

                3 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Well, I'll be a rabbit in da eyes of a fox. Best ya chase after 'im then and get us our dosh! Don't you do a runner, now!"
                ).also { stage++ }

                4 -> playerl(
                    FacialExpression.FRIENDLY,
                    "Don't worry, I'm not about to let Effigy get away with treating us builders like this!"
                ).also { stage = END_DIALOGUE }
            }

            6 -> {
                if (getAttribute(player, TolUtils.TOL_START_CUTSCENE_ATTRIBUTE, false)) {
                    when (stage) {
                        START_DIALOGUE -> playerl(
                            FacialExpression.FRIENDLY,
                            "You won't BELIEVE what I just saw!"
                        ).also { stage++ }

                        1 -> npcl(FacialExpression.FRIENDLY, "What? What?").also { stage++ }
                        2 -> playerl(
                            FacialExpression.FRIENDLY,
                            "That tower is...a tower of life! I just witnessed with my very own eyes the creation of a homunculus."
                        ).also { stage++ }

                        3 -> npcl(FacialExpression.FRIENDLY, "A homanque-what?").also { stage++ }
                        4 -> playerl(
                            FacialExpression.FRIENDLY,
                            "Homunculus! They have found a way of creating life itself, but the creature they have made is not a happy being."
                        ).also { stage++ }

                        5 -> npcl(FacialExpression.FRIENDLY, "And our money?").also { stage++ }
                        6 -> playerl(
                            FacialExpression.FRIENDLY,
                            "Are you listening at all? This creature is very dangerous. It needs to be stopped. And fast!"
                        ).also { stage++ }

                        7 -> npcl(FacialExpression.FRIENDLY, "I'm glad you've got the money sorted.").also {
                            stage = END_DIALOGUE
                        }
                    }
                }
            }

            8 -> when (stage) {
                START_DIALOGUE -> playerl(FacialExpression.FRIENDLY, "Hi, Bonafido.").also { stage++ }
                1 -> npcl(FacialExpression.FRIENDLY, "Hi. How's the tower?").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "Finished, really.").also { stage++ }
                3 -> npcl(FacialExpression.FRIENDLY, "Looks like we're out of a job then.").also { stage++ }
                4 -> playerl(
                    FacialExpression.FRIENDLY,
                    "Oh, I wouldn't worry about it. There are frequently new buildings required around " + GameWorld.settings!!.name + ". I'm sure your services will be called upon again."
                ).also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BONAFIDO_5580)
    }
}
