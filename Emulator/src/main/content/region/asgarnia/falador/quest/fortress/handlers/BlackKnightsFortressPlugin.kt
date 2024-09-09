package content.region.asgarnia.falador.quest.fortress.handlers

import cfg.consts.Animations
import cfg.consts.Graphics
import cfg.consts.Items
import core.api.*
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

/**
 * Represents the Black knight fortress quest plugin.
 */
class BlackKnightsFortressPlugin : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        registerHandlers()
        return this
    }

    private fun registerHandlers() {
        val itemHandlers = mapOf(
            9589  to "option:read",
            74    to "option:open",
            73    to "option:open",
            2337  to "option:open",
            2338  to "option:open",
            2341  to "option:push",
            17148 to "option:climb-up",
            17149 to "option:climb-down",
            17160 to "option:climb-down"
        )
        itemHandlers.forEach { (id, option) ->
            ItemDefinition.forId(id).handlers[option] = this
            SceneryDefinition.forId(id).handlers[option] = this
        }
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        val id = if (node is Item) node.getId() else node.id
        val scenery = node as? Scenery
        val dest: Location? = determineDestination(scenery, id)

        when (id) {
            17160, 17149, 17148 -> handleClimb(player, scenery, dest, node, option)
            2341 -> handleSecretPassage(player, node)
            2338 -> handleBigTableRoom(player, node)
            2337 -> handleDoor(player, node)
            74, 73 -> handleLargeDoor(player, node)
            9589 -> handleRead(player, node.asItem(), option)
        }
        return true
    }

    private fun determineDestination(scenery: Scenery?, id: Int): Location? {
        return when (id) {
            17160 -> when (scenery?.location) {
                Location(3022, 3518, 1) -> Location.create(3022, 3517, 0)
                else -> null
            }

            17149 -> when (scenery?.location) {
                Location(3023, 3513, 2) -> Location.create(3023, 3514, 1)
                Location(3025, 3513, 2) -> Location.create(3025, 3514, 1)
                Location(3016, 3519, 2) -> Location.create(3016, 3518, 1)
                Location(3015, 3519, 1) -> Location.create(3015, 3518, 0)
                Location(3017, 3516, 2) -> Location.create(3017, 3515, 1)
                else -> null
            }

            17148 -> when (scenery?.location) {
                Location(3021, 3510, 0) -> Location.create(3022, 3510, 1)
                Location(3015, 3519, 0) -> Location.create(3015, 3518, 1)
                Location(3016, 3519, 0) -> Location.create(3016, 3518, 2)
                else -> null
            }

            else -> null
        }
    }

    private fun handleClimb(player: Player, scenery: Scenery?, dest: Location?, node: Node, option: String) {
        if (dest != null) {
            climb(player, Animation(Animations.HUMAN_CLIMB_STAIRS_828), dest)
        } else {
            climbLadder(player, node as Scenery, option)
        }
    }

    private fun handleSecretPassage(player: Player, node: Node) {
        sendMessage(player, "You push against the wall. You find a secret passage.")
        handleAutowalkDoor(player, node as Scenery)
    }

    private fun handleBigTableRoom(player: Player, node: Node) {
        if (player.location.x > 3019) {
            handleAutowalkDoor(player, node as Scenery)
        } else {
            player.dialogueInterpreter.open(4605, findNPC(4605), true, true)
        }
    }

    private fun handleDoor(player: Player, node: Node) {
        when (player.location.y) {
            3514 -> if (allInEquipment(player, Items.BRONZE_MED_HELM_1139, Items.IRON_CHAINBODY_1101)) {
                handleAutowalkDoor(player, node as Scenery)
            } else {
                player.dialogueInterpreter.open(4605, findNPC(4604), true)
            }

            3515 -> handleAutowalkDoor(player, node as Scenery)
        }
    }

    private fun handleLargeDoor(player: Player, node: Node) {
        if (player.location.x == 3008) {
            handleAutowalkDoor(player, node as Scenery)
        } else {
            sendMessage(player, "You can't open this door.")
        }
    }

    private fun handleRead(player: Player, node: Item, option: String) {
        if (option == "read" && removeItem(player, node)) {
            lock(player, 7)
            Pulser.submit(object : Pulse(1) {
                var counter: Int = 0

                override fun pulse(): Boolean {
                    when (counter++) {
                        1 -> {
                            animate(player, FIRST_ANIM)
                            sendDialogueLines(
                                player,
                                "Infiltrate fortress... sabotage secret weapon... self",
                                "destruct in 3...2...ARG!"
                            )
                        }

                        5 -> visualize(player, -1, SMOKE)
                        7 -> {
                            animate(player, LAST_ANIM)
                            closeChatBox(player)
                            resetAnimator(player)
                            return true
                        }
                    }
                    return false
                }
            })
        }
    }

    override fun isWalk(): Boolean {
        return false
    }

    override fun isWalk(player: Player, node: Node): Boolean {
        return node !is Item
    }

    companion object {
        private val FIRST_ANIM = Animation(Animations.READ_NOTE_WHILE_SHAKING_HEAD_4549)
        private val LAST_ANIM = Animation(Animations.BURN_NOTE_4551)
        private val SMOKE = Graphic(Graphics.RANDOM_EVENT_PUFF_OF_SMOKE_86, 109, 1)
    }
}
