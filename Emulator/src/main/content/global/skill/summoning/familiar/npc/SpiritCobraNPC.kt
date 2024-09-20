package content.global.skill.summoning.familiar.npc

import core.api.sendMessage
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import org.rs.consts.Items
import org.rs.consts.NPCs

/**
 * Spirit cobra familiar.
 */
@Initializable
class SpiritCobraNPC @JvmOverloads constructor(owner: Player? = null, id: Int = NPCs.SPIRIT_COBRA_6802) : content.global.skill.summoning.familiar.Familiar(owner, id, 5600, 12015, 3, WeaponInterface.STYLE_ACCURATE) {

    override fun construct(owner: Player, id: Int): content.global.skill.summoning.familiar.Familiar {
        return SpiritCobraNPC(owner, id)
    }

    override fun specialMove(special: content.global.skill.summoning.familiar.FamiliarSpecial): Boolean {
        val item = special.node as Item
        val egg = Egg.forEgg(item)
        if (egg == null) {
            sendMessage(owner, "You can't use the special move on this item.")
            return false
        }
        owner.inventory.replace(egg.product, item.slot)
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SPIRIT_COBRA_6802, NPCs.SPIRIT_COBRA_6803)
    }


    /**
     * Egg
     *
     * @param egg
     * @param product
     * @constructor Egg
     */
    enum class Egg(val egg: Item, val product: Item) {
        /**
         * Cockatrice
         *
         * @constructor Cockatrice
         */
        COCKATRICE(Item(Items.EGG_1944), Item(Items.COCKATRICE_EGG_12109)),

        /**
         * Saratrice
         *
         * @constructor Saratrice
         */
        SARATRICE(Item(Items.BLUE_EGG_10533), Item(Items.SARATRICE_EGG_12113)),

        /**
         * Zamatrice
         *
         * @constructor Zamatrice
         */
        ZAMATRICE(Item(Items.RED_EGG_10532), Item(Items.ZAMATRICE_EGG_12115)),

        /**
         * Guthatrice
         *
         * @constructor Guthatrice
         */
        GUTHATRICE(Item(Items.GREEN_EGG_10531), Item(Items.GUTHATRICE_EGG_12111)),

        /**
         * Coracatrice
         *
         * @constructor Coracatrice
         */
        CORACATRICE(Item(Items.RAVEN_EGG_11964), Item(Items.CORAXATRICE_EGG_12119)),

        /**
         * Pengatrice
         *
         * @constructor Pengatrice
         */
        PENGATRICE(Item(Items.PENGUIN_EGG_12483), Item(Items.PENGATRICE_EGG_12117)),

        /**
         * Vulatrice
         *
         * @constructor Vulatrice
         */
        VULATRICE(Item(Items.VULTURE_EGG_11965), Item(Items.VULATRICE_EGG_12121));


        companion object {

            fun forEgg(item: Item): Egg? {
                for (egg in values()) {
                    if (egg.egg.id == item.id) {
                        return egg
                    }
                }
                return null
            }
        }
    }
}
