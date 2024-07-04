package core.network.packet;

/**
 * Represents an outgoing packet.
 * @author Emperor
 */
public interface OutgoingPacket<Context> {

	/**
	 * Sends the packet.
	 * @param context The context.
	 */
	public void send(Context context);

}
