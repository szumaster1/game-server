package content.minigame.tbwcleanup

import core.api.addItemOrDrop
import core.api.anyInEquipment
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.dialogue.Topic
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.RandomFunction.random
import core.tools.START_DIALOGUE
import kotlin.math.min
import core.game.node.entity.player.link.diary.DiaryType
import content.region.karamja.handlers.KaramjaAchievementDiary.Companion.MediumTasks.EXCHANGE_GEMS_TUBER_TRADING_STICKS_FOR_MACHETE
import core.api.consts.Items
import core.api.consts.NPCs

@Initializable
class SaftaDocDialogue(player: Player? = null) : Dialogue(player) {
    override fun newInstance(player: Player?): Dialogue {
        return SaftaDocDialogue(player)
    }

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (anyInEquipment(player, Items.TRIBAL_MASK_6335,Items.TRIBAL_MASK_6337,Items.TRIBAL_MASK_6339)) {
            npcl(FacialExpression.FRIENDLY,"AAAHHH!!!  Get away from me!").also{ stage = END_DIALOGUE }
            player.sendMessage("Perhaps I should try taking of the mask before talking to the villagers.")
            return false
        } else if (!player.questRepository.isComplete("Jungle Potion")) {
            npcl(FacialExpression.FRIENDLY,"Sorry I am not interested in talking to you right now.").also{ stage = END_DIALOGUE }
            return false
        }

        if (!player.getAttribute("/save:startedTBWCleanup", false) || player.getAttribute("/save:tbwcleanup", 0)==0)
            options("What do you do here?", "Is there anything interesting to do here?").also { stage = START_DIALOGUE }
        else
            options("What do you do here?", "Is there anything interesting to do here?","I've been doing some work around the village.").also { stage = START_DIALOGUE }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> when (buttonId) {
                1 -> playerl( FacialExpression.FRIENDLY,"What do you do here?").also { stage = 100 }
                2 -> playerl( FacialExpression.FRIENDLY,"Is there anything interesting to do here?").also { stage = 300 }
                3 -> playerl( FacialExpression.FRIENDLY,"I've been doing some work around the village.").also { stage = 400 }
            }
            1 -> { if (!player.getAttribute("/save:startedTBWCleanup", false) || player.getAttribute("/save:tbwcleanup",0) == 0) {
                    options( "What do you do here?","Is there anything interesting to do here?", "Ok, thanks.").also { stage = 2 }
                } else {
                    options("What do you do here?","Is there anything interesting to do here?","I've been doing some work around the village.","Ok, thanks.").also { stage = 3 }
                    }
            }
            2 -> {when (buttonId) {
                    1 -> playerl(FacialExpression.FRIENDLY,"What do you do here?").also { stage = 100 }
                    2 -> playerl(FacialExpression.FRIENDLY,"Is there anything interesting to do here?").also { stage = 300 }
                    3 -> playerl(FacialExpression.FRIENDLY,"Ok, thanks.").also { stage = END_DIALOGUE }
                }
            }
            3 -> {when (buttonId) {
                    1 -> playerl(FacialExpression.FRIENDLY, "What do you do here?").also { stage = 100 }
                    2 -> playerl(FacialExpression.FRIENDLY, "Is there anything interesting to do here?").also {stage = 300 }
                    3 -> playerl(FacialExpression.FRIENDLY, "I've been doing some work around the village.").also { stage = 400 }
                    4 -> playerl(FacialExpression.FRIENDLY, "Ok, thanks.").also { stage = END_DIALOGUE }
                }
            }

            100 -> { npcl(FacialExpression.FRIENDLY, "I's been making da machetes for Gabooty's shop Bwana!").also { stage = 101 }
            }
            101 -> { npcl( FacialExpression.FRIENDLY,
                    "If you want me to make you a machete we can do a deal if you like."
                ).also { stage = 102 }
            }
            102 -> { options("Yes, I'd like to get a machete.","No thanks, but I'd like to ask another question.","No thanks, I have to go.").also { stage = 103 }}
            103 -> { when(buttonId) {
                    1 -> playerl(FacialExpression.FRIENDLY, "Yes, I'd like to get a machete.").also { stage = 200 }
                    2 -> playerl(FacialExpression.FRIENDLY, "No thanks, but I'd like to ask another question.").also { stage = 1 }
                    3 -> playerl(FacialExpression.FRIENDLY, "No thanks, I have to go.").also { stage = END_DIALOGUE }
                }
            }

            200 -> { npcl(FacialExpression.FRIENDLY, "Ok Bwana, here's the deal. There are three gem bladed machete types, in order of quality and hacking proficiency, they're ordered from low to high as follows.").also { stage = 201 }
            }

            201 -> { showTopics(
                Topic("Opal machete.", 210,true),
                Topic("Jade machete.", 220,true),
                Topic("Red topaz machete.", 230,true),
                Topic("Talk to Safta-doc", 500,true),
                Topic(FacialExpression.FRIENDLY,"No thanks, but I'd like to ask another question.", 1))
            }

            210 -> {
                interpreter.sendItemMessage(Items.OPAL_MACHETE_6313, "Opal bladed machete - Safta doc needs the following to make<br> this machete: 3 opals - cut or uncut, 1 gout tuber plant<br> and 300 trading sticks.").also { stage = 211 }
            }
            211 -> { showTopics(
                Topic("Order the Opal machete.", 213,true),
                Topic("Go back to machete descriptions", 201,true))
            }
            213 -> {
                if (tryToMakeMachete(player, Items.OPAL_MACHETE_6313, Items.OPAL_1609, Items.UNCUT_OPAL_1625, 300)) {
                    npcl(FacialExpression.FRIENDLY, "Here you are Bwana, take good care of it.").also {stage = END_DIALOGUE}
                } else {
                    playerl(FacialExpression.SAD, "I don't think I have the required materials on me. Sorry.").also {stage = 214}
                }
            }
            214 -> {
                npcl(FacialExpression.FRIENDLY, "Ok Bwana, come back when you have them.").also { stage = END_DIALOGUE }
            }

            220 -> {
                interpreter.sendItemMessage(Items.JADE_MACHETE_6315, "Jade bladed machete - Safta doc needs the following to make<br> this machete: 3 jades - cut or uncut, 1 gout tuber plant<br> and 600 trading sticks.").also { stage = 221 }
            }
            221 -> { showTopics(
                Topic("Order the Jade machete.", 223,true),
                Topic("Go back to machete descriptions", 201,true))
            }
            223 -> if (tryToMakeMachete(player, Items.JADE_MACHETE_6315, Items.JADE_1611, Items.UNCUT_JADE_1627, 600)) {
                    npcl(FacialExpression.FRIENDLY, "Here you are Bwana, take good care of it.").also { stage = END_DIALOGUE }
                } else {
                playerl(FacialExpression.SAD, "I don't think I have the required materials on me. Sorry.").also { stage = 224 }
            }

            224 -> npcl(FacialExpression.FRIENDLY, "Ok Bwana, come back when you have them.").also { stage = END_DIALOGUE }


            230 -> interpreter.sendItemMessage(Items.RED_TOPAZ_MACHETE_6317, "Red topaz bladed machete - Safta doc needs the following to make<br> this machete: 3 red topaz - cut or uncut, 1 gout tuber plant<br> and 900 trading sticks.").also { stage = 231 }

            231 -> { showTopics(
                Topic("Order the Red topaz machete.", 233,true),
                Topic("Go back to machete descriptions", 201,true))
            }

            233 -> {
                if (tryToMakeMachete(player, Items.RED_TOPAZ_MACHETE_6317, Items.RED_TOPAZ_1613, Items.UNCUT_RED_TOPAZ_1629, 1200)) {
                    npcl(FacialExpression.FRIENDLY, "Here you are Bwana, take good care of it.").also {stage = END_DIALOGUE}
                    player.achievementDiaryManager.finishTask(player, DiaryType.KARAMJA, 1, EXCHANGE_GEMS_TUBER_TRADING_STICKS_FOR_MACHETE)
                } else {
                    playerl(FacialExpression.SAD, "I don't think I have the required materials on me. Sorry.").also { stage = 234}
                }
            }
            234 -> {
                npcl(FacialExpression.FRIENDLY, "Ok Bwana, come back when you have them.").also { stage = END_DIALOGUE }
            }

            300 -> { when(random(1,6)) {
                1 -> npcl(FacialExpression.FRIENDLY, "Not that I'm aware of, just sit back and enjoy the sunshine bwana!").also { stage = END_DIALOGUE }
                2 -> npcl(FacialExpression.FRIENDLY, "Bwana, if anyone else asks me that question today, I'll explode! Go and ask someone else!").also { stage = END_DIALOGUE }
                3 -> npcl(FacialExpression.FRIENDLY, "It's just village life as normal around here Bwana, always something interesting to find to occupy your time if you look hard enough.").also { stage = END_DIALOGUE }
                4 -> npcl(FacialExpression.FRIENDLY, "Sorry Bwana, I'm already busy, why not go and talk to Murcaily! He's around the village somewhere.").also { stage = END_DIALOGUE }
                5 -> npcl(FacialExpression.FRIENDLY, "Well, I think that there's some work to be done...perhaps Murcaily can help you. He usually tends to the hardwood grove to the east of Trufitus's hut.").also { stage = END_DIALOGUE }
                }
            }
            400 -> {
                val tradingStickReward = min(player.getAttribute("/save:tbwcleanup", 0), 100)
                if (tradingStickReward == 0) {
                    npcl(FacialExpression.FRIENDLY, "I don't think you deserve any trading sticks yet. Why don't you do some work around the village first.").also { stage = END_DIALOGUE }
                } else {
                    spendTBWCleanupPoints(player, tradingStickReward)
                    addItemOrDrop(player, Items.TRADING_STICKS_6306, tradingStickReward)
                    npcl(FacialExpression.FRIENDLY, "Hey, I've seen you working around the village. You've been doing a lot of good work around here for us. Let me give you something for your trouble.").also { stage = END_DIALOGUE }
                }
            }
            500 -> { npcl(FacialExpression.FRIENDLY, "So Bwana, do you like the look of the machetes?").also { stage = 501 }
            }
            501 -> { options("Why do you need gems?","Why do you need a gout plant tuber?","Why is it so expensive?","Let me see the machetes again.","Ok, thanks.").also { stage = 502 }}
            502 -> { when(buttonId) {
                    1 -> playerl(FacialExpression.FRIENDLY, "Why do you need gems?").also { stage = 510 }
                    2 -> playerl(FacialExpression.FRIENDLY, "Why do you need a gout plant tuber?").also { stage = 520 }
                    3 -> playerl(FacialExpression.FRIENDLY, "Why is it so expensive?").also { stage = 530 }
                    4 -> playerl(FacialExpression.FRIENDLY, "Let me see the machetes again.").also { stage = 201 }
                    5 -> playerl(FacialExpression.FRIENDLY, "Ok, thanks.").also { stage = END_DIALOGUE }
                }
            }
            510 -> npcl(FacialExpression.FRIENDLY, "They're tougher than just metal on its own, plus it's the only such resource naturally available in this region.").also { stage = 501 }
            520 -> npcl(FacialExpression.FRIENDLY, "It's one of the harder to find resources which I mix with other ingredients in order to bind the ground down gem dust to the surface of the metal bladed machete.").also { stage = 521 }
            521 -> npcl(FacialExpression.FRIENDLY, "You can sometimes, if you're lucky, find the plant if you hack away at the jungle around here. You'll need to find a way to dig up the tubers though, they're the important part of the plant.").also { stage = 501 }
            530 -> npcl(FacialExpression.FRIENDLY, "Expensive bwana, it's quite reasonably priced if you ask me. I can make it cheaper than Gabooty charges for it, that's for sure. And after all, I have to earn a living around here as well you know, I have children to feed!").also { stage = 501 }


        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.KARADAY_2516)
    }

    private fun tryToMakeMachete(player: Player, macheteId: Int, gemCut: Int,gemUnCut: Int, tradingSticksNeeded: Int): Boolean {
        for (gem in arrayOf(gemCut, gemUnCut)) {
            val itemsNeeded = arrayOf(
                Item(gem,3),
                Item(Items.GOUT_TUBER_6311, 1),
                Item(Items.TRADING_STICKS_6306, tradingSticksNeeded))

            if (player.inventory.containsItems(*itemsNeeded)) {
                if(player.inventory.remove(*itemsNeeded)) {
                    addItemOrDrop(player, macheteId, 1)
                    return true
                }
            }
        }
        return false
    }
}
