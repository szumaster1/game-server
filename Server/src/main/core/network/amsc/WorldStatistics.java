package core.network.amsc;

import java.util.ArrayList;
import java.util.List;

/**
 * World statistics.
 */
public final class WorldStatistics {

    private final int id; // Unique identifier for the world statistics

    private final List<String> players = new ArrayList<>(20); // List to store player names

    /**
     * Instantiates a new World statistics.
     *
     * @param id the id - The constructor to initialize WorldStatistics with a specific id
     */
    public WorldStatistics(int id) {
        this.id = id;
    }

    /**
     * Gets players.
     *
     * @return the players - Returns the list of players
     */
    public List<String> getPlayers() {
        return players;
    }

    /**
     * Gets id.
     *
     * @return the id - Returns the id of the WorldStatistics
     */
    public int getId() {
        return id;
    }
}
