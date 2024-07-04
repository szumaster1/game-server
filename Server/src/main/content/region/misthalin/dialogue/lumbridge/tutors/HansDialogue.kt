package content.region.misthalin.dialogue.lumbridge.tutors

import core.api.setAttribute
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.IronmanMode
import core.game.world.map.zone.ZoneBorders
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import java.util.*

@Initializable
class HansDialogue(player: Player? = null) : Dialogue(player) {

    private val timePlayed = IntArray(3)
    private val joinDateDays = 0
    private var inStartDungeon = false

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.NEUTRAL, "Hello. What are you doing here?")
        stage = 0
        if (ZoneBorders(2528, 5004, 2520, 4997).insideBorder(player.location)) {
            inStartDungeon = true
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        var buttonId = buttonId
        if (inStartDungeon && stage == 0) {
            stage = 1
            buttonId = 4
        }
        when (stage) {
            0 -> {
                interpreter.sendOptions(
                    "Select an Option",
                    "I'm looking for whoever is in charge of this place.",
                    "I have come to kill everyone in this castle!",
                    "I don't know. I'm lost. Where am I?",
                    "Have you been here as long as me?"
                )
                stage++
            }

            1 -> when (buttonId) {
                1 -> {
                    npc(FacialExpression.NEUTRAL,
                        "Who, the Duke? He's in his study, on the first floor."
                    )
                    stage = 50
                }

                2 -> {
                    end()
                    //TODO:
                    // Face the player and walk away from them (like moon walking?).
                    // After a moment, return to normal pathing associated with HansNPC.java
                    npc.sendChat("Help! Help!")
                }

                3 -> {
                    npc(FacialExpression.NEUTRAL, "You are in Lumbridge Castle.")
                    stage = 50
                }

                4 -> {
                    npc(FacialExpression.NEUTRAL,
                        "I've been patrolling this castle for years!"
                    )
                    stage = 41
                }
            }

            10 -> when (buttonId) {
                1 -> {
                    //Have you been here as long as me?
                    npc(FacialExpression.NEUTRAL,
                        "I've been patrolling this castle for years!"
                    )
                    stage = 41
                }

                2 -> {
                    npc("Your current XP rate is: " + player.getSkills().experienceMultiplier)
                    stage = 11
                }

                3 ->                         //About Iron Man Mode...
                    stage = if (player.ironmanManager.isIronman) {
                        npc("Your ironman mode is: " + player.ironmanManager.mode.name.lowercase(Locale.getDefault()))
                        50
                    } else {
                        interpreter.sendOptions(
                            "Select an Option",
                            "I would like to be an Iron Man.",
                            "What is an Iron Man?",
                            "Go Back..."
                        )
                        110
                    }

                4 -> {
                    interpreter.sendDialogue(
                        "Randoms are now permanently enabled. This option to be removed",
                        "at a later date."
                    )
                    stage = END_DIALOGUE
                }

                5 -> {
                    interpreter.sendOptions(
                        "Select an Option",
                        "I'm looking for whoever is in charge of this place.",
                        "I have come to kill everyone in this castle!",
                        "I don't know. I'm lost. Where am I?",
                        "More Options..."
                    )
                    stage = 1
                }
            }

            11 -> if (player.getSkills().experienceMultiplier == 5.0) {
                player.newPlayer = player.getSkills().totalLevel < 50
                options("Change xp rate", "Nevermind.")
                stage++
            } else {
                npc("Have a great day!")
                stage = 131
            }

            12 -> when (buttonId) {
                1 -> {
                    if (player.attributes.containsKey("permadeath")) {
                        options("1.0x", "2.5x", "Stay 5.0x", "(HCIM Only) 10x")
                    } else {
                        options("1.0x", "2.5x", "Stay 5.0x")
                    }
                    stage++
                }

                2 -> {
                    npc(FacialExpression.LAUGH, "Haha, alright then!")
                    stage = 50
                }
            }

            13 -> {
                when (buttonId) {
                    1 -> if (player.newPlayer) {
                        player.getSkills().experienceMultiplier = 1.0
                        stage = 14
                    } else {
                        stage = 15
                    }

                    2 -> if (player.newPlayer) {
                        player.getSkills().experienceMultiplier = 2.5
                        stage = 14
                    } else {
                        stage = 15
                    }

                    3 -> {
                        playerl(FacialExpression.FRIENDLY, "I'd rather stay 5x, thank you.")
                        stage = END_DIALOGUE
                        return true
                    }

                    4 -> if (player.newPlayer) {
                        player.getSkills().experienceMultiplier = 10.0
                        stage = 14
                    } else {
                        stage = 15
                    }
                }
                npc("One moment, please...")
            }

            14 -> {
                npc("Tada, your xp rate is now " + player.getSkills().experienceMultiplier)
                stage = 131
            }

            15 -> {
                npc("Sorry, only new accounts can select 2.5x.")
                stage = 131
            }

            41 -> {
                player(FacialExpression.THINKING, "You must be old then?")
                stage++
            }

            42 -> {
                npc(FacialExpression.LAUGH,
                    "Haha, you could say I'm quite the veteran of these lands.",
                    "Yes, I've been here a fair while..."
                )
                stage++
            }

            43 -> {
                player(FacialExpression.ASKING, "Can you tell me how long I've been here?")
                stage++
            }

            44 -> {
                if (!inStartDungeon) {
                    npc(FacialExpression.FRIENDLY,
                        "Ahh, I see all the newcomers arriving in Lumbridge, ",
                        "fresh-faced and eager for adventure. I remember you..."
                    )
                    player.sendMessage("Feature not currently available.")
                } else {
                    npc(FacialExpression.FRIENDLY,
                        "Ahh, I see all the newcomers arriving in Lumbridge, ",
                        "fresh-faced and eager for adventure.",
                        "But this is the first time meeting you..."
                    )
                }
                stage = 50
            }

            50 -> end()
            100 -> when (buttonId) {
                1 -> {
                    if (player.getSavedData().activityData.hardcoreDeath) {
                        interpreter.sendDialogues(
                            npc,
                            FacialExpression.GUILTY,
                            "Sorry, but you've fallen as a Hardcore Iron Man",
                            "already. It would be unfair for those with other",
                            " restrictions if your status were to be removed!"
                        )
                        stage = 50
                    }
                    if (player.getSkills().totalLevel > 500 || player.getQuestRepository().points > 10) {
                        interpreter.sendDialogues(
                            npc,
                            FacialExpression.GUILTY,
                            "Sorry, but you are too far along your journey.",
                            "It would be unfair for those with other",
                            " restrictions if your status were to be removed!"
                        )
                        stage = 50
                    } else {
                        npc(FacialExpression.NEUTRAL, "I have removed your Iron Man status.")
                        player.ironmanManager.mode = IronmanMode.NONE
                        player.sendMessage("Your Iron Man status has been removed.")
                        stage = 50
                    }
                }

                2 -> {
                    if (player.getSavedData().activityData.hardcoreDeath) {
                        interpreter.sendDialogues(
                            npc,
                            FacialExpression.GUILTY,
                            "Sorry, but you've fallen as a Hardcore Iron Man",
                            "already. It would be unfair for those with other",
                            " restrictions if your status were to be changed!"
                        )
                        stage = 50
                    }
                    if (player.getSkills().totalLevel > 500 || player.getQuestRepository().points > 10) {
                        interpreter.sendDialogues(
                            npc,
                            FacialExpression.GUILTY,
                            "Sorry, but you are too far along your journey.",
                            "It would be unfair for those with other",
                            " restrictions if your status were to be changed!"
                        )
                        stage = 50
                    } else {
                        interpreter.sendOptions(
                            "Select a Mode",
                            "Standard",
                            "<col=8A0808>Hardcore</col>",
                            "<col=ECEBEB>Ultimate</col>",
                            "Nevermind."
                        )
                        stage = 150
                    }
                }

                3 -> {
                    player(FacialExpression.ASKING, "What is an Iron Man?")
                    stage = 120
                }

                4 -> {
                    interpreter.sendOptions(
                        "Select an Option",
                        "Have you been here as long as me?",
                        "I'd like to learn faster!",
                        "About Iron Man mode...",
                        "Go Back..."
                    )
                    stage = 10
                }
            }

            110 -> when (buttonId) {
                1 -> if (player.getSkills().totalLevel > 50 || player.getQuestRepository().points > 10) {
                    npc(FacialExpression.GUILTY,
                        "Sorry, but you are too far along your journey.",
                        "It would be unfair for those with other",
                        " restrictions if your status were to be changed!"
                    )
                    stage = 50
                } else {
                    interpreter.sendOptions(
                        "Select a Mode",
                        "Standard",
                        "<col=8A0808>Hardcore</col>",
                        "<col=ECEBEB>Ultimate</col>",
                        "Nevermind."
                    )
                    stage = 150
                }

                2 -> {
                    player("What is an Iron Man?")
                    stage = 120
                }

                3 -> {
                    interpreter.sendOptions(
                        "Select an Option",
                        "Have you been here as long as me?",
                        "I'd like to learn faster!",
                        "About Iron Man mode...",
                        "Go Back..."
                    )
                    stage = 10
                }
            }

            120 -> {
                npc(FacialExpression.NEUTRAL,
                    "An Iron Man account is a style of playing where players",
                    "are completely self-sufficient."
                )
                stage++
            }

            121 -> {
                npc(FacialExpression.NEUTRAL,
                    "A Standard Ironman does not receive items or",
                    "assistance from other players. They cannot trade, stake,",
                    "receive PK loot, scavenge dropped items, nor play",
                    "certain minigames."
                )
                stage++
            }

            122 -> {
                npc(FacialExpression.NEUTRAL,
                    "In addition to Standard Ironman restrictions,",
                    "<col=8A0808>Hardcore</col> Ironmen only have one life. In the event of",
                    "a dangerous death, a player will be downgraded to a",
                    "Standard Ironman, and their stats frozen on the hiscores."
                )
                stage++
            }

            123 -> {
                npc(FacialExpression.NEUTRAL,
                    "For the ultimate challenge, players who choose the",
                    "<col=ECEBEB>Ultimate</col> Ironman mode cannot use banks, nor",
                    "retain any items on death in dangerous areas."
                )
                stage++
            }

            124 -> stage = if (player.ironmanManager.isIronman) {
                interpreter.sendOptions(
                    "Select an Option",
                    "I no longer want to be an Iron Man",
                    "I'd like to change my Iron Man mode",
                    "What is an Iron Man?",
                    "Go Back."
                )
                100
            } else {
                interpreter.sendOptions(
                    "Select an Option",
                    "I would like to be an Iron Man.",
                    "What is an Iron Man?",
                    "Go Back..."
                )
                110
            }

            131 -> end()
            150 -> when (buttonId) {
                1, 2 -> {
                    npc(FacialExpression.NEUTRAL,
                        "I have changed your Iron Man mode to: ",
                        if (buttonId == 1) "Standard" else "<col=8A0808>Hardcore</col>" + " Ironman mode."
                    )
                    player.settings.toggleAcceptAid()
                    player.ironmanManager.mode = IronmanMode.values()[buttonId]
                    if (buttonId == 2) {
                        setAttribute(player, "/save:permadeath", true)
                    }
                    player.sendMessage("Your Iron Man status has been changed.")
                    stage = 50
                }

                3 -> if (!player.bank.isEmpty) {
                    npc(FacialExpression.GUILTY,
                        "Sorry, but your bank is has items in it.",
                        "Please empty your bank and speak to me again."
                    )
                    stage = 50
                } else {
                    npc(FacialExpression.NEUTRAL,
                        "I have changed your Iron Man mode to:",
                        "<col=ECEBEB>Ultimate</col> Ironman mode."
                    )
                    player.ironmanManager.mode = IronmanMode.ULTIMATE
                    player.sendMessage("Your Iron Man status has been changed.")
                    stage = 50
                }

                4 -> stage = if (player.ironmanManager.isIronman) {
                    interpreter.sendOptions(
                        "Select an Option",
                        "I no longer want to be an Iron Man",
                        "I'd like to change my Iron Man mode",
                        "What is an Iron Man?",
                        "Go Back..."
                    )
                    100
                } else {
                    interpreter.sendOptions(
                        "Select an Option",
                        "I would like to be an Iron Man.",
                        "What is an Iron Man?",
                        "Go Back..."
                    )
                    110
                }
            }

            200 -> {
                options( "Set my experience rate to 10x", "Nevermind.")
                stage++
            }

            201 -> when (buttonId) {
                1 -> {
                    npc(FacialExpression.FRIENDLY,
                        "Tada! Your experience rate is now 10x.",
                        "Happy Scaping!"
                    )
                    player.getSkills().experienceMultiplier = 10.0
                    stage = 50
                }

                2 -> end()
            }
        }
        return true
    }

    private val playerTime: Unit
        private get() {

            //TODO:
            // Find the Date Joined and Time Played variables for the player WITHOUT directly connecting to the SQL database here
            // Split the Time Played variable into Days, Hours and Minutes
            // Insert each calculation into the timePlayed array ( 0 for Days, 1 for Hours and 2 for Minutes)
            // Calculate the Days Since registering by subtracting the Date Joined from the Current Server Date (ServerDate - Join_Date)
            // Insert the date difference into joinDateDays variable
            // return;???
        }

    override fun getIds(): IntArray {
        return intArrayOf(0)
    }
}
