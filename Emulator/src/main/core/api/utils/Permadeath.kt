package core.api.utils

import core.api.isUsingSecondaryBankAccount
import core.api.teleport
import core.api.toggleBankAccount
import core.game.node.entity.player.Player
import core.game.node.entity.player.VarpManager
import core.game.node.entity.player.info.login.PlayerSaver
import core.game.node.entity.player.link.IronmanMode
import core.game.node.entity.player.link.SavedData
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.entity.player.link.quest.QuestRepository
import core.game.node.entity.skill.Skills
import core.game.world.map.Location

/**
 * Permadeath
 *
 * @param target The player who is undergoing permadeath.
 */
fun Permadeath(target: Player) {
    // Teleport the player to a specific location upon permadeath
    teleport(target, Location.create(3094, 3107, 0))

    // Clear the player's equipment
    target.equipment.clear()

    // Clear the player's inventory
    target.inventory.clear()

    // Check if the player is using a secondary bank account and toggle if necessary
    if (isUsingSecondaryBankAccount(target)) {
        toggleBankAccount(target)
    }

    // Clear the player's primary bank
    target.bank.clear()

    // Clear the player's secondary bank
    target.bankSecondary.clear()

    // Reset the player's skills to a new Skills object
    target.skills = Skills(target)

    // Clear all attributes associated with the player
    target.clearAttributes()

    // Initialize saved data for the player
    target.savedData = SavedData(target)

    // Initialize the quest repository for the player
    target.questRepository = QuestRepository(target)

    // Initialize the variable manager for the player
    target.varpManager = VarpManager(target)

    // Clear the variable map for the player
    target.varpMap.clear()

    // Clear the saved variable data for the player
    target.saveVarp.clear()

    // Dismiss the player's familiar if they have one
    if (target.familiarManager.hasFamiliar()) {
        target.familiarManager.dismiss()
    }

    // Retrieve the keys of the player's pet details and remove them
    val petKeys = target.familiarManager.petDetails.keys.toList()
    for (key in petKeys) {
        target.familiarManager.removeDetails(key)
    }

    // Reset all diary tasks for the player
    for (type in DiaryType.values()) {
        val diary = target.achievementDiaryManager.getDiary(type)
        if (diary != null) {
            for (level in 0 until diary.levelStarted.size) {
                for (task in 0 until diary.taskCompleted[level].size) {
                    diary.resetTask(target, level, task)
                }
            }
        }
    }

    // Clear the player's unlocked music tracks
    target.musicPlayer.clearUnlocked()

    // Set the player's Ironman mode to NONE
    target.ironmanManager.mode = IronmanMode.NONE

    // Save the player's current state
    PlayerSaver(target).save()

    // Clear the player's data completely
    target.clear()
}
