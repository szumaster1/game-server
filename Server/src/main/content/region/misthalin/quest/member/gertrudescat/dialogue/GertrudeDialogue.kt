package content.region.misthalin.quest.member.gertrudescat.dialogue

import content.global.skill.combat.summoning.pet.Pet
import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.RandomFunction

/**
 * Gertrude dialogue.
 */
@Initializable
class GertrudeDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        val quest = player.getQuestRepository().getQuest("Gertrude's Cat")
        when (quest.getStage(player)) {
            0 -> player(FacialExpression.HALF_GUILTY, "Hello, are you ok?")
            10 -> player(FacialExpression.HALF_GUILTY, "Hello Gertrude.").also { stage = 210 }
            20 -> player(FacialExpression.HALF_GUILTY, "Hello Gertrude.").also { stage = 230 }
            30 -> player(FacialExpression.HALF_GUILTY, "Hello again.").also { stage = 236 }
            50 -> npc(FacialExpression.HALF_WORRIED, "Please bring me my cat back!").also { stage = 235 }
            40 -> player(FacialExpression.HALF_GUILTY, "Hello Gertrude.").also { stage = 300 }
            60 -> playerl(FacialExpression.HALF_GUILTY, "Hello Gertrude. Fluffs ran off with her kitten.").also { stage = 320 }
            100 -> player(FacialExpression.NEUTRAL, "Hello again.").also { stage = 500 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        val quest = player.getQuestRepository().getQuest("Gertrude's Cat")
        when (stage) {
            0 -> npc(FacialExpression.HALF_GUILTY, "Do I look ok? Those kids drive me crazy.").also { stage++ }
            1 -> npc(FacialExpression.HALF_GUILTY, "I'm sorry. It's just that I've lost her.").also { stage++ }
            2 -> player(FacialExpression.HALF_GUILTY, "Lost who?").also { stage++ }
            3 -> npc(FacialExpression.HALF_GUILTY, "Fluffs, poor Fluffs. She never hurt anyone.").also { stage++ }
            4 -> player(FacialExpression.HALF_GUILTY, "Who's Fluffs?").also { stage++ }
            5 -> npc(FacialExpression.HALF_GUILTY, "My beloved feline friends Fluffs. She's been purring by", "my side for almost a decade. Please, could you go", "search for her while I look over the kids?").also { stage++ }

            6 -> options("Well, I suppose I could.", "Sorry, I'm too busy to play pet rescue.").also { stage++ }
            7 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_GUILTY, "Well, I suppose I could.").also { stage = 100 }
                2 -> player(FacialExpression.HALF_GUILTY, "Sorry, I'm to busy too play pet rescue.").also { stage = END_DIALOGUE }
            }

            100 -> npc(FacialExpression.HALF_GUILTY, "Really? Thank you so much! I really have no idea", "where she could be!").also { stage++ }
            101 -> npc(FacialExpression.HALF_GUILTY, "I think my sons, Shilop and Wilough, saw the cat last.", "They'll be out in the market place.").also { stage++ }
            102 -> player(FacialExpression.HALF_GUILTY, "Alright then, I'll see what I can do.").also { stage++ }

            103 -> {
                quest.start(player)
                updateQuestTab(player)
                end()
            }

            210 -> npc(FacialExpression.HALF_GUILTY, "Have you seen my poor Fluffs?").also { stage++ }
            211 -> player(FacialExpression.HALF_GUILTY, "I'm afraid not.").also { stage++ }
            212 -> npc(FacialExpression.HALF_ASKING, "What about Shilop?").also { stage++ }
            213 -> player(FacialExpression.HALF_GUILTY, "No sign of him either.").also { stage++ }
            214 -> npcl(FacialExpression.HALF_GUILTY, "Hmmm...strange, he should be at the market.").also { stage = END_DIALOGUE }

            230 -> npc(FacialExpression.HALF_GUILTY, "Hello again, did you manage to find Shilop? I can't keep", "an eye on him for the life of me.").also { stage++ }
            231 -> player(FacialExpression.HALF_GUILTY, "He does seem quite a handful.").also { stage++ }
            232 -> npc(FacialExpression.HALF_GUILTY, "You have no idea! Did he help at all?").also { stage++ }
            233 -> player(FacialExpression.OLD_NORMAL, "I think so, I'm just going to look now.").also { stage++ }
            234 -> npc(FacialExpression.HAPPY, "Thanks again adventurer!").also { stage = END_DIALOGUE }
            236 -> npc(FacialExpression.HALF_ASKING, "Hello. How's it going? Any luck?").also { stage++ }
            237 -> player(FacialExpression.NEUTRAL, "Yes, I've found Fluffs!").also { stage++ }
            238 -> npc(FacialExpression.HALF_ASKING, "Well well, you are clever!", "Did you bring her back?").also { stage++ }
            239 -> player(FacialExpression.HALF_WORRIED, "Well, that's the thing, she refuses to leave.").also { stage++ }
            240 -> npc(FacialExpression.HALF_GUILTY, "Oh dear, oh dear! Maybe she's just hungry.", "She loves doogle sardines but I'm all out.").also { stage++ }
            241 -> player(FacialExpression.HALF_ASKING, "Doogle sardines?").also { stage++ }
            242 -> npc(FacialExpression.HALF_ASKING, "Yes, raw sardines seasoned with doogle leaves.", "Unfortunately I've used all my doogle leaves,", "but you may find some in the woods out back.").also { stage = 304 }

            300 -> npc(FacialExpression.HALF_GUILTY, "Hi! Did you find fluffs?").also { stage++ }
            301 -> player(FacialExpression.HALF_GUILTY, "Yes! But she won't follow me.").also { stage++ }
            302 -> npc(FacialExpression.HALF_GUILTY, "You should try tempting her with a", "seasoned sardine! Those are her favourite snacks.").also { stage++ }
            303 -> player(FacialExpression.HALF_GUILTY, "Thanks for the advice!").also { stage = END_DIALOGUE }
            320 -> npc(FacialExpression.HALF_GUILTY, "You're back! Thank you! Thank you! Fluffs just came", "back! I think she was just upset as she couldn't find her", "kitten.").also { face(npc, player); stage++ }
            321 -> sendDialogue(player, "Gertrude gives you a hug.").also { stage++ }
            322 -> npc(FacialExpression.HALF_GUILTY, "If you hadn't found her kitten it would have died out", "there!").also { stage++ }
            323 -> player(FacialExpression.HALF_GUILTY, "That's ok, I like to do my bit.").also { stage++ }
            324 -> npc(FacialExpression.HALF_GUILTY, "I don't know how to thank you. I have no real material", "possessions. I do have kittens! I can only really look", "after one.").also { stage++ }
            325 -> player(FacialExpression.HALF_GUILTY, "Well, if it needs a home.").also { stage++ }
            326 -> npc(FacialExpression.HALF_GUILTY, "I would sell it to my cousin in West Ardougne. I hear", "there's a rat epidemic there. But it's too far.").also { stage++ }
            327 -> if (freeSlots(player) == 0) {
                player(FacialExpression.HALF_GUILTY, "I don't seem to have enough inventory space.").also { stage = 1000 }
            } else {
                npc(FacialExpression.HALF_GUILTY, "Here you go, look after her and thank you again!").also { stage++ }
            }

            328 -> npc(FacialExpression.HALF_GUILTY, "Oh by the way, the kitten can live in your backpack,", "but to make it grow you must take it out and feed and", "stroke it often.").also { stage++ }
            329 -> sendDialogue(player, "Gertrude gives you a kitten.").also { stage++ }
            330 -> {
                end()
                finishQuest(player, "Gertrude's Cat")
                sendMessageWithDelay(player, "...and some food!", 1)
                updateQuestTab(player)
            }

            1000 -> npc(FacialExpression.HALF_GUILTY, "Good good, See you again.").also { stage = END_DIALOGUE }
            500 -> npc(FacialExpression.HALF_ASKING, "Hello my dear. How's things?").also { stage++ }
            501 -> options("I'm fine, thanks.", "Do you have any more kittens?").also { stage++ }
            502 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_GUILTY, "I'm fine, thanks.").also { stage = 1000 }
                2 -> player(FacialExpression.HALF_ASKING, "Do you have any more kittens?").also { stage++ }
            }

            503 -> {
                var has = false
                val kittens = intArrayOf(
                    Items.PET_KITTEN_1555,
                    Items.PET_KITTEN_1556,
                    Items.PET_KITTEN_1557,
                    Items.PET_KITTEN_1558,
                    Items.PET_KITTEN_1559,
                    Items.PET_KITTEN_1560,
                    Items.HELL_KITTEN_7583
                )
                if (player.familiarManager.hasFamiliar()) {
                    val pet = player.familiarManager.familiar as Pet
                    for (i in kittens) {
                        if (pet.itemId == i) {
                            has = true
                            break
                        }
                    }
                }
                val searchSpace = arrayOf(player.inventory, player.bank)
                for (container in searchSpace) {
                    if (container.containsAtLeastOneItem(kittens)) {
                        has = true
                        break
                    }
                }
                stage = if (has) {
                    npc("Aren't you still raising that other kitten? Only once it's", "fully grown and it no longer needs your attention will", "I let you have another kitten.")
                    505
                } else {
                    npc("Indeed I have. They are 100 coins each, do you want", "one?")
                    900
                }
            }

            900 -> options("Yes please.", "No thanks.").also { stage++ }
            901 -> when (buttonId) {
                1 -> if (!inInventory(player, Items.COINS_995, 100)) {
                    sendMessage(player, "You need a 100 coins to buy a kitten.")
                    end()
                } else {
                    if (freeSlots(player) == 0) {
                        sendMessage(player, "You don't have enough inventory space.")
                        end()
                    } else {
                        player("Yes please.")
                        stage = 903
                    }
                }

                2 -> end()
            }

            903 -> npc("Ok then, here you go.").also { stage++ }
            904 -> player("Thanks.").also { stage++ }
            905 -> {
                if (!player.inventory.containsItem(COINS)) {
                    end()
                    return true
                }
                if (player.inventory.remove(COINS)) {
                    sendDialogue(player, "Gertrude gives you another kitten.")
                    stage = 906
                    val kitten = kitten
                    player.inventory.add(kitten)
                    player.familiarManager.summon(kitten, true)
                }
            }

            906 -> end()
            505 -> end()
        }
        return true
    }

    val kitten: Item
        get() = Item(RandomFunction.random(Items.PET_KITTEN_1555, Items.PET_KITTEN_1560))

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GERTRUDE_780)
    }

    companion object {
        private val COINS = Item(995, 100)
    }
}