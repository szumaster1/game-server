package core.network.packet.outgoing;

import core.network.packet.IoBuffer;
import core.network.packet.OutgoingPacket;
import core.network.packet.context.MusicContext;

public class MusicPacket implements OutgoingPacket<MusicContext> {

    @Override
    public void send(MusicContext context) {
        IoBuffer buffer = null;
        if (context.isSecondary()) {
            buffer = new IoBuffer(208);
            buffer.putTri(255);
            buffer.putLEShort(context.getMusicId());
        } else {
            buffer = new IoBuffer(4);
            buffer.putLEShortA(context.getMusicId());
        }
        buffer.cypherOpcode(context.getPlayer().getSession().getIsaacPair().getOutput());
        context.getPlayer().getDetails().getSession().write(buffer);
    }

}
