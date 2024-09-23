package core.game.system.timer.impl

import content.global.ame.RandomEventNPC
import content.global.ame.RandomEvents
import core.api.*
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.node.entity.player.info.Rights
import core.game.node.item.Item
import core.game.system.command.Privilege
import core.game.system.timer.PersistTimer
import core.game.world.map.zone.ZoneRestriction
import core.game.world.repository.Repository
import core.tools.RandomFunction
import core.tools.colorize
import org.json.simple.JSONObject

/**
 * Represents the Anti-Macro timer.
 */
class AntiMacro : PersistTimer(0, "antimacro", isAuto = true), Commands {
    var paused = false
    var nextRandom: RandomEvents? = null

    /**
     * Executes the anti-macro logic for the given entity.
     *
     * @param entity The entity to run the anti-macro logic on.
     * @return True if the execution was successful, false otherwise.
     */
    override fun run(entity: Entity): Boolean {
        if (entity !is Player) return false

        setNextExecution()

        if (!canSpawn(entity)) return true
        val eventSelection = rollEventPool(entity)
        val eventNpc = eventSelection.npc.create(entity, eventSelection.loot, eventSelection.type)
        if (eventNpc.spawnLocation == null) {
            entity.debug("[AntiMacro] Attempted to spawn random, but spawnLoc was null.")
            return true
        }

        eventNpc.init()
        setAttribute(entity, EVENT_NPC, eventNpc)
        entity.debug("[AntiMacro] Fired ${eventSelection.name}.")
        return true
    }

    /**
     * Registers the entity for the anti-macro timer.
     *
     * @param entity The entity to register.
     */
    override fun onRegister(entity: Entity) {
        if (entity !is Player || entity.isArtificial)
            entity.timers.removeTimer(this)
        if (entity is Player && entity.rights == Rights.ADMINISTRATOR)
            paused = true

        if (runInterval == 0)
            setNextExecution()
    }

    /**
     * Saves the state of the anti-macro timer.
     *
     * @param root The JSON object to save the state into.
     * @param entity The entity whose state is being saved.
     */
    override fun save(root: JSONObject, entity: Entity) {
        root["ticksRemaining"] = (nextExecution - getWorldTicks()).toString()
    }

    /**
     * Parses the state of the anti-macro timer from a JSON object.
     *
     * @param root The JSON object containing the state.
     * @param entity The entity whose state is being parsed.
     */
    override fun parse(root: JSONObject, entity: Entity) {
        runInterval = (root["ticksRemaining"]?.toString()?.toIntOrNull() ?: 0)
        nextExecution = getWorldTicks() + runInterval
    }

    /**
     * Checks if a random event can spawn for the given entity.
     *
     * @param entity The entity to check.
     * @return True if a random event can spawn, false otherwise.
     */
    private fun canSpawn(entity: Entity) : Boolean {
        if (entity.zoneMonitor.isRestricted(ZoneRestriction.RANDOM_EVENTS))
            return false

        val current = getAttribute<RandomEventNPC?>(entity, EVENT_NPC, null)
        if (current != null)
            return false

        if (paused)
            return false

        return true
    }

    /**
     * Sets the next execution time for the anti-macro timer.
     */
    private fun setNextExecution() {
        runInterval = RandomFunction.random(MIN_DELAY_TICKS, MAX_DELAY_TICKS + 1)
        nextExecution = getWorldTicks() + runInterval
    }

    /**
     * Rolls for a random event based on the entity's skills and other factors.
     *
     * @param entity The entity to roll the event for.
     * @return The selected random event.
     */
    private fun rollEventPool(entity: Entity) : RandomEvents {
        if (nextRandom != null) {
            val result = nextRandom!!
            nextRandom = null
            return result
        }

        val skillBasedRandom = RandomEvents.getSkillBasedRandomEvent(entity.skills.lastTrainedSkill)
        val normalRandom = RandomEvents.getNonSkillRandom()
        val roll = RandomFunction.random(100)

        if (roll >= 65 && skillBasedRandom != null && getWorldTicks() - entity.skills.lastXpGain < 250)
            return skillBasedRandom
        return normalRandom
    }

    /**
     * Defines commands related to the anti-macro system.
     */
    override fun defineCommands() {
        define("revent", Privilege.ADMIN, "::revent [-p] <lt>player name<gt> [-e <lt>event name<gt>]", "Spawns a random event for the target player.<br>Optional -e parameter to pass a specific event.") {player, args ->
            if (args.size == 1) {
                val possible = RandomEvents.values()
                for (event in possible) {
                    notify(player, event.name.lowercase())
                }
                return@define
            }

            val arg = parseCommandArgs(args.joinToString(" "))
            val target = Repository.getPlayerByName(arg.targetPlayer)
            if (target == null)
                reject(player, "Unable to find user ${arg.targetPlayer}.")
            if (target!!.rights == Rights.ADMINISTRATOR) {
                unpause(target)
                sendMessage(target, colorize("%RAntiMacro timer unpaused until next login."))
            }

            forceEvent(target!!, arg.targetEvent)
        }
    }

    /**
     * Command args
     *
     * @param targetPlayer The name of the player that the command is targeting.
     * @param targetEvent  The event associated with the command, which can be a random event or null.
     * @constructor Command args
     */
    data class CommandArgs (val targetPlayer: String, val targetEvent: RandomEvents?)

    companion object {
        const val EVENT_NPC = "re-npc"
        const val MIN_DELAY_TICKS = 3000
        const val MAX_DELAY_TICKS = 9000

        /**
         * Terminates the event NPC for the given player.
         *
         * @param player The player whose event NPC is to be terminated.
         */
        fun terminateEventNpc (player: Player) {
            getEventNpc(player)?.terminate()
        }

        /**
         * Rolls for loot from the event NPC for the given player.
         *
         * @param player The player to roll loot for.
         * @return A list of items obtained from the event NPC.
         */
        fun rollEventLoot (player: Player) : ArrayList<Item> {
            return getEventNpc(player)?.loot?.roll(player) ?: ArrayList()
        }

        /**
         * Retrieves the event NPC for the given player.
         *
         * @param player The player to retrieve the event NPC for.
         * @return The event NPC associated with the player, or null if none exists.
         */
        fun getEventNpc (player: Player) : RandomEventNPC? {
            return getAttribute<RandomEventNPC?>(player, EVENT_NPC, null)
        }

        /**
         * Pauses the anti-macro timer for the given player.
         *
         * @param player The player whose timer is to be paused.
         */
        fun pause (player: Player) {
            val timer = getTimer<AntiMacro>(player) ?: return
            timer.paused = true
        }

        /**
         * Unpauses the anti-macro timer for the given player.
         *
         * @param player The player whose timer is to be unpaused.
         */
        fun unpause (player: Player) {
            val timer = getTimer<AntiMacro>(player) ?: return
            timer.paused = false
        }

        /**
         * Forces a random event for the given player.
         *
         * @param player The player to force the event for.
         * @param event The random event to force, or null for a random selection.
         */
        fun forceEvent (player: Player, event: RandomEvents? = null) {
            val timer = getTimer<AntiMacro>(player) ?: return
            timer.nextExecution = getWorldTicks()
            timer.nextRandom = event
        }

        /**
         * Parses command arguments for the anti-macro commands.
         *
         * @param args The command arguments as a string.
         * @param commandName The name of the command being parsed.
         * @return The parsed command arguments.
         */
        fun parseCommandArgs (args: String, commandName: String = "revent") : CommandArgs {
            val tokens = args.split(" ")
            val modeTokens = arrayOf("-p", "-e")

            var userString = ""
            var eventString = ""
            var lastMode = "-p"

            for (token in tokens) {
                when (token) {
                    commandName -> continue
                    in modeTokens -> lastMode = token
                    else -> when (lastMode) {
                        "-p" -> userString += "$token "
                        "-e" -> eventString += "$token "
                    }
                }
            }

            val username = userString.trim().lowercase().replace(" ", "_")
            val eventName = eventString.trim().uppercase().replace(" ", "_")

            var event: RandomEvents? = null

            try { event = RandomEvents.valueOf(eventName) } catch (_: Exception) {}

            return CommandArgs(username, event)
        }
    }
}