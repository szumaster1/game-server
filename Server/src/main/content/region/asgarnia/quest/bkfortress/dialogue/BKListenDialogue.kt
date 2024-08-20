package content.region.asgarnia.quest.bkfortress.dialogue

import core.api.consts.Animations
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.removeItem
import core.api.sendNPCDialogue
import core.api.sendNPCDialogueLines
import core.api.setQuestStage
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable

/**
 * Represents the Black Knights listen dialogue.
 */
@Initializable
class BKListenDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        if (args.size == 2) {
            sendNPCDialogueLines(player, NPCs.WITCH_611, FacialExpression.NEUTRAL,false,  "Where has Greldo got to with that magic cabbage!")
            stage = 10
            player.animate(Animation(Animations.MULTI_USE_TAKE_832))
            return true
        }
        sendNPCDialogueLines(player, NPCs.BLACK_KNIGHT_CAPTAIN_610, FacialExpression.ASKING, false,"So... how's the secret weapon coming along?")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> sendNPCDialogue(player, NPCs.WITCH_611, "The invincibility potion is almost ready...", FacialExpression.HAPPY).also { stage++ }
            1 -> sendNPCDialogueLines(player, NPCs.WITCH_611, FacialExpression.HAPPY, false,"It's taken me FIVE YEARS, but it's almost ready.").also { stage++ }
            2 -> sendNPCDialogueLines(player, NPCs.WITCH_611, FacialExpression.NEUTRAL, false,"Greldo the Goblin here is just going to fetch the last", "ingredient for me.").also { stage++ }
            3 -> sendNPCDialogueLines(player, NPCs.WITCH_611, FacialExpression.NEUTRAL, false,"It's a special grown cabbage grown by my cousin", "Helda who lives in Draynor Manor.").also { stage++ }
            4 -> sendNPCDialogueLines(player, NPCs.WITCH_611, FacialExpression.NEUTRAL, false, "The soil there is slightly magical and it gives the", "cabbages slight magical properties....").also { stage++ }
            5 -> sendNPCDialogue(player, NPCs.WITCH_611, "...not to mention the trees!", FacialExpression.AMAZED).also { stage++ }
            6 -> sendNPCDialogueLines(player, NPCs.WITCH_611, FacialExpression.NEUTRAL, false ,"Now remember Greldo, only a Draynor Manor", "cabbage will do! Don't get lazy and bring any old", "cabbage, THAT would ENTIRELY wreck the potion!").also { stage++ }
            7 -> sendNPCDialogue(player, NPCs.GRELDO_612, "Yeth, Mithreth.", FacialExpression.OLD_NORMAL).also { stage++ }
            8 -> end().also { setQuestStage(player, "Black Knights' Fortress", 20) }
            10 -> sendNPCDialogue(player, NPCs.BLACK_KNIGHT_CAPTAIN_610, "What's that noise?", FacialExpression.ASKING).also { stage++ }
            11 -> sendNPCDialogueLines(player, NPCs.WITCH_611, FacialExpression.NEUTRAL, false, "Hopefully Greldo with the cabbage... yes, look her it","co....NOOOOOoooo!").also { stage++ }
            12 -> sendNPCDialogue(player, NPCs.WITCH_611, "My potion!", FacialExpression.EXTREMELY_SHOCKED).also { stage++ }
            13 -> sendNPCDialogue(player, NPCs.BLACK_KNIGHT_CAPTAIN_610, "Oh boy, this doesn't look good!", FacialExpression.WORRIED).also { stage++ }
            14 -> sendNPCDialogue(player, NPCs.BLACK_CAT_4607, "Meow!", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            15 -> if (removeItem(player, Items.CABBAGE_1965)) {
                setQuestStage(player, "Black Knights' Fortress", 30)
                player(FacialExpression.HAPPY, "Looks like my work here is done. Seems like that's", "successfully sabotaged their little secret weapon plan.")
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(992752973)
    }

}
