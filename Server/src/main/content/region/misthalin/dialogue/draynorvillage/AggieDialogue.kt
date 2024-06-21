package content.region.misthalin.dialogue.draynorvillage

import core.api.sendItemDialogue
import core.api.stopWalk
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.quest.Quest
import core.game.node.item.Item
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable

@Initializable
class AggieDialogue(player: Player? = null) : Dialogue(player) {

    private var quest: Quest? = null

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (args.size >= 2) {
            options("What do you need to make a red dye?", "What do you need to make yellow dye?", "What do you need to make blue dye?")
            stage = 42
            return true
        }
        quest = player.getQuestRepository().getQuest("Prince Ali Rescue")
        npc("What can I help you with?")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                if (quest!!.getStage(player) == 20 || quest!!.getStage(player) == 30 || quest!!.getStage(player) == 40 || quest!!.getStage(player) == 50 || quest!!.getStage(player) == 60) {
                    options("Could you think of a way to make skin paste?",
                        "What could you make for me?",
                        "Cool, do you turn people into frogs?",
                        "You mad old witch, you can't help me.",
                        "Can you make dyes for me please?"
                    )
                    stage = 720
                    return true
                }
                options("What could you make for me?",
                    "Cool, do you turn people into frogs?",
                    "You mad old witch, you can't help me.",
                    "Can you make dyes for me please?"
                )
                stage = 1
            }

            720 -> when (buttonId) {
                1 -> {
                    player("Could you think of a way to make skin paste?")
                    stage = 721
                }

                2 -> {
                    player("What could you make for me?")
                    stage = 10
                }

                3 -> {
                    player("Cool, do you turn people into frogs?")
                    stage = 20
                }

                4 -> {
                    player(FacialExpression.FURIOUS, "You mad old witch, you can't help me.")
                    stage = 30
                }

                5 -> {
                    player(FacialExpression.FURIOUS, "Can you make dyes for me please?")
                    stage = 40
                }
            }

            721 -> stage = if (!hasIngredients(player)) {
                npc("Why it's one of my most popular potions. The women",
                    "here, they like to have smooth looking skin. And I must",
                    "admit, some of the men buy it as well."
                )
                722
            } else {
                npc("Yes I can, I see you already have the ingredients.",
                    "Would you like me to mix some for you now?"
                )
                726
            }

            722 -> {
                npc("I can make it for you, just get me what's needed.")
                stage = 723
            }

            723 -> {
                player("What do you need to make it?")
                stage = 724
            }

            724 -> {
                npc("Well dearie, you need a base for the paste. That's a",
                    "mix of ash, flour and water. Then you need redberries",
                    "to colour it as you want. Bring me those four items",
                    "and I will make you some."
                )
                stage = 725
            }

            725 -> end()
            726 -> {
                options("Yes please. Mix me some skin paste.",
                    "No thank you, I don't need any skin paste right now."
                )
                stage = 727
            }

            727 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY,
                        "Yes please. Mix me some skin paste."
                    )
                    stage = 731
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY,
                        "No thank you, I don't need any skin paste right now."
                    )
                    stage = 729
                }
            }

            729 -> {
                npc("Okay dearie, that's always your choice.")
                stage = 730
            }

            730 -> end()
            731 -> {
                npc("That should be simple. Hand the things to Aggie then.")
                stage = 732
            }

            732 ->                 // remove the solid ingredients and one of the water ingredients
                if (player.inventory.remove(*PASTE_SOLID_INGREDIENTS) && (player.inventory.remove(BUCKET_OF_WATER) || player.inventory.remove(JUG_OF_WATER))) {
                    interpreter.sendDialogue(
                        "You hand the ash, flour, water and redberries to Aggie.",
                        "Aggie tips the ingredients into a cauldron",
                        "and mutters some words."
                    )
                    stage = 733
                }

            733 -> {
                npc("Tourniquet, Fenderbaum, Tottenham, marshmaallow,", "MarbleArch.")
                stage = 734
            }

            734 -> if (player.inventory.add(PASTE)) {
                interpreter.sendDialogue("Aggie hands you the skin paste.")
                stage = 735
            }

            735 -> {
                npc("There you go dearie, your skin potion. That will make", "you look good at the Varrock dances.")
                stage = 736
            }

            736 -> end()
            1 -> when (buttonId) {
                1 -> {
                    player("What could you make for me?")
                    stage = 10
                }

                2 -> {
                    player("Cool, do you turn people into frogs?")
                    stage = 20
                }

                3 -> {
                    player(FacialExpression.FURIOUS, "You mad old witch, you can't help me.")
                    stage = 30
                }

                4 -> {
                    player(FacialExpression.FURIOUS, "Can you make dyes for me please?")
                    stage = 40
                }
            }

            40 -> {
                npc(FacialExpression.FURIOUS,
                    "What sort of dye would you like? Red, yellow or blue?"
                )
                stage = 41
            }

            41 -> {
                options("What do you need to make a red dye?",
                    "What do you need to make yellow dye?",
                    "What do you need to make blue dye?"
                )
                stage = 42
            }

            42 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.FURIOUS, "What do you need to make red dye?")
                    stage = 410
                }

                2 -> {
                    player(FacialExpression.FURIOUS, "What do you need to make yellow dye?")
                    stage = 420
                }

                3 -> {
                    player(FacialExpression.FURIOUS, "What do you need to make blue dye?")
                    stage = 430
                }
            }

            430 -> {
                npc("2 woad leaves and 5 coins to you.")
                stage = 431
            }

            431 -> {
                player(FacialExpression.FURIOUS, "Okay, make me some blue dye please.")
                stage = 432
            }

            432 -> {
                if (player.inventory.containsItem(COINS) && player.inventory.containsItem(WOAD_LEAVES)) {
                    player.inventory.remove(COINS)
                    player.inventory.remove(WOAD_LEAVES)
                    player.inventory.add(BLUE_DYE)
                    make(1767)
                    sendItemDialogue(
                        player,
                        BLUE_DYE,
                        "You hand the woad leaves and payment to Aggie. Aggie produces a blue bottle and hands it to you."
                    )
                } else {
                    interpreter.sendDialogue("You need 2 woad leaves and 5 coins.")
                }
                stage = 413
            }

            433 -> end()
            420 -> {
                npc("Yellow is a strange colour to get, comes from onion",
                    "skins. I need 2 onions and 5 coins to make yellow dye."
                )
                stage = 421
            }

            421 -> {
                player(FacialExpression.FURIOUS, "Okay, make me some yellow dye please.")
                stage = 422
            }

            422 -> {
                if (player.inventory.containsItem(COINS) && player.inventory.containsItem(ONIONS)) {
                    player.inventory.remove(COINS)
                    player.inventory.remove(ONIONS)
                    player.inventory.add(YELLOW_DYE)
                    make(1765)
                    sendItemDialogue(
                        player,
                        YELLOW_DYE,
                        "You hand the onions and payment to Aggie. Aggie produces a yellow bottle and hands it to you."
                    )
                } else {
                    interpreter.sendDialogue("You need 2 onions and 5 coins.")
                }
                stage = 423
            }

            423 -> end()
            410 -> {
                npc("3 lots of redberries and 5 coins to you.")
                stage = 411
            }

            411 -> {
                player(FacialExpression.FURIOUS, "Okay, make me some red dye please.")
                stage = 412
            }

            412 -> {
                if (player.inventory.containsItem(COINS) && player.inventory.containsItem(REDBERRIES)) {
                    player.inventory.remove(COINS)
                    player.inventory.remove(REDBERRIES)
                    player.inventory.add(RED_DYE)
                    make(1763)
                    sendItemDialogue(
                        player,
                        RED_DYE,
                        "You hand the berries and payment to Aggie. Aggie produces a red bottle and hands it to you."
                    )
                } else {
                    interpreter.sendDialogue("You need 3 redberries leaves and 5 coins.")
                }
                stage = 413
            }

            413 -> end()
            30 -> {
                npc("Oh, you like to call a witch names do you?")
                stage = 31
            }

            31 -> {
                val item = Item(995, 20)
                stage = if (player.inventory.remove(item)) {
                    sendItemDialogue(player, item, "Aggie waves her hands about, and you seem to be 20 coins poorer.")
                    32
                } else {
                    npc("You should be careful about insulting a witch. You",
                        "never know what shape you could wake up in."
                    )
                    34
                }
            }

            32 -> {
                npc("That's a fine for insulting a witch. You should learn",
                    "some respect."
                )
                stage = 33
            }

            34 -> end()
            33 -> end()
            20 -> {
                npc("Oh, not for years, but if you meet a talking chicken,",
                    "you have probably met the professor in the manor north",
                    "of here. A few years ago it was flying fish. That",
                    "machine is a menace."
                )
                stage = 11
            }

            10 -> {
                npc("I mostly make what I find pretty. I sometimes",
                    "make dye for the women's clothes to brighten the place",
                    "up. I can make red, yellow and blue dyes. If you'd like",
                    "some, just bring me the appropriate ingredients."
                )
                stage = 11
            }

            11 -> end()
        }
        return true
    }

    fun make(dye: Int) {
        stopWalk(npc!!)
        npc.animate(ANIMATION)
        npc.faceLocation(CAULDRON_LOCATION)
    }

    private fun hasIngredients(player: Player): Boolean {
        // check if the player has all the solid ingredients
        for (i in PASTE_SOLID_INGREDIENTS) {
            if (!player.inventory.containsItem(i)) {
                return false
            }
        }
        // check if the player has a bucket or jug of water
        return player.inventory.containsItem(BUCKET_OF_WATER) || player.inventory.containsItem(JUG_OF_WATER)
    }

    override fun getIds(): IntArray {
        return intArrayOf(922)
    }

    companion object {
        private val ANIMATION = Animation(4352)
        private val ASHES = Item(592)
        private val POT_OF_FLOUR = Item(1933)
        private val REDBERRIES_SINGLE = Item(1951) // specifying single because 3 redberries are needed for the red dye
        private val PASTE_SOLID_INGREDIENTS = arrayOf(ASHES, REDBERRIES_SINGLE, POT_OF_FLOUR)
        private val BUCKET_OF_WATER = Item(1929)
        private val JUG_OF_WATER = Item(1937) // Jug of Water is meant to be substitutable with the Bucket of Water
        private val CAULDRON_LOCATION = Location.create(3085, 3258, 0)
        private val COINS = Item(995, 5)
        private val WOAD_LEAVES = Item(1793, 2)
        private val ONIONS = Item(1957, 2)
        private val REDBERRIES = Item(1951, 3)
        private val PASTE = Item(2424)
        private val BLUE_DYE = Item(1767)
        private val YELLOW_DYE = Item(1765)
        private val RED_DYE = Item(1763)
    }
}