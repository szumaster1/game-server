package content.global.skill.production.runecrafting

import cfg.consts.Items
import cfg.consts.Vars
import content.global.skill.production.runecrafting.data.Staff
import content.global.skill.production.runecrafting.data.TalismanStaff
import content.global.skill.production.runecrafting.data.Tiara
import content.global.skill.production.runecrafting.data.Altar
import core.api.*
import core.game.dialogue.DialogueFile
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

/**
 * Represents the rc equipment interactions.
 */
class RunecraftingEquipment : InteractionListener {

    private val talismanStaff = Staff.values().map { it.item.id }.toIntArray()
    private val tiaraItem = Tiara.values().map { it.item.id }.toIntArray()
    private val tiaraValues = mapOf(Items.AIR_TIARA_5527 to 1, Items.MIND_TIARA_5529 to 2, Items.WATER_TIARA_5531 to 4, Items.EARTH_TIARA_5535 to 8, Items.FIRE_TIARA_5537 to 16, Items.BODY_TIARA_5533 to 32, Items.COSMIC_TIARA_5539 to 64, Items.CHAOS_TIARA_5543 to 128, Items.NATURE_TIARA_5541 to 256, Items.LAW_TIARA_5545 to 512, Items.DEATH_TIARA_5547 to 1024, Items.BLOOD_TIARA_5549 to 2048)
    private val varpMap = mapOf(13630 to 1, 13631 to 2, 13632 to 4, 13633 to 8, 13634 to 16, 13635 to 32, 13636 to 64, 13637 to 128, 13638 to 256, 13639 to 512, 13640 to 1024, 13641 to 2048)

    override fun defineListeners() {

        /*
         * Handles tiara equip.
         */

        onEquip(tiaraItem) { player, node ->
            setVarp(player, Vars.VARP_SCENERY_ABYSS, tiaraValues.getValue(node.id))
            return@onEquip true
        }

        /*
         * Handles the scenery reset when unequip a tiara.
         */

        onUnequip(tiaraItem) { player, _ ->
            setVarp(player, Vars.VARP_SCENERY_ABYSS, 0)
            return@onUnequip true
        }

        /*
         * Handles equip the rc staffs.
         */

        onEquip(talismanStaff) { player, node ->
            val varpValue = varpMap[node.id] ?: run {
                sendMessage(player, "Nothing interesting happens.")
                return@onEquip false
            }

            setVarp(player, Vars.VARP_SCENERY_ABYSS, varpValue)
            return@onEquip true
        }

        /*
         * Handles the scenery reset when unequip a rc staff.
         */

        onUnequip(talismanStaff) { player, _ ->
            setVarp(player, Vars.VARP_SCENERY_ABYSS, 0)
            return@onUnequip true
        }

        /*
         * Handles create rc staff and tiara on mysterious ruin.
         */

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
                                        addItemOrDrop(player, it.first.staff.item.id)
                                        rewardXP(player, Skills.RUNECRAFTING, it.first.staff.experience)
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

/**
 * Rc staffs to altar map.
 */
object TalismanStaffToAltarMapper {
    fun map(staff: TalismanStaff): Altar? {
        return when (staff) {
            TalismanStaff.AIR -> Altar.AIR
            TalismanStaff.MIND -> Altar.MIND
            TalismanStaff.WATER -> Altar.WATER
            TalismanStaff.EARTH -> Altar.EARTH
            TalismanStaff.FIRE -> Altar.FIRE
            TalismanStaff.BODY -> Altar.BODY
            TalismanStaff.COSMIC -> Altar.COSMIC
            TalismanStaff.CHAOS -> Altar.CHAOS
            TalismanStaff.NATURE -> Altar.NATURE
            TalismanStaff.LAW -> Altar.LAW
            TalismanStaff.DEATH -> Altar.DEATH
            TalismanStaff.BLOOD -> Altar.BLOOD
            else -> null
        }
    }
}