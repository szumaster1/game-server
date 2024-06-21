package content.global.random.event.prisonpete

/*
import core.api.consts.Components
import core.api.setAttribute
import core.api.setComponentVisibility
import core.api.setInterfaceText
import core.game.interaction.InterfaceListener
import core.tools.RandomFunction

class PrisonPeteInterface : InterfaceListener {

    val PRISON_PETE_INTERFACE = Components.PRISONPETE_273
    override fun defineInterfaceListeners() {
        onOpen(PRISON_PETE_INTERFACE) { player, _ ->
            setComponentVisibility(player, PRISON_PETE_INTERFACE, 2, true)
            return@onOpen true
        }

        on(PRISON_PETE_INTERFACE) { player, _, _, _, _, _ ->
            val getRandom = RandomFunction.random(0, 3)
            when (getRandom) {
                0 -> {
                    setInterfaceText(player, core.tools.YELLOW + "POP THE NPC ID: 3119", PRISON_PETE_INTERFACE, 1)
                    setAttribute(player, PrisonUtils.POP_KEY_VALUE, 0)
                }

                1 -> {
                    setInterfaceText(player, core.tools.YELLOW + "POP THE NPC ID: 3120", PRISON_PETE_INTERFACE, 1)
                    setAttribute(player, PrisonUtils.POP_KEY_VALUE, 1)
                }

                2 -> {
                    setInterfaceText(player, core.tools.YELLOW + "POP THE NPC ID: 3121", PRISON_PETE_INTERFACE, 1)
                    setAttribute(player, PrisonUtils.POP_KEY_VALUE, 2)
                }

                3 -> {
                    setInterfaceText(player, "${core.tools.YELLOW} POP THE NPC ID: 3122", PRISON_PETE_INTERFACE, 1)
                    setAttribute(player, PrisonUtils.POP_KEY_VALUE, 3)
                }
            }
            return@on true
        }
    }
}
*/