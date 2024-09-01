package content.region.morytania.swamp.quest.druidspirit.handlers

import cfg.consts.Animations
import cfg.consts.Graphics
import cfg.consts.Sounds
import content.region.morytania.swamp.quest.druidspirit.dialogue.DrezelDialogueFile
import core.api.*
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic

/**
 * Class representing a Blessing Pulse.
 *
 * @param drezel The NPC associated with the Blessing Pulse.
 * @param player The Player associated with the Blessing Pulse.
 * @constructor Creates a Blessing Pulse with the given NPC and Player.
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