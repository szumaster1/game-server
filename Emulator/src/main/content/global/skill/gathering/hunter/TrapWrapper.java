package content.global.skill.gathering.hunter;

import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;
import core.game.node.scenery.Scenery;
import core.game.node.scenery.SceneryBuilder;
import core.game.world.GameWorld;
import core.game.world.update.flag.context.Animation;
import core.game.world.update.flag.context.Graphic;

import java.util.ArrayList;
import java.util.List;

/**
 * Trap wrapper.
 */
public final class TrapWrapper {
    private final List<Item> items = new ArrayList<>(10);
    private final Player player;
    private final Traps type;
    private NetTrapSetting.NetTrap netType;
    private final int originalId;
    private Scenery object;
    private Scenery secondary;
    private TrapHook hook;
    private TrapNode reward;
    private boolean smoked;
    private boolean baited;
    private boolean failed;
    private int busyTicks;
    private int ticks;
    private final HunterManager instance;

    public TrapWrapper(final Player player, Traps type, Scenery object) {
        this.player = player;
        this.type = type;
        this.object = object;
        this.originalId = object.getId();
        this.ticks = GameWorld.getTicks() + (100);
        this.instance = HunterManager.getInstance(player);
        this.object.getAttributes().setAttribute("trap-uid", instance.getUid());
    }

    public boolean cycle() {
        if (isTimeUp() && type.settings.clear(this, 0)) {
            if (!isCaught()) {
                player.sendMessage(type.settings.getTimeUpMessage());
            }
            return true;
        }
        return false;
    }

    public void setObject(final int id) {
        Scenery newObject = object.transform(id);
        SceneryBuilder.remove(object);
        this.object = SceneryBuilder.add(newObject);
        this.object.getAttributes().setAttribute("trap-uid", instance.getUid());
    }

    public void smoke() {
        if (smoked) {
            player.sendMessage("This trap has already been smoked.");
            return;
        }
        if (player.skills.getStaticLevel(Skills.HUNTER) < 39) {
            player.sendMessage("You need a Hunter level of at least 39 to be able to smoke traps.");
            return;
        }
        smoked = true;
        player.lock(4);
        player.visualize(new Animation(5208), new Graphic(931));
        player.sendMessage("You use the smoke from the torch to remove your scent from the trap.");
    }

    public void bait(Item bait) {
        if (baited) {
            player.sendMessage("This trap has already been baited.");
            return;
        }
        if (!type.settings.hasBait(bait)) {
            player.sendMessage("You can't use that on this trap.");
            return;
        }
        baited = true;
        bait = new Item(bait.getId(), 1);
        player.getInventory().remove(new Item(bait.getId(), 1));
    }

    public double getChanceRate() {
        double chance = 0.0;
        if (baited) {
            chance += 1.0;
        }
        if (smoked) {
            chance += 1.0;
        }
        chance += HunterGear.getChanceRate(player);
        return chance;
    }

    public void addItem(Item... items) {
        for (Item item : items) {
            addItem(item);
        }
    }

    public void addItem(Item item) {
        items.add(item);
    }

    private boolean isTimeUp() {
        return ticks < GameWorld.getTicks();
    }

    public Traps getType() {
        return type;
    }

    public Scenery getObject() {
        return object;
    }

    public int getOriginalId() {
        return originalId;
    }

    public Player getPlayer() {
        return player;
    }

    public int getTicks() {
        return ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

    public boolean isSmoked() {
        return smoked;
    }

    public void setSmoked(boolean smoked) {
        this.smoked = smoked;
    }

    public TrapHook getHook() {
        return hook;
    }

    public void setHook(TrapHook hook) {
        this.hook = hook;
    }

    public boolean isBaited() {
        return baited;
    }

    public void setBaited(boolean baited) {
        this.baited = baited;
    }

    public boolean isCaught() {
        return getReward() != null;
    }

    public TrapNode getReward() {
        return reward;
    }

    public void setReward(TrapNode reward) {
        this.reward = reward;
        this.addItem(reward.rewards);
    }

    public boolean isBusy() {
        return getBusyTicks() > GameWorld.getTicks();
    }

    public int getBusyTicks() {
        return busyTicks;
    }

    public void setBusyTicks(int busyTicks) {
        this.busyTicks = GameWorld.getTicks() + busyTicks;
    }

    public List<Item> getItems() {
        return items;
    }

    public Scenery getSecondary() {
        return secondary;
    }

    public void setSecondary(Scenery secondary) {
        this.secondary = secondary;
        this.secondary.getAttributes().setAttribute("trap-uid", player.getName().hashCode());
    }

    public NetTrapSetting.NetTrap getNetType() {
        return netType;
    }

    public void setNetType(NetTrapSetting.NetTrap netType) {
        this.netType = netType;
    }

    public void setObject(Scenery object) {
        this.object = object;
    }

    public boolean isFailed() {
        return failed;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
    }

}
