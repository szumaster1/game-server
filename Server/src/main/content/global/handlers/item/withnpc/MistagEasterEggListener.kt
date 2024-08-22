package content.global.handlers.item.withnpc

import core.api.*
import cfg.consts.Items
import cfg.consts.NPCs
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.node.item.Item
import core.tools.END_DIALOGUE

/**
 * Mistag easter egg listener.
 */
class MistagEasterEggListener : InteractionListener {

    private val diamond = Items.DIAMOND_1601
    private val mistag = NPCs.MISTAG_2084
    private val zanikRing = Items.ZANIK_RING_14649
    private val drunkRender = 982

    override fun defineListeners() {
        onUseWith(IntType.NPC, diamond, mistag) { player, _, _ ->
            val alreadyHasRing = inInventory(player, zanikRing, 1) || player.bank.contains(zanikRing, 1) || inEquipment(player, zanikRing, 1)
            openDialogue(player, object : DialogueFile() {
                override fun handle(componentID: Int, buttonID: Int) {
                    npc = NPC(NPCs.MISTAG_2084)
                    if (alreadyHasRing) {
                        npc(FacialExpression.OLD_HAPPY,"Lovely gem, adventurer, but I have nothing for you.").also { stage = END_DIALOGUE }
                        return
                    }
                    when (stage++) {
                        0 -> npc(FacialExpression.OLD_HAPPY,"Well thank you adventurer! Here, take this.")
                        1 -> {
                            end()
                            if (removeItem(player, Item(diamond))) {
                                addItem(player,zanikRing)
                            }
                        }
                    }
                }
            })
            return@onUseWith true
        }

        onEquip(zanikRing) { player, _ ->
            player.appearance.transformNPC(NPCs.ZANIK_3712)
            return@onEquip true
        }

        onUnequip(zanikRing) { player, _ ->
            player.appearance.transformNPC(-1)
            return@onUnequip true
        }

        onEquip(Items.BEER_1917) { player, _ ->
            setAttribute(player, "render-anim-override", drunkRender)
            return@onEquip true
        }

        onUnequip(Items.BEER_1917) { player, _ ->
            removeAttribute(player, "render-anim-override")
            player.appearance.setDefaultAnimations()
            player.appearance.sync()
            return@onUnequip true
        }
    }
}
