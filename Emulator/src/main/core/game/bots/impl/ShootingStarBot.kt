package core.game.bots.impl

import cfg.consts.Items
import content.dd.star.handlers.ShootingStarPlugin
import core.game.bots.GeneralBotCreator
import core.game.bots.Script
import core.game.interaction.IntType
import core.game.interaction.InteractionListeners
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.map.Location
import core.tools.RandomFunction

/**
 * Shooting star bot.
 */
class ShootingStarBot : Script() {
    private var state = State.FULL_IDLE
    private var timerCountdown = 0
    val star = ShootingStarPlugin.getStar()

    override fun tick() {
        bot!!.fullRestore()

        if (timerCountdown > 0) {
            --timerCountdown
            return
        }

        when (state) {
            State.FULL_IDLE -> {}

            State.TELEPORT_TO -> {
                scriptAPI!!.teleport(star.crash_locations[star.location]!!.transform(0, -1, 0))
                state = State.MINING
                timerCountdown = 15
            }

            State.MINING -> {
                InteractionListeners.run(star.starObject.id, IntType.SCENERY, "mine", bot!!, star.starObject)
            }

            State.TELEPORT_BACK -> {
                scriptAPI!!.teleport(spawnLoc)
                timerCountdown = 15
            }
        }
    }

    init {
        skills[Skills.ATTACK] == 41
        skills[Skills.RANGE] == RandomFunction.random(30, 99)
        skills[Skills.MINING] == 99
        skills[Skills.HITPOINTS] == 99
        skills[Skills.DEFENCE] == 99
        skills[Skills.SUMMONING] == 99
        skills[Skills.PRAYER] == 99
        inventory.add(Item(Items.RUNE_PICKAXE_1275))
    }

    override fun newInstance(): Script {
        return ShootingStarBot()
    }

    /**
     * Activate
     *
     * @param instant
     */
    fun activate(instant: Boolean) {
        state = State.TELEPORT_TO
        if (!instant) timerCountdown = RandomFunction.random(500)
    }

    /**
     * Sleep
     *
     */
    fun sleep() {
        state = State.TELEPORT_BACK
    }

    /**
     * Is mining
     *
     * @return
     */
    fun isMining(): Boolean {
        return state == State.MINING
    }

    /**
     * Is idle
     *
     * @return
     */
    fun isIdle(): Boolean {
        return state == State.FULL_IDLE
    }

    /**
     * State
     *
     * @constructor State
     */
    internal enum class State {
        /**
         * Full Idle
         *
         * @constructor Full Idle
         */
        FULL_IDLE,

        /**
         * Teleport To
         *
         * @constructor Teleport To
         */
        TELEPORT_TO,

        /**
         * Mining
         *
         * @constructor Mining
         */
        MINING,

        /**
         * Teleport Back
         *
         * @constructor Teleport Back
         */
        TELEPORT_BACK
    }

    companion object {
        val spawnLoc = Location.create(2230, 3339, 0)
        fun new(): ShootingStarBot {
            val script = ShootingStarBot()
            GeneralBotCreator(script, spawnLoc)
            return script
        }
    }
}
