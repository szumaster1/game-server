package content.region.kandarin.quest.murder.dialogue

import content.region.kandarin.quest.murder.handlers.MysteryUtils
import core.api.*
import org.rs.consts.Items
import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.world.GameWorld
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Represents the Guard dialogue.
 */
@Initializable
class GuardDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (inBorders(player, MysteryUtils.MANSION_ROAD_ZONE)) {
            playerl(FacialExpression.FRIENDLY, "What's going on here?").also { stage++ }
        } else if (getQuestStage(player,"Murder Mystery") == 1 && inBorders(player, MysteryUtils.MANSION_ROAD_ZONE)) {
            playerl(FacialExpression.FRIENDLY, "What should I be doing to help again?").also { stage++ }
        } else {
            sendMessage(player!!, "He is ignoring you.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun handle(componentID: Int, buttonID: Int): Boolean {
        var guiltyEvidence = intArrayOf(Items.CRIMINALS_THREAD_1808, Items.CRIMINALS_THREAD_1809, Items.CRIMINALS_THREAD_1810)
        val questStage = getQuestStage(player!!, "Murder Mystery")
        when (questStage) {
            0 -> when (stage) {
                START_DIALOGUE -> npcl(FacialExpression.FRIENDLY, "There's some kind of commotion up at the Sinclair place I hear. Not surprising all things considered.").also { stage = 2 }
                2 -> npcl(FacialExpression.FRIENDLY, "If you can help us we will be very grateful.").also { stage = 3 }
                3 -> options("Yes.", "No.").also { stage = 4 }
                4 -> when (buttonID) {
                    1 -> playerl(FacialExpression.FRIENDLY, "Sure, I'll help!").also { stage = 5 }
                    2 -> playerl(FacialExpression.FRIENDLY, " You should do your own dirty work.").also { stage = 8 }
                }
                5 -> npcl(FacialExpression.FRIENDLY, "Thanks a lot!").also { stage = 6 }
                6 -> playerl(FacialExpression.FRIENDLY, "What should I be doing to help?").also { stage = 7 }
                7 -> npcl(FacialExpression.FRIENDLY, "Look around and investigate who might be responsible. The Sarge said every murder leaves clues to who done it, but frankly we're out of our depth here.").also { stage = 9 }
                8 -> npcl(FacialExpression.ANGRY, "Get lost then, this is private property! ...Unless you'd like to be taken in for questioning yourself?").also { stage = END_DIALOGUE }
                9 -> {
                    end()
                    setQuestStage(player!!, "Murder Mystery", 1)
                }
            }

            1 -> when (stage) {
                START_DIALOGUE -> npcl(FacialExpression.FRIENDLY, "Look around and investigate who might be responsible. The Sarge said every murder leaves clues to who done it, but frankly we're out of our depth here.").also { stage = 2 }
                2 -> options("How did Lord Sinclair die?", "I know who did it!").also { stage++ }
                3 -> when (buttonID) {
                    1 -> playerl(FacialExpression.FRIENDLY, "How did Lord Sinclair die?").also { stage = 4 }
                    2 -> {
                        if (anyInInventory(player, *guiltyEvidence) && getQuestStage(player, "Murder Mystery") >= 1) {
                            playerl(FacialExpression.FRIENDLY, "I know who did it!").also { stage = 32 }
                        } else if (getQuestStage(player, "Murder Mystery") >= 2) {
                            playerl(FacialExpression.FRIENDLY, "I know who did it!").also { stage = 36 }
                        } else if (getQuestStage(player,"Murder Mystery") >= 3) {
                            playerl(FacialExpression.FRIENDLY, "I know who did it!").also { stage = 61 }
                        } else {
                            playerl(FacialExpression.FRIENDLY, "I know who did it!").also { stage = 6 }
                        }
                    }
                }
                4 -> npcl(FacialExpression.FRIENDLY, "Well, it's all very mysterious. Mary, the maid, found the body in the study next to his bedroom on the east wing of the ground floor.").also { stage++ }
                5 -> npcl(FacialExpression.FRIENDLY, "The door was found locked from the inside, and he seemed to have been stabbed, but there was an odd smell in the room. Frankly, I'm stumped.").also { stage = END_DIALOGUE }
                6 -> npcl(FacialExpression.FRIENDLY, "Really? That was quick work! Who?").also { stage++ }
                7 -> options("It was an intruder!", "The butler did it!", "It was one of the servants!", "It was one of his family!").also { stage++ }
                8 -> when (buttonID) {
                    1 -> playerl(FacialExpression.FRIENDLY, "It was an intruder!").also { stage = 11 }
                    2 -> playerl(FacialExpression.FRIENDLY, "The butler did it!").also { stage = 9 }
                    3 -> playerl(FacialExpression.FRIENDLY, "It was one of the servants!").also { stage = 13 }
                    4 -> playerl(FacialExpression.FRIENDLY, "It was one of his family!").also { stage = 20 }
                }
                9 -> npcl(FacialExpression.FRIENDLY, "I hope you have proof to that effect. We have to arrest someone for this and it seems to me that only the actual murderer would gain by falsely accusing someone.").also { stage++ }
                10 -> npcl(FacialExpression.FRIENDLY, "Although having said that the butler is kind of shifty looking...").also { stage = END_DIALOGUE }
                11 -> npcl(FacialExpression.FRIENDLY, "That's what we were thinking too. That someone broke in to steal something, was discovered by Lord Sinclair, stabbed him and ran. It's odd that apparently nothing was stolen though, find out something has been stolen, and the case is closed.").also { stage++ }
                12 -> npcl(FacialExpression.FRIENDLY, "But the murdered man was a friend of the king and it's more than my jobs worth not to investigate fully.").also { stage = END_DIALOGUE }
                13 -> npcl(FacialExpression.FRIENDLY, "Oh really? Which one?").also { stage++ }
                14 -> options("It was one of the women.", "It was one of the men.").also { stage++ }
                15 -> when (buttonID) {
                    1 -> playerl(FacialExpression.FRIENDLY, "It was one of the women.").also { stage = 16 }
                    2 -> playerl(FacialExpression.FRIENDLY, "It was one of the men.").also { stage = 18 }
                }
                16 -> options("It was so obviously Louisa The Cook.", "It must have been Mary The Maid.").also { stage++ }
                17 -> when (buttonID) {
                    1 -> playerl(FacialExpression.FRIENDLY, "It was so obviously Louisa The Cook.").also { stage = 27 }
                    2 -> playerl(FacialExpression.FRIENDLY, "It must have been Mary The Maid.").also { stage = 27 }
                }
                18 -> options("It can only be Donovan the Handyman.", "Pierre the Dog Handler. No question", "Hobbes the Butler. The butler *always* did it.", "You must know it was Stanford The Gardener.").also { stage++ }
                19 -> when (buttonID) {
                    1 -> playerl(FacialExpression.FRIENDLY, "It can only be Donovan the Handyman.").also { stage = 27 }
                    2 -> playerl(FacialExpression.FRIENDLY, "Pierre the Dog Handler. No question.").also { stage = 27 }
                    3 -> playerl(FacialExpression.FRIENDLY, "Hobbes the Butler. The butler *always* did it.").also { stage = 9 }
                    4 -> playerl(FacialExpression.FRIENDLY, "You must know it was Stanford The Gardener.").also { stage = 27 }
                }
                20 -> npcl(FacialExpression.FRIENDLY, "Oh really? Which one?").also { stage++ }
                21 -> options("It was one of the women.", "It was one of the men.").also { stage++ }
                22 -> when (buttonID) {
                    1 -> playerl(FacialExpression.FRIENDLY, "It was one of the women.").also { stage = 23 }
                    2 -> playerl(FacialExpression.FRIENDLY, "It was one of the men.").also { stage = 26 }
                }
                23 -> options("I know it was Anna.", "I am so sure it was Carol.", "I'll bet you anything it was Elizabeth.").also { stage++ }
                24 -> when (buttonID) {
                    1 -> playerl(FacialExpression.FRIENDLY, "I know it was Anna.").also { stage = 27 }
                    2 -> playerl(FacialExpression.FRIENDLY, "I am so sure it was Carol.").also { stage = 27 }
                    3 -> playerl(FacialExpression.FRIENDLY, "I'll bet you anything it was Elizabeth.").also { stage = 27 }
                }
                25 -> options("I'm certain it was Bob.", "It was David. No doubt about it.", "If it wasn't Frank I'll eat my shoes.").also { stage++ }
                26 -> when (buttonID) {
                    1 -> playerl(FacialExpression.FRIENDLY, "I'm certain it was Bob.").also { stage = 27 }
                    2 -> playerl(FacialExpression.FRIENDLY, "It was David. No doubt about it.").also { stage = 27 }
                    3 -> playerl(FacialExpression.FRIENDLY, "If it wasn't Frank I'll eat my shoes.").also { stage = 27 }
                }
                27 -> interpreter!!.sendDialogue("You tell the guard who you suspect of the crime").also { stage++ }
                28 -> npcl(FacialExpression.FRIENDLY, "Great work, show me the evidence and we'll take them to the dungeons.").also { stage++ }
                29 -> npcl(FacialExpression.FRIENDLY, "You *DO* have evidence of their crime, right?").also { stage++ }
                30 -> playerl(FacialExpression.FRIENDLY, "Uh...").also { stage++ }
                31 -> npcl(FacialExpression.FRIENDLY, "Tch. You wouldn't last a day in the guards with sloppy thinking like that. Come see me when you have some proof of your accusations.").also { stage = END_DIALOGUE }
                32 -> playerl(FacialExpression.FRIENDLY, "I have proof that it wasn't any of the servants!").also { stage++ }
                33 -> interpreter!!.sendDialogue("You show the guard the thread you found on the window.").also { stage++ }
                34 -> playerl(FacialExpression.FRIENDLY, "All the servants dress in black so it couldn't have been one of them.").also { stage++ }
                35 -> npcl(FacialExpression.FRIENDLY, "That's some good work there. I guess it wasn't a servant. You still haven't proved who did do it though.").also { stage = END_DIALOGUE }
                36 -> {
                    if (getAttribute(player!!, MysteryUtils.ATTRIBUTE_ANNA, false)) {
                        playerl(FacialExpression.FRIENDLY, "I have proof one of the family lied about the poison.").also { stage = 37 }
                    } else if (getAttribute(player!!, MysteryUtils.ATTRIBUTE_DAVID, false)) {
                        playerl(FacialExpression.FRIENDLY, "I have proof one of the family lied about the poison.").also { stage = 46 }
                    } else if (getAttribute(player!!, MysteryUtils.ATTRIBUTE_ELIZABETH, false)) {
                        playerl(FacialExpression.FRIENDLY, "I have proof one of the family lied about the poison.").also { stage = 49 }
                    }
                }
                // If it was Anna
                37 -> playerl(FacialExpression.FRIENDLY, "I have proof that Anna is lying about the poison.").also { stage++ }
                38 -> npcl(FacialExpression.FRIENDLY, "Oh really? How did you get that?").also { stage++ }
                39 -> interpreter!!.sendDialogue("You tell the guard about the compost heap.").also { stage = 55 }
                // If it was Bob
                40 -> playerl(FacialExpression.FRIENDLY, "I have proof that Bob is lying about the poison.").also { stage++ }
                41 -> npcl(FacialExpression.FRIENDLY, "Oh really? How did you get that?").also { stage++ }
                42 -> interpreter!!.sendDialogue("You tell the guard about the beehive.").also { stage = 55 }
                // If it was Carol
                43 -> playerl(FacialExpression.FRIENDLY, "I have proof that Carol is lying about the poison.").also { stage++ }
                44 -> npcl(FacialExpression.FRIENDLY, "Oh really? How did you get that?").also { stage++ }
                45 -> interpreter!!.sendDialogue("You tell the guard about the drain.").also { stage = 55 }
                // If it was David
                46 -> playerl(FacialExpression.FRIENDLY, "I have proof that David is lying about the poison.").also { stage++ }
                47 -> npcl(FacialExpression.FRIENDLY, "Oh really? How did you get that?").also { stage++ }
                48 -> interpreter!!.sendDialogue("You tell the guard about the spiders nest.").also { stage = 55 }
                // If it was Elizabeth
                49 -> playerl(FacialExpression.FRIENDLY, "I have proof that Elizabeth is lying about the poison.").also { stage++ }
                50 -> npcl(FacialExpression.FRIENDLY, "Oh really? How did you get that?").also { stage++ }
                51 -> interpreter!!.sendDialogue("You tell the guard about the mosquitos at the fountain.").also { stage = 55 }
                // If it was Frank
                52 -> playerl(FacialExpression.FRIENDLY, "I have proof that Frank is lying about the poison.").also { stage++ }
                53 -> npcl(FacialExpression.FRIENDLY, "Oh really? How did you get that?").also { stage++ }
                54 -> interpreter!!.sendDialogue("You tell the guard about the tarnished family crest.").also { stage = 55 }
                55 -> npcl(FacialExpression.FRIENDLY, "Hmm. That's some good detective work there, we need more evidence before we can close the case though. Keep up the good work.").also { stage = END_DIALOGUE }
                56 -> {
                    if (getAttribute(player!!, MysteryUtils.ATTRIBUTE_ANNA, false)) {
                        playerl(FacialExpression.FRIENDLY, "I have the fingerprints of the culprit! I have Anna's fingerprint's here, you can see for yourself they match the fingerprints on the murder weapon exactly.").also { stage++ }
                    } else if (getAttribute(player!!, MysteryUtils.ATTRIBUTE_DAVID, false)) {
                        playerl(FacialExpression.FRIENDLY, "I have the fingerprints of the culprit! I have David fingerprint's here, you can see for yourself they match the fingerprints on the murder weapon exactly.").also { stage++ }
                    } else if (getAttribute(player!!, MysteryUtils.ATTRIBUTE_ELIZABETH, false)) {
                        playerl(FacialExpression.FRIENDLY, "I have the fingerprints of the culprit! I have Elizabeth fingerprint's here, you can see for yourself they match the fingerprints on the murder weapon exactly.").also { stage++ }
                    }
                }
                57 -> sendDialogue(player!!, "You show the guard the finger prints evidence.").also { stage++ }
                58 -> npcl(FacialExpression.FRIENDLY, "...").also { stage++ }
                59 -> npcl(FacialExpression.FRIENDLY, "I'm impressed. How on earth did you think of something like that? I've never heard of such a technique for finding criminals before, this will come in very handy in the future!").also { stage++ }
                60 -> npcl(FacialExpression.FRIENDLY, "But we can't arrest someone on just this. I'm afraid you'll still need to find more evidence before we can close this case completely.").also { stage++ }
                61 -> playerl(FacialExpression.FRIENDLY, "I have conclusive proof who the killer was.").also { stage++ }
                62 -> npcl(FacialExpression.FRIENDLY, "You do? That's excellent work. Let's hear it then.").also { stage++ }
                63 -> playerl(FacialExpression.FRIENDLY, "I don't think it was an intruder, and I don't think Lord Sinclair was killed by being stabbed.").also { stage++ }
                64 -> npcl(FacialExpression.FRIENDLY, "Hmmm? Really? Why not?").also { stage++ }
                65 -> playerl(FacialExpression.FRIENDLY, "Nobody heard the guard dog barking, which it would have if it had been an intruder who was responsible.").also { stage++ }
                66 -> playerl(FacialExpression.FRIENDLY, "Nobody heard any signs of a struggle either. I think the knife was there to throw suspicion away from the real culprit.").also { stage++ }
                67 -> npcl(FacialExpression.FRIENDLY, "Yes, that makes sense. But who did do it then?").also { stage++ }
                68 -> {
                    if (getAttribute(player!!, MysteryUtils.ATTRIBUTE_ANNA, false)) {
                        sendDialogue(player!!, "You prove to the guard the thread matches Anna's clothes.").also { stage++ }
                    } else if (getAttribute(player!!, MysteryUtils.ATTRIBUTE_DAVID, false)) {
                        sendDialogue(player!!, "You prove to the guard the thread matches David's clothes.").also { stage++ }
                    } else if (getAttribute(player!!, MysteryUtils.ATTRIBUTE_ELIZABETH, false)) {
                        sendDialogue(player!!, "You prove to the guard the thread matches Elizabeth clothes.").also { stage++ }
                    }
                }
                69 -> npcl(FacialExpression.FRIENDLY, "Yes, I'd have to agree with that... but we need more evidence!").also { stage++ }
                70 -> {
                    if (getAttribute(player!!, MysteryUtils.ATTRIBUTE_ANNA, false)) { // Anna & Bob
                        sendDialogue(player!!, "You prove to the guard, that Bob did not use the poison on the beehive.").also { stage++ }
                    } else if (getAttribute(player!!,  MysteryUtils.ATTRIBUTE_DAVID, false)) { // David & Frank
                        sendDialogue(player!!, "You prove to the guard, that Frank did not use the poison on the family crest.").also { stage++ }
                    } else if (getAttribute(player!!, MysteryUtils.ATTRIBUTE_ELIZABETH, false)) { // Elizabeth & Carol
                        sendDialogue(player!!, "You prove to the guard, that Carol did not use the poison on the drain.").also { stage++ }
                    }
                }
                71 -> npcl(FacialExpression.FRIENDLY, "Excellent work – have you considered a career as a detective? But I'm afraid it's still not quite enough...").also { stage++ }
                72 -> {
                    if (getAttribute(player!!, MysteryUtils.ATTRIBUTE_ANNA, false)) {
                        sendDialogue(player!!, "You match Anna's fingerprint's with those on the dagger found in the body of Lord Sinclair.").also { stage++ }
                    } else if (getAttribute(player!!, MysteryUtils.ATTRIBUTE_DAVID, false)) {
                        sendDialogue(player!!, "You match David's fingerprint's with those on the dagger found in the body of Lord Sinclair.").also { stage++ }
                    } else if (getAttribute(player!!, MysteryUtils.ATTRIBUTE_ELIZABETH, false)) {
                        sendDialogue(player!!, "You match Elizabeth fingerprint's with those on the dagger found in the body of Lord Sinclair.").also { stage++ }
                    }
                }
                // TODO: COMPLETE DIALOGUE
                73 -> npcl(FacialExpression.FRIENDLY, "Yes. There's no doubt about it. It must have been [suspect] who killed [his/her] father. All of the guards must congratulate you on your excellent work in helping us to solve this case.We don't have many murders here in " + GameWorld.settings!!.name + " and I'm afraid we wouldn't have been able to solve it by ourselves. We will hold [him/her] here under house arrest until such time as we bring [him/her] to trial.").also { stage++ }
                74 -> npcl(FacialExpression.FRIENDLY, "ou have our gratitude, and I'm sure the rest of the family's as well, in helping to apprehend the murderer. I'll just take the evidence from you now.").also { stage++ }
                75 -> sendDialogue(player!!, "You hand over all the evidence.").also { stage++ }
                76 -> npcl(FacialExpression.FRIENDLY, "Please accept this reward from the family!").also { stage++ }
                77 -> {
                    end()
                    finishQuest(player, "Murder Mystery")
                    updateQuestTab(player)
                }
            }

            100 -> when (stage) {
                0 -> npcl(FacialExpression.FRIENDLY, "Excellent work on solving the murder! All of the guards I know are very impressed, and don't worry, we have the murderer under guard until they can be taken to trial.").also { stage++ }
                1 -> playerl(FacialExpression.FRIENDLY, "Is there anything else I can do? Seems awfully quiet up at the house, considering their sibling has just been arrested.").also { stage++ }
                2 -> npcl(FacialExpression.FRIENDLY, "Is there anything else I can do? Seems awfully quiet up at the house, considering their sibling has just been arrested.").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GUARD_812)
    }
}
