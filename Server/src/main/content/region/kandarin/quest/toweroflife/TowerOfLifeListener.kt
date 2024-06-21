package content.region.kandarin.quest.toweroflife

import core.api.*
import core.api.consts.Animations
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.consts.Scenery
import core.game.dialogue.FacialExpression
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.utilities.RandomFunction

class TowerOfLifeListener : InteractionListener {
    companion object {
        const val TOWER_DOORS = Scenery.TOWER_DOOR_21814
        const val PLANTS = Scenery.PLANT_21924
        const val PLANTS_SEARCH = Animations.HUMAN_PLANT_SEARCH_5819
    }

    override fun defineListeners() {
        on(TOWER_DOORS, IntType.SCENERY, "open") { player, node ->
            val tolPlayerEyeGloCrystalSaw = inInventory(player, Items.CRYSTAL_SAW_9625)
            val tolPlayerConstructionHardHat = inEquipment(player, Items.HARD_HAT_10862)
            val tolPlayerConstructionShirt = inEquipment(player, Items.BUILDERS_SHIRT_10863)
            val tolPlayerConstructionTrousers = inEquipment(player, Items.BUILDERS_TROUSERS_10864)
            val tolPlayerConstructionBoots = inEquipment(player, Items.BUILDERS_BOOTS_10865)
            if (tolPlayerConstructionHardHat && tolPlayerConstructionShirt && tolPlayerConstructionTrousers && tolPlayerConstructionBoots)
                if (!anyInInventory(player, Items.SAW_8794, Items.HAMMER_2347)) {
                    sendNPCDialogue(
                        player,
                        NPCs.BONAFIDO_5580,
                        "just as a warnin' ${if (player.isMale) "mate" else "babe"}, you gonna need a hammer and saw to make anyfin' in there.",
                        FacialExpression.NEUTRAL
                    )
                } else {
                    DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
                    queueScript(player, 3, QueueStrength.WEAK) {
                        when (player.location.y) {
                            3225 -> {
                                sendPlayerDialogue(player, "Wow, this place looks special. Best I look around for something to fix.", FacialExpression.AMAZED)
                                return@queueScript stopExecuting(player)
                            }

                            else -> {
                                sendMessage(player, "The doors appear to be stuck.")
                                return@queueScript stopExecuting(player)
                            }
                        }
                    }
                }
            return@on true
        }

        on(PLANTS, IntType.SCENERY, "search") { player, _ ->
            val random = RandomFunction.random(1, 5)
            lock(player, 2)
            animate(player, PLANTS_SEARCH)
            sendMessage(player, "You search the plant...")

            queueScript(player, 3, QueueStrength.WEAK) {
                when (RandomFunction.random(1, 5)) {
                    3 -> {
                        sendDialogue(player, "Aha!, You find some trousers!")
                        sendMessage(player, "Try the beckon emote while wearing an item of builders' clothing!")
                        return@queueScript stopExecuting(player)
                    }

                    else -> {
                        sendMessage(player, "Nope, nothing here.")
                        return@queueScript stopExecuting(player)
                    }
                }
            }
            return@on true
        }

        //      0 -> player("Why does nobody listen?").also { stage++ }
        //		1 -> player("Best I follow them, I suppose.").also { stage = END_DIALOGUE }

    }
}

