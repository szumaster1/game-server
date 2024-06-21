package content.region.kandarin.handlers.guilds.wizard

import content.region.kandarin.quest.zogreflesheaters.dialogue.ZavisticRarveDefaultDialogue
import content.region.kandarin.quest.zogreflesheaters.dialogue.ZavisticRarveDialogueFiles
import core.api.consts.NPCs
import core.api.isQuestComplete
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

@Initializable
class ZavisticRarveDialogue(player: Player? = null) : Dialogue(player) {

    /*
     *  Info: Grand Secretary of the Wizards' Guild.
     */

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        end()
        if (isQuestComplete(player, "Zogre Flesh Eaters"))
            openDialogue(player, ZavisticRarveDefaultDialogue())
         else
             openDialogue(player, ZavisticRarveDialogueFiles())
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ZAVISTIC_RARVE_2059)
    }

}