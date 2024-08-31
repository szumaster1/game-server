package core.api

import core.game.node.item.Item
import java.util.*

/**
 * Extension function to convert IntProgression to IntArray.
 *
 * @return IntArray.
 */
fun IntProgression.toIntArray(): IntArray {
    val result = IntArray((last - first) / step + 1)
    forEachIndexed { index, i -> result[index] = i }
    return result
}

/**
 * Extension function to convert Int to Item.
 *
 * @return Item.
 */
fun Int.asItem(): Item {
    return Item(this)
}

/**
 * Extension function to convert Collection<IntArray> to IntArray.
 *
 * @return IntArray.
 */
fun Collection<IntArray>.toIntArray(): IntArray {
    val list = ArrayList<Int>()
    this.forEach { arr -> arr.forEach { list.add(it) } }
    return list.toIntArray()
}

/**
 * Inline function to check if an element is the last one in a Collection.
 *
 * @param T element.
 * @return Boolean.
 */
inline fun <reified T> Collection<T>.isLast(element: T): Boolean {
    return this.indexOf(element) == this.size - 1
}

/**
 * Inline function to get the next element in a Collection.
 *
 * @param T element.
 * @return T.
 */
inline fun <reified T> Collection<T>.getNext(element: T): T {
    val idx = this.indexOf(element)
    return if (idx < this.size - 1) this.elementAt(idx + 1)
    else element
}

/**
 * Inline function to check if the next element is the last one in a Collection.
 *
 * @param T element.
 * @return Boolean.
 */
inline fun <reified T> Collection<T>.isNextLast(element: T): Boolean {
    return this.isLast(this.getNext(element))
}

/**
 * Function to check if an element is the last one in an IntArray.
 *
 * @param Int element.
 * @return Boolean.
 */
fun IntArray.isLast(element: Int): Boolean {
    return this.indexOf(element) == this.size - 1
}

/**
 * Function to get the next element in an IntArray.
 *
 * @param Int element.
 * @return Int.
 */
fun IntArray.getNext(element: Int): Int {
    val idx = this.indexOf(element)
    return if (idx < this.size - 1) this.elementAt(idx + 1)
    else element
}

/**
 * Function to check if the next element is the last one in an IntArray.
 *
 * @param Int element.
 * @return Boolean.
 */
fun IntArray.isNextLast(element: Int): Boolean {
    return this.isLast(this.getNext(element))
}

/**
 * Function to try to pop an element from a LinkedList.
 *
 * @param T default.
 * @return T?.
 */
fun <T> LinkedList<T>.tryPop(default: T?): T? {
    this.peek() ?: return default
    return this.pop()
}

/**
 * Inline function to parse an enum entry by name.
 *
 * @param E name.
 * @return E?.
 */
inline fun <reified E : Enum<E>> parseEnumEntry(name: String): E? {
    return try {
        enumValueOf<E>(name)
    } catch (e: Exception) {
        null
    }
}
