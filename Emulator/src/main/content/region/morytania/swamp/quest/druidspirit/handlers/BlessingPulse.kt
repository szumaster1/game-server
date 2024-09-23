package content.region.morytania.swamp.quest.druidspirit.handlers

import org.rs.consts.Animations
import org.rs.consts.Graphics
import org.rs.consts.Sounds
import content.region.morytania.swamp.quest.druidspirit.dialogue.DrezelDialogueFile
import core.api.*
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic

/**
 * Represents a blessing pulse.
 */
class BlessingPulse(val drezel: NPC, val player: Player) : Pulse() {
    var ticks = 0

    override fun pulse(): Boolean {
        when (ticks) {
            0 -> animate(drezel, Animations.WALK_1162).also {
                spawnProjectile(drezel, player, 268); playAudio(player, Sounds.PRAYER_RECHARGE_2674)
            }

            2 -> visualize(player, Animation(Animations.HUMAN_PRAY_645),
                Graphic(Graphics.BRIGHT_GREEN_PUFF_OF_STUFF_267, 100)
            )
            4 -> unlock(player).also {
                setQuestStage(player, "Nature Spirit", 40)
                return true
            }
        }
        ticks++
        return false
    }

    override fun stop() {
        super.stop()
        openDialogue(player, DrezelDialogueFile(), drezel)
    }
}