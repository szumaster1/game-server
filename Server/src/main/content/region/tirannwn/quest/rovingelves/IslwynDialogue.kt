package content.region.tirannwn.quest.rovingelves

import core.game.dialogue.Dialogue
import core.game.dialogue.DialogueInterpreter
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.game.node.item.GroundItemManager
import core.game.node.item.Item

class IslwynDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        val quest = player.getQuestRepository().getQuest("Roving Elves")
        val waterfall = player.getQuestRepository().getQuest("Waterfall")
        if (quest.getStage(player) == 0 && waterfall.isCompleted(player)) {
            player(FacialExpression.HALF_GUILTY, "Hello there.")
            stage = 0
        }
        if (!waterfall.isCompleted(player)) {
            player(FacialExpression.HALF_GUILTY, "Hello there.")
            stage = 1000
        }
        if (quest.isStarted(player) && quest.getStage(player) >= 10) {
            player(FacialExpression.HALF_GUILTY, "Hello Islwyn.")
            stage = 0
        }
        if (quest.isCompleted(player) || quest.getStage(player) >= 100) {
            player(FacialExpression.HALF_GUILTY, "Hello Islwyn, I'm back.")
            stage = 31
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        val quest = player.getQuestRepository().getQuest("Roving Elves")
        when (stage) {
            500 -> end()
            1000 -> {
                interpreter.sendDialogues(
                    1680,
                    FacialExpression.HALF_GUILTY,
                    "Hello there, it's a lovely day for a walk in the woods.",
                    "So what can I help you with?"
                )
                stage = 1001
            }

            1001 -> {
                player(FacialExpression.HALF_GUILTY, "I'm just looking around.")
                stage = 500
            }

            2000 -> {
                if (quest.getStage(player) == 15) {
                    player(FacialExpression.HALF_GUILTY,
                        "Yes I have! She explained that I have to visit",
                        "Glarial's old tomb and obtain a consecration seed",
                        "from the temple guardian in there."
                    )
                    stage = 2001
                }
                if (quest.getStage(player) != 15) {
                    player(FacialExpression.HALF_GUILTY,
                        "Not yet, I'll be back when I have."
                    )
                    stage = 500
                }
            }

            2001 -> {
                interpreter.sendDialogues(
                    1680,
                    FacialExpression.HALF_GUILTY,
                    "Good luck against the guardian, adventurer.",
                    "Do it in the name of my grandmother Glarial."
                )
                stage = 500
            }

            0 -> {
                if (quest.getStage(player) == 10 || quest.getStage(player) == 15) {
                    interpreter.sendDialogues(1680, FacialExpression.HALF_GUILTY, "Have you spoken to Eluned yet?")
                    stage = 2000
                }
                if (quest.getStage(player) == 20) {
                    interpreter.sendDialogues(
                        1680,
                        FacialExpression.HALF_GUILTY,
                        "You have returned! Thank you for all you have done.",
                        "Now both me and my grandmother can rest in peace."
                    )
                    stage = 19
                } else if (quest.getStage(player) != 10 && quest.getStage(player) != 15) {
                    interpreter.sendDialogues(
                        1680,
                        FacialExpression.HALF_GUILTY,
                        "Leave me be, I have no time for easterners. Between",
                        "your lot and them gnomes, all you do is take and",
                        "destroy. No thought for others."
                    )
                    stage = 1
                }
            }

            1 -> {
                player(FacialExpression.HALF_GUILTY, "...but...")
                stage = 2
            }

            2 -> {
                interpreter.sendDialogues(
                    1680,
                    FacialExpression.HALF_GUILTY,
                    "Save your excuses young one! It was one of your",
                    "species that disturbed my grandmother's remains. Will",
                    "she never get the peace she deserves?"
                )
                stage = 3
            }

            3 -> {
                player(FacialExpression.HALF_GUILTY, "Grandmother?")
                stage = 4
            }

            4 -> {
                interpreter.sendDialogues(
                    1680,
                    FacialExpression.HALF_GUILTY,
                    "Yes! Someone took her ashes from her tomb. If it",
                    "wasn't for them gnomes she'd have been left in peace.",
                    "But now I can sense her restlessness."
                )
                stage = 5
            }

            5 -> {
                player(FacialExpression.HALF_GUILTY, "Gnomes?")
                stage = 6
            }

            6 -> {
                interpreter.sendDialogues(
                    1680,
                    FacialExpression.HALF_GUILTY,
                    "Yes gnomes! One of those little pests took the key to",
                    "my grandmother's tomb. He must've given it to the",
                    "human that desecrated the tomb."
                )
                stage = 7
            }

            7 -> {
                player(FacialExpression.HALF_GUILTY, "Was your grandmother's name Glarial?")
                stage = 8
            }

            8 -> {
                interpreter.sendDialogues(1680, FacialExpression.HALF_GUILTY, "Yes... How did you know that?")
                stage = 9
            }

            9 -> {
                interpreter.sendOptions("Do you want to;", "Tell the truth?", "Lie?", "Leave the old elf be?")
                stage = 10
            }

            10 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY,
                        "It's a bit of a long tale, but to cut the story short, her",
                        "remains reside in Baxtorian's home. I thought it's where",
                        "she'd want to be. It was I that removed your",
                        "grandmother's ashes."
                    )
                    stage = 11
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY,
                        "I just guessed.",
                        "Well, now that that's over, I really need to be",
                        "going."
                    )
                    stage = 500
                }

                3 -> {
                    player(FacialExpression.HALF_GUILTY,
                        "On second thought, I really should be going."
                    )
                    stage = 500
                }
            }

            11 -> {
                interpreter.sendDialogues(
                    1680,
                    FacialExpression.HALF_GUILTY,
                    "You've been in grandfather's home? That's where we",
                    "originally wanted to leave Glarial's ashes to rest, but we",
                    "could not understand how to enter."
                )
                stage = 12
            }

            12 -> {
                interpreter.sendDialogues(
                    1680,
                    FacialExpression.HALF_GUILTY,
                    "This is gravely concerning... Her resting place must be",
                    "consecrated."
                )
                stage = 13
            }

            13 -> {
                interpreter.sendOptions(
                    "Select an Option",
                    "Maybe I could help.",
                    "Sounds like you've got a lot to do."
                )
                stage = 14
            }

            14 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY,
                        "Maybe I could help. What needs doing to consecrate",
                        "her new tomb?"
                    )
                    stage = 15
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY,
                        "Sounds like you've got a lot to do."
                    )
                    stage = 500
                }
            }

            15 -> {
                interpreter.sendDialogues(
                    1680,
                    FacialExpression.HALF_GUILTY,
                    "Are you offering to help?!? Maybe not all humans are",
                    "as bad as I thought."
                )
                stage = 16
            }

            16 -> {
                interpreter.sendDialogues(
                    1680,
                    FacialExpression.HALF_GUILTY,
                    "I don't know the consecration process. You should speak",
                    "with Eluned... she is wise in the ways of the ritual."
                )
                stage = 17
            }

            17 -> {
                player(FacialExpression.HALF_GUILTY, "I'll see what I can do.")
                stage = 18
            }

            18 -> {
                end()
                quest.start(player)
            }

            19 -> {
                player(FacialExpression.HALF_GUILTY,
                    "How did you know that I have consecrated the tomb?"
                )
                stage = 20
            }

            20 -> {
                interpreter.sendDialogues(
                    1680,
                    FacialExpression.HALF_GUILTY,
                    "Her restlessness has finally left me. Here - I should",
                    "give you something for your effort."
                )
                stage = 21
            }

            21 -> {
                interpreter.sendDoubleItemMessage(
                    RovingElves.CRYSTAL_BOW_FULL,
                    RovingElves.CRYSTAL_SHIELD_FULL,
                    "Islwyn shows you a crystal bow and a crystal shield."
                )
                stage = 22
            }

            22 -> {
                interpreter.sendDialogues(
                    1680,
                    FacialExpression.HALF_GUILTY,
                    "Crystal equipment is at its best when new and",
                    "previously unused. The bow does not require",
                    "ammunition and reduces in strength the more it's fired.",
                    "The shield decreases in defensive capabilities the more"
                )
                stage = 23
            }

            23 -> {
                interpreter.sendDialogues(
                    1680,
                    FacialExpression.HALF_GUILTY,
                    "it's hit. Both the shield and the bow I am carrying only",
                    "have 500 uses before they revert to seed."
                )
                stage = 24
            }

            24 -> {
                player(FacialExpression.HALF_GUILTY, "Revert to seed? What do you mean?")
                stage = 25
            }

            25 -> {
                interpreter.sendDialogues(
                    1680,
                    FacialExpression.HALF_GUILTY,
                    "Ahhh, young one. It was thousands of years before we",
                    "fully understood that ourselves. All will be explained if",
                    "we feel you are ready. Now which one of these crystal",
                    "creations would you like?"
                )
                stage = 26
            }

            26 -> {
                interpreter.sendOptions(
                    "Select an Option",
                    "Shields are for wimps! Give me the bow!",
                    "I don't like running and hiding behind mushrooms. Shield please!"
                )
                stage = 27
            }

            27 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY,
                        "Shields are for wimps! Give me the bow!"
                    )
                    stage = 30
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY,
                        "I don't like running and hiding behind mushrooms.",
                        "Shield please!"
                    )
                    stage = 301
                }
            }

            30 -> {
                if (!quest.isCompleted(player)) {
                    if (!player.inventory.add(Item(4222, 1))) {
                        GroundItemManager.create(Item(4222, 1), player)
                    }
                    quest.finish(player)
                }
                end()
            }

            301 -> {
                if (!quest.isCompleted(player)) {
                    if (!player.inventory.add(Item(4233, 1))) {
                        GroundItemManager.create(Item(4233, 1), player)
                    }
                    quest.finish(player)
                }
                end()
            }

            31 -> {
                interpreter.sendDialogues(
                    1680,
                    FacialExpression.HALF_GUILTY,
                    "Welcome back to the land of the elves, friend!",
                    "Do you need your seeds charged into equipment?"
                )
                stage = 32
            }

            32 -> {
                interpreter.sendOptions(
                    "Select an Option",
                    "I need to buy a new piece of equipment.",
                    "I need to recharge my seeds into equipment."
                )
                stage = 33
            }

            33 -> when (buttonId) {
                1 -> {
                    interpreter.sendDialogues(
                        1680,
                        FacialExpression.HALF_GUILTY,
                        "Ah, very well.",
                        "I will sell you a new bow or shield for 900,000 coins."
                    )
                    stage = 37
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY,
                        "I need to recharge my current seeds into equipment."
                    )
                    stage = 34
                }
            }

            34 -> {
                options( "Recharge seed into bow", "Recharge seed into shield")
                stage = 35
            }

            35 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY,
                        "Recharge my seed into a bow, please."
                    )
                    stage = 36
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY,
                        "Recharge my seed into a shield, please."
                    )
                    stage = 3601
                }
            }

            36 -> {
                if (!player.inventory.contains(RovingElves.CRYSTAL_SEED.id, 1)) {
                    interpreter.sendDialogue("You don't have any seeds to recharge.")
                    stage = 500
                }
                val timesRecharged = player.getAttribute("rovingelves:crystal-equip-recharges", 0)
                val price = crystalWeaponPrice(timesRecharged)
                if (!player.inventory.contains(995, price)) {
                    interpreter.sendDialogue(String.format("You don't have enough coins, you need %d.", price))
                    stage = 500
                }
                if (player.inventory.contains(995, price) && player.inventory.contains(
                        RovingElves.CRYSTAL_SEED.id,
                        1
                    )
                ) {
                    if (player.inventory.remove(RovingElves.CRYSTAL_SEED) && player.inventory.remove(
                            Item(
                                995,
                                price
                            )
                        )
                    ) {
                        player.inventory.add(Item(4214, 1))
                        player.incrementAttribute("/save:rovingelves:crystal-equip-recharges", 1)
                        end()
                    }
                }
            }

            3601 -> {
                if (!player.inventory.contains(RovingElves.CRYSTAL_SEED.id, 1)) {
                    interpreter.sendDialogue("You don't have any seeds to recharge.")
                    stage = 500
                }
                val timesRecharged = player.getAttribute<Int>("rovingelves:crystal-equip-recharges", 0)
                var price = crystalWeaponPrice(timesRecharged)
                if (!player.inventory.contains(995, price)) {
                    interpreter.sendDialogue("You don't have enough coins.")
                    stage = 500
                }
                if (player.inventory.contains(995, price) && player.inventory.contains(
                        RovingElves.CRYSTAL_SEED.id,
                        1
                    )
                ) {
                    if (player.inventory.remove(RovingElves.CRYSTAL_SEED) && player.inventory.remove(
                            Item(
                                995,
                                price
                            )
                        )
                    ) {
                        player.inventory.add(Item(4225, 1))
                        player.incrementAttribute("/save:rovingelves:crystal-equip-recharges", 1)
                        end()
                    }
                }
            }

            37 -> {
                options( "Purchase bow", "Purchase shield")
                stage = 38
            }

            38 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY, "I'd like to buy a new bow.")
                    stage = 39
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY, "I'd like to buy a new shield.")
                    stage = 40
                }
            }

            39 -> {
                val price = crystalWeaponPrice(0)
                if (!player.inventory.contains(995, price)) {
                    interpreter.sendDialogue("You don't have enough coins.")
                    stage = 500
                }
                if (player.inventory.contains(995, price)) {
                    if (player.inventory.remove(Item(995, price))) {
                        if (!player.inventory.add(Item(4212, 1))) {
                            GroundItemManager.create(Item(4212, 1), player)
                        }
                        end()
                    }
                }
            }

            40 -> {
                val price = crystalWeaponPrice(0)
                if (!player.inventory.contains(995, price)) {
                    interpreter.sendDialogue("You don't have enough coins.")
                    stage = 500
                }
                if (player.inventory.contains(995, price)) {
                    if (player.inventory.remove(Item(995, price))) {
                        if (!player.inventory.add(Item(4224, 1))) {
                            GroundItemManager.create(Item(4224, 1), player)
                        }
                        end()
                    }
                }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(DialogueInterpreter.getDialogueKey("islwyn_dialogue"), 1680)
    }

    fun crystalWeaponPrice(timesRecharged: Int): Int {
        return Math.max(900000 - 180000 * timesRecharged, 180000)
    }
}