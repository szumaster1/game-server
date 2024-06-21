package content.region.kandarin.quest.fightarena.npc

import content.region.kandarin.quest.fightarena.FightArenaListeners.Companion.General
import content.region.kandarin.quest.fightarena.dialogue.JeremyServilDialogueFile
import core.api.consts.NPCs
import core.api.getQuestStage
import core.api.openDialogue
import core.api.setQuestStage
import core.game.node.entity.Entity
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.plugin.Initializable

@Initializable
class GeneralNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {
    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return GeneralNPC(id, location)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GENERAL_KHAZARD_258)
    }

    override fun handleTickActions() {
        super.handleTickActions()
        General.asNpc().isRespawn = true
        General.respawnTick = 10
    }

    override fun finalizeDeath(killer: Entity?) {
        if (killer is Player) {
            val quest = "Fight Arena"
            if (getQuestStage(killer, quest) == 97) {
                setQuestStage(killer, "Fight Arena", 98)
                openDialogue(killer.asPlayer(), JeremyServilDialogueFile())
            }
        }
        super.finalizeDeath(killer)
    }
}