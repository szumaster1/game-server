package content.region.desert.pollnivneach.dialogue

import cfg.consts.NPCs
import core.api.sendDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.tools.RandomFunction

/**
 * Represents the Ali the camel dialogue.
 */
@Initializable
class AliTheCamelDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        val phrase = RandomFunction.random(1, 4)
        when (phrase) {
            1 -> player(FacialExpression.AFRAID, "That beast would probably bite my fingers off", "if I tried to pet it")
            2 -> player(FacialExpression.DISGUSTED, "I'm not going to pet that! I might get fleas", "or something else that nasty creature", "might have.")
            3 -> player(FacialExpression.THINKING, "Mmmm... Won't you make the nicest kebab?")
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                player.lock()
                player.animate(Animation(7299))
                player.impactHandler.disabledTicks = 3
                Pulser.submit(object : Pulse(4, player) {
                    override fun pulse(): Boolean {
                        player.unlock()
                        player.animator.reset()
                        return true
                    }
                })
                sendDialogue(player, "The camel tries to kick you for insulting it.")
                stage = 1
            }

            1 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ALI_THE_CAMEL_1873)
    }

}
