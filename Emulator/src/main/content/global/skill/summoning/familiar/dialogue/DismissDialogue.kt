package content.global.skill.summoning.familiar.dialogue

import content.global.skill.summoning.pet.Pet
import core.api.sendDialogue
import core.api.sendDialogueOptions
import core.game.dialogue.Dialogue
import core.game.dialogue.DialogueInterpreter
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Dismiss dialogue.
 */
@Initializable
class DismissDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        if (player.familiarManager.familiar is content.global.skill.summoning.pet.Pet) {
            sendDialogueOptions(player, "Free pet?", "Yes", "No")
        } else {
            sendDialogueOptions(player,"Dismiss Familiar?", "Yes", "No")
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> when (buttonId) {
                1 -> {
                    if (player.familiarManager.familiar is content.global.skill.summoning.pet.Pet) {
                        sendDialogue(player,"Run along; I'm setting you free.")
                        val pet = player.familiarManager.familiar as content.global.skill.summoning.pet.Pet
                        player.familiarManager.removeDetails(pet.itemIdHash)
                    } else {
                        end()
                    }
                    player.familiarManager.dismiss()
                    stage = 1
                }

                2 -> end()
            }

            1 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(DialogueInterpreter.getDialogueKey("dismiss_dial"))
    }
}
