package core.game.node.entity.player.info.login;

import core.ServerConstants;
import core.game.node.entity.player.Player;
import core.tools.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static core.api.ContentAPIKt.log;

/**
 * Player parser.
 */
public final class PlayerParser {
    /**
     * Parse player save parser.
     *
     * @param player the player
     * @return the player save parser
     */
    public static PlayerSaveParser parse(Player player) {
        // Create a new instance of PlayerSaveParser for the given player
        PlayerSaveParser parser = new PlayerSaveParser(player);

        try {
            // Attempt to parse the player's data
            parser.parse();
            return parser; // Return the successfully parsed player save parser
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace for debugging purposes
            return null; // Return null if parsing fails
        }
    }

    /**
     * Save.
     *
     * @param player the player
     */
    public static void save(Player player) {
        // Set an attribute to indicate that the player is flagged for saving
        player.setAttribute("flagged-for-save", true);
    }

    /**
     * Save immediately.
     *
     * @param player the player
     */
    public static void saveImmediately(Player player) {
        // Create a new PlayerSaver instance and save the player's data immediately
        new PlayerSaver(player).save();
    }

    /**
     * Make from template.
     *
     * @param player the player
     */
    public static void makeFromTemplate(Player player) {
        InputStream is = null; // Declare InputStream for reading the template
        OutputStream os = null; // Declare OutputStream for writing the player's save file
        try {
            // Initialize InputStream to read from the template JSON file
            is = new FileInputStream(ServerConstants.PLAYER_SAVE_PATH + "template/template.json");
            // Initialize OutputStream to write to the player's save file
            os = new FileOutputStream(ServerConstants.PLAYER_SAVE_PATH + player.getName() + ".json");
            byte[] buffer = new byte[1024]; // Buffer for reading data
            int length; // Variable to store the length of read data
            // Read from the template and write to the player's save file
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length); // Write the read data to the output stream
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace if an exception occurs
        } finally {
            try {
                assert is != null; // Ensure InputStream is not null before closing
                is.close(); // Close the InputStream
                assert os != null; // Ensure OutputStream is not null before closing
                os.close(); // Close the OutputStream
            } catch (Exception f) {
                // Log an error if unable to close the streams
                log(PlayerParser.class, Log.ERR, "Unable to close file copiers in PlayerParser.java line 216.");
                f.printStackTrace(); // Print the stack trace for debugging
            }
        }
    }
}