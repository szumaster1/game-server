package content.global.skill.cooking

import core.api.*
import org.rs.consts.Components
import org.rs.consts.Items
import core.game.dialogue.DialogueFile
import core.game.node.scenery.Scenery
import core.tools.START_DIALOGUE

/**
 * Represents the cooking dialogue.
 */
class CookingDialogue(vararg val args: Any) : DialogueFile() {

    var initial = 0
    var product = 0
    var scenery: Scenery? = null
    var sinew = false
    var itemid = 0

    override fun handle(componentID: Int, buttonID: Int) {
        when (stage) {
            START_DIALOGUE -> {
                when (args.size) {
                    2 -> {
                        initial = args[0] as Int
                        if (Cookable.intentionalBurn(initial)) {
                            /*
                             * checks intentional burning.
                             */
                            product =
                                Cookable.getIntentionalBurn(initial).id
                        } else {
                            product =
                                Cookable.forId(initial)!!.cooked
                        }
                        scenery = args[1] as Scenery
                    }

                    5 -> {
                        initial = args[0] as Int
                        product = args[1] as Int
                        sinew = args[2] as Boolean
                        scenery = args[3] as Scenery
                        itemid = args[4] as Int
                        if (sinew) {
                            options("Dry the meat into sinew", "Cook the meat")
                            stage = if (amountInInventory(player!!, initial) > 1) 100 else 101
                            return
                        }
                    }
                }
                display()
            }

            1 -> {
                end()
                when (val amount = getAmount(buttonID)) {
                    -1 -> sendInputDialogue(player!!, true, "Enter the amount:") { value ->
                        if (value is String) {
                            CookingRewrite.cook(player!!, scenery, initial, product, value.toInt())
                        } else {
                            CookingRewrite.cook(player!!, scenery, initial, product, value as Int)
                        }
                    }

                    else -> {
                        end()
                        CookingRewrite.cook(player!!, scenery, initial, product, amount)
                    }
                }
            }

            100 -> {
                when (buttonID) {
                    1 -> {
                        product = Items.SINEW_9436
                        display()
                    }

                    2 -> {
                        product = Cookable.forId(initial)!!.cooked
                        display()
                    }
                }
            }

            101 -> {
                when (buttonID) {
                    1 -> {
                        end()
                        CookingRewrite.cook(player!!, scenery, initial, Items.SINEW_9436, 1)
                    }

                    2 -> {
                        end()
                        CookingRewrite.cook(
                            player!!,
                            scenery,
                            initial,
                            Cookable.forId(initial)!!.cooked,
                            1
                        )
                    }
                }
            }
        }
    }

    private fun getAmount(buttonId: Int): Int {
        when (buttonId) {
            5 -> return 1
            4 -> return 5
            3 -> return -1
            2 -> return amountInInventory(player!!, initial)
        }
        return -1
    }

    fun display() {
        sendItemZoomOnInterface(player!!, Components.SKILL_COOKMANY_307, 2, initial, 160)
        sendString(player!!, "<br><br><br><br>${getItemName(initial)}", Components.SKILL_COOKMANY_307, 6)

        /*
         * Swords sprite.
         */
        setInterfaceSprite(player!!, Components.SKILL_COOKMANY_307, 0, 12, 15)
        setInterfaceSprite(player!!, Components.SKILL_COOKMANY_307, 1, 431, 15)
        /*
         * "How many would you like to cook?".
         */
        setInterfaceSprite(player!!, Components.SKILL_COOKMANY_307, 7, 0, 12)
        /*
         * Right click context menu boxes.
         */
        setInterfaceSprite(player!!, Components.SKILL_COOKMANY_307, 3, 58, 27)
        setInterfaceSprite(player!!, Components.SKILL_COOKMANY_307, 4, 58, 27)
        setInterfaceSprite(player!!, Components.SKILL_COOKMANY_307, 5, 58, 27)
        setInterfaceSprite(player!!, Components.SKILL_COOKMANY_307, 6, 58, 27)
        openChatbox(player!!, Components.SKILL_COOKMANY_307)
        stage = 1
    }

}
