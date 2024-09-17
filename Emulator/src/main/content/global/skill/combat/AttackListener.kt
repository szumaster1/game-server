package content.global.skill.combat

import org.rs.consts.NPCs
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.combat.CombatStyle

/**
 * Attack listener.
 */
class AttackListener : InteractionListener {
    override fun defineListeners() {
        flagInstant()
        /*
         * Makes sure player uses correct attack
         * styles for Lumbridge dummies.
         */
        on(IntType.NPC, "attack") { player, npc ->
            if (npc.id == NPCs.MAGIC_DUMMY_4474 && player.getSwingHandler(false).type != CombatStyle.MAGIC) {
                sendMessage(player, "You can only attack this with magic.")
                return@on true
            }

            if (npc.id == NPCs.MELEE_DUMMY_7891 && player.getSwingHandler(false).type != CombatStyle.MELEE) {
                sendMessage(player, "You must use the training sword to attack this.")
                return@on true
            }
            player.attack(npc)
            return@on true
        }
    }
}