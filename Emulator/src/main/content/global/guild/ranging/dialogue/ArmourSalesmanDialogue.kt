package content.global.guild.ranging.dialogue

import cfg.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.global.Skillcape.isMaster
import core.game.global.Skillcape.purchase
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.plugin.Initializable

/**
 * Armour salesman dialogue.
 */
@Initializable
class ArmourSalesmanDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player("Good day to you.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc("And to you. Can I help you?").also { stage++ }
            1 -> {
                if (isMaster(player, Skills.RANGE)) {
                    options("What do you do here?", "I'd like to see what you sell.", "Can I buy a Skillcape of Range?", "I've seen enough, thanks.")
                    stage = 100
                } else {
                    options("What do you do here?", "I'd like to see what you sell.", "I've seen enough, thanks.")
                    stage = 2
                }
            }

            2 -> when (buttonId) {
                1 -> {
                    player("What do you do here?")
                    stage = 10
                }

                2 -> {
                    player("I'd like to see what you sell.")
                    stage = 20
                }

                3 -> {
                    player("I've seen enough, thanks.")
                    stage = 30
                }
            }

            10 -> {
                npc("I am a supplier of leather armours and accessories. Ask", "and I will tell you what I know.")
                stage = 11
            }

            11 -> {
                options("Tell me about your armours.", "Tell me about your accessories.", "I've seen enough, thanks.")
                stage = 12
            }

            12 -> when (buttonId) {
                1 -> {
                    player("Tell me about your armours.")
                    stage = 13
                }

                2 -> {
                    player("Tell me about your accessories.")
                    stage = 15
                }

                3 -> {
                    player("I've seen enough, thanks.")
                    stage = 30
                }
            }

            13 -> {
                npc("I have normal, studded and hard types.")
                stage = 14
            }

            14, 31, 105 -> end()
            15 -> {
                npc(
                    "Ah yes we have a new range of accessories in stock.",
                    "Essential items for an archer like you."
                )
                stage = 14
            }

            20 -> {
                npc("Indeed, cast your eyes on my wares, adventurer.")
                stage = 21
            }

            21 -> {
                end()
                openNpcShop(player, npc.id)
            }

            30 -> {
                npc("Very good, adventurer.")
                stage = 31
            }

            100 -> when (buttonId) {
                1 -> {
                    player("What do you do here?")
                    stage = 10
                }

                2 -> {
                    player("I'd like to see what you sell.")
                    stage = 20
                }

                3 -> {
                    player("Can I buy a Skillcape of Range?")
                    stage = 101
                }

                4 -> {
                    player("I've seen enough, thanks.")
                    stage = 30
                }
            }

            101 -> {
                npc("Certainly! Right when you give me 99000 coins.")
                stage = 102
            }

            102 -> {
                options("Okay, here you go.", "No, thanks.")
                stage = 103
            }

            103 -> when (buttonId) {
                1 -> {
                    player("Okay, here you go.")
                    stage = 104
                }

                2 -> end()
            }

            104 -> {
                if (purchase(player, Skills.RANGE)) {
                    npc("There you go! Enjoy.")
                }
                stage = 105
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ARMOUR_SALESMAN_682)
    }
}
