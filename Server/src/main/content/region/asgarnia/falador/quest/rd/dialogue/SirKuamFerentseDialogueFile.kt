package content.region.asgarnia.falador.quest.rd.dialogue

import content.region.asgarnia.falador.quest.rd.RDUtils
import content.region.asgarnia.falador.quest.rd.RecruitmentDrive
import core.api.getAttribute
import core.game.dialogue.DialogueBuilder
import core.game.dialogue.DialogueBuilderFile
import core.game.dialogue.FacialExpression

/**
 * Represents the Sir Kuam Ferentse dialogue file.
 * @author Ovenbread
 */
class SirKuamFerentseDialogueFile(private val dialogueNum: Int = 0) : DialogueBuilderFile() {

    companion object {
        const val ATTRIBUTE_SPAWN_NPC = "rd:generatedsirleye"
    }

    override fun create(builder: DialogueBuilder) {
        builder.apply {
            onPredicate { player -> getAttribute(player, RecruitmentDrive.stagePass, false) }
                .npc(FacialExpression.HAPPY, "Excellent work, @name!", "Please step through the portal to meet your next", "challenge.")
                .end()

            onPredicate { true }
                .npc("Ah, @name, you're finally here.",
                    "Your task for this room is to defeat Sir Leye.",
                    "He has been blessed by Saradomin to be undefeatable",
                    "by any man, so it should be quite the challenge for you.")
                .npc("If you are having problems, remember",
                    "A true warrior uses his wits as much as his brawn.",
                    "Fight smarter, not harder.")
                .endWith { _, player ->
                    RDUtils.spawnBoss(player)
                }
        }
    }

}
