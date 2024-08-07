package content.region.morytania.dialogue.phasmatys

import core.api.consts.Items
import core.api.consts.NPCs
import core.api.addItemOrDrop
import core.api.freeSlots
import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Ghost disciple dialogue.
 */
@Initializable
class GhostDiscipleDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Info: Followers of Necrovarus. They worship the Ectofuntus in Port Phasmatys.
     * Location: 3657,3516 - 3663,3522 - 3654,3518
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (args.size > 1) {
            val amount = player.getSavedData().globalData.getEctoCharges() * 5
            if (freeSlots(player) < 1) {
                player("Sorry, I don't have enough inventory space.")
                return true
            }
            if (amount > 0) {
                addItemOrDrop(player, Items.ECTO_TOKEN_4278, amount)
                player.getSavedData().globalData.setEctoCharges(0)
                npc("Certainly, mortal. Here's $amount ectotokens.")
            } else {
                npc("I'm sorry, but you haven't earned any.")
            }
            stage = 81
            return true
        }
        if (player.getSavedData().globalData.getEctoCharges() > 0) {
            player("Can I have the tokens I have earned?")
            stage = 80
            return true
        }
        if (player.location.z > 0) {
            if (args.size > 1) {
                npc("What are you doing going in there?")
                stage = 70
                return true
            }
            player("How do I use the bone grinder?")
            stage = 60
            return true
        }
        player("What is this strange fountain?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc("This is the Ectofuntus, the most marvellous creation of", "Necrovarus, our glorious leader.").also { stage++ }
            1 -> options("What is the Ectofunuts?", "Where do I get ectoplasm from?", "How do I grind bones?", "How do I receive Ectotokens?", "Thanks for your time.").also { stage++ }
            2 -> when (buttonId) {
                1 -> player("What is the Ectofunuts?").also { stage = 10 }
                2 -> player("Where do I get ectoplasm from?").also { stage = 20 }
                3 -> player("How do I grind bones?").also { stage = 30 }
                4 -> player("How do I receive Ectotokens?").also { stage = 40 }
                5 -> player("Thanks for your time.").also { stage = END_DIALOGUE }
            }
            10 -> npc("It provides the power to keep us ghosts from passing", "over into the next plane of existence.").also { stage++ }
            11 -> player("And how does it work?").also { stage++ }
            12 -> npc("You have to pour a bucket of ectoplasm into the", "fountain, a pot of ground bones, and then worship at", "the Ectofuntus. A unit of unholy power will then be", "created.").also { stage = 1 }
            20 -> npc("Necrovarus sensed the power bubbling beneath our feet,", "and we delved long and deep beneath Port Phasmatys,", "until we found a pool of natural ectoplasm. You may", "find it by using the trapdoor over there.").also { stage = 1 }
            30 -> npc("There is a bone grinding machine upstairs. Put bones", "of any type into the machine's hopper, and then turn", "the handle to grind them. You will need a pot to empty", "the machine of ground up bones.").also { stage = 1 }
            40 -> npc("We disciples keep track of how many units of power", "have been produced. Just talk to us once you have", "generated some and we will reimburse you with the", "correct amount of Ectotokens.").also { stage++ }
            41 -> player("How do I generate units of power?").also { stage++ }
            42 -> npc("You have to pour a bucket of ectoplasm into the", "fountain and then worship at the Ectofuntus with a pot", "of ground bones. This will create a unit of unholy", "power.").also { stage = 1 }
            60 -> npc("Put bones of any type into the machine's hopper, and", "then turn the handle to grind them. You will need a pot", "to empty the machine of ground up bones.").also { stage = END_DIALOGUE }
            70 -> player("Err, I was just curious...").also { stage++ }
            71 -> npc("Inside that room is a coffin, inside which lie the mortal", "remains of our most glorious master. Necrovarus.", "None may enter.").also { stage = END_DIALOGUE }
            80 -> {
                val amount = player.getSavedData().globalData.getEctoCharges() * 5;
                if (freeSlots(player) < 1) {
                    player("Sorry, I don't have enough inventory space.").also { stage++ }
                    stage++
                }
                stage++
                addItemOrDrop(player, Items.ECTO_TOKEN_4278, amount)
                player.getSavedData().globalData.setEctoCharges(0);
                npc("Certainly, mortal. Here's $amount ectotokens.")
            }

            81 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GHOST_DISCIPLE_1686)
    }

}
