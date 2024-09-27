package content.region.asgarnia.falador.quest.rd.dialogue

import core.api.getQuestStage
import core.api.isQuestComplete
import core.api.setQuestStage
import core.game.dialogue.DialogueBuilder
import core.game.dialogue.DialogueBuilderFile
import core.game.dialogue.FacialExpression
import org.rs.consts.QuestName

/**
 * Represents the Sir Amik Varze dialogue file.
 * @author Ovenbread
 */
class SirAmikVarzeDialogueFile : DialogueBuilderFile() {

    override fun create(builder: DialogueBuilder) {
        builder.onQuestStages(QuestName.RECRUITMENT_DRIVE, 0)
            .npcl("Hello, friend!")
            .playerl(FacialExpression.THINKING, "Do you have any other quests for me to do?")
            .branch { player ->
                if (isQuestComplete(player, QuestName.BLACK_KNIGHTS_FORTRESS) && isQuestComplete(player, QuestName.DRUIDIC_RITUAL)) 1 else 0
            }.let { branch ->
                branch.onValue(0)
                    .npcl(FacialExpression.THINKING, "A quest? Alas I do not have any quests I can offer you at this time.")
                    .end()
                branch.onValue(1)
                    .npc("Quests, eh?", "Well, I don't have anything on the go at the moment,", "but there is an organisation that is always looking for", "capable adventurers to assist them.")
                    .npc(FacialExpression.FRIENDLY, "Your excellent work sorting out those Black Knights", "means I will happily write you a letter of", "recommendation.")
                    .npc("Would you like me to put your name forwards to", "them?")
                    .options()
                    .let { optionBuilder ->
                        optionBuilder
                            .option("Yes please")
                            .playerl("Sure thing Sir Amik, sign me up!")
                            .npc(FacialExpression.SUSPICIOUS, "Erm, well, this is a little embarrassing, I already HAVE", "put you forward as a potential member.")
                            .npc("They are the Temple Knights, and you are to", "meet Sir Tiffy Cashien in Falador park for testing", "immediately.")
                            .playerl("Okey dokey, I'll go do that then.")
                            .endWith { _, player ->
                                if (getQuestStage(player, "Recruitment Drive") == 0) {
                                    setQuestStage(player, "Recruitment Drive", 1)
                                }
                            }

                        optionBuilder
                            .option_playerl("No thanks")
                            .end()

                        optionBuilder
                            .option("Tell me about this organization...")
                            .npc(FacialExpression.SUSPICIOUS, "I cannot tell you much...", "They are called the Temple Knights, and are an", "organisation that was founded by Saradomin personally", "many centuries ago.")
                            .npc("There are many rumours and fables about their works and", "actions, but official records of their presence are non-", "existent.")
                            .npc("It is a secret organisation of extraordinary power and", "resourcefulness...")
                            .npc("Let me put it this way:", "Should you decide to take them up on their generous", "offer to join, you will find yourself in an advantageous", "position that many in this world would envy, and that few")
                            .npc("are called to occupy.")
                            .playerl("Well, that wasn't quite as helpful as I thought it would be...but thanks anyway, I guess.")
                            .end()
                    }
            }
    }
}
