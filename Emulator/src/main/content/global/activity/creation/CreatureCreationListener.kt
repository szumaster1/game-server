package content.global.activity.creation

import content.data.consumables.effects.NettleTeaEffect
import core.api.*
import core.game.consumable.Drink
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item
import core.game.world.update.flag.context.Animation
import org.rs.consts.*

class CreatureCreationListener : InteractionListener {

    override fun defineListeners() {

        /*
         * Handles entering to tol basement.
         */

        on(Scenery.TRAPDOOR_21921, IntType.SCENERY, "open") { player, _ ->
            if (hasRequirement(player, QuestName.TOWER_OF_LIFE)) {
                setVarbit(player, 3372, 1)
            }
            return@on true
        }

        /*
         * Handles interaction with trapdoor.
         */

        on(Scenery.TRAPDOOR_21922, IntType.SCENERY, "close") { player, _ ->
            setVarbit(player, 3372, 0)
            return@on true
        }

        /*
         * Handles post-quest homunculus dialogue.
         */

        on(NPCs.HOMUNCULUS_5581, IntType.NPC, "talk-to") { player, _ ->
            openDialogue(player, HomunculusDialogue())
            return@on true
        }

        /*
         * Handles adding resources to satchel (cake, banana, square sandwich).
         */

        onUseWith(IntType.ITEM, SATCHEL_RESOURCES, *SATCHEL_IDS) { player, used, _ ->
            val satchelKey = "$SATCHEL:${used.id}"
            if (player.getAttribute(satchelKey, -1) == 1) {
                sendMessage(player, "You already have a ${getItemName(used.id).lowercase()} in there.")
                return@onUseWith false
            }
            if (removeItem(player, Item(used.id, 1), Container.INVENTORY)) {
                setAttribute(player, satchelKey, 1)
            }
            return@onUseWith true
        }

        /*
         * Handles satchel options (inspect and empty).
         */

        on(SATCHEL_IDS, IntType.ITEM, "inspect", "empty") { player, node ->
            val option = getUsedOption(player)
            val itemsInSatchel = mapOf(
                Items.CAKE_1891 to "one cake",
                Items.BANANA_1963 to "one banana",
                Items.SQUARE_SANDWICH_6965 to "one sandwich"
            )
            val contents = itemsInSatchel.filter { player.getAttribute("$SATCHEL:${it.key}", -1) == 1 }

            when (option) {
                "inspect" -> sendItemDialogue(
                    player, node.id, "The ${getItemName(node.id)}!<br>Containing: ${contents.values.joinToString()}"
                        .ifEmpty { "empty" }
                )

                "empty" -> if (contents.isNotEmpty()) {
                    contents.keys.forEach { item ->
                        addItemOrDrop(player, item)
                        setAttribute(player, "$SATCHEL:$item", 0)
                    }
                } else {
                    sendMessage(player, "It's empty!")
                }
            }
            return@on true
        }

        /*
         * Handles tea flask drink and look-in options.
         */

        on(Items.TEA_FLASK_10859, IntType.ITEM, "drink", "look-in") { player, node ->
            val option = getUsedOption(player)
            val cupsAmount = getAttribute(player, TEA_FLASK, cup)

            when (option) {
                "drink" -> {
                    if (cupsAmount > 0) {
                        lock(player, 1)
                        drink.consume(Item(-1), player)
                        player.incrementAttribute(TEA_FLASK, -1)
                        sendChat(player, "Ahh, tea is so refreshing.")
                    } else {
                        sendMessage(player, "The tea flask is empty.")
                    }
                }

                "look-in" -> sendItemDialogue(player, node.id, "The tea flask holds: $cupsAmount ${if (cupsAmount > 1) "cups" else "cup"} of tea.")
            }
            return@on true
        }

        /*
         * Handles pouring tea from flask into cup.
         */

        onUseWith(IntType.ITEM, Items.TEA_FLASK_10859, Items.EMPTY_CUP_1980) { player, _, with ->
            val cupsAmount = getAttribute(player, TEA_FLASK, cup)
            if (cupsAmount > 0) {
                player.incrementAttribute(TEA_FLASK, -1)
                replaceSlot(player, with.asItem().slot, Item(Items.CUP_OF_TEA_712))
                sendMessage(player, "You fill the cup with tea.")
            } else {
                sendMessage(player, "The tea flask is empty.")
            }
            return@onUseWith true
        }

        /*
         * Handles adding a cup of tea to the tea flask.
         */

        onUseWith(IntType.ITEM, CUP_OF_TEA, Items.TEA_FLASK_10859) { player, used, _ ->
            val cupsAmount = getAttribute(player, TEA_FLASK, cup)
            if (cupsAmount >= 5) {
                sendMessage(player, "The tea flask is already full.")
                return@onUseWith false
            }
            if (removeItem(player, Item(used.id, 1), Container.INVENTORY)) {
                replaceSlot(player, used.asItem().slot, Item(Items.EMPTY_CUP_1980))
                sendMessage(player, "You add the tea to the flask.")
                player.incrementAttribute(TEA_FLASK, 1)
            }
            return@onUseWith true
        }
    }

    companion object {
        const val cup = 0
        const val TEA_FLASK = "/save:tea_flask"
        const val SATCHEL = "/save:satchel_container"
        val CUP_OF_TEA = intArrayOf(Items.CUP_OF_TEA_712, Items.CUP_OF_TEA_1978)
        val SATCHEL_RESOURCES = intArrayOf(Items.CAKE_1891, Items.BANANA_1963, Items.SQUARE_SANDWICH_6965)
        val SATCHEL_IDS = intArrayOf(
            Items.PLAIN_SATCHEL_10877, Items.GREEN_SATCHEL_10878, Items.RED_SATCHEL_10879,
            Items.BLACK_SATCHEL_10880, Items.GOLD_SATCHEL_10881, Items.RUNE_SATCHEL_10882
        )
        val drink = Drink(
            intArrayOf(-1),
            NettleTeaEffect(),
            Animation.create(Animations.TEA_FLASK_TOWER_OF_LIFE_5827),
            "You take a drink from the flask..."
        )
    }
}