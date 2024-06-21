package content.region.desert.dialogue.alkharid

import content.global.skill.support.agility.AgilityHandler
import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.consts.Sounds
import core.cache.def.impl.NPCDefinition
import core.game.container.impl.EquipmentContainer
import core.game.dialogue.Dialogue
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.system.timer.impl.AntiMacro.Companion.pause
import core.game.system.timer.impl.AntiMacro.Companion.unpause
import core.game.world.GameWorld.Pulser
import core.game.world.GameWorld.ticks
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.plugin.ClassScanner.definePlugin
import core.plugin.Initializable
import core.plugin.Plugin
import core.utilities.colorize

@Initializable
class RugMerchantDialogue(player: Player? = null) : Dialogue(player) {

    private var current: RugDestination? = null
    private lateinit var options: Array<RugDestination>
    private var destination: RugDestination? = null

    override fun init() {
        super.init()
        definePlugin(RugMerchantPlugin())
    }

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        current = RugDestination.forId(npc.id)
        if (args.size >= 2) {
            options = getDestination(npc.id)
            sendOptions(options)
            stage = 11
            return true
        }
        player("Hello.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            else -> handleDefault(buttonId)
        }
        return true
    }

    private fun handleDefault(buttonId: Int) {
        when (stage) {
            0 -> {
                npc("Greetings, desert traveller. Do you require the services", "of Ali Morrisane's flying carpet fleet?")
                stage++
            }

            1 -> {
                options("Yes, please.", "No thanks.")
                stage++
            }

            2 -> when (buttonId) {
                1 -> {
                    player("Yes, please.")
                    stage = 10
                }

                2 -> {
                    player("No thanks.")
                    stage = 20
                }
            }

            8 -> {
                options("Yes.", "No.")
                stage++
            }

            9 -> if (buttonId == 2) {
                end()
            } else {
                destination = options[0]
                player("Yes, please.")
                stage = 11
            }

            10 -> {
                sendOptions((getDestination(npc.id).also { options = it }))
                stage = 11
            }

            11 -> {
                if (!player.inventory.contains(995, 100)) {
                    npc("There is a fare for this service you know - normally it's", "200 gold per journey.")
                    stage = 20
                    return
                }
                end()
                destination = if (options.size == 1) options[0] else options[buttonId - 1]
                if (destination == RugDestination.UZER && !hasRequirement(player, "The Golem")) return
                else if (destination == RugDestination.BEDABIN_CAMP && !hasRequirement(player, "The Tourist Trap"))
                else if (destination == RugDestination.SOPHANEM && !hasRequirement(player, "Icthlarin's Little Helper"))
                    if (player.equipment[EquipmentContainer.SLOT_WEAPON] != null) {
                        player.sendMessage(colorize("%RYou must unequip all your weapons before you can fly on a carpet."))
                    } else {
                        if (player.inventory.remove(Item(995, 200))) destination!!.travel(current, player)
                    }
            }

            20 -> end()
        }
    }

    /**
     * Sends the rug travelling destination options.
     * @param destinations the destinations.
     */
    private fun sendOptions(destinations: Array<RugDestination>) {
        val options = arrayOfNulls<String>(destinations.size)
        if (destinations.size == 1) {
            npc("Travel back to " + destinations[0].name + "?")
            stage = 8
            return
        }
        for (i in destinations.indices) {
            options[i] = destinations[i].name
        }
        interpreter.sendOptions("Select a Destination", options.toString())
    }

    override fun getIds(): IntArray {
        return IDS
    }

    inner class RugMerchantPlugin : OptionHandler() {

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

    enum class RugDestination(val npc: Int, val location: Location, name: String, vararg locData: Location?) {
        SHANTAY_PASS(
            2291,
            Location.create(3308, 3110, 0),
            "Shantay Pass"
        ),
        BEDABIN_CAMP(
            2292,
            Location.create(3180, 3045, 0),
            "Bedabin Camp",
            Location.create(3305, 3107, 0),
            Location.create(3299, 3107, 0),
            Location.create(3285, 3088, 0),
            Location.create(3285, 3073, 0),
            Location.create(3268, 3073, 0),
            Location.create(3263, 3068, 0),
            Location.create(3246, 3068, 0),
            Location.create(3246, 3057, 0),
            Location.create(3232, 3057, 0),
            Location.create(3215, 3057, 0),
            Location.create(3200, 3057, 0),
            Location.create(3179, 3057, 0),
            Location.create(3179, 3047, 0),
            Location.create(3180, 3045, 0)
        ),
        NORTH_POLLNIVNEACH(
            2294,
            Location.create(3349, 3003, 0),
            "North Pollnivneach",
            Location(3308, 3096, 0),
            Location(3308, 3079, 0),
            Location(3308, 3066, 0),
            Location(3311, 3057, 0),
            Location(3319, 3042, 0),
            Location(3332, 3033, 0),
            Location(3341, 3020, 0),
            Location(3350, 3009, 0),
            Location(3351, 3003, 0),
            Location(3349, 3003, 0)
        ),
        UZER(
            2293,
            Location.create(3469, 3113, 0),
            "Uzer",
            Location.create(3308, 3105, 0),
            Location.create(3325, 3105, 0),
            Location.create(3332, 3105, 0),
            Location.create(3332, 3080, 0),
            Location.create(3341, 3080, 0),
            Location.create(3341, 3082, 0),
            Location.create(3358, 3082, 0),
            Location.create(3370, 3082, 0),
            Location.create(3382, 3082, 0),
            Location.create(3396, 3082, 0),
            Location.create(3432, 3082, 0),
            Location.create(3432, 3093, 0),
            Location.create(3440, 3093, 0),
            Location.create(3454, 3107, 0),
            Location.create(3469, 3107, 0),
            Location.create(3469, 3113, 0)
        ),

        NARDAH(
            2296,
            Location.create(3401, 2916, 0),
            "Nardah",
            Location(3351, 2942, 0),
            Location(3350, 2936, 0),
            Location(3362, 2936, 0),
            Location(3380, 2928, 0),
            Location(3392, 2920, 0),
            Location(3397, 2916, 0),
            Location(3401, 2916, 0)
        ),

        SOPHANEM(
            2298,
            Location.create(3285, 2813, 0),
            "Sophanem",
            Location.create(3351, 2934, 0),
            Location.create(3351, 2928, 0),
            Location.create(3351, 2919, 0),
            Location.create(3346, 2902, 0),
            Location.create(3339, 2884, 0),
            Location.create(3328, 2877, 0),
            Location.create(3328, 2862, 0),
            Location.create(3328, 2845, 0),
            Location.create(3318, 2838, 0),
            Location.create(3307, 2828, 0),
            Location.create(3292, 2817, 0),
            Location.create(3285, 2818, 0),
            Location.create(3285, 2813, 0)
        ),
        SOUTH_POLLNIVNEACH(
            3020,
            Location.create(3351, 2942, 0),
            "South Pollnivneach"
        );

        val locData: Array<Location?> = locData as Array<Location?>

        fun travel(current: RugDestination?, player: Player) {
            player.lock()
            setVarp(player, 499, 0)
            player.impactHandler.disabledTicks = ticks + 200
            player.interfaceManager.removeTabs(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13)
            player.equipment.replace(Item(Items.MAGIC_CARPET_5614), EquipmentContainer.SLOT_WEAPON)
            player.packetDispatch.sendInterfaceConfig(548, 69, true)
            playAudio(player, Sounds.CARPET_RISE_1196)
            playJingle(player, 132)
            pause(player)
            registerLogoutListener(player, "magic-carpet") { pl: Player? ->
                removeItem(pl!!, Items.MAGIC_CARPET_5614, Container.EQUIPMENT)
                Unit
            }

            Pulser.submit(object : Pulse(1, player) {
                var count: Int = 0
                var index: Int = 0
                var locs = locData as Array<Location?>

                override fun pulse(): Boolean {
                    when (++count) {
                        1 -> {
                            if (this@RugDestination == SHANTAY_PASS || this@RugDestination == SOUTH_POLLNIVNEACH) {
                                val temp = arrayOfNulls<Location>(
                                    current!!.locData.size + 1
                                )
                                var counter = 0
                                var i = current.locData.size - 1
                                while (i >= 0) {
                                    temp[counter++] = current.locData[i]
                                    i--
                                }
                                temp[temp.size - 1] = this@RugDestination.location
                                locs = temp
                            }
                            player.faceLocation(current!!.location)
                        }

                        2 -> AgilityHandler.walk(player, -1, player.location, current!!.location, null, 0.0, null)
                        3 -> player.faceLocation(location)
                        4 -> setVarp(player, 499, 1)
                        200 -> {}
                        901 -> {
                            player.equipment.replace(null, EquipmentContainer.SLOT_WEAPON)
                            player.interfaceManager.restoreTabs()
                            player.packetDispatch.sendInterfaceConfig(548, 69, false)
                            player.impactHandler.disabledTicks = 0
                            player.unlock()
                            player.animate(Animation(331))
                            setVarp(player, 499, 0)
                            playAudio(player, Sounds.CARPET_DESCEND_1195)
                            clearLogoutListener(player, "magic-carpet")
                            unpause(player)
                        }

                        902 -> {
                            player.moveStep()
                            return true
                        }

                        else -> {
                            if (index > locs.size - 1) {
                                count = 900
                            }
                            if (index == 0 || player.location == locs[index - 1]) {
                                AgilityHandler.walk(player, -1, player.location, locs[index++], null, 0.0, null, true)
                            }
                            return false
                        }
                    }
                    return false
                }

                override fun stop() {
                    super.stop()
                    player.unlock()
                }
            })
        }

        fun hasRequirements(player: Player?): Boolean {
            return true
        }

        companion object {

            fun forId(id: Int): RugDestination? {
                for (dest in values()) {
                    if (dest.npc == id) {
                        return dest
                    }
                }
                return null
            }
        }
    }

    companion object {

        private val IDS = intArrayOf(
            NPCs.RUG_MERCHANT_2291,
            NPCs.RUG_MERCHANT_2292,
            NPCs.RUG_MERCHANT_2293,
            NPCs.RUG_MERCHANT_2294,
            NPCs.RUG_MERCHANT_2296,
            NPCs.RUG_MERCHANT_2298,
            NPCs.RUG_MERCHANT_3020
        )

        private val FLOATING_ANIMATION = Animation(330)
        private val LANDING_ANIMATION = Animation(331)
        private val GETTING_ON_CARPET = Animation(654)

        fun getDestination(npcId: Int): Array<RugDestination> {
            return when (npcId) {
                2291 -> arrayOf(RugDestination.UZER, RugDestination.BEDABIN_CAMP, RugDestination.NORTH_POLLNIVNEACH)
                2292 -> arrayOf(RugDestination.SHANTAY_PASS)
                2294 -> arrayOf(RugDestination.SHANTAY_PASS)
                2293 -> arrayOf(RugDestination.SHANTAY_PASS)
                3020 -> arrayOf(RugDestination.NARDAH, RugDestination.SOPHANEM)
                else -> arrayOf(RugDestination.SOUTH_POLLNIVNEACH)
            }
        }
    }
}
