package content.region.misthalin.quest.free.dragonslayer.npc

import content.region.misthalin.quest.free.dragonslayer.DragonSlayer
import core.api.consts.NPCs
import core.game.node.entity.Entity
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.node.item.GroundItemManager
import core.game.world.map.Location

/**
 * Wormbrain NPC.
 */
class WormbrainNPC : AbstractNPC {

    constructor() : super(0, null)

    private constructor(id: Int, location: Location) : super(id, location)

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return WormbrainNPC(id, location)
    }

    override fun finalizeDeath(killer: Entity) {
        super.finalizeDeath(killer)
        if (killer is Player) {
            if (killer.getQuestRepository().getQuest("Dragon Slayer").getStage(killer.asPlayer()) == 20 && !killer.inventory.containsItem(DragonSlayer.WORMBRAIN_PIECE) && !killer.bank.containsItem(DragonSlayer.WORMBRAIN_PIECE)) {
                GroundItemManager.create(DragonSlayer.WORMBRAIN_PIECE, getLocation(), killer)
                killer.packetDispatch.sendMessage("Wormbrain drops a map piece on the floor.")
            }
        }
    }

    override fun isAttackable(entity: Entity, style: CombatStyle, message: Boolean): Boolean {
        if (entity is Player) {
            val player = entity
            if (player.getQuestRepository().getQuest("Dragon Slayer").getStage(player) != 20) {
                if (message) {
                    player.packetDispatch.sendMessage("The goblin is already in prison. You have no reason to attack him.")
                }
                return false
            }
        }
        return super.isAttackable(entity, style, message)
    }

    override fun getIds(): IntArray {
        return ID
    }

    companion object {
        private val ID = intArrayOf(NPCs.WORMBRAIN_745)
    }
}
