package content.global.skill.combat.summoning

import cfg.consts.Sounds
import core.api.*
import core.cache.def.impl.CS2Mapping
import core.game.component.Component
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.map.Location
import core.game.world.map.RegionManager.getObject

/**
 * Represents a utility class of creating summoning nodes.
 * @author Vexia
 */
object SummoningCreator {
    /**
     * Represents the params used for the access mask on the pouch creating interface.
     */
    private val POUCH_PARAMS = arrayOf<Any>(
        "List<col=FF9040>",
        "Infuse-X<col=FF9040>",
        "Infuse-All<col=FF9040>",
        "Infuse-10<col=FF9040>",
        "Infuse-5<col=FF9040>",
        "Infuse<col=FF9040>",
        20,
        4,
        669 shl 16 or 15
    )
    /**
     * Represents the params used for the access mask on the scroll relating interface.
     */
    private val SCROLL_PARAMS = arrayOf<Any>(
        "Transform-X<col=ff9040>",
        "Transform-All<col=ff9040>",
        "Transform-10<col=ff9040>",
        "Transform-5<col=ff9040>",
        "Transform<col=ff9040>",
        20,
        4,
        673 shl 16 or 15
    )
    /**
     * Represents the summoning component.
     */
    private val SUMMONING_COMPONENT = Component(669)
    /**
     * Represents the scroll component.
     */
    private val SCROLL_COMPONENT = Component(673)

    /**
     * Method used to open the creation screen.
     *
     * @param player the player
     * @param pouch  the pouch
     */
    @JvmStatic
    fun open(player: Player, pouch: Boolean) {
        configure(player, pouch)
    }

    /**
     * Method used to configure a creation interface.
     *
     * @param player the player
     * @param pouch  the pouch
     */
    @JvmStatic
    fun configure(player: Player, pouch: Boolean) {
        player.interfaceManager.open(if (pouch) SUMMONING_COMPONENT else SCROLL_COMPONENT)
        player.packetDispatch.sendRunScript(
            if (pouch) 757 else 765,
            if (pouch) "Iiissssss" else "Iiisssss",
            *if (pouch) POUCH_PARAMS else SCROLL_PARAMS
        )
        player.packetDispatch.sendIfaceSettings(if (pouch) 190 else 126, 15, if (pouch) 669 else 673, 0, 78)
    }

    /**
     * Method used to create a summoning node.
     *
     * @param player the player.
     * @param amount the amount.
     * @param node the node.
     */
    @JvmStatic
    fun create(player: Player, amount: Int, node: Any?) {
        if (node == null) {
            return
        }
        player.pulseManager.run(CreatePulse(player, null, SummoningNode.parse(node), amount))
    }

    /**
     * Method used to list the items needed for a pouch.
     *
     * @param player the player
     * @param pouch  the pouch
     */
    @JvmStatic
    fun list(player: Player, pouch: SummoningPouch) {
        player.packetDispatch.sendMessage(CS2Mapping.forId(1186).map[pouch.pouchId] as String?)
    }

    /**
     * Represents the skill pulse used to create a summoning node.
     */
    class CreatePulse(player: Player?, node: Item?,
                      /**
                       * Represents the summoning node type.
                       */
                      private val type: SummoningNode,
                      /**
                       * Represents the amount to make.
                       */
                      private val amount: Int) :
        SkillPulse<Item?>(player, node) {
        /**
         * Represents the object.
         */
        private val objectsIDs = getObject(Location(2209, 5344, 0))

        override fun checkRequirements(): Boolean {
            closeInterface(player)
            if (getStatLevel(player, Skills.SUMMONING) < type.level) {
                sendMessage(player, "You need a Summoning level of at least " + type.level + " in order to do this.")
                return false
            }
            if (amount == 0) {
                sendMessage(player, "You don't have the required item(s) to make this.")
                return false
            }
            for (i in type.required) {
                if (!inInventory(player, i.id)) {
                    sendMessage(player, "You don't have the required item(s) to make this.")
                    return false
                }
            }
            return true
        }

        override fun animate() {
            lock(player, 3)
            animate(player, 9068)
        }

        override fun stop() {
            super.stop()
            animateScenery(player, objectsIDs!!, 8510, true)
        }

        override fun reward(): Boolean {
            if (delay == 1) {
                delay = 4
                animateScenery(player, objectsIDs!!, 8509, true)
                playAudio(player, Sounds.CRAFT_POUCH_4164)
                return false
            }
            animateScenery(player, objectsIDs!!, 8510, true)
            for (i in 0 until amount) {
                for (item in type.required) {
                    if (!anyInInventory(player, item.id)) {
                        return true
                    }
                }
                if (player.inventory.remove(*type.required)) {
                    val item = type.product
                    player.inventory.add(item)
                    rewardXP(player, Skills.SUMMONING, type.experience)
                }
            }
            return true
        }
    }

    /**
     * Represents a Summoning node type.
     *
     * @param base       the base
     * @param required   the required
     * @param product    the product
     * @param experience the experience
     * @param level      the level
     */
    class SummoningNode(
        /**
         * Gets the base.
         *
         * @return The base.
         */
        val base: Any,
        /**
         * Gets the required.
         *
         * @return The required.
         */
        val required: Array<Item>,
        /**
         * Gets the product.
         *
         * @return the product
         */
        val product: Item,
        /**
         * Gets the experience.
         *
         * @return The experience.
         */
        val experience: Double,
        /**
         * Gets the level.
         *
         * @return The level.
         */
        val level: Int
    ) {
        /**
         * Method used to check if the base is a pouch.
         *
         * @return the pouch.
         */
        val isPouch: Boolean
            get() = base is SummoningPouch

        companion object {
            /**
             * Method used to parse a summoning node.
             *
             * @param node the node
             * @return the summoning node
             * @throws IllegalArgumentException if the node is neither a SummoningPouch nor a SummoningScroll
             */
            fun parse(node: Any): SummoningNode {
                return when (node) {
                    is SummoningPouch -> {
                        val required = node.items
                        val product = Item(node.pouchId, 1)
                        val level = node.levelRequired
                        val experience = node.createExperience
                        SummoningNode(node, required, product, experience, level)
                    }

                    is SummoningScroll -> {
                        val required = createList(*(node.items))
                        val product = Item(node.itemId, 10)
                        val level = node.level
                        val experience = node.experience
                        SummoningNode(node, required, product, experience, level)
                    }

                    else -> throw IllegalArgumentException("Invalid node type: ${node::class.simpleName}")
                }
            }

            /**
             * Method used to create the list.
             * @param ids the ids.
             * @return the array of items.
             */
            private fun createList(vararg ids: Int): Array<Item> {
                val list = arrayOfNulls<Item>(ids.size)
                for (i in ids.indices) {
                    list[i] = Item(ids[i], 1)
                }
                return list.requireNoNulls()
            }
        }
    }
}
