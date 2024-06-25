package content.global.plugins.iface

import core.game.component.Component
import core.game.component.ComponentDefinition
import core.game.component.ComponentPlugin
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.combat.CombatSwingHandler
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.combat.equipment.WeaponInterface.WeaponInterfaces
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.plugin.Initializable
import core.plugin.Plugin

@Initializable
class CombatTabInterfacePlugin : ComponentPlugin() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        for (inter in WeaponInterfaces.values()) {
            ComponentDefinition.put(inter.interfaceId, this)
        }
        ComponentDefinition.put(92, this)
        return this
    }

    override fun handle(p: Player, component: Component, opcode: Int, button: Int, slot: Int, itemId: Int): Boolean {
        when (button) {
            7, 9, 24, 26, 27 -> Pulser.submit(object : Pulse(1, p) {
                override fun pulse(): Boolean {
                    p.settings.toggleRetaliating()
                    return true
                }
            })

            11, 10, 8, 85 -> Pulser.submit(object : Pulse(1, p) {
                override fun pulse(): Boolean {
                    val inter = p.getExtension<WeaponInterface>(WeaponInterface::class.java)
                    if (inter != null && inter.isSpecialBar) {
                        p.settings.toggleSpecialBar()
                        if (p.settings.isSpecialToggled) {
                            var handler: CombatSwingHandler
                            if ((CombatStyle.MELEE.swingHandler.getSpecial(p.equipment.getNew(3).id)
                                    .also { handler = it!! }) != null
                            ) {
                                val plugin = handler as Plugin<Any>
                                if (plugin.fireEvent("instant_spec", p) === java.lang.Boolean.TRUE) {
                                    handleInstantSpec(p, handler, plugin)
                                }
                            }
                        }
                    }
                    return true
                }
            })

            0 -> {
                val inter = p.getExtension<WeaponInterface>(WeaponInterface::class.java) ?: return false
                if (inter.setAttackStyle(button)) {
                    if (button == 4 || button == 5) {
                        inter.openAutocastSelect()
                    } else if (p.properties.autocastSpell != null) {
                        inter.selectAutoSpell(-1, false)
                    }
                    return true
                }
                return false
            }

            else -> {
                val inter = p.getExtension<WeaponInterface>(WeaponInterface::class.java) ?: return false
                if (inter.setAttackStyle(button)) {
                    if (button == 4 || button == 5) {
                        inter.openAutocastSelect()
                    } else if (p.properties.autocastSpell != null) {
                        inter.selectAutoSpell(-1, false)
                    }
                    return true
                }
                return false
            }
        }
        return true
    }

    companion object {
        private fun handleInstantSpec(p: Player, handler: CombatSwingHandler, plugin: Plugin<Any>) {
            handler.swing(p, p.properties.combatPulse.getVictim(), null)
        }
    }
}
