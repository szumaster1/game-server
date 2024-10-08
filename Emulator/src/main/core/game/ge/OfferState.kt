package core.game.ge

/**
 * Represents the state of a Grand Exchange offer.
 * @author Emperor
 */
enum class OfferState {
    PENDING,
    REGISTERED,
    ABORTED,
    UPDATED,
    COMPLETED,
    OUTDATED,
    REMOVED
}