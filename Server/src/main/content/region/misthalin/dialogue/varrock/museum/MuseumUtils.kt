package content.region.misthalin.dialogue.varrock.museum

import core.api.consts.Components
import core.api.consts.NPCs
import core.api.closeInterface
import core.api.lock
import core.api.lockInteractions
import core.api.openInterface
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld

object MuseumUtils {

    val NATURAL_HISTORIAN = NPC(NPCs.NATURAL_HISTORIAN_5967)
    val TEACHER_AND_PUPIL = NPC(NPCs.TEACHER_AND_PUPIL_5944)

    // ======================================================
    // Refs: https://youtu.be/vayf-9IaPYM?si=_3UV44Ai9vzpiLgV
    // QUEST REQUIRED: The Digsite (to clean artifacts)
    // FOR EXTRA KUDOS: Demon Slayer ,Ghostly Robes Mini Quest, Grand Tree, Hazeel Cult, In Aid of the Myreque, Making History, Merlin's Crystal, Observatory Quest, Priest in Peril, Rune Mysteries, Shield of Arrav, Tail or Two Cats, Temple of Ikov, What Lies Below.
    // ITEMS REQUIRED: For Cleaning Artifacts ONLY: Leather Boots, Leather Gloves, Rock Pick, Specimen Brush, Trowel.
    // ======================================================

    fun initLearning(player: Player) {
        GameWorld.Pulser.submit(object : Pulse(0) {
            var counter = 0
            override fun pulse(): Boolean {
                when (counter++) {
                    0 -> {
                        lock(player, 10000000)
                        lockInteractions(player, 1000000)
                        openInterface(player, Components.FADE_TO_BLACK_120)
                    }
                    6 -> {
                        closeInterface(player)
                        openInterface(player, Components.FADE_FROM_BLACK_170)
                    }
                    9 -> {
                        closeInterface(player)
                        return true
                    }
                }
                return false
            }
        })
    }
}