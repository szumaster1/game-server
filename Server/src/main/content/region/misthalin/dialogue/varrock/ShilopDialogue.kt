package content.region.misthalin.dialogue.varrock

import core.api.consts.Items
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable

private var id = 0

/**
 * Shilop dialogue.
 */
@Initializable
class ShilopDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        if (args[0] is NPC) {
            id = (args[0] as NPC).id
        } else if (args[0] is Int) {
            id = args[0] as Int
        }
        val quest = player.getQuestRepository().getQuest("Gertrude's Cat")
        stage = when (quest.getStage(player)) {
            0 -> {
                player(FacialExpression.HALF_GUILTY, "Hello again.")
                0
            }

            10 -> {
                player(FacialExpression.HALF_GUILTY, "Hello there, I've been looking for you.")
                100
            }

            20, 30, 40, 50 -> {
                player("Where did you say you saw Fluffs?")
                130
            }

            else -> {
                player(FacialExpression.HALF_GUILTY, "Hello again.")
                0
            }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        val quest = player.getQuestRepository().getQuest("Gertrude's Cat")
        when (stage) {
            0 -> {
                interpreter.sendDialogues(id, FacialExpression.CHILD_THINKING, "You think you're tough do you?")
                stage = 1
            }

            1 -> {
                player(FacialExpression.HALF_GUILTY, "Pardon?")
                stage = 2
            }

            2 -> {
                interpreter.sendDialogues(id, FacialExpression.CHILD_FRIENDLY, "I can beat anyone up!")
                stage = 3
            }

            3 -> {
                interpreter.sendDialogues(783, FacialExpression.CHILD_FRIENDLY, "He can you know!")
                stage = 4
            }

            4 -> {
                player(FacialExpression.HALF_GUILTY, "Really?")
                stage = 5
            }

            5 -> {
                interpreter.sendDialogue("The boy begins to jump around with his fists up.", "You wonder what sort of desperado he'll grow up to be.")
                stage = 6
            }

            6 -> end()
            100 -> {
                interpreter.sendDialogues(id, FacialExpression.CHILD_FRIENDLY, "I didn't mean to take it! I just forgot to pay.")
                stage = 101
            }

            101 -> {
                player(FacialExpression.HALF_GUILTY, "What? I'm trying to help your mum find Fluffs.")
                stage = 102
            }

            102 -> {
                interpreter.sendDialogues(if (id == 781) 783 else 781, FacialExpression.CHILD_FRIENDLY, "I might be able to help. Fluffs followed me to our secret", "play area and I haven't seen her since.")
                stage = 103
            }

            103 -> {
                player(FacialExpression.HALF_GUILTY, "Where is this play area?")
                stage = 104
            }

            104 -> {
                interpreter.sendDialogues(if (id == 781) 783 else 781, FacialExpression.CHILD_FRIENDLY, "If I told you that, it wouldn't be a secret.")
                stage = 105
            }

            105 -> {
                player(FacialExpression.HALF_GUILTY, "What will make you tell me?")
                stage = 106
            }

            106 -> {
                interpreter.sendDialogues(if (id == 781) 783 else 781, FacialExpression.CHILD_FRIENDLY, "Well...now you ask, I am a bit short on cash.")
                stage = 107
            }

            107 -> {
                player(FacialExpression.HALF_ASKING, "How much?")
                stage = 108
            }

            108 -> {
                interpreter.sendDialogues(if (id == 781) 783 else 781, FacialExpression.CHILD_FRIENDLY, "10 coins.")
                stage = 109
            }

            109 -> {
                player(FacialExpression.HALF_ASKING, "10 coins?!")
                stage = 110
            }

            110 -> {
                player(FacialExpression.CHILD_FRIENDLY, "I'll handle this.")
                stage = 111
            }

            111 -> {
                interpreter.sendDialogues(if (id == 781) 783 else 781, FacialExpression.CHILD_FRIENDLY, "100 coins should cover it.")
                stage = 112
            }

            112 -> {
                player(FacialExpression.ANNOYED, "100 coins! Why should I pay you?")
                stage = 113
            }

            113 -> {
                interpreter.sendDialogues(if (id == 781) 783 else 781, FacialExpression.CHILD_FRIENDLY, "You shouldn't, but we won't help otherwise. We never", "liked that cat anyway, so what do you say?")
                stage = 114
            }

            114 -> {
                options( "I'm not paying you a penny.", "Okay then, I'll pay.")
                stage = 115
            }

            115 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.NEUTRAL, "I'm not paying you a penny.")
                    stage = 116
                }

                2 -> {
                    player(FacialExpression.FRIENDLY, "Okay then, I'll pay.")
                    stage = 118
                }
            }

            116 -> {
                interpreter.sendDialogues(if (id == 781) 783 else 781, FacialExpression.CHILD_FRIENDLY, "Okay then, I'll find another way to make money.")
                stage = 117
            }

            117 -> end()
            118 -> {
                if (!player.inventory.containsItem(COINS)) {
                    player(FacialExpression.SAD, "Sorry, I don't seem to have enough coins.")
                    stage = 117
                    return true
                }
                stage = if (player.inventory.remove(COINS)) {
                    interpreter.sendItemMessage(Items.COINS_8896, "You give the lad 100 coins.")
                    119
                } else {
                    player(FacialExpression.SAD, "Sorry, I don't seem to have enough coins.")
                    117
                }
                quest.setStage(player, 20)
            }

            119 -> {
                player(FacialExpression.HALF_ASKING, "There you go, now where did you see Fluffs?")
                stage = 120
            }

            120 -> {
                interpreter.sendDialogues(if (id == 781) 783 else 781, FacialExpression.CHILD_FRIENDLY, "We play at an abandoned lumber mill to the north east.", "Just beyond the Jolly Boar Inn. I saw Fluffs running", "around in there.")
                stage = 121
            }

            121 -> {
                player(FacialExpression.ASKING, "Anything else?")
                stage = 122
            }

            122 -> {
                interpreter.sendDialogues(if (id == 781) 783 else 781, FacialExpression.CHILD_FRIENDLY, "Well, you'll have to find the broken fence to get in. I'm", "sure you can manage that.")
                stage = 123
            }

            123 -> end()
            130 -> {
                interpreter.sendDialogues(id, FacialExpression.CHILD_FRIENDLY, "Weren't you listening? I saw the flea bag in the old", "lumber mill just north east of here. Just walk past the", "Jolly Boar Inn and you should find it.")
                stage = 131
            }

            131 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(781)
    }

    companion object {
        /*
     *	Info: One of Gertrude's children.
     *	He is in the Varrock Square (the center of Varrock).
     */
        private val COINS = Item(995, 100)
    }
}