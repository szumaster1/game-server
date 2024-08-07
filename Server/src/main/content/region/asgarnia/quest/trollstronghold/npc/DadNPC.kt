package content.region.asgarnia.quest.trollstronghold.npc

import content.region.asgarnia.quest.trollstronghold.dialogue.DadTSDialogueFile
import core.api.*
import core.api.consts.NPCs
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.npc.AbstractNPC
import core.game.system.task.Pulse
import core.game.world.map.Location
import core.plugin.Initializable

/**
 * Dad NPC.
 */
@Initializable
class DadNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {
    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return DadNPC(id, location)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DAD_1125)
    }

    override fun isAttackable(entity: Entity, style: CombatStyle, message: Boolean): Boolean {
        val attackable = super.isAttackable(entity, style, message)
        val player = entity.asPlayer()

        // Attack Dad. If quest is done, you cannot attack Dad.
        when (getQuestStage(player, "Troll Stronghold")) {
            3 -> openDialogue(player, DadTSDialogueFile(2), this.asNpc()).also { return false }
            4 -> {
                return attackable
            }

            in 5..100 -> sendMessage(player, "You don't need to fight him again.").also { return false }
        }
        return attackable
    }

    override fun checkImpact(state: BattleState) {
        super.checkImpact(state)
        val player = state.attacker
        val opponent = state.victim
        // If dad is below 30/120 lifepoints, give player option
        // Dad can be killed if you oneshot him past 30.
        if (opponent.skills.lifepoints < 30) {
            player.properties.combatPulse.stop()
            opponent.properties.combatPulse.stop()
            if (getQuestStage(player!!.asPlayer(), "Troll Stronghold") == 4) {
                setQuestStage(player.asPlayer(), "Troll Stronghold", 5)
            }
            submitWorldPulse(object : Pulse() {
                var counter = 0
                override fun pulse(): Boolean {
                    when (counter++) {
                        1 -> {
                            openDialogue(player.asPlayer(), DadTSDialogueFile(3), opponent.asNpc())
                            return true
                        }
                    }
                    return false
                }
            })
        }
    }

    override fun finalizeDeath(killer: Entity?) {
        // In case Dad gets one shotted to death.
        super.finalizeDeath(killer)
        if (getQuestStage(killer!!.asPlayer(), "Troll Stronghold") == 4) {
            setQuestStage(killer.asPlayer(), "Troll Stronghold", 5)
        }
    }

}
