package content.region.desert.quest.deserttreasure.handlers

import content.region.desert.quest.deserttreasure.DesertTreasure
import content.region.desert.quest.deserttreasure.dialogue.ChatFatherAndMotherTrollDialogueFile
import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.consts.Scenery
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.Entity
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.game.world.map.zone.ZoneBorders
import core.tools.END_DIALOGUE

/**
 * Represents the Diamond of ice listeners.
 */
class DiamondOfIceListeners : InteractionListener {

    override fun defineListeners() {
        onUseWith(IntType.NPC, Items.CHOCOLATE_CAKE_1897, NPCs.BANDIT_1932 /* should be NPCs.TROLL_CHILD_1932 */) { player, used, with ->
            if (removeItem(player, used)) {
                if (DesertTreasure.getSubStage(player, DesertTreasure.attributeIceStage) == 0) {
                    DesertTreasure.setSubStage(player, DesertTreasure.attributeIceStage, 1)
                }
                openDialogue(player, object : DialogueFile() {
                    override fun handle(componentID: Int, buttonID: Int) {
                        when (stage) {
                            0 -> player("Hey there little troll...", "Take this and dry those tears...").also { stage++ }
                            1 -> npc(FacialExpression.OLD_NEARLY_CRYING, "(sniff)").also {
                                stage = END_DIALOGUE
                            }
                        }
                    }
                }, with as NPC)
            }
            return@onUseWith true
        }

        on(intArrayOf(Scenery.ICE_GATE_5043, Scenery.ICE_GATE_5044), SCENERY, "go-through") { player, node ->

            if ((getQuestStage(player, DesertTreasure.questName) == 9 &&
                            DesertTreasure.getSubStage(player, DesertTreasure.attributeIceStage) > 1) ||
                    getQuestStage(player, DesertTreasure.questName) > 10) {
                sendMessage(player, "You squeeze through the large icy bars of the gate.")
                // Anim 3272 to squeeze through?
                if(player.location.x > 2838) {
                    teleport(player, Location(2837, 3739, 0))
                } else {
                    teleport(player, Location(2839, 3739, 0))
                }
            } else {
                // j_SdwOX1JWg
                sendDialogueLines(player, "The bars are frozen tightly shut and a sturdy layer of ice prevents", "you from slipping through.")
            }
            return@on true
        }

        on(Scenery.CAVE_ENTRANCE_6441, SCENERY, "enter") { player, node ->
            lock(player, 3)
            animate(player, 2796) // Crawling
            queueScript(player, 3, QueueStrength.SOFT) {
                teleport(player, Location(2874, 3720, 0))
                return@queueScript stopExecuting(player)
            }
            return@on true
        }

        on(Scenery.CAVE_ENTRANCE_6446, SCENERY, "enter") { player, node ->
            sendMessage(player, "The entrance to the cave is covered in too much ice to get through.")
            return@on true
        }

        on(Scenery.CAVE_EXIT_6447, SCENERY, "enter") { player, node ->
            lock(player, 3)
            animate(player, 2796) // Crawling
            queueScript(player, 3, QueueStrength.SOFT) {
                teleport(player, Location(2867, 3719, 0))
                return@queueScript stopExecuting(player)
            }
            return@on true
        }

        on(Scenery.ICE_LEDGE_6455, SCENERY, "use") { player, node ->
            if (inEquipment(player, Items.SPIKED_BOOTS_3107)) {
                teleport(player, Location(2838, 3803, 1))
            } else {
                sendPlayerDialogue(player, "I don't think I'll make much headway along that icy slope without some spiked boots...")
            }
            return@on true
        }

        on(intArrayOf(Scenery.ICE_GATE_6461, Scenery.ICE_GATE_6462), SCENERY, "go-through") { player, node ->

            teleport(player, Location(2852, 3810, 2))
            return@on true
        }

        on(NPCs.ICE_BLOCK_1944, NPC, "talk-to") { player, node ->
            sendDialogueLines(player, "There is a thick layer of ice covering this troll.", "You will have to find some way of shattering it.")
            return@on true
        }

        on(NPCs.ICE_BLOCK_1944, NPC, "smash-ice") { player, _ ->
            setVarbit(player, DesertTreasure.varbitFrozenFather, 1)
            if (getVarbit(player, DesertTreasure.varbitFrozenMother) == 1) {
                setVarbit(player, DesertTreasure.varbitChildReunite, 4)
                teleport(player, Location(2836, 3739, 0))
                if (DesertTreasure.getSubStage(player, DesertTreasure.attributeIceStage) == 3) {
                    addItemOrDrop(player, Items.ICE_DIAMOND_4671)
                    DesertTreasure.setSubStage(player, DesertTreasure.attributeIceStage, 100)
                }
            }
            return@on true
        }

        on(NPCs.ICE_BLOCK_1946, NPC, "talk-to") { player, node ->
            sendDialogueLines(player, "There is a thick layer of ice covering this troll.", "You will have to find some way of shattering it.")
            return@on true
        }
        on(NPCs.ICE_BLOCK_1946, NPC, "smash-ice") { player, _ ->
            setVarbit(player, DesertTreasure.varbitFrozenMother, 1)
            if (getVarbit(player, DesertTreasure.varbitFrozenFather) == 1) {
                setVarbit(player, DesertTreasure.varbitChildReunite, 4)
                openDialogue(player, ChatFatherAndMotherTrollDialogueFile())
                //teleport(player, Location(2836, 3739, 0))

            }
            return@on true
        }
    }
}

/**
 * Represents the Ice dungeon warning
 *
 * @constructor Create empty Ice dungeon warning
 */
class IceDungeonWarning : MapArea {
    override fun defineAreaBorders(): Array<ZoneBorders> {
        return arrayOf(ZoneBorders(2880, 3759, 2881, 3756))
    }

    override fun areaEnter(entity: Entity) {
        if (entity is Player &&
                getQuestStage(entity, DesertTreasure.questName) == 9 &&
                DesertTreasure.getSubStage(entity, DesertTreasure.attributeIceStage) == 2) {
            sendMessage(entity, "You can feel an evil presence nearby...")
        }
    }
}

/**
 * Represents the Comical tripping ice area
 *
 * @constructor Create empty Comical tripping ice area
 */
class ComicalTrippingIceArea : MapArea {
    override fun defineAreaBorders(): Array<ZoneBorders> {
        return arrayOf(
                ZoneBorders(2815, 3775, 2880, 3839, 1),
                ZoneBorders(2815, 3775, 2880, 3839, 2)
        )
    }

    override fun entityStep(entity: Entity, location: Location, lastLocation: Location) {
        if (entity is Player) {
            if ((1..10).random() == 1) {
                lock(entity, 2)
                stopWalk(entity)
                animate(entity, 767)
            }
        }
    }
}

/**
 * Represents the Ice area attack
 *
 * @constructor Create empty Ice area attack
 */
class IceAreaAttack : MapArea {
    override fun defineAreaBorders(): Array<ZoneBorders> {
        return arrayOf(ZoneBorders(2874, 3753, 2874, 3766))
    }

    override fun areaEnter(entity: Entity) {
        if (entity is Player &&
                getQuestStage(entity, DesertTreasure.questName) == 9 &&
                DesertTreasure.getSubStage(entity, DesertTreasure.attributeIceStage) == 2 &&
                getAttribute<NPC?>(entity, DesertTreasure.attributeKamilInstance, null) == null
        ) {
            val npc = NPC.create(NPCs.KAMIL_1913, Location(2857, 3754, 0))
            setAttribute(entity, DesertTreasure.attributeKamilInstance, npc)
            setAttribute(npc, "target", entity)
            npc.isRespawn = false
            npc.init()
            npc.attack(entity)
        }
    }
}
