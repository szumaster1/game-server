package content.global.random.event.treespirit

import content.global.random.RandomEventNPC
import core.api.consts.NPCs
import core.api.utils.WeightBasedTable
import core.game.node.entity.Entity
import core.game.node.entity.npc.NPC
import kotlin.math.max

class TreeSpiritRENPC(override var loot: WeightBasedTable? = null) : RandomEventNPC(NPCs.TREE_SPIRIT_438) {

    val ids = (438..443).toList()
    override fun talkTo(npc: NPC) {}
    override fun init() {
        super.init()
        val index = max(0, (player.properties.combatLevel / 20) - 1)
        val id = ids.toList()[index]
        this.transform(id)
        this.attack(player)
        sendChat("Leave these woods and never return!")
        this.isRespawn = false
    }

    override fun finalizeDeath(killer: Entity?) {
        super.finalizeDeath(killer)
    }

    override fun tick() {
        if (!player.location.withinDistance(this.location, 8)) {
            this.terminate()
        }
        super.tick()
        if (!player.viewport.currentPlane.npcs.contains(this)) this.clear()
    }

}