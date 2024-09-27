package core.game.node.entity.player.info;

import core.Configuration;
import core.game.node.entity.Entity;
import core.game.node.entity.npc.NPC;
import core.game.node.entity.player.Player;
import core.game.world.map.Location;

import java.util.LinkedList;
import java.util.List;

/**
 * Holds a player's render information.
 * @author Emperor
 */
public final class RenderInfo {

    private final Player player;

    private List<Player> localPlayers = new LinkedList<Player>();

    private List<NPC> localNpcs = new LinkedList<NPC>();

    private final long[] appearanceStamps = new long[Configuration.MAX_PLAYERS];

    private Entity[] maskUpdates = new Entity[256];

    private int maskUpdateCount;

    private Location lastLocation;

    private boolean onFirstCycle = true;

    private boolean preparedAppearance;

    /**
     * Constructs a new Render info.
     *
     * @param player the player associated with this RenderInfo
     */
    public RenderInfo(Player player) {
        this.player = player;
    }

    /**
     * Update information about the player's state.
     */
    public void updateInformation() {
        onFirstCycle = false;
        lastLocation = player.getLocation();
        preparedAppearance = false;
    }

    /**
     * Register a mask update for an entity.
     *
     * @param entity the entity to register for mask updates
     */
    public void registerMaskUpdate(Entity entity) {
        maskUpdates[maskUpdateCount++] = entity;
    }

    /**
     * Gets the list of local NPCs.
     *
     * @return the list of local NPCs
     */
    public List<NPC> getLocalNpcs() {
        return localNpcs;
    }

    /**
     * Sets the list of local NPCs.
     *
     * @param localNpcs the list of local NPCs to set
     */
    public void setLocalNpcs(List<NPC> localNpcs) {
        this.localNpcs = localNpcs;
    }

    /**
     * Checks if it is the first cycle.
     *
     * @return true if it is the first cycle, false otherwise
     */
    public boolean isOnFirstCycle() {
        return onFirstCycle;
    }

    /**
     * Sets the first cycle status.
     *
     * @param onFirstCycle the status to set for the first cycle
     */
    public void setOnFirstCycle(boolean onFirstCycle) {
        this.onFirstCycle = onFirstCycle;
    }

    /**
     * Gets the last known location of the player.
     *
     * @return the last location of the player
     */
    public Location getLastLocation() {
        return lastLocation;
    }

    /**
     * Sets the last known location of the player.
     *
     * @param lastLocation the last location to set
     */
    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    /**
     * Gets the list of local players.
     *
     * @return the list of local players
     */
    public List<Player> getLocalPlayers() {
        return localPlayers;
    }

    /**
     * Sets the list of local players.
     *
     * @param localPlayers the list of local players to set
     */
    public void setLocalPlayers(List<Player> localPlayers) {
        this.localPlayers = localPlayers;
    }

    /**
     * Gets the appearance stamps for players.
     *
     * @return the array of appearance stamps
     */
    public long[] getAppearanceStamps() {
        return appearanceStamps;
    }

    /**
     * Gets the count of mask updates.
     *
     * @return the count of mask updates
     */
    public int getMaskUpdateCount() {
        return maskUpdateCount;
    }

    /**
     * Sets the count of mask updates.
     *
     * @param maskUpdateCount the count to set for mask updates
     */
    public void setMaskUpdateCount(int maskUpdateCount) {
        this.maskUpdateCount = maskUpdateCount;
    }

    /**
     * Gets the array of mask updates.
     *
     * @return the array of mask updates
     */
    public Entity[] getMaskUpdates() {
        return maskUpdates;
    }

    /**
     * Sets the array of mask updates.
     *
     * @param maskUpdates the array of mask updates to set
     */
    public void setMaskUpdates(Entity[] maskUpdates) {
        this.maskUpdates = maskUpdates;
    }

    /**
     * Sets the prepared appearance status.
     *
     * @param prepared the status to set for prepared appearance
     */
    public void setPreparedAppearance(boolean prepared) {
        this.preparedAppearance = prepared;
    }

    /**
     * Checks if the appearance is prepared.
     *
     * @return true if the appearance is prepared, false otherwise
     */
    public boolean preparedAppearance() {
        return preparedAppearance;
    }
}