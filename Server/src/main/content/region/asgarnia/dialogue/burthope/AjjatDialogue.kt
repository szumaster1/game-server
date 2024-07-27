package content.region.asgarnia.dialogue.burthope

import core.api.consts.NPCs
import core.api.getStatLevel
import core.api.openDialogue
import core.game.dialogue.DialogueFile
import core.game.global.Skillcape.purchase
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.node.entity.skill.Skills
import core.tools.END_DIALOGUE

class AjjatDialogue : DialogueFile(), InteractionListener {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.AJJAT_4288)
        when (stage) {
            0 -> npc("Greetings, fellow warrior. I am Ajjat, former Black Knight", "and now training officer here in the Warriors' Guild.").also { stage++ }
            1 -> options("Can you tell me about skillcapes, please?", "Black knight? Why are you here?", "What's the dummy room all about?", "May I claim my tokens please?", "Bye!").also { stage++ }
            2 -> when (buttonID) {
                1 -> player("Can you tell me about skillcapes, please?").also { stage++ }
                2 -> player("Black knight? Why are you here?").also { stage = 5 }
                3 -> player("What's the dummy room all about?").also { stage = 7 }
                4 -> {
                    end()
                    openDialogue(player!!, "wg:claim-tokens", npc!!.id)
                }
                5 -> player("Bye!").also { stage = 13 }
            }
            3 -> npc("Skillcapes, also knows as Capes of Accomplishment, are", "reserved for the elite of the elite. Only a person who has", "truly mastered a skill can buy one, even then a", "Skillcape can only be bought from one who is recognised as").also { stage++ }
            4 -> npc("the highest skilled in the land at any particular skill. I", "have the privilege of being the person that controls", "access to the Skillcape of Attack. Is there anything else I", "can help you with?").also { stage = 14 }
            5 -> npc("Indeed I was however, their... methods did not match", "with my ideals, so I left. Harrallak, recognizing my talent as", "a warrior, took me in and offered me a job here.").also { stage++ }
            6 -> player("Hmm...well, if Harrallak trusts you, I guess I can.").also { stage = 0 }
            7 -> npc("Ahh yes, the dummies. Another ingenious invention of the", "noble dwarf, Gamfred. They're mechanical, you see, and", "pop up out of the floor. You have to hit them with the", "correct attack mode before they disappear again.").also { stage++ }
            8 -> player("Do, how do I tell which one is which?").also { stage++ }
            9 -> npc("here are two different ways. One indication is their", "colour, the other is the pose and weapons they are", "holding, for instance, the one holding daggers you will", "to hit with a piercing attack.").also { stage++ }
            10 -> npc("In the room, you will find a poster on the wall to", " help you recognize each different dummy.").also { stage++ }
            11 -> player("That sounds ingenious!").also { stage++ }
            12 -> npc("Indeed, you may find that you need several weapons to", "be successfully all of the time, but keep trying", "The weapons Shop upstairs may help you there.").also { stage = 0 }
            13 -> npc("Farewell, warrior. Stay away from the dark side.").also { stage = END_DIALOGUE }
            14 -> {
                if (getStatLevel(player!!, Skills.ATTACK) == 99) {
                    player("I'd like to buy a skillcape of Attack.").also { stage++ }
                } else {
                    end()
                }
            }
            15 -> npc("Okay, it will cost you 99000 coins", "is that ok?").also { stage++ }
            16 -> options("Yes.", "No.").also { stage++ }
            17 -> when (buttonID) {
                1 -> player("Yes.").also { stage++ }
                2 -> end()
            }
            18 -> if (purchase(player!!, Skills.ATTACK)) {
                npc("There you go! Enjoy.").also { stage = END_DIALOGUE }
            }
        }
    }

    override fun defineListeners() {
        on(NPCs.AJJAT_4288, IntType.NPC, "Talk-to") { player, _ ->
            openDialogue(player, AjjatDialogue())
            return@on true
        }
    }

}
