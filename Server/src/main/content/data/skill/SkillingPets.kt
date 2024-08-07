package content.data.skill

import core.api.sendNews
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

/**
 * Represents the skilling pets obtained randomly.
 * @author Empathy
 *
 * @param skill The skill id.
 * @param pet The pet item.
 */
enum class SkillingPets(
    val pet: Item,
    val petName: String,
    val skill: Int
) {
    BABY_RED_CHINCHOMPA(
        pet = Item(14823),
        petName = "Baby Chinchompa",
        skill = Skills.HUNTER
    ),
    BABY_GREY_CHINCHOMPA(
        pet = Item(14824),
        petName = "Baby Chinchompa",
        skill = Skills.HUNTER
    ),
    BEAVER(
        pet = Item(14821),
        petName = "Beaver",
        skill = Skills.WOODCUTTING
    ),
    GOLEM(
        pet = Item(14822),
        petName = "Rock Golem",
        skill = Skills.MINING
    ),
    HERON(
        pet = Item(14827),
        petName = "Heron",
        skill = Skills.FISHING
    );

    companion object {
        /**
         * Checks the pet drop.
         * @param player The player.
         * @param pet    The pet drop to check.
         */
        fun checkPetDrop(player: Player, pet: SkillingPets?) {
            if (pet == null) {
                return
            }
            val defaultChance = 15000
            val newChance = (defaultChance / player.getSkills().getStaticLevel(pet.skill) * 55)
            val outOf = (if (newChance > defaultChance) defaultChance else newChance)
            val getChance = outOf
            if (getChance != 1) {
                return
            }
            if (player.hasItem(pet.pet)) {
                return
            }
            if (player.familiarManager.hasFamiliar() && player.inventory.isFull) {
                return
            }
            if (player.familiarManager.hasFamiliar()) {
                if (player.familiarManager.familiar.name.equals(pet.petName, ignoreCase = true)) {
                    return
                }
                player.inventory.add(pet.pet)
                player.sendNotificationMessage("You feel something weird sneaking into your backpack.")
            } else {
                player.familiarManager.summon(pet.pet, true)
                player.sendNotificationMessage("You have a funny feeling like you're being followed.")
            }
            sendNews(player.username + " has found a " + pet.pet.name + "!")
        }
    }
}
