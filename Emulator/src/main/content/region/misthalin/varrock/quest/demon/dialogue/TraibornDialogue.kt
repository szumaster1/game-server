package content.region.misthalin.varrock.quest.demon.dialogue

import org.rs.consts.NPCs
import content.region.misthalin.varrock.quest.demon.handlers.DemonSlayerUtils
import core.api.removeAttribute
import core.api.setAttribute
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.quest.Quest
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import org.rs.consts.QuestName

/**
 * Represents the Traiborn dialogue.
 */
class TraibornDialogue(player: Player? = null) : Dialogue(player) {

    private var quest: Quest? = null

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        quest = player.getQuestRepository().getQuest(QuestName.DEMON_SLAYER)
        stage = when (quest!!.getStage(player)) {
            20 -> if (player.getAttribute("demon-slayer:traiborn", false)) {
                npc("How are you doing finding bones?")
                380
            } else {
                npc(FacialExpression.HALF_GUILTY, "Ello young thingummywut.")
                0
            }

            else -> {
                npc(FacialExpression.HALF_GUILTY, "Ello young thingummywut.")
                0
            }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (quest!!.getStage(player)) {
            20 -> when (stage) {
                0 -> stage =
                    if (player.inventory.containsItem(DemonSlayerUtils.THIRD_KEY) && player.bank.containsItem(
                            DemonSlayerUtils.THIRD_KEY)) {
                        options("What's a thingummywut?", "Teach me to be a mighty powerful wizard.")
                        1
                    } else {
                        options("What's a thingummywut?", "Teach me to be a mighty powerful wizard.", "I need to get a key given to you by Sir Prysin.")
                        1
                    }

                1 -> when (buttonId) {
                    1, 2 -> handleDefault(buttonId)
                    3 -> {
                        player("I need to get a key given to you by Sir Prysin.")
                        stage = 300
                    }
                }

                300 -> {
                    npc("Sir Prysin? Who's that? What would I want his key", "for?")
                    stage = 301
                }

                301 -> {
                    player("Well, have you got any keys knocking around?")
                    stage = 302
                }

                302 -> {
                    npc("Now you come to mention it, yes I do have a key. It's", "in my special closet of valuable stuff. Now how do I get", "into that?")
                    stage = 303
                }

                303 -> {
                    npc("I sealed it using one of my magic rituals. So it would", "makes sense that another ritual would open it again.")
                    stage = 304
                }

                304 -> {
                    player("So do you know what ritual to use?")
                    stage = 305
                }

                305 -> {
                    npc("Let me think a second.")
                    stage = 306
                }

                306 -> {
                    npc("Yes a simple drazier style ritual should suffice. Hmm,", "main problem with that is I'll need 25 sets of bones.", "Now where am I going to get hold of something like", "that?")
                    stage = 307
                }

                307 -> {
                    options("Hmm, that's too bad. I really need that key.", "I'll get the bones for you.")
                    stage = 308
                }

                308 -> when (buttonId) {
                    1 -> {
                        player("Hmm, that's too bad. I really need that key.")
                        stage = 330
                    }

                    2 -> {
                        player("I'll get the bones for you.")
                        stage = 340
                    }
                }

                330 -> {
                    npc("Ah well, sorry I couldn't be any more help.")
                    stage = 331
                }

                331 -> end()
                340 -> {
                    npc("Ooh that would be very good of you.")
                    stage = 341
                }

                341 -> {
                    player("Ok I'll speak to you when I've got some bones.")
                    stage = 342
                }

                342 -> {
                    setAttribute(player, "/save:demon-slayer:traiborn", true)
                    end()
                }

                380 -> if (player.inventory.containsItem(BONES[0]) || player.inventory.containsItem(BONES[1])) {
                    player("I have some bones.")
                    stage = 382
                } else {
                    player("I don't have all the bones yet.")
                    stage = 381
                }

                382 -> {
                    npc("Give 'em here then.")
                    stage = 383
                }

                383 -> {
                    interpreter.sendDialogue("You give Traiborn 25 sets of bones.")
                    stage = 384
                }

                384 -> {
                    npc("Hurrah! That's all 25 sets of bones.")
                    stage = 385
                }

                385 -> {
                    npc.animate(Animation(4602))
                    npc("Wings of dark and colour too,", "Spreading in the morning dew;", "Locked away I have a key;", "Return it now, please, unto me.")
                    stage = 386
                }

                386 -> {
                    val `object` = Scenery(17434, Location.create(3113, 3161, 1), 11, 1)
                    SceneryBuilder.add(`object`)
                    npc.faceLocation(`object`.location)
                    npc.animate(ANIMATION)
                    if (!player.inventory.containsItem(BONES[0]) && !player.inventory.containsItem(BONES[1])) {
                        end()
                        return true
                    }
                    if (player.inventory.remove(BONES[0]) || player.inventory.remove(BONES[1])) {
                        removeAttribute(player, "demon-slayer:traiborn")
                        player.inventory.add(DemonSlayerUtils.THIRD_KEY)
                        interpreter.sendItemMessage(DemonSlayerUtils.THIRD_KEY.id, "Traiborn hands you a key.")
                        stage = 387
                    }
                    Pulser.submit(object : Pulse(1) {
                        var counter = 0
                        override fun pulse(): Boolean {
                            when (counter++) {
                                5 -> npc.face(player)
                                7 -> {
                                    SceneryBuilder.remove(`object`)
                                    return true
                                }
                            }
                            return false
                        }
                    })
                }

                387 -> {
                    player("Thank you very much.")
                    stage = 388
                }

                388 -> {
                    npc("Not a problem for a friend of Sir What's-his-face.")
                    stage = 389
                }

                389 -> end()
                381 -> end()
            }

            0 -> when (stage) {
                else -> handleDefault(buttonId)
            }
        }
        return true
    }

    private fun handleDefault(buttonId: Int) {
        when (stage) {
            0 -> {
                options("What's a thingummywut?", "Teach me to be a mighty and powerful wizard.")
                stage = 1
            }

            1 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY, "What's a thingummywut?")
                    stage = 10
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY, "Teach  me to be a mighty and powerful wizard.")
                    stage = 20
                }
            }

            10 -> {
                npc(FacialExpression.HALF_GUILTY, "A thingummywut? Where? Where?")
                stage = 11
            }

            11 -> {
                npc(FacialExpression.HALF_GUILTY, "Those pesky thingummywuts. They get everywhere.", "They leave a terrible mess too.")
                stage = 12
            }

            12 -> {
                options("Err you just called me a thingummywut.", "Tell me what they look like and I'll mask 'em.")
                stage = 13
            }

            13 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY, "Err you just called me thingummywut.")
                    stage = 100
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY,
                        "Tell me what they look like and I'll mash 'em."
                    )
                    stage = 120
                }
            }

            120 -> {
                npc(FacialExpression.HALF_GUILTY, "Don't be ridiculous. No-one has ever seen one.")
                stage = 121
            }

            121 -> {
                npc(FacialExpression.HALF_GUILTY, "They're invisible, or a myth, or a figment of my", "imagination. Can't remember which right now.")
                stage = 122
            }

            122 -> end()
            100 -> {
                npc(FacialExpression.HALF_GUILTY, "You're a thingummywut? I've never seen one up close", "before. They said I was mad!")
                stage = 101
            }

            101 -> {
                npc(FacialExpression.HALF_GUILTY, "Now you are my proof! There ARE thingummywuts in", "this tower. Now where can I find a cage big enough to", "keep you?")
                stage = 102
            }

            102 -> {
                options("Err I'd better be off really.", "They're right, you are mad.")
                stage = 103
            }

            103 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY, "Err I'd better be off really.")
                    stage = 110
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY, "They're right, you are mad.")
                    stage = 130
                }
            }

            130 -> {
                npc(FacialExpression.HALF_GUILTY, "That's a pity. I thought maybe they were winding me", "up.")
                stage = 131
            }

            131 -> end()
            110 -> {
                npc(FacialExpression.HALF_GUILTY, "Oh ok, have a good time, and watch out for sheep!", "They're more cunning than they look.")
                stage = 111
            }

            111 -> end()
            20 -> {
                npc(FacialExpression.HALF_GUILTY, "Wizard eh? You don't want any truck with that sort.", "They're not to be trusted. That's what I've heard", "anyways.")
                stage = 21
            }

            21 -> {
                options("So aren't you a wizard?", "Oh I'd better stop talking to you then.")
                stage = 22
            }

            22 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY, "So aren't you a wizard?")
                    stage = 40
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY, "Oh I'd better stop talking to you then.")
                    stage = 60
                }
            }

            60 -> {
                npc(FacialExpression.HALF_GUILTY, "Cheerio then. It was nice chatting to you.")
                stage = 61
            }

            61 -> end()
            40 -> {
                npc(FacialExpression.HALF_GUILTY, "How dare you? Of course I'm a wizard. Now don't be", "so cheeky or I'll turn you into a frog.")
                stage = 41
            }

            41 -> end()
        }
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.TRAIBORN_881)
    }

    companion object {
        private val BONES = arrayOf(Item(526, 25), Item(2530, 25))
        private val ANIMATION = Animation(536)
    }
}
