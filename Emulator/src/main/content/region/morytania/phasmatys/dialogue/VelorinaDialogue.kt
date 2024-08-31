package content.region.morytania.phasmatys.dialogue

import cfg.consts.Items
import cfg.consts.NPCs
import content.region.morytania.phasmatys.quest.ahoy.dialogue.VelorinaDialogueFile
import core.api.*
import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Velorina dialogue.
 */
@Initializable
class VelorinaDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Info: Ghost living in the northern house in Port Phasmatys.
     * She desires the ability to pass over to the afterlife.
     * Location: 3678,3511
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if(!inEquipment(player, Items.GHOSTSPEAK_AMULET_552)){
            npc("Woooo wooo wooooo woooo")
            stage = 4
        }
        when {
            isQuestComplete(player, "Ghosts Ahoy") -> options("I thought you were going to pass over to the next world.", "Can I have another Ectophial?")
            !isQuestComplete(player, "Ghosts Ahoy") -> openDialogue(player, VelorinaDialogueFile())
            else -> sendMessage(player, "She is ignoring you.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        val hasEctophial = hasAnItem(player, Items.ECTOPHIAL_4251, Items.ECTOPHIAL_4252).container != null
        when (stage) {
            0 -> when (buttonId) {
                1 -> player("I thought you were going to pass over", " to the next world.").also { stage++ }
                2 -> player("Can I have another Ectophial?").also { stage = 2 }
            }
            1 -> npc("All in good time, Player. We stand forever", "in your debt, and will certainly put in a good word", "for you when we pass over.").also { stage = END_DIALOGUE }
            2 -> {
                if (!hasEctophial) {
                    npc("Of course you can, you have helped us more than we", "could ever have hoped.").also { stage++ }
                } else {
                    npc("You already have an ectophial.").also { stage = END_DIALOGUE }
                }
            }
            3 -> {
                end()
                sendItemDialogue(player, Items.ECTOPHIAL_4251, "Velorina gives you a vial of bright green ectoplasm.")
                addItemOrDrop(player, Items.ECTOPHIAL_4251)
            }
            4 -> sendDialogue(player, "You cannot understand the ghost.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.VELORINA_1683)
    }

}
