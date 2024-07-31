package content.region.kandarin.dialogue.feldip.gutanoth

import core.api.consts.NPCs
import core.api.sendMessage
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

@Initializable
class OgreGuardNWDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.OLD_DEFAULT, "Stop, creature! Only ogres and their friends allowed in this city. Show me a sign of companionship, like a lost relic or somefing, and you may pass.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.OLD_ANGRY1, "Until then, back to whence you came!").also { stage++ }
            1 -> {
                end()
                sendMessage(player, "The guard pushes you back down the hill.")
            }
        }
        return true
    }


    override fun getIds(): IntArray {
        return intArrayOf(NPCs.OGRE_GUARD_859)
    }
}