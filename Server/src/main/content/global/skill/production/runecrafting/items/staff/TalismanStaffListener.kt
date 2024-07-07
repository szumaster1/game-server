package content.global.skill.production.runecrafting.items.staff

import content.global.skill.production.runecrafting.data.Staff
import content.global.skill.production.runecrafting.data.TalismanStaff
import core.api.*
import core.api.consts.Items
import core.api.consts.Vars
import core.game.dialogue.DialogueFile
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

class TalismanStaffListener : InteractionListener {

    private val talismanStaff = Staff.values().map { it.item.id }.toIntArray()

    override fun defineListeners() {

        onEquip(talismanStaff) { player, node ->
            val varpValues = mapOf(
                13630 to 1, 13631 to 2, 13632 to 4, 13633 to 8, 13634 to 16,
                13635 to 32, 13636 to 64, 13637 to 128, 13638 to 256,
                13639 to 512, 13640 to 1024, 13641 to 2048
            )

            val varpValue = varpValues[node.id] ?: run {
                sendMessage(player, "Nothing interesting happens.")
                return@onEquip false
            }

            setVarp(player, Vars.VARP_SCENERY_ABYSS, varpValue, true)
            return@onEquip true
        }

        onUnequip(talismanStaff) { player, _ ->
            setVarp(player, Vars.VARP_SCENERY_ABYSS, 0)
            return@onUnequip true
        }

        TalismanStaff.values()
            .map { Pair(it, TalismanStaffToAltarMapper.map(it)) }
            .filter { it.second != null }
            .forEach {
                onUseWith(IntType.SCENERY, it.first.item.id, it.second!!.scenery) { player, used, _ ->
                    setTitle(player, 2)
                    sendDialogueOptions(player, "Do you want to enchant a tiara or staff?", "Tiara.", "Staff.")
                    openDialogue(player, object : DialogueFile() {
                        override fun handle(componentID: Int, buttonID: Int) {
                            when (buttonID) {
                                1 -> {
                                    end()
                                    if (!inInventory(player, Items.TIARA_5525)) {
                                        sendMessage(player, "You need a tiara.")
                                    } else {
                                        removeItem(player, used.id)
                                        removeItem(player, Item(Items.TIARA_5525))
                                        addItemOrDrop(player, it.first.tiara)
                                        rewardXP(player, Skills.RUNECRAFTING, it.second!!.tiara!!.experience)
                                        sendMessage(player, "You bind the power of the talisman into your tiara.")
                                    }
                                }

                                2 -> {
                                    end()
                                    if (!inInventory(player, Items.RUNECRAFTING_STAFF_13629)) {
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
