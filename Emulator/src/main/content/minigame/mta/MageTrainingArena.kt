package content.minigame.mta

import content.global.skill.agility.AgilityHandler
import content.minigame.mta.impl.TelekineticZone
import org.rs.consts.Items
import core.cache.def.impl.ItemDefinition
import core.cache.def.impl.NPCDefinition
import core.cache.def.impl.SceneryDefinition
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.GroundItem
import core.game.node.item.Item
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.map.zone.ZoneBuilder
import core.game.world.update.flag.context.Animation
import core.plugin.PluginManager.definePlugins
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Represents the Mage training arena.
 */
@Initializable
class MageTrainingArena : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        ItemDefinition.forId(6885).handlers["option:destroy"] = this
        ItemDefinition.forId(6885).handlers["option:talk-to"] = this
        SceneryDefinition.forId(10721).handlers["option:enter"] = this
        NPCDefinition.forId(3103).handlers["option:trade-with"] = this
        for (type in MTAType.values()) {
            if (type.mtaZone != null) {
                ZoneBuilder.configure(type.mtaZone)
            }
            SceneryDefinition.forId(type.sceneryId).handlers["option:enter"] = this
        }
        ItemDefinition.forId(TelekineticZone.STATUE).handlers["option:observe"] = this
        ItemDefinition.forId(TelekineticZone.STATUE).handlers["option:reset"] = this
        NPCDefinition.forId(3102).handlers["option:talk-to"] = this
        definePlugins(EnchantSpell(), TelekineticGrabSpell())
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        when (node.id) {
            10721 -> {
                if (!player.getSkills().hasLevel(Skills.MAGIC, 7)) {
                    player.dialogueInterpreter.sendDialogue("You need a Magic level of at least 7 to enter the guild.")
                }
                AgilityHandler.walk(player, -1, player.location, player.location.transform(Direction.getDirection(player.location, node.location), 2), Animation(1426), 0.0, null)
            }

            3103 -> if (!player.getSavedData().activityData.isStartedMta) {
                player.dialogueInterpreter.open(3103, this, true, true)
            } else {
                SHOP.open(player)
            }

            6885 -> player.dialogueInterpreter.open(3096, *if (option == "destroy") arrayOf(node, true, true) else arrayOf())

            3102 -> player.dialogueInterpreter.open(node.id, node)
        }
        when (option) {
            "enter" -> {
                val type: MTAType = MTAType.forId(node.id)!!
                if (type != null) {
                    type.enter(player)
                }
            }

            "reset", "observe" -> {
                val zone: TelekineticZone = TelekineticZone.getZone(player)!!
                if (option == "reset") {
                    zone.reset(player)
                } else {
                    zone.observe(player)
                }
            }
        }
        return true
    }

    override fun isWalk(player: Player, n: Node): Boolean {
        if (n !is GroundItem) {
            return true
        }
        return n.getId() != TelekineticZone.STATUE
    }

    override fun getDestination(node: Node, n: Node): Location? {
        return if (n.id == 3102) {
            n.location.transform(Direction.getDirection(node.location, n.location), -1)
        } else null
    }

    override fun isWalk(): Boolean {
        return false
    }

    companion object {
        val PROGRESS_HAT = Item(Items.PROGRESS_HAT_6885)
        val SHOP = MTAShopInterfaceListener()
    }
}