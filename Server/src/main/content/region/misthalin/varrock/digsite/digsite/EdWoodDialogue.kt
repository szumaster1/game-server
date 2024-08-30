package content.region.misthalin.varrock.digsite.digsite

import core.api.sendChat
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import cfg.consts.NPCs

/**
 * Represents the Ed Wood dialogue.
 */
@Initializable
class EdWoodDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        sendChat(npc, arrayOf("Can't stop. Too busy.", "Wonder when I'll get paid.", "Is it lunch break yet?", "Hey I'm working here. I'm working here.", "This work isn't going to do itself.", "Ouch! That was my finger!").random())
        stage = END_DIALOGUE
        return true
    }

    override fun newInstance(player: Player): Dialogue {
        return EdWoodDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ED_WOOD_5964)
    }

}
