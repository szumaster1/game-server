package content.region.kandarin.baxtorian.handlers.barbariantraining.firemaking;

import content.region.kandarin.baxtorian.handlers.barbariantraining.BarbarianTraining;
import content.global.skill.firemaking.logs.Log;
import core.api.Container;
import org.rs.consts.Items;
import core.game.event.LitFireEvent;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.SkillPulse;
import core.game.node.entity.skill.Skills;
import core.game.node.item.GroundItem;
import core.game.node.item.GroundItemManager;
import core.game.node.item.Item;
import core.game.node.scenery.Scenery;
import core.game.node.scenery.SceneryBuilder;
import core.game.world.GameWorld;
import core.game.world.map.RegionManager;
import core.game.world.update.flag.context.Animation;
import core.tools.RandomFunction;

import static core.api.ContentAPIKt.*;

/**
 * Represents the pulse used to light a log using
 * barbarian training method.
 */
public final class BarbFiremakingPulse extends SkillPulse<Item> {

    /**
     * Represents the animation to use.
     */
    private final Animation ANIMATION = new Animation(tool.animation);

    /**
     * Represents the tinderbox item.
     */
    private static final Item TINDERBOX = new Item(Items.TINDERBOX_590);


    /**
     * Represents the log being burned.
     */
    private final Log fire;

    /**
     * Represents the ground item.
     */
    private GroundItem groundItem;

    /**
     * Represents the ticks.
     */
    private int ticks;

    /**
     * Constructs a new {@code FireMaking}.
     *
     * @param player     the player.
     * @param node       the node.
     * @param groundItem the ground item if not null.
     */
    public BarbFiremakingPulse(Player player, Item node, GroundItem groundItem) {
        super(player, node);
        this.fire = Log.forId(node.getId());
        if (groundItem == null) {
            this.groundItem = new GroundItem(node, player.getLocation(), player);
            player.setAttribute("remove-log", true);
        } else {
            this.groundItem = groundItem;
            player.removeAttribute("remove-log");
        }
    }

    @Override
    public boolean checkRequirements() {
        if (fire == null) {
            return false;
        }
        if (player.getIronmanManager().isIronman() && !groundItem.droppedBy(player)) {
            sendMessage(player,"You can't do that as an Ironman.");
            return false;
        }
        if (RegionManager.getObject(player.getLocation()) != null || player.getZoneMonitor().isInZone("bank")) {
            sendMessage(player,"You can't light a fire here.");
            return false;
        }
        if (!inInventory(player, tool.id, 1)) {
            sendMessage(player, "You do not have the required items to light this.");
            return false;
        }
        if (getStatLevel(player, Skills.FIREMAKING) < fire.getBarbarianLevel()) {
            sendMessage(player, "You need a firemaking level of " + fire.getBarbarianLevel() + " to light this log.");
            return false;
        }
        if (player.getAttribute("remove-log", false)) {
            player.removeAttribute("remove-log");
            if (inInventory(player, node.getId(), 1)) {
                replaceSlot(player, node.getSlot(), new Item(node.getId(), (node.getAmount() - 1)), node, Container.INVENTORY);
                GroundItemManager.create(groundItem);
            }
        }
        return true;
    }

    @Override
    public void animate() {
    }

    @Override
    public boolean reward() {
        if (getLastFire() >= GameWorld.getTicks()) {
            createFire();
            return true;
        }
        if (ticks == 0) {
            player.animate(ANIMATION);
        }
        if (++ticks % 3 != 0) {
            return false;
        }
        if (ticks % 12 == 0) {
            player.animate(ANIMATION);
        }
        if (!success()) {
            return false;
        }
        createFire();
        return true;
    }

    /**
     * Creates the fire.
     */
    public void createFire() {
        if (!groundItem.isActive()) {
            return;
        }
        // Scenery originalOnSpot = RegionManager.getObject(player.getLocation());
        final Scenery object = new Scenery(fire.getFireId(), player.getLocation());
        SceneryBuilder.add(object, fire.getLife(), getAsh(player, fire, object));
        GroundItemManager.destroy(groundItem);
        player.moveStep();
        player.faceLocation(object.getFaceLocation(player.getLocation()));
        player.getSkills().addExperience(Skills.FIREMAKING, fire.getXp());

        int playerRegion = player.getViewport().getRegion().getId();

        setLastFire();
        player.dispatch(new LitFireEvent(fire.getLogId()));

        // Check if the player is in the firemaking tutorial.
        if (getAttribute(player, BarbarianTraining.INSTANCE.getFM_BASE(), false)) {
            removeAttribute(player, BarbarianTraining.INSTANCE.getFM_BASE());
            setAttribute(player, "/save:barbtraining:firemaking", true);
            sendDialogueLines(player, "You feel you have learned more of barbarian ways. Otto might wish", "to talk to you more.");
        }
    }

    @Override
    public void message(int type) {
        String name = node.getId() == Items.JOGRE_BONES_3125 ? "bones" : "logs";
        switch (type) {
            case 0:
                sendMessage(player,"You attempt to light the " + name + "..");
                break;
            case 1:
                sendMessage(player,"The fire catches and the " + name + " begin to burn.");
                break;
        }
    }

    /**
     * Gets the last firemake.
     *
     * @return the tick.
     */
    public int getLastFire() {
        return player.getAttribute("last-firemake", 0);
    }

    /**
     * Sets the last fire.
     */
    public void setLastFire() {
        player.setAttribute("last-firemake", GameWorld.getTicks() + 2);
    }

    /**
     * Gets the ground item ash.
     *
     * @param player the player
     * @param fire   the fire
     * @param object the object.
     * @return {@code GroundItem} the itemm.
     */
    public static GroundItem getAsh(final Player player, Log fire, final Scenery object) {
        final GroundItem ash = new GroundItem(new Item(Items.ASHES_592), object.getLocation(), player);
        ash.setDecayTime(fire.getLife() + 200);
        return ash;
    }

    /**
     * Checks if the player gets rewarded.
     *
     * @return {@code True} if so.
     */
    private boolean success() {
        int level = 1 + player.getSkills().getLevel(Skills.FIREMAKING);
        double req = fire.getDefaultLevel();
        double successChance = Math.ceil((level * 50 - req * 15) / req / 3 * 4);
        int roll = RandomFunction.random(99);
        if (successChance >= roll) {
            return true;
        }
        return false;
    }
}
