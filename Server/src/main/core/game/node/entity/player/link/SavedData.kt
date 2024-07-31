package core.game.node.entity.player.link

import core.game.node.entity.player.Player
import java.nio.ByteBuffer

/**
 * Represents a managing class of saved data related to in-game interactions,
 * such as questing data, npc talking data, etc.
 */
class SavedData(val player: Player) {

    @JvmField
    val globalData: GlobalData = GlobalData()

    @JvmField
    val activityData: ActivityData = ActivityData()

    @JvmField
    val questData: QuestData = QuestData()

    companion object {
        /**
         * Method used to save an activity var that isn't valued at default.
         *
         * @param buffer the buffer.
         * @param var    the variable to save.
         * @param index  the index
         */
        fun save(buffer: ByteBuffer, `var`: Any?, index: Int) {
            if (if (`var` is Int) `var` != 0 else if (`var` is Double) `var` != 0.0 else if (`var` is Byte) `var`.toInt() != 0 else if (`var` is Short) `var`.toInt() != 0 else if (`var` is Long) `var` != 0L else if (`var` is Boolean) `var` != false else `var` != null) {
                buffer.put(index.toByte())
                if (`var` is Int) {
                    buffer.putInt(`var`)
                } else if (`var` is Byte) {
                    buffer.put(`var`)
                } else if (`var` is Short) {
                    buffer.putShort(`var`)
                } else if (`var` is Long) {
                    buffer.putLong(`var`)
                } else if (`var` is Boolean) {
                    buffer.put(1.toByte())
                } else if (`var` is Double) {
                    buffer.putDouble(`var`)
                } else if (`var` is DoubleArray) {
                    val doubleArray = `var`
                    for (i in doubleArray.indices) {
                        buffer.putDouble(doubleArray[i])
                    }
                } else if (`var` is BooleanArray) {
                    val booleanArray = `var`
                    for (i in booleanArray.indices) {
                        buffer.put((if (booleanArray[i]) 1 else 0).toByte())
                    }
                } else if (`var` is IntArray) {
                    val intArray = `var`
                    for (i in intArray.indices) {
                        buffer.putInt(intArray[i])
                    }
                }
            }
        }

        /**
         * Gets the boolean value.
         *
         * @param value the value.
         * @return the value.
         */
        fun getBoolean(value: Byte): Boolean {
            return value.toInt() == 1
        }

        /**
         * Gets the boolean value.
         *
         * @param buffer the buffer.
         * @return the value.
         */
        fun getBoolean(buffer: ByteBuffer): Boolean {
            return getBoolean(buffer.get())
        }
    }
}
