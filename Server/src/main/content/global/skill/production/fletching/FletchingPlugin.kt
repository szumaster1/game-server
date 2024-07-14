package content.global.skill.production.fletching

import content.global.skill.production.fletching.data.Bolt
import content.global.skill.production.fletching.data.Dart
import content.global.skill.production.fletching.item.BoltPulse
import content.global.skill.production.fletching.item.DartPulse
import core.api.asItem
import core.api.consts.Items
import core.game.dialogue.SkillDialogueHandler
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.item.Item
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * @author: Ceikry(https://gitlab.com/Ceikry).
 */
@Initializable
class FletchingPlugin : UseWithHandler(Items.BRONZE_DART_TIP_819, Items.IRON_DART_TIP_820, Items.STEEL_DART_TIP_821, Items.MITHRIL_DART_TIP_822, Items.ADAMANT_DART_TIP_823, Items.RUNE_DART_TIP_824, Items.DRAGON_DART_TIP_11232, Items.BRONZE_BOLTS_UNF_9375, Items.BLURITE_BOLTS_UNF_9376, Items.IRON_BOLTS_UNF_9377, Items.SILVER_BOLTS_UNF_9382, Items.STEEL_BOLTS_UNF_9378, Items.MITHRIL_BOLTS_UNF_9379, Items.ADAMANT_BOLTS_UNF_9380, Items.RUNITE_BOLTS_UNF_9381, Items.BROAD_BOLTS_UNF_13279,) {

    override fun newInstance(arg: Any?): Plugin<Any> {
        addHandler(Items.FEATHER_314, ITEM_TYPE, this)
        addHandler(Items.STRIPY_FEATHER_10087, ITEM_TYPE, this)
        addHandler(Items.RED_FEATHER_10088, ITEM_TYPE, this)
        addHandler(Items.BLUE_FEATHER_10089, ITEM_TYPE, this)
        addHandler(Items.YELLOW_FEATHER_10090, ITEM_TYPE, this)
        addHandler(Items.ORANGE_FEATHER_10091, ITEM_TYPE, this)
        return this
    }

    override fun handle(event: NodeUsageEvent): Boolean {

        /*
            Handle darts.
         */

        if (Dart.isDart(event.usedItem.id)) {
            val dart = Dart.productMap[event.usedItem.id]
            val handler: SkillDialogueHandler =
                object : SkillDialogueHandler(event.player, SkillDialogue.MAKE_SET_ONE_OPTION, dart!!.finished.asItem()) {
                    override fun create(amount: Int, index: Int) {
                        player.pulseManager.run(DartPulse(event.player, event.usedItem, dart!!, amount))
                    }

                    override fun getAll(index: Int): Int {
                        return player.inventory.getAmount(event.usedItem)
                    }
                }
            handler.open()
            return true
        }

        /*
            Handle bolts.
         */

        if (Bolt.isBolt(event.usedItem.id) || Bolt.isBolt(event.usedWith.id)) {
            val bolt = if (Bolt.isBolt(event.usedItem.id)) Bolt.productMap[event.usedItem.id] else Bolt.productMap[event.usedWith.id]
            val featherId = if (Bolt.isBolt(event.usedItem.id)) event.usedWith.id else event.usedItem.id
            val hasFeather = (featherId == 314 || (featherId in 10087..10091))

            if (hasFeather) {
                val handler: SkillDialogueHandler =
                    object : SkillDialogueHandler(event.player, SkillDialogue.MAKE_SET_ONE_OPTION, bolt!!.finished.asItem()) {
                        override fun create(amount: Int, index: Int) {
                            player.pulseManager.run(BoltPulse(player, event.usedItem, bolt!!, Item(featherId), amount))
                        }

                        override fun getAll(index: Int): Int {
                            return player.inventory.getAmount(event.usedItem)
                        }
                    }
                handler.open()
                return true
            }
            return false
        }

        return false
    }

}
