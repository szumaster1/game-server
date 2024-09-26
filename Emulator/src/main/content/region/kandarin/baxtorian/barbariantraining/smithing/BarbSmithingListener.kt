package content.region.kandarin.baxtorian.barbariantraining.smithing

import org.rs.consts.Items
import org.rs.consts.Scenery
import content.global.skill.skillcape.SkillcapePerksEffect
import core.api.*
import core.game.dialogue.SkillDialogueHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.skill.Skills

class BarbSmithingListener : InteractionListener {

    private val bars = BarbarianWeapon.values().map(BarbarianWeapon::requiredBar).toIntArray()

    override fun defineListeners() {

        /*
         * Used bar on barbarian anvil.
         */

        onUseWith(IntType.SCENERY, bars, Scenery.BARBARIAN_ANVIL_25349) { player, used, _ ->
            val weapon = BarbarianWeapon.weaponMap[used.id] ?: return@onUseWith true
            if (getStatLevel(player, Skills.SMITHING) < weapon.requiredLevel) {
                sendMessage(player, "You need a Smithing level of ${weapon.requiredLevel} to make this.")
                return@onUseWith false
            }
            if (!inInventory(player, Items.HAMMER_2347) && !SkillcapePerksEffect.isActive(SkillcapePerksEffect.BAREFISTED_SMITHING, player)) {
                sendDialogue(player, "You need a hammer to work the metal with.")
                return@onUseWith false
            }
            if (!inInventory(player, weapon.requiredWood)) {
                sendMessage(player, "You don't have the necessary logs for the weapon.")
                return@onUseWith false
            }
            if (!inInventory(player, weapon.requiredBar)) {
                return@onUseWith false
            }

            val dialogue: SkillDialogueHandler =
                object : SkillDialogueHandler(player, SkillDialogue.TWO_OPTION, weapon.spearId, weapon.hastaId) {
                    override fun create(amount: Int, index: Int) {
                        submitIndividualPulse(
                            entity = player,
                            pulse = BarbSmithingPulse(player, weapon, 1, index)
                        )
                    }

                    override fun getAll(index: Int): Int {
                        return amountInInventory(player, used.id)
                    }
                }
            dialogue.open()
            return@onUseWith true
        }
    }
}