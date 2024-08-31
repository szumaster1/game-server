package content.global.skill.production.cooking.item

import content.global.skill.production.cooking.CookableItems
import content.global.skill.skillcape.SkillcapePerks
import content.global.skill.skillcape.SkillcapePerks.Companion.isActive
import core.api.*
import cfg.consts.Animations
import cfg.consts.Items
import cfg.consts.Sounds
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
 * Standard cooking pulse
 *
 * @param player Represents the player involved in the cooking process.
 * @param scenery Represents the environment or setting where the cooking takes place.
 * @param initial Represents the initial state or value related to the cooking pulse.
 * @param product Represents the type of product being cooked.
 * @param amount Represents the quantity of the product being cooked.
 * @constructor Standard cooking pulse initializes the cooking parameters.
 */
open class StandardCookingPulse(
    open val player: Player, // The player who is performing the cooking action.
    open val scenery: Scenery, // The environment where the cooking occurs.
    open val initial: Int, // The initial value for the cooking pulse.
    open val product: Int, // The identifier for the product being cooked.
    open var amount: Int // The amount of the product to be cooked.
) : Pulse() {

    private var experience = 0.0
    private var burned = false

    var properties: CookableItems? = null

    override fun start() {
        properties = CookableItems.forId(initial)
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

    /**
     * Animate
     *
     */
    fun animate() {
        player.animate(getAnimation(scenery))
    }

    /**
     * Check requirements
     *
     * @return
     */
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

    /**
     * Reward
     *
     * @return
     */
    open fun reward(): Boolean {
        if (delay == 1) {
            var delay = if (scenery.name.lowercase().contains("range")) 5 else 4
            if (isActive(SkillcapePerks.HASTY_COOKING, player)) {
                delay -= 1
            }
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

    /**
     * Is burned
     *
     * @param player
     * @param scenery
     * @param food
     * @return
     */
    open fun isBurned(player: Player, scenery: Scenery, food: Int): Boolean {
        val hasGauntlets = player.equipment.containsItem(Item(Items.COOKING_GAUNTLETS_775))
        var effectiveCookingLevel = player.getSkills().getLevel(Skills.COOKING)
        if (isActive(SkillcapePerks.HASTY_COOKING, player)) {
            effectiveCookingLevel -= 5
        }

        val item = CookableItems.forId(food)
        val low: Int
        val high: Int

        if (hasGauntlets && CookableItems.gauntletValues.containsKey(food)) {
            val successValues = CookableItems.gauntletValues[food]
            low = successValues!![0]
            high = successValues[1]
        } else if (scenery.id == LUMBRIDGE_RANGE) {
            val successValues = CookableItems.lumbridgeRangeValues.getOrDefault(food, intArrayOf(item!!.lowRange, item.highRange))
            low = successValues[0]
            high = successValues[1]
        } else {
            val isFire = scenery.name.lowercase().contains("fire")
            low = if (isFire) item!!.low else item!!.lowRange
            high = if (isFire) item!!.high else item!!.highRange
        }
        val host_ratio = RandomFunction.randomDouble(100.0)
        val client_ratio = RandomFunction.getSkillSuccessChance(low.toDouble(), high.toDouble(), effectiveCookingLevel)
        return host_ratio > client_ratio
    }

    /**
     * Cook
     *
     * @param player
     * @param sceneryId
     * @param burned
     * @param initial
     * @param product
     * @return
     */
    open fun cook(player: Player, sceneryId: Scenery?, burned: Boolean, initial: Int, product: Int): Boolean {
        val initialItem = Item(initial)
        val productItem = Item(product)
        animate()

        when (initial) {
            Items.SKEWERED_CHOMPY_7230, Items.SKEWERED_RABBIT_7224, Items.SKEWERED_BIRD_MEAT_9984, Items.SKEWERED_BEAST_9992, Items.IRON_SPIT_7225 -> if (RandomFunction.random(15) == 5) {
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
                        CookableItems.getBurnt(initial).id,
                        1,
                        sceneryId!!,
                        initialItem.id
                    )
                )
                player.inventory.add(CookableItems.getBurnt(initial))
            }
            getMessage(initialItem, productItem, burned)?.let { sendMessage(player, it) }
            playAudio(player, Sounds.FRY_2577)
            return true
        }
        return false
    }

    /**
     * Get message
     *
     * @param food
     * @param product
     * @param burned
     * @return
     */
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
        if (CookableItems.intentionalBurn(food.id)) {
            return "You deliberately burn the perfectly good piece of meat."
        }

        if (!burned && food.name.startsWith("Raw")) {
            return "You manage to cook some " + food.name.replace("Raw ", "") + "."
        } else if (burned && food.name.startsWith("Raw")) {
            return "You accidentally burn some " + food.name.replace("Raw ", "") + "."
        }

        if (!burned && product.id == Items.NETTLE_WATER_4237) {
            return "You boil the water and make nettle tea."
        }

        if (burned && product.id == Items.NETTLE_WATER_4237) {
            return ""
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
