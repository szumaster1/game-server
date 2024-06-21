package content.global.skill.skillcape

import core.game.interaction.IntType
import core.game.interaction.InteractionListener

class SkillcapeListeners : InteractionListener {

    private val capeIds = ArrayList<Int>()

    override fun defineListeners() {

        /*
            Interactions related with Perks.
            https://cdn.2009scape.org/wiki/skill_guides/skill_cape_perks
         */

        for (cape in content.data.skill.SkillcapePerks.values()) {
            cape.skillcapeIds.forEach { capeIds.add(it) }
        }

        val capes = capeIds.toIntArray()

        onEquip(capes) { player, node ->
            val skillcape = Skillcape.forId(node.id)
            val perk = SkillcapePerks.forSkillcape(skillcape)
            perk.activate(player)
            return@onEquip true
        }

        onUnequip(capes) { player, node ->
            val skillcape = Skillcape.forId(node.id)
            val perk = SkillcapePerks.forSkillcape(skillcape)
            perk.deactivate(player)
            return@onUnequip true
        }

        on(capes, IntType.ITEM, "operate") { player, node ->
            val skillcape = Skillcape.forId(node.id ?: 0)
            val perk = SkillcapePerks.forSkillcape(skillcape)
            perk.operate(player)
            return@on true
        }
    }

}
