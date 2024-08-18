package content.region.desert.dialogue.pollnivneach

import core.api.consts.NPCs
import core.api.openNpcShop
import core.api.sendDialogue
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE

/**
 * Represents the Ali the barman dialogue.
 */
class AliTheBarmanDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.ALI_THE_BARMAN_1864)
        when(stage){
            0 -> player("Hello there.").also { stage++ }
            1 -> npc("Good day. Can I help you with anything?").also { stage++ }
            2 -> options("Yes, I'd like a drink please.", "What's going on in town?", "The 'Asp and Snake'? What a strange name for a bar.").also { stage++ }
            3 -> when(buttonID){
                1 -> player("Yes, I'd like a drink please.").also { stage++ }
                2 -> player("What's going on in town?").also { stage = 5 }
                3 -> player("The 'Asp and Snake'? What a strange name for a bar.").also { stage = 6 }
            }
            4 -> {
                end()
                openNpcShop(player!!, NPCs.ALI_THE_BARMAN_1864)
            }
            5 -> sendDialogue(player!!, "Transcript missing.").also { stage = END_DIALOGUE }
            6 -> npcl(FacialExpression.HALF_ASKING, "I know what you're thinking, asps are a type of snake, right?").also { stage++ }
            7 -> npcl(FacialExpression.HALF_ASKING, "I admit it is a little confusing, but neither 'The Asp' nor 'The Snake' have quite the same ring to them.").also { stage++ }
            8 -> player("I get your point. I quite like the name.").also { stage = END_DIALOGUE }
        }
    }
}

/*
            (After The Feud:) Could you tell Traitorous Hesham that someone has poisoned his drink.
            Player:
            Could you tell Traitorous Hesham that someone has poisoned his drink.
            Faisal the Barman:
            What! Drink being poisoned in my bar! My reputation could be destroyed if someone died here. Thank you adventurer.
            Player:
            No problem at all.
            Faisal the Barman:
            Here have a beer - it's on the house.
            Select an option
            Thanks!
            Player receives beer.
            Faisal the barman gives you a beer.
            Congratulations! You have completed: 'Free Beer Here' - Tell Faisal the Barman that Traitorous Hesham's drink was poisoned.
            Thanks but no thanks, I don't drink.
            Player:
            Thanks but no thanks, I don't drink.
            Faisal the Barman:
            Don't say that around here too loud please, it may catch and then I'd be out of business.
            Player:
            You could always open up a non alcoholic fruit juice bar. You'd have less problems with drunken customers.
            Faisal the Barman:
            You might be onto something there, but then what would I do with the drunken sailor?
            Player:
            Who?
            Faisal the Barman:
            Jalal the Drunk. Oh never mind its a long story.
            Congratulations! You have completed: 'Free Beer Here' - Tell Faisal the Barman that Traitorous Hesham's drink was poisoned.
            Yuck! I don't like beer.
            Faisal the Barman:
            Fine, no need to insult me then.
            Congratulations! You have completed: 'Free Beer Here' - Tell Faisal the Barman that Traitorous Hesham's drink was poisoned.
*/
