package core.cache;

import core.cache.misc.ContainersInformation;
import core.tools.StringUtils;

import java.nio.ByteBuffer;

/**
 * Cache file manager.
 */
public final class CacheFileManager {

    private CacheFile cacheFile;

    private ContainersInformation information;

    private boolean discardFilesData;

    private byte[][][] filesData;

    /**
     * Instantiates a new Cache file manager.
     *
     * @param cacheFile        the cache file
     * @param discardFilesData the discard files data
     */
    public CacheFileManager(CacheFile cacheFile, boolean discardFilesData) {
        this.cacheFile = cacheFile;
        this.discardFilesData = discardFilesData;
        byte[] informContainerPackedData = Cache.getReferenceFile().getContainerData(cacheFile.getIndexFileId());
        if (informContainerPackedData == null) {
            return;
        }
        information = new ContainersInformation(informContainerPackedData);
        resetFilesData();
    }

    /**
     * Gets cache file.
     *
     * @return the cache file
     */
    public CacheFile getCacheFile() {
        return cacheFile;
    }

    /**
     * Gets containers size.
     *
     * @return the containers size
     */
    public int getContainersSize() {
        return information.getContainers().length;
    }

    /**
     * Gets files size.
     *
     * @param containerId the container id
     * @return the files size
     */
    public int getFilesSize(int containerId) {
        if (!validContainer(containerId)) {
            return -1;
        }
        return information.getContainers()[containerId].getFiles().length;
    }

    /**
     * Reset files data.
     */
    public void resetFilesData() {
        filesData = new byte[information.getContainers().length][][];
    }

    /**
     * Valid file boolean.
     *
     * @param containerId the container id
     * @param fileId      the file id
     * @return the boolean
     */
    public boolean validFile(int containerId, int fileId) {
        if (!validContainer(containerId)) {
            return false;
        }
        return fileId >= 0 && information.getContainers()[containerId] != null && information.getContainers()[containerId].getFiles().length > fileId;

    }

    /**
     * Valid container boolean.
     *
     * @param containerId the container id
     * @return the boolean
     */
    public boolean validContainer(int containerId) {
        return containerId >= 0 && information.getContainers().length > containerId;
    }

    /**
     * Get file ids int [ ].
     *
     * @param containerId the container id
     * @return the int [ ]
     */
    public int[] getFileIds(int containerId) {
        if (!validContainer(containerId)) {
            return null;
        }
        return information.getContainers()[containerId].getFilesIndexes();
    }

    /**
     * Gets archive id.
     *
     * @param name the name
     * @return the archive id
     */
    public int getArchiveId(String name) {
        if (name == null) {
            return -1;
        }
        int hash = StringUtils.getNameHash(name);
        for (int containerIndex = 0; containerIndex < information.getContainersIndexes().length; containerIndex++) {
            if (information.getContainers()[information.getContainersIndexes()[containerIndex]].getNameHash() == hash) {
                return information.getContainersIndexes()[containerIndex];
            }
        }
        return -1;
    }

    /**
     * Get file data byte [ ].
     *
     * @param containerId the container id
     * @param fileId      the file id
     * @return the byte [ ]
     */
    public byte[] getFileData(int containerId, int fileId) {
        return getFileData(containerId, fileId, null);
    }

    /**
     * Load files data boolean.
     *
     * @param archiveId the archive id
     * @param keys      the keys
     * @return the boolean
     */
    public boolean loadFilesData(int archiveId, int[] keys) {
        byte[] data = cacheFile.getContainerUnpackedData(archiveId, keys);
        if (data == null) {
            return false;
        }
        if (filesData[archiveId] == null) {
            if (information.getContainers()[archiveId] == null) {
                return false; // container inform doesnt exist anymore
            }
            filesData[archiveId] = new byte[information.getContainers()[archiveId].getFiles().length][];
        }
        if (information.getContainers()[archiveId].getFilesIndexes().length == 1) {
            int fileId = information.getContainers()[archiveId].getFilesIndexes()[0];
            filesData[archiveId][fileId] = data;
        } else {
            int readPosition = data.length;
            int amtOfLoops = data[--readPosition] & 0xff;
            readPosition -= amtOfLoops * (information.getContainers()[archiveId].getFilesIndexes().length * 4);
            ByteBuffer buffer = ByteBuffer.wrap(data);
            int[] filesSize = new int[information.getContainers()[archiveId].getFilesIndexes().length];
            buffer.position(readPosition);
            for (int loop = 0; loop < amtOfLoops; loop++) {
                int offset = 0;
                for (int fileIndex = 0; fileIndex < information.getContainers()[archiveId].getFilesIndexes().length; fileIndex++) {
                    filesSize[fileIndex] += offset += buffer.getInt();
                }
            }
            byte[][] filesBufferData = new byte[information.getContainers()[archiveId].getFilesIndexes().length][];
            for (int fileIndex = 0; fileIndex < information.getContainers()[archiveId].getFilesIndexes().length; fileIndex++) {
                filesBufferData[fileIndex] = new byte[filesSize[fileIndex]];
                filesSize[fileIndex] = 0;
            }
            buffer.position(readPosition);
            int sourceOffset = 0;
            for (int loop = 0; loop < amtOfLoops; loop++) {
                int dataRead = 0;
                for (int fileIndex = 0; fileIndex < information.getContainers()[archiveId].getFilesIndexes().length; fileIndex++) {
                    dataRead += buffer.getInt();
                    System.arraycopy(data, sourceOffset, filesBufferData[fileIndex], filesSize[fileIndex], dataRead);
                    sourceOffset += dataRead;
                    filesSize[fileIndex] += dataRead;
                }
            }
            for (int fileIndex = 0; fileIndex < information.getContainers()[archiveId].getFilesIndexes().length; fileIndex++) {
                filesData[archiveId][information.getContainers()[archiveId].getFilesIndexes()[fileIndex]] = filesBufferData[fileIndex];
            }
        }
        return true;

    }

    /**
     * Get file data byte [ ].
     *
     * @param containerId the container id
     * @param fileId      the file id
     * @param xteaKeys    the xtea keys
     * @return the byte [ ]
     */
    public byte[] getFileData(int containerId, int fileId, int[] xteaKeys) {
        if (!validFile(containerId, fileId)) {
            return null;
        }
        if (filesData[containerId] == null || filesData[containerId][fileId] == null) {
            if (!loadFilesData(containerId, xteaKeys)) {
                return null;
            }
        }
        byte[] data = filesData[containerId][fileId];
        if (discardFilesData) {
            if (filesData[containerId].length == 1) {
                filesData[containerId] = null;
            } else {
                filesData[containerId][fileId] = null;
            }
        }
        return data;
    }

    /**
     * Gets information.
     *
     * @return the information
     */
    public ContainersInformation getInformation() {
        return information;
    }

    /**
     * Is discard files data boolean.
     *
     * @return the boolean
     */
    public boolean isDiscardFilesData() {
        return discardFilesData;
    }

    /**
     * Sets discard files data.
     *
     * @param discardFilesData the discard files data
     */
    public void setDiscardFilesData(boolean discardFilesData) {
        this.discardFilesData = discardFilesData;
    }

    /**
     * Get files data byte [ ] [ ] [ ].
     *
     * @return the byte [ ] [ ] [ ]
     */
    public byte[][][] getFilesData() {
        return filesData;
    }

    /**
     * Sets files data.
     *
     * @param filesData the files data
     */
    public void setFilesData(byte[][][] filesData) {
        this.filesData = filesData;
    }

    /**
     * Sets cache file.
     *
     * @param cacheFile the cache file
     */
    public void setCacheFile(CacheFile cacheFile) {
        this.cacheFile = cacheFile;
    }

    /**
     * Sets information.
     *
     * @param information the information
     */
    public void setInformation(ContainersInformation information) {
        this.information = information;
    }
}
