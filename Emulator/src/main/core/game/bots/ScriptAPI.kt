package core.game.bots

import content.data.consumables.Consumables
import content.data.consumables.effects.HealingEffect
import core.Configuration
import core.Configuration.Companion.SERVER_GE_NAME
import core.api.itemDefinition
import core.api.log
import core.api.sendNews
import core.api.utils.Vector
import core.cache.def.impl.ItemDefinition
import core.game.component.Component
import core.game.consumable.Consumable
import core.game.consumable.Food
import core.game.ge.GrandExchange
import core.game.ge.GrandExchangeOffer
import core.game.interaction.*
import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.GroundItem
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.system.config.ItemConfigParser
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Location
import core.game.world.map.RegionManager
import core.game.world.map.path.Pathfinder
import core.game.world.repository.Repository
import core.game.world.update.flag.EntityFlag
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.ChatMessage
import core.game.world.update.flag.context.Graphic
import core.tools.Log
import core.tools.RandomFunction
import core.tools.colorize
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.rs.consts.Items
import java.util.concurrent.CountDownLatch
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * The ScriptAPI class provides an interface for interacting with various in-game elements such as
 * nodes, players, and the environment. It includes methods for navigating, interacting with objects,
 * sending messages, and more.
 *
 * @property bot The player instance that represents the bot.
 * @property GRAPHICSUP A variable related to graphical updates.
 * @property ANIMATIONUP A variable related to animation updates.
 * @property GRAPHICSDOWN A variable related to graphics downtimes.
 * @property ANIMATIONDOWN A variable related to animation downtimes.
 */
class ScriptAPI(private val bot: Player) {
    /**
     * A constant variable representing a specific `Graphic` instance.
     *
     * `GRAPHICSUP` is initialized with the `Graphic` class, with an identifier of 1576.
     * This variable might be used to represent a particular graphic resource in the system.
     */
    val GRAPHICSUP = Graphic(1576)
    /**
     * ANIMATIONUP represents a specific animation instance within the application.
     * It is initialized with the ID 8939, which corresponds to a predefined animation sequence.
     * This constant is typically used to refer to the upward movement animation within the system.
     *
     * The ID value helps in identifying and invoking the correct animation logic for rendering
     * relevant visuals in the application.
     */
    val ANIMATIONUP = Animation(8939)
    /**
     * Constant variable representing a specific graphic effect or image asset.
     * The integer value 1577 associates with a particular graphic that can be
     * used in rendering or other graphical operations within the application.
     */
    val GRAPHICSDOWN = Graphic(1577)
    /**
     * ANIMATIONDOWN is an instance of the Animation class initialized with the ID 8941.
     * This variable typically represents a specific type of animation used within an application.
     */
    val ANIMATIONDOWN = Animation(8941)

    /**
     * Calculates the Euclidean distance between two nodes.
     *
     * @param n1 The first node containing the x and y coordinates.
     * @param n2 The second node containing the x and y coordinates.
     * @return The Euclidean distance between the two nodes.
     */
    fun distance(n1: Node, n2: Node): Double {
        return sqrt(
            (n1.location.x - n2.location.x.toDouble()).pow(2.0) + (n2.location.y - n1.location.y.toDouble()).pow(
                2.0
            )
        )
    }

    /**
     * Interacts with a given node based on the specified option.
     *
     * @param bot The player who is initiating the interaction.
     * @param node The target node to interact with. It can be of type `Scenery`, `NPC`, or `Item`.
     * @param option The interaction option name to use.
     */
    fun interact(bot: Player, node: Node?, option: String) {

        if (node == null) return

        val type = when (node) {
            is Scenery -> IntType.SCENERY
            is NPC -> IntType.NPC
            is Item -> IntType.ITEM
            else -> null
        } ?: return
        val opt: Option? = node.interaction.options.filter { it != null && it.name.equals(option, true) }.firstOrNull()

        if (opt == null) {
            log(this::class.java, Log.WARN, "Invalid option name provided: $option")
            return
        }

        if (!InteractionListeners.run(node.id, type, option, bot, node)) node.interaction.handle(bot, opt)
    }

    /**
     * Uses the specified item on the provided node within the game context.
     *
     * This method determines the type of node and handles item usage interactions,
     * triggering the appropriate event listeners and handlers.
     *
     * @param bot The player performing the interaction.
     * @param itemId The ID of the item to be used.
     * @param node The target node where the item will be used. If null, the method returns immediately.
     */
    fun useWith(bot: Player, itemId: Int, node: Node?) {
        if (node == null) return

        val type = when (node) {
            is Scenery -> IntType.SCENERY
            is NPC -> IntType.NPC
            is Item -> IntType.ITEM
            else -> null
        } ?: return

        val item = bot.inventory.getItem(Item(itemId))

        val childNode = node.asScenery()?.getChild(bot)

        if (InteractionListeners.run(item, node, type, bot)) return
        if (childNode != null && childNode.id != node.id) {
            if (InteractionListeners.run(item, childNode, type, bot)) return
        }
        val flipped = type == IntType.ITEM && item.id < node.id
        val event = if (flipped) NodeUsageEvent(bot, 0, node, item)
        else NodeUsageEvent(bot, 0, item, childNode ?: node)
        if (PluginInteractionManager.handle(bot, event)) return
        UseWithHandler.run(event)
    }

    /**
     * Sends a chat message using the bot's chat system and updates the mask.
     *
     * @param message the message to be sent via chat
     */
    fun sendChat(message: String) {
        bot.sendChat(message)
        bot.updateMasks.register(EntityFlag.Chat, ChatMessage(bot, message, 0, 0))
    }

    /**
     * Retrieves the nearest node from a list based on specified names and type (object or entity).
     *
     * @param acceptedNames A list of names to be matched against nodes in the region.
     * @param isObject A boolean flag indicating whether to search for objects (true) or entities (false).
     * @return The nearest node that matches one of the accepted names, or null if no match is found.
     */
    fun getNearestNodeFromList(acceptedNames: List<String>, isObject: Boolean): Node? {
        if (isObject) return processEvaluationList(
            RegionManager.forId(bot.location.regionId).planes[bot.location.z].objectList, acceptedName = acceptedNames
        )
        else return processEvaluationList(
            RegionManager.forId(bot.location.regionId).planes[bot.location.z].entities, acceptedName = acceptedNames
        )
    }

    /**
     * Retrieves the nearest node based on the given ID.
     *
     * @param id The identification number of the node to find.
     * @param isObject A boolean flag indicating whether to search within object nodes or entity nodes.
     * @return The nearest Node if found, otherwise null.
     */
    fun getNearestNode(id: Int, isObject: Boolean): Node? {
        if (isObject) return processEvaluationList(
            RegionManager.forId(bot.location.regionId).planes[bot.location.z].objectList, acceptedId = id
        )
        else return processEvaluationList(
            RegionManager.forId(bot.location.regionId).planes[bot.location.z].entities, acceptedId = id
        )
    }

    /**
     * Finds and returns the nearest `Node` entity by its name.
     *
     * @param entityName The name of the entity to search for.
     * @return The `Node` entity closest to the calling location, or null if not found.
     */
    fun getNearestNode(entityName: String): Node? {
        return processEvaluationList(
            RegionManager.forId(bot.location.regionId).planes[bot.location.z].entities,
            acceptedName = listOf(entityName)
        )
    }

    /**
     * Finds the nearest node by name.
     *
     * @param name The name of the node to find.
     * @param isObject Specifies if the node is an object or an entity.
     * @return The nearest node if found, otherwise null.
     */
    fun getNearestNode(name: String, isObject: Boolean): Node? {
        if (isObject) return processEvaluationList(
            RegionManager.forId(bot.location.regionId).planes[bot.location.z].objectList, acceptedName = listOf(name)
        )
        else return processEvaluationList(
            RegionManager.forId(bot.location.regionId).planes[bot.location.z].entities, acceptedName = listOf(name)
        )
    }

    /**
     * Finds the nearest object that matches the given predicate.
     *
     * @param predicate A function that takes a Node? and returns a Boolean. The function is used to evaluate
     * each object to determine if it meets the criteria.
     * @return The nearest Node that matches the predicate, or null if no such object is found.
     */
    fun getNearestObjectByPredicate(predicate: (Node?) -> Boolean): Node? {
        return processEvaluationList(
            RegionManager.forId(bot.location.regionId).planes[bot.location.z].objectList, acceptedPredicate = predicate
        )
    }

    /**
     * Evaluates if a node meets certain viability criteria based on distance, names, id, and a custom predicate.
     *
     * @param e The node to be evaluated. Can be null.
     * @param minDistance The minimum distance from the bot to the node for it to be considered viable.
     * @param maxDistance The maximum distance from the bot to the node for it to be considered viable.
     * @param acceptedNames A list of accepted names for the node. Can be null.
     * @param acceptedId The accepted id for the node. Default value is -1.
     * @param acceptedPredicate A custom predicate to further evaluate the node. Can be null.
     * @return True if the node meets the viability criteria, false otherwise.
     */
    fun evaluateViability(
        e: Node?,
        minDistance: Double,
        maxDistance: Double,
        acceptedNames: List<String>? = null,
        acceptedId: Int = -1,
        acceptedPredicate: ((Node?) -> Boolean)? = null
    ): Boolean {
        if (e == null || !e.isActive) return false
        if (acceptedId != -1 && e.id != acceptedId) return false

        val dist = distance(bot, e)
        if (dist > maxDistance || dist > minDistance) return false

        if (acceptedPredicate != null) {
            return acceptedPredicate(e) && !Pathfinder.find(bot, e).isMoveNear
        } else {
            val name = e.name
            return (acceptedNames?.stream()?.anyMatch({ s -> s.equals(name, true) }) ?: true && !Pathfinder.find(
                bot, e
            ).isMoveNear)
        }
    }

    /**
     * Processes a list of nodes to find the nearest viable node based on specified criteria.
     *
     * @param list List of nodes to evaluate.
     * @param acceptedName Optional list of names to accept during evaluation.
     * @param acceptedId Optional ID to accept during evaluation.
     * @param acceptedPredicate Optional predicate function for custom evaluation.
     * @return The nearest viable node that matches the criteria, or null if no such node is found.
     */
    fun processEvaluationList(list: List<Node>, acceptedName: List<String>? = null, acceptedId: Int = -1, acceptedPredicate: ((Node?) -> Boolean)? = null): Node? {
        var entity: Node? = null
        var minDistance = Double.MAX_VALUE
        val maxDistance = Configuration.MAX_PATHFIND_DISTANCE.toDouble()
        for (e in list) {
            if (evaluateViability(e, minDistance, maxDistance, acceptedName, acceptedId, acceptedPredicate)) {
                entity = e
                minDistance = distance(bot, e)
            }
        }
        return entity
    }

    /**
     * Retrieves the nearest ground item with the specified ID that is within a distance of 10 units from the bot.
     *
     * @param id The ID of the ground item to find.
     * @return The nearest ground item with the specified ID, or null if no such item is found within the allowed distance.
     */
    private fun getNearestGroundItem(id: Int): GroundItem? {
        var distance = 11.0
        var closest: GroundItem? = null
        if (AIRepository.getItems(bot) != null) {
            for (item in AIRepository.getItems(bot)!!.filter { it: GroundItem -> it.distance(bot.location) < 10 }) {
                if (item.id == id) {
                    //distance = item.distance(bot.location)
                    closest = item
                }
            }
            if (!GroundItemManager.getItems().contains(closest)) {
                AIRepository.getItems(bot)?.remove(closest)
                return null
            }
        } else {
            val items: ArrayList<GroundItem>? = bot.getAttribute("botting:drops", null)
            if (items != null) {
                for (item in items.filter { it.distance(bot.location) < 10 }) {
                    if (item.id == id) return item.also { items.remove(item); bot.setAttribute("botting:drops", items) }
                }
            }
        }
        return closest
    }

    /**
     * Attempts to take the nearest ground item with the given ID.
     *
     * @param id The ID of the ground item to be taken.
     * @return True if the item was successfully taken, false otherwise.
     */
    fun takeNearestGroundItem(id: Int): Boolean {
        val item = getNearestGroundItem(id)
        if (item != null) {
            item.interaction?.handle(bot, item.interaction[2])
            return true
        } else return false
    }

    /**
     * Finds the nearest game object to the given location with the specified object ID.
     *
     * @param tag loc The location from which to find the nearest game object.
     * @param tag objectId The ID of the game object to find.
     * @return The nearest game object with the specified ID, or null if no such object exists.
     */
    fun getNearestGameObject(loc: Location, objectId: Int): Scenery? {
        var nearestObject: Scenery? = null
        val minDistance = Double.MAX_VALUE
        for (o in RegionManager.forId(loc.regionId).planes[0].objects) {
            for (obj in o) {
                if (obj != null) {
                    if (distance(loc, obj) < minDistance && obj.id == objectId) {
                        nearestObject = obj
                    }
                }
            }
        }
        return nearestObject
    }

    /**
     * Finds targets within a specified radius around a given entity.
     *
     * @param entity The central entity from which to search.
     * @param radius The radius within which to search for targets.
     * @param name The optional name of the targets to find. If null, all valid targets are considered.
     * @return A list of targets found within the specified radius, or null if no targets are found.
     */
    private fun findTargets(entity: Entity, radius: Int, name: String? = null): List<Entity>? {
        val targets: MutableList<Entity> = ArrayList()
        val localNPCs: Array<Any> = RegionManager.getLocalNpcs(entity, radius).toTypedArray()
        var length = localNPCs.size
        if (length > 5) {
            length = 5
        }
        for (i in 0 until length) {
            val npc = localNPCs[i] as NPC
            run { if (checkValidTargets(npc, name)) targets.add(npc) }
        }
        return if (targets.size == 0) null else targets
    }

    /**
     * Checks if the given target is a valid target based on specific criteria.
     *
     * @param target The NPC to be checked.
     * @param name The name to match against the target's name. If null, name check is bypassed.
     * @return true if the target is valid, false otherwise.
     */
    private fun checkValidTargets(target: NPC, name: String?): Boolean {
        if (!target.isActive) {
            return false
        }
        if (!target.properties.isMultiZone && target.inCombat()) {
            return false
        }
        if (name != null) {
            if (target.name != name) return false
        }
        return target.definition.hasAction("attack")
    }

    /**
     * Attacks non-playable characters (NPCs) within a specified radius around the player.
     *
     * @param bot The player who will perform the attack.
     * @param radius The radius within which NPCs will be targeted.
     * @return Returns true if at least one NPC is successfully attacked, false otherwise.
     */
    fun attackNpcsInRadius(bot: Player, radius: Int): Boolean {
        if (bot.inCombat()) return true
        var creatures: List<Entity>? = findTargets(bot, radius) ?: return false
        bot.attack(creatures!![RandomFunction.getRandom(creatures.size - 1)])
        return if (creatures.isNotEmpty()) {
            true
        } else {
            creatures = findTargets(bot, radius)
            if (!creatures!!.isEmpty()) {
                bot.attack(creatures[RandomFunction.getRandom(creatures.size - 1)])
                return true
            }
            false
        }
    }

    /**
     * Instructs the bot to walk to a specified location.
     *
     * @param loc the target destination for the bot.
     */
    fun walkTo(loc: Location) {
        if (!bot.walkingQueue.isMoving) {
            walkToIterator(loc)
        }
    }

    /**
     * Guides the bot to walk through the given array of locations step by step.
     *
     * @param steps An array of Location objects that define the path the bot should follow.
     */
    fun walkArray(steps: Array<Location>) {
        bot.pulseManager.run(object : Pulse() {
            /**
             * Index used to track the current step in the `walkArray` function's step sequence.
             */
            var stepIndex = 0
            /**
             * Executes a step-by-step walking pulse.
             *
             * This method determines the next step for the bot to take in its path. It checks
             * if the bot is near the current target step, updates the step index, and
             * commands the bot to walk to the next step if necessary. It returns a boolean
             * indicating whether the bot has completed its path.
             *
             * @return `true` if the bot has completed its path or is near the last step;
             *         `false` if the bot is still walking towards the next step.
             */
            override fun pulse(): Boolean {
                // If the stepIndex is out of bounds, we're done
                if (stepIndex >= steps.size) return true
                // If we're near the last step, we're done
                if (bot.location.withinDistance(steps[steps.size - 1], 2)) {
                    return true
                }
                // If we're not near the next step, walk to it
                if (!bot.location.withinDistance(steps[stepIndex], 5)) {
                    walkTo(steps[stepIndex])
                    return false
                }
                // If we're near the next step, increment the step index
                stepIndex++

                return false
            }
        })
    }

    /**
     * This method performs a random walk towards a specified location within a certain radius.
     * If the bot is currently not moving, it calculates a new location within the defined radius
     * around the given location and initiates movement towards it.
     *
     * @param loc The target location to walk towards.
     * @param radius The radius within which the new location will be randomly determined.
     */
    fun randomWalkTo(loc: Location, radius: Int) {
        if (!bot.walkingQueue.isMoving) {
            var newloc =
                loc.transform(RandomFunction.random(radius, -radius), RandomFunction.random(radius, -radius), 0)
            walkToIterator(newloc)
        }
    }


    /**
     * Moves the bot to the specified location using an iterator-based approach.
     *
     * @param loc The location to which the bot will walk.
     */
    private fun walkToIterator(loc: Location) {
        var diffX = loc.x - bot.location.x
        var diffY = loc.y - bot.location.y

        val vec = Vector.betweenLocs(bot.location, loc)
        val norm = vec.normalized()
        val tiles =
            kotlin.math.min(kotlin.math.floor(vec.magnitude()).toInt(), Configuration.MAX_PATHFIND_DISTANCE - 1)
        val loc = bot.location.transform(norm * tiles)
        bot.pulseManager.run(object : MovementPulse(bot, loc) {
            /**
             *
             * This method is called to indicate a periodic heartbeat or pulse.
             * Can be used to signal activity or status.
             *
             * @return Boolean indicating the pulse status.
             */
            override fun pulse(): Boolean {
                return true
            }
        })
    }

    /**
     * Attempts to attack a non-player character (NPC) within a specified radius from the given player.
     *
     * @param bot The player executing the attack.
     * @param name The name of the NPC to target.
     * @param radius The radius within which to search for targets.
     * @return Returns true if an attack is initiated; false otherwise.
     */
    fun attackNpcInRadius(bot: Player, name: String, radius: Int): Boolean {
        if (bot.inCombat()) return true
        var creatures: List<Entity>? = findTargets(bot, radius, name) ?: return false
        bot.attack(creatures!![RandomFunction.getRandom(creatures.size - 1)])
        return if (creatures.isNotEmpty()) {
            true
        } else {
            creatures = findTargets(bot, radius, name)
            if (!creatures!!.isEmpty()) {
                bot.attack(creatures.random())
                return true
            }
            false
        }
    }

    /**
     * Calculates the distance between this GroundItem's location and the specified location.
     *
     * @param loc The target location to which the distance is to be calculated.
     * @return The distance between the GroundItem's location and the specified location as a Double.
     */
    fun GroundItem.distance(loc: Location): Double {
        return location.getDistance(loc)
    }

    /**
     * Teleports the bot to the Grand Exchange (GE) if it is not tele-blocked.
     * This method will also handle necessary animations and disable impact for a short duration.
     *
     * @return True if the teleportation was successful, false if the bot is tele-blocked.
     */
    fun teleportToGE(): Boolean {
        if (bot.isTeleBlocked) {
            return false
        }
        bot.lock()
        bot.visualize(ANIMATIONUP, GRAPHICSUP)
        bot.impactHandler.disabledTicks = 4
        val location = Location.create(3165, 3482, 0)
        bot.pulseManager.run(object : Pulse(4, bot) {
            /**
             * Executes a series of actions to prepare the bot for teleportation.
             *
             * @return Always returns true indicating the completion of the pulse actions.
             */
            override fun pulse(): Boolean {
                bot.unlock()
                bot.properties.teleportLocation = location
                bot.pulseManager.clear()
                bot.animator.reset()
                return true
            }
        })
        return true
    }

    /**
     * Sells the specified item on the Grand Exchange (GE).
     *
     * @param id The ID of the item to be sold.
     */
    fun sellOnGE(id: Int) {
        class toCounterPulse : MovementPulse(bot, Location.create(3165, 3487, 0)) {
            /**
             * Executes the pulse action for the bot.
             *
             * This method performs various operations such as checking the amount of an item in the bot's bank,
             * determining the actual ID of the item (considering noted items), and attempting to sell the item
             * on the Grand Exchange. If the sale is significant, it sends a news message. Finally, it removes
             * the item from the bot's bank and refreshes the bank status.
             *
             * @return always returns true after performing the pulse action.
             */
            override fun pulse(): Boolean {
                var actualId = id
                val itemAmt = bot.bank.getAmount(id)
                if (ItemDefinition.forId(id).noteId == id) {
                    actualId = Item(id).noteChange
                }
                val canSell = GrandExchange.addBotOffer(actualId, itemAmt)
                if (canSell && saleIsBigNews(actualId, itemAmt)) {
                    Repository.sendNews(
                        SERVER_GE_NAME + " just offered " + itemAmt + " " + ItemDefinition.forId(
                            actualId
                        ).name.lowercase() + " on the GE."
                    )
                }
                bot.bank.remove(Item(id, itemAmt))
                bot.bank.refresh()
                return true
            }
        }
        bot.pulseManager.run(toCounterPulse())
    }

    /**
     * Initiates the process of selling all tradeable items in the bot's bank on the Grand Exchange.
     *
     * This method creates an internal movement pulse to a specified location and performs
     * the sale operation for each item in the bot's bank. Non-tradeable items and certain
     * excluded items such as lobsters, swordfish, and sharks are skipped. If an item can be
     * sold and qualifies as noteworthy, a news message is broadcasted.
     */
    fun sellAllOnGe() {
        class toCounterPulseAll : MovementPulse(bot, Location.create(3165, 3487, 0)) {
            /**
             * The `pulse` method processes items in the bot's bank. It iterates through each item and checks certain conditions
             * (if the item is null, if it is one of specified food items, or if it's tradeable). If an item meets these conditions,
             * it attempts to sell the item using the Grand Exchange. If the sale is significant, it sends a news notification.
             *
             * @return Returns `true` after processing all items in the bot's bank.
             */
            override fun pulse(): Boolean {
                for (item in bot.bank.toArray()) {
                    item ?: continue
                    if (item.id == Items.LOBSTER_379) continue
                    if (item.id == Items.SWORDFISH_373) continue
                    if (item.id == Items.SHARK_385) continue
                    if (!item.definition.isTradeable) {
                        continue
                    }
                    val itemAmt = item.amount
                    var actualId = item.id
                    if (ItemDefinition.forId(actualId).noteId == actualId) {
                        actualId = Item(actualId).noteChange
                    }
                    val canSell = GrandExchange.addBotOffer(actualId, itemAmt)
                    if (canSell && saleIsBigNews(actualId, itemAmt)) {
                        sendNews(SERVER_GE_NAME + " just offered " + itemAmt + " " + ItemDefinition.forId(actualId).name.lowercase() + " on the GE.")
                    }
                    bot.bank.remove(item)
                    bot.bank.refresh()
                }
                return true
            }
        }
        bot.pulseManager.run(toCounterPulseAll())
    }

    /**
     * Initiates the process to sell all tradeable items available in the bot's bank on the Grand Exchange.
     *
     * The method locates the nearest "Desk" node in the environment and triggers a movement pulse
     * to interact with it. Upon reaching the desk:
     *
     * - Iterates over all items in the bot's bank.
     * - Skips items that are not tradeable.
     * - Converts any noted items back to their unnoted form if necessary.
     * - Attempts to add each tradeable item to the Grand Exchange offer.
     * - Notifies of significant sales unless the item is one of the specified log types.
     * - Removes items from the bank and refreshes the bank state after adding them to the offer.
     */
    fun sellAllOnGeAdv() {
        val ge: Scenery? = getNearestNode("Desk", true) as Scenery?

        class toCounterPulseAll : MovementPulse(bot, ge, DestinationFlag.OBJECT) {
            /**
             * Handles the process of posting items from the bot's bank to the Grand Exchange if they are tradeable.
             * It iterates through the items in the bank, checks if they are tradeable, and attempts to sell them.
             * If an item can be sold and is considered "big news," a notification is sent.
             *
             * @return Always returns true after processing all items in the bank.
             */
            override fun pulse(): Boolean {
                for (item in bot.bank.toArray()) {
                    item ?: continue
                    if (!item.definition.isTradeable) {
                        continue
                    }
                    val itemAmt = item.amount
                    var actualId = item.id
                    if (ItemDefinition.forId(actualId).noteId == actualId) {
                        actualId = Item(actualId).noteChange
                    }
                    val canSell = GrandExchange.addBotOffer(actualId, itemAmt)
                    if (canSell && saleIsBigNews(actualId, itemAmt)) {
                        when (actualId) {
                            1511 -> continue
                            1513 -> continue
                            1515 -> continue
                            1517 -> continue
                            1519 -> continue
                            1521 -> continue
                            else -> Repository.sendNews(
                                SERVER_GE_NAME + " just offered " + itemAmt + " " + ItemDefinition.forId(
                                    actualId
                                ).name.lowercase() + " on the GE."
                            )
                        }
                    }
                    bot.bank.remove(item)
                    bot.bank.refresh()
                }
                return true
            }
        }
        bot.pulseManager.run(toCounterPulseAll())
    }

    /**
     * Determines if a sale is significant enough to be considered big news.
     *
     * @param itemID The unique identifier of the item being sold.
     * @param amount The quantity of the item being sold.
     * @return `true` if the sale amount meets or exceeds the announcement limit, `false` otherwise.
     */
    fun saleIsBigNews(itemID: Int, amount: Int): Boolean {
        return ItemDefinition.forId(itemID).getAlchemyValue(true) * amount >= (GameWorld.settings?.ge_announcement_limit ?: 500)
    }


    /**
     * Teleports the bot to a given location if it is not currently teleblocked.
     *
     * @param loc The destination location to teleport to.
     * @return A boolean indicating whether the teleportation was successful.
     */
    fun teleport(loc: Location): Boolean {
        if (bot.isTeleBlocked) {
            return false
        }
        bot.lock()
        bot.visualize(ANIMATIONUP, GRAPHICSUP)
        bot.impactHandler.disabledTicks = 4
        val location = loc
        GameWorld.Pulser.submit(object : Pulse(4, bot) {
            /**
             *
             * This method pulses the bot's activity. The bot's state is reset and its location is updated.
             *
             * @return true if the pulse was successfully carried out
             */
            override fun pulse(): Boolean {
                bot.unlock()
                bot.properties.teleportLocation = location
                bot.pulseManager.clear()
                bot.animator.reset()
                return true
            }
        })
        return true
    }


    /**
     * Initiates a banking action for the specified item. The method creates
     * an instance of `BankingPulse` which executes the transfer of the item
     * from the inventory to the bank.
     *
     * @param item The identifier of the item to be banked.
     */
    fun bankItem(item: Int) {
        class BankingPulse : Pulse(20) {
            /**
             *
             * Carries out a pulse operation that transfers a specified item from the bot's inventory to the bank.
             *
             * @return true if the pulse operation was successful.
             */
            override fun pulse(): Boolean {
                val logs = bot.inventory.getAmount(item)
                bot.inventory.remove(Item(item, logs))
                bot.bank.add(Item(item, logs))
                return true
            }
        }
        bot.pulseManager.run(BankingPulse())
    }


    /**
     * Banks all items from the bot's inventory. Once completed, the specified callback function is invoked.
     *
     * @param onComplete A callback function to be executed after all items have been banked. It is optional and defaults to null.
     */
    fun bankAll(onComplete: (() -> Unit)? = null) {
        class BankingPulse : Pulse(20) {
            /**
             * Executes the pulse action by transferring all items from the bot's inventory to the bank.
             * For each item in the inventory, if the item is not null, it attempts to remove the item
             * from the inventory and add it to the bank.
             *
             * If a completion callback is specified, it is invoked after all items are transferred.
             *
             * @return true Always returns true, indicating the pulse action is complete.
             */
            override fun pulse(): Boolean {
                for (item in bot.inventory.toArray()) {
                    if (item != null) {
                        var itemAmount = bot.inventory.getAmount(item)

                        if (bot.inventory.remove(item)) {
                            bot.bank.add(item)
                        }
                    }
                }
                if (onComplete != null) {
                    onComplete.invoke()
                }
                return true
            }
        }
        bot.pulseManager.run(BankingPulse())
    }

    /**
     * Consumes the specified food item to restore hitpoints if certain conditions are met.
     *
     * @param foodId The identifier of the food item to be consumed.
     */
    fun eat(foodId: Int) {
        val foodItem = Item(foodId)
        if (bot.skills.getStaticLevel(Skills.HITPOINTS) * RandomFunction.random(0.5, 0.75) >= bot.skills.lifepoints && bot.inventory.containsItem(foodItem)) {
            bot.lock(3)
            //this.animate(new Animation(829));
            val food = bot.inventory.getItem(foodItem)
            var consumable: Consumable? = Consumables.getConsumableById(foodId)?.consumable
            if (consumable == null) {
                consumable = Food(intArrayOf(food.id), HealingEffect(1))
            }
            consumable.consume(food, bot)
            bot.properties.combatPulse.delayNextAttack(3)
        }
    }


    /**
     * Forces the bot to consume a specified food item from its inventory.
     *
     * @param foodId The unique identifier of the food item to be consumed.
     */
    fun forceEat(foodId: Int) {
        val foodItem = Item(foodId)
        if (bot.inventory.containsItem(foodItem)) {
            bot.lock(3)
            //this.animate(new Animation(829));
            val food = bot.inventory.getItem(foodItem)
            var consumable: Consumable? = Consumables.getConsumableById(foodId)?.consumable
            if (consumable == null) {
                consumable = Food(intArrayOf(foodId), HealingEffect(1))
            }
            consumable.consume(food, bot)
            bot.properties.combatPulse.delayNextAttack(3)
        }
    }

    /**
     * Initiates a buy offer on the Grand Exchange for a specified item and amount.
     *
     * @param bot The player bot that is initiating the buy offer.
     * @param itemID The ID of the item to be purchased.
     * @param amount The quantity of the item to be purchased.
     */
    fun buyFromGE(bot: Player, itemID: Int, amount: Int) {
        GlobalScope.launch {
            val offer = GrandExchangeOffer()
            offer.itemID = itemID
            offer.sell = false
            offer.offeredValue = checkPriceOverrides(itemID) ?: ItemDefinition.forId(itemID).value
            offer.amount = amount
            offer.player = bot
            //GrandExchange.dispatch(bot, offer)
            AIRepository.addOffer(bot, offer)
            var bought: Boolean = false
            val latch = CountDownLatch(1)
            bot.pulseManager.run(object : Pulse(5) {
                /**
                 * Checks if the offer has been fully bought and counts down the latch.
                 *
                 * @return Always returns true.
                 */
                override fun pulse(): Boolean {
                    bought = offer.completedAmount == offer.amount
                    latch.countDown()
                    return true
                }
            })
            latch.await()
            if (bought) {
                bot.bank.add(Item(offer.itemID, offer.completedAmount))
                bot.bank.refresh()
            }
        }
    }

    /**
     * Withdraws a specified amount of an item from the bank and adds it to the inventory.
     *
     * @param itemID The unique identifier of the item to be withdrawn.
     * @param amount The number of items to withdraw. If the amount exceeds the available quantity in the bank,
     *               the maximum available quantity will be withdrawn. If the inventory does not have enough space,
     *               only enough items to fill the space will be withdrawn.
     */
    fun withdraw(itemID: Int, amount: Int) {
        var item: Item? = null
        if (bot.bank.containsItem(Item(itemID, amount))) {
            item = Item(itemID, amount)
        } else {
            item = Item(itemID, bot.bank.getAmount(itemID))
        }
        if (item.amount == 0) return
        if (!bot.inventory.hasSpaceFor(item)) {
            item.amount = bot.inventory.getMaximumAdd(item)
        }
        bot.bank.remove(item)
        bot.inventory.add(item)
    }

    /**
     * Equips items and sets their stats for the character.
     *
     * @param items A list of items to be equipped and have their stats set.
     */
    fun equipAndSetStats(items: List<Item>?) {
        if (items == null) return
        for (item in items) {
            equipAndSetStats(item)
        }
    }

    /**
     * Equips the provided item to the bot and updates its stats accordingly.
     *
     * @param item The item to be equipped. The item must have defined 'equipment_slot' in its configurations.
     * If 'requirements' are specified in the item's configurations, the bot's skill levels are set to meet these requirements.
     */
    fun equipAndSetStats(item: Item) {
        val configs = item.definition.handlers
        val slot = configs["equipment_slot"] ?: return
        bot.equipment.add(
            item, slot as Int, false, false
        )
        val reqs = configs["requirements"]
        if (reqs != null) for (req in configs["requirements"] as HashMap<Int, Int>) bot.skills.setStaticLevel(
            req.key, req.value
        )
        bot.skills.updateCombatLevel()
    }

    /**
     * Loads the appearance and equipment of the bot from the provided JSON object.
     * It parses the appearance and equipment from the JSON and updates the bot's
     * equipment and appearance accordingly. Additionally, it sets all combat stats
     * to a function of the highest combat stat.
     *
     * @param json The JSON object containing the appearance and equipment data. If null,
     *             the method returns without making any changes.
     */
    fun loadAppearanceAndEquipment(json: JSONObject?) {
        if (json == null) return
        bot.equipment.clear()
        bot.appearance.parse(json["appearance"] as JSONObject)
        val equipment = json["equipment"] as JSONArray
        bot.equipment.parse(equipment)
        bot.appearance.sync()
        for (i in 0 until bot.equipment.capacity()) {
            val item = bot.equipment.get(i)
            if (item != null) {
                equipAndSetStats(item)
            }
        }
        /*
         * Set all combat stats to a function of the highest combat stat.
         * (otherwise you end up with lopsided stats)
         */
        val highestCombatSkill = bot.skills.getStaticLevel(bot.skills.highestCombatSkillId)
        for (i in 0 until 7) {
            bot.skills.setStaticLevel(i, max((highestCombatSkill * 0.75).toInt(), bot.skills.getStaticLevel(i)))
        }
        bot.skills.updateCombatLevel()
    }

    /**
     * Retrieves an instance of the BottingOverlay specific to the current bot.
     *
     * @return an instance of BottingOverlay configured with the current bot instance.
     */
    fun getOverlay(): BottingOverlay {
        return BottingOverlay(bot)
    }

    /**
     * Checks for price overrides based on the provided item ID.
     * If an override is found, returns the overridden price.
     *
     * @param id the ID of the item to check for price overrides
     * @return the overridden price if it exists, or null if no override is found
     */
    fun checkPriceOverrides(id: Int): Int? {
        return when (id) {
            else -> itemDefinition(id).getConfiguration(ItemConfigParser.GE_PRICE)
        }
    }

    /**
     * BottingOverlay class manages the player's interface overlay and updates various interface components.
     *
     * @property player The player instance for which this overlay is managed.
     */
    class BottingOverlay(val player: Player) {
        /**
         * Initializes the player's interface by opening an overlay and configuring the interface settings.
         *
         * This method performs the following actions:
         * - Opens an interface overlay with the component ID 195.
         * - Sends a packet to configure the interface with ID 195 at index 5 to be enabled.
         *
         * The purpose of this method is to set up the player's user interface with necessary components
         * and configurations for the gameplay.
         */
        fun init() {
            player.interfaceManager.openOverlay(Component(195))
            player.packetDispatch.sendInterfaceConfig(195, 5, true)
        }

        /**
         * Updates the player's title with the given text.
         *
         * @param title The new title to set for the player.
         */
        fun setTitle(title: String) {
            player.packetDispatch.sendString(colorize("%B$title"), 195, 7)
        }

        /**
         * Sets the task label with the specified label text.
         *
         * @param label The text to be used as the task label.
         */
        fun setTaskLabel(label: String) {
            player.packetDispatch.sendString(colorize("%B$label"), 195, 8)
        }

        /**
         * Sets the amount of a player's item.
         *
         * @param amount The amount to be set.
         */
        fun setAmount(amount: Int) {
            player.packetDispatch.sendString(colorize("%B$amount"), 195, 9)
        }
    }
}
