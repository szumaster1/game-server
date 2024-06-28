package content.global.skill.production.crafting.handlers

import core.api.*
import core.api.consts.Animations
import core.api.consts.Items
import core.api.consts.Sounds
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.skill.Skills
import core.utilities.RandomUtils
import kotlin.math.min

class LimestoneBricksCraftingListener : InteractionListener {

    override fun defineListeners() {
        onUseWith(IntType.ITEM, Items.LIMESTONE_3211, Items.CHISEL_1755) { player, used, _ ->
            if (!inInventory(player, Items.CHISEL_1755)) {
                return@onUseWith true
            }
            if (!inInventory(player, used.id)) {
                sendMessage(player, "You have ran out of limestone.")
                return@onUseWith true
            }
            if (getStatLevel(player, Skills.CRAFTING) < 12) {
                sendMessage(player, "You need a crafting level of at least 12 to turn the limestone into a brick.")
                return@onUseWith true
            }

            sendSkillDialogue(player) {
                withItems(Items.LIMESTONE_BRICK_3420)

                create { _, amount ->

                    runTask(player, 2, amount) {
                        if (amount < 1) return@runTask

                        val craftingLevel = getStatLevel(player, Skills.CRAFTING)
                        val successProbability = BASE_SUCCESS_PROBABILITY + (craftingLevel * successPerLevel)
                        val succeeded = RandomUtils.randomDouble() <= successProbability

                        playAudio(player, Sounds.CHISEL_2586)
                        animate(player, Animations.HUMAN_CHISEL_LIMESTONE_4470)

                        if (removeItem(player, used.id)) {
                            if (succeeded) {
                                rewardXP(player, Skills.CRAFTING, 6.0)
                                addItem(player, Items.LIMESTONE_BRICK_3420)
                                sendMessage(
                                    player,
                                    "You use the chisel on the limestone and carve it into a building block."
                                )
                            } else {
                                rewardXP(player, Skills.CRAFTING, 1.5)
                                addItem(player, Items.ROCK_968)
                                sendMessage(
                                    player,
                                    "You use the chisel on the limestone but fail to carve it into a building block."
                                )
                            }
                        }
                    }
                }

                calculateMaxAmount { _ ->
                    min(amountInInventory(player, used.id), amountInInventory(player, used.id))
                }
            }

            return@onUseWith true
        }
    }

    companion object {
        val MAXIMUM_SUCCESS_LEVEL = 40
        val BASE_SUCCESS_PROBABILITY = 0.75
        val MAXIMUM_SUCCESS_PROBABILITY = 1.0

        val spreadSuccess = MAXIMUM_SUCCESS_PROBABILITY - BASE_SUCCESS_PROBABILITY
        val successPerLevel = spreadSuccess / MAXIMUM_SUCCESS_LEVEL
    }
}
