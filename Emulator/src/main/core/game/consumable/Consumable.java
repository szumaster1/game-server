package core.game.consumable;

import content.data.consumables.Consumables;
import core.api.Container;
import static core.api.ContentAPIKt.*;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.game.world.update.flag.context.Animation;

/**
 * Represents any item that has a consumption option such as 'Eat' or 'Drink'.
 */
public abstract class Consumable {

    /**
     * Represents the item IDs of all the variants of a consumable where the last one is often the empty container, if it has any.
     */
    protected final int[] ids;

    /**
     * Represents the effect to apply on the player once the item is consumed.
     */
    protected final ConsumableEffect effect;

    /**
     * Represents the messages to send to the player when it consumes the item.
     */
    protected final String[] messages;

    /**
     * Represents the animation that the player will execute when consuming the item.
     */
    protected Animation animation = null;

    /**
     * Constructor to initialize the consumable with item IDs, effect, and messages.
     *
     * @param ids      the IDs of the consumable items.
     * @param effect   the effect that will be applied upon consumption.
     * @param messages the messages to be sent to the player.
     */
    public Consumable(final int[] ids, final ConsumableEffect effect, final String... messages) {
        this.ids = ids;
        this.effect = effect;
        this.messages = messages;
    }

    /**
     * Constructor to initialize the consumable with item IDs, effect, animation, and messages.
     *
     * @param ids       the IDs of the consumable items.
     * @param effect    the effect that will be applied upon consumption.
     * @param animation the animation to be played during consumption.
     * @param messages  the messages to be sent to the player.
     */
    public Consumable(final int[] ids, final ConsumableEffect effect, final Animation animation, final String... messages) {
        this.ids = ids;
        this.effect = effect;
        this.animation = animation;
        this.messages = messages;
    }

    /**
     * Method to handle the consumption of an item by a player.
     *
     * @param item   the item being consumed.
     * @param player the player consuming the item.
     */
    public void consume(final Item item, final Player player) {
        executeConsumptionActions(player);
        handleItemChangesOnConsumption(item, player);

        final int initialLifePoints = player.getSkills().getLifepoints();
        Consumables.getConsumableById(item.getId()).consumable.effect.activate(player);
        sendMessages(player, initialLifePoints, item, messages);
    }

    /**
     * Method to handle item changes upon consumption.
     *
     * @param item   the item being consumed.
     * @param player the player consuming the item.
     */
    public void handleItemChangesOnConsumption(final Item item, final Player player) {
        final int nextItemId = getNextItemId(item.getId());

        // STACKABLE + NON-RETURN
        if (ids.length == 1) {
            replaceSlot(player, item.getSlot(), new Item(item.getId(), (item.getAmount() - 1)), item, Container.INVENTORY);
        } else {
            // ITEM HAS RETURN
            int spiderOnStickId = 6297;
            // If return item is stackable (only implemented for spider on stick), returns item to stack instead of slot
            if(item.getId() == spiderOnStickId && inInventory(player,spiderOnStickId,1)) {
                if (removeItem(player, item, Container.INVENTORY)) {
                    addItem(player, nextItemId, 1, Container.INVENTORY);
                }
            } else {
                replaceSlot(player, item.getSlot(), new Item(nextItemId, 1), item, Container.INVENTORY);
            }
        }
    }

    /**
     * Method to send messages to the player after consumption.
     *
     * @param player            the player consuming the item.
     * @param initialLifePoints the player's initial life points before consumption.
     * @param item              the item being consumed.
     * @param messages          the messages to be sent to the player.
     */
    protected void sendMessages(final Player player, final int initialLifePoints, final Item item, String[] messages) {
        if (messages.length == 0) {
            sendDefaultMessages(player, item);
            sendHealingMessage(player, initialLifePoints);
        } else {
            sendCustomMessages(player, messages);
        }
    }

    /**
     * Method to send a healing message to the player if applicable.
     *
     * @param player            the player consuming the item.
     * @param initialLifePoints the player's initial life points before consumption.
     */
    protected void sendHealingMessage(final Player player, final int initialLifePoints) {
        if (player.getSkills().getLifepoints() > initialLifePoints) {
            player.getPacketDispatch().sendMessage("It heals some health.");
        }
    }

    /**
     * Method to send custom messages to the player.
     *
     * @param player   the player consuming the item.
     * @param messages the custom messages to be sent to the player.
     */
    protected void sendCustomMessages(final Player player, final String[] messages) {
        for (String message : messages) {
            player.getPacketDispatch().sendMessage(message);
        }
    }

    /**
     * Abstract method to send default messages to the player.
     *
     * @param player the player consuming the item.
     * @param item   the item being consumed.
     */
    protected abstract void sendDefaultMessages(final Player player, final Item item);

    /**
     * Abstract method to execute actions upon consumption.
     *
     * @param player the player consuming the item.
     */
    protected abstract void executeConsumptionActions(Player player);

    /**
     * Method to get the ID of the next item in the consumable chain.
     *
     * @param currentConsumableId the ID of the current consumable.
     * @return The ID of the next consumable, or -1 if there is none.
     */
    protected int getNextItemId(final int currentConsumableId) {
        for (int i = 0; i < ids.length; i++) {
            if (ids[i] == currentConsumableId && i != ids.length - 1) {
                return ids[i + 1];
            }
        }
        return -1;
    }

    /**
     * Method to get the formatted name of the item.
     *
     * @param item the item whose name is to be formatted.
     * @return The formatted name of the item.
     */
    public String getFormattedName(Item item) {
        return item.getName().replaceAll("\\(\\d\\)", "").trim().toLowerCase();
    }

    /**
     * Method to get the health effect value of the consumable for the player.
     *
     * @param player The player consuming the item.
     * @return The health effect value.
     */
    public int getHealthEffectValue(Player player) {
        return effect.getHealthEffectValue(player);
    }

    /**
     * Method to get the effect of the consumable.
     *
     * @return The consumable effect.
     */
    public ConsumableEffect getEffect() {
        return effect;
    }

    /**
     * Method to get the IDs of the consumable items.
     *
     * @return The array of item IDs.
     */
    public int[] getIds() {
        return ids;
    }
}
