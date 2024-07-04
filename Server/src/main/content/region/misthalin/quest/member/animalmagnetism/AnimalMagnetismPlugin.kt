package content.region.misthalin.quest.member.animalmagnetism

import content.data.skill.SkillingTool.Companion.getHatchet
import core.api.*
import core.api.consts.Components
import core.api.consts.Sounds
import core.cache.def.impl.ItemDefinition
import core.cache.def.impl.NPCDefinition
import core.cache.def.impl.SceneryDefinition
import core.game.component.Component
import core.game.component.ComponentDefinition
import core.game.component.ComponentPlugin
import core.game.interaction.NodeUsageEvent
import core.game.interaction.OptionHandler
import core.game.interaction.UseWithHandler
import core.game.node.Node
import core.game.node.entity.impl.Animator.Priority
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.map.zone.MapZone
import core.game.world.map.zone.ZoneBorders
import core.game.world.map.zone.ZoneBuilder
import core.game.world.update.flag.context.Animation
import core.plugin.ClassScanner.definePlugin
import core.plugin.Initializable
import core.plugin.Plugin
import core.tools.RandomFunction

@Initializable
class AnimalMagnetismPlugin : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        ItemDefinition.forId(688).handlers["option:polish"] = this
        ItemDefinition.forId(4251).handlers["option:empty"] = this
        ItemDefinition.forId(4251).handlers["option:drop"] = this
        ItemDefinition.forId(4252).handlers["option:drop"] = this
        NPCDefinition.forId(5198).handlers["option:trade"] = this
        SceneryDefinition.forId(5167).handlers["option:push"] = this
        AnimalMagnetism.RESEARCH_NOTES.definition.handlers["option:translate"] = this
        ItemDefinition.forId(AnimalMagnetism.CRONE_AMULET.id).handlers["option:wear"] = this
        ItemDefinition.forId(AnimalMagnetism.CRONE_AMULET.id).handlers["option:equip"] = this
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        when (node.id) {
            5167 -> {
                if (!hasRequirement(player, "Creature of Fenkenstrain")) {
                    return true
                }
                teleport(player, Location(3577, 9927))
            }

            5198, 5199 -> {
                if (player.getQuestRepository().getQuest(AnimalMagnetism.NAME).getStage(player) == 0) {
                    player.dialogueInterpreter.sendDialogues(node as NPC, null, "Hello there, I'm busy with my research. Come back in a", "bit, could you?")
                    return true
                }
                openNpcShop(player, node.id)
            }

            10500 -> sendMessage(player,"Perhaps you should wait a few hundred years or so?")
            10492 -> open(player)
            688 -> {
                lock(player, 1)
                if (getStatLevel(player, Skills.CRAFTING) < 3) {
                    sendMessage(player,"You need a Crafting level of at least 3 in order to do that.")
                    return true
                }
                rewardXP(player, Skills.CRAFTING, 5.0)
                player.inventory.replace(AnimalMagnetism.POLISHED_BUTTONS, (node as Item).slot)
            }
        }
        return true
    }

    private fun clearCache(player: Player) {
        removeAttribute(player, "note-cache")
        removeAttribute(player, "note-disabled")
    }

    fun open(player: Player) {
        clearCache(player)
        openInterface(player, Components.ANMA_RGB_480)
        sendMessage(player, "You fiddle with the notes.")
    }

    /*
     * Handles the hammering of a magnet.
     */

    class HammerMagnetPlugin : UseWithHandler(2347) {

        override fun newInstance(arg: Any?): Plugin<Any> {
            ZoneBuilder.configure(object : MapZone("rimmington mine", true) {
                override fun configure() {
                    register(ZoneBorders(2970, 3230, 2984, 3249))
                }
            })
            addHandler(AnimalMagnetism.SELECTED_IRON.id, ITEM_TYPE, this)
            return this
        }

        override fun handle(event: NodeUsageEvent): Boolean {
            val player = event.player
            animate(player, ANIMATION)
            lock(player, ANIMATION.definition.durationTicks)
            Pulser.submit(object : Pulse(ANIMATION.definition.durationTicks, player) {
                override fun pulse(): Boolean {
                    if (!player.zoneMonitor.isInZone("rimmington mine")) {
                        sendMessage(player,"You aren't in the right area for this to work.")
                    } else {
                        if (player.direction != Direction.NORTH) {
                            sendMessage(player,"You think that facing North might work better.")
                        } else {
                            player.inventory.replace(AnimalMagnetism.BAR_MAGNET, event.usedItem.slot)
                            sendMessage(player,"You hammer the iron bar and create a magnet.")
                        }
                    }
                    return true
                }
            })
            return true
        }

        companion object {
            private val ANIMATION = Animation(5365)
        }
    }

    /*
     * Handles the axe on a undead tree.
     */

    class UndeadTreePlugin : UseWithHandler(1355, 1357, 1359, 6739) {

        private val IDS = intArrayOf(1355, 1357, 1359, 6739)


        override fun newInstance(arg: Any?): Plugin<Any> {
            definePlugin(object : OptionHandler() {

                override fun newInstance(arg: Any): Plugin<Any> {
                    NPCDefinition.forId(5208).handlers["option:chop"] = this
                    return this
                }

                override fun handle(player: Player, node: Node, option: String): Boolean {
                    val quest = player.getQuestRepository().getQuest(AnimalMagnetism.NAME)
                    if (quest.getStage(player) <= 28) {
                        val tool = getHatchet(player)
                        if (tool == null || tool.ordinal < 4) {
                            sendMessage(player,"You don't have the required axe in order to do that.")
                            return true
                        }
                        val animation = getAnimation(tool.id)
                        player.animate(animation, 2)
                        if (quest.getStage(player) == 28) {
                            quest.setStage(player, 29)
                        }
                        sendMessage(player,"The axe bounces off the undead wood." + (if (quest.getStage(player) == 28 || quest.getStage(player) == 29) " I should report this to Ava." else ""))
                        return true
                    }
                    if (freeSlots(player) < 1) {
                        sendMessage(player,"Your inventory is full right now.")
                        return true
                    }
                    if (!player.inventory.containsItem(AnimalMagnetism.BLESSED_AXE) && !player.equipment.containsItem(AnimalMagnetism.BLESSED_AXE)) {
                        sendMessage(player,"You need a blessed axe in order to do that.")
                        return true
                    }
                    val animation = getAnimation(1355)
                    lock(player, animation!!.definition.durationTicks)
                    if (RandomFunction.random(10) < 3) {
                        sendMessage(player,"You almost remove a suitable twig, but you don't quite manage it.")
                    } else {
                        player.inventory.add(AnimalMagnetism.UNDEAD_TWIGS)
                        sendMessage(player,"You cut some undead twigs.")
                    }
                    player.animate(animation, 2)
                    return true
                }
            })
            addHandler(5208, NPC_TYPE, this)
            addHandler(152, NPC_TYPE, this)
            return this
        }

        override fun handle(event: NodeUsageEvent): Boolean {
            val player = event.player
            val animation = getAnimation(event.usedItem.id)
            val quest = player.getQuestRepository().getQuest(AnimalMagnetism.NAME)
            player.animate(animation, 2)
            if (quest.getStage(player) == 28) {
                quest.setStage(player, 29)
            }
            sendMessage(player, "The axe bounces off the undead wood." + (if (quest.getStage(player) == 28 || quest.getStage(player) == 29) " I should report this to Ava." else ""))
            return true
        }

        fun getAnimation(itemId: Int): Animation? {
            for (i in IDS.indices) {
                if (IDS[i] == itemId) {
                    return Animation(5366 + i, Priority.HIGH)
                }
            }
            return null
        }
    }

    /*
     * Handles the research note handler.
     */

    class ResearchNoteHandler : ComponentPlugin() {

        private val BUTTONS = arrayOf(
            intArrayOf(40, 39, 6),
            intArrayOf(42, 41, 3),
            intArrayOf(44, 43, 7),
            intArrayOf(46, 45, 8),
            intArrayOf(48, 47, 4),
            intArrayOf(50, 49, 9),
            intArrayOf(52, 51, 10),
            intArrayOf(54, 53, 11),
            intArrayOf(56, 55, 5)
        )

        override fun newInstance(arg: Any?): Plugin<Any> {
            ComponentDefinition.forId(480).plugin = this
            return this
        }

        override fun handle(
            player: Player,
            component: Component,
            opcode: Int,
            button: Int,
            slot: Int,
            itemId: Int
        ): Boolean {
            if (player.getAttribute("note-disabled", false)) {
                return true
            }
            val data = getIndex(button)
            val toggled = data[1] as Boolean
            val configs = getConfigs(data[0] as Int)
            val quest = player.getQuestRepository().getQuest(AnimalMagnetism.NAME)
            player.packetDispatch.sendInterfaceConfig(480, configs[0], !toggled)
            player.packetDispatch.sendInterfaceConfig(480, data[2] as Int, toggled)
            if (quest.getStage(player) == 33) {
                setNoteCache(player, data[0] as Int, !toggled)
                sendMessage(player, "You fiddle with the notes.")
                if (isTranslated(player)) {
                    if (player.inventory.remove(AnimalMagnetism.RESEARCH_NOTES)) {
                        player.setAttribute("note-disabled", true)
                        player.inventory.add(AnimalMagnetism.TRANSLATED_NOTES)
                        sendMessage(player, "It suddenly all makes sense.")
                    }
                }
            }
            return true
        }

        private fun setNoteCache(player: Player, index: Int, toggled: Boolean) {
            val cache = getNoteCache(player)
            cache[index] = toggled
            player.setAttribute("note-cache", cache)
        }

        private fun isTranslated(player: Player): Boolean {
            val cache: Map<Int, Boolean> = getNoteCache(player)
            val correct = intArrayOf(0, 2, 3, 5, 6, 7)
            val wrong = intArrayOf(1, 4, 8)
            for (i in correct) {
                if (cache[i]!!) {
                    return false
                }
            }
            for (i in wrong) {
                if (!cache[i]!!) {
                    return false
                }
            }
            return true
        }

        private fun getNoteCache(player: Player): MutableMap<Int, Boolean> {
            var cache = player.getAttribute<MutableMap<Int, Boolean>>("note-cache", null)
            if (cache == null) {
                cache = HashMap()
                for (i in BUTTONS.indices) {
                    cache[i] = true
                }
            }
            return cache
        }

        private fun getConfigs(index: Int): IntArray {
            return intArrayOf(21 + index, 0)
        }

        private fun getIndex(buttonId: Int): Array<Any> {
            for (i in BUTTONS.indices) {
                for (k in 0 until BUTTONS[i].size - 1) {
                    if (buttonId == BUTTONS[i][k]) {
                        return arrayOf(i, k == 0, BUTTONS[i][2])
                    }
                }
            }
            return arrayOf(0, true)
        }
    }

    /*
     * Handles the creating of a container.
     */

    class ContainerHandler : UseWithHandler(10496, 1743) {

        override fun newInstance(arg: Any?): Plugin<Any> {
            addHandler(AnimalMagnetism.PATTERN.id, ITEM_TYPE, this)
            return this
        }

        override fun handle(event: NodeUsageEvent): Boolean {
            val player = event.player
            if (!player.inventory.containsItem(AnimalMagnetism.HARD_LEATHER)) {
                player.sendMessage("You need hard leather as well as these 2 items.")
                return true
            }
            if (!player.inventory.containsItem(AnimalMagnetism.POLISHED_BUTTONS)) {
                player.sendMessage("You need polished buttons as well as these 2 items.")
                return true
            }
            if (player.inventory.remove(AnimalMagnetism.HARD_LEATHER, AnimalMagnetism.POLISHED_BUTTONS, AnimalMagnetism.PATTERN)) {
                playAudio(player, Sounds.ANMA_POLISH_BUTTONS_3281)
                player.inventory.add(AnimalMagnetism.CONTAINER)
            }
            return true
        }
    }

    override fun isWalk(player: Player, node: Node): Boolean {
        return node !is Item
    }

    override fun isWalk(): Boolean {
        return false
    }
}
