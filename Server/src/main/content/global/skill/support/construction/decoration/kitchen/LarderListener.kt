package content.global.skill.support.construction.decoration.kitchen

import core.api.*
import core.api.consts.Animations
import core.api.consts.Items
import core.api.consts.Scenery
import core.game.dialogue.DialogueFile
import core.game.dialogue.IfTopic
import core.game.dialogue.Topic
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

class LarderListener : InteractionListener {

    private val larderIds = intArrayOf(Scenery.WOODEN_LARDER_13565, Scenery.OAK_LARDER_13566, Scenery.TEAK_LARDER_13567)
    val searchAnim = Animations.HUMAN_SEARCH_LARDER_3659


    override fun defineListeners() {
        on(larderIds, IntType.SCENERY, "Search") { player, l ->
            if (freeSlots(player) < 1) {
                sendMessage(player, "You need at least one free inventory space to take from the larder.")
                return@on true
            }
            lock(player, 1)
            openDialogue(player, object : DialogueFile() {
                override fun handle(componentID: Int, buttonID: Int) {
                    when (stage) {
                        0 -> showTopics(
                            Topic("Tea", 1, true),
                            Topic("Milk", 2, true),
                            IfTopic("Egg", 3, l.id != Scenery.WOODEN_LARDER_13565, true),
                            IfTopic("Flour", 4, l.id != Scenery.WOODEN_LARDER_13565, true),
                            IfTopic("More...", 5, l.id == Scenery.TEAK_LARDER_13567, true)
                        )

                        1 -> {
                            end()
                            animate(player, searchAnim)
                            addItem(player, Items.TEA_LEAVES_7738, 1, Container.INVENTORY)
                            sendMessageWithDelay(player, "You take some tea leaves.", 1)
                        }

                        2 -> {
                            end()
                            animate(player, searchAnim)
                            addItem(player, Items.BUCKET_OF_MILK_1927, 1, Container.INVENTORY)
                            sendMessageWithDelay(player, "You take a bucket of milk.", 1)
                        }

                        3 -> {
                            end()
                            animate(player, searchAnim)
                            addItem(player, Items.EGG_1944, 1, Container.INVENTORY)
                            sendMessageWithDelay(player, "You take an egg.", 1)
                        }

                        4 -> {
                            end()
                            animate(player, searchAnim)
                            addItem(player, Items.POT_OF_FLOUR_1933, 1, Container.INVENTORY)
                            sendMessageWithDelay(player, "You take some flour.", 1)
                        }

                        5 -> showTopics(
                            Topic("Potatoes", 6, true),
                            Topic("Garlic", 7, true),
                            Topic("Onions", 8, true),
                            Topic("Cheese", 9, true)
                        )

                        6 -> {
                            end()
                            animate(player, searchAnim)
                            addItem(player, Items.POTATO_1942, 1, Container.INVENTORY)
                            sendMessageWithDelay(player, "You take potato.", 1)
                        }

                        7 -> {
                            end()
                            animate(player, searchAnim)
                            addItem(player, Items.GARLIC_1550, 1, Container.INVENTORY)
                            sendMessageWithDelay(player, "You take garlic.", 1)
                        }

                        8 -> {
                            end()
                            animate(player, searchAnim)
                            addItem(player, Items.ONION_1957, 1, Container.INVENTORY)
                            sendMessageWithDelay(player, "You take an onion.", 1)
                        }

                        9 -> {
                            end()
                            animate(player, searchAnim)
                            addItem(player, Items.CHEESE_1985, 1, Container.INVENTORY)
                            sendMessageWithDelay(player, "You take a cheese.", 1)
                        }
                    }
                }
            })
            return@on true
        }
    }
}