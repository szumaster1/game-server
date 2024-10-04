package content.global.skill.runecrafting

import content.global.skill.runecrafting.`object`.Altar
import content.global.skill.runecrafting.items.Staves
import content.global.skill.runecrafting.items.TalismanStaves
import content.global.skill.runecrafting.items.Tiara
import core.api.*
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
    private val stavesIDs = Staves.values().map { it.item }.toIntArray()
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

        onEquip(stavesIDs) { player, node ->
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

        onUnequip(stavesIDs) { player, _ ->
            setVarp(player, Vars.VARP_SCENERY_ABYSS, 0)
            return@onUnequip true
        }

        /*
         * Handles separate options dialogue,
         * If player has both (tiara and staff) in inventory.
         */

        TalismanStaves.values().forEach { item ->
            val altar = map(item)
            altar?.let {
                onUseWith(IntType.SCENERY, item.items.id, it.objs) { player, used, _ ->
                    setTitle(player, 2)
                    sendDialogueOptions(player, "Do you want to enchant a tiara or staff?", "Tiara.", "Staff.")
                    addDialogueAction(player) { p, button ->
                        if (button == 2 && !inInventory(p, Items.TIARA_5525, 1)) {
                            return@addDialogueAction sendMessage(p, "You need a tiara.")
                        }
                        if (button == 3 && !inInventory(p, Items.RUNECRAFTING_STAFF_13629, 1)) {
                            return@addDialogueAction sendMessage(p, "You need a runecrafting staff.")
                        }
                        enchant(p, used.asItem(), button, item)
                    }
                    return@onUseWith true
                }
            }
        }
    }

    /*
     * Map the rc staff to ruins.
     */
    fun map(staff: TalismanStaves): Altar? {
        return when (staff) {
            TalismanStaves.AIR -> Altar.AIR
            TalismanStaves.MIND -> Altar.MIND
            TalismanStaves.WATER -> Altar.WATER
            TalismanStaves.EARTH -> Altar.EARTH
            TalismanStaves.FIRE -> Altar.FIRE
            TalismanStaves.BODY -> Altar.BODY
            TalismanStaves.COSMIC -> Altar.COSMIC
            TalismanStaves.CHAOS -> Altar.CHAOS
            TalismanStaves.NATURE -> Altar.NATURE
            TalismanStaves.LAW -> Altar.LAW
            TalismanStaves.DEATH -> Altar.DEATH
            TalismanStaves.BLOOD -> Altar.BLOOD
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

    /**
     * This function handles the enchanting process.
     *
     * @param player    The player.
     * @param itemId    The talisman id.
     * @param buttonId  The button id.
     * @param product   The enchanted item.
     */
    private fun enchant(player: Player, itemId: Item, buttonId: Int, product: TalismanStaves) {
        closeDialogue(player)
        removeItem(player, if (buttonId == 3) Items.RUNECRAFTING_STAFF_13629 else Items.TIARA_5525)
        replaceSlot(player, itemId.slot, if (buttonId == 3) Item(product.staves.item) else Item(product.tiara))
        rewardXP(player, Skills.RUNECRAFTING, product.staves.experience) /*
                                                                         * Same experience for both items.
                                                                         */
        sendMessage(player, "You bind the power of the talisman into your ${if (buttonId == 3) "staff" else "tiara"}.")
    }

}
