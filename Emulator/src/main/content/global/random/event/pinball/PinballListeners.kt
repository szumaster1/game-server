package content.global.random.event.pinball

import core.api.*
import cfg.consts.Animations
import cfg.consts.Components
import cfg.consts.NPCs
import core.api.utils.PlayerCamera
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.world.map.Direction
import core.game.world.map.zone.ZoneBorders
import core.game.world.map.zone.ZoneRestriction
import core.tools.BLUE
import core.tools.RandomFunction

/**
 * Pinball listeners.
 */
class PinballListeners : InteractionListener, MapArea {

    init {
        PinballUtils.oldMan.init()
        PinballUtils.oldMan.isWalks = false
        PinballUtils.oldMan.isInvisible = false
        PinballUtils.oldMan.direction = Direction.EAST
    }

    override fun defineListeners() {
        on(PinballUtils.pillars, IntType.SCENERY, "tag") { player, node ->
            animate(player, Animations.HUMAN_MULTI_USE_832)
            if(node.id in PinballUtils.wrongPillars.indices) {
                setAttribute(player, PinballUtils.PINBALL_SCORE, 0)
                setInterfaceText(player, "Score: 0", Components.PINBALL_INTERFACE_263, 1)
                sendUnclosableDialogue(player, true, "", "Wrong post! Your score has been reset.", "Tag the post with the " + BLUE + "flashing rings" + ".")
            } else {
                player.incrementAttribute(PinballUtils.PINBALL_SCORE)
                setInterfaceText(player, "Score: " + getAttribute(player, PinballUtils.PINBALL_SCORE, this.toString()) + "", Components.PINBALL_INTERFACE_263, 1)
                sendUnclosableDialogue(player, true,"", "Well done! Now tag the next post.")
            }
            lock(player, 1)
            val random = RandomFunction.random(0,4)
            if (getAttribute(player, PinballUtils.PINBALL_SCORE, 0) < 10) {
                setAttribute(player, PinballUtils.GET_PILLAR, random)
                runTask(player, 1) {
                    PinballUtils.replaceTag(player)
                    PinballUtils.generateTag(player)
                }
            } else {
                runTask(player, 1) {
                    PinballUtils.replaceTag(player)
                    PlayerCamera(player).reset()
                }
                sendUnclosableDialogue(player, true, "", "Congratulations - you can now leave the arena.")
            }

            return@on true
        }

        on(PinballUtils.CAVE_EXIT, IntType.SCENERY, "exit") { player, _ ->
            openDialogue(player, PinballGuardDialogue(), PinballUtils.guards)
            return@on true
        }

        on(intArrayOf(NPCs.FLIPPA_3912,NPCs.TILT_3913), IntType.NPC, "Talk-to") { player, npc ->
            openDialogue(player, PinballGuardDialogue(), npc)
            return@on true
        }
    }

    override fun defineAreaBorders(): Array<ZoneBorders> {
        return arrayOf(ZoneBorders(1961, 5033,1982, 5054))
    }

    override fun getRestrictions(): Array<ZoneRestriction> {
        return arrayOf(ZoneRestriction.CANNON, ZoneRestriction.TELEPORT, ZoneRestriction.FOLLOWERS, ZoneRestriction.FIRES, ZoneRestriction.RANDOM_EVENTS)
    }

}
