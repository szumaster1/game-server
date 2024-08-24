package content.global.skill.support.agility.shortcuts

import cfg.consts.Animations
import cfg.consts.NPCs
import cfg.consts.Scenery
import core.api.*
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.tools.END_DIALOGUE

/**
 * Represents the Trellis wall shortcut outside the Watchtower northern wall.
 *
 * Source [YouTube](https://youtu.be/8HyiUy6MwTM?si=BKLNWPjvEu6lf7Nu&t=146)
 */
class WatchtowerShortcut : InteractionListener {

    private val SHORTCUT_END_LOCATION = Location.create(2548, 3117, 1)
    private val CLIMB_STAIRS_ANIMATION = Animation.create(Animations.HUMAN_CLIMB_STAIRS_828)

    override fun defineListeners() {

        /*
         * Ladder that leads to the 1st floor, and is blocked by guards.
         */
        on(Scenery.LADDER_2833, IntType.SCENERY, "climb-up") { player, _ ->
            sendNPCDialogue(player, NPCs.TOWER_GUARD_877, "You can't go up there. That's private, that is.", FacialExpression.ANNOYED)
            return@on true
        }

        /*
         * Handling interactions with Town guards.
         */
        on(NPCs.TOWER_GUARD_877, IntType.NPC, "talk-to") { player, _ ->
            openDialogue(player, TowerGuardDialogue())
            return@on true
        }

        /*
         * Handle Climb to the 1st floor.
         */
        on(Scenery.TRELLIS_20056, IntType.SCENERY, "climb-up") { player, _ ->
            if (!hasLevelDyn(player, Skills.AGILITY, 18)) {
                sendDialogue(player, "You need an Agility level of at least 18 to negotiate this obstacle.")
                return@on false
            }
            executeClimb(player)
            return@on true
        }
    }

    /**
     * Executes the climbing action for the player.
     *
     * @param player The player who is attempting to use shortcut.
     */
    private fun executeClimb(player: Player) {
        lock(player, 2)
        sendMessage(player, "You climb up the wall...")
        sendMessageWithDelay(player, "...and squeeze in through the window.", 1)
        animate(player, CLIMB_STAIRS_ANIMATION)
        queueScript(player, 2, QueueStrength.SOFT) {
            teleport(player, SHORTCUT_END_LOCATION)
            rewardXP(player, Skills.AGILITY, 31.0)
            return@queueScript stopExecuting(player)
        }
    }

    /**
     * Represents the Tower guard dialogue.
     */
    private inner class TowerGuardDialogue : DialogueFile() {
        override fun handle(componentID: Int, buttonID: Int) {
            npc = NPC(NPCs.TOWER_GUARD_877)
            when (stage) {
                0 -> player("Hello. What are you doing here?").also { stage++ }
                1 -> npcl(FacialExpression.ANNOYED, "We are the tower guards - our business is our own!").also { stage = END_DIALOGUE }
            }
        }
    }
}
