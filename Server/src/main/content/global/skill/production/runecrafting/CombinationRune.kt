package content.global.skill.production.runecrafting

import content.global.skill.production.runecrafting.data.Altar
import content.global.skill.production.runecrafting.data.Altar.Companion.forScenery
import content.global.skill.production.runecrafting.data.CombinationRune.Companion.forAltar
import content.global.skill.production.runecrafting.data.Rune
import content.global.skill.production.runecrafting.data.Talisman
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.scenery.Scenery
import core.plugin.Plugin

class CombinationRune : UseWithHandler(
    Talisman.AIR.talisman.id, Talisman.WATER.talisman.id, Talisman.EARTH.talisman.id, Talisman.FIRE.talisman.id,
    Rune.WATER.rune.id, Rune.EARTH.rune.id, Rune.AIR.rune.id, Rune.FIRE.rune.id
) {

    override fun newInstance(arg: Any?): Plugin<Any> {
        for (altar in Altar.values()) {
            addHandler(altar.scenery, OBJECT_TYPE, this)
        }
        return this
    }

    override fun handle(event: NodeUsageEvent): Boolean {
        val player = event.player
        val altar = forScenery((event.usedWith as Scenery))
        val combo = forAltar(altar!!, event.usedItem) ?: return false
        player.pulseManager.run(RunecraftingPulse(player, event.usedItem, altar, true, combo))
        return true
    }
}
