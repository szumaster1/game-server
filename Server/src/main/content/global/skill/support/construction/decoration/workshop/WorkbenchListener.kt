package content.global.skill.support.construction.decoration.workshop

import content.global.skill.support.construction.BuildHotspot
import content.global.skill.support.construction.BuildingUtils
import core.api.*
import core.api.consts.Components
import core.api.consts.Scenery
import core.game.dialogue.DialogueFile
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.InterfaceListener
import core.game.world.update.flag.context.Animation

class WorkbenchListener : InteractionListener, InterfaceListener {


    private val workbenchId = intArrayOf(
        Scenery.WORKBENCH_13704,
        Scenery.WORKBENCH_13705,
        Scenery.WORKBENCH_13706,
        Scenery.WORKBENCH_13707,
        Scenery.WORKBENCH_13708,
    )

    override fun defineListeners() {
        on(workbenchId, IntType.SCENERY, "work-at", "upgrade") { player, node ->
            val option = getUsedOption(player)

            if (option == "work-at") {
                animate(player, 4103)
                animate(player, if (node.id == 13704) Animation(4107, 1) else Animation(4108, 1), true)
                setAttribute(player, "con:using-workbench", true)
                openInterface(player, Components.POH_WORKBENCH_397)
                return@on true
            }

            if (option == "upgrade") {
                openDialogue(player, object : DialogueFile() {
                    override fun handle(componentID: Int, buttonID: Int) {
                        when (stage) {
                            0 -> sendDialogueOptions(player, "Are you sure?", "Yes I am.", "No I'm not.").also { stage++ }

                            1 -> when (buttonID) {
                                1 -> {
                                    end()
                                    BuildingUtils.openBuildInterface(player, BuildHotspot.WORKBENCH)
                                    BuildingUtils.removeDecoration(player, node.asScenery())
                                }

                                2 -> end()
                            }
                        }
                    }
                })
            }
            return@on true
        }
    }

    override fun defineInterfaceListeners() {
        on(Components.POH_WORKBENCH_397) { player, _, _, buttonId, _, _ ->
            when (buttonId) {
                111 -> BuildingUtils.openBuildInterface(player, BuildHotspot.CHAIRS_3)
                112 -> BuildingUtils.openBuildInterface(player, BuildHotspot.BOOKCASE)
                113 -> BuildingUtils.openBuildInterface(player, BuildHotspot.BARRELS)
                114 -> BuildingUtils.openBuildInterface(player, BuildHotspot.KITCHEN_TABLE)
                115 -> BuildingUtils.openBuildInterface(player, BuildHotspot.DINING_TABLE)
                116 -> BuildingUtils.openBuildInterface(player, BuildHotspot.DINING_BENCH_1)
                117 -> BuildingUtils.openBuildInterface(player, BuildHotspot.BED)
                118 -> BuildingUtils.openBuildInterface(player, BuildHotspot.DRESSER)
                119 -> BuildingUtils.openBuildInterface(player, BuildHotspot.DRAWERS)
                120 -> BuildingUtils.openBuildInterface(player, BuildHotspot.CLOCK)
                121 -> BuildingUtils.openBuildInterface(player, BuildHotspot.CAPE_RACK)
                122 -> BuildingUtils.openBuildInterface(player, BuildHotspot.MAGIC_WARDROBE)
                123 -> BuildingUtils.openBuildInterface(player, BuildHotspot.ARMOUR_CASE)
                124 -> BuildingUtils.openBuildInterface(player, BuildHotspot.TREASURE_CHEST)
                125 -> BuildingUtils.openBuildInterface(player, BuildHotspot.COSTUME_BOX)
                126 -> BuildingUtils.openBuildInterface(player, BuildHotspot.TOY_BOX)
            }
            return@on true
        }
    }
}