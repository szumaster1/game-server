package content.region.kandarin.battlefield

import cfg.consts.NPCs
import core.api.*
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.game.node.entity.player.Player
import core.game.world.map.zone.ZoneBorders
import core.tools.RandomFunction

/**
 * Represents the Khazard warlords in the Khazard Battlefield south of West Ardougne.
 */
class KhazardTrooperNPC : NPCBehavior(NPCs.KHAZARD_TROOPER_2245, NPCs.KHAZARD_TROOPER_2246) {

    val gnomes = intArrayOf(
        NPCs.GNOME_67,
        NPCs.MOUNTED_TERRORBIRD_GNOME_1752,
        NPCs.GNOME_TROOP_2247,
        NPCs.GNOME_2249,
        NPCs.GNOME_2250,
        NPCs.GNOME_2251,
        NPCs.TORTOISE_3808,
        NPCs.MOUNTED_TERRORBIRD_GNOME_6109
    )

    override fun onCreation(self: NPC) {
        if (inBorders(self, ZoneBorders.forRegion(10034))) {
            self.attack(findLocalNPC(self, gnomes.random()))
            return
        }
    }

    override fun afterDamageReceived(self: NPC, attacker: Entity, state: BattleState) {
        if (getAttribute(self, "battlefield:shout", false)) return
        if (attacker !is Player) {
            sendChat(self, "Get off of me, ya little pest!")
            setAttribute(self, "battlefield:shout", true)
        }
    }

    override fun tick(self: NPC): Boolean {
        if (RandomFunction.random(1, 100) == 5) {
            sendChat(self, "Arrgh! Get the gnome!")
        }

        if (!self.inCombat() && !getAttribute(self, "battlefield:attack", false)) {
            setAttribute(self, "battlefield:attack", true)
            self.attack(findLocalNPC(self, gnomes.random()))
        }
        return true
    }

    override fun onRespawn(self: NPC) {
        removeAttributes(self, "battlefield:shout", "battlefield:attack")
        if (inBorders(self, ZoneBorders.forRegion(10034))) {
            self.attack(findLocalNPC(self, gnomes.random()))
            return
        }
    }

}