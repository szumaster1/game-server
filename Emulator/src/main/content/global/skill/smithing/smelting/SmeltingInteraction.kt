package content.global.skill.smithing.smelting

import core.api.*
import core.game.dialogue.FacialExpression
import core.game.dialogue.SkillDialogueHandler
import core.game.event.ResourceProducedEvent
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.GameWorld
import core.game.world.update.flag.context.Animation
import org.rs.consts.*

/**
 * Handles the smelting interactions.
 */
class SmeltingInteraction : InteractionListener {

    override fun defineListeners() {

        /*
         * Handles cannonball smithing.
         */

        onUseWith(IntType.SCENERY, Items.STEEL_BAR_2353, *furnaceIDs) { player, used, _ ->
            val handler: SkillDialogueHandler =
                object : SkillDialogueHandler(player, SkillDialogue.ONE_OPTION, used.asItem()) {
                    override fun create(amount: Int, index: Int) {
                        submitIndividualPulse(player, CannonballPulse(player, used.asItem(), amount))
                    }

                    override fun getAll(index: Int): Int {
                        return amountInInventory(player, used.id)
                    }
                }

            handler.open()
            return@onUseWith true
        }

        /*
         * Smelt ore interaction.
         */

        on(IntType.SCENERY, "smelt", "smelt-ore") { player, node ->
            if (node.id == Scenery.FURNACE_26814 && !isDiaryComplete(player, DiaryType.VARROCK, 0)) {
                if (!GameWorld.settings!!.isMembers) {
                    sendNPCDialogue(player, NPCs.JEFFERY_6298, "Keep away from that! It's dangerous!")
                } else {
                    sendNPCDialogue(player, NPCs.JEFFERY_6298, "You want to use my furnace?", FacialExpression.HALF_ASKING)
                    addDialogueAction(player) { player, button ->
                        if(button >= 1) {
                            sendNPCDialogue(player, NPCs.JEFFERY_6298, "No one can use my furnace! Only I can use my furnace!", FacialExpression.ANNOYED)
                        }
                    }
                    sendMessage(player, rejectMessage)
                }
                return@on true
            }
            show(player)
            return@on true
        }

        /*
         * Special interaction related to quest.
         */

        on(specialFurnace, IntType.SCENERY, "use") { player, _ ->
            if (!isDiaryComplete(player, DiaryType.VARROCK, 0)) {
                sendMessage(player, rejectMessage)
                return@on true
            }
            show(player)
            return@on true
        }

        /*
         * Standard smelting interaction.
         */

        onUseWith(IntType.SCENERY, ids, *furnaceIDs) { player, _, with ->
            if (with.asScenery().id == Scenery.FURNACE_26814 && !isDiaryComplete(player, DiaryType.VARROCK, 0)) {
                if (!GameWorld.settings!!.isMembers) {
                    sendNPCDialogue(player, NPCs.JEFFERY_6298, "Keep away from that! It's dangerous!")
                } else {
                    sendNPCDialogue(player, NPCs.JEFFERY_6298, "You want to use my furnace?", FacialExpression.HALF_ASKING)
                    addDialogueAction(player) { player, button ->
                        if(button >= 1) {
                            sendNPCDialogue(player, NPCs.JEFFERY_6298, "No one can use my furnace! Only I can use my furnace!", FacialExpression.ANNOYED)
                        }
                    }
                }
                return@onUseWith false
            }
            show(player)
            return@onUseWith true
        }

        /*
         * Handles base ore smelting interaction for tutorial island.
         */

        onUseWith(IntType.SCENERY, tutorialOres, Scenery.FURNACE_3044) { player, used, _ ->
            if (!anyInInventory(player, *tutorialOres)) {
                sendPlainDialogue(player,false, "<b>You do not have the required ores to make this bar.</b>")
                return@onUseWith false
            }
            if(removeItem(player, Item(Items.TIN_ORE_438, 1)) && removeItem(player, Item(Items.COPPER_ORE_436, 1))) {
                animate(player, smeltAnimation)
                playAudio(player, soundEffect, 1)
                queueScript(player, 4, QueueStrength.SOFT) {
                    addItem(player, Items.BRONZE_BAR_2349)
                    rewardXP(player, Skills.SMITHING, Bar.BRONZE.experience)
                    player.dispatch(ResourceProducedEvent(Bar.BRONZE.product.id, 1, used.asItem(), original = if(used.id != Items.TIN_ORE_438) Items.COPPER_ORE_436 else Items.TIN_ORE_438))
                    return@queueScript stopExecuting(player)
                }
            }
            return@onUseWith true
        }
    }

    companion object {
        private val smeltAnimation = Animation(Animations.HUMAN_FURNACE_SMELTING_3243)
        private const val soundEffect = Sounds.FURNACE_2725
        private val tutorialOres = intArrayOf(Items.TIN_ORE_438, Items.COPPER_ORE_436)
        private val specialFurnace = intArrayOf(Scenery.CLAY_FORGE_21303, Scenery.FURNACE_3044)
        val furnaceIDs = intArrayOf(Scenery.FURNACE_4304, Scenery.FURNACE_6189, Scenery.FURNACE_11010, Scenery.FURNACE_11666, Scenery.FURNACE_12100, Scenery.FURNACE_12809, Scenery.FURNACE_18497, Scenery.FURNACE_26814, Scenery.FURNACE_30021, Scenery.FURNACE_30510, Scenery.FURNACE_36956, Scenery.FURNACE_37651)
        private const val rejectMessage = "You need to have completed the easy tasks in the Varrock Diary in order to use this."

        val ids: IntArray
            get() {
                val ids: MutableList<Int> = ArrayList(10)
                for (bar in Bar.values()) {
                    for (i in bar.ores) {
                        ids.add(i.id)
                    }
                }
                val array = IntArray(ids.size)
                for (i in ids.indices) {
                    array[i] = ids[i]
                }
                return array
            }

        private fun show(player: Player) {
            openChatbox(player, Components.SMELTING_311)
            sendItemZoomOnInterface(player, Components.SMELTING_311, 4, Bar.BRONZE.product.id, 150)
            if (isQuestComplete(player, "The Knight's Sword")) {
                sendString(player, "<br><br><br><br>Blurite", Components.SMELTING_311, 20)
            }
            sendItemZoomOnInterface(player, Components.SMELTING_311, 5, Bar.BLURITE.product.id, 150)
            sendItemZoomOnInterface(player, Components.SMELTING_311, 6, Bar.IRON.product.id, 150)
            sendItemZoomOnInterface(player, Components.SMELTING_311, 7, Bar.SILVER.product.id, 150)
            sendItemZoomOnInterface(player, Components.SMELTING_311, 8, Bar.STEEL.product.id, 150)
            sendItemZoomOnInterface(player, Components.SMELTING_311, 9, Bar.GOLD.product.id, 150)
            sendItemZoomOnInterface(player, Components.SMELTING_311, 10, Bar.MITHRIL.product.id, 150)
            sendItemZoomOnInterface(player, Components.SMELTING_311, 11, Bar.ADAMANT.product.id, 150)
            sendItemZoomOnInterface(player, Components.SMELTING_311, 12, Bar.RUNITE.product.id, 150)
        }
    }
}
