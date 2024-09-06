package content.region.misthalin.lumbridge.quest.priest.dialogue

import content.region.misthalin.lumbridge.quest.priest.RestlessGhost
import core.api.freeSlots
import core.api.getQuestStage
import core.api.isQuestComplete
import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable

/**
 * Represents the Father Uhrney dialogue.
 */
@Initializable
class FatherUhrneyDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc("Go away! I'm meditating!")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> if (getQuestStage(player, "The Restless Ghost")  == 0) {
                options("Well, that's friendly.", "I've come to repossess your house.")
                stage = 1
            } else if (getQuestStage(player, "The Restless Ghost") == 10) {
                options("Well, that's friendly.", "I've come to repossess your house.", "Father Aereck sent me to talk to you.")
                stage = 500
            } else if (player.gameAttributes.attributes.containsKey("restless-ghost:urhney") || isQuestComplete(player, "The Restless Ghost")) {
                options("Well, that's friendly.", "I've come to repossess your house.", "I've lost the Amulet of Ghostspeak.")
                stage = 514
            }

            500 -> when (buttonId) {
                1 -> {
                    player("Well, that's friendly.")
                    stage = 10
                }

                2 -> {
                    player("I've come to repossess your house.")
                    stage = 20
                }

                3 -> {
                    player("Father Aereck sent me to talk to you.")
                    stage = 501
                }
            }

            501 -> {
                npc("I suppose I'd better talk to you then. What problems", "has he got himself into this time?")
                stage = 502
            }

            502 -> {
                player("He's got a ghost haunting his graveyard.")
                stage = 503
            }

            503 -> {
                npc("Oh, the silly fool.")
                stage = 504
            }

            504 -> {
                npc("I leave town for just five months, and ALREADY he", "can't manage.")
                stage = 505
            }

            505 -> {
                npc("(sigh)")
                stage = 506
            }

            506 -> {
                npc("Well, I can't go back and exorcise it. I vowed not to", "leave this place. Until I had done a full two years of", "prayer and meditation.")
                stage = 507
            }

            507 -> {
                npc("Tell you what I can do though; take this amulet.")
                stage = 508
            }

            508 -> {
                if (freeSlots(player) == 0) {
                    end()
                    player.packetDispatch.sendMessage("You don't have enough inventory space to accept this amulet.")
                }
                interpreter.sendItemMessage(552, "Father Urhney hands you an amulet.")
                player.inventory.add(Item(552, 1))
                player.getQuestRepository().getQuest("The Restless Ghost").setStage(player, 20)
                player.gameAttributes.setAttribute("/save:restless-ghost:urhney", true)
                stage = 509
            }

            509 -> {
                npc("It is an Amulet of Ghostspeak.")
                stage = 510
            }

            510 -> {
                npc("So called, because when you wear it you can speak to", "ghosts. A lot of ghosts are doomed to be ghosts because", "they have left some important task uncompleted.")
                stage = 511
            }

            511 -> {
                npc("Maybe if you know what this task is, you can get rid of", "the ghost. I'm not making any guarantees mind you,", "but it is the best I can do right now.")
                stage = 512
            }

            512 -> {
                player("Thank you. I'll give it a try!")
                stage = 513
            }

            513 -> end()
            514 -> when (buttonId) {
                1 -> {
                    player("Well, that's friendly.")
                    stage = 10
                }

                2 -> {
                    player("I've come to repossess your house.")
                    stage = 20
                }

                3 -> {
                    player("I've lost the Amulet of Ghostpeak.")
                    stage = 515
                }
            }

            515 -> {
                if (player.inventory.contains(552, 1) || player.equipment.contains(552, 1)) {
                    interpreter.sendDialogue("Father Urhney sighs.")
                    stage = 516
                }
                if (player.bank.contains(552, 1)) {
                    interpreter.sendDialogue("Father Urhney sighs.")
                    stage = 517
                }
                interpreter.sendDialogue("Father Urhney sighs.")
                stage = 519
            }

            516 -> {
                npc("What are you talking about? I can see you've got it", "with you!")
                stage = 518
            }

            517 -> {
                npc("What are you talking about? I can see you've got it", "in your bank!")
                stage = 518
            }

            518 -> end()
            519 -> {
                npc("How careless can you get? Those things aren't easy to", "come by you know! It's a good job I've got a spare.")
                stage = 520
            }

            520 -> {
                player.inventory.add(Item(552))
                interpreter.sendItemMessage(552, "Father Urhney hands you an amulet.")
                stage = 521
            }

            521 -> {
                npc("Be more careful this time.")
                stage = 522
            }

            522 -> {
                player("Ok, I'll try to be.")
                stage = 523
            }

            523 -> end()
            1 -> when (buttonId) {
                1 -> {
                    player("Well, that's friendly.")
                    stage = 10
                }

                2 -> {
                    player("I've come to repossess your house.")
                    stage = 20
                }
            }

            10 -> {
                npc("I SAID go AWAY.")
                stage = 11
            }

            11 -> {
                player("Ok, ok... sheesh, what a grouch.")
                stage = 12
            }

            12 -> end()
            20 -> {
                npc("Under what grounds???")
                stage = 21
            }

            21 -> {
                options("Repeated failure on mortgage repayments.", "I don't know, I just wanted this house.")
                stage = 22
            }

            22 -> when (buttonId) {
                1 -> {
                    player("Repeated failure on mortgage repayments.")
                    stage = 100
                }

                2 -> {
                    player("I don't know. I just wanted this house...")
                    stage = 200
                }
            }

            100 -> {
                npc("What?")
                stage = 101
            }

            101 -> {
                npc("I don't have a mortgage! I built this house.")
                stage = 102
            }

            102 -> {
                player("Sorry. I must have got the wrong address. All the", "houses look the same around here.")
                stage = 103
            }

            103 -> {
                npc("What? What houses? What ARE you talking about???")
                stage = 104
            }

            104 -> {
                player("Never mind.")
                stage = 105
            }

            105 -> end()
            200 -> {
                npc("Oh... go away and stop wasting my time!")
                stage = 201
            }

            201 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(458)
    }
}