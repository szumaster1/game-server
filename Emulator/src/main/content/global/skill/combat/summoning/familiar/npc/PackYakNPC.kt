package content.global.skill.combat.summoning.familiar.npc

import content.global.skill.combat.summoning.SummoningScroll
import content.global.skill.combat.summoning.familiar.BurdenBeast
import content.global.skill.combat.summoning.familiar.Familiar
import content.global.skill.combat.summoning.familiar.FamiliarSpecial
import core.api.*
import org.rs.consts.NPCs
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.node.entity.player.info.LogType
import core.game.node.entity.player.info.PlayerMonitor.log
import core.game.node.item.Item
import core.game.system.config.ItemConfigParser
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable

/**
 * Pack yak familiar.
 */
@Initializable
class PackYakNPC @JvmOverloads constructor(owner: Player? = null, id: Int = NPCs.PACK_YAK_6873) : BurdenBeast(owner, id, 5800, 12093, 12, 30, WeaponInterface.STYLE_AGGRESSIVE) {

    override fun construct(owner: Player, id: Int): Familiar {
        return PackYakNPC(owner, id)
    }

    override fun specialMove(special: FamiliarSpecial): Boolean {
        val player = owner
        var item = Item(special.item.id, 1)
        if (item.id == SummoningScroll.WINTER_STORAGE_SCROLL.itemId) {
            return false
        }
        if (!item.definition.getConfiguration(ItemConfigParser.BANKABLE, true)) {
            sendMessage(player, "A magical force prevents you from banking this item.")
            return false
        }
        var remove = item
        if (!item.definition.isUnnoted) {
            remove = Item(item.id, 1)
            item = Item(item.noteChange, 1)
        }
        var success = addItem(player, item.id, item.amount, Container.BANK)
        if (success) {
            success = removeItem(player, remove, Container.INVENTORY)
            if (!success) {
                // Add worked, but remove failed. This should never happen (it by definition constitutes an item duplication).
                val recovered = removeItem(player, item, Container.BANK)
                if (recovered) {
                    log(player, LogType.DUPE_ALERT, "Successfully recovered from potential dupe attempt involving the winter storage scroll")
                } else {
                    log(player, LogType.DUPE_ALERT, "Failed to recover from potentially successful dupe attempt involving the winter storage scroll")
                }
            }
        }
        if (success) {
            closeDialogue(player)
            graphics(Graphic.create(1358))
            sendMessage(player, "The pack yak has sent an item to your bank.")
        } else {
            sendMessage(player, "The pack yak can't send that item to your bank.")
        }
        return true
    }

    override fun visualizeSpecialMove() {
        visualize(owner, Animation.create(7660), Graphic.create(1316))
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.PACK_YAK_6873, NPCs.PACK_YAK_6874)
    }

    override fun getText(): String {
        return "Baroo!"
    }
}
