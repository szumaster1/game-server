package content.region.asgarnia.quest.bkfortress

import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable

@Initializable
class BKListenDialogue(player: Player? = null) : Dialogue(player) {

    private val captain = 610
    private val witch = 611
    private val greldo = 612
    private val cat = 4607

    override fun open(vararg args: Any): Boolean {
        if (args != null && args.size == 2) {
            interpreter.sendDialogues(witch, FacialExpression.NEUTRAL, "Where has Greldo got to with that magic cabbage!")
            stage = 10
            player.animate(ANIMATION)
            return true
        }
        interpreter.sendDialogues(captain, FacialExpression.ASKING, "So... how's the secret weapon coming along?")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                interpreter.sendDialogues(witch, FacialExpression.HAPPY, "The invincibility potion is almost ready...")
                stage = 1
            }

            1 -> {
                interpreter.sendDialogues(
                    witch,
                    FacialExpression.HAPPY,
                    "It's taken me FIVE YEARS, but it's almost ready."
                )
                stage = 2
            }

            2 -> {
                interpreter.sendDialogues(
                    witch,
                    FacialExpression.NEUTRAL,
                    "Greldo the Goblin here is just going to fetch the last",
                    "ingredient for me."
                )
                stage = 3
            }

            3 -> {
                interpreter.sendDialogues(
                    witch,
                    FacialExpression.NEUTRAL,
                    "It's a special grown cabbage grown by my cousin",
                    "Helda who lives in Draynor Manor."
                )
                stage = 4
            }

            4 -> {
                interpreter.sendDialogues(
                    witch,
                    FacialExpression.NEUTRAL,
                    "The soil there is slightly magical and it gives the",
                    "cabbages slight magical properties...."
                )
                stage = 5
            }

            5 -> {
                interpreter.sendDialogues(witch, FacialExpression.AMAZED, "...not to mention the trees!")
                stage = 6
            }

            6 -> {
                interpreter.sendDialogues(
                    witch,
                    FacialExpression.NEUTRAL,
                    "Now remember Greldo, only a Draynor Manor",
                    "cabbage will do! Don't get lazy and bring any old",
                    "cabbage, THAT would ENTIRELY wreck the potion!"
                )
                stage = 7
            }

            7 -> {
                interpreter.sendDialogues(greldo, FacialExpression.OLD_NORMAL, "Yeth, Mithreth.")
                stage = 8
            }

            8 -> {
                player.getQuestRepository().getQuest("Black Knights' Fortress").setStage(player, 20)
                end()
            }

            10 -> {
                interpreter.sendDialogues(captain, FacialExpression.ASKING, "What's that noise?")
                stage = 11
            }

            11 -> {
                interpreter.sendDialogues(
                    witch,
                    FacialExpression.NEUTRAL,
                    "Hopefully Greldo with the cabbage... yes, look her it",
                    "co....NOOOOOoooo!"
                )
                stage = 12
            }

            12 -> {
                interpreter.sendDialogues(witch, FacialExpression.EXTREMELY_SHOCKED, "My potion!")
                stage = 13
            }

            13 -> {
                interpreter.sendDialogues(captain, FacialExpression.WORRIED, "Oh boy, this doesn't look good!")
                stage = 14
            }

            14 -> {
                interpreter.sendDialogues(cat, FacialExpression.CHILD_FRIENDLY, "Meow!")
                stage = 15
            }

            15 -> if (player.inventory.remove(CABBAGE)) {
                player.getQuestRepository().getQuest("Black Knights' Fortress").setStage(player, 30)
                player(FacialExpression.HAPPY,
                    "Looks like my work here is done. Seems like that's",
                    "successfully sabotaged their little secret weapon plan."
                )
                stage = 16
            }

            16 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(992752973)
    }

    companion object {
        private val CABBAGE = Item(1965)
        private val ANIMATION = Animation(832)
    }
}
