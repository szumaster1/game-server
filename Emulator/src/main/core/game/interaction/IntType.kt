package core.game.interaction

/**
 * Enum class representing different types of entities in the game.
 */
enum class IntType {
    ITEM,        // Represents an item that can be interacted with
    SCENERY,     // Represents background elements that are part of the environment
    NPC,         // Represents non-playable characters that the player can interact with
    GROUNDITEM,  // Represents items that are found on the ground
    PLAYER       // Represents the player character
}
