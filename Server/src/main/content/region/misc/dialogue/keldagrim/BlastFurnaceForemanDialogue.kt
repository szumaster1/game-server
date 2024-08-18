package content.region.misc.dialogue.keldagrim

import core.api.addItemOrDrop
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.removeItem
import core.api.sendChat
import core.api.sendItemDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.dialogue.Topic
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.RandomFunction

/**
 * Represents the Blast furnace foreman dialogue.
 */
@Initializable
class BlastFurnaceForemanDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                npcl(FacialExpression.OLD_ANGRY1, "You! Get to work!")
                stage++
            }

            1 -> showTopics(
                Topic("What?", 2, true),
                Topic("Ask about dragon metal.", 52, true),
                Topic(FacialExpression.FRIENDLY, "Okay.", END_DIALOGUE),
            )

            2 -> {
                npcl(FacialExpression.OLD_DEFAULT, "You are here to help work the blast furnace, aren't you?")
                stage++
            }

            3 -> {
                showTopics(
                    Topic(FacialExpression.FRIENDLY, "What's the blast furnace?", 4),
                    Topic(FacialExpression.FRIENDLY, "How can I help work the blast furnace?", 6),
                    Topic(FacialExpression.FRIENDLY, "Can I use the furnace to smelt ore?", 8)
                )
            }

            4 -> {
                npcl(FacialExpression.OLD_DEFAULT, "The blast furnace is the pinnacle of dwarven metal- processing! Ore goes in the top and comes out as metal bars almost at once! And it's so efficient it only takes half as much coal to purify it as a regular furnace.")
                stage++
            }

            5 -> {
                npcl(FacialExpression.OLD_DEFAULT, "But we've got a bit of a labour shortage at the moment, which is why I have to rely on immigrant workers to keep the furnace going. So, are you here to start work?")
                stage = 100
            }

            6 -> {
                npcl(FacialExpression.OLD_DEFAULT, "The blast furnace will only work if there is a group of people keeping it going. Let me explain...")
                stage++
            }

            7 -> {
                npcl(FacialExpression.OLD_DEFAULT, "BLAST FURNACE PLAN INTERFACE")
                stage = 100
            }

            8 -> {
                npcl(FacialExpression.OLD_DEFAULT, "Keeping the furnace going is valuable experience in itself!")
                stage++
            }

            9 -> {
                npcl(FacialExpression.OLD_DEFAULT, "Keeping the stove alight will train your Firemaking, working the pump will train your Strength, pedalling the conveyor belt will train your Agility and repairing the furnace will train Crafting.")
                stage++
            }

            10 -> {
                npcl(FacialExpression.OLD_DEFAULT, "Plus, everyone working the furnace will get a bit of experience in Smithing whenever anyone smelts ore in it.")
                stage = 100
            }

            11 -> {
                npcl(FacialExpression.OLD_DEFAULT, "I offer you the privilege of working on the greatest example of dwarven engineering and you want to be PAID?")
                stage++
            }

            12 -> {
                npcl(FacialExpression.OLD_DEFAULT, "If you co-operate with a group of people you can all get your smelting done pretty quickly. Or perhaps some humans who need to do some smelting will pay you, since you humans are all so obsessed with money.")
                stage = 100
            }

            24 -> {
                npcl(FacialExpression.OLD_DEFAULT, "What would be the point of making money if I gave it all away to menial workers?")
                stage++
            }

            25 -> {
                npcl(FacialExpression.OLD_DEFAULT, "I mean... Money isn't important! What about the greater glory of dwarven engineering? What about the satisfaction of helping others? What about the XP?")
                stage = 100
            }

            52 -> {
                playerl(FacialExpression.HALF_ASKING, "What would dragon metal be useful for?")
                stage = 53
            }

            53 -> {
                npcl(FacialExpression.OLD_DEFAULT, "Well, I guess if you could find a furnace hot enough and a hammer hot enough, you could probably make it into some effective personal armour.")
                stage = 54
            }

            54 -> {
                npcl(FacialExpression.OLD_DEFAULT, "I'm afraid I can't offer any advice on the furnace, but I may be able to help out with the hammer.")
                stage = 55
            }

            55 -> {
                playerl(FacialExpression.FRIENDLY, "Oh, really?")
                stage = 56
            }

            56 -> {
                npcl(FacialExpression.OLD_DEFAULT, "Yes, you'll need very high heat resistance to work with difficult materials. I've got a very special type of hammer, which I hold back for experienced smiths - I call it the blast fusion hammer.")
                stage = 57
            }

            57 -> {
                npcl(FacialExpression.OLD_DEFAULT, "It's very hard-wearing and of the utmost quality.")
                stage = 58
            }

            58 -> {
                playerl(FacialExpression.HALF_ASKING, "And, I suppose, very expensive?")
                stage = 59
            }

            59 -> {
                npcl(FacialExpression.OLD_DEFAULT, "Quality doesn't come cheap, my friend.")
                stage = 60
            }

            60 -> {
                options("How much is a hammer?", "I want to trade in my hammer and get some money back.", "How come you have such a hammer in the first place?", "What would dragon metal be used for?", "Okay, thanks.")
                stage = 61
            }

            61 -> when (buttonId) {
                1 -> {
                    playerl(FacialExpression.HALF_ASKING, "How much is a hammer?")
                    stage = 62
                }

                2 -> {
                    playerl(FacialExpression.FRIENDLY, "I want to trade in my hammer and get some money back.")
                    stage = 65
                }

                3 -> {
                    playerl(FacialExpression.FRIENDLY, "How come you have such a hammer in the first place?")
                    stage = 68
                }

                4 -> {
                    playerl(FacialExpression.FRIENDLY, "What would dragon metal be used for?")
                    stage = 53
                }

                5 -> {
                    playerl(FacialExpression.FRIENDLY, "Okay, thanks.")
                    stage = 100
                }
            }

            62 -> {
                npcl(FacialExpression.OLD_DEFAULT, "It costs 1,000,000 coins.")
                stage = 63
            }

            63 -> {
                options("Buy blast fusion hammer : 1,000,000 (1M) coins.", "I'll leave it for now, thanks.")
                stage = 64
            }

            64 -> when (buttonId) {
                1 -> if (!removeItem(player, Item(Items.COINS_995, 1000000))) {
                    npcl(
                        FacialExpression.OLD_DEFAULT,
                        "You don't have enough money to buy a blast fusion hammer, come back when you can afford one."
                    )
                    stage = 100
                } else {
                    sendItemDialogue(
                        player!!,
                        Items.BLAST_FUSION_HAMMER_14478,
                        "You hand over 1,000,000 coins for the blast fusion hammer."
                    )
                    addItemOrDrop(player, Items.BLAST_FUSION_HAMMER_14478)
                    stage = 100
                }

                2 -> {
                    playerl(FacialExpression.FRIENDLY, "I'll leave it for now, thanks.")
                    stage = 100
                }
            }

            65 -> {
                npcl(
                    FacialExpression.OLD_DEFAULT,
                    "No problem. Just hand me the hammer you want to return and I'll tell you how much I can give you back."
                )
                stage = 66
            }

            66 -> {
                playerl(FacialExpression.FRIENDLY, "I just, er, use the hammer on you and we'll agree a price, yes?")
                stage = 67
            }

            67 -> {
                npc(FacialExpression.OLD_DEFAULT, "Exactly!")
                stage = 100
            }

            68 -> {
                npcl(
                    FacialExpression.OLD_DEFAULT,
                    "An interesting question, which, I'm sorry to say, I can't answer. Top secret dwarven technological and industrial secrets, you see."
                )
                stage = 69
            }

            69 -> {
                playerl(FacialExpression.FRIENDLY, "Oh, sounds exciting. Come on, you can tell me...")
                stage = 70
            }

            70 -> {
                npcl(FacialExpression.OLD_DEFAULT, "Oh, okay, but keep it to yourself.")
                stage = 71
            }

            71 -> {
                playerl(FacialExpression.FRIENDLY, "Will do.")
                stage = 72
            }

            72 -> {
                npcl(
                    FacialExpression.OLD_DEFAULT,
                    "Very well. Us dwarves have been working on a series of very important metallurgical experiements. We found some quite interesting rock formations, in a place that shall remain nameless,"
                )
                stage = 73
            }

            73 -> {
                npcl(
                    FacialExpression.OLD_DEFAULT,
                    "and our experiments developed and evolved until we came up with the blast fusion hammer. The hammer is capable of working even the strongest and most magical of metals!"
                )
                stage = 100
            }

            100 -> end()
        }
        return true
    }


    /**
     * Chat pulse.
     */
    class ChatPulse(val npc: NPC) : Pulse() {
        companion object {
            val forceChat = arrayOf(
                "Good work soldier!",
                "Push it!",
                "Work it!",
                "The dummy is the enemy. Kill it!",
                "Put your back into it soldier!",
                "You're not out for a sunday stroll soldier!",
                "My daughter can hit harder than that!",
                "I want to see you sweat!",
                "Keep it up soldier!"
            )
        }

        var ticks = 0
        override fun pulse(): Boolean {
            ticks++
            if (ticks % 100 == 0) {
                sendChat(npc, forceChat[RandomFunction.random(forceChat.size)])
            }
            return true
        }
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BLAST_FURNACE_FOREMAN_2553)
    }
}
