package content.global.handlers.item

import core.api.consts.Graphics
import core.api.consts.Items
import core.api.getUsedOption
import core.api.sendDialogueOptions
import core.api.setTitle
import core.game.dialogue.DialogueFile
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.update.flag.context.Graphic

/**
 * Staff of the raven
 *
 * @constructor Staff of the raven
 */
class StaffOfTheRaven : InteractionListener {

    private val staffOfTheRaven = intArrayOf(
        Items.STAFF_OF_THE_RAVEN_DEFAULT_14654,
        Items.STAFF_OF_THE_RAVEN_TIER1_14655,
        Items.STAFF_OF_THE_RAVEN_TIER2_14656
    )

    override fun defineListeners() {
        on(staffOfTheRaven, ITEM, "recolor", "operate") { player, node ->
            val isBase = node.id == Items.STAFF_OF_THE_RAVEN_DEFAULT_14654
            val isOperate = getUsedOption(player) == "operate"

            if (!isBase) {
                switchStaff(player, Items.STAFF_OF_THE_RAVEN_DEFAULT_14654, isOperate, node.asItem())
                return@on true
            }

            player.dialogueInterpreter.open(object : DialogueFile() {
                override fun handle(componentID: Int, buttonID: Int) {
                    when (stage) {
                        0 -> {
                            setTitle(player, 2)
                            sendDialogueOptions(player, "what color you want to choose", "Purple", "Orange.").also { stage++ }
                        }
                        1 -> when (buttonID) {
                            1 -> switchStaff(player, Items.STAFF_OF_THE_RAVEN_TIER1_14655, isOperate, node.asItem())
                            2 -> switchStaff(player, Items.STAFF_OF_THE_RAVEN_TIER2_14656, isOperate, node.asItem())
                            else -> {}
                        }.also { end() }
                    }
                }
            })
            return@on true
        }
    }

    /**
     * Switch staff
     *
     * @param player
     * @param to
     * @param equipped
     * @param original
     */
    fun switchStaff(player: Player, to: Int?, equipped: Boolean, original: Item) {
        player.graphics(Graphic(Graphics.IMPLING_TP_WHITE_SMOKE_PUFF_1119))
        val item: Item? = if (to != null) Item(to) else to

        if (equipped) {
            player.equipment.replace(item, original.slot)
        } else {
            player.inventory.replace(item, original.slot)
        }
    }
}