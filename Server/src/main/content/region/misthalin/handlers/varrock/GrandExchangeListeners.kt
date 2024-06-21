package content.region.misthalin.handlers.varrock

import content.global.interaction.iface.ExchangeItemSetsListener
import content.global.interaction.iface.StockMarketInterfaceListener
import core.api.getUsedOption
import core.api.openDialogue
import core.game.ge.GEGuidePrice
import core.game.ge.GrandExchangeRecords
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.api.consts.NPCs
import core.api.consts.Scenery

class GrandExchangeListeners : InteractionListener {

    companion object {
        private val CLERK = intArrayOf(6531, 6529, 6530, 6528)
    }

    override fun defineDestinationOverrides() {
        setDest(IntType.NPC, CLERK, "talk-to", "exchange", "history", "sets") { _, node ->
            val npc = node.asNpc()
            return@setDest npc.location.transform(node.direction, 1)
        }
    }

    override fun defineListeners() {

        /*
            Interaction with NPC (clerks) at Grand Exchange.
         */

        on(CLERK, IntType.NPC, "talk-to", "exchange", "history", "sets") { player, node ->
            val records = GrandExchangeRecords.getInstance()
            if (getUsedOption(player) == "talk-to") {
                val npc = node as NPC
                player.dialogueInterpreter.open(npc.id, npc)
            }
            if (getUsedOption(player) == "exchange") StockMarketInterfaceListener.openFor(player)
            if (getUsedOption(player) == "history") records.openHistoryLog(player)
            if (getUsedOption(player) == "sets") ExchangeItemSetsListener.openFor(player)
            return@on true
        }

        /*
            Interaction with desk (between players and clerks) at Grand Exchange.
         */

        on(Scenery.DESK_28089, IntType.SCENERY, "use", "exchange", "collect", "history") { player, _ ->
            val records = GrandExchangeRecords.getInstance(player)
            when (getUsedOption(player)) {
                "use" -> openDialogue(player, 6528)
                "exchange" -> StockMarketInterfaceListener.openFor(player)
                "collect" -> records.openCollectionBox()
                "history" -> records.openHistoryLog(player)
            }
            return@on true
        }

        /*
            Interaction with NPCs around Grand Exchange.
         */

        on(NPCs.FARID_MORRISANE_ORES_6523, IntType.NPC, "info-ores") { player, _ ->
            GEGuidePrice.open(player, GEGuidePrice.GuideType.ORES)
            return@on true
        }

        on(NPCs.BOB_BARTER_HERBS_6524, IntType.NPC, "info-herbs") { player, _ ->
            GEGuidePrice.open(player, GEGuidePrice.GuideType.HERBS)
            return@on true
        }

        on(NPCs.MURKY_MATT_RUNES_6525, IntType.NPC, "info-runes") { player, _ ->
            GEGuidePrice.open(player, GEGuidePrice.GuideType.RUNES)
            return@on true
        }

        on(NPCs.RELOBO_BLINYO_LOGS_6526, IntType.NPC, "info-logs") { player, _ ->
            GEGuidePrice.open(player, GEGuidePrice.GuideType.LOGS)
            return@on true
        }

        on(NPCs.HOFUTHAND_ARMOUR_AND_WEAPONS_6527, IntType.NPC, "info-combat") { player, _ ->
            GEGuidePrice.open(player, GEGuidePrice.GuideType.WEAPONS_AND_ARMOUR)
            return@on true
        }
    }
}
