package content.region.kandarin.quest.toweroflife.dialogue

import content.region.kandarin.quest.toweroflife.util.TolUtils
import core.api.consts.NPCs
import core.api.getAttribute
import core.api.getQuestStage
import core.api.setQuestStage
import core.api.setVarbit
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE
import core.utilities.START_DIALOGUE

@Initializable
class EffigyDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(componentID: Int, buttonID: Int): Boolean {
        when (getQuestStage(player!!, "Tower of Life")) {
            0 -> when (stage) {
                START_DIALOGUE -> playerl(
                    FacialExpression.FRIENDLY,
                    "Hi. Interesting building you have here."
                ).also { stage++ }

                1 -> playerl(FacialExpression.FRIENDLY, "What's it for?").also { stage++ }
                2 -> npcl(
                    FacialExpression.FRIENDLY,
                    "You like it? It's simply fabulous, isn't it? A real marvel of modern design made just for my fellow alchemists and I!"
                ).also { stage++ }

                3 -> playerl(
                    FacialExpression.FRIENDLY,
                    "I see some builders over there. So I assume it's not yet finished."
                ).also { stage++ }

                4 -> npcl(
                    FacialExpression.FRIENDLY,
                    "What a keen eye this " + (if (player.isMale) "LAD" else "LASS") + " has! But imagine this: a gargantuan cylinder of expert design, incorporating an inventive inner spiral walkway to multiple planes of wondrous inhabitance!"
                ).also { stage++ }

                5 -> playerl(FacialExpression.FRIENDLY, "So, it's a tower.").also { stage++ }
                6 -> npcl(FacialExpression.FRIENDLY, "Oh, no, no, no, no.").also { stage++ }
                7 -> npcl(
                    FacialExpression.FRIENDLY,
                    "It's going to have a use that will change this world!"
                ).also { stage++ }

                8 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Or at least it would if we could ever get it finished."
                ).also { stage++ }

                9 -> npcl(FacialExpression.FRIENDLY, "See those builders?").also { stage++ }
                10 -> playerl(FacialExpression.FRIENDLY, "They don't seem to be building much.").also { stage++ }
                11 -> npcl(FacialExpression.FRIENDLY, "Exactly").also { stage++ }
                12 -> npcl(
                    FacialExpression.FRIENDLY,
                    "They've gone on strike for some petty reason about the tower being too weird. I really can't understand those men."
                ).also { stage++ }

                13 -> playerl(FacialExpression.FRIENDLY, "Shame.").also { stage++ }
                14 -> npcl(FacialExpression.FRIENDLY, "Hey! Maybe you could talk to them?").also { stage++ }
                15 -> npcl(
                    FacialExpression.FRIENDLY,
                    "You look like a sturdy " + (if (player.isMale) "LAD" else "LASS") + "."
                ).also { stage++ }

                16 -> npcl(FacialExpression.FRIENDLY, "Maybe you could finish the work?").also { stage++ }
                17 -> playerl(FacialExpression.FRIENDLY, "Me?").also { stage++ }
                18 -> npcl(FacialExpression.FRIENDLY, "Come on! This is the chance of a lifetime!").also { stage++ }
                19 -> options("Sure, why not.", "Sorry, feeling a bit ill today.").also { stage++ }
                20 -> when (buttonID) {
                    1 -> playerl(FacialExpression.FRIENDLY, "Sure, why not.").also { stage = 21 }
                    2 -> playerl(FacialExpression.FRIENDLY, "Sorry, feeling a bit ill today.").also {
                        stage = END_DIALOGUE
                    }
                }

                21 -> npcl(FacialExpression.FRIENDLY, "That's fantastic! Oh, I'm so pleased.").also { stage++ }
                22 -> playerl(FacialExpression.FRIENDLY, "Calm down. I'm not making any promises.").also { stage++ }
                23 -> npcl(FacialExpression.FRIENDLY, "Oh, I have a good feeling about this.").also { stage++ }
                24 -> npcl(FacialExpression.FRIENDLY, "Right").also { stage++ }
                25 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Go have a word with Bonafido the head builder. See if you can't get him off his backside to do some work. Oh, and I'm Effigy by the way."
                ).also { stage++ }

                26 -> playerl(
                    FacialExpression.FRIENDLY,
                    "Nice to meet you Effigy, I'm ${player.username}. Hold on and I'll see what I can do!"
                ).also { stage++ }

                27 -> {
                    end()
                    setQuestStage(player, "Tower of Life", 1)
                    setVarbit(player, 3337, 1, true)
                }
            }

            3 -> if (getAttribute(player, TolUtils.TOL_PLAYER_ENTER_TOWER_ATTRIBUTE, 0) == 1) when (stage) {
                START_DIALOGUE -> playerl(FacialExpression.FRIENDLY, "Guess what!").also { stage++ }
                1 -> npcl(FacialExpression.FRIENDLY, "What?").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "I'm a fully-fledged builder!").also { stage++ }
                3 -> npcl(FacialExpression.FRIENDLY, "Marvelous! So you can bring this tower to life!").also { stage++ }
                4 -> playerl(
                    FacialExpression.FRIENDLY,
                    "I'm not sure about that, but I'll take a look and see what I can do."
                ).also { stage++ }

                5 -> npcl(FacialExpression.FRIENDLY, "Oh, what a wondrous person you are!").also {
                    stage = END_DIALOGUE
                }
            }

            4 -> when (stage) {
                START_DIALOGUE -> playerl(FacialExpression.FRIENDLY, "I've fixed all the machinery.").also { stage++ }
                1 -> npcl(FacialExpression.FRIENDLY, "Hurrah! Splendiferous!").also { stage++ }
                2 -> playerl(
                    FacialExpression.FRIENDLY,
                    "Now listen, what does it all do? Why are you-"
                ).also { stage++ }

                3 -> npcl(FacialExpression.FRIENDLY, "Years of devoted work; it's complete!").also { stage++ }
                4 -> npcl(FacialExpression.FRIENDLY, "Hey, guys! It's finished!").also { stage++ }
                5 -> npcl(FacialExpression.FRIENDLY, "At last! Our work is complete!").also { stage++ }
                6 -> npcl(FacialExpression.FRIENDLY, "I can almost taste the riches!").also { stage++ }
                7 -> npcl(FacialExpression.FRIENDLY, "To the top of the tower, fellow alchemists!").also { stage++ }
                8 -> playerl(FacialExpression.FRIENDLY, "Wait! What about-").also { stage = END_DIALOGUE }
            }

            6 -> {
                if (getAttribute(player, TolUtils.TOL_START_CUTSCENE_ATTRIBUTE, false)) {
                    when (stage) {
                        START_DIALOGUE -> playerl(FacialExpression.FRIENDLY, "Effigy!").also { stage++ }
                        1 -> npcl(
                            FacialExpression.FRIENDLY,
                            "I know, I know. This is a truly dangerous creation, of all proportions."
                        ).also { stage++ }

                        2 -> playerl(FacialExpression.FRIENDLY, "I hope you learn from this.").also { stage++ }
                        3 -> npcl(
                            FacialExpression.FRIENDLY,
                            "I will, I will. Next time we'll use a stronger cage."
                        ).also { stage++ }

                        4 -> playerl(FacialExpression.FRIENDLY, "What?!").also { stage++ }
                        5 -> npcl(FacialExpression.FRIENDLY, "A bad joke.").also { stage++ }
                        6 -> playerl(FacialExpression.FRIENDLY, "I'll just ignore that.").also { stage++ }
                        7 -> playerl(
                            FacialExpression.FRIENDLY,
                            "So, what about that Homunculus up there? You can't just leave it."
                        ).also { stage++ }

                        8 -> npcl(FacialExpression.FRIENDLY, "Maybe you could go have a talk with it?").also { stage++ }
                        9 -> playerl(FacialExpression.FRIENDLY, "Why me? You created it!").also { stage++ }
                        10 -> npcl(FacialExpression.FRIENDLY, "Pleeeaaase.").also { stage++ }
                        11 -> playerl(FacialExpression.FRIENDLY, "No.").also { stage++ }
                        12 -> npcl(FacialExpression.FRIENDLY, "Pretty please with a cherry on top?").also { stage++ }
                        13 -> playerl(FacialExpression.FRIENDLY, "No way!").also { stage++ }
                        14 -> npcl(
                            FacialExpression.FRIENDLY,
                            "I'll make sure you're compensated. You won't be forgotten for this!"
                        ).also { stage++ }

                        15 -> playerl(
                            FacialExpression.FRIENDLY,
                            "Why do I get the feeling I'm doomed to go face that creature on my own?"
                        ).also { stage++ }

                        16 -> npcl(FacialExpression.FRIENDLY, "Good! I knew you would help us!").also { stage++ }
                        17 -> playerl(
                            FacialExpression.FRIENDLY,
                            "Oh well. My mum did always tell me that experience teaches fools."
                        ).also { stage = END_DIALOGUE }
                    }
                }
            }

            8 -> when (stage) {
                START_DIALOGUE -> playerl(FacialExpression.FRIENDLY, "Effigy, I need a word with you.").also { stage++ }
                1 -> npcl(FacialExpression.FRIENDLY, "You've killed it?").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "Not quite...").also { stage++ }
                3 -> npcl(FacialExpression.FRIENDLY, "Boo.").also { stage++ }
                4 -> npcl(FacialExpression.FRIENDLY, "On no, look!").also { stage++ }
                5 -> npcl(FacialExpression.FRIENDLY, "Arrghhh! Have mercy!").also { stage++ }
                6 -> npcl(FacialExpression.FRIENDLY, "You set it free. Oh, please don't hurt me!").also { stage++ }
                7 -> npcl(FacialExpression.FRIENDLY, "Hahaha.").also { stage++ }
                8 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Me not hurt. Now tell. What you plans? What you do with me?"
                ).also { stage++ }

                9 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Please, we just wanted to experiment. We wanted to create life. There's a dungeon under the tower. Long ago, we found a strange source of energy that we believe was left behind by the great Guthix. He used to"
                ).also { stage++ }

                10 -> npcl(
                    FacialExpression.FRIENDLY,
                    "create life with such ease. We realised we should be able to use this powerful substance, but we needed to bring together our magic along with the logical construction of the builders. It seemed through our experiments that"
                ).also { stage++ }

                11 -> npcl(
                    FacialExpression.FRIENDLY,
                    "even magic needs a certain level of rules and laws to work."
                ).also { stage++ }

                12 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Bad play with. Naughty men. Go! Go, never return!"
                ).also { stage++ }

                13 -> npcl(FacialExpression.FRIENDLY, "Right away, right away!").also { stage++ }
                14 -> npcl(
                    FacialExpression.FRIENDLY,
                    "Go look dungeon, ${player.username}. Please meet there."
                ).also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.EFFIGY_5578)
    }
}
