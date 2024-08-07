package core.game.global.report

import core.game.node.entity.player.Player

/**
 * Rule
 *
 * @property rule
 * @constructor Rule
 */
enum class Rule(val rule: Int) {
    /**
     * Offensive Language
     *
     * @constructor Offensive Language
     */
    OFFENSIVE_LANGUAGE(0),

    /**
     * Item Scamming
     *
     * @constructor Item Scamming
     */
    ITEM_SCAMMING(1),

    /**
     * Password Scamming
     *
     * @constructor Password Scamming
     */
    PASSWORD_SCAMMING(2),

    /**
     * Bug Abuse
     *
     * @constructor Bug Abuse
     */
    BUG_ABUSE(3),

    /**
     * Staff Impersonation
     *
     * @constructor Staff Impersonation
     */
    STAFF_IMPERSONATION(4),

    /**
     * Acccount Sharing
     *
     * @constructor Acccount Sharing
     */
    ACCCOUNT_SHARING(5),

    /**
     * Macroing
     *
     * @constructor Macroing
     */
    MACROING(6),

    /**
     * Multiple Logging
     *
     * @constructor Multiple Logging
     */
    MULTIPLE_LOGGING(7),

    /**
     * Encouraging To Break Rules
     *
     * @constructor Encouraging To Break Rules
     */
    ENCOURAGING_TO_BREAK_RULES(8),

    /**
     * Misuse Of Customer Support
     *
     * @constructor Misuse Of Customer Support
     */
    MISUSE_OF_CUSTOMER_SUPPORT(9),

    /**
     * Adveristing
     *
     * @constructor Adveristing
     */
    ADVERISTING(10),

    /**
     * Real World Item Trading
     *
     * @constructor Real World Item Trading
     */
    REAL_WORLD_ITEM_TRADING(11),

    /**
     * Asking Personal Details
     *
     * @constructor Asking Personal Details
     */
    ASKING_PERSONAL_DETAILS(12);

    /**
     * Can request
     *
     * @param target
     * @return
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
