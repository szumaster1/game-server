package content.region.asgarnia.burthope.handlers.heroguild

import content.data.EnchantedJewellery
import content.data.EnchantedJewellery.Companion.idMap
import core.api.animate
import org.rs.consts.Items
import core.api.hasRequirement
import core.cache.def.impl.SceneryDefinition
import core.game.global.action.DoorActionHandler.handleAutowalkDoor
import core.game.interaction.NodeUsageEvent
import core.game.interaction.OptionHandler
import core.game.interaction.UseWithHandler
import core.game.node.Node
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.world.update.flag.context.Animation
import core.plugin.PluginManager.definePlugin
import core.plugin.Initializable
import core.plugin.Plugin
import org.rs.consts.Animations
import org.rs.consts.QuestName

/**
 * Represents the Heroes guild plugin.
 */
@Initializable
class HeroesGuildPlugin : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        SceneryDefinition.forId(2624).handlers["option:open"] = this
        SceneryDefinition.forId(2625).handlers["option:open"] = this
        definePlugin(JewelleryRechargePlugin())
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        val id = node.id
        when (option) {
            "open" -> {
                when (id) {
                    2624, 2625 -> {
                        if (!hasRequirement(player, QuestName.HEROES_QUEST)) return true
                        handleAutowalkDoor(player, (node as Scenery))
                    }
                }
                return true
            }
        }
        return true
    }

    /**
     * Jewellery recharge plugin.
     */
    class JewelleryRechargePlugin : UseWithHandler(*IDS) {

        override fun newInstance(arg: Any?): Plugin<Any> {
            addHandler(36695, OBJECT_TYPE, this)
            addHandler(7339, NPC_TYPE, this)
            addHandler(7340, NPC_TYPE, this)
            return this
        }

        override fun handle(event: NodeUsageEvent): Boolean {
            val player = event.player
            checkNotNull(event.usedItem)
            val jewellery = idMap[event.usedItem.id]
            if (!hasRequirement(player, QuestName.HEROES_QUEST)) return true
            if (jewellery == EnchantedJewellery.COMBAT_BRACELET || jewellery == EnchantedJewellery.SKILLS_NECKLACE) if (!hasRequirement(player, "Legend's Quest")
            ) return true
            if (jewellery == null && event.usedItem.id != 2572) {
                return true
            }
            val fam = event.usedWith is NPC
            if (fam && (jewellery != EnchantedJewellery.AMULET_OF_GLORY) and (jewellery != EnchantedJewellery.AMULET_OF_GLORY_T)) {
                return false
            }
            if (fam) {
                val familiar = event.usedWith as content.global.skill.summoning.familiar.Familiar
                if (!player.familiarManager.isOwner(familiar)) {
                    return false
                }
                familiar.animate(Animation.create(7882))
            }
            player.lock(1)
            animate(player, Animations.MULTI_USE_TAKE_832)
            val rechargedItem = Item(jewellery!!.ids[0])
            player.inventory.replace(rechargedItem, event.usedItem.slot)
            val name = jewellery.getJewelleryName(rechargedItem)
            val item = when {
                name.contains("amulet", true) -> "amulet"
                name.contains("bracelet", true) -> "bracelet"
                name.contains("ring", true) -> "ring"
                name.contains("necklace", true) -> "necklace"
                else -> name
            }
            if (!fam) {
                player.sendMessage("You dip the $item in the fountain...")
            } else {
                player.sendMessage("Your titan recharges the glory.")
            }
            return true
        }

        companion object {
            private val IDS = intArrayOf(
                Items.AMULET_OF_GLORY3_1710,
                Items.AMULET_OF_GLORY2_1708,
                Items.AMULET_OF_GLORY1_1706,
                Items.AMULET_OF_GLORY_1704,
                Items.SKILLS_NECKLACE3_11107,
                Items.SKILLS_NECKLACE2_11109,
                Items.SKILLS_NECKLACE1_11111,
                Items.SKILLS_NECKLACE_11113,
                Items.COMBAT_BRACELET3_11120,
                Items.COMBAT_BRACELET2_11122,
                Items.COMBAT_BRACELET1_11124,
                Items.COMBAT_BRACELET_11126,
                Items.AMULET_OF_GLORYT4_10354,
                Items.AMULET_OF_GLORYT3_10356,
                Items.AMULET_OF_GLORYT2_10358,
                Items.AMULET_OF_GLORYT1_10360,
                Items.AMULET_OF_GLORYT_10362,
                Items.RING_OF_WEALTH_2572
            )
        }
    }
}
