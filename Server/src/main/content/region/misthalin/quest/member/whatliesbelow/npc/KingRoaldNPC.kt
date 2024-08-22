package content.region.misthalin.quest.member.whatliesbelow.npc

import cfg.consts.NPCs
import content.region.misthalin.quest.member.whatliesbelow.cutscene.WhatLiesBelowCutscene
import core.game.node.entity.combat.BattleState
import core.game.node.entity.npc.AbstractNPC
import core.game.world.map.Location
import core.plugin.Initializable

/**
 * Handles the King Roald NPC.
 *
 * Related to [What Lies Below][content.region.misthalin.quest.member.whatliesbelow.WhatLiesBelow] quest.
 * @author Vexia
 */
@Initializable
class KingRoaldNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    private var cutscene: WhatLiesBelowCutscene? = null

    /**
     * Constructs a new [KingRoaldNPC][AbstractNPC].
     *
     * @param id       the id.
     * @param location the location.
     */
    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return KingRoaldNPC(id, location)
    }

    override fun checkImpact(state: BattleState) {
        val lp = getSkills().lifepoints
        if (state.estimatedHit > -1) {
            if (lp - state.estimatedHit < 1) {
                state.estimatedHit = 0
                if (lp > 1) {
                    state.estimatedHit = lp - 1
                }
            }
        }
        if (state.secondaryHit > -1) {
            if (lp - state.secondaryHit < 1) {
                state.secondaryHit = 0
                if (lp > 1) {
                    state.secondaryHit = lp - 1
                }
            }
        }
        val totalHit = state.estimatedHit + state.secondaryHit
        if (lp - totalHit < 1) {
            state.estimatedHit = 0
            state.secondaryHit = 0
        }
        if (lp <= 1 && !getAttribute("message", false)) {
            setAttribute("message", true)
            cutscene!!.player.sendMessage("Now would be a good time to summon Zaff!")
        }
    }

    /**
     * Sets the cutscene.
     *
     * @param cutscene the cutscene.
     */
    fun setCutscene(cutscene: WhatLiesBelowCutscene?) {
        this.cutscene = cutscene
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.KING_ROALD_5838)
    }
}
