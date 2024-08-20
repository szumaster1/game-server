package content.region.desert.quest.deserttreasure.handlers

import content.region.desert.quest.deserttreasure.DesertTreasure
import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.consts.Scenery
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.npc.NPC
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation

/**
 * Represents the Diamond of blood listeners.
 */
class DiamondOfBloodListeners : InteractionListener {
    override fun defineListeners() {

        // Silver pot conversions
        onUseWith(ITEM, Items.SILVER_POT_4660, Items.SPICE_2007) { player, used, with ->
            sendMessage(player, "You add some spices to the pot.")
            if(removeItem(player, used) && removeItem(player, with)) {
                addItemOrDrop(player, Items.SILVER_POT_4664)
            }
            return@onUseWith true
        }
        onUseWith(ITEM, Items.SILVER_POT_4660, Items.GARLIC_POWDER_4668) { player, used, with ->
            sendMessage(player, "You add some crushed garlic to the pot.")
            if(removeItem(player, used) && removeItem(player, with)) {
                addItemOrDrop(player, Items.SILVER_POT_4662)
            }
            return@onUseWith true
        }
        onUseWith(ITEM, Items.SILVER_POT_4662, Items.SPICE_2007) { player, used, with ->
            sendMessage(player, "You add some spices to the pot.")
            if(removeItem(player, used) && removeItem(player, with)) {
                addItemOrDrop(player, Items.SILVER_POT_4666)
            }
            return@onUseWith true
        }
        onUseWith(ITEM, Items.SILVER_POT_4664, Items.GARLIC_POWDER_4668) { player, used, with ->
            sendMessage(player, "You add some crushed garlic to the pot.")
            if(removeItem(player, used) && removeItem(player, with)) {
                addItemOrDrop(player, Items.SILVER_POT_4666)
            }
            return@onUseWith true
        }
        // Blessed pot conversions
        onUseWith(ITEM, Items.BLESSED_POT_4661, Items.SPICE_2007) { player, used, with ->
            sendMessage(player, "You add some spices to the pot.")
            if(removeItem(player, used) && removeItem(player, with)) {
                addItemOrDrop(player, Items.BLESSED_POT_4665)
            }
            return@onUseWith true
        }
        onUseWith(ITEM, Items.BLESSED_POT_4661, Items.GARLIC_POWDER_4668) { player, used, with ->
            sendMessage(player, "You add some crushed garlic to the pot.")
            if(removeItem(player, used) && removeItem(player, with)) {
                addItemOrDrop(player, Items.BLESSED_POT_4663)
            }
            return@onUseWith true
        }
        onUseWith(ITEM, Items.BLESSED_POT_4663, Items.SPICE_2007) { player, used, with ->
            sendMessage(player, "You add some spices to the pot.")
            if(removeItem(player, used) && removeItem(player, with)) {
                addItemOrDrop(player, Items.BLESSED_POT_4667)
            }
            return@onUseWith true
        }
        onUseWith(ITEM, Items.BLESSED_POT_4665, Items.GARLIC_POWDER_4668) { player, used, with ->
            sendMessage(player, "You add some crushed garlic to the pot.")
            if(removeItem(player, used) && removeItem(player, with)) {
                addItemOrDrop(player, Items.BLESSED_POT_4667)
            }
            return@onUseWith true
        }

        // Dessous jumps out.
        onUseWith(SCENERY, Items.BLESSED_POT_4667, Scenery.VAMPIRE_TOMB_6437) { player, used, with ->
            val prevNpc = getAttribute<NPC?>(player, DesertTreasure.attributeDessousInstance, null)
            if (prevNpc != null) {
                prevNpc.clear()
            }
            sendMessage(player, "You pour the blood from the pot onto the tomb.")
            removeItem(player, used)
            val scenery = with.asScenery()
            // Swap to a splittable vampire tomb scenery.
            replaceScenery(scenery, Scenery.VAMPIRE_TOMB_6438, Animation(1915).duration)
            // Vampire Tomb breaks open.
            animateScenery(player, scenery, 1915)
            // 8 Bat projectiles
            spawnProjectile(Location(3570, 3402), Location(3570, 3404), 350, 0, 0, 0, 60, 0)
            spawnProjectile(Location(3570, 3402), Location(3570, 3400), 350, 0, 0, 0, 60, 0)
            spawnProjectile(Location(3570, 3402), Location(3568, 3402), 350, 0, 0, 0, 60, 0)
            spawnProjectile(Location(3570, 3402), Location(3572, 3402), 350, 0, 0, 0, 60, 0)
            spawnProjectile(Location(3570, 3402), Location(3568, 3404), 350, 0, 0, 0, 60, 0)
            spawnProjectile(Location(3570, 3402), Location(3572, 3404), 350, 0, 0, 0, 60, 0)
            spawnProjectile(Location(3570, 3402), Location(3568, 3400), 350, 0, 0, 0, 60, 0)
            spawnProjectile(Location(3570, 3402), Location(3572, 3400), 350, 0, 0, 0, 60, 0)
            val npc = NPC(NPCs.DESSOUS_1914)
            queueScript(player, 1, QueueStrength.SOFT) { stage: Int ->
                when (stage) {
                    0 -> {
                        // Projectile gfx for Dessous to jump out.
                        spawnProjectile(Location(3570, 3402), Location(3570, 3405), 351, 0, 0, 0, 40, 0)
                        return@queueScript delayScript(player, 1)
                    }
                    1 -> {
                        npc.isRespawn = false
                        npc.isWalks = false
                        npc.location = Location(3570, 3405, 0)
                        npc.direction = Direction.NORTH
                        setAttribute(player, DesertTreasure.attributeDessousInstance, npc)
                        setAttribute(npc, "target", player)

                        npc.init()
                        npc.attack(player)
                        return@queueScript stopExecuting(player)
                    }
                    else -> return@queueScript stopExecuting(player)
                }
            }

            //sendGraphics(350, Location(3570, 3402))
            //sendGraphics(351, Location(3570, 3402))
            return@onUseWith true
        }
    }
}