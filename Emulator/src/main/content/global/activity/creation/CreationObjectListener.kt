package content.global.activity.creation

import core.api.*
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.map.Location
import org.rs.consts.Animations
import org.rs.consts.Items
import org.rs.consts.NPCs
import org.rs.consts.Scenery

class CreationObjectListener : InteractionListener {

    override fun defineListeners() {

        /*
         * Handles inspection with altar.
         */

        on(Scenery.SYMBOL_OF_LIFE_21893, IntType.SCENERY, "inspect") { player, node ->
            faceLocation(player, node.location)
            sendDialogueLines(player, "You see some text scrolled above the altar on a symbol...")
            addDialogueAction(player) { _, button ->
                if (button in 0..3) {
                    handleInspection(player, node.location)
                }
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

        /*
         * Handles adding the ingredients.
         */

        creatureIngredients.forEach { ingredient ->
            onUseWith(IntType.SCENERY, intArrayOf(ingredient.item1, ingredient.item2), Scenery.SYMBOL_OF_LIFE_21893) { player, used, with ->
                if (with.location == ingredient.location) {
                    handleIngredientUsage(player, used.asItem(), ingredient)
                }
                return@onUseWith true
            }
        }

        /*
         * Handles the creature creation (spawn).
         */

        on(Scenery.SYMBOL_OF_LIFE_21893, IntType.SCENERY, "activate") { player, node ->
            faceLocation(player, node.location)
            creatureIngredients.forEach { ingredient ->
                val item1Placed = getAttribute(player, "${ingredient.creatureName}:${ingredient.item1}", false)
                val item2Placed = getAttribute(player, "${ingredient.creatureName}:${ingredient.item2}", false)

                if (item1Placed && item2Placed) {
                    animateScenery(node.asScenery(), 5844)
                    sendNPCDialogue(
                        player,
                        NPCs.HOMUNCULUS_5581,
                        "You haveee materials need. Here goes!",
                        FacialExpression.OLD_NORMAL
                    )
                    addDialogueAction(player) { _, button ->
                        if (button >= 5) {
                            removeAttributesForActivation(player, ingredient)
                            spawnCreature(player, ingredient.location)
                        } else {
                            sendMessage(player, "Nothing interesting happens.")
                        }
                    }
                } else {
                    sendDialogueLines(player, "You need all the materials necessary to activate this altar!")
                }
            }
            return@on true
        }
    }
}

/**
 * Handles the usage of ingredients on the altar.
 *
 * @param player The player using the ingredient.
 * @param used The item being used.
 * @param ingredient The ingredient being used.
 */
private fun handleIngredientUsage(player: Player, used: Item, ingredient: CreatureIngredient) {
    val item1Placed = getAttribute(player, "${ingredient.creatureName}:${ingredient.item1}", false)
    val item2Placed = getAttribute(player, "${ingredient.creatureName}:${ingredient.item2}", false)

    if (item1Placed && item2Placed) {
        sendDialogueLines(player, "You have all the materials necessary to activate this altar!")
    } else {
        placeItemOnAltar(player, used, ingredient)
    }
}

/**
 * Adding the ingredient item on the altar.
 *
 * @param player The player placing the item.
 * @param used The item being added.
 * @param ingredient The ingredient item id.
 */
private fun placeItemOnAltar(player: Player, used: Item, ingredient: CreatureIngredient) {
    val requiredItem = when (used.id) {
        ingredient.item1 -> ingredient.item1
        ingredient.item2 -> ingredient.item2
        else -> null
    }

    requiredItem?.let {
        if (!getAttribute(player, "${ingredient.creatureName}:$it", false)) {
            lock(player, 1)
            removeItem(player, it)
            animate(player, Animations.HUMAN_BURYING_BONES_827)
            sendDialogue(player, "You place the ${getItemName(it).lowercase()} on the altar.")
            setAttribute(player, "/save:${ingredient.creatureName}:$it", true)
            closeDialogue(player)
        } else {
            sendDialogueLines(player, "You already placed the ${getItemName(it).lowercase()} on the altar!")
        }
    }
}

/**
 * Spawns a creature for the altar.
 *
 * @param player The player who triggered the spawn.
 * @param location The location where the creature will spawn.
 */
private fun spawnCreature(player: Player, location: Location) {
    val npcLocationMap = mapOf(
        Location(3058, 4410, 0) to NPCs.NEWTROOST_5597,
        Location(3066, 4380, 0) to NPCs.JUBSTER_5596,
        Location(3034, 4361, 0) to NPCs.SWORDCHICK_5595,
        Location(3012, 4380, 0) to NPCs.FROGEEL_5593,
        Location(3018, 4410, 0) to NPCs.UNICOW_5603,
        Location(3043, 4361, 0) to NPCs.SPIDINE_5594
    )

    val npcLocation = npcLocationMap[location]
    npcLocation?.let {
        val npc = core.game.node.entity.npc.NPC.create(
            it,
            if (location == Location(3018, 4410, 0))
                Location.getRandomLocation(Location(3022, 4403, 0), 2, true)
            else
                Location.create(location.x - 1, location.y - 3, 0)
        )
        npc.init()
        npc.attack(player)
        npc.isRespawn = false
    }
}

/**
 * Handles the inspection option of the altar.
 *
 * @param player The player who used the option.
 * @param location The location of the altar.
 */
private fun handleInspection(player: Player, location: Location) {
    val dialogueData = mapOf(
        Location(3058, 4410, 0) to Pair(
            Items.FEATHER_314 to Items.EYE_OF_NEWT_221,
            "Feather of chicken and eye of newt..."
        ),
        Location(3034, 4361, 0) to Pair(
            Items.RAW_SWORDFISH_371 to Items.RAW_CHICKEN_2138,
            "Swordfish raw and chicken uncooked..."
        ),
        Location(3043, 4361, 0) to Pair(
            Items.RED_SPIDERS_EGGS_223 to Items.RAW_SARDINE_327,
            "Red spider's eggs and a sardine raw..."
        ),
        Location(3018, 4410, 0) to Pair(
            Items.UNICORN_HORN_237 to Items.COWHIDE_1739,
            "Horn of unicorn and hide of cow..."
        ),
        Location(3066, 4380, 0) to Pair(
            Items.RAW_JUBBLY_7566 to Items.RAW_LOBSTER_377,
            "Raw meat of jubbly bird and a lobster raw..."
        ),
        Location(3012, 4380, 0) to Pair(
            Items.RAW_CAVE_EEL_5001 to Items.GIANT_FROG_LEGS_4517,
            "Cave eel raw and giant frog legs..."
        )
    )

    dialogueData[location]?.let { (items, dialogue) ->
        sendDoubleItemDialogue(player, items.first, items.second, dialogue)
    }
}

/**
 * Removes attributes related to the activation of the altar.
 *
 * @param player The player whose attributes will be removed.
 * @param ingredient The item ids associated with the activation.
 */
private fun removeAttributesForActivation(player: Player, ingredient: CreatureIngredient) {
    removeAttribute(player, "${ingredient.creatureName}:${ingredient.item1}")
    removeAttribute(player, "${ingredient.creatureName}:${ingredient.item2}")
}

/**
 * Data class representing a creature ingredient.
 *
 * @property location The location of the scenery for the ingredient.
 * @property item1 The first ingredient item required.
 * @property item2 The second ingredient item required.
 * @property creatureName The attribute assigned to scenery.
 */
data class CreatureIngredient(
    val location: Location,
    val item1: Int,
    val item2: Int,
    val creatureName: String
)
