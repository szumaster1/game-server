package content.global.skill.gathering.hunter.tracking

import core.game.world.map.Location

/**
 * Trail definition
 *
 * @property varbit
 * @property type
 * @property inverted
 * @property startLocation
 * @property endLocation
 * @property triggerObjectLocation
 * @constructor Trail definition
 */
class TrailDefinition(
    val varbit: Int,
    val type: TrailType,
    var inverted: Boolean,
    val startLocation: Location,
    val endLocation: Location,
    val triggerObjectLocation: Location = endLocation
) {
    override fun toString(): String {
        return "$startLocation $endLocation [varbit: $varbit] [${type.name}] [inverted: $inverted]"
    }
}

/**
 * Trail type
 *
 * @constructor Trail type
 */
enum class TrailType {
    /**
     * Linking.
     */
    LINKING,

    /**
     * Initial.
     */
    INITIAL,

    /**
     * Tunnel.
     */
    TUNNEL
}
