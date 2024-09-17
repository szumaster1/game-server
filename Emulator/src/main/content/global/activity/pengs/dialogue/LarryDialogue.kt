package content.global.activity.pengs.dialogue

import org.rs.consts.NPCs
import content.global.activity.pengs.handlers.Penguin
import content.global.activity.pengs.handlers.PenguinManager
import core.api.getStatLevel
import core.api.removeAttribute
import core.api.sendMessage
import core.api.setAttribute
import core.game.component.Component
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.tools.END_DIALOGUE

/**
 * Represents the Larry dialogue.
 */
class LarryDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        options("Can I have a spy notebook?", "Can I have a hint?", "I'd like to turn in my points.").also {
            stage = 0
            return true
        }
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {

        class HintPulse : Pulse() {
            override fun pulse(): Boolean {
                val hint = Penguin.values()[PenguinManager.penguins.random()].hint
                sendMessage(player, "Here, I know one is...")
                player.sendMessage(hint)
                return true
            }
        }
        when (stage) {
            0 -> when (buttonId) {
                1 -> player("Can I have a spy notebook?").also { stage++ }
                2 -> player("Can I have a hint?").also { stage = 10 }
                3 -> player("I'd like to turn in my points.").also { stage = 20 }
            }
            1 -> npc("Sure!").also { player.inventory.add(Item(13732));stage = 1000 }
            10 -> npc("Yes, give me just one moment...").also { stage++ }
            11 -> {
                val hint = Penguin.values()[PenguinManager.penguins.random()].hint
                npcl(FacialExpression.FRIENDLY, "One is $hint")
                stage = END_DIALOGUE
            }
            20 -> if (player.getAttribute("phns:points", 0) > 0) npc("Sure thing, what would you like to be", "rewarded with?").also { stage++ } else npc("Uh, you don't have any points", "to turn in.").also { stage = 1000 }
            21 -> options("Coins", "Experience").also { stage++ }
            22 -> when (buttonId) {
                1 -> player.inventory.add(Item(995, 6500 * player.getAttribute("phns:points", 0))).also { player("Thanks!"); removeAttribute(player, "phns:points");stage = 1000 }
                2 -> {
                    setAttribute(player, "caller", this)
                    player.interfaceManager.open(Component(134).setCloseEvent { _: Player?, _: Component? ->
                        player.interfaceManager.openDefaultTabs()
                        removeAttribute(player, "lamp")
                        player.unlock()
                        true
                    }).also { end() }
                }
            }

            1000 -> end()
        }
        return true
    }

    override fun handleSelectionCallback(skill: Int, player: Player) {
        val points = player.getAttribute("phns:points", 0)
        if (points == 0) {
            sendMessage(player, "Sorry, but you have no points to redeem.")
            return
        }
        val level = getStatLevel(player, skill)
        val expGained = points?.toDouble()?.times((level * 25))
        System.out.print("exp: $expGained")
        player.skills.addExperience(skill, expGained!!)
        setAttribute(player, "/save:phns:points", 0)
    }

    override fun newInstance(player: Player?): Dialogue {
        return content.global.activity.pengs.dialogue.LarryDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.LARRY_5424)
    }

}
