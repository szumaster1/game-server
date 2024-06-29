package content.global.skill.production.cooking.handlers


import core.api.addItemOrDrop
import core.api.consts.Items
import core.api.removeItem
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

class SaucyKebabListener : InteractionListener {

    private val KEBABS = intArrayOf(Items.KEBAB_1971, Items.UGTHANKI_KEBAB_1883, Items.UGTHANKI_KEBAB_1885)

    override fun defineListeners() {
        /*
         * https://runescape.wiki/w/Ugthanki_kebab?oldid=2077970 | add
         * https://runescape.wiki/w/Red_hot_sauce?oldid=1936150
         * https://www.youtube.com/watch?v=SnKCK41qMjg&ab_channel=guthixking6
         */

        onUseWith(IntType.ITEM, Items.RED_HOT_SAUCE_4610, *KEBABS) { player, used, with ->
            if (removeItem(player, used.asItem()) && removeItem(player, with.asItem())) {
                addItemOrDrop(player, Items.SUPER_KEBAB_4608)
            } else {
                sendMessage(player, "Nothing interesting happens.")
            }
            return@onUseWith true
        }
    }

}
