package content.region.kandarin.baxtorian.handlers.barbariantraining

/**
 * Barbarian training attributes.
 */
object BarbarianTraining {
    /*
     * Base attribute that you get as soon as you start a conversation.
     */
    val BARBARIAN_TRAINING = "/save:barbtraining:start"

    /**
     * Barbarian Firemaking.
     */
    @JvmField val FM_BASE = "/save:barbtraining:firemaking-start"
    @JvmField val FM_FULL = "/save:barbtraining:firemaking"

    /**
     * Barbarian Fishing.
     */
    val FISHING_BASE = "/save:barbtraining:fishing-start"
    val FISHING_FULL = "/save:barbtraining:fishing"

    /**
     * Barbarian barehanded Fishing.
     */
    val FISH_BARE_BASE = "/save:barbtraining:barehand" // NOT USED
    val FISH_BARE_FULL = "/save:barbtraining:barehand" // NOT USED

    /**
     * Barbarian mixes Herblore.
     */
    val HERBLORE_BASE = "/save:barbtraining:herblore-start"
    val HERBLORE_FULL = "/save:barbtraining:herblore"

    /**
     * Barbarian entering the Ancient Cavern.
     */
    val PYRESHIP_BASE = "/save:barbtraining:ancients-start" // NOT USED
    val PYRESHIP_FULL = "/save:barbtraining:ancients" // NOT USED

    /**
     * Barbarian Smithing.
     */
    val SPEAR_BASE = "/save:barbtraining:spear-start" // NOT USED
    val SPEAR_FULL = "/save:barbtraining:spear" // NOT USED

    /**
     * Barbarian forging hastae.
     */
    val HASTA_BASE = "/save:barbtraining:hasta-start" // NOT USED
    val HASTA_FULL = "/save:barbtraining:hasta" // NOT USED
}