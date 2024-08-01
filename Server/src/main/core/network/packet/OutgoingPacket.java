package core.network.packet;

public interface OutgoingPacket<Context> {

	void send(Context context);

}
