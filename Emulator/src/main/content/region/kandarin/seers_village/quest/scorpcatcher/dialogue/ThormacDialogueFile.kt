package content.region.kandarin.seers_village.quest.scorpcatcher.dialogue

import content.region.kandarin.seers_village.quest.scorpcatcher.ScorpionCatcher
import content.region.kandarin.seers_village.quest.scorpcatcher.handlers.ScorpionCatcherListeners.Companion.getCage
import core.api.*
import cfg.consts.Components
import cfg.consts.Items
import cfg.consts.NPCs
import core.game.component.Component
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.skill.Skills
import core.game.world.GameWorld
import core.tools.END_DIALOGUE

/**
 * Represents the Thormac dialogue file.
 */
class ThormacDialogueFile : DialogueFile() {

    // Thormac is a npc who lives at the top of the Sorcerer's Tower south of Seers Village.
    // After completing the quest, he can perform a useful service for players which will change a battlestaff
    // into a mystic staff. This service costs 40,000 coins or 27,000 if you are wearing the Seers' headband 3.

    override fun handle(componentID: Int, buttonID: Int) {
        val questName = "Scorpion Catcher"
        val questStage = getQuestStage(player!!, questName)
        val hasAnCage = hasAnItem(player!!, *intArrayOf(Items.SCORPION_CAGE_456, Items.SCORPION_CAGE_457, Items.SCORPION_CAGE_458, Items.SCORPION_CAGE_459, Items.SCORPION_CAGE_460, Items.SCORPION_CAGE_461,Items.SCORPION_CAGE_462,Items.SCORPION_CAGE_463)).container != null
        val wearHeadBand = anyInEquipment(player!!, Items.SEERS_HEADBAND_1_14631, Items.SEERS_HEADBAND_2_14659, Items.SEERS_HEADBAND_3_14660)
        npc = NPC(NPCs.THORMAC_389)
        when {
            (questStage == 0) -> {
                when (stage) {
                    0 -> {
                        if (!hasLevelStat(player!!, Skills.PRAYER, 31))
                            sendMessage(player!!, "Thormac is not interested in talking.")
                        else if (getAttribute(player!!, ScorpionCatcher.ATTRIBUTE_CAGE, false)) {
                            npcl(FacialExpression.NEUTRAL, "If you go up to the village of Seers, to the North of here, one of them will be able to tell you where the scorpions are now.").also { stage = 21 }
                        } else {
                            npcl(FacialExpression.FRIENDLY, "Hello I am Thormac the sorcerer. I don't suppose you could be of assistance to me?").also { stage++ }
                        }
                    }

                    1 -> options("What do you need assistance with?", "I'm a little busy.").also { stage++ }
                    2 -> when (buttonID) {
                        1 -> playerl(FacialExpression.HALF_ASKING, "What do you need assistance with?").also { stage = 5 }
                        2 -> playerl(FacialExpression.NEUTRAL, "I'm a little busy.").also { stage = END_DIALOGUE }
                    }
                    5 -> npcl(FacialExpression.SAD, "I've lost my pet scorpions. They're lesser Kharid scorpions, a very rare breed.").also { stage++ }
                    6 -> npcl(FacialExpression.SAD, "I left their cage door open, now I don't know where they've gone.").also { stage++ }
                    7 -> npcl(FacialExpression.NEUTRAL, "There's three of them, and they're quick little beasties. They're all over" + GameWorld.settings!!.name + ".").also { stage++ }
                    8 -> options("So how would I go about catching them then?", "What's in it for me?", "I'm not interested then.").also { stage++ }
                    9 -> when (buttonID) {
                        1 -> playerl(FacialExpression.HALF_ASKING, "So how would I go about catching them then?").also { stage = 16 }
                        2 -> playerl(FacialExpression.HALF_ASKING, "What's in it for me?").also { stage = 13 }
                        3 -> playerl(FacialExpression.NEUTRAL, "I'm not interested then.").also { stage = 11 }
                    }
                    11 -> npcl(FacialExpression.NEUTRAL, "Blast, I suppose I'll have to find someone else then.").also { stage = END_DIALOGUE }
                    13 -> npcl(FacialExpression.NEUTRAL, "Well I suppose I can aid you with my skills as a staff sorcerer. Most battlestaves around here are a bit puny. I can beef them up for you a bit.").also { stage++ }
                    14 -> options("So how would I go about catching them then?", "I'm not interested then.").also { stage++ }
                    15 -> when (buttonID) {
                        1 -> playerl(FacialExpression.HALF_ASKING, "So how would I go about catching them then?").also { stage = 16 }
                        2 -> playerl(FacialExpression.NEUTRAL, "I'm not interested then.").also { stage = 11 }
                    }
                    16 -> npcl(FacialExpression.NEUTRAL, "Well I have a scorpion cage here which you can use to catch them in.").also { stage++ }
                    17 -> runTask(player!!, 0) {
                        lock(player!!, 6)
                        lockInteractions(npc!!, 6)
                        getCage(player!!)
                        player!!.interfaceManager.close(Component(Components.NPCCHAT1_241))
                    }
                    18 -> options("Ok, I will do it then", "What's in it for me?", "I'm not interested then.").also { stage++ }
                    19 -> when (buttonID) {
                        1 -> playerl(FacialExpression.HALF_ASKING, "Ok, I will do it then").also { stage = END_DIALOGUE }
                        2 -> playerl(FacialExpression.HALF_ASKING, "What's in it for me?").also { stage = 20 }
                        3 -> playerl(FacialExpression.NEUTRAL, "I'm not interested then.").also { stage = 11 }
                    }
                    20 -> npcl(FacialExpression.NEUTRAL, "Well I suppose I can aid you with my skills as a staff sorcerer. Most battlestaves around here are a bit puny. I can beef them up for you a bit.").also { stage++ }

                    21 -> options("Ok, I will do it then", "I'm not interested then.").also {
                        removeAttribute(player!!, ScorpionCatcher.ATTRIBUTE_CAGE)
                        stage++
                    }

                    22 -> when (buttonID) {
                        1 -> playerl(FacialExpression.NEUTRAL, "Ok, I will do it then.").also {
                            setQuestStage(player!!, "Scorpion Catcher", 10)
                            stage = END_DIALOGUE
                        }
                        2 -> playerl(FacialExpression.NEUTRAL, "I'm not interested then.").also { stage = 11 }
                    }
                }
            }

            (questStage in 1..50) -> {
                when (stage) {
                    0 -> npcl(FacialExpression.HALF_ASKING, "How goes your quest?").also { stage++ }
                    1 -> if(!hasAnCage) {
                        playerl(FacialExpression.NEUTRAL, "I've lost my cage.").also { stage++ }
                    } else if(inInventory(player!!, Items.SCORPION_CAGE_463)){
                        playerl(FacialExpression.NEUTRAL, "I have retrieved all your scorpions.").also { stage = 5 }
                    } else {
                        playerl(FacialExpression.NEUTRAL, "I've not caught all the scorpions yet.").also { stage = 4 }
                    }
                    2 -> npcl(FacialExpression.NEUTRAL, "Ok, here's another cage. You're almost as bad at losing things as me.").also { stage++ }
                    3 -> {
                        end()
                        // Start again.
                        player!!.questRepository.setStageNonmonotonic(player!!.questRepository.forIndex(108), 10)
                        removeAttribute(player!!,"scorpion_catcher:caught_taverly")
                        removeAttribute(player!!,"scorpion_catcher:caught_barb")
                        removeAttribute(player!!,"scorpion_catcher:caught_monk")
                        addItemOrDrop(player!!, Items.SCORPION_CAGE_456)
                        stage = END_DIALOGUE
                    }
                    4 -> npc(FacialExpression.NEUTRAL, "Well remember to go speak to the Seers, ", "North of here, if you need any help.").also { stage = END_DIALOGUE }
                    5 -> npcl(FacialExpression.NEUTRAL, "Aha, my little scorpions home at last!").also { stage++ }
                    6 -> {
                        end()
                        finishQuest(player!!, "Scorpion Catcher")
                        removeItem(player!!, Items.SCORPION_CAGE_463)
                        sendMessage(player!!, "You don't need that scorpion cage anymore.")
                        stage = END_DIALOGUE
                    }
                }
            }

            (questStage == 100) -> {
                when (stage) {
                    0 -> npcl(FacialExpression.NEUTRAL, "Thank you for rescuing my scorpions.").also { stage++ }
                    1 -> options("That's okay.", "You said you'd enchant my battlestaff for me.").also { stage++ }
                    2 -> when (buttonID) {
                        1 -> playerl(FacialExpression.NEUTRAL, "That's okay.").also { stage = END_DIALOGUE }
                        2 -> playerl(FacialExpression.NEUTRAL, "You said you'd enchant my battlestaff for me.").also { stage++ }
                    }
                    3 -> npc(FacialExpression.NEUTRAL, "Yes, although it'll cost you " + (if (wearHeadBand) "27,000" else "40,000") + " coins for the", "materials. What kind of staff did you want enchanting?").also { stage++ }
                    4 -> {
                        end()
                        openInterface(player!!, Components.STAFF_ENCHANT_332)
                    }
                }
            }
        }
    }

}

