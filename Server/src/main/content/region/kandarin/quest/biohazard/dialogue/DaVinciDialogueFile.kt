package content.region.kandarin.quest.biohazard.dialogue

import content.region.kandarin.quest.biohazard.util.BiohazardUtils
import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.dialogue.DialogueFile
import core.game.node.entity.npc.NPC

/**
 * Da vinci dialogue file
 *
 * @constructor Da vinci dialogue file
 */
class DaVinciDialogueFile : DialogueFile() {
    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.DA_VINCI_336)
        when (stage) {
            0 -> player("Hello, I hear you're an errand boy for the chemist.").also { stage++ }
            1 -> npc(
                "Well that's my job yes. But I don't necessarily define",
                "my identity in such black and white terms."
            ).also { stage++ }

            2 -> player("Good for you. Now can you take a vial to Varrock for", "me?").also { stage++ }
            3 -> npc("Go on then.").also { stage++ }
            4 -> options(
                "You give him the vial of ethenea...",
                "You give him the vial of liquid honey...",
                "You give him the vial of sulphuric broline..."
            ).also { stage++ }

            5 -> when (buttonID) {
                1 -> {
                    if (!removeItem(player!!, Items.ETHENEA_415)) {
                        end()
                        sendMessage(player!!, "You can't give him what you don't have.")
                    } else {
                        sendMessage(player!!, "You give him the vial of ethenea.")
                        player("Right. I'll see you later in the Dancing Donkey Inn.")
                        stage++
                    }
                }

                2 -> {
                    if (!removeItem(player!!, Items.LIQUID_HONEY_416)) {
                        end()
                        sendMessage(player!!, "You can't give him what you don't have.")
                    } else {
                        sendMessage(player!!, "You give him the vial of liquid honey.")
                        stage = 8
                    }
                }

                3 -> {
                    if (!removeItem(player!!, Items.SULPHURIC_BROLINE_417)) {
                        end()
                        sendMessage(player!!, "You can't give him what you don't have.")
                    } else {
                        sendMessage(player!!, "You give him the vial of sulphuric broline.")
                        stage = 8
                    }
                }
            }

            6 -> npc("That's right.").also { stage++ }
            7 -> {
                end()
                setAttribute(player!!, BiohazardUtils.SECOND_VIAL_CORRECT, true)
            }

            8 -> {
                end()
                //setAttribute(player!!, BiohazardUtils.SECOND_VIAL_WRONG, true)
            }
        }
    }
}

/**
 * Da vinci varrock dialogue file
 *
 * @constructor Da vinci varrock dialogue file
 */
class DaVinciVarrockDialogueFile : DialogueFile() {
    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.DA_VINCI_337)
        when (stage) {
            0 -> npc("Hello again. I hope your journey was as pleasant as", "mine.").also { stage++ }
            1 -> player("Well, as they say, it's always sunny in Gielinor.").also { stage++ }
            2 -> npc("Ok, here it is.").also { stage++ }
            3 -> {
                end()
                sendMessage(player!!, "He gives you the vial of ethenea.")
                player("Thanks, you've been a big help.")
                addItemOrDrop(player!!, Items.ETHENEA_415)
                removeAttribute(player!!, BiohazardUtils.SECOND_VIAL_CORRECT)
            }
        }
    }
}
