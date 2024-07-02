package content.global.handlers.item

import core.api.*
import core.api.consts.Animations
import core.api.consts.Items
import core.game.dialogue.DialogueFile
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.update.flag.context.Animation

class GodBooksListener : InteractionListener {

    private val saradominGodBook = Items.HOLY_BOOK_3840
    private val zamorakGodBook = Items.UNHOLY_BOOK_3842
    private val guthixGodBook = Items.BOOK_OF_BALANCE_3844

    override fun defineListeners() {

        /*
         * God Books related interactions.
         */

        on(saradominGodBook, IntType.ITEM, "preach") { player, _ ->
            openDialogue(player, HolyDialogue(BOOK.SARA))
            return@on true
        }

        on(zamorakGodBook, IntType.ITEM, "preach") { player, _ ->
            openDialogue(player, HolyDialogue(BOOK.ZAM))
            return@on true
        }

        on(guthixGodBook, IntType.ITEM, "preach") { player, _ ->
            openDialogue(player, HolyDialogue(BOOK.GUTHIX))
            return@on true
        }
    }

    internal enum class BOOK {
        SARA, GUTHIX, ZAM
    }

    internal class HolyDialogue(val book: BOOK) : DialogueFile() {

        private val weddings = arrayOf(
            "In the name of Saradomin, protector of us all, I now join you in the eyes of Saradomin.",
            "Light and dark, day and night, balance arises from contrast. I unify thee in the name of Guthix.",
            "Two great warriors, joined by hand, to spread destruction across the land. In Zamorak's name, now two are one."
        )

        private val lastRites = arrayOf(
            "Thy cause was false, thy skills did lack; See you in Lumbridge when you get back.",
            "Thy death was not in vain, for it brought some balance to the world. May Guthix bring you rest.",
            "The weak deserve to die, so the strong may flourish. This is the creed of Zamorak."
        )

        private val blessing = arrayOf(
            "Go in peace in the name of Saradomin; may his glory shine upon you like the sun.",
            "May you walk the path, and never fall, for Guthix walks beside thee on thy journey. May Guthix bring you peace.",
            "May your bloodthirst never be sated, and may all your battles be glorious. Zamorak bring you strength."
        )

        private val preachings = arrayOf(
            arrayOf(
                "Protect your self, protect your friends. Mine is the glory that never ends.",
                "The darkness in life may be avoided, by the light of wisdom shining.",
                "Show love to your friends, and mercy to your enemies, and know that the wisdom of Saradomin will follow. ",
                "A fight begun, when the cause is just, will prevail over all others.",
                "The currency of goodness is honour; It retains its value through scarcity."
            ), arrayOf(
                "All things must end, as all begin; Only Guthix knows the role thou must play.",
                "In life, in death, in joy, in sorrow: May thine experience show thee balance.",
                "Thou must do as thou must, no matter what. Thine actions bring balance to this world.",
                "he river flows, the sun ignites, May you stand with Guthix in thy fights.",
                "A journey of a single step, May take thee over a thousand miles."
            ), arrayOf(
                "There is no opinion that cannot be proven true...by crushing those who choose to disagree with it.",
                "Battles are not lost and won; They simply remove the weak from the equation.",
                "Those who fight, then run away, shame Zamorak with their cowardice.",
                "Battle is by those who choose to disagree with it.",
                "Strike fast, strike hard, strike true: The strength of Zamorak will be with you."
            )
        )

        private val preachSaradomin = "This is Saradomin's Wisdom."
        private val preachZamorak = "Zamorak give me strength!"
        private val preachGuthix = "May Guthix bring you balance."

        override fun handle(componentID: Int, buttonID: Int) {
            when (stage) {
                0 -> options("Weddings", "Last Rites", "Blessings", "Preaching").also { stage++ }
                1 -> when (buttonID) {
                    1 -> say(player!!, weddings[book.ordinal], book, false)
                    2 -> say(player!!, lastRites[book.ordinal], book, false)
                    3 -> say(player!!, blessing[book.ordinal], book, false)
                    4 -> say(player!!, preachings[book.ordinal].random(), book, true)
                    else -> {}
                }.also { end() }
            }
        }

        private fun say(player: Player, message: String, book: BOOK, preach: Boolean) {
            val animation = when (book) {
                BOOK.SARA -> Animation(Animations.PREACH_WHITE_BOOK_1335)
                BOOK.ZAM -> Animation(Animations.PREACH_RED_BOOK_1336)
                BOOK.GUTHIX -> Animation(Animations.PREACH_GREEN_BOOK_1337)
            }

            val preachText = when (book) {
                BOOK.SARA -> preachSaradomin
                BOOK.ZAM -> preachZamorak
                BOOK.GUTHIX -> preachGuthix
            }

            val lastTick = animationDuration(animation)

            lock(player, 100)
            animate(player, animation)

            submitIndividualPulse(player, object : Pulse() {
                var counter = 0
                override fun pulse(): Boolean {
                    when (counter++) {
                        0 -> sendChat(player, message)
                        lastTick - 1 -> if (preach) sendChat(player, preachText)
                        lastTick -> unlock(player).also { return true }
                    }
                    return false
                }
            })
        }

    }

}
