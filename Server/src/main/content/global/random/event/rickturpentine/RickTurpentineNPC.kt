package content.global.random.event.rickturpentine

import content.global.random.RandomEventNPC
import cfg.consts.NPCs
import core.api.getWorldTicks
import core.api.openDialogue
import core.api.utils.WeightBasedTable
import core.game.node.entity.npc.NPC

/**
 * Rick turpentine NPC.
 */
class RickTurpentineNPC(override var loot: WeightBasedTable? = null) : RandomEventNPC(NPCs.RICK_TURPENTINE_2476) {

    private var attackDelay = 0

    override fun init() {
        super.init()
        sendChat("Good day to you, " + (if (player.isMale) "milord " else "milady ") + player.username.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() } + ".")
    }

    override fun tick() {
        if (ticksLeft <= 10) {
            ticksLeft = 10
            if (attackDelay <= getWorldTicks())
                this.attack(player)
        }
        super.tick()
    }

    override fun talkTo(npc: NPC) {
        attackDelay = getWorldTicks() + 10
        this.pulseManager.clear()
        openDialogue(player, RickTurpentineDialogue(), this.asNpc())
    }
}