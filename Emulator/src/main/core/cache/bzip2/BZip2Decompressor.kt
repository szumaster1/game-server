package core.cache.bzip2

/**
 * The type B zip 2 decompressor.
 */
object BZip2Decompressor {
    private var anIntArray257: IntArray? = null
    private var entryInstance: BZip2BlockEntry? = BZip2BlockEntry()

    /**
     * Decompress.
     *
     * @param decompressedData the decompressed data
     * @param packedData       the packed data
     * @param containerSize    the container size
     * @param blockSize        the block size
     */
    @JvmStatic
    fun decompress(decompressedData: ByteArray, packedData: ByteArray?, containerSize: Int, blockSize: Int) {
        synchronized(entryInstance!!) {
            entryInstance!!.aByteArray2224 = packedData
            entryInstance!!.anInt2209 = blockSize
            entryInstance!!.aByteArray2212 = decompressedData
            entryInstance!!.anInt2203 = 0
            entryInstance!!.anInt2206 = decompressedData.size
            entryInstance!!.anInt2232 = 0
            entryInstance!!.anInt2207 = 0
            entryInstance!!.anInt2217 = 0
            entryInstance!!.anInt2216 = 0
            method1793(entryInstance)
            entryInstance!!.aByteArray2224 = null
            entryInstance!!.aByteArray2212 = null
        }
    }

    private fun method1785(entry: BZip2BlockEntry?) {
        entry!!.anInt2215 = 0
        for (i in 0..255) {
            if (entry.aBooleanArray2213[i]) {
                entry.aByteArray2211[entry.anInt2215] = i.toByte()
                entry.anInt2215++
            }
        }
    }

    private fun method1786(ai: IntArray, ai1: IntArray, ai2: IntArray, abyte0: ByteArray, i: Int, j: Int, k: Int) {
        var l = 0
        for (i1 in i..j) {
            for (l2 in 0 until k) {
                if (abyte0[l2].toInt() == i1) {
                    ai2[l] = l2
                    l++
                }
            }
        }
        for (j1 in 0..22) {
            ai1[j1] = 0
        }
        for (k1 in 0 until k) {
            ai1[abyte0[k1] + 1]++
        }
        for (l1 in 1..22) {
            ai1[l1] += ai1[l1 - 1]
        }
        for (i2 in 0..22) {
            ai[i2] = 0
        }
        var i3 = 0
        for (j2 in i..j) {
            i3 += ai1[j2 + 1] - ai1[j2]
            ai[j2] = i3 - 1
            i3 = i3 shl 1
        }
        for (k2 in i + 1..j) {
            ai1[k2] = (ai[k2 - 1] + 1 shl 1) - ai1[k2]
        }
    }

    private fun method1787(entry: BZip2BlockEntry?) {
        var byte4 = entry!!.aByte2201
        var i = entry.anInt2222
        var j = entry.anInt2227
        var k = entry.anInt2221
        val ai = anIntArray257
        var l = entry.anInt2208
        val abyte0 = entry.aByteArray2212
        var i1 = entry.anInt2203
        var j1 = entry.anInt2206
        val k1 = j1
        val l1 = entry.anInt2225 + 1
        label0@ do {
            if (i > 0) {
                do {
                    if (j1 == 0) {
                        break@label0
                    }
                    if (i == 1) {
                        break
                    }
                    abyte0[i1] = byte4
                    i--
                    i1++
                    j1--
                } while (true)
                abyte0[i1] = byte4
                i1++
                j1--
            }
            var flag = true
            while (flag) {
                flag = false
                if (j == l1) {
                    i = 0
                    break@label0
                }
                byte4 = k.toByte()
                l = ai!![l]
                val byte0 = (l and 0xff).toByte()
                l = l shr 8
                j++
                if (byte0.toInt() != k) {
                    k = byte0.toInt()
                    if (j1 == 0) {
                        i = 1
                    } else {
                        abyte0[i1] = byte4
                        i1++
                        j1--
                        flag = true
                        continue
                    }
                    break@label0
                }
                if (j != l1) {
                    continue
                }
                if (j1 == 0) {
                    i = 1
                    break@label0
                }
                abyte0[i1] = byte4
                i1++
                j1--
                flag = true
            }
            i = 2
            l = ai!![l]
            val byte1 = (l and 0xff).toByte()
            l = l shr 8
            if (++j != l1) {
                if (byte1.toInt() != k) {
                    k = byte1.toInt()
                } else {
                    i = 3
                    l = ai[l]
                    val byte2 = (l and 0xff).toByte()
                    l = l shr 8
                    if (++j != l1) {
                        if (byte2.toInt() != k) {
                            k = byte2.toInt()
                        } else {
                            l = ai[l]
                            val byte3 = (l and 0xff).toByte()
                            l = l shr 8
                            j++
                            i = (byte3.toInt() and 0xff) + 4
                            l = ai[l]
                            k = (l and 0xff).toByte().toInt()
                            l = l shr 8
                            j++
                        }
                    }
                }
            }
        } while (true)
        entry.anInt2216 += k1 - j1
        entry.aByte2201 = byte4
        entry.anInt2222 = i
        entry.anInt2227 = j
        entry.anInt2221 = k
        anIntArray257 = ai
        entry.anInt2208 = l
        entry.aByteArray2212 = abyte0
        entry.anInt2203 = i1
        entry.anInt2206 = j1
    }

    private fun method1788(entry: BZip2BlockEntry?): Byte {
        return method1790(1, entry).toByte()
    }

    private fun method1789(entryInstance2: BZip2BlockEntry?): Byte {
        return method1790(8, entryInstance2).toByte()
    }

    private fun method1790(i: Int, entry: BZip2BlockEntry?): Int {
        val j: Int
        do {
            if (entry!!.anInt2232 >= i) {
                val k = entry.anInt2207 shr entry.anInt2232 - i and (1 shl i) - 1
                entry.anInt2232 -= i
                j = k
                break
            }
            entry.anInt2207 = entry.anInt2207 shl 8 or (entry.aByteArray2224[entry.anInt2209].toInt() and 0xff)
            entry.anInt2232 += 8
            entry.anInt2209++
            entry.anInt2217++
        } while (true)
        return j
    }

    /**
     * Clear block entry instance.
     */
    fun clearBlockEntryInstance() {
        entryInstance = null
    }

    private fun method1793(entryInstance2: BZip2BlockEntry?) {
        // unused
        /*
         * boolean flag = false; boolean flag1 = false; boolean flag2 = false;
         * boolean flag3 = false; boolean flag4 = false; boolean flag5 = false;
         * boolean flag6 = false; boolean flag7 = false; boolean flag8 = false;
         * boolean flag9 = false; boolean flag10 = false; boolean flag11 =
         * false; boolean flag12 = false; boolean flag13 = false; boolean flag14
         * = false; boolean flag15 = false; boolean flag16 = false; boolean
         * flag17 = false;
         */
        var j8 = 0
        var ai: IntArray? = null
        var ai1: IntArray? = null
        var ai2: IntArray? = null
        entryInstance2!!.anInt2202 = 1
        if (anIntArray257 == null) {
            anIntArray257 = IntArray(entryInstance2.anInt2202 * 0x186a0)
        }
        var flag18 = true
        while (flag18) {
            var byte0 = method1789(entryInstance2)
            if (byte0.toInt() == 23) {
                return
            }
            byte0 = method1789(entryInstance2)
            byte0 = method1789(entryInstance2)
            byte0 = method1789(entryInstance2)
            byte0 = method1789(entryInstance2)
            byte0 = method1789(entryInstance2)
            byte0 = method1789(entryInstance2)
            byte0 = method1789(entryInstance2)
            byte0 = method1789(entryInstance2)
            byte0 = method1789(entryInstance2)
            byte0 = method1788(entryInstance2)
            entryInstance2.anInt2223 = 0
            byte0 = method1789(entryInstance2)
            entryInstance2.anInt2223 = entryInstance2.anInt2223 shl 8 or (byte0.toInt() and 0xff)
            byte0 = method1789(entryInstance2)
            entryInstance2.anInt2223 = entryInstance2.anInt2223 shl 8 or (byte0.toInt() and 0xff)
            byte0 = method1789(entryInstance2)
            entryInstance2.anInt2223 = entryInstance2.anInt2223 shl 8 or (byte0.toInt() and 0xff)
            for (j in 0..15) {
                val byte1 = method1788(entryInstance2)
                entryInstance2.aBooleanArray2205[j] = byte1.toInt() == 1
            }
            for (k in 0..255) {
                entryInstance2.aBooleanArray2213[k] = false
            }
            for (l in 0..15) {
                if (entryInstance2.aBooleanArray2205[l]) {
                    for (i3 in 0..15) {
                        val byte2 = method1788(entryInstance2)
                        if (byte2.toInt() == 1) {
                            entryInstance2.aBooleanArray2213[l * 16 + i3] = true
                        }
                    }
                }
            }
            method1785(entryInstance2)
            val i4 = entryInstance2.anInt2215 + 2
            val j4 = method1790(3, entryInstance2)
            val k4 = method1790(15, entryInstance2)
            for (i1 in 0 until k4) {
                var j3 = 0
                do {
                    val byte3 = method1788(entryInstance2)
                    if (byte3.toInt() == 0) {
                        break
                    }
                    j3++
                } while (true)
                entryInstance2.aByteArray2214[i1] = j3.toByte()
            }
            val abyte0 = ByteArray(6)
            for (byte16 in 0 until j4) {
                abyte0[byte16] = byte16.toByte()
            }
            for (j1 in 0 until k4) {
                var byte17 = entryInstance2.aByteArray2214[j1]
                val byte15 = abyte0[byte17.toInt()]
                while (byte17 > 0) {
                    abyte0[byte17.toInt()] = abyte0[byte17 - 1]
                    byte17--
                }
                abyte0[0] = byte15
                entryInstance2.aByteArray2219[j1] = byte15
            }
            for (k3 in 0 until j4) {
                var k6 = method1790(5, entryInstance2)
                for (k1 in 0 until i4) {
                    do {
                        var byte4 = method1788(entryInstance2)
                        if (byte4.toInt() == 0) {
                            break
                        }
                        byte4 = method1788(entryInstance2)
                        if (byte4.toInt() == 0) {
                            k6++
                        } else {
                            k6--
                        }
                    } while (true)
                    entryInstance2.aByteArrayArray2229[k3][k1] = k6.toByte()
                }
            }
            for (l3 in 0 until j4) {
                var byte8: Byte = 32
                var i = 0
                for (l1 in 0 until i4) {
                    if (entryInstance2.aByteArrayArray2229[l3][l1] > i) {
                        i = entryInstance2.aByteArrayArray2229[l3][l1].toInt()
                    }
                    if (entryInstance2.aByteArrayArray2229[l3][l1] < byte8) {
                        byte8 = entryInstance2.aByteArrayArray2229[l3][l1]
                    }
                }
                method1786(
                    entryInstance2.anIntArrayArray2230[l3],
                    entryInstance2.anIntArrayArray2218[l3],
                    entryInstance2.anIntArrayArray2210[l3],
                    entryInstance2.aByteArrayArray2229[l3],
                    byte8.toInt(),
                    i,
                    i4
                )
                entryInstance2.anIntArray2200[l3] = byte8.toInt()
            }
            val l4 = entryInstance2.anInt2215 + 1
            var i5 = -1
            var j5 = 0
            for (i2 in 0..255) {
                entryInstance2.anIntArray2228[i2] = 0
            }
            var i9 = 4095
            for (k8 in 15 downTo 0) {
                for (l8 in 15 downTo 0) {
                    entryInstance2.aByteArray2204[i9] = (k8 * 16 + l8).toByte()
                    i9--
                }
                entryInstance2.anIntArray2226[k8] = i9 + 1
            }
            var l5 = 0
            if (j5 == 0) {
                i5++
                j5 = 50
                val byte12 = entryInstance2.aByteArray2219[i5]
                j8 = entryInstance2.anIntArray2200[byte12.toInt()]
                ai = entryInstance2.anIntArrayArray2230[byte12.toInt()]
                ai2 = entryInstance2.anIntArrayArray2210[byte12.toInt()]
                ai1 = entryInstance2.anIntArrayArray2218[byte12.toInt()]
            }
            j5--
            var l6 = j8
            var k7: Int
            var byte9: Byte
            k7 = method1790(l6, entryInstance2)
            while (k7 > ai!![l6]) {
                l6++
                byte9 = method1788(entryInstance2)
                k7 = k7 shl 1 or byte9.toInt()
            }
            var k5 = ai2!![k7 - ai1!![l6]]
            while (k5 != l4) {
                if (k5 == 0 || k5 == 1) {
                    var i6 = -1
                    var j6 = 1
                    do {
                        if (k5 == 0) {
                            i6 += j6
                        } else if (k5 == 1) {
                            i6 += 2 * j6
                        }
                        j6 *= 2
                        if (j5 == 0) {
                            i5++
                            j5 = 50
                            val byte13 = entryInstance2.aByteArray2219[i5]
                            j8 = entryInstance2.anIntArray2200[byte13.toInt()]
                            ai = entryInstance2.anIntArrayArray2230[byte13.toInt()]
                            ai2 = entryInstance2.anIntArrayArray2210[byte13.toInt()]
                            ai1 = entryInstance2.anIntArrayArray2218[byte13.toInt()]
                        }
                        j5--
                        var i7 = j8
                        var l7: Int
                        var byte10: Byte
                        l7 = method1790(i7, entryInstance2)
                        while (l7 > ai!![i7]) {
                            i7++
                            byte10 = method1788(entryInstance2)
                            l7 = l7 shl 1 or byte10.toInt()
                        }
                        k5 = ai2!![l7 - ai1!![i7]]
                    } while (k5 == 0 || k5 == 1)
                    i6++
                    val byte5 =
                        entryInstance2.aByteArray2211[entryInstance2.aByteArray2204[entryInstance2.anIntArray2226[0]].toInt() and 0xff]
                    entryInstance2.anIntArray2228[byte5.toInt() and 0xff] += i6
                    while (i6 > 0) {
                        anIntArray257!![l5] = byte5.toInt() and 0xff
                        l5++
                        i6--
                    }
                } else {
                    var i11 = k5 - 1
                    var byte6: Byte
                    if (i11 < 16) {
                        val i10 = entryInstance2.anIntArray2226[0]
                        byte6 = entryInstance2.aByteArray2204[i10 + i11]
                        while (i11 > 3) {
                            val j11 = i10 + i11
                            entryInstance2.aByteArray2204[j11] = entryInstance2.aByteArray2204[j11 - 1]
                            entryInstance2.aByteArray2204[j11 - 1] = entryInstance2.aByteArray2204[j11 - 2]
                            entryInstance2.aByteArray2204[j11 - 2] = entryInstance2.aByteArray2204[j11 - 3]
                            entryInstance2.aByteArray2204[j11 - 3] = entryInstance2.aByteArray2204[j11 - 4]
                            i11 -= 4
                        }
                        while (i11 > 0) {
                            entryInstance2.aByteArray2204[i10 + i11] = entryInstance2.aByteArray2204[i10 + i11 - 1]
                            i11--
                        }
                        entryInstance2.aByteArray2204[i10] = byte6
                    } else {
                        var k10 = i11 / 16
                        val l10 = i11 % 16
                        var j10 = entryInstance2.anIntArray2226[k10] + l10
                        byte6 = entryInstance2.aByteArray2204[j10]
                        while (j10 > entryInstance2.anIntArray2226[k10]) {
                            entryInstance2.aByteArray2204[j10] = entryInstance2.aByteArray2204[j10 - 1]
                            j10--
                        }
                        entryInstance2.anIntArray2226[k10]++
                        while (k10 > 0) {
                            entryInstance2.anIntArray2226[k10]--
                            entryInstance2.aByteArray2204[entryInstance2.anIntArray2226[k10]] =
                                entryInstance2.aByteArray2204[entryInstance2.anIntArray2226[k10 - 1] + 16 - 1]
                            k10--
                        }
                        entryInstance2.anIntArray2226[0]--
                        entryInstance2.aByteArray2204[entryInstance2.anIntArray2226[0]] = byte6
                        if (entryInstance2.anIntArray2226[0] == 0) {
                            var l9 = 4095
                            for (j9 in 15 downTo 0) {
                                for (k9 in 15 downTo 0) {
                                    entryInstance2.aByteArray2204[l9] =
                                        entryInstance2.aByteArray2204[entryInstance2.anIntArray2226[j9] + k9]
                                    l9--
                                }
                                entryInstance2.anIntArray2226[j9] = l9 + 1
                            }
                        }
                    }
                    entryInstance2.anIntArray2228[entryInstance2.aByteArray2211[byte6.toInt() and 0xff].toInt() and 0xff]++
                    anIntArray257!![l5] = entryInstance2.aByteArray2211[byte6.toInt() and 0xff].toInt() and 0xff
                    l5++
                    if (j5 == 0) {
                        i5++
                        j5 = 50
                        val byte14 = entryInstance2.aByteArray2219[i5]
                        j8 = entryInstance2.anIntArray2200[byte14.toInt()]
                        ai = entryInstance2.anIntArrayArray2230[byte14.toInt()]
                        ai2 = entryInstance2.anIntArrayArray2210[byte14.toInt()]
                        ai1 = entryInstance2.anIntArrayArray2218[byte14.toInt()]
                    }
                    j5--
                    var j7 = j8
                    var i8: Int
                    var byte11: Byte
                    i8 = method1790(j7, entryInstance2)
                    while (i8 > ai!![j7]) {
                        j7++
                        byte11 = method1788(entryInstance2)
                        i8 = i8 shl 1 or byte11.toInt()
                    }
                    k5 = ai2!![i8 - ai1!![j7]]
                }
            }
            entryInstance2.anInt2222 = 0
            entryInstance2.aByte2201 = 0
            entryInstance2.anIntArray2220[0] = 0
            for (j2 in 1..256) {
                entryInstance2.anIntArray2220[j2] = entryInstance2.anIntArray2228[j2 - 1]
            }
            for (k2 in 1..256) {
                entryInstance2.anIntArray2220[k2] += entryInstance2.anIntArray2220[k2 - 1]
            }
            for (l2 in 0 until l5) {
                val byte7 = (anIntArray257!![l2] and 0xff).toByte()
                anIntArray257!![entryInstance2.anIntArray2220[byte7.toInt() and 0xff]] =
                    anIntArray257!![entryInstance2.anIntArray2220[byte7.toInt() and 0xff]] or (l2 shl 8)
                entryInstance2.anIntArray2220[byte7.toInt() and 0xff]++
            }
            entryInstance2.anInt2208 = anIntArray257!![entryInstance2.anInt2223] shr 8
            entryInstance2.anInt2227 = 0
            entryInstance2.anInt2208 = anIntArray257!![entryInstance2.anInt2208]
            entryInstance2.anInt2221 = (entryInstance2.anInt2208 and 0xff).toByte().toInt()
            entryInstance2.anInt2208 = entryInstance2.anInt2208 shr 8
            entryInstance2.anInt2227++
            entryInstance2.anInt2225 = l5
            method1787(entryInstance2)
            flag18 = entryInstance2.anInt2227 == entryInstance2.anInt2225 + 1 && entryInstance2.anInt2222 == 0
        }
    }
}