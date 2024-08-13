package core.game.node.entity.player.info;

import core.ServerConstants;
import core.game.node.entity.Entity;
import core.game.node.entity.npc.NPC;
import core.game.node.entity.player.Player;
import core.game.world.map.Location;

import java.util.LinkedList;
import java.util.List;

/**
 * Render info class responsible for managing
 * rendering information for players and NPCs.
 */
public final class RenderInfo {

    private final Player player; // The player associated with this RenderInfo instance

    private List<Player> localPlayers = new LinkedList<Player>(); // List of local players in the vicinity

    private List<NPC> localNpcs = new LinkedList<NPC>(); // List of local NPCs in the vicinity

    private final long[] appearanceStamps = new long[ServerConstants.MAX_PLAYERS]; // Array to store appearance timestamps for players

    private Entity[] maskUpdates = new Entity[256]; // Array to hold mask updates for entities

    private int maskUpdateCount; // Counter for the number of mask updates

    private Location lastLocation; // The last known location of the player

    private boolean onFirstCycle = true; // Flag to indicate if this is the first cycle

    private boolean preparedAppearance; // Flag to indicate if the appearance is prepared

    /**
     * Instantiates a new Render info.
     *
     * @param player the player associated with this RenderInfo
     */
    public RenderInfo(Player player) {
        this.player = player; // Assign the player to the instance variable
    }

    /**
     * Update information about the player's state.
     */
    public void updateInformation() {
        onFirstCycle = false; // Set the first cycle flag to false after the first update
        lastLocation = player.getLocation(); // Update the last known location of the player
        preparedAppearance = false; // Reset the prepared appearance flag
    }

    /**
     * Register a mask update for an entity.
     *
     * @param entity the entity to register for mask updates
     */
    public void registerMaskUpdate(Entity entity) {
        maskUpdates[maskUpdateCount++] = entity; // Add the entity to the mask updates array and increment the count
    }

    /**
     * Gets the list of local NPCs.
     *
     * @return the list of local NPCs
     */
    public List<NPC> getLocalNpcs() {
        return localNpcs; // Return the list of local NPCs
    }

    /**
     * Sets the list of local NPCs.
     *
     * @param localNpcs the list of local NPCs to set
     */
    public void setLocalNpcs(List<NPC> localNpcs) {
        this.localNpcs = localNpcs; // Update the local NPCs list
    }

    /**
     * Checks if it is the first cycle.
     *
     * @return true if it is the first cycle, false otherwise
     */
    public boolean isOnFirstCycle() {
        return onFirstCycle; // Return the status of the first cycle flag
    }

    /**
     * Sets the first cycle status.
     *
     * @param onFirstCycle the status to set for the first cycle
     */
    public void setOnFirstCycle(boolean onFirstCycle) {
        this.onFirstCycle = onFirstCycle; // Update the first cycle flag
    }

    /**
     * Gets the last known location of the player.
     *
     * @return the last location of the player
     */
    public Location getLastLocation() {
        return lastLocation; // Return the last known location
    }

    /**
     * Sets the last known location of the player.
     *
     * @param lastLocation the last location to set
     */
    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation; // Update the last known location
    }

    /**
     * Gets the list of local players.
     *
     * @return the list of local players
     */
    public List<Player> getLocalPlayers() {
        return localPlayers; // Return the list of local players
    }

    /**
     * Sets the list of local players.
     *
     * @param localPlayers the list of local players to set
     */
    public void setLocalPlayers(List<Player> localPlayers) {
        this.localPlayers = localPlayers; // Update the local players list
    }

    /**
     * Gets the appearance stamps for players.
     *
     * @return the array of appearance stamps
     */
    public long[] getAppearanceStamps() {
        return appearanceStamps; // Return the array of appearance stamps
    }

    /**
     * Gets the count of mask updates.
     *
     * @return the count of mask updates
     */
    public int getMaskUpdateCount() {
        return maskUpdateCount; // Return the current count of mask updates
    }

    /**
     * Sets the count of mask updates.
     *
     * @param maskUpdateCount the count to set for mask updates
     */
    public void setMaskUpdateCount(int maskUpdateCount) {
        this.maskUpdateCount = maskUpdateCount; // Update the mask update count
    }

    /**
     * Gets the array of mask updates.
     *
     * @return the array of mask updates
     */
    public Entity[] getMaskUpdates() {
        return maskUpdates; // Return the array of mask updates
    }

    /**
     * Sets the array of mask updates.
     *
     * @param maskUpdates the array of mask updates to set
     */
    public void setMaskUpdates(Entity[] maskUpdates) {
        this.maskUpdates = maskUpdates; // Update the mask updates array
    }

    /**
     * Sets the prepared appearance status.
     *
     * @param prepared the status to set for prepared appearance
     */
    public void setPreparedAppearance(boolean prepared) {
        this.preparedAppearance = prepared; // Update the prepared appearance flag
    }

    /**
     * Checks if the appearance is prepared.
     *
     * @return true if the appearance is prepared, false otherwise
     */
    public boolean preparedAppearance() {
        return preparedAppearance; // Return the status of the prepared appearance flag
    }
}