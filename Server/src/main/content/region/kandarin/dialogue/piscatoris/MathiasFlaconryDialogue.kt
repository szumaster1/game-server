package content.region.kandarin.dialogue.piscatoris

import core.api.consts.Items
import core.api.consts.NPCs
import core.api.sendNPCDialogue
import core.game.container.impl.EquipmentContainer
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.plugin.Initializable

/**
 * Represents the Mathias flaconry dialogue.
 */
@Initializable
class MathiasFlaconryDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        var quick = false
        if (player.equipment.contains(Items.FALCONERS_GLOVE_10023, 1)) {
            sendNPCDialogue(player, NPCs.MATTHIAS_5093, "Oh, it looks like you've lost your falcon. Would you like a new one?")
            stage = 900
            return true
        }
        if (args.size == 2) quick = true
        if (quick) {
            sendNPCDialogue(player, NPCs.MATTHIAS_5093, "If you wish to try falconry, I request a small fee. How does 500 gold coins sound?", FacialExpression.HALF_GUILTY)
            stage = 90
            return true
        }
        player(FacialExpression.HALF_GUILTY, "Hello there.")
        stage = 500
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            90 -> {
                options("Ok, that seems reasonable.", "I'm not interested then, thanks.")
                stage = 91
            }

            91 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY, "Ok, that seems reasonable.")
                    stage = 95
                }

                2 -> {
                    player("I'm not interested then, thanks.")
                    stage = 323
                }
            }

            323 -> end()
            95 -> {
                if (player.bank.containsItem(FALCON) || player.equipment.containsItem(FALCON) || player.inventory.containsItem(
                        FALCON
                    )
                ) {
                    sendNPCDialogue(player, NPCs.MATTHIAS_5093, "You already have a falcon!")
                    stage = 99
                    return true
                }
                if (player.equipment[EquipmentContainer.SLOT_HANDS] != null || player.equipment[EquipmentContainer.SLOT_SHIELD] != null || player.equipment[EquipmentContainer.SLOT_WEAPON] != null) {
                    sendNPCDialogue(player, NPCs.MATTHIAS_5093, "Sorry, free your hands, weapon, and shield slot first.")
                    stage = 99
                }
                if (player.inventory.contains(Items.COINS_995, 500)) {
                    player.inventory.remove(Item(Items.COINS_995, 500))
                    player.equipment.add(Item(Items.FALCONERS_GLOVE_10024), true, false)
                    interpreter.sendDialogue(
                        "The falconer gives you a large leather glove and brings one of the",
                        "smaller birds over to land on it."
                    )
                    stage = 97
                } else {
                    end()
                    sendNPCDialogue(player, NPCs.MATTHIAS_5093, "You don't have enough coins for that.",FacialExpression.HALF_GUILTY)
                }
            }

            97 -> {
                sendNPCDialogue(player, NPCs.MATTHIAS_5093, "Don't worry: I'll keep and eye on you to make sure you don't upset it roo much.", FacialExpression.HALF_GUILTY)
                stage = 99
            }

            99 -> end()
            500 -> {
                sendNPCDialogue(player, NPCs.MATTHIAS_5093, "Greetings. Can I help you at all?",FacialExpression.HALF_GUILTY)
                stage = 501
            }

            501 -> {
                player("Could I have a go with your bird?")
                stage = 502
            }

            502 -> {
                if (player.getSkills().getLevel(Skills.HUNTER) < 43) {
                    npc("Try coming back when you're more experienced", "I wouldn't want my birds being injured.")
                    stage = 967
                    return true
                }
                sendNPCDialogue(player, NPCs.MATTHIAS_5093, "Training falcons is a lot of work and I doubt you're up to the task. However, I suppose I could let you try hunting with one.", FacialExpression.HALF_GUILTY)
                stage = 503
            }

            503 -> {
                sendNPCDialogue(player, NPCs.MATTHIAS_5093, "I have some tamer birds that I occasionally lend to rich noblemen who consider it a sufficiently refined sport for their tastes. and you look like the kind who might appreciate a good hunt.")
                stage = 90
            }

            900 -> {
                options( "Yes, please.", "No thank you.")
                stage = 901
            }

            901 -> when (buttonId) {
                1 -> {
                    player("Yes, please.")
                    stage = 920
                }

                2 -> end()
            }

            920 -> {
                player.bank.remove(FALCON, Item(Items.FALCONERS_GLOVE_10023))
                player.inventory.remove(Item(Items.FALCONERS_GLOVE_10023), FALCON)
                player.equipment.add(FALCON, true, false)
                interpreter.sendDialogue("The falconer gives you a large leather glove and brings one of the", "smaller birds over to land on it.")
                stage = 97
            }

            967 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MATTHIAS_5093)
    }

    companion object {
        private val FALCON = Item(Items.FALCONERS_GLOVE_10024)
    }

}