package content.region.kandarin.observatory.quest.itgronigen.npc

import cfg.consts.NPCs
import core.api.sendChat
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.tools.RandomFunction

/**
 * Lost goblin NPC.
 */
class LostGoblinNPC : NPCBehavior(NPCs.GOBLIN_6126) {

    /*
     * There is a lost goblin wandering aimlessly around the dungeon
     * trying to find his way out. He constantly exclaims about how
     * confusing the tunnels are.
     * These goblins speak proper English unlike most other goblins.
     */

    val forceChat = arrayOf(
        "Which way should I go?",
        "These dungeons are such a maze.",
        "Where's the exit?!?",
        "This is the fifth time this week. I'm lost!",
        "I've been wandering around down here for hours.",
        "How do you get back to the village?",
        "I hate being so lost!",
        "How could I be so disoriented?",
        "Where am I? I'm so lost.",
        "I know the exit's around here, somewhere."
    )

    override fun tick(self: NPC): Boolean {
        if (RandomFunction.random(15) == 5) {
            sendChat(self, forceChat.random())
        }
        return true
    }

    /*
        override fun onCreation(self: NPC) {
        // Movement path: https://i.imgur.com/qk15XlU.png
        // Should cover the whole area but that's all I've seen.
        val movementPath = arrayOf(
                Location.create(2355, 9390, 0),
                Location.create(2349, 9390, 0),
                Location.create(2343, 9385, 0),
                Location.create(2340, 9380, 0),
                Location.create(2347, 9379, 0),
                Location.create(2342, 9378, 0),
                Location.create(2344, 9386, 0),
                Location.create(2349, 9390, 0),
                Location.create(2355, 9390, 0),
                Location.create(2357, 9385, 0),
                Location.create(2363, 9380, 0),
                Location.create(2364, 9372, 0),
                Location.create(2364, 9368, 0),
                Location.create(2362, 9362, 0),
                Location.create(2358, 9359, 0),
                Location.create(2356, 9353, 0),
                Location.create(2351, 9358, 0),
                Location.create(2355, 9353, 0),
                Location.create(2358, 9359, 0),
                Location.create(2358, 9365, 0),
                Location.create(2357, 9361, 0),
                Location.create(2364, 9365, 0),
                Location.create(2364, 9375, 0),
                Location.create(2361, 9381, 0),
                Location.create(2355, 9388, 0)
            )
            self.configureMovementPath(*movementPath)
            self.isWalks = true
        }
    }
   */
}
