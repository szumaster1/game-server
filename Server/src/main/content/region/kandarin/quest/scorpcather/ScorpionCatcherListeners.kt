package content.region.kandarin.quest.scorpcather

import content.region.kandarin.quest.scorpcather.dialogue.SeersDialogueFile
import content.region.kandarin.quest.scorpcather.dialogue.ThormacDialogueFile
import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.system.config.NPCConfigParser
import core.game.system.task.Pulse
import core.game.world.GameWorld


/**
 * Scorpion catcher listeners
 *
 * @constructor Scorpion catcher listeners
 */
class ScorpionCatcherListeners : InteractionListener {

    override fun defineListeners() {

        val haveAlready = "You already have this scorpion in this cage."
        val catchMessage = "You catch a scorpion!"

        onUseWith(IntType.NPC, Items.SCORPION_CAGE_456, NPCs.KHARID_SCORPION_386){ player, used, with ->
            removeItem(player, Item(used.id, 1))
            addItem(player, Items.SCORPION_CAGE_457)
            player.sendMessage(catchMessage)
            if (!player.getAttribute(ScorpionCatcher.ATTRIBUTE_TAVERLY, false)){
                player.setAttribute("/save:${ScorpionCatcher.ATTRIBUTE_TAVERLY}", true)
            }
            runTask(player, 2) {
                with.asNpc().respawnTick =
                    GameWorld.ticks + with.asNpc().definition.getConfiguration(NPCConfigParser.RESPAWN_DELAY, 34)
            }
            return@onUseWith true
        }

        onUseWith(IntType.NPC, Items.SCORPION_CAGE_459, NPCs.KHARID_SCORPION_386){ player, used, with ->
            removeItem(player, Item(used.id, 1))
            addItem(player, Items.SCORPION_CAGE_458)
            player.sendMessage(catchMessage)
            if (!player.getAttribute(ScorpionCatcher.ATTRIBUTE_TAVERLY, false)){
                player.setAttribute("/save:${ScorpionCatcher.ATTRIBUTE_TAVERLY}", true)
            }
            runTask(player, 2) {
                with.asNpc().respawnTick =
                    GameWorld.ticks + with.asNpc().definition.getConfiguration(NPCConfigParser.RESPAWN_DELAY, 34)
            }
            return@onUseWith true
        }

        onUseWith(IntType.NPC, Items.SCORPION_CAGE_461, NPCs.KHARID_SCORPION_386){ player, used, with ->
            removeItem(player, Item(used.id, 1))
            addItem(player, Items.SCORPION_CAGE_462)
            player.sendMessage(catchMessage)
            if (!player.getAttribute(ScorpionCatcher.ATTRIBUTE_TAVERLY, false)){
                player.setAttribute("/save:${ScorpionCatcher.ATTRIBUTE_TAVERLY}", true)
            }
            runTask(player, 2) {
                with.asNpc().respawnTick =
                    GameWorld.ticks + with.asNpc().definition.getConfiguration(NPCConfigParser.RESPAWN_DELAY, 34)
            }
            return@onUseWith true
        }

        onUseWith(IntType.NPC, Items.SCORPION_CAGE_460, NPCs.KHARID_SCORPION_386){ player, used, with ->
            removeItem(player, Item(used.id, 1))
            addItem(player, Items.SCORPION_CAGE_463)
            player.sendMessage(catchMessage)
            if (!player.getAttribute(ScorpionCatcher.ATTRIBUTE_TAVERLY, false)){
                player.setAttribute("/save:${ScorpionCatcher.ATTRIBUTE_TAVERLY}", true)
            }
            runTask(player, 2) {
                with.asNpc().respawnTick =
                    GameWorld.ticks + with.asNpc().definition.getConfiguration(NPCConfigParser.RESPAWN_DELAY, 34)
            }
            return@onUseWith true
        }

        onUseWith(IntType.NPC, Items.SCORPION_CAGE_456, NPCs.KHARID_SCORPION_385){ player, used, with ->
            removeItem(player, Item(used.id, 1))
            addItem(player, Items.SCORPION_CAGE_459)
            // This is the first time barbarian has been caught
            if (!player.getAttribute(ScorpionCatcher.ATTRIBUTE_BARBARIAN, false)){
                player.setAttribute("/save:${ScorpionCatcher.ATTRIBUTE_BARBARIAN}", true)
            }
            runTask(player, 2) {
                with.asNpc().respawnTick =
                    GameWorld.ticks + with.asNpc().definition.getConfiguration(NPCConfigParser.RESPAWN_DELAY, 34)
            }
            return@onUseWith true
        }

        onUseWith(IntType.NPC, Items.SCORPION_CAGE_457, NPCs.KHARID_SCORPION_385) { player, used, with ->
            removeItem(player, Item(used.id, 1))
            addItem(player, Items.SCORPION_CAGE_458)
            player.sendMessage(catchMessage)
            // This is the first time barbarian has been caught
            if (!player.getAttribute(ScorpionCatcher.ATTRIBUTE_BARBARIAN, false)){
                player.setAttribute("/save:${ScorpionCatcher.ATTRIBUTE_BARBARIAN}", true)
            }
            runTask(player, 2) {
                with.asNpc().respawnTick =
                    GameWorld.ticks + with.asNpc().definition.getConfiguration(NPCConfigParser.RESPAWN_DELAY, 34)
            }
            return@onUseWith true
        }

        onUseWith(IntType.NPC, Items.SCORPION_CAGE_461, NPCs.KHARID_SCORPION_385) { player, used, with ->
            removeItem(player, Item(used.id, 1))
            addItem(player, Items.SCORPION_CAGE_460)
            player.sendMessage(catchMessage)
            if (!player.getAttribute(ScorpionCatcher.ATTRIBUTE_BARBARIAN, false)){
                player.setAttribute("/save:${ScorpionCatcher.ATTRIBUTE_BARBARIAN}", true)
            }
            runTask(player, 2) {
                with.asNpc().respawnTick =
                    GameWorld.ticks + with.asNpc().definition.getConfiguration(NPCConfigParser.RESPAWN_DELAY, 34)
            }
            return@onUseWith true
        }

        onUseWith(IntType.NPC, Items.SCORPION_CAGE_462, NPCs.KHARID_SCORPION_385) { player, used, with ->
            removeItem(player, Item(used.id, 1))
            addItem(player, Items.SCORPION_CAGE_463)
            player.sendMessage(catchMessage)
            // This is the first time barbarian has been caught
            if (!player.getAttribute(ScorpionCatcher.ATTRIBUTE_BARBARIAN, false)){
                player.setAttribute("/save:${ScorpionCatcher.ATTRIBUTE_BARBARIAN}", true)
            }
            runTask(player, 2) {
                with.asNpc().respawnTick =
                    GameWorld.ticks + with.asNpc().definition.getConfiguration(NPCConfigParser.RESPAWN_DELAY, 34)
            }
            return@onUseWith true
        }

        onUseWith(IntType.NPC, Items.SCORPION_CAGE_456, NPCs.KHARID_SCORPION_387) { player, used, with ->
            removeItem(player, Item(used.id, 1))
            addItem(player, Items.SCORPION_CAGE_461)
            player.sendMessage(catchMessage)
            if (!player.getAttribute(ScorpionCatcher.ATTRIBUTE_MONK, false)){
                player.setAttribute("/save:${ScorpionCatcher.ATTRIBUTE_MONK}", true)
            }
            runTask(player, 2) {
                with.asNpc().respawnTick =
                    GameWorld.ticks + with.asNpc().definition.getConfiguration(NPCConfigParser.RESPAWN_DELAY, 34)
            }
            return@onUseWith true
        }

        onUseWith(IntType.NPC, Items.SCORPION_CAGE_457, NPCs.KHARID_SCORPION_387) { player, used, with ->
            removeItem(player, Item(used.id, 1))
            addItem(player, Items.SCORPION_CAGE_462)
            player.sendMessage(catchMessage)
            if (!player.getAttribute(ScorpionCatcher.ATTRIBUTE_MONK, false)){
                player.setAttribute("/save:${ScorpionCatcher.ATTRIBUTE_MONK}", true)
            }
            runTask(player, 2) {
                with.asNpc().respawnTick =
                    GameWorld.ticks + with.asNpc().definition.getConfiguration(NPCConfigParser.RESPAWN_DELAY, 34)
            }
            return@onUseWith true
        }

        onUseWith(IntType.NPC, Items.SCORPION_CAGE_459, NPCs.KHARID_SCORPION_387) { player, used, with ->
            removeItem(player, Item(used.id, 1))
            addItem(player, Items.SCORPION_CAGE_460)
            player.sendMessage(catchMessage)
            if (!player.getAttribute(ScorpionCatcher.ATTRIBUTE_MONK, false)){
                player.setAttribute("/save:${ScorpionCatcher.ATTRIBUTE_MONK}", true)
            }
            runTask(player, 2) {
                with.asNpc().respawnTick =
                    GameWorld.ticks + with.asNpc().definition.getConfiguration(NPCConfigParser.RESPAWN_DELAY, 34)
            }
            return@onUseWith true
        }

        onUseWith(IntType.NPC, Items.SCORPION_CAGE_458, NPCs.KHARID_SCORPION_387) { player, used, with ->
            removeItem(player, Item(used.id, 1))
            addItem(player, Items.SCORPION_CAGE_463)
            player.sendMessage(catchMessage)
            if (!player.getAttribute(ScorpionCatcher.ATTRIBUTE_MONK, false)){
                player.setAttribute("/save:${ScorpionCatcher.ATTRIBUTE_MONK}", true)
            }
            runTask(player, 2) {
                with.asNpc().respawnTick =
                    GameWorld.ticks + with.asNpc().definition.getConfiguration(NPCConfigParser.RESPAWN_DELAY, 34)
            }
            return@onUseWith true
        }

        // Just Taverly
        onUseWith(IntType.NPC, Items.SCORPION_CAGE_457, NPCs.KHARID_SCORPION_386){ player, _, _ ->
            player.sendMessage(haveAlready)
            return@onUseWith true
        }
        // Taverly and Barb
        onUseWith(IntType.NPC, Items.SCORPION_CAGE_458, NPCs.KHARID_SCORPION_386){ player, _, _ ->
            player.sendMessage(haveAlready)
            return@onUseWith true
        }
        // Taverly and Monk
        onUseWith(IntType.NPC, Items.SCORPION_CAGE_462, NPCs.KHARID_SCORPION_386){ player, _, _ ->
            player.sendMessage(haveAlready)
            return@onUseWith true
        }
        // All
        onUseWith(IntType.NPC, Items.SCORPION_CAGE_463, NPCs.KHARID_SCORPION_386){ player, _, _ ->
            player.sendMessage(haveAlready)
            return@onUseWith true
        }
        // Just Barb
        onUseWith(IntType.NPC, Items.SCORPION_CAGE_459, NPCs.KHARID_SCORPION_385){ player, _, _ ->
            player.sendMessage(haveAlready)
            return@onUseWith true
        }
        // Barb and Taverly
        onUseWith(IntType.NPC, Items.SCORPION_CAGE_458, NPCs.KHARID_SCORPION_385){ player, _, _ ->
            player.sendMessage(haveAlready)
            return@onUseWith true
        }
        // Barb and Monk
        onUseWith(IntType.NPC, Items.SCORPION_CAGE_460, NPCs.KHARID_SCORPION_385){ player, _, _ ->
            player.sendMessage(haveAlready)
            return@onUseWith true
        }
        // All
        onUseWith(IntType.NPC, Items.SCORPION_CAGE_463, NPCs.KHARID_SCORPION_385){ player, _, _ ->
            player.sendMessage(haveAlready)
            return@onUseWith true
        }
        // Just Monk
        onUseWith(IntType.NPC, Items.SCORPION_CAGE_461, NPCs.KHARID_SCORPION_387){ player, _, _ ->
            player.sendMessage(haveAlready)
            return@onUseWith true
        }
        // Monk and Taverly
        onUseWith(IntType.NPC, Items.SCORPION_CAGE_462, NPCs.KHARID_SCORPION_387){ player, _, _ ->
            player.sendMessage(haveAlready)
            return@onUseWith true
        }
        // Monk and Barb
        onUseWith(IntType.NPC, Items.SCORPION_CAGE_460, NPCs.KHARID_SCORPION_387){ player, _, _ ->
            player.sendMessage(haveAlready)
            return@onUseWith true
        }
        // All
        onUseWith(IntType.NPC, Items.SCORPION_CAGE_463, NPCs.KHARID_SCORPION_387){ player, _, _ ->
            player.sendMessage(haveAlready)
            return@onUseWith true
        }

    }

    companion object {

        fun getScorpionLocation(player: Player) {
            submitIndividualPulse(player, object : Pulse() {
                var counter = 0
                override fun pulse(): Boolean {
                    when (counter++) {
                        1 -> sendMessage(player, "The seer produces a small mirror.")
                        3 -> sendMessage(player, "The seer gazes into the mirror.")
                        6 -> sendMessage(player, "The seer smoothes his hair with his hand.")
                        9 -> {
                            setAttribute(player, ScorpionCatcher.ATTRIBUTE_MIRROR, true)
                            setAttribute(player, ScorpionCatcher.ATTRIBUTE_SECRET, true)
                            openDialogue(player, SeersDialogueFile())
                            return true
                        }
                    }
                    return false
                }
            })
        }

        fun getCage(player: Player) {
            submitIndividualPulse(player, object : Pulse() {
                var counter = 0
                override fun pulse(): Boolean {
                    when (counter++) {
                        0 -> {
                            sendMessage(player, "Thormac gives you a cage.")
                            addItemOrDrop(player, Items.SCORPION_CAGE_456)
                        }

                        2 -> {
                            setAttribute(player, ScorpionCatcher.ATTRIBUTE_CAGE, true)
                            openDialogue(player, ThormacDialogueFile())
                            return true
                        }
                    }
                    return false
                }
            })
        }
    }
}