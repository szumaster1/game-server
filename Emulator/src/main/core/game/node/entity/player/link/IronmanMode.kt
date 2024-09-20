package core.game.node.entity.player.link

/**
 * A type of iron man mode.
 * @author Vexia
 */
enum class IronmanMode(@JvmField val icon: Int) {
    NONE(-1),
    STANDARD(5),
    HARDCORE(6),
    ULTIMATE(7)
}
