package content.global.random.event.lostandfound

import content.global.random.RandomEventNPC
import core.api.*
import core.api.utils.WeightBasedTable
import core.game.interaction.QueueStrength
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.link.TeleportManager
import core.game.system.timer.impl.AntiMacro

class LostAndFoundInitNPC(override var loot: WeightBasedTable? = null) : RandomEventNPC(-1) {
    /*
     * TODO:
     *  1. Change to non-skill random event. It was only accessible through normal teleports and a essence mine.
     *  2. Trying to alchemise something returns: "You didn't come here to do that!"
     */

    override fun init() {
        super.init()
        /*
         * val runes = getAttribute(player, "teleport:items", emptyArray<Item>())
         */
        lock(player, 4)
        sendChat(player, "Uh? Help!")
        if(player.location.isInRegion(11595)) {
            setAttribute(player, LostAndFound.essenceMine, true)
        }
        queueScript(player, 1, QueueStrength.SOFT) { stage: Int ->
            when (stage) {
                0 -> {
                    /*
                     * if (runes.isNotEmpty()) {
                     *     setAttribute(player, "teleport:items", runes)
                     * }
                     */
                    setMinimapState(player, 2)
                    LostAndFound.setRandomAppendage(player)
                    setAttribute(player, LostAndFound.previousLocation, player.location)
                    teleport(player, LostAndFound.eventLocation, type = TeleportManager.TeleportType.RANDOM_EVENT_OLD)
                    return@queueScript delayScript(player, 3)
                }

                1 -> {
                    sendUnclosableDialogue(player, false, "There has been a fault in the teleportation matrix. Please operate the", "odd appendage out to be forwarded to your destination.")
                    /*
                     * sendMessage(player, "You have to operate the appendage before you can teleport again.")
                     */
                    player.locks.lockTeleport(1000)
                    AntiMacro.terminateEventNpc(player)
                    return@queueScript stopExecuting(player)
                }

                else -> return@queueScript stopExecuting(player)
            }
        }
    }

    override fun talkTo(npc: NPC) {

    }

}
