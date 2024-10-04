package content.global.skill.runecrafting

import content.global.skill.runecrafting.`object`.Altar
import content.global.skill.runecrafting.items.Staff
import content.global.skill.runecrafting.items.TalismanStaff
import content.global.skill.runecrafting.items.Tiara
import core.api.*
import core.game.dialogue.DialogueFile
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import org.rs.consts.Items
import org.rs.consts.Vars

class RunecraftingListener : InteractionListener {

    private val pouchIDs = (5509..5515).toIntArray()
    private val varpIDs = (13630..13641).associateWith { 1 shl (it - 13630) }
    private val staffIDs = Staff.values().map { it.item }.toIntArray()
    private val tiaraIDs = Tiara.values().map { it.item.id }.toIntArray()

    override fun defineListeners() {

        /*
         * Handles the pouches.
         */

        on(pouchIDs, IntType.ITEM, "fill", "empty", "check", "drop") { player, node ->
            val option = getUsedOption(player)
            val runeEssenceAmount = amountInInventory(player, Items.RUNE_ESSENCE_1436)
            val pureEssenceAmount = amountInInventory(player, Items.PURE_ESSENCE_7936)

            if (runeEssenceAmount == pureEssenceAmount && option == "fill") return@on true

            val essence = checkAmount(runeEssenceAmount, pureEssenceAmount)

            when (option) {
                "fill" -> player.pouchManager.addToPouch(node.id, essence.amount, essence.id)
                "empty" -> player.pouchManager.withdrawFromPouch(node.id)
                "check" -> player.pouchManager.checkAmount(node.id)
                "drop" -> openDialogue(player, 9878, Item(node.id))
            }

            return@on true
        }

        /*
         * Handles equip a tiara and enter to altar.
         */

        onEquip(tiaraIDs) { player, node ->
            setVarp(player, Vars.VARP_SCENERY_ABYSS, tiaraIDs[node.id] ?: 0)
            return@onEquip true
        }

        /*
         * Handles hidden scenery options.
         */

        onUnequip(tiaraIDs) { player, _ ->
            setVarp(player, Vars.VARP_SCENERY_ABYSS, 0)
            return@onUnequip true
        }

        /*
         * Handles equip a talisman staff and enter to altar.
         */

        onEquip(staffIDs) { player, node ->
            val varpValue = varpIDs[node.id] ?: run {
                sendMessage(player, "Nothing interesting happens.")
                return@onEquip false
            }
            setVarp(player, Vars.VARP_SCENERY_ABYSS, varpValue)
            return@onEquip true
        }

        /*
         * Handles hidden scenery options.
         */

        onUnequip(staffIDs) { player, _ ->
            setVarp(player, Vars.VARP_SCENERY_ABYSS, 0)
            return@onUnequip true
        }

        /*
         * Handles separate options dialogue,
         * If player has both (tiara and staff) in inventory.
         */

        TalismanStaff.values().forEach { item ->
            val altar = map(item)
            altar?.let {
                onUseWith(IntType.SCENERY, item.item.id, it.objs) { player, used, _ ->
                    setTitle(player, 2)
                    sendDialogueOptions(player, "Do you want to enchant a tiara or staff?", "Tiara.", "Staff.")
                    openDialogue(player, object : DialogueFile() {
                        override fun handle(componentID: Int, buttonID: Int) {
                            when (buttonID) {
                                1 -> enchantTiara(player, used.id, item)
                                2 -> enchantStaff(player, used.id, item)
                            }
                        }
                    })
                    return@onUseWith true
                }
            }
        }
    }

    /*
     * Map the rc staff to ruins.
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

    /*
     * Check essences amount.
     */

    private fun checkAmount(runeEssenceAmount: Int, pureEssenceAmount: Int): Item {
        val isRunePreferred = runeEssenceAmount >= pureEssenceAmount
        val id = if (isRunePreferred) Items.RUNE_ESSENCE_1436 else Items.PURE_ESSENCE_7936
        val amount = if (isRunePreferred) runeEssenceAmount else pureEssenceAmount

        return Item(id, amount)
    }

    /*
     * Handles enchanting tiara item.
     */

    private fun enchantTiara(player: Player, itemId: Int, product: TalismanStaff) {
        if (!inInventory(player, Items.TIARA_5525)) {
            sendMessage(player, "You need a tiara.")
            return
        }
        if (removeItem(player, Item(itemId))) {
            replaceSlot(player, Item(Items.TIARA_5525).slot, Item(product.tiara))
            rewardXP(player, Skills.RUNECRAFTING, Staff.forStaff(product.tiara)!!.experience)
            sendMessage(player, "You bind the power of the talisman into your tiara.")
        }
    }

    /*
     * Handles enchanting runecrafting staff.
     */

    private fun enchantStaff(player: Player, itemId: Int, product: TalismanStaff) {
        if (!inInventory(player, Items.RUNECRAFTING_STAFF_13629) ) {
            sendMessage(player, "You need a runecrafting staff.")
            return
        }
        if (removeItem(player, Item(itemId))) {
            replaceSlot(player, Item(Items.RUNECRAFTING_STAFF_13629).slot, Item(product.staff.item))
            rewardXP(player, Skills.RUNECRAFTING, product.staff.experience)
            sendMessage(player, "You bind the power of the talisman into your staff.")
        }
    }

}
