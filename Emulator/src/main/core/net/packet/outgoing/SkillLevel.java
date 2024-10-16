package core.net.packet.outgoing;

import core.game.node.entity.skill.Skills;
import core.net.packet.IoBuffer;
import core.net.packet.OutgoingPacket;
import core.net.packet.context.SkillContext;

/**
 * Handles the update skill outgoing packet.
 * @author Emperor
 */
public final class SkillLevel implements OutgoingPacket<SkillContext> {

    @Override
    public void send(SkillContext context) {
        final IoBuffer buffer = new IoBuffer(38);
        buffer.cypherOpcode(context.getPlayer().getSession().getIsaacPair().output);
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
