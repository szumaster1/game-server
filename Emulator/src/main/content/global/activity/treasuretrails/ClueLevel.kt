package content.global.activity.treasuretrails

import cfg.consts.Items
import content.global.activity.treasuretrails.clue.ClueScrollPlugin
import core.api.IfaceSettingsBuilder
import core.api.addItemOrDrop
import core.game.component.Component
import core.game.container.access.InterfaceContainer
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.system.config.ClueRewardParser
import core.game.world.GameWorld
import core.tools.RandomFunction
import java.text.NumberFormat

/**
 * A clue scroll level.
 */
enum class ClueLevel(
    /**
     * The Casket id.
     */
    val casketId: Int,
    /**
     * Min steps clue level.
     */
    val minSteps: Int,
    /**
     * Max steps clue level.
     */
    val maxSteps: Int
) {
    /**
     * Easy clue level.
     */
    EASY(Items.CASKET_2714, 1, 5),

    /**
     * Medium clue level.
     */
    MEDIUM(Items.CASKET_2717, 1, 6),

    /**
     * Hard clue level.
     */
    HARD(Items.CASKET_2720, 1, 8),

    /**
     * Unknown clue level.
     */
    UNKNOWN(0, 0, 0);




    companion object {
        /**
         * Get the ClueLevel based on the casket item.
         */
        private fun getLevelForCasket(casket: Item): ClueLevel {
            return when (casket.id) {
                Items.CASKET_2714 -> EASY
                Items.CASKET_2717 -> MEDIUM
                Items.CASKET_2720 -> HARD
                else -> UNKNOWN
            }
        }

        /**
         * Open.
         *
         * @param player the player
         * @param casket the casket
         */
        fun open(player: Player, casket: Item?) {
            if (casket == null || !player.inventory.containsItem(casket)) {
                return
            }

            val playerTrails = TreasureTrailManager.getInstance(player)

            val trailCompleted = playerTrails.isCompleted
            val isDevMode = GameWorld.settings!!.isDevMode

            val clueLevel = getLevelForCasket(casket)

            if (trailCompleted || isDevMode) {
                val rewards = rollLoot(player, clueLevel)

                player.interfaceManager.open(Component(364))

                if (player.inventory.remove(casket)) {
                    var rewardValue = 0L

                    for (reward in rewards) {
                        addItemOrDrop(player, reward.id, reward.amount)
                        rewardValue += reward.value
                    }

                    playerTrails.incrementClues(clueLevel)
                    playerTrails.clearTrail()

                    player.sendMessage("Well done, you've completed the Treasure Trail!")
                    player.sendMessage(
                        getChatColor(clueLevel) +
                                "You have completed " +
                                playerTrails.getCompletedClues(clueLevel) +
                                " " +
                                clueLevel.name.lowercase() +
                                " clues."
                    )

                    player.sendMessage(
                        "<col=990000>Your clue is worth approximately $rewardValue gold coins!</col>"
                    )

                    val clueIfaceSettings = IfaceSettingsBuilder().enableAllOptions().build()
                    player.packetDispatch.sendIfaceSettings(clueIfaceSettings, 4, 364, 0, 6)
                    InterfaceContainer.generateItems(
                        player,
                        rewards.toTypedArray(),
                        arrayOf(""),
                        364, 4, 3, 3
                    )
                }
                return
            }

            val newClue = content.global.activity.treasuretrails.clue.ClueScrollPlugin.getClue(clueLevel)

            if (casket != null && player.inventory.remove(casket, casket.slot, true)) {
                player.inventory.replace(newClue, casket.slot)
            } else {
                player.inventory.add(newClue)
            }

            playerTrails.clueId = newClue!!.id
            player.dialogueInterpreter.sendItemMessage(newClue, "You've found another clue!")
        }

        /**
         * Roll loot list.
         *
         * @param player the player
         * @param level  the level
         * @return the list
         */
        fun rollLoot(player: Player, level: ClueLevel): List<Item> {
            val loot = ArrayList<Item>()
            var itemCount = RandomFunction.random(1, 6)

            if (level == HARD) {
                itemCount = Math.max(itemCount, RandomFunction.random(4, 6))
            }

            repeat(itemCount) {
                when (level) {
                    EASY -> loot.addAll(ClueRewardParser.easyTable.roll(player))
                    MEDIUM -> loot.addAll(ClueRewardParser.medTable.roll(player))
                    HARD -> loot.addAll(ClueRewardParser.hardTable.roll(player))
                    else -> {}
                }
            }

            if (level == HARD && RandomFunction.random(100) == 50) {
                loot.addAll(ClueRewardParser.rareTable.roll(player))
            }

            return loot
        }

        /**
         * Gets the Chat color to send on completed clues.
         *
         * @param level The clue level.
         * @return the chat color.
         */
        fun getChatColor(level: ClueLevel): String {
            return when (level) {
                HARD -> "<col=ff1a1a>"
                MEDIUM -> "<col=b38f00>"
                else -> "<col=00e673>"
            }
        }
    }

    /**
     * Gets the casket.
     *
     * @return the casket
     */
    fun getCasket(): Item {
        return Item(casketId)
    }

    /**
     * Gets the name of the clue level.
     *
     * @return the name.
     */
    fun getName(): String {
        return Companion.toString()
    }


}
