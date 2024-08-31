package core.game.bots

import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Script.
 */
abstract class Script {
    var scriptAPI: ScriptAPI? = null
    var inventory: ArrayList<Item> = ArrayList(20)
    var equipment: ArrayList<Item> = ArrayList(20)
    var skills: Map<Int, Int> = HashMap()
    var quests: ArrayList<String> = ArrayList(20)


    var bot: Player? = null

    var running: Boolean = true
    var endDialogue: Boolean = true

    /**
     * Init
     *
     * @param isPlayer
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

    override fun toString(): String {
        return bot!!.name + " is a " + this.javaClass.simpleName + " at location " + bot!!.location.toString() + " Current pulse: " + bot!!.pulseManager.current
    }

    /**
     * Tick
     *
     */
    abstract fun tick()

    /**
     * Set level
     *
     * @param skill
     * @param level
     */
    fun setLevel(skill: Int, level: Int) {
        bot!!.getSkills().setLevel(skill, level)
        bot!!.getSkills().setStaticLevel(skill, level)
        bot!!.getSkills().updateCombatLevel()
        bot!!.appearance.sync()
    }

    /**
     * New instance
     *
     * @return
     */// This does not get called and all implementations should be removed
    abstract fun newInstance(): Script?
}
