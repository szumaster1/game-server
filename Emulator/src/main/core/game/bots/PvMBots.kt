package core.game.bots

import content.data.consumables.Consumables.Companion.getConsumableById
import core.game.node.entity.Entity
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.prayer.PrayerType
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.map.Location
import core.game.world.map.RegionManager.getLocalNpcs
import core.tools.RandomFunction

/**
 * PvMBots is a specialized AIPlayer designed for player vs monster (PvM) activities.
 */
open class PvMBots : AIPlayer {

    /**
     * A private variable used to keep track of the current game state or progression in the PvMBots class.
     * This variable increments with each tick cycle and is used to manage various in-game actions and behaviors.
     */
    private var tick = 0

    /**
     * Constructor for initializing with a non-null location.
     *
     * @param l The location to be passed, which might be nullable.
     * @throws NullPointerException if the location is null.
     */
    constructor(l: Location?) : super(l!!)

    /**
     * Constructs an instance of the PvMBots class by copying details from a given file and location.
     *
     * @param copyFromFile The file path from which to copy details. Must not be null.
     * @param l The location associated with this instance. Must not be null.
     */
    constructor(copyFromFile: String?, l: Location?) : super(copyFromFile!!, l!!)

    /**
     * Finds and returns a list of target entities within a specified radius from the given entity.
     *
     * @param entity The entity from which the search radius is calculated. Should not be null.
     * @param radius The radius within which to search for target entities.
     * @return A list of target entities if any are found within the specified radius, otherwise null.
     */
    fun FindTargets(entity: Entity?, radius: Int): List<Entity>? {
        val targets: MutableList<Entity> = ArrayList(20)
        val localNPCs: Array<Any> = getLocalNpcs(entity!!, radius).toTypedArray()
        var length = localNPCs.size
        if (length > 5) {
            length = 5
        }
        for (i in 0 until length) {
            val npc = localNPCs[i] as NPC
            run {
                if (checkValidTargets(npc)) targets.add(npc)
            }
        }
        if (targets.size == 0) return null
        return targets
    }

    /**
     * Validates whether the specified NPC target is a valid target for attack.
     *
     * @param target The NPC to be checked.
     * @return True if the target is valid, else false.
     */
    fun checkValidTargets(target: NPC): Boolean {
        if (!target.isActive) {
            return false
        }
        if (!target.properties.isMultiZone && target.inCombat()) {
            return false
        }
        if (!target.definition.hasAction("attack")) {
            return false
        }
        return true
    }

    /**
     * Attacks all non-player characters (NPCs) within a specified radius.
     *
     * @param radius The radius within which to attack NPCs.
     * @return Boolean indicating whether any NPCs were successfully attacked.
     */
    fun AttackNpcsInRadius(radius: Int): Boolean {
        return AttackNpcsInRadius(this, radius)
    }

    /**
     * Attempts to find and attack NPCs within a given radius from the bot player.
     * If the bot is already in combat, it does nothing and returns true.
     *
     * @param bot The player initiating the attack.
     * @param radius The radius within which to search for NPCs.
     * @return `true` if the bot successfully initiates combat with an NPC, `false` otherwise.
     */
    fun AttackNpcsInRadius(bot: Player, radius: Int): Boolean {
        if (bot.inCombat()) return true
        var creatures = FindTargets(bot, radius) ?: return false
        bot.attack(creatures[RandomFunction.getRandom((creatures.size - 1))])
        if (!creatures.isEmpty()) {
            return true
        } else {
            creatures = FindTargets(bot, radius)!!
            if (!creatures.isEmpty()) {
                bot.attack(creatures[RandomFunction.getRandom((creatures.size - 1))])
                return true
            }
            return false
        }
    }

    /**
     * Overrides the tick method to update the state of the bot on each game tick.
     *
     * This method:
     * - Increments the tick counter.
     * - Checks if the bot's lifepoints are at or below 5. If so, it resets the lifepoints to 20.
     * - Resets the tick counter to 0 after 100 ticks.
     *
     * The method also contains commented out sections for potential future functionalities,
     * such as NPC combat and teleportation in case of low lifepoints.
     */
    override fun tick() {
        super.tick()

        tick++

        //Despawn
        if (getSkills().lifepoints <= 5) {
            //TODO: Just respawn a new bot (not sure how you'd do that :L)
            // Maybe make all PvMBots know what to do if they aren't in right area? I.e. pest control bots teleport to PC
            //this.teleport(new Location(500, 500));
            //Despawning not being delayed causes 3 errors in the console
            getSkills().lifepoints = 20
        }

        //Npc Combat
        /*if (this.tick % 10 == 0) {
			if (!this.inCombat())
				AttackNpcsInRadius(this, 5);
		}*/
        if (this.tick == 100) this.tick = 0

        //this.eat();
        //this.getPrayer().toggle()
    }

    /**
     * Toggles the specified types of prayers for the player.
     *
     * @param type an array of PrayerType objects representing the types of prayers to be toggled.
     */
    fun CheckPrayer(type: Array<PrayerType?>) {
        for (i in type.indices) prayer.toggle(type[i])
    }

    /**
     * Consumes a food item specified by the given food ID, assuming certain conditions are met.
     *
     * This method first checks if the player's current hitpoints are at least three times
     * their life points and if the item exists in the inventory. If so, it locks the player
     * for 3 ticks, retrieves and consumes the food item, and then delays the next attack by 3 ticks.
     *
     * If the victim is not a player and the inventory doesn't contain the specified food item,
     * the method adds five of the specified food items to the inventory.
     *
     * @param foodId The ID of the food item to be consumed or added to the inventory.
     */
    fun eat(foodId: Int) {
        val foodItem = Item(foodId)

        if ((getSkills().getStaticLevel(Skills.HITPOINTS) >= getSkills().lifepoints * 3) && inventory.containsItem(
                foodItem
            )
        ) {
            this.lock(3)
            //this.animate(new Animation(829));
            val food = inventory.getItem(foodItem)

            val consumable = getConsumableById(food.id)!!.consumable ?: return

            consumable.consume(food, this)
            properties.combatPulse.delayNextAttack(3)
        }
        if (this.checkVictimIsPlayer() == false) if (!(inventory.contains(foodId, 1))) inventory.add(
            Item(
                foodId,
                5
            )
        ) //Add Food to Inventory
    }
}
