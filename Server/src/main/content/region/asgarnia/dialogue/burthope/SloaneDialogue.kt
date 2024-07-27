package content.region.asgarnia.dialogue.burthope

import core.api.consts.NPCs
import core.api.openDialogue
import core.game.dialogue.DialogueFile
import core.game.global.Skillcape.isMaster
import core.game.global.Skillcape.purchase
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.node.entity.skill.Skills
import core.tools.END_DIALOGUE

class SloaneDialogue : DialogueFile(), InteractionListener {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.SLOANE_4297)
        when (stage) {
            0 -> if (isMaster(player!!, Skills.STRENGTH)) {
                options("Ask about Skillcape", "Something else").also { stage++ }
            } else {
                npc("Ahhh, hello there, " + player!!.username + ".").also { stage = 10 }
            }
            1 -> when (buttonID) {
                1 -> player("Can I buy a Skillcape of Strength?").also { stage = 100 }
                2 -> npc("Ahhh, hello there, " + player!!.username + ".").also { stage++ }

            }
            10 -> options("What can I do here?", "That's a big axe!", "May I claim my tokens please?", "Bye!").also { stage++ }
            11 -> when (buttonID) {
                1 -> player("What can I do here?").also { stage = 12 }
                2 -> player("That's a big axe!").also { stage = 30 }
                3 -> {
                    end()
                    openDialogue(player!!,"wg:claim-tokens", npc!!.id)
                }
                4 -> player("Bye!").also { stage = END_DIALOGUE }
            }
            12 -> npc("Ahh, the shot put is a great test of strength and can be", "quite rewarding. Mind you do it properly though, you", "might want to dust your hands with some powdery", "substance first. It'll give better grip.").also { stage = END_DIALOGUE }
            30 -> npc("Yes indeed it is. Have to be mighty strong to wield it", "too.").also { stage++ }
            31 -> player("But you don't look that strong!").also { stage++ }
            32 -> npc("Maybe, maybe not, but I still had to beat a Barbarian", "to get it. Mind you, usually they don't part with them.", "This was an unusual circumstance.").also { stage = END_DIALOGUE }
            50 -> npc("Be well, warrior " + player!!.username + ".").also { stage = END_DIALOGUE }
            40 -> {
                end()
                openDialogue(player!!, "wg:claim-tokens", npc!!.id)
            }
            100 -> npc("Certainly! Right when you give me 99000 coins.").also { stage++ }
            101 -> options("Okay, here you go.", "No, thanks.").also { stage++ }
            102 -> when (buttonID) {
                1 -> player("Okay, here you go.").also { stage++ }
                2 -> end()
            }
            103 -> {
                end()
                if (purchase(player!!, Skills.STRENGTH)) {
                    npc("There you go! Enjoy.")
                }
            }
        }
    }

    override fun defineListeners() {
        on(NPCs.SLOANE_4297, IntType.NPC, "Talk-to") { player, _ ->
            openDialogue(player, SloaneDialogue())
            return@on true
        }
    }

}
