package content.global.skill.crafting.items.armour.leather

import content.global.skill.crafting.items.armour.dragon.DragonCraftingPulse
import content.global.skill.crafting.Leather
import core.api.*
import core.cache.def.impl.ItemDefinition
import core.game.component.Component
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import org.rs.consts.Components
import org.rs.consts.Items

/**
 * Represents the leather craft dialogue.
 */
@Initializable
class LeatherCraftingDialogue(player: Player) : Dialogue(player) {

    private var type: String = ""
    private var leather: Int = 0

    override fun newInstance(player: Player): Dialogue = LeatherCraftingDialogue(player)

    override fun open(vararg args: Any): Boolean {
        val component = Component(Components.SKILL_MAKE_304)
        type = args[0] as String
        leather = if (type == "hard") 0 else args[1] as Int
        if (type == "hard") openHardLeatherDialogue(component) else openDragonLeatherDialogue(component)
        return true
    }

    private fun openHardLeatherDialogue(component: Component) {
        openChatbox(player, component.id)
        sendItemZoomOnInterface(player, component.id, 2, Items.HARDLEATHER_BODY_1131, 150)
        sendString(player,"<br><br><br><br>Hardleather body", Components.SKILL_MULTI1_309, 6)
    }

    private fun openDragonLeatherDialogue(component: Component) {
        openChatbox(player, component.id)
        getDragonHideProducts().forEachIndexed { i, productId ->
            sendItemZoomOnInterface(player, Components.SKILL_MAKE_304, i + 2, productId, 175)

            sendString(player, "<br><br><br><br>${getItemName(productId)}", Components.SKILL_MAKE_304, 8 + i * 3)
        }
    }

    private fun getDragonHideProducts(): IntArray {
        return when (leather) {
            Leather.GREEN_LEATHER -> intArrayOf(
                Leather.DragonHide.GREEN_D_HIDE_BODY.product,
                Leather.DragonHide.GREEN_D_HIDE_VAMBS.product,
                Leather.DragonHide.GREEN_D_HIDE_CHAPS.product
            )
            Leather.BLUE_LEATHER -> intArrayOf(
                Leather.DragonHide.BLUE_D_HIDE_BODY.product,
                Leather.DragonHide.BLUE_D_HIDE_VAMBS.product,
                Leather.DragonHide.BLUE_D_HIDE_CHAPS.product
            )
            Leather.RED_LEATHER -> intArrayOf(
                Leather.DragonHide.RED_D_HIDE_BODY.product,
                Leather.DragonHide.RED_D_HIDE_VAMBS.product,
                Leather.DragonHide.RED_D_HIDE_CHAPS.product
            )
            Leather.BLACK_LEATHER -> intArrayOf(
                Leather.DragonHide.BLACK_D_HIDE_BODY.product,
                Leather.DragonHide.BLACK_D_HIDE_VAMBS.product,
                Leather.DragonHide.BLACK_D_HIDE_CHAPS.product
            )
            else -> intArrayOf()
        }
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        closeChatBox(player)
        val amt = when (type) {
            "hard" -> handleHardLeather(buttonId)
            "dragon" -> handleDragonLeather(buttonId)
            else -> 0
        }
        return true
    }

    private fun handleHardLeather(buttonId: Int): Int {
        val amt = when (buttonId) {
            6 -> 1
            4 -> 5
            3 -> {
                sendInputDialogue(player, true, "Enter the amount:") { value ->
                    submitIndividualPulse(player, HardLeatherCraftingPulse(player, null, value as Int))
                }
                return 0
            }
            2 -> amountInInventory(player, Leather.HARD_LEATHER)
            else -> 0
        }
        if (amt > 0) submitIndividualPulse(player, HardLeatherCraftingPulse(player, null, amt))
        return amt
    }

    private fun handleDragonLeather(buttonId: Int): Int {
        val index = when {
            buttonId in 4..7 -> 1
            buttonId in 8..12 -> 2
            buttonId in 12..16 -> 3
            else -> 0
        }
        val hide = getDragonHideByIndex(index)
        val amt = when (buttonId) {
            7, 11, 15 -> 1
            6, 10, 14 -> 5
            5, 9, 13 -> 10
            4, 8, 12 -> {
                hide?.let {
                    sendInputDialogue(player, InputType.AMOUNT, "Enter the amount:") { value ->
                        submitIndividualPulse(player, DragonCraftingPulse(player, null, it, value as Int))
                    }
                }
                return 0
            }
            else -> 0
        }
        hide?.let { submitIndividualPulse(player, DragonCraftingPulse(player, null, it, amt)) }
        return amt
    }

    private fun getDragonHideByIndex(index: Int): Leather.DragonHide? {
        return when (index) {
            1 -> when (leather) {
                Leather.GREEN_LEATHER -> Leather.DragonHide.GREEN_D_HIDE_BODY
                Leather.BLUE_LEATHER  -> Leather.DragonHide.BLUE_D_HIDE_BODY
                Leather.RED_LEATHER   -> Leather.DragonHide.RED_D_HIDE_BODY
                Leather.BLACK_LEATHER -> Leather.DragonHide.BLACK_D_HIDE_BODY
                else -> null
            }
            2 -> when (leather) {
                Leather.GREEN_LEATHER -> Leather.DragonHide.GREEN_D_HIDE_VAMBS
                Leather.BLUE_LEATHER  -> Leather.DragonHide.BLUE_D_HIDE_VAMBS
                Leather.RED_LEATHER   -> Leather.DragonHide.RED_D_HIDE_VAMBS
                Leather.BLACK_LEATHER -> Leather.DragonHide.BLACK_D_HIDE_VAMBS
                else -> null
            }
            3 -> when (leather) {
                Leather.GREEN_LEATHER -> Leather.DragonHide.GREEN_D_HIDE_CHAPS
                Leather.BLUE_LEATHER  -> Leather.DragonHide.BLUE_D_HIDE_CHAPS
                Leather.RED_LEATHER   -> Leather.DragonHide.RED_D_HIDE_CHAPS
                Leather.BLACK_LEATHER -> Leather.DragonHide.BLACK_D_HIDE_CHAPS
                else -> null
            }
            else -> null
        }
    }

    override fun getIds(): IntArray = intArrayOf(48923)
}
