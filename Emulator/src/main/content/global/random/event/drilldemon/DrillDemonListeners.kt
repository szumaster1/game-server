package content.global.random.event.drilldemon

import core.api.*
import org.rs.consts.Components
import org.rs.consts.NPCs
import org.rs.consts.Sounds
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.Entity
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.world.map.zone.ZoneBorders
import core.game.world.map.zone.ZoneRestriction
import core.tools.secondsToTicks

/**
 * Drill demon listeners.
 */
class DrillDemonListeners : InteractionListener, MapArea {
    val MATS = intArrayOf(10076, 10077, 10078, 10079)
    override fun defineListeners() {

        on(DrillDemonUtils.DD_NPC, IntType.NPC, "talk-to") { player, _ ->
            if (inBorders(player, DrillDemonUtils.DD_AREA)) {
                openDialogue(player, SergeantDamienDialogue(isCorrect = true), DrillDemonUtils.DD_NPC)
            } else {
                sendNPCDialogue(player, NPCs.SERGEANT_DAMIEN_2790, "I Haven't given you the order yet, worm!", FacialExpression.OLD_DEFAULT)
            }
            return@on true
        }

        on(MATS, IntType.SCENERY, "use") { player, node ->
            val correctTask =
                getAttribute(player, DrillDemonUtils.DD_KEY_TASK, -1)
            if (correctTask == -1) {
                sendPlainDialogue(player, true, "Follow Sergeant Damien's orders!")
                return@on true
            }

            val task = DrillDemonUtils.getMatTask(node.id, player)
            val npc = NPC(NPCs.SERGEANT_DAMIEN_2790)
            val anim = DrillDemonUtils.animationForTask(task)

            lock(player, secondsToTicks(30))
            player.walkingQueue.reset()
            player.walkingQueue.addPath(node.location.x, 4820)
            queueScript(player, 0, QueueStrength.SOFT) { stage: Int ->
                when (stage) {
                    0 -> {
                        player.faceLocation(player.location.transform(0, -1, 0))
                        return@queueScript delayScript(player, 2)
                    }

                    1 -> {
                        animate(player, DrillDemonUtils.animationForTask(task))
                        when (task) {
                            DrillDemonUtils.DD_SIGN_JOG -> playAudio(player, Sounds.RUNONSPOT_2484, 0, 5)
                            DrillDemonUtils.DD_SIGN_SITUP -> playAudio(player, Sounds.SITUPS_2486, 40, 5)
                            DrillDemonUtils.DD_SIGN_PUSHUP -> playAudio(player, Sounds.PRESSUPS_2481, 25, 5)
                            DrillDemonUtils.DD_SIGN_JUMP -> playAudio(player, Sounds.STAR_JUMP_2492, 0, 5)
                        }
                        return@queueScript delayScript(player, anim.duration + 2)
                    }

                    2 -> {
                        DrillDemonUtils.changeSignsAndAssignTask(player)
                        if (task == correctTask) {
                            player.incrementAttribute(DrillDemonUtils.DD_CORRECT_COUNTER)
                            openDialogue(player, SergeantDamienDialogue(true), npc)
                        } else {
                            openDialogue(player, SergeantDamienDialogue(false), npc)
                        }
                        return@queueScript stopExecuting(player)
                    }

                    else -> return@queueScript stopExecuting(player)
                }
            }
            return@on true
        }
    }

    override fun defineAreaBorders(): Array<ZoneBorders> {
        return arrayOf(DrillDemonUtils.DD_AREA)
    }

    override fun getRestrictions(): Array<ZoneRestriction> {
        return arrayOf(ZoneRestriction.RANDOM_EVENTS, ZoneRestriction.CANNON, ZoneRestriction.FOLLOWERS)
    }

    override fun areaEnter(entity: Entity) {
        if (entity is Player) {
            entity.asPlayer().interfaceManager.closeDefaultTabs()
            entity.locks.lockTeleport(1000000)
            setComponentVisibility(entity.asPlayer(), Components.TOPLEVEL_548, 69, true)
            setComponentVisibility(entity.asPlayer(), 746, 12, true)
        }
    }
}
