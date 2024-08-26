package content.region.asgarnia.quest.rd.dialogue

import content.region.asgarnia.quest.rd.RecruitmentDrive
import content.region.asgarnia.quest.rd.npc.SirLeyeNPC.Companion.spawnSirLeye
import core.api.getAttribute
import core.api.setAttribute
import core.game.dialogue.DialogueBuilder
import core.game.dialogue.DialogueBuilderFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC

/**
 * Represents the Sir Kuam Ferentse dialogue file.
 */
class SirKuamFerentseDialogueFile(private val dialogueNum: Int = 0) : DialogueBuilderFile() {

    companion object {
        const val ATTRIBUTE_SPAWN_NPC = "rd:generatedsirleye"
    }

    override fun create(b: DialogueBuilder) {

        b.onPredicate { player -> getAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_STAGE_PASSED, false) }
            .npc(FacialExpression.FRIENDLY, "Excellent work,  ${player!!.username}!", "Please step through the portal to meet your next", "challenge.")
            .end()

        b.onPredicate { _ -> true }
            .npc("Ah,  ${player!!.username}, you're finally here.", "Your task for this room is to defeat Sir Leye.", "He has been blessed by Saradomin to be undefeatable", "by any man, so it should be quite the challenge for you.")
            .npc("If you are having problems, remember", "A true warrior uses his wits as much as his brawn.", "Fight smarter, not harder.")
            .endWith { _, player ->

                var boss = getAttribute(player, ATTRIBUTE_SPAWN_NPC, NPC(0))
                if (boss.id != 0) {
                    boss.clear()
                }
                setAttribute(player, ATTRIBUTE_SPAWN_NPC, boss)
                spawnSirLeye(player)
            }
    }
}