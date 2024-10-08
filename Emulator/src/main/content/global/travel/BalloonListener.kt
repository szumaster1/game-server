package content.global.travel

import core.api.*
import core.cache.def.impl.ItemDefinition
import core.game.component.Component
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.InterfaceListener
import core.game.node.Node
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.map.Location
import org.rs.consts.*

class BalloonListener : InterfaceListener, InteractionListener {

    companion object {
        const val BALLOON_MAP_INTERFACE = Components.ZEP_BALLOON_MAP_469
        val assistantAuguste = NPC(NPCs.AUGUSTE_5050, Location.create(2938, 3424, 0))
        val assistantVarrock = NPC(NPCs.ASSISTANT_SERF_5053, Location.create(3298, 3484, 0))
        val assistantGrandTree = NPC(NPCs.ASSISTANT_LE_SMITH_5056, Location.create(2480, 3458, 0))
        val assistantTaverley = NPC(NPCs.ASSISTANT_STAN_5057, Location.create(2938, 3424, 0))
        val allAssistants = (5049..5057).toIntArray()
        val balloonIds = intArrayOf(19128, 19129, 19133, 19135, 19143, 19141, 19137, 19139)

        fun adjustInterface(player: Player, npc: Node) {
            val npcs = npc as NPC
            setComponentVisibility(
                player, BALLOON_MAP_INTERFACE, when (npcs.id) {
                    5049 -> 12
                    5050 -> 22
                    5053 -> 21
                    5054, 5065 -> 20
                    5055, 5063 -> 24
                    5056 -> 23
                    5057 -> 22
                    else -> return
                }, false
            )
        }

        @JvmStatic
        fun handleFlight(player: Player, flight: FlightDestination) {
            lock(player, 4)
            lockInteractions(player, 4)
            player.dialogueInterpreter.sendPlainMessage(
                true,
                "<br>You board the balloon and fly to ${flight.areaName}."
            )
            animateInterface(player, Components.ZEP_BALLOON_MAP_469, 0, flight.flyAnim)
            teleport(player, flight.flightDestination)
            openInterface(player, Components.FADE_TO_BLACK_115)
            val animDuration = animationDuration(getAnimation(flight.flyAnim))
            if (inBorders(player, getRegionBorders(13110))) {
                player.achievementDiaryManager.finishTask(player, DiaryType.VARROCK, 2, 17)
            }
            submitWorldPulse(object : Pulse(6) {
                var count = 0
                override fun pulse(): Boolean {
                    when (count++) {
                        0 -> openInterface(player, Components.FADE_FROM_BLACK_170)
                        3 -> player.dialogueInterpreter.sendPlainMessage(
                            true, "<br>You arrive safely in ${flight.areaName}."
                        )

                        5 -> {
                            closeDialogue(player)
                            closeInterface(player)
                        }

                        6 -> {
                            unlock(player)
                            return true
                        }
                    }
                    return false
                }
            })
        }

        enum class FlightDestination(val areaName: String, val flightDestination: Location, val flyAnim: Int, var button: Int, var logId: Int, var varbitId: Int, var requiredLevel: Int) {
            CASTLE_WARS(
                areaName = "Castle Wars",
                flightDestination = Location.create(2462, 3108, 0),
                flyAnim = 11009,
                button = 14,
                logId = Items.YEW_LOGS_1515,
                varbitId = Vars.VARBIT_QUEST_ENLIGHTENED_JOURNEY_CASTLE_WARS_BALLOON,
                requiredLevel = 50
            ),
            GRAND_TREE(
                areaName = "Grand Tree",
                flightDestination = Location.create(2480, 3458, 0),
                flyAnim = 11009,
                button = 15,
                logId = Items.MAGIC_LOGS_1513,
                varbitId = Vars.VARBIT_QUEST_ENLIGHTENED_JOURNEY_GRAND_TREE_BALLOON,
                requiredLevel = 60
            ),
            CRAFT_GUILD(
                areaName = "Crafting Guild",
                flightDestination = Location.create(2924, 3303, 0),
                flyAnim = 11009,
                button = 16,
                logId = Items.OAK_LOGS_1521,
                varbitId = Vars.VARBIT_QUEST_ENLIGHTENED_JOURNEY_CRAFTING_GUILD_BALLOON,
                requiredLevel = 30
            ),
            VARROCK(
                areaName = "Varrock",
                flightDestination = Location.create(3298, 3481, 0),
                flyAnim = 11009,
                button = 19,
                logId = Items.WILLOW_LOGS_1519,
                varbitId = Vars.VARBIT_QUEST_ENLIGHTENED_JOURNEY_VARROCK_BALLOON,
                requiredLevel = 40
            ),
            ENTRANA(
                areaName = "Entrana",
                flightDestination = Location.create(2809, 3356, 0),
                flyAnim = 11009,
                button = 17,
                logId = Items.LOGS_1511,
                varbitId = Vars.VARBIT_QUEST_ENLIGHTENED_JOURNEY_ENTRANA_BALLOON,
                requiredLevel = 20
            ),
            TAVERLEY(
                areaName = "Taverley",
                flightDestination = Location.create(2940, 3420, 0),
                flyAnim = 11009,
                button = 18,
                logId = Items.LOGS_1511,
                varbitId = Vars.VARBIT_QUEST_ENLIGHTENED_JOURNEY_TAVERLEY_BALLOON,
                requiredLevel = 20
            );

            companion object {
                val flightMap = HashMap<Int, FlightDestination>()

                init {
                    for (flight in values()) {
                        flightMap[flight.button] = flight
                    }
                }
            }
        }
    }

    init {
        assistantTaverley.init()
        assistantAuguste.init()
        assistantGrandTree.init()
        assistantVarrock.init()

        assistantTaverley.isWalks = true
        assistantAuguste.isWalks = true
        assistantGrandTree.isWalks = true
        assistantVarrock.isWalks = true
    }


    override fun defineInterfaceListeners() {
        onOpen(BALLOON_MAP_INTERFACE) { player: Player, _: Component ->
            setMinimapState(player, 2)
            return@onOpen true
        }

        onClose(BALLOON_MAP_INTERFACE) { player: Player, _: Component ->
            setMinimapState(player, 0)
            return@onClose true
        }

        on(Components.ZEP_BALLOON_MAP_469) { player, _, _, buttonID, _, _ ->
            val button = FlightDestination.flightMap[buttonID] ?: return@on true
            when (button.button) {
                buttonID -> {
                    if (buttonID == 17 && !ItemDefinition.canEnterEntrana(player)) {
                        sendDialogue(player, "You can't take flight with weapons and armour to Entrana.")
                        return@on true
                    }

                    if (!hasLevelStat(player, Skills.FIREMAKING, button.requiredLevel)) {
                        sendDialogue(player, "You require a Firemaking level of ${button.requiredLevel} to travel at ${button.areaName}.")
                        return@on true
                    }
                    if (!removeItem(player, Item(button.logId, 1))) {
                        sendDialogue(player, "You need at least one ${getItemName(button.logId).lowercase().replace("s", "").trim()}.")
                        return@on true
                    }
                    if (player.familiarManager.hasFamiliar()) {
                        sendMessage(player, "You can't take a follower on a ride.")
                        return@on true
                    }
                    else {
                        //setAttribute(player, "/save:${UNLOCKED_DESTINATION}:${button.unlockedDest}", true)
                        handleFlight(player, button)
                    }
                    return@on true
                }

                else -> return@on true
            }
        }
    }

    override fun defineListeners() {
        on(balloonIds, IntType.SCENERY, "use") { player, node ->
            when (node.id) {
                5054 -> player.dialogueInterpreter.open(5065)
                else -> {
                    if (!hasRequirement(player, QuestName.ENLIGHTENED_JOURNEY)) return@on true
                    openInterface(player, Components.ZEP_BALLOON_MAP_469).also {
                        setComponentVisibility(
                            player, Components.ZEP_BALLOON_MAP_469,
                            when (node.asScenery().getWrapper().id) {
                                19128, 19133 -> 12
                                19135 -> 22
                                19137 -> 24
                                19139 -> 23
                                19141 -> 20
                                19143 -> 21
                                else -> return@on false
                            }, false
                        )
                    }
                    return@on true
                }
            }
        }

        on(allAssistants, IntType.NPC, "fly") { player, node ->
            if (!isQuestComplete(player, QuestName.ENLIGHTENED_JOURNEY)) {
                sendMessage(player, "You must complete Enlightened Journey before you can use it.")
            } else {
                adjustInterface(player, node.asNpc())
                openInterface(player, Components.ZEP_BALLOON_MAP_469)
            }
            return@on true
        }
    }
}
