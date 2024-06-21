package content.region.karamja.handlers.shilovillage

import content.region.karamja.dialogue.shilovillage.BlackPrismDialogue
import content.region.karamja.dialogue.shilovillage.CartTravelDialogue
import core.api.*
import core.api.consts.Components
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.consts.Scenery
import core.game.dialogue.DialogueFile
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.npc.NPC
import core.game.node.item.Item
import core.game.shops.Shops
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.utilities.END_DIALOGUE

class ShilovillageListeners : InteractionListener {

    companion object {
        private const val YANNI = NPCs.YANNI_SALIKA_515
        private const val NOTES = Items.BERVIRIUS_NOTES_624
        private const val BLACKSMITH_DOOR = Scenery.BLACKSMITH_S_DOOR_2266
        private const val BROKEN_CART = 2216
        private val BOARD = intArrayOf(2265, 2230, 511, 510)
        val ANTIQUE_ITEMS = intArrayOf(605, 606, 607, 608, 611, 616, 624, 4808)
        private val GABOOTY_NPC_WRAPPERS = intArrayOf(2520, 2521, 2522)

        /*
            Shilo cart interactions.
        */

        class CartQuickPay : DialogueFile() {
            override fun handle(componentID: Int, buttonID: Int) {
                if (!hasRequirement(player!!, "Shilo Village", true)) return
                val shilo = npc?.id == 510
                when (stage) {
                    0 -> {
                        if (inInventory(player!!, Items.COINS_995, 10)) {
                            sendDialogue(player!!, "You pay the fare and hand 10 gold coins to " + (npc?.name ?: "") + ".").also { stage++ }
                        } else {
                            sendMessage(player!!, "You don't have enough coins.").also { stage = END_DIALOGUE }
                        }
                    }

                    1 -> {
                        if (removeItem(player!!, Item(Items.COINS_995, 10))) {
                            queueScript(player!!, 1, QueueStrength.SOFT) { stage: Int ->
                                when (stage) {
                                    0 -> {
                                        closeOverlay(player!!)
                                        openOverlay(player!!, Components.FADE_TO_BLACK_120)
                                        return@queueScript keepRunning(player!!)
                                    }

                                    1 -> {
                                        teleport(player!!, if (shilo) Location.create(2834, 2951, 0) else Location.create(2780, 3212, 0))
                                        closeOverlay(player!!)
                                        openOverlay(player!!, Components.FADE_FROM_BLACK_170)
                                        sendDialogue(player!!, "You feel tired from the journey, but at least you didn't have to walk all that distance.")
                                    }
                                }
                                return@queueScript stopExecuting(player!!)
                            }
                        }
                        stage = END_DIALOGUE
                    }
                }
            }
        }
    }

    override fun defineListeners() {

        /*
            Gabooty NPC interaction.
         */

        on(GABOOTY_NPC_WRAPPERS, IntType.NPC, "talk-to", "trade-co-op", "trade-drinks") { player, node ->
            if(node.id in GABOOTY_NPC_WRAPPERS){
                when (getUsedOption(player)) {
                    "talk-to" -> openDialogue(player, NPCs.GABOOTY_2521)
                    "trade-co-op" -> Shops.openId(player, 226)
                    "trade-drinks" -> Shops.openId(player, 254)
                }
            }
            return@on true
        }

        /*
            Drop "Bervirius Notes" interaction.
         */

        on(NOTES, IntType.ITEM, "drop") { player, node ->
            if (removeItem(player, node.asItem())) {
                sendMessage(player, "As you drop the delicate scrolls onto the floor, they disintegrate immediately.")
            }
            return@on true
        }

        /*
            Blacksmith door interaction.
         */

        on(BLACKSMITH_DOOR, IntType.SCENERY, "open") { player, node ->
            if (!getAttribute(player, "shilo-village:blacksmith-doors", false)) {
                sendNPCDialogue(player, NPCs.YOHNUS_513, "Sorry but the blacksmiths is closed. But I can let you use the furnace at the cost of 20 gold pieces.")
            } else {
                DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
            }
            return@on true
        }

        /*
            Broken cart interaction.
         */

        on(BROKEN_CART, IntType.SCENERY, "look-at") { player, node ->
            if (!hasRequirement(player, "Shilo Village")) return@on true
            var location = Location(0, 0)
            val playerloc = Location(player.location.x, player.location.y)
            if (node.id == 2216 && getUsedOption(player) == "look-at") {
                if (playerloc.x > 2878) {
                    location = Location(2876, 2952)
                } else if (playerloc.x < 2879) {
                    location = Location(2880, 2952)
                }
            }

            player.lock()
            player.animate(Animation(839))
            player.impactHandler.disabledTicks = 4
            Pulser.submit(object : Pulse(1, player) {
                override fun pulse(): Boolean {
                    player.unlock()
                    player.properties.teleportLocation = location
                    player.animator.reset()
                    return true
                }
            })
            return@on true
        }

        on(BOARD, IntType.SCENERY, "board") { player, node ->
            if (getUsedOption(player) == "talk-to") {
                openDialogue(player, CartTravelDialogue(), if (node.id == 510) NPC(NPCs.HAJEDY_510) else NPC(NPCs.VIGROY_511))
                return@on true
            }

            if (node.id == 2230 || node.id == 510) {
                when (getUsedOption(player)) {
                    "pay-fare" -> openDialogue(player, CartQuickPay(), NPC(NPCs.HAJEDY_510))
                    "board" -> openDialogue(player, CartTravelDialogue(), NPC(NPCs.HAJEDY_510))
                }
                return@on true
            }

            if (node.id == 2265 || node.id == 511) {
                when (getUsedOption(player)) {
                    "pay-fare" -> openDialogue(player, CartQuickPay(), NPC(NPCs.VIGROY_511))
                    "board" -> openDialogue(player, CartTravelDialogue(), NPC(NPCs.VIGROY_511))
                }
                return@on true
            }

            return@on true
        }

        /*
            Antique exchange interactions.
         */

        onUseWith(IntType.NPC, ANTIQUE_ITEMS, YANNI) { player, used, _ ->
            val item = AntiqueItem.antiqueMap[used.id] ?: return@onUseWith true
            if (amountInInventory(player, used.id) == 1) {
                if (!inInventory(player, used.id)) {
                    sendNPCDialogue(player, NPCs.YANNI_SALIKA_515, "Sorry Bwana, you have nothing I am interested in.")
                } else {
                    openDialogue(player, object : DialogueFile() {
                        override fun handle(componentID: Int, buttonID: Int) {
                            npc = NPC(NPCs.YANNI_SALIKA_515)
                            when (stage) {
                                0 -> sendNPCDialogue(player, NPCs.YANNI_SALIKA_515, item.dialogue).also { stage++ }
                                1 -> sendNPCDialogue(player, NPCs.YANNI_SALIKA_515, item.priceInfo).also { stage++ }
                                2 -> {
                                    setTitle(player, 2)
                                    sendDialogueOptions(player, "Sell the " + getItemName(used.id) + "?", "Yes.", "No.").also { stage++ }
                                }
                                3 -> when (buttonID) {
                                    1 -> {
                                        end()
                                        if (removeItem(player, used.id)) {
                                            sendNPCDialogue(player, NPCs.YANNI_SALIKA_515, "Here's " + item.price + " for it.").also { stage++ }
                                            sendMessage(player, "You sell the " + getItemName(used.id) + " for " + item.price + " gold.")
                                            addItem(player, Items.COINS_995, item.price)
                                        }
                                    }

                                    2 -> end()
                                }
                            }
                        }
                    })
                }
                if (used.id == Items.BLACK_PRISM_4808) {
                    openDialogue(player, BlackPrismDialogue())
                }
            }
            return@onUseWith true
        }
    }
}
