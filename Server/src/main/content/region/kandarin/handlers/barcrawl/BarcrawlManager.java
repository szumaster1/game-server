package content.region.kandarin.handlers.barcrawl;

import core.api.LoginListener;
import core.api.PersistPlayer;
import core.game.component.Component;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import static core.api.ContentAPIKt.setAttribute;

/**
 * The Barcrawl manager.
 */
public final class BarcrawlManager implements LoginListener, PersistPlayer {

    /**
     * The constant BARCRAWL_CARD.
     */
    public static final Item BARCRAWL_CARD = new Item(455);

    /**
     * The constant NAMES.
     */
    public static final String[] NAMES = new String[]{"BlueMoon Inn", "Blueberry's Bar", "Dead Man's Chest", "Dragon Inn", "Flying Horse Inn", "Foresters Arms", "Jolly Boar Inn", "Karamja Spirits bar", "Rising Sun Inn", "Rusty Anchor Inn"};

    /**
     * The constant COMPONENT.
     */
    public static final Component COMPONENT = new Component(220);

    private final Player player;

    private final boolean[] bars = new boolean[10];

    private boolean started;

    /**
     * Instantiates a new Barcrawl manager.
     *
     * @param player the player
     */
    public BarcrawlManager(final Player player) {
        this.player = player;
    }

    /**
     * Instantiates a new Barcrawl manager.
     */
    public BarcrawlManager() {
        this.player = null;
    }

    @Override
    public void login(@NotNull Player player) {
        BarcrawlManager instance = new BarcrawlManager(player);
        setAttribute(player, "barcrawl-inst", instance);
    }

    @Override
    public void parsePlayer(@NotNull Player player, @NotNull JSONObject data) {
        JSONObject bcData = (JSONObject) data.get("barCrawl");
        if (bcData == null) return;
        JSONArray barsVisisted = (JSONArray) bcData.get("bars");
        BarcrawlManager instance = getInstance(player);
        instance.started = (boolean) bcData.get("started");
        for (int i = 0; i < barsVisisted.size(); i++) {
            instance.bars[i] = (boolean) barsVisisted.get(i);
        }
    }

    @Override
    public void savePlayer(@NotNull Player player, @NotNull JSONObject save) {
        BarcrawlManager instance = getInstance(player);
        JSONObject barCrawl = new JSONObject();
        barCrawl.put("started", instance.started);
        JSONArray barsVisited = new JSONArray();
        for (boolean visited : instance.bars)
            barsVisited.add(visited);
        barCrawl.put("bars", barsVisited);
        save.put("barCrawl", barCrawl);
    }

    /**
     * Read.
     */
    public void read() {
        if (isFinished()) {
            player.getPacketDispatch().sendMessage("You are too drunk to be able to read the barcrawl card.");
            return;
        }
        player.getInterfaceManager().open(COMPONENT);
        drawCompletions();
    }

    private void drawCompletions() {
        player.getPacketDispatch().sendString("<col=0000FF>The Official Alfred Grimhand Barcrawl!", 220, 1);
        boolean complete;
        for (int i = 0; i < bars.length; i++) {
            complete = bars[i];
            player.getPacketDispatch().sendString((complete ? "<col=00FF00>" : "<col=FF0000>") + NAMES[i] + " - " + (complete ? "Complete!" : "Not Completed..."), 220, 3 + i);
        }
    }

    /**
     * Complete.
     *
     * @param index the index
     */
    public void complete(int index) {
        bars[index] = true;
    }

    /**
     * Is finished boolean.
     *
     * @return the boolean
     */
    public boolean isFinished() {
        for (int i = 0; i < bars.length; i++) {
            if (!isCompleted(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Reset.
     */
    public void reset() {
        started = false;
        for (int i = 0; i < bars.length; i++) {
            bars[i] = false;
        }
    }

    /**
     * Is completed boolean.
     *
     * @param index the index
     * @return the boolean
     */
    public boolean isCompleted(int index) {
        return bars[index];
    }

    /**
     * Has card boolean.
     *
     * @return the boolean
     */
    public boolean hasCard() {
        return player.getInventory().containsItem(BARCRAWL_CARD) || player.getBank().containsItem(BARCRAWL_CARD);
    }

    /**
     * Is started boolean.
     *
     * @return the boolean
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * Sets started.
     *
     * @param started the started
     */
    public void setStarted(boolean started) {
        this.started = started;
    }

    /**
     * Get bars boolean [ ].
     *
     * @return the boolean [ ]
     */
    public boolean[] getBars() {
        return bars;
    }

    /**
     * Gets instance.
     *
     * @param player the player
     * @return the instance
     */
    public static BarcrawlManager getInstance(Player player) {
        return player.getAttribute("barcrawl-inst", new BarcrawlManager());
    }
}
