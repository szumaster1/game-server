package content.region.misthalin.draynor.dialogue

import cfg.consts.NPCs
import core.game.activity.ActivityManager
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable

/**
 * Represents the Draynor Bank guard dialogue.
 */
@Initializable
class DraynorBankGuardDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HALF_GUILTY, "Yes?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> stage = if (!player.getSavedData().globalData.isDraynorRecording()) {
                options("Can I deposit my stuff here?", "That wall doesn't look very good.", "Sorry, I don't want anything.")
                1
            } else {
                options("Can I see that recording again, please?", "Sorry, I don't want anything.")
                70
            }

            70 -> when (buttonId) {
                1 -> {
                    player("Can I see that recording again, please?")
                    stage = 71
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY, "Sorry, I don't want anything.")
                    stage = 30
                }
            }

            71 -> {
                npc("I'd like you to pay me 50 gp first.")
                stage = 72
            }

            72 -> if (!player.inventory.containsItem(COINS)) {
                player("I'm not carrying that much.")
                stage = 73
                return true
            } else {
                options("Ok, here's 50 gp.", "Thanks, maybe another day.")
                stage = 80
            }

            73 -> stage = if (player.bank.containsItem(COINS)) {
                npc("As a bank employee, I suppose I could take the money", "directly from your bank account.")
                74
            } else {
                npc("Come back when you do.")
                75
            }

            74 -> {
                options("Ok, you can take 50 gp from my bank account.", "Thanks, maybe another day.")
                stage = 76
            }

            75 -> end()
            76 -> when (buttonId) {
                1 -> {
                    player("Ok, you can take 50 gp from my bank account.")
                    stage = 79
                }

                2 -> {
                    player("Thanks, maybe another day.")
                    stage = 77
                }
            }

            77 -> {
                npc("Ok.")
                stage = 78
            }

            78 -> end()
            79 -> {
                end()
                if (player.bank.containsItem(COINS) && player.bank.remove(COINS)) {
                    startRecording(player)
                }
            }

            80 -> when (buttonId) {
                1 -> {
                    player("Ok, here's 50 gp.")
                    stage = 81
                }

                2 -> {
                    player("Thanks, maybe another day.")
                    stage = 77
                }
            }

            81 -> {
                end()
                if (!player.inventory.containsItem(COINS)) {
                    end()
                    return true
                }
                if (player.inventory.remove(COINS)) {
                    startRecording(player)
                }
            }

            1 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY,
                        "Hello. Can I deposit my stuff here?"
                    )
                    stage = 10
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY, "That wall doesn't look very good.")
                    stage = 20
                }

                3 -> {
                    player(FacialExpression.HALF_GUILTY, "Sorry, I don't want anything.")
                    stage = 30
                }
            }

            30 -> {
                npc(FacialExpression.HALF_GUILTY, "Ok.")
                stage = 31
            }

            31 -> end()
            20 -> {
                npc(FacialExpression.HALF_GUILTY, "No, it doesn't.")
                stage = 21
            }

            21 -> {
                options("Are you going to tell me what happened?", "Alright, I'll stop bothering you now.")
                stage = 22
            }

            22 -> when (buttonId) {
                1 -> {
                    player("Are you going to tell me what happended?")
                    stage = 25
                }

                2 -> {
                    player("Alright, I'll stop bothering you now.")
                    stage = 23
                }
            }

            23 -> {
                npc("Good day, sir.")
                stage = 24
            }

            24 -> end()
            25 -> {
                npc("I could do.")
                stage = 26
            }

            26 -> {
                player("Ok, go on!")
                stage = 27
            }

            27 -> {
                npc("Someone smashed the wall when", "they were robbing the bank.")
                stage = 28
            }

            28 -> {
                player("Someone's robbed the bank?")
                stage = 29
            }

            29 -> {
                npc("Yes.")
                stage = 50
            }

            50 -> {
                player("But... was anyone hurt?", "Did they get anything valuable?")
                stage = 51
            }

            51 -> {
                npc("Yes, but we were able to get more staff and mend the", "wall easily enough.")
                stage = 52
            }

            52 -> {
                npc("The Bank has already replaced all the stolen items that", "belonged to customers.")
                stage = 53
            }

            53 -> {
                player("Oh, good... but the bank staff got hurt?")
                stage = 54
            }

            54 -> {
                npc("Yes but the new ones are just as good.")
                stage = 55
            }

            55 -> {
                player("You're not very nice, are you?")
                stage = 56
            }

            56 -> {
                npc("No-one's expecting me to be nice.")
                stage = 57
            }

            57 -> {
                player("Anyway... So, someone's robbed the bank?")
                stage = 58
            }

            58 -> {
                npc("Yes.")
                stage = 59
            }

            59 -> {
                player("Do you know who did it?")
                stage = 60
            }

            60 -> {
                npc("We are fairly sure we know who the robber was. The", "security recording was damaged in the attack, but it still", "shows his face clearly enough.")
                stage = 61
            }

            61 -> {
                player("You've got a security recording?")
                stage = 62
            }

            62 -> {
                npc("Yes. Our insurers insisted that we", "install a magical scrying orb.")
                stage = 63
            }

            63 -> {
                player("Can I see the recording?")
                stage = 64
            }

            64 -> {
                npc("I suppose so. But it's quite long.")
                stage = 65
            }

            65 -> {
                options("That's ok, show me the recording.", "Thanks, maybe another day.")
                stage = 66
            }

            66 -> when (buttonId) {
                1 -> {
                    player("That's ok, show me the recording.")
                    stage = 68
                }

                2 -> {
                    player("Thanks, maybe another day.")
                    stage = 67
                }
            }

            67 -> end()
            68 -> {
                npc("Alright... The bank's magical playback device will feed the", "recorded images into your mind. Just shut your eyes.")
                stage = 69
            }

            69 -> startRecording(player)
            10 -> {
                npc(FacialExpression.HALF_GUILTY, "No. I'm a security guard, not a bank clerk.")
                stage = 11
            }

            11 -> end()
        }
        return true
    }

    private fun startRecording(player: Player) {
        end()
        ActivityManager.start(player, "dbr cutscene", false)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BANK_GUARD_2574)
    }

    companion object {
        private val COINS = Item(995, 50)
    }
}