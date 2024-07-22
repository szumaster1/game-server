package content.global.plugins.item

import core.Util
import core.api.consts.Items
import core.api.consts.Sounds
import core.api.playAudio
import core.cache.def.impl.ItemDefinition
import core.cache.def.impl.SceneryDefinition
import core.game.dialogue.Dialogue
import core.game.dialogue.DialogueInterpreter
import core.game.interaction.NodeUsageEvent
import core.game.interaction.OptionHandler
import core.game.interaction.UseWithHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.node.item.WeightedChanceItem
import core.game.world.update.flag.context.Animation
import core.plugin.ClassScanner.definePlugin
import core.plugin.Initializable
import core.plugin.Plugin
import core.tools.RandomFunction

@Initializable
class FishbowlPlugin : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        ItemDefinition.forId(FISHBOWL_WATER).handlers["option:empty"] = this
        ItemDefinition.forId(FISHBOWL_SEAWEED).handlers["option:empty"] = this
        for (id in intArrayOf(FISHBOWL_BLUE, FISHBOWL_GREEN, FISHBOWL_SPINE)) {
            val def = ItemDefinition.forId(id)
            def.handlers["option:talk-at"] = this
            def.handlers["option:play-with"] = this
            def.handlers["option:feed"] = this
            def.handlers["option:drop"] = this
        }
        definePlugin(FishbowlDialogue())
        definePlugin(FeedPetFishHandler())
        AquariumPlugin().newInstance(arg)
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        if (node is Item) {
            val item = node.asItem()
            when (item.id) {
                FISHBOWL_WATER, FISHBOWL_SEAWEED -> if (player.inventory.remove(item)) {
                    player.lock(2)
                    player.inventory.add(Item(FISHBOWL_EMPTY))
                    playAudio(player, Sounds.LIQUID_2401, 0, 1)
                    player.packetDispatch.sendMessage("You empty the contents of the fishbowl onto the ground.")
                }

                FISHBOWL_BLUE, FISHBOWL_GREEN, FISHBOWL_SPINE -> when (option) {
                    "talk-at" -> {
                        player.lock(ANIM_TALK.duration)
                        player.animate(ANIM_TALK)
                        return player.dialogueInterpreter.open("fishbowl-options", option)
                    }

                    "play-with" -> {
                        player.lock(ANIM_PLAY.duration)
                        player.animate(ANIM_PLAY)
                        return player.dialogueInterpreter.open("fishbowl-options", option)
                    }

                    "feed" -> return player.dialogueInterpreter.open("fishbowl-options", option)
                    "drop" -> return player.dialogueInterpreter.open("fishbowl-options", option, item)
                }
            }
        }

        return true
    }

    private inner class FeedPetFishHandler : UseWithHandler(Items.FISH_FOOD_272) {

        override fun newInstance(arg: Any?): Plugin<Any?> {
            addHandler(FISHBOWL_BLUE, ITEM_TYPE, this)
            addHandler(FISHBOWL_GREEN, ITEM_TYPE, this)
            addHandler(FISHBOWL_SPINE, ITEM_TYPE, this)
            return this
        }

        override fun handle(event: NodeUsageEvent): Boolean {
            return event.player.dialogueInterpreter.open("fishbowl-options", "feed")
        }
    }

    inner class FishbowlDialogue(player: Player? = null) : Dialogue(player) {
        private var fishbowl: Item? = null
        private var option: String? = null

        override fun newInstance(player: Player): Dialogue {
            return FishbowlDialogue(player)
        }

        override fun open(vararg args: Any): Boolean {
            for (arg in args) {
                if (arg is Item) {
                    this.fishbowl = arg
                } else if (arg is String) {
                    this.option = arg
                }
            }
            when (option) {
                "talk-at" -> {
                    player("Good fish. Just keep swimming... swimming... swimming...")
                    stage = 1
                }

                "play-with" -> {
                    player("Jump! 'Cmon " + (if (player.isMale) "girl" else "boy") + ", jump!")
                    stage = 2
                }

                "feed" -> {
                    if (player.inventory.containsAtLeastOneItem(Items.FISH_FOOD_272) && player.inventory.remove(Item(Items.FISH_FOOD_272))) {
                        player.inventory.add(Item(Items.AN_EMPTY_BOX_6675))
                        player.lock(ANIM_FEED.duration)
                        player.animate(ANIM_FEED)
                        player.packetDispatch.sendMessage("You feed your fish.")
                    } else if (player.inventory.containsAtLeastOneItem(Items.POISONED_FISH_FOOD_274)) {
                        player.packetDispatch.sendMessage("You can't poison your own pet!")
                    } else {
                        player.packetDispatch.sendMessage("You don't have any fish food.")
                    }
                }

                "drop" -> {
                    sendDialogue("If you drop your fishbowl it will break!")
                    stage = 4
                }
            }
            return true
        }

        override fun handle(interfaceId: Int, buttonId: Int): Boolean {
            when (stage) {
                999 -> end()
                1 -> {
                    sendDialogue("The fish swims. It is clearly an obedient fish.")
                    stage = 999
                }

                2 -> {
                    sendDialogue("The fish bumps into the side of the fishbowl. Then it swims some", "more.")
                    stage++
                }

                3 -> {
                    player("Good fish...")
                    stage = 999
                }

                4 -> {
                    options("Drop it regardless", "Keep hold")
                    stage++
                }

                5 -> when (buttonId) {
                    1 -> {
                        sendDialogue("The fishbowl shatters on the ground.")
                        player.inventory.remove(fishbowl)
                        stage = 999
                    }

                    2 -> {
                        sendDialogue("You keep a hold of it for now.")
                        stage = 999
                    }
                }
            }
            return true
        }

        override fun getIds(): IntArray {
            return intArrayOf(DialogueInterpreter.getDialogueKey("fishbowl-options"))
        }
    }

    class AquariumPlugin : OptionHandler() {

        override fun newInstance(arg: Any?): Plugin<Any?> {
            SceneryDefinition.forId(10091).handlers["option:fish-in"] = this
            definePlugin(TinyNetHandler())
            return this
        }

        override fun handle(player: Player, node: Node, option: String): Boolean {
            return getFish(player)
        }

        fun getFish(player: Player): Boolean {
            if (!player.inventory.containsAtLeastOneItem(TINY_NET)) {
                player.packetDispatch.sendMessage("You see some tiny fish swimming around... but how to catch them?")
                return true
            } else if (player.inventory.remove(Item(FISHBOWL_SEAWEED))) {
                player.packetDispatch.sendMessage("You wave the net around...")
                player.getSkills().addExperience(Skills.FISHING, 1.0, true)
                val level = player.getSkills().getLevel(Skills.FISHING)
                val blueChance = Math.round(Util.clamp(-0.6667 * level.toDouble() + 106.0, 40.0, 60.0)).toInt()
                val greenChance = Math.round(Util.clamp(0.2941 * level.toDouble() + 19.7059, 20.0, 40.0)).toInt()
                val spineChance = Math.round(Util.clamp(0.6667 * level.toDouble() - 46.0, 0.0, 20.0)).toInt()

                val fishChance = arrayOf(
                    WeightedChanceItem(FISHBOWL_BLUE, 1, blueChance), WeightedChanceItem(
                        FISHBOWL_GREEN, 1, greenChance
                    ), WeightedChanceItem(FISHBOWL_SPINE, 1, spineChance)
                )

                val fish = RandomFunction.rollWeightedChanceTable(*fishChance)
                player.inventory.add(fish)
                var msg = "[ REPORT BUG ON GITLAB ]"
                when (fish.id) {
                    FISHBOWL_BLUE -> msg = "Bluefish"
                    FISHBOWL_GREEN -> msg = "Greenfish"
                    FISHBOWL_SPINE -> msg = "Spinefish"
                }
                player.packetDispatch.sendMessage("...and you catch a Tiny $msg!")
                player.achievementDiaryManager.finishTask(player, DiaryType.SEERS_VILLAGE, 1, 10)
                return true
            } else {
                player.packetDispatch.sendMessage("You need something to put your catch in!")
                return true
            }
        }

        private inner class TinyNetHandler : UseWithHandler(TINY_NET) {

            override fun newInstance(arg: Any?): Plugin<Any?> {
                addHandler(10091, OBJECT_TYPE, this)
                return this
            }

            override fun handle(event: NodeUsageEvent): Boolean {
                return getFish(event.player)
            }
        }
    }

    companion object {
        private const val FISHBOWL_EMPTY = Items.FISHBOWL_6667
        private const val FISHBOWL_WATER = Items.FISHBOWL_6668
        private const val FISHBOWL_SEAWEED = Items.FISHBOWL_6669
        private const val FISHBOWL_BLUE = Items.FISHBOWL_6670
        private const val FISHBOWL_GREEN = Items.FISHBOWL_6671
        private const val FISHBOWL_SPINE = Items.FISHBOWL_6672
        private const val TINY_NET = Items.TINY_NET_6674
        private val ANIM_TALK = Animation(2782)
        private val ANIM_PLAY = Animation(2780)
        private val ANIM_FEED = Animation(2781)
    }
}
