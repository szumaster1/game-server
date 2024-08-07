package content.global.random.event.mime

import content.global.random.RandomEventNPC
import core.api.*
import core.api.consts.NPCs
import core.api.utils.WeightBasedTable
import core.game.interaction.QueueStrength
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.link.TeleportManager
import core.game.system.timer.impl.AntiMacro
import core.tools.RandomFunction

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
                        player.properties.teleportLocation = getAttribute(player, MimeUtils.TELEPORT_ATTRIBUTE, null)
                    }
                    removeTabs(player, 0, 1, 2, 3, 4, 5, 6, 7, 9, 10, 11, 12, 13, 14)
                    teleport(player, MimeUtils.MIME_EVENT_LOCATION, TeleportManager.TeleportType.NORMAL)
                    AntiMacro.terminateEventNpc(player)
                    return@queueScript delayScript(player, 8)
                }

                1 -> {
                    sendMessage(player, "You need to copy the mime's performance, then you'll be returned to where you were.")

                    /*
                        Face location bug
                        https://www.youtube.com/watch?v=VGMfcLQbZGI&ab_channel=skillmatrix09
                     */

                    if(RandomFunction.random(0,1) == 1) {
                        forceMove(player, MimeUtils.MIME_EVENT_LOCATION, MimeUtils.MIME_LOCATION, 0, 80)
                    } else {
                        forceMove(player, MimeUtils.MIME_EVENT_LOCATION, MimeUtils.MIME_LOCATION, 10, 60)
                    }
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
