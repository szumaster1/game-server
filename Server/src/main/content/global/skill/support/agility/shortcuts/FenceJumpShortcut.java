package content.global.skill.support.agility.shortcuts;

import content.global.skill.support.agility.AgilityShortcut;
import cfg.consts.Animations;
import core.game.node.Node;
import core.game.node.entity.impl.ForceMovement;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.link.diary.DiaryType;
import core.game.node.scenery.Scenery;
import core.game.system.task.Pulse;
import core.game.world.GameWorld;
import core.game.world.map.Location;
import core.game.world.update.flag.context.Animation;
import core.plugin.Initializable;

/**
 * Represents the Fence jump shortcut interaction.
 */
@Initializable
public class FenceJumpShortcut extends AgilityShortcut {

    private static final Animation RUNNING_ANIM = new Animation(Animations.RUNNING_OSRS_STYLE_1995);
    private static final Animation JUMP_ANIM = new Animation(Animations.JUMP_OBSTACLE_WEREWOLF_AGILITY_1603);
    private static final Location[] LOCATIONS = new Location[]{new Location(3240, 3331, 0), new Location(3240, 3338, 0), Location.create(3240, 3334, 0), Location.create(3240, 3335, 0)};

    /**
     * Instantiates a new Fence jump shortcut.
     */
    public FenceJumpShortcut() {
        super(new int[]{9300}, 13, 0.0, "jump-over");
    }

    @Override
    public void run(final Player player, Scenery object, String option, boolean failed) {
        player.faceLocation(object.getLocation());
        GameWorld.getPulser().submit(new Pulse(2, player) {
            @Override
            public boolean pulse() {
                player.animate(JUMP_ANIM);
                player.getAchievementDiaryManager().finishTask(player, DiaryType.VARROCK, 0, 5);
                return true;
            }
        });
        ForceMovement.run(player, player.getLocation().getY() >= 3335 ? LOCATIONS[1] : LOCATIONS[0], player.getLocation().getY() >= 3335 ? LOCATIONS[2] : LOCATIONS[3], RUNNING_ANIM, 18);
    }

    @Override
    public Location getDestination(Node node, Node n) {
        Player player = node.asPlayer();
        return player.getLocation().getY() >= 3335 ? LOCATIONS[1] : LOCATIONS[0];
    }

}
