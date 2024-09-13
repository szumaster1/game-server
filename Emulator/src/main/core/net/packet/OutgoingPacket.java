package core.net.packet;

/**
 * Represents an outgoing packet.
 * @author Emperor
 * @param <Context> The context type.
 */
public interface OutgoingPacket<Context> {

    /**
     * Sends the packet.
     *
     * @param context The context.
     */
    void send(Context context);

}
