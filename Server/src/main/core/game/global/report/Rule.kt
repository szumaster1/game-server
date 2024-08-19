package core.game.global.report

import core.game.node.entity.player.Player

/**
 * Represents a rule.
 * @author Vexia
 *
 * @param rule The rule ID.
 * @constructor Creates a Rule instance with the given rule ID.
 */
enum class Rule(val rule: Int) {
    /**
     * Offensive Language.
     * @constructor Creates a Rule instance for offensive language.
     */
    OFFENSIVE_LANGUAGE(0),

    /**
     * Item Scamming.
     * @constructor Creates a Rule instance for item scamming.
     */
    ITEM_SCAMMING(1),

    /**
     * Password Scamming.
     * @constructor Creates a Rule instance for password scamming.
     */
    PASSWORD_SCAMMING(2),

    /**
     * Bug Abuse
     * @constructor Creates a Rule instance for bug abuse.
     */
    BUG_ABUSE(3),

    /**
     * Staff Impersonation.
     * @constructor Creates a Rule instance for staff impersonation.
     */
    STAFF_IMPERSONATION(4),

    /**
     * Account Sharing.
     * @constructor Creates a Rule instance for account sharing.
     */
    ACCOUNT_SHARING(5),

    /**
     * Macroing.
     * @constructor Creates a Rule instance for macroing.
     */
    MACROING(6),

    /**
     * Multiple Logging.
     * @constructor Creates a Rule instance for multiple logging.
     */
    MULTIPLE_LOGGING(7),

    /**
     * Encouraging To Break Rules.
     * @constructor Creates a Rule instance for encouraging to break rules.
     */
    ENCOURAGING_TO_BREAK_RULES(8),

    /**
     * Misuse Of Customer Support.
     * @constructor Creates a Rule instance for misuse of customer support.
     */
    MISUSE_OF_CUSTOMER_SUPPORT(9),

    /**
     * Advertising.
     * @constructor Creates a Rule instance for advertising.
     */
    ADVERTISING(10),

    /**
     * Real World Item Trading.
     * @constructor Creates a Rule instance for real world item trading.
     */
    REAL_WORLD_ITEM_TRADING(11),

    /**
     * Asking Personal Details.
     * @constructor Creates a Rule instance for asking personal details.
     */
    ASKING_PERSONAL_DETAILS(12);

    /**
     * Checks if a player can request a report for this rule.
     *
     * @param target The player to check.
     * @return True if the player can request a report, false otherwise.
     */
    fun canRequest(target: Player?): Boolean {
        if (target == null) {
            return false
        }
        if (target.getSavedData().globalData.getChatPing() < System.currentTimeMillis()) {
            return false
        }
        return true
    }

    companion object {
        /**
         * Retrieves the Rule instance for the given rule ID.
         *
         * @param id The rule ID.
         * @return The Rule instance, or null if not found.
         */
        @JvmStatic
        fun forId(id: Int): Rule? {
            for (rule in values()) {
                if (rule.rule == id) {
                    return rule
                }
            }
            return null
        }
    }
}
