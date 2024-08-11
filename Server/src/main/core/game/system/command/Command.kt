package core.game.system.command

import core.ServerConstants
import core.game.node.entity.player.Player
import core.game.world.GameWorld

/**
 * Command class represents a game command.
 *
 * @property name The name of the command.
 * @property privilege The privilege level required to execute the command.
 * @property usage The usage information of the command.
 * @property description The description of the command.
 * @property handle The function that handles the command execution.
 * @constructor Creates a new Command instance.
 */
class Command(
    val name: String,
    val privilege: Privilege,
    val usage: String = "UNDOCUMENTED",
    val description: String = "UNDOCUMENTED",
    val handle: (Player, Array<String>) -> Unit
) {
    /**
     * Attempts to handle the command.
     *
     * @param player The player executing the command.
     * @param args The arguments passed with the command.
     */
    fun attemptHandling(player: Player, args: Array<String>?) {
        args ?: return // Check if arguments are null and return if so.
        if (player.rights.ordinal >= privilege.ordinal || GameWorld.settings?.isDevMode == true || ServerConstants.I_AM_A_CHEATER) {
            handle(player, args) // Execute the command handling function.
        }
    }
}

object CommandMapping {
    private val mapping = hashMapOf<String, Command>()

    /**
     * Retrieves a command by name.
     *
     * @param name The name of the command to retrieve.
     * @return The Command instance associated with the given name.
     */
    fun get(name: String): Command? {
        return mapping[name]
    }

    /**
     * Registers a new command.
     *
     * @param command The Command instance to register.
     */
    fun register(command: Command) {
        mapping[command.name] = command
    }

    /**
     * Retrieves all registered commands.
     *
     * @return An array of all registered Command instances.
     */
    fun getCommands(): Array<Command> {
        return mapping.values.toTypedArray()
    }

    /**
     * Retrieves the names of all registered commands.
     *
     * @return An array of names of all registered commands.
     */
    fun getNames(): Array<String> {
        return mapping.keys.toTypedArray()
    }

    /**
     * Calculates the page indices for displaying commands based on user rights.
     *
     * @param rights The privilege level of the user.
     * @return An array of page indices for command display.
     */
    fun getPageIndices(rights: Int): IntArray {
        val list = ArrayList<Int>()
        list.add(0)

        var lineCounter = 0
        for ((index, command) in getCommands().filter { it.privilege.ordinal <= rights }.withIndex()) {

            lineCounter += 2
            if (command.usage.isNotEmpty()) lineCounter++
            if (command.description.isNotEmpty()) lineCounter++

            if (lineCounter > 306) {
                list.add(index)
                lineCounter = 0
            }
        }

        return list.toIntArray()
    }
}
