package content.region.kandarin.handlers.gutanoth

import core.api.*
import core.api.consts.Animations
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.system.task.Pulse
import core.game.world.GameWorld
import java.util.concurrent.TimeUnit

private const val CHEST = 2827

/**
 * Gutanoth chest interaction handler.
 */
class GutanothChestInteractionHandler : InteractionListener {

    override fun defineListeners() {

        on(CHEST, IntType.SCENERY, "open") { player, node ->
            val delay = getAttribute(player, "gutanoth-chest-delay", 0L)
            GameWorld.Pulser.submit(ChestPulse(player, System.currentTimeMillis() > delay, node as Scenery))
            return@on true
        }

    }

    /**
     * Represents a pulse related to a chest interaction.
     *
     * @property player The player interacting with the chest.
     * @property isLoot Indicates if the interaction is for looting.
     * @property chest The chest being interacted with.
     * @constructor Creates a ChestPulse instance with the specified player, interaction type, and chest.
     */
    class ChestPulse(val player: Player, val isLoot: Boolean, val chest: Scenery) : Pulse() {
        var ticks = 0
        override fun pulse(): Boolean {
            when (ticks++) {
                0 -> {
                    lock(player, 3)
                    animate(player, Animations.OPEN_CHEST_536)
                    SceneryBuilder.replace(chest, Scenery(2828, chest.location, chest.rotation), 5)
                }

                3 -> {
                    lootChest(player)
                    return true
                }
            }
            return false
        }

        /**
         * Loot chest.
         *
         * @param player The player who is looting the chest.
         */
        fun lootChest(player: Player) {
            if (isLoot) {
                setAttribute(
                    player,
                    "/save:gutanoth-chest-delay",
                    System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(15)
                )
            } else {
                sendMessage(player, "You open the chest and find nothing.")
                return
            }
            val reward = Rewards.values().random()
            player.sendChat(reward.message)
            when (reward.type) {
                Type.ITEM -> if (!player.inventory.add(Item(reward.id))) GroundItemManager.create(
                    Item(reward.id),
                    player.location
                )

                Type.NPC -> {
                    val npc = NPC(reward.id)
                    npc.location = player.location
                    npc.isAggressive = true
                    npc.isRespawn = false
                    npc.properties.combatPulse.attack(player)
                    npc.init()
                }
            }
        }

        /**
         * Rewards.
         *
         * @property id The ID of the reward.
         * @property type The type of the reward (ITEM or NPC).
         * @property message The message associated with the reward.
         * @constructor Creates a reward with the given properties.
         */
        enum class Rewards(val id: Int, val type: Type, val message: String) {
            /**
             * Bones.
             */
            BONES(Items.BONES_2530, Type.ITEM, "Oh! Some bones. Delightful."),

            /**
             * Emerald.
             */
            EMERALD(Items.EMERALD_1605, Type.ITEM, "Ooh! A lovely emerald!"),

            /**
             * Rotten Apple.
             */
            ROTTEN_APPLE(Items.ROTTEN_APPLE_1984, Type.ITEM, "Oh, joy, spoiled fruit! My favorite!"),

            /**
             * Chaos Dwarf.
             */
            CHAOS_DWARF(NPCs.CHAOS_DWARF_119, Type.NPC, "You've gotta be kidding me, a dwarf?!"),

            /**
             * Rat.
             */
            RAT(NPCs.RAT_47, Type.NPC, "Eek!"),

            /**
             * Scorpion.
             */
            SCORPION(NPCs.SCORPION_1477, Type.NPC, "Zoinks!"),

            /**
             * Spider.
             */
            SPIDER(NPCs.SPIDER_1004, Type.NPC, "Awh, a cute lil spidey!")
        }

        /**
         * Type.
         */
        enum class Type {
            ITEM,
            NPC
        }
    }
}
