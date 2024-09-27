package core.game.bots

import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Abstract base class representing a script that can be executed in a game environment.
 */
abstract class Script {
    /**
     * A nullable variable representing an instance of the ScriptAPI class.
     *
     * This variable may hold a reference to an object that provides functions and properties
     * required to interact with and manipulate scripts within the application or system.
     *
     * The initial value is set to null, indicating that the ScriptAPI instance is not created or assigned yet.
     * When assigned, it allows the invoking context to access script-related functionalities.
     */
    var scriptAPI: ScriptAPI? = null
    /**
     * Represents a collection of Item objects with an initial capacity of 20.
     *
     * This variable is used to maintain a list of items currently held in the inventory.
     * The inventory can dynamically grow beyond its initial capacity if needed.
     *
     * Inventory operations such as adding, removing, or accessing items can be performed on this list.
     */
    var inventory: ArrayList<Item> = ArrayList(20)
    /**
     * A list representing the equipment inventory.
     *
     * The list is capable of holding up to 20 `Item` objects initially.
     * This can be used to store various items of equipment for a character
     * or entity in a game or other application.
     */
    var equipment: ArrayList<Item> = ArrayList(20)
    /**
     * A map representing the skills of individuals, where the key is the individual's ID and the value is the skill level.
     *
     * - The key (`Int`) key.
     * - The value (`Int`) skill level of the individual.
     */
    var skills: Map<Int, Int> = HashMap()
    /**
     * Holds a list of quest names.
     * The list can contain up to 20 elements.
     */
    var quests: ArrayList<String> = ArrayList(20)


    /**
     * Holds a reference to a Player object to be controlled or manipulated by the script.
     * This variable is used to perform various actions such as setting levels, updating quests, and managing equipment.
     */
    var bot: Player? = null

    /**
     * Indicates whether the system or process is actively running.
     *
     * This variable is used to control the active state of an ongoing operation,
     * loop, or service. Setting it to `true` signifies that the operation should
     * continue running, while setting it to `false` signifies that the operation
     * should halt.
     */
    var running: Boolean = true
    /**
     * A flag indicating whether the dialogue should end.
     *
     * When set to `true`, the dialogue sequence will terminate.
     * When set to `false`, the dialogue will continue.
     */
    var endDialogue: Boolean = true

    /**
     * Initializes the bot and sets up the script API. Configures skills, quests, equipment,
     * and inventory based on whether the entity is a player or not.
     *
     * @param isPlayer A boolean flag indicating if the initialization is for a player.
     *                 If `true`, only the bot and script API are initialized.
     *                 If `false`, skills, quests, equipment, and inventory are also set up.
     */
    fun init(isPlayer: Boolean) {
        //bot.init();
        scriptAPI = ScriptAPI(bot!!)

        if (!isPlayer) {
            // Skills and quests need to be set before equipment in case equipment has level or quest requirements
            for ((key, value) in skills) {
                setLevel(key, value)
            }
            for (quest in quests) {
                bot!!.getQuestRepository().setStage(bot!!.getQuestRepository().getQuest(quest), 100)
            }
            for (i in equipment) {
                bot!!.equipment.add(i, true, false)
            }
            bot!!.inventory.clear()
            for (i in inventory) {
                bot!!.inventory.add(i)
            }
        }
    }

    /**
     * Returns a string representation of the object, which includes the bot's name, class type,
     * location, and current pulse.
     *
     * @return A string describing the bot's name, class, location, and current pulse.
     */
    override fun toString(): String {
        return bot!!.name + " is a " + this.javaClass.simpleName + " at location " + bot!!.location.toString() + " Current pulse: " + bot!!.pulseManager.current
    }

    abstract fun tick()

    /**
     * Sets the level of a particular skill and updates the combat level and appearance.
     *
     * @param skill The skill identifier for which the level needs to be set.
     * @param level The new level to be assigned to the specified skill.
     */
    fun setLevel(skill: Int, level: Int) {
        bot!!.getSkills().setLevel(skill, level)
        bot!!.getSkills().setStaticLevel(skill, level)
        bot!!.getSkills().updateCombatLevel()
        bot!!.appearance.sync()
    }

    /**
     * Creates a new instance of the Script.
     *
     * @return A new instance of the [Script] or `null` if creation failed.
     */
    abstract fun newInstance(): Script?
}
