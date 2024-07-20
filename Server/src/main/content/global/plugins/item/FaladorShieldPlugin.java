package content.global.plugins.item;

import content.data.consumables.effects.PrayerEffect;
import core.cache.def.impl.ItemDefinition;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.Entity;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.game.system.task.Pulse;
import core.game.world.GameWorld;
import core.game.world.update.flag.context.Animation;
import core.game.world.update.flag.context.Graphic;
import core.plugin.Initializable;
import core.plugin.Plugin;

import java.util.concurrent.TimeUnit;

import static core.api.ContentAPIKt.*;

/**
 * The Falador shield plugin.
 */
@Initializable
public class FaladorShieldPlugin extends OptionHandler {
    /**
     * The constant ANIM_EMOTE.
     */
    final static int ANIM_EMOTE = 11012;
    /**
     * The Gfx emote.
     */
    final static int[] GFX_EMOTE = {1966, 1965, 1965};
    /**
     * The Gfx prayer restore.
     */
    final static int[] GFX_PRAYER_RESTORE = {1962, 1963, 1964};

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        ItemDefinition.forId(14577).getHandlers().put("option:operate", this);
        ItemDefinition.forId(14578).getHandlers().put("option:operate", this);
        ItemDefinition.forId(14579).getHandlers().put("option:operate", this);

        ItemDefinition.forId(14577).getHandlers().put("option:prayer-restore", this);
        ItemDefinition.forId(14578).getHandlers().put("option:prayer-restore", this);
        ItemDefinition.forId(14579).getHandlers().put("option:prayer-restore", this);
        return this;
    }

    @Override
    public boolean handle(Player player, Node node, String option) {
        final Item item = (Item) node;
        int level = getLevel(item.getId());
        switch (option) {
            case "prayer-restore":
                Long attrTime = player.getAttribute("diary:falador:shield-restore-time");
                setTitle(player, 2);
                sendDialogueOptions(player, "Are you sure you wish to recharge?", "Yes, recharge my Prayer points.", "No, I've changed my mind.");
                player.getDialogueInterpreter().addAction((player1, buttonId) -> {
                    if (buttonId == 2) {
                        if (attrTime != null && attrTime > System.currentTimeMillis()) {
                            player1.sendMessage("You have no charges left today.");
                        } else {
                            final PrayerEffect effect = new PrayerEffect(0, level == 0 ? 0.25 : level == 1 ? 0.5 : 1.0);
                            player1.graphics(new Graphic(GFX_PRAYER_RESTORE[level]));
                            setAttribute(player1, "/save:diary:falador:shield-restore-time", System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));
                            player1.sendMessage("You restore " + (level < 2 ? "some" : "your") + " prayer points.");
                            effect.activate(player1);
                        }
                    }
                });
                return true;
            case "operate":
                GameWorld.getPulser().submit(getPulse(player, level));
                break;
        }
        return true;
    }

    private int getLevel(int itemId) {
        switch (itemId) {
            case 14577:
                return 0;
            case 14578:
                return 1;
            case 14579:
                return 2;
        }
        return -1;
    }

    private Pulse getPulse(final Entity entity, final int level) {
        return new Pulse(1) {
            int delay = 0;

            @Override
            public boolean pulse() {
                if (delay == 0) {
                    entity.lock();
                    entity.animate(new Animation(ANIM_EMOTE));
                    entity.graphics(new Graphic(GFX_EMOTE[level]));
                } else if (delay == 3) {
                    entity.getAnimator().forceAnimation(Animation.RESET);
                    entity.unlock();
                    return true;
                }
                delay++;
                return false;
            }
        };
    }

}
