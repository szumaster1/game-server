package content.region.misthalin.varrock.quest.surok.handlers

import org.rs.consts.NPCs
import core.api.amountInBank
import core.api.amountInInventory
import core.game.node.entity.Entity
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.game.world.map.Location
import core.plugin.Initializable

/**
 * Handles the Outlaw NPC.
 */
@Initializable
class OutlawNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    /**
     * Constructs a new [OutlawNPC][AbstractNPC].
     *
     * @param id       the id.
     * @param location the location.
     */
    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return OutlawNPC(id, location)
    }

    override fun getIds(): IntArray {
        return intArrayOf(
            NPCs.OUTLAW_5842,
            NPCs.OUTLAW_5843,
            NPCs.OUTLAW_5844,
            NPCs.OUTLAW_5845,
            NPCs.OUTLAW_5846,
            NPCs.OUTLAW_5847,
            NPCs.OUTLAW_5848,
            NPCs.OUTLAW_5849,
            NPCs.OUTLAW_5850
        )
    }

    override fun onAttack(e: Entity) {
        sendChat("Stand and deliver!")
    }

    override fun finalizeDeath(killer: Entity) {
        super.finalizeDeath(killer)
        if (killer is Player) {
            val player = killer.asPlayer()
            val quest = player.getQuestRepository().getQuest("What Lies Below")
            if (quest.getStage(player) == 10) {
                val amount = amountInInventory(player, WhatLiesBelowListener.RATS_PAPER) + amountInBank(
                    player,
                    WhatLiesBelowListener.RATS_PAPER
                )
                if (amount < 5) {
                    GroundItemManager.create(Item(WhatLiesBelowListener.RATS_PAPER), getLocation())
                }
            }
        }
    }
}
