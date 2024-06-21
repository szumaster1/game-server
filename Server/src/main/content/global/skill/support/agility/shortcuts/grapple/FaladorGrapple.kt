package content.global.skill.support.agility.shortcuts.grapple

import core.api.consts.Items
import core.api.sendDialogue
import core.cache.def.impl.SceneryDefinition
import core.game.component.Component
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.impl.ForceMovement
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.network.packet.PacketRepository
import core.network.packet.context.MinimapStateContext
import core.network.packet.outgoing.MinimapState
import core.plugin.Initializable
import core.plugin.Plugin

@Initializable
class FaladorGrapple : OptionHandler() {

    companion object {
        private val REQUIREMENTS = HashMap<Int?, Int>()
        private var requirementsString: String? = null

        init {
            REQUIREMENTS.putIfAbsent(Skills.AGILITY, 11)
            REQUIREMENTS.putIfAbsent(Skills.RANGE, 19)
            REQUIREMENTS.putIfAbsent(Skills.STRENGTH, 37)
            requirementsString =
                "You need at least " + REQUIREMENTS[Skills.AGILITY] + " " + Skills.SKILL_NAME[Skills.AGILITY] + ", " + REQUIREMENTS[Skills.RANGE] + " " + Skills.SKILL_NAME[Skills.RANGE] + ", and " + REQUIREMENTS[Skills.STRENGTH] + " " + Skills.SKILL_NAME[Skills.STRENGTH] + " to use this shortcut."
        }

        private val crossbowIds = intArrayOf(Items.DORGESHUUN_CBOW_8880, Items.MITH_CROSSBOW_9181, Items.ADAMANT_CROSSBOW_9183, Items.RUNE_CROSSBOW_9185, Items.KARILS_CROSSBOW_4734)
        private val grappleId = Item(Items.MITH_GRAPPLE_9419)
    }

    override fun newInstance(arg: Any?): Plugin<Any> {
        SceneryDefinition.forId(17049).handlers["option:grapple"] = this
        SceneryDefinition.forId(17050).handlers["option:grapple"] = this
        SceneryDefinition.forId(17051).handlers["option:jump"] = this
        SceneryDefinition.forId(17052).handlers["option:jump"] = this
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        val destination: Location
        val current = player.location
        when (option) {
            "jump" -> ForceMovement.run(
                player,
                current,
                if (node.asScenery().id == 17051) Location.create(3033, 3390, 0) else Location.create(3032, 3388, 0),
                Animation(7268),
                10
            )

            "grapple" -> {
                destination =
                    if (node.asScenery().id == 17049) Location.create(3032, 3389, 1) else Location.create(3032, 3391, 1)
                for ((key, value) in REQUIREMENTS) {
                    if (player.getSkills().getLevel(key!!) < value) {
                        sendDialogue(player, requirementsString.toString())
                        return true
                    }
                }
                if (!player.equipment.containsAtLeastOneItem(crossbowIds) || !player.equipment.containsItem(grappleId)) {
                    player.dialogueInterpreter.sendDialogue("You need a Mithril crossbow and a Mithril grapple in order to do this.")
                    return true
                }
                player.lock()
                Pulser.submit(object : Pulse(1, player) {
                    var counter = 1
                    var tab: Component? = null
                    override fun pulse(): Boolean {
                        when (counter++) {
                            1 -> {
                                player.faceLocation(destination)
                                player.visualize(Animation(4455),
                                    Graphic(760, 100)
                                )
                            }

                            8 -> {
                                tab = player.interfaceManager.singleTab
                                player.interfaceManager.openOverlay(Component(115))
                                PacketRepository.send(MinimapState::class.java, MinimapStateContext(player, 2))
                                player.interfaceManager.removeTabs(0, 1, 2, 3, 4, 5, 6, 11, 12)
                            }

                            13 -> player.properties.teleportLocation = destination
                            14 -> {
                                player.interfaceManager.restoreTabs()
                                if (tab != null) {
                                    player.interfaceManager.openTab(tab)
                                }
                                PacketRepository.send(MinimapState::class.java, MinimapStateContext(player, 0))
                                player.interfaceManager.closeOverlay()
                                player.interfaceManager.close()
                                player.unlock()
                                player.achievementDiaryManager.finishTask(player, DiaryType.FALADOR, 1, 2)
                                return true
                            }
                        }
                        return false
                    }
                })
            }
        }
        return true
    }

    override fun getDestination(moving: Node, destination: Node): Location? {
        return if (destination.asScenery().id == 17050) Location.create(3032, 3388, 0) else null
    }
}

