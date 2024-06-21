package content.region.kandarin.quest.dwarfcannon.dialogue

import content.region.kandarin.quest.dwarfcannon.DwarfCannon
import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.quest.Quest

class LollkDialogue(player: Player? = null) : Dialogue(player) {

    private var quest: Quest? = null

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        quest = player.getQuestRepository().getQuest(DwarfCannon.NAME)
        when (quest!!.getStage(player)) {
            40 -> npc("Thank the heavens, you saved me!", "I thought I'd be goblin lunch for sure!")
            else -> player.sendMessage("The dwarf doesn't seem interested in talking to you.")
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (quest!!.getStage(player)) {
            40 -> when (stage) {
                0 -> {
                    player("Are you ok?")
                    stage++
                }

                1 -> {
                    npc("I think so, I'd better run off home.")
                    stage++
                }

                2 -> {
                    player("That's right, you get going. I'll catch up.")
                    stage++
                }

                3 -> {
                    npc("Thanks again, brave adventurer.")
                    stage++
                }

                4 -> {
                    npc.isInvisible = true
                    end()
                }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(207)
    }
}