package content.global.random.event.pillory

import content.global.random.RandomEventNPC
import org.rs.consts.NPCs
import core.api.registerLogoutListener
import core.api.runTask
import core.api.utils.WeightBasedTable
import core.game.node.entity.npc.NPC
import core.game.system.timer.impl.AntiMacro

/**
 * Pillory guard NPC.
 */
class PilloryGuardNPC(override var loot: WeightBasedTable? = null) : RandomEventNPC(NPCs.PILLORY_GUARD_2573) {

    override fun init() {
        super.init()
        runTask(player, 1) {
            core.api.setAttribute(player, PilloryUtils.PILLORY_LOCATION, player.location)
            registerLogoutListener(player, PilloryUtils.PILLORY_LOGOUT) { p ->
                p.location = core.api.getAttribute(p, PilloryUtils.PILLORY_LOCATION, p.location)
            }
            PilloryCutscene(player).start()
            AntiMacro.terminateEventNpc(player)
        }
    }

    override fun talkTo(npc: NPC) {
    }
}

/*
 * 29 - projectile.
 * 30 - throw.
 * 31 - contact with target/White splash.
 */