package core.tools;

const val BLACK = "<col=000000>"
const val RED = "<col=ff0000>"
const val ORANGE = "<col=ff6600>"
const val YELLOW = "<col=ffff00>"
const val GREEN = "<col=66ff33>"
const val BLUE = "<col=0000ff>"
const val PURPLE = "<col=cc00ff>"
const val WHITE = "<col=ffffff>"

const val DARK_RED = "<col=8A0808>"
const val DARK_ORANGE = "<col=ab7000>"
const val DARK_YELLOW = "<col=a6a300>"
const val DARK_GREEN = "<col=007d0c>"
const val DARK_BLUE = "<col=08088A>"
const val DARK_PURPLE = "<col=734a75>"

private val pattern = Regex("%[0-9a-fA-F]{6}")

private val testData = arrayOf(
    "This is a string with no colors.",
    "This %R is a string with one color.",
    "This %R %G %B is a string with multiple colors.",
    "This %ffffff is an arbitrary hex string."
)

/**
 * Colorize font util function.
 *
 * @param line the string to be colorized.
 * @return colored text with appropriate color codes.
 */
fun colorize(line: String): String {
    return line
        .replace("%BK", BLACK)
        .replace("%R", RED)
        .replace("%O", ORANGE)
        .replace("%Y", YELLOW)
        .replace("%G", GREEN)
        .replace("%B", BLUE)
        .replace("%P", PURPLE)
        .replace("%W", WHITE)
        .replace("%DR", DARK_RED)
        .replace("%DO", DARK_ORANGE)
        .replace("%DY", DARK_YELLOW)
        .replace("%DG", DARK_GREEN)
        .replace("%DB", DARK_BLUE)
        .replace("%DP", DARK_PURPLE)
        .replace(pattern) { matchResult -> "<col=${matchResult.value.substring(1)}>" }
        .append("</col>") + " "
}

/**
 * Colorize with hex color.
 *
 * @param line the string to be colorized.
 * @param hexColor the hex color code to apply.
 * @return string with hex color applied.
 */
fun colorize(line: String, hexColor: String): String {
    return line.prepend("<col=$hexColor>").append("</col>")
}

/**
 * Append a string.
 *
 * @param line the string to append.
 * @return the concatenated string.
 */
fun String.append(line: String): String {
    return this + line
}

/**
 * Prepend a string.
 *
 * @param line the string to prepend.
 * @return the concatenated string.
 */
fun String.prepend(line: String): String {
    return line + this
}

/**
 * Shuffle the characters in the string.
 *
 * @return the shuffled string.
 */
fun String.shuffle(): String {
    var new = ""
    val old = this.split("").toMutableList()
    for (i in this.indices) {
        val c = old.random()
        new += c.toString()
        old.remove(c)
    }
    return new
}

/**
 * Prepend article to a noun.
 *
 * @param noun the string to prepend an article to.
 * @return the string with the appropriate article.
 */
fun prependArticle(noun: String): String {
    if (noun == null) return noun
    val exceptions = hashMapOf("unicorn" to "a", "herb" to "an", "hour" to "an")
    if (exceptions.contains(noun.lowercase())) {
        return "${exceptions[noun.lowercase()]} $noun"
    }
    return when (noun[0]) {
        'a', 'e', 'i', 'o', 'u' -> "an $noun"
        else -> "a $noun"
    }
}
