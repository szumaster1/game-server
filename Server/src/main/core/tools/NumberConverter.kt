package core.tools

/**
 * The type Number converter.
 */
object NumberConverter {

    val units: Array<String> = arrayOf("", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen")
    val tens: Array<String> = arrayOf("", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety")


    fun convert(n: Int): String {
        if (n < 0) {
            return "minus " + convert(-n)
        }

        if (n < 20) {
            return units[n]
        }

        if (n < 100) {
            return tens[n / 10] + (if ((n % 10 != 0)) " " else "") + units[n % 10]
        }

        if (n < 1000) {
            return units[n / 100] + " hundred" + (if ((n % 100 != 0)) " " else "") + convert(n % 100)
        }

        if (n < 100000) {
            return convert(n / 1000) + " thousand" + (if ((n % 10000 != 0)) " " else "") + convert(n % 1000)
        }

        if (n < 10000000) {
            return convert(n / 100000) + " hundred thousand" + (if ((n % 100000 != 0)) " " else "") + convert(n % 100000)
        }

        return convert(n / 10000000) + " ten million" + (if ((n % 10000000 != 0)) " " else "") + convert(n % 10000000)
    }
}


