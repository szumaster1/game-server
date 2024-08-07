package content.region.kandarin.quest.biohazard.dialogue

import content.region.kandarin.quest.biohazard.util.BiohazardUtils
import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.dialogue.Topic
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE

/**
 * Elena dialogue file
 *
 * @constructor Elena dialogue file
 */
class ElenaDialogueFile : DialogueFile() {
    override fun handle(componentID: Int, buttonID: Int) {
        val questStage = getQuestStage(player!!, "Biohazard")
        npc = NPC(NPCs.ELENA_3209)
        when {
            (questStage == 0) -> {
                when (stage) {
                    0 -> player("Good day to you, Elena.").also { stage++ }
                    1 -> npc("You too, thanks for freeing me.").also { stage++ }
                    2 -> npc("It's just a shame the mourners confiscated my", "equipment.").also { stage++ }
                    3 -> player("What did they take?").also { stage++ }
                    4 -> npc(
                        "My distillator, I can't test any plague samples without it.",
                        "They're holding it in the mourner quarters in West",
                        "Ardougne."
                    ).also { stage++ }

                    5 -> npc(
                        "I must somehow retrieve that distillator if I am to find",
                        "a cure for this awful affliction."
                    ).also { stage++ }

                    6 -> options("I'll try to retrieve it for you.", "Well, good luck.").also { stage++ }
                    7 -> when (buttonID) {
                        1 -> player("I'll try to retrieve it for you.").also { stage = 9 }
                        2 -> player("Well, good luck.").also { stage++ }
                    }

                    8 -> npc("Thanks traveller.").also { stage = END_DIALOGUE }
                    9 -> npc(
                        "I was hoping you would say that. Unfortunately they",
                        "discovered the tunnel and filled it in. We need another",
                        "way over the wall."
                    ).also { stage++ }

                    10 -> player("Any ideas?").also { stage++ }
                    11 -> npc(
                        "My father's friend Jerico is in communication with",
                        "West Ardougne. He might be able to help us, he lives",
                        "next to the chapel."
                    ).also { stage++ }

                    12 -> {
                        end()
                        setQuestStage(player!!, "Biohazard", 1)
                    }
                }
            }

            (questStage == 1) -> {
                when (stage) {
                    0 -> player("Hello Elena.").also { stage++ }
                    1 -> npc("Hello brave adventurer. Any luck finding my distillator?").also { stage++ }
                    2 -> player("No, I'm afraid not.").also { stage++ }
                    3 -> npc(
                        "Speak to Jerico, he will help you to cross the",
                        "wall. He lives next to the chapel."
                    ).also { stage++ }

                    4 -> end()
                }
            }

            (questStage == 4) -> {
                when (stage) {
                    0 -> player("I've distracted the guards at the watch tower.").also { stage++ }
                    1 -> npc(
                        "Yes, I saw. Quickly meet with Jerico's friends",
                        "and cross the wall before the pigeons fly off."
                    ).also { stage++ }

                    2 -> end()
                }
            }

            (questStage in 5..7) -> {
                when (stage) {
                    0 -> player("Hello again.").also { stage++ }
                    1 -> npc("You're back, did you find the distillator?").also { stage++ }
                    2 -> player("I'm afraid not.").also { stage++ }
                    3 -> npc("I can't test the samples without the distillator.").also { stage++ }
                    4 -> npc("Please don't give up until you find it.").also { stage++ }
                    5 -> end()
                }
            }

            (questStage in 10..15) -> {
                when (stage) {
                    0 -> npc("So, have you managed to retrieve my distillator?").also { stage++ }
                    1 -> {
                        if (!inInventory(player!!, Items.DISTILLATOR_420)) {
                            player("I'm afraid not.").also { stage++ }
                        } else {
                            player("Yes, here it is!").also { stage = 5 }
                        }
                    }

                    2 -> npc("I can't test the samples without the distillator.").also { stage++ }
                    3 -> npc("Please don't give up until you find it.").also { stage++ }
                    4 -> end()

                    5 -> {
                        npc("You have? That's great! Now can you pass me those", "reaction agents please?")
                        sendMessage(player!!, "You hand Elena the distillator and an assortment of vials.")
                        stage++
                    }

                    6 -> player("Those look pretty fancy.").also { stage++ }
                    7 -> npc(
                        "Well, yes and no. The liquid honey isn't worth much, ",
                        "but the others are. Especially this colourless ethenea. Be",
                        "careful with the sulphuric broline, it's highly poisonous."
                    ).also { stage++ }

                    8 -> player("You're not kidding, I can smell it from here!").also { stage++ }
                    9 -> {
                        npc("I don't understand... the touch paper hasn't changed", "colour at all...")
                        sendMessage(player!!, "Elena puts the agents through the distillator.")
                        stage++
                    }

                    10 -> npc(
                        "You'll need to go and see my old mentor Guidor. He",
                        "lives in Varrock. Take these vials and this sample to",
                        "him."
                    ).also { stage++ }

                    11 -> npc(
                        "But first you'll need some more touch paper. Go and",
                        "see the chemist in Rimmington."
                    ).also { stage++ }

                    12 -> npc("Just don't get into any fights, and be careful who", "you speak to.").also { stage++ }
                    13 -> npc(
                        "Those vials are fragile, and plague carriers don't",
                        "tend to be too popular."
                    ).also { stage++ }

                    14 -> {
                        end()
                        if (removeItem(player!!, Items.DISTILLATOR_420)) {
                            addItemOrDrop(player!!, Items.LIQUID_HONEY_416)
                            addItemOrDrop(player!!, Items.ETHENEA_415)
                            addItemOrDrop(player!!, Items.SULPHURIC_BROLINE_417)
                            addItemOrDrop(player!!, Items.PLAGUE_SAMPLE_418)
                            sendMessage(player!!, "Elena gives you three vials and a sample in a tin container.")
                            setAttribute(player!!, BiohazardUtils.ELENA_REPLACE, true)
                        }
                    }
                }
            }

            getAttribute(player!!, BiohazardUtils.ELENA_REPLACE, false) -> {
                when (stage) {
                    0 -> npcl(FacialExpression.HALF_ASKING, "What are you doing back here?").also { stage++ }
                    1 -> showTopics(
                        Topic(FacialExpression.HALF_GUILTY, "I just find it hard to say goodbye sometimes.", 100),
                        Topic(
                            FacialExpression.GUILTY,
                            "I'm afraid i've lost some of the stuff that you gave me...",
                            200
                        ),
                        Topic(FacialExpression.HALF_ASKING, "I've forgotten what I need to do.", 300)
                    )

                    100 -> npc(
                        FacialExpression.HALF_GUILTY,
                        "Yes... I have feelings for you too...",
                        "Now get to work!"
                    ).also { stage = END_DIALOGUE }

                    200 -> {
                        if (!inInventory(player!!, Items.LIQUID_HONEY_416) || !inInventory(
                                player!!,
                                Items.ETHENEA_415
                            ) || !inInventory(player!!, Items.PLAGUE_SAMPLE_418) || !inInventory(
                                player!!,
                                Items.SULPHURIC_BROLINE_417
                            )
                        ) {
                            stage++
                        } else {
                            npcl(
                                FacialExpression.HALF_THINKING,
                                "Are you sure? Looks like you have everything to me."
                            ).also { stage = END_DIALOGUE }
                        }
                    }

                    201 -> if (freeSlots(player!!) < 4) {
                        sendDialogue(
                            player!!,
                            "Elena tries to give you some items but you don't have enough room for them."
                        ).also { stage = END_DIALOGUE }
                    } else {
                        sendDialogue(player!!, "Elena replaces your items.")
                        if (!inInventory(player!!, Items.LIQUID_HONEY_416))
                            addItem(player!!, Items.LIQUID_HONEY_416)
                        if (!inInventory(player!!, Items.ETHENEA_415))
                            addItem(player!!, Items.ETHENEA_415)
                        if (!inInventory(player!!, Items.SULPHURIC_BROLINE_417))
                            addItem(player!!, Items.SULPHURIC_BROLINE_417)
                        if (!inInventory(player!!, Items.PLAGUE_SAMPLE_418))
                            addItem(player!!, Items.PLAGUE_SAMPLE_418)
                        stage++
                    }

                    202 -> npc(
                        FacialExpression.HALF_THINKING,
                        "Ok, so that's the colourless ethenea...",
                        "Some highly toxic sulphuric broline...",
                        "And some bog-standard liquid honey..."
                    ).also { stage++ }

                    203 -> playerl(FacialExpression.FRIENDLY, "Great. I'll be on my way.").also {
                        //removeAttributes(player!!, BiohazardUtils.FIRST_VIAL_WRONG,BiohazardUtils.SECOND_VIAL_WRONG,BiohazardUtils.THIRD_VIAL_WRONG)
                        stage = END_DIALOGUE
                    }

                    300 -> npcl(
                        FacialExpression.HALF_THINKING,
                        "Go to Rimmington and get some touch paper from the chemist. Use his errand boys to smuggle the vials into Varrock, then go to Varrock and take the sample to Guidor, my old mentor."
                    ).also { stage++ }

                    301 -> playerl(FacialExpression.FRIENDLY, "Ok, I'll get to it.").also { stage = END_DIALOGUE }
                }
            }

            (questStage in 16..98) -> {
                when (stage) {
                    0 -> npc("You're back! So what did Guidor say?").also { stage++ }
                    1 -> player("Nothing.").also { stage++ }
                    2 -> npc("What?").also { stage++ }
                    3 -> player("He said that there is no plague.").also { stage++ }
                    4 -> npc("So what, this thing has all been a big hoax?").also { stage++ }
                    5 -> player("Or maybe we're about to uncover something huge.").also { stage++ }
                    6 -> npc("Then I think this thing may be bigger than both", "of us.").also { stage++ }
                    7 -> player("What do you mean?").also { stage++ }
                    8 -> npc("I mean you need to go right to the top...").also { stage++ }
                    9 -> npc("You need to see the King of East Ardougne!").also { stage++ }
                    10 -> {
                        end()
                        setQuestStage(player!!, "Biohazard", 99)
                    }
                }
            }

            (questStage == 99) -> {
                when (stage) {
                    0 -> playerl(FacialExpression.FRIENDLY, "Hello Elena.").also { stage++ }
                    1 -> npcl(FacialExpression.ANNOYED, "You must go to King Lathas immediately!").also { stage++ }
                    2 -> end()
                }
            }

            (questStage == 100) -> {
                when (stage) {
                    0 -> player("Hello Elena.").also { stage++ }
                    1 -> npc("Hey, how are you?").also { stage++ }
                    2 -> player("Good thanks, yourself?").also { stage++ }
                    3 -> npc("Not bad, let me know when you hear from", "King Lathas again.").also { stage++ }
                    4 -> player("Will do.").also { stage++ }
                    5 -> end()
                }
            }
        }
    }
}

