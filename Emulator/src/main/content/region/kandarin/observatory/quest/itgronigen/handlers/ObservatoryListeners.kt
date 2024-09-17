package content.region.kandarin.observatory.quest.itgronigen.handlers

import content.region.kandarin.observatory.quest.itgronigen.cutscene.TheObservatoryCutscene
import content.region.kandarin.observatory.quest.itgronigen.handlers.GoblinGuardNPC.Companion.spawnGoblinGuard
import content.region.kandarin.observatory.quest.itgronigen.handlers.PoisonSpiderNPC.Companion.spawnPoisonSpider
import core.api.*
import org.rs.consts.*
import content.region.kandarin.observatory.quest.itgronigen.dialogue.*
import core.api.utils.PlayerCamera
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.system.task.Pulse
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation

/**
 * Observatory listeners.
 */
class ObservatoryListeners : InteractionListener {

    companion object {
        val KEY_ATTRIBUTE = "observatory:goblin-key"
        val CONSTELLATION_ATTRIBUTE = "observatory:constellation"
        val FAIL_ATTRIBUTE = "observatory:fail-counter"

        private const val OBSERVATORY_ASSISTANT = NPCs.OBSERVATORY_ASSISTANT_6118
        private const val TELESCOPE_SCENERY = Scenery.TELESCOPE_25439
        private const val MOLTEN_GLASS = Items.MOLTEN_GLASS_1775
        private const val LENS_MOULD = Items.LENS_MOULD_602
        private const val OBSERVATORY_LENS = Items.OBSERVATORY_LENS_603
        private const val DUNGEON_STAIRS_DOWN = Scenery.STAIRS_25432
        private const val DUNGEON_STAIRS_UP = Scenery.STAIRS_25429
        private const val SLEEPING_GUARD = NPCs.SLEEPING_GUARD_6122
        private val KITCHEN_GATES = intArrayOf(Scenery.KITCHEN_GATE_2199, Scenery.KITCHEN_GATE_2200)
        private val KEY_CHEST = intArrayOf(Scenery.CHEST_25391, Scenery.CHEST_25389, Scenery.CHEST_25385, Scenery.CHEST_25387)
        private val KEY_CHEST_OPEN = intArrayOf(Scenery.CHEST_25392, Scenery.CHEST_25390, Scenery.CHEST_25386, Scenery.CHEST_25388)
        private val CHEST_LOCATIONS = arrayOf(
            Location(2312, 9400, 0),
            Location(2310, 9374, 0),
            Location(2335, 9374, 0),
            Location(2351, 9361, 0),
            Location(2364, 9355, 0),
            Location(2326, 9360, 0)
        )
        private const val POISON_SPIDER = NPCs.POISON_SPIDER_1009
        private const val GOBLIN_STOVE = Scenery.GOBLIN_STOVE_25440
        private const val EMPTY_GOBLIN_STOVE = Scenery.GOBLIN_STOVE_25441
        private const val ORRERY = Scenery.ORRERY_25401
        private val OBSERVATORY_GATES = intArrayOf(Scenery.DOOR_25526, Scenery.DOOR_25527)
        private const val SIGNPOST = Scenery.SIGNPOST_25397
        private const val OBSERVATORY_STAIRS = Scenery.STAIRS_25434
        private val STAR_CHART = intArrayOf(
            Scenery.STAR_CHART_25578,
            Scenery.STAR_CHART_25579,
            Scenery.STAR_CHART_25580,
            Scenery.STAR_CHART_25581,
            Scenery.STAR_CHART_25582,
            Scenery.STAR_CHART_25583
        )
    }

    override fun defineListeners() {

        /*
         * Observatory assistant interaction.
         */

        on(OBSERVATORY_ASSISTANT, IntType.NPC, "Talk-to") { player, _ ->
            val plankItem = Items.PLANK_960
            val amountOfPlanks = amountInInventory(player, plankItem)

            when (getQuestStage(player, "Observatory Quest")) {
                0 -> openDialogue(player, ObservatoryAssistantDialogue())
                2 -> openDialogue(player, AssistantTalkAboutPlanksDialogue(amountOfPlanks))
                4 -> openDialogue(player, AssistantTalkAboutBronzeBarDialogue())
                6 -> openDialogue(player, AssistantTalkAboutMoltenGlassDialogue())
                8 -> openDialogue(player, AssistantTalkAboutTheMouldDialogue())
                11 -> openDialogue(player, AssistantTalkAboutObservatoryLensDialogue())
                12 -> openDialogue(player, AssistantTalkAboutObservatoryDialogue())
                else -> sendDialogue(player, "Observatory assistant seems too busy to talk.")
            }
            return@on true
        }

        /*
         * Climb down to goblin dungeon.
         */

        on(DUNGEON_STAIRS_DOWN, IntType.SCENERY, "climb-down") { player, _ ->
            if (getQuestStage(player, "Observatory Quest") >= 8) {
                openInterface(player, Components.CWS_WARNING_9_560)
            } else {
                sendMessage(player, "Nothing interesting happens.")
            }
            return@on true
        }

        /*
         * Climb up from goblin dungeon.
         */

        on(DUNGEON_STAIRS_UP, IntType.SCENERY, "climb up") { player, node ->
            val questStage = getQuestStage(player, "Observatory Quest")
            if (node.location.x == 2335 && node.location.y == 9351) {
                if (questStage in 12..13) {
                    TheObservatoryCutscene(player).start()
                } else {
                    teleport(player, Location(2439, 3164, 0))
                    sendMessage(player, "You climb up the stairs.")
                }
            } else {
                teleport(player, Location(2457, 3186, 0))
                sendMessage(player, "You climb up the stairs.")
            }
            return@on true
        }

        /*
         * Prod interaction on Sleeping guard near kitchen gate.
         */

        on(SLEEPING_GUARD, IntType.NPC, "Prod") { player, _ ->
            val animation = Animation(Animations.HUMAN_GUARD_PROD_6839)
            if(!getAttribute(player, "observatory:goblin-spawn", false)) {
                animate(player, animation)
                runTask(player, animation.duration) {
                    spawnGoblinGuard(player)
                    setAttribute(player, "/save:observatory:goblin-spawn", true)
                }
            } else {
                sendMessage(player, "You can't do that right now.")
            }
            return@on true
        }

        /*
         * Open chest interaction.
         */

        on(KEY_CHEST, IntType.SCENERY, "open") { player, node ->
            val random = (0..3).random()
            if(!isQuestComplete(player, "Observatory Quest")) {
                animate(player, Animations.HUMAN_OPEN_CHEST_536)
                replaceScenery(node.asScenery(), node.id + 1, 80)
                sendMessage(player, "You open the chest.")
                setAttribute(player, "/save:$KEY_ATTRIBUTE", random)
                player.incrementAttribute(FAIL_ATTRIBUTE)
                if (getAttribute(player, FAIL_ATTRIBUTE, -1) == 10) {
                    removeAttribute(player, KEY_ATTRIBUTE)
                }
            } else {
                sendMessage(player, "It looks like this chest has already been looted.")
            }
            return@on true
        }

        /*
         * Search chest interaction.
         */

        on(KEY_CHEST_OPEN, IntType.SCENERY, "search") { player, _ ->
            animate(player, Animations.HUMAN_TAKE_FROM_CHEST_539)
            sendMessage(player, "You search the chest.")
            when (getAttribute(player, KEY_ATTRIBUTE, -1)) {
                0 -> {
                    sendMessage(player, "The chest contains a poisonous spider.")
                    spawnPoisonSpider(player)
                }

                1 -> {
                    sendMessage(player, "The chest is empty.")
                }

                2 -> {
                    sendMessage(player, "You find a kitchen key.")
                    addItem(player, Items.GOBLIN_KITCHEN_KEY_601)
                    removeAttribute(player, KEY_ATTRIBUTE)
                }

                else -> sendMessage(player, "The chest is empty.")
            }
            return@on true
        }

        /*
         * Close chest interaction.
         */

        on(KEY_CHEST_OPEN, IntType.SCENERY, "close") { player, node ->
            val attribute = getAttribute(player, KEY_ATTRIBUTE, -1)
            if(attribute != 2 || isQuestComplete(player, "Observatory Quest")){
                sendMessage(player, "You can't do that right now.")
            } else {
                animate(player, Animations.HUMAN_CLOSE_CHEST_538)
                replaceScenery(node.asScenery(), node.id.dec(), -1)
                sendMessage(player, "You close the chest.")
            }
            return@on true
        }

        /*
         * Kitchen gates leads to goblin stove.
         */

        on(KITCHEN_GATES, IntType.SCENERY, "open") { player, node ->
            if (getVarbit(player, Vars.VARBIT_QUEST_OBSERVATORY_GOBLIN_STOVE_LENS_MOULD_TAKEN) == 1) {
                DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
                return@on true
            }

            if (!inInventory(player, Items.GOBLIN_KITCHEN_KEY_601) || getQuestStage(player, "Observatory Quest") < 10) {
                sendMessage(player, "These gates are locked, you don't seem to be able to open them.")
            } else {
                sendPlayerDialogue(player, "You had better be quick, there may be more guards about.")
                DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
                removeItem(player, Items.GOBLIN_KITCHEN_KEY_601)
                sendMessage(player, "The gate unlocks.")
                sendMessage(player, "The key is useless now. You discard it.")
            }
            return@on true
        }

        /*
         * Inspect interaction on goblin stove.
         */

        on(GOBLIN_STOVE, IntType.SCENERY, "inspect") { player, _ ->
            sendDialogueLines(player, "The goblins appear to have been using the lens mould to cook their", "stew!")
            addDialogueAction(player) { _, _ ->
                animate(player, Animations.HUMAN_TAKE_FROM_CHEST_539)
                sendDialogue(player, "You shake out its contents and take it with you.")
                addItemOrDrop(player, Items.LENS_MOULD_602)
                runTask(player, 3) {
                    setVarbit(player, Vars.VARBIT_QUEST_OBSERVATORY_GOBLIN_STOVE_LENS_MOULD_TAKEN, 1, true)
                    sendChat(player, "Euuuw, that smells awful!")
                }
            }
            return@on true
        }

        /*
         * Inspect interaction on goblin stove after take lens.
         */

        on(EMPTY_GOBLIN_STOVE, IntType.SCENERY, "inspect") { player, _ ->
            sendMessage(player, "Just a plain stove. Nothing there.")
            return@on true
        }

        /*
         * Used molten glass on lens mould.
         */

        onUseWith(IntType.ITEM, MOLTEN_GLASS, LENS_MOULD) { player, used, _ ->
            if (removeItem(player, used.asItem())) {
                addItem(player, OBSERVATORY_LENS)
                sendMessage(player, "You pour the molten glass into the mould.")
                sendMessage(player, "You clasp it together.")
                sendItemDialogue(player, OBSERVATORY_LENS, "It has produced a small, convex glass dist.")
            } else {
                sendMessage(player, "Nothing interesting happens.")
            }
            return@onUseWith true
        }

        /*
         * Interaction with Orrey.
         */

        on(ORRERY, IntType.SCENERY, "view") { player, _ ->
            lock(player, 10000)
            lockInteractions(player, 10000)
            player.interfaceManager.removeTabs(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13)
            submitWorldPulse(object : Pulse(1) {
                var counter = 0
                override fun pulse(): Boolean {
                    when (counter++) {
                        0 -> {
                            PlayerCamera(player).setPosition(2443, 3186, 300)
                            PlayerCamera(player).rotateTo(2444, 3185, 300, 1000)
                        }

                        3 -> {
                            sendChat(player, "Oooooh, bizarre!")
                        }

                        6 -> {
                            PlayerCamera(player).reset()
                            player.interfaceManager.restoreTabs()
                            unlock(player)
                            return true
                        }
                    }
                    return false
                }
            })
            return@on true
        }

        /*
         * Gates inside observatory.
         */

        on(OBSERVATORY_GATES, IntType.SCENERY, "open") { player, _ ->
            sendMessage(player, "This gate is locked.")
            return@on true
        }

        /*
         * Stairs inside observatory.
         */

        on(OBSERVATORY_STAIRS, IntType.SCENERY, "climb-down") { player, _ ->
            teleport(player, Location(2335, 9350, 0))
            sendMessage(player, "You climb down the stairs.")
            return@on true
        }

        /*
         * Interaction with repaired telescope.
         */

        on(TELESCOPE_SCENERY, IntType.SCENERY, "view") { player, _ ->
            val telescopeAnim = Animation(Animations.HUMAN_LOOK_THROUGH_TELESCOPE_6849)
            animate(player, telescopeAnim)
            sendMessage(player, "You look through the telescope.")
            runTask(player, telescopeAnim.duration) {
                openInterface(player, Components.TELESCOPE_552)
                setQuestStage(player, "Observatory Quest", 14)
            }
            return@on true
        }

        /*
         * Look-at star chart interaction.
         */

        on(STAR_CHART, IntType.SCENERY, "Look-at") { player, _ ->
            openInterface(player, Components.STAR_CHART_104)
            return@on true
        }


    }
}