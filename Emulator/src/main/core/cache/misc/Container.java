package core.cache.misc;

/**
 * A container.
 * @author Dragonkk
 */
public class Container {

    /**
     * The version.
     */
    private int version;

    /**
     * The CRC.
     */
    private int crc;

    /**
     * The name hash.
     */
    private int nameHash;

    /**
     * If updated.
     */
    private boolean updated;

    /**
     * Construct a new container.
     */
    public Container() {
        nameHash = -1;
        version = -1;
        crc = -1;
    }

    /**
     * Set the version.
     *
     * @param version the version
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * Update the version.
     */
    public void updateVersion() {
        version++;
        updated = true;
    }

    /**
     * Get the version.
     *
     * @return the version.
     */
    public int getVersion() {
        return version;
    }

    /**
     * Get the next version.
     *
     * @return the next version.
     */
    public int getNextVersion() {
        return updated ? version : version + 1;
    }

    /**
     * Set the CRC.
     *
     * @param crc the CRC.
     */
    public void setCrc(int crc) {
        this.crc = crc;
    }

    /**
     * Get the CRC.
     *
     * @return the CRC.
     */
    public int getCrc() {
        return crc;
    }

    /**
     * Set the name hash.
     *
     * @param nameHash the name hash.
     */
    public void setNameHash(int nameHash) {
        this.nameHash = nameHash;
    }

    /**
     * Get the name hash.
     *
     * @return the name hash.
     */
    public int getNameHash() {
        return nameHash;
    }

    /**
     * If is updated.
     *
     * @return If is updated.
     */
    public boolean isUpdated() {
        return updated;
    }
}
