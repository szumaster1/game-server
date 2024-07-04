package content.region.morytania.quest.ghostsahoy

import core.api.*
import core.api.consts.Animations
import core.api.consts.Components
import core.api.consts.Items
import core.api.consts.Sounds
import core.game.component.Component
import core.game.interaction.QueueStrength
import core.game.node.entity.combat.ImpactHandler
import core.game.node.entity.player.Player
import core.tools.RandomFunction

object GAUtils {

    val signatures = 0

    // Ship model items:
    const val shipModel = Items.MODEL_SHIP_4253
    const val silkShipModel = Items.MODEL_SHIP_4254

    // Attribute indicating that ship is valid.
    val rightShip = "/save:ghostsahoy:right-ship"

    // Attribute for update model ship.
    val colorMatching = "/save:ghostsahoy:paint-ship"

    // Attribute for collecting all map scraps.
    val lastMapScrap = "/save:ghostahoy:map-scrap"

    // Attribute for changing wind speed in WindSpeedInterface.
    val windSpeed = "/save:ghostsahoy:windspeed"

    // Attribute for register logout
    val logout = "/save:ghostsahoy:bedsheet-costume"
    val petitionsigns = "/save:ghostsahoy:petition"
    val petitionstart = "/save:ghostsahoy:petition-start"
    val petitioncomplete = "/save:ghostsahoy:petition-complete"
    val getSignedBow = "/save:ghostsahoy:signed-bow"
    val shipFlag = "/save:ghostsahoy:ship-flag"
    val shipBottom = "/save:ghostsahoy:ship-bottom"
    val shipSkull = "/save:ghostsahoy:ship-skull"

    fun jumpRockPath(player: Player) {
        queueScript(player, 1, QueueStrength.SOFT) {
            if (RandomFunction.getRandom(3) == 2) {
                player.impactHandler.manualHit(player, 1, ImpactHandler.HitsplatType.NORMAL)
                animate(player, Animations.HUMAN_JUMP_FAIL_767)
                playGlobalAudio(player.location, Sounds.JUMP_AND_FALL_2463)
            } else {
                animate(player, Animations.HUMAN_JUMP_SHORT_GAP_741)
                playGlobalAudio(player.location, Sounds.JUMP2_2462)
            }
            return@queueScript stopExecuting(player)
        }
    }

    fun collectSignature(player: Player) {
        player.incrementAttribute(petitionsigns)
        sendItemDialogue(player, Items.PETITION_FORM_4283, "The ghost signs your petition. You have obtained ${getAttribute(player, petitionsigns, signatures.toString())} " + if (getAttribute(player, petitionsigns, 0) == 1) "signature so far." else "signature's so far.")
        if (getAttribute(player, petitionsigns, 0) > 9) {
            setAttribute(player, petitioncomplete, true)
            setQuestStage(player, "Ghosts Ahoy", 50)
            removeItem(player, Items.PETITION_FORM_4283)
            removeAttributes(player, petitionstart, petitionsigns)
            sendItemDialogue(player, Items.PETITION_FORM_4283, "You have succeeded in obtaining 10 signatures on the petition form!")
        }
    }

    fun travelToDragontoothIsland(player: Player) {
        queueScript(player, 1, QueueStrength.SOFT) { qstage: Int ->
            when (qstage) {
                0 -> {
                    openInterface(player, Components.AHOY_BLACKOUT_7)
                    return@queueScript delayScript(player, 5)
                }

                1 -> {
                    teleport(player, location(3792, 3559, 0))
                    player.interfaceManager.close(Component(Components.AHOY_BLACKOUT_7))
                    return@queueScript stopExecuting(player)
                }

                else -> return@queueScript stopExecuting(player)
            }
        }
    }

    fun travelToPortPhasmatys(player: Player) {
        queueScript(player, 1, QueueStrength.SOFT) { qstage: Int ->
            when (qstage) {
                0 -> {
                    openInterface(player, Components.AHOY_BLACKOUT_7)
                    return@queueScript delayScript(player, 5)
                }

                1 -> {
                    teleport(player, location(3702, 3487, 0))
                    player.interfaceManager.close(Component(Components.AHOY_BLACKOUT_7))
                    return@queueScript stopExecuting(player)
                }

                else -> return@queueScript stopExecuting(player)
            }
        }
    }

}
