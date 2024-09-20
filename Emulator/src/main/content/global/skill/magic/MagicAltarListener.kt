package content.global.skill.magic

import core.api.*
import org.rs.consts.Animations
import org.rs.consts.Scenery
import org.rs.consts.Sounds
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.SpellBookManager.SpellBook
import core.game.node.entity.skill.Skills

/**
 * Magic altar listener.
 */
class MagicAltarListener : InteractionListener {

    override fun defineListeners() {
        on(intArrayOf(
            content.global.skill.magic.MagicAltarListener.Companion.ANCIENT_ALTAR,
            content.global.skill.magic.MagicAltarListener.Companion.LUNAR_ALTAR
        ), IntType.SCENERY, "pray-at", "pray") { player, node ->
            if (meetsRequirements(player, node)) {
                swapSpellBook(player, node)
            }
            return@on true
        }
    }

    /**
     * Meets requirements.
     *
     * @param [player] the player who is interacting with the altar.
     * @param [altar] the altar that the player is interacting with.
     *
     * @return `true` if the player meets the requirements to interact with the altar, `false` otherwise.
     */
    private fun meetsRequirements(player: Player, altar: Node): Boolean {
        val level = if (altar.id == content.global.skill.magic.MagicAltarListener.Companion.ANCIENT_ALTAR) 50 else 65
        if (!hasRequirement(player, if (altar.id == content.global.skill.magic.MagicAltarListener.Companion.ANCIENT_ALTAR) "Desert Treasure" else "Lunar Diplomacy")) {
            return false
        }
        if (!hasLevelStat(player, Skills.MAGIC, level)) {
            sendMessage(player, "You need a Magic level of at least $level in order to do this.")
            return false
        }

        return true
    }

    /**
     * Swap spell book.
     *
     * @param [player] the player who is swapping the spell book.
     * @param [altar] the altar node that the player is interacting with.
     */
    private fun swapSpellBook(player: Player, altar: Node) {
        lock(player, 3)
        playAudio(player, Sounds.PRAYER_RECHARGE_2674)
        animate(player, Animations.HUMAN_PRAY_645)

        if (altar.id == content.global.skill.magic.MagicAltarListener.Companion.ANCIENT_ALTAR) {
            player.skills.decrementPrayerPoints(player.skills.prayerPoints)
        }
        if (SpellBook.forInterface(player.spellBookManager.spellBook) == if (altar.id == content.global.skill.magic.MagicAltarListener.Companion.ANCIENT_ALTAR) SpellBook.ANCIENT else SpellBook.LUNAR) {
            sendMessage(player, if (altar.id == content.global.skill.magic.MagicAltarListener.Companion.ANCIENT_ALTAR) "You feel a strange drain upon your memory..." else "Modern spells activated!")
            player.spellBookManager.setSpellBook(SpellBook.MODERN)
            player.spellBookManager.update(player)
        } else {
            sendMessage(player, if (altar.id == content.global.skill.magic.MagicAltarListener.Companion.ANCIENT_ALTAR) "You feel a strange wisdom fill your mind..." else "Lunar spells activated!")
            player.spellBookManager.setSpellBook(if (altar.id == content.global.skill.magic.MagicAltarListener.Companion.ANCIENT_ALTAR) SpellBook.ANCIENT else SpellBook.LUNAR)
            player.spellBookManager.update(player)
        }
    }

    companion object {
        private const val ANCIENT_ALTAR = Scenery.ALTAR_6552
        private const val LUNAR_ALTAR = Scenery.ALTAR_17010
    }
}
