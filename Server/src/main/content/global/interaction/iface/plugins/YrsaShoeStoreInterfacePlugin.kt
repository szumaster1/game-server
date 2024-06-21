package content.global.interaction.iface.plugins

import core.api.*
import core.api.consts.Components
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.component.Component
import core.game.component.ComponentDefinition
import core.game.component.ComponentPlugin
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import core.plugin.Plugin
import core.utilities.END_DIALOGUE

@Initializable
class YrsaShoeStoreInterfacePlugin : ComponentPlugin() {
    private val paymentCheck = "yrsa-paid"
    private val previousColor = "yrsa-previous"
    private val pictureId = intArrayOf(3680, 3681, 3682, 3683, 3684, 3685)
    private val selectButtonId = intArrayOf(15, 16, 17, 18, 19, 20)
    private val colorId = intArrayOf(0, 1, 2, 3, 4, 5)
    private val shopInterface = Components.YRSA_SHOE_STORE_200
    private val exitSound = 96

    override fun open(player: Player, component: Component?) {
        component ?: return
        super.open(player, component)

        val originalColor = player.appearance.feet.color
        setAttribute(player, previousColor, originalColor)

        for (i in selectButtonId.indices.also {
            for (i in pictureId.indices) {
                sendItemOnInterface(player, shopInterface, selectButtonId[i], pictureId[i])
            }
        }) player.toggleWardrobe(true)

        component.setCloseEvent { p, _ ->
            p.toggleWardrobe(false)
            playGlobalAudio(player.location, exitSound, 1)
            if (!getAttribute(player, paymentCheck, false)) {
                p.appearance.feet.changeColor(getAttribute(p, previousColor, originalColor))
                syncAppearance(p)
            }

            syncAppearance(p)
            removeAttribute(p, paymentCheck)
            true
        }

        syncAppearance(player)
        removeAttribute(player, previousColor)
    }

    override fun handle(
        player: Player?, component: Component?, opcode: Int, button: Int, slot: Int, itemId: Int
    ): Boolean {
        player ?: return false
        when (button) {
            14 -> pay(player)
            else -> when (component?.id) {
                shopInterface -> {
                    if (selectButtonId.contains(button)) {
                        updateFeet(player, button)
                        return true
                    }
                }
            }
        }
        return true
    }

    fun pay(player: Player) {
        val newColor = getAttribute(player, previousColor, player.appearance.skin.color)
        if (newColor == player.appearance.feet.color) {
            closeInterface(player)
        }

        if (removeItem(player, Item(Items.COINS_995, 500))) {
            setAttribute(player, paymentCheck, true)
            closeInterface(player)
            if(inBorders(player, 2622, 3672, 2626, 3676))
                openDialogue(player, YrsaCloseEventDialogue())
        } else {
            sendDialogue(player, "You can not afford that.")
        }

    }

    private fun updateFeet(player: Player, button: Int) {
        var subtractor = 0
        when (button) {
            15, 16, 17, 18, 19, 20 -> subtractor += 15
        }
        player.appearance.feet.changeColor(colorId[button - subtractor])
        syncAppearance(player)
    }

    private fun syncAppearance(player: Player) {
        player.appearance.sync()
    }

    override fun newInstance(arg: Any?): Plugin<Any> {
        ComponentDefinition.put(Components.YRSA_SHOE_STORE_200, this)
        return this
    }


    class YrsaCloseEventDialogue : DialogueFile() {
        override fun handle(componentID: Int, buttonID: Int) {
            npc = NPC(NPCs.YRSA_1301)
            when (stage) {
                0 -> npcl(FacialExpression.FRIENDLY, "I think they suit you.").also { stage++ }
                1 -> playerl(FacialExpression.HAPPY, "Thanks!").also { stage = END_DIALOGUE }
            }
        }
    }
}
