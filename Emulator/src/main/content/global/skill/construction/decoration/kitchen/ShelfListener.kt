package content.global.skill.construction.decoration.kitchen

import core.api.addItem
import core.api.freeSlots
import core.api.sendMessage
import core.game.dialogue.Dialogue
import core.game.dialogue.Topic
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import org.rs.consts.Items

/**
 * Handle Shelf interactions.
 */
class ShelfListener : InteractionListener {

    private val SHELF_IDS = intArrayOf(13545, 13546, 13547, 13548, 13549, 13550, 13551)

    override fun defineListeners() {

        /*
         * Handle search option.
         */

        on(SHELF_IDS, IntType.SCENERY, "search") { player, node ->
            player.dialogueInterpreter.open(778341, node.id)
            return@on true
        }
    }

    /**
     * Represents the Shelf dialogue.
     */
    inner class ShelfDialogue(player: Player? = null) : Dialogue(player) {

        override fun newInstance(player: Player): Dialogue {
            return ShelfDialogue(player)
        }

        override fun open(vararg args: Any): Boolean {
            val id = args[0] as Int
            when (id) {
                13545 -> showTopics(Topic("Kettle", 1, true), Topic("Teapot", 1, true), Topic("Clay cup", 1, true))
                13546 -> showTopics(
                    Topic("Kettle", 1, true),
                    Topic("Teapot", 1, true),
                    Topic("Clay cup", 1, true),
                    Topic("Empty beer glass", 1, true)
                )

                13547 -> showTopics(
                    Topic("Kettle", 1, true),
                    Topic("Teapot", 1, true),
                    Topic("Clay cup", 1, true),
                    Topic("Empty beer glass", 1, true),
                    Topic("Cake tin", 1, true)
                )

                13548 -> showTopics(
                    Topic("Kettle", 2, true),
                    Topic("Teapot", 2, true),
                    Topic("Clay cup", 2, true),
                    Topic("Empty beer glass", 2, true),
                    Topic("Bowl", 2, true)
                )

                13549 -> showTopics(
                    Topic("Kettle", 3, true),
                    Topic("Teapot", 3, true),
                    Topic("Clay cup", 3, true),
                    Topic("Empty beer glass", 3, true),
                    Topic("More Options", 3, true)
                )

                13550, 13551 -> showTopics(
                    Topic("Kettle", 5, true),
                    Topic("Teapot", 5, true),
                    Topic("Porcelain cup", 5, true),
                    Topic("Empty beer glass", 5, true),
                    Topic("More Options", 5, true)
                )
            }
            return true
        }

        override fun handle(interfaceId: Int, buttonId: Int): Boolean {
            if (freeSlots(player) < 1) {
                end()
                sendMessage(player, "You need at least one free inventory space to take from the shelves.")
                return true
            }

            val itemMap = mapOf(
                1 to Items.KETTLE_7688,
                2 to Items.TEAPOT_7702,
                3 to Items.EMPTY_CUP_7728,
                4 to Items.BEER_GLASS_1919,
                5 to Items.CAKE_TIN_1887,
                6 to Items.BOWL_1923,
                7 to Items.PORCELAIN_CUP_4244,
                8 to Items.PIE_DISH_2313,
                9 to Items.EMPTY_POT_1931
            )

            when (stage) {
                1, 2, 3, 4, 5, 6 -> {
                    itemMap[buttonId]?.let {
                        end()
                        addItem(player, it, 1)
                    }
                }
            }
            return true
        }

        override fun getIds(): IntArray {
            return intArrayOf(778341)
        }
    }
}