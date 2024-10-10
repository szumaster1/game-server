package content.global.skill.summoning.items

import content.global.skill.summoning.dialogue.PikkupstixDialogue
import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.update.flag.context.Graphic
import org.rs.consts.Animations
import org.rs.consts.Items
import org.rs.consts.NPCs

class EnchantedHeadgearListener : InteractionListener {

    override fun defineListeners() {

        /*
         * Handles enchant option.
         */

        on(NPCs.PIKKUPSTIX_6970, IntType.NPC, "Enchant") { player, _ ->
            sendNPCDialogue(player, NPCs.PIKKUPSTIX_6970, "What would you like disenchanted or enchanted?")
            addDialogueAction(player) { _, _ ->
                sendItemSelect(player, "Choose") { slot, index ->
                    val item = player.inventory[slot] ?: return@sendItemSelect
                    enchant(player, item, index)
                    return@sendItemSelect
                }
            }
            return@on true
        }

        /*
         * Handles un-charge option for charged items.
         */

        on(chargedIDs, IntType.ITEM, "Uncharge") { player, node ->
            val enchantId = EnchantedHeadgear.forItem(node.asItem()) ?: return@on true
            sendMessages(player, "You remove the scrolls. You will need to use a Summoning scroll on it to charge the", "headgear up once more.")
            replaceSlot(player, node.asItem().slot, enchantId.enchantedItem)

            /*
             * Scrolls: addItem(player, Items.NULL_5150)
             */

            return@on true
        }
    }

    companion object {
        private val chargedIDs = EnchantedHeadgear.values().map { it.chargedItem.id }.toIntArray()
        private val scrollIDs = intArrayOf(Items.DREADFOWL_STRIKE_SCROLL_12445, Items.SLIME_SPRAY_SCROLL_12459, Items.PESTER_SCROLL_12838, Items.ELECTRIC_LASH_SCROLL_12460, Items.FIREBALL_ASSAULT_SCROLL_12839, Items.SANDSTORM_SCROLL_12446, Items.VAMPIRE_TOUCH_SCROLL_12447, Items.BRONZE_BULL_RUSH_SCROLL_12461, Items.EVIL_FLAMES_SCROLL_12448, Items.PETRIFYING_GAZE_SCROLL_12458, Items.IRON_BULL_RUSH_SCROLL_12462, Items.ABYSSAL_DRAIN_SCROLL_12454, Items.DISSOLVE_SCROLL_12453, Items.AMBUSH_SCROLL_12836, Items.RENDING_SCROLL_12840, Items.DOOMSPHERE_SCROLL_12455, Items.DUST_CLOUD_SCROLL_12468, Items.STEEL_BULL_RUSH_SCROLL_12463, Items.POISONOUS_BLAST_SCROLL_12467, Items.MITH_BULL_RUSH_SCROLL_12464, Items.TOAD_BARK_SCROLL_12452, Items.FAMINE_SCROLL_12830, Items.ARCTIC_BLAST_SCROLL_12451, Items.RISE_FROM_THE_ASHES_SCROLL_14622, Items.CRUSHING_CLAW_SCROLL_12449, Items.MANTIS_STRIKE_SCROLL_12450, Items.INFERNO_SCROLL_12841, Items.ADDY_BULL_RUSH_SCROLL_12465, Items.DEADLY_CLAW_SCROLL_12831, Items.ACORN_MISSILE_SCROLL_12457, Items.SPIKE_SHOT_SCROLL_12456, Items.EBON_THUNDER_SCROLL_12837, Items.SWAMP_PLAGUE_SCROLL_12832, Items.RUNE_BULL_RUSH_SCROLL_12466, Items.BOIL_SCROLL_12833, Items.IRON_WITHIN_SCROLL_12828, Items.STEEL_OF_LEGENDS_SCROLL_12825)

        /**
         * Enchants the headgear for the player.
         *
         * @param player The player who is enchanting the item.
         * @param slot The item to be enchanted.
         */
        fun enchant(player: Player, slot: Item, option: Int) {
            val item = EnchantedHeadgear.forItem(Item(slot.id))!!
            if (option == 1) lock(player, 1)
            if (removeItem(player, slot)) {
                addItem(player, if (slot == item.defaultItem) item.enchantedItem.id else item.enchantedItem.id)
                face(findLocalNPC(player, NPCs.PIKKUPSTIX_6970)!!, player, 1)
                animate(findLocalNPC(player, NPCs.PIKKUPSTIX_6970)!!, Animations.CAST_SPELL_711)
                sendGraphics(Graphic(1574, 100), player.location)
                openDialogue(player, PikkupstixDialogue())
            }
        }

    }

}