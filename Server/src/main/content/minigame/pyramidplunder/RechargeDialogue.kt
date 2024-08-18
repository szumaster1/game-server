package content.minigame.pyramidplunder

import core.api.sendDialogueOptions
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Represents the Recharge dialogue.
 */
class RechargeDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        usedSceptre = args[0] as Item
        player.packetDispatch.sendMessage("" + args[0])
        interpreter.sendDialogue(
            "Mrrrh, how do you have this? You shouldn't.",
            "Nevertheless, I can recharge this for you."
        )
        return true
    }

    override fun handle(dialogId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                sendDialogueOptions(player, "Recharge sceptre?", "Yes, please.", "No, thanks.")
                stage = 1
            }

            1 -> when (buttonId) {
                1 -> {
                    sendDialogueOptions(player, "Recharge with?", "Clay/Ivory Artefacts(24)", "Stone Artefacts(12)", "Gold Artefacts(6)")
                    stage = 20
                }

                2 -> stage = 100
            }

            20 -> when (buttonId) {
                1 -> {
                    var totalCounter = 0
                    run {
                        var i = 0
                        while (i < 28) {
                            if (totalCounter == 24) {
                                break
                            }
                            val thisSlot = player.inventory.getId(i)
                            if (thisSlot == 9026) {
                                clayRecharge[totalCounter] = i
                                totalCounter += 1
                            }
                            i++
                        }
                    }
                    run {
                        var i = 0
                        while (i < 28) {
                            if (totalCounter == 24) {
                                break
                            }
                            val thisSlot = player.inventory.getId(i)
                            if (thisSlot == 9032) {
                                clayRecharge[totalCounter] = i
                                totalCounter += 1
                            }
                            i++
                        }
                    }
                    run {
                        var i = 0
                        while (i < 28) {
                            if (totalCounter == 24) {
                                break
                            }
                            val thisSlot = player.inventory.getId(i)
                            if (thisSlot == 9036) {
                                clayRecharge[totalCounter] = i
                                totalCounter += 1
                            }
                            i++
                        }
                    }
                    if (clayRecharge[23] == 0) {
                        player.packetDispatch.sendMessage("You do not have enough clay/ivory artifacts.")
                        end()

                    }
                    var i = 0
                    while (i < 24) {
                        player.inventory.remove(
                            Item(player.inventory.getId(clayRecharge[i])),
                            clayRecharge[i],
                            true
                        )
                        i++
                    }
                    player.inventory.remove(usedSceptre)
                    player.inventory.add(Item(9044))
                    player.packetDispatch.sendMessage("<col=7f03ff>Your Pharoah's Sceptre has been fully recharged!")
                    clayRecharge[23] = 0
                    end()
                }

                2 -> {
                    var totalCounter = 0
                    run {
                        var i = 0
                        while (i < 28) {
                            if (totalCounter == 12) {
                                break
                            }
                            val thisSlot = player.inventory.getId(i)
                            if (thisSlot == 9042) {
                                stoneRecharge[totalCounter] = i
                                totalCounter += 1
                            }
                            i++
                        }
                    }
                    run {
                        var i = 0
                        while (i < 28) {
                            if (totalCounter == 12) {
                                break
                            }
                            val thisSlot = player.inventory.getId(i)
                            if (thisSlot == 9030) {
                                stoneRecharge[totalCounter] = i
                                totalCounter += 1
                            }
                            i++
                        }
                    }
                    run {
                        var i = 0
                        while (i < 28) {
                            if (totalCounter == 12) {
                                break
                            }
                            val thisSlot = player.inventory.getId(i)
                            if (thisSlot == 9038) {
                                stoneRecharge[totalCounter] = i
                                totalCounter += 1
                            }
                            i++
                        }
                    }
                    if (stoneRecharge[11] == 0) {
                        player.packetDispatch.sendMessage("You do not have enough stone artifacts.")
                        end()

                    }
                    var i = 0
                    while (i < 12) {
                        player.inventory.remove(
                            Item(player.inventory.getId(stoneRecharge[i])),
                            stoneRecharge[i],
                            true
                        )
                        i++
                    }
                    player.inventory.remove(usedSceptre)
                    player.inventory.add(Item(9044))
                    player.packetDispatch.sendMessage("<col=7f03ff>Your Pharoah's Sceptre has been fully recharged!")
                    stoneRecharge[11] = 0
                    end()
                }

                3 -> {
                    var totalCounter = 0
                    run {
                        var i = 0
                        while (i < 28) {
                            if (totalCounter == 6) {
                                break
                            }
                            val thisSlot = player.inventory.getId(i)
                            if (thisSlot == 9040) {
                                goldRecharge[totalCounter] = i
                                totalCounter += 1
                            }
                            i++
                        }
                    }
                    run {
                        var i = 0
                        while (i < 28) {
                            if (totalCounter == 6) {
                                break
                            }
                            val thisSlot = player.inventory.getId(i)
                            if (thisSlot == 9028) {
                                goldRecharge[totalCounter] = i
                                totalCounter += 1
                            }
                            i++
                        }
                    }
                    run {
                        var i = 0
                        while (i < 28) {
                            if (totalCounter == 6) {
                                break
                            }
                            val thisSlot = player.inventory.getId(i)
                            if (thisSlot == 9034) {
                                goldRecharge[totalCounter] = i
                                totalCounter += 1
                            }
                            i++
                        }
                    }
                    if (goldRecharge[5] == 0) {
                        player.packetDispatch.sendMessage("You do not have enough gold artifacts.")
                        end()

                    }
                    var i = 0
                    while (i < 6) {
                        player.inventory.remove(
                            Item(player.inventory.getId(goldRecharge[i])),
                            goldRecharge[i],
                            true
                        )
                        i++
                    }
                    player.inventory.remove(usedSceptre)
                    player.inventory.add(Item(9044))
                    player.packetDispatch.sendMessage("<col=7f03ff>Your Pharoah's Sceptre has been fully recharged!")
                    goldRecharge[5] = 0
                    end()
                }
            }

            100 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(999876)
    }

    companion object {
        var usedSceptre = Item(4950)
        var clayRecharge = IntArray(24)
        var stoneRecharge = IntArray(12)
        var goldRecharge = IntArray(6)
    }
}