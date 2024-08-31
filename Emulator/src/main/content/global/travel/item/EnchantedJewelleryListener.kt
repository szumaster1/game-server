package content.global.travel.item

import cfg.consts.Items
import core.api.openDialogue
import core.api.sendDialogueOptions
import core.api.sendMessage
import core.api.setTitle
import core.game.dialogue.DialogueFile
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.Node
import core.game.node.entity.impl.PulseType
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.tools.START_DIALOGUE

/**
 * Enchanted jewellery listener.
 */
class EnchantedJewelleryListener : InteractionListener {

    val ids = EnchantedJewellery.idMap.keys.toIntArray()

    override fun defineListeners() {
        on(ids, IntType.ITEM, "operate") { player, node ->
            handle(player, node, true)
            return@on true
        }
        on(ids, IntType.ITEM, "rub") { player, node ->
            handle(player, node, false)
            return@on true
        }
    }
    private fun handle(player: Player, node: Node, isEquipped: Boolean) {
        player.pulseManager.clear(PulseType.STANDARD)
        val item = node.asItem()
        if (item.id == Items.RING_OF_LIFE_2570) {
            sendMessage(player, "You can't operate that.")
            return
        }
        val jewellery = EnchantedJewellery.idMap[item.id]
        if (jewellery != null) {
            if (jewellery.isLastItemIndex(jewellery.getItemIndex(item)) && !jewellery.isCrumble) {
                sendMessage(player, "The ${jewellery.getJewelleryType(item)} has lost its charge.")
                sendMessage(player, "It will need to be recharged before you can use it again.")
                return
            }
            sendMessage(player, "You rub the ${jewellery.getJewelleryType(item)}...")
            if (jewellery.options.isEmpty()) {
                jewellery.use(player, item, 0, isEquipped)
                return
            }
            openDialogue(player, EnchantedJewelleryDialogueFile(jewellery,item,isEquipped))
        }
    }

    /**
     * Class representing a dialogue file for enchanted jewellery.
     *
     * @param jewellery The enchanted jewellery object associated with the dialogue.
     * @param item The item object related to the dialogue.
     * @param isEquipped A boolean indicating if the jewellery is equipped.
     * @constructor Creates a new instance of EnchantedJewelleryDialogueFile.
     */
    class EnchantedJewelleryDialogueFile(val jewellery: EnchantedJewellery, val item: Item, val isEquipped: Boolean) : DialogueFile() {
        override fun handle(componentID: Int, buttonID: Int) {
            when (stage) {
                START_DIALOGUE -> {
                    setTitle(player!!, jewellery.options.size)
                    sendDialogueOptions(player!!, "Where would you like to teleport to?", *jewellery.options)
                    stage++
                }
                1 -> {
                    end()
                    jewellery.use(player!!, item, buttonID - 1, isEquipped)
                }
            }
        }
    }
}
