package content.region.misthalin.dialogue.varrock

import content.global.skill.combat.summoning.pet.Pet
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import core.utilities.RandomFunction

@Initializable
class GertrudeDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        val quest = player.getQuestRepository().getQuest("Gertrude's Cat")
        when (quest.getStage(player)) {
            0 -> player(FacialExpression.HALF_GUILTY, "Hello, are you ok?")
            10 -> {
                player(FacialExpression.HALF_GUILTY, "Hello Gertrude.")
                stage = 210
            }

            20 -> {
                player(FacialExpression.HALF_GUILTY, "Hello Gertrude.")
                stage = 230
            }

            30 -> {
                player(FacialExpression.HALF_GUILTY, "Hello again.")
                stage = 236
            }

            50 -> {
                npc(FacialExpression.HALF_WORRIED, "Please bring me my cat back!")
                stage = 235
            }

            40 -> {
                player(FacialExpression.HALF_GUILTY, "Hello Gertrude.")
                stage = 300
            }

            60 -> {
                player(FacialExpression.HALF_GUILTY,
                    "Hello Gertrude. Fluffs ran off with her kitten."
                )
                stage = 320
            }

            100 -> {
                player(FacialExpression.NEUTRAL, "Hello again.")
                stage = 500
            }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        val quest = player.getQuestRepository().getQuest("Gertrude's Cat")
        when (stage) {
            0 -> {
                npc(FacialExpression.HALF_GUILTY, "Do I look ok? Those kids drive me crazy.")
                stage = 1
            }

            1 -> {
                npc(FacialExpression.HALF_GUILTY, "I'm sorry. It's just that I've lost her.")
                stage = 2
            }

            2 -> {
                player(FacialExpression.HALF_GUILTY, "Lost who?")
                stage = 3
            }

            3 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "Fluffs, poor Fluffs. She never hurt anyone."
                )
                stage = 4
            }

            4 -> {
                player(FacialExpression.HALF_GUILTY, "Who's Fluffs?")
                stage = 5
            }

            5 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "My beloved feline friends Fluffs. She's been purring by",
                    "my side for almost a decade. Please, could you go",
                    "search for her while I look over the kids?"
                )
                stage = 6
            }

            6 -> {
                interpreter.sendOptions(
                    "Select an Option",
                    "Well, I suppose I could.",
                    "Sorry, I'm too busy to play pet rescue."
                )
                stage = 7
            }

            7 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY, "Well, I suppose I could.")
                    stage = 100
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY,
                        "Sorry, I'm to busy too play pet rescue."
                    )
                    stage = 200
                }
            }

            100 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "Really? Thank you so much! I really have no idea",
                    "where she could be!"
                )
                stage = 101
            }

            101 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "I think my sons, Shilop and Wilough, saw the cat last.",
                    "They'll be out in the market place."
                )
                stage = 102
            }

            102 -> {
                player(FacialExpression.HALF_GUILTY, "Alright then, I'll see what I can do.")
                stage = 103
            }

            103 -> {
                quest.start(player)
                player.getQuestRepository().syncronizeTab(player)
                end()
            }

            200 -> end()
            210 -> {
                npc(FacialExpression.HALF_GUILTY, "Have you seen my poor Fluffs?")
                stage = 211
            }

            211 -> {
                player(FacialExpression.HALF_GUILTY, "I'm afraid not.")
                stage = 212
            }

            212 -> {
                npc(FacialExpression.HALF_ASKING, "What about Shilop?")
                stage = 213
            }

            213 -> {
                player(FacialExpression.HALF_GUILTY, "No sign of him either.")
                stage = 214
            }

            214 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "Hmmm...strange, he should be at the market."
                )
                stage = 215
            }

            215 -> end()
            230 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "Hello again, did you manage to find Shilop? I can't keep",
                    "an eye on him for the life of me."
                )
                stage = 231
            }

            231 -> {
                player(FacialExpression.HALF_GUILTY, "He does seem quite a handful.")
                stage = 232
            }

            232 -> {
                npc(FacialExpression.HALF_GUILTY, "You have no idea! Did he help at all?")
                stage = 233
            }

            233 -> {
                player(FacialExpression.OLD_NORMAL,
                    "I think so, I'm just going to look now."
                )
                stage = 234
            }

            234 -> {
                npc(FacialExpression.HAPPY, "Thanks again adventurer!")
                stage = 235
            }

            235 -> end()
            236 -> {
                npc(FacialExpression.HALF_ASKING, "Hello. How's it going? Any luck?")
                stage = 237
            }

            237 -> {
                player(FacialExpression.NEUTRAL, "Yes, I've found Fluffs!")
                stage = 238
            }

            238 -> {
                npc(FacialExpression.HALF_ASKING,
                    "Well well, you are clever!",
                    "Did you bring her back?"
                )
                stage = 239
            }

            239 -> {
                player(FacialExpression.HALF_WORRIED,
                    "Well, that's the thing, she refuses to leave."
                )
                stage = 240
            }

            240 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "Oh dear, oh dear! Maybe she's just hungry.",
                    "She loves doogle sardines but I'm all out."
                )
                stage = 241
            }

            241 -> {
                player(FacialExpression.HALF_ASKING, "Doogle sardines?")
                stage = 242
            }

            242 -> {
                npc(FacialExpression.HALF_ASKING,
                    "Yes, raw sardines seasoned with doogle leaves.",
                    "Unfortunately I've used all my doogle leaves,",
                    "but you may find some in the woods out back."
                )
                stage = 304
            }

            300 -> {
                npc(FacialExpression.HALF_GUILTY, "Hi! Did you find fluffs?")
                stage = 301
            }

            301 -> {
                player(FacialExpression.HALF_GUILTY, "Yes! But she won't follow me.")
                stage = 302
            }

            302 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "You should try tempting her with a",
                    "seasoned sardine! Those are her favourite snacks."
                )
                stage = 303
            }

            303 -> {
                player(FacialExpression.HALF_GUILTY, "Thanks for the advice!")
                stage = 304
            }

            304 -> end()
            320 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "You're back! Thank you! Thank you! Fluffs just came",
                    "back! I think she was just upset as she couldn't find her",
                    "kitten."
                )
                stage = 321
            }

            321 -> {
                interpreter.sendDialogue("Gertrude gives you a hug.")
                npc.face(player)
                stage = 322
            }

            322 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "If you hadn't found her kitten it would have died out",
                    "there!"
                )
                stage = 323
            }

            323 -> {
                player(FacialExpression.HALF_GUILTY, "That's ok, I like to do my bit.")
                stage = 324
            }

            324 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "I don't know how to thank you. I have no real material",
                    "possessions. I do have kittens! I can only really look",
                    "after one."
                )
                stage = 325
            }

            325 -> {
                player(FacialExpression.HALF_GUILTY, "Well, if it needs a home.")
                stage = 326
            }

            326 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "I would sell it to my cousin in West Ardougne. I hear",
                    "there's a rat epidemic there. But it's too far."
                )
                stage = 327
            }

            327 -> if (player.inventory.freeSlots() == 0) {
                player(FacialExpression.HALF_GUILTY,
                    "I don't seem to have enough inventory space."
                )
                stage = 1000

            } else {
                npc(FacialExpression.HALF_GUILTY,
                    "Here you go, look after her and thank you again!"
                )
                stage = 328
            }

            328 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "Oh by the way, the kitten can live in your backpack,",
                    "but to make it grow you must take it out and feed and",
                    "stroke it often."
                )
                stage = 329
            }

            329 -> {
                interpreter.sendDialogue("Gertrude gives you a kitten.")
                stage = 330
            }

            330 -> {
                player.packetDispatch.sendMessage("...and some food!")
                end()
                quest.finish(player)
                player.getQuestRepository().syncronizeTab(player)
            }

            331 -> {}
            1000 -> {
                npc(FacialExpression.HALF_GUILTY, "Good good, See you again.")
                stage = 1001
            }

            1001 -> end()
            500 -> {
                npc(FacialExpression.HALF_ASKING, "Hello my dear. How's things?")
                stage = 501
            }

            501 -> {
                options( "I'm fine, thanks.", "Do you have any more kittens?")
                stage = 502
            }

            502 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY, "I'm fine, thanks.")
                    stage = 1000
                }

                2 -> {
                    player(FacialExpression.HALF_ASKING, "Do you have any more kittens?")
                    stage = 503
                }
            }

            503 -> {
                var has = false
                val kittens = intArrayOf(1555, 1556, 1557, 1558, 1559, 1560, 7583)
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
                    npc("Aren't you still raising that other kitten? Only once it's",
                        "fully grown and it no longer needs your attention will",
                        "I let you have another kitten."
                    )
                    505
                } else {
                    npc("Indeed I have. They are 100 coins each, do you want", "one?")
                    900
                }
            }

            900 -> {
                options( "Yes please.", "No thanks.")
                stage = 901
            }

            901 -> when (buttonId) {
                1 -> if (!player.inventory.contains(995, 100)) {
                        player.packetDispatch.sendMessage("You need a 100 coins to buy a kitten.")
                        end()
                    } else {
                    if (player.inventory.freeSlots() == 0) {
                        player.packetDispatch.sendMessage("You don't have enough inventory space.")
                        end()
                    } else {
                        npc("Yes please.")
                        stage = 903

                    }
                }

                2 -> end()
            }

            903 -> {
                npc("Ok then, here you go.")
                stage = 904
            }

            904 -> {
                npc("Thanks.")
                stage = 905
            }

            905 -> {
                if (!player.inventory.containsItem(COINS)) {
                    end()
                    return true
                }
                if (player.inventory.remove(COINS)) {
                    interpreter.sendDialogue("Gertrude gives you another kitten.")
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
        /**
         * Gets kitten.
         *
         * @return the kitten
         */
        get() = Item(RandomFunction.random(1555, 1560))

    override fun getIds(): IntArray {
        return intArrayOf(780)
    }

    companion object {
        private val COINS = Item(995, 100)
    }
}
