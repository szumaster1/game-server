package content.global.skill.combat.summoning.familiar.npc

import content.global.skill.combat.summoning.familiar.BurdenBeast
import content.global.skill.combat.summoning.familiar.Familiar
import content.global.skill.combat.summoning.familiar.FamiliarSpecial
import org.rs.consts.NPCs
import core.api.visualize
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable

/**
 * Spirit terrorbird familiar.
 */
@Initializable
class SpiritTerrorbirdNPC @JvmOverloads constructor(owner: Player? = null, id: Int = NPCs.SPIRIT_TERRORBIRD_6794) : BurdenBeast(owner, id, 3600, 12007, 8, 12, WeaponInterface.STYLE_CONTROLLED) {

    override fun construct(owner: Player, id: Int): Familiar {
        return SpiritTerrorbirdNPC(owner, id)
    }

    override fun specialMove(special: FamiliarSpecial): Boolean {
        visualize(Animation.create(1009), Graphic.create(1521))
        owner.getSkills().updateLevel(Skills.AGILITY, 2)
        owner.settings.updateRunEnergy(-owner.getSkills().getStaticLevel(Skills.AGILITY) / 2.0)
        return true
    }

    override fun visualizeSpecialMove() {
        visualize(owner, Animation(7660), Graphic(1295))
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SPIRIT_TERRORBIRD_6794, NPCs.SPIRIT_TERRORBIRD_6795)
    }

}
