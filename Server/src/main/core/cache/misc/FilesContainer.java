package core.cache.misc;

/**
 * Files container.
 */
public final class FilesContainer extends Container {

    private int[] filesIndexes;

    private Container[] files;

    /**
     * Instantiates a new Files container.
     */
    public FilesContainer() {

    }

    /**
     * Sets files.
     *
     * @param containers the containers
     */
    public void setFiles(Container[] containers) {
        this.files = containers;
    }

    /**
     * Get files container [ ].
     *
     * @return the container [ ]
     */
    public Container[] getFiles() {
        return files;
    }

    /**
     * Sets files indexes.
     *
     * @param containersIndexes the containers indexes
     */
    public void setFilesIndexes(int[] containersIndexes) {
        this.filesIndexes = containersIndexes;
    }

    /**
     * Get files indexes int [ ].
     *
     * @return the int [ ]
     */
    public int[] getFilesIndexes() {
        return filesIndexes;
    }
}
