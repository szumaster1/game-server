package core.cache;

import java.nio.ByteBuffer;

/**
 * Store file.
 */
public final class StoreFile {

    private boolean dynamic;

    private byte[] data;

    /**
     * Instantiates a new Store file.
     */
    public StoreFile() {
        /*
         * empty.
         */
    }

    /**
     * Put.
     *
     * @param buffer the buffer
     */
    public void put(ByteBuffer buffer) {
        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);
        this.data = data;
    }

    /**
     * Data byte buffer.
     *
     * @return the byte buffer
     */
    public ByteBuffer data() {
        return ByteBuffer.wrap(data);
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(byte[] data) {
        this.data = data;
    }

    /**
     * Is dynamic boolean.
     *
     * @return the boolean
     */
    public boolean isDynamic() {
        return dynamic;
    }

    /**
     * Sets dynamic.
     *
     * @param dynamic the dynamic
     */
    public void setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
    }

}