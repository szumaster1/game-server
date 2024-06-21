package content.global.skill.combat.prayer

import core.api.*
import core.api.consts.Animations
import core.api.consts.Scenery
import core.api.consts.Sounds
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.SpellBookManager.SpellBook
import core.game.node.entity.player.link.SpellBookManager.SpellBook.Companion.forInterface
import core.game.node.entity.skill.Skills
import core.game.world.map.Location

class PrayerAltarListener : InteractionListener {

    override fun defineListeners() {

        /*
         *  Altar interactions.
         */

        on(IntType.SCENERY, "pray-at", "pray") { player, node ->
            val altar = Altar.forId(node.id)
            if (altar != null) {
                altar.pray(player)
                visualize(player)
                return@on true
            }
            if (player.getSkills().prayerPoints == getStatLevel(player, Skills.PRAYER).toDouble()) {
                sendMessage(player, "You already have full prayer points.")
                return@on true
            }
            visualize(player)
            player.getSkills().rechargePrayerPoints()
            sendMessage(player, "You recharge your Prayer points.")
            if (node.id == Scenery.ALTAR_2640) {
                player.getSkills().setLevel(Skills.PRAYER, getStatLevel(player, Skills.PRAYER) + 2)
            }
            if (node.asScenery().location == Location(2571, 9499, 0)) {
                teleport(player, Location(2583, 9576, 0))
                sendMessage(player, "It's a trap!")
                return@on true
            }
            return@on false
        }

    }

    fun visualize(player: Player) {
        lock(player, 3)
        playAudio(player, Sounds.PRAYER_RECHARGE_2674)
        animate(player, Animations.HUMAN_PRAY_645)
    }

    enum class Altar(
        val id: Int,
        val book: Int,
        vararg messages: String
    ) {
        ANCIENT(
            Scenery.ALTAR_6552,
            SpellBook.ANCIENT.interfaceId,
            "You feel a strange wisdom fill your mind...",
            "You feel a strange drain upon your memory..."
        ) {
            override fun pray(player: Player) {
                if (!hasRequirement(player, "Desert Treasure")) return
                if (getStatLevel(player, Skills.MAGIC) < 50) {
                    sendMessage(player, "You need a Magic level of at least 50 in order to do this.")
                    return
                }
                drain(player)
                if (!isPrayerType(player)) {
                    switchToBook(player)
                    sendMessage(player, messages[0])
                } else {
                    revert(player)
                    sendMessage(player, messages[1])
                }
            }
        },

        LUNAR(
            Scenery.ALTAR_17010,
            SpellBook.LUNAR.interfaceId,
            "Lunar spells activated!",
            "Lunar spells deactivated!"
        ) {
            override fun pray(player: Player) {
                if (!hasRequirement(player, "Lunar Diplomacy")) return
                if (getStatLevel(player, Skills.MAGIC) < 65) {
                    sendMessage(player, "You need a Magic level of at least 65 in order to do this.")
                    return
                }
                if (!isPrayerType(player)) {
                    switchToBook(player)
                    sendMessage(player, messages[0])
                } else {
                    revert(player)
                    sendMessage(player, messages[1])
                }
            }
        };

        val messages: Array<String> = messages as Array<String>

        open fun pray(player: Player) {
        }

        fun revert(player: Player) {
            player.spellBookManager.setSpellBook(SpellBook.MODERN)
            openSingleTab(player, SpellBook.values()[SpellBook.MODERN.ordinal].interfaceId)
        }

        fun drain(player: Player) {
            player.getSkills().decrementPrayerPoints(player.getSkills().prayerPoints)
        }

        fun switchToBook(player: Player) {
            player.spellBookManager.setSpellBook(forInterface(book)!!)
            openSingleTab(player, book)
        }

        fun isPrayerType(player: Player): Boolean {
            return player.spellBookManager.spellBook == book
        }

        companion object {
            fun forId(id: Int): Altar? {
                for (altar in values()) {
                    if (id == altar.id) {
                        return altar
                    }
                }
                return null
            }
        }
    }

}
