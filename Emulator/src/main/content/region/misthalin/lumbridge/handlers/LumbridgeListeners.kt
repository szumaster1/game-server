package content.region.misthalin.lumbridge.handlers

import core.GlobalStatistics
import core.api.*
import org.rs.consts.*
import content.region.misthalin.lumbridge.basement.CulinaromancerChestListener
import core.game.activity.ActivityManager
import core.game.global.action.ClimbActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.system.task.Pulse
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation

/**
 * Represents the Lumbridge listeners.
 */
class LumbridgeListeners : InteractionListener {

    var FLAG_IN_USE: Boolean = false

    override fun defineListeners() {

        /*
         * Handling npc interaction.
         */

        on(intArrayOf(4707, 1861), IntType.NPC, "claim") { player, node ->
            openDialogue(player, node.asNpc().id, node, true)
            return@on true
        }

        /*
         * GnomeCopter sign interaction.
         */

        on(Scenery.ADVERTISEMENT_30037, IntType.SCENERY, "read") { player, _ ->
            sendDialogue(player, "Come check our gnome copters up north!")
            return@on true
        }

        /*
         * Cow field sign interaction.
         */

        on(Scenery.SIGNPOST_31297, IntType.SCENERY, "read") { player, _ ->
            val cowDeaths = GlobalStatistics.getDailyCowDeaths()
            if (cowDeaths > 0) {
                sendDialogue(player, "Local cowherders have reported that $cowDeaths cows have been slain in this field today by passing adventurers. Farmers throughout the land fear this may be an epidemic.")
            } else {
                sendDialogue(player, "The Lumbridge cow population has been thriving today, without a single cow death to worry about!")
            }
            return@on true
        }

        /*
         * Church sign interaction.
         */

        on(Scenery.SIGNPOST_31299, IntType.SCENERY, "read") { player, _ ->
            val deaths = GlobalStatistics.getDailyDeaths()
            if (deaths > 0) {
                sendDialogue(player, "So far today $deaths unlucky adventurers have died on RuneScape and been sent to their respawn location. Be careful out there.")
            } else {
                sendDialogue(player, "So far today not a single adventurer on RuneScape has met their end grisly or otherwise. Either the streets are getting safer or adventurers are getting warier.")
            }
            return@on true
        }

        /*
         * Warning sign interaction.
         */

        on(Scenery.WARNING_SIGN_15566, IntType.SCENERY, "read"){ player, _ ->
            openInterface(player, Components.MESSAGESCROLL_220).also {
                sendString(player, "<col=8A0808>~-~-~ WARNING ~-~-~", 220, 5)
                sendString(player, "<col=8A0808>Noxious gases vent into this cave.", 220, 7)
                sendString(player, "<col=8A0808>Naked flames may cause an explosion!", 220, 8)
                sendString(player, "<col=8A0808>Beware of vicious head-grabbing beasts!", 220, 10)
                sendString(player, "<col=8A0808>Contact a Slayer master for protective headgear.", 220, 11)
            }
            return@on true
        }

        /*
         * Culinaromancer's chest interaction.
         */

        on(Scenery.CHEST_12309, IntType.SCENERY, "buy-items", "buy-food") { player, _ ->
            CulinaromancerChestListener.openShop(player, food = getUsedOption(player).lowercase() == "buy-food")
            return@on true
        }

        /*
         * Culinaromancer's chest interaction.
         */

        on(Scenery.CHEST_12309, IntType.SCENERY, "bank") { player, _ ->
            openBankAccount(player)
            return@on true
        }

        /*
         * Church organs interaction.
         */

        on(Scenery.ORGAN_36978, IntType.SCENERY, "play") { player, _ ->
            lock(player, 10)
            ActivityManager.start(player, "organ cutscene", false)
            return@on true
        }

        /*
         * Church bell interaction.
         */

        on(Scenery.BELL_36976, IntType.SCENERY, "ring") { player, _ ->
            sendMessage(player, "The townspeople wouldn't appreciate you ringing their bell.")
            return@on true
        }

        /*
         * Lumbridge Castle flag interaction.
         */

        on(Scenery.FLAG_37335, IntType.SCENERY, "raise") { player, node ->
            lock(player, 12)
            if (!FLAG_IN_USE) {
                FLAG_IN_USE = true
                submitIndividualPulse(player, object : Pulse(1, player) {
                    var counter: Int = 0
                    override fun pulse(): Boolean {
                        when (counter++) {
                            0 -> {
                                sendMessage(player, "You start cranking the lever.")
                                sendMessageWithDelay(player, "The flag reaches the top...", 8)
                                sendChat(player, "All Hail the Duke!", 9)
                                sendMessageWithDelay(player, "...and slowly descends.", 12)
                                animateScenery(node.asScenery(), 9979)
                                animate(player, Animations.CRANKING_LUMBRIDGE_FLAG_9977)
                            }

                            8 -> animate(player, Animations.FINISH_CRANKING_LUMBRIDGE_FLAG_9978)
                        }
                        return counter >= 20
                    }

                    override fun stop() {
                        super.stop()
                        FLAG_IN_USE = false
                    }
                })
            }

            return@on true
        }

        /*
         * Lumbridge map interaction.
         */

        on(Scenery.LUMBRIDGE_MAP_37655, IntType.SCENERY, "view") { player, _ ->
            openInterface(player, 270)
            return@on true
        }

        /*
         * Training archery target interaction.
         */

        on(Scenery.ARCHERY_TARGET_37095, IntType.SCENERY, "shoot-at") { player, node ->
            if (!anyInEquipment(player, 9706, 9705)) {
                sendMessage(player, "You need a training bow and arrow to practice here.")
                return@on true
            }
            player.pulseManager.run(ArcheryTargetPulse(player, node.asScenery()))
            return@on true
        }

        /*
         * Doomsayer toggle-warning interaction.
         */

        on(NPCs.DOOMSAYER_3777, IntType.NPC, "toggle-warnings") { player, _ ->
            player.warningMessages.open(player)
            return@on true
        }

        on(Scenery.TOOLS_10375, IntType.SCENERY, "take") { player, node ->
            if (freeSlots(player) < 2) {
                sendMessage(player, "You do not have enough inventory space.")
                return@on true
            }
            addItem(player, Items.RAKE_5341)
            addItem(player, Items.SPADE_952)
            replaceScenery(node.asScenery(), 10373, 300)
            return@on true
        }

        /*
         * Interaction with Dark hole (Lumbridge swamp).
         */

        on(intArrayOf(Scenery.DARK_HOLE_5947, Scenery.CLIMBING_ROPE_5946), IntType.SCENERY, "climb-down", "climb") { player, node ->
            when(getUsedOption(player)) {
                "climb-down" -> {
                    if (!player.getSavedData().globalData.hasTiedLumbridgeRope()) {
                        sendDialogue(player, "There is a sheer drop below the hole. You will need a rope.")
                        return@on true
                    } else {
                        if(node.id == Scenery.DARK_HOLE_5947) {
                            ClimbActionHandler.climb(
                                player,
                                Animation(Animations.MULTI_USE_BEND_OVER_827),
                                Location(3168, 9572, 0)
                            )
                        }
                    }
                }

                else -> {
                    if (node.id != Scenery.DARK_HOLE_5947) {
                        ClimbActionHandler.climb(
                            player,
                            Animation(Animations.USE_LADDER_828),
                            Location(3169, 3173, 0)
                        )
                    }
                }
            }
            return@on true
        }
    }

}
