package content.region.kandarin.quest.tree.handlers

import core.api.addItemOrDrop
import org.rs.consts.Items
import org.rs.consts.NPCs
import core.api.getQuestStage
import core.api.sendDialogue
import core.game.interaction.InteractionListener
import core.game.node.entity.Entity
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.plugin.Initializable
import org.rs.consts.QuestName

/**
 * Khazard warlord NPC.
 */
@Initializable
class KhazardWarlordNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location), InteractionListener {

    override fun construct(id: Int, location: Location?, vararg objects: Any?): AbstractNPC {
        return KhazardWarlordNPC(id, location)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.KHAZARD_WARLORD_477)
    }

    override fun defineListeners() {
    }

    override fun finalizeDeath(killer: Entity?) {
        if (getQuestStage(killer as Player, QuestName.TREE_GNOME_VILLAGE) == 40) {
            sendDialogue(killer, "As the warlord falls to the ground, a ghostly vapour floats upwards from his battle-worn armour. You search his satchel and find the orbs of protection.")
            addItemOrDrop(killer, Items.ORBS_OF_PROTECTION_588)
        }
        super.finalizeDeath(killer)
    }
}