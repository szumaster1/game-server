package content.global.handlers.iface.plugin

import core.api.*
import core.api.consts.Components
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.component.Component
import core.game.component.ComponentDefinition
import core.game.component.ComponentPlugin
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.item.Item
import core.plugin.Initializable
import core.plugin.Plugin

@Initializable
class SawmillPlankInterfacePlugin : ComponentPlugin() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        ComponentDefinition.put(Components.POH_SAWMILL_403, this)
        return this
    }

    override fun handle(player: Player, component: Component, opcode: Int, button: Int, slot: Int, itemId: Int): Boolean {
        val plank: Plank? = when {
            button > 101 && button < 108 -> Plank.WOOD
            button > 108 && button < 114 -> Plank.OAK
            button > 114 && button < 120 -> Plank.TEAK
            button > 120 && button < 126 -> Plank.MAHOGANY
            else -> null
        }
        var amount = -1
        val fullIndex = if (plank == Plank.WOOD) 107 else if (plank == Plank.OAK) 113 else if (plank == Plank.TEAK) 119 else 125
        val difference = if (plank != Plank.WOOD) fullIndex - button else fullIndex - button - if (button != 107) 1 else 0
        when (difference) {
            0 -> amount = 1
            1 -> amount = 5
            2 -> amount = 10
            3 -> amount = 69
            4 -> amount = player.inventory.getAmount(plank?.log)
        }
        if (amount == 69) {
            val planks = plank
            sendInputDialogue(player, true, "Enter the amount:") { value ->
                create(player, planks!!, value as Int)
            }
            return true
        }
        if (plank != null) {
            create(player, plank, amount)
        }
        return true
    }

    private fun create(player: Player, plank: Plank, amount: Int) {
        player.interfaceManager.close()
        var amount = amount
        if (amount > player.inventory.getAmount(plank.log)) {
            amount = player.inventory.getAmount(plank.log)
        }
        if (!player.inventory.containsItem(Item(plank.log.id))) {
            sendNPCDialogue(player, NPCs.SAWMILL_OPERATOR_4250, "You'll need to bring me some more logs.")
            return
        }
        if (!player.inventory.contains(995, plank.price * amount)) {
            sendDialogue(player, "Sorry, I don't have enough coins to pay for that.")
            return
        }
        if (player.inventory.remove(Item(995, plank.price * amount))) {
            if (plank == Plank.WOOD) {
                finishDiaryTask(player, DiaryType.VARROCK, 0, 3)
            }
            if (plank == Plank.MAHOGANY && amount >= 20) {
                finishDiaryTask(player, DiaryType.VARROCK, 1, 15)
            }
            val remove = plank.log
            remove.amount = amount
            if (removeItem(player, remove)) {
                val planks = plank.plank
                planks.amount = amount
                player.inventory.add(planks)
            }
        }
    }
}

enum class Plank(val log: Item, val plank: Item, val price: Int) {
    WOOD(Item(Items.LOGS_1511), Item(Items.PLANK_960), 100),
    OAK(Item(Items.OAK_LOGS_1521), Item(Items.OAK_PLANK_8778), 250),
    TEAK(Item(Items.TEAK_LOGS_6333), Item(Items.TEAK_PLANK_8780), 500),
    MAHOGANY(Item(Items.MAHOGANY_LOGS_6332), Item(Items.MAHOGANY_PLANK_8782), 1500)
}
