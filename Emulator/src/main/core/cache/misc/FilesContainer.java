package core.cache.misc;

/**
 * A class holding the file containers.
 * @author Dragonkk
 */
public final class FilesContainer extends Container {

    /**
     * The file indexes.
     */
    private int[] filesIndexes;

    /**
     * The files.
     */
    private Container[] files;

    /**
     * Construct a new file container.
     */
    public FilesContainer() {

    }

    /**
     * Set the files.
     *
     * @param containers the files.
     */
    public void setFiles(Container[] containers) {
        this.files = containers;
    }

    /**
     * Get the files.
     *
     * @return the files.
     */
    public Container[] getFiles() {
        return files;
    }

    /**
     * Set the file indexes.
     *
     * @param containersIndexes the file indexes.
     */
    public void setFilesIndexes(int[] containersIndexes) {
        this.filesIndexes = containersIndexes;
    }

    /**
     * Get the file indexes.
     *
     * @return the file indexes.
     */
    public int[] getFilesIndexes() {
        return filesIndexes;
    }
}
