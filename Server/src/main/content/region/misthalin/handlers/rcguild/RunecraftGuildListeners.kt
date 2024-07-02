package content.region.misthalin.handlers.rcguild

import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.skill.Skills
import core.game.world.map.Location
import core.api.consts.*
import core.game.node.item.Item

class RunecraftGuildListeners : InteractionListener {

    companion object {
        private const val RC_PORTAL = 38279
        private const val MAP_TABLE = 38315
        private val BOOKCASES = intArrayOf(38322, 38323, 38324)
        private val RC_HAT = intArrayOf(13626, 13625, 13621, 13620, 13616, 13615)
    }

    override fun defineListeners() {

        /*
         * Remove and apply goggles to the Runecraft hat.
         */

        on(RC_HAT, IntType.ITEM, "Goggles") { player, node ->
            when (node.id) {
                13626 -> replaceSlot(player, node.asItem().slot, Item(13625))
                13625 -> replaceSlot(player, node.asItem().slot, Item(13626))
                13621 -> replaceSlot(player, node.asItem().slot, Item(13620))
                13620 -> replaceSlot(player, node.asItem().slot, Item(13621))
                13616 -> replaceSlot(player, node.asItem().slot, Item(13615))
                13615 -> replaceSlot(player, node.asItem().slot, Item(13616))
            }
            return@on true
        }

        on(NPCs.WIZARD_ELRISS_8032, IntType.NPC, "Exchange") { player, _ ->
            openInterface(player, Components.RCGUILD_REWARDS_779)
            return@on true
        }

        on(MAP_TABLE, IntType.SCENERY, "Study") { player, _ ->
            openInterface(player, Components.RCGUILD_MAP_780)
            return@on true
        }

        on(RC_PORTAL, IntType.SCENERY, "Enter") { player, _ ->

            /*
             * Wizards' Tower to Runecrafting Guild Portal.
             */

            if (getStatLevel(player, Skills.RUNECRAFTING) >= 50 && isQuestComplete(player, "Rune Mysteries")) {
                if (player.viewport.region.regionId == 12337) {
                    lock(player, 6)
                    lockInteractions(player, 6)
                    visualize(player, Animations.RC_GUILD_TP_A_10180, Graphics.RC_GUILD_TP)
                    queueScript(player, 3, QueueStrength.SOFT) { _ ->
                        visualize(player, Animations.RC_GUILD_TP_B_10182, Graphics.RC_GUILD_TP)
                        teleport(player, location(1696, 5461, 2))
                        face(player, location(1696, 5463, 2))
                        return@queueScript stopExecuting(player)
                    }
                } else {
                    visualize(player, Animations.RC_GUILD_TP_A_10180, Graphics.RC_GUILD_TP)
                    queueScript(player, 3, QueueStrength.SOFT) { _ ->
                        teleport(player, location(3106, 3160, 1))
                        visualize(player, Animations.RC_GUILD_TP_B_10182, Graphics.RC_GUILD_TP)
                        face(player, location(3105, 3160, 1))
                        return@queueScript stopExecuting(player)
                    }
                }
            } else if (getStatLevel(player, Skills.RUNECRAFTING) >= 50 && !isQuestComplete(player, "Rune Mysteries")) {
                sendDialogue(player, "You need to complete Rune Mysteries to enter the Runecrafting guild.")
            } else {
                sendDialogue(player, "You need a Runecrafting level of 50 to enter the Runecrafting guild.")
            }
            return@on true
        }

        on(BOOKCASES, IntType.SCENERY, "search") { player, _ ->
            sendMessage(player, "You search the books...")
            player.sendMessage("None of them look very interesting.", 1)
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
