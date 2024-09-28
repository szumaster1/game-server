package content.region.morytania.handlers

import core.api.*
import core.game.bots.AIPlayer
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.interaction.QueueStrength
import core.game.node.entity.Entity
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.info.Rights
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Location
import core.game.world.map.zone.ZoneBorders
import org.rs.consts.NPCs
import org.rs.consts.QuestName

/**
 * Represents the Morytania map area.
 */
class MorytaniaArea : MapArea {

    override fun defineAreaBorders(): Array<ZoneBorders> {
        return arrayOf(ZoneBorders(3426, 3191, 3715, 3588))
    }

    override fun areaEnter(entity: Entity) {
        if (entity is Player && entity !is AIPlayer && !isQuestComplete(entity, QuestName.PRIEST_IN_PERIL) && entity.details.rights != Rights.ADMINISTRATOR) {
            kickThemOut(entity)
        }
    }

    private fun kickThemOut(entity: Player) {
        val watchdog = NPC(NPCs.ABIDOR_CRANK_3635)
        watchdog.isNeverWalks = true
        watchdog.isWalks = false
        watchdog.location = entity.location
        watchdog.init()
        entity.lock()

        runTask(watchdog, 1) {
            watchdog.moveStep()
            watchdog.face(entity)
            openDialogue(entity, WatchDogDialogueFile(), watchdog)
            GameWorld.Pulser.submit(object : Pulse() {
                override fun pulse(): Boolean {
                    if (getAttribute(entity, "teleporting-away", false)) return true
                    if (!entity.isActive) poofClear(watchdog)
                    if (entity.dialogueInterpreter.dialogue == null || entity.dialogueInterpreter.dialogue.file == null)
                        openDialogue(
                        entity,
                        WatchDogDialogueFile(),
                        watchdog
                    )
                    return !watchdog.isActive || !entity.isActive
                }
            })
        }
    }
}

/**
 * Watch dog dialogue file.
 */
class WatchDogDialogueFile : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        when (stage) {
            0 -> npcl(FacialExpression.WORRIED, "Oh this is no good, you surely will not survive here. Let me take you back.").also { stage++ }
            1 -> {
                end()
                visualize(npc!!, 1818, 343)
                sendGraphics(342, player!!.location)
                setAttribute(player!!, "teleporting-away", true)
                queueScript(player!!, 3, QueueStrength.SOFT) {
                    poofClear(npc!!)
                    teleport(player!!, Location.create(3402, 3485, 0))
                    unlock(player!!)
                    removeAttribute(player!!, "teleporting-away")
                    return@queueScript stopExecuting(player!!)
                }
            }
        }
    }
}




