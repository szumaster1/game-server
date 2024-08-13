package content.global.skill.gathering.farming

/**
 * Seedling.
 *
 * @property id      Unique identifier for the seedling.
 * @property TTL     Time to live for the seedling.
 * @property sapling Type of sapling for the seedling.
 * @constructor Seedling Represents a Seedling object with the given properties.
 */
class Seedling(val id: Int, val TTL: Long, val sapling: Int)