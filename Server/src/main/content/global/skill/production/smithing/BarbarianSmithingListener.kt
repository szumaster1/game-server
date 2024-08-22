package content.global.skill.production.smithing

import content.global.skill.production.smithing.data.BarbarianWeapon
import content.global.skill.production.smithing.item.BarbarianSmithingPulse
import content.global.skill.skillcape.SkillcapePerks
import core.api.*
import cfg.consts.Items
import cfg.consts.Scenery
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.skill.Skills

/**
 * Barbarian smithing listener.
 */
class BarbarianSmithingListener : InteractionListener {

    private val bars = BarbarianWeapon.values().map(BarbarianWeapon::requiredBar).toIntArray()

    override fun defineListeners() {

        /**
         * Used bar on barbarian anvil.
         */
        onUseWith(IntType.SCENERY, bars, Scenery.BARBARIAN_ANVIL_25349) { player, used, _ ->
            val weapon = BarbarianWeapon.weaponMap[used.id] ?: return@onUseWith true
            if (getStatLevel(player, Skills.SMITHING) < weapon.requiredLevel) {
                sendMessage(player, "You need a Smithing level of ${weapon.requiredLevel} to make this.")
                return@onUseWith false
            }
            if (!inInventory(player, Items.HAMMER_2347) && !SkillcapePerks.isActive(SkillcapePerks.BAREFISTED_SMITHING, player)) {
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
            sendDoubleItemOptions(player, "What would you like to make?", weapon.spearId, weapon.hastaId, "a " + getItemName(weapon.spearId) + ".", "a " + getItemName(weapon.hastaId) + ".")
            addDialogueAction(player) { player, buttonID ->
                submitIndividualPulse(player, BarbarianSmithingPulse(player, weapon, 1, buttonID))
            }
            return@onUseWith true
        }
    }
}