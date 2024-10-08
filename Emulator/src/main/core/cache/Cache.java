package core.cache;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

import core.Configuration;
import core.cache.def.impl.AnimationDefinition;
import core.cache.def.impl.GraphicDefinition;
import core.cache.def.impl.ItemDefinition;
import core.cache.def.impl.NPCDefinition;
import core.cache.def.impl.SceneryDefinition;
import core.tools.Log;

import static core.api.ContentAPIKt.log;

/**
 * A cache reader.
 * @author Emperor, Dragonkk
 */
public final class Cache {

    /**
     * The cache file manager.
     */
    private static CacheFileManager[] cacheFileManagers;

    /**
     * The container cache file informer.
     */
    private static CacheFile referenceFile;

    /**
     * Construct a new instance.
     */
    private Cache(String location) {
        try {
            init(location);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize the cache reader.
     *
     * @param path the cache path.x
     * @throws Throwable when an exception occurs.
     */
    public static void init(String path) throws Throwable {
        log(Cache.class, Log.FINE, "Initializing cache...");
        byte[] cacheFileBuffer = new byte[520];
        RandomAccessFile containersInformFile = new RandomAccessFile(path + File.separator + "main_file_cache.idx255", "r");
        RandomAccessFile dataFile = new RandomAccessFile(path + File.separator + "main_file_cache.dat2", "r");
        referenceFile = new CacheFile(255, containersInformFile, dataFile, 500000, cacheFileBuffer);
        int length = (int) (containersInformFile.length() / 6);
        cacheFileManagers = new CacheFileManager[length];
        for (int i = 0; i < length; i++) {
            File f = new File(path + File.separator + "main_file_cache.idx" + i);
            if (f.exists() && f.length() > 0) {
                CacheFile cacheFile = new CacheFile(i, new RandomAccessFile(f, "r"), dataFile, 1000000, cacheFileBuffer);
                cacheFileManagers[i] = new CacheFileManager(cacheFile, true);
                if (cacheFileManagers[i].getInformation() == null) {
                    log(Cache.class, Log.ERR, "Error loading cache index " + i + ": no information.");
                    cacheFileManagers[i] = null;
                }
            }
        }
        ItemDefinition.parse();
        SceneryDefinition.parse();
    }

    /**
     * Initializes the cache.
     */
    public static void init() {
        try {
            init(Configuration.CACHE_PATH);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the archive buffer for the grab requests.
     *
     * @param index           the index id.
     * @param archive         the archive id.
     * @param priority        the priority.
     * @param encryptionValue the current encryption value.
     * @return the byte buffer.
     */
    public static ByteBuffer getArchiveData(int index, int archive, boolean priority, int encryptionValue) {
        byte[] data = index == 255 ? referenceFile.getContainerData(archive) : cacheFileManagers[index].getCacheFile().getContainerData(archive);
        if (data == null || data.length < 1) {
            log(Cache.class, Log.ERR, "Invalid JS-5 request - " + index + ", " + archive + ", " + priority + ", " + encryptionValue + "!");
            return null;
        }
        int compression = data[0] & 0xff;
        int length = ((data[1] & 0xff) << 24) + ((data[2] & 0xff) << 16) + ((data[3] & 0xff) << 8) + (data[4] & 0xff);
        int settings = compression;
        if (!priority) {
            settings |= 0x80;
        }
        int realLength = compression != 0 ? length + 4 : length;


        realLength += (index != 255 && compression != 0 && data.length - length == 9) ? 2 : 0;
        ByteBuffer buffer = ByteBuffer.allocate((realLength + 5) + (realLength / 512) + 10);
        buffer.put((byte) index);
        buffer.putShort((short) archive);
        buffer.put((byte) settings);
        buffer.putInt(length);
        for (int i = 5; i < realLength + 5; i++) {
            if (buffer.position() % 512 == 0) {
                buffer.put((byte) 255);
            }
            if (data.length > i)
                buffer.put(data[i]);
            else
                buffer.put((byte) 0);
        }
        if (encryptionValue != 0) {
            for (int i = 0; i < buffer.position(); i++) {
                buffer.put(i, (byte) (buffer.get(i) ^ encryptionValue));
            }
        }
        buffer.flip();
        return buffer;
    }

    /**
     * Generate the reference data for the cache files.
     *
     * @return the reference data byte array.
     */
    public static byte[] generateReferenceData() {
        ByteBuffer buffer = ByteBuffer.allocate(cacheFileManagers.length * 8);
        for (int index = 0; index < cacheFileManagers.length; index++) {
            if (cacheFileManagers[index] == null) {
                buffer.putInt(index == 24 ? 609698396 : 0);
                buffer.putInt(0);
                continue;
            }
            buffer.putInt(cacheFileManagers[index].getInformation().getInformationContainer().getCrc());
            buffer.putInt(cacheFileManagers[index].getInformation().getRevision());
        }
        return buffer.array();
    }

    /**
     * Get the cache file managers.
     *
     * @return the cache file managers.
     */
    public static CacheFileManager[] getIndexes() {
        return cacheFileManagers;
    }

    /**
     * Get the container cache file informer.
     *
     * @return the container cache file informer.
     */
    public static final CacheFile getReferenceFile() {
        return referenceFile;
    }

    /**
     * Method used to return the component size of the interface.
     *
     * @param interfaceId the interface.
     * @return the value.
     */
    public static final int getInterfaceDefinitionsComponentsSize(int interfaceId) {
        return getIndexes()[3].getFilesSize(interfaceId);
    }

    /**
     * Method used to return the max size of the interface definitions.
     *
     * @return the size.
     */
    public static final int getInterfaceDefinitionsSize() {
        return getIndexes()[3].getContainersSize();
    }

    /**
     * Method used to return the {@link NPCDefinition} size.
     *
     * @return the size.
     */
    public static final int getNPCDefinitionsSize() {
        int lastContainerId = getIndexes()[18].getContainersSize() - 1;
        return lastContainerId * 128 + getIndexes()[18].getFilesSize(lastContainerId);
    }

    /**
     * Method used to return the {@link GraphicDefinition} size.
     *
     * @return the size.
     */
    public static final int getGraphicDefinitionsSize() {
        int lastContainerId = getIndexes()[21].getContainersSize() - 1;
        return lastContainerId * 256 + getIndexes()[21].getFilesSize(lastContainerId);
    }

    /**
     * Method used to return the {@link AnimationDefinition} size.
     *
     * @return the size.
     */
    public static final int getAnimationDefinitionsSize() {
        int lastContainerId = getIndexes()[20].getContainersSize() - 1;
        return lastContainerId * 128 + getIndexes()[20].getFilesSize(lastContainerId);
    }

    /**
     * Method used to return the {@link SceneryDefinition} size.
     *
     * @return the size.
     */
    public static final int getObjectDefinitionsSize() {
        int lastContainerId = getIndexes()[16].getContainersSize() - 1;
        return lastContainerId * 256 + getIndexes()[16].getFilesSize(lastContainerId);
    }

    /**
     * Method used to return the item definition size.
     *
     * @return the size.
     */
    public static final int getItemDefinitionsSize() {
        int lastContainerId = getIndexes()[19].getContainersSize() - 1;
        return lastContainerId * 256 + getIndexes()[19].getFilesSize(lastContainerId);
    }

}