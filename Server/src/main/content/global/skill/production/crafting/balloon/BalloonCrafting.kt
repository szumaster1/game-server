package content.global.skill.production.crafting.balloon

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

class BalloonCrafting : InteractionListener {
    private val dyesId = OrigamiBalloon.values().map { it.requiredDye }.toIntArray()
    private val balloonId = OrigamiBalloon.values().map { it.ballonId }.toIntArray()
    private val defaultBalloon = Items.ORIGAMI_BALLOON_9934
    private val balloonStructure = Items.BALLOON_STRUCTURE_9933
    private val craftAnim = Animations.HUMAN_CRAFT_ORIGAMI_BALLOON_5140
    private val releaseAnim = Animations.HUMAN_RELEASE_A_BALLOON_5142
    override fun defineListeners() {
        onUseWith(IntType.ITEM, Items.PAPYRUS_970, Items.BALL_OF_WOOL_1759) { p, used, wool ->
            if (getQuestStage(p, "Enlightened Journey") < 1) {
                sendMessage(p, "You need start the Enlightened Journey quest in order to make this.")
                return@onUseWith false
            }
            if (removeItem(p, used.asItem()) && removeItem(p, wool.asItem())) {
                sendMessage(p, "You create the origami balloon structure.")
                animate(p, craftAnim)
                addItemOrDrop(p, balloonStructure)
            }
            return@onUseWith true
        }

        onUseWith(IntType.ITEM, intArrayOf(Items.CANDLE_36, Items.BLACK_CANDLE_38), balloonStructure) { p, used, with ->
            if (removeItem(p, used.asItem()) && removeItem(p, with.asItem())) {
                sendMessage(p, "You create the origami balloon.")
                rewardXP(p, Skills.CRAFTING, 35.0)
                animate(p, craftAnim)
                addItemOrDrop(p, defaultBalloon)
            }
            return@onUseWith true
        }

        onUseWith(IntType.ITEM, dyesId, defaultBalloon) { p, used, balloon ->
            val product = OrigamiBalloon.productMap[used.id] ?: return@onUseWith true
            if (amountInInventory(p, used.id) == amountInInventory(p, balloon.id)) {
                if (removeItem(p, product.requiredDye) && removeItem(p, defaultBalloon)) {
                    addItem(p, product.ballonId, 1)
                }
                return@onUseWith true
            }
            sendSkillDialogue(p) {
                withItems(product.ballonId)
                create { _, a ->
                    runTask(player, 2, a) {
                        if (a < 1) return@runTask
                        if (removeItem(p, product.requiredDye) && removeItem(p, defaultBalloon)) {
                            addItem(p, product.ballonId, 1)
                        }
                    }
                }
                calculateMaxAmount { _ -> min(amountInInventory(p, balloon.id), amountInInventory(p, balloon.id)) }
            }
            return@onUseWith true
        }

        onUseWith(IntType.ITEM, balloonId, Items.TINDERBOX_590) { p, used, _ ->
            visualize(p, releaseAnim, OrigamiBalloon.projectileMap[used.id])
            if (removeItem(p, used.asItem())) {
                queueScript(p, 1, QueueStrength.SOFT) {
                    resetAnimator(p)
                    sendMessage(p, "You light the origami balloon.")
                    rewardXP(p, Skills.FIREMAKING, 20.00)
                    val animDuration = animationDuration(Animation(releaseAnim))
                    lock(p, duration = animDuration)
                    lockInteractions(p, duration = animDuration)
                    face(p, Location.getRandomLocation(Projectile.getLocation(p), 2, true))
                    visualize(p, releaseAnim, OrigamiBalloon.projectileMap[used.id])
                    spawnProjectile(Projectile.getLocation(p), Location.getRandomLocation(Projectile.getLocation(p), 5, true), OrigamiBalloon.projectileMap[used.id]!!.plus(2), 0, 40, 20, 600, 0)
                    return@queueScript stopExecuting(p)
                }
            }
            return@onUseWith true
        }
    }
}