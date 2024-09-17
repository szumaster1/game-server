package content.region.fremennik.neitiznot.handlers

import org.rs.consts.NPCs
import core.api.registerMapZone
import core.api.sendMessage
import core.game.node.Node
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.map.zone.MapZone
import core.game.world.map.zone.ZoneBorders

class YakZone : MapZone("Yakzone", true) {

    override fun register(borders: ZoneBorders?) {
        return registerMapZone(this, ZoneBorders(2313, 3786, 2331, 3802))
    }

    override fun handleUseWith(player: Player, used: Item?, with: Node?): Boolean {
        // Check if the player interacts with a specific NPC
        if (with is NPC && with.id == NPCs.YAK_5529) {
            sendMessage(player, "The cow doesn't want that.")
            return true
        }
        return false
    }
}