package content.global.activity.creation

import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.map.Location
import org.rs.consts.Items
import org.rs.consts.NPCs
import org.rs.consts.Scenery

class CreationObjectListener : InteractionListener {

    override fun defineListeners() {

        on(Scenery.SYMBOL_OF_LIFE_21893, IntType.SCENERY, "inspect") { player, _ ->
            sendDialogue(player, "You see some text scrolled above the altar on a symbol...")
            addDialogueAction(player) { node, _ ->
                handleInspection(player, node.location)
                return@addDialogueAction
            }
            return@on true
        }

        defineIngredientListeners(
            listOf(
                CreatureIngredient(Location(3058, 4410, 0), Items.FEATHER_314, Items.EYE_OF_NEWT_221, "newtroost"),
                CreatureIngredient(Location(3034, 4361, 0), Items.RAW_SWORDFISH_371, Items.RAW_CHICKEN_2138, "swordchick"),
                CreatureIngredient(Location(3043, 4361, 0), Items.RED_SPIDERS_EGGS_223, Items.RAW_SARDINE_327, "spidine"),
                CreatureIngredient(Location(3018, 4410, 0), Items.UNICORN_HORN_237, Items.COWHIDE_1739, "unicow"),
                CreatureIngredient(Location(3066, 4380, 0), Items.RAW_JUBBLY_7566, Items.RAW_LOBSTER_377, "jubster"),
                CreatureIngredient(Location(3012, 4380, 0), Items.RAW_CAVE_EEL_5001, Items.GIANT_FROG_LEGS_4517, "frogeel")
            )
        )
    }

    private fun defineIngredientListeners(creatureIngredients: List<CreatureIngredient>) {
        creatureIngredients.forEach { (location, item1, item2, creatureName) ->
            onUseWith(IntType.SCENERY, intArrayOf(item1, item2), Scenery.SYMBOL_OF_LIFE_21893) { player, used, with ->
                if (with.location == location) return@onUseWith true
                handleIngredientUsage(player, used.asItem(), item1, item2, creatureName)
                return@onUseWith true
            }
        }
    }

    private fun handleIngredientUsage(player: Player, used: Item, item1: Int, item2: Int, creatureName: String) {
        val item1Placed = getAttribute(player, "$creatureName:$item1", false)
        val item2Placed = getAttribute(player, "$creatureName:$item2", false)

        if (item1Placed && item2Placed) {
            sendDialogue(player, "You have all the materials necessary to activate this altar!")
        } else {
            placeItemOnAltar(player, used, item1, creatureName)
            placeItemOnAltar(player, used, item2, creatureName)
        }
    }

    private fun placeItemOnAltar(player: Player, used: Item, requiredItemId: Int, creatureName: String) {
        if (used.id == requiredItemId && !getAttribute(player, "$creatureName:$requiredItemId", false)) {
            removeItem(player, requiredItemId)
            sendMessage(player, "You place the ${getItemName(used.id).lowercase()} on the altar.")
            setAttribute(player, "/save:$creatureName:$requiredItemId", true)
        } else if (used.id == requiredItemId) {
            sendMessage(player, "You already placed the ${getItemName(used.id).lowercase()} on the altar!")
        }
    }

    private fun handleInspection(player: Player, location: Location) {
        val dialogueData = mapOf(
            Location(3058, 4410, 0) to Pair(Pair(Items.FEATHER_314, Items.EYE_OF_NEWT_221), "Feather of chicken and eye of newt..."),
            Location(3034, 4361, 0) to Pair(Pair(Items.RAW_SWORDFISH_371, Items.RAW_CHICKEN_2138), "Swordfish raw and chicken uncooked..."),
            Location(3043, 4361, 0) to Pair(Pair(Items.RED_SPIDERS_EGGS_223, Items.RAW_SARDINE_327), "Red spider's eggs and a sardine raw..."),
            Location(3018, 4410, 0) to Pair(Pair(Items.UNICORN_HORN_237, Items.COWHIDE_1739), "Horn of unicorn and hide of cow..."),
            Location(3066, 4380, 0) to Pair(Pair(Items.RAW_JUBBLY_7566, Items.RAW_LOBSTER_377), "Raw meat of jubbly bird and a lobster raw..."),
            Location(3012, 4380, 0) to Pair(Pair(Items.RAW_CAVE_EEL_5001, Items.GIANT_FROG_LEGS_4517), "Cave eel raw and giant frog legs...")
        )

        dialogueData[location]?.let { (items, dialogue) ->
            sendDoubleItemDialogue(player, items.first, items.second, dialogue)
        }
    }

    data class CreatureIngredient(
        val location: Location,
        val item1: Int,
        val item2: Int,
        val creatureName: String
    )

    companion object {
        fun handleCreatureCreation(player: Player) {
            sendNPCDialogue(player, NPCs.HOMUNCULUS_5581, "You haveee materials need. Here goes!")
        }
    }
}