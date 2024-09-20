package core.game.global.report

import core.api.sendMessage
import core.game.node.entity.player.Player
import core.game.node.entity.player.info.LogType
import core.game.node.entity.player.info.PlayerMonitor.log
import core.game.system.command.CommandMapping.get

/**
 * Represents an abuse report to file.
 * @author Vexia
 *
 * @param reporter The player reporting the abuse.
 * @param victim The player being reported.
 * @param rule The rule violated.
 * @constructor Represents an [AbuseReport] instance with reporter, victim, and rule.
 */
class AbuseReport(val reporter: String, val victim: String, val rule: Rule) {

    /**
     * The messages.
     */
    var messages: String? = null

    /**
     * Construct method to handle abuse report.
     *
     * @param player The player making the report.
     * @param mute Flag indicating if the player should be muted.
     */
    fun construct(player: Player, mute: Boolean) {
        if (mute) {
            // Attempt to mute the reported player for 48 hours
            get("mute")!!.attemptHandling(player, arrayOf("mute", victim, "48h"))
        }
        // Send a confirmation message to the reporting player
        sendMessage(player, "Thank-you, your abuse report has been received.")
        // Log the abuse report action
        log(player, LogType.COMMAND, "$reporter-$victim-Abuse Report - ${rule.name}")
    }
}
