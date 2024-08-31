package core.cache.def.impl;

import core.cache.Cache;
import core.cache.misc.buffer.ByteBufferUtils;
import core.tools.CP1252;
import core.tools.Log;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static core.api.ContentAPIKt.log;

/**
 * Data map.
 */
public class DataMap {

    private static final Map<Integer, DataMap> definitions = new HashMap<>();

    private final int id;

    /**
     * The Key type.
     */
    public char keyType = '?';

    /**
     * The Value type.
     */
    public char valueType = '?';

    /**
     * The Default string.
     */
    public String defaultString;

    /**
     * The Default int.
     */
    public int defaultInt;

    /**
     * The Data store.
     */
    public HashMap<Integer, Object> dataStore = new HashMap<>();

    /**
     * Instantiates a new Data map.
     *
     * @param id the id
     */
    public DataMap(int id) {
        this.id = id;
    }

    /**
     * Gets int.
     *
     * @param key the key
     * @return the int
     */
    public int getInt(int key) {
        if (!dataStore.containsKey(key)) {
            log(this.getClass(), Log.ERR, "Invalid value passed for key: " + key + " map: " + id);
            return -1;
        }
        return (int) dataStore.get(key);
    }

    /**
     * Gets string.
     *
     * @param key the key
     * @return the string
     */
    public String getString(int key) {
        return (String) dataStore.get(key);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        return "DataMapDefinition{" +
            "id=" + id +
            ", keyType=" + keyType +
            ", valueType=" + (valueType == 'K' ? "Normal" : valueType == 'J' ? "Struct Pointer" : "Unknown") +
            ", defaultString='" + defaultString + '\'' +
            ", defaultInt=" + defaultInt +
            ", dataStore=" + dataStore +
            '}' + "\n";
    }

    /**
     * Get data map.
     *
     * @param id the id
     * @return the data map
     */
    public static DataMap get(int id) {
        DataMap def = definitions.get(id);
        if (def != null) {
            return def;
        }
        byte[] data = Cache.getIndexes()[17].getFileData(id >>> 8, id & 0xFF);
        def = parse(id, data);
        definitions.put(id, def);
        return def;
    }

    /**
     * Parse data map.
     *
     * @param id   the id
     * @param data the data
     * @return the data map
     */
    public static DataMap parse(int id, byte[] data) {
        DataMap def = new DataMap(id);
        if (data != null) {
            ByteBuffer buffer = ByteBuffer.wrap(data);
            int opcode;

            while ((opcode = buffer.get() & 0xFF) != 0) {

                if (opcode == 1) {
                    def.keyType = CP1252.decode(buffer.get());
                } else if (opcode == 2) {
                    def.valueType = CP1252.decode(buffer.get());
                } else if (opcode == 3) {
                    def.defaultString = ByteBufferUtils.getString(buffer);
                } else if (opcode == 4) {
                    def.defaultInt = buffer.getInt();
                } else if (opcode == 5 || opcode == 6) {
                    int size = buffer.getShort() & 0xFFFF;

                    for (int i = 0; i < size; i++) {
                        int key = buffer.getInt();

                        Object value;
                        if (opcode == 5) {
                            value = ByteBufferUtils.getString(buffer);
                        } else {
                            value = buffer.getInt();
                        }
                        def.dataStore.put(key, value);
                    }
                }
            }
        }
        return def;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }
}
