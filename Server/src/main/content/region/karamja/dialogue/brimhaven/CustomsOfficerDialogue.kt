package content.region.karamja.dialogue.brimhaven

import content.global.travel.ship.Ships
import core.api.amountInInventory
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.inInventory
import core.api.removeItem
import core.api.sendMessage
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.map.Location
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Customs officer dialogue.
 */
@Initializable
class CustomsOfficerDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (args.size > 1) {
            if (player.getQuestRepository().isComplete("Pirate's Treasure")) {
                if (inInventory(player, Items.KARAMJAN_RUM_431)) {
                    npc("Aha, trying to smuggle rum are we?").also { stage = 900 }
                    return true
                }
                npc(FacialExpression.HALF_GUILTY, "Well you've got some odd stuff, but it's all legal. Now", "you need to pay a boarding charge of " + getPrice() + " coins.").also { stage = 121 }
                return true
            }
        }
        npc(FacialExpression.HALF_GUILTY, "Can I help you?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Can I journey on this ship?", "Does Karamja have unusual customs then?").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_GUILTY, "Can I journey on this ship?").also { stage = 10 }
                2 -> player(FacialExpression.HALF_GUILTY, "Does Karamja have unusual customs then?").also { stage = 20 }
            }
            10 -> npc(FacialExpression.HALF_GUILTY, "You need to be searched before you can board?").also { stage++ }
            11 -> options("Why?", "Search away, I have nothing to hide.", "You're not putting your hands on my things!").also { stage++ }
            12 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_GUILTY, "Why?").also { stage = 110 }
                2 -> player(FacialExpression.HALF_GUILTY, "Search away, I have nothing to hide.").also { stage = 120 }
                3 -> player(FacialExpression.HALF_GUILTY, "You're not putting your hands on my things!").also { stage = 130 }
            }

            20 -> npc(FacialExpression.HALF_GUILTY, "I'm not that sort of customs officer.").also { stage = END_DIALOGUE }
            110 -> npc(FacialExpression.HALF_GUILTY, "Because Asgarnia has banned the import of intoxicating", "spirits.").also { stage = END_DIALOGUE }

            120 -> {
                if (inInventory(player, Items.KARAMJAN_RUM_431)) {
                    npc("Aha, trying to smuggle rum are we?").also { stage = 900 }
                    return true
                }
                npc(FacialExpression.HALF_GUILTY, "Well you've got some odd stuff, but it's all legal. Now", "you need to pay a boarding charge of " + getPrice() + " coins.").also { stage++ }
            }

            121 -> options("Ok.", "Oh, I'll not bother then.").also { stage = 122 }

            122 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_GUILTY, "Ok.").also { stage = 210 }
                2 -> player(FacialExpression.HALF_GUILTY, "Oh, I'll not bother then.").also { stage = END_DIALOGUE }
            }

            130 -> npc(FacialExpression.HALF_GUILTY, "You're not getting on this ship then.").also { stage = END_DIALOGUE }

            210 -> {
                val coins = Item(995, getPrice())
                if (!player.inventory.containsItem(coins)) {
                    player("Sorry, I don't seem to have enough coins.").also { stage = END_DIALOGUE }
                    return true
                }
                if (!player.inventory.remove(coins)) {
                    player("Sorry, I don't seem to have enough coins.").also { stage = END_DIALOGUE }
                    return true
                }
                end()
                var ship: Ships? = null
                if (player.location.getDistance(LOCATIONS[0]) < 40) {
                    ship = Ships.BRIMHAVEN_TO_ARDOUGNE
                }
                if (player.location.getDistance(LOCATIONS[1]) < 40) {
                    ship = Ships.KARAMJAMA_TO_PORT_SARIM
                }
                getPrice()
                ship!!.sail(player)
            }

            900 -> player(FacialExpression.HALF_GUILTY, "Umm... it's for personal use?").also { stage = 901 }
            901 -> {
                var i = 0
                while (i < amountInInventory(player, Items.KARAMJAN_RUM_431)) {
                    removeItem(player, Items.KARAMJAN_RUM_431)
                    i++
                }
                sendMessage(player, "The customs officer confiscates your rum.")
                sendMessage(player, "You will need to find some way to smuggle it off the island...")
                end()
            }
        }
        return true
    }


    /**
     * Get price
     *
     * @return
     */
    fun getPrice(): Int {
        if (player.achievementDiaryManager.karamjaGlove != -1) {
            sendMessage(player, "The Captain's grin broadens as he recognises you as having earned Karamja gloves")
            sendMessage(player, "and he lets you on half price - 15 coins.")
            return 15
        } else {
            sendMessage(player, "You pay 30 coins and board the ship.")
            return 30
        }
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.CUSTOMS_OFFICER_380)
    }


    companion object {
        private val LOCATIONS = arrayOf(Location.create(2771, 3227, 0), Location.create(2954, 3147, 0))
    }
}

