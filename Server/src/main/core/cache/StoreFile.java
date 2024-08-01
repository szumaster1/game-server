package core.cache;

import java.nio.ByteBuffer;

public final class StoreFile {

    private boolean dynamic;

    private byte[] data;

    public StoreFile() {
        /*
         * empty.
         */
    }

    public void put(ByteBuffer buffer) {
        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);
        this.data = data;
    }

    public ByteBuffer data() {
        return ByteBuffer.wrap(data);
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public boolean isDynamic() {
        return dynamic;
    }

    public void setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
    }

}