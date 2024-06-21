package content.region.asgarnia.quest.trollstronghold.npc

import core.api.consts.Items
import core.api.consts.NPCs
import core.api.inInventory
import core.api.isQuestInProgress
import core.api.produceGroundItem
import core.game.node.entity.Entity
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.plugin.Initializable

@Initializable
class TwigNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {
    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return TwigNPC(id, location)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.TWIG_1126, NPCs.TWIG_1128)
    }

    override fun isAttackable(entity: Entity, style: CombatStyle, message: Boolean): Boolean {
        val attackable = super.isAttackable(entity, style, message)
        if (this.id == NPCs.TWIG_1128) {
            val prevLifePoints = this.skills.lifepoints
            this.transform(NPCs.TWIG_1126)
            this.skills.lifepoints = prevLifePoints
        }
        return attackable
    }

    override fun handleTickActions() {
        super.handleTickActions()
        if (getAttribute("return-to-spawn", false) && this.id == NPCs.TWIG_1126) {
            val prevLifePoints = this.skills.lifepoints
            this.transform(NPCs.TWIG_1128)
            this.skills.lifepoints = prevLifePoints
        }
    }

    override fun finalizeDeath(killer: Entity?) {
        if (isQuestInProgress(killer as Player, "Troll Stronghold", 8, 10) && !inInventory(killer, Items.CELL_KEY_1_3136)) {
            produceGroundItem(killer, Items.CELL_KEY_1_3136, 1, this.location)
        }
        super.finalizeDeath(killer)
    }
}