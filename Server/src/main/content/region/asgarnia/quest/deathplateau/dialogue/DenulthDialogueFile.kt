package content.region.asgarnia.quest.deathplateau.dialogue

import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE

/**
 * Represents the Denulth dialogue file.
 */
class DenulthDialogueFile : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.DENULTH_1060)
        when (getQuestStage(player!!, "Death Plateau")) {
            in 0..4 -> when (stage) {
                0 -> playerl(FacialExpression.FRIENDLY, "Hello!").also { stage++ }
                1 -> npcl(FacialExpression.FRIENDLY, "Hello citizen, how can I help you?").also { stage++ }
                2 -> options("Do you have any quests for me?", "What is this place?", "You can't, thanks.").also { stage++ }

                3 -> when (buttonID) {
                    1 -> playerl(FacialExpression.HALF_ASKING, "Do you have any quests for me?").also { stage = 5 }
                    2 -> playerl(FacialExpression.ASKING, "What is this place?").also { stage = 4 }
                    3 -> playerl(FacialExpression.NEUTRAL, "You can't, thanks.").also { stage = END_DIALOGUE }
                }
                4 -> npcl(FacialExpression.HAPPY, "Welcome to the Principality of Burthorpe! We are the Imperial Guard for his Royal Highness Prince Anlaf of Burthorpe. Can I assist you with anything else?").also { stage = 2 }
                5 -> npcl(FacialExpression.FRIENDLY, "I don't know if you can help us!").also { stage++ }
                6 -> npcl(FacialExpression.FRIENDLY, "The trolls have taken up camp on the Death Plateau! They are using it to launch raids at night on the village. We have tried to attack the camp, but the main path is heavily guarded!").also { stage++ }
                7 -> playerl(FacialExpression.ASKING, "Perhaps there is a way you can sneak up at night?").also { stage++ }
                8 -> npcl(FacialExpression.FRIENDLY, "If there is another way, I do not know of it.").also { stage++ }
                9 -> npcl(FacialExpression.FRIENDLY, "Do you know of such a path?").also { stage++ }
                10 -> options("No, but perhaps I could try and find one?", "No, sorry.").also { stage++ }
                11 -> when (buttonID) {
                    1 -> playerl(FacialExpression.HALF_ASKING, "No, but perhaps I could try and find one?").also { stage++ }
                    2 -> playerl(FacialExpression.NEUTRAL, "No, sorry.").also { stage = END_DIALOGUE }
                }
                12 -> npc(FacialExpression.FRIENDLY, "Citizen you would be well rewarded!").also { stage++ }
                13 -> npcl(FacialExpression.FRIENDLY, "If you go up to Death Plateau, be very careful as the trolls will attack you on sight!").also { stage++ }
                14 -> playerl(FacialExpression.FRIENDLY, "I'll be careful.").also { stage++ }
                15 -> npcl(FacialExpression.FRIENDLY, "One other thing.").also { stage++ }
                16 -> playerl(FacialExpression.FRIENDLY, "What's that?").also { stage++ }
                17 -> npcl(FacialExpression.FRIENDLY, "All of our equipment is kept in the castle on the hill.").also { stage++ }
                18 -> npcl(FacialExpression.FRIENDLY, "The stupid guard that was on duty last night lost the combination to the lock! I told the Prince that the Imperial Guard should've been in charge of security!").also { stage++ }
                19 -> playerl(FacialExpression.ASKING, "No problem, what does the combination look like?").also { stage++ }
                20 -> npcl(FacialExpression.FRIENDLY, "The equipment room is unlocked when the stone balls are placed in the correct order on the stone mechanism outside it. The right order is written on a piece of paper the guard had.").also { stage++ }
                21 -> playerl(FacialExpression.FRIENDLY, "A stone what...?!").also { stage++ }
                22 -> npcl(FacialExpression.FRIENDLY, "Well citizen, the Prince is fond of puzzles. Why we couldn't just have a key is beyond me!").also { stage++ }
                23 -> playerl(FacialExpression.SUSPICIOUS, "I'll get on it right away!").also { stage++ }
                24 -> {
                    end()
                    setQuestStage(player!!, "Death Plateau", 5)
                }
            }

            in 5..21 -> when (stage) {
                0 -> npcl(FacialExpression.HAPPY, "Hello citizen, is there anything you'd like to know?").also { stage++ }
                1 -> options("Can you remind me of the quest I am on?", "I thought the White Knights controlled Asgarnia?", "What is this place?", "That's all, thanks.").also { stage++ }
                2 -> when (buttonID) {
                    1 -> playerl(FacialExpression.HALF_ASKING, "Can you remind me of the quest I am on?").also {
                        stage = 5
                    }
                    2 -> playerl(FacialExpression.NEUTRAL, "I thought the White Knights controlled Asgarnia?").also { stage = 11 }
                    3 -> playerl(FacialExpression.ASKING, "What is this place?").also { stage = 4 }
                    4 -> playerl(FacialExpression.HAPPY, "That's all, thanks.").also { stage++ }
                }

                3 -> npcl(FacialExpression.HAPPY, "God speed citizen.").also { stage = END_DIALOGUE }
                4 -> npcl(FacialExpression.HAPPY, "Welcome to the Principality of Burthorpe! We are the Imperial Guard for his Royal Highness Prince Anlaf of Burthorpe. Can I assist you with anything else?").also { stage = 1 }
                5 -> npcl(FacialExpression.HALF_THINKING, "You offered to see if you could find another way up Death Plateau. We could then use it to sneak up and attack the trolls by night.").also { stage++ }
                6 -> playerl(FacialExpression.THINKING, "Ah yes, and the guard had lost the combination to your equipment rooms in the castle on the hill?").also { stage++ }
                7 -> npcl(FacialExpression.HAPPY, "That's right citizen, you offered to recover the combination and unlock the door.").also { stage++ }
                8 -> playerl(FacialExpression.FRIENDLY, "I'll get on it right away!").also { stage++ }
                9 -> npcl(FacialExpression.FRIENDLY, "Good work citizen!").also { stage++ }
                10 -> npcl(FacialExpression.ASKING, "Is there anything else you would like to know?").also { stage = 1 }
                11 -> npcl(FacialExpression.ANGRY, "You are right citizen. The White Knights have taken advantage of the old and weak king, they control most of Asgarnia, including Falador. However they do not control Burthorpe!").also { stage++ }
                12 -> npcl(FacialExpression.EVIL_LAUGH, "We are the prince's elite troops! We keep Burthorpe secure!").also { stage++ }
                13 -> npcl(FacialExpression.ANGRY, "The White Knights have overlooked us until now! They are pouring money into their war against the Black Knights, They are looking for an excuse to stop our funding and I'm afraid they may have found it!").also { stage++ }
                14 -> npcl(FacialExpression.HALF_WORRIED, "If we can not destroy the troll camp on Death Plateau then the Imperial Guard will be disbanded and Burthorpe will come under control of the White Knights. We cannot let this happen!").also { stage++ }
                15 -> npcl(FacialExpression.ASKING, "Is there anything else you'd like to know?").also { stage = 1 }
            }

            22 -> when (stage) {
                0 -> playerl(FacialExpression.FRIENDLY, "Hello!").also { stage++ }
                1 -> npcl(FacialExpression.FRIENDLY, "Hello citizen, have you found another way up Death Plateau?").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "Yes there is another way up Death Plateau!").also { stage++ }
                3 -> npcl(FacialExpression.FRIENDLY, "We are saved!").also { stage++ }
                4 -> playerl(FacialExpression.FRIENDLY, "There's one thing...").also { stage++ }
                5 -> npcl(FacialExpression.FRIENDLY, "And what is that citizen?").also { stage++ }
                6 -> playerl(FacialExpression.FRIENDLY, "There is a Sherpa who will only show me the secret way if I first get some spikes for his climbing boots. The smith will only do this for me if you sign up his son for the Imperial Guard!").also { stage++ }
                7 -> npcl(FacialExpression.FRIENDLY, "Hmm...this is very irregular.").also { stage++ }
                8 -> playerl(FacialExpression.FRIENDLY, "Will you not do this?").also { stage++ }
                9 -> npcl(FacialExpression.FRIENDLY, "I have heard of Dunstan's son, he is a very promising young man. For the sake of your mission we can make an exception!").also { stage++ }
                10 -> sendItemDialogue(player!!, Items.CERTIFICATE_3114, "Denulth has given you a certificate.").also {
                    addItemOrDrop(player!!, Items.CERTIFICATE_3114)
                    stage++
                }
                11 -> npcl(FacialExpression.FRIENDLY, "This certificate proves that we have accepted Dunstan's son for training in the Imperial Guard!").also { stage++ }
                12 -> playerl(FacialExpression.FRIENDLY, "Thank you Denulth, I shall be back shortly!").also { stage++ }
                13 -> {
                    end()
                    setQuestStage(player!!, "Death Plateau", 23)
                }
            }

            23 -> when (stage) {
                0 -> playerl(FacialExpression.FRIENDLY, "Hello!").also { stage++ }
                1 -> npcl(FacialExpression.FRIENDLY, "Hello citizen, have you found the secret way up Death Plateau?").also { stage++ }
                2 -> if (!inInventory(player!!, Items.CERTIFICATE_3114)) {
                    playerl(FacialExpression.FRIENDLY, "I'm working on it but I've lost the certificate!").also { stage = 3 }
                } else {
                    playerl(FacialExpression.FRIENDLY, "I'm working on it, I just need to give the certificate to Dunstan.").also { stage = END_DIALOGUE }
                }
                3 -> npcl(FacialExpression.FRIENDLY, "No problem, I have a duplicate.").also { stage++ }
                4 -> {
                    end()
                    sendItemDialogue(player!!, Items.CERTIFICATE_3114, "Denulth has given you a certificate.")
                    addItemOrDrop(player!!, Items.CERTIFICATE_3114)
                }
            }

            in 24..26 -> when (stage) {
                0 -> playerl(FacialExpression.FRIENDLY, "Hello!").also { stage++ }
                1 -> npcl(FacialExpression.FRIENDLY, "Hello citizen, have you found the secret way up Death Plateau?").also { stage++ }

                2 -> if (inInventory(player!!, Items.SECRET_WAY_MAP_3104)) {
                    playerl(FacialExpression.FRIENDLY, "Yes! There is a path that runs from a Sherpa's hut around the back of Death Plateau. The trolls haven't found it yet. The Sherpa made a map I can give you.").also { stage++ }
                } else {
                    playerl(FacialExpression.FRIENDLY, "I don't have the map on me..").also { stage = END_DIALOGUE }
                }
                3 -> sendItemDialogue(player!!, Items.SECRET_WAY_MAP_3104, "You give Denulth the map of the secret way.").also {
                    removeItem(player!!, Items.SECRET_WAY_MAP_3104)
                    sendMessage(player!!, "You give a Denulth the map of the secret way.")
                    stage++
                }
                4 -> npcl(FacialExpression.FRIENDLY, "Excellent, this looks perfect. They will never see us coming.").also { stage++ }
                5 -> npcl(FacialExpression.FRIENDLY, "Have you managed to open the equipment room?").also { stage++ }
                6 -> {
                    if (inInventory(player!!, Items.COMBINATION_3102)) {
                        playerl(FacialExpression.FRIENDLY, "Yes! The door is open and here is the combination.").also { stage++ }
                    } else {
                        playerl(FacialExpression.FRIENDLY, "I have opened the door but I don't have the combination on me.").also { stage = END_DIALOGUE }
                    }
                }
                7 -> sendItemDialogue(player!!, Items.COMBINATION_3102, "You give Denulth the combination to the equipment room.").also {
                    removeItem(player!!, Items.COMBINATION_3102)
                    sendMessage(player!!, "You give Denulth the combination to the equipment room.")
                    stage++
                }
                8 -> npcl(FacialExpression.FRIENDLY, "Well done citizen! We will reward you by training you in attack!").also { stage++ }
                9 -> npcl(FacialExpression.FRIENDLY, "I shall present you with some steel fighting claws. In addition I shall show you the knowledge of creating the fighting claws for yourself.").also { stage++ }
                10 -> npcl(FacialExpression.FRIENDLY, "You are now an honorary member of the Imperial Guard!").also { stage++ }
                11 -> {
                    end()
                    finishQuest(player!!, "Death Plateau")
                }
            }
        }
    }

}
