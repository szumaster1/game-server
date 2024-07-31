package content.region.karamja.dialogue.brimhaven

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class CharlieTheCookDialogue(player: Player? = null): Dialogue(player) {

    /*
        The chef (and part owner) for The Shrimp and Parrot restaurant in Brimhaven.
        He is a member of the Phoenix Gang.
        Location: 2793,3191
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc("Hey! What are you doing back here?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("I'm looking for a gherkin...", "I'm a fellow member of the Phoenix Gang.", "Just exploring.").also { stage++ }
            1 -> when (buttonId) {
                1 -> player("I'm looking for a gherkin...").also { stage = 3 }
                2 -> player("I'm a fellow member of the Phoenix Gang.").also { stage = 3 }
                3 -> player("Just exploring.").also { stage = 2 }
            }
            2 -> npc("Well, get out! This kitchen isn't for exploring.", "It's a private establishment! It's out of bounds", "to customers!").also { stage = END_DIALOGUE }
            3 -> npc("Ah, a fellow Phoenix! So, tell me compadre...", "What brings you to sunny Brimhaven?").also { stage++ }
            4 -> options("Sun, sand, and the fresh sea air!", "I want to steal Scarface Pete's candlesticks.").also { stage++ }
            5 -> when (buttonId) {
                1 -> player("Sun, sand, and the fresh sea air!").also { stage++ }
                2 -> player("I want to steal Scarface Pete's candlesticks.").also { stage = 8 }
            }
            6 -> npc("Well, can't say I blame you, compadre.", "I used to be a city boy myself, but have to admit ", "it's a lot nicer living here nowadays. Brimhaven's", "certainly good for it.").also { stage++ }
            7 -> npc("I also want to steal Scarface Pete's candlesticks.").also { stage++ }
            8 -> npc("Ah yes, of course. The candlesticks.", "Well, I have to be honest with you, compadre, we haven't made", "much progress in that task ourselves so far.").also { stage++ }
            9 -> npc("We can however offer a little assistance.", "Setting up this restaurant was the start of things;", "we have a secret door out the back of here that leads", "through the back of Cap'n Arnav's garden.").also { stage++ }
            10 -> npc("Now, at the other side of Cap'n Arnav's garden,", "is an old side entrance to Scarface Pete's mansion.", "It seems to have been blocked off from the rest of", "the mansion some years ago and we can't seem to find a way through.").also { stage++ }
            11 -> npc("We're positive this is the key to entering", "the house undetected, however, and I promise to let", "you know if we find anything there.").also { stage++ }
            12 -> player("Mind if I check it out for myself?").also { stage++ }
            13 -> npc("Not at all! The more minds we have working", "on the problem, the quicker we get that loot!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.CHARLIE_THE_COOK_794)
    }

}
