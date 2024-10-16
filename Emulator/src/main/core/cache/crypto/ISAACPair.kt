package core.cache.crypto

/**
 * Represents a ISAAC key pair, for both input and output.
 * @author `Discardedx2
 */
class ISAACPair
/**
 * Constructs a new `ISAACPair` `Object`.
 *
 * @param input  The input cipher.
 * @param output The output cipher.
 */(
    /**
     * The input cipher.
     */
    val input: ISAACCipher,
    /**
     * The output cipher.
     */
    @JvmField val output: ISAACCipher
)