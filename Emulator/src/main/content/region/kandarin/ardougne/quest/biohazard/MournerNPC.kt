package content.region.kandarin.ardougne.quest.biohazard

import core.api.*
import org.rs.consts.Items
import org.rs.consts.NPCs
import core.game.node.entity.Entity
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.plugin.Initializable

/**
 * Mourner NPC.
 */
@Initializable
class MournerNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {
    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return MournerNPC(id, location)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MOURNER_370)
    }

    override fun finalizeDeath(killer: Entity?) {
        if (killer is Player) {
            if (isQuestInProgress(killer, "Biohazard", 6, 15)) {
                sendMessage(killer.asPlayer(), "You search the mourner...")
                if (!inInventory(killer.asPlayer(), Items.KEY_2832)) {
                    addItemOrDrop(killer.asPlayer(), Items.KEY_2832)
                    killer.asPlayer().sendMessage("and find a key.", 1)
                    setQuestStage(killer.asPlayer(), "Biohazard", 8)
                } else {
                    sendMessage(killer.asPlayer(), "...but find nothing.")
                }
            }
            super.finalizeDeath(killer)
        }
    }
}