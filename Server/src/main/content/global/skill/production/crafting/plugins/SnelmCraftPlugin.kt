package content.global.skill.production.crafting.plugins

import content.global.skill.production.crafting.item.SnelmCraftPulse
import core.api.consts.Items
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.plugin.Initializable
import core.plugin.Plugin

@Initializable
class SnelmCraftPlugin : UseWithHandler(Items.CHISEL_1755) {

    override fun newInstance(arg: Any?): Plugin<Any> {
        for (datum in DATA) {
            for (k in datum.indices) {
                addHandler(datum[0], ITEM_TYPE, this)
            }
        }
        return this
    }

    override fun handle(event: NodeUsageEvent): Boolean {
        val player = event.player
        var snelm: IntArray? = null
        for (datum in DATA) {
            for (k in datum.indices) {
                if (datum[0] == event.usedItem.id) {
                    snelm = datum
                    break
                }
            }
        }
        if (snelm == null) {
            return false
        }
        player.lock(1)
        player.pulseManager.run(SnelmCraftPulse(player, event.usedItem, snelm))
        return true
    }

    companion object {
        private val DATA = arrayOf(
            intArrayOf(3345, 3327),
            intArrayOf(3355, 3337),
            intArrayOf(3349, 3341),
            intArrayOf(3341, 3359),
            intArrayOf(3347, 3329),
            intArrayOf(3357, 3339),
            intArrayOf(3351, 3333),
            intArrayOf(3361, 3343),
            intArrayOf(3353, 3335)
        )
    }
}
