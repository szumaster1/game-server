package core.network.registry;

import core.Configuration;
import core.auth.UserAccountInfo;
import core.cache.misc.buffer.ByteBufferUtils;
import core.game.system.task.Pulse;
import core.game.world.GameWorld;
import core.network.Constants;
import core.network.IoSession;
import core.network.packet.incoming.Login;
import core.tools.Log;

import java.nio.ByteBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static core.api.ContentAPIKt.log;

/**
 * Handles the registry of new accounts.
 * @author Vexia
 */
public class AccountRegister {

    /**
     * The pattern compiler.
     */
    private static final Pattern PATTERN = Pattern.compile("[a-z0-9_]{1,12}");

    /**
     * Reads the incoming opcode of an account register.
     *
     * @param session the session
     * @param opcode  the opcode
     * @param buffer  the buffer
     */
    public static void read(final IoSession session, int opcode, ByteBuffer buffer) {
        // Variables for storing registration data
        int day, month, year, country;
        UserAccountInfo info = UserAccountInfo.createDefault();

        switch (opcode) {
            case 147://details
                // Read day, month, year, and country from the buffer
                day = buffer.get();
                month = buffer.get();
                year = buffer.getShort();
                country = buffer.getShort();
                response(session, RegistryResponse.SUCCESS);
                break;
            case 186://username
                // Read the username from the buffer and perform necessary modifications
                final String username = ByteBufferUtils.getString(buffer).replace(" ", "_").toLowerCase().replace("|", "");
                info.setUsername(username);
                // Check if the username length is valid
                if (username.length() <= 0 || username.length() > 12) {
                    response(session, RegistryResponse.INVALID_USERNAME);
                    break;
                }
                // Check if the username is invalid
                if (invalidUsername(username)) {
                    response(session, RegistryResponse.INVALID_USERNAME);
                    break;
                }
                // Check if the account can be created with the provided username
                if (!GameWorld.getAuthenticator().canCreateAccountWith(info)) {
                    response(session, RegistryResponse.NOT_AVAILBLE_USER);
                    return;
                }
                response(session, RegistryResponse.SUCCESS);
                break;
            case 36://Register details
                // Read the RSA-encrypted buffer and decrypt it
                buffer.get(); //Useless size being written that is already written in the RSA block
                buffer = Login.decryptRSABuffer(buffer, Configuration.EXPONENT, Configuration.MODULUS);
                // Check if the decryption was successful
                if (buffer.get() != 10) { //RSA header (aka did this decrypt properly)
                    log(AccountRegister.class, Log.ERR, "Decryption failed during registration :(");
                    response(session, RegistryResponse.CANNOT_CREATE);
                    break;
                }
                buffer.getShort(); // random data
                int revision = buffer.getShort();//revision?
                // Check if the revision matches the expected value
                if (revision != Constants.REVISION) {
                    response(session, RegistryResponse.CANNOT_CREATE);
                    break;
                }
                // Read the username and password from the buffer and perform necessary modifications
                final String name = ByteBufferUtils.getString(buffer).replace(" ", "_").toLowerCase().replace("|", "");
                buffer.getInt();
                String password = ByteBufferUtils.getString(buffer);
                info.setUsername(name);
                info.setPassword(password);
                // Check if the password length is valid
                if (password.length() < 5 || password.length() > 20) {
                    response(session, RegistryResponse.INVALID_PASS_LENGTH);
                    break;
                }
                // Check if the password is similar to the username
                if (password.equals(name)) {
                    response(session, RegistryResponse.PASS_SIMILAR_TO_USER);
                    break;
                }
                // Check if the username is invalid
                if (invalidUsername(name)) {
                    response(session, RegistryResponse.INVALID_USERNAME);
                    break;
                }
                buffer.getInt();
                buffer.getShort();
                day = buffer.get();
                month = buffer.get();
                buffer.getInt();
                year = buffer.getShort();
                country = buffer.getShort();
                buffer.getInt();
                // Check if the account can be created with the provided information
                if (!GameWorld.getAuthenticator().canCreateAccountWith(info)) {
                    response(session, RegistryResponse.CANNOT_CREATE);
                    return;
                }
                // Create the account and send a success response
                GameWorld.getAuthenticator().createAccountWith(info);
                GameWorld.getPulser().submit(new Pulse() {
                    @Override
                    public boolean pulse() {
                        response(session, RegistryResponse.SUCCESS);
                        return true;
                    }
                });
                break;
            default:
                log(AccountRegister.class, Log.ERR, "Unhandled account registry opcode = " + opcode);
                break;
        }
    }

    /**
     * Send a response to the client.
     *
     * @param session  the session
     * @param response the response
     */
    private static void response(IoSession session, RegistryResponse response) {
        ByteBuffer buf = ByteBuffer.allocate(100);
        buf.put((byte) response.id);
        session.queue(buf.flip());
    }

    /**
     * Check if a username is invalid.
     *
     * @param username the username
     * @return true if the username is invalid, false otherwise
     */
    public static boolean invalidUsername(final String username) {
        Matcher matcher = PATTERN.matcher(username);
        return !matcher.matches();
    }
}
