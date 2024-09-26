package content.region.misthalin.varrock.miniquest.abyss.handlers

import content.global.skill.runecrafting.items.RunePouch
import org.rs.consts.NPCs
import core.game.node.entity.Entity
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.map.Location
import core.tools.RandomFunction

/**
 * Represents the Abyssal NPC.
 */
class AbyssalNPC : AbstractNPC {

    constructor() : super(0, null, true) {
        isAggressive = true
    }

    constructor(id: Int, location: Location?) : super(id, location, true)

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return AbyssalNPC(id, location)
    }

    override fun init() {
        super.init()
        setDefaultBehavior()
    }

    override fun handleTickActions() {
        super.handleTickActions()
    }

    override fun finalizeDeath(killer: Entity) {
        super.finalizeDeath(killer)
        if (killer is Player) {
            val p = killer.asPlayer()
            if (RandomFunction.random(750) < 12) {
                val pouch = getPouch(p)
                if (pouch != null) {
                    definition.dropTables.createDrop(pouch, p, this, getLocation())
                }
            }
        }
    }

    private fun getPouch(player: Player): Item? {
        if (!player.hasItem(RunePouch.SMALL.pouch)) {
            return RunePouch.SMALL.pouch
        }
        if (!player.hasItem(RunePouch.MEDIUM.pouch) && !player.hasItem(RunePouch.MEDIUM.decayedPouch)) {
            return RunePouch.MEDIUM.pouch
        }
        if (!player.hasItem(RunePouch.LARGE.pouch) && !player.hasItem(RunePouch.LARGE.decayedPouch)) {
            return RunePouch.LARGE.pouch
        }
        return if (!player.hasItem(RunePouch.GIANT.pouch) && !player.hasItem(RunePouch.GIANT.decayedPouch)) {
            RunePouch.GIANT.pouch
        } else null
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ABYSSAL_LEECH_2263, NPCs.ABYSSAL_GUARDIAN_2264, NPCs.ABYSSAL_WALKER_2265)
    }
}
