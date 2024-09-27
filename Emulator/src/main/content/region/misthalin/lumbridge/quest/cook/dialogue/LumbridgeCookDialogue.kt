package content.region.misthalin.lumbridge.quest.cook.dialogue

import core.api.*
import org.rs.consts.Items
import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import org.rs.consts.QuestName

/**
 * Represents the Lumbridge Cook dialogue.
 */
@Initializable
class LumbridgeCookDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (player?.questRepository?.getQuest(QuestName.THE_LOST_TRIBE)?.getStage(player) == 10) {
            player("Did you see what happened in the cellar?")
            stage = 0
            return true
        }
        if (getQuestStage(player, QuestName.COOKS_ASSISTANT) <= 0) {
            npc(FacialExpression.SAD, "What am I to do?")
            stage = 0
            return true
        }
        else if (getQuestStage(player, QuestName.COOKS_ASSISTANT) in 10..99) {
            if (getAttribute(player, "cooks_assistant:all_submitted", false) || (getAttribute(player, "cooks_assistant:milk_submitted", false) && getAttribute(player, "cooks_assistant:flour_submitted", false) && getAttribute(player, "cooks_assistant:egg_submitted", false))) {
                npc(FacialExpression.HAPPY, "You've brought me everything I need! I am saved!", "Thank you!")
                stage = 200
                return true
            }
            else {
                npc(FacialExpression.SAD, "How are you getting on with finding the ingredients?")
                gave = false
                stage = 100
                return true
            }
        }
        npc(FacialExpression.HAPPY, "Hello friend, how is the adventuring going?")
        stage = 300
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        /*
         * Lost Tribe quest dialogue.
         */
        if (getQuestStage(player, QuestName.THE_LOST_TRIBE) == 10) {
            when (stage) {
                0 -> npc("Last night I was in the kitchen and I heard a noise", "from the cellar. I opened the trapdoor and saw a", "creature dart into a hole in the wall.").also { stage++ }
                1 -> npc("It looked a bit like a goblin, but it had big bulging eyes.", "It wasn't wearing armour, but it had this odd helmet", "with a light on it.").also { stage++ }
                2 -> npc("The tunnel was too dark for me to follow it, so I went", "to tell the Duke. But when we went down to the cellar", "the hole had been blocked up, and no one believes me.").also { stage++ }
                3 -> player("I believe you.").also { stage++ }
                4 -> npc("Thank you, ${player.name}! If you can convince the Duke", "I'm telling the truth then we can get to the bottom of", "this mystery.").also { stage = 1000; setQuestStage(player, QuestName.THE_LOST_TRIBE, 20) }
                5 -> end()
            }
            return true
        }
        when (stage) {
            0 -> options("What's wrong?", "Can you make me a cake?", "You don't look very happy.", "Nice hat!").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.NEUTRAL, "What's wrong?").also { stage = 10 }
                2 -> player(FacialExpression.ASKING, "You're a cook, why don't you bake me a cake?").also { stage = 20 }
                3 -> player(FacialExpression.NEUTRAL, "You don't look very happy.").also { stage = 30; }
                4 -> player(FacialExpression.HAPPY, "Nice hat!").also { stage = 40 }
            }
            10 -> npc(FacialExpression.SCARED, "Oh dear, oh dear, oh dear, I'm in a terrible terrible", "mess! It's the Duke's birthday today, and I should be", "making him a lovely big birthday cake.").also { stage++ }
            11 -> npc(FacialExpression.SAD, "I've forgotten to buy the ingredients. I'll never get", "them in time now. He'll sack me! What will I do? I have", "four children and a goat to look after. Would you help", "me? Please?").also { stage++ }
            12 -> options("I'm always happy to help a cook in distress.", "I can't right now, Maybe later.").also { stage++ }
            13 -> when (buttonId) {
                1 -> player(FacialExpression.HAPPY, "Yes, I'll help you.").also { stage = 50 }
                2 -> player(FacialExpression.ANNOYED, "No, I don't feel like it. Maybe later.").also { stage++ }
            }
            14 -> npc(FacialExpression.SAD, "Fine. I always knew you Adventurer types were callous", "beasts. Go on your merry way!").also { stage = 1000 }
            20 -> npc(FacialExpression.SAD, "*sniff* Don't talk to me about cakes...").also { stage++ }
            21 -> player(FacialExpression.NEUTRAL, "What's wrong?").also { stage = 10 }
            30 -> npc(FacialExpression.SAD, "No, I'm not. The world is caving in around me - I am", "overcome by dark feelings of impending doom.").also { stage++ }
            31 -> options("What's wrong?", "I'd take the rest of the day off if I were you.").also { stage++ }
            32 -> when (buttonId) {
                1 -> player(FacialExpression.NEUTRAL, "What's wrong?").also { stage = 10 }
                2 -> player(FacialExpression.NEUTRAL, "I'd take the rest of the day off if I were you.").also { stage++ }
            }
            33 -> npc(FacialExpression.SAD, "No, that's the worst thing I could do. I'd get in terrible", "trouble.").also { stage++ }
            34 -> player(FacialExpression.ASKING, "Well maybe you need to take a holiday...").also { stage++ }
            35 -> npc(FacialExpression.SAD, "That would be nice, but the Duke doesn't allow holidays", "for core staff").also { stage++ }
            36 -> player(FacialExpression.SUSPICIOUS, "Hmm, why not run away to the sea and start a new", "life as a Pirate?").also { stage++ }
            37 -> npc(FacialExpression.SAD, "My wife gets sea sick, and I have an irrational fear of", "eyepatches. I don't see it working myself.").also { stage++ }
            38 -> player(FacialExpression.NEUTRAL, "I'm afraid I've run out of ideas.").also { stage++ }
            39 -> npc(FacialExpression.SAD, "I know I'm doomed.").also { stage = 21 }
            40 -> npc(FacialExpression.SAD, "Er, thank you. It's a pretty ordinary cook's hat, really.").also { stage++ }
            41 -> player(FacialExpression.HAPPY, "Still, it suits you. The trousers are pretty special too.").also { stage++ }
            42 -> npc(FacialExpression.SAD, "It's all standard-issue cook's uniform.").also { stage++ }
            43 -> player(FacialExpression.HAPPY, "The whole hat, apron, stripy trousers ensemble. It", "works. It makes you looks like a real cook.").also { stage++ }
            44 -> npc(FacialExpression.ANGRY, "I AM a real cook! I haven't got time to be chatting", "about culinary fashion. I'm in desperate need of help!").also { stage = 21 }
            50 -> npc(FacialExpression.HAPPY, "Oh thank you, thank you. I need milk, an egg and", "flour. I'd be very grateful if you can get them for me.").also { player?.questRepository?.getQuest(QuestName.COOKS_ASSISTANT)?.start(player!!); stage++ }
            51 -> player(FacialExpression.NEUTRAL, "So where do I find these ingredients then?").also { stage = 60 }
            60 -> options("Where do I find some flour?", "How about milk?", "And eggs? Where are they found?", "Actually, I know where to find this stuff.").also { stage++ }
            61 -> when (buttonId) {
                1 -> npc(FacialExpression.NEUTRAL, "There is a Mill fairly close, go North and then West.", "Mill Lane Mill is just off the road to Draynor. I", "usually get my flour from there.").also { stage = 70 }
                2 -> npc(FacialExpression.NEUTRAL, "There is a cattle field on the other side of the river,", "just across the road from the Groats' Farm.").also { stage = 71 }
                3 -> npc(FacialExpression.NEUTRAL, "I normally get my eggs from the Groats' farm, on the", "other side of the river.").also { stage = 73 }
                4 -> player(FacialExpression.NEUTRAL, "Actually, I know where to find this stuff.").also { stage = 1000 }
            }
            70 -> npc(FacialExpression.SUSPICIOUS, "Talk to Millie, she'll help, she's a lovely girl and a fine", "Miler. Make sure you take a pot with you for the flour", "though, " + if (inInventory(player, EMPTY_POT, 1)) "you've got one on you already." else "there should be one on the table in here.").also { stage = 80 }
            71 -> npc(FacialExpression.SUSPICIOUS, "Talk to Gillie Groats, she looks after the Dairy cows -", "she'll tell you everything you need to know about", "milking cows!").also { stage++ }
            72 -> if (inInventory(player, EMPTY_BUCKET, 1)) {
                npc(FacialExpression.NEUTRAL, "You'll need an empty bucket for the milk itself. I do see", "you've got a bucket with you already luckily!").also { stage = 80 }
            } else {
                npc(FacialExpression.NEUTRAL, "You'll need an empty bucket for the milk itself. The", "general store just north of the castle will sell you one", "for a couple of coins.").also { stage = 80 }
            }
            73 -> npc(FacialExpression.NEUTRAL, "But any chicken should lay eggs.").also { stage = 80 }
            80 -> options("Where do I find some flour?", "How about milk?", "And eggs? Where are they found?", "I've got all the information I need. Thanks.").also { stage++ }

            81 -> when (buttonId) {
                1 -> npc(FacialExpression.NEUTRAL, "There is a Mill fairly close, go North and then West.", "Mill Lane Mill is just off the road to Draynor. I", "usually get my flour from there.").also { stage = 70 }
                2 -> npc(FacialExpression.NEUTRAL, "There is a cattle field on the other side of the river,", "just across the road from the Groats' Farm.").also { stage = 71 }
                3 -> npc(FacialExpression.NEUTRAL, "I normally get my eggs from the Groats' farm, on the", "other side of the river.").also { stage = 73 }
                4 -> player(FacialExpression.NEUTRAL, "I've got all the information I need. Thanks.").also { stage = 1000 }
            }
            100 ->
                if (!getAttribute(player,"cooks_assistant:milk_submitted", false) && inInventory(player, MILK, 1)) {
                    setAttribute(player, "/save:cooks_assistant:milk_submitted", true).also {
                        player(FacialExpression.HAPPY, "Here's a bucket of milk.")
                        player.inventory.remove(Item(MILK))
                        gave = true
                        stage = 100
                    }
                } else if (!getAttribute(player,"cooks_assistant:flour_submitted", false) && inInventory(player, FLOUR, 1)) {
                    setAttribute(player, "/save:cooks_assistant:flour_submitted", true).also {
                        player(FacialExpression.HAPPY, "Here's a pot of flour.")
                        player.inventory.remove(Item(FLOUR))
                        gave = true
                        stage = 100
                    }
                } else if (!getAttribute(player, "cooks_assistant:egg_submitted", false) && inInventory(player, EGG, 1)) {
                    setAttribute(player, "/save:cooks_assistant:egg_submitted", true).also {
                        player(FacialExpression.HAPPY, "Here's a fresh egg.")
                        player.inventory.remove(Item(EGG))
                        gave = true
                        stage = 100
                    }
                } else {
                    if (gave) {
                        if (!player.getAttribute("cooks_assistant:submitted_some_items", false)) {
                            setAttribute(player, "/save:cooks_assistant:submitted_some_items", true)
                        }
                        if (getAttribute(player, "cooks_assistant:milk_submitted", false) && getAttribute(player, "cooks_assistant:flour_submitted", false) && getAttribute(player,"cooks_assistant:egg_submitted", false)) {
                            npc(FacialExpression.HAPPY, "You've brought me everything I need! I am saved!", "Thank you!").also { setAttribute(player, "/save:cooks_assistant:all_submitted", true); stage = 200 }
                        } else {
                            npc(FacialExpression.WORRIED, "Thanks for the ingredients you have got so far, please get", "the rest quickly. I'm running out of time! The Duke", "will throw me into the streets!").also { stage = 151 }
                        }
                    } else {
                        if (!getAttribute(player,"cooks_assistant:submitted_some_items", false)) {
                            player(FacialExpression.NEUTRAL, "I haven't gotten any of them yet, I'm still looking.").also { stage = 155 }
                        } else {
                            options("I'll get right on it.", "Can you remind me how to find these things again?").also { stage = 161 }
                        }
                    }
                }
            150 -> npc(FacialExpression.WORRIED, "Thanks for the ingredients you have got so far, please get", "the rest quickly. I'm running out of time! The Duke", "will throw me into the streets!").also { stage++ }
            151 -> leftoverItems = "".also {
                if (!getAttribute(player,"cooks_assistant:milk_submitted", false)) {
                    leftoverItems += "A bucket of milk. "
                }
                if (!getAttribute(player,"cooks_assistant:flour_submitted", false)) {
                    leftoverItems += "A pot of flour. "
                }
                if (!getAttribute(player,"cooks_assistant:egg_submitted", false)) {
                    leftoverItems += "An egg."
                }
                if (leftoverItems != "") {
                    sendDialogue("You still need to get:", leftoverItems).also { stage = 160 }
                } else {
                    npc(FacialExpression.HAPPY, "You've brought me everything I need! I am saved!", "Thank you!").also { setAttribute(player, "/save:cooks_assistant:all_submitted", true); stage = 200 }
                }
            }
            155 -> npc(FacialExpression.WORRIED, "Please get the ingredients quickly. I'm running out of", "time! The Duke will throw me into the streets!").also { stage++ }
            156 -> sendDialogue("You still need to get:", "A bucket of milk. A pot of flour. An egg.").also { stage = 160 }
            160 -> options("I'll get right on it.", "Can you remind me how to find these things again?").also { stage++ }
            161 -> when (buttonId) {
                1 -> player(FacialExpression.NEUTRAL, "I'll get right on it.").also { stage = 1000 }
                2 -> player(FacialExpression.ASKING, "Can you remind me how to find these things again?").also {
                    stage = 60
                }
            }
            200 -> player(FacialExpression.HAPPY, "So do I get to go to the Duke's Party?").also { stage++ }
            201 -> npc(FacialExpression.SAD, "I'm afraid not, only the big cheeses get to dine with the", "Duke.").also { stage++ }
            202 -> player(FacialExpression.NEUTRAL, "Well, maybe one day I'll be important enough to sit on", "the Duke's table.").also { stage++ }
            203 -> npc(FacialExpression.NEUTRAL, "Maybe, but I won't be holding my breath.").also { stage++ }
            204 -> end().also { finishQuest(player, QuestName.COOKS_ASSISTANT) }
            300 -> if (getQuestStage(player, QuestName.THE_LOST_TRIBE) == 10) {
                player("Do you know what happened in the castle cellar?").also { stage = 600 }
            } else {
                options("I am getting strong and mighty.", "I keep on dying.", "Can I use your range?").also { stage++ }
            }
            301 -> when (buttonId) {
                1 -> player(FacialExpression.HAPPY, "I am getting strong and mighty. Grrr").also { stage = 310 }
                2 -> player(FacialExpression.SAD, "I keep on dying.").also { stage = 320 }
                3 -> player(FacialExpression.ASKING, "Can I use your range?").also { stage = 330 }
            }
            310 -> npc(FacialExpression.HAPPY, "Glad to hear it!").also { stage = 1000 }
            320 -> npc(FacialExpression.HAPPY, "Ah, well, at least you keep coming back to life too!").also { stage = 1000 }
            330 -> npc(FacialExpression.HAPPY, "Go ahead! It's a very good range; it's better than most", "other ranges.").also { stage++ }
            331 -> npc(FacialExpression.NEUTRAL, "It's called the Cook-o-Matic 100 and it uses a combination", "of state-of-the-art temperature regulation and magic.").also { stage++ }
            332 -> player(FacialExpression.ASKING, "Will it mean my food will burn less often?").also { stage++ }
            333 -> npc(FacialExpression.NEUTRAL, "Well, that's what the salesman told us anyway...").also { stage++ }
            334 -> player(FacialExpression.THINKING, "Thanks?").also { stage = 1000 }
            1000 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.COOK_278)
    }

    companion object {
        const val EMPTY_BUCKET = Items.BUCKET_1925
        const val MILK = Items.BUCKET_OF_MILK_1927
        const val EMPTY_POT = Items.EMPTY_POT_1931
        const val FLOUR = Items.POT_OF_FLOUR_1933
        const val EGG = Items.EGG_1944

        var gave = false
        var leftoverItems = ""
    }
}
