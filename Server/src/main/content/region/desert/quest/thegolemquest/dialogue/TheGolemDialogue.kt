package content.region.desert.quest.thegolemquest.dialogue

import core.api.consts.NPCs
import core.api.finishQuest
import core.api.setQuestStage
import core.game.dialogue.Dialogue
import core.game.dialogue.DialogueBuilder
import core.game.dialogue.DialogueBuilderFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Clay golem dialogue.
 */
@Initializable
class ClayGolemDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg objects: Any?): Boolean {
        npc = objects[0] as NPC
        player.dialogueInterpreter.open(ClayGolemDialogueFile(), npc)
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        return false
    }

    override fun getIds(): IntArray {
        return intArrayOf(1907, NPCs.BROKEN_CLAY_GOLEM_1908, NPCs.DAMAGED_CLAY_GOLEM_1909, NPCs.CLAY_GOLEM_1910)
    }
}

/**
 * Clay golem dialogue file
 *
 * @constructor Clay golem dialogue file
 */
class ClayGolemDialogueFile : DialogueBuilderFile() {
    override fun create(b: DialogueBuilder) {
        val opt1 = b.onQuestStages("The Golem", 0)
            .npc(FacialExpression.OLD_BOWS_HEAD_SAD, "Damage... severe...", "task... incomplete...")
            .options()
        opt1
            .optionIf("Shall I try to repair you?") { player ->
                return@optionIf player.questRepository.getQuest("The Golem").hasRequirements(player)
            }
            .playerl("Shall I try to repair you?")
            .npcl(FacialExpression.OLD_BOWS_HEAD_SAD, "Repairs... needed...")
            .endWith { _, player ->
                if (player.questRepository.getStage("The Golem") < 1) {
                    setQuestStage(player, "The Golem", 1)
                }
            }
        opt1
            .option("I'm not going to find a conversation here!")
            .playerl("I'm not going to find a conversation here!")
            .end()
        b.onQuestStages("The Golem", 1)
            .npcl(FacialExpression.OLD_BOWS_HEAD_SAD, "Repairs... needed...")
            .end()
        b.onQuestStages("The Golem", 2)
            .npcl(FacialExpression.OLD_BOWS_HEAD_SAD, "Damage repaired...")
            .npcl(FacialExpression.OLD_BOWS_HEAD_SAD, "Thank you. My body and mind are fully healed.")
            .npcl(FacialExpression.OLD_BOWS_HEAD_SAD, "Now I must complete my task by defeating the great enemy.")
            .playerl("What enemy?")
            .npcl(
                FacialExpression.OLD_BOWS_HEAD_SAD,
                "A great demon. It broke through from its dimension to attack the city."
            )
            .npcl(
                FacialExpression.OLD_BOWS_HEAD_SAD,
                "The golem army was created to fight it. Many were destroyed, but we drove the demon back!"
            )
            .npcl(
                FacialExpression.OLD_BOWS_HEAD_SAD,
                "The demon is still wounded. You must open the portal so that I can strike the final blow and complete my task."
            )
            .endWith { _, player -> setQuestStage(player, "The Golem", 3) }
        b.onQuestStages("The Golem", 3)
            .npcl(
                FacialExpression.OLD_BOWS_HEAD_SAD,
                "The demon is still wounded. You must open the portal so that I can strike the final blow and complete my task."
            )
            .end()
        b.onQuestStages("The Golem", 4)
            .npcl(
                FacialExpression.OLD_BOWS_HEAD_SAD,
                "My task is incomplete. You must open the portal so I can defeat the great demon."
            )
            .playerl("It's ok, the demon is dead!")
            .npcl(FacialExpression.OLD_BOWS_HEAD_SAD, "The demon must be defeated...")
            .playerl("No, you don't understand. I saw the demon's skeleton. It must have died of its wounds.")
            .npcl(FacialExpression.OLD_BOWS_HEAD_SAD, "Demon must be defeated! Task incomplete.")
            .endWith { _, player -> setQuestStage(player, "The Golem", 5) }
        b.onQuestStages("The Golem", 5)
            .npcl(FacialExpression.OLD_BOWS_HEAD_SAD, "Task incomplete.")
            .playerl("Oh, how am I going to convince you?")
            .endWith { _, player -> setQuestStage(player, "The Golem", 6) }
        b.onQuestStages("The Golem", 6, 7)
            .npcl(
                FacialExpression.OLD_BOWS_HEAD_SAD,
                "My task is incomplete. You must open the portal so I can defeat the great demon."
            )
            .playerl("I already told you, he's dead!")
            .npcl(FacialExpression.OLD_BOWS_HEAD_SAD, "Task incomplete.")
            .playerl("Oh, how am I going to convince you?")
            .endWith { _, player ->
                if (player.questRepository.getStage("The Golem") < 7) {
                    setQuestStage(player, "The Golem", 7)
                }
            }
    }
}

/**
 * Clay golem program dialogue file
 *
 * @constructor Clay golem program dialogue file
 */
class ClayGolemProgramDialogueFile : DialogueBuilderFile() {
    override fun create(b: DialogueBuilder) {
        b.onQuestStages("The Golem", 8)
            .npc(FacialExpression.OLD_BOWS_HEAD_SAD, "New instructions...", "Updating program...")
            .npcl(FacialExpression.OLD_BOWS_HEAD_SAD, "Task complete!")
            .npcl(FacialExpression.OLD_BOWS_HEAD_SAD, "Thank you. Now my mind is at rest.")
            .endWith { _, player -> finishQuest(player, "The Golem") }
    }
}

/**
 * Curator haig halen golem dialogue
 *
 * @constructor Curator haig halen golem dialogue
 */
class CuratorHaigHalenGolemDialogue : DialogueBuilderFile() {
    override fun create(b: DialogueBuilder) {
        val opt1 = b.onQuestStages("The Golem", 3)
            .npcl(
                FacialExpression.OLD_BOWS_HEAD_SAD,
                "Ah yes, a very impressive artefact. The people of that city were excellent sculptors."
            )
            .npcl(FacialExpression.OLD_BOWS_HEAD_SAD, "It's in the display case upstairs.")
            .playerl("No, I need to take it away with me.")
            .npcl(FacialExpression.OLD_BOWS_HEAD_SAD, "What do you want it for?")
            .options()
        opt1
            .option("I want to open a portal to the lair of an elder-demon.")
            .playerl("I want to open a portal to the lair of an elder-demon.")
            .npcl(FacialExpression.OLD_BOWS_HEAD_SAD, "Good heavens! I'd never let you do such a dangerous thing.")
            .end()
        opt1
            .option("Well, I, er, just want it.")
            .playerl("Well, I, er, just want it.")
            .end()
    }
}

val LETTER_LINES = arrayOf(
    "",
    "",
    "Dearest Varmen,",
    "I hope this finds you well. Here are the books you asked for",
    "There has been an exciting development closer to home --",
    "another city from the same period has been discovered east",
    "of Varrock, and we are starting a huge excavation project",
    "here. I don't know if the museum will be able to finance your",
    "expedition as well as this one, so I fear your current trip will be",
    "the last.",
    "May Saradomin grant you a safe journey home",
    "Your loving Elissa.",
)

val DISPLAY_CASE_TEXT = arrayOf(
    "3rd age - yr 3000-4000",
    "",
    "This statuette was found in an underground",
    "temple in the ruined city of Uzer, which was",
    "destroyed late in the 3rd Age, suddenly, due to",
    "causes unknown. It probably represents one of",
    "the clay golems that the craftsmen of the city",
    "built as warriors and servants. The statuette",
    "was originally part of a mechanism whose",
    "purpose is unknown."
)
