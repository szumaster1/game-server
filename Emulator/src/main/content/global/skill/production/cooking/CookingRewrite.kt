package content.global.skill.production.cooking

import content.global.skill.production.cooking.item.PieCookingPulse
import content.global.skill.production.cooking.item.PizzaCookingPulse
import content.global.skill.production.cooking.item.StandardCookingPulse
import core.api.amountInInventory
import cfg.consts.Items
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.node.scenery.Scenery

/**
 * Cooking rewrite
 *
 * @constructor Cooking rewrite
 */
class CookingRewrite : InteractionListener {

    val RAW_FOODS: IntArray

    init {
        val list = CookableItems.values().map { it.raw }.toMutableList()
        list.add(Items.COOKED_MEAT_2142)
        list.add(Items.RAW_BEEF_2132)
        list.add(Items.RAW_BEAR_MEAT_2136)
        list.add(Items.SEAWEED_401)
        RAW_FOODS = list.toIntArray()
    }

    override fun defineListeners() {

        onUseWith(IntType.SCENERY, RAW_FOODS, *COOKING_OBJs) { player, used, with ->
            val item = used.asItem()
            val obj = with.asScenery()
            val range = obj.name.lowercase().contains("range")
            when (item.id) {
                Items.RAW_BEEF_2132, Items.RAW_BEAR_MEAT_2136 -> if (range) {
                    player.dialogueInterpreter.open(CookingDialogue(item.id, 9436, true, obj, item.id))
                    return@onUseWith true
                }

                Items.BREAD_DOUGH_2307, Items.UNCOOKED_CAKE_1889 -> if (!range) {
                    sendMessage(player, "You need to cook this on a range.")
                    return@onUseWith false
                }
            }

            if (amountInInventory(player, item.id) > 1) {
                player.dialogueInterpreter.open(CookingDialogue(item.id, obj))
            } else {
                val product = if (CookableItems.intentionalBurn(item.id)) {
                    CookableItems.getIntentionalBurn(item.id).id
                } else {
                    CookableItems.forId(item.id)!!.cooked
                }
                cook(player, obj, item.id, product, 1)
            }
            return@onUseWith true
        }

    }

    companion object {
        val COOKING_OBJs = intArrayOf(114, 2724, 2725, 2726, 2728, 2729, 2730, 2731, 2732, 2859, 3038, 3039, 3769, 3775, 4172, 4265, 4266, 4618, 4650, 5165, 5249, 5275, 5499, 5631, 5632, 5981, 6093, 6094, 6095, 6096, 8712, 8750, 9085, 9086, 9087, 9374, 9439, 9440, 9441, 9682, 10377, 10433, 10824, 11404, 11405, 11406, 12102, 12269, 12796, 13337, 13528, 13529, 13531, 13533, 13536, 13539, 13542, 13881, 14169, 14919, 15156, 15398, 16893, 17640, 17641, 17642, 17643, 18039, 20000, 20001, 21302, 21620, 21792, 21795, 22154, 22713, 22714, 23046, 24283, 24284, 24285, 24313, 24329, 25155, 25156, 25440, 25441, 25465, 25730, 27251, 27297, 29139, 30017, 32099, 33498, 33500, 34410, 34495, 34546, 34565, 35449, 36815, 36816, 36973, 37426, 37597, 37629, 37726, 40110)

        @JvmStatic
        fun cook(player: Player, scenery: Scenery?, initial: Int, product: Int, amount: Int) {
            val food = Item(initial)
            if (food.name.lowercase().contains("pizza")) {
                player.pulseManager.run(PizzaCookingPulse(player, scenery!!, initial, product, amount))
            } else if (food.name.lowercase().contains("pie")) {
                player.pulseManager.run(PieCookingPulse(player, scenery!!, initial, product, amount))
            } else if (CookableItems.intentionalBurn(initial)) {
                player.pulseManager.run(IntentionalBurnPulse(player, scenery!!, initial, product, amount))
            } else {
                player.pulseManager.run(StandardCookingPulse(player, scenery!!, initial, product, amount))
            }
        }
    }

}
