package core.game.node.entity.player.link.diary

import core.api.addDialogueAction
import core.api.sendDialogue
import core.api.sendMessage
import core.api.setInterfaceText
import core.cache.def.impl.NPCDefinition
import core.game.component.Component
import core.game.diary.DiaryLevel
import core.game.node.entity.player.Player
import core.game.node.item.Item
import org.json.simple.JSONArray
import org.json.simple.JSONObject

/**
 * Achievement diary
 *
 * @property type
 * @constructor Achievement diary
 */
class AchievementDiary(val type: DiaryType) {

    val levelStarted: BooleanArray = BooleanArray(3)
    val levelRewarded: BooleanArray = BooleanArray(3)
    val taskCompleted: Array<BooleanArray> = Array(type.achievements.size) { BooleanArray(25) }

    /**
     * Open
     *
     * @param player
     */
    fun open(player: Player) {
        clear(player)
        sendString(player, "<red>Achievement Diary - " + type.diaryName, 2)
        var child = 12
        sendString(player, ((if (isComplete) GREEN else if (isStarted) YELLOW else "<red>") + type.diaryName) + " Area Tasks", child++)
        if (type.info.isNotEmpty() && !this.isStarted) {
            sendString(player, type.info, child++)
            child += type.info.split("<br><br>".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().size
        }
        child++

        var complete: Boolean
        var line: String
        for (level in type.achievements.indices) {
            sendString(player, getStatus(level) + getLevel(level) + "", child++)
            child++
            for (i in type.getAchievements(level).indices) {
                complete = isComplete(level, i)
                line = type.getAchievements(level)[i]
                if (line.contains("<br><br>")) {
                    val lines = line.split("<br><br>".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    for (l in lines) {
                        sendString(player, if (complete) "<str><str>$l" else l, child++)
                    }
                } else {
                    sendString(player, if (complete) "<str><str>$line" else line, child++)
                }
                sendString(player, "*", child++)
            }
            child++
        }
        /*
         * sendString(player, builder.toString(), 11);
         * Changes the size of the scroll bar
         * player.getPacketDispatch().sendRunScript(1207, "i", new Object[] { 330 });
         * sendString(player, builder.toString(), 11);
         */
        if (!player.interfaceManager.isOpened) {
            player.interfaceManager.open(Component(DIARY_COMPONENT))
        }
    }

    private fun clear(player: Player) {
        for (i in 0..310) {
            setInterfaceText(player, "", DIARY_COMPONENT, i)
        }
    }

    /**
     * Parse
     *
     * @param data
     */
    fun parse(data: JSONObject) {
        val startedArray = data["startedLevels"] as JSONArray
        for (i in startedArray.indices) {
            levelStarted[i] = startedArray[i] as Boolean
        }
        val completedArray = data["completedLevels"] as JSONArray
        for (i in completedArray.indices) {
            val level = completedArray[i] as JSONArray
            var completed = true
            for (j in level.indices) {
                taskCompleted[i][j] = level[j] as Boolean
                if (!taskCompleted[i][j]) {
                    completed = !completed
                }
                completedLevels.add(i)
            }
        }
        val rewardedArray = data["rewardedLevels"] as JSONArray
        for (i in rewardedArray.indices) {
            levelRewarded[i] = rewardedArray[i] as Boolean
        }
    }

    /**
     * Draw status
     *
     * @param player
     */
    fun drawStatus(player: Player) {
        if (isStarted) {
            setInterfaceText(player, (if (isComplete) GREEN else YELLOW) + type.diaryName, 259, type.buttonId)
            for (i in 0..2) {
                setInterfaceText(player, (if (isComplete(i)) GREEN else if (isStarted(i)) YELLOW else "<col=FF0000>") + getLevel(i), 259, type.buttonId + (i + 1))
            }
        }
    }

    /**
     * Update task
     *
     * @param player
     * @param level
     * @param index
     * @param complete
     */// TODO: NPC for each level, NPC location for each level.
    fun updateTask(player: Player, level: Int, index: Int, complete: Boolean) {
        if (!levelStarted[level]) {
            levelStarted[level] = true
        }
        if (!complete) {
            sendMessage(player, "Well done! A " + type.diaryName + " task has been updated.")
        } else {
            taskCompleted[level][index] = true
            val tempLevel = if (this.type == DiaryType.LUMBRIDGE) level - 1 else level
            sendMessage(player,"Well done! You have completed " + (if (tempLevel == -1) "a beginner" else if (tempLevel == 0) "an easy" else if (tempLevel == 1) "a medium" else "a hard") + " task in the " + type.diaryName + " area. Your Achievement")
            sendMessage(player, "Diary has been updated.")
        }
        if (isComplete(level)) {
            val message = "Congratulations! You have completed all of the " + getLevel(level).lowercase() + " tasks in the " + type.diaryName + " area."
            sendMessage(player, message)
            sendDialogue(player, message)
            addDialogueAction(player){ player1: Player?, buttonId: Int ->
                if (buttonId == 3) {
                    if (isComplete(level) != levelStarted[level]) {
                        sendDialogue(player1!!, "To upgrade your reward visit " + NPCDefinition.forId(type.npcs[level]).name.lowercase() + " in " + type.diaryName + ".")
                    } else {
                        sendDialogue(player1!!, "Speak to " + NPCDefinition.forId(type.npcs[level]).name.lowercase() + " to claim your reward.")
                    }
                }
            }
            return
        }
        drawStatus(player)
    }

    /**
     * Finish task
     *
     * @param player
     * @param level
     * @param index
     */
    fun finishTask(player: Player, level: Int, index: Int) {
        if (!this.isComplete(level, index)) {
            this.updateTask(player, level, index, true)
            var complete = true
            for (i in taskCompleted[level].indices) {
                if (!taskCompleted[level][i]) {
                    complete = false
                    break
                }
            }
            if (complete) {
                completedLevels.add(level)
            } else if (completedLevels.contains(level)) completedLevels.removeAt(level)
        }
    }

    /**
     * Reset task
     *
     * @param player
     * @param level
     * @param index
     */
    fun resetTask(player: Player, level: Int, index: Int) {
        taskCompleted[level][index] = false
        if (!isStarted(level)) {
            levelStarted[level] = false
        }
        if (!isComplete(level)) {
            levelRewarded[level] = false
        }
        drawStatus(player)
    }

    /**
     * Check complete
     *
     * @param level
     * @return
     */
    fun checkComplete(level: DiaryLevel): Boolean {
        if (type != DiaryType.LUMBRIDGE && level == DiaryLevel.BEGINNER) {
            return false
        }

        if (level == DiaryLevel.BEGINNER) {
            return completedLevels.contains(level.ordinal)
        }

        return completedLevels.contains(level.ordinal - 1)
    }

    private fun sendString(player: Player, string: String, child: Int) {
        setInterfaceText(player, string.replace("<blue>", BLUE).replace("<red>", RED), DIARY_COMPONENT, child)
    }

    /**
     * Set level started
     *
     * @param level
     */
    fun setLevelStarted(level: Int) {
        levelStarted[level] = true
    }

    /**
     * Set completed
     *
     * @param level
     * @param index
     */
    fun setCompleted(level: Int, index: Int) {
        taskCompleted[level][index] = true
    }

    /**
     * Is started
     *
     * @param level
     * @return
     */
    fun isStarted(level: Int): Boolean {
        return levelStarted[level]
    }

    val isStarted: Boolean
        get() {
            for (j in type.levelNames.indices) {
                if (isStarted(j)) {
                    return true
                }
            }
            return false
        }

    /**
     * Is complete
     *
     * @param level
     * @param index
     * @return
     */
    fun isComplete(level: Int, index: Int): Boolean {
        return taskCompleted[level][index]
    }

    /**
     * Is complete
     *
     * @param level
     * @return
     */
    fun isComplete(level: Int): Boolean {
        for (i in type.getAchievements(level).indices) {
            if (!taskCompleted[level][i]) {
                return false
            }
        }
        return true
    }

    /**
     * Is complete
     *
     * @param level
     * @param cumulative
     * @return
     */
    fun isComplete(level: Int, cumulative: Boolean): Boolean {
        return if (isComplete(level)) {
            !cumulative || level <= 0 || isComplete(level - 1, true)
        } else {
            false
        }
    }

    val isComplete: Boolean
        get() {
            for (i in taskCompleted.indices) {
                for (x in type.getAchievements(i).indices) {
                    if (!taskCompleted[i][x]) {
                        return false
                    }
                }
            }
            return true
        }

    val level: Int
        get() = if (isComplete(2)) 2 else if (isComplete(1)) 1 else if (isComplete(0)) 0 else -1

    val reward: Int
        get() = if (isLevelRewarded(2)) 2 else if (isLevelRewarded(1)) 1 else if (isLevelRewarded(0)) 0 else -1

    /**
     * Get level
     *
     * @param level
     * @return
     */
    fun getLevel(level: Int): String {
        return type.levelNames[level]
    }

    /**
     * Get status
     *
     * @param level
     * @return
     */
    fun getStatus(level: Int): String {
        return if (!isStarted(level)) RED else if (isComplete(level)) GREEN else YELLOW
    }

    /**
     * Set level rewarded
     *
     * @param level
     */
    fun setLevelRewarded(level: Int) {
        levelRewarded[level] = true
    }

    /**
     * Is level rewarded
     *
     * @param level
     * @return
     */
    fun isLevelRewarded(level: Int): Boolean {
        return levelRewarded[level]
    }

    companion object {
        const val DIARY_COMPONENT: Int = 275
        val completedLevels: ArrayList<Int> = ArrayList()
        private const val RED = "<col=8A0808>"
        private const val BLUE = "<col=08088A>"
        private const val YELLOW = "<col=F7FE2E>"
        private const val GREEN = "<col=3ADF00>"

        fun removeRewardsFor(player: Player, type: DiaryType, level: Int): Boolean {
            val rewards = type.getRewards(level)
            /*
             * Lamps are always the 2nd reward for a level, don't remove lamps.
             */
            val hasRemoved =
                (player.inventory.remove(rewards[0])
                        || player.bank.remove(rewards[0])
                        || player.equipment.remove(rewards[0]))

            if (hasRemoved) {
                player.debug("Removed previous reward")
            }

            return hasRemoved
        }

        fun addRewardsFor(player: Player, type: DiaryType, level: Int): Boolean {
            val rewards = type.getRewards(level)

            val freeSlots = player.inventory.freeSlots()
            if (freeSlots < rewards.size) return false

            var allRewarded = true
            for (reward in rewards) {
                allRewarded = allRewarded and player.inventory.add(reward)
            }

            if (!allRewarded) {
                rewards.forEach { item: Item? ->
                    val ignored = player.inventory.remove(item)
                }
            }

            return allRewarded
        }

        fun flagRewarded(player: Player, type: DiaryType, level: Int): Boolean {
            if (level > 0) {
                removeRewardsFor(player, type, level - 1)
            }
            if (addRewardsFor(player, type, level)) player.achievementDiaryManager.getDiary(type)!!.setLevelRewarded(level)
            else {
                sendMessage(player, "You do not have enough space in your inventory to claim these rewards.")
                return false
            }

            return true
        }

        fun canReplaceReward(player: Player, type: DiaryType, level: Int): Boolean {
            val reward = type.getRewards(level)[0]
            val claimed = (hasCompletedLevel(player, type, level)
                    && hasClaimedLevelRewards(player, type, level)
                    && !player.hasItem(reward))
            return if (level == 2) claimed else claimed && !hasClaimedLevelRewards(player, type, level + 1)
        }

        fun grantReplacement(player: Player, type: DiaryType, level: Int): Boolean {
            val reward = type.getRewards(level)[0]
            /*
             * Can only replace non-lamp reward.
             */
            return canReplaceReward(player, type, level) && player.inventory.add(reward)
        }

        fun hasCompletedLevel(player: Player, type: DiaryType, level: Int): Boolean {
            if (level > type.levelNames.size - 1) return false
            return player.achievementDiaryManager.getDiary(type)!!.isComplete(level, true)
        }

        fun hasClaimedLevelRewards(player: Player, type: DiaryType, level: Int): Boolean {
            return player.achievementDiaryManager.getDiary(type)!!.isLevelRewarded(level)
        }

        fun canClaimLevelRewards(player: Player, type: DiaryType, level: Int): Boolean {
            return if (level == 2) // Cannot be a higher level to claim.
                hasCompletedLevel(player, type, level) && !hasClaimedLevelRewards(player, type, level)
            else !hasClaimedLevelRewards(player, type, level + 1) && hasCompletedLevel(player, type, level) && !hasClaimedLevelRewards(player, type, level)
        }

        fun getRewards(type: DiaryType, level: Int): Array<Item> {
            return type.getRewards(level)
        }
    }
}
