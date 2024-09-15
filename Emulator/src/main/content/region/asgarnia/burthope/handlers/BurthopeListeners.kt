package content.region.asgarnia.burthope.handlers

import core.api.*
import cfg.consts.NPCs
import cfg.consts.Scenery
import core.game.dialogue.FacialExpression
import core.game.global.action.ClimbActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.skill.Skills
import core.game.world.map.Location

/**
 * Represents the Burthope interaction listener.
 */
class BurthopeListeners : InteractionListener {

    companion object {
        private const val BENEDICT = NPCs.EMERALD_BENEDICT_2271
        private const val MARTIN = NPCs.MARTIN_THWAIT_2270
        private const val STAIRS_1 = Scenery.STAIRS_4624
        private const val STAIRS_2 = Scenery.STAIRS_4627
        private val ARCHERS = intArrayOf(NPCs.ARCHER_1073, NPCs.ARCHER_1074)
        private val GUARDS = intArrayOf(NPCs.GUARD_1076, NPCs.GUARD_1077)
        private val SERGEANTS = intArrayOf(NPCs.SERGEANT_1061, NPCs.SERGEANT_1062)
        private val SOLDIERS_1 = intArrayOf(NPCs.SOLDIER_1063, NPCs.SOLDIER_1064)
        private val SOLDIERS_2 = intArrayOf(NPCs.SOLDIER_1066, NPCs.SOLDIER_1067, NPCs.SOLDIER_1068)
        private val THIEVING_GUILD_PASSAGE = intArrayOf(Scenery.TRAPDOOR_7257, Scenery.PASSAGEWAY_7258)
    }

    override fun defineListeners() {

        /*
         * Handle entering through passage.
         */

        on(THIEVING_GUILD_PASSAGE, IntType.SCENERY, "enter") { player, node ->
            if (node.id == 7257) {
                teleport(player, Location(3061, 4985, 1))
            } else {
                teleport(player, Location(2906, 3537, 0))
            }
            return@on true
        }

        /*
         * Handle the action of climbing down the stairs.
         */

        on(STAIRS_1, IntType.SCENERY, "climb-down") { player, _ ->
            ClimbActionHandler.climb(player, null, Location(2205, 4934, 1))
            return@on true
        }

        /*
         * Handle the action of climbing up the stairs.
         */

        on(STAIRS_2, IntType.SCENERY, "climb-up") { player, _ ->
            ClimbActionHandler.climb(player, null, Location(2899, 3565, 0))
            return@on true
        }

        /*
         * Handle opening the bank account for the player.
         */

        on(BENEDICT, IntType.NPC, "bank") { player, _ ->
            openBankAccount(player)
            return@on true
        }

        /*
         * Handle opening the Grand Exchange collection box.
         */

        on(BENEDICT, IntType.NPC, "collect") { player, _ ->
            openGrandExchangeCollectionBox(player)
            return@on true
        }

        /*
         * Handle opening the Lost and Found shop.
         */

        on(MARTIN, IntType.NPC, "trade"){ player, _ ->
            if (getStatLevel(player, Skills.THIEVING) < 50 || getStatLevel(player, Skills.AGILITY) < 50) {
                sendNPCDialogue(player, NPCs.MARTIN_THWAIT_2270, "Sorry, mate. Train up your ${if (getStatLevel(player, Skills.THIEVING) < 50 && getStatLevel(player, Skills.AGILITY) < 50) "Thieving and Agility" else if(getStatLevel(player, Skills.THIEVING) < 50) "Thieving" else "Agility"} skill to at least 50 and I might be able to help you out.", FacialExpression.HALF_GUILTY)
            } else {
                openNpcShop(player, NPCs.MARTIN_THWAIT_2270)
            }
            return@on true
        }

        /*
         * Handling Burthope soldiers talk-to interaction.
         */

        on(SERGEANTS, IntType.NPC, "talk-to") { player, _ ->
            sendDialogue(player, "The Sergeant is busy training the soldiers.")
            return@on true
        }

        /*
         * Handling Burthope soldiers talk-to interaction.
         */

        on(SOLDIERS_1, IntType.NPC, "talk-to") { player, _ ->
            sendDialogue(player, "The soldier is busy training.")
            return@on true
        }

        /*
         * Handling Burthope soldiers talk-to interaction.
         */

        on(SOLDIERS_2, IntType.NPC, "talk-to") { player, _ ->
            sendDialogue(player, "The soldier is busy eating.")
            return@on true
        }

        /*
         * Handling Burthope archers talk-to interaction.
         */

        on(ARCHERS, IntType.NPC, "talk-to") { player, _ ->
            sendDialogue(player, "The archer won't talk whilst on duty.")
            return@on true
        }

        /*
         * Handling Burthope guards talk-to interaction.
         */

        on(GUARDS, IntType.NPC, "talk-to") { player, _ ->
            sendDialogue(player, "The guard won't talk whilst on duty.")
            return@on true
        }
    }
}
