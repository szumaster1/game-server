package content.region.misthalin.handlers.varrock

import content.region.kandarin.quest.biohazard.dialogue.GuidorsWifeDialogueFile
import content.region.misthalin.dialogue.varrock.KnockatDoorDialogue
import core.GlobalStats
import core.api.*
import core.api.consts.*
import core.game.dialogue.FacialExpression
import core.game.global.action.ClimbActionHandler
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.Option
import core.game.interaction.QueueStrength
import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.entity.skill.Skills
import core.game.world.map.Location
import core.game.world.map.zone.MapZone
import core.game.world.map.zone.ZoneBorders
import core.game.world.update.flag.context.Animation
import core.utilities.Log

class VarrockListeners : InteractionListener {

    companion object {
        private var COUNTER = 0
        private val BERRIES = intArrayOf(
            Scenery.CADAVA_BUSH_23625, Scenery.CADAVA_BUSH_23626, Scenery.CADAVA_BUSH_23627,
            Scenery.REDBERRY_BUSH_23628, Scenery.REDBERRY_BUSH_23629, Scenery.REDBERRY_BUSH_23630
        )
    }

    override fun defineListeners() {

        /*
         * Thessalia Nitter runs Thessalia's Fine Clothes
         * and Thessalia's Makeovers in Varrock.
         * Change clothes interaction.
         */

        on(NPCs.THESSALIA_548, IntType.NPC, "change-clothes") { player, _ ->
            openDialogue(player, NPCs.THESSALIA_548, true, true, true)
            return@on true
        }

        /*
         *  Broken cart south of Varrock,
         *  starting point for the What Lies Below quest.
         */

        on(Scenery.BROKEN_CART_23055, IntType.SCENERY, "search") { player, _ ->
            sendDialogueLines(player, "You search the cart but are surprised to find very little there. It's a", "little odd for a travelling trader not to have anything to trade.")
            return@on true
        }

        /*
         * The shed provides an alternate entrance to the
         * Edgeville Dungeon, entering north of the Hill Giant room.
         */

        on(Scenery.DOOR_1804, IntType.SCENERY, "open") { player, node ->
            if (!inInventory(player, Items.BRASS_KEY_983)) {
                sendMessage(player, "This door is locked.")
            } else {
                DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
            }
            return@on true
        }

        /*
         * Doors to Guidor NPC that can be found in southeastern Varrock.
         * He tests the plague sample for you during the Biohazard quest.
         */

        on(Scenery.BEDROOM_DOOR_2032, IntType.SCENERY, "open") { player, node ->
            if (!anyInEquipment(player, Items.PRIEST_GOWN_426, Items.PRIEST_GOWN_428) && getQuestStage(player, "Biohazard") >= 11) {
                openDialogue(player, GuidorsWifeDialogueFile())
            } else if (inEquipment(player, Items.PRIEST_GOWN_426) && !inEquipment(player, Items.PRIEST_GOWN_428) && getQuestStage(player, "Biohazard") >= 11) {
                DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
                if (player.location.x == 3282) sendMessage(player, "Guidor's wife allows you to go in.")
            } else {
                sendNPCDialogue(player, NPCs.GUIDORS_WIFE_342, "Please leave my husband alone. He's very sick, and I don't want anyone bothering him.", FacialExpression.SAD)
            }
            return@on true
        }

        /*
         * The Champions' Guild doors.
         */

        on(Scenery.DOOR_1805, IntType.SCENERY, "open") { player, node ->
            if (getQuestPoints(player) < 32) {
                sendDialogue(player, "You have not proved yourself worthy to enter here yet.")
                sendMessage(player, "The door won't open - you need at least 32 Quest Points.")
            } else {
                when(player.location.y){
                    3363 -> sendNPCDialogue(player, NPCs.GUILDMASTER_198, "Greetings bold adventurer. Welcome to the guild of Champions.").also {
                        DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
                    }
                    3362 -> DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
                }
            }
            return@on true
        }

        /*
         * Interaction with bank service door.
         */

        on(Scenery.DOOR_24389, IntType.SCENERY, "knock-at") { player, node ->
            openDialogue(player, KnockatDoorDialogue(), node.asScenery())
            return@on true
        }

        /*
         * The Cooks' Guild doors.
         */

        on(intArrayOf(Scenery.DOOR_2712, Scenery.DOOR_26810), IntType.SCENERY, "open") { player, node ->
            var requiredItems = anyInEquipment(
                player,
                Items.CHEFS_HAT_1949,
                Items.COOKING_CAPE_9801,
                Items.COOKING_CAPET_9802,
                Items.VARROCK_ARMOUR_3_11758
            )

            when (node.id) {
                26810 -> {
                    if (!inEquipment(player, Items.VARROCK_ARMOUR_3_11758) && player.location.x <= 3143) {
                        sendNPCDialogue(player, NPCs.HEAD_CHEF_847, "The bank's closed. You just can't get the staff these days.")
                    } else {
                        DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
                    }
                }

                2712 -> {
                    if (getStatLevel(player, Skills.COOKING) < 32) {
                        if (!requiredItems) {
                            sendNPCDialogue(player, NPCs.HEAD_CHEF_847, "Sorry. Only the finest chefs are allowed in here. Get your cooking level up to 32 and come back wearing a chef's hat.")
                        } else {
                            sendNPCDialogue(player, NPCs.HEAD_CHEF_847, "Sorry. Only the finest chefs are allowed in here. Get your cooking level up to 32.")
                        }
                    } else if (!requiredItems && player.location.y <= 3443) {
                        sendNPCDialogue(player, NPCs.HEAD_CHEF_847, "You can't come in here unless you're wearing a chef's hat or something like that.")
                    } else {
                        if (inEquipment(player, Items.VARROCK_ARMOUR_3_11758)) {
                            sendNPCDialogue(player, NPCs.HEAD_CHEF_847, "My word! A master explorer of Varrock! Come in, come in! You are more than welcome in here, my friend!")
                        }
                        DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
                    }
                }
            }

            return@on true
        }

        /*
         * Cadava & Red berries bushes found near Varrock.
         */

        on(BERRIES, IntType.SCENERY, "pick-from") { player, node ->

            if (node.id == 23630 || node.id == 23627) {
                sendMessage(player, "There are no berries left on this bush.")
                sendMessage(player, "More berries will grow soon.")
                return@on true
            }

            if (freeSlots(player) == 0) {
                sendMessage(player, "Your inventory is too full to pick the berries from the bush.")
                return@on true
            }

            stopWalk(player)
            lock(player, 3)
            animate(player, Animations.PICK_SOMETHING_UP_FROM_GROUND_2282)

            if (node.id == 23628 || node.id == 23629)
                addItem(player, Items.REDBERRIES_1951)
            else
                addItem(player, Items.CADAVA_BERRIES_753)

            if (COUNTER == 2) {
                if (node.id != 23628 || node.id != 23629)
                    replaceScenery(node.asScenery(), 23630, 30)
                else
                    replaceScenery(node.asScenery(), 23627, 30)

                COUNTER = 0
                return@on true
            }
            COUNTER++
            return@on true
        }

        /*
         * Stray dog "Shoo-away" interaction.
         */

        on(NPCs.STRAY_DOG_5917, IntType.NPC, "shoo-away") { player, node ->
            val dog = node.asNpc()
            face(player, node)
            animate(player, Animations.RASPBERRY_2110)
            sendChat(player, "Thbbbbt!")
            queueScript(player, 1, QueueStrength.SOFT) {
                dog.sendChat("Whine!")
                dog.moveStep()
                dog.pulseManager.clear()
                return@queueScript stopExecuting(player)
            }
            return@on true
        }

        val zone = object : MapZone("Varrock Guards", true) {
            override fun interact(e: Entity?, target: Node?, option: Option?): Boolean {
                if (option != null && option.name.lowercase().contains("pickpocket") && target != null && target.name.lowercase().contains("guard")) {
                    GlobalStats.incrementGuardPickpockets()
                }
                return false
            }
        }

        registerMapZone(zone, ZoneBorders(3225, 3445, 3198, 3471))
        registerMapZone(zone, ZoneBorders(3222, 3375, 3199, 3387))
        registerMapZone(zone, ZoneBorders(3180, 3420, 3165, 3435))
        registerMapZone(zone, ZoneBorders(3280, 3422, 3266, 3435))

        on(Scenery.SIGNPOST_31298, IntType.SCENERY, "read") { player, _ ->
            val pickpocketCount = GlobalStats.getDailyGuardPickpockets()
            log(this::class.java, Log.FINE, "Is equal? ${pickpocketCount == 0}")
            when (pickpocketCount) {
                0 -> sendDialogue(player, "The Varrock Palace guards are pleased to announce that crime is at an all-time low, without a single guard in the palace or at the city gates being pickpocketed today.")
                1 -> sendDialogue(player, "One of the Varrock Palace guards was pickpocketed today. He was close to tears at having lost his last few gold pieces.")
                else -> sendDialogue(player, "Guards in the Varrock Palace are on full alert due to increasing levels of pickpocketing. So far today, $pickpocketCount guards have had their money pickpocketed in the palace or at the city gates.")
            }
            return@on true
        }

        on(17985, IntType.SCENERY, "climb-down"){ player, _ ->
            ClimbActionHandler.climb(player, ClimbActionHandler.CLIMB_DOWN, Location(3204, 9910), "You enter the murky sewers.")
            return@on true
        }

        /*
         * Exit from Varrock sewers to Varrock east bank.
         */

        on(Scenery.LADDER_24366, IntType.SCENERY, "climb-up") { player, _ ->
            ClimbActionHandler.climb(player, ClimbActionHandler.CLIMB_UP, Location(3237, 3459))
            return@on true
        }

        on(Scenery.PORTAL_28780, IntType.SCENERY, "use"){ player, _ ->
            visualize(player, -1, Graphics.CURSE_IMPACT_110)
            queueScript(player, 1, QueueStrength.SOFT) {
                teleport(player, Location(3326, 5469, 0))
                return@queueScript stopExecuting(player)
            }
            return@on true
        }

        /*
         * Interaction showing Phoenix Gang plaque interface.
         */

        on(Scenery.PLAQUE_23636, IntType.SCENERY, "read"){ player, _ ->
            openInterface(player, Components.SOA_PLAQUE_531)
            return@on true
        }

        on(Scenery.LADDER_1749, IntType.SCENERY, "climb-down") { player, _ ->
            if (player.location.z == 2) {
                ClimbActionHandler.climb(player, Animation(Animations.MULTI_USE_BEND_OVER_827), Location.create(3097, 3432, 1))
            } else {
                ClimbActionHandler.climb(player, Animation(Animations.MULTI_USE_BEND_OVER_827), Location.create(3096, 3432, 0))
            }
            return@on true
        }

    }

}
