package content.region.desert.alkharid.dialogue

import cfg.consts.Animations
import content.global.skill.support.agility.AgilityHandler
import cfg.consts.Items
import cfg.consts.NPCs
import cfg.consts.Sounds
import core.api.*
import core.cache.def.impl.NPCDefinition
import core.game.container.impl.EquipmentContainer
import core.game.dialogue.Dialogue
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.plugin.PluginManager
import core.plugin.Initializable
import core.plugin.Plugin
import core.tools.colorize

/**
 * Represents the Rug merchant dialogue.
 */
@Initializable
class RugMerchantDialogue(player: Player? = null) : Dialogue(player) {

    private var current: RugDestination? = null
    private var options: Array<RugDestination>? = null
    private var destination: RugDestination? = null

    companion object {
        private val IDS = intArrayOf(NPCs.RUG_MERCHANT_2291, NPCs.RUG_MERCHANT_2292, NPCs.RUG_MERCHANT_2293, NPCs.RUG_MERCHANT_2294, NPCs.RUG_MERCHANT_2296, NPCs.RUG_MERCHANT_2298, NPCs.RUG_MERCHANT_3020)
        private val FLOATING_ANIMATION = Animation(Animations.MAGIC_CARPET_FLIGHT_330)

        /**
         * Gets the rug destinations for a npc id.
         *
         * @param npcId the npc id
         * @return the rug destination
         */
        fun getDestination(npcId: Int): Array<RugDestination> {
            return when (npcId) {
                NPCs.RUG_MERCHANT_2291 -> arrayOf(RugDestination.UZER, RugDestination.BEDABIN_CAMP, RugDestination.NORTH_POLLNIVNEACH)
                NPCs.RUG_MERCHANT_2292, NPCs.RUG_MERCHANT_2294, NPCs.RUG_MERCHANT_2293 -> arrayOf(RugDestination.SHANTAY_PASS)
                NPCs.RUG_MERCHANT_3020 -> arrayOf(RugDestination.NARDAH, RugDestination.SOPHANEM)
                else -> arrayOf(RugDestination.SOUTH_POLLNIVNEACH)
            }
        }
    }

    override fun init() {
        super.init()
        PluginManager.definePlugin(RugMerchantPlugin())
    }

    override fun newInstance(player: Player): Dialogue {
        return RugMerchantDialogue(player)
    }

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        current = RugDestination.forId(npc.id)
        if (args.size >= 2) {
            options = getDestination(npc.id)
            sendOptions(options!!)
            stage = 11
            return true
        }
        player("Hello.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        handleDefault(buttonId)
        return true
    }

    private fun handleDefault(buttonId: Int) {
        when (stage) {
            0 -> {
                npc(
                    "Greetings, desert traveller. Do you require the services",
                    "of Ali Morrisane's flying carpet fleet?"
                )
                stage++
            }

            1 -> {
                options("Yes, please.", "No thanks.")
                stage++
            }

            2 -> {
                when (buttonId) {
                    1 -> {
                        player("Yes, please.")
                        stage = 10
                    }

                    2 -> {
                        player("No thanks.")
                        stage = 20
                    }
                }
            }

            8 -> {
                options("Yes.", "No.")
                stage++
            }

            9 -> {
                if (buttonId == 2) {
                    end()
                } else {
                    destination = options!![0]
                    player("Yes, please.")
                    stage = 11
                }
            }

            10 -> {
                sendOptions(getDestination(npc!!.id))
                stage = 11
            }

            11 -> {
                if (!player.inventory.contains(995, 200)) {
                    npc("A travel on one of my rugs costs 200 gold", "coins.")
                    stage = 20
                    return
                }
                end()
                destination = if (options!!.size == 1) options!![0] else options!![buttonId - 1]
                when (destination) {
                    RugDestination.UZER -> if (!hasRequirement(player, "The Golem")) return
                    RugDestination.BEDABIN_CAMP -> if (!hasRequirement(player, "The Tourist Trap")) return
                    RugDestination.SOPHANEM -> if (!hasRequirement(player, "Icthlarin's Little Helper")) return
                    else -> if (player.inventory.remove(Item(995, 200))) {
                        destination!!.travel(current, player)
                    }
                }
                if (player.equipment[EquipmentContainer.SLOT_WEAPON] != null) {
                    player.sendMessage(colorize("%RYou must unequip all your weapons before you can fly on a carpet."))
                } else {
                    if (player.inventory.remove(Item(995, 200))) {
                        destination!!.travel(current, player)
                    }
                }
            }

            20 -> end()
        }
    }

    private fun sendOptions(destinations: Array<RugDestination>) {
        val options = Array(destinations.size) { i -> destinations[i].destinationName }
        if (destinations.size == 1) {
            npc("Travel back to ${destinations[0].destinationName}?")
            stage = 8
            return
        }
        interpreter.sendOptions("Select a Destination", options.toString())
    }

    override fun getIds(): IntArray {
        return IDS
    }

    class RugMerchantPlugin : OptionHandler() {
        override fun newInstance(arg: Any?): Plugin<Any> {
            for (id in IDS) {
                NPCDefinition.forId(id).handlers["option:travel"] = this
            }
            return this
        }

        override fun handle(player: Player, node: Node, option: String): Boolean {
            player.dialogueInterpreter.open(node.id, node, true, true)
            return true
        }
    }

    enum class RugDestination(
        val npc: Int,
        val location: Location,
        val destinationName: String,
        val locData: Array<Location> = emptyArray()
    ) {
        SHANTAY_PASS(
            npc = NPCs.RUG_MERCHANT_2291,
            location = Location.create(3308, 3110, 0),
            destinationName = "Shantay Pass"
        ),
        BEDABIN_CAMP(
            npc = NPCs.RUG_MERCHANT_2292,
            location = Location.create(3180, 3045, 0),
            destinationName = "Bedabin Camp",
            locData = arrayOf(Location.create(3305, 3107, 0), Location.create(3299, 3107, 0), Location.create(3285, 3088, 0))
        ),
        NORTH_POLLNIVNEACH(
            npc = NPCs.RUG_MERCHANT_2294,
            location = Location.create(3349, 3003, 0),
            destinationName = "North Pollnivneach"
        ),
        UZER(
            npc = NPCs.RUG_MERCHANT_2293,
            location = Location.create(3469, 3113, 0),
            destinationName = "Uzer"
        ),
        NARDAH(
            npc = NPCs.RUG_MERCHANT_2296,
            location = Location.create(3401, 2916, 0),
            destinationName = "Nardah"
        ),
        SOPHANEM(
            npc = NPCs.RUG_MERCHANT_2298,
            location = Location.create(3285, 2813, 0),
            destinationName = "Sophanem"
        ),
        SOUTH_POLLNIVNEACH(
            npc = NPCs.RUG_MERCHANT_3020,
            location = Location.create(3351, 2942, 0),
            destinationName = "South Pollnivneach"
        );

        fun travel(current: RugDestination?, player: Player) {
            player.lock()
            setVarp(player, 499, 0)
            player.impactHandler.disabledTicks = GameWorld.ticks + 200
            player.interfaceManager.removeTabs(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13)
            player.equipment.replace(Item(Items.MAGIC_CARPET_5614), EquipmentContainer.SLOT_WEAPON)
            player.packetDispatch.sendInterfaceConfig(548, 69, true)
            playAudio(player, Sounds.CARPET_RISE_1196)
            playJingle(player, 132)

            registerLogoutListener(player, "magic-carpet") {
                removeItem(it, Items.MAGIC_CARPET_5614, Container.EQUIPMENT)
                Unit
            }

            GameWorld.Pulser.submit(object : Pulse(1, player) {
                var count = 0
                var index = 0
                var locs: Array<Location> = current?.locData ?: locData

                override fun pulse(): Boolean {
                    count++
                    when (count) {
                        1 -> {
                            player.faceLocation(current!!.location)
                        }

                        2 -> AgilityHandler.walk(player, -1, player.location, current!!.location, null, 0.0, null)
                        3 -> player.faceLocation(location)
                        4 -> setVarp(player, 499, 1)
                        200 -> {
                        }

                        901 -> {
                            player.equipment.replace(null, EquipmentContainer.SLOT_WEAPON)
                            player.interfaceManager.restoreTabs()
                            player.packetDispatch.sendInterfaceConfig(548, 69, false)
                            player.impactHandler.disabledTicks = 0
                            player.unlock()
                            player.animate(Animation(-1))
                            setVarp(player, 499, 0)
                            playAudio(player, Sounds.CARPET_DESCEND_1195)
                            clearLogoutListener(player, "magic-carpet")
                        }

                        902 -> {
                            player.moveStep()
                            return true
                        }

                        else -> {
                            if (index > locs.size - 1) {
                                count = 900
                                return false
                            }
                            if (index == 0 || player.location == locs[index - 1]) {
                                AgilityHandler.walk(
                                    player,
                                    -1,
                                    player.location,
                                    locs[index],
                                    FLOATING_ANIMATION,
                                    0.0,
                                    null
                                )
                                index++
                            }
                        }
                    }
                    return false
                }
            })
        }

        companion object {
            fun forId(npcId: Int): RugDestination? {
                return values().find { it.npc == npcId }
            }
        }
    }
}
