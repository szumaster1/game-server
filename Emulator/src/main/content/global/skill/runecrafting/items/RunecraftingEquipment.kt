package content.global.skill.runecrafting.items

import content.global.skill.runecrafting.altars.Altar
import core.api.*
import core.game.dialogue.DialogueFile
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import org.rs.consts.Items
import org.rs.consts.Vars

/**
 * Handles options if player has both items (**tiara and staff**) in inventory.
 */
class RunecraftingEquipment : InteractionListener {

    private val talismanStaff = Staff.values().map { it.item }.toIntArray()
    private val tiaraItem = Tiara.values().map { it.item.id }.toIntArray()
    private val tiaraValues = mapOf(Items.AIR_TIARA_5527 to 1, Items.MIND_TIARA_5529 to 2, Items.WATER_TIARA_5531 to 4, Items.EARTH_TIARA_5535 to 8, Items.FIRE_TIARA_5537 to 16, Items.BODY_TIARA_5533 to 32, Items.COSMIC_TIARA_5539 to 64, Items.CHAOS_TIARA_5543 to 128, Items.NATURE_TIARA_5541 to 256, Items.LAW_TIARA_5545 to 512, Items.DEATH_TIARA_5547 to 1024, Items.BLOOD_TIARA_5549 to 2048)
    private val varpMap = (13630..13641).associateWith { 1 shl (it - 13630) }

    /**
     * Map the [TalismanStaff] to [Altar].
     */
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

    override fun defineListeners() {
        /*
         * Handles equip a tiara and enter to altar.
         */

        onEquip(tiaraItem) { player, node ->
            setVarp(player, Vars.VARP_SCENERY_ABYSS, tiaraValues[node.id] ?: 0)
            return@onEquip true
        }

        /*
         * Handles hidden scenery options.
         */

        onUnequip(tiaraItem) { player, _ ->
            setVarp(player, Vars.VARP_SCENERY_ABYSS, 0)
            return@onUnequip true
        }

        /*
         * Handles equip a talisman staff and enter to altar.
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
         * Handles hidden scenery options.
         */

        onUnequip(talismanStaff) { player, _ ->
            setVarp(player, Vars.VARP_SCENERY_ABYSS, 0)
            return@onUnequip true
        }

        /*
         * Handles separate options dialogue,
         * If player has both (tiara and staff) in inventory.
         */

        TalismanStaff.values().forEach { staff ->
            val altar = map(staff)
            altar?.let {
                onUseWith(IntType.SCENERY, staff.item.id, it.scenery) { player, used, _ ->
                    setTitle(player, 2)
                    sendDialogueOptions(player, "Do you want to enchant a tiara or staff?", "Tiara.", "Staff.")
                    openDialogue(player, object : DialogueFile() {
                        override fun handle(componentID: Int, buttonID: Int) {
                            when (buttonID) {
                                1 -> enchantTiara(player, used.asItem(), staff)
                                2 -> enchantStaff(player, used.asItem(), staff)
                            }
                        }
                    })
                    return@onUseWith true
                }
            }
        }
    }

    private fun enchantTiara(player: Player, used: Item, staff: TalismanStaff) {
        if (!inInventory(player, Items.TIARA_5525)) {
            sendMessage(player, "You need a tiara.")
            return
        }
        removeItem(player, used.id)
        removeItem(player, Item(Items.TIARA_5525))
        addItemOrDrop(player, staff.tiara)
        rewardXP(player, Skills.RUNECRAFTING, Staff.forStaff(staff.tiara)!!.experience)
        sendMessage(player, "You bind the power of the talisman into your tiara.")
    }

    private fun enchantStaff(player: Player, used: Item, staff: TalismanStaff) {
        if (!inInventory(player, Items.RUNECRAFTING_STAFF_13629)) {
            sendMessage(player, "You need a runecrafting staff.")
            return
        }
        removeItem(player, used.id)
        removeItem(player, Item(Items.RUNECRAFTING_STAFF_13629))
        addItemOrDrop(player, staff.staff.item)
        rewardXP(player, Skills.RUNECRAFTING, staff.staff.experience)
        sendMessage(player, "You bind the power of the talisman into your staff.")
    }
}
