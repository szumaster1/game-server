package content.global.skill.hunter.tracking

import core.game.world.map.Location

/**
 * Represents the trial definition data.
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
 * Represents the trial types.
 */
enum class TrailType {
    LINKING,
    INITIAL,
    TUNNEL
}
