package core.cache.crypto;

/**
 * Isaac pair.
 */
public final class ISAACPair {

    private ISAACCipher input;

    private ISAACCipher output;

    /**
     * Instantiates a new Isaac pair.
     *
     * @param input  the input
     * @param output the output
     */
    public ISAACPair(ISAACCipher input, ISAACCipher output) {
        this.input = input;
        this.output = output;
    }

    /**
     * Gets input.
     *
     * @return the input
     */
    public ISAACCipher getInput() {
        return input;
    }

    /**
     * Gets output.
     *
     * @return the output
     */
    public ISAACCipher getOutput() {
        return output;
    }

}
