package content.global.skill.combat.summoning.familiar.npc

import content.global.skill.combat.summoning.familiar.Familiar
import content.global.skill.combat.summoning.familiar.FamiliarSpecial
import content.global.skill.combat.summoning.familiar.Forager
import content.global.skill.gathering.farming.FarmingPatch
import content.global.skill.gathering.farming.PatchType
import org.rs.consts.Items
import org.rs.consts.NPCs
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import core.plugin.Plugin
import core.tools.RandomFunction

/**
 * Giant ent familiar.
 */
@Initializable
class GiantEntNPC @JvmOverloads constructor(owner: Player? = null, id: Int = 6800) :
    Forager(owner, id, 4900, 12013, 6, WeaponInterface.STYLE_CONTROLLED, *ITEMS) {

    override fun construct(owner: Player, id: Int): Familiar {
        return GiantEntNPC(owner, id)
    }

    override fun specialMove(special: FamiliarSpecial): Boolean {
        return false
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GIANT_ENT_6800, NPCs.GIANT_ENT_6801)
    }

    override fun configureFamiliar() {
        UseWithHandler.addHandler(
            NPCs.GIANT_ENT_6800,
            UseWithHandler.NPC_TYPE,
            object : UseWithHandler(Items.PURE_ESSENCE_7936) {

                override fun newInstance(arg: Any?): Plugin<Any> {
                    addHandler(NPCs.GIANT_ENT_6800, NPC_TYPE, this)
                    return this
                }

                override fun handle(event: NodeUsageEvent): Boolean {
                    val player = event.player
                    player.lock(1)
                    val runeType = if (RandomFunction.random(9) < 4) Items.EARTH_RUNE_557 else Items.NATURE_RUNE_561
                    val runes = Item(runeType, 1)
                    if (player.inventory.remove(event.usedItem)) {
                        player.inventory.add(runes)
                        player.sendMessage(
                            String.format(
                                "The giant ent transmutes the pure essence into a %s.",
                                runes.name.lowercase()
                            )
                        )
                    }
                    return true
                }
            })
    }

    /**
     * Modify farming reward
     *
     * @param fPatch
     * @param reward
     */
    fun modifyFarmingReward(fPatch: FarmingPatch, reward: Item) {
        val patchType = fPatch.type
        if (patchType == PatchType.FRUIT_TREE_PATCH || patchType == PatchType.BUSH_PATCH || patchType == PatchType.BELLADONNA_PATCH || patchType == PatchType.CACTUS_PATCH) {
            if (RandomFunction.roll(2)) {
                reward.amount *= 2
            }
        }
    }

    companion object {
        private val ITEMS = arrayOf(Item(Items.OAK_LOGS_1521))
    }

}
