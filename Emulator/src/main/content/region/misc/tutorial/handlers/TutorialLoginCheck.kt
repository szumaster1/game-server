package content.region.misc.tutorial.handlers

import core.api.LoginListener
import core.api.getAttribute
import core.api.setAttribute
import core.api.submitWorldPulse
import core.game.node.entity.player.Player
import core.game.system.task.Pulse

class TutorialLoginCheck : LoginListener {

    override fun login(player: Player) {
        if (!getAttribute(player, "tutorial:complete", false)) {
            if (getAttribute(entity = player, attribute = "tutorial:stage", default = 0) == 0 && (player.skills.totalLevel > 33 || player.bank.itemCount() > 0 || player.inventory.itemCount() > 0)) {
                setAttribute(player, "/save:tutorial:complete", true)
                return
            }
            submitWorldPulse(object : Pulse(2) {
                override fun pulse(): Boolean {
                    TutorialStage.load(player, getAttribute(player, "tutorial:stage", 0), true)
                    return true
                }
            })
        }
    }
}