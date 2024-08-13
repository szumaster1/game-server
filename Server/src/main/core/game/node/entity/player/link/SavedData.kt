package core.game.node.entity.player.link

import core.game.node.entity.player.Player
import java.nio.ByteBuffer

/**
 * Class representing saved data for a player.
 *
 * @param player The player associated with this saved data.
 * @constructor Represents the saved data with the specified player.
 */
class SavedData(val player: Player) {

    @JvmField
    // Global data associated with the player.
    val globalData: GlobalData = GlobalData()

    @JvmField
    // Activity data related to the player's activities.
    val activityData: ActivityData = ActivityData()

    @JvmField
    // Quest data pertaining to the player's quests.
    val questData: QuestData = QuestData()

    companion object {
        /*
         * Method used to save an activity variable that isn't valued at default.
         *
         * @param buffer The ByteBuffer to save the data into.
         * @param var The variable to be saved, which can be of various types.
         * @param index The index in the buffer where the data will be saved.
         */
        fun save(buffer: ByteBuffer, `var`: Any?, index: Int) {
            // Check if the variable is not equal to its default value.
            if (if (`var` is Int) `var` != 0 else if (`var` is Double) `var` != 0.0 else if (`var` is Byte) `var`.toInt() != 0 else if (`var` is Short) `var`.toInt() != 0 else if (`var` is Long) `var` != 0L else if (`var` is Boolean) `var` != false else `var` != null) {
                // Store the index in the buffer.
                buffer.put(index.toByte())
                // Save the variable based on its type.
                if (`var` is Int) {
                    buffer.putInt(`var`) // Save an integer.
                } else if (`var` is Byte) {
                    buffer.put(`var`) // Save a byte.
                } else if (`var` is Short) {
                    buffer.putShort(`var`) // Save a short.
                } else if (`var` is Long) {
                    buffer.putLong(`var`) // Save a long.
                } else if (`var` is Boolean) {
                    buffer.put(1.toByte()) // Save a boolean as 1.
                } else if (`var` is Double) {
                    buffer.putDouble(`var`) // Save a double.
                } else if (`var` is DoubleArray) {
                    val doubleArray = `var`
                    // Iterate through the double array and save each element.
                    for (i in doubleArray.indices) {
                        buffer.putDouble(doubleArray[i]) // Save each double in the array.
                    }
                } else if (`var` is BooleanArray) {
                    val booleanArray = `var`
                    // Iterate through the boolean array and save each element as 1 or 0.
                    for (i in booleanArray.indices) {
                        buffer.put((if (booleanArray[i]) 1 else 0).toByte()) // Save each boolean as 1 or 0.
                    }
                } else if (`var` is IntArray) {
                    val intArray = `var`
                    // Iterate through the integer array and save each element.
                    for (i in intArray.indices) {
                        buffer.putInt(intArray[i]) // Save each integer in the array.
                    }
                }
            }
        }

        // Converts a byte value to a boolean.
        fun getBoolean(value: Byte): Boolean {
            return value.toInt() == 1 // Return true if the byte is 1, otherwise false.
        }

        // Retrieves a boolean value from the ByteBuffer.
        fun getBoolean(buffer: ByteBuffer): Boolean {
            return getBoolean(buffer.get()) // Get the byte from the buffer and convert it to boolean.
        }
    }
}