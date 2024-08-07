package content.region.fremennik.quest.horrorfromthedeep.dialogue

import content.region.fremennik.quest.horrorfromthedeep.HFTDUtils
import core.api.*
import core.api.consts.Components
import core.api.consts.Items
import core.game.dialogue.DialogueFile
import core.game.node.item.Item

/**
 * Metal doors interaction dialogue
 *
 * @property qitems
 * @constructor Metal doors interaction dialogue
 */
class MetalDoorsInteractionDialogue(private var qitems: Int) : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        when (stage) {
            0 -> {
                sendDialogue(player!!, "I don't think I'll get that back if I put it in there.")
                stage++
            }

            1 -> {
                setComponentVisibility(player!!, Components.MULTI2_228, 6, true)
                setComponentVisibility(player!!, Components.MULTI2_228, 9, false)
                sendDialogueOptions(player!!, "Really place the rune into the door?", "Yes", "No")
                stage++
            }

            2 -> when (buttonID) {
                1 -> when (qitems) {
                    882 -> {
                        end()
                        if (!removeItem(player!!, Item(Items.BRONZE_ARROW_882, 1), Container.INVENTORY)) {
                            sendMessage(player!!, "Nothing interesting happens.")
                        } else {
                            sendMessage(player!!, "you place an arrow into the slot in the wall.")
                            setAttribute(player!!, HFTDUtils.STRANGE_W_ARROW, 1)
                            player!!.incrementAttribute(HFTDUtils.PUZZLE_PROGRESS)
                        }
                    }

                    1277 -> {
                        end()
                        if (!removeItem(player!!, Item(Items.BRONZE_SWORD_1277, 1), Container.INVENTORY)) {
                            sendMessage(player!!, "Nothing interesting happens.")
                        } else {
                            sendMessage(player!!, "you place an a sword into the slot in the wall.")
                            setAttribute(player!!, HFTDUtils.STRANGE_W_SWORD, 1)
                            player!!.incrementAttribute(HFTDUtils.PUZZLE_PROGRESS)
                        }
                    }

                    556 -> {
                        end()
                        if (!removeItem(player!!, Item(Items.AIR_RUNE_556, 1), Container.INVENTORY)) {
                            sendMessage(player!!, "Nothing interesting happens.")
                        } else {
                            sendMessage(player!!, "you place an air rune into the slot in the wall.")
                            setAttribute(player!!, HFTDUtils.STRANGE_W_AIR, 1)
                            player!!.incrementAttribute(HFTDUtils.PUZZLE_PROGRESS)
                        }
                    }

                    554 -> {
                        end()
                        if (!removeItem(player!!, Item(Items.FIRE_RUNE_554, 1), Container.INVENTORY)) {
                            sendMessage(player!!, "Nothing interesting happens.")
                        } else {
                            sendMessage(player!!, "you place a fire rune into the slot in the wall.")
                            setAttribute(player!!, HFTDUtils.STRANGE_W_FIRE, 1)
                            player!!.incrementAttribute(HFTDUtils.PUZZLE_PROGRESS)
                        }
                    }

                    557 -> {
                        end()
                        if (!removeItem(player!!, Item(Items.EARTH_RUNE_557, 1), Container.INVENTORY)) {
                            sendMessage(player!!, "Nothing interesting happens.")
                        } else {
                            sendMessage(player!!, "you place an earth rune into the slot in the wall.")
                            setAttribute(player!!, HFTDUtils.STRANGE_W_EARTH, 1)
                            player!!.incrementAttribute(HFTDUtils.PUZZLE_PROGRESS)
                        }
                    }

                    555 -> {
                        end()
                        if (!removeItem(player!!, Item(Items.WATER_RUNE_555, 1), Container.INVENTORY)) {
                            sendMessage(player!!, "Nothing interesting happens.")
                        } else {
                            sendMessage(player!!, "you place a water rune into the slot in the wall.")
                            setAttribute(player!!, HFTDUtils.STRANGE_W_WATER, 1)
                            player!!.incrementAttribute(HFTDUtils.PUZZLE_PROGRESS)
                        }
                    }
                }

                2 -> {
                    end()
                }
            }
        }
    }
}