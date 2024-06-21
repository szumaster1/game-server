package content.global.skill.production.crafting.battlestaff

import core.api.*
import core.api.consts.Items
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.entity.skill.Skills
import kotlin.math.min

class BattlestaffListener : InteractionListener {

    private val battlestaff = Items.BATTLESTAFF_1391
    val orbs = BattlestaffProduct.values().map { it.requiredOrbItemId }.toIntArray()

    override fun defineListeners() {
        onUseWith(IntType.ITEM, orbs, battlestaff) { player, used, with ->
            val product = BattlestaffProduct.productMap[used.id] ?: return@onUseWith true

            if (!hasLevelDyn(player, Skills.CRAFTING, product.minimumLevel)) {
                sendMessage(player, "You need a Crafting level of ${product.minimumLevel} to make this.")
                return@onUseWith true
            }

            if (amountInInventory(player, used.id) == 1 || amountInInventory(player, with.id) == 1) {
                if (removeItem(player, product.requiredOrbItemId) && removeItem(player, Items.BATTLESTAFF_1391)) {
                    addItem(player, product.producedItemId, product.amountProduced)
                    rewardXP(player, Skills.CRAFTING, product.experience)
                }
                return@onUseWith true
            }

            sendSkillDialogue(player) {
                withItems(product.producedItemId)
                create { _, amount ->

                    runTask(player, 2, amount) {
                        if (amount < 1) return@runTask

                        if (removeItem(player, product.requiredOrbItemId) && removeItem(player, Items.BATTLESTAFF_1391)) {
                            addItem(player, product.producedItemId, product.amountProduced)
                            rewardXP(player, Skills.CRAFTING, product.experience)
                        }

                        if (product.producedItemId == Items.AIR_BATTLESTAFF_1397) {
                            player.achievementDiaryManager.finishTask(player, DiaryType.VARROCK, 2, 6)
                        } else return@runTask
                    }
                }

                calculateMaxAmount { _ ->
                    min(amountInInventory(player, with.id), amountInInventory(player, used.id))
                }
            }

            return@onUseWith true
        }
    }
}