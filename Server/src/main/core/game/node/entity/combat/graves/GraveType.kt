package core.game.node.entity.combat.graves

import core.api.consts.NPCs

/**
 * Grave type.
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
    val npcId: Int,
    val cost: Int,
    val durationMinutes: Int,
    val isMembers: Boolean,
    val requiredQuest: String = "",
    val text: String
) {
    /**
     * Mem Plaque.
     */
    MEM_PLAQUE(
        npcId = NPCs.GRAVE_MARKER_6565,
        cost = 0,
        durationMinutes = 2,
        isMembers = false,
        text = "In memory of @name,<br>who died here."
    ),

    /**
     * Flag.
     */
    FLAG(
        npcId = NPCs.GRAVE_MARKER_6568,
        cost = 50,
        durationMinutes = 2,
        isMembers = false,
        text = MEM_PLAQUE.text
    ),

    /**
     * Small Gs.
     */
    SMALL_GS(
        npcId = NPCs.GRAVESTONE_6571,
        cost = 500,
        durationMinutes = 2,
        isMembers = false,
        text = "In loving memory of our dear friend @name,<br>who died in this place @mins ago."
    ),

    /**
     * Ornate Gs.
     */
    ORNATE_GS(
        npcId = NPCs.GRAVESTONE_6574,
        cost = 5000,
        durationMinutes = 3,
        isMembers = false,
        text = SMALL_GS.text
    ),

    /**
     * Font Of Life.
     */
    FONT_OF_LIFE(
        npcId = NPCs.GRAVESTONE_6577,
        cost = 50000,
        durationMinutes = 4,
        isMembers = true,
        text = "In your travels,<br>pause awhile to remember @name,<br>who passed away at this spot."
    ),

    /**
     * Stele.
     */
    STELE(
        npcId = NPCs.STELE_6580,
        cost = 50000,
        durationMinutes = 4,
        isMembers = true,
        text = FONT_OF_LIFE.text
    ),

    /**
     * Sara Symbol.
     */
    SARA_SYMBOL(
        npcId = NPCs.SARADOMIN_SYMBOL_6583,
        cost = 50000,
        durationMinutes = 4,
        isMembers = true,
        text = "@name,<br>an enlightened servant of Saradomin,<br>perished in this place."
    ),

    /**
     * Zam Symbol.
     */
    ZAM_SYMBOL(
        npcId = NPCs.ZAMORAK_SYMBOL_6586,
        cost = 50000,
        durationMinutes = 4,
        isMembers = true,
        text = "@name,<br>a most bloodthirsty follower of Zamorak,<br>perished in this place."
    ),

    /**
     * Guthix Symbol.
     */
    GUTH_SYMBOL(
        npcId = NPCs.GUTHIX_SYMBOL_6589,
        cost = 50000,
        durationMinutes = 4,
        isMembers = true,
        text = "@name,<br>who walked with the Balance of Guthix,<br>perished in this place."
    ),

    /**
     * Bandos Symbol.
     */
    BAND_SYMBOL(
        npcId = NPCs.BANDOS_SYMBOL_6592,
        cost = 50000,
        durationMinutes = 4,
        isMembers = true,
        requiredQuest = "Land of the Goblins",
        text = "@name,<br>a vicious warrior dedicated to Bandos,<br>perished in this place. "
    ),

    /**
     * Arma Symbol.
     */
    ARMA_SYMBOL(
        npcId = NPCs.ARMADYL_SYMBOL_6595,
        cost = 50000,
        durationMinutes = 4,
        isMembers = true,
        requiredQuest = "Temple of Ikov",
        text = "@name,<br>a follower of the Law of Armadyl,<br>perished in this place."
    ),

    /**
     * Zaro Symbol.
     */
    ZARO_SYMBOL(
        npcId = NPCs.MEMORIAL_STONE_6598,
        cost = 50000,
        durationMinutes = 4,
        isMembers = true,
        requiredQuest = "Desert Treasure",
        text = "@name,<br>servant of the Unknown Power,<br>perished in this place."
    ),

    /**
     * Angel Death.
     */
    ANGEL_DEATH(
        npcId = NPCs.MEMORIAL_STONE_6601,
        cost = 500000,
        durationMinutes = 5,
        isMembers = true,
        text = "Ye frail mortals who gaze upon this sight,<br>forget not the fate of @name, once mighty, now<br>surrendered to the inescapable grasp of destiny.<br><i>Requires cat in pace.</i>"
    );

    companion object {
        val ids = values().fold(ArrayList<Int>()) { list, type ->
            list.add(type.npcId)
            list.add(type.npcId + 1)
            list.add(type.npcId + 2)
            list
        }.toIntArray()
    }
}