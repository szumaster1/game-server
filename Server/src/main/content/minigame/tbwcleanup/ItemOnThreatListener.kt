package content.minigame.tbwcleanup

import content.data.consumables.Consumables
import cfg.consts.Items
import cfg.consts.NPCs
import core.game.node.item.Item
import core.game.node.entity.npc.NPC
import core.api.impact
import core.api.lock
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.tools.RandomFunction.random

/**
 * Represents the Item on threat plugin.
 */
class ItemOnThreatListener : InteractionListener {

    private val ANTIPOISONS = arrayOf(Items.ANTIPOISON1_179, Items.ANTIPOISON2_177, Items.ANTIPOISON3_175, Items.ANTIPOISON4_2446)
    private val SUPER_ANTIPOISONS = arrayOf(Items.SUPER_ANTIPOISON1_185, Items.SUPER_ANTIPOISON2_183, Items.SUPER_ANTIPOISON3_181, Items.SUPER_ANTIPOISON4_2448)
    private val ANTIPOISON_PLUS = arrayOf(Items.ANTIPOISON_PLUS1_5949, Items.ANTIPOISON_PLUS2_5947, Items.ANTIPOISON_PLUS3_5945, Items.ANTIPOISON_PLUS4_5943)
    private val ANTIPOISON_PLUS_PLUS = arrayOf(Items.ANTIPOISON_PLUS_PLUS1_5958, Items.ANTIPOISON_PLUS_PLUS2_5956, Items.ANTIPOISON_PLUS_PLUS3_5954, Items.ANTIPOISON_PLUS_PLUS4_5952)
    private val RELICYMs_BALM = arrayOf(Items.RELICYMS_BALM4_4842, Items.RELICYMS_BALM3_4844, Items.RELICYMS_BALM2_4846, Items.RELICYMS_BALM1_4848)
    private val SANFEW_SYRUM = arrayOf(Items.SANFEW_SERUM4_10925, Items.SANFEW_SERUM3_10927, Items.SANFEW_SERUM2_10929, Items.SANFEW_SERUM1_10931)

    private val BROODOO_VICTIM_GREEN = NPCs.BROODOO_VICTIM_2499
    private val BROODOO_VICTIM_YELLOW = NPCs.BROODOO_VICTIM_2501
    private val BROODOO_VICTIM_WHITE = NPCs.BROODOO_VICTIM_2503

    private val LARGE_MOSQUITO = NPCs.LARGE_MOSQUITO_2493
    private val MOSQUITO_SWARM_3 = NPCs.MOSQUITO_SWARM_2494
    private val MOSQUITO_SWARM_5 = NPCs.MOSQUITO_SWARM_2495

    private val MOSQUITOS = intArrayOf(LARGE_MOSQUITO, MOSQUITO_SWARM_3, MOSQUITO_SWARM_5)

    override fun defineListeners() {
        /*
         * Activates when player uses an item on a Green Broodoo victim.
         */

        onUseAnyWith(IntType.NPC, BROODOO_VICTIM_GREEN) { player, used, with ->
            val maxDamage = if (ANTIPOISONS.contains(used.id)) {
                20
            } else if (SUPER_ANTIPOISONS.contains(used.id)) {
                30
            } else if (SANFEW_SYRUM.contains(used.id)) {
                30
            } else if (ANTIPOISON_PLUS.contains(used.id)) {
                40
            } else if (ANTIPOISON_PLUS_PLUS.contains(used.id)) {
                50
            } else {
                player.sendMessage("Nothing interesting happens.")
                return@onUseAnyWith false
            }
            val damage = random(0, maxDamage)
            player.sendMessage("You offer the poison cure to the broodoo victim... The entity snatches the potion and drinks some.")
            attackBroodoo(used.asItem(), player, with.asNpc(), damage)
            player.skills.addExperience(Skills.HERBLORE, damage.toDouble() * 2)
            return@onUseAnyWith true
        }

        /*
         * Activates when player uses an item on a Yellow Broodoo victim.
         */

        onUseAnyWith(IntType.NPC, BROODOO_VICTIM_YELLOW) { player, used, with ->
            if (RELICYMs_BALM.contains(used.id) || SANFEW_SYRUM.contains(used.id)) {
                val damage = random(0, 30)
                player.sendMessage("You offer the poison cure to the broodoo victim... The entity snatches the potion and drinks some.")
                attackBroodoo(used.asItem(), player, with.asNpc(), damage)
                player.skills.addExperience(Skills.HERBLORE, damage.toDouble() * 2)
            } else {
                player.sendMessage("Nothing interesting happens.")
                return@onUseAnyWith false
            }
            return@onUseAnyWith true
        }

        /*
         * Activates when player uses an item on a White Broodoo victim.
         */

        onUseAnyWith(IntType.NPC, BROODOO_VICTIM_WHITE) { player, used, with ->
            return@onUseAnyWith attackBroodooWithFood(used.asItem(), player, with.asNpc())
        }

        /*
         * Activates when player uses insect repellent on a large mosquito or mosquito swarm.
         */

        onUseWith(IntType.NPC, Items.INSECT_REPELLENT_28, *MOSQUITOS) { _, _, with ->
            //Halve all combat stats of the mosquito
            for (skillNumber in arrayOf(0, 1, 2, 4, 6)) {
                with.asNpc().skills.setStaticLevel(
                    skillNumber,
                    (with.asNpc().skills.getStaticLevel(skillNumber) + 1) / 2
                )
            }

            //freeze the mosquito for 10 ticks (6seconds).
            lock(with.asNpc(), 10)
            return@onUseWith true
        }
    }

    /*
     * This part is not 100% authentic as the damage per food is not the same as how much it heals but I can't find a
     * source with values for all foods (and I doubt it exists). For now I roll between 0 and the healing value of the
     * food. Only for fruits and vegetables I roll between 10 & 35 as this is the only group with known damage values.

     * Mod Ash: "Different categories of food use different damage ranges. Same for poison cures. Looks like vegetables
     * and fruit have the highest damage range for food (10-35). I think the dev was vegetarian :P"
     */

    private fun attackBroodooWithFood(used: Item, player: Player, with: NPC): Boolean {
        // Incomplete list of fruits and vegetables.
        val FRUITS_AND_VEGETABLES = arrayOf(
            Items.CABBAGE_1965,
            Items.ONION_1957,
            Items.POTATO_1942,
            Items.TOMATO_1982,
            Items.SWEETCORN_5986,
            Items.BANANA_1963,
            Items.SLICED_BANANA_3162,
            Items.ORANGE_2108,
            Items.ORANGE_CHUNKS_2110,
            Items.ORANGE_SLICES_2112,
            Items.PINEAPPLE_2114,
            Items.PINEAPPLE_CHUNKS_2116,
            Items.PINEAPPLE_RING_2118,
            Items.STRAWBERRY_5504,
            Items.WATERMELON_5982,
            Items.WATERMELON_SLICE_5984,
            Items.COOKING_APPLE_1955
        )

        val consumable = Consumables.getConsumableById(used.id)!!.consumable
        val consumableName = consumable::class.simpleName.toString()

        if (consumableName == null) {
//            player.sendMessage("This item is not a consumable.")
            player.sendMessage("Nothing interesting happens.")
            return false
        }
        if (consumableName !in arrayOf("Food", "Cake", "HalfableFood")) {
//            player.sendMessage("This item is not food. this item is of type ${CONSUMABLE::class.simpleName}")
            player.sendMessage("Nothing interesting happens.")
            return false
        }
        if (consumable.effect::class.simpleName != "HealingEffect") {
//            player.sendMessage("This food does not heal. This item has a ${CONSUMABLE.effect::class.simpleName}")
            player.sendMessage("Nothing interesting happens.")
            return false
        }

        val damage = if (used.id in FRUITS_AND_VEGETABLES) {
            random(10, 35)
        } else {
            random(0, consumable.effect.getHealthEffectValue(player))
        }

        player.sendMessage("The entity swiftly takes the food and devours it.")
        attackBroodoo(used, player, with, damage)
        player.skills.addExperience(Skills.COOKING, damage.toDouble() * 2)
        return true
    }

    private fun attackBroodoo(used: Item, player: Player, with: NPC, damage: Int) {
        val consumable = Consumables.getConsumableById(used.id)!!.consumable
        consumable.handleItemChangesOnConsumption(used.asItem(), player)
        // As the potion last dose handler doesn't trigger with this function,
        // this is a bit of a dirty fix to return the empty vial.
        if (used.id in arrayOf(
                Items.SANFEW_SERUM1_10931,
                Items.RELICYMS_BALM1_4848,
                Items.ANTIPOISON_PLUS_PLUS1_5958,
                Items.ANTIPOISON_PLUS1_5949,
                Items.SUPER_ANTIPOISON1_185,
                Items.ANTIPOISON1_179
            )
        ) {
            player.inventory.add(Item(Items.VIAL_229))
        }
        impact(with.asNpc(), damage)
        with.asNpc().attack(player)
    }
}