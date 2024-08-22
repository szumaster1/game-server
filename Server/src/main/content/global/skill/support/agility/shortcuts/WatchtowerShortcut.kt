package content.global.skill.support.agility.shortcuts

import core.api.*
import cfg.consts.Animations
import cfg.consts.NPCs
import cfg.consts.Scenery
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.npc.NPC
import core.game.node.entity.skill.Skills
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.tools.END_DIALOGUE

/**
 * Represents the Watchtower shortcut interaction.
 */
class WatchtowerShortcut : InteractionListener {

    /*
     * Name: Trellis wall obstacle.
     * Location: Outside of the Watchtower northern wall.
     * Source: https://youtu.be/8HyiUy6MwTM?si=BKLNWPjvEu6lf7Nu&t=146
     * Required level: 18
     * Scenery: 20056
     * Reward: 31xp
     * Image: https://i.imgur.com/HBMOccq.png
     */

    private val shortcutDestination = Location.create(2548, 3117, 1)
    private val climbStairsAnimation = Animation.create(Animations.HUMAN_CLIMB_STAIRS_828)

    override fun defineListeners() {

        on(Scenery.LADDER_2833, IntType.SCENERY, "climb-up") { player, _ ->
            sendNPCDialogue(player, NPCs.TOWER_GUARD_877, "You can't go up there. That's private, that is.", FacialExpression.ANNOYED)
            return@on true
        }

        on(NPCs.TOWER_GUARD_877, IntType.NPC, "talk-to") { player, _ ->
            openDialogue(player, object : DialogueFile() {
                override fun handle(componentID: Int, buttonID: Int) {
                    npc = NPC(NPCs.TOWER_GUARD_877)
                    when (stage) {
                        0 -> player("Hello. What are you doing here?").also { stage++ }
                        1 -> npcl(FacialExpression.ANNOYED, "We are the tower guards - our business is our own!").also { stage = END_DIALOGUE }
                    }
                }
            })
            return@on true
        }

        on(Scenery.TRELLIS_20056, IntType.SCENERY, "climb-up") { player, _ ->

            if (!hasLevelDyn(player, Skills.AGILITY, 18)) {
                sendDialogue(player, "You need an Agility level of at least 18 to negotiate this obstacle.")
                return@on true
            }

            lock(player, 3)
            sendMessage(player, "You climb up the wall...")
            sendMessage(player, "...and squeeze in through the window.")
            animate(player, climbStairsAnimation)
            queueScript(player, 2, QueueStrength.SOFT) {
                teleport(player, shortcutDestination)
                rewardXP(player, Skills.AGILITY, 31.0)
                return@queueScript stopExecuting(player)
            }
            return@on true
        }
    }

}
