package content.region.misthalin.dialogue.stronghold.security

import core.api.Container
import core.api.addItem
import core.api.consts.Items
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.emote.Emotes

/**
 * Grain of plenty dialogue.
 */
class GrainOfPlentyDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        interpreter.sendDialogue("The grain shifts in the sack, sighing audible words....")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                if (!addItem(player, Items.COINS_995, 3000, Container.INVENTORY)) {
                    player.packetDispatch.sendMessage("You don't have enough inventory space.")
                    end()
                }
                player.getSavedData().globalData.getStrongHoldRewards()[1] = true
                interpreter.sendDialogue("...congratualtions adventurer, you have been deemed worthy of this", "reward. You have also unlocked the Slap Head emote!")
                stage = 1
                player.emoteManager.unlock(Emotes.SLAP_HEAD)
            }

            1 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(56875)
    }
}