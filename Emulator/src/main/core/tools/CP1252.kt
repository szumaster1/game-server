package core.tools

class CP1252 {

    companion object {
        private val charMap = charArrayOf('\u20ac', '\u0000', '\u201a', '\u0192', '\u201e', '\u2026', '\u2020', '\u2021', '\u02c6', '\u2030', '\u0160', '\u2039', '\u0152', '\u0000', '\u017d', '\u0000', '\u0000', '\u2018', '\u2019', '\u201c', '\u201d', '\u2022', '\u2013', '\u2014', '\u02dc', '\u2122', '\u0161', '\u203a', '\u0153', '\u0000', '\u017e', '\u0178')

        @JvmStatic
        fun getFromByte(value: Byte): Char {
            var out = value.toInt() and 0xff
            if (out == 0) {
                throw IllegalArgumentException("Non cp1252 character 0x${Integer.toString(out, 16)} provided")
            }
            if (out in 128..159) {
                var cp1252 = charMap[out - 128]
                if (cp1252 == '\u0000') {
                    cp1252 = '?'
                }
                out = cp1252.code
            }
            return out.toChar()
        }
    }
}
