package content.region.misthalin.dialogue.varrock.museum

import core.api.consts.NPCs
import content.region.desert.quest.thegolemquest.dialogue.CuratorHaigHalenGolemDialogue
import content.region.misthalin.dialogue.digsite.CuratorHaigHelenDialogueFile
import content.region.misthalin.quest.free.shieldofarrav.dialogue.CuratorHaigHalenSOADialogue
import core.api.getQuestStage
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE


@Initializable
class CuratorHaigHalenDialogue(player: Player? = null) : Dialogue(player) {

    /*
     *  Info: Curator of the Varrock Museum.
     *  He can be found on the ground floor.
     *  Location: 3257,3447
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (getQuestStage(player, "Shield of Arrav") == 70 && getQuestStage(player, "The Golem") == 3 && getQuestStage(player, "The Dig Site") == 3) {
            options("Talk about quest. (Shield of Arrav)", "Talk about quest. (The Golem)", "Talk about quest. (The Dig Site)", "Nevermind.")
            stage = 0
        } else if (getQuestStage(player, "Shield of Arrav") == 70) {
            end()
            openDialogue(player, CuratorHaigHalenSOADialogue())
        } else if (getQuestStage(player, "The Golem") >= 3) {
            end()
            openDialogue(player, CuratorHaigHalenGolemDialogue())
        } else if (getQuestStage(player, "The Dig Site") > 1) {
            end()
            openDialogue(player, CuratorHaigHelenDialogueFile())
        } else {
            npc(FacialExpression.HAPPY, "Welcome to the museum of Varrock.")
            stage = END_DIALOGUE
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> when (buttonId) {
                1 -> {
                    end(); openDialogue(player, CuratorHaigHalenDialogue())
                }
                2 -> {
                    end(); openDialogue(player, CuratorHaigHalenGolemDialogue())
                }
                3 -> {
                    end(); openDialogue(player, CuratorHaigHalenDialogue())
                }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.CURATOR_HAIG_HALEN_646)
    }

}
