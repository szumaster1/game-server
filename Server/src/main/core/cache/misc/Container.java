package core.cache.misc;

/**
 * Container.
 */
public class Container {

    private int version;

    private int crc;

    private int nameHash;

    private boolean updated;

    /**
     * Instantiates a new Container.
     */
    public Container() {
        nameHash = -1;
        version = -1;
        crc = -1;
    }

    /**
     * Sets version.
     *
     * @param version the version
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * Update version.
     */
    public void updateVersion() {
        version++;
        updated = true;
    }

    /**
     * Gets version.
     *
     * @return the version
     */
    public int getVersion() {
        return version;
    }

    /**
     * Gets next version.
     *
     * @return the next version
     */
    public int getNextVersion() {
        return updated ? version : version + 1;
    }

    /**
     * Sets crc.
     *
     * @param crc the crc
     */
    public void setCrc(int crc) {
        this.crc = crc;
    }

    /**
     * Gets crc.
     *
     * @return the crc
     */
    public int getCrc() {
        return crc;
    }

    /**
     * Sets name hash.
     *
     * @param nameHash the name hash
     */
    public void setNameHash(int nameHash) {
        this.nameHash = nameHash;
    }

    /**
     * Gets name hash.
     *
     * @return the name hash
     */
    public int getNameHash() {
        return nameHash;
    }

    /**
     * Is updated boolean.
     *
     * @return the boolean
     */
    public boolean isUpdated() {
        return updated;
    }
}
