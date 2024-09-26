package content.region.asgarnia.goblinvillage.handlers

import org.rs.consts.NPCs
import org.rs.consts.Scenery
import core.api.sendNPCDialogue
import core.api.sendPlainDialogue
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.combat.DeathTask
import core.game.world.map.RegionManager.getLocalNpcs

class GoblinVillageListener : InteractionListener {

    override fun defineListeners() {

        /*
         * Handling talk with goblin in Gnome labirynth.
         */

        on(NPCs.GOBLIN_444, IntType.NPC, "Talk-To") { player, _ ->
            sendNPCDialogue(player, NPCs.GOBLIN_444, "Go away, human!", FacialExpression.OLD_ANGRY1)
            return@on true
        }

        /*
         * Handling read interaction with signpost.
         */

        on(Scenery.SIGNPOST_31301, IntType.SCENERY, "read") { player, _ ->
            var population = 3
            val npcs = getLocalNpcs(player, 50)
            for (n in npcs) {
                if (n.name == "Goblin" && !DeathTask.isDead(n)) {
                    population++
                }
                sendPlainDialogue(player, false, "Welcome to Goblin Village.", "Current population: $population")
            }
            return@on true
        }
    }

}