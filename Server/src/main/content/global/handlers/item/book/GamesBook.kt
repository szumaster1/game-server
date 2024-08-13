package content.global.handlers.item.book

import content.global.handlers.iface.BookInterfaceListener
import content.global.handlers.iface.BookLine
import content.global.handlers.iface.Page
import content.global.handlers.iface.PageSet
import core.api.consts.Items
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player

/**
 * Games book.
 */
class GamesBook : InteractionListener {

    /*
     * It features guides to all the games the player, can
     * make and play at poh, for Games or Combat Room.
     */

    companion object {
        private val TITLE = "Party Pete's Bumper Book of Games"
        private val CONTENTS = arrayOf(
            PageSet(
                Page(
                    BookLine("<col=08088A>Hangman", 55),
                    BookLine("This word-guessing game", 57),
                    BookLine("was invented by Mad King", 58),
                    BookLine("Narras of Ardougne in", 59),
                    BookLine("order to encourage literacy", 60),
                    BookLine("among his subjects. Today", 61),
                    BookLine("it is played with an effigy", 62),
                    BookLine("rather than a real person,", 63),
                    BookLine("and it makes an amusing", 64),
                    BookLine("distraction for one or", 65),
                ),
                Page(
                    BookLine("more players.", 66),
                    BookLine("When the game is started,", 68),
                    BookLine("the hangman game chooses", 69),
                    BookLine("a word at random. Players", 70),
                    BookLine("take turns to guess one", 71),
                    BookLine("letter at a time. If they", 72),
                    BookLine("guess correctly, that", 73),
                    BookLine("letter is revealed, but", 74),
                    BookLine("if they are incorrect", 75),
                    BookLine("then the scaffold and", 76),
                )
            ),
            PageSet(
                Page(
                    BookLine("effigy are built one step", 55),
                    BookLine("at a time until the effigy", 56),
                    BookLine("is hanged. Players can", 57),
                    BookLine("also guess multiple letters", 58),
                    BookLine("if they know the word,", 59),
                    BookLine("but if they guess", 60),
                    BookLine("incorrectly no", 61),
                    BookLine("letters will be revealed.", 62),
                    BookLine("The player to complete", 63),
                    BookLine("the word wins.", 64),
                ),
                Page(
                    BookLine("<col=08088A>Fairy Treasure Hunt", 66),
                    BookLine("The Treasure Fairies", 68),
                    BookLine("delight in hiding in", 69),
                    BookLine("human dwellings and watching", 70),
                    BookLine("in glee as people try", 71),
                    BookLine("to find them. While this", 72),
                    BookLine("once caused havoc", 74),
                    BookLine("throughout Misthalin,", 75),
                    BookLine("since then the", 76),
                )
            ),
            PageSet(
                Page(
                    BookLine("fairies have turned", 55),
                    BookLine("this activity into a", 56),
                    BookLine("fun party game. When", 57),
                    BookLine("summoned to her mushroom", 58),
                    BookLine("house, the fairy will", 59),
                    BookLine("hide somewhere in the", 60),
                    BookLine("house or its grounds.", 61),
                    BookLine("Each player is given", 62),
                    BookLine("a magic stone that will", 63),
                    BookLine("tell them how close they", 64),
                    BookLine("are to the fairy. The", 65),
                ),
                Page(
                    BookLine("first player to use the", 66),
                    BookLine("stone while standing", 67),
                    BookLine("at the right place wins.", 68),
                    BookLine("<col=08088A>Mimic-The-Jester", 70),
                    BookLine("The jester is a cousin", 72),
                    BookLine("of the mime, but rather", 73),
                    BookLine("than summoning people", 74),
                    BookLine("from their activities", 75),
                    BookLine("to pass its test, the", 76),
                )
            ),
            PageSet(
                Page(
                    BookLine("jester is happy to be", 55),
                    BookLine("summoned whenever a game", 56),
                    BookLine("is desired. It will perform", 57),
                    BookLine("gestures which the players", 58),
                    BookLine("must try to copy. The", 59),
                    BookLine("first player to get ten", 60),
                    BookLine("correct wins!", 61),
                    BookLine("<col=08088A>Elemental Balance", 63),
                    BookLine("The Elemental Balance", 65),
                ),
                Page(
                    BookLine("is a battle of wits and", 66),
                    BookLine("wizardry. When the sphere", 67),
                    BookLine("is summoned it is unbalanced", 68),
                    BookLine("on the two elemental axes,", 69),
                    BookLine("earth-air and fire-water.", 70),
                    BookLine("The direction in which", 71),
                    BookLine("it is most unbalanced", 72),
                    BookLine("can be seen by its colour.", 73),
                    BookLine("To balance the sphere,", 74),
                    BookLine("players must attack it", 75),
                    BookLine("with the spells of the", 76),
                )
            ),
            PageSet(
                Page(
                    BookLine("opposite element. (So", 55),
                    BookLine("if it is red, for example,", 56),
                    BookLine("players must use water", 57),
                    BookLine("spells.) The winner is", 58),
                    BookLine("the player who casts the", 59),
                    BookLine("spell that puts it in", 60),
                    BookLine("perfect balance.", 61),
                    BookLine("<col=08088A>Attack Stone", 63),
                    BookLine("This ancient game was", 65),
                ),
                Page(
                    BookLine("once used as a means", 66),
                    BookLine("of resolving leadership", 67),
                    BookLine("disputes", 68),
                    BookLine("The Attack stone is simply", 70),
                    BookLine("a pillar of clay, limestone", 71),
                    BookLine("or marble, carefully constructed", 72),
                    BookLine("to respond differently", 73),
                    BookLine("to stabbing, slashing", 74),
                    BookLine("and crushing. If it takes", 75),
                    BookLine("enough damage in any of", 76),
                )
            ),
            PageSet(
                Page(
                    BookLine("these three forms it shatters,", 55),
                    BookLine("and the player who struck", 56),
                    BookLine("that blow wins.", 57),
                    BookLine("<col=08088A>Ranging Games", 59),
                    BookLine("The stick-and-hoop, dartboard", 61),
                    BookLine("and archery game are all", 62),
                    BookLine("played using the same", 63),
                    BookLine("rules. Players take turns", 64),
                    BookLine("to throw or shoot at the", 65),
                ),
                Page(
                    BookLine("target in order to score", 66),
                    BookLine("points. The range of points", 67),
                    BookLine("that can be awarded depends", 68),
                    BookLine("on the target: with the", 69),
                    BookLine("hoop there is only hitting", 70),
                    BookLine("and missing, with the", 71),
                    BookLine("dartboard the maximum", 72),
                    BookLine("is 3 and with the archery", 73),
                    BookLine("the target is 10. After", 74),
                    BookLine("all the players have had", 75),
                    BookLine("ten shots, the highest", 76),
                )
            ),
            PageSet(
                Page(
                    BookLine("scoring player wins.", 55),
                    BookLine("<col=08088A>Combat", 57),
                    BookLine("The various combat rings", 59),
                    BookLine("have different restrictions", 60),
                    BookLine("on the weapons that can", 61),
                    BookLine("be used in them. No equipment", 62),
                    BookLine("can be worn in the boxing", 63),
                    BookLine("ring except for boxing", 64),
                    BookLine("gloves (although boxers", 65),
                ),
                Page(
                    BookLine("may wish to fight bare-fisted", 66),
                    BookLine("if they wish). Any weapons", 67),
                    BookLine("can be used in the fencing", 68),
                    BookLine("ring, but no armour of", 69),
                    BookLine("any kind can be worn.", 70),
                    BookLine("There are no restrictions", 71),
                    BookLine("in the combat ring. There", 72),
                    BookLine("are similarly no restrictions", 73),
                    BookLine("for the ranging pedestals,", 74),
                    BookLine("but since the combatants", 75),
                    BookLine("are separated only ranged", 76),
                )
            ),
            PageSet(
                Page(
                    BookLine("weapons will be effective!", 55),
                    BookLine("The balance beam only", 56),
                    BookLine("allows one weapon, the", 57),
                    BookLine("pugel stick, and fighting", 58),
                    BookLine("on the beam is a test", 59),
                    BookLine("of agility as well as", 60),
                    BookLine("combat!", 61),
                    BookLine("The combat rings", 63),
                    BookLine("are enchanted so that", 64),
                    BookLine("players cannot kill", 65),
                ),
                Page(
                    BookLine("one another; the lower", 66),
                    BookLine("is simply ejected", 67),
                    BookLine("from the ring and", 68),
                    BookLine("healed.", 69),
                    BookLine("<col=08088A>Prizes", 71),
                    BookLine("If the games room has", 73),
                    BookLine("a price chest, and the", 74),
                    BookLine("house owner has put money", 75),
                    BookLine("into it, then the winner", 76),
                )
            ),
            PageSet(
                Page(
                    BookLine("of any of these games", 55),
                    BookLine("will be given a key to", 56),
                    BookLine("the chest so that they", 57),
                    BookLine("can claim their prize!", 58),
                ),
            )
        )
    }

    private fun display(player: Player, pageNum: Int, buttonID: Int): Boolean {
        BookInterfaceListener.pageSetup(player, BookInterfaceListener.FANCY_BOOK_3_49, TITLE, CONTENTS)
        return true
    }

    override fun defineListeners() {
        on(Items.GAME_BOOK_7681, IntType.ITEM, "read") { player, _ ->
            BookInterfaceListener.openBook(player, BookInterfaceListener.FANCY_BOOK_3_49, ::display)
            return@on true
        }
    }
}
