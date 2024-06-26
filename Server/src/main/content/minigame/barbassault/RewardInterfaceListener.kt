package content.minigame.barbassault

import core.api.*
import core.api.consts.Components
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.interaction.InterfaceListener
import core.game.node.item.Item

class RewardInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {

        /*
         * Interface screenshot: https://i.imgur.com/r9Mu6Og.png
         */

        onOpen(Components.BARBASSAULT_REWARD_SHOP_491) { player, components ->
            for (i in BA_POINTS) {
                setInterfaceText(player, "0", 491, i)
            }
            for (i in HEAD_REQUIREMENTS) {
                setInterfaceText(player, "200,000K<br>45 Defence", 491, i)
            }
            for (i in BODY_PARTS_REQUIREMENTS) {
                setInterfaceText(player, "200,000K<br>40 Defence", 491, i)
            }
            setInterfaceText(player, "200,000K<br>50 Defence", 491, 171)
            return@onOpen true
        }

        on(Components.BARBASSAULT_REWARD_SHOP_491) { player, _, opcode, buttonID, _, _ ->
            if (opcode == 10) {
                when (buttonID) {
                    111 -> {
                        // fighter hat
                        if (!removeItem(player, Item(Items.COINS_995, 200000))) {
                            sendNPCDialogue(player, NPCs.COMMANDER_CONNAD_5029, "You don't have enough money!")
                        } else {
                            sendItemDialogue(player, Items.FIGHTER_HAT_10548, "you've purchased the Fighter hat.")
                            addItemOrDrop(player, Items.FIGHTER_HAT_10548)
                        }
                    }

                    118 -> {
                        // ranger hat
                        if (!removeItem(player, Item(Items.COINS_995, 200000))) {
                            sendNPCDialogue(player, NPCs.COMMANDER_CONNAD_5029, "You don't have enough money!")
                        } else {
                            sendItemDialogue(player, Items.RANGER_HAT_10550, "you've purchased the Ranger hat.")
                            addItemOrDrop(player, Items.RANGER_HAT_10550)
                        }
                    }

                    125 -> {
                        // penance runner hat
                        if (!removeItem(player, Item(Items.COINS_995, 200000))) {
                            sendNPCDialogue(player, NPCs.COMMANDER_CONNAD_5029, "You don't have enough money!")
                        } else {
                            sendItemDialogue(player, Items.RUNNER_HAT_10549, "you've purchased the Runner hat.")
                            addItemOrDrop(player, Items.RUNNER_HAT_10549)
                        }
                    }

                    132 -> {
                        // penance healer hat
                        if (!removeItem(player, Item(Items.COINS_995, 200000))) {
                            sendNPCDialogue(player, NPCs.COMMANDER_CONNAD_5029, "You don't have enough money!")
                        } else {
                            sendItemDialogue(player, Items.HEALER_HAT_10547, "you've purchased the Healer hat.")
                            addItemOrDrop(player, Items.HEALER_HAT_10547)
                        }
                    }

                    139 -> {
                        // fighter torso
                        if (!removeItem(player, Item(Items.COINS_995, 200000))) {
                            sendNPCDialogue(player, NPCs.COMMANDER_CONNAD_5029, "You don't have enough money!")
                        } else {
                            sendItemDialogue(player, Items.FIGHTER_TORSO_10551, "you've purchased the Fighter torso.")
                            addItemOrDrop(player, Items.FIGHTER_TORSO_10551)
                        }
                    }

                    146 -> {
                        // penance skirt
                        if (!removeItem(player, Item(Items.COINS_995, 200000))) {
                            sendNPCDialogue(player, NPCs.COMMANDER_CONNAD_5029, "You don't have enough money!")
                        } else {
                            sendDoubleItemDialogue(player, -1, Items.PENANCE_SKIRT_10555, "you've purchased the Penance skirt.")
                            addItemOrDrop(player, Items.PENANCE_SKIRT_10555)
                        }
                    }

                    153 -> {
                        // runner boots
                        if (!removeItem(player, Item(Items.COINS_995, 200000))) {
                            sendNPCDialogue(player, NPCs.COMMANDER_CONNAD_5029, "You don't have enough money!")
                        } else {
                            sendItemDialogue(player, Items.RUNNER_BOOTS_10552, "you've purchased the Runner boots.")
                            addItemOrDrop(player, Items.RUNNER_BOOTS_10552)
                        }
                    }

                    160 -> {
                        // penance gloves
                        if (!removeItem(player, Item(Items.COINS_995, 200000))) {
                            sendNPCDialogue(player, NPCs.COMMANDER_CONNAD_5029, "You don't have enough money!")
                        } else {
                            sendItemDialogue(player, Items.PENANCE_GLOVES_10553, "you've purchased the Penance gloves.")
                            addItemOrDrop(player, Items.PENANCE_GLOVES_10553)
                        }
                    }

                    167 -> {
                        // granite body
                        if (!removeItem(player, Item(Items.COINS_995, 200000))) {
                            sendNPCDialogue(player, NPCs.COMMANDER_CONNAD_5029, "You don't have enough money!")
                        } else {
                            sendItemDialogue(player, Items.GRANITE_BODY_10564, "you've purchased the Penance gloves.")
                            addItemOrDrop(player, Items.GRANITE_BODY_10564)
                        }
                    }

                    else -> sendMessage(player, "Nothing interesting happens.")
                }
                closeInterface(player)
            }
            return@on true
        }
    }

    companion object {
        val HEAD_REQUIREMENTS = intArrayOf(115, 122, 129, 136)
        val BODY_PARTS_REQUIREMENTS = intArrayOf(143, 150, 157, 164)
        val BA_POINTS = (219..222)
    }
}
