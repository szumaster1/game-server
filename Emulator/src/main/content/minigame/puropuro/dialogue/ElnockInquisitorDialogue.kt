package content.minigame.puropuro.dialogue

import content.global.skill.gathering.hunter.bnet.BNetTypes
import core.api.removeAttribute
import core.api.setAttribute
import core.api.setVarp
import core.game.component.Component
import core.game.component.ComponentDefinition
import core.game.component.ComponentPlugin
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.PluginManager.definePlugin
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Represents the Elnock Inquisitor dialogue.
 */
@Initializable
class ElnockInquisitorDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc("Ah, good day, it's you again. What can I do for you?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                if (!player.hasItem(SCROLL)) {
                    npc("Ah, I notice you don't own an impling collector's scroll.")
                    stage = 900
                }
                options("Can you remind me how to catch implings again?", "Can I trade some jarred implings please?", "Do you have some spare equipment I can use?")
                stage++
            }

            1 -> when (buttonId) {
                1 -> {
                    player("Can you remind me how to catch implings again?")
                    stage = 10
                }

                2 -> {
                    player("Can I trade some jarred implings please?")
                    stage = 20
                }

                3 -> {
                    player("Do you have some spare equipment I can use?")
                    stage = 30
                }
            }

            10 -> {
                npc("Certainly.")
                stage++
            }

            11 -> {
                npc("Firstly you will need a butterfly net in which to catch", "them and at least one special impling jar to store an", "impling.")
                stage++
            }

            12 -> {
                npc("You will also require some experience as a Hunter", "since these creatures are elusive. The more immature", "implings require less experience, but some of the rarer", "implings are extraordinarily hard to find and catch.")
                stage++
            }

            13 -> {
                npc("Once you have caught one, you may break the jar", "open and obtain the object the impling is carrying.", "Alternatively, you may exchange certain combinations of", "jars with me. I will return the jars to my clients. In")
                stage++
            }

            14 -> {
                npc("exchange I will be able to provide you with some", "equipment that may help you hunt butterflies more", "effectively.")
                stage++
            }

            15 -> {
                npc("also beware. Those imps walking around the maze do", "not like the fact that their kindred spirits are being", "captured and will attempt to steal any full jars you have", "on you, setting the implings free.")
                stage++
            }

            16 -> end()
            20 -> {
                end()
                openShop(player)
            }

            30 -> if (!player.getSavedData().activityData.isElnockSupplies) {
                player.getSavedData().activityData.isElnockSupplies = true
                player.inventory.add(Item(10010), player)
                player.inventory.add(Item(11262, 1), player)
                player.inventory.add(Item(11260, 6), player)
                npc("Here you go!")
                stage++
            } else {
                npc("Since I have already given you some equipment for free,", "I'll be willing to sell you some now.")
                stage = 500
            }

            31 -> {
                npc("If you are ready to start hunting implings, then enter", "the main part of the maze.")
                stage++
            }

            32 -> {
                npc("Just push through the wheat that surrounds the centre", "of the maze and get catching!")
                stage++
            }

            33 -> end()
            900 -> {
                options("Yes, please.", "No, thanks.")
                stage++
            }

            901 -> when (buttonId) {
                1 -> {
                    player.inventory.add(SCROLL, player)
                    interpreter.sendItemMessage(SCROLL, "Elnock gives you a scroll. If you check it whilst in the maze, you will see how many of each impling you have captured.")
                    stage++
                }

                2 -> end()
            }

            902 -> end()
            500 -> {
                player("*sigh*", "Alright, sell me some Impling jars.")
                stage++
            }

            501 -> {
                npc("I'll sell you five jars for 25,000gp, what do you say?")
                stage++
            }

            502 -> if (player.inventory.contains(995, 25000)) {
                player("Wow, that's expensive!", "Alright... here's some gold for a few jars.")
                stage++
            } else {
                player("Actually, I don't have that many coins at the moment.")
                stage = 902
            }

            503 -> {
                npc("Young one, that is the price you pay for", "forgetting to save some impling jars!")
                if (player.inventory.remove(Item(995, 25000))) {
                    player.inventory.add(Item(11260, 5))
                }
                stage++
            }

            504 -> end()
        }
        return true
    }

    override fun init() {
        super.init()
        definePlugin(ElnockExchangeInterfaceHandler())
    }

    override fun getIds(): IntArray {
        return intArrayOf(6070)
    }


    /**
     * Elnock exchange interface handler
     *
     * @constructor Elnock exchange interface handler
     */
    class ElnockExchangeInterfaceHandler : ComponentPlugin() {
        @Throws(Throwable::class)
        override fun newInstance(arg: Any?): Plugin<Any> {
            ComponentDefinition.forId(540)?.plugin = this
            return this
        }

        override fun handle(
            player: Player,
            component: Component,
            opcode: Int,
            button: Int,
            slot: Int,
            itemId: Int
        ): Boolean {
            var exchange = player.getAttribute<ElnockExchange>("exchange", null)
            if (button == 34) {
                setVarp(player, 1018, 0)
                if (exchange == null) {
                    player.sendMessage("Making a selection before confirming.")
                    return true
                }
                if (!exchange.hasItems(player)) {
                    player.sendMessage("You don't have the required implings in a jar to trade for this.")
                    return true
                }
                if (exchange === ElnockExchange.JAR_GENERATOR && player.hasItem(ElnockExchange.JAR_GENERATOR.reward)) {
                    player.sendMessage("You can't have more than one jar generator at a time.")
                    return true
                }
                if (!player.inventory.hasSpaceFor(exchange.reward)) {
                    player.sendMessage("You don't have enough inventory space.")
                    player.interfaceManager.close()
                    removeAttribute(player, "exchange")
                    return true
                }
                if (if (exchange === ElnockExchange.IMPLING_JAR) player.inventory.remove(ElnockExchange.getItem(player)) else player.inventory.remove(
                        *exchange.required
                    )
                ) {
                    player.interfaceManager.close()
                    removeAttribute(player, "exchange")
                    player.inventory.add(exchange.reward, player)
                }
                return true
            }
            exchange = ElnockExchange.forButton(button)
            if (exchange != null) {
                setAttribute(player, "exchange", exchange)
                setVarp(player, 1018, exchange.configValue)
            }
            return true
        }


        /**
         * Elnock exchange
         *
         * @param button Represents the button associated with the exchange.
         * @param configValue Holds the configuration value for the exchange.
         * @param sendItem Indicates the item that will be sent during the exchange.
         * @param reward Represents the reward item received from the exchange.
         * @param required A variable number of items that are required for the exchange.
         */
        enum class ElnockExchange(val button: Int, val configValue: Int, val sendItem: Int, val reward: Item, vararg required: Item) {
            /**
             * Imp Repellant.
             */
            IMP_REPELLANT(23, 444928, 11271, Item(11262), Item(11238, 3), Item(11240, 2), Item(11242)),

            /**
             * Magic Butterfly.
             */
            MAGIC_BUTTERFLY(26, 707072, 11268, Item(11259), Item(11242, 3), Item(11244, 2), Item(11246)),

            /**
             * Jar Generator.
             */
            JAR_GENERATOR(29, 969216, 11267, Item(11258), Item(11246, 3), Item(11248, 2), Item(11250)),

            /**
             * Impling Jar.
             */
            IMPLING_JAR(32, 1231360, 11269, Item(11260, 3)) {
                override fun hasItems(player: Player): Boolean {
                    for (node in BNetTypes.getImplings()) {
                        if (player.inventory.containsItem(node.reward)) {
                            return true
                        }
                    }
                    return false
                }
            };

            val required: Array<Item>

            init {
                this.required = required as Array<Item>
            }

            /**
             * Has items.
             */
            open fun hasItems(player: Player): Boolean {
                return player.inventory.containsItems(*required)
            }

            companion object {

                fun getItem(player: Player): Item? {
                    for (node in BNetTypes.getImplings()) {
                        if (player.inventory.containsItem(node.reward)) {
                            return node.reward
                        }
                    }
                    return null
                }

                fun forButton(button: Int): ElnockExchange? {
                    for (e in ElnockExchange.values()) {
                        if (e.button == button) {
                            return e
                        }
                    }
                    return null
                }
            }
        }
    }

    companion object {
        private val SCROLL = Item(11273)

        fun openShop(player: Player) {
            val vals = intArrayOf(22, 25, 28, 31)
            player.interfaceManager.open(Component(540))
            for (i in ElnockExchangeInterfaceHandler.ElnockExchange.values().indices) {
                val e = ElnockExchangeInterfaceHandler.ElnockExchange.values()[i]
                player.packetDispatch.sendItemZoomOnInterface(e.sendItem, 115, 540, vals[i])
            }
        }
    }
}