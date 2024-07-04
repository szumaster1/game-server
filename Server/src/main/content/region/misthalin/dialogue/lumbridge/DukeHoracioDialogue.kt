package content.region.misthalin.dialogue.lumbridge

import core.api.consts.NPCs
import content.region.misthalin.quest.member.losttribe.dialogue.DukeHoracioLostTribeDialogue
import content.region.misthalin.quest.free.dragonslayer.DragonSlayer
import content.region.misthalin.quest.free.dragonslayer.dialogue.DukeHoracioDragonSlayerDialogue
import content.region.misthalin.quest.free.runemysteries.dialogue.DukeHoracioRuneMysteriesDialogue
import core.api.getQuestStage
import core.api.isQuestComplete
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.tools.DIALOGUE_INITIAL_OPTIONS_HANDLE
import core.tools.END_DIALOGUE

class DukeHoracioDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if ((getQuestStage(player, "Dragon Slayer") == 100 && !player.inventory.containsItem(DragonSlayer.SHIELD) && !player.bank.containsItem(DragonSlayer.SHIELD)) || (player.questRepository.getQuest("Dragon Slayer").isStarted(player) && !isQuestComplete(player, "Dragon Slayer"))) {
            addOption("Dragon Slayer", DukeHoracioDragonSlayerDialogue(player.questRepository.getStage("Dragon Slayer")))
        }
        if (!isQuestComplete(player, "Lost Tribe") && player.questRepository.getQuest("Lost Tribe").isStarted(player)) {
            addOption("Lost Tribe", DukeHoracioLostTribeDialogue(player.questRepository.getStage("Lost Tribe")))
        }
        if (!sendChoices()) {
            interpreter.sendDialogues(npc, FacialExpression.HALF_GUILTY, "Greetings. Welcome to my castle.")
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            DIALOGUE_INITIAL_OPTIONS_HANDLE -> {
                npc("Greetings. Welcome to my castle.")
                loadFile(optionFiles[buttonId - 1])
            }
            0 -> {
                options("Have you any quests for me?", "Where can I find money?")
                stage = 1
            }
            1 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY, "Have any quests for me?")
                    stage = 20
                }
                2 -> {
                    npc(FacialExpression.HALF_GUILTY, "I hear many of the local people earn money by learning a", "skill. Many people get by in life by becoming accomplished", "smiths, cooks, miners and woodcutters.")
                    stage = END_DIALOGUE
                }
            }
            20 -> {
                npc("Let me see...")
                if (!player.questRepository.isComplete("Rune Mysteries")) {
                    loadFile(DukeHoracioRuneMysteriesDialogue(player.questRepository.getStage("Rune Mysteries")))
                } else {
                    stage++
                }
            }
            21 -> {
                npc("Nope, I've got everything under control", "in the castle at the moment.")
                stage = END_DIALOGUE
            }
        }
        return true
    }

    override fun newInstance(player: Player): Dialogue {
        return DukeHoracioDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DUKE_HORACIO_741)
    }

}
