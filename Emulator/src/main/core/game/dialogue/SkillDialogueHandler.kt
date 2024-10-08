package core.game.dialogue

import core.api.sendItemZoomOnInterface
import core.api.sendString
import core.api.setInterfaceSprite
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.net.packet.PacketRepository
import core.net.packet.context.ChildPositionContext
import core.net.packet.outgoing.RepositionChild
import core.tools.StringUtils

/**
 * Handles the display and functionality of skill-related dialogues for a player.
 *
 * @param player The player associated with this skill dialogue handler.
 * @param type The type of skill dialogue being handled.
 * @param data Additional data related to the skill dialogue.
 */
open class SkillDialogueHandler(val player: Player, val type: SkillDialogue?, vararg data: Any) {
    /**
     * The data passed to the skill dialogue handler.
     */
    val data: Array<Any>

    /**
     * Opens the skill dialogue for the player, initiating the dialogue interpreter.
     */
    fun open() {
        player.dialogueInterpreter.open(SKILL_DIALOGUE, this)
    }

    /**
     * Displays the skill dialogue interface to the player.
     */
    fun display() {
        if (type == null) {
            player.debug("Error! Type is null.")
            return
        }
        type.display(player, this)
    }

    /**
     * Handles the creation process for the skill dialogue.
     *
     * @param amount The amount to create.
     * @param index The index of the item.
     */
    open fun create(amount: Int, index: Int) {}

    /**
     * Retrieves the total amount of a specified item in the player's inventory.
     *
     * @param index The index to retrieve items from.
     * @return The amount of items in the player's inventory.
     */
    open fun getAll(index: Int): Int {
        return player.inventory.getAmount(data[0] as Item)
    }

    /**
     * Retrieves the formatted name of the specified item, removing specific keywords.
     *
     * @param item The item whose name is to be retrieved.
     * @return The formatted name of the item.
     */
    protected open fun getName(item: Item): String {
        return StringUtils.formatDisplayName(item.name.replace("Unfired", ""))
    }

    /**
     * Represents the different types of skill dialogues, each with its own unique interface and behavior.
     *
     * @param interfaceId The ID of the interface.
     * @param baseButton The base button associated with the dialogue.
     * @param length The number of options available in the dialogue.
     */
    enum class SkillDialogue(val interfaceId: Int, private val baseButton: Int, private val length: Int) {
        /**
         * A dialogue with a single option.
         */
        ONE_OPTION(309, 5, 1) {
            override fun display(player: Player, handler: SkillDialogueHandler) {
                val item = handler.data[0] as Item
                setInterfaceSprite(player, 309, 7, 8, 12)
                sendString(player, "<br><br><br><br>${item.name}", 309, 6)
                sendItemZoomOnInterface(player, 309, 2, item.id, 160)
                setInterfaceSprite(player, 309, 0, 12, 15)
                setInterfaceSprite(player, 309, 1, 431, 15)
                setInterfaceSprite(player, 309, 2, 210, 32)
                setInterfaceSprite(player, 309, 3, 60, 35)
                setInterfaceSprite(player, 309, 4, 60, 35)
                setInterfaceSprite(player, 309, 5, 60, 35)
                setInterfaceSprite(player, 309, 6, 60, 35)
            }

            override fun getAmount(handler: SkillDialogueHandler, buttonId: Int): Int {
                return when (buttonId) {
                    5 -> 1
                    4 -> 5
                    3 -> -1 //-1 is used to prompt an "enter an amount"
                    else -> handler.getAll(getIndex(handler, buttonId))
                }
            }
        },

        /**
         * A dialogue for creating one set of items.
         */
        MAKE_SET_ONE_OPTION(582, 4, 1) {
            override fun display(player: Player, handler: SkillDialogueHandler) {
                val item = handler.data[0] as Item
                sendItemZoomOnInterface(player, 582, 2, item.id, 160)
                sendString(player, "<br><br><br><br>${item.name}", 582, 5)
                setInterfaceSprite(player, 582, 0, 12, 15)
                setInterfaceSprite(player, 582, 1, 431, 15)
                setInterfaceSprite(player, 582, 6, 6, 16)
                setInterfaceSprite(player, 582, 2, 207, 23)
                setInterfaceSprite(player, 582, 3, 60, 35)
                setInterfaceSprite(player, 582, 4, 60, 35)
                setInterfaceSprite(player, 582, 5, 60, 35)
            }

            override fun getAmount(handler: SkillDialogueHandler, buttonId: Int): Int {
                return when (buttonId) {
                    // "Continue" Option
                    5 -> 1
                    // "Make 1 set" Option
                    4 -> 1
                    // "Make 5 sets" Option
                    3 -> 5
                    // "Make 10 sets" Option
                    2 -> 10
                    else -> 10
                }
            }
        },

        /**
         * A dialogue with two options.
         */
        TWO_OPTION(303, 7, 2) {
            override fun display(player: Player, handler: SkillDialogueHandler) {
                var item: Item
                player.interfaceManager.openChatbox(306)
                for (i in handler.data.indices) {
                    item = handler.data[i] as Item
                    setInterfaceSprite(player, 303, 0, 12, 15)
                    setInterfaceSprite(player, 303, 1, 431, 15)
                    player.packetDispatch.sendString("<br><br><br><br>" + handler.getName(item), 303, 7 + i)
                    player.packetDispatch.sendItemZoomOnInterface(item.id, 170, 303, 2 + i)
                }
            }

            override fun getIndex(handler: SkillDialogueHandler?, buttonId: Int): Int {
                when (buttonId) {
                    6, 5, 4, 3 -> return 0
                    10, 9, 8, 7 -> return 1
                }
                return 1
            }

            override fun getAmount(handler: SkillDialogueHandler, buttonId: Int): Int {
                when (buttonId) {
                    6, 10 -> return 1
                    5, 9 -> return 5
                    4, 8 -> return 10
                    3, 7 -> return -1
                }
                return 1
            }
        },

        /**
         * A dialogue with three options.
         */
        THREE_OPTION(304, 8, 3) {
            override fun display(player: Player, handler: SkillDialogueHandler) {
                var item: Item? = null
                for (i in 0..2) {
                    item = handler.data[i] as Item
                    setInterfaceSprite(player, 304, 0, 12, 13)
                    setInterfaceSprite(player, 304, 1, 431, 13)
                    player.packetDispatch.sendItemZoomOnInterface(item.id, 170, 304, 2 + i)
                    player.packetDispatch.sendString("<br><br><br><br>" + item.name, 304, 304 - 296 + i * 4)
                }
            }

            override fun getIndex(handler: SkillDialogueHandler?, buttonId: Int): Int {
                when (buttonId) {
                    7, 6, 5, 4 -> return 0
                    11, 10, 9, 8 -> return 1
                    15, 14, 13, 12 -> return 2
                }
                return 1
            }

            override fun getAmount(handler: SkillDialogueHandler, buttonId: Int): Int {
                when (buttonId) {
                    7, 11, 15 -> return 1
                    6, 10, 14 -> return 5
                    5, 9, 13 -> return 10
                    4, 8, 12 -> return -1
                }
                return 1
            }
        },

        /**
         * A dialogue with four options.
         */
        FOUR_OPTION(305, 9, 4) {
            override fun display(player: Player, handler: SkillDialogueHandler) {
                var item: Item? = null
                for (i in 0..3) {
                    item = handler.data[i] as Item
                    setInterfaceSprite(player, 305, 0, 12, 15)
                    setInterfaceSprite(player, 305, 1, 431, 15)
                    player.packetDispatch.sendItemZoomOnInterface(item.id, 160, 305, 2 + i)
                    sendString(player, "<br><br><br><br>" + item.name, 305, 305 - 296 + i * 4)
                }
            }

            override fun getIndex(handler: SkillDialogueHandler?, buttonId: Int): Int {
                when (buttonId) {
                    5, 8, 6, 7 -> return 0
                    9, 10, 11, 12 -> return 1
                    13, 14, 15, 16 -> return 2
                    17, 18, 19, 20 -> return 3
                }
                return 0
            }

            override fun getAmount(handler: SkillDialogueHandler, buttonId: Int): Int {
                when (buttonId) {
                    8, 12, 16, 20 -> return 1
                    7, 11, 15, 19 -> return 5
                    6, 10, 14, 18 -> return 10
                    5, 9, 13, 17 -> return -1
                }
                return 1
            }
        },

        /**
         * Five option.
         */
        FIVE_OPTION(306, 7, 5) {
            /**
             * Represents the position data.
             */
            private val positions = arrayOf(
                intArrayOf(10, 30), intArrayOf(117, 10), intArrayOf(217, 20), intArrayOf(317, 15), intArrayOf(408, 15)
            )

            override fun display(player: Player, handler: SkillDialogueHandler) {
                var item: Item
                player.interfaceManager.openChatbox(306)
                for (i in handler.data.indices) {
                    item = handler.data[i] as Item
                    sendString(player, "<br><br><br><br>" + handler.getName(item), 306, 10 + 4 * i)
                    player.packetDispatch.sendItemZoomOnInterface(item.id, 160, 306, 2 + i)
                    PacketRepository.send(
                        RepositionChild::class.java,
                        ChildPositionContext(player, 306, 2 + i, positions[i][0], positions[i][1])
                    )
                }
                setInterfaceSprite(player, 306, 0, 12, 15)
                setInterfaceSprite(player, 306, 1, 431, 15)
            }

            override fun getIndex(handler: SkillDialogueHandler?, buttonId: Int): Int {
                when (buttonId) {
                    9, 8, 7, 6 -> return 0
                    13, 12, 11, 10 -> return 1
                    17, 16, 15, 14 -> return 2
                    21, 20, 19, 18 -> return 3
                    25, 24, 23, 22 -> return 4
                }
                return 0
            }

            override fun getAmount(handler: SkillDialogueHandler, buttonId: Int): Int {
                when (buttonId) {
                    9, 13, 17, 21, 25 -> return 1
                    8, 12, 16, 20, 24 -> return 5
                    7, 11, 15, 19, 23 -> return 10
                    6, 10, 14, 18, 22 -> return -1
                }
                return 1
            }
        };

        /**
         * Displays the skill dialogue interface for the player using the provided handler.
         *
         * @param player The player for whom the interface is displayed.
         * @param handler The handler responsible for managing the skill dialogue.
         */
        open fun display(player: Player, handler: SkillDialogueHandler) {}

        /**
         * Retrieves the number of items to be created based on the button pressed.
         *
         * @param handler The handler managing the skill dialogue.
         * @param buttonId The ID of the button pressed.
         * @return The number of items to be created.
         */
        open fun getAmount(handler: SkillDialogueHandler, buttonId: Int): Int {
            for (k in 0..3) {
                for (i in 0 until length) {
                    val `val` = baseButton - k + 4 * i
                    if (`val` == buttonId) {
                        return if (k == 13) 1 else if (k == 8) 5 else if (k == 7) 10 else 6
                    }
                }
            }
            return -1
        }

        /**
         * Retrieves the index of the item in the data array based on the button pressed.
         *
         * @param handler The handler managing the skill dialogue.
         * @param buttonId The ID of the button pressed.
         * @return The index of the item in the data array.
         */
        open fun getIndex(handler: SkillDialogueHandler?, buttonId: Int): Int {
            var index = 0
            for (k in 0..3) {
                for (i in 1 until length) {
                    val `val` = baseButton + k + 4 * i
                    if (`val` == buttonId) {
                        return index + 1
                    } else if (`val` <= buttonId) {
                        index++
                    }
                }
                index = 0
            }
            return index
        }

        companion object {
            /**
             * Gets the type for the length.
             * @param length2 The length to compare.
             * @return The corresponding SkillDialogue type or null if not found.
             */
            fun forLength(length2: Int): SkillDialogue? {
                for (dial in values()) {
                    if (dial.length == length2) {
                        return dial
                    }
                }
                return null
            }
        }

    }

    companion object {
        /**
         * Represents the skill dialogue id.
         */
        const val SKILL_DIALOGUE = 3 shl 16
    }

    /**
     * Constructs a new `SkillDialogueHandler` `Object`.
     *
     * @param player The player interacting with the skill dialogue.
     * @param type The type of skill dialogue being used.
     * @param data The data associated with the skill dialogue.
     */
    init {
        this.data = data as Array<Any>
    }
}
