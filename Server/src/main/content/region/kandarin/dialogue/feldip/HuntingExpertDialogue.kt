package content.region.kandarin.dialogue.feldip

import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.plugin.Initializable

/**
 * The Hunting expert.
 */
@Initializable
class HuntingExpertDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        interpreter.sendDialogues(
            npc,
            FacialExpression.HALF_GUILTY,
            "Whoah there, stranger, careful where you're walking. I",
            "almost clobbered you there; thought you were a larupia",
            "or something."
        )
        stage = 1
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            1 -> {
                interpreter.sendOptions(
                    "Select an Option",
                    "What are you doing here?",
                    "What's a larupia?",
                    "What is that cool cape you're wearing?",
                    "Ahh, leave me alone you crazy killing person."
                )
                stage = 2
            }

            2 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY, "What are you doing here?")
                    stage = 10
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY, "What's a larupia?")
                    stage = 20
                }

                3 -> {
                    player(FacialExpression.HALF_GUILTY,
                        "What is that cool cape you're wearing?"
                    )
                    stage = 30
                }

                4 -> {
                    player(FacialExpression.HALF_GUILTY,
                        "Ahh, leave me alone you crazy killing person."
                    )
                    stage = 140
                }
            }

            10 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "Me? Oh, I'm just having some fun, capturing wild",
                    "animals, living on the edge, stuff like that."
                )
                stage = 11
            }

            11 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "Hey, want me to teach you how to catch the critters",
                    "around here? Ain't come across anything yet that I",
                    "couldn't capture."
                )
                stage = 12
            }

            12 -> {
                interpreter.sendOptions(
                    "Select an Option",
                    "Okay, where's a good place to start?",
                    "What sort of things are there to catch around here?",
                    "How can I improve my chances of making a capture?",
                    "How can I get clothes and equipment like yours?",
                    "I think I'll give it a miss right now."
                )
                stage = 13
            }

            13 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY,
                        "Ok, where's a good place to start?"
                    )
                    stage = 100
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY,
                        "What sort of things are there to catch around here?"
                    )
                    stage = 110
                }

                3 -> {
                    player(FacialExpression.HALF_GUILTY,
                        "How can I improve my chances of making a capture?"
                    )
                    stage = 120
                }

                4 -> {
                    player(FacialExpression.HALF_GUILTY,
                        "Where can I get clothes and equipment like yours?"
                    )
                    stage = 130
                }

                5 -> {
                    player(FacialExpression.HALF_GUILTY,
                        "I think I'll give it a miss right now, thanks."
                    )
                    stage = 150
                }
            }

            100 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "Your easiest bet around here is probably to go for some",
                    "of the birds. Go for crimson swifts; they're the",
                    "bright red ones that hang around near the coast.",
                    "They're as obliging as can be, or maybe they're just"
                )
                stage = 101
            }

            101 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "plain dumb, but either way they just seem to throw",
                    "themselves into traps half the time."
                )
                stage = 102
            }

            102 -> {
                player(FacialExpression.HALF_GUILTY, "What sort of trap should I use?")
                stage = 103
            }

            103 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "Just use a standard bird snare and you'll be fine. You",
                    "can get them in any decent shop that sells Hunter",
                    "gear."
                )
                stage = 104
            }

            104 -> {
                player(FacialExpression.HALF_GUILTY, "How do they work, then?")
                stage = 105
            }

            105 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "Well, they're kind of sneaky, really. You've got a tall",
                    "post with what appears to be a little perch sticking out",
                    "the side. Now, that the bird will see this as somewhere to sit",
                    "but the perch is actually rigged such that when the bird"
                )
                stage = 106
            }

            106 -> {
                npc(FacialExpression.HALF_GUILTY, "lands on it, it'll drop away.")
                stage = 107
            }

            107 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "Further, as the perch drops, it releases a noose that",
                    "tightens around the bird's feet and captures it. Neat",
                    "huh?"
                )
                stage = 108
            }

            110 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "What're you interested in? You've got anything from",
                    "birds for beginners up to larupias for the pros, not to",
                    "mention weasels, butterflies, barb-tails and chinchompas."
                )
                stage = 111
            }

            111 -> end()
            120 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "There are four key factors that will affect your",
                    "chances: what bait you use, how close to the trap you",
                    "are, your appearance and your smell."
                )
                stage = 121
            }

            121 -> end()
            130 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "Well, the equipment isn't too hard to get hold of. You've",
                    "got specialist shops like Aleck's in Yanille that'll see you",
                    "right. He's a bit pricey, mind, but it's pretty decent kit."
                )
                stage = 131
            }

            131 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "The clothing is a tad trickier to get hold of, the",
                    "materials required being rather difficult to separate from",
                    "their reluctant owners."
                )
                stage = 132
            }

            132 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "But, if you can get the furs, there are shops like the",
                    "tailor's in east Varrock that can help you."
                )
                stage = 133
            }

            133 -> end()
            140 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "Heh, well I guess it's not everyone's thing."
                )
                stage = 141
            }

            150 -> {
                npc(FacialExpression.HALF_GUILTY, "Suit yourself!")
                stage = 151
            }

            151 -> end()
            108 -> end()
            20 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "What's a larupia? You don't know? You mean you",
                    "really don't recognise the significance of these clothes",
                    "I'm wearing?"
                )
                stage = 21
            }

            21 -> {
                player(FacialExpression.HALF_GUILTY,
                    "Well, they're certainly decorative...what about them?"
                )
                stage = 22
            }

            22 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "Well, these are the furs from a larupia. Caught the",
                    "creature myself I did, and these make me blend in",
                    "better, see?"
                )
                stage = 23
            }

            23 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "If you want to be successful as a hunter you've got to",
                    "be as stealthy as a kyatt, as quiet as a mouse and blend",
                    "in like a, well like a larupia, I guess!"
                )
                stage = 24
            }

            24 -> end()
            30 -> stage = if (player.getSkills().getLevel(Skills.HUNTER) < 99) {
                npc(FacialExpression.HALF_GUILTY,
                    "This? It's a Hunter's Skillcape. It shows that I am a",
                    "true master of the art of Hunting. If you ever manage",
                    "to train your Hunter skill to it maximum level then I",
                    "could sell you one."
                )
                31
            } else {
                npc("An adventurer whom possesses the same level",
                    "of Hunter as me, would you be interested in buying",
                    "a skillcape of Hunter for 99,000 gold coins?"
                )
                35
            }

            31 -> end()
            35 -> {
                options( "Yes, please.", "No, thank you.")
                stage = 36
            }

            36 -> when (buttonId) {
                1 -> {
                    player("Yes, please.")
                    stage = 38
                }

                2 -> {
                    player("Yes, please.")
                    stage = 99
                }
            }

            38 -> if (!player.inventory.contains(995, 99000)) {
                player("Sorry, I don't have that amount of money.")
                stage = 99

            } else {
                if (player.inventory.freeSlots() < 2) {
                    player("Sorry, I don't seem to have enough inventory space.")
                    stage = 99

                }
                if (!player.inventory.containsItem(COINS)) {
                    end()
                    return true
                }
                if (player.inventory.remove(COINS)) {
                    player.inventory.add(if (player.getSkills().masteredSkills > 1) ITEMS[1] else ITEMS[0], ITEMS[2])
                    npc("There you go! Enjoy adventurer.")
                    stage = 99
                }
            }

            99 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(5113)
    }

    companion object {
        private val ITEMS = arrayOf(Item(9948), Item(9949), Item(9950))
        private val COINS = Item(995, 99000)
    }
}