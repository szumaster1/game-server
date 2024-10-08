package content.region.kandarin.battlefield

import org.rs.consts.NPCs
import core.api.*
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.game.world.map.zone.ZoneBorders
import core.tools.RandomFunction

/**
 * Represents the Khazard's Army in the Khazard Battlefield south of West Ardougne.
 */
class GnomeNPC : NPCBehavior(
    NPCs.GNOME_67,
    NPCs.MOUNTED_TERRORBIRD_GNOME_1752,
    NPCs.GNOME_TROOP_2247,
    NPCs.GNOME_2249,
    NPCs.GNOME_2250,
    NPCs.GNOME_2251,
    NPCs.TORTOISE_3808,
    NPCs.MOUNTED_TERRORBIRD_GNOME_6109
) {

    val troopers = intArrayOf(NPCs.KHAZARD_TROOPER_2245, NPCs.KHAZARD_TROOPER_2246)

    override fun onCreation(self: NPC) {
        if (inBorders(self, ZoneBorders.forRegion(10034))) {
            self.attack(findLocalNPC(self, troopers.random()))
            return
        }
    }

    override fun tick(self: NPC): Boolean {
        if (RandomFunction.random(1, 100) == 5) {
            sendChat(self, "Tally Ho!")
        }

        if (!self.inCombat() && !getAttribute(self, "battlefield:attack", false)) {
            setAttribute(self, "battlefield:attack", true)
            self.attack(findLocalNPC(self, troopers.random()))
        }
        return true
    }

    override fun onRespawn(self: NPC) {
        removeAttribute(self, "battlefield:attack")
        if (inBorders(self, ZoneBorders.forRegion(10034))) {
            self.attack(findLocalNPC(self, troopers.random()))
            return
        }
    }

}