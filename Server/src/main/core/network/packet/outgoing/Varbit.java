package core.network.packet.outgoing;

import core.network.packet.IoBuffer;
import core.network.packet.OutgoingPacket;
import core.network.packet.context.VarbitContext;

public class Varbit implements OutgoingPacket<VarbitContext> {
    @Override
    public void send(VarbitContext varbitContext) {
        IoBuffer buffer;
        if (varbitContext.value > 255) {
            buffer = new IoBuffer(84);
            buffer.putLEInt((128 | varbitContext.value) & 255);
        } else {
            buffer = new IoBuffer(37);
            buffer.put((byte) 128 | varbitContext.value);
        }
        buffer.putLEShort(varbitContext.varbitId);
        varbitContext.getPlayer().getSession().write(buffer);
    }
}
