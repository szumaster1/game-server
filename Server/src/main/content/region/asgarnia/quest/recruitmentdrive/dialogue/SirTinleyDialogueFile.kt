package content.region.asgarnia.quest.recruitmentdrive.dialogue

import content.region.asgarnia.quest.recruitmentdrive.RecruitmentDrive
import content.region.asgarnia.quest.recruitmentdrive.RecruitmentDriveListeners
import core.api.*
import core.api.consts.NPCs
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
 * Sir tinley dialogue file.
 */
class SirTinleyDialogueFile(private val dialogueNum: Int = 0) : DialogueBuilderFile(), MapArea {
    companion object {
        const val RD_DONT_MOVE_ATTR = "rd:donotmove"
    }

    override fun create(b: DialogueBuilder) {
        b.onPredicate { player ->
            dialogueNum == 0 && !getAttribute(player, RD_DONT_MOVE_ATTR, false) && !getAttribute(
                player, RecruitmentDrive.ATTRIBUTE_RD_STAGE_FAILED, false
            )
        }.npc("Ah, welcome @name.", "I have but one clue for you to pass this room's puzzle:", "'Patience'.")
            .endWith { _, player ->
                setAttribute(player, RD_DONT_MOVE_ATTR, true)
                submitWorldPulse(object : Pulse() {
                    var counter = 0
                    override fun pulse(): Boolean {
                        when (counter++) {
                            15 -> {
                                if (!getAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_STAGE_FAILED, false)) {
                                    setAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_STAGE_PASSED, true)
                                    setAttribute(player, RD_DONT_MOVE_ATTR, false)
                                    npc(
                                        FacialExpression.FRIENDLY,
                                        "Excellent work, @name.",
                                        "Please step through the portal to meet your next",
                                        "challenge."
                                    )
                                }
                            }
                        }
                        return false
                    }
                })
            }
        // If you talk to him before time is up, you fail.
        b.onPredicate { player -> dialogueNum == 0 && getAttribute(player, RD_DONT_MOVE_ATTR, false) || getAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_STAGE_FAILED, false) || dialogueNum == 2
        }
            .betweenStage { _, player, _, _ ->
            setAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_STAGE_FAILED, true)
        }
            .npc(FacialExpression.SAD, "No... I am very sorry.", "Apparently you are not up to the challenge.", "I will return you where you came from, better luck in the", "future.")
            .endWith { _, player ->
                removeAttribute(player, RD_DONT_MOVE_ATTR)
                setAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_STAGE_PASSED, false)
                setAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_STAGE_FAILED, false)
                RecruitmentDriveListeners.FailTestCutscene(player).start()
            }

        b.onPredicate { _ -> dialogueNum == 1 }
            .npc("Ah, @name, you have arrived.", "Speak to me to begin your task.")
            .endWith { _, player ->
                setAttribute(player, RD_DONT_MOVE_ATTR, false)
            }
    }


    override fun defineAreaBorders(): Array<ZoneBorders> {
        return arrayOf(ZoneBorders(2474, 4959, 2478, 4957))
    }

    override fun entityStep(entity: Entity, location: Location, lastLocation: Location) {
        if (entity is Player) {
            if (getAttribute(entity, RD_DONT_MOVE_ATTR, false)) {
                setAttribute(entity, RD_DONT_MOVE_ATTR, false)
                setAttribute(entity, RecruitmentDrive.ATTRIBUTE_RD_STAGE_FAILED, true)
                openDialogue(entity, SirTinleyDialogueFile(2), NPC(NPCs.SIR_TINLEY_2286))
            }
        }
    }
}
