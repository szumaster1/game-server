package content.global.skill.summoning.familiar.npc

import core.api.sendNPCDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.Metamorphosis
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.RandomFunction

/**
 * Baby Chinchompa NPC.
 */
@Initializable
class BabyChinchompaNPC : Metamorphosis(*CHINCHOMPA_IDS) {

    override fun getDialogue(): Dialogue {
        return BabyChinchompaDialogue()
    }

    override fun getRandomNpcId(): Int {
        val i = RandomFunction.getRandom(getIds().size - 1)
        if (getIds()[i] == 8658) {
            val x = RandomFunction.getRandom(30)
            return if (x == 1) {
                getIds()[i]
            } else {
                getIds()[i - 1]
            }
        }
        return getIds()[i]
    }

    /**
     * Baby chinchompa dialogue.
     */
    inner class BabyChinchompaDialogue : Dialogue {
        constructor()

        constructor(player: Player?) : super(player)

        override fun newInstance(player: Player): Dialogue {
            return BabyChinchompaDialogue(player)
        }

        override fun open(vararg args: Any): Boolean {
            npc = args[0] as NPC
            sendNPCDialogue(
                player,
                npc.id,
                if (npc.id != 8658) "Squeak! Squeak!" else "Squeaka! Squeaka!",
                FacialExpression.OLD_NORMAL
            )
            stage = 0
            return true
        }

        override fun handle(interfaceId: Int, buttonId: Int): Boolean {
            when (stage) {
                0 -> end()
            }
            return true
        }

        override fun getIds(): IntArray {
            return intArrayOf(8643, 8644, 8657, 8658)
        }
    }

    companion object {
        private val CHINCHOMPA_IDS = intArrayOf(8643, 8644, 8657, 8658)
    }
}
