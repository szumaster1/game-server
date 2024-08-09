package content.region.fremennik.quest.fremtrials

import core.api.consts.Components
import core.api.getAttribute
import core.api.removeAttribute
import core.api.sendMessage
import core.api.setAttribute
import core.game.interaction.InterfaceListener

/**
 * Seer lock interface listener.
 */
class SeerLockInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        val letterOneBack = 39 // Button ID for moving the first letter back
        val letterOneForward = 40 // Button ID for moving the first letter forward
        val letterTwoBack = 35 // Button ID for moving the second letter back
        val letterTwoForward = 36 // Button ID for moving the second letter forward
        val letterThreeBack = 31 // Button ID for moving the third letter back
        val letterThreeForward = 32 // Button ID for moving the third letter forward
        val letterFourBack = 27 // Button ID for moving the fourth letter back
        val letterFourForward = 28 // Button ID for moving the fourth letter forward
        val enterButton = 1 // Button ID for the enter button
        val exitButton = 2 // Button ID for the exit button
        val doorLockInterface = Components.SEER_COMBOLOCK_298 // Interface ID for the door lock interface
        val letters = arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z") // Array of letters

        // Called when the door lock interface is opened
        onOpen(doorLockInterface) { player, _ ->
            player.packetDispatch.sendVarcUpdate(618, 0)
            player.packetDispatch.sendVarcUpdate(619, 0)
            player.packetDispatch.sendVarcUpdate(620, 0)
            player.packetDispatch.sendVarcUpdate(621, 0)
            player.packetDispatch.sendIfaceSettings(0, 2, 298, 0, 1)
            setAttribute(player, "riddle-letter-one", 0)
            setAttribute(player, "riddle-letter-two", 0)
            setAttribute(player, "riddle-letter-three", 0)
            setAttribute(player, "riddle-letter-four", 0)
            return@onOpen true
        }

        // Called when the door lock interface is closed
        onClose(doorLockInterface) { player, _ ->
            removeAttribute(player, "riddle-letter-one")
            removeAttribute(player, "riddle-letter-two")
            removeAttribute(player, "riddle-letter-three")
            removeAttribute(player, "riddle-letter-four")
            return@onClose true
        }

        // Called when a button is clicked in the door lock interface
        on(doorLockInterface) { player, _, _, buttonID, _, _ ->
            when (buttonID) {
                letterOneBack -> {
                    if (getAttribute(player, "riddle-letter-one", 0) == 0) {
                        setAttribute(player, "riddle-letter-one", 25)
                    } else {
                        (player.incrementAttribute("riddle-letter-one", -1))
                    }
                }

                letterOneForward -> {
                    if (getAttribute(player, "riddle-letter-one", 0) == 25) {
                        setAttribute(player, "riddle-letter-one", 0)
                    } else {
                        (player.incrementAttribute("riddle-letter-one", 1))
                    }
                }

                letterTwoBack -> {
                    if (getAttribute(player, "riddle-letter-two", 0) == 0) {
                        setAttribute(player, "riddle-letter-two", 25)
                    } else {
                        (player.incrementAttribute("riddle-letter-two", -1))
                    }
                }

                letterTwoForward -> {
                    if (getAttribute(player, "riddle-letter-two", 0) == 25) {
                        setAttribute(player, "riddle-letter-two", 0)
                    } else {
                        (player.incrementAttribute("riddle-letter-two", 1))
                    }
                }

                letterThreeBack -> {
                    if (getAttribute(player, "riddle-letter-three", 0) == 0) {
                        setAttribute(player, "riddle-letter-three", 25)
                    } else {
                        (player.incrementAttribute("riddle-letter-three", -1))
                    }
                }

                letterThreeForward -> {
                    if (getAttribute(player, "riddle-letter-three", 0) == 25) {
                        setAttribute(player, "riddle-letter-three", 0)
                    } else {
                        (player.incrementAttribute("riddle-letter-three", 1))
                    }
                }

                letterFourBack -> {
                    if (getAttribute(player, "riddle-letter-four", 0) == 0) {
                        setAttribute(player, "riddle-letter-four", 25)
                    } else {
                        (player.incrementAttribute("riddle-letter-four", -1))
                    }
                }

                letterFourForward -> {
                    if (getAttribute(player, "riddle-letter-four", 0) == 25) {
                        setAttribute(player, "riddle-letter-four", 0)
                    } else {
                        (player.incrementAttribute("riddle-letter-four", 1))
                    }
                }

                enterButton -> {
                    val letterOne = letters[getAttribute(player, "riddle-letter-one", 0)]
                    val letterTwo = letters[getAttribute(player, "riddle-letter-two", 0)]
                    val letterThree = letters[getAttribute(player, "riddle-letter-three", 0)]
                    val letterFour = letters[getAttribute(player, "riddle-letter-four", 0)]
                    when (getAttribute(player, "PeerRiddle", 0)) {
                        0 -> {
                            if (letterOne == "L" && letterTwo == "I" && letterThree == "F" && letterFour == "E") {
                                sendMessage(player, "You have solved the riddle!")
                                removeAttribute(player, "PeerRiddle")
                                setAttribute(player, "/save:riddlesolved", true)
                                player.interfaceManager.close()
                            } else {
                                sendMessage(player, "You have failed to solve the riddle.")
                                player.interfaceManager.close()
                            }
                        }

                        1 -> {
                            if (letterOne == "M" && letterTwo == "I" && letterThree == "N" && letterFour == "D") {
                                sendMessage(player, "You have solved the riddle!")
                                removeAttribute(player, "PeerRiddle")
                                setAttribute(player, "/save:riddlesolved", true)
                                player.interfaceManager.close()
                            } else {
                                sendMessage(player, "You have failed to solve the riddle.")
                                player.interfaceManager.close()
                            }
                        }

                        2 -> {
                            if (letterOne == "T" && letterTwo == "I" && letterThree == "M" && letterFour == "E") {
                                sendMessage(player, "You have solved the riddle!")
                                removeAttribute(player, "PeerRiddle")
                                setAttribute(player, "/save:riddlesolved", true)
                                player.interfaceManager.close()
                            } else {
                                sendMessage(player, "You have failed to solve the riddle.")
                                player.interfaceManager.close()
                            }
                        }

                        3 -> {
                            if (letterOne == "W" && letterTwo == "I" && letterThree == "N" && letterFour == "D") {
                                sendMessage(player, "You have solved the riddle!")
                                removeAttribute(player, "PeerRiddle")
                                setAttribute(player, "/save:riddlesolved", true)
                                player.interfaceManager.close()
                            } else {
                                sendMessage(player, "You have failed to solve the riddle.")
                                player.interfaceManager.close()
                            }
                        }
                    }
                }
            }
            return@on true
        }
    }
}
