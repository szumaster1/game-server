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
    private val names = arrayOfNulls<String>(SIZE)
    private val scores = IntArray(SIZE)

    init {
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
        var component = 654
        if (this == ROGUES) {
            component = 655
        }
        player.interfaceManager.open(Component(component))
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
        var score = player.getSavedData().activityData.bountyHunterRate
        if (this == ROGUES) {
            score = player.getSavedData().activityData.bountyRogueRate
        }
        for (i in 0 until SIZE) {
            if (score > scores[i]) {
                insert(player, score, i)
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
        if (names[index] == player.name) {
            scores[index] = score
            return
        }
        var i: Int = SIZE - 2
        while (i >= index) {
            var name = names[i]
            if (name == player.name) {
                name = names[--i]
            }
            scores[i + 1] = scores[i]
            names[i + 1] = name
            i--
        }
        names[index] = player.name
        scores[index] = score
    }

    companion object {
        private const val SIZE = 10
        private val HUNTERS = BHScoreBoard()
        private val ROGUES = BHScoreBoard()

        fun init() {}

        fun update() {
            val buffer = ByteBuffer.allocate(500)
            for (i in 0 until SIZE) {
                buffer.putInt(HUNTERS.scores.get(i))
                ByteBufferUtils.putString(HUNTERS.names.get(i), buffer)
            }
            for (i in 0 until SIZE) {
                buffer.putInt(ROGUES.scores.get(i))
                ByteBufferUtils.putString(ROGUES.names.get(i), buffer)
            }
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
