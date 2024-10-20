package content.global.skill.cooking

import core.api.*
import org.rs.consts.Animations
import org.rs.consts.Items
import org.rs.consts.Sounds
import core.game.event.ResourceProducedEvent
import core.game.node.entity.impl.Animator
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse
import core.game.world.update.flag.context.Animation
import core.tools.RandomFunction

/**
 * Represents the standard cooking pulse
 */
open class StandardCookingPulse(open val player: Player, open val scenery: Scenery, open val initial: Int, open val product: Int, open var amount: Int) : Pulse() {

    private var experience = 0.0
    private var burned = false

    var properties: Cookable? = null

    override fun start() {
        properties = Cookable.forId(initial)
        if (checkRequirements()) {
            super.start()
            cook(player, scenery, properties != null && burned, initial, product)
            amount--
        }
    }

    override fun pulse(): Boolean {
        if (amount < 1 || !checkRequirements()) {
            return true
        }
        return reward()
    }

    fun animate() {
        player.animate(getAnimation(scenery))
    }

    open fun checkRequirements(): Boolean {
        this.experience = 0.0
        if (properties != null) {
            /*
             * Handle Cook's Assistant range.
             */
            if (scenery.id == LUMBRIDGE_RANGE && !isQuestComplete(player, "Cook's Assistant")) {
                sendDialogue(player, "That requires completion of the Cook's Assistant quest in order to use it.")
                return false
            }

            if (getStatLevel(player, Skills.COOKING) < properties!!.level) {
                sendDialogue(player, "You need a cooking level of " + properties!!.level + " to cook this.")
                return false
            }

            this.experience = properties!!.experience
            this.burned = isBurned(player, scenery, initial)
        }
        if (amount < 1) {
            return false
        }

        return scenery.isActive
    }

    open fun reward(): Boolean {
        if (delay == 1) {
            var delay = if (scenery.name.lowercase().contains("range")) 5 else 4
            setDelay(delay)
            return false
        }

        if (cook(player, scenery, burned, initial, product)) {
            amount--
        } else {
            return true
        }
        return amount < 1
    }

    open fun isBurned(player: Player, scenery: Scenery, food: Int): Boolean {
        val hasGauntlets = player.equipment.containsItem(Item(Items.COOKING_GAUNTLETS_775))
        var effectiveCookingLevel = player.getSkills().getLevel(Skills.COOKING)

        val item = Cookable.forId(food)
        val low: Int
        val high: Int

        if (hasGauntlets && Cookable.gauntletValues.containsKey(
                food
            )
        ) {
            val successValues = Cookable.gauntletValues[food]
            low = successValues!![0]
            high = successValues[1]
        } else if (scenery.id == LUMBRIDGE_RANGE) {
            val successValues =
                Cookable.lumbridgeRangeValues.getOrDefault(
                    food,
                    intArrayOf(item!!.lowRange, item.highRange)
                )
            low = successValues[0]
            high = successValues[1]
        } else {
            val isFire = scenery.name.lowercase().contains("fire")
            low = if (isFire) item!!.low else item!!.lowRange
            high = if (isFire) item.high else item.highRange
        }
        val host_ratio = RandomFunction.randomDouble(100.0)
        val client_ratio = RandomFunction.getSkillSuccessChance(low.toDouble(), high.toDouble(), effectiveCookingLevel)
        return host_ratio > client_ratio
    }

    open fun cook(player: Player, sceneryId: Scenery?, burned: Boolean, initial: Int, product: Int): Boolean {
        val initialItem = Item(initial)
        val productItem = Item(product)
        animate()

        when (initial) {
            Items.SKEWERED_CHOMPY_7230, Items.SKEWERED_RABBIT_7224, Items.SKEWERED_BIRD_MEAT_9984, Items.SKEWERED_BEAST_9992, Items.IRON_SPIT_7225 -> if (RandomFunction.random(
                    15
                ) == 5
            ) {
                sendMessage(player, "Your iron spit seems to have broken in the process.")
            } else {
                if (!player.inventory.add(Item(Items.IRON_SPIT_7225))) {
                    GroundItemManager.create(Item(Items.IRON_SPIT_7225), player.location, player)
                }
            }

            Items.UNCOOKED_CAKE_1889 -> if (!player.inventory.add(Item(Items.CAKE_TIN_1887))) {
                GroundItemManager.create(Item(Items.CAKE_TIN_1887), player)
            }
        }
        if (player.inventory.remove(initialItem)) {
            if (!burned) {
                player.inventory.add(productItem)
                player.dispatch(ResourceProducedEvent(productItem.id, 1, sceneryId!!, initialItem.id))
                player.getSkills().addExperience(Skills.COOKING, experience, true)
            } else {
                player.dispatch(
                    ResourceProducedEvent(
                        Cookable.getBurnt(initial).id,
                        1,
                        sceneryId!!,
                        initialItem.id
                    )
                )
                player.inventory.add(Cookable.getBurnt(initial))
            }
            getMessage(initialItem, productItem, burned)?.let { sendMessage(player, it) }
            playAudio(player, Sounds.FRY_2577)
            return true
        }
        return false
    }

    open fun getMessage(food: Item, product: Item, burned: Boolean): String? {
        if (food.id == Items.RAW_OOMLIE_2337) {
            return "The meat is far too delicate to cook like this. Perhaps you should wrap something around it to protect it from the heat."
        }
        if (product.id == Items.SODA_ASH_1781) {
            return "You burn the seaweed into soda ash."
        }
        if (food.id == Items.RAW_SWAMP_PASTE_1940) {
            return "You warm the paste over the fire. It thickens into a sticky goo."
        }
        if (Cookable.intentionalBurn(food.id)) {
            return "You deliberately burn the perfectly good piece of meat."
        }

        if (!burned && food.name.startsWith("Raw")) {
            return "You manage to cook some " + food.name.replace("Raw ", "") + "."
        } else if (burned && food.name.startsWith("Raw")) {
            return "You accidentally burn some " + food.name.replace("Raw ", "") + "."
        }

        if (!burned && product.id == Items.NETTLE_TEA_4239) {
            return "You boil the water and make nettle tea."
        }

        if (burned && product.id == Items.NETTLE_TEA_4239 || product.id == Items.BOWL_OF_HOT_WATER_4456 || product.id == Items.CUP_OF_HOT_WATER_4460) {
            return "You accidentally let the water boil over."
        }

        if(!burned && (product.id == 4456 || product.id == 4460)) {
            return "You boil the water."
        }

        return if (!burned && food.name.startsWith(("Uncooked"))) {
            "You manage to cook some " + food.name.replace("Raw ", "") + "."
        } else {
            "You accidentally burn some " + food.name.replace("Raw ", "") + "."
        }
    }

    private fun getAnimation(scenery: Scenery): Animation {
        return if (!scenery.name.equals("fire", ignoreCase = true)) RANGE_ANIMATION else FIRE_ANIMATION
    }

    companion object {
        private val RANGE_ANIMATION = Animation(Animations.HUMAN_MAKE_PIZZA_883, Animator.Priority.HIGH)
        private val FIRE_ANIMATION = Animation(Animations.OLD_COOKING_ON_FIRE_897, Animator.Priority.HIGH)
        private const val LUMBRIDGE_RANGE = 114
    }
}
