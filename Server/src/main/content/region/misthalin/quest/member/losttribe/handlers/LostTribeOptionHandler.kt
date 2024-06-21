package content.region.misthalin.quest.member.losttribe.handlers

import core.api.consts.Components
import core.api.consts.Items
import core.api.*
import core.cache.def.impl.ItemDefinition
import core.cache.def.impl.NPCDefinition
import core.cache.def.impl.SceneryDefinition
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.plugin.Plugin

@Initializable
class LostTribeOptionHandler : OptionHandler() {
    override fun newInstance(arg: Any?): Plugin<Any> {
        ItemDefinition.forId(5008).handlers["option:look-at"] = this
        ItemDefinition.forId(5009).handlers["option:read"] = this
        SceneryDefinition.forId(6916).handlers["option:search"] = this
        SceneryDefinition.forId(6911).handlers["option:search"] = this
        NPCDefinition.forId(2084).handlers["option:follow"] = this
        NPCDefinition.forId(2086).handlers["option:follow"] = this
        return this
    }

    override fun handle(player: Player?, node: Node?, option: String?): Boolean {
        player ?: return false
        node ?: return false
        val hasAnBook = hasAnItem(player, Items.GOBLIN_SYMBOL_BOOK_5009).container != null
        when (node.id) {
            5008 -> openInterface(player, Components.BROOCH_VIEW_50)
            5009 -> openInterface(player, Components.GOBLIN_SYMBOL_BOOK_183)
            6916 -> {
                if (!hasAnBook && getQuestStage(player, "Lost Tribe") >= 41) {
                    sendDialogue(player, "'A History of the Goblin Race.' This must be it.")
                    addItemOrDrop(player, Items.GOBLIN_SYMBOL_BOOK_5009)
                } else {
                    return false
                }
            }

            6911 -> {
                if (!inInventory(player, Items.SILVERWARE_5011) && getQuestStage(player, "Lost Tribe") == 48) {
                    sendItemDialogue(player, Items.SILVERWARE_5011, "You find the missing silverware!")
                    addItemOrDrop(player, Items.SILVERWARE_5011)
                    setQuestStage(player, "Lost Tribe", 49)
                } else {
                    sendMessage(player, "You find nothing.")
                }
            }
        }

        if (option.equals("follow")) {
            when (node.id) {
                2084 -> GoblinFollower.sendToLumbridge(player)
                2085 -> GoblinFollower.sendToMines(player)
            }
        }
        return true
    }

}