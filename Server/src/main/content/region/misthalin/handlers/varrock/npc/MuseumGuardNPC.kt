package content.region.misthalin.handlers.varrock.npc

import core.api.consts.NPCs
import core.api.sendChat
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.tools.RandomFunction

/*
    Workman: Another lot for ya!
    Tinse Torpe or Sinco Doar: Alright, thanks!
    Workman: Off we go again!
    Upon leaving Varrock, near the guards stationed at the easterne exit
    Workman: How's it going, officers?
    Guard: Another boring day.
    Workman: Nothing new there.
    Upon leaving Varrock, if speaking to a guard who is under attack from a player
    Workman: How's it going, officers?
    Guard: I'm being slaughtered!
    Workman: Nothing new there.
    Upon reaching the gate next to the museum guard after leaving Varrock
    Museum Guard: Don't daudle there!
    Workman: Yeah, yeah, I'm movin' guv.
    Upon returning to the gate next to the museum guard when return to Varrock
    Museum Guard: Keep 'em coming!
    Workman: Bleah
 */

/**
 * Represents the Museum guard NPC.
 */
class MuseumGuardNPC : NPCBehavior(*guardsNPCs) {

    companion object{
        private val guardsNPCs = intArrayOf(
            NPCs.MUSEUM_GUARD_5941,
            NPCs.MUSEUM_GUARD_5942,
            NPCs.MUSEUM_GUARD_5943,
        )
    }

    private val forceChat =
        arrayOf(
            "Another boring day.",
            "Nothing new there.",
            "Keep 'em coming!",
            "Don't daudle there!"
        )

    override fun onCreation(self: NPC) {
        self.isNeverWalks = false
        self.isWalks = false
    }

    override fun tick(self: NPC): Boolean {
        if (RandomFunction.random(35) == 5) {
            sendChat(self, forceChat.random())
        }
        return true
    }
}
