package content.global.skill.support.slayer

import content.global.skill.support.slayer.data.SlayerMaster
import content.global.skill.support.slayer.data.Tasks
import core.api.consts.Items
import core.api.setVarp
import core.game.node.entity.combat.BattleState
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.SpellBookManager.SpellBook
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.entity.skill.Skills
import core.tools.RandomFunction

/**
 * Slayer utils.
 */
object SlayerUtils {

    /**
     * Generates a random task for the player based on their combat level and task requirements.
     *
     * @param player The player for whom the task is being generated.
     * @param master The SlayerMaster who assigns the task.
     * @return A randomly selected task or null if no task can be assigned.
     */
    fun generate(player: Player, master: SlayerMaster): Tasks? {
        val tasks: MutableList<SlayerMaster.Task?> = ArrayList(10)
        val taskWeightSum = intArrayOf(0)
        master.tasks.stream().filter { task: SlayerMaster.Task ->
            canBeAssigned(player, task.task) && task.task.combatCheck <= player.properties.currentCombatLevel
        }.forEach { task: SlayerMaster.Task ->
            taskWeightSum[0] += task.weight
            tasks.add(task)
        }
        tasks.shuffle(RandomFunction.RANDOM)
        var rnd = RandomFunction.random(taskWeightSum[0])
        for (task in tasks) {
            if (rnd < task!!.weight) return task.task
            rnd -= task.weight
        }
        return null
    }

    /**
     * Checks if a task can be assigned to the player based on their Slayer level and quest requirements.
     *
     * @param player The player to check against.
     * @param task The task to be checked.
     * @return True if the task can be assigned, false otherwise.
     */
    fun canBeAssigned(player: Player, task: Tasks): Boolean {
        return player.getSkills()
            .getLevel(Skills.SLAYER) >= task.levelReq && !SlayerManager.getInstance(player).flags.removed.contains(task) && task.hasQuestRequirements(
            player
        )
    }

    /**
     * Assigns a task to the player and updates their task-related information.
     *
     * @param player The player to whom the task is assigned.
     * @param task The task to be assigned.
     * @param master The SlayerMaster assigning the task.
     */
    fun assign(player: Player, task: Tasks, master: SlayerMaster) {
        SlayerManager.getInstance(player).master = master
        SlayerManager.getInstance(player).task = task
        SlayerManager.getInstance(player).amount =
            RandomFunction.random(master.assigmentCount[0], master.assigmentCount[1])
        if (master == SlayerMaster.DURADEL) {
            player.achievementDiaryManager.finishTask(player, DiaryType.KARAMJA, 2, 8)
        } else if (master == SlayerMaster.VANNAKA) {
            player.achievementDiaryManager.finishTask(player, DiaryType.VARROCK, 1, 14)
        }
        setVarp(player, 2502, SlayerManager.getInstance(player).flags.taskFlags shr 4)
    }

    /**
     * Checks if the player has a broad weapon equipped.
     *
     * @param player The player to check.
     * @param state The current battle state of the player.
     * @return True if a broad weapon is equipped, false otherwise.
     */
    @JvmStatic
    fun hasBroadWeaponEquipped(player: Player, state: BattleState): Boolean {
        return (state.weapon != null && state.weapon.id == Items.LEAF_BLADED_SPEAR_4158 || state.weapon != null && state.weapon.id == Items.LEAF_BLADED_SWORD_13290 || state.ammunition != null && (state.ammunition.itemId == Items.BROAD_ARROW_4160 || state.ammunition.itemId == Items.BROAD_TIPPED_BOLTS_13280) || state.spell != null && state.spell.spellId == 31 && player.spellBookManager.spellBook == SpellBook.MODERN.interfaceId)
    }
}
