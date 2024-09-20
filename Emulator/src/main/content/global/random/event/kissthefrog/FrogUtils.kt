package content.global.random.event.kissthefrog

import core.api.*
import org.rs.consts.Animations
import org.rs.consts.Components
import org.rs.consts.Items
import org.rs.consts.NPCs
import core.game.dialogue.FacialExpression
import core.game.node.Node
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic

/**
 * Frog utils.
 */
object FrogUtils {
    const val ATTRIBUTE_FROG_RANDOM_EVENT = "frog:task"
    const val ATTRIBUTE_FROG_LOCATION = "/save:original-loc"
    const val ATTRIBUTE_FROG_LOGOUT = "frog:logout"
    const val ATTRIBUTE_FROG_TASK_FAIL = "frog:fail"

    const val FROG_NPC = NPCs.FROG_2469
    const val FROG_APPEARANCE_NPC = NPCs.FROG_2473
    const val FROG_PRINCE_NPC = NPCs.FROG_PRINCE_2474
    const val FROG_PRINCESS_NPC = NPCs.FROG_PRINCESS_2475

    const val TRANSFORMATION_ANIM = Animations.GET_INTO_FROG_POSITION_2377
    const val FIRST_PHASE_TRANSFORM_ANIM = 2374
    const val SECOND_PHASE_TRANSFORM_ANIM = 2375
    const val BLOW_KISS_ANIM = 1374

    const val BLOW_KISS_GFX = 1702

    /**
     * Cleanup.
     *
     * @param [player] The player to clean up after the frog event.
     */
    fun cleanup(player: Player) {
        player.properties.teleportLocation = getAttribute(player, ATTRIBUTE_FROG_LOCATION, null)
        restoreTabs(player)
        player.appearance.transformNPC(-1)
        clearLogoutListener(player, ATTRIBUTE_FROG_LOGOUT)
        removeAttributes(player, ATTRIBUTE_FROG_RANDOM_EVENT, ATTRIBUTE_FROG_TASK_FAIL, ATTRIBUTE_FROG_LOCATION, ATTRIBUTE_FROG_LOGOUT)
        unlock(player)
    }

    /**
     * Kiss the frog.
     *
     * @param [player]  the player who is kissing the frog.
     * @param [node]    the frog NPC node.
     */
    fun kissTheFrog(player: Player, node: Node) {
        val npc = node as NPC
        lock(player, 100)
        lockInteractions(player, 100)
        submitIndividualPulse(player, object : Pulse() {
            var counter = 0
            override fun pulse(): Boolean {
                when (counter++) {
                    1 -> {
                        face(player, npc)
                        face(npc, player)

                        animate(player, Animation(TRANSFORMATION_ANIM))
                        animate(npc, Animation(FIRST_PHASE_TRANSFORM_ANIM))
                    }

                    2 -> animate(npc, Animation(SECOND_PHASE_TRANSFORM_ANIM))

                    3 -> {
                        findLocalNPC(player, FROG_NPC)!!.transform(if (player.isMale) FROG_PRINCESS_NPC else FROG_PRINCE_NPC)
                        visualize(npc, -1, Graphic(85, 100))
                    }

                    5 -> {
                        player.dialogueInterpreter.sendDialogues(
                            if (player.isMale) FROG_PRINCESS_NPC else FROG_PRINCE_NPC,
                            FacialExpression.HAPPY,
                            "Thank you so much, ${player.username}.",
                            "I must return to my fairy tale kingdom now, but I will",
                            "leave you a reward for your kindness.",
                        )
                    }

                    6 -> visualize(npc, BLOW_KISS_ANIM, BLOW_KISS_GFX)
                    8 -> visualize(npc, BLOW_KISS_ANIM, BLOW_KISS_GFX)
                    9 -> openInterface(player, Components.FADE_TO_BLACK_120)
                    12 -> {
                        closeInterface(player)
                        npc.reTransform()
                        cleanup(player)
                        reward(player)
                        openInterface(player, Components.FADE_FROM_BLACK_170)
                        sendMessage(player, "You've received a random event gift!")
                    }
                }
                return false
            }
        })
        return
    }

    /**
     * Get the reward.
     *
     * @param [player] The player to reward.
     */
    fun reward(player: Player) {
        addItemOrDrop(player, Items.FROG_TOKEN_6183)
    }
}
