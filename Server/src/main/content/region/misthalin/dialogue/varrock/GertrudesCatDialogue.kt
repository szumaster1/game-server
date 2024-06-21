package content.region.misthalin.dialogue.varrock

import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable

@Initializable
class GertrudesCatDialogue(player: Player? = null) : Dialogue(player) {

    private val BEND_DOWN = Animation.create(827)

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        options( "Talk-with", "Pick-up", "Stroke")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        val quest = player.getQuestRepository().getQuest("Gertrude's Cat")
        when (stage) {
            545 -> end()
            0 -> when (buttonId) {
                1 -> if (quest.getStage(player) < 60) {
                    end()
                    npc.sendChat("Miaoww")
                } else {
                    player("Oh, it looks like Fluffs has returned.",
                        "I'd better leave her alone."
                    )
                    stage = 99
                }

                2 -> {
                    close()
                    player.pulseManager.run(object : Pulse(1, player) {
                        var count = 0
                        override fun pulse(): Boolean {
                            when (count++) {
                                0 -> player.animate(BEND_DOWN)
                                2 -> npc.sendChat("Hisss!")
                                3 -> player.sendChat("Ouch!")
                                4 -> {
                                    if (quest.getStage(player) == 40) {
                                        interpreter.sendDialogue(
                                            "The cat seems afraid to leave.",
                                            "In the distance you can hear kittens mewing..."
                                        )
                                        stage = 545
                                        return true
                                    }
                                    if (quest.getStage(player) == 30) {
                                        interpreter.sendDialogue("Maybe the cat is hungry?")
                                        stage = 545
                                        return true
                                    }
                                    if (quest.getStage(player) == 50) {
                                        end()
                                        return true
                                    }
                                    end()
                                    interpreter.sendDialogue("Maybe the cat is thirsty?")
                                    stage = 545
                                }
                            }
                            return count == 5
                        }
                    })
                }

                545 -> end()
                3 -> {
                    close()
                    player.pulseManager.run(object : Pulse(1, player) {
                        var count = 0
                        override fun pulse(): Boolean {
                            when (count++) {
                                0 -> player.animate(BEND_DOWN)
                                2 -> npc.sendChat("Hisss!")
                                3 -> player.sendChat("Ouch!")
                                4 -> {
                                    if (quest.getStage(player) == 40) {
                                        return true
                                    }
                                    interpreter.sendDialogue("Perhaps the cat want's something?")
                                    stage = 545
                                }
                            }
                            return count == 5
                        }
                    })
                    if (quest.getStage(player) == 40) {
                        return true
                    }
                    Pulser.submit(object : Pulse(7, player) {
                        override fun pulse(): Boolean {
                            end()
                            return true
                        }
                    })
                }
            }

            99 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(2997)
    }
}