package content.region.karamja.quest.totem.dialogue

import org.rs.consts.Items
import org.rs.consts.NPCs
import core.api.isQuestComplete
import core.api.removeItem
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import org.rs.consts.QuestName

/**
 * Represents the Kangai Mau dialogue.
 */
@Initializable
class KangaiMauDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        if (!player.questRepository.hasStarted(QuestName.TRIBAL_TOTEM)) {
            npcl(FacialExpression.HAPPY, "Hello. I'm Kangai Mau of the Rantuki Tribe.")
            stage = 0
        } else if (isQuestComplete(player, QuestName.TRIBAL_TOTEM)) {
            npcl(FacialExpression.HAPPY, "Many greetings esteemed thief.")
            stage = 40
        } else if (player.inventory.containsAtLeastOneItem(Items.TOTEM_1857)) {
            npcl(FacialExpression.ASKING, "Have you got our totem back?")
            stage = 35
        } else if (player.questRepository.hasStarted(QuestName.TRIBAL_TOTEM)) {
            npcl(FacialExpression.ASKING, "Have you got our totem back?")
            stage = 30
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("And what are you doing here in Brimhaven?", "I'm in search of adventure!", "Who are the Rantuki tribe?").also { stage++ }
            1 -> when (buttonId) {
                1 -> playerl(FacialExpression.ASKING, "And what are you doing here in Brimhaven?").also { stage = 5 }
                2 -> playerl(FacialExpression.HAPPY, "I'm in search of adventure!").also { stage = 15 }
                3 -> playerl(FacialExpression.ASKING, "Who are the Rantuki Tribe?").also { stage = 10 }
            }
            5 -> npcl(FacialExpression.HAPPY, "I'm looking for someone brave to go on important mission. Someone skilled in thievery and sneaking about.").also { stage++ }
            6 -> npcl(FacialExpression.HAPPY, "I am told I can find such people in Brimhaven.").also { stage++ }
            7 -> playerl(FacialExpression.HAPPY, "Yep. I have heard there are many of that type here.").also { stage++ }
            8 -> npcl(FacialExpression.THINKING, "Let's hope I find them.").also { stage = END_DIALOGUE }
            10 -> npcl(FacialExpression.HAPPY, "A proud and noble tribe of Karamja.").also { stage++ }
            11 -> npcl(FacialExpression.ANGRY, "But now we are few, as men come from across, steal our land, and settle on our hunting grounds").also { stage = END_DIALOGUE }
            15 -> npcl(FacialExpression.HAPPY, "Adventure is something I may be able to give. I need someone to go on a mission to the city of Ardougne.").also { stage++ }
            16 -> npcl(FacialExpression.HAPPY, "There you will find the house of Lord Handlemort. In his house he has our tribal totem. We need it back.").also { stage++ }
            17 -> playerl(FacialExpression.ASKING, "Why does he have it?").also { stage++ }
            18 -> npcl(FacialExpression.ANGRY, "Lord Handlemort is an Ardougnese explorer which means he think he have the right to come to my tribal home,").also { stage++ }
            19 -> npcl(FacialExpression.ANGRY, "steal our stuff and put in his private museum.").also { stage++ }
            20 -> playerl(FacialExpression.THINKING, "How can I find Handlemoret's house? Ardougne IS a big place...").also { stage++ }
            21 -> npcl(FacialExpression.ANNOYED, "I don't know Ardougne. You tell me.").also { stage++ }
            22 -> playerl(FacialExpression.HAPPY, "Ok, I will get it back.").also {
                player.questRepository.getQuest(QuestName.TRIBAL_TOTEM).start(player)
                player.questRepository.getQuest(QuestName.TRIBAL_TOTEM).setStage(player, 10)
                stage++
            }
            23 -> npcl(FacialExpression.HAPPY, "Best of luck with that adventurer").also { stage = END_DIALOGUE }
            30 -> playerl(FacialExpression.SAD, "Not yet, sorry.").also { stage = END_DIALOGUE }
            35 -> playerl(FacialExpression.HAPPY, "Yes I have.").also { stage++ }
            36 -> npcl(FacialExpression.HAPPY, "You have??? Many thanks brave adventurer! Here, have some freshly cooked Karamjan fish, caught specially by my tribe.").also { stage++ }
            37 -> sendDialogue("You hand over the totem").also {
                if (!isQuestComplete(player, QuestName.TRIBAL_TOTEM) && removeItem(player, Items.TOTEM_1857)) {
                    player.questRepository.getQuest(QuestName.TRIBAL_TOTEM).finish(player)
                    stage = END_DIALOGUE
                } else {
                    stage = END_DIALOGUE
                }
            }
            40 -> player(FacialExpression.NEUTRAL, "Hey.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.KANGAI_MAU_846)
    }
}
