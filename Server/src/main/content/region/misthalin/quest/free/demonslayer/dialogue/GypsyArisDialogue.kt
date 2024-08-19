package content.region.misthalin.quest.free.demonslayer.dialogue

import content.region.misthalin.quest.free.demonslayer.DemonSlayer
import core.api.sendNPCDialogue
import core.game.activity.ActivityManager
import core.game.activity.CutscenePlugin
import core.game.component.Component
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.quest.Quest
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Direction
import core.game.world.update.flag.context.Animation
import core.network.packet.PacketRepository
import core.network.packet.context.CameraContext
import core.network.packet.context.CameraContext.CameraType
import core.network.packet.outgoing.CameraViewPacket

/**
 * Represents the Gypsy aris dialogue.
 */
class GypsyArisDialogue(player: Player? = null) : Dialogue(player) {

    private var quest: Quest? = null
    private var wally: NPC? = null
    private var cutscene: CutscenePlugin? = null

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        quest = player.getQuestRepository().getQuest("Demon Slayer")
        when (quest!!.getStage(player)) {
            100 -> {
                npc("Greetings young one.")
                stage = 0
            }

            30, 10, 20 -> {
                if (args.size > 1) {
                    npc("Delrith will come forth from the stone circle again.")
                    stage = 200
                    return true
                }
                npc("Greetings. How goes the quest?")
                stage = if (quest!!.getStage(player) != 30) {
                    1
                } else {
                    0
                }
            }

            0 -> {
                if (args.size > 1) {
                    cutscene = args[1] as CutscenePlugin
                    npc(
                        "Wally managed to arrive at the stone circle just as",
                        "Delrith was summoned by a cult of chaos druids..."
                    )
                    stage = 200
                    return true
                }
                npc("Hello young one.")
                stage = 1
            }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (quest!!.getStage(player)) {
            100 -> when (stage) {
                0 -> {
                    npc("You're a hero now. That was a good bit of", "demonslaying.")
                    stage = 1
                }

                1 -> {
                    player("Thanks.")
                    stage = 2
                }

                2 -> end()
            }

            30 -> when (stage) {
                0 -> {
                    player("I have the sword now. I just need to kill the demon, I", "think.")
                    stage = 1
                }

                1 -> {
                    npc("Yep, that's right.")
                    stage = 2
                }

                2 -> {
                    options(
                        "What is the magical incantation?",
                        "Well I'd better press on with it",
                        "Where can I find the demon?"
                    )
                    stage = 3
                }

                3 -> when (buttonId) {
                    1 -> {
                        player("What is the magical incantation?")
                        stage = 10
                    }

                    2 -> {
                        player("Well I'd better press on with it.")
                        stage = 20
                    }

                    3 -> {
                        player("Where can I find the demon?")
                        stage = 30
                    }
                }

                10 -> {
                    npc(DemonSlayer.getIncantation(player) + ".")
                    stage = 11
                }

                11 -> {
                    player("Well I'd better press on with it.")
                    stage = 20
                }

                20 -> {
                    npc("See you anon.")
                    stage = 21
                }

                21 -> end()
                30 -> {
                    npc("Just head south and you should find the stone circle", "just outside the city gate.")
                    stage = 31
                }

                31 -> end()
            }

            20 -> when (stage) {
                0 -> {
                    player(
                        "I found Sir Prysin. Unfortunately I haven't got the",
                        "sword yet. He's made it complicated for me."
                    )
                    stage = 1
                }

                1 -> {
                    npc("Ok, hurry, we haven't much time.")
                    stage = 2
                }

                2 -> {
                    options("What is the magical incantation?", "Well I'd better press on with it.")
                    stage = 3
                }

                3 -> when (buttonId) {
                    1 -> {
                        player("What is the magical incantation?")
                        stage = 10
                    }

                    2 -> {
                        player("Well I'd better press on with it.")
                        stage = 20
                    }
                }

                10 -> {
                    npc(DemonSlayer.getIncantation(player) + ".")
                    stage = 11
                }

                11 -> {
                    player("Well I'd better press on with it.")
                    stage = 20
                }

                20 -> {
                    player("See you anon.")
                    stage = 21
                }

                21 -> end()
            }

            10 -> when (stage) {
                1 -> {
                    player("I'm still working on it.")
                    stage = 2
                }

                2 -> {
                    npc("Well if you need any advice I'm always here, young", "one.")
                    stage = 3
                }

                3 -> {
                    options(
                        "What is the magical incantation?",
                        "Where can I find Silverlight?",
                        "Stop calling me that!",
                        "Well I'd better press on with it."
                    )
                    stage = 4
                }

                4 -> when (buttonId) {
                    1 -> {
                        player("What is the magical incantation?")
                        stage = 10
                    }

                    2 -> {
                        player("Where can I find Silverlight?")
                        stage = 20
                    }

                    3 -> {
                        player("Stop calling me that!")
                        stage = 30
                    }

                    4 -> {
                        player("Well I'd better press on with it.")
                        stage = 40
                    }
                }

                10 -> {
                    npc("Oh yes, let me think a second...")
                    stage = 11
                }

                11 -> {
                    npc("Alright, I think I've got it now, it goes...")
                    stage = 12
                }

                12 -> {
                    npc(DemonSlayer.getIncantation(player) + ".", "Have you got that?")
                    stage = 13
                }

                13 -> {
                    player("I think so, yes.")
                    stage = 14
                }

                14 -> end()
                20 -> {
                    npc(
                        "Silverlight has been passed down through Wally's",
                        "descendants. I believe it is currently in the care of one",
                        "of the King's knights called Sir Prysin."
                    )
                    stage = 21
                }

                21 -> {
                    npc(
                        "He shouldn't be too hard to find. He lives in the royal",
                        "palace in this city. Tell him Gypsy Aris sent you."
                    )
                    stage = 22
                }

                22 -> end()
                30 -> {
                    npc("In the scheme of things you are very young.")
                    stage = 31
                }

                31 -> end()
                40 -> {
                    npc("See you anon.")
                    stage = 41
                }

                41 -> end()
                200 -> {
                    npc(
                        "I would imagine an evil sorcerer is already starting on",
                        "the rituals to summon Delrith as we speak."
                    )
                    stage = 201
                }

                201 -> {
                    options(
                        "How am I meant to fight a demon who can destroy cities?",
                        "Okay, where is he? I'll kill him for you!",
                        "What is the magical incantation?",
                        "Where can I find Silverlight?"
                    )
                    stage = 202
                }

                202 -> when (buttonId) {
                    1 -> {
                        player("How am I meant to fight a demon who can destroy ", "cities?!")
                        stage = 110
                    }

                    2 -> {
                        player("Okay, where is he? I'll kill him for you!")
                        stage = 120
                    }

                    3 -> {
                        player("What is the magical incantation?")
                        stage = 10
                    }

                    4 -> {
                        player("Where can I find Silverlight?")
                        stage = 20
                    }
                }

                120 -> {
                    npc("Ah, the overconfidence of the young!")
                    stage = 121
                }

                121 -> {
                    npc(
                        "Delrith can't be harmed by ordinary weapons. You",
                        "must face him using the same weapon that Wally used."
                    )
                    stage = 201
                }

                110 -> {
                    npc(
                        "If you face Delrith while he is still weak from being",
                        "summoned, and use the correct weapon, you will not",
                        "find the task to arduous."
                    )
                    stage = 111
                }

                111 -> {
                    npc(
                        "Do not fear. If you follow the path of the great hero",
                        "Wally, then you are sure to defeat the demon."
                    )
                    stage = 201
                }
            }

            0 -> when (stage) {
                1 -> {
                    npc("Cross my palm with silver and the future will be", "revealed to you.")
                    stage = 2
                }

                2 -> {
                    options(
                        "Ok, here you go.",
                        "Who are you calling young one?!",
                        "No, I don't believe in that stuff.",
                        "With silver?"
                    )
                    stage = 3
                }

                3 -> when (buttonId) {
                    1 -> {
                        player("Ok, here you go.")
                        stage = 10
                    }

                    2 -> {
                        player("Who are you calling young one?!")
                        stage = 20
                    }

                    3 -> {
                        player("No, I don't believe in that stuff.")
                        stage = 30
                    }

                    4 -> {
                        player("With silver?")
                        stage = 40
                    }
                }

                10 -> {
                    if (!player.inventory.containsItem(COINS)) {
                        end()
                        return true
                    }
                    stage = if (player.inventory.remove(COINS)) {
                        npc(
                            "Come closer, and listen carefully to what the future",
                            "holds for you, as I peer int the swirling mists of the",
                            "crystal ball."
                        )
                        npc.animate(ANIMATION)
                        12
                    } else {
                        player("Sorry, I don't seem to have enough coins.")
                        11
                    }
                }

                11 -> end()
                12 -> {
                    npc("I can see images forming. I can see you.")
                    stage = 13
                }

                13 -> {
                    npc("You are holding a very impressive looking sword. I'm", "sure I recognize that sword...")
                    stage = 14
                }

                14 -> {
                    npc("There is a big dark shadow appearing now.")
                    stage = 15
                }

                15 -> {
                    npc("Aargh!")
                    stage = 16
                }

                16 -> {
                    player("Are you all right?")
                    stage = 17
                }

                17 -> {
                    npc("It's Delrith! Delrith is coming!")
                    stage = 18
                }

                18 -> {
                    player("Who's Delrith?")
                    stage = 50
                }

                50 -> {
                    npc("Delrith...")
                    stage = 51
                }

                51 -> {
                    npc("Delrith is a powerful demon.")
                    stage = 52
                }

                52 -> {
                    npc("Oh! I really hope he didn't see me looking at him", "through my crystal ball!")
                    stage = 53
                }

                53 -> {
                    npc(
                        "He tried to destroy this city 150 years ago. He was",
                        "stopped just in time by the great hero Wally."
                    )
                    stage = 54
                }

                54 -> {
                    npc(
                        "Using his magic sword Silverlight, Wally managed to",
                        "trap the demon in the stone circle just south",
                        "of this city."
                    )
                    stage = 55
                }

                55 -> {
                    npc(
                        "Ye gods! Silverlight was the sword you were holding in",
                        "my vision! You are the one destined to stop the demon",
                        "this time."
                    )
                    stage = 56
                }

                56 -> {
                    options(
                        "How am I meant to fight a demon who can destroy cities?",
                        "Okay, where is he? I'll kill him for you!",
                        "Wally doesn't sound like a very heroic name."
                    )
                    stage = 57
                }

                57 -> when (buttonId) {
                    1 -> {
                        player("How am I meant to fight a demon who can destroy ", "cities?!")
                        stage = 110
                    }

                    2 -> {
                        player("Okay, where is he? I'll kill him for you!")
                        stage = 120
                    }

                    3 -> {
                        player("Wally doesn't sound like a very heroic name.")
                        stage = 130
                    }
                }

                110 -> {
                    npc(
                        "If you face Delrith while he is still weak from being",
                        "summoned, and use the correct weapon, you will not",
                        "find the task to arduous."
                    )
                    stage = 111
                }

                111 -> {
                    npc(
                        "Do not fear. If you follow the path of the great hero",
                        "Wally, then you are sure to defeat the demon."
                    )
                    stage = 112
                }

                112 -> {
                    options(
                        "Okay, where is he? I'll kill him for you!",
                        "Wally doesn't sound like a very heroic name.",
                        "So how did Wally kill Delrith?"
                    )
                    stage = 113
                }

                113 -> when (buttonId) {
                    1 -> {
                        player("Okay, where is he? I'll kill him for you!")
                        stage = 120
                    }

                    2 -> {
                        player("Wally doesn't sound like a very heroic name.")
                        stage = 130
                    }

                    3 -> {
                        player("So how did Wally kill Delrith?")
                        stage = 180
                    }
                }

                120 -> {
                    npc("Ah, the overconfidence of the young!")
                    stage = 121
                }

                121 -> {
                    npc(
                        "Delrith can't be harmed by ordinary weapons. You",
                        "must face him using the same weapon that Wally used."
                    )
                    stage = 122
                }

                122 -> {
                    options(
                        "How am I meant to fight a demon who can destroy cities?",
                        "Wally doesn't sound like a very heroic name.",
                        "So how did wally kill Delrith?"
                    )
                    stage = 123
                }

                123 -> when (buttonId) {
                    1 -> {
                        player("How am I meant to fight a demon who can destroy ", "cities?!")
                        stage = 110
                    }

                    2 -> {
                        player("Wally doesn't sound like a very heroic name.")
                        stage = 130
                    }

                    3 -> {
                        player("So how did Wally kill Delrith?")
                        stage = 180
                    }
                }

                130 -> {
                    npc(
                        "Yes I know. Maybe this is why history doesn't",
                        "remember him. However he was a very great hero."
                    )
                    stage = 131
                }

                131 -> {
                    npc(
                        "Who knows how much pain and suffering Delrith would",
                        "have brought forth without Wally to stop him!"
                    )
                    stage = 132
                }

                132 -> {
                    npc("It looks like you are going to need to perform similar", "heroics.")
                    stage = 133
                }

                133 -> {
                    options(
                        "How am I meant to fight a demon who can destroy cities?",
                        "Okay, where is he? I'll kill him for you!",
                        "So how did Wally kill Delrith?"
                    )
                    stage = 134
                }

                134 -> when (buttonId) {
                    1 -> {
                        player("How am I meant to fight a demon who can destroy ", "cities?!")
                        stage = 110
                    }

                    2 -> {
                        player("Okay, where is he? I'll kill him for you!")
                        stage = 120
                    }

                    3 -> {
                        player("So how did Wally kill Delrith?")
                        stage = 180
                    }
                }

                180 -> {
                    ActivityManager.start(player, "Wally cutscene", false)
                    end()
                }

                20 -> {
                    npc("You have been on this world a relatively short time. At", "least compared to me.")
                    stage = 21
                }

                21 -> end()
                30 -> {
                    npc("Ok suit yourself.")
                    stage = 31
                }

                31 -> end()
                40 -> {
                    npc(
                        "Oh, sorry, I forgot. With gold, I mean. They haven't",
                        "used silver coins since before you were born! So, do",
                        "you want your fortune told?"
                    )
                    stage = 41
                }

                41 -> {
                    options("Ok, here you go.", "No, I don't believe in that stuff.")
                    stage = 42
                }

                42 -> when (buttonId) {
                    1 -> {
                        player("Ok, here you go.")
                        stage = 10
                    }

                    2 -> {
                        player("No, I don't believe in that stuff.")
                        stage = 30
                    }
                }

                200 -> {
                    val wally = NPC.create(4664, cutscene!!.base.transform(31, 40, 0))
                    wally.direction = Direction.WEST
                    cutscene!!.npcs.add(wally)
                    wally.init()
                    PacketRepository.send(
                        CameraViewPacket::class.java,
                        CameraContext(
                            player,
                            CameraType.POSITION,
                            player.location.x + 2,
                            player.location.y + 2,
                            260,
                            1,
                            10
                        )
                    )
                    PacketRepository.send(
                        CameraViewPacket::class.java,
                        CameraContext(
                            player,
                            CameraType.ROTATION,
                            player.location.x + 190,
                            player.location.y + 14,
                            260,
                            1,
                            10
                        )
                    )
                    interpreter.sendDialogues(wally, FacialExpression.FURIOUS, "Die, foul demon!")
                    Pulser.submit(object : Pulse(5) {
                        override fun pulse(): Boolean {
                            wally.animate(Animation(4603))
                            return true
                        }
                    })
                    stage = 201
                }

                201 -> {
                    sendNPCDialogue(player, wally!!.id, "Now, what was that incantation again?")
                    stage = 202
                }

                202 -> {
                    sendNPCDialogue(player, wally!!.id, DemonSlayer.generateIncantation(player) + "!")
                    stage = 203
                }

                203 -> {
                    close()
                    player.interfaceManager.openOverlay(Component(115))
                    Pulser.submit(object : Pulse(1) {
                        var counter = 0
                        override fun pulse(): Boolean {
                            when (counter++) {
                                1 -> player.properties.teleportLocation = cutscene!!.base.transform(25, 36, 0)
                                3 -> {
                                    wally!!.direction = Direction.SOUTH_WEST
                                    wally!!.properties.teleportLocation = cutscene!!.base.transform(28, 40, 0)
                                    PacketRepository.send(
                                        CameraViewPacket::class.java,
                                        CameraContext(
                                            player,
                                            CameraType.POSITION,
                                            player.location.x,
                                            player.location.y,
                                            440,
                                            1,
                                            100
                                        )
                                    )
                                    PacketRepository.send(
                                        CameraViewPacket::class.java,
                                        CameraContext(
                                            player,
                                            CameraType.ROTATION,
                                            player.location.x + 1,
                                            player.location.y + 1,
                                            440,
                                            1,
                                            100
                                        )
                                    )
                                    player.interfaceManager.closeOverlay()
                                    player.interfaceManager.close()
                                    wally!!.animate(Animation(4604))
                                    sendNPCDialogue(player, wally!!.id, "I am the greatest demon slayer EVER!")
                                    stage = 204
                                }

                                4 -> {
                                    player.interfaceManager.closeOverlay()
                                    player.interfaceManager.close()
                                    wally!!.animate(Animation(4604))
                                    sendNPCDialogue(player, wally!!.id,"I am the greatest demon slayer EVER!")
                                    stage = 204
                                }

                                5 -> {
                                    wally!!.animate(Animation(4604))
                                    return true
                                }
                            }
                            return false
                        }
                    })
                }

                204 -> {
                    npc(
                        "By reciting the correct magical incantation, and",
                        "thrusting Silverlight into Delrith while he was newly",
                        "summoned, Wally was able to imprison Delrith in the",
                        "stone block in the centre of the circle."
                    )
                    stage = 205
                }

                205 -> {
                    cutscene!!.stop(true)
                    close()
                }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(882)
    }

    companion object {
        private val COINS = Item(995, 1)
        private val ANIMATION = Animation(4615)
    }
}
