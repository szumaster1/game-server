package core.cache.misc;

import core.cache.bzip2.BZip2Decompressor;
import core.cache.gzip.GZipDecompressor;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.zip.CRC32;

/**
 * Containers information.
 */
public final class ContainersInformation {

    private Container informationContainer;

    private int protocol;

    private int revision;

    private int[] containersIndexes;

    private FilesContainer[] containers;

    private boolean filesNamed;

    private boolean whirpool;

    private final byte[] data;

    /**
     * Instantiates a new Containers information.
     *
     * @param informationContainerPackedData the information container packed data
     */
    public ContainersInformation(byte[] informationContainerPackedData) {
        this.data = Arrays.copyOf(informationContainerPackedData, informationContainerPackedData.length);
        informationContainer = new Container();
        informationContainer.setVersion((informationContainerPackedData[informationContainerPackedData.length - 2] << 8 & 0xff00) + (informationContainerPackedData[-1 + informationContainerPackedData.length] & 0xff));
        CRC32 crc32 = new CRC32();
        crc32.update(informationContainerPackedData);
        informationContainer.setCrc((int) crc32.getValue());
        decodeContainersInformation(unpackCacheContainer(informationContainerPackedData));
    }

    /**
     * Unpack cache container byte [ ].
     *
     * @param packedData the packed data
     * @return the byte [ ]
     */
    public static byte[] unpackCacheContainer(byte[] packedData) {
        ByteBuffer buffer = ByteBuffer.wrap(packedData);
        int compression = buffer.get() & 0xFF;
        int containerSize = buffer.getInt();
        if (containerSize < 0 || containerSize > 5000000) {
            return null;
            // throw new RuntimeException();
        }
        if (compression == 0) {
            byte[] unpacked = new byte[containerSize];
            buffer.get(unpacked, 0, containerSize);
            return unpacked;
        }
        int decompressedSize = buffer.getInt();
        if (decompressedSize < 0 || decompressedSize > 20000000) {
            return null;
            // throw new RuntimeException();
        }
        byte[] decompressedData = new byte[decompressedSize];
        if (compression == 1) {
            BZip2Decompressor.decompress(decompressedData, packedData, containerSize, 9);
        } else {
            GZipDecompressor.decompress(buffer, decompressedData);
        }
        return decompressedData;
    }

    /**
     * Get containers indexes int [ ].
     *
     * @return the int [ ]
     */
    public int[] getContainersIndexes() {
        return containersIndexes;
    }

    /**
     * Get containers files container [ ].
     *
     * @return the files container [ ]
     */
    public FilesContainer[] getContainers() {
        return containers;
    }

    /**
     * Gets information container.
     *
     * @return the information container
     */
    public Container getInformationContainer() {
        return informationContainer;
    }

    /**
     * Gets revision.
     *
     * @return the revision
     */
    public int getRevision() {
        return revision;
    }

    /**
     * Decode containers information.
     *
     * @param data the data
     */
    public void decodeContainersInformation(byte[] data) {
        ByteBuffer buffer = ByteBuffer.wrap(data);
        protocol = buffer.get() & 0xFF;
        if (protocol != 5 && protocol != 6) {
            throw new RuntimeException();
        }
        revision = protocol < 6 ? 0 : buffer.getInt();
        int nameHash = buffer.get() & 0xFF;
        filesNamed = (0x1 & nameHash) != 0;
        whirpool = (0x2 & nameHash) != 0;
        containersIndexes = new int[buffer.getShort() & 0xFFFF];
        int lastIndex = -1;
        for (int index = 0; index < containersIndexes.length; index++) {
            containersIndexes[index] = (buffer.getShort() & 0xFFFF) + (index == 0 ? 0 : containersIndexes[index - 1]);
            if (containersIndexes[index] > lastIndex) {
                lastIndex = containersIndexes[index];
            }
        }
        containers = new FilesContainer[lastIndex + 1];
        for (int i : containersIndexes) {
            containers[i] = new FilesContainer();
        }
        if (filesNamed) {
            for (int containersIndex : containersIndexes) {
                containers[containersIndex].setNameHash(buffer.getInt());
            }
        }
        byte[][] filesHashes = null;
        if (whirpool) {
            filesHashes = new byte[containers.length][];
            for (int containersIndex : containersIndexes) {
                filesHashes[containersIndex] = new byte[64];
                buffer.get(filesHashes[containersIndex], 0, 64);
            }
        }
        for (int containersIndex : containersIndexes) {
            containers[containersIndex].setCrc(buffer.getInt());
        }
        for (int i : containersIndexes) {
            containers[i].setVersion(buffer.getInt());
        }
        for (int containersIndex : containersIndexes) {
            containers[containersIndex].setFilesIndexes(new int[buffer.getShort() & 0xFFFF]);
        }
        for (int containersIndex : containersIndexes) {
            int lastFileIndex = -1;
            for (int fileIndex = 0; fileIndex < containers[containersIndex].getFilesIndexes().length; fileIndex++) {
                containers[containersIndex].getFilesIndexes()[fileIndex] = (buffer.getShort() & 0xFFFF) + (fileIndex == 0 ? 0 : containers[containersIndex].getFilesIndexes()[fileIndex - 1]);
                if (containers[containersIndex].getFilesIndexes()[fileIndex] > lastFileIndex) {
                    lastFileIndex = containers[containersIndex].getFilesIndexes()[fileIndex];
                }
            }
            containers[containersIndex].setFiles(new Container[lastFileIndex + 1]);
            for (int fileIndex = 0; fileIndex < containers[containersIndex].getFilesIndexes().length; fileIndex++) {
                containers[containersIndex].getFiles()[containers[containersIndex].getFilesIndexes()[fileIndex]] = new Container();
            }
        }
        if (whirpool) {
            for (int containersIndex : containersIndexes) {
                for (int fileIndex = 0; fileIndex < containers[containersIndex].getFilesIndexes().length; fileIndex++) {
                    containers[containersIndex].getFiles()[containers[containersIndex].getFilesIndexes()[fileIndex]].setVersion(filesHashes[containersIndex][containers[containersIndex].getFilesIndexes()[fileIndex]]);
                }
            }
        }
        if (filesNamed) {
            for (int containersIndex : containersIndexes) {
                for (int fileIndex = 0; fileIndex < containers[containersIndex].getFilesIndexes().length; fileIndex++) {
                    containers[containersIndex].getFiles()[containers[containersIndex].getFilesIndexes()[fileIndex]].setNameHash(buffer.getInt());
                }
            }
        }
    }

    /**
     * Is whirpool boolean.
     *
     * @return the boolean
     */
    public boolean isWhirpool() {
        return whirpool;
    }

    /**
     * Get data byte [ ].
     *
     * @return the byte [ ]
     */
    public byte[] getData() {
        return data;
    }

}
