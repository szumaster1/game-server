package content.global.skill.support.construction.decoration.workshop

import core.api.*
import core.api.consts.Items
import core.api.consts.Scenery
import core.game.dialogue.DialogueFile
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

/**
 * Heraldry listener.
 */
class HeraldryListener : InteractionListener {

    private val heraldryStand = intArrayOf(Scenery.HELMET_PLUMING_STAND_13716, Scenery.PAINTING_STAND_13717, Scenery.BANNER_MAKING_STAND_13718)

    override fun defineListeners() {
        on(heraldryStand, IntType.SCENERY, "make-helmet", "use") { player, node ->
            openDialogue(player, object : DialogueFile() {
                var crestType = 0
                val crestItemType = getAttribute(player, "con:crest-type", crestType)
                override fun handle(componentID: Int, buttonID: Int) {
                    when (stage) {
                        0 -> when (node.asScenery().id) {
                            Scenery.HELMET_PLUMING_STAND_13716 -> {
                                setTitle(player, 2)
                                sendDialogueOptions(player, "Plume which type of helmet?", "Steel helmet", "Runite helmet").also { stage++ }
                            }
                            Scenery.PAINTING_STAND_13717 -> {
                                setTitle(player, 4)
                                sendDialogueOptions(player, "What do you want to make?", "Steel helmet", "Runite helmet", "Steel heraldic shield", "Runite heraldic shield").also { stage++ }
                            }
                            else -> {
                                setTitle(player, 5)
                                sendDialogueOptions(player, "What do you want to make?", "Steel helmet", "Runite helmet", "Steel heraldic shield", "Runite heraldic shield", "Heraldic banner").also { stage++ }
                            }
                        }

                        1 -> when (buttonID) {
                            1, 2 -> {
                                if (buttonID == 2 && !inInventory(player, Items.RUNE_FULL_HELM_1163, 1) || (buttonID == 1 && !inInventory(player, Items.STEEL_FULL_HELM_1157))) {
                                    end()
                                    sendDialogue(player, "You need ${if (buttonID == 2) "Rune full helm" else "Steel full helm"} to do this.")
                                } else {
                                    end()
                                    lock(player, 100)
                                    removeItem(player, if (buttonID == 2) Item(Items.RUNE_FULL_HELM_1163, 1)
                                    else Item(Items.STEEL_FULL_HELM_1157, 1), Container.INVENTORY)
                                    face(player, node)
                                    animate(player, 3656)
                                    runTask(player, 3) {
                                        addItem(player, if (buttonID == 2) getRuniteHeraldicHelm(crestItemType) else getSteelHeraldicHelm(crestItemType), 1)
                                        rewardXP(player, Skills.CRAFTING, 37.0)
                                        sendMessage(player, "You make a helmet with your symbol on.")
                                        unlock(player)
                                    }
                                }
                            }

                            3, 4 -> {
                                if (buttonID == 4 && !inInventory(player, Items.RUNE_KITESHIELD_1201, 1) || (buttonID == 3 && !inInventory(player, Items.STEEL_KITESHIELD_1193))) {
                                    end()
                                    sendDialogue(player, "You need ${if (buttonID == 4) "Rune kiteshield" else "Steel kiteshield"} to do this.")
                                } else {
                                    end()
                                    lock(player, 100)
                                    removeItem(player, if (buttonID == 4) Item(Items.RUNE_KITESHIELD_1201, 1)
                                    else Item(Items.STEEL_KITESHIELD_1193, 1), Container.INVENTORY)
                                    face(player, node)
                                    animate(player, 3654)
                                    runTask(player, 3) {
                                        addItem(player, if (buttonID == 4) getRuniteHeraldicShield(crestItemType) else getSteelHeraldicShield(crestItemType), 1)
                                        rewardXP(player, Skills.CRAFTING, 40.0)
                                        sendMessage(player, "You make a shield with your symbol on.")
                                        unlock(player)
                                    }
                                }
                            }

                            5 -> {
                                if (!inInventory(player, Items.BOLT_OF_CLOTH_8790, 1) || !inInventory(player, Items.PLANK_960, 1)) {
                                    end()
                                    sendDialogue(player, "You need 1 bolt of cloth and 1 plank to do this.")
                                } else {
                                    end()
                                    lock(player, 100)
                                    removeItem(player, Items.BOLT_OF_CLOTH_8790, Container.INVENTORY)
                                    removeItem(player, Items.PLANK_960, Container.INVENTORY)
                                    face(player, node)
                                    animate(player, 3655)
                                    runTask(player, 3) {
                                        addItem(player, getHeraldicBanner(crestItemType), 1)
                                        rewardXP(player, Skills.CRAFTING, 42.5)
                                        sendMessage(player, "You make a banner with your symbol on.")
                                        unlock(player)
                                    }
                                }
                            }
                        }
                    }
                }
            }, node.asScenery())
            return@on true
        }
    }

    private fun getSteelHeraldicHelm(crest: Int): Int {
        return when (crest) {
            1 -> Items.STEEL_HERALDIC_HELM_8682
            2 -> Items.STEEL_HERALDIC_HELM_8684
            3 -> Items.STEEL_HERALDIC_HELM_8686
            4 -> Items.STEEL_HERALDIC_HELM_8688
            5 -> Items.STEEL_HERALDIC_HELM_8690
            6 -> Items.STEEL_HERALDIC_HELM_8692
            7 -> Items.STEEL_HERALDIC_HELM_8694
            8 -> Items.STEEL_HERALDIC_HELM_8696
            9 -> Items.STEEL_HERALDIC_HELM_8698
            10 -> Items.STEEL_HERALDIC_HELM_8700
            11 -> Items.STEEL_HERALDIC_HELM_8702
            12 -> Items.STEEL_HERALDIC_HELM_8704
            13 -> Items.STEEL_HERALDIC_HELM_8706
            14 -> Items.STEEL_HERALDIC_HELM_8708
            15 -> Items.STEEL_HERALDIC_HELM_8710
            16 -> Items.STEEL_HERALDIC_HELM_8712
            else -> Items.STEEL_FULL_HELM_1157
        }
    }

    private fun getSteelHeraldicShield(crest: Int): Int {
        return when (crest) {
            1 -> Items.STEEL_KITESHIELD_8746
            2 -> Items.STEEL_KITESHIELD_8748
            3 -> Items.STEEL_KITESHIELD_8750
            4 -> Items.STEEL_KITESHIELD_8752
            5 -> Items.STEEL_KITESHIELD_8754
            6 -> Items.STEEL_KITESHIELD_8756
            7 -> Items.STEEL_KITESHIELD_8758
            8 -> Items.STEEL_KITESHIELD_8760
            9 -> Items.STEEL_KITESHIELD_8762
            10 -> Items.STEEL_KITESHIELD_8764
            11 -> Items.STEEL_KITESHIELD_8766
            12 -> Items.STEEL_KITESHIELD_8768
            13 -> Items.STEEL_KITESHIELD_8770
            14 -> Items.STEEL_KITESHIELD_8772
            15 -> Items.STEEL_KITESHIELD_8774
            16 -> Items.STEEL_KITESHIELD_8776
            else -> Items.STEEL_KITESHIELD_1193
        }
    }

    private fun getRuniteHeraldicHelm(crest: Int): Int {
        return when (crest) {
            1 -> Items.RUNE_HERALDIC_HELM_8464
            2 -> Items.RUNE_HERALDIC_HELM_8466
            3 -> Items.RUNE_HERALDIC_HELM_8468
            4 -> Items.RUNE_HERALDIC_HELM_8470
            5 -> Items.RUNE_HERALDIC_HELM_8472
            6 -> Items.RUNE_HERALDIC_HELM_8474
            7 -> Items.RUNE_HERALDIC_HELM_8476
            8 -> Items.RUNE_HERALDIC_HELM_8478
            9 -> Items.RUNE_HERALDIC_HELM_8480
            10 -> Items.RUNE_HERALDIC_HELM_8482
            11 -> Items.RUNE_HERALDIC_HELM_8484
            12 -> Items.RUNE_HERALDIC_HELM_8486
            13 -> Items.RUNE_HERALDIC_HELM_8488
            14 -> Items.RUNE_HERALDIC_HELM_8490
            15 -> Items.RUNE_HERALDIC_HELM_8492
            16 -> Items.RUNE_HERALDIC_HELM_8494
            else -> Items.RUNE_FULL_HELM_1163
        }
    }

    private fun getRuniteHeraldicShield(crest: Int): Int {
        return when (crest) {
            1 -> Items.RUNE_KITESHIELD_8714
            2 -> Items.RUNE_KITESHIELD_8716
            3 -> Items.RUNE_KITESHIELD_8718
            4 -> Items.RUNE_KITESHIELD_8720
            5 -> Items.RUNE_KITESHIELD_8722
            6 -> Items.RUNE_KITESHIELD_8724
            7 -> Items.RUNE_KITESHIELD_8726
            8 -> Items.RUNE_KITESHIELD_8728
            9 -> Items.RUNE_KITESHIELD_8730
            10 -> Items.RUNE_KITESHIELD_8732
            11 -> Items.RUNE_KITESHIELD_8734
            12 -> Items.RUNE_KITESHIELD_8736
            13 -> Items.RUNE_KITESHIELD_8738
            14 -> Items.RUNE_KITESHIELD_8740
            15 -> Items.RUNE_KITESHIELD_8742
            16 -> Items.RUNE_KITESHIELD_8744
            else -> Items.RUNE_KITESHIELD_1201
        }
    }

    private fun getHeraldicBanner(crest: Int): Int {
        return when (crest) {
            1 -> Items.BANNER_8650
            2 -> Items.BANNER_8652
            3 -> Items.BANNER_8654
            4 -> Items.BANNER_8656
            5 -> Items.BANNER_8658
            6 -> Items.BANNER_8660
            7 -> Items.BANNER_8662
            8 -> Items.BANNER_8664
            9 -> Items.BANNER_8666
            10 -> Items.BANNER_8668
            11 -> Items.BANNER_8670
            12 -> Items.BANNER_8672
            13 -> Items.BANNER_8674
            14 -> Items.BANNER_8676
            15 -> Items.BANNER_8678
            16 -> Items.BANNER_8680
            else -> Items.PLANK_960
        }
    }
}
