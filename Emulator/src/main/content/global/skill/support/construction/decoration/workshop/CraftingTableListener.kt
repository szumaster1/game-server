package content.global.skill.support.construction.decoration.workshop

import cfg.consts.Items
import content.global.skill.support.construction.BuildingUtils
import content.global.skill.support.construction.Decoration
import core.api.*
import core.game.dialogue.Dialogue
import core.game.dialogue.DialogueInterpreter
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.node.scenery.Scenery

/**
 * Handles the interactions of the craft table in the workshop.
 * @author Hope
 */
class CraftingTableListener : InteractionListener {

    private val sceneryIDs = intArrayOf(13709, 13710, 13711, 13712)

    override fun defineListeners() {
        on(sceneryIDs, IntType.SCENERY, "craft") { player, node ->
            player.dialogueInterpreter.open(DialogueInterpreter.getDialogueKey("con:crafting-table"), node.asScenery())
            return@on true
        }
    }

    private enum class Craftable(val itemId: Int, val craftingLevel: Int, vararg var materials: Item) {
        /**
         * Toy horsey craftable.
         */
        TOY_HORSEY(
            itemId = Items.TOY_HORSEY_2520,
            craftingLevel = 10,
            BuildingUtils.PLANK
        ),

        /**
         * The Clockwork.
         */
        CLOCKWORK(
            itemId = Items.CLOCKWORK_8792,
            craftingLevel = 8,
            Item(Items.STEEL_BAR_2353)
        ),

        /**
         * The Toy soldier.
         */
        TOY_SOLDIER(
            itemId = Items.TOY_SOLDIER_7759,
            craftingLevel = 13,
            BuildingUtils.PLANK,
            Item(Items.CLOCKWORK_8792)
        ),

        /**
         * The Toy doll.
         */
        TOY_DOLL(
            itemId = Items.TOY_DOLL_7763,
            craftingLevel = 18,
            BuildingUtils.PLANK,
            Item(Items.CLOCKWORK_8792)
        ),

        /**
         * The Toy mouse.
         */
        TOY_MOUSE(
            itemId = Items.TOY_MOUSE_7767,
            craftingLevel = 33,
            BuildingUtils.PLANK,
            Item(Items.CLOCKWORK_8792)
        ),

        /**
         * The Toy cat.
         */
        TOY_CAT(
            itemId = Items.CLOCKWORK_CAT_7771,
            craftingLevel = 85,
            BuildingUtils.PLANK,
            Item(Items.CLOCKWORK_8792)
        ),

        /**
         * The Watch.
         */
        WATCH(
            itemId = Items.WATCH_2575,
            craftingLevel = 28,
            Item(Items.CLOCKWORK_8792),
            Item(Items.STEEL_BAR_2353)
        ),

        /**
         * The Sextant.
         */
        SEXTANT(
            itemId = Items.SEXTANT_2574,
            craftingLevel = 23,
            Item(Items.STEEL_BAR_2353)
        );
    }

    private inner class CraftingTableDialogue(player: Player? = null): Dialogue(player) {

        var decoration: Decoration? = null

        override fun open(vararg args: Any): Boolean {
            val scenery = args[0] as Scenery
            decoration = Decoration.forObjectId(scenery.id)
            decoration?.let {
                when (it) {
                    Decoration.CRAFTING_TABLE_1 -> options("Toy Horsey", "Nevermind")
                    Decoration.CRAFTING_TABLE_2 -> options("Toy Horsey", "Clockwork Mechanism")
                    Decoration.CRAFTING_TABLE_3 -> options("Toy Horsey", "Clockwork Mechanism", "Clockwork Devices")
                    Decoration.CRAFTING_TABLE_4 -> options(
                        "Toy Horsey",
                        "Clockwork Mechanism",
                        "Clockwork Devices",
                        "Watch",
                        "Sextant"
                    )

                    else -> {}
                }
            }
            stage = 1
            return true
        }

        override fun handle(interfaceId: Int, buttonId: Int): Boolean {
            when (stage) {
                1 -> handleFirstOption(buttonId)
                2 -> handleSecondOption(buttonId)
                3 -> end()
            }
            return true
        }

        private fun handleFirstOption(buttonId: Int) {
            when (buttonId) {
                1, 2 -> {
                    if (decoration == Decoration.CRAFTING_TABLE_1 && buttonId == 2) {
                        end()
                        return
                    }
                    craftItem(if (buttonId == 1) Craftable.TOY_HORSEY else Craftable.CLOCKWORK)
                    stage = 3
                }

                3 -> {
                    if (decoration == Decoration.CRAFTING_TABLE_3) {
                        options("Clockwork Soldier", "Clockwork Doll")
                    } else if (decoration == Decoration.CRAFTING_TABLE_4) {
                        options("Clockwork Soldier", "Clockwork Doll", "Clockwork Mouse", "Clockwork Cat")
                    }
                    stage = 2
                }

                4, 5 -> {
                    craftItem(if (buttonId == 4) Craftable.WATCH else Craftable.SEXTANT)
                    stage = 3
                }
            }
        }

        private fun handleSecondOption(buttonId: Int) {
            when (buttonId) {
                1 -> craftItem(Craftable.TOY_SOLDIER)
                2 -> craftItem(Craftable.TOY_DOLL)
                3 -> craftItem(Craftable.TOY_MOUSE)
                4 -> craftItem(Craftable.TOY_CAT)
            }
            stage = 3
        }

        private fun craftItem(item: Craftable?) {
            item?.let {
                if (getStatLevel(player, Skills.CRAFTING) < it.craftingLevel) {
                    sendDialogue(player, "You need level ${it.craftingLevel} crafting to make that.")
                    return
                }
                for (material in it.materials) {
                    if (!inInventory(player, material.id)) {
                        sendDialogue(player, "You need a ${getItemName(material.id)} to make that!")
                        return
                    }
                }
                it.materials.forEach { material ->
                    material.amount = 1
                    removeItem(player, material)
                }
                rewardXP(player, Skills.CRAFTING, 15.0)
                addItem(player, it.itemId, 1)
                animate(player, BuildingUtils.BUILD_MID_ANIM)
                sendDialogue(player, "You made a ${getItemName(it.itemId)}!")
            }
        }

        override fun newInstance(player: Player?): Dialogue {
            return CraftingTableDialogue(player)
        }

        override fun getIds(): IntArray {
            return intArrayOf(DialogueInterpreter.getDialogueKey("con:crafting-table"))
        }
    }
}
