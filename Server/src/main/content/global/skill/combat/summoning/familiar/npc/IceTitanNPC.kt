package content.global.skill.combat.summoning.familiar.npc

import content.global.skill.combat.summoning.familiar.Familiar
import core.api.consts.NPCs
import core.api.visualize
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable

@Initializable
class IceTitanNPC(owner: Player? = null, id: Int = NPCs.ICE_TITAN_7359) :
    ElementalTitanNPC(owner, id, 6400, 12806, 20, WeaponInterface.STYLE_ACCURATE) {

    override fun construct(owner: Player, id: Int): Familiar {
        return IceTitanNPC(owner, id)
    }

    override fun visualizeSpecialMove() {
        visualize(owner, Animation(7660), Graphic(1306))
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ICE_TITAN_7359, NPCs.ICE_TITAN_7360)
    }

}
