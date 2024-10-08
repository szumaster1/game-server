package core.cache;

import java.nio.ByteBuffer;

/**
 * Represents a file used in the server store.
 * @author Emperor
 */
public final class StoreFile {

    /**
     * If the data can change during server runtime.
     */
    private boolean dynamic;

    /**
     * The file data.
     */
    private byte[] data;

    /**
     * Constructs a new {@code StoreFile} {@code Object}.
     */
    public StoreFile() {
        /*
         * empty.
         */
    }

    /**
     * Puts the data on the buffer.
     *
     * @param buffer the buffer.
     */
    public void put(ByteBuffer buffer) {
        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);
        this.data = data;
    }

    /**
     * Creates a byte buffer containing the file data.
     *
     * @return the buffer.
     */
    public ByteBuffer data() {
        return ByteBuffer.wrap(data);
    }

    /**
     * Sets the data.
     *
     * @param data the data.
     */
    public void setData(byte[] data) {
        this.data = data;
    }

    /**
     * Gets the dynamic.
     *
     * @return the dynamic.
     */
    public boolean isDynamic() {
        return dynamic;
    }

    /**
     * Sets the dynamic.
     *
     * @param dynamic the dynamic to set.
     */
    public void setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
    }

}