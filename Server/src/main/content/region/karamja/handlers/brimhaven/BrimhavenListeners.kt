package content.region.karamja.handlers.brimhaven

import content.region.karamja.dialogue.brimhaven.CapnIzzyDialogue
import content.region.karamja.dialogue.brimhaven.PirateJackieDialogue
import core.api.*
import core.api.consts.Components
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.consts.Scenery
import core.game.dialogue.FacialExpression
import core.game.global.action.ClimbActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.combat.ImpactHandler
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.repository.Repository
import core.tools.RandomFunction
import kotlin.math.ceil

class BrimhavenListeners : InteractionListener {

    companion object {
        private val AGILITY_ARENA = location(2805, 9589, 3)
        private val AGILITY_ARENA_HUT = location(2809, 3193, 0)
        private const val AGILITY_ARENA_EXIT_LADDER = Scenery.LADDER_3618
        private const val AGILITY_ARENA_ENTRANCE_LADDER = Scenery.LADDER_3617
        private const val TICKET_EXCHANGE_IFACE = Components.AGILITYARENA_TRADE_6
        private const val RESTAURANT_REAR_DOOR = Scenery.DOOR_1591
        private const val GANG_OFFICE_DOOR = Scenery.DOOR_2626
        private const val MANSION_DOOR = Scenery.DOOR_2627
        private const val RESTAURANT_KITCHEN_DOOR = Scenery.DOOR_2628
        private const val KARAMBWAN_FISHING_SPOT = NPCs.FISHING_SPOT_1178
    }

    override fun defineListeners() {

        on(AGILITY_ARENA_EXIT_LADDER, IntType.SCENERY, "climb-up") { player, _ ->
            ClimbActionHandler.climb(player, ClimbActionHandler.CLIMB_UP, AGILITY_ARENA_HUT)
            return@on true
        }

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

        on(NPCs.CAPN_IZZY_NO_BEARD_437, IntType.NPC, "talk-to") { player, node ->
            openDialogue(player, CapnIzzyDialogue(0), node)
            return@on true
        }

        on(NPCs.CAPN_IZZY_NO_BEARD_437, IntType.NPC, "pay") { player, node ->
            openDialogue(player, CapnIzzyDialogue(2), node)
            return@on true
        }

        on(NPCs.PIRATE_JACKIE_THE_FRUIT_1055, IntType.NPC, "talk-to") { player, node ->
            openDialogue(player, PirateJackieDialogue(), node)
            return@on true
        }

        on(NPCs.PIRATE_JACKIE_THE_FRUIT_1055, IntType.NPC, "trade") { player, _ ->
            openInterface(player, TICKET_EXCHANGE_IFACE)
            return@on true
        }

        on(RESTAURANT_REAR_DOOR, IntType.SCENERY, "open") { player, _ ->
            sendMessage(player, "You try and open the door...")
            sendMessage(player, "The door is locked tight, I can't open it.")
            return@on true
        }

        on(GANG_OFFICE_DOOR, IntType.SCENERY, "open") { player, _ ->
            openDialogue(player, 789, Repository.findNPC(789)!!)
            return@on true
        }

        on(MANSION_DOOR, IntType.SCENERY, "open") { player, _ ->
            openDialogue(player, 788, Repository.findNPC(788)!!, true)
            return@on true
        }

        on(RESTAURANT_KITCHEN_DOOR, IntType.SCENERY, "open") { player, _ ->
            sendMessage(player, "The door is securely closed.")
            return@on true
        }

        on(KARAMBWAN_FISHING_SPOT, IntType.NPC, "fish") { player, _ ->
            sendNPCDialogue(player, NPCs.LUBUFU_1171, "Keep off my fishing spot, whippersnapper!", FacialExpression.FURIOUS)
            return@on true
        }

        /*
         * Selecting the option 'Unlock diary' turns it into an Unlocked diary.
         * A player may fail to unlock the diary, and trigger a trap, resulting damage
         * to hitpoints. This damage is equal to half the current hitpoints available.
         * A higher Thieving level reduces the chances of this trap from triggering.
         * Reading it provides evidence that Sandy did kill Clarence. After giving the
         * diary to Zavistic Rarve, it will no longer be available;
         * even after searching Sandy's desk.
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
}
