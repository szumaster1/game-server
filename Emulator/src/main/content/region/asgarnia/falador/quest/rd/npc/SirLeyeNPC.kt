package content.region.asgarnia.falador.quest.rd.npc

import org.rs.consts.NPCs
import content.region.asgarnia.falador.quest.rd.RecruitmentDrive
import content.region.asgarnia.falador.quest.rd.dialogue.SirKuamFerentseDialogueFile
import core.api.*
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills

/**
 * Represents the Sir Leye NPC.
 * @author Ovenbread
 */
class SirLeyeNPC : NPCBehavior(NPCs.SIR_LEYE_2285) {

    private var clearTime = 0

    override fun tick(self: NPC): Boolean {
        if (++clearTime > 288) {
            clearTime = 0
            poofClear(self)
        }
        return true
    }

    override fun beforeDamageReceived(self: NPC, attacker: Entity, state: BattleState) {
        val lifepoints = self.skills.lifepoints
        if (attacker is Player) {
            if (attacker.isMale) {
                if (state.estimatedHit + Integer.max(state.secondaryHit, 0) > lifepoints - 1) {
                    self.skills.lifepoints = self.getSkills().getStaticLevel(Skills.HITPOINTS)
                }
            }
        }
    }

    override fun onDeathFinished(self: NPC, killer: Entity) {
        if (killer is Player) {
            clearHintIcon(killer)
            setAttribute(killer, RecruitmentDrive.stagePass, true)
            openDialogue(killer, SirKuamFerentseDialogueFile(1), NPC(NPCs.SIR_KUAM_FERENTSE_2284))
            removeAttribute(killer, SirKuamFerentseDialogueFile.spawnSirLeye)
        }
    }

    override fun getXpMultiplier(self: NPC, attacker: Entity): Double = 0.0
}
