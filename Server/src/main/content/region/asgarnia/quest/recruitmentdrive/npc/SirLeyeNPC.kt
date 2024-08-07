package content.region.asgarnia.quest.recruitmentdrive.npc

import content.region.asgarnia.quest.recruitmentdrive.RecruitmentDrive
import content.region.asgarnia.quest.recruitmentdrive.dialogue.SirKuamFerentseDialogueFile
import core.api.*
import core.api.consts.NPCs
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Location
import core.plugin.Initializable

/**
 * Sir leye NPC.
 */
@Initializable
class SirLeyeNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {
    var clearTime = 0
    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return SirLeyeNPC(id, location)
    }


    override fun handleTickActions() {
        super.handleTickActions()
        if (clearTime++ > 288) poofClear(this)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SIR_LEYE_2285)
    }

    companion object {
        fun spawnSirLeye(player: Player) {
            val leye = SirLeyeNPC(NPCs.SIR_LEYE_2285)
            leye.location = location(2457, 4966, 0)
            leye.isWalks = true
            leye.isAggressive = true
            leye.isActive = false
            leye.isRespawn = false

            if (leye.asNpc() != null && leye.isActive) {
                leye.properties.teleportLocation = leye.properties.spawnLocation
            }
            leye.isActive = true
            GameWorld.Pulser.submit(object : Pulse(1, leye) {
                override fun pulse(): Boolean {
                    leye.init()
                    registerHintIcon(player, leye)
                    leye.attack(player)
                    sendChat(leye, "No man may defeat me!")
                    return true
                }
            })
        }
    }

    override fun checkImpact(state: BattleState) {
        super.checkImpact(state)
        val player = state.attacker
        if (player is Player) {
            if (player.isMale && state.style == CombatStyle.MELEE) {
                if (state.estimatedHit > -1) {
                    state.estimatedHit = 0
                    return
                }
                if (state.secondaryHit > -1) {
                    state.secondaryHit = 0
                    return
                }
            } else {
                state.neutralizeHits()
                state.estimatedHit = state.maximumHit
            }
        }
    }

    override fun finalizeDeath(killer: Entity?) {
        if (killer is Player) {
            clearHintIcon(killer)
            setAttribute(killer, RecruitmentDrive.ATTRIBUTE_RD_STAGE_PASSED, true)
            openDialogue(killer, SirKuamFerentseDialogueFile(1), NPC(NPCs.SIR_KUAM_FERENTSE_2284))
        }
        clear()
        super.finalizeDeath(killer)
    }
}