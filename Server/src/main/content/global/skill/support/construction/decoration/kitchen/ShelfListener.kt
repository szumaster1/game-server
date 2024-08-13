package content.global.skill.support.construction.decoration.kitchen

import core.api.addItem
import core.api.consts.Items
import core.api.freeSlots
import core.api.sendMessage
import core.game.dialogue.Dialogue
import core.game.dialogue.Topic
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player

/**
 * Shelf listener.
 */
class ShelfListener : InteractionListener {


    private val shelfIds = intArrayOf(13545, 13546, 13547, 13548, 13549, 13550, 13551)
    override fun defineListeners() {
        on(shelfIds, IntType.SCENERY, "search") { player, node ->
            player.dialogueInterpreter.open(778341, node.id)
            return@on true
        }
    }

    /**
     * Shelf dialogue.
     */
    inner class ShelfDialogue(player: Player? = null) : Dialogue(player) {

        override fun newInstance(player: Player): Dialogue {
            return ShelfDialogue(player)
        }

        override fun open(vararg args: Any): Boolean {
            val id = args[0] as Int
            when (id) {
                13545 -> showTopics(
                    Topic("Kettle", 1, true),
                    Topic("Teapot", 1, true),
                    Topic("Clay cup", 1, true)
                )

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
            when (stage) {
                1 -> when (buttonId) {
                    1 -> {
                        end()
                        addItem(player, Items.KETTLE_7688, 1)
                    }

                    2 -> {
                        end()
                        addItem(player, Items.TEAPOT_7702, 1)
                    }

                    3 -> {
                        end()
                        addItem(player, Items.EMPTY_CUP_7728, 1)
                    }

                    4 -> {
                        end()
                        addItem(player, Items.BEER_GLASS_1919, 1)
                    }

                    5 -> {
                        end()
                        addItem(player, Items.CAKE_TIN_1887, 1)
                    }
                }

                2 -> when (buttonId) {
                    1 -> {
                        end()
                        addItem(player, Items.KETTLE_7688, 1)
                    }

                    2 -> {
                        end()
                        addItem(player, Items.TEAPOT_7702, 1)
                    }

                    3 -> {
                        end()
                        addItem(player, Items.EMPTY_CUP_7728, 1)
                    }

                    4 -> {
                        end()
                        addItem(player, Items.BEER_GLASS_1919, 1)
                    }

                    5 -> {
                        end()
                        addItem(player, Items.BOWL_1923, 1)
                    }
                }

                3 -> when (buttonId) {
                    1 -> {
                        end()
                        addItem(player, Items.KETTLE_7688, 1)
                    }

                    2 -> {
                        end()
                        addItem(player, Items.TEAPOT_7702, 1)
                    }

                    3 -> {
                        end()
                        addItem(player, Items.PORCELAIN_CUP_4244, 1)
                    }

                    4 -> {
                        end()
                        addItem(player, Items.BEER_GLASS_1919, 1)
                    }

                    5 -> showTopics(
                        Topic("Bowl", 4, true),
                        Topic("Cake tin", 4, true),
                    )
                }

                4 -> {
                    when (buttonId) {
                        1 -> {
                            end()
                            addItem(player, Items.BOWL_1923, 1)
                        }

                        2 -> {
                            end()
                            addItem(player, Items.CAKE_TIN_1887, 1)
                        }
                    }
                    when (buttonId) {
                        1 -> {
                            end()
                            addItem(player, Items.KETTLE_7688, 1)
                        }

                        2 -> {
                            end()
                            addItem(player, Items.TEAPOT_7702, 1)
                        }

                        3 -> {
                            end()
                            addItem(player, Items.PORCELAIN_CUP_7735, 1)
                        }

                        4 -> {
                            end()
                            addItem(player, Items.BEER_GLASS_1919, 1)
                        }

                        5 -> showTopics(
                            Topic("Bowl", 6, true),
                            Topic("Pie dish", 6, true),
                            Topic("Empty pot", 6, true),
                        )
                    }
                }

                5 -> when (buttonId) {
                    1 -> {
                        end()
                        addItem(player, Items.KETTLE_7688, 1)
                    }

                    2 -> {
                        end()
                        addItem(player, Items.TEAPOT_7702, 1)
                    }

                    3 -> {
                        end()
                        addItem(player, Items.PORCELAIN_CUP_7735, 1)
                    }

                    4 -> {
                        end()
                        addItem(player, Items.BEER_GLASS_1919, 1)
                    }

                    5 -> showTopics(
                        Topic("Bowl", 6, true),
                        Topic("Pie dish", 6, true),
                        Topic("Empty pot", 6, true),
                    )
                }

                6 -> when (buttonId) {
                    1 -> {
                        end()
                        addItem(player, Items.BOWL_1923, 1)
                    }

                    2 -> {
                        end()
                        addItem(player, Items.PIE_DISH_2313, 1)
                    }

                    3 -> {
                        end()
                        addItem(player, Items.EMPTY_POT_1931, 1)
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