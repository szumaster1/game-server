package core.api

import core.game.node.item.Item
import java.util.*

/**
 * To int array
 *
 * @return
 */
fun IntProgression.toIntArray(): IntArray {
    val result = IntArray((last - first) / step + 1)
    forEachIndexed { index, i -> result[index] = i }
    return result
}

/**
 * As item
 *
 * @return
 */
fun Int.asItem(): Item {
    return Item(this)
}

/**
 * To int array
 *
 * @return
 */
fun Collection<IntArray>.toIntArray(): IntArray {
    val list = ArrayList<Int>()
    this.forEach { arr -> arr.forEach { list.add(it) } }
    return list.toIntArray()
}

/**
 * Is last
 *
 * @param T
 * @param element
 * @return
 */
inline fun <reified T> Collection<T>.isLast(element: T): Boolean {
    return this.indexOf(element) == this.size - 1
}

/**
 * Get next
 *
 * @param T
 * @param element
 * @return
 */
inline fun <reified T> Collection<T>.getNext(element: T): T {
    val idx = this.indexOf(element)
    return if (idx < this.size - 1) this.elementAt(idx + 1)
    else element
}

/**
 * Is next last
 *
 * @param T
 * @param element
 * @return
 */
inline fun <reified T> Collection<T>.isNextLast(element: T): Boolean {
    return this.isLast(this.getNext(element))
}

/**
 * Is last
 *
 * @param element
 * @return
 */
fun IntArray.isLast(element: Int): Boolean {
    return this.indexOf(element) == this.size - 1
}

/**
 * Get next
 *
 * @param element
 * @return
 */
fun IntArray.getNext(element: Int): Int {
    val idx = this.indexOf(element)
    return if (idx < this.size - 1) this.elementAt(idx + 1)
    else element
}

/**
 * Is next last
 *
 * @param element
 * @return
 */
fun IntArray.isNextLast(element: Int): Boolean {
    return this.isLast(this.getNext(element))
}

/**
 * Try pop
 *
 * @param T
 * @param default
 * @return
 */
fun <T> LinkedList<T>.tryPop(default: T?): T? {
    this.peek() ?: return default
    return this.pop()
}

/**
 * Parse enum entry
 *
 * @param E
 * @param name
 * @return
 */
inline fun <reified E : Enum<E>> parseEnumEntry(name: String): E? {
    return try {
        enumValueOf<E>(name)
    } catch (e: Exception) {
        null
    }
}