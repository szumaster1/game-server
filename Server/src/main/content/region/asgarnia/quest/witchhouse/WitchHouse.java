package content.region.asgarnia.quest.witchhouse;

import core.api.consts.Vars;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.link.quest.Quest;
import core.game.node.entity.skill.Skills;
import core.plugin.Initializable;
import org.jetbrains.annotations.NotNull;

/**
 * Witch house.
 */
@Initializable
public class WitchHouse extends Quest {

    /**
     * Instantiates a new Witch house.
     */
    public WitchHouse() {
        super("Witch's House", 124, 123, 4, Vars.VARP_QUEST_WTICHS_HOUSE_PROGRESS, 0, 1, 7);
    }

    @Override
    public void drawJournal(@NotNull Player player, int stage) {
        super.drawJournal(player, stage);
        switch (getStage(player)) {
            case 0:
                line(player, "<blue>I can start this quest by speaking to the <red>little boy", 4 + 7);
                line(player, "<blue>standing by the long garden just <red>north of Taverley", 5 + 7);
                line(player, "<blue>I must be able to defeat a <red>level 53 enemy.", 6 + 7);
                break;
            case 10:
                line(player, "<str>A small boy has kicked his ball over the fence into the", 4 + 7);
                line(player, "<str>nearby garden, and I have agreed to retrieve it for him.", 5 + 7);
                line(player, "<blue>I should find a way into the <red>garden<blue> where the <red>ball<blue> is.", 6 + 7);
                break;
            case 100:
                line(player, "<str>A small boy has kicked his ball over the fence into the", 4 + 7);
                line(player, "<str>nearby garden, and I have agreed to retrieve it for him.", 5 + 7);
                line(player, "<str>After puzzling through the strangely elaborate security", 6 + 7);
                line(player, "<str>system, and defeating a very strange monster, I returned", 7 + 7);
                line(player, "<str>the child's ball to him, and he thanked me for my help.", 8 + 7);
                line(player, "<col=FF0000>QUEST COMPLETE!", 10 + 7);
                break;
        }
    }

    @Override
    public void finish(@NotNull Player player) {
        super.finish(player);
        player.getPacketDispatch().sendString("4 Quest Points", 277, 8 + 2);
        player.getPacketDispatch().sendString("6325 Hitpoints XP", 277, 9 + 2);
        player.getSkills().addExperience(Skills.HITPOINTS, 6325);
        player.getInterfaceManager().closeChatbox();
        player.getPacketDispatch().sendItemZoomOnInterface(2407, 240, 277, 3 + 2);
    }

    @NotNull
    @Override
    public Quest newInstance(@NotNull Object object) {
        return this;
    }
}
