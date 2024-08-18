package content.region.asgarnia.dialogue.portsarim

import core.api.consts.NPCs
import core.api.isQuestComplete
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Ahab dialogue.
 */
@Initializable
class AhabDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (args.size > 1) {
            val isTelegrab = args[1] as Boolean
            player.debug("" + isTelegrab)
            if (!isTelegrab) {
                npc(FacialExpression.FURIOUS, "Oi! Get yer hands off me beer!")
            } else {
                npc(FacialExpression.FURIOUS, "Don't ye go castin' spells on me beer!")
            }
            stage = 301
        } else {
            npc("Arrr, matey!")
            stage = 0
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                options("Arrr!", "Are you going to sit there all day?", "Do you have anything for trade?")
                stage = 1
            }

            1 -> when (buttonId) {
                1 -> {
                    player("Arrr!")
                    stage = 10
                }

                2 -> {
                    player("Are you going to sit there all day?")
                    stage = 20
                }

                3 -> {
                    player("Do you have anything for trade?")
                    stage = 300
                }
            }

            10 -> {
                npc("Arrr, matey!")
                stage = 0
            }

            20 -> {
                npc("Aye, I am. I canna walk, ye see.")
                stage = 21
            }

            21 -> {
                player("What's stopping you from walking?")
                stage = 22
            }

            22 -> {
                npc("Arrr, I'ave only the one leg! I lost its twin when my last", "ship went down.")
                stage = 23
            }

            23 -> {
                player("But I can see both your legs!")
                stage = 24
            }

            24 -> {
                npc(
                    "Nay, young laddie, this be a false leg. For years I had me a",
                    "sturdy wooden peg-leg, but now I wear this dainty little",
                    "feller."
                )
                stage = 25
            }

            25 -> {
                npc("Yon peg-leg kept getting stuck in the floorboards.")
                stage = 26
            }

            26 -> {
                player("Right...")
                stage = 27
            }

            27 -> {
                npc(
                    "Perhaps a bright young laddie like yerself would like to",
                    "help me? I be needing another ship to go a-hunting my",
                    "enemy."
                )
                stage = 28
            }

            28 -> if (!isQuestComplete(player, "Dragon Slayer")) {
                player("No, I don't have a ship.")
                stage = 294
            } else {
                player("Well, I do have a ship that I'm not using.", "It's the Lady Lumbridge.")
                stage = 29
            }

            294 -> {
                npc("Arr matey... You make me sad.")
                stage = 295
            }

            295 -> end()
            29 -> {
                npc("Arrr! That ship be known to me, and a fine lass she is.")
                stage = 30
            }

            30 -> {
                player("I suppose she might be...")
                stage = 31
            }

            31 -> {
                npc("So would ye be kind enough to let", "me take her out to sea?")
                stage = 32
            }

            32 -> {
                player("I had to pay 2000gp for that ship! ", "Have you got that much?")
                stage = 33
            }

            33 -> {
                npc("Nay, I have nary a penny to my name. All my wordly goods", "went down with me old ship.")
                stage = 34
            }

            34 -> {
                player("So you're actually asking me to give you a free ship.")
                stage = 35
            }

            35 -> {
                npc("Arrr! Would ye be so kind?")
                stage = 36
            }

            36 -> {
                player("No I jolly well wouldn't!")
                stage = 37
            }

            37 -> {
                npc("Arrr.")
                stage = 38
            }

            38 -> end()
            300 -> {
                npc("Nothin' at the moment, but then again the Customs", "Agents are on the warpath right now.")
                stage = 301
            }

            301 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.AHAB_2692)
    }

}
