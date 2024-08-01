package core.network.packet.outgoing;

import core.game.node.entity.skill.Skills;
import core.network.packet.IoBuffer;
import core.network.packet.OutgoingPacket;
import core.network.packet.context.SkillContext;

public final class SkillLevel implements OutgoingPacket<SkillContext> {

    @Override
    public void send(SkillContext context) {
        final IoBuffer buffer = new IoBuffer(38);
        buffer.cypherOpcode(context.getPlayer().getSession().getIsaacPair().getOutput());
        Skills skills = context.getPlayer().getSkills();
        if (context.skillId == Skills.PRAYER) {
            buffer.putA((int) Math.ceil(skills.getPrayerPoints()));
        } else if (context.skillId == Skills.HITPOINTS) {
            buffer.putA(skills.getLifepoints());
        } else {
            buffer.putA(skills.getLevel(context.skillId, true));
        }
        buffer.putIntA((int) skills.getExperience(context.skillId)).put(context.skillId);
        context.getPlayer().getDetails().getSession().write(buffer);
    }

}
