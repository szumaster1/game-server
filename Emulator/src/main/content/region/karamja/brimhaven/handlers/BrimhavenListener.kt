package content.region.karamja.brimhaven.handlers

import content.region.karamja.brimhaven.dialogue.CapnIzzyDialogue
import content.region.karamja.brimhaven.dialogue.PirateJackieDialogue
import core.api.*
import org.rs.consts.Components
import org.rs.consts.Items
import org.rs.consts.NPCs
import org.rs.consts.Scenery
import core.game.dialogue.FacialExpression
import core.game.global.action.ClimbActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.combat.ImpactHandler
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.tools.RandomFunction
import kotlin.math.ceil

class BrimhavenListener : InteractionListener {

    override fun defineListeners() {

        /*
         * Handle climbs up the Agility Arena exit ladder.
         */

        on(AGILITY_ARENA_EXIT_LADDER, IntType.SCENERY, "climb-up") { player, _ ->
            ClimbActionHandler.climb(player, ClimbActionHandler.CLIMB_UP, AGILITY_ARENA_HUT)
            return@on true
        }

        /*
         * Handle climbs down the Agility Arena entrance ladder.
         */

        on(AGILITY_ARENA_ENTRANCE_LADDER, IntType.SCENERY, "climb-down") { player, _ ->
            if (!getAttribute(player, "capn_izzy", false)) {
                openDialogue(player, CapnIzzyDialogue(1))
                return@on true
            } else {
                ClimbActionHandler.climb(player, ClimbActionHandler.CLIMB_DOWN, AGILITY_ARENA)
                removeAttribute(player, "capn_izzy")
            }
            return@on true
        }

        /*
         * Handle talks to Cap'n Izzy No-Beard without a beard.
         */

        on(NPCs.CAPN_IZZY_NO_BEARD_437, IntType.NPC, "talk-to") { player, node ->
            openDialogue(player, CapnIzzyDialogue(0), node)
            return@on true
        }

        /*
         * Handle pays Cap'n Izzy No-Beard without a beard.
         */

        on(NPCs.CAPN_IZZY_NO_BEARD_437, IntType.NPC, "pay") { player, node ->
            openDialogue(player, CapnIzzyDialogue(2), node)
            return@on true
        }

        /*
         * Handle talks to Pirate Jackie the Fruit.
         */

        on(NPCs.PIRATE_JACKIE_THE_FRUIT_1055, IntType.NPC, "talk-to") { player, node ->
            openDialogue(player, PirateJackieDialogue(), node)
            return@on true
        }

        /*
         * Handle trades with Pirate Jackie the Fruit.
         */

        on(NPCs.PIRATE_JACKIE_THE_FRUIT_1055, IntType.NPC, "trade") { player, _ ->
            openInterface(player, TICKET_EXCHANGE_IFACE)
            return@on true
        }

        /*
         * Handle tries to open the rear door of the restaurant.
         */

        on(RESTAURANT_REAR_DOOR, IntType.SCENERY, "open") { player, _ ->
            sendMessage(player, "You try and open the door...")
            sendMessage(player, "The door is locked tight, I can't open it.")
            return@on true
        }

        /*
         * Handle tries to fish at the Karambwan fishing spot.
         */

        on(KARAMBWAN_FISHING_SPOT, IntType.NPC, "fish") { player, _ ->
            sendNPCDialogue(player, NPCs.LUBUFU_1171, "Keep off my fishing spot, whippersnapper!", FacialExpression.FURIOUS)
            return@on true
        }

        /*
         * Success on open locked diary.
         */

        fun success(player: Player, skill: Int): Boolean {
            val level = player.getSkills().getLevel(skill).toDouble()
            val req = 40.0
            val successChance = ceil((level * 50 - req) / req / 3 * 4)
            val roll = RandomFunction.random(99)
            return successChance >= roll
        }

        on(Items.LOCKED_DIARY_11761, IntType.ITEM, "unlock") { player, _ ->
            val success: Boolean = success(player, Skills.THIEVING)
            if (removeItem(player, Item(Items.LOCKED_DIARY_11761, 1))) {
                if (!success) {
                    sendMessage(player, "You fail to open the diary.")
                    player.impactHandler.manualHit(
                        player,
                        (getDynLevel(player, Skills.HITPOINTS) * 0.50).toInt(),
                        ImpactHandler.HitsplatType.NORMAL
                    )
                } else {
                    sendMessage(player, "You successfully opened the diary.")
                    addItemOrDrop(player, Items.UNLOCKED_DIARY_11762, 1)
                }
            }
            return@on true
        }
    }

    companion object {
        private val AGILITY_ARENA = location(2805, 9589, 3)
        private val AGILITY_ARENA_HUT = location(2809, 3193, 0)
        private const val AGILITY_ARENA_EXIT_LADDER = Scenery.LADDER_3618
        private const val AGILITY_ARENA_ENTRANCE_LADDER = Scenery.LADDER_3617
        private const val TICKET_EXCHANGE_IFACE = Components.AGILITYARENA_TRADE_6
        private const val RESTAURANT_REAR_DOOR = Scenery.DOOR_1591
        private const val KARAMBWAN_FISHING_SPOT = NPCs.FISHING_SPOT_1178
    }
}