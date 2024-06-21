package content.region.asgarnia.quest.recruitmentdrive

import content.region.asgarnia.quest.recruitmentdrive.dialogue.SirRenItchwoodDialogueFile
import core.api.*
import core.api.consts.Components
import core.api.consts.NPCs
import core.game.interaction.InterfaceListener
import core.game.node.entity.npc.NPC

class ComboLockInterfaceListener : InterfaceListener {

    companion object {
        const val LOCK_1 = "rd:lock1"
        const val LOCK_2 = "rd:lock2"
        const val LOCK_3 = "rd:lock3"
        const val LOCK_4 = "rd:lock4"
        val lockArray = arrayOf(LOCK_1, LOCK_2, LOCK_3, LOCK_4)
        val answers = arrayOf("BITE", "FISH", "LAST", "MEAT", "RAIN", "TIME")
    }

    override fun defineInterfaceListeners() {

        onOpen(Components.RD_COMBOLOCK_285) { player, _ ->
            setAttribute(player, LOCK_1, 65)
            setAttribute(player, LOCK_2, 65)
            setAttribute(player, LOCK_3, 65)
            setAttribute(player, LOCK_4, 65)
            return@onOpen true
        }

        onClose(Components.RD_COMBOLOCK_285) { player, _ ->
            removeAttribute(player, LOCK_1)
            removeAttribute(player, LOCK_2)
            removeAttribute(player, LOCK_3)
            removeAttribute(player, LOCK_4)
            return@onClose true
        }

        on(Components.RD_COMBOLOCK_285) { player, _, _, buttonID, _, _ ->
            // Child IDs for respective locks:
            //         6                 7                 8                 9
            //  10 < Lock1 > 11   12 < Lock2 > 13   14 < Lock3 > 15   16 < Lock4 > 17
            if (buttonID in 10..17) {
                val position = (buttonID - 10) / 2
                val backForth = (buttonID - 10) % 2
                var newValue = getAttribute(player, lockArray[position], 65) + (if (backForth == 0) {
                    -1
                } else {
                    1
                })
                if (newValue < 65) {
                    newValue = 90
                } // If char number is under A(65), loop back to Z(90)
                if (newValue > 90) {
                    newValue = 65
                } // If char number is over Z(90), loop back to A(65)
                setAttribute(player, lockArray[position], newValue)
                setInterfaceText(player, newValue.toChar().toString(), Components.RD_COMBOLOCK_285, position + 6)
            }
            // Enter Button
            if (buttonID == 18) {
                val lock1 = getAttribute(player, LOCK_1, 65).toChar()
                val lock2 = getAttribute(player, LOCK_2, 65).toChar()
                val lock3 = getAttribute(player, LOCK_3, 65).toChar()
                val lock4 = getAttribute(player, LOCK_4, 65).toChar()
                val answer = arrayOf(lock1, lock2, lock3, lock4).joinToString("")
                closeInterface(player)
                if (answers[getAttribute(player, SirRenItchwoodDialogueFile.ATTRIBUTE_CLUE, 0)] == answer) {
                    if (!getAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_STAGE_FAILED, false)) {
                        setAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_STAGE_PASSED, true)
                    }
                    sendNPCDialogue(player, NPCs.SIR_REN_ITCHOOD_2287, "Your wit is sharp, your brains quite clear; You solved my puzzle with no fear. At puzzles I rank you quite the best, now enter the portal for your next test.")
                } else {
                    setAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_STAGE_FAILED, true)
                    openDialogue(player, SirRenItchwoodDialogueFile(2), NPC(NPCs.SIR_REN_ITCHOOD_2287))
                }
            }
            return@on true
        }
    }
}
