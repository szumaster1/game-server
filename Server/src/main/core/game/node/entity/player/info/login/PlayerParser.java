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
        PlayerSaveParser parser = new PlayerSaveParser(player);

        try {
            parser.parse();
            return parser;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Save.
     *
     * @param player the player
     */
    public static void save(Player player) {
        player.setAttribute("flagged-for-save", true);
    }

    /**
     * Save immediately.
     *
     * @param player the player
     */
    public static void saveImmediately(Player player) {
        new PlayerSaver(player).save();
    }

    /**
     * Make from template.
     *
     * @param player the player
     */
    public static void makeFromTemplate(Player player) {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(ServerConstants.PLAYER_SAVE_PATH + "template/template.json");
            os = new FileOutputStream(ServerConstants.PLAYER_SAVE_PATH + player.getName() + ".json");
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert is != null;
                is.close();
                assert os != null;
                os.close();
            } catch (Exception f) {
                log(PlayerParser.class, Log.ERR, "Unable to close file copiers in PlayerParser.java line 216.");
                f.printStackTrace();
            }
        }
    }
}
