package content.global.random.event.surpriseexam

import core.api.clearLogoutListener
import core.api.consts.Components
import core.api.consts.Items
import core.api.removeAttributes
import core.api.setAttribute
import core.game.node.entity.impl.PulseType
import core.game.node.entity.player.Player
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.game.system.task.Pulse

/**
 * Surprise exam utils.
 */
object SurpriseExamUtils {

    // Key location for saving the original location
    val SE_KEY_LOC = "/save:original-loc"

    // Key index for the surprise exam
    val SE_KEY_INDEX = "surpriseexam:index"

    // Key for surprise exam logout
    val SE_LOGOUT_KEY = "surprise_exam"

    // Key for surprise exam door
    val SE_DOOR_KEY = "surpriseexam:door"

    // Child components for the interface pattern
    val INTER_PATTERN_CHILDS = intArrayOf(6, 7, 8)

    // Child components for the interface options
    val INTER_OPTION_CHILDS = intArrayOf(10, 11, 12, 13)

    // Array of surprise exam doors
    val SE_DOORS = intArrayOf(2188, 2189, 2192, 2193)

    // Interface for the surprise exam
    val INTERFACE = Components.PATTERN_NEXT_103

    // Key for storing the correct option
    val SE_KEY_CORRECT = "surpriseexam:correct"

    // Array of sets for the surprise exam
    val sets = arrayOf(
        intArrayOf(Items.GARDENING_TROWEL_5325, Items.SECATEURS_5329, Items.SEED_DIBBER_5343, Items.RAKE_5341),
        intArrayOf(Items.SALMON_329, Items.SHARK_385, Items.TROUT_333, Items.SHRIMPS_315),
        intArrayOf(Items.BRONZE_SWORD_1277, Items.WOODEN_SHIELD_1171, Items.BRONZE_MED_HELM_1139, Items.ADAMANT_BATTLEAXE_1371),
        intArrayOf(Items.FLY_FISHING_ROD_309, Items.BARBARIAN_ROD_11323, Items.SMALL_FISHING_NET_303, Items.HARPOON_311)
    )

    /**
     * Cleanup.
     *
     * @param player The player to clean up.
     */
    fun cleanup(player: Player) {
        // Reset the player's teleport location
        player.properties.teleportLocation = player.getAttribute(SE_KEY_LOC, null)

        // Clear the logout listener for the surprise exam
        clearLogoutListener(player, SE_LOGOUT_KEY)

        // Remove the surprise exam attributes from the player
        removeAttributes(player, SE_KEY_LOC, SE_KEY_INDEX, SE_KEY_CORRECT)

        // Run a pulse to give the player a reward
        player.pulseManager.run(object : Pulse(2) {
            override fun pulse(): Boolean {
                val reward = Item(Items.BOOK_OF_KNOWLEDGE_11640)
                if (!player.inventory.add(reward)) {
                    GroundItemManager.create(reward, player)
                }
                return true
            }
        }, PulseType.CUSTOM_1)
    }

    /**
     * Generate interface.
     *
     * @param player The player to generate the interface for.
     */
    fun generateInterface(player: Player) {
        // Get a random set from the array of sets
        val set = sets.random()

        // Get the index of the selected set
        val setIndex = sets.indexOf(set)

        // Create a temporary list of the set items and shuffle it
        val tempList = set.toList().shuffled().toMutableList()

        // Get a random correct option from the temporary list
        val correctOpt = tempList.random()

        // Get the index of the correct option
        val optIndex = tempList.indexOf(correctOpt)

        // Remove the correct option from the temporary list
        tempList.remove(correctOpt)

        // Send the items from the temporary list to the interface pattern child components
        for (i in INTER_PATTERN_CHILDS.indices) {
            player.packetDispatch.sendItemOnInterface(tempList[i], 1, INTERFACE, INTER_PATTERN_CHILDS[i])
        }

        // Send the correct option and false options to the interface option child components
        for (i in INTER_OPTION_CHILDS.indices) {
            if (i == optIndex) {
                player.packetDispatch.sendItemOnInterface(correctOpt, 1, INTERFACE, INTER_OPTION_CHILDS[i])
            } else {
                player.packetDispatch.sendItemOnInterface(getFalseSet(setIndex)[i], 1, INTERFACE, INTER_OPTION_CHILDS[i])
            }
        }

        // Set the attribute for the selected option index
        setAttribute(player, SE_KEY_INDEX, optIndex)
    }

    /**
     * Get false set.
     *
     * @param trueSetIndex The index of the true set.
     * @return The false set.
     */
    fun getFalseSet(trueSetIndex: Int): IntArray {
        // Create a temporary list of the sets and remove the true set
        val tempSets = sets.toMutableList()
        tempSets.removeAt(trueSetIndex)

        // Return a random set from the temporary list
        return tempSets.random()
    }

    /**
     * Pick random door.
     *
     * @param player The player to pick a random door for.
     */
    fun pickRandomDoor(player: Player) {
        // Set the attribute for the random door
        setAttribute(player, SE_DOOR_KEY, SE_DOORS.random())
    }
}
