package content.region.asgarnia.quest.recruitmentdrive

import content.region.asgarnia.dialogue.falador.KnightNotesDialogue
import content.region.asgarnia.quest.recruitmentdrive.dialogue.*
import core.api.*
import core.api.consts.Components
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.consts.Scenery
import core.game.activity.Cutscene
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.Entity
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.game.world.map.zone.ZoneBorders
import core.game.world.map.zone.ZoneRestriction
import core.network.packet.PacketRepository
import core.network.packet.context.MinimapStateContext
import core.network.packet.outgoing.MinimapState

class RecruitmentDriveListeners : InteractionListener, MapArea {

    override fun areaLeave(entity: Entity, logout: Boolean) {
        super.areaLeave(entity, logout)
        if (entity is Player && !entity.isArtificial) {
            val player = entity.asPlayer()
            if (!getRegionBorders(9805).insideRegion(player)) {
                clearInventoryAndEquipment(player)
            }
        }
    }

    override fun getRestrictions(): Array<ZoneRestriction> {
        return arrayOf(
            ZoneRestriction.CANNON,
            ZoneRestriction.FOLLOWERS,
            ZoneRestriction.RANDOM_EVENTS,
            ZoneRestriction.TELEPORT
        )
    }

    override fun defineAreaBorders(): Array<ZoneBorders> {
        return arrayOf(getRegionBorders(9805))
    }

    companion object {
        enum class Stages(val npc: Int, val startLocation: Location, val startWalkLocation: Location, val quitPortal: Int, val successDoor: Int) {
            SIR_SPISHYUS(NPCs.SIR_SPISHYUS_2282, Location(2490, 4972), Location(2489, 4972), Scenery.PORTAL_7272, Scenery.DOOR_7274),
            LADY_TABLE(NPCs.LADY_TABLE_2283, Location(2460, 4979), Location(2459, 4979), Scenery.PORTAL_7288, Scenery.DOOR_7302),
            SIR_KUAM_FERENTSE(NPCs.SIR_KUAM_FERENTSE_2284, Location(2455, 4964), Location(2456, 4964), Scenery.PORTAL_7315, Scenery.DOOR_7317),
            SIR_TINLEY(NPCs.SIR_TINLEY_2286, Location(2471, 4956), Location(2472, 4956), Scenery.PORTAL_7318, Scenery.DOOR_7320),
            SIR_REN_ITCHOOD(NPCs.SIR_REN_ITCHOOD_2287, Location(2439, 4956), Location(2440, 4956), Scenery.PORTAL_7321, Scenery.DOOR_7323),
            MISS_CHEEVERS(NPCs.MISS_CHEEVERS_2288, Location(2467, 4940), Location(2468, 4940), Scenery.PORTAL_7324, Scenery.DOOR_7326),
            MS_HYNN_TERPRETT(NPCs.MS_HYNN_TERPRETT_2289, Location(2451, 4935), Location(2451, 4936), Scenery.PORTAL_7352, Scenery.DOOR_7354);

            companion object {
                @JvmField
                val indexMap = values().associateBy { it.ordinal }
                val quitPortalArray = indexMap.values.map { it.quitPortal }.toIntArray()
                val successDoorArray = indexMap.values.map { it.successDoor }.toIntArray()
            }
        }

        fun shuffleStages(player: Player) {
            val stageArray = intArrayOf(0, 1, 2, 3, 4, 5, 6)
            stageArray.shuffle()
            setAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_STAGE_ZERO, stageArray[0])
            setAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_FIRST_STAGE, stageArray[1])
            setAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_SECOND_STAGE, stageArray[2])
            setAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_THIRD_STAGE, stageArray[3])
            setAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_FOURTH_STAGE, stageArray[4])
            setAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_STAGE_PASSED, false)
            setAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_STAGE_FAILED, false)
            setAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_CURRENT_STAGE, 0)
        }

        fun callStartingDialogues(player: Player, npc: Int) {
            when (npc) {
                NPCs.SIR_SPISHYUS_2282 -> openDialogue(player, SirSpishyusDialogueFile(1), NPC(npc))
                NPCs.LADY_TABLE_2283 -> openDialogue(player, LadyTableDialogueFile(1), NPC(npc))
                NPCs.SIR_KUAM_FERENTSE_2284 -> openDialogue(player, SirKuamFerentseDialogueFile(1), NPC(npc))
                NPCs.SIR_TINLEY_2286 -> openDialogue(player, SirTinleyDialogueFile(1), NPC(npc))
                NPCs.SIR_REN_ITCHOOD_2287 -> openDialogue(player, SirRenItchwoodDialogueFile(1), NPC(npc))
                NPCs.MISS_CHEEVERS_2288 -> openDialogue(player, MissCheeversDialogueFile(1), NPC(npc))
                NPCs.MS_HYNN_TERPRETT_2289 -> openDialogue(player, MsHynnTerprettDialogueFile(1), NPC(npc))
            }
        }
    }

    override fun defineListeners() {
        on(Items.NITROUS_OXIDE_5581, IntType.ITEM, "open") { player, _ ->
            sendMessage(player, "You uncork the vial...")
            queueScript(player, 0, QueueStrength.SOFT) { stage: Int ->
                when (stage) {
                    0 -> {
                        removeItem(player, Items.NITROUS_OXIDE_5581)
                        return@queueScript keepRunning(player)
                    }

                    1 -> {
                        addItem(player, Items.VIAL_229)
                        return@queueScript keepRunning(player)
                    }

                    2 -> {
                        sendMessage(player, "You smell a strange gas as it escapes from inside the vial.")
                        sendChat(player, "Hahahahahahaha!")
                        return@queueScript stopExecuting(player)
                    }

                    else -> return@queueScript stopExecuting(player)
                }
            }
            return@on true
        }

        onUseWith(IntType.NPC, Items.KNIGHTS_NOTES_11734, NPCs.SIR_TIFFY_CASHIEN_2290) { player, _, with ->
            openDialogue(player, KnightNotesDialogue(), with)
            return@onUseWith true
        }

        onUseWith(IntType.NPC, Items.KNIGHTS_NOTES_11735, NPCs.SIR_TIFFY_CASHIEN_2290) { player, _, with ->
            openDialogue(player, KnightNotesDialogue.BrokenKnightNotes(), with)
            return@onUseWith true
        }

        on(Stages.quitPortalArray, IntType.SCENERY, "use") { player, _ ->
            openDialogue(player, object : DialogueFile() {
                override fun handle(componentID: Int, buttonID: Int) {
                    npc = NPC(NPCs.SIR_TIFFY_CASHIEN_2290)
                    when (stage) {
                        0 -> sendDialogue(player, "Quit the training grounds?").also { stage++ }
                        1 -> options("YES.", "NO.").also { stage++ }
                        2 -> when (buttonID) {
                            1 -> {
                                end()
                                FailTestCutscene(player).start()
                            }

                            2 -> end()
                        }
                    }
                }
            })
            return@on true
        }

        on(Stages.successDoorArray, IntType.SCENERY, "open") { player, node ->
            if (inInventory(player, Items.BRONZE_KEY_5585)) {
                DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
                sendMessage(player, "You use the duplicate key you made to unlock the door.")
                setAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_STAGE_PASSED, true)
            }
            if (getAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_STAGE_PASSED, false)) {
                setAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_STAGE_PASSED, false)
                setAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_CURRENT_STAGE, getAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_CURRENT_STAGE, 0) + 1)
                val currentLevel = getAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_CURRENT_STAGE, 0)
                if (currentLevel >= 5) {
                    DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
                    face(player, node.asScenery())
                    CompleteTestCutscene(player).start()
                    return@on true
                }
                val currentStage = getAttribute(player, RecruitmentDrive.RD_STAGE_ARRAY[currentLevel], 0)
                val currentStageEnum = Stages.indexMap[currentStage]!!
                closeDialogue(player)
                clearInventory(player)
                queueScript(player, 1, QueueStrength.SOFT) { stage: Int ->
                    when (stage) {
                        0 -> {
                            DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
                            face(player, node.asScenery())
                            return@queueScript delayScript(player, 4)
                        }

                        1 -> {
                            teleport(player, currentStageEnum.startLocation)
                            return@queueScript delayScript(player, 2)
                        }

                        2 -> {
                            forceWalk(player, currentStageEnum.startWalkLocation, "dumb")
                            return@queueScript delayScript(player, 1)
                        }

                        3 -> {
                            callStartingDialogues(player, currentStageEnum.npc)
                            return@queueScript stopExecuting(player)
                        }

                        else -> return@queueScript stopExecuting(player)
                    }
                }
            } else {
                if (node.id == Scenery.DOOR_7323) {
                    openInterface(player, Components.RD_COMBOLOCK_285)
                } else {
                    sendMessage(player, "You have not completed this room's puzzle yet.")
                }
            }
            return@on true
        }

        setDest(IntType.SCENERY, intArrayOf(7345), "walk-through") { player, _ ->
            if (inBorders(player, 2476, 4941, 2477, 4939)) {
                return@setDest Location(2476, 4940, 0)
            } else if (inBorders(player, 2477, 4941, 2478, 4939)) {
                return@setDest Location(2478, 4940, 0)
            } else {
                Location(2478, 4940, 0)
            }
        }

        setDest(IntType.NPC, intArrayOf(NPCs.SIR_TIFFY_CASHIEN_2290), "talk-to") { _, _ ->
            return@setDest Location(2997, 3374, 0)
        }
    }

    class StartTestCutscene(player: Player) : Cutscene(player) {
        override fun setup() {
            loadRegion(9805)
            val currentStage = getAttribute(player, RecruitmentDrive.RD_STAGE_ARRAY[0], 0)
            setExit(Stages.indexMap[currentStage]!!.startLocation)
        }

        override fun runStage(stage: Int) {
            when (stage) {
                0 -> {
                    fadeToBlack()
                    PacketRepository.send(MinimapState::class.java, MinimapStateContext(player, 2))
                    timedUpdate(6)
                }

                1 -> {
                    dialogueLinesUpdate(NPCs.SIR_TIFFY_CASHIEN_2290, FacialExpression.HAPPY, "Here we go!", "Mind your head!")
                    timedUpdate(3)
                }

                2 -> {
                    dialogueLinesUpdate(NPCs.SIR_TIFFY_CASHIEN_2290, FacialExpression.HAPPY, "Oops. Ignore the smell!", "Nearly there!")
                    timedUpdate(3)
                }

                3 -> {
                    dialogueLinesUpdate(NPCs.SIR_TIFFY_CASHIEN_2290, FacialExpression.HAPPY, "And...", "Here we are!", "Best of luck!")
                    timedUpdate(3)
                }

                4 -> {
                    clearInventory(player)
                    endWithoutFade {
                        val currentStage = getAttribute(player, RecruitmentDrive.RD_STAGE_ARRAY[0], 0)
                        val firstStage = Stages.indexMap[currentStage]!!
                        queueScript(player, 0, QueueStrength.SOFT) { stage: Int ->
                            when (stage) {
                                0 -> {
                                    fadeFromBlack()
                                    return@queueScript delayScript(player, 3)
                                }

                                1 -> {
                                    forceWalk(player, firstStage.startWalkLocation, "dumb")
                                    return@queueScript delayScript(player, 2)
                                }

                                2 -> {
                                    unlock(player)
                                    callStartingDialogues(player, firstStage.npc)
                                    return@queueScript stopExecuting(player)
                                }

                                else -> return@queueScript stopExecuting(player)
                            }
                        }

                    }
                }
            }
        }
    }

    class FailTestCutscene(player: Player) : Cutscene(player) {
        override fun setup() {
            loadRegion(9805)
            setExit(Location(2996, 3375))
        }

        override fun runStage(stage: Int) {
            when (stage) {
                0 -> {
                    fadeToBlack()
                    PacketRepository.send(MinimapState::class.java, MinimapStateContext(player, 2))
                    timedUpdate(6)
                }

                1 -> {
                    var clearBoss = getAttribute(player, SirKuamFerentseDialogueFile.ATTRIBUTE_SPAWN_NPC, NPC(0))
                    if (clearBoss.id != 0) {
                        clearBoss.clear()
                    }
                    clearInventory(player)
                    queueScript(player, 0, QueueStrength.SOFT) { stage: Int ->
                        when (stage) {
                            0 -> {
                                fadeFromBlack()
                                return@queueScript delayScript(player, 2)
                            }

                            1 -> {
                                openDialogue(player, SirTiffyCashienFailedDialogueFile(), NPC(NPCs.SIR_TIFFY_CASHIEN_2290))
                                return@queueScript stopExecuting(player)
                            }

                            else -> return@queueScript stopExecuting(player)
                        }
                    }
                    endWithoutFade {
                        face(player, Location(2996, 3374))
                        fadeFromBlack()
                        player.interfaceManager.restoreTabs()
                    }
                }
            }
        }
    }

    class CompleteTestCutscene(player: Player) : Cutscene(player) {
        override fun setup() {
            loadRegion(9805)
            setExit(Location(2996, 3375))
        }

        override fun runStage(stage: Int) {
            when (stage) {
                0 -> {
                    if (getQuestStage(player, "Recruitment Drive") == 2) {
                        setQuestStage(player, "Recruitment Drive", 3)
                    }
                    closeDialogue(player)
                    fadeToBlack()
                    PacketRepository.send(MinimapState::class.java, MinimapStateContext(player, 2))
                    timedUpdate(6)
                }

                1 -> {
                    clearInventory(player)
                    queueScript(player, 0, QueueStrength.SOFT) { stage: Int ->
                        when (stage) {
                            0 -> {
                                fadeFromBlack()
                                return@queueScript delayScript(player, 2)
                            }
                            1 -> {
                                openDialogue(player, SirTiffyCashienDialogueFile(), NPC(NPCs.SIR_TIFFY_CASHIEN_2290))
                                return@queueScript stopExecuting(player)
                            }
                            else -> return@queueScript stopExecuting(player)
                        }
                    }
                    endWithoutFade {
                        face(player, Location(2996, 3374))
                        fadeFromBlack()
                    }
                }
            }
        }
    }
}
