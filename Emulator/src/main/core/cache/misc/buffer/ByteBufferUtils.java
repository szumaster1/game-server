package core.cache.misc.buffer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Holds utility methods for reading/writing a byte buffer.
 * @author Emperor
 */
public final class ByteBufferUtils {

    /**
     * Gets a string from the byte buffer.
     *
     * @param buffer the byte buffer.
     * @return the string.
     */
    public static String getString(ByteBuffer buffer) {
        StringBuilder sb = new StringBuilder();
        byte b;
        while ((b = buffer.get()) != 0) {
            sb.append((char) b);
        }
        return sb.toString();
    }

    /**
     * Puts a string on the byte buffer.
     *
     * @param string the string to put.
     * @param buffer the byte buffer.
     */
    public static void putString(String string, ByteBuffer buffer) {
        buffer.put(string.getBytes(StandardCharsets.UTF_8)).put((byte) 0);
    }

    /**
     * Gets a string from the byte buffer.
     *
     * @param string the string.
     * @param buffer the byte buffer.
     * @return the string.
     */
    public static ByteBuffer putGJ2String(String string, ByteBuffer buffer) {
        byte[] packed = new byte[256];
        int length = packGJString2(0, packed, string);
        return buffer.put((byte) 0).put(packed, 0, length).put((byte) 0);
    }

    /**
     * Decodes the XTEA encryption.
     *
     * @param keys   the keys.
     * @param start  the start index.
     * @param end    the end index.
     * @param buffer the byte buffer.
     */
    public static void decodeXTEA(int[] keys, int start, int end, ByteBuffer buffer) {
        int l = buffer.position();
        buffer.position(start);
        int length = (end - start) / 8;
        for (int i = 0; i < length; i++) {
            int firstInt = buffer.getInt();
            int secondInt = buffer.getInt();
            int sum = 0xc6ef3720;
            int delta = 0x9e3779b9;
            for (int j = 32; j-- > 0; ) {
                secondInt -= keys[(sum & 0x1c84) >>> 11] + sum ^ (firstInt >>> 5 ^ firstInt << 4) + firstInt;
                sum -= delta;
                firstInt -= (secondInt >>> 5 ^ secondInt << 4) + secondInt ^ keys[sum & 3] + sum;
            }
            buffer.position(buffer.position() - 8);
            buffer.putInt(firstInt);
            buffer.putInt(secondInt);
        }
        buffer.position(l);
    }

    /**
     * Converts a String to an Integer?
     *
     * @param position the position.
     * @param buffer   the buffer used.
     * @param string   the String to convert.
     * @return the integer.
     */
    public static int packGJString2(int position, byte[] buffer, String string) {
        int length = string.length();
        int offset = position;
        for (int i = 0; length > i; i++) {
            int character = string.charAt(i);
            if (character > 127) {
                if (character > 2047) {
                    buffer[offset++] = (byte) ((character | 919275) >> 12);
                    buffer[offset++] = (byte) (128 | ((character >> 6) & 63));
                    buffer[offset++] = (byte) (128 | (character & 63));
                } else {
                    buffer[offset++] = (byte) ((character | 12309) >> 6);
                    buffer[offset++] = (byte) (128 | (character & 63));
                }
            } else
                buffer[offset++] = (byte) character;
        }
        return offset - position;
    }

    /**
     * Gets a tri-byte from the buffer.
     *
     * @param buffer the buffer.
     * @return the value.
     */
    public static int getMedium(ByteBuffer buffer) {
        return ((buffer.get() & 0xFF) << 16) + ((buffer.get() & 0xFF) << 8) + (buffer.get() & 0xFF);
    }

    /**
     * Gets a smart from the buffer.
     *
     * @param buffer the buffer.
     * @return the value.
     */
    public static int getSmart(ByteBuffer buffer) {
        int peek = buffer.get() & 0xFF;
        if (peek <= Byte.MAX_VALUE) {
            return peek;
        }
        return ((peek << 8) | (buffer.get() & 0xFF)) - 32768;
    }

    /**
     * Gets a smart from the buffer.
     *
     * @param buffer the buffer.
     * @return the value.
     */
    public static int getBigSmart(ByteBuffer buffer) {
        int value = 0;
        int current = getSmart(buffer);
        while (current == 32767) {
            current = getSmart(buffer);
            value += 32767;
        }
        value += current;
        return value;
    }

    /**
     * Writes an object on the buffer.
     *
     * @param buffer the buffer to write on.
     * @param obj    the object.
     */
    public static void putObject(ByteBuffer buffer, Object obj) {
        ByteBuffer b;
        try (ObjectOutputStream out = new ObjectOutputStream(new
            BufferOutputStream(b = ByteBuffer.allocate(99999)))) {
            out.writeObject(obj);
            b.flip();
        } catch (Throwable e) {
            e.printStackTrace();
            b = (ByteBuffer) ByteBuffer.allocate(0).flip();
        }
        buffer.putInt(b.remaining());
        if (b.remaining() > 0) {
            buffer.put(b);
        }
    }


    /**
     * Gets an object from the byte buffer.
     *
     * @param buffer the buffer.
     * @return the object.
     */
    public static Object getObject(ByteBuffer buffer) {
        int length = buffer.getInt();
        if (length > 0) {
            byte[] bytes = new byte[length];
            buffer.get(bytes);
            try (ObjectInputStream str = new ObjectInputStream(new BufferInputStream(ByteBuffer.wrap(bytes)))) {
                return (Object) str.readObject();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Constructs a new {@code ByteBufferUtils} {@code Object}.
     */
    private ByteBufferUtils() {
        /*
         * empty.
         */
    }
}