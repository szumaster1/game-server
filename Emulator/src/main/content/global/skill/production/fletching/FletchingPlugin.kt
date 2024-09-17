package content.global.skill.production.fletching

import org.rs.consts.Items
import content.global.skill.production.fletching.data.Bolt
import content.global.skill.production.fletching.data.Dart
import content.global.skill.production.fletching.item.BoltPulse
import content.global.skill.production.fletching.item.DartPulse
import core.api.amountInInventory
import core.api.asItem
import core.api.submitIndividualPulse
import core.game.dialogue.SkillDialogueHandler
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.item.Item
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Represents the plugin used to open the fletch dialogue.
 */
@Initializable
class FletchingPlugin : UseWithHandler(
    Items.BRONZE_DART_TIP_819, Items.IRON_DART_TIP_820, Items.STEEL_DART_TIP_821,
    Items.MITHRIL_DART_TIP_822, Items.ADAMANT_DART_TIP_823, Items.RUNE_DART_TIP_824,
    Items.DRAGON_DART_TIP_11232, Items.BRONZE_BOLTS_UNF_9375, Items.BLURITE_BOLTS_UNF_9376,
    Items.IRON_BOLTS_UNF_9377, Items.SILVER_BOLTS_UNF_9382, Items.STEEL_BOLTS_UNF_9378,
    Items.MITHRIL_BOLTS_UNF_9379, Items.ADAMANT_BOLTS_UNF_9380, Items.RUNITE_BOLTS_UNF_9381,
    Items.BROAD_BOLTS_UNF_13279
) {

    override fun newInstance(arg: Any?): Plugin<Any> {
        listOf(
            Items.FEATHER_314, Items.STRIPY_FEATHER_10087, Items.RED_FEATHER_10088,
            Items.BLUE_FEATHER_10089, Items.YELLOW_FEATHER_10090, Items.ORANGE_FEATHER_10091
        ).forEach { addHandler(it, ITEM_TYPE, this) }
        return this
    }

    override fun handle(event: NodeUsageEvent): Boolean {
        return when {
            Dart.isDart(event.usedItem.id) -> handleDart(event)
            Bolt.isBolt(event.usedItem.id) || Bolt.isBolt(event.usedWith.id) -> handleBolt(event)
            else -> false
        }
    }

    private fun handleDart(event: NodeUsageEvent): Boolean {
        val dart = Dart.productMap[event.usedItem.id] ?: return false
        val handler = createSkillDialogueHandler(event, dart.finished.asItem()) { amount ->
            submitIndividualPulse(event.player, DartPulse(event.player, event.usedItem, dart, amount))
        }
        handler.open()
        return true
    }

    private fun handleBolt(event: NodeUsageEvent): Boolean {
        val bolt = when {
            Bolt.isBolt(event.usedItem.id) -> Bolt.productMap[event.usedItem.id]
            else -> Bolt.productMap[event.usedWith.id]
        } ?: return false

        val featherId = if (Bolt.isBolt(event.usedItem.id)) event.usedWith.id else event.usedItem.id
        val hasFeather = featherId in listOf(Items.FEATHER_314) || (featherId in 10087..10091)

        if (hasFeather) {
            val handler = createSkillDialogueHandler(event, bolt.finished.asItem()) { amount ->
                submitIndividualPulse(event.player, BoltPulse(event.player, event.usedItem, bolt, Item(featherId), amount))
            }
            handler.open()
            return true
        }
        return false
    }

    private fun createSkillDialogueHandler(event: NodeUsageEvent, finishedItem: Item, createAction: (Int) -> Unit): SkillDialogueHandler {
        return object : SkillDialogueHandler(event.player, SkillDialogue.MAKE_SET_ONE_OPTION, finishedItem) {
            override fun create(amount: Int, index: Int) {
                createAction(amount)
            }

            override fun getAll(index: Int): Int {
                return amountInInventory(player, event.usedItem.id)
            }
        }
    }
}
