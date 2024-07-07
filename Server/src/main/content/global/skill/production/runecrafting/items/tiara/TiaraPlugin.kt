package content.global.skill.production.runecrafting.items.tiara

import content.global.skill.production.runecrafting.data.MysteriousRuin.Companion.forTalisman
import content.global.skill.production.runecrafting.data.Tiara.Companion.forItem
import core.api.setVarbit
import core.api.setVarp
import core.cache.def.impl.ItemDefinition
import core.cache.def.impl.SceneryDefinition
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Plugin

class TiaraPlugin : Plugin<Any?> {

    override fun newInstance(arg: Any?): Plugin<Any?> {
        for (i in IDS) {
            ItemDefinition.forId(i).handlers["equipment"] = this
        }
        return this
    }

    override fun fireEvent(identifier: String, vararg args: Any): Any {
        val player = args[0] as Player
        val item = args[1] as Item
        when (identifier) {
            "equip" -> {
                val ruin = forTalisman(forItem(item)!!.talisman!!)
                setVarbit(player, SceneryDefinition.forId(ruin!!.scenery[0]).varbitID, 1, true)
            }

            "unequip" -> {
                val other = if (args.size == 2) null else args[2] as Item
                if (other != null) {
                    val tiara = forItem(other)
                    if (tiara != null) {
                        val r = forTalisman(tiara.talisman!!)
                        setVarbit(player, SceneryDefinition.forId(r!!.scenery[0]).varbitID, 1, true)
                    }
                }
                setVarp(player, CONFIG, 0)
            }
        }
        return true
    }

    companion object {
        private const val CONFIG = 491
        private val IDS = intArrayOf(5527, 5529, 5531, 5533, 5535, 5537, 5539, 5541, 5543, 5545, 5547, 5549)
    }
}
