package content.region.asgarnia.dialogue.rimmington

import core.api.consts.NPCs
import core.api.sendDialogueLines
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class HettyDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        val quest = player.getQuestRepository().getQuest("Witch's Potion")
        if (quest.isCompleted(player)) {
            npc(FacialExpression.ASKING, "How's your magic coming along?")
            stage = 0
        }
        when (quest.getStage(player)) {
            0 -> {
                npc(FacialExpression.NEUTRAL, "What could you want with an old woman like me?")
                stage = 11
            }

            20 -> {
                npc(FacialExpression.HAPPY, "So have you found the things for the potion?")
                stage = 100
            }

            40 -> if (args.size == 2) {
                sendDialogueLines(player,"You drink from the cauldron, it tastes horrible! You feel yourself", "imbued with power.")
                stage = 41
            } else {
               npc(FacialExpression.HALF_GUILTY, "Well are you going to drink the potion or not?")
                stage = 500
            }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        val quest = player.getQuestRepository().getQuest("Witch's Potion")
        when (stage) {
            0 -> {
                player(FacialExpression.HALF_GUILTY, "I'm practicing and slowly getting better.").also { stage++ }
                stage = 1
            }
            1 -> npc(FacialExpression.HALF_GUILTY, "Good, good.").also { stage = END_DIALOGUE }
            11 -> {
                options("I am in search of a quest.", "I've heard that you are a witch.")
                stage = 12
            }
            12 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.NEUTRAL, "I am in search of a quest.")
                    stage = 13
                }
                2 -> {
                    player(FacialExpression.NEUTRAL, "I've heard that you are a witch.")
                    stage = 20
                }
            }
            13 -> {
                npc(FacialExpression.HAPPY, "Hmmm... Maybe I can think of something for you.")
                stage = 14
            }
            14 -> {
                npc(FacialExpression.HAPPY, "Would you like to become more proficient in the dark", "arts?")
                stage = 15
            }
            15 -> {
                options("Yes help me become one with my darker side.", "No I have my principles and honour.")
                stage = 16
            }
            16 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HAPPY, "Yes help me become one with my darker side.")
                    stage = 30
                }
                2 -> {
                    player(FacialExpression.NEUTRAL, "No I have my principles and honour.")
                    stage = 17
                }
            }
            17 -> npc(FacialExpression.HALF_GUILTY, "Suit yourself, but you're missing out.").also { stage = END_DIALOGUE }
            20 -> {
                npc(FacialExpression.HALF_GUILTY, "Yes it does seem to be getting fairly common", "knowledge.")
                stage = 21
            }

            21 -> {
                npc(FacialExpression.HALF_GUILTY, "I fear I may get a visit from the witch hunter of", "Falador before long.")
                stage = 22
            }

            22 -> end()
            30 -> {
                npc(FacialExpression.NEUTRAL, "Ok I'm going to make a potion to help bring out your", "darker self.")
                stage = 31
            }

            31 -> {
                npc(FacialExpression.NEUTRAL, "You will need certain ingredients.")
                stage = 32
            }

            32 -> {
                player(FacialExpression.NEUTRAL, "What do I need?")
                stage = 33
            }

            33 -> {
                npc(FacialExpression.NEUTRAL, "You need an eye of newt, a rat's tail, and onion... Oh", "and a piece of burnt meat.")
                stage = 34
            }

            34 -> {
                player(FacialExpression.HAPPY, "Great, I'll go get them.")
                stage = 35
            }

            35 -> {
                quest.start(player)
                quest.setStage(player, 20)
                end()
            }

            100 -> if (!player.inventory.containItems(1957, 300, 2146, 221)) {
                player(FacialExpression.HALF_GUILTY, "I'm afraid I don't have all of them yet.")
                stage = 101
            } else {
                player(FacialExpression.HAPPY, "Yes I have everything!")
                stage = 110
            }

            110 -> {
                npc(FacialExpression.HAPPY, "Excellent, can I have them then?")
                stage = 111
            }

            111 -> {
                interpreter.sendDialogue("You pass the ingredients to Hetty and she puts them all into her", "cauldron. Hetty closes her eyes and begins to chant. The cauldron", "bubbles mysteriously.")
                stage = 112
            }

            112 -> {
                interpreter.sendDialogues(player, FacialExpression.NEUTRAL, "Well, is it ready?")
                stage = 113
            }

            113 -> if (player.inventory.remove(Item(1957), Item(300), Item(2146), Item(221))) {
                quest.setStage(player, 40)
                npc(FacialExpression.HAPPY, "Ok, now drink from the cauldron.")
                stage = 114
            }

            114 -> end()
            101 -> {
                npc(FacialExpression.NEUTRAL, "Well I can't make the potion without them! Remember...", "You need an eye of newt, a rat's tail, an onion, and a", "piece of burnt meat. Off you go dear!")
                stage = 102
            }

            102 -> end()
            500 -> end()
            41 -> {
                end()
                quest.finish(player)
                player.getQuestRepository().syncronizeTab(player)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.HETTY_307)
    }
}
