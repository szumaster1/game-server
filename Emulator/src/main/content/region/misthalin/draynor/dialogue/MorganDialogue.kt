package content.region.misthalin.draynor.dialogue

import org.rs.consts.NPCs
import content.region.misthalin.draynor.quest.vampire.dialogue.MorganDialogue
import core.api.getQuest
import core.api.getQuestStage
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Represents the Morgan dialogue.
 */
@Initializable
class MorganDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (getQuestStage(player, "Vampire Slayer") > 1) {
            end()
            openDialogue(player, MorganDialogue())
        } else {
            npc(FacialExpression.HALF_GUILTY, "Please please help us, bold adventurer!")
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> player(FacialExpression.HALF_GUILTY, "What's the problem?").also { stage++ }
            1 -> npc(FacialExpression.HALF_GUILTY, "Our little village has been dreadfully ravaged by an evil", "vampire! He lives in the basement of the manor to the", "north, we need someone to get rid of him once and for", "all!").also { stage++ }
            2 -> options("No, vampires are scary!", "Ok, I'm up for an adventure.", "Have you got any tips on killing the vampire?").also { stage++ }
            3 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_GUILTY, "No, vampires are scary!").also { stage = END_DIALOGUE }
                2 -> player(FacialExpression.HALF_GUILTY, "Ok, I'm up for an adventure.").also { stage = 4 }
                3 -> player(FacialExpression.HALF_GUILTY, "Have you got any tips on killing the vampire?").also { stage = 4 }
            }
            4 -> npc(FacialExpression.HALF_GUILTY, "I think first you should seek help. I have a friend who", "is a retired vampire hunter, his name is Dr. Harlow. He", "may be able to give you some tips. He can normally be", "found in the Blue Moon Inn in Varrock, he's a bit of").also { stage++ }
            5 -> npc(FacialExpression.HALF_GUILTY, "an old soak these days. Mention his old friend Morgan,", "I'm sure he wouldn't want me killed by a vampire.").also { stage++ }
            6 -> player(FacialExpression.HALF_GUILTY, "I'll look him up then.").also { stage++ }
            7 -> {
                getQuest(player, "Vampire Slayer").start(player)
                player.getQuestRepository().syncronizeTab(player)
                end()
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MORGAN_755)
    }

}
