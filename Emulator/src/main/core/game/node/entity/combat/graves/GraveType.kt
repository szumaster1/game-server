package core.game.node.entity.combat.graves

import org.rs.consts.NPCs

/**
 * Grave type.
 * @author Ceikry
 *
 * @param npcId Unique identifier for the NPC associated with the grave type.
 * @param cost Cost associated with the grave type.
 * @param durationMinutes Duration in minutes for which the grave type is valid.
 * @param isMembers Boolean indicating if the grave type is available to members only.
 * @param requiredQuest Optional quest required to access this grave type.
 * @param text Descriptive text for the grave type.
 * @constructor Grave type.
 */
enum class GraveType(
    val npcId: Int, // Unique identifier for the NPC associated with this grave type
    val cost: Int, // Cost associated with this grave type
    val durationMinutes: Int, // Duration in minutes for which this grave type is valid
    val isMembers: Boolean, // Indicates if this grave type is available to members only
    val requiredQuest: String = "", // Optional quest required to access this grave type
    val text: String // Descriptive text for this grave type
) {
    /**
     * Mem Plaque.
     */
    MEM_PLAQUE(
        npcId = NPCs.GRAVE_MARKER_6565, // NPC ID for the Mem Plaque
        cost = 0, // Cost for the Mem Plaque
        durationMinutes = 2, // Duration for the Mem Plaque
        isMembers = false, // Mem Plaque is available to all players
        text = "In memory of @name,<br>who died here." // Descriptive text for the Mem Plaque
    ),

    /**
     * Flag.
     */
    FLAG(
        npcId = NPCs.GRAVE_MARKER_6568, // NPC ID for the Flag
        cost = 50, // Cost for the Flag
        durationMinutes = 2, // Duration for the Flag
        isMembers = false, // Flag is available to all players
        text = MEM_PLAQUE.text // Reuses the text from Mem Plaque
    ),

    /**
     * Small Gs.
     */
    SMALL_GS(
        npcId = NPCs.GRAVESTONE_6571, // NPC ID for the Small Gravestone
        cost = 500, // Cost for the Small Gravestone
        durationMinutes = 2, // Duration for the Small Gravestone
        isMembers = false, // Small Gravestone is available to all players
        text = "In loving memory of our dear friend @name,<br>who died in this place @mins ago." // Descriptive text for the Small Gravestone
    ),

    /**
     * Ornate Gs.
     */
    ORNATE_GS(
        npcId = NPCs.GRAVESTONE_6574, // NPC ID for the Ornate Gravestone
        cost = 5000, // Cost for the Ornate Gravestone
        durationMinutes = 3, // Duration for the Ornate Gravestone
        isMembers = false, // Ornate Gravestone is available to all players
        text = SMALL_GS.text // Reuses the text from Small Gravestone
    ),

    /**
     * Font Of Life.
     */
    FONT_OF_LIFE(
        npcId = NPCs.GRAVESTONE_6577, // NPC ID for the Font of Life
        cost = 50000, // Cost for the Font of Life
        durationMinutes = 4, // Duration for the Font of Life
        isMembers = true, // Font of Life is available to members only
        text = "In your travels,<br>pause awhile to remember @name,<br>who passed away at this spot." // Descriptive text for the Font of Life
    ),

    /**
     * Stele.
     */
    STELE(
        npcId = NPCs.STELE_6580, // NPC ID for the Stele
        cost = 50000, // Cost for the Stele
        durationMinutes = 4, // Duration for the Stele
        isMembers = true, // Stele is available to members only
        text = FONT_OF_LIFE.text // Reuses the text from Font of Life
    ),

    /**
     * Sara Symbol.
     */
    SARA_SYMBOL(
        npcId = NPCs.SARADOMIN_SYMBOL_6583, // NPC ID for the Sara Symbol
        cost = 50000, // Cost for the Sara Symbol
        durationMinutes = 4, // Duration for the Sara Symbol
        isMembers = true, // Sara Symbol is available to members only
        text = "@name,<br>an enlightened servant of Saradomin,<br>perished in this place." // Descriptive text for the Sara Symbol
    ),

    /**
     * Zam Symbol.
     */
    ZAM_SYMBOL(
        npcId = NPCs.ZAMORAK_SYMBOL_6586, // NPC ID for the Zam Symbol
        cost = 50000, // Cost for the Zam Symbol
        durationMinutes = 4, // Duration for the Zam Symbol
        isMembers = true, // Zam Symbol is available to members only
        text = "@name,<br>a most bloodthirsty follower of Zamorak,<br>perished in this place." // Descriptive text for the Zam Symbol
    ),

    /**
     * Guthix Symbol.
     */
    GUTH_SYMBOL(
        npcId = NPCs.GUTHIX_SYMBOL_6589, // NPC ID for the Guthix Symbol
        cost = 50000, // Cost for the Guthix Symbol
        durationMinutes = 4, // Duration for the Guthix Symbol
        isMembers = true, // Guthix Symbol is available to members only
        text = "@name,<br>who walked with the Balance of Guthix,<br>perished in this place." // Descriptive text for the Guthix Symbol
    ),

    /**
     * Bandos Symbol.
     */
    BAND_SYMBOL(
        npcId = NPCs.BANDOS_SYMBOL_6592, // NPC ID for the Bandos Symbol
        cost = 50000, // Cost for the Bandos Symbol
        durationMinutes = 4, // Duration for the Bandos Symbol
        isMembers = true, // Bandos Symbol is available to members only
        requiredQuest = "Land of the Goblins", // Quest required to access the Bandos Symbol
        text = "@name,<br>a vicious warrior dedicated to Bandos,<br>perished in this place." // Descriptive text for the Bandos Symbol
    ),

    /**
     * Arma Symbol.
     */
    ARMA_SYMBOL(
        npcId = NPCs.ARMADYL_SYMBOL_6595, // NPC ID for the Arma Symbol
        cost = 50000, // Cost for the Arma Symbol
        durationMinutes = 4, // Duration for the Arma Symbol
        isMembers = true, // Arma Symbol is available to members only
        requiredQuest = "Temple of Ikov", // Quest required to access the Arma Symbol
        text = "@name,<br>a follower of the Law of Armadyl,<br>perished in this place." // Descriptive text for the Arma Symbol
    ),

    /**
     * Zaro Symbol.
     */
    ZARO_SYMBOL(
        npcId = NPCs.MEMORIAL_STONE_6598, // NPC ID for the Zaro Symbol
        cost = 50000, // Cost for the Zaro Symbol
        durationMinutes = 4, // Duration for the Zaro Symbol
        isMembers = true, // Zaro Symbol is available to members only
        requiredQuest = "Desert Treasure", // Quest required to access the Zaro Symbol
        text = "@name,<br>servant of the Unknown Power,<br>perished in this place." // Descriptive text for the Zaro Symbol
    ),

    /**
     * Angel Death.
     */
    ANGEL_DEATH(
        npcId = NPCs.MEMORIAL_STONE_6601, // NPC ID for the Angel Death
        cost = 500000, // Cost for the Angel Death
        durationMinutes = 5, // Duration for the Angel Death
        isMembers = true, // Angel Death is available to members only
        text = "Ye frail mortals who gaze upon this sight,<br>forget not the fate of @name, once mighty, now<br>surrendered to the inescapable grasp of destiny.<br><i>Requires cat in pace.</i>" // Descriptive text for the Angel Death
    );

    companion object {
        // Collects all NPC IDs into an array
        val ids = values().fold(ArrayList<Int>()) { list, type ->
            list.add(type.npcId) // Adds the NPC ID to the list
            list.add(type.npcId + 1) // Adds the next NPC ID to the list
            list.add(type.npcId + 2) // Adds the following NPC ID to the list
            list // Returns the updated list
        }.toIntArray() // Converts the list to an IntArray
    }
}