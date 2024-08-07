package core.cache.gzip;

import java.nio.ByteBuffer;
import java.util.zip.Inflater;

/**
 * G zip decompressor.
 */
public class GZipDecompressor {

    private static final Inflater inflaterInstance = new Inflater(true);

    /**
     * Decompress.
     *
     * @param buffer the buffer
     * @param data   the data
     */
    public static final void decompress(ByteBuffer buffer, byte[] data) {
        synchronized (inflaterInstance) {
            if (~buffer.get(buffer.position()) != -32 || buffer.get(buffer.position() + 1) != -117) {
                data = null;
                // throw new RuntimeException("Invalid GZIP header!");
            }
            try {
                inflaterInstance.setInput(buffer.array(), buffer.position() + 10, -buffer.position() - 18 + buffer.limit());
                inflaterInstance.inflate(data);
            } catch (Exception e) {
                // inflaterInstance.reset();
                data = null;
                // throw new RuntimeException("Invalid GZIP compressed data!");
            }
            inflaterInstance.reset();
        }
    }

    /**
     * Decompress boolean.
     *
     * @param compressed the compressed
     * @param data       the data
     * @param offset     the offset
     * @param length     the length
     * @return the boolean
     */
    public static final boolean decompress(byte[] compressed, byte data[], int offset, int length) {
        synchronized (inflaterInstance) {
            if (data[offset] != 31 || data[offset + 1] != -117) return false;
            // throw new RuntimeException("Invalid GZIP header!");
            try {
                inflaterInstance.setInput(data, offset + 10, -offset - 18 + length);
                inflaterInstance.inflate(compressed);
            } catch (Exception e) {
                inflaterInstance.reset();
                e.printStackTrace();
                return false;
                // throw new RuntimeException("Invalid GZIP compressed data!");
            }
            inflaterInstance.reset();
            return true;
        }
    }

}
