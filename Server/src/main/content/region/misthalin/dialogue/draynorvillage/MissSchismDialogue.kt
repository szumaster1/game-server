package content.region.misthalin.dialogue.draynorvillage

import core.api.consts.NPCs
import core.api.sendDialogueLines
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Miss Schism dialogue.
 */
@Initializable
class MissSchismDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HALF_GUILTY, "Oooh, my dear, have you heard the news?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                sendDialogueLines(player, "What would you like to say?", "Ok, tell me about the news.", "Who are you?", "I'm not talking to you, you horrible woman.")
                stage = 1
            }

            1 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY, "Ok, tell me about the news.")
                    stage = 10
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY, "Who are you?")
                    stage = 20
                }

                3 -> {
                    player(FacialExpression.HALF_GUILTY,
                        "I'm not talking to you, you horrible woman."
                    )
                    stage = 30
                }
            }

            10 -> {
                npc(FacialExpression.HALF_GUILTY, "Well, there's just to much to tell at once! What would", "you like to hear first: the vampire or the bank?")
                stage = 11
            }

            11 -> {
                options( "Tell me about the vampire.", "Tell me about the bank.")
                stage = 12
            }

            12 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY, "Tell me about the vampire.")
                    stage = 110
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY, "What about the bank?")
                    stage = 210
                }
            }

            110 -> stage = if (player.getQuestRepository().isComplete("Vampire Slayer")) {
                npc(FacialExpression.HALF_GUILTY, "Well, there's nothing to tell NOW. You killed it.")
                111
            } else {
                npc("There is an evil Vampire terrorizing the city!")
                119
            }

            111 -> {
                player(FacialExpression.HALF_GUILTY, "You could sound a little grateful.")
                stage = 112
            }

            112 -> {
                npc(FacialExpression.HALF_GUILTY, "I'm sure I could, but I don't see why. The vampire wasn't", "bothering me.")
                stage = 113
            }

            113 -> {
                player(FacialExpression.HALF_GUILTY, "...")
                stage = 114
            }

            114 -> end()
            119 -> {
                player("Oh, that's not good.")
                stage = 120
            }

            120 -> end()
            210 -> {
                npc(FacialExpression.HALF_GUILTY, "It's terrible, absolutely terrible! Those poor people!")
                stage = 211
            }

            211 -> {
                player(FacialExpression.HALF_GUILTY, "Ok, yeah.")
                stage = 212
            }

            212 -> {
                npc(FacialExpression.HALF_GUILTY, "And who'd have ever thought such a sweet old gentleman", "would do such a thing?")
                stage = 213
            }

            213 -> {
                player(FacialExpression.HALF_GUILTY, "Are we talking about the bank robbery?")
                stage = 214
            }

            214 -> {
                npc(FacialExpression.HALF_GUILTY, "Oh yes, my dear. It was terrible! TERRIBLE!")
                stage = 215
            }

            215 -> end()
            20 -> {
                npc(FacialExpression.HALF_GUILTY, "I, my dear, am a concerned citizen of Draynor Village.", "Ever since the Council allowed those farmers to set up", "their stalls here, we've had a constant flow of thieves and", "murderers through our fair village, and I decided that")
                stage = 21
            }

            21 -> {
                npc(FacialExpression.HALF_GUILTY, "someone HAD to stand up and", "keep an eye on the situation.")
                stage = 22
            }

            22 -> {
                npc(FacialExpression.HALF_GUILTY, "I also do voluntary work for the Draynor Manor", "Restoration Fund. We're campaigning to have", "Draynor manor turned into a museum before the wet-rot", "destroys it completely.")
                stage = 23
            }

            23 -> if (player.getQuestRepository().isComplete("Vampire Slayer")) {
                player(FacialExpression.HALF_GUILTY, "Well, now that I've cleared the vampire out of the manor,", "I guess you won't have too much trouble turning it into a", "museum.")
                stage = 24
            } else {
                end()
            }

            24 -> {
                npc(FacialExpression.HALF_GUILTY, "That's all very well dear, but no vampire was ever going to", "stop me making it a museum.")
                stage = 25
            }

            25 -> end()
            30 -> {
                npc(FacialExpression.HALF_GUILTY, "Oooh.")
                stage = 31
            }

            31 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MISS_SCHISM_2634)
    }
}