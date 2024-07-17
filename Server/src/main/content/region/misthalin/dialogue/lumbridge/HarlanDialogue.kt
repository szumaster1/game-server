package content.region.misthalin.dialogue.lumbridge

import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.global.Skillcape.purchase
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.plugin.Initializable

@Initializable
class HarlanDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        options("Can you tell me about different weapon types I can use?", "Please tell me about Skillcapes.", "Bye.")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY,
                        "Can you tell me about different weapon types I can",
                        "use?"
                    )
                    stage = 10
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY, "Please tell me about Skillcapes.")
                    stage = 20
                }

                3 -> {
                    player(FacialExpression.HALF_GUILTY, "Bye.")
                    stage = 30
                }
            }

            10 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "Well let me see now...There are stabbing type weapons",
                    "such as daggers, then you have swords which are",
                    "slashing, maces that have great crushing abilities, battle",
                    "axes which are powerful and spears which can be good"
                )
                stage = 11
            }

            11 -> {
                npc(FacialExpression.HALF_GUILTY, "for Defence and many forms of Attack.")
                stage = 12
            }

            12 -> {
                npc(FacialExpression.HALF_GUILTY,
                    "It depends a lot on how you want to fight. Experiment",
                    "and find out what is best for you. Never be scared to",
                    "try out a new weapon; you never know, you might like",
                    "it! Why, I tried all of them for a while and settled on"
                )
                stage = 13
            }

            13 -> {
                npc(FacialExpression.HALF_GUILTY, "this rather good sword!")
                stage = 14
            }

            14 -> {
                options( "I'd like a training sword and shield.", "Bye")
                stage = 15
            }

            15 -> when (buttonId) {
                1 -> {
                    player(FacialExpression.HALF_GUILTY,
                        "I'd like a training sword and shield."
                    )
                    stage = 16
                }

                2 -> {
                    player(FacialExpression.HALF_GUILTY, "Bye.")
                    stage = 30
                }
            }

            16 -> {
                if (hasBoth()) {
                    npc("You already have them! If they're not in your",
                        "inventory, perhaps you should check your bank."
                    )
                    stage = 99
                    return true
                }
                if (hasItem(SHIELD)) {
                    npc("You already have a shield but I can give you a sword.")
                    stage = 16
                    return true
                }
                if (hasItem(SWORD)) {
                    npc("You already have a sword but I can give you a shield.")
                    stage = 17
                    return true
                }
                if (player.inventory.add(SWORD)) {
                    interpreter.sendItemMessage(SWORD, "Harlan gives you a training sword.")
                } else {
                    end()
                }
                stage = 17
            }

            17 -> {
                if (player.inventory.add(SHIELD)) {
                    interpreter.sendItemMessage(SHIELD, "Harlan gives you a training shield.")
                } else {
                    end()
                }
                stage = 18
            }

            18 -> end()
            20 -> {
                npc("Of course. Skillcapes are a symbol of achievement. Only",
                    "people who have mastered a skill and reached level 99",
                    "can get their hands on them and gain the benefits they",
                    "carry. Is there something else I can help you with,"
                )
                stage = 21
            }

            21 -> {
                npc("perhaps?")
                stage = 22
            }

            22 -> {
                if (player.getSkills().getStaticLevel(Skills.DEFENCE) >= 99) {
                    interpreter.sendOptions(
                        "Select an Option",
                        "Can you tell me about different weapon types I can use?",
                        "Can I purchase a defence cape?",
                        "Bye."
                    )
                    stage = 23
                    return true
                }
                options("Can you tell me about different weapon types I can use?",
                    "Please tell me about skillcapes.",
                    "Bye."
                )
                stage = 0
            }

            23 -> {
                npc("You will have to pay a fee of 99,000 gp.")
                stage = 24
            }

            24 -> {
                options( "Okay.", "No, thanks.")
                stage = 25
            }

            25 -> when (buttonId) {
                1 -> {
                    player("Okay.")
                    stage = 27
                }

                2 -> {
                    player("No, thanks.")
                    stage = 26
                }
            }

            27 -> {
                if (purchase(player, Skills.DEFENCE)) {
                    npc("There you go! Enjoy.")
                }
                stage = 26
            }

            26 -> end()
            30 -> end()
            99 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(705)
    }

    /**
     * Has both boolean.
     *
     * @return the boolean
     */
    fun hasBoth(): Boolean {
        val containers = arrayOf(player.inventory, player.bank, player.equipment)
        var count = 0
        for (c in containers) {
            if (c.containsItem(SWORD)) {
                count++
            }
            if (c.containsItem(SHIELD)) {
                count++
            }
        }
        return count >= 2
    }

    /**
     * Has item boolean.
     *
     * @param item the item
     * @return the boolean
     */
    fun hasItem(item: Item?): Boolean {
        val containers = arrayOf(player.inventory, player.bank, player.equipment)
        for (c in containers) {
            if (c.containsItem(item)) {
                return true
            }
        }
        return false
    }

    companion object {
        private val SWORD = Item(9703)
        private val SHIELD = Item(9704)
    }
}