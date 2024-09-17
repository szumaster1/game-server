package content.region.fremennik.rellekka.lighthouse.quest.horror.handlers

import core.api.*
import org.rs.consts.Items
import org.rs.consts.NPCs
import org.rs.consts.Scenery
import content.region.fremennik.rellekka.lighthouse.quest.horror.dialogue.JossikDialogueFile
import core.game.dialogue.FacialExpression
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.system.task.Pulse

/**
 * Horror from the deep listeners.
 */
class HorrorFromTheDeepListeners : InteractionListener {

    override fun defineListeners() {

        /*
         * Handle interaction with Jossik underground.
         */

        on(NPCs.JOSSIK_1335, IntType.NPC, "talk-to") { player, _ ->
            openDialogue(player, JossikDialogueFile())
            return@on true
        }

        /*
         * Door leading to the lighthouse.
         */

        on(HorrorUtils.LIGHTHOUSE_FRONT_DOORS, IntType.SCENERY, "walk-through") { player, node ->
            if (isQuestComplete(player, "Horror from the Deep")) {
                DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
            } else if (getQuestStage(player, "Horror from the Deep") >= 20) {
                submitIndividualPulse(player, object : Pulse(2) {
                    override fun pulse(): Boolean {
                        if (inBorders(player, 2508, 3634, 2510, 3635)) {
                            sendMessage(player, "You unlock the Lighthouse front door.")
                            DoorActionHandler.handleAutowalkDoor(player, node.asScenery()).also { runTask(player, 3) { teleport(player, location(2445, 4596, 0)) } }
                        } else {
                            DoorActionHandler.handleAutowalkDoor(player, node.asScenery()).also { runTask(player, 3) { teleport(player, location(2509, 3635, 0)) } }
                        }
                        return true
                    }
                })
            } else if (getQuestStage(player, "Horror from the Deep") < 20) {
                sendNPCDialogue(player, NPCs.LARRISSA_1336, "Please adventurer... We are both curious as to what has happened in that lighthouse, but you need to fix the bridge for me!")
            } else {
                sendDialogue(player, "You can't see any way to open the door.")
            }
            return@on true
        }

        /*
         * Handle the metal ladders to boss.
         */

        on(Scenery.IRON_LADDER_4383, IntType.SCENERY, "climb") { player, _ ->
            animate(player, 827)
            queueScript(player, 1, QueueStrength.SOFT) {
                if (isQuestComplete(player, "Horror from the Deep")) {
                    teleport(player, location(2519, 9994, 1)) // Dagannoth cave.
                } else if (getQuestStage(player, "Horror from the Deep") >= 40) {
                    teleport(player, location(2519, 4618, 1)) // Dagannoth cave with boss.
                } else if (getQuestStage(player, "Horror from the Deep") < 40) {
                    sendPlayerDialogue(player, "I have no reason to go down there.", FacialExpression.HALF_THINKING)
                } else {
                    sendNPCDialogue(player, NPCs.LARRISSA_1336, "Please adventurer, do not let curiosity get the better of you! We have to fix the lighthouse before any accidents happen!")
                }
                return@queueScript stopExecuting(player)
            }
            return@on true
        }

        /*
         * Handle first interaction fix for lighthouse mechanism.
         */

        onUseWith(IntType.SCENERY, Items.SWAMP_TAR_1939, Scenery.LIGHTING_MECHANISM_4588) { player, _, _ ->
            if (removeItem(player, Items.SWAMP_TAR_1939)) {
                sendMessage(player, "You use the swamp tar to make the torch flammable again.")
                setAttribute(player,
                    HorrorUtils.fixLighthouseMechanism, 1)
            }
            return@onUseWith true
        }

        /*
         * Handles the second fix for lighthouse mechanism.
         */

        onUseWith(IntType.SCENERY, Items.TINDERBOX_590, Scenery.LIGHTING_MECHANISM_4588) { player, _, _ ->
            sendMessage(player, "You light the torch with your tinderbox.")
            player.incrementAttribute(HorrorUtils.fixLighthouseMechanism)
            return@onUseWith true
        }

        /*
         * Handles the third fix for lighthouse mechanism.
         */

        onUseWith(IntType.SCENERY, Items.MOLTEN_GLASS_1775, Scenery.LIGHTING_MECHANISM_4588) { player, item, mechanism ->
            if (getAttribute(player,
                    HorrorUtils.fixLighthouseMechanism, 0) == 2 && removeItem(player, item.asItem())) {
                replaceScenery(mechanism.asScenery(), Scenery.LIGHTING_MECHANISM_4587, 80)
                setQuestStage(player, "Horror from the Deep", 40)
                sendMessage(player, "You use the molten glass to repair the lens.")
                sendMessage(player, "You have managed to repair the lighthouse torch!")
            }
            return@onUseWith true
        }
    }
}