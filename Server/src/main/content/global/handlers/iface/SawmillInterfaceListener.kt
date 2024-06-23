package content.global.handlers.iface

import core.api.*
import core.api.consts.Components
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.interaction.InterfaceListener
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.item.Item

enum class Plank(val log: Item, val plank: Item, val price: Int) {
    WOOD(Item(Items.LOGS_1511), Item(Items.PLANK_960), 100),
    OAK(Item(Items.OAK_LOGS_1521), Item(Items.OAK_PLANK_8778), 250),
    TEAK(Item(Items.TEAK_LOGS_6333), Item(Items.TEAK_PLANK_8780), 500),
    MAHOGANY(Item(Items.MAHOGANY_LOGS_6332), Item(Items.MAHOGANY_PLANK_8782), 1500)
}

class SawmillInterfaceListener : InterfaceListener {

    companion object {

        private const val SAWMILL_PLANKS = Components.POH_SAWMILL_403

        fun create(player: Player, plank: Plank, amount: Int) {
            var amount = amount
            closeInterface(player)
            if (amount > amountInInventory(player, plank.log.amount)) {
                amount = amountInInventory(player, plank.log.amount)
            }
            if (!inInventory(player, plank.log.id)) {
                sendNPCDialogue(player, NPCs.SAWMILL_OPERATOR_4250, "You'll need to bring me some more logs.")
                return
            }
            if (!inInventory(player, Items.COINS_995, plank.price * amount)) {
                sendPlayerDialogue(player, "Sorry, I don't have enough coins to pay for that.")
                return
            }
            if (removeItem(player, Item(Items.COINS_995, plank.price * amount))) {
                if (plank == Plank.WOOD) {
                    player.achievementDiaryManager.finishTask(player, DiaryType.VARROCK, 0, 3)
                }
                if (plank == Plank.MAHOGANY && amount >= 20) {
                    player.achievementDiaryManager.finishTask(player, DiaryType.VARROCK, 1, 15)
                }
                val remove = plank.log
                remove.amount = amount
                if (player.inventory.remove(remove)) {
                    val planks = plank.plank
                    planks.amount = amount
                    player.inventory.add(planks)
                }
            }
        }

    }

    override fun defineInterfaceListeners() {
        on(SAWMILL_PLANKS) { player, _, _, buttonID, _, _ ->
            var plank: Plank? = null
            if (buttonID in 102..107) {
                plank = Plank.WOOD
            } else if (buttonID in 109..113) {
                plank = Plank.OAK
            } else if (buttonID in 115..119) {
                plank = Plank.TEAK
            } else if (buttonID in 121..125) {
                plank = Plank.MAHOGANY
            }

            var amount = -1
            val fullIndex =
                if (plank == Plank.WOOD) 107 else if (plank == Plank.OAK) 113 else if (plank == Plank.TEAK) 119 else 125

            var difference = -1
            difference = if (plank != Plank.WOOD) {
                fullIndex - buttonID
            } else {
                fullIndex - buttonID - (if (buttonID != 107) 1 else 0)
            }
            when (difference) {
                0 -> amount = 1
                1 -> amount = 5
                2 -> amount = 10
                3 -> amount = 69
                4 -> amount = player.inventory.getAmount(plank!!.log)
            }
            if (amount == 69) {
                val planks = plank
                sendInputDialogue(player, true, "Enter the amount:") { value: Any ->
                    create(player, planks!!, value as Int)
                }
                return@on true
            }
            if (plank != null) {
                create(player, plank, amount)
            }
            return@on true
        }
    }
}
