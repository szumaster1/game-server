package content.region.morytania.quest.naturespirit

import core.api.consts.Sounds
import content.region.morytania.quest.naturespirit.dialogue.DrezelDialogueFile
import core.api.*
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic

class BlessingPulse(val drezel: NPC, val player: Player) : Pulse() {
    var ticks = 0

    override fun pulse(): Boolean {
        when (ticks) {
            0 -> animate(drezel, 1162).also {
                spawnProjectile(drezel, player, 268); playAudio(
                player,
                Sounds.PRAYER_RECHARGE_2674
            )
            }

            2 -> visualize(player, Animation(645),
                Graphic(267, 100)
            )
            4 -> unlock(player).also {
                player.questRepository.getQuest("Nature Spirit").setStage(player, 40); return true
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