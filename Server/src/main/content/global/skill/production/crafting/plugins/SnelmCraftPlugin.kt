package content.global.skill.production.crafting.plugins

import content.global.skill.production.crafting.item.SnelmCraftPulse
import core.api.consts.Items
import core.api.lock
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.plugin.Initializable
import core.plugin.Plugin

@Initializable
class SnelmCraftPlugin : UseWithHandler(Items.CHISEL_1755) {

    override fun newInstance(arg: Any?): Plugin<Any> {
        for (data in DATA) {
            for (item in data.indices) {
                addHandler(data[0], ITEM_TYPE, this)
            }
        }
        return this
    }

    override fun handle(event: NodeUsageEvent): Boolean {
        val player = event.player
        var snelm: IntArray? = null
        for (data in DATA) {
            for (item in data.indices) {
                if (data[0] == event.usedItem.id) {
                    snelm = data
                    break
                }
            }
        }
        if (snelm == null) {
            return false
        }
        lock(player, 1)
        player.pulseManager.run(SnelmCraftPulse(player, event.usedItem, snelm))
        return true
    }

    companion object {
        private val DATA = arrayOf(
            intArrayOf(Items.BLAMISH_MYRE_SHELL_3345, Items.MYRE_SNELM_3327),
            intArrayOf(Items.BLAMISH_MYRE_SHELL_3355, Items.MYRE_SNELM_3337),
            intArrayOf(Items.BLAMISH_OCHRE_SHELL_3349, Items.OCHRE_SNELM_3341),
            intArrayOf(Items.OCHRE_SNELM_3341, Items.BLAMISH_OCHRE_SHELL_3359),
            intArrayOf(Items.BLAMISH_RED_SHELL_3347, Items.BLOODNTAR_SNELM_3329),
            intArrayOf(Items.BLAMISH_RED_SHELL_3357, Items.BLOODNTAR_SNELM_3339),
            intArrayOf(Items.BLAMISH_BLUE_SHELL_3351, Items.BRUISE_BLUE_SNELM_3333),
            intArrayOf(Items.BLAMISH_BLUE_SHELL_3361, Items.SACRED_OIL3_3433),
            intArrayOf(Items.BLAMISH_BARK_SHELL_3353, Items.BROKEN_BARK_SNELM_3335)
        )
    }
}
