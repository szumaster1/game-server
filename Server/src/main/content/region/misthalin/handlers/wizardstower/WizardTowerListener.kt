package content.region.misthalin.handlers.wizardstower

import core.api.consts.NPCs
import core.api.consts.Scenery
import core.api.consts.Sounds
import content.global.travel.EssenceTeleport
import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.impl.Projectile
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.link.emote.Emotes
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Location
import core.game.world.update.flag.context.Graphic

/**
 * Represents the Wizard tower listener.
 */
class WizardTowerListener : InteractionListener {

    override fun defineListeners() {
        /**
         * Handling the player interaction with the bookcases in the Wizard's Tower.
         */
        on(intArrayOf(WIZARDS_TOWER_BOOKCASE_1, WIZARDS_TOWER_BOOKCASE_2), IntType.SCENERY, "search") { player, _ ->
            openDialogue(player, 458543948)
            return@on true
        }

        /**
         * Handling ladder interaction.
         */
        on(SEDRIDOR_TELEPORT_OPTION, IntType.NPC, "teleport") { player, node ->
            if (!hasRequirement(player, "Rune Mysteries")) return@on true
            EssenceTeleport.teleport((node as NPC), player)
            return@on true
        }

        /**
         * Handling ladder interaction.
         */
        on(WIZARDS_TOWER_LADDER_DOWN, IntType.SCENERY, "climb-down") { player, _ ->
            teleport(player, location(3104, 9576, 0))
            return@on true
        }

        /**
         * Handling the player interaction with railing to taunt the demon.
         */
        on(WIZARDS_TOWER_DEMON_TAUNT, IntType.SCENERY, "taunt-through") { player, _ ->
            val demon = findLocalNPC(player, NPCs.LESSER_DEMON_82) ?: return@on true
            forceWalk(demon, player.location, "smart")
            face(player, demon, 3)
            sendMessage(player, "You taunt the demon, making it growl.")
            sendChat(demon, "Graaaagh!")
            face(demon, player, 3)
            emote(player, Emotes.RASPBERRY)
            return@on true
        }

        /**
         * Handling interaction with the magic portals.
         */
        on(intArrayOf(WIZARDS_TOWER_PORTAL, DARK_WIZARDS_TOWER_PORTAL, THORMAC_SORC_HOUSE_PORTAL), IntType.SCENERY, "enter") { player, node ->
            when (node.id) {
                WIZARDS_TOWER_PORTAL -> {
                    teleport(player, Location.create(3109, 3159, 0))
                    sendMessage(player, "You enter the magic portal...")
                    sendMessage(player, "You teleport to the Wizards' tower.")
                }

                DARK_WIZARDS_TOWER_PORTAL -> {
                    teleport(player, Location.create(2907, 3333, 0))
                    sendMessage(player, "You enter the magic portal...")
                    sendMessage(player, "You teleport to the Dark Wizards' tower.")
                }

                THORMAC_SORC_HOUSE_PORTAL -> {
                    teleport(player, Location.create(2703, 3406, 0))
                    sendMessage(player, "You enter the magic portal...")
                    sendMessage(player, "You teleport to Thormac the Sorcerer's house.")
                }
            }
            return@on true
        }

        /**
         * Handling the basement cabinet.
         */
        on(CABINET_BASEMENT_CLOSED, IntType.SCENERY, "open") { player, node ->
            replaceScenery(node.asScenery(), CABINET_BASEMENT_OPEN, 100)
            playAudio(player, Sounds.OPEN_CABINET_44)
            return@on true
        }

        /**
         * Handling the basement cabinet.
         */
        on(CABINET_BASEMENT_OPEN, IntType.SCENERY, "close") { player, node ->
            if (getUsedOption(player) == "close") {
                replaceScenery(node.asScenery(), CABINET_BASEMENT_CLOSED, -1)
                return@on true
            } else {
                if (getUsedOption(player) == "search") {
                    sendMessage(player, "You search the cabinet but find nothing.")
                } else {
                    sendMessage(player, "Nothing interesting happens.")
                }
                return@on true
            }
        }

        /**
         * Handling exit portal at Land of the snow area.
         */
        on(LAND_OF_SNOW_PORTAL, IntType.SCENERY, "exit") { player, node ->
            Projectile.create(node.location, player.location, 109, 15, 10, 0, 10, 0, 2).send()
            GameWorld.Pulser.submit(object : Pulse(1) {
                var counter = 0
                override fun pulse(): Boolean {
                    when (counter++) {
                        0 -> {
                            lock(player, 2)
                            player.graphics(Graphic(110, 150))
                        }

                        1 -> {
                            teleport(player, location(3102, 9563, 0))
                            player.graphics(Graphic(110, 150))
                            unlock(player)
                            return true
                        }
                    }
                    return false
                }
            })
            return@on true
        }
    }

    companion object {
        private const val WIZARDS_TOWER_BOOKCASE_1 = Scenery.BOOKCASE_12539
        private const val WIZARDS_TOWER_BOOKCASE_2 = Scenery.BOOKCASE_12540
        private const val WIZARDS_TOWER_DEMON_TAUNT = Scenery.RAILING_37668
        private const val WIZARDS_TOWER_LADDER_DOWN = Scenery.LADDER_2147
        private const val WIZARDS_TOWER_PORTAL = Scenery.MAGIC_PORTAL_2156
        private const val DARK_WIZARDS_TOWER_PORTAL = Scenery.MAGIC_PORTAL_2157
        private const val THORMAC_SORC_HOUSE_PORTAL = Scenery.MAGIC_PORTAL_2158
        private const val SEDRIDOR_TELEPORT_OPTION = NPCs.SEDRIDOR_300
        private const val CABINET_BASEMENT_CLOSED = Scenery.CABINET_33062
        private const val CABINET_BASEMENT_OPEN = Scenery.CABINET_33063
        private const val LAND_OF_SNOW_PORTAL = Scenery.PORTAL_41681
    }

}
