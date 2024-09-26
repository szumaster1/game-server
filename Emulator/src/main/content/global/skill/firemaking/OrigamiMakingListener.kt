package content.global.skill.firemaking

import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.impl.Projectile
import core.game.node.entity.skill.Skills
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import org.rs.consts.Animations
import org.rs.consts.Items
import kotlin.math.min

class OrigamiMakingListener : InteractionListener {

    override fun defineListeners() {

        onUseWith(IntType.ITEM, Items.PAPYRUS_970, Items.BALL_OF_WOOL_1759) { player, used, wool ->
            if (getQuestStage(player, "Enlightened Journey") < 1) {
                sendMessage(player, "You need start the Enlightened Journey quest in order to make this.")
                return@onUseWith false
            }
            if (removeItem(player, used.asItem()) && removeItem(player, wool.asItem())) {
                sendMessage(player, "You create the origami balloonIDs structure.")
                animate(player, CRAFTING_ANIMATION)
                addItemOrDrop(player, BALLOON_STRUCTURE)
            }
            return@onUseWith true
        }

        onUseWith(IntType.ITEM, intArrayOf(Items.CANDLE_36, Items.BLACK_CANDLE_38), BALLOON_STRUCTURE) { player, used, with ->
            if (removeItem(player, used.asItem()) && removeItem(player, with.asItem())) {
                sendMessage(player, "You create the origami balloonIDs.")
                rewardXP(player, Skills.CRAFTING, 35.0)
                animate(player, CRAFTING_ANIMATION)
                addItemOrDrop(player, ORIGAMI_BALLOON)
            }
            return@onUseWith true
        }

        onUseWith(IntType.ITEM, dyesIDs, ORIGAMI_BALLOON) { player, used, balloon ->
            val product = Origami.forId(used.id) ?: return@onUseWith true
            if (amountInInventory(player, used.id) == amountInInventory(player, balloon.id)) {
                if (removeItem(player, product.base) && removeItem(player, ORIGAMI_BALLOON)) {
                    addItem(player, product.product, 1)
                }
                return@onUseWith true
            }
            sendSkillDialogue(player) {
                withItems(product.product)
                create { _, a ->
                    runTask(player, 2, a) {
                        if (a < 1) return@runTask
                        if (removeItem(player, product.base) && removeItem(player, ORIGAMI_BALLOON)) {
                            addItem(player, product.product, 1)
                        }
                    }
                }
                calculateMaxAmount { _ ->
                    min(amountInInventory(player, balloon.id), amountInInventory(player, balloon.id))
                }
            }
            return@onUseWith true
        }

        onUseWith(IntType.ITEM, balloonIDs, Items.TINDERBOX_590) { player, used, _ ->
            visualize(player, RELEASE_A_BALLOON_ANIMATION, Origami.forId(used.id))
            if (removeItem(player, used.asItem())) {
                queueScript(player, 1, QueueStrength.SOFT) {
                    resetAnimator(player)
                    sendMessage(player, "You light the origami balloonIDs.")
                    rewardXP(player, Skills.FIREMAKING, 20.00)
                    val animDuration = animationDuration(Animation(RELEASE_A_BALLOON_ANIMATION))
                    lock(player, duration = animDuration)
                    lockInteractions(player, duration = animDuration)
                    visualize(player, RELEASE_A_BALLOON_ANIMATION, Origami.forId(used.id))
                    spawnProjectile(Projectile.getLocation(player), Location.getRandomLocation(Projectile.getLocation(player), 5, true), it.plus(2), 0, 40, 20, 600, 0)
                    return@queueScript stopExecuting(player)
                }
            }
            return@onUseWith true
        }
    }

    companion object {
        private val dyesIDs = Origami.values().map { it.base }.toIntArray()
        private val balloonIDs = Origami.values().map { it.product }.toIntArray()
        private const val ORIGAMI_BALLOON = Items.ORIGAMI_BALLOON_9934
        private const val BALLOON_STRUCTURE = Items.BALLOON_STRUCTURE_9933
        private const val CRAFTING_ANIMATION = Animations.HUMAN_CRAFT_ORIGAMI_BALLOON_5140
        private const val RELEASE_A_BALLOON_ANIMATION = Animations.HUMAN_RELEASE_A_BALLOON_5142
    }

}
