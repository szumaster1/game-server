package content.region.karamja.quest.junglepotion.plugin

import content.region.karamja.quest.junglepotion.JunglePotion
import content.region.karamja.quest.junglepotion.JunglePotion.JungleObjective
import core.api.sendDialogue
import core.api.sendDialogueLines
import core.api.sendDialogueOptions
import core.api.setTitle
import core.cache.def.impl.SceneryDefinition
import core.game.dialogue.Dialogue
import core.game.dialogue.DialogueInterpreter
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.quest.Quest
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.world.map.Location
import core.plugin.Plugin

/**
 * "Jungle potion" quest plugin.
 */
class JunglePotionPlugin : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        SceneryDefinition.forId(2584).handlers["option:search"] = this
        SceneryDefinition.forId(2585).handlers["option:climb"] = this
        for (s in JungleObjective.values()) {
            SceneryDefinition.forId(s.objectId).handlers["option:search"] = this
        }
        SceneryBuilder.add(Scenery(2585, Location.create(2828, 9522, 0), 8, 0))
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        val quest = player.getQuestRepository().getQuest(JunglePotion.NAME)
        when (node.id) {
            2584 -> {
                player.dialogueInterpreter.open("jogre_dialogue")
                return true
            }

            2585 -> {
                player.dialogueInterpreter.open("jogre_dialogue", true, true)
                return true
            }
        }
        when (option) {
            "search" -> search(player, quest, node as Scenery, JungleObjective.forId(node.getId()))
        }
        return true
    }

    private fun search(player: Player, quest: Quest, scenery: Scenery, objective: JungleObjective) {
        if (quest.getStage(player) < objective.stage) {
            player.sendMessage("Unfortunately, you find nothing of interest.")
            return
        }
        if (player.inventory.freeSlots() < 1) {
            player.sendMessage("You don't have enough inventory space.")
            return
        }
        player.sendMessage("You search the " + scenery.name.lowercase() + "...")
        objective.search(player, scenery)
    }

    override fun getDestination(n: Node, node: Node): Location? {
        if (node.id == 2585) {
            return Location.create(2830, 9521, 0)
        }
        return null
    }

    /**
     * Represents the Jogre cavern dialogue.
     */
    class JogreCavernDialogue : Dialogue {

        constructor()

        constructor(player: Player?) : super(player)

        override fun newInstance(player: Player): Dialogue {
            return JogreCavernDialogue(player)
        }

        override fun open(vararg args: Any): Boolean {
            if (args.size > 1) {
                sendDialogue(player,"You attempt to climb the rocks back out.")
                stage = 13
                return true
            }
            sendDialogueLines(player, "You search the rocks... You find an entrance into some caves.")
            return true
        }

        override fun handle(interfaceId: Int, buttonId: Int): Boolean {
            when (stage) {
                0 -> {
                    setTitle(player, 2)
                    sendDialogueOptions(player, "Would you like to enter the caves?", "Yes, I'll enter the cave.", "No thanks, I'll give it a miss.")
                    stage++
                }

                1 -> when (buttonId) {
                    1 -> {
                        sendDialogueLines(player, "You decide to enter the caves. You climb down several steep rock", "faces into the cavern below.")
                        stage = 10
                    }

                    2 -> {
                        sendDialogue(player, "You decide to stay where you are!")
                        stage = 11
                    }
                }

                10 -> {
                    end()
                    player.properties.teleportLocation = Location.create(2830, 9520, 0)
                }

                11 -> end()
                13 -> {
                    end()
                    player.properties.teleportLocation = Location.create(2823, 3120, 0)
                }
            }
            return true
        }

        override fun getIds(): IntArray {
            return intArrayOf(DialogueInterpreter.getDialogueKey("jogre_dialogue"))
        }
    }
}
