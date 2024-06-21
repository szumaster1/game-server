package content.region.asgarnia.quest.bkfortress

import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.global.action.DoorActionHandler
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.map.RegionManager.getLocalNpcs
import core.game.world.map.RegionManager.getObject
import core.plugin.Initializable

@Initializable
class FortressGuardDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (args.size == 2) {
            npc(FacialExpression.ANGRY,
                "Hey! You can't come in here! This is a high security",
                "military installation!"
            )
            stage = 40
            return true
        }
        if (args.size == 3) {
            npc(FacialExpression.NEUTRAL,
                "I wouldn't go in there if I were you. Those Black",
                "Knights are in an important meeting. They said they'd",
                "kill anyone who went in there!"
            )
            stage = 50
            return true
        }
        if (!inUniform(player)) {
            npc(FacialExpression.ANGRY, "Get lost. This is private property.")
            stage = 0
        } else {
            npc(FacialExpression.FURIOUS, "Hey! Get back on duty!")
            stage = 30
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                player(FacialExpression.HALF_GUILTY, "Yes, but I work here!")
                stage = 1
            }

            1 -> {
                npc(FacialExpression.NEUTRAL,
                    "Well, this is the guards' entrance. I might be new here",
                    "but I can tell you're not a guard."
                )
                stage = 2
            }

            2 -> {
                player(FacialExpression.ASKING, "How can you tell?")
                stage = 3
            }

            3 -> {
                npc(FacialExpression.ANGRY, "You're not even wearing proper guards uniform!")
                stage = 4
            }

            4 -> {
                options( "Oh pleeeaase let me in!", "So what is this uniform?")
                stage = 5
            }

            5 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY, "Oh pleeeaaase let me in!")
                    stage = 10
                }

                2 -> {
                    player(FacialExpression.ASKING, "So what is this uniform?")
                    stage = 20
                }
            }

            10 -> {
                npc(FacialExpression.ANNOYED, "Go away. You're getting annoying.")
                stage = 11
            }

            11 -> end()
            20 -> {
                npc(FacialExpression.NEUTRAL,
                    "Well you can see me wearing it. It's an iron chainbody",
                    "and a medium bronze helm."
                )
                stage = 21
            }

            21 -> {
                player(FacialExpression.HALF_ASKING,
                    "Hmmm... I wonder if I can make that or get some in",
                    "the local towns..."
                )
                stage = 22
            }

            22 -> {
                npc(FacialExpression.HALF_ASKING, "What was that you muttered?")
                stage = 23
            }

            23 -> {
                player(FacialExpression.SUSPICIOUS, "Oh, nothing important!")
                stage = 24
            }

            24 -> end()
            30 -> {
                player(FacialExpression.SUSPICIOUS, "Uh...")
                stage = 31
            }

            31 -> end()
            40 -> {
                interpreter.sendOptions(
                    "Select an Option",
                    "Yes, but I work here!",
                    "Oh, sorry.",
                    "So who does it belong to?"
                )
                stage = 41
            }

            41 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY, "Yes, but I work here!")
                    stage = 1
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY, "Oh, sorry.")
                    stage = 42
                }

                3 -> {
                    player(FacialExpression.ASKING, "So who does it belong to?")
                    stage = 44
                }
            }

            42 -> {
                npc(FacialExpression.NEUTRAL, "Don't let it happen again.")
                stage = 43
            }

            43 -> end()
            44 -> {
                npc(FacialExpression.NEUTRAL,
                    "This fortress belongs to the order of Black Knights",
                    "known as the Kinshra."
                )
                stage = 45
            }

            45 -> {
                player(FacialExpression.FRIENDLY, "Oh. Okay, thanks.")
                stage = 46
            }

            46 -> end()
            50 -> {
                options( "Okay, I won't.", "I don't care. I'm going in anyway.")
                stage = 51
            }

            51 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.NEUTRAL, "Okay, I won't.")
                    stage = 52
                }

                2 -> {
                    player(FacialExpression.NEUTRAL, "I don't care. I'm going in anyway.")
                    stage = 54
                }
            }

            52 -> {
                npc(FacialExpression.NEUTRAL, "Wise move.")
                stage = 53
            }

            53 -> end()
            54 -> {
                end()
                DoorActionHandler.handleAutowalkDoor(player, getObject(0, 3020, 3515))
                val npcs = getLocalNpcs(player)
                for (npc in npcs) {
                    if (npc.id == 179) {
                        npc.properties.combatPulse.attack(player)
                        npc.sendChat("Die, intruder!")
                        return true
                    }
                }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(609, 4603, 4604, 4605, 4606)
    }

    companion object {
        private val UNIFORM = arrayOf(Item(1139), Item(1101))

        /**
         * In uniform boolean.
         *
         * @param player the player
         * @return the boolean
         */
        fun inUniform(player: Player): Boolean {
            for (i in UNIFORM) {
                if (!player.equipment.containsItem(i)) {
                    return false
                }
            }
            return true
        }
    }
}
