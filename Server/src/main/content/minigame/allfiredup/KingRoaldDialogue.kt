package content.minigame.allfiredup

import core.api.consts.Items
import core.api.getAttribute
import core.api.removeAttribute
import core.game.dialogue.DialogueFile
import core.game.node.item.Item
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * King roald AFU dialogue.
 */
class KingRoaldAFUDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        when (stage) {
            START_DIALOGUE -> npc("Did what?").also { stage++ }
            1 -> {
                if (getAttribute(player!!,"afu-mini:adze", false)) {
                    player("I lit all 14 beacons at once!")
                } else if (getAttribute(player!!,"afu-mini:gloves", false)) {
                    player("I lit 10 beacons at once!")
                } else if (getAttribute(player!!,"afu-mini:ring", false)) {
                    player("I lit 6 beacons at once!")
                }
                stage++
            }

            2 -> {
                npc("Oh, wonderful! Here is your reward then.")
                if (getAttribute(player!!,"afu-mini:adze", false)) if (player!!.inventory.add(Item(Items.INFERNO_ADZE_13661))) removeAttribute(player!!, "afu-mini:adze")
                if (getAttribute(player!!,"afu-mini:gloves", false)) if (player!!.inventory.add(Item(Items.FLAME_GLOVES_13660))) removeAttribute(player!!,"afu-mini:gloves")
                if (getAttribute(player!!,"afu-mini:ring", false)) if (player!!.inventory.add(Item(Items.RING_OF_FIRE_13659))) removeAttribute(player!!,"afu-mini:ring")
                stage = END_DIALOGUE
            }

            END_DIALOGUE -> end()
        }
    }
}
