package content.region.asgarnia.falador.quest.rd.dialogue

import org.rs.consts.NPCs
import content.region.asgarnia.falador.quest.rd.RecruitmentDrive
import content.region.asgarnia.falador.quest.rd.handlers.RecruitmentDriveListener
import core.api.*
import core.game.dialogue.DialogueBuilder
import core.game.dialogue.DialogueBuilderFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.Entity
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.map.Location
import core.game.world.map.zone.ZoneBorders

/**
 * Represents the Sir Tinley dialogue file.
 * @author Ovenbread
 */
class SirTinleyDialogueFile(private val dialogueNum: Int = 0) : DialogueBuilderFile(), MapArea {

    companion object {
        const val patience = "rd:donotmove"
    }

    override fun create(builder: DialogueBuilder) {
        builder.onPredicate { player ->
            isInitialDialogue(player)
        }.npc("Ah, welcome @name.", "I have but one clue for you to pass this room's puzzle:", "'Patience'.")
            .endWith { _, player ->
                handlePatienceDialogue(player)
            }

        builder.onPredicate { player -> isFailDialogue(player) }
            .betweenStage { _, player, _, _ ->
                setAttribute(player, RecruitmentDrive.stageFail, true)
            }
            .npc(FacialExpression.SAD, "No... I am very sorry.", "Apparently you are not up to the challenge.", "I will return you where you came from, better luck in the", "future.")
            .endWith { _, player ->
                handleFailure(player)
            }

        builder.onPredicate { dialogueNum == 1 }
            .npc("Ah, @name, you have arrived.", "Speak to me to begin your task.")
            .endWith { _, player ->
                setAttribute(player, patience, false)
            }
    }

    private fun isInitialDialogue(player: Player): Boolean {
        return dialogueNum == 0 && !getAttribute(player, patience, false) && !getAttribute(player, RecruitmentDrive.stageFail, false)
    }

    private fun handlePatienceDialogue(player: Player) {
        setAttribute(player, patience, true)
        submitWorldPulse(object : Pulse() {
            private var counter = 0

            override fun pulse(): Boolean {
                if (counter++ == 15) {
                    if (!getAttribute(player, RecruitmentDrive.stageFail, false)) {
                        setAttribute(player, RecruitmentDrive.stagePass, true)
                        setAttribute(player, patience, false)
                        npc(FacialExpression.HAPPY, "Excellent work, @name.", "Please step through the portal to meet your next", "challenge.")
                    }
                }
                return false
            }
        })
    }

    private fun isFailDialogue(player: Player): Boolean {
        return dialogueNum == 0 && (getAttribute(player, patience, false) || getAttribute(player, RecruitmentDrive.stageFail, false) || dialogueNum == 2)
    }

    private fun handleFailure(player: Player) {
        removeAttribute(player, patience)
        setAttribute(player, RecruitmentDrive.stagePass, false)
        setAttribute(player, RecruitmentDrive.stageFail, false)
        RecruitmentDriveListener.FailTestCutscene(player).start()
    }

    override fun defineAreaBorders(): Array<ZoneBorders> {
        return arrayOf(ZoneBorders(2474, 4959, 2478, 4957))
    }

    override fun entityStep(entity: Entity, location: Location, lastLocation: Location) {
        if (entity is Player && getAttribute(entity, patience, false)) {
            setAttribute(entity, patience, false)
            setAttribute(entity, RecruitmentDrive.stageFail, true)
            openDialogue(entity, SirTinleyDialogueFile(2), NPC(NPCs.SIR_TINLEY_2286))
        }
    }
}
