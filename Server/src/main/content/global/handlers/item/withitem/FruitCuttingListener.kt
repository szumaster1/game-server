package content.global.handlers.item.withitem

import core.api.*
import core.api.consts.Animations
import core.api.consts.Items
import core.api.consts.Sounds
import core.game.dialogue.DialogueFile
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item
import core.game.world.update.flag.context.Animation
import core.tools.START_DIALOGUE

/**
 * Fruit cutting listener
 *
 * @constructor Fruit cutting listener
 */
class FruitCuttingListener : InteractionListener {

    /**
     * Fruits
     *
     * @property fruit
     * @property chunk
     * @property slice
     * @constructor Fruits
     */
    internal enum class Fruits(val fruit: Int, val chunk: Int, val slice: Item) {
        /**
         * Pineapple
         *
         * @constructor Pineapple
         */
        PINEAPPLE(Items.PINEAPPLE_2114, Items.PINEAPPLE_CHUNKS_2116, Item(Items.PINEAPPLE_RING_2118, 4)),

        /**
         * Banana
         *
         * @constructor Banana
         */
        BANANA(Items.BANANA_1963, -1, Item(Items.SLICED_BANANA_3162)),

        /**
         * Lemon
         *
         * @constructor Lemon
         */
        LEMON(Items.LEMON_2102, Items.LEMON_CHUNKS_2104, Item(Items.LEMON_SLICES_2106)),

        /**
         * Lime
         *
         * @constructor Lime
         */
        LIME(Items.LIME_2120, Items.LIME_CHUNKS_2122, Item(Items.LIME_SLICES_2124)),

        /**
         * Orange
         *
         * @constructor Orange
         */
        ORANGE(Items.ORANGE_2108, Items.ORANGE_CHUNKS_2110, Item(Items.ORANGE_SLICES_2112));

        companion object {
            val productOfChunk = values().associate { it.fruit to it.chunk }
            val productOfSlice = values().associate { it.fruit to it.slice }
            val cutable = intArrayOf(PINEAPPLE.fruit, BANANA.fruit, LEMON.fruit, LIME.fruit, ORANGE.fruit)

            fun forChunkId(id: Int): Int {
                return productOfChunk[id]!!
            }

            fun forSliceId(id: Int): Item {
                return productOfSlice[id]!!
            }
        }
    }

    override fun defineListeners() {

        /*
         * Fruit slice interaction.
         */

        onUseWith(IntType.ITEM, Fruits.cutable, Items.KNIFE_946) { player, used, _ ->
            openDialogue(player, FruitCuttingDialogue(used.id, Fruits.forChunkId(used.id), Fruits.forSliceId(used.id)))
            return@onUseWith true
        }
    }

    /**
     * Fruit cutting dialogue
     *
     * @property fruit
     * @property chunk
     * @property slice
     * @constructor Fruit cutting dialogue
     */
    internal class FruitCuttingDialogue(val fruit: Int, val chunk: Int, val slice: Item) : DialogueFile() {

        private val anim = Animation(Animations.HUMAN_FRUIT_CUTTING_1192)

        override fun handle(componentID: Int, buttonID: Int) {
            when (stage) {
                START_DIALOGUE -> {
                    if (chunk == -1) {
                        if (removeItem(player!!, fruit) && addItem(player!!, slice.id)) {
                            animate(player!!, anim, true)
                            playAudio(player!!, Sounds.BANANA_SLICING_1261)
                            sendMessage(player!!, "You deftly chop the ${getItemName(fruit).lowercase()} into slices.")
                        }
                    } else {
                        setInterfaceText(player!!, "Would you like to...", 140, 4)
                        sendItemOnInterface(player!!, 140, 6, chunk, 1)
                        sendItemOnInterface(player!!, 140, 5, slice.id, 1)
                        setInterfaceText(player!!, "Slice the ${getItemName(fruit).lowercase()}", 140, 2)
                        setInterfaceText(player!!, "Dice the ${getItemName(fruit).lowercase()}", 140, 3)
                        openInterface(player!!, 140)
                        when (buttonID) {
                            1 -> {
                                closeInterface(player!!)
                                if (removeItem(player!!, fruit) && addItem(player!!, slice.id, slice.amount)) {
                                    animate(player!!, anim, true)
                                    sendMessage(player!!, "You cut the ${getItemName(fruit).lowercase()} into slices.")
                                }
                            }

                            2 -> {
                                closeInterface(player!!)
                                if (removeItem(player!!, fruit) && addItem(player!!, chunk)) {
                                    animate(player!!, anim, true)
                                    sendMessage(player!!, "You cut the ${getItemName(fruit).lowercase()} into chunks.")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
