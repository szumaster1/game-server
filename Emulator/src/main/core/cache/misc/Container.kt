package core.cache.misc

/**
 * A container.
 * @author Dragonkk
 */
open class Container {
    var version: Int = -1
    var crc: Int = -1
    var nameHash: Int = -1
    var updated: Boolean = false

    init {
        nameHash = -1
        version = -1
        crc = -1
    }

}
