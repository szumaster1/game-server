package content.global.ame.mime

import content.global.ame.RandomEventNPC
import core.api.*
import core.api.utils.WeightBasedTable
import core.game.interaction.QueueStrength
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.link.TeleportManager
import core.game.system.timer.impl.AntiMacro
import core.tools.RandomFunction
import org.rs.consts.NPCs

/**
 * Mime NPC.
 */
class MimeNPC(override var loot: WeightBasedTable? = null) : RandomEventNPC(NPCs.MYSTERIOUS_OLD_MAN_410) {

    override fun init() {
        super.init()
        sendChat("Aha, you're required, " + player.username.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() } + "!")
        queueScript(player, 3, QueueStrength.SOFT) { stage: Int ->
            when (stage) {
                0 -> {
                    lock(player, 10)
                    lockInteractions(player, 10)
                    setAttribute(player, MimeUtils.TELEPORT_ATTRIBUTE, player.location)
                    registerLogoutListener(player, MimeUtils.LOGOUT_ATTRIBUTE) { p ->
                        registerLogoutListener(player, MimeUtils.LOGOUT_ATTRIBUTE) { p ->
                            p.location = getAttribute(p, MimeUtils.TELEPORT_ATTRIBUTE, p.location)
                        }
                    }
                    removeTabs(player, 0, 1, 2, 3, 4, 5, 6, 7, 9, 10, 11, 12, 13, 14)
                    teleport(player, MimeUtils.MIME_EVENT_LOCATION, TeleportManager.TeleportType.NORMAL)
                    AntiMacro.terminateEventNpc(player)
                    return@queueScript delayScript(player, 8)
                }

                1 -> {
                    sendMessage(player, "You need to copy the mime's performance, then you'll be returned to where you were.")
                    // https://www.youtube.com/watch?v=VGMfcLQbZGI&ab_channel=skillmatrix09
                    forceMove(player, MimeUtils.MIME_EVENT_LOCATION, MimeUtils.MIME_LOCATION, 20, 80)
                    return@queueScript delayScript(player, 6)
                }

                2 -> {
                    setAttribute(player, MimeUtils.COPY_ATTRIBUTE, 0)
                    setAttribute(player, MimeUtils.EMOTE_ATTRIBUTE, RandomFunction.random(2, 9))
                    MimeUtils.getEmote(player)
                    return@queueScript stopExecuting(player)
                }
            }
            return@queueScript stopExecuting(player)
        }
    }

    override fun talkTo(npc: NPC) {
        sendMessage(player, "You can't do that right now.")
    }

}
