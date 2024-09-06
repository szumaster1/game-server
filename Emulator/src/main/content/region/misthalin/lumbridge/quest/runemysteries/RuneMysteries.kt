package content.region.misthalin.lumbridge.quest.runemysteries

import cfg.consts.Components
import cfg.consts.Items
import cfg.consts.Vars
import core.api.sendItemZoomOnInterface
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.quest.Quest
import core.plugin.Initializable

/**
 * Represents the Rune mysteries.
 */
@Initializable
class RuneMysteries: Quest("Rune Mysteries", 27, 26, 1, Vars.VARP_QUEST_RUNE_MYSTERIES_PROGRESS, 0, 1, 6) {


    override fun drawJournal(player: Player, stage: Int) {
        super.drawJournal(player, stage)
        var line = 11

        if (getStage(player) == 0) {
            line(player, "I can start this quest by speaking to !!Duke Horacio?? of", line++)
            line(player, "!!Lumbridge??, upstairs in !!Lumbridge Castle??.", line++)
            line++
        }
        if (getStage(player) == 10) {
            line(player, "I spoke to Duke Horacio and he showed me a strange", line++)
            line(player, "talisman that had been found by one of his subjects.", line++)
            line(player, "I agreed to take it to the Wizards' Tower, South West of", line++)
            line(player, "Lumbridge for further examination by the wizards.", line++)
            line(player, "I need to find the !!Head Wizard?? and give him the !!Talisman??", line++)
            line++
        }
        if (getStage(player) == 20) {
            line(player, "I spoke to Duke Horacio and he showed me a strange", line++)
            line(player, "talisman that had been found by one of his subjects.", line++)
            line(player, "I agreed to take it to the Wizards' Tower, South West of", line++)
            line(player, "Lumbridge for further examination by the wizards.", line++)
            line(player, "I gave the Talisman to the Wizard but I didn't want to help", line++)
            line(player, "him in his research right now.", line++)
            line(player, "I should talk to !!Sedridor?? again to continue this quest.", line++)
            line++
        }
        if (getStage(player) == 30) {
            line(player, "I spoke to Duke Horacio and he showed me a strange", line++)
            line(player, "talisman that had been found by one of his subjects.", line++)
            line(player, "I agreed to take it to the Wizards' Tower, South West of", line++)
            line(player, "Lumbridge for further examination by the wizards.", line++)
            line(player, "I gave the Talisman to the Head of the Tower and", line++)
            line(player, "agreed to help him with his research into rune stones.", line++)
            line(player, "I should take this !!Research Package?? to !!Aubury?? in !!Varrock??", line++)
            line++
        }
        if (getStage(player) == 40) {
            line(player, "I spoke to Duke Horacio and he showed me a strange", line++)
            line(player, "talisman that had been found by one of his subjects.", line++)
            line(player, "I agreed to take it to the Wizards' Tower, South West of", line++)
            line(player, "Lumbridge for further examination by the wizards.", line++)
            line(player, "I gave the Talisman to the Head of the Tower and", line++)
            line(player, "agreed to help him with his research into rune stones.", line++)
            line(player, "I took the research package to Varrock and delivered it.", line++)
            line(player, "I should speak to !!Aubury?? again when he has finished", line++)
            line(player, "examining the !!research package?? I have delivered to him.", line++)
            line++
        }
        if (getStage(player) == 50) {
            line(player, "I spoke to Duke Horacio and he showed me a strange", line++)
            line(player, "talisman that had been found by one of his subjects.", line++)
            line(player, "I agreed to take it to the Wizards' Tower, South West of", line++)
            line(player, "Lumbridge for further examination by the wizards.", line++)
            line(player, "I gave the Talisman to the Head of the Tower and", line++)
            line(player, "agreed to help him with his research into rune stones.", line++)
            line(player, "I took the research package to Varrock and delivered it.", line++)
            line(player, "Aubury was interested in the research package and gave", line++)
            line(player, "me his own research notes to deliver to Sedridor.", line++)
            line(player, "I should take the !!notes?? to !!Sedridor?? and see what he says", line++)
            line++
        }
        if (stage == 100) {
            line(player, "I spoke to Duke Horacio and he showed me a strange", line++)
            line(player, "talisman that had been found by one of his subjects.", line++)
            line(player, "I agreed to take it to the Wizards' Tower, South West of", line++)
            line(player, "Lumbridge for further examination by the wizards.", line++)
            line(player, "I gave the Talisman to the Head of the Tower and", line++)
            line(player, "agreed to help him with his research into rune stones.", line++)
            line(player, "I took the research package to Varrock and delivered it.", line++)
            line(player, "Aubury was interested in the research package and gave", line++)
            line(player, "me his own research notes to deliver to Sedridor.", line++)
            line(player, "I brought Sedridor the research notes that Aubury had", line++)
            line(player, "compiled so that he could compare their research. They", line++)
            line(player, "They discovered that it was now possible to create new rune", line++)
            line(player, "stones, a skill that had been thought lost forever.", line++)
            line(player, "In return for all of my help they taught me how to do this,", line++)
            line(player, "and will teleport me to mine blank runes anytime.", line++)
            line++
            line(player, "<col=FF0000>QUEST COMPLETE!", line)
        }
    }

    override fun finish(player: Player) {
        super.finish(player)
        var line = 10
        drawReward(player,"1 Quest Point", line++)
        drawReward(player,"Runecrafting skill", line++)
        drawReward(player,"Air talisman", line++)
        drawReward(player,"You have completed the Rune Mysteries Quest!", line)
        sendItemZoomOnInterface(player, Components.QUEST_COMPLETE_SCROLL_277, 5, Items.AIR_TALISMAN_1438, 240)
    }

    override fun newInstance(`object`: Any?): Quest {
        return this
    }
}