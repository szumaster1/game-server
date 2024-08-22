package content.global.handlers.iface.player

import cfg.consts.Components
import core.api.getAttribute
import core.api.setVarbit
import core.game.interaction.InterfaceListener

/**
 * Skill interface listener.
 */
class SkillInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.SKILL_GUIDE_V2_499){ player, _, _, buttonID, _, _ ->
            setVarbit(player, 3288, getAttribute(player, "skillMenu", -1))
            setVarbit(player, 3289, buttonID - 10)
            return@on true
        }
    }
}
