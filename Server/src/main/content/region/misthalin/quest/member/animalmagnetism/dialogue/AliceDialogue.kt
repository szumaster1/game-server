package content.region.misthalin.quest.member.animalmagnetism.dialogue

import content.region.misthalin.quest.member.animalmagnetism.AnimalMagnetism
import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.quest.Quest

class AliceDialogue(player: Player? = null) : Dialogue(player) {

    private var quest: Quest? = null

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        quest = player.getQuestRepository().getQuest(AnimalMagnetism.NAME)
        when (quest!!.getStage(player)) {
            10, 11, 12, 13, 14, 15, 16, 17, 18 -> options(
                "What are you selling?",
                "I'm okay, thank you.",
                "I'm here about a quest."
            )

            else -> options("What are you selling?", "I'm okay, thank you.")
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (quest!!.getStage(player)) {
            10, 11, 12, 13, 14, 15, 16, 17, 18 -> when (stage) {
                0 -> when (buttonId) {
                    1 -> {
                        end()
                        npc.openShop(player)
                    }

                    2 -> {
                        player("I'm okay, thank you.")
                        stage++
                    }

                    3 -> {
                        player("I'm here about a quest.")
                        stage = 2
                    }
                }

                1 -> end()
                else -> handleQuest(buttonId)
            }

            else -> when (stage) {
                0 -> when (buttonId) {
                    1 -> {
                        end()
                        npc.openShop(player)
                    }

                    2 -> {
                        player("I'm okay, thank you.")
                        stage++
                    }
                }

                1 -> end()
            }
        }
        return true
    }

    private fun handleQuest(buttonId: Int) {
        when (quest!!.getStage(player)) {
            10 -> when (stage) {
                2 -> {
                    player("I am after one of your, er, unhealthier poultry. Could", "you help me?")
                    stage++
                }

                3 -> {
                    npc("You need those useless, undead chickens? How odd you", "adventurers are.")
                    stage++
                }

                4 -> {
                    npc("You need to talk to my husband though - not that I", "can these days.")
                    stage++
                }

                5 -> {
                    player("Why ever would this be?")
                    stage++
                }

                6 -> {
                    npc("Can't you see, he is dead. I can't talk to the dead.")
                    stage++
                }

                7 -> end()
            }

            11 -> when (stage) {
                2 -> {
                    player(
                        "I have a message from your husband. He wants you to",
                        "know that he still loves you, despite his ghostly state."
                    )
                    stage++
                }

                3 -> {
                    npc(
                        "The curse of undeath was so cruel; all the men out",
                        "here succumbed, but Lyra and I were left alive."
                    )
                    stage++
                }

                4 -> {
                    npc("Ever since that day, I've not been able to speak to him.")
                    stage++
                }

                5 -> {
                    npc(
                        "Tell him I love him but I can't find our savings. I",
                        "know he had our collection of gold and 'prize cow'",
                        "rosettes just before the curse struck."
                    )
                    stage++
                }

                6 -> {
                    player("I'll have a word with him then; magic has its uses I", "suppose.")
                    stage++
                }

                7 -> {
                    quest!!.setStage(player, 12)
                    end()
                }
            }

            12 -> when (stage) {
                2 -> {
                    npc("Have you spoken to my husband yet?")
                    stage++
                }

                3 -> {
                    player("I'm working on it.")
                    stage++
                }

                4 -> end()
            }

            13 -> when (stage) {
                2 -> {
                    player("Your husband say he put the cash in the bank.")
                    stage++
                }

                3 -> {
                    npc("I'll need his bank pass, in that case.")
                    stage++
                }

                4 -> {
                    player("Can't you just take a ghostspeak amulet? Then you", "could talk to him directly?")
                    stage++
                }

                5 -> {
                    npc(
                        "I tried that once, but all those other ghosts - and even",
                        "the undead chickens and cows - scared me so much. I",
                        "wouldn't try it again for all the cash in Varrock bank."
                    )
                    stage++
                }

                6 -> {
                    quest!!.setStage(player, 14)
                    end()
                }
            }

            14 -> when (stage) {
                2 -> {
                    npc("Have you asked him about the bank pass?")
                    stage++
                }

                3 -> {
                    player("Not yet.")
                    stage++
                }

                4 -> end()
            }

            15 -> when (stage) {
                2 -> {
                    player("He says he won't trust me with the bank pass.")
                    stage++
                }

                3 -> {
                    player("What if I gave some sort of altered ghostspeak amulet", "to him - surely that would work?")
                    stage++
                }

                4 -> {
                    npc(
                        "You're so clever; I've overheard passing adventurers",
                        "say that there's some witch near here who changes",
                        "ghostspeak amulets."
                    )
                    stage++
                }

                5 -> {
                    npc("I think she lives a bit west of that mad Professor", "Frenksomething, past the Farming patch.")
                    stage++
                }

                6 -> {
                    player(
                        "I'll see if I can find her. Big nose and a monstrous hat",
                        "I assume? I wonder where the beautiful young witches",
                        "hide..."
                    )
                    stage++
                }

                7 -> {
                    npc("Mysterious indeed, but in this case she actually looks", "pretty normal.")
                    stage++
                }

                8 -> {
                    quest!!.setStage(player, 16)
                    end()
                }
            }

            16, 17 -> when (stage) {
                2 -> {
                    npc("Have you found a way for me to talk to my husband", "yet?")
                    stage++
                }

                3 -> {
                    player("I've not progressed at all, I'm afraid.")
                    stage++
                }

                4 -> end()
            }

            18 -> when (stage) {
                2 -> {
                    npc("Have you handed him an enhanced amulet?")
                    stage++
                }

                3 -> {
                    player("I Have obtained the amulet; I just haven't handed it", "over yet. So, it's looking good!")
                    stage++
                }

                4 -> end()
            }
        }
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ALICE_2307)
    }
}