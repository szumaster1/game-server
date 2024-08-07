package content.global.skill.production.runecrafting.item.tiara

import content.global.skill.production.runecrafting.data.Altar
import content.global.skill.production.runecrafting.data.Talisman
import content.global.skill.production.runecrafting.data.TalismanStaff
import core.api.submitIndividualPulse
import core.game.dialogue.SkillDialogueHandler
import core.game.dialogue.SkillDialogueHandler.SkillDialogue.ONE_OPTION
import core.game.node.entity.player.Player

/**
 * Enchant tiara dialogue
 *
 * @property talisman
 * @property tiara
 * @property altar
 * @constructor
 *
 * @param player
 */
class EnchantTiaraDialogue(player: Player, val talisman: Talisman, val tiara: TalismanStaff, val altar: Altar) :
    SkillDialogueHandler(player, ONE_OPTION, tiara.item) {

    override fun create(amount: Int, index: Int) {
        submitIndividualPulse(player, EnchantTiaraPulse(player, talisman, altar, tiara, amount))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EnchantTiaraDialogue

        if (talisman != other.talisman) return false
        if (tiara != other.tiara) return false
        if (altar != other.altar) return false

        return true
    }

    override fun hashCode(): Int {
        var result = talisman.hashCode()
        result = 31 * result + tiara.hashCode()
        result = 31 * result + altar.hashCode()
        return result
    }
}
