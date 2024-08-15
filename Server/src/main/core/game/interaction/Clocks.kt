package core.game.interaction

/**
 * Define an object named Clocks to hold constant values
 */
object Clocks {
    // Constant representing the movement event
    @JvmStatic
    val MOVEMENT = 0

    // Constant representing the end of an animation
    @JvmStatic
    val ANIMATION_END = 1

    // Constant representing the next eating event
    @JvmStatic
    val NEXT_EAT = 2

    // Constant representing the next consumption event
    @JvmStatic
    val NEXT_CONSUME = 3

    // Constant representing the next drinking event
    @JvmStatic
    val NEXT_DRINK = 4

    // Constant representing the next attack event
    @JvmStatic
    val NEXT_ATTACK = 5

    // Constant representing a stun event
    @JvmStatic
    val STUN = 6

    // Constant representing a skilling event
    @JvmStatic
    val SKILLING = 7
}

