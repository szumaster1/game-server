package content.region.desert.dialogue.pollnivneach

import core.api.consts.NPCs
import core.api.sendDialogueOptions
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable

@Initializable
class AliTheCamelManDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc("Welcome to my discount camel store.", "Can I help you with anything?")
        stage = 0
        return true
    }

    override fun handle(interaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                sendDialogueOptions(player, "Select one.", "A discount camel store?", "Tell me about this town.", "Lovely day isn't it?", "Are those camels around the side for sale?", "I'm looking for Ali from Pollnivneach.")
                stage = 10
            }

            10 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.ASKING, "A discount camel store?")
                    stage = 20
                }

                2 -> {
                    player(FacialExpression.THINKING, "Tell me about this town.")
                    stage = 30
                }

                3 -> {
                    player(FacialExpression.ASKING, "Lovely day isn't it?")
                    stage = 40
                }

                4 -> {
                    player(FacialExpression.ASKING, "Are those camels around the side for sale?")
                    stage = 50
                }

                5 -> {
                    player(FacialExpression.ASKING, "I'm looking for Ali from Pollnivneach.")
                    stage = 60
                }
            }

            20 -> {
                npc(FacialExpression.JOLLY, "Yes- a great idea - selling camels at discounted", "prices so that the common man can experience", "the joys of owning a camel too. They're not just", "a source of kebab meat you know!")
                stage = 21
            }

            21 -> {
                sendDialogueOptions(player, "Select one.", "So can I buy a camel then? I'm hungry!", "Yes camels are beautiful creatures.", "Filthy animals all they do is spit and...", "Actually I think I was a camel in a previous existence.", "So is business good then?")
                stage = 22
            }

            22 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.ASKING, "So can I buy a camel then? I'm hungry!")
                    stage = 230
                }

                2 -> {
                    player(FacialExpression.HAPPY, "Yes camels are beautiful creatures.")
                    stage = 240
                }

                3 -> {
                    player(FacialExpression.DISGUSTED, "Filthy animals all they do is spit and...")
                    stage = 250
                }

                4 -> {
                    player(FacialExpression.THINKING, "Actually I think I was a camel in a previous existence.")
                    stage = 260
                }

                5 -> {
                    player(FacialExpression.ASKING, "So is business good then?")
                    stage = 270
                }
            }

            230 -> {
                npc(FacialExpression.DISGUSTED, "I would never sell to the likes of you,", "they are beautiful majestic creatures not sandwich", "fillers! Get ouf my shop.")
                stage = 100
            }

            240 -> {
                npc(FacialExpression.LAUGH, "Ah, wondrous creatures, ships of the desert, far", "more useful than any horse or donkey.", "A man's best friend.")
                stage = 25
            }

            25 -> {
                npc(FacialExpression.FRIENDLY, "Is there anything else I can help you with?")
                stage = 26
            }

            26 -> {
                sendDialogueOptions(player, "Select one.", "Well yes actually I'd like to ask about something else.", "No but thanks for your time.")
                stage = 27
            }

            27 -> when (buttonId) {
                1 -> {
                    player("Well yes actually I'd like to ask about something else.")
                    stage = 0
                }

                2 -> {
                    player("No but thanks for your time.")
                    stage = 100
                }
            }

            250 -> {
                npc(FacialExpression.ANGRY, "Pah! You just do not know how to treat them.", "If you abuse them and treat them badly of", "course they are going to spit and bite.")
                stage = 25
            }

            260 -> {
                npc(FacialExpression.LAUGH, "You're teasing me now! You rogue! Everyone has", "a laugh at my expense, I'm just more", "enlightened than you!")
                stage = 25
            }

            270 -> {
                npc(FacialExpression.HAPPY, "Business is booming, Pollnivneach has never seen this", "number of visitors.")
                stage = 271
            }

            271 -> {
                npc(FacialExpression.THINKING, "In fact I'm thinking of expanding and trying to break", "into the camel hire market.")
                stage = 25
            }

            30 -> {
                npc(FacialExpression.THINKING, "Well Polvnivneach is a funny little place. It's a", "small town and you'd think it would be a", "quiet place.")
                stage = 300
            }

            300 -> {
                npc(FacialExpression.ANGRY, "But those menaphites and bandits are making this place", "quit unbearable.")
                stage = 310
            }

            310 -> {
                sendDialogueOptions(player, "Select one.", "In what way?", "Well the weak will always be trodden on. Why not stand up to them?", "Perhaps I can help.")
                stage = 311
            }

            311 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.ASKING, "In what way?")
                    stage = 320
                }

                2 -> {
                    player(FacialExpression.ASKING, "Well the weak will always be trodden on. Why not stand up to them?")
                    stage = 330
                }

                3 -> {
                    player(FacialExpression.FRIENDLY, "Perhaps I can help.")
                    stage = 340
                }
            }

            320 -> {
                npc(FacialExpression.ANGRY, "Well one group is just as bad as the other, stealing from each other", "and the locals, fighting, and just the last dayone of the", "bandits came looking for protection money.")
                stage = 321
            }

            321 -> {
                player(FacialExpression.ANGRY, "Why don't the authorities stop this?")
                stage = 322
            }

            322 -> {
                npc(FacialExpression.SAD, "If you haven't noticed this is a remote town, half way between", "Menaphos and Al Kharid, a place where neither", "exert any power.")
                stage = 323
            }

            323 -> {
                npc(FacialExpression.SAD, "To tell the truth we live in hope that an objective", "outsider could broker a deal to halt the hostilities", "between them.")
                stage = 324
            }

            324 -> {
                player("Perhaps I will.")
                stage = 25
            }

            330 -> {
                npc("That's a little unfair my friend. We can't all be brave", "adventurers. If we were, where would you go to get camels", "at discounted prices?")
                stage = 331
            }

            331 -> {
                npc(FacialExpression.ASKING, "You might think about that. Anyways isn't it the job of the brave", "adventurer to deliver the weak and innocent from", "evil and harm?")
                stage = 25
            }

            340 -> {
                //TODO: Add the quest and fix dialogue starting here.
                npc(FacialExpression.SAD, "No, I don't think you can just yet. Perhaps in the future.")
                stage = 100
            }

            40 -> {
                npc(FacialExpression.SUSPICIOUS, "It's a beautiful day today but word has it", "that it will rain tomorrow.")
                stage = 400
            }

            400 -> {
                player(FacialExpression.SUSPICIOUS, "Really?")
                stage = 410
            }

            410 -> {
                npc(FacialExpression.LAUGH, "No don't worry I'm only joking!")
                stage = 420
            }

            420 -> {
                player(FacialExpression.ANNOYED, "Ok so....")
                stage = 25
            }

            50 -> {
                npc(FacialExpression.HALF_GUILTY, "Those two camels around the side are already sold.")
                stage = 500
            }

            500 -> {
                npc(FacialExpression.FRIENDLY, "I've got new stock coming very soon though.", "Come back later if you're still interested.")
                stage = 25
            }

            60 -> {
                npc(FacialExpression.HAPPY, "Well you've found your man.")
                stage = 600
            }

            600 -> {
                player(FacialExpression.SUSPICIOUS, "Really do you have an uncle called Ali?")
                stage = 610
            }

            610 -> {
                npc(FacialExpression.NEUTRAL, "Why yes - All my uncles are called Ali!")
                stage = 620
            }

            620 -> {
                player(FacialExpression.THINKING, "Oh dear! Do any of them own a stall in the", "Al Kharid Bazaar?")
                stage = 630
            }

            630 -> {
                npc(FacialExpression.THINKING, "Well no, but one of them owns a camel hire franchise.")
                stage = 640
            }

            640 -> {
                player(FacialExpression.ANNOYED, "Never mind.")
                stage = 100
            }

            100 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ALI_THE_CAMEL_MAN_1867)
    }

}