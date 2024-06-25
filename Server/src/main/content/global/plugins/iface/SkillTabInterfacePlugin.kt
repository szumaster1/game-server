package content.global.plugins.iface

import core.api.consts.Components
import core.api.setVarp
import core.game.component.Component
import core.game.component.ComponentDefinition
import core.game.component.ComponentPlugin
import core.game.node.entity.player.Player
import core.game.node.entity.skill.LevelUp
import core.game.node.entity.skill.Skills
import core.game.world.GameWorld.settings
import core.plugin.Initializable
import core.plugin.Plugin

@Initializable
class SkillTabInterfacePlugin : ComponentPlugin() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        ComponentDefinition.put(Components.STATS_320, this)
        return this
    }

    override fun handle(p: Player, component: Component, opcode: Int, button: Int, slot: Int, itemId: Int): Boolean {
        val config = SkillConfig.forId(button) ?: return true
        if (!settings!!.isPvp) {
            if (p.getAttribute("levelup:" + config.skillId, false)) {
                p.removeAttribute("levelup:" + config.skillId)
                LevelUp.sendFlashingIcons(p, -1)
                setVarp(p, 1230, ADVANCE_CONFIGS[config.skillId])
                p.interfaceManager.open(Component(741))
            } else {
                p.pulseManager.clear()
                p.interfaceManager.open(Component(499))
                setVarp(p, 965, config.config)
                p.attributes["skillMenu"] = config.config
            }
        } else {
            if (config.skillId > 6) {
                p.packetDispatch.sendMessage("You cannot set a target level for this skill.")
                return false
            }
            if (p.canSpawn()) {
                p.sendMessage("You must be inside Edgeville bank to set levels.")
                return false
            }
        }
        return true
    }

    enum class SkillConfig(
        val button: Int,
        val config: Int,
        val skillId: Int
    ) {
        ATTACK(125, 1, Skills.ATTACK),
        STRENGTH(126, 2, Skills.STRENGTH),
        DEFENCE(127, 5, Skills.DEFENCE),
        RANGE(128, 3, Skills.RANGE),
        PRAYER(129, 7, Skills.PRAYER),
        MAGIC(130, 4, Skills.MAGIC),
        RUNECRAFTING(131, 12, Skills.RUNECRAFTING),
        HITPOINTS(133, 6, Skills.HITPOINTS),
        AGILITY(134, 8, Skills.AGILITY),
        HERBLORE(135, 9, Skills.HERBLORE),
        THIEVING(136, 10, Skills.THIEVING),
        CRAFTING(137, 11, Skills.CRAFTING),
        FLETCHING(138, 19, Skills.FLETCHING),
        SLAYER(139, 20, Skills.SLAYER),
        MINING(141, 13, Skills.MINING),
        SMITHING(142, 14, Skills.SMITHING),
        FISHING(143, 15, Skills.FISHING),
        COOKING(144, 16, Skills.COOKING),
        FIREMAKING(145, 17, Skills.FIREMAKING),
        WOODCUTTING(146, 18, Skills.WOODCUTTING),
        FARMING(147, 21, Skills.FARMING),
        CONSTRUCTION(132, 22, Skills.CONSTRUCTION),
        HUNTER(140, 23, Skills.HUNTER),
        SUMMONING(148, 24, Skills.SUMMONING);

        companion object {

            fun forId(id: Int): SkillConfig? {
                for (config in values()) {
                    if (config.button == id) return config
                }
                return null
            }
        }
    }

    companion object {
        val ADVANCE_CONFIGS: IntArray = intArrayOf(9, 40, 17, 49, 25, 57, 33, 641, 659, 664, 121, 649, 89, 114, 107, 72, 64, 80, 673, 680, 99, 698, 689, 705,)
    }
}
