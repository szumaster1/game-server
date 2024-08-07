package content.global.skill.production.runecrafting.item.pouch

import core.api.consts.Items
import core.cache.def.impl.ItemDefinition
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Plugin

/**
 * Rune pouch plugin.
 */
class RunePouchPlugin : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any>? {
        for (i in 5509..5515) {
            val itemDef = ItemDefinition.forId(i)
            itemDef.handlers["option:fill"] = this
            itemDef.handlers["option:empty"] = this
            itemDef.handlers["option:check"] = this
            itemDef.handlers["option:drop"] = this
        }
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        val rEssAmt = player.inventory.getAmount(Items.RUNE_ESSENCE_1436)
        val pEssAmt = player.inventory.getAmount(Items.PURE_ESSENCE_7936)

        val preferenceFlag = if (rEssAmt > pEssAmt) 0 else 1

        val essence = Item(
            if (preferenceFlag == 0) Items.RUNE_ESSENCE_1436 else Items.PURE_ESSENCE_7936,
            if (preferenceFlag == 0) rEssAmt else pEssAmt
        )

        when (option) {
            "fill" -> player.pouchManager.addToPouch(node.id, essence.amount, essence.id)
            "empty" -> player.pouchManager.withdrawFromPouch(node.id)
            "check" -> player.pouchManager.checkAmount(node.id)
            "drop" -> player.dialogueInterpreter.open(9878, Item(node.id))
        }
        return true
    }

    override fun isWalk(): Boolean {
        return false
    }
}
