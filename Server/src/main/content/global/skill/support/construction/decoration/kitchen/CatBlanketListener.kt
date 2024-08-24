package content.global.skill.support.construction.decoration.kitchen

import cfg.consts.Items
import cfg.consts.Scenery
import content.global.skill.combat.summoning.pet.Pet
import content.global.skill.combat.summoning.pet.Pets
import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.system.task.Pulse
import core.game.world.update.flag.context.Animation

/**
 * Handle the cat blanket interactions.
 */
class CatBlanketListener : InteractionListener {

    companion object {
        val ITEMS = intArrayOf(
            Items.PET_CAT_1561,
            Items.PET_CAT_1562,
            Items.PET_CAT_1563,
            Items.PET_CAT_1564,
            Items.PET_CAT_1565,
            Items.PET_CAT_1566
        )
        val BLANKETS = intArrayOf(Scenery.PET_BLANKET_13574, Scenery.PET_BASKET_13575, Scenery.PET_BASKET_13576)
        val PUTTING_CAT_DOWN = Animation(827)
        val FALLING_ASLEEP = Animation(2160)
        val SLEEPING = Animation(2159)
    }

    override fun defineListeners() {
        /*
         * Handle drop the pet item on blanket.
         */
        onUseWith(IntType.SCENERY, ITEMS, *BLANKETS) { player, used, scenery ->
            val pet = Pets.forId(used.id) ?: return@onUseWith true
            if (player.houseManager.isBuildingMode) {
                sendMessage(player, "You cannot do this in building mode.")
                return@onUseWith false
            }
            lock(player, 1)
            animate(player, PUTTING_CAT_DOWN)
            submitWorldPulse(object : Pulse() {
                var counter = 0
                override fun pulse(): Boolean {
                    val petId = pet.getNpcId(used.id) ?: return false
                    val cat = Pet.create(petId, scenery.location)
                    if (counter == 0) {
                        cat.isWalks = false
                        cat.isNeverWalks = true
                        if (removeItem(player, used.asItem())) {
                            cat.sendChat("Meeeew!")
                            cat.init()
                            cat.lock()
                            cat.animate(FALLING_ASLEEP)
                            counter++
                        } else {
                            return false
                        }
                    }
                    if (counter == 1) {
                        cat.animate(SLEEPING)
                        return false
                    }
                    return true
                }
            })
            return@onUseWith true
        }
    }
}