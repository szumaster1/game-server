package content.minigame.sorceressgarden.dialogue

import cfg.consts.NPCs
import core.api.hasRequirement
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.impl.Projectile
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Location
import core.game.world.update.flag.context.Graphic

/**
 * Represents the Sorceress Apprentice dialogue.
 */
class SorceressApprenticeDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        stage = if (player.getSavedData().globalData.hasSpokenToApprentice()) {
            player(FacialExpression.HALF_GUILTY,
                "Hey apprentice, do you want to try out",
                "your teleport skills again?"
            )
            0
        } else {
            player("Hello. What are you doing?")
            10
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "Okay, here goes - and remember, to",
                    "return just drink from the fountain."
                )
                stage = 1
            }

            1 -> {
                teleport(npc, player)
                end()
            }

            10 -> {
                npc(
                    "Cleaning, cleaning, always cleaning. This apprenticeship",
                    "isn't all that it was cracked up to be."
                )
                stage = 11
            }

            11 -> {
                player("Whose apprentice are you?")
                stage = 12
            }

            12 -> {
                npc(
                    "Oh, Aadeela, the sorceress upstairs, said she'd teach me",
                    "magic, she did. And here I am scrubbing floors without",
                    "a spell to help me."
                )
                stage = 13
            }

            13 -> {
                options("I could cast a Water Blast or a Wind Blast spell?", "Surely there must be upsides to the task?")
                stage = 14
            }

            14 -> when (buttonId) {
                1 -> {
                    player("I could cast a Water Blast or a Wind Blast spell to", "hurry things along if you'd like?")
                    stage = 110
                }

                2 -> {
                    player("Surely there must be upsides to the task?")
                    stage = 120
                }
            }

            110 -> {
                npc(
                    "No, no, she'd kill me or worse if she knew I was using",
                    "Magic to do chores. Her last apprentice - well I'd",
                    "rather not say."
                )
                stage = 111
            }

            111 -> {
                player("Oh go on, what happend to them?")
                stage = 112
            }

            112 -> {
                npc("They say she turned them into little spiders.")
                stage = 113
            }

            113 -> {
                player("Oh, that's too bad. I had better leave you to it.")
                stage = 114
            }

            114 -> end()
            120 -> {
                npc(
                    "Nope. Clean this, clean that. When I'm finished cleaning",
                    "here I have to go help out in the garden."
                )
                stage = 121
            }

            121 -> {
                player("What garden?")
                stage = 122
            }

            122 -> {
                npc("Oh, I shouldn't have told you.")
                stage = 123
            }

            123 -> {
                options("You're right, you shouldn't have.", "Oh, you can talk to me. I can see you're having a bad day.")
                stage = 124
            }

            124 -> when (buttonId) {
                1 -> {
                    player("You're right, you shouldn't have.")
                    stage = 125
                }

                2 -> {
                    player("Oh, you can talk to me. I can see you're having a bad", "day.")
                    stage = 126
                }
            }

            125 -> end()
            126 -> {
                npc("You know you're right. Nobody listens to me.")
                stage = 127
            }

            127 -> {
                player("A sympathetic ear can do wonders.")
                stage = 128
            }

            128 -> {
                npc("Yes, if I just let my frustrations out, I'd feel a lot", "better. Now what was I saying?")
                stage = 129
            }

            129 -> {
                player("You mentioned something about the garden.")
                stage = 130
            }

            130 -> {
                npc("Oh yeah, the dreadful garden of hers.")
                stage = 131
            }

            131 -> {
                player("Where is it?")
                stage = 132
            }

            132 -> {
                npc("Oh, it's nowhere.")
                stage = 133
            }

            133 -> {
                player("What do you mean?")
                stage = 134
            }

            134 -> {
                npc(
                    "Well it's here, but not really. You see the sorceress is",
                    "trying out some new type of compression magic."
                )
                stage = 135
            }

            135 -> {
                player("Oh, that sounds interesting - so how does it work?")
                stage = 136
            }

            136 -> {
                npc("It would take too long to explain and, to be honest, I", "don't really understand how it works.")
                stage = 137
            }

            137 -> {
                player("Fair enough, but tell me, how do you get to the", "garden?")
                stage = 138
            }

            138 -> {
                npc("By magic! The sorceress did teach me one spell.")
                stage = 139
            }

            139 -> {
                options("Wow, cast the spell on me. It will be good Magic training for you.",
                    "Oh, that's nice. Well it's been great talking to you."
                )
                stage = 140
            }

            140 -> when (buttonId) {
                1 -> {
                    player("Wow, cast the spell on me. It will be good Magic", "training for you.")
                    stage = 142
                }

                2 -> {
                    player("Oh, that's nice. Well it's been great", "talking to you.")
                    stage = 141
                }
            }

            141 -> end()
            142 -> {
                npc("You wouldn't mind?")
                stage = 143
            }

            143 -> {
                player("Of course not. I'd be glad to help.")
                stage = 144
            }

            144 -> {
                npc("Okay, here goes! Remember, to return, just drink from", "the fountain.")
                stage = 145
            }

            145 -> {
                player.getSavedData().globalData.setApprentice(true)
                teleport(npc, player)
                end()
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.APPRENTICE_5532)
    }

    companion object {
        fun teleport(npc: NPC, player: Player) {
            if (!hasRequirement(player, "Prince Ali Rescue")) return
            npc.faceTemporary(player, 4)
            npc.graphics(Graphic(108))
            player.lock()
            Projectile.create(npc, player, 109).send()
            npc.sendChat("Senventior Disthinte Molesko!")
            Pulser.submit(object : Pulse(1) {
                var counter = 0
                override fun pulse(): Boolean {
                    when (counter++) {
                        2 -> player.properties.teleportLocation = Location.create(2912, 5474, 0)
                        3 -> {
                            player.graphics(Graphic(110))
                            player.unlock()
                            return true
                        }
                    }
                    return false
                }
            })
        }
    }
}