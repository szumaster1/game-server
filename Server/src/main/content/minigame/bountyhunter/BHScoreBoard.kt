package content.minigame.bountyhunter

import core.cache.misc.buffer.ByteBufferUtils
import core.game.component.Component
import core.game.node.entity.player.Player
import core.tools.StringUtils
import java.nio.ByteBuffer

/**
 * The score board.
 * @author Emperor
 */
class BHScoreBoard {
    // Array to hold player names, initialized to a size defined by SIZE
    private val names = arrayOfNulls<String>(SIZE)
    // Array to hold player scores, initialized to a size defined by SIZE
    private val scores = IntArray(SIZE)

    init {
        // Initialize all names in the scoreboard to "Nobody yet"
        for (i in 0 until SIZE) {
            names[i] = "Nobody yet"
        }
    }

    /**
     * Opens the score board.
     *
     * @param player The player.
     */
    fun open(player: Player) {
        // Default component ID for the scoreboard
        var component = 654
        // If the scoreboard is for rogues, use a different component ID
        if (this == ROGUES) {
            component = 655
        }
        // Open the scoreboard interface for the player
        player.interfaceManager.open(Component(component))
        // Send player names and scores to the scoreboard interface
        for (i in 0 until SIZE) {
            player.packetDispatch.sendString(StringUtils.formatDisplayName(names[i]), component, 15 + i)
            player.packetDispatch.sendString(Integer.toString(scores[i]), component, 25 + i)
        }
    }

    /**
     * Checks if the ratings of the player is good enough for the score board.
     *
     * @param player The player.
     */
    fun check(player: Player) {
        // Get the player's score based on their activity data
        var score = player.getSavedData().activityData.bountyHunterRate
        // If the scoreboard is for rogues, get the rogue score instead
        if (this == ROGUES) {
            score = player.getSavedData().activityData.bountyRogueRate
        }
        // Check if the player's score qualifies for the scoreboard
        for (i in 0 until SIZE) {
            if (score > scores[i]) {
                // Insert the player into the scoreboard if their score is higher
                insert(player, score, i)
                // Update the scoreboard after insertion
                update()
                break
            }
        }
    }

    /**
     * Inserts the player in the score board.
     *
     * @param player The player.
     * @param score The score.
     * @param index The board index.
     */
    private fun insert(player: Player, score: Int, index: Int) {
        // If the player is already on the scoreboard, update their score
        if (names[index] == player.name) {
            scores[index] = score
            return
        }
        // Shift scores and names down to make space for the new entry
        var i: Int = SIZE - 2
        while (i >= index) {
            var name = names[i]
            // If the name matches the player's name, skip it
            if (name == player.name) {
                name = names[--i]
            }
            // Move scores and names down one position
            scores[i + 1] = scores[i]
            names[i + 1] = name
            i--
        }
        // Insert the player's name and score at the specified index
        names[index] = player.name
        scores[index] = score
    }

    companion object {
        // Constant defining the size of the scoreboard
        private const val SIZE = 10
        // Singleton instances for hunters and rogues
        private val HUNTERS = BHScoreBoard()
        private val ROGUES = BHScoreBoard()

        // Initialization function for the scoreboard
        fun init() {}

        // Updates the scoreboard data
        fun update() {
            // Allocate a ByteBuffer for storing scoreboard data
            val buffer = ByteBuffer.allocate(500)
            // Store hunters' scores and names in the buffer
            for (i in 0 until SIZE) {
                buffer.putInt(HUNTERS.scores.get(i))
                ByteBufferUtils.putString(HUNTERS.names.get(i), buffer)
            }
            // Store rogues' scores and names in the buffer
            for (i in 0 until SIZE) {
                buffer.putInt(ROGUES.scores.get(i))
                ByteBufferUtils.putString(ROGUES.names.get(i), buffer)
            }
            // Prepare the buffer for reading
            buffer.flip()
            //AriosStore.setArchive("bh_scores", buffer);
        }

        /**
         * Gets the rogues.
         *
         * @return The rogues.
         */
        val rogues: BHScoreBoard
            get() = ROGUES

        /**
         * Gets the hunters.
         *
         * @return The hunters.
         */
        val hunters: BHScoreBoard
            get() = HUNTERS
    }
}