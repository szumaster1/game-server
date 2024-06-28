package content.global.skill.production.crafting.handlers

import content.global.skill.production.crafting.data.OrigamiData
import core.api.*
import core.api.consts.Animations
import core.api.consts.Items
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.impl.Projectile
import core.game.node.entity.skill.Skills
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import kotlin.math.min

class BalloonCraftingListener : InteractionListener {

    companion object {
        private val dyesId = OrigamiData.values().map { it.requiredDye }.toIntArray()
        private val balloonId = OrigamiData.values().map { it.ballonId }.toIntArray()
        private const val defaultBalloon = Items.ORIGAMI_BALLOON_9934
        private const val balloonStructure = Items.BALLOON_STRUCTURE_9933
        private const val craftAnim = Animations.HUMAN_CRAFT_ORIGAMI_BALLOON_5140
        private const val releaseAnim = Animations.HUMAN_RELEASE_A_BALLOON_5142
    }

    override fun defineListeners() {

        onUseWith(IntType.ITEM, Items.PAPYRUS_970, Items.BALL_OF_WOOL_1759) { player, used, wool ->
            if (getQuestStage(player, "Enlightened Journey") < 1) {
                sendMessage(player, "You need start the Enlightened Journey quest in order to make this.")
                return@onUseWith false
            }
            if (removeItem(player, used.asItem()) && removeItem(player, wool.asItem())) {
                sendMessage(player, "You create the origami balloon structure.")
                animate(player, craftAnim)
                addItemOrDrop(player, balloonStructure)
            }
            return@onUseWith true
        }

        onUseWith(IntType.ITEM, intArrayOf(Items.CANDLE_36, Items.BLACK_CANDLE_38), balloonStructure) { player, used, with ->
            if (removeItem(player, used.asItem()) && removeItem(player, with.asItem())) {
                sendMessage(player, "You create the origami balloon.")
                rewardXP(player, Skills.CRAFTING, 35.0)
                animate(player, craftAnim)
                addItemOrDrop(player, defaultBalloon)
            }
            return@onUseWith true
        }

        onUseWith(IntType.ITEM, dyesId, defaultBalloon) { player, used, balloon ->
            val product = OrigamiData.productMap[used.id] ?: return@onUseWith true
            if (amountInInventory(player, used.id) == amountInInventory(player, balloon.id)) {
                if (removeItem(player, product.requiredDye) && removeItem(player, defaultBalloon)) {
                    addItem(player, product.ballonId, 1)
                }
                return@onUseWith true
            }
            sendSkillDialogue(player) {
                withItems(product.ballonId)
                create { _, a ->
                    runTask(player, 2, a) {
                        if (a < 1) return@runTask
                        if (removeItem(player, product.requiredDye) && removeItem(player, defaultBalloon)) {
                            addItem(player, product.ballonId, 1)
                        }
                    }
                }
                calculateMaxAmount { _ ->
                    min(amountInInventory(player, balloon.id), amountInInventory(player, balloon.id))
                }
            }
            return@onUseWith true
        }

        onUseWith(IntType.ITEM, balloonId, Items.TINDERBOX_590) { player, used, _ ->
            visualize(player, releaseAnim, OrigamiData.projectileMap[used.id])
            if (removeItem(player, used.asItem())) {
                queueScript(player, 1, QueueStrength.SOFT) {
                    resetAnimator(player)
                    sendMessage(player, "You light the origami balloon.")
                    rewardXP(player, Skills.FIREMAKING, 20.00)
                    val animDuration = animationDuration(Animation(releaseAnim))
                    lock(player, duration = animDuration)
                    lockInteractions(player, duration = animDuration)
                    visualize(player, releaseAnim, OrigamiData.projectileMap[used.id])
                    spawnProjectile(Projectile.getLocation(player), Location.getRandomLocation(Projectile.getLocation(player), 5, true), OrigamiData.projectileMap[used.id]!!.plus(2), 0, 40, 20, 600, 0)
                    return@queueScript stopExecuting(player)
                }
            }
            return@onUseWith true
        }
    }
}
