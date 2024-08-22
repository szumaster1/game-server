package content.region.desert.dialogue.shantaypass

import core.api.*
import cfg.consts.Items
import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Location
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Shantay dialogue.
 */
@Initializable
class ShantayDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        if (args[0] != null && args[0] is NPC) {
            npc = args[0] as NPC
        }
        if (args.size == 2) {
            sendMessage(player,"Shantay saunters over to talk with you.")
            npc(FacialExpression.HALF_GUILTY, "If you want to be let out, you have to pay a fine of", "five gold. Do you want to pay now?")
            stage = 703
            return true
        }
        npc(FacialExpression.HALF_GUILTY, "Hello effendi, I am Shantay.")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.HALF_GUILTY, "I see you're new. Please read the billboard poster", "before going into the desert. It'll give yer details on the", "dangers you can face.").also { stage++ }
            1 -> npc(FacialExpression.HALF_GUILTY, "There is a heartbroken mother just past the gates and", "in the desert. Her name is Irena and she mourns her", "lost daughter. Such a shame.").also { stage++ }
            2 -> options("What is this place?", "Can I see what you have to sell please?", "I must be going.", "I want to buy a Shantay pass for 5 gold coins.").also { stage++ }
            3 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_GUILTY, "What is this place?").also { stage = 10 }
                2 -> player(FacialExpression.HALF_GUILTY, "Can I see what you have to sell please?").also { stage = 20 }
                3 -> player(FacialExpression.HALF_GUILTY, "I must be going.").also { stage = 30 }
                4 -> player(FacialExpression.HALF_GUILTY, "I want to buy a Shantay pass for 5 gold coins.").also { stage = 40 }
            }
            40 -> {
                if (player.inventory.contains(995, 5) && player.inventory.remove(Item(995, 5))) {
                    player.inventory.add(Item(1854))
                    sendItemDialogue(player, Items.SHANTAY_PASS_1854, "You purchase a Shantay Pass.")
                } else {
                    player("Sorry, I don't seem to have enough money.")
                }
                stage = 41
            }
            41 -> {
                end()
                sendMessage(player, "Something drops out of Shantay's pocket onto the floor. It looks like a piece of paper.")
                produceGroundItem(player, Items.SCRUMPLED_PAPER_1847, 1, npc.location)
            }
            30 -> npc(FacialExpression.HALF_GUILTY, "So long...").also { stage = 41 }
            10 -> npc(FacialExpression.HALF_GUILTY, "This is the pass of Shantay. I guard this area with my", "men. I am responsible for keeping this pass open and", "repaired.").also { stage++ }
            11 -> npc(FacialExpression.HALF_GUILTY, "My men and I prevent outlaws from getting out of the", "desert. And we stop the inexeperienced from a dry death", "in the sands. Which would you say you were?").also { stage++ }
            12 -> options("I am definitely an outlaw, prepare to die!", "I am a little inexperienced.", "Er, neither, I'm an adventurer.").also { stage++ }
            13 -> when (buttonId) {
                1 -> player("I am definitely an outlaw, prepare to die.").also { stage = 700 }
                2 -> player("I am a little inexperienced.").also { stage = 710 }
                3 -> player("Er, neither, I'm an adventurer.").also { stage = 720 }
            }
            20 -> npc(FacialExpression.HALF_GUILTY, "Absolutely Effendi!").also { stage++ }
            21 -> {
                end()
                openNpcShop(player, NPCs.SHANTAY_836)
            }
            700 -> npc("Ha, very funny.....").also { stage++ }
            701 -> npc("Guards arrest him!").also { stage++ }
            702 -> {
                sendMessage(player, "The guards arrest you and place you in the jail.")
                close()
                player.lock(10)
                Pulser.submit(object : Pulse(3, player) {
                    override fun pulse(): Boolean {
                        setAttribute(player, "/save:shantay-jail", true)
                        player.properties.teleportLocation = Location.create(3298, 3123, 0)
                        npc("You'll have to stay in there until you pay the fine of", "five gold pieces. Do you want to pay now?")
                        stage = 703
                        return true
                    }
                })
            }
            703 -> options("Yes, okay.", "No thanks, you're not having my money.").also { stage++ }
            704 -> when (buttonId) {
                1 -> player("Yes, okay.").also { stage++ }
                2 -> player("No thanks, you're not having my money.").also { stage = 800 }
            }
            705 -> stage = if (player.inventory.remove(Item(995, 5))) {
                sendMessage(player, "You hand over the five gold pieces to Shantay.")
                sendMessage(player, "Shantay unlocks the door to the cell.")
                npc("Great, Effendi, now please try to keep the peace.")
                removeAttribute(player, "shantay-jail")
                822
            } else {
                player("Sorry, I don't seem to have enough money.")
                825
            }
            800 -> npc("You have a choice. You can either pay five gold pieces", "or... You can be transported to a maximum security", "prision in Port Sarim").also { stage++ }
            801 -> npc("Will you pay the five gold pieces?").also { stage++ }
            802 -> options("Yes, okay.", "No, do your worst!").also { stage++ }
            803 -> when (buttonId) {
                1 -> player("Yes, okay.").also { stage = 820 }
                2 -> player("No, do your worst!").also { stage++ }
            }
            804 -> npc("You are to be transported to a maximum security", "prision in Port Sarim. I hope you've learn an important", "lesson from this.").also { stage++ }
            805 -> {
                sendMessage(player, "You find yourself in a prison.")
                removeAttribute(player, "shantay-jail")
                player.properties.teleportLocation = Location.create(3018, 3188, 0)
                end()
            }
            820 -> npc("Good, I see that you have come to your senses.").also { stage++ }
            821 -> {
                end()
                if (player.inventory.remove(Item(995, 5))) {
                    sendMessage(player, "You hand over the five gold pieces to Shantay.")
                    sendMessage(player, "Shantay unlocks the door to the cell.")
                    npc("Great, Effendi, now please try to keep the peace.")
                } else {
                    player("Sorry, I don't seem to have enough money.")
                }
            }
            710 -> npc("Can I recommend that you purchase a full waterskin", "and a knife! These items will no doubt save your life. A", "waterskin will keep water from evaporating in the desert.").also { stage++ }
            711 -> npc("And a keen woodsman with a knife can extract juice", "from a cactus. Before you go into the desert, it's", "advisable to wear desert clothes. It's very hot in the", "desert and you'll surely cook if you wear amour.").also { stage = END_DIALOGUE }
            720 -> npc("Great, I have just the thing for you the desert adventurer.", "I sell desert clothes which will keep you cool in the heat", "of the desert. I also sell waterskins so that you won't", "die in the desert.").also { stage++ }
            721 -> npc("A waterskin and a knife help you survive from the juice", "of a cactus. Use the chest to store your items, we'll take", "them to the bank. It's hot in the desert, you'll bake in", "all that armour.").also { stage++ }
            722 -> npc("To keep the pass open we ask for 5 gold pieces. And", "we give you a Shantay Pass, just ask to see what I sell", "to buy one.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SHANTAY_836)
    }

}
