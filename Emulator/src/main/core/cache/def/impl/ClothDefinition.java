package core.cache.def.impl;

import core.Configuration;
import core.cache.Cache;

import java.nio.ByteBuffer;

/**
 * Cloth definition.
 */
public final class ClothDefinition {

    private int equipmentSlot;

    private int[] modelIds;

    private boolean unknownBool;

    private int[] originalColors;

    private int[] modifiedColors;

    private int[] originalTextureColors;

    private int[] modifiedTextureColors;

    private int[] models = {-1, -1, -1, -1, -1};

    /**
     * For id cloth definition.
     *
     * @param clothId the cloth id
     * @return the cloth definition
     */
    public static ClothDefinition forId(int clothId) {
        ClothDefinition def = new ClothDefinition();
        byte[] bs = Cache.getIndexes()[2].getFileData(3, clothId);
        if (bs != null) {
            def.load(ByteBuffer.wrap(bs));
        }
        return def;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String... args) {
        try {
            Cache.init(Configuration.CACHE_PATH);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        int length = Cache.getIndexes()[2].getFilesSize(3);

        for (int i = 0; i < length; i++) {
            ClothDefinition def = forId(i);
        }
    }

    /**
     * Load.
     *
     * @param buffer the buffer
     */
    public void load(ByteBuffer buffer) {
        int opcode;
        while ((opcode = buffer.get() & 0xFF) != 0) {
            parse(opcode, buffer);
        }
    }

    private void parse(int opcode, ByteBuffer buffer) {
        switch (opcode) {
            case 1:
                equipmentSlot = buffer.get() & 0xFF;
                break;
            case 2:
                int length = buffer.get() & 0xFF;
                modelIds = new int[length];
                for (int i = 0; i < length; i++) {
                    modelIds[i] = buffer.getShort() & 0xFFFF;
                }
                break;
            case 3:
                unknownBool = true;
                break;
            case 40:
                length = buffer.get() & 0xFF;
                originalColors = new int[length];
                modifiedColors = new int[length];
                for (int i = 0; i < length; i++) {
                    originalColors[i] = buffer.getShort();
                    modifiedColors[i] = buffer.getShort();
                }
                break;
            case 41:
                length = buffer.get() & 0xFF;
                originalTextureColors = new int[length];
                modifiedTextureColors = new int[length];
                for (int i = 0; i < length; i++) {
                    originalTextureColors[i] = buffer.getShort();
                    modifiedTextureColors[i] = buffer.getShort();
                }
                break;
            default:
                if (opcode >= 60 && opcode < 70) {
                    models[opcode - 60] = buffer.getShort() & 0xFFFF;
                }
                break;
        }
    }

    /**
     * Gets unknown.
     *
     * @return the unknown
     */
    public int getUnknown() {
        return equipmentSlot;
    }

    /**
     * Get model ids int [ ].
     *
     * @return the int [ ]
     */
    public int[] getModelIds() {
        return modelIds;
    }

    /**
     * Is unknown bool boolean.
     *
     * @return the boolean
     */
    public boolean isUnknownBool() {
        return unknownBool;
    }

    /**
     * Get original colors int [ ].
     *
     * @return the int [ ]
     */
    public int[] getOriginalColors() {
        return originalColors;
    }

    /**
     * Get modified colors int [ ].
     *
     * @return the int [ ]
     */
    public int[] getModifiedColors() {
        return modifiedColors;
    }

    /**
     * Get original texture colors int [ ].
     *
     * @return the int [ ]
     */
    public int[] getOriginalTextureColors() {
        return originalTextureColors;
    }

    /**
     * Get modified texture colors int [ ].
     *
     * @return the int [ ]
     */
    public int[] getModifiedTextureColors() {
        return modifiedTextureColors;
    }

    /**
     * Get models int [ ].
     *
     * @return the int [ ]
     */
    public int[] getModels() {
        return models;
    }
}