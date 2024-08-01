package core.cache.crypto;

public final class ISAACPair {

    private ISAACCipher input;

    private ISAACCipher output;

    public ISAACPair(ISAACCipher input, ISAACCipher output) {
        this.input = input;
        this.output = output;
    }

    public ISAACCipher getInput() {
        return input;
    }

    public ISAACCipher getOutput() {
        return output;
    }

}
