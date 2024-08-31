package content.region.kandarin.baxtorian.activity.barbarian_training

/**
 * Barbarian training attributes.
 *
 * Related [Barbarian Training book][content.data.book.BarbarianBook]
 */
object BarbarianTraining {
    /*
     * Base attribute that you get as soon as you start a conversation.
     */
    val BARBARIAN_TRAINING = "barbtraining:start"

    /**
     * Barbarian Firemaking.
     */
    val FM_BASE = "barbtraining:firemaking-start"
    val FM_FULL = "barbtraining:firemaking"

    /**
     * Barbarian Fishing.
     */
    val FISHING_BASE = "barbtraining:fishing-start"
    val FISHING_FULL = "barbtraining:fishing"

    /**
     * Barbarian barehanded Fishing.
     */
    val FISH_BARE_BASE = "barbtraining:barehand" // NOT USED
    val FISH_BARE_FULL = "barbtraining:barehand" // NOT USED

    /**
     * Barbarian mixes Herblore.
     */
    val HERBLORE_BASE = "barbtraining:herblore-start"
    val HERBLORE_FULL = "barbtraining:herblore"

    /**
     * Barbarian entering the Ancient Cavern.
     */
    val PYRESHIP_BASE = "barbtraining:ancients-start" // NOT USED
    val PYRESHIP_FULL = "barbtraining:ancients" // NOT USED

    /**
     * Barbarian Smithing.
     */
    val SPEAR_BASE = "barbtraining:spear-start" // NOT USED
    val SPEAR_FULL = "barbtraining:spear" // NOT USED

    /**
     * Barbarian forging hastae.
     */
    val HASTA_BASE = "barbtraining:hasta-start" // NOT USED
    val HASTA_FULL = "barbtraining:hasta" // NOT USED
}