package content.region.misthalin.wizardstower.handlers.rcguild.handlers

import org.rs.consts.Animations
import org.rs.consts.Components
import org.rs.consts.Graphics
import org.rs.consts.NPCs
import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.map.Location

class RunecraftGuildListener : InteractionListener {

    companion object {
        private const val RC_PORTAL = 38279
        private const val MAP_TABLE = 38315
        private val BOOKCASES = intArrayOf(38322, 38323, 38324)
        private val RC_HAT = intArrayOf(13626, 13625, 13621, 13620, 13616, 13615)
    }

    override fun defineListeners() {

        /*
         * Handle interaction with bookcases around guild.
         */

        on(BOOKCASES, IntType.SCENERY, "search") { player, _ ->
            sendMessage(player, "You search the books...")
            sendMessageWithDelay(player, "None of them look very interesting.", 1)
            return@on true
        }

        /*
         * Remove and apply goggles to the Runecraft hat.
         */

        on(RC_HAT, IntType.ITEM, "Goggles") { player, node ->
            val newHatId = when (node.id) {
                13626 -> 13625
                13625 -> 13626
                13621 -> 13620
                13620 -> 13621
                13616 -> 13615
                13615 -> 13616
                else -> return@on false
            }
            replaceSlot(player, node.asItem().slot, Item(newHatId))
            return@on true
        }

        /*
         * Handle exchange interaction with Elriss.
         */

        on(NPCs.WIZARD_ELRISS_8032, IntType.NPC, "Exchange") { player, _ ->
            openInterface(player, Components.RCGUILD_REWARDS_779)
            return@on true
        }

        /*
        * Handle opening rc map.
        */

        on(MAP_TABLE, IntType.SCENERY, "Study") { player, _ ->
            openInterface(player, Components.RCGUILD_MAP_780)
            return@on true
        }

        /*
         * Handle rc portal.
         */

        on(RC_PORTAL, IntType.SCENERY, "Enter") { player, _ ->
            when {
                getStatLevel(player, Skills.RUNECRAFTING) < 50 -> {
                    sendDialogue(player, "You need a Runecrafting level of 50 to enter the Runecrafting guild.")
                }

                !isQuestComplete(player, "Rune Mysteries") -> {
                    sendDialogue(player, "You need to complete Rune Mysteries to enter the Runecrafting guild.")
                }

                else -> {
                    val destination = if (player.viewport.region.regionId == 12337) {
                        Location.create(1696, 5461, 2)
                    } else {
                        Location.create(3106, 3160, 1)
                    }
                    visualize(player, Animations.RC_GUILD_TP_A_10180, Graphics.RC_GUILD_TP)
                    queueScript(player, 3, QueueStrength.SOFT) {
                        teleport(player, destination)
                        visualize(player, Animations.RC_GUILD_TP_B_10182, Graphics.RC_GUILD_TP)
                        face(player, destination)
                        return@queueScript stopExecuting(player)
                    }
                }
            }
            return@on true
        }

    }

    override fun defineDestinationOverrides() {
        setDest(IntType.SCENERY, intArrayOf(RC_PORTAL), "enter") { player, node ->
            if (player.viewport.region.regionId == 12337) {
                return@setDest node.asScenery().location
            } else {
                return@setDest Location.create(1696, 5461, 2)
            }
        }
    }
}
