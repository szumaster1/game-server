package content.global.skill.production.runecrafting.staff

import core.api.*
import core.api.consts.Components
import core.api.consts.Items
import core.api.consts.Vars
import core.game.dialogue.DialogueFile
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

class TalismanStaffListener : InteractionListener {

    private val talismanStaff = Staff.values().map{ it.item.id }.toIntArray()

    override fun defineListeners() {

        onEquip(talismanStaff){ player, node ->
            when(node.id){
                13630 -> setVarp(player, Vars.VARP_SCENERY_ABYSS, 1, true)
                13631 -> setVarp(player, Vars.VARP_SCENERY_ABYSS, 2, true)
                13632 -> setVarp(player, Vars.VARP_SCENERY_ABYSS, 4, true)
                13633 -> setVarp(player, Vars.VARP_SCENERY_ABYSS, 8, true)
                13634 -> setVarp(player, Vars.VARP_SCENERY_ABYSS, 16, true)
                13635 -> setVarp(player, Vars.VARP_SCENERY_ABYSS, 32, true)
                13636 -> setVarp(player, Vars.VARP_SCENERY_ABYSS, 64, true)
                13637 -> setVarp(player, Vars.VARP_SCENERY_ABYSS, 128, true)
                13638 -> setVarp(player, Vars.VARP_SCENERY_ABYSS, 256, true)
                13639 -> setVarp(player, Vars.VARP_SCENERY_ABYSS, 512, true)
                13640 -> setVarp(player, Vars.VARP_SCENERY_ABYSS, 1024, true)
                13641 -> setVarp(player, Vars.VARP_SCENERY_ABYSS, 2048, true)
                else -> sendMessage(player, "Nothing interesting happens.")
            }
            return@onEquip true
        }

        onUnequip(talismanStaff){ player, _ ->
            setVarp(player, Vars.VARP_SCENERY_ABYSS, 0)
            return@onUnequip true
        }

        TalismanStaff.values()
            .map { Pair(it, TalismanStaffToAltarMapper.map(it)) }
            .filter { it.second != null }
            .forEach {
                onUseWith(IntType.SCENERY, it.first.item.id, it.second!!.`object`) { player, used, _ ->
                    setComponentVisibility(player, Components.MULTI2_228, 6, true)
                    setComponentVisibility(player, Components.MULTI2_228, 9, false)
                    player.dialogueInterpreter.sendOptions("Do you want to enchant a tiara or staff?", "Tiara.", "Staff.")
                    openDialogue(player, object : DialogueFile() {
                        override fun handle(componentID: Int, buttonID: Int) {
                            when (buttonID) {
                                1 -> {
                                    end()
                                    if (!player.inventory.containsItem(Item(Items.TIARA_5525))) {
                                        sendMessage(player, "You need a tiara.")
                                    } else {
                                        removeItem(player, used.id)
                                        removeItem(player, Item(Items.TIARA_5525))
                                        addItemOrDrop(player, it.first.tiara)
                                        rewardXP(player, Skills.RUNECRAFTING, it.second!!.tiara.experience)
                                        sendMessage(player, "You bind the power of the talisman into your tiara.")
                                    }
                                }

                                2 -> {
                                    end()
                                    if (!player.inventory.containsItem(Item(Items.RUNECRAFTING_STAFF_13629))) {
                                        sendMessage(player, "You need an runecrafting staff.")
                                    } else {
                                        removeItem(player, used.id)
                                        removeItem(player, Item(Items.RUNECRAFTING_STAFF_13629))
                                        addItemOrDrop(player, it.first.staff!!.item.id)
                                        rewardXP(player, Skills.RUNECRAFTING, it.first.staff!!.experience)
                                        sendMessage(player, "You bind the power of the talisman into your staff.")
                                    }
                                }
                            }
                        }

                    })
                    return@onUseWith true
                }
            }
    }
}