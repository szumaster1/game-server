package core.net.packet.outgoing;

import core.net.packet.IoBuffer;
import core.net.packet.OutgoingPacket;
import core.net.packet.context.VarbitContext;

/**
 * Varbit.
 */
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
