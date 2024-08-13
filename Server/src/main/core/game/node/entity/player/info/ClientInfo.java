package core.game.node.entity.player.info;

/**
 * Client info.
 * This class holds information about the client's display settings.
 */
public final class ClientInfo {

    private int displayMode; // Variable to store the display mode of the client

    private int windowMode; // Variable to store the window mode of the client

    private int screenWidth; // Variable to store the width of the client's screen

    private int screenHeight; // Variable to store the height of the client's screen

    /**
     * Instantiates a new Client info.
     *
     * @param displayMode  the display mode
     * @param windowMode   the window mode
     * @param screenWidth  the screen width
     * @param screenHeight the screen height
     */
    public ClientInfo(int displayMode, int windowMode, int screenWidth, int screenHeight) {
        this.displayMode = displayMode; // Assigning the display mode parameter to the class variable
        this.windowMode = windowMode; // Assigning the window mode parameter to the class variable
        this.screenWidth = screenWidth; // Assigning the screen width parameter to the class variable
        this.screenHeight = screenHeight; // Assigning the screen height parameter to the class variable
    }

    /**
     * Is high detail boolean.
     *
     * @return the boolean
     */
    public boolean isHighDetail() {
        return displayMode > 0; // Returns true if the display mode is greater than 0, indicating high detail
    }

    /**
     * Is resizable boolean.
     *
     * @return the boolean
     */
    public boolean isResizable() {
        return windowMode > 1; // Returns true if the window mode is greater than 1, indicating resizable window
    }

    /**
     * Gets display mode.
     *
     * @return the display mode
     */
    public int getDisplayMode() {
        return displayMode; // Returns the current display mode
    }

    /**
     * Sets display mode.
     *
     * @param displayMode the display mode
     */
    public void setDisplayMode(int displayMode) {
        this.displayMode = displayMode; // Updates the display mode with the provided value
    }

    /**
     * Gets window mode.
     *
     * @return the window mode
     */
    public int getWindowMode() {
        return windowMode; // Returns the current window mode
    }

    /**
     * Sets window mode.
     *
     * @param windowMode the window mode
     */
    public void setWindowMode(int windowMode) {
        this.windowMode = windowMode; // Updates the window mode with the provided value
    }

    /**
     * Gets screen width.
     *
     * @return the screen width
     */
    public int getScreenWidth() {
        return screenWidth; // Returns the current screen width
    }

    /**
     * Sets screen width.
     *
     * @param screenWidth the screen width
     */
    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth; // Updates the screen width with the provided value
    }

    /**
     * Gets screen height.
     *
     * @return the screen height
     */
    public int getScreenHeight() {
        return screenHeight; // Returns the current screen height
    }

    /**
     * Sets screen height.
     *
     * @param screenHeight the screen height
     */
    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight; // Updates the screen height with the provided value
    }
}
