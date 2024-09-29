package content.global.skill.crafting.items.armour.leather

import content.global.skill.crafting.Leather
import core.api.amountInInventory
import core.api.getStatLevel
import core.api.sendDialogue
import core.api.submitIndividualPulse
import core.game.dialogue.SkillDialogueHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import org.rs.consts.Items

class LeatherCraftingListener : InteractionListener {

    override fun defineListeners() {
        onUseWith(IntType.ITEM, LEATHER, Leather.NEEDLE) { player, used, with ->
            player.attributes["leatherId"] = used.id
            when (used.id) {
                Leather.LEATHER -> {
                    Leather.SoftLeather.open(player)
                }

                Leather.HARD_LEATHER -> {
                    handleHardLeather(player)
                }

                else -> {
                    player.dialogueInterpreter.open(48923, "dragon", used.id)
                }
            }
            return@onUseWith true
        }

        onUseWith(IntType.ITEM, Leather.HARD_LEATHER, Leather.NEEDLE) { player, used, _ ->
            if (getStatLevel(player, Skills.CRAFTING) < 28) {
                sendDialogue(player, "You need a crafting level of " + 28 + " to make a hardleather body.")
                return@onUseWith false
            }

            val handler: SkillDialogueHandler =
                object : SkillDialogueHandler(player, SkillDialogue.ONE_OPTION, Item(Items.HARDLEATHER_BODY_1131)) {
                    override fun create(amount: Int, index: Int) {
                        submitIndividualPulse(
                            entity = player,
                            pulse = HardLeatherCraftingPulse(player, used.asItem(), amount)
                        )
                    }

                    override fun getAll(index: Int): Int {
                        return amountInInventory(player, Items.HARD_LEATHER_1743)
                    }
                }
            handler.open()
            return@onUseWith true
        }
    }

    private fun handleHardLeather(player: Player) {
        val handler: SkillDialogueHandler =
            object : SkillDialogueHandler(player, SkillDialogue.ONE_OPTION, Items.HARDLEATHER_BODY_1131) {
                override fun create(amount: Int, index: Int) {
                    submitIndividualPulse(
                        entity = player,
                        pulse = HardLeatherCraftingPulse(player, Item(Items.HARD_LEATHER_1743), amount)
                    )
                }

                override fun getAll(index: Int): Int {
                    return amountInInventory(player, Items.HARD_LEATHER_1743)
                }
            }
        handler.open()
    }

    companion object {
        private val LEATHER = intArrayOf(
            Leather.LEATHER,
            Leather.GREEN_LEATHER,
            Leather.BLUE_LEATHER,
            Leather.RED_LEATHER,
            Leather.BLACK_LEATHER
        )
    }
}
