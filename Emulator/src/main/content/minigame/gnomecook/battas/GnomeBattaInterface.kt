package content.minigame.gnomecook.battas

import org.rs.consts.Items
import core.api.inInventory
import core.game.component.Component
import core.game.component.ComponentDefinition
import core.game.component.ComponentPlugin
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.plugin.Initializable
import core.plugin.Plugin


private const val WORM_BATTA = 2219
private const val TOAD_BATTA = 2221
private const val CHEESE_TOM_BATTA = 2223
private const val FRUIT_BATTA = 2225
private const val VEG_BATTA = 2227

/**
 * Gnome batta interface.
 */
@Initializable
class GnomeBattaInterface : ComponentPlugin() {

    override fun open(player: Player?, component: Component?) {
        component ?: return
        player ?: return
        super.open(player, component)
        player.packetDispatch.sendItemOnInterface(FRUIT_BATTA, component.id, component.id, 3)
        player.packetDispatch.sendItemOnInterface(TOAD_BATTA, component.id, component.id, 14)
        player.packetDispatch.sendItemOnInterface(WORM_BATTA, component.id, component.id, 25)
        player.packetDispatch.sendItemOnInterface(VEG_BATTA, component.id, component.id, 34)
        player.packetDispatch.sendItemOnInterface(CHEESE_TOM_BATTA, component.id, component.id, 47)
    }

    override fun handle(
        player: Player?,
        component: Component?,
        opcode: Int,
        button: Int,
        slot: Int,
        itemId: Int
    ): Boolean {
        player ?: return false
        component ?: return false

        when (button) {
            3 -> attemptMake(CookedProduct.HALF_MADE_FR, player)
            14 -> attemptMake(CookedProduct.HALF_MADE_TO, player)
            25 -> attemptMake(CookedProduct.HALF_MADE_WO, player)
            34 -> attemptMake(CookedProduct.HALF_MADE_VE, player)
            47 -> attemptMake(CookedProduct.HALF_MADE_CT, player)
        }
        return true
    }

    private fun attemptMake(batta: CookedProduct, player: Player) {
        if (!inInventory(
                player,
                Items.GNOME_SPICE_2169
            ) && (batta == CookedProduct.HALF_MADE_TO || batta == CookedProduct.HALF_MADE_WO)
        ) {
            player.dialogueInterpreter.sendDialogue("You need gnome spices for this.")
            return
        }

        if (player.skills.getLevel(Skills.COOKING) < batta.levelReq) {
            player.dialogueInterpreter.sendDialogue("You don't have the needed level to make this.")
            return
        }

        var hasAll = true
        for (item in batta.requiredItems) {
            if (!player.inventory.containsItem(item)) {
                hasAll = false
                break
            }
        }

        if (!hasAll) {
            player.dialogueInterpreter.sendDialogue("You don't have all the ingredients needed for this.")
            return
        }

        player.inventory.remove(*batta.requiredItems)
        player.inventory.remove(Item(Items.HALF_BAKED_BATTA_2249))
        player.inventory.add(Item(batta.product))
        player.skills.addExperience(Skills.COOKING, batta.experience)
        player.interfaceManager.close()
    }

    /**
     * Represents a cooked product.
     *
     * @param product       the ID of the cooked product.
     * @param levelReq      the level required to cook the product.
     * @param experience    the experience gained from cooking the product.
     * @param requiredItems the array of items required to cook the product.
     */
    internal enum class CookedProduct(
        val product: Int,
        val levelReq: Int,
        val experience: Double,
        val requiredItems: Array<Item>
    ) {
        HALF_MADE_CT(
            product = 9478,
            levelReq = 29,
            experience = 40.0,
            requiredItems = arrayOf(Item(Items.TOMATO_1982), Item(Items.CHEESE_1985))
        ),
        HALF_MADE_FR(
            product = 9480,
            levelReq = 25,
            experience = 40.0,
            requiredItems = arrayOf(Item(Items.EQUA_LEAVES_2128, 4), Item(Items.LIME_CHUNKS_2122), Item(Items.ORANGE_CHUNKS_2110), Item(Items.PINEAPPLE_CHUNKS_2116))
        ),
        HALF_MADE_TO(
            product = 9482,
            levelReq = 26,
            experience = 40.0,
            requiredItems = arrayOf(Item(Items.EQUA_LEAVES_2128), Item(Items.CHEESE_1985), Item(Items.TOADS_LEGS_2152))
        ),
        HALF_MADE_VE(
            product = 9483,
            levelReq = 28,
            experience = 40.0,
            requiredItems = arrayOf(Item(Items.TOMATO_1982, 2), Item(Items.CHEESE_1985), Item(Items.DWELLBERRIES_2126), Item(Items.ONION_1957), Item(Items.CABBAGE_1965))
        ),
        HALF_MADE_WO(
            product = 9485,
            levelReq = 27,
            experience = 40.0,
            requiredItems = arrayOf(Item(Items.KING_WORM_2162), Item(Items.CHEESE_1985))
        )
    }

    override fun newInstance(arg: Any?): Plugin<Any> {
        ComponentDefinition.put(434, this)
        return this
    }

}