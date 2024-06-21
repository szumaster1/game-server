package content.region.asgarnia.quest.bkfortress

import content.region.asgarnia.quest.recruitmentdrive.dialogue.SirAmikVarzeDialogueFile
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.quest.Quest
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.plugin.Initializable

class SirAmikVarzeDialogue(player: Player? = null) : Dialogue(player) {

    private var quest: Quest? = null

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        quest = player.getQuestRepository().getQuest("Black Knights' Fortress")
        when (quest!!.getStage(player)) {
            30 -> {
                player(FacialExpression.HAPPY,
                    "I have ruined the Black Knights' invincibility potion."
                )
                stage = 0
            }

            10, 20 -> {
                npc(FacialExpression.ASKING, "How's the mission going?")
                stage = 0
            }

            100 -> {
                player(FacialExpression.HAPPY, "Hello Sir Amik.")
                stage = 0
            }

            else -> {
                npc(FacialExpression.ASKING,
                    "I am the leader of the White Knights of Falador. Why",
                    "do you seek my audience?"
                )
                stage = 0
            }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (quest!!.getStage(player)) {
            100 -> openDialogue(player, SirAmikVarzeDialogueFile(), npc)
            30 -> when (stage) {
                0 -> {
                    npc(FacialExpression.NEUTRAL,
                        "Yes, we have just received a message from the Black",
                        "Knights saying they withdraw their demands, which",
                        "would seem to confirm your story."
                    )
                    stage = 1
                }

                1 -> {
                    player(FacialExpression.HALF_ASKING,
                        "Now I believe there was some talk of a cash reward..."
                    )
                    stage = 2
                }

                2 -> {
                    npc(FacialExpression.HAPPY,
                        "Absolutely right. Please accept this reward."
                    )
                    stage = 3
                }

                3 -> {
                    interpreter.sendDialogue("Sir Amik hands you 2500 coins.")
                    stage = 4
                }

                4 -> {
                    if (!player.inventory.add(Item(995, 2500))) {
                        GroundItemManager.create(Item(995, 2500), player)
                    }
                    quest!!.finish(player)
                    player.getQuestRepository().syncronizeTab(player)
                    end()
                }
            }

            20 -> when (stage) {
                0 -> {
                    player(FacialExpression.NEUTRAL,
                        "I have managed to find what the secret weapon is",
                        "I am now in the process of destroying it."
                    )
                    stage = 1
                }

                1 -> end()
            }

            10 -> when (stage) {
                0 -> {
                    player(FacialExpression.HALF_GUILTY,
                        "I haven't managed to find what the secret weapon is",
                        "yet..."
                    )
                    stage = 1
                }

                1 -> {
                    npc(FacialExpression.NEUTRAL,
                        "Well keep at it! Falador's future is at stake!"
                    )
                    stage = 2
                }

                2 -> if (!player.inventory.containsItem(BlackKnightsFortress.DOSSIER) && !player.bank.containsItem(
                        BlackKnightsFortress.DOSSIER
                    )
                ) {
                    npc(FacialExpression.NEUTRAL, "Here's the dossier on the case.")
                    stage = 3
                } else {
                    npc(FacialExpression.NEUTRAL,
                        "Don't forget to read that dossier of information I gave",
                        "you."
                    )
                    stage = 4
                }

                3 -> {
                    player.inventory.add(BlackKnightsFortress.DOSSIER)
                    end()
                }

                4 -> end()
            }

            0 -> when (stage) {
                0 -> if (player.getQuestRepository().points < 12) {
                    player(FacialExpression.NEUTRAL, "I don't I'm just looking around.")
                    stage = 2
                } else {
                    options( "I seek a quest!", "I don't, I'm just looking around.")
                    stage = 1
                }

                1 -> when (buttonId) {
                    1 -> {
                        player(FacialExpression.NEUTRAL, "I seek a quest.")
                        stage = 5
                    }

                    2 -> {
                        player(FacialExpression.NEUTRAL, "I don't I'm just looking around.")
                        stage = 2
                    }
                }

                2 -> end()
                3 -> {
                    npc(FacialExpression.HALF_WORRIED, "Ok. Please don't break anything.")
                    stage = 4
                }

                4 -> end()
                5 -> {
                    npc(FacialExpression.NEUTRAL,
                        "Well, I need some spy work doing but it's quite",
                        "dangerous. It will involve going into the Black Knights'",
                        "fortress."
                    )
                    stage = 6
                }

                6 -> {
                    interpreter.sendOptions(
                        "Select an Option",
                        "I laugh in the face of danger!",
                        "I go and cower in the corner at the first sign of danger!"
                    )
                    stage = 7
                }

                7 -> when (buttonId) {
                    1 -> {
                        player(FacialExpression.HAPPY, "I laugh in the face of danger!")
                        stage = 15
                    }

                    2 -> {
                        interpreter.sendDialogues(
                            player,
                            FacialExpression.HALF_GUILTY,
                            "I go and cower in a corner at the first sign of danger!"
                        )
                        stage = 8
                    }
                }

                8 -> {
                    npc(FacialExpression.NEUTRAL, "Err....")
                    stage = 9
                }

                9 -> {
                    npc(FacialExpression.NEUTRAL, "Well.")
                    stage = 10
                }

                10 -> {
                    npc(FacialExpression.NEUTRAL,
                        "I... suppose spy work DOES involve a little hiding in",
                        "corners."
                    )
                    stage = 11
                }

                11 -> {
                    interpreter.sendOptions(
                        "Select an Option",
                        "Oh. I suppose I'll give it a go then.",
                        "No, I'm not ready to do that."
                    )
                    stage = 12
                }

                12 -> when (buttonId) {
                    1 -> {
                        interpreter.sendDialogues(
                            player,
                            FacialExpression.FRIENDLY,
                            "Oh. I suppose I'll give it a go then."
                        )
                        stage = 17
                    }

                    2 -> {
                        player(FacialExpression.HALF_GUILTY, "No, I'm not ready to do that.")
                        stage = 13
                    }
                }

                13 -> {
                    npc(FacialExpression.NEUTRAL,
                        "Come see me again if you change your mind."
                    )
                    stage = 14
                }

                14 -> end()
                15 -> {
                    npc(FacialExpression.NEUTRAL,
                        "Well that's good. Don't get too overconfident though."
                    )
                    stage = 16
                }

                16 -> {
                    npc(FacialExpression.NEUTRAL,
                        "You've come along at just the right time actually. All of",
                        "my knights are already known to the Black Knights."
                    )
                    stage = 17
                }

                17 -> {
                    npc(FacialExpression.HALF_GUILTY,
                        "Subtlety isn't exactly our strong point."
                    )
                    stage = 18
                }

                18 -> {
                    player(FacialExpression.HALF_ASKING,
                        "Can't you just take your White Knights' armour off?",
                        "They wouldn't recognise you then!"
                    )
                    stage = 19
                }

                19 -> {
                    npc(FacialExpression.NEUTRAL,
                        "I am afraid our charter prevents us using espionage in",
                        "any form, that is the domain of the Temple Knights."
                    )
                    stage = 20
                }

                20 -> {
                    player(FacialExpression.HALF_ASKING, "Temple Knights? Who are they?")
                    stage = 21
                }

                21 -> {
                    npc(FacialExpression.NEUTRAL,
                        "The information is classified. I am forbidden to share it",
                        "with outsiders."
                    )
                    stage = 22
                }

                22 -> {
                    player(FacialExpression.HALF_ASKING, "So... What do you need doing?")
                    stage = 23
                }

                23 -> {
                    npc(FacialExpression.ANGRY,
                        "Well, the Black Knights have started making strange",
                        "threats to us; demanding large amounts of money and",
                        "land, and threatening to invade Falador if we don't pay",
                        "them."
                    )
                    stage = 24
                }

                24 -> {
                    npc(FacialExpression.NEUTRAL,
                        "Now, NORMALLY this wouldn't be a problem..."
                    )
                    stage = 25
                }

                25 -> {
                    npc(FacialExpression.ANGRY,
                        "But they claim to have a powerful new secret weapon."
                    )
                    stage = 26
                }

                26 -> {
                    npc(FacialExpression.NEUTRAL,
                        "Your mission, should you decide to accept it, is to",
                        "infiltrate their fortress, find out what their secret",
                        "weapon is, and then sabotage it."
                    )
                    stage = 27
                }

                27 -> {
                    options( "Ok, I'll do my best.", "No, I'm not ready to do that.")
                    stage = 28
                }

                28 -> when (buttonId) {
                    1 -> {
                        player(FacialExpression.NEUTRAL, "Ok, I'll do my best.")
                        stage = 31
                    }

                    2 -> {
                        player(FacialExpression.NEUTRAL, "No, I'm not ready to do that.")
                        stage = 29
                    }
                }

                29 -> {
                    npc(FacialExpression.NEUTRAL,
                        "Come see me again if you change your mind."
                    )
                    stage = 30
                }

                30 -> end()
                31 -> {
                    npc(FacialExpression.NEUTRAL,
                        "Good luck! Let me know how you get on. Here's the",
                        "dossier for the case, I've already given you the details."
                    )
                    stage = 32
                }

                32 -> {
                    if (player.inventory.add(BlackKnightsFortress.DOSSIER)) {
                        quest!!.start(player)
                    } else {
                        player.packetDispatch.sendMessage("You don't have enough inventory space.")
                    }
                    end()
                }
            }

            else -> {}
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(608)
    }
}