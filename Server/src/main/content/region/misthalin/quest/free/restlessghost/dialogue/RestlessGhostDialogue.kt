package content.region.misthalin.quest.free.restlessghost.dialogue

import content.region.misthalin.quest.free.restlessghost.RestlessGhost
import core.api.getQuestStage
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Restless ghost dialogue.
 */
@Initializable
class RestlessGhostDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.HALF_GUILTY, "Hello ghost, how are you?")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                if (!player.equipment.contains(552, 1)) {
                    npc(FacialExpression.HALF_GUILTY, "Wooo wooo wooooo!")
                    stage = 1
                } else {
                    if (getQuestStage(player, RestlessGhost.NAME) == 20) {
                        npc(FacialExpression.HALF_GUILTY, "Not very good actually.")
                        stage = 500
                    }
                    if (getQuestStage(player, RestlessGhost.NAME) == 30) {
                        npc(FacialExpression.HALF_GUILTY, "How are you doing finding my skull?")
                        stage = 520
                    }
                    if (getQuestStage(player, RestlessGhost.NAME) == 40) {
                        npc(FacialExpression.HALF_GUILTY, "How are you doing finding my skull?")
                        stage = 550
                    }
                    npc("Fine, thanks.")
                    stage = 990
                }
            }

            990 -> end()
            500 -> {
                player(FacialExpression.HALF_GUILTY, "What's the problem then?")
                stage = 501
            }

            501 -> {
                npc(FacialExpression.HALF_GUILTY, "Did you just understand what I said???")
                stage = 502
            }

            502 -> {
                player(FacialExpression.HALF_GUILTY, "Yep, now tell me what the problem is.")
                stage = 503
            }

            503 -> {
                npc(FacialExpression.HALF_GUILTY, "WOW! This is INCREDIBLE! I didn't expect anyone", "to ever understand me again!")
                stage = 504
            }

            504 -> {
                player(FacialExpression.HALF_GUILTY, "Ok, Ok, I can understand you!")
                stage = 505
            }

            505 -> {
                player(FacialExpression.HALF_GUILTY,
                    "But have you any idea WHY you're doomed to be a",
                    "ghost?"
                )
                stage = 506
            }

            506 -> {
                npc(FacialExpression.HALF_GUILTY, "Well, to be honest... I'm not sure.")
                stage = 507
            }

            507 -> {
                player(FacialExpression.HALF_GUILTY,
                    "I've been told a certain task may need to be completed",
                    "so you can rest in peace."
                )
                stage = 508
            }

            508 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "I should think it is probably because a warlock has come",
                    "along and stolen my skull. If you look inside my coffin",
                    "there, you'll find my corpse without a head on it."
                )
                stage = 509
            }

            509 -> {
                player(FacialExpression.HALF_GUILTY,
                    "Do you know where this warlock might be now?"
                )
                stage = 510
            }

            510 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "I think it was one of the warlocks who lives in the big",
                    "tower by the sea south-west from here."
                )
                stage = 511
            }

            511 -> {
                player(FacialExpression.HALF_GUILTY,
                    "Ok. I will try and get the skull back for you, then you",
                    "can rest in peace."
                )
                player.getQuestRepository().getQuest(RestlessGhost.NAME).setStage(player, 30)
                stage = 512
            }

            512 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "Ooh, thank you. That would be such a great relief!"
                )
                stage = 513
            }

            513 -> {
                npc(FacialExpression.HALF_GUILTY, "It is so dull being a ghost...")
                stage = 514
            }

            514 -> end()
            520 -> if (player.inventory.contains(964, 1)) {
                end()
            } else {
                player(FacialExpression.HALF_GUILTY, "Sorry, I can't find it at the moment.")
                stage = 521
            }

            521 -> {
                npc(FacialExpression.HALF_GUILTY, "Ah well. Keep on looking.")
                stage = 522
            }

            522 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "I'm pretty sure it's somewhere in the tower south-west",
                    "from here. There's a lot of levels to the tower, though. I",
                    "suppose it might take a little while to find."
                )
                stage = 523
            }

            523 -> end()
            550 -> {
                player(FacialExpression.HALF_GUILTY, "I have found it!")
                stage = 551
            }

            551 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "Hurrah! Now I can stop being a ghost! You just need",
                    "to put it on my coffin there, and I will be free!"
                )
                stage = 523
            }

            1 -> {
                options("Sorry, I don't speak ghost.",
                    "Ooh... THAT'S interesting.",
                    "Any hints where I can find some treasure?"
                )
                stage = 2
            }

            2 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY, "Sorry, I don't speak ghost.")
                    stage = 10
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY, "Ooh... THAT'S interesting.")
                    stage = 20
                }

                3 -> {
                    player(FacialExpression.HALF_GUILTY,
                        "Any hints where I can find some treasure?"
                    )
                    stage = 30
                }
            }

            10 -> {
                npc(FacialExpression.HALF_GUILTY, "Woo woo?")
                stage = 11
            }

            11 -> {
                player(FacialExpression.HALF_GUILTY, "Nope, still don't understand you.")
                stage = 12
            }

            12 -> {
                npc(FacialExpression.HALF_GUILTY, "WOOOOOOOOO!")
                stage = 13
            }

            13 -> {
                player(FacialExpression.HALF_GUILTY, "Never mind.")
                stage = 14
            }

            14 -> end()
            30 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "Wooooooo woo! Wooooo woo wooooo woowoowoo wooo",
                    "Woo woooo. Wooooo woo woo? Wooooooooooooooooooo!"
                )
                stage = 31
            }

            31 -> {
                player(FacialExpression.HALF_GUILTY, "Sorry, I don't speak ghost.")
                stage = 32
            }

            32 -> {
                npc(FacialExpression.HALF_GUILTY, "Woo woo?")
                stage = 11
            }

            20 -> {
                npc(FacialExpression.HALF_GUILTY, "Woo woooo. Woooooooooooooooooo!")
                stage = 21
            }

            21 -> {
                player(FacialExpression.HALF_GUILTY, "Did he really?")
                stage = 22
            }

            22 -> {
                npc(FacialExpression.HALF_GUILTY, "Woo.")
                stage = 23
            }

            23 -> {
                player(FacialExpression.HALF_GUILTY,
                    "My brother had EXACTLY the same problem."
                )
                stage = 24
            }

            24 -> {
                npc(FacialExpression.HALF_GUILTY, "Woo Wooooo!")
                stage = 25
            }

            25 -> {
                npc(FacialExpression.HALF_GUILTY, "Wooooo Woo woo woo!")
                stage = 26
            }

            26 -> {
                player(FacialExpression.HALF_GUILTY, "Goodbye. Thanks for the chat.")
                stage = 27
            }

            27 -> {
                npc(FacialExpression.HALF_GUILTY, "Wooo wooo?")
                stage = 28
            }

            28 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(457)
    }
}