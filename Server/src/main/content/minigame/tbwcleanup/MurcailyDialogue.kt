package content.minigame.tbwcleanup

import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE
import core.api.consts.NPCs
import core.api.addItemOrDrop
import core.api.anyInEquipment
import core.game.dialogue.Topic
import core.api.consts.Items
import kotlin.math.min

/**
 * Represents the Murcaily dialogue.
 */
@Initializable
class MurcailyDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (anyInEquipment(player, Items.TRIBAL_MASK_6335, Items.TRIBAL_MASK_6337, Items.TRIBAL_MASK_6339)) {
            npcl(FacialExpression.FRIENDLY, "AAAHHH!!!  Get away from me!").also { stage = END_DIALOGUE }
            player.sendMessage("Perhaps I should try taking of the mask before talking to the villagers.")
            return false
        } else if (!player.questRepository.isComplete("Jungle Potion")) {
            npcl(FacialExpression.FRIENDLY, "Sorry I am not interested in talking to you right now.").also {
                stage = END_DIALOGUE
            }
            return false
        }

        if (!player.getAttribute("/save:startedTBWCleanup", false) || player.getAttribute("/save:tbwcleanup", 0) == 0)
            options("What do you do here?", "Is there anything interesting to do here?").also { stage = START_DIALOGUE }
        else
            options("What do you do here?", "Is there anything interesting to do here?", "I've been doing some work around the village.").also { stage = START_DIALOGUE }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "What do you do here?").also { stage = 100 }
                2 -> playerl(FacialExpression.FRIENDLY, "Is there anything interesting to do here?").also {
                    stage = 200
                }

                3 -> playerl(FacialExpression.FRIENDLY, "I've been doing some work around the village.").also {
                    stage = 300
                }

                1 -> if (!player.getAttribute("/save:startedTBWCleanup", false) || player.getAttribute("/save:tbwcleanup", 0) == 0) {
                    options("What do you do here?", "Is there anything interesting to do here?", "Ok, thanks.").also { stage = 2 }
                } else {
                    options("What do you do here?", "Is there anything interesting to do here?", "I've been doing some work around the village.", "Ok, thanks.").also { stage = 3 }
                }
                2 -> when (buttonId) {
                    1 -> playerl(FacialExpression.FRIENDLY, "What do you do here?").also { stage = 100 }
                    2 -> playerl(FacialExpression.FRIENDLY, "Is there anything interesting to do here?").also { stage = 200 }
                    3 -> playerl(FacialExpression.FRIENDLY, "Ok, thanks.").also { stage = END_DIALOGUE }
                }
                3 -> when (buttonId) {
                    1 -> playerl(FacialExpression.FRIENDLY, "What do you do here?").also { stage = 100 }
                    2 -> playerl(FacialExpression.FRIENDLY, "Is there anything interesting to do here?").also { stage = 200 }
                    3 -> playerl(FacialExpression.FRIENDLY, "I've been doing some work around the village.").also { stage = 300 }
                    4 -> playerl(FacialExpression.FRIENDLY, "Ok, thanks.").also { stage = END_DIALOGUE }
                }
                100 -> npcl(FacialExpression.FRIENDLY, "Well, I tend the hardwood grove nearby, you can enter if you like and harvest some of the very<br> fine hardwood that grows within.").also { stage = 101 }
                101 -> npcl(FacialExpression.FRIENDLY, "Apart from that I'm a simple Tai Bwo Wannai villager... I like to try and find out what's going on in the mainland and the rest of the known world.").also { stage = 1 }
                200 -> npcl(FacialExpression.FRIENDLY, "Apart from cutting back the jungle and repairing  the fence, I think we've got most things covered now Bwana.").also { stage = 201 }
                201 -> options("Ask about cutting back the jungle.", "Ok, thanks.").also { stage = 202 }
            }
            202 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "You need some help with cutting back the Jungle?").also { stage = 203 }
                2 -> playerl(FacialExpression.FRIENDLY, "Ok, thanks.").also { stage = END_DIALOGUE }
            }
            203 -> npcl(FacialExpression.FRIENDLY, "Yes, we need to keep the jungle back from the village but jungle plants are tough, so you will need a machete to cut down the jungle.").also { stage = 204 }
            204 -> {
                player.setAttribute("/save:startedTBWCleanup", true)
                player.sendMessage("You have started the Tai Bwo Wannai Cleanup minigame.")
                npcl(FacialExpression.FRIENDLY, "You can also help us make the village safer by using the pieces of jungle plant you hack down to repair the fence of our village.").also { stage = 205 }
            }
            205 -> options("Do I get anything for doing this?", "Where can I get a machete from?", "Where is this jungle?", "Where is the fence that needs to be repaired?", "Ok, thanks.").also { stage = 206 }
            206 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "Do I get anything for doing this?").also { stage = 210 }
                2 -> playerl(FacialExpression.FRIENDLY, "Where can I get a machete from?").also { stage = 220 }
                3 -> playerl(FacialExpression.FRIENDLY, "Where is this jungle?").also { stage = 230 }
                4 -> playerl(FacialExpression.FRIENDLY, "Where is the fence that needs to be repaired?").also { stage = 240 }
                5 -> playerl(FacialExpression.FRIENDLY, "Ok, thanks.").also { stage = END_DIALOGUE }
            }

            210 -> npcl(FacialExpression.FRIENDLY, "Yes Bwana, you gain experience in woodcutting. In addition many members of the community will reward you with trading sticks, our local currency, for your efforts.").also { stage = 211 }
            211 -> player("Trading sticks?").also { stage = 212 }
            212 -> npcl(FacialExpression.FRIENDLY, "Correct Bwana, trading sticks! It's our form of local currency! Everything you do to help out in the village will give you some favour, but there's a maximum limit!").also { stage = 213 }
            213 -> npcl(FacialExpression.FRIENDLY, "When you've reached one hundred percent, you cannot gain any more favour, you need to cash in your favour for trading sticks before you can increase your favour again.").also { stage = 214 }
            214 -> options("Who do I get trading sticks from?", "Where can I get a machete from?", "Where is this jungle?", "Where is the fence that needs to be repaired?", "Ok, thanks.").also { stage = 215 }
            215 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "Who do I get trading sticks from?").also { stage = 250 }
                2 -> playerl(FacialExpression.FRIENDLY, "Where can I get a machete from?").also { stage = 220 }
                3 -> playerl(FacialExpression.FRIENDLY, "Where is this jungle?").also { stage = 230 }
                4 -> playerl(FacialExpression.FRIENDLY, "Where is the fence that needs to be repaired?").also { stage = 240 }
                5 -> playerl(FacialExpression.FRIENDLY, "Ok, thanks.").also { stage = END_DIALOGUE }
            }
            220 -> npcl(FacialExpression.FRIENDLY, "You can purchase a standard machete from Jiminua's store. Gabooty has a selection of local items which you may like to purchase, including better machetes.").also { stage = 221 }
            221 -> npcl(FacialExpression.FRIENDLY, "Also, you may like to chat to Safta Doc, he may have a deal you'll find interesting.").also { stage = 205 }
            230 -> npcl(FacialExpression.FRIENDLY, "You'll find much of it to the southern end of Tai Bwo Wannai, outside of the village. Some is tougher than others so you may have difficulties cutting it back.").also { stage = 231 }
            231 -> npcl(FacialExpression.FRIENDLY, " Beware though Bwana, those bushes can conceal a great many dangers or trophies, it could be the death... or the making of you!").also { stage = 232 }
            232 -> player("What do you mean by that?").also { stage = 233 }
            233 -> npcl(FacialExpression.FRIENDLY, "It means that you may find something you wish you hadn't but then discover something you never thought you could! If you find me too vague, go and have a chat with Sharimika about it!").also { stage = 205 }
            240 -> npcl(FacialExpression.FRIENDLY, "This is to the South Bwana, you should see that there are sections which are rotten, but some other passers-by sometimes help out repairing it.").also { stage = 241 }
            241 -> npcl(FacialExpression.FRIENDLY, "You'll need some thatching spars and a machete in order to repair the fence though Bwana.").also { stage = 205 }
            250 -> npcl(FacialExpression.FRIENDLY, "Well Bwana, there's a good ten villagers I know of who would show their appreciation for your effort. Who do you want to ask about?").also { stage = 251 }
            251 -> showTopics(
                Topic(FacialExpression.FRIENDLY, "Sharimika", 253, false),
                Topic(FacialExpression.FRIENDLY, "Mamma Bufetta", 254, false),
                Topic(FacialExpression.FRIENDLY, "Layleen", 255, false),
                Topic(FacialExpression.FRIENDLY, "Karaday", 256, false),
                Topic("Villager Menu 2", 257, true)
            )
            253 -> npcl(FacialExpression.FRIENDLY, "Sharimika is married to Mamma Bufetta, Bwana. They live in the houses just in front of the tribal statue.").also { stage = 251 }
            254 -> npcl(FacialExpression.FRIENDLY, "Mamma Bufetta lives with her family in the building that faces the stone statue, she lives there with her husband Sharimika and her daughter Layleen.").also { stage = 251 }
            255 -> npcl(FacialExpression.FRIENDLY, "Layleen is a young girl, the daughter of Mamma Bufetta and Sharimika, she lives with her parents.").also { stage = 251 }
            256 -> npcl(FacialExpression.FRIENDLY, " Karaday is a young man and his chosen profession is fishing, he follows after his father Safta Doc in that respect. You can find him usually around the center of the village, that is when he's not off fishing.").also { stage = 251 }
            257 -> showTopics(
                Topic(FacialExpression.FRIENDLY, "Safta Doc", 259, false),
                Topic(FacialExpression.FRIENDLY, "Gabooty", 261, false),
                Topic(FacialExpression.FRIENDLY, "Fanellaman", 262, false),
                Topic(FacialExpression.FRIENDLY, "Jagbakoba", 263, false),
                Topic("Villager Menu 3", 264, true)
            )
            259 -> npcl(FacialExpression.FRIENDLY, "Safta Doc used to be a fisherman, but he's just about retired now. He has an interesting profession which you might like to talk to him about. You'll find him usually near the building with the anvil in it.").also { stage = 260 }
            260 -> npcl(FacialExpression.FRIENDLY, "It's just slightly north of the village statue.").also { stage = 257 }
            261 -> npcl(FacialExpression.FRIENDLY, "Gabooty is a bit of a mystery, no one's sure where he came from! He's quite helpful though, you can find him in the middle of the village enclosure, to the south of Sharimika's house.").also { stage = 257 }
            262 -> npcl(FacialExpression.FRIENDLY, "Fanellaman was a fisherman, he's retired now, but he'll try and regale you with tales of his salty sea dog days. He's often walking up and down the western beach.").also { stage = 257 }
            263 -> npcl(FacialExpression.FRIENDLY, "Jagbakoba is a wild hunter. He can be a bit aggressive but he'll reward anyone who helps to keep the village in good order. You'll find him to the south of the enclosure, near the fires.").also { stage = 257 }
            264 -> showTopics(
                Topic(FacialExpression.FRIENDLY, "Rionasta", 266, false),
                Topic(FacialExpression.AMAZED, "Murcaily", 267, false),
                Topic("Back to original questions", 1, true),
                Topic(FacialExpression.FRIENDLY, "I want to ask another question", 215, false),
                Topic("Villager Menu 1", 251, true)
            )
            266 -> npcl(FacialExpression.FRIENDLY, "Ah Rionasta! He's an enterprising man! Had a very good idea and is now much in demand, you'll find him between the blacksmith's and Timfraku's hut.").also { stage = 264 }
            267 -> npcl(FacialExpression.HAPPY, "Murcaily! That's me! Well, what do you want to know?! I look after the teak and mahogany trees and make sure that we get payment of anyone who uses the facility. And I'm usually standing just about here!").also { stage = 264 }
            300 -> {
                val tradingStickReward = min(player.getAttribute("/save:tbwcleanup", 0), 100)
                if (tradingStickReward == 0) {
                    npcl(FacialExpression.FRIENDLY, "I don't think you deserve any trading sticks yet. Why don't you do some work around the village first.").also { stage = END_DIALOGUE }
                } else {
                    spendTBWCleanupPoints(player, tradingStickReward)
                    addItemOrDrop(player, Items.TRADING_STICKS_6306, tradingStickReward)
                    npcl(FacialExpression.FRIENDLY, "Hey, I've seen you working around the village. You've been doing a lot of good work around here for us. Let me give you something for your trouble.").also { stage = END_DIALOGUE }
                }
            }
        }
        return true
    }

    override fun newInstance(player: Player?): Dialogue {
        return MurcailyDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.JAGBAKOBA_2528)
    }
}
