package content.region.asgarnia.falador.quest.fortress.handlers

import cfg.consts.Animations
import cfg.consts.Graphics
import cfg.consts.Items
import cfg.consts.Scenery
import core.api.*
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.world.update.flag.context.Graphic

class BlackKnightsFortressListeners : InteractionListener {

    private val listenAnimation = Animations.ON_GROUND_LISTENING_TO_DOOR_4195
    private val lowerAnimation = Animations.CROUCHED_HIDING_POSITION_4552
    private val firstAnimation = Animations.READ_NOTE_WHILE_SHAKING_HEAD_4549
    private val lastAnimation = Animations.BURN_NOTE_4551
    private val smokeGraphics = Graphic(Graphics.RANDOM_EVENT_PUFF_OF_SMOKE_86, 109, 1)

    override fun defineListeners() {

        /*
         * Handles cabbage on hole.
         */

        onUseWith(IntType.SCENERY, intArrayOf(Items.CABBAGE_1965, Items.CABBAGE_1967), Scenery.HOLE_2336) { player, used, _ ->
            if (getQuestStage(player, "Black Knights' Fortress") >= 20) {
                if (used.id == Items.CABBAGE_1967) {
                    sendDialogue(player, "This is the wrong sort of cabbage!")
                    return@onUseWith false
                }
                openDialogue(player, 992752973, true, true)
                return@onUseWith true
            } else {
                sendPlayerDialogue(player, "Why exactly would I want to do that?", FacialExpression.THINKING)
            }
            return@onUseWith true
        }

        /*
         * Listen-at the grill interaction.
         */

        on(Scenery.GRILL_2342, IntType.SCENERY, "listen-at") { player, node ->
            animate(player, listenAnimation)
            queueScript(player, 2, QueueStrength.SOFT) {
                animate(player, lowerAnimation)
                player.dialogueInterpreter.open(992752973)
                return@queueScript stopExecuting(player)
            }
            return@on true
        }
    }

}