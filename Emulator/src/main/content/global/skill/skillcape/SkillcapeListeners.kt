package content.global.skill.skillcape

import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Handles skillcape interactions.
 */
class SkillcapeListeners : InteractionListener {

    private val capeIds = ArrayList<Int>()

    override fun defineListeners() {

        for (cape in content.data.skill.SkillcapePerks.values()) {
            cape.skillcapeIds.forEach { capeIds.add(it) }
        }

        val capes = capeIds.toIntArray()

        /*
         * Activate the perk on equip the cape.
         */

        onEquip(capes) { player, node ->
            val skillcape = Skillcape.forId(node.id)
            val perk = SkillcapePerks.forSkillcape(skillcape)
            perk.activate(player)
            return@onEquip true
        }

        /*
         * Deactivate the perk on un-equip the cape.
         */

        onUnequip(capes) { player, node ->
            val skillcape = Skillcape.forId(node.id)
            val perk = SkillcapePerks.forSkillcape(skillcape)
            perk.deactivate(player)
            return@onUnequip true
        }

        /*
         * Use the cape perk from equipment.
         */

        on(capes, IntType.ITEM, "operate") { player, node ->
            val skillcape = Skillcape.forId(node.id ?: 0)
            val perk = SkillcapePerks.forSkillcape(skillcape)
            perk.operate(player)
            return@on true
        }
    }
}
