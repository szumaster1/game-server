package content.miniquest.gshadow.dialogue

import core.api.*
import cfg.consts.Items
import cfg.consts.NPCs
import content.miniquest.gshadow.GSUtils
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Jungle Scout dialogue.
 */
@Initializable
class JungleScoutDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        if (getAttribute(player, GSUtils.GS_PROGRESS, 0) == 2 && inEquipment(player, Items.GHOSTSPEAK_AMULET_552)) {
            player("Hello there! General Khazard sent me.").also { stage++ }
        } else if (getAttribute(player, GSUtils.GS_COMPLETE, false)) {
            player("Hello again.").also { stage = 100 }
        } else {
            end()
            sendDialogue(player, "The Scout is too busy to talk.")
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc("You must be a great mage to be able to see me, ", "human.").also { stage++ }
            1 -> player("So, are you a ghost?").also { stage++ }
            2 -> npc("Nothing so morbid. I am a scout for Khazard, and I", "move more easily through the land unseen. There are", "enemies everywhere.").also { stage++ }
            3 -> player("If you're not a ghost, why do I need an Amulet of", "Ghostspeak to talk to you?").also { stage++ }
            4 -> npc("Those who walk fully in the Shadow Realm, be they alive or dead, become", "warped by it and speak as ghosts do. That is why I", "cannot speak to you unless you wear the amulet.").also { stage++ }
            5 -> player("Oh. Well, here's your message.").also { stage++ }
            6 -> player("Khazard says: 'The planets are nearly alignment; we", "will meet in the place of half light and ice soon. Beware", "of the others, for though they are weak and few, they", "are cunning.'").also { stage++ }
            7 -> npc("Thank you, messenger.").also { stage++ }
            8 -> player("So, how's your mission going?").also { stage++ }
            9 -> npc("There is nothing pleasant about this land. The animals", "are vile, the temperature is unbearable and the plant life", "is often more dangerous than the animals.").also { stage++ }
            10 -> if (isQuestComplete(player, "Jungle Potion")) {
                npc("There were some strange occurrences in Tai Bwo", "Wannai. The people fled their village because their gods", "were angry with them, or the like, but now they have", "returned. I must investigate why.").also { stage++ }
            } else {
                player("Well, there's always something interesting going on here.").also { stage++ }
            }
            11 -> if (isQuestComplete(player, "Shilo Village")) {
                npc("Shilo Village was overrun with the undead, but someone", "of great ability has brought the situation under control.").also { stage++ }
            } else {
                player("Well, there's always something interesting going on here.").also { stage++ }
            }
            12 -> player("Any idea where the other scouts are?").also { stage++ }
            13 -> npc("I am a scout, not a spy. I do not keep track of them.").also { stage++ }
            14 -> npc("I was told to go south, so here I am. I know one was", "to investigate the gnomes. I believe one headed towards", "Asgarnia. Another was told to go to a place of great", "heat, but that they would have to sneak through a pass.").also { stage++ }
            15 -> {
                end()
                setAttribute(player, GSUtils.GS_PROGRESS, 3)
            }
            100 -> npc("I can't speak to you; I must continue on my mission.").also { stage = END_DIALOGUE }

        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SCOUT_5568)
    }
}
