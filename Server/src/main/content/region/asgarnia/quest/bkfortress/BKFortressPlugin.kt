package content.region.asgarnia.quest.bkfortress

import core.api.allInEquipment
import core.api.consts.Items
import core.cache.def.impl.ItemDefinition
import core.cache.def.impl.SceneryDefinition
import core.game.global.action.ClimbActionHandler.climb
import core.game.global.action.ClimbActionHandler.climbLadder
import core.game.global.action.DoorActionHandler.handleAutowalkDoor
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Location
import core.game.world.repository.Repository.findNPC
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Plugin

class BKFortressPlugin : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        ItemDefinition.forId(9589).handlers["option:read"] = this
        SceneryDefinition.forId(74).handlers["option:open"] = this
        SceneryDefinition.forId(73).handlers["option:open"] = this
        SceneryDefinition.forId(2337).handlers["option:open"] = this
        SceneryDefinition.forId(2338).handlers["option:open"] = this
        SceneryDefinition.forId(2341).handlers["option:push"] = this
        SceneryDefinition.forId(17148).handlers["option:climb-up"] = this
        SceneryDefinition.forId(17149).handlers["option:climb-down"] = this
        SceneryDefinition.forId(17160).handlers["option:climb-down"] = this
        SceneryDefinition.forId(2342).handlers["option:listen-at"] = this
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        val id = if (node is Item) node.getId() else node.id
        val scenery = if (node is Scenery) node else null
        var dest: Location? = null
        when (id) {
            2342 -> {
                player.animate(LISTEN_ANIM)
                Pulser.submit(object : Pulse(2, player) {
                    override fun pulse(): Boolean {
                        player.animate(LOWER_ANIM)
                        player.dialogueInterpreter.open(992752973)
                        return true
                    }
                })
            }

            17160 -> {
                if (scenery!!.location == Location(3022, 3518, 1)) {
                    dest = Location.create(3022, 3517, 0)
                }
                if (dest != null) {
                    climb(player, Animation(828), dest)
                } else {
                    climbLadder(player, node as Scenery, option)
                }
            }

            17149 -> {
                if (scenery!!.location == Location(3023, 3513, 2)) {
                    dest = Location.create(3023, 3514, 1)
                } else if (scenery.location == Location(3025, 3513, 2)) {
                    dest = Location.create(3025, 3514, 1)
                } else if (scenery.location == Location(3016, 3519, 2)) {
                    dest = Location.create(3016, 3518, 1)
                } else if (scenery.location == Location(3015, 3519, 1)) {
                    dest = Location.create(3015, 3518, 0)
                } else if (scenery.location == Location(3017, 3516, 2)) {
                    dest = Location.create(3017, 3515, 1)
                }
                if (dest != null) {
                    climb(player, Animation(828), dest)
                } else {
                    climbLadder(player, node as Scenery, option)
                }
            }

            17148 -> {
                if (scenery!!.location == Location(3021, 3510, 0)) {
                    dest = Location.create(3022, 3510, 1)
                } else if (scenery.location == Location(3015, 3519, 0)) {
                    dest = Location.create(3015, 3518, 1)
                } else if (scenery.location == Location(3016, 3519, 0)) {
                    dest = Location.create(3016, 3518, 2)
                }
                if (dest != null) {
                    climb(player, Animation(828), dest)
                } else {
                    climbLadder(player, node as Scenery, option)
                }
            }

            2341 -> {
                player.packetDispatch.sendMessage("You push against the wall. You find a secret passage.")
                handleAutowalkDoor(player, (node as Scenery))
                return true
            }

            2338 -> {
                if (player.location.x > 3019) {
                    handleAutowalkDoor(player, (node as Scenery)) // big table room door
                    return true
                }
                player.dialogueInterpreter.open(4605, findNPC(4605), true, true)
            }

            2337 -> when (player.location.y) {
                3514 -> if (allInEquipment(player, Items.BRONZE_MED_HELM_1139, Items.IRON_CHAINBODY_1101)) {
                    handleAutowalkDoor(player, (node as Scenery))
                } else player.dialogueInterpreter.open(4605, findNPC(4604), true)

                3515 -> handleAutowalkDoor(player, (node as Scenery))
                else -> {}
            }

            74, 73 -> {
                if (player.location.x == 3008) { // only opened from inside
                    handleAutowalkDoor(player, (node as Scenery))
                    return true
                }
                player.packetDispatch.sendMessage("You can't open this door.") // large door to the fortress
            }

            9589 -> when (option) {
                "read" -> if (player.inventory.remove(node as Item)) {
                    player.lock()
                    Pulser.submit(object : Pulse(1) {
                        var counter: Int = 0

                        override fun pulse(): Boolean {
                            when (counter++) {
                                1 -> {
                                    player.animate(FIRST_ANIM)
                                    player.dialogueInterpreter.sendDialogue(
                                        "Infiltrate fortress... sabotage secret weapon... self",
                                        "destruct in 3...2...ARG!"
                                    )
                                }

                                5 -> player.graphics(SMOKE)
                                7 -> {
                                    player.interfaceManager.closeChatbox()
                                    player.animate(LAST_ANIM)
                                    player.unlock()
                                    return true
                                }
                            }
                            return false
                        }
                    })
                }
            }
        }
        return true
    }

    override fun isWalk(): Boolean {
        return false
    }

    override fun isWalk(player: Player, node: Node): Boolean {
        return node !is Item
    }

    companion object {
        private val LISTEN_ANIM = Animation(4195)
        private val LOWER_ANIM = Animation(4552)
        private val FIRST_ANIM = Animation(4549)
        private val LAST_ANIM = Animation(4551)
        private val SMOKE = Graphic(86, 109, 1)
    }
}
