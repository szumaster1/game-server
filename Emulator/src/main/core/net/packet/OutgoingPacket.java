package core.net.packet;

/**
 * Represents an outgoing packet.
 *
 * @param <Context> The context type.
 * @author Emperor
 */
public interface OutgoingPacket<Context> {

    /**
     * Sends the packet.
     *
     * @param context The context.
     */
    void send(Context context);

}
