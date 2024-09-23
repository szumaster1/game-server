package content.global.handlers.`object`

import org.rs.consts.Sounds
import core.api.freeSlots
import core.api.playAudio
import core.api.sendMessage
import core.api.setAttribute
import core.cache.def.impl.SceneryDefinition
import core.game.container.impl.EquipmentContainer
import core.game.event.ResourceProducedEvent
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.combat.ImpactHandler.HitsplatType
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.GameWorld.ticks
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.plugin.Plugin
import core.tools.RandomFunction

/**
 * Field picking plugin.
 */
@Initializable
class FieldPicking : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        for (p in PickingPlant.values().map { it.objectId }.toIntArray()) {
            SceneryDefinition.forId(p).handlers["option:pick"] = this
        }
        SceneryDefinition.forId(3511).handlers["option:take-cutting"] = this
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        if (player.getAttribute("delay:picking", -1) > ticks) {
            return true
        }
        val `object` = node as Scenery
        val plant = PickingPlant.forId(`object`.id) ?: return false
        if (!`object`.isActive) {
            return true
        }
        val reward = Item(if (plant == PickingPlant.POTATO && RandomFunction.random(10) == 0) 5318 else plant.reward)
        if (!player.inventory.hasSpaceFor(reward)) {
            sendMessage(player, "You don't have enough space in your inventory.")
            return true
        }
        if (freeSlots(player) == 0) {
            return true
        }
        player.lock(1)
        setAttribute(player, "delay:picking", ticks + (if (plant == PickingPlant.FLAX) 2 else 3))
        player.animate(ANIMATION)
        playAudio(player, Sounds.PICK_2581, 30)
        player.dispatch(ResourceProducedEvent(reward.id, reward.amount, node, -1))
        if (plant.name.startsWith("NETTLES", true) && (player.equipment[EquipmentContainer.SLOT_HANDS] == null || player.equipment[EquipmentContainer.SLOT_HANDS] != null && !player.equipment[EquipmentContainer.SLOT_HANDS].name.contains("glove", true))
        ) {
            player.packetDispatch.sendMessage("You have been stung by the nettles!")
            player.impactHandler.manualHit(player, 2, HitsplatType.POISON)
            return true
        }
        if (plant.respawn != -1 && plant != PickingPlant.FLAX) {
            `object`.isActive = false
        }
        Pulser.submit(object : Pulse(1, player) {
            override fun pulse(): Boolean {
                if (!player.inventory.add(reward)) {
                    sendMessage(player, "You don't have enough space in your inventory.")
                    return true
                }
                if (plant == PickingPlant.FLAX) {
                    handleFlaxPick(player, `object`, plant)
                    return true
                }
                val banana = plant.name.startsWith("BANANA", true)
                var full: Scenery? = null
                if (plant == PickingPlant.BANANA_TREE_4) {
                    full = `object`.transform(2073)
                    SceneryBuilder.replace(`object`, full)
                }
                val isBloomPlant =
                    plant == PickingPlant.FUNGI_ON_LOG || plant == PickingPlant.BUDDING_BRANCH || plant == PickingPlant.GOLDEN_PEAR_BUSH
                if (isBloomPlant) {
                    full = `object`.transform(`object`.id - 1)
                    SceneryBuilder.replace(`object`, full)
                }
                if (!isBloomPlant) {
                    SceneryBuilder.replace(
                        if (plant == PickingPlant.BANANA_TREE_4) full else `object`,
                        `object`.transform(if (banana) plant.respawn else 0),
                        if (banana) 300 else plant.respawn
                    )
                }
                if(plant.name.startsWith("MORT", true)){
                    sendMessage(player, "You pick a mushroom from the log.")
                } else if (!plant.name.startsWith("NETTLES", true)) {
                    sendMessage(player, "You pick a " + reward.name.lowercase() + ".")
                } else {
                    sendMessage(player, "You pick a handful of nettles.")
                }
                return true
            }
        })
        return true
    }

    private fun handleFlaxPick(player: Player, `object`: Scenery, plant: PickingPlant) {
        val charge = `object`.charge
        playAudio(player, Sounds.PICK_2581)
        player.packetDispatch.sendMessage("You pick some flax.")

        if (charge > 1000 + RandomFunction.random(2, 8)) {
            `object`.isActive = false
            `object`.charge = 1000
            SceneryBuilder.replace(`object`, `object`.transform(0), plant.respawn)
            return
        }
        `object`.charge = charge + 1
    }

    private enum class PickingPlant(val objectId: Int, val reward: Int, val respawn: Int) {
        /**
         * Potato
         *
         * @constructor Potato
         */
        POTATO(312, 1942, 30),

        /**
         * Wheat 0
         *
         * @constructor Wheat 0
         */
        WHEAT_0(313, 1947, 30),

        /**
         * Wheat 1
         *
         * @constructor Wheat 1
         */
        WHEAT_1(5583, 1947, 30),

        /**
         * Wheat 2
         *
         * @constructor Wheat 2
         */
        WHEAT_2(5584, 1947, 30),

        /**
         * Wheat 3
         *
         * @constructor Wheat 3
         */
        WHEAT_3(5585, 1947, 30),

        /**
         * Wheat 4
         *
         * @constructor Wheat 4
         */
        WHEAT_4(15506, 1947, 30),

        /**
         * Wheat 5
         *
         * @constructor Wheat 5
         */
        WHEAT_5(15507, 1947, 30),

        /**
         * Wheat 6
         *
         * @constructor Wheat 6
         */
        WHEAT_6(15508, 1947, 30),

        /**
         * Wheat 7
         *
         * @constructor Wheat 7
         */
        WHEAT_7(22300, 1947, 30),

        /**
         * Wheat 8
         *
         * @constructor Wheat 8
         */
        WHEAT_8(22473, 1947, 30),

        /**
         * Wheat 9
         *
         * @constructor Wheat 9
         */
        WHEAT_9(22474, 1947, 30),

        /**
         * Wheat 10
         *
         * @constructor Wheat 10
         */
        WHEAT_10(22475, 1947, 30),

        /**
         * Wheat 11
         *
         * @constructor Wheat 11
         */
        WHEAT_11(22476, 1947, 30),

        /**
         * Cabbage 0
         *
         * @constructor Cabbage 0
         */
        CABBAGE_0(1161, 1965, 30),

        /**
         * Cabbage 1
         *
         * @constructor Cabbage 1
         */
        CABBAGE_1(11494, 1967, 30),

        /**
         * Cabbage 2
         *
         * @constructor Cabbage 2
         */
        CABBAGE_2(22301, 1965, 30),

        /**
         * Nettles 0
         *
         * @constructor Nettles 0
         */
        NETTLES_0(1181, 4241, 30),

        /**
         * Nettles 1
         *
         * @constructor Nettles 1
         */
        NETTLES_1(5253, 4241, 30),

        /**
         * Nettles 2
         *
         * @constructor Nettles 2
         */
        NETTLES_2(5254, 4241, 30),

        /**
         * Nettles 3
         *
         * @constructor Nettles 3
         */
        NETTLES_3(5255, 4241, 30),

        /**
         * Nettles 4
         *
         * @constructor Nettles 4
         */
        NETTLES_4(5256, 4241, 30),

        /**
         * Nettles 5
         *
         * @constructor Nettles 5
         */
        NETTLES_5(5257, 4241, 30),

        /**
         * Nettles 6
         *
         * @constructor Nettles 6
         */
        NETTLES_6(5258, 4241, 30),

        /**
         * Pineapple Plant 0
         *
         * @constructor Pineapple Plant 0
         */
        PINEAPPLE_PLANT_0(1408, 2114, 30),

        /**
         * Pineapple Plant 1
         *
         * @constructor Pineapple Plant 1
         */
        PINEAPPLE_PLANT_1(1409, 2114, 30),

        /**
         * Pineapple Plant 2
         *
         * @constructor Pineapple Plant 2
         */
        PINEAPPLE_PLANT_2(1410, 2114, 30),

        /**
         * Pineapple Plant 3
         *
         * @constructor Pineapple Plant 3
         */
        PINEAPPLE_PLANT_3(1411, 2114, 30),

        /**
         * Pineapple Plant 4
         *
         * @constructor Pineapple Plant 4
         */
        PINEAPPLE_PLANT_4(1412, 2114, 30),

        /**
         * Pineapple Plant 5
         *
         * @constructor Pineapple Plant 5
         */
        PINEAPPLE_PLANT_5(1413, 2114, 30),

        /**
         * Pineapple Plant 6
         *
         * @constructor Pineapple Plant 6
         */
        PINEAPPLE_PLANT_6(4827, 2114, 30),

        /**
         * Banana Tree 0
         *
         * @constructor Banana Tree 0
         */
        BANANA_TREE_0(2073, 1963, 2074),

        /**
         * Banana Tree 1
         *
         * @constructor Banana Tree 1
         */
        BANANA_TREE_1(2074, 1963, 2075),

        /**
         * Banana Tree 2
         *
         * @constructor Banana Tree 2
         */
        BANANA_TREE_2(2075, 1963, 2076),

        /**
         * Banana Tree 3
         *
         * @constructor Banana Tree 3
         */
        BANANA_TREE_3(2076, 1963, 2077),

        /**
         * Banana Tree 4
         *
         * @constructor Banana Tree 4
         */
        BANANA_TREE_4(2077, 1963, 2078),

        /**
         * Banana Tree 5
         *
         * @constructor Banana Tree 5
         */
        BANANA_TREE_5(12606, 1963, 2079),

        /**
         * Banana Tree 6
         *
         * @constructor Banana Tree 6
         */
        BANANA_TREE_6(12607, 1963, 2080),

        /**
         * Flax
         *
         * @constructor Flax
         */
        FLAX(2646, 1779, 30),

        /**
         * Onion 0
         *
         * @constructor Onion 0
         */
        ONION_0(3366, 1957, 30),

        /**
         * Onion 1
         *
         * @constructor Onion 1
         */
        ONION_1(5538, 1957, 30),

        /**
         * Fungi On Log
         *
         * @constructor Fungi On Log
         */
        FUNGI_ON_LOG(3509, 2970, -1),

        /**
         * Budding Branch
         *
         * @constructor Budding Branch
         */
        BUDDING_BRANCH(3511, 2972, -1),

        /**
         * Golden Pear Bush
         *
         * @constructor Golden Pear Bush
         */
        GOLDEN_PEAR_BUSH(3513, 2974, -1),

        /**
         * Glowing Fungus 0
         *
         * @constructor Glowing Fungus 0
         */
        GLOWING_FUNGUS_0(4932, 4075, 30),

        /**
         * Glowing Fungus 1
         *
         * @constructor Glowing Fungus 1
         */
        GLOWING_FUNGUS_1(4933, 4075, 30),

        /**
         * Rare Flowers
         *
         * @constructor Rare Flowers
         */
        RARE_FLOWERS(5006, 2460, 30),

        /**
         * Black Mushrooms
         *
         * @constructor Black Mushrooms
         */
        BLACK_MUSHROOMS(6311, 4620, 30),

        /**
         * Kelp
         *
         * @constructor Kelp
         */
        KELP(12478, 7516, 30),

        /**
         * Red Banana Tree
         *
         * @constructor Red Banana Tree
         */
        RED_BANANA_TREE(12609, 7572, 30),

        /**
         * Bush
         *
         * @constructor Bush
         */
        BUSH(12615, 7573, 30),

        /**
         * Red Flowers
         *
         * @constructor Red Flowers
         */
        RED_FLOWERS(15846, 8938, 30),

        /**
         * Blue Flowers
         *
         * @constructor Blue Flowers
         */
        BLUE_FLOWERS(15872, 8936, 30),

        /**
         * Hardy Goutweed
         *
         * @constructor Hardy Goutweed
         */
        HARDY_GOUTWEED(18855, 3261, 30),

        /**
         * Herbs 0
         *
         * @constructor Herbs 0
         */
        HERBS_0(21668, 199, 30),

        /**
         * Herbs 1
         *
         * @constructor Herbs 1
         */
        HERBS_1(21669, 201, 30),

        /**
         * Herbs 2
         *
         * @constructor Herbs 2
         */
        HERBS_2(21670, 203, 30),

        /**
         * Herbs 3
         *
         * @constructor Herbs 3
         */
        HERBS_3(21671, 205, 30),

        /**
         * Fever Grass
         *
         * @constructor Fever Grass
         */
        FEVER_GRASS(29113, 12574, 30),

        /**
         * Lavender
         *
         * @constructor Lavender
         */
        LAVENDER(29114, 12572, 30),

        /**
         * Tansymum
         *
         * @constructor Tansymum
         */
        TANSYMUM(29115, 12576, 30),

        /**
         * Primweed
         *
         * @constructor Primweed
         */
        PRIMWEED(29116, 12588, 30),

        /**
         * Stinkbloom
         *
         * @constructor Stinkbloom
         */
        STINKBLOOM(29117, 12590, 30),

        /**
         * Trollweiss Flowers
         *
         * @constructor Trollweiss Flowers
         */
        TROLLWEISS_FLOWERS(37328, 4086, 30),

        /**
         * Hollow Log
         *
         * @constructor Hollow Log
         */
        HOLLOW_LOG(37830, 10968, 30);

        companion object {

            @JvmStatic
            fun forId(objectId: Int): PickingPlant? {
                for (plant in values()) if (plant.objectId == objectId) return plant
                return null
            }
        }
    }

    companion object {
        private val ANIMATION = Animation(827)
    }
}
